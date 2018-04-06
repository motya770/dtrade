diamondApp.controller("MenuController", function MenuController($scope, $location) {
    $scope.getClass = function (path) {
        return window.location.pathname === path ? 'active' : '';
    }
});