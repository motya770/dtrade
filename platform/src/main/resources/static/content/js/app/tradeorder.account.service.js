diamondApp.service("TradeOrderAccountService", function ($http, $q) {

    var historyOrders = [];

    var getAccountHistoryTradeOrders = function (pageNumber) {

        if(pageNumber==null){
            pageNumber = 0;
        }
        return $http.post("/trade-order/account-history-orders?pageNumber="+ pageNumber, null, null).then(function (responce) {
            historyOrders = responce.data;
            return historyOrders;
        });
        /*
        if (historyOrders != null && historyOrders.length != 0) {
            return $q.resolve(historyOrders)
        }else{

        }*/
    };

    return {
        getAccountHistoryTradeOrders: getAccountHistoryTradeOrders
    }
});

