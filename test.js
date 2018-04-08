diamondApp.controller('ChartController', function ($scope, $timeout, $http, $interval, $timeout, DiamondService, AvailableService) {

    var self = this;
    self.isFirstTimeout = true;
    var graph = null;

    var getChartData = function () {

        if(DiamondService.getCurrentDiamond() == null){
            $timeout(getChartData, 1000);
            return;
        }

        $http.post('/graph/get-quotes?diamond=' + DiamondService.getCurrentDiamond().id, null).then(function(response)  {

            //255 92 92 red  #ff5c5c
            //73 209 109 green #49d16d
            //209 209 209

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

            // create the chart


            if(self.isFirstTimeout){
                self.isFirstTimeout = false;
                $interval(getChartData, 2000);
            }
            graph.series[0].addPoints(points);
        });
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

    graph = Highcharts.stockChart('container', {

        rangeSelector: {
            enabled: false
        },

        title: {
            //text: 'AAPL Stock Price'
        },

        series: [{
            type: 'candlestick',
            //name: 'AAPL Stock Price',
            data: null,
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

    getChartData();
});