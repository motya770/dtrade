<html>
<head>
    <script src="/bower_components/highcharts/highstock.js" type="text/javascript"></script>
    <script src="https://code.jquery.com/jquery-3.1.1.min.js"></script>
    <script>
        $.getJSON("/content/test.json", function (data) {


            var defaultTheme = {

                chart: {
                    animation: false,
                    backgroundColor: '#0f0f0f',
                    borderRadius: 0,
                    borderWidth: 1,
                    borderColor: '#3c3c41',
                    plotBorderColor: "#353535",
                    plotBorderWidth: 0,
                    plotBackgroundColor: "transparent",
                    plotShadow: false,
                    spacingTop:0,
                    spacingBottom: 0,
                    marginRight: 0,
                    marginLeft: 0,
                    scrollbar: {
                        enabled: false
                    },
                    spacingBottom: 5
                },

                series: {
                    lineWidth : 1
                },
                xAxis: {
                    lineWidth: 1,
                    gridLineWidth: 1,
                    gridLineColor: '#2c2d2f',
                    lineColor: "#3c3c41",
                    gridLineDashStyle : 'ShortDash',
                    backgroundColor: '#272727',
                    tickWidth: 1,
                    tickColor: "#2c2d2f",
                    tickPosition: "outside"
                },

                yAxis: {
                    lineWidth: 0,
                    gridLineWidth: 1,
                    gridLineColor: '#2c2d2f',
                    lineColor: "#3c3c41",
                    /*gridColor: "#FFFFFF",*/
                    gridLineDashStyle : 'ShortDash',
                    //minPadding: 0.35,
                    //maxPadding: 0.35,
                    opposite: true,
                    tickColor: "#2c2d2f",
                    tickWidth: 0,
                    tickPosition: "inside",
                    labels: {
                        style: {
                            color: '#cccccc'
                        },
                        formatter: function(){
                            return this.value;
                        }
                    }
                },

                plotOptions: {
                    line: {
                        lineWidth : 1,
                        lineColor : '#FFFFF6'
                    },
                    series : {
                        marker: {
                            radius: 8
                        }
                    }

                },

                tooltip: {

                    backgroundColor: "#c1c1c1",

                    borderWidth: 0,
                    borderRadius: 10,
                    style: {
                        fontSize: '10px',
                        color : '#000',
                        fontWeight: 400,
                        padding: 5
                    },
                    useHTML: true,
                    shadow: false,
                    pointFormat: {

                    },

                    positioner: function(labelWidth, labelHeight, point) {
                        // above pointer
                        var y = (point.plotY || 0) - labelHeight-10,
                                x = point.plotX-labelWidth/2;

                        if (y < 0)
                            y = (point.plotY || 0) + 20;

                        if (x < 0)
                            x = 0;
                        else if (x > this.chart.chartWidth - labelWidth)
                            x = this.chart.chartWidth - labelWidth;


                        // if behind resolution buttons
                        if (/* x < 150 &&  */y < 27)
                        // random numbers by EN layout
                            y = (point.plotY || 10) + 15;
                        return {x: x, y: y};
                    },

                    formatter: function(){
                        var showHTML = "",
                                space = '   ',
                                data,
                                priceLabel = (typeof bmTemplatesMessageTexts !== "undefined" && bmTemplatesMessageTexts["tradeGraph-price"]) || "",
                                positionLabel = (typeof bmTemplatesMessageTexts !== "undefined" && bmTemplatesMessageTexts["tradeGraph-position"]) || "",
                                openLabel = (typeof bmTemplatesMessageTexts !== "undefined" && bmTemplatesMessageTexts["tradeGraph-candle-open"]) || "",
                                highLabel = (typeof bmTemplatesMessageTexts !== "undefined" && bmTemplatesMessageTexts["tradeGraph-candle-high"]) || "",
                                lowLabel = (typeof bmTemplatesMessageTexts !== "undefined" && bmTemplatesMessageTexts["tradeGraph-candle-low"]) || "",
                                closeLabel = (typeof bmTemplatesMessageTexts !== "undefined" && bmTemplatesMessageTexts["tradeGraph-candle-close"]) || "";


                        if ((data = this.points) && (data = data[0]) && data.series.userOptions.type == "candlestick"){
                            // Candles tooltip

                            data = data.point;

                            showHTML += '<div style="font-size: 11px; padding-right: 11px;">' + space;

                            if (moment && data.x) {
                                showHTML += "  " + moment(data.x).format("DD/MM/YYYY HH:mm") + '';
                            }

                            showHTML += "  <div>" + openLabel  + ": " + data.open  +   '</div> ';
                            showHTML += "  <div>" + highLabel  + ": " + data.high  +   '</div>';
                            showHTML += "  <div>" + lowLabel   + ": " + data.low   +   '</div>';
                            showHTML += "  <div>" + closeLabel + ": " + data.close +   '</div> </div>';

                            return showHTML;
                        }


                        // Regular tooltip
                        showHTML += ' <span style="font-size: 12px; padding-right: 11px;">' /*+ formatter.formatDateTime(this.x)*/;
                        showHTML += space;
                        if (this.series && this.series.type === "flags"){
                            showHTML += "  " + positionLabel + " #"+this.series.userOptions.displayId+": ";
                        } else {
                            showHTML += "  " + priceLabel + ": ";
                        }
                        showHTML += '' + this.y + '</span> ';
                        return showHTML;
                    }
                },

                exporting: {
                    enabled: false
                },

                scrollbar: {
                    /*barBackgroundColor: "#cacaca",*/
                    barBackgroundColor: {
                        linearGradient : {
                            x1 : 0,
                            y1 : 1,
                            x2 : 0,
                            y2 : 0
                        },
                        stops : [
                            [0, "#444" ],
                            [1, "#666" ]
                        ]
                    },
                    barBorderColor: "#353535",
                    /*barBorderWidth: 0,*/
                    buttonWidth: 0,
                    buttonArrowColor: "#ddd",
                    // buttonBackgroundColor: "#666",
                    buttonBackgroundColor: {
                        linearGradient : {
                            x1 : 0,
                            y1 : 1,
                            x2 : 0,
                            y2 : 0
                        },
                        stops : [
                            [0, "#444" ],
                            [1, "#666" ]
                        ]
                    },
                    buttonBorderColor: "#191919",
                    buttonBorderWidth: 0,
                    rifleColor: '#ddd',
                    /*trackBackgroundColor: {
                        linearGradient : {
                            x1 : 0,
                            y1 : 1,
                            x2 : 0,
                            y2 : 0
                        },
                        stops : [
                            [0, "rgba(150, 150, 150, 0.2)" ],
                            [1, "#d6d6d6" ]
                        ]
                    },*/
                    trackBackgroundColor: "#aaa",
                    trackBorderColor: 'rgba(0,0,0,0)'
                },

                global : {
                    useUTC : false
                }
            };

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
                    //selected: 1
                },

                title: {
                    //text: 'AAPL Stock Price'
                },

                series: [{
                    type: 'candlestick',
                    //name: 'AAPL Stock Price',
                    data: data,
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


    </script>
</head>
<body>


<div id="container" style="height: 400px; min-width: 310px; width: 600px;"></div>


</body>
</html>


