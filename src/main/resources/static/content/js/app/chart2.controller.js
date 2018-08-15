diamondApp.controller('ChartController', function ($scope, $timeout, $http, $interval, $timeout, $q, DiamondService, AvailableService) {

    var self = this;
    self.isFirstTimeout = true;
    self.graph = null;
    self.lastTimeQuote = null;
    self.lastDiamondId = null;
    self.poolingPromise = null;

    var simpleTheme = {
        "time": {
            "useUTC": false
        },
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
        var result = data;
        if(data.length>0) {
            self.lastTimeQuote = data[data.length - 1][0];
        }
        /*
        var points = [data.length + 1];
        for(var i in data){
            var point = new Array(2);
            point[0] = data[i].time;
            point[1] = (data[i].bid + data[i].ask)/2;

            if(i==data.length - 1){

            }

            result.push(point);

        }*/
        /*
            point[0] = data[i].time;
            point[1] = data[i].bid;
            point[2] = data[i].ask;
            point[3] = data[i].bid;
            point[4] = data[i].ask;
            points[i] = point;
            */

        return result;
    }

    var addNewPoints = function (that){

        if(that.series == null){
            return;
        }
        var series = that.series[0];
        $http.post('/graph/get-quotes?diamond=' + DiamondService.getCurrentDiamond().id +  '&start=' + self.lastTimeQuote, null).then(function(response) {
            var result = parseQuotesToArray(response.data);
            //console.log(result.length);
            for(var i in result){
                series.addPoint(result[i], true, true);
                if(i == result.length - 1){
                    self.lastTimeQuote = result[i][0];
                }
            }
           // console.log("last time: " + self.lastTimeQuote);
        });
    }

    var getGraphData = function () {

        if(DiamondService.getCurrentDiamond()==null){
            $timeout(getGraphData, 500, false);
            return;
        }

        $http.post('/graph/get-quotes?diamond=' + DiamondService.getCurrentDiamond().id, null).then(function(response)  {

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
                            self.poolingPromise = $interval( function () {
                                addNewPoints(that);
                            }, 1000, 0, false);
                        },
                    },

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
                    selected: 4
                },

                // rangeSelector: {
                //     buttons: [{
                //         count: 1,
                //         type: 'minute',
                //         text: '1M'
                //     }, {
                //         count: 5,
                //         type: 'minute',
                //         text: '5M'
                //     }, {
                //         type: 'all',
                //         text: 'All'
                //     }],
                //     inputEnabled: false,
                //     selected: 0
                // },

                title: {
                    text: ''
                },

                exporting: {
                    enabled: false
                },

                series: [{
                    name: '',
                    data: result

                }]
            });
        });
    };


    var reloadGraph = function () {
        if(self.poolingPromise){
            $interval.cancel(self.poolingPromise);

            getGraphData();
            getDepthGraph();
        }
    }

    $scope.$on('buyDiamondChoosed', function (event, arg) {
        reloadGraph();
    });

    $scope.$on('ownedDiamondChoosed', function (event, arg) {
        reloadGraph();
    });

    $scope.$on('openForSaleDiamondChoosed', function (event, arg) {
        reloadGraph();
    });


  var getDepthGraph = function () {
      if(DiamondService.getCurrentDiamond()==null){
          $timeout(getDepthGraph, 500, false);
          return;
      }

      $http.get('/graph/get-depth-quotes?diamond=' + DiamondService.getCurrentDiamond().id).then(function(response) {

          var data = response.data;

          var bidList = data["first"];
          var askList = data["second"];

          var bidData = new Array();
          var askData = new Array();

          if(bidList != null) {
              for (var i = 0; i < bidList.length; i++) {
                  var p = bidList[i];
                  var pointArr = new Array();
                  pointArr.push(p.price);
                  pointArr.push(p.amount);
                  bidData.push(pointArr);
              }
          }

          if(askList!=null) {
              for (var i = 0; i < askList.length; i++) {
                  var p = askList[i];
                  var pointArr = new Array();
                  pointArr.push(p.price);
                  pointArr.push(p.amount);
                  askData.push(pointArr);
              }
          }



          /*
          for (var i = 0; i < askData.length; i++) {
              askDepthTotal += askData[i][1];
              askData[i][1] = askDepthTotal;
          }

          for (var i = 0; i < bidData.length; i++) {
              bidDepthTotal += bidData[i][1];
              bidData[i][1] = bidDepthTotal;
          }*/


          var chart = Highcharts.chart('containerDepth', {
              chart: {
                  type: 'area',
                  zoomType: 'xy'
              },
              title: {
                  text: '',
                  style: {
                      color: '#000000'
                  }
              },
              subtitle: {
                  text: '',
                  style: {
                      color: '#000000'
                  }
              },
              xAxis: [{
                  type: 'logarithmic',
                  title: {
                      text: 'Price',
                      style: {
                          color: '#000000'
                      },
                  },
                  width: '50%',
                  labels: {
                      style: {
                          color: 'green'
                      }
                  }
              }, {
                  type: 'logarithmic',
                  title: {
                      text: 'Price',
                      style: {
                          color: '#000000'
                      },
                  },
                  labels: {
                      style: {
                          color: 'red'
                      }
                  },
                  offset: 0,
                  left: '50%',
                  width: '50%'
              }],
              yAxis: {
                  gridLineWidth: 0,
                  title: {
                      text: 'Depth',
                      style: {
                          color: '#000000'
                      }
                  }
              },
              legend: {
                  enabled: false
              },
              plotOptions: {
                  area: {
                      softThreshold: true,
                      marker: {
                          radius: 2
                      },
                      lineWidth: 2,
                      states: {
                          hover: {
                              lineWidth: 3
                          }
                      },
                      threshold: null
                  },
              },

              tooltip: {
                  shared: true,
                  useHTML: true,
                  headerFormat: '<table>',
                  pointFormat: '<tr><td>Price:</td><td style="text-align: right;">{point.x}</td></tr><tr><td>Sum ($):</td><td style="text-align: right;">{point.y}</td></tr>',
                  footerFormat: '</table>',
                  valueDecimals: 2
              },

              series: [{
                  name: 'Bids',
                  data: bidData,
                  color: '#48d26f',
                  fillColor: '#48d26f',
                  xAxis: 0,
              }, {
                  name: 'Asks',
                  data: askData,
                  color: '#ff6565',
                  fillColor: '#ff6565',
                  xAxis: 1,
              }]
          });
      });
  };



   $timeout(getDepthGraph, 1000, false);
   $timeout(getGraphData, 1000, false);
});