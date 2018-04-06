diamondApp.controller("BookOrderController", function BookOrderController($scope, $http, $rootScope, $timeout, $interval, BookOrderService) {

    var self = this;
    self.firstTimeOut = true;

    var callBookOrderService = function() {
        if(self.diamond==null){
            $timeout(callBookOrderService, 2000);
            return;
        }

        BookOrderService.getBookOrder(self.diamond).then(function (data) {
            self.bookOrder = data;
            if(self.firstTimeOut) {
                self.firstTimeOut = false;
                $interval(callBookOrderService, 1000);
            }
        });
    }

    callBookOrderService();

    $scope.$on('buyDiamondChoosed', function (event, arg) {
        self.diamond = arg;
        //callBookOrderService();
    });

    $scope.$on('ownedDiamondChoosed', function (event, arg) {
        self.diamond = arg;
        //callBookOrderService();
    });

    $scope.$on('openForSaleDiamondChoosed', function (event, arg) {
        self.diamond = arg;
        //callBookOrderService();
    });

    $scope.$on('buyDiamondChoosed', function (event, arg) {
        self.diamond = arg;
        //callBookOrderService();
    });
});