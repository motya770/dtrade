diamondApp.service("DiamondService", function DiamondService($http, $rootScope, $q) {

    var diamonds = [];
    var currentDiamond = null;

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

    var getCurrentDiamond = function () {
        return currentDiamond;
    }

    $rootScope.$on('buyDiamondChoosed', function (event, arg) {
        currentDiamond = arg;
    });

    $rootScope.$on('ownedDiamondChoosed', function (event, arg) {
        currentDiamond = arg;
    });

    $rootScope.$on('openForSaleDiamondChoosed', function (event, arg) {
        currentDiamond = arg;
    });

    return {
        getCurrentDiamond: getCurrentDiamond,
        getAllEnlistedDiamonds: getAllEnlistedDiamonds
    }
});

