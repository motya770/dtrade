diamondApp.controller('RegisterController', ['$scope', '$http', function($scope, $http) {
    $scope.master = {};

    $scope.update = function(user) {
        //$scope.master = angular.copy(user);

        /*var config = {
            headers : {
                'Content-Type': 'application/x-www-form-urlencoded;charset=utf-8;'
            }
        };*/

        $http.post('/accounts/register', user).then(function(response){
            debugger;
            //$scope.status = response.status;
            //$scope.data = response.data;
        });
    };
}]);