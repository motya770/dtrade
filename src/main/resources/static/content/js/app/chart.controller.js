diamondApp.controller('ChartController', function ($scope, $timeout, $http, AvailableService) {
    $scope.chartConfig = {
        options: {
            chart: {
                zoomType: 'x'
            },
            rangeSelector: {
                enabled: true
            },
            navigator: {
                enabled: true
            }
        },

        series: [],
        title: {
            text: 'Diamond Price'
        },

        useHighStocks: true
    }

    var pushToChart = function(data, seriesId, name){
        $scope.chartConfig.series.push({
                id: seriesId,
                data: data,
                name: name
            }
        );
    };

    var self = this;
    //var diamondId = 1;
    var getChartData = function(diamondId){
        $http.post('/graph/get-quotes?diamond=' + diamondId, null).then(function(response) {
            var data = response.data;

            console.log("l:" + data.length)
            var bids = [data.length + 1];
            var asks = [data.length + 1];
            for (var i in data) {
                var bidPoint = new Array(2);
                bidPoint[0] = data[i].time;
                bidPoint[1] = data[i].bid;
                bids[i] = bidPoint;

                var askPoint = new Array(2);
                askPoint[0] = data[i].time;
                askPoint[1] = data[i].ask;
                asks[i] = askPoint;
            }

            pushToChart(bids, 1, "Bids");
            pushToChart(asks, 2, "Asks");
        });
    };


    var getCategoryScoreData = function (score) {

        //TODO
        return;
        $http.post("/category-tick/for-score?score="+score, null).then(function (responce) {
            var data = responce.data;
            var formattedData = [data.length + 1];
            for (var i in data){
                var point = new Array(2);
                point[0] = data[i].time;
                point[1] = data[i].avarage;
                formattedData[i] = point;
            }

            pushToChart(formattedData, 2);

        });
    }

    //init for graph
    AvailableService.getAvailable().then(function (data) {
        if(data!=null && data.length>0){
            var firstDiamond = data[0];
            getChartData(firstDiamond.id);
            getCategoryScoreData(firstDiamond.score);
        }
    })

    $scope.$on('buyDiamondChoosed', function (event, arg) {
        getChartData(arg.id);
        getCategoryScoreData(arg.score);
    });

    $scope.$on('ownedDiamondChoosed', function (event, arg) {
        getChartData(arg.id);
        getCategoryScoreData(arg.score);
    });

    $scope.$on('openForSaleDiamondChoosed', function (event, arg) {
        getChartData(arg.id);
        getCategoryScoreData(arg.score);
    });
});