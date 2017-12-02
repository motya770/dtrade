diamondApp.controller("BookOrderController", function BookOrderController($scope, $http, $rootScope, BookOrderService) {

    var self = this;

    function callBookOrderService() {
        BookOrderService.getBookOrder(self.diamond).then(function (data) {
            self.bookOrder = data;
        });
    }

    $scope.$on('buyDiamondChoosed', function (event, arg) {
        self.diamond = arg;
        callBookOrderService();
    });

    $scope.$on('ownedDiamondChoosed', function (event, arg) {
        self.diamond = arg;
        callBookOrderService();
    });

    $scope.$on('openForSaleDiamondChoosed', function (event, arg) {
        self.diamond = arg;
        callBookOrderService();
    });

    $scope.$on('buyDiamondChoosed', function (event, arg) {
        self.diamond = arg;
        callBookOrderService();
    });
});