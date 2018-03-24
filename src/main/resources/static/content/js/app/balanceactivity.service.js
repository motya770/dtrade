diamondApp.service('BalanceActivityService', function BalanceActivityService($http, $q){
    var balanceActivities = [];

    var getBalanceActivities = function() {
        if(balanceActivities!=null && balanceActivities.length > 0) {
            return $q.resolve(balanceActivities)
        }else{
            return $http.post('/balance-activity/by-account', null).then(function(response) {
                balanceActivities = response.data;
                return balanceActivities;
            });
        }
    };

    return {
        getBalanceActivities: getBalanceActivities
    };
});
