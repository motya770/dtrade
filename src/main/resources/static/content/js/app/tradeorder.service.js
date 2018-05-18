diamondApp.service("TradeOrderService", function ($http, $q) {

    var liveOrders = null;
    var historyOrders = [];

    var addLiveOrder = function(order){
        liveOrders.content.push(order);
    };


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
            return $http.post("/trade-order/history-orders", diamond, null).then(function (responce) {
                historyOrders = responce.data;
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
                return liveOrders;
            });
        //}
    };

    return {
        getHistoryOrders: getHistoryOrders,
        getLiveOrders: getLiveOrders,
        addLiveOrder: addLiveOrder,
        addHistoryOrder: addHistoryOrder
    }
});

