diamondApp.controller('ChartController', function ($scope, $timeout, $http, AvailableService) {

    $http.post('/graph/get-quotes?diamond=2', null).then(function(response)  {

        //255 92 92 red  #ff5c5c
        //73 209 109 green #49d16d

        var data = response.data;

        var points = [data.length + 1];
        for(var i in data){
            var point = new Array(5);
            point[0] = data[i].time;
            point[1] = data[i].bid;
            point[2] = data[i].ask;
            point[3] = data[i].bid;
            point[4] = data[i].ask;

            points[i] = point;
        }

        var simpleTheme = {
            "colors": ["#d35400", "#2980b9", "#2ecc71", "#f1c40f", "#2c3e50", "#7f8c8d"],
            "chart": {
                "style": {
                    "fontFamily": "Roboto",
                    "color": "#666666"
                }
            },
            "title": {
                "align": "left",
                "style": {
                    "fontFamily": "Roboto Condensed",
                    "fontWeight": "bold"
                }
            },
            "subtitle": {
                "align": "left",
                "style": {
                    "fontFamily": "Roboto Condensed"
                }
            },
            "legend": {
                "align": "right",
                "verticalAlign": "bottom"
            },
            "xAxis": {
                "gridLineWidth": 1,
                "gridLineColor": "#F3F3F3",
                "lineColor": "#F3F3F3",
                "minorGridLineColor": "#F3F3F3",
                "tickColor": "#F3F3F3",
                "tickWidth": 1
            },
            "yAxis": {
                "gridLineColor": "#F3F3F3",
                "lineColor": "#F3F3F3",
                "minorGridLineColor": "#F3F3F3",
                "tickColor": "#F3F3F3",
                "tickWidth": 1
            },
            "plotOptions": {
                "line": {
                    "marker": {
                        "enabled": false
                    }
                },
                "spline": {
                    "marker": {
                        "enabled": false
                    }
                },
                "area": {
                    "marker": {
                        "enabled": false
                    }
                },
                "areaspline": {
                    "marker": {
                        "enabled": false
                    }
                },
                "bubble": {
                    "maxSize": "10%"
                }
            }
        };

        Highcharts.setOptions(simpleTheme);

        // create the chart
        Highcharts.stockChart('container', {

            rangeSelector: {
                enabled: false
            },

            title: {
                //text: 'AAPL Stock Price'
            },

            series: [{
                type: 'candlestick',
                //name: 'AAPL Stock Price',
                data: points,
                dataGrouping: {
                    units: [
                        [
                            'week', // unit name
                            [1] // allowed multiples
                        ], [
                            'month',
                            [1, 2, 3, 4, 6]
                        ]
                    ]
                }
            }]
        });
    });



    $scope.$on('buyDiamondChoosed', function (event, arg) {
        //getChartData(arg.id);
        //getCategoryScoreData(arg.score);
    });

    $scope.$on('ownedDiamondChoosed', function (event, arg) {
       // getChartData(arg.id);
        //getCategoryScoreData(arg.score);
    });

    $scope.$on('openForSaleDiamondChoosed', function (event, arg) {
        //getChartData(arg.id);
        //getCategoryScoreData(arg.score);
    });

});