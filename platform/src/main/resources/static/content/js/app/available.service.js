diamondApp.service('AvailableService', function($http, $q){
    var availableDiamonds = [];


    var getAvailable = function(name) {
        // if(availableDiamonds!=null && availableDiamonds.length > 0) {
        //     return $q.resolve(ownedDiamonds)
        // }else{
            return $http.post('/diamond/available?name=' + name , null).then(function(response) {
                availableDiamonds = response.data;
                return availableDiamonds;
            });
        //}
    };

    var getDiamondByBaseCurrency = function (currency) {
        for(var i in availableDiamonds){
            var diamond = availableDiamonds[i];
            if(diamond.currency == currency){
                return diamond;
            }
        }
    }

    return {
        getAvailable: getAvailable,
        getDiamondByBaseCurrency: getDiamondByBaseCurrency,
    };
});
