diamondApp.service('OwnedService', function($http, $q){
    var ownedDiamonds = [];

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

    var addOwnedDiamond = function (diamond) {
        ownedDiamonds.push(diamond);
    }
    var hideOwnedDiamond=function (diamond) {
        ownedDiamonds.splice(arrayObjectIndexOf(ownedDiamonds, diamond),1);
    }

    return {
        addOwnedDiamond: addOwnedDiamond,
        hideOwnedDiamond: hideOwnedDiamond,
        getOwnedDiamonds: getOwnedDiamonds,
        addOwned: addOwned
    };
});