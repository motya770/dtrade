diamondApp.controller('ChartController', function ($scope, $timeout, $http, $interval, $timeout, $q, DiamondService, AvailableService) {

    var self = this;
    self.isFirstTimeout = true;
    self.graph = null;
    self.lastTimeQuote = null;

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
            //graph.series[0].addPoints(points);
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

    var parseQuotesToArray = function (data) {
        var result = [];

        var points = [data.length + 1];
        for(var i in data){
            var point = new Array(2);
            point[0] = data[i].time;
            point[1] = (data[i].bid + data[i].ask)/2;

            if(i==data.length - 1){
                self.lastTimeQuote = data[i].time;
            }

            result.push(point);
            /*
            point[0] = data[i].time;
            point[1] = data[i].bid;
            point[2] = data[i].ask;
            point[3] = data[i].bid;
            point[4] = data[i].ask;
            points[i] = point;
            */
        }
        return result;
    }

    var addNewPoints = function (that){

        var series = that.series[0];
        //$interval(function () {
            $http.post('/graph/get-quotes?diamond=2&start=' + self.lastTimeQuote, null).then(function(response) {
                var result = parseQuotesToArray(response.data);
                console.log(result.length);
                for(var i in result){
                    series.addPoint(result[i], true, true);
                    if(i == result.length - 1){
                        self.lastTimeQuote = result[i][0];
                    }
                }
                console.log("last time: " + self.lastTimeQuote);
            });
       // }, 1000);
    }

    var consFunc = function () {
        console.log("Test1");
    };
    var getGraphData = function () {

         // + DiamondService.getCurrentDiamond().id,
        $http.post('/graph/get-quotes?diamond=2', null).then(function(response)  {

            //255 92 92 red  #ff5c5c
            //73 209 109 green #49d16d
            //209 209 209
            var result = parseQuotesToArray(response.data);

            self.graph = Highcharts.stockChart('container', {
                chart: {
                    events: {
                        load:function () {
                            console.log("load1");
                             var that = this;
                             $interval( function () {
                                 addNewPoints(that);
                             }, 1000);
                        },
                    }
                },

                rangeSelector: {
                    buttons: [{
                        count: 1,
                        type: 'minute',
                        text: '1M'
                    }, {
                        count: 5,
                        type: 'minute',
                        text: '5M'
                    }, {
                        type: 'all',
                        text: 'All'
                    }],
                    inputEnabled: false,
                    selected: 0
                },

                title: {
                    text: 'Live random data'
                },

                exporting: {
                    enabled: false
                },

                series: [{
                    name: 'Random data',
                    data: result
                }]
            });
        });
    };



    //var data  = getGraphData();
    //$timeout(addNewPoints(graph), 3000);

    getGraphData();
});