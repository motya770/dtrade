
diamondApp.factory( 'AccountService', ["$http", function($http) {

    var self = this;
    self.currentAccount;

    var currentAccountCall = function() {
        return $http.post('/accounts/get-current').then(function(response){
            if(self.currentAccount!=null){
                self.currentAccount.balance = response.data.balance;
            }else{
                self.currentAccount = response.data;
            }
            return self.currentAccount;
        });
    };

    return {
        currentAccount: currentAccountCall,
        refreshCurrentAccount: currentAccountCall
    }
    /*

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
    }*/
}]);