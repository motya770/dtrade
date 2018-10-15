diamondApp.service('SaleService', function($http, $q, OwnedService){
    var saleDiamonds = [];

    var hideFromSale = function(diamond) {
        saleDiamonds.splice(arrayObjectIndexOf(saleDiamonds, diamond),1);
        OwnedService.addOwnedDiamond(diamond);
    };

    var addForSale = function (diamond) {
        saleDiamonds.push(diamond);
        OwnedService.hideOwnedDiamond(diamond);
    };

    var getForSaleDiamonds = function() {
        if (saleDiamonds != null && saleDiamonds.length != 0) {
            return $q.resolve(saleDiamonds)
        }else{
            return $http.post('/diamond/my-for-sale', null).then(function (response) {
                saleDiamonds = response.data;
                return saleDiamonds;
            });
        }
    };

    return {
        hideFromSale: hideFromSale,
        getForSaleDiamonds: getForSaleDiamonds,
        addForSale: addForSale,
    };
});