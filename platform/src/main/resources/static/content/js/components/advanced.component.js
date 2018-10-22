angular.
module('diamondApp').
component('advancedComponent', {
    // Note: The URL is relative to our `index.html` file
    templateUrl: '/content/js/templates/advanced.html'
})
.
component('basicComponent', {
    // Note: The URL is relative to our `index.html` file
    templateUrl: '/content/js/templates/basic.html'
})
.
component('coinsComponent', {
    // Note: The URL is relative to our `index.html` file
    templateUrl: '/content/js/templates/coins.html'
}).
component('loginFormComponent', {
    // Note: The URL is relative to our `index.html` file
    templateUrl: '/content/js/templates/login-form.html'
}).
component('accountComponent', {
    // Note: The URL is relative to our `index.html` file
    templateUrl: '/content/js/templates/account.html'
}).
component('referralComponent', {
    // Note: The URL is relative to our `index.html` file
    templateUrl: '/content/js/templates/referral.html'
});


