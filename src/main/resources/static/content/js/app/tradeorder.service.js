diamondApp.service("TradeOrderService", function ($http, $q) {

    var liveOrders = [];
    var historyOrders = [];

    var addLiveOrder = function(order){
        liveOrders.push(order);
    };


    var addHistoryOrder = function (order) {
        liveOrders.splice(arrayObjectIndexOf(liveOrders, order),1);
        historyOrders.push(order);
    };

    var getHistoryOrders = function () {

        if (historyOrders != null && historyOrders.length != 0) {
            return $q.resolve(historyOrders)
        }else{
            return $http.post("/trade-order/history-orders", null, null).then(function (responce) {
                historyOrders = responce.data;
                return historyOrders;
            });
        }
    };

    var getLiveOrders = function () {

        if (liveOrders != null && liveOrders.length != 0) {
            return $q.resolve(liveOrders)
        }else{
            return $http.post("/trade-order/live-orders", null, null).then(function (response) {
                liveOrders = response.data;
                return liveOrders;
            });
        }
    };

    return {
        getHistoryOrders: getHistoryOrders,
        getLiveOrders: getLiveOrders,
        addLiveOrder: addLiveOrder,
        addHistoryOrder: addHistoryOrder
    }
});

