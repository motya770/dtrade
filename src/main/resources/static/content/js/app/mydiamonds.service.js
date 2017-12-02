diamondApp.service('MyDiamondsService', function($http, $q){
    var ownedDiamonds = [];
    var saleDiamonds = [];

    var hideFromSale = function(diamond) {
        saleDiamonds.splice(arrayObjectIndexOf(saleDiamonds, diamond),1);
        ownedDiamonds.push(diamond);
    };

    var addForSale = function (diamond) {
        saleDiamonds.push(diamond);
        ownedDiamonds.splice(arrayObjectIndexOf(ownedDiamonds, diamond),1);
    };

    var addOwned = function (diamond) {
        ownedDiamonds.push(diamond);
    };

    var getOwnedDiamonds = function() {
        if (ownedDiamonds != null && ownedDiamonds.length != 0) {
            return $q.resolve(ownedDiamonds)
        }else{
            return $http.post('/diamond/my-owned', null).then(function(response) {
                ownedDiamonds = response.data;
                return ownedDiamonds;
            });
        }
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
        getOwnedDiamonds: getOwnedDiamonds,
        getForSaleDiamonds: getForSaleDiamonds,
        addForSale: addForSale,
        addOwned: addOwned
    };
});