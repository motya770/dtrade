// Define the `phonecatApp` module
var diamondApp = angular.module('diamondApp', []);

// Define the `PhoneListController` controller on the `phonecatApp` module
diamondApp.controller('DiamondListController', function DiamondListController($scope) {
    $scope.diamonds = [
        {
            id: '123',
            name: 'Fast just got faster with Nexus S.'
        }, {
            id: '34',
            name: 'The Next, Next Generation tablet.'
        }, {
            id: '09324',
            name: 'The Next, Next Generation tablet.'
        }
    ];
});