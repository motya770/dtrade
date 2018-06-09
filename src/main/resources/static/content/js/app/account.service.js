
diamondApp.factory( 'AccountService', ["$http", function($http) {

    var self = this;
    var currentAccount;

    var currentAccountCall = function() {
        return $http.post('/accounts/get-current').then(function(response){
            currentAccount = response.data;
            return currentAccount;
        });
    };

    if(currentAccount!=null) {
        return {
            currentAccount: function () {
                return  currentAccount;
            },
            refreshCurrentAccount: function () {
                currentAccountCall();
            }
        }
    }else{
        return{
            currentAccount: function () {
                return currentAccountCall();
            },
            refreshCurrentAccount: function () {
                return currentAccountCall();
            }
        }
    }
}]);