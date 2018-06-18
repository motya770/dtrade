diamondApp.service('BookOrderService', function BookOrderService($http){
    var self = this;
    self.buyPrice = null;
    self.sellPrice = null;

    var getBookOrder = function(diamond) {
        return $http.post('/book-order/', diamond, null).then(function (response) {
            return response.data;
        });
    };
    var setSpread= function(sellPrice, buyPrice){
        
    }
    return {
        getBookOrder: getBookOrder,
        setSpread: setSpread
    };
});
