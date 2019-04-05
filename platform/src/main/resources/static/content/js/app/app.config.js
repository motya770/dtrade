angular.
module('diamondApp').
config(['$locationProvider', '$routeProvider',
    function config($locationProvider, $routeProvider) {
        $locationProvider.hashPrefix('!');

        $routeProvider.
        when('/trade', {
            template: '<advanced-component></advanced-component>'
        }).
        when('/basic', {
            template: '<basic-component></basic-component>'
        }).
        when('/coins', {
            template: '<coins-component></coins-component>'
        }).
        when('/login-form', {
            template: '<login-form-component></login-form-component>'
        }).
        when('/account', {
            template: '<account-component></account-component>'
        }).
        when('/referral', {
            template: '<referral-component></referral-component>'
        }).
        when('/forgot-password', {
            template: '<forgot-password-component></forgot-password-component>'
        }).
        when('/recovery-password', {
            template: '<recovery-password-component></recovery-password-component>'
        }).
        otherwise('/trade');
    }
]);