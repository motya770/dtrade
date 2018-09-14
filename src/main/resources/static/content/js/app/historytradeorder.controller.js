diamondApp.controller("HistoryTradeOrderController", function TradeOrderController($scope, $q, $rootScope, $http, $interval, $timeout,
                                                                                   DiamondService, AccountService,
                                                                                   TradeOrderService){

    console.log("start: " + new Date());

    $scope.tab = 3;
    var self = this;
   // self.firstTimeOut = true;
   // var historyInterval;

    var historyOrders = function() {
        if(DiamondService.getCurrentDiamond()==null){
            $timeout(historyOrders, 50, false);
            return;
        }
        TradeOrderService.getHistoryOrders(DiamondService.getCurrentDiamond()).then(function (data) {
            self.historyTradeOrders = data;
        });
    };

    historyOrders();

    $scope.$on('buyDiamondChoosed', function (event, arg) {
        var diamond = arg;
        TradeOrderService.clearHistoryOrders(diamond);
    });


   // var promise = $interval(historyOrders, 1000);

// Cancel interval on page changes
    /*
    $scope.$on('$destroy', function(){
        if (angular.isDefined(promise)) {
            $interval.cancel(promise);
            promise = undefined;
        }
    });*/

    //historyOrders();
});
