
diamondApp.factory( 'AccountService', ["$http", function($http) {

    var self = this;
    self.currentAccount;

    var currentAccount = function () {
        return self.currentAccount;
    };

    var currentAccountRefresh = function() {
        return $http.post('/accounts/get-current').then(function(response){
            if(self.currentAccount!=null){
                self.currentAccount.balance = response.data.balance;
            }else{
                self.currentAccount = response.data;
            }
            return self.currentAccount;
        });
    };

    var currentAccountCall = function () {
        if(self.currentAccount!=null){
            return currentAccountRefresh();
        }else{
            return currentAccountRefresh();
        }
    }
    
    var refreshAccount = function (account) {
        self.currentAccount.balance = account.balance;
    }
    
    return {
        currentAccount: currentAccount,
        refreshAccount: refreshAccount,
        currentAccountCall: currentAccountCall,
        refreshCurrentAccount: currentAccountRefresh
    };
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