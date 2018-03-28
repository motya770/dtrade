diamondApp.service("DiamondService", function ($http, $q) {

    var diamonds = [];

    var getAllEnlistedDiamonds = function () {

        return $http.post("/diamond/available", null, null).then(function (responce) {
            diamonds = responce.data;
            return diamonds;
        });
        /*
        ??//TODO what to do with it
        if (historyOrders != null && historyOrders.length != 0) {
            return $q.resolve(historyOrders)
        }else{}
        */
    };

    return {
        getAllEnlistedDiamonds: getAllEnlistedDiamonds
    }
});

