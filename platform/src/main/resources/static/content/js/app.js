var diamondApp = angular.module('diamondApp', ['ngMaterial','ngMessages', 'ngCookies', 'ngRoute',
    'angular-nicescroll', 'ngMaterialDatePicker', 'pascalprecht.translate', 'ngSanitize'])
    .filter('diamondTypeFilter', function() {
        return function(input) {
            input = input || '';
            var out = '';
            for (var i = 0; i < 3; i++) {
                var nextChar;
                if(i==0){
                    nextChar = input.charAt(i).toUpperCase();
                }else{
                    nextChar =input.charAt(i).toLowerCase();
                }
                out = out + nextChar;
            }
            return out;
        };
    })
.controller("TopController", [ '$scope', '$timeout', '$mdDialog', function($scope, $timeout, $mdDialog, AccountSesrvice, DiamondService) {
    $scope.loaded = false;
   // $scope.title = "This is an App";
    $scope.$on('accountReceived', function (event, arg) {
        if(!$scope.loaded){
            $scope.loaded = true;
        }
    });

  $scope.showAdvanced = function(diamond, ev) {
    var self = this;
    self.currentDiamond = diamond;

    $mdDialog.show({
      controller: DialogController,
      templateUrl: 'dialog1.tmpl.html',
      parent: angular.element(document.body),
      targetEvent: ev,
      clickOutsideToClose:true
    });
  };

  //$scope.diamondImage = "/image/diamond-image?image=" + 3;//"/image/diamond-image?image=" + DiamondService.getCurrentDiamond().images[0].id;

  function DialogController(DiamondService, $scope, $mdDialog) {
      $scope.diamond = DiamondService.getCurrentDiamond();
      $scope.diamondImage = "/image/diamond-image?image=" + $scope.diamond.images[0].id;
      var number = 0;
      var pic = $scope.diamond.images;
      var len = pic.length;
      $scope.primitiveRotator = function(element){
          number++;
          number = number == len ? 0 : number;
          $scope.diamondImage  = "/image/diamond-image?image=" + $scope.diamond.images[number].id;
      }

    $scope.hide = function() {
      $mdDialog.hide();
    };

    $scope.cancel = function() {
      $mdDialog.cancel();
    };

    $scope.answer = function(answer) {
      $mdDialog.hide(answer);
    };
  }

}]);

var translations = {
    BOOK_ORDER : {
       TRADE_HISTORY: 'TRADE HISTORY',
       ORDER_BOOK: 'ORDER BOOK',
       AMOUNT : 'Amount',
       PRICE : 'Price',
       TIME : 'Time',
       SPREAD : 'Spread',
    },
    ITEMS_LISTING: {
        MAIN_CAPTION_LISTING : ' STOCKS AND COINS LISTING',
        SUB_CAPTION_LISTING : ' All Stocks and coins are available',
        ITEM_NAME : 'Stocks and coins',
        BID: 'Bid',
        ASK: 'ASK'
    },
    GRAPH: {
        GRAPH: 'GRAPH',
        DEPTH: 'DEPTH'
    },
    ACCOUNT: {
        ACCOUNT: 'Account',
        BALANCE: 'Balance'
    },
    MY_ACCOUNT: {
        MY_HISTORY: 'MY HISTORY',
        BALANCES: 'BALANCES',
        TRADE_HISTORY: 'TRADE HISTORY',
        BALANCE_ACTIVITY: 'BALANCE ACTIVITY',
        CURRENCY: 'Currency',
        AMOUNT: 'Amount',
        EQUITY: 'Equity ($)',
        AVERAGE_PRICE: 'Average price',
        TODAY_PROFIT : 'Today profit',
        TOTAL_PROFIT:  'Total profit',
        DATE_CREATED: 't created',
        DATE_EXECUTED: 't executed',
        NAME: 'Name',
        PRICE: 'Price',
        SUM: 'Sum',
        STATUS: 'Status',
        OPERATION_TYPE: 'Operation type',
        BASE_CURRENCY: 'Base currency',
        BALANCE: 'Balance',
        PREVIOUS_ACTIVITIES: 'Previous activities',
        SUMMARY: 'Summary',
        PREVIOUS_ORDERS: 'Previous orders',
        FROM: 'From',
        TO: 'To'
    },
    PERSONAL: {
        OPEN_ORDERS: 'OPEN ORDERS',
        HISTORY_ORDERS: 'HISTORY ORDERS',
        MY_STOCKS_AND_COINS: 'MY STOCKS AND COINS',
        NAME: 'Name',
        PRICE: 'Price',
        AMOUNT: 'Amount',
        EXECUTED_SUM: 'Executed sum',
        T_CREATION: 't creation',
        STATUS: 'Status',
        TYPE: 'Type',
        ACTION: 'Action',
        PREVIOUS_ORDERS: 'Previous orders',
        T_CREATED: 't created',
        T_EXECUTED: 't executed',
    },
    BIDDER: {
         MARKET: 'MARKET',
         LIMIT: 'LIMIT',
         BUY: 'BUY',
         SELL: 'SELL',
         AMOUNT: 'Amount',
         LIMIT_PRICE: 'Limit price',
         TOTAL: 'Total',
         PLACE_ORDER: 'PLACE ORDER'
    },
    MENU: {
        BASIC: 'BASIC',
        ADVANCED: 'ADVANCED',
        MY_ACCOUNT: 'MY ACCOUNT',
        REFERRAL: 'REFERRAL'
    },
    LOGIN: {
        LOGIN: 'LOGIN',
        EMAIL: 'Email (required)',
        PASSWORD: 'Password',
        REGISTRATION: 'Registration',
        NEW_PASSWORD: 'New password',
        CONFIRM_NEW_PASSWORD: 'Confirm new password',
        I_ACCEPT : 'I accept the terms of service.',
        REGISTER: 'REGISTER'
    }
};

diamondApp.config(['$translateProvider', function ($translateProvider) {
    $translateProvider
         // .useStaticFilesLoader({
         //     prefix: '/locale/locale-',
         //     suffix: '.json'
         // })
         //.useSanitizeValueStrategy('sanitizeParameters')
    $translateProvider
        .translations('en', translations)
        .preferredLanguage('en');
}]);

