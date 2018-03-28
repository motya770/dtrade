// Define the `phonecatApp` module
//var diamondApp = angular.module('diamondApp', []);

// Define the `PhoneListController` controller on the `phonecatApp` module
diamondApp.controller('GraphController', function GraphController($scope, $http) {

    var self = this;

    //var setQuotes = function(){};

    var init = function(formattedData){
        var data = [];
        $('#graph-container').highcharts('StockChart', {
            rangeSelector: {
                enabled: false
            },
            title: {
                text: 'TEST'
            },
            series: [{
                showInLegend: false,
                name: 'AAPL',
                data: formattedData,
                tooltip: {
                    valueDecimals: 2
                }
            }]
        });
    };



    $http.get('/graph/get-quotes').then(function(response) {
        self.quotes = response.data;

        // Create the chart
        var data = response.data;
        var formattedData = [data.length + 1];

        for (var i in data) {
            var point = new Array(2);
            //console.log(data[i].time + " " + new Date(data[i].time));
            point[0] = new Date(data[i].time);
            point[1] = data[i].price;
            formattedData[i] = point;

        }

        init(formattedData);
    });
});


/*

var Graph = {
    _graph: null,
    init: function () {
        var data = [];
        $('#graph-container').highcharts('StockChart', {

            rangeSelector: {
                enabled: false
            },

            title: {
                text: ''
            },

            series: [{
                name: 'AAPL',
                data: data,
                tooltip: {
                    valueDecimals: 2
                }
            }]
        });
    },
    _setupTechnicalAnalysesLink: function (assetExternalId) {
        $(".technical-analysis a").attr("href", "/technical-analysis?assetExternalId=" + assetExternalId.replace("/", ""));
    },
    currentAssetExternalId: function(){
        return $(".graph-block .graph-header .asset-external-id").text().trim();
    },
    addQuoteToGraph: function (price, time) {
        var timeValue = parseInt(time);
        var priceValue = parseFloat(price);

        Graph._graph.series[0].addPoint([timeValue, priceValue], true, false);
    },
    _addFlag: function (positionOpenTime) {
        var positionFlag = {
            turboThreshold: 0,
            id: "flagSeries",
            allowPointSelect: true,
            type: 'flags',
            data: [{
                x: positionOpenTime,
                title: ' '
            }],
            onSeries: 'dataseries',
            shape: 'url(/resources/images/main/open_pos.png)',
            width: 16
        };
        return positionFlag;
    },
    _addNewPositionFlag: function (positionOpenTime) {

        var flagPoint = {
            x: positionOpenTime,
            turboThreshold: 0,
            title: ' '
        };
        //Graph._graph.series[1].turboThreshold = 0;
        Graph._graph.series[1].addPoint(flagPoint);
    },
    _loadGraphData: function (assetExternalId, bidStartTime, tradeTime, closeTime, positionGraph, positions, shortTerm, pipSize) {
        Graph._setupTechnicalAnalysesLink(assetExternalId);
        $(".graph-header .asset-name").text(assetExternalId);
        $(".graph-header .asset-external-id").text(assetExternalId);

        var url = "";
        var startRange = bidStartTime - 30000;
        var dataStart = null;
        var dataEnd = null;

        if (positionGraph) {
            if(shortTerm){
               var openTime  =  parseInt($(".open-time", positions[positions.length - 1]).text());
               dataStart =  openTime - 10000;
               dataEnd = closeTime;

               startRange = dataStart;
               closeTime = openTime + 60000;
            }else{
               dataStart = (bidStartTime - 30000);
               dataEnd = closeTime;
            }

            url = "/graph/get-quotes?assetExternalId=" +
            assetExternalId + "&openTime=" + dataStart + "&closeTime=" + dataEnd;
        } else {

            dataEnd = closeTime;

            if(shortTerm){
                dataStart = (Date.now() - 5 * 60 * 1000);
            }else{
                dataStart = (Date.now() - 20 * 60 * 1000);
            }

            url = "/graph/get-quotes?assetExternalId=" +
                assetExternalId + "&openTime=" + dataStart + "&closeTime=" + dataEnd;
        }

        var endRange = closeTime;

        $.getJSON(url, function (data) {
            if (data.error) {
                Notifications.error(data.message);
                return;
            }
            // Create the chart
            var formattedData = [data.length + 1];

            for (var i in data) {
                var point = new Array(2);
                //console.log(data[i].time + " " + new Date(data[i].time));
                point[0] = data[i].time;
                point[1] = data[i].value;
                formattedData[i] = point;
            }

            var series = [{
                id: 'dataseries',
                threshold: null,
                type: 'area',
                name: assetExternalId,
                data: formattedData,
                tooltip: {
                    valueDecimals: pipSize
                },
                fillColor: {
                    linearGradient: {x1: 0, y1: 0, x2: 0, y2: 1},
                    stops: [
                        [0, Highcharts.getOptions().colors[0]],
                        [1, Highcharts.Color(Highcharts.getOptions().colors[0]).setOpacity(0).get('rgba')]
                    ]
                }
            }, {
                turboThreshold: 0,
                id: "flagSeries",
                allowPointSelect: true,
                type: 'flags',
                data: [],
                onSeries: 'dataseries',
                shape: 'url(/resources/images/main/open_pos.png)',
                width: 16
            }];

            $(positions).each(function () {
                console.log('test 1');
                var p = this;
                var positionOpenTime = parseInt($(".open-time", p).text());
                var positionFlag = Graph._addFlag(positionOpenTime);
                series.push(positionFlag);
            });

            Highcharts.setOptions({
                global : {
                    useUTC : false
                }
            });

            var plotLines = new Array();
            if(shortTerm){
                if(positionGraph) {
                    plotLines = [
                        {color: 'red', value: closeTime, width: 2}
                    ];
                }
            }else{
                plotLines = [
                    {color: 'blue', value: bidStartTime,  width: 2},
                    {color: 'green', value: tradeTime, width: 2},
                    {color: 'red', value: closeTime, width: 2}
                ];
            }

            var chartOptions = {
                navigator: {adaptToUpdatedData: false},
                rangeSelector: {enabled: false},
                chart: {renderTo: 'graph-container'},
                title: {text: null},
                yAxis: {},
                xAxis: {min: startRange, max: endRange, ordinal: false, maxPadding: 0.5, plotLines: plotLines},
                series: series
            }

            Graph._graph = new Highcharts.StockChart(chartOptions, function () {
            });
        });
    },
    loadPositionGraph: function (position) {

        var assetExternalId = $(".asset-external-id", position).text();
        var tradeTime = parseInt($(".trade-time", position).text());
        var closeTime = null;

        var positions = new Array();
        positions.push(position);

        var bidStartTime = parseInt($(".bid-start-time", position).text());
        var closeTime = parseInt($(".close-time", position).text());

        var optionType = $(".option-type", position).text();
        var isShortTerm =  (optionType == "SHORT_TERM");
        var pipSize =  parseInt($(".pip-size", position).text());

        Graph._loadGraphData(assetExternalId, bidStartTime, tradeTime, closeTime, true, positions, isShortTerm);
    },
    loadGraph: function () {
        var assetExternalId = Options.getSelectedAssetExternalId();
        if (assetExternalId == null || assetExternalId == "") {
            return;
        }
        var option = Options.getSelectedOption();
        var optionId = $(".option-id", option).text();

        var positions = $(".open-positions-container .open-positions .open-position.option-id-" + optionId);

        var bidStartTime = parseInt($(".open-time", option).text());
        var tradeTime = parseInt($(".trade-time", option).text())
        var closeTime = parseInt($(".close-time", option).text())

        var optionType = $(".option-type", option).text();
        var isShortTerm =  (optionType == "SHORT_TERM");
        var pipSize =  parseInt($(".pip-size", option).text());

        Graph._loadGraphData(assetExternalId, bidStartTime, tradeTime, closeTime, false, positions, isShortTerm, pipSize);
    }
}*/