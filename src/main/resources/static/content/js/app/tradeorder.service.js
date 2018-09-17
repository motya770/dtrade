diamondApp.service("TradeOrderService", function ($http, $q, DiamondService) {

    var liveOrders = null;

    var historyOrders = [];

    var addLiveOrder = function(order){
        if(liveOrders.content==""){
            liveOrders.content = new Array();
        }
        liveOrders.content.push(order);
    };

    var clearHistoryOrders = function (diamond) {
        historyOrders.length = 0;
        //getHistoryOrders(diamond);
    }

    var addHistoryOrder = function (order) {
        liveOrders.content.splice(arrayObjectIndexOf(liveOrders, order),1);
        historyOrders.push(order);
    };

    var getHistoryOrders = function (diamond) {

        // if (historyOrders != null && historyOrders.length != 0) {
        //     return $q.resolve(historyOrders)
        // }else{

            // var diamond = {}
            // diamond["diamond"] = diamondObj;
            return $http.post("/trade-order/history-orders", diamond.id, null).then(function (responce) {
                if(historyOrders==null) {
                    //historyOrders = responce.data;
                }else {
                    //historyOrders.length = 0;
                    //historyOrders = responce.data.slice();
                }

                return historyOrders;
            });
        //}
    };

    var getLiveOrders = function (pageNumber) {


        /*if (liveOrders != null && liveOrders.content!=null && liveOrders.content.length != 0) {
            return $q.resolve(liveOrders.content)
        }else{*/
            var url;
            if(pageNumber==null){
               url =  "/trade-order/live-orders"
            }else {
               url = "/trade-order/live-orders?pageNumber=" + pageNumber;
            }

            return $http.post(url, null, null).then(function (response) {
                liveOrders = response.data;
                console.log("LO: " + liveOrders.content);
                return liveOrders;
            });
        //}
    };

    var ws = new WebSocket('ws://localhost:8083');

    // Listen for messages
    ws.addEventListener('message', function (event) {
        console.log('Message from server ', event.data);
        var data  = JSON.parse(event.data);
        var channel = data.channel;
        if(channel=='executed_tradeOrder'){

            var diamond = DiamondService.getCurrentDiamond();
            if(diamond.id == data.entity.diamondId) {
                historyOrders.unshift(data.entity);
                if(historyOrders.length>10) {
                    historyOrders.pop();
                }
            }
        }
    });

    return {
        getHistoryOrders: getHistoryOrders,
        getLiveOrders: getLiveOrders,
        addLiveOrder: addLiveOrder,
        addHistoryOrder: addHistoryOrder,
        clearHistoryOrders: clearHistoryOrders
    }
});

