var app = angular.module('myApp', []);
app.config(['$httpProvider', function($httpProvider) {
    $httpProvider.defaults.timeout = 20000;
}])
app.controller('myCtrl', function($scope, $http) {
    this.retrieve = function() {
    $http.get('http://localhost:8300/api/stock-service/rest/stock/' + $scope.name)
    .then(function (response) {
        console.log('inside'+ response);
        $scope.quotes = response.data;
    }, function (response) {
        console.log('came here');
    });
    }


    this.add = function() {
        var message = {
            userName: $scope.name,
            quotes: [$scope.quote]
        }
        $http.post('http://localhost:8300/api/db-service/rest/db/quote/', message)
            .then(function(response) {
                $scope.quotes = response.data;
            }, function(response) {
                console.log('error..');
            });
    }
});