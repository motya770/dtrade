diamondApp.service("TradeOrderAccountService", function ($http, $q) {

    var historyOrders = [];

    var getAccountHistoryTradeOrders = function () {

        if (historyOrders != null && historyOrders.length != 0) {
            return $q.resolve(historyOrders)
        }else{
            return $http.post("/trade-order/account-history-orders", null, null).then(function (responce) {
                historyOrders = responce.data;
                return historyOrders;
            });
        }
    };

    return {
        getAccountHistoryTradeOrders: getAccountHistoryTradeOrders
    }
});

