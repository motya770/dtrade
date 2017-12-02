
diamondApp.factory( 'AccountService', ["$http", function($http) {

    var self = this;
    var currentAccount;

    if(currentAccount!=null) {
        return {
            currentAccount: function () {
                return  currentAccount;
            }
        }
    }else{
        return{
            currentAccount: function () {
                return $http.post('/accounts/get-current').then(function(response){
                    currentAccount = response.data;
                    return currentAccount;
                });
            }
        }
    }
}]);