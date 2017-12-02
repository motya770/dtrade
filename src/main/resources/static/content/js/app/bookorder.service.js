diamondApp.service('BookOrderService', function BookOrderService($http){
    var getBookOrder = function(diamond) {
        return $http.post('/book-order/', diamond, null).then(function (response) {
            return response.data;
        });
    };
    return {
        getBookOrder: getBookOrder
    };
});
