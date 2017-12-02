diamondApp.service('AvailableService', function($http, $q){
    var availableDiamonds = [];

    var getAvailable = function() {
        if(availableDiamonds!=null && availableDiamonds.length > 0) {
            return $q.resolve(ownedDiamonds)
        }else{
            return $http.post('/diamond/available', null).then(function(response) {
                availableDiamonds = response.data;
                return availableDiamonds;
            });
        }
    };

    return {
        getAvailable: getAvailable
    };
});
