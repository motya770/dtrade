<html ng-app="diamondApp">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <title>TRO</title>


    <link rel="stylesheet" type="text/css" href="/content/libs/bootstrap/css/bootstrap-select.css">


    <link rel="stylesheet" href="/content/css/style.css"/>
    <link rel="stylesheet" href="/content/css/diamond.css"/>

    <!--
    <script src="/resources/js/hightstock/highstock.js"></script>
    <script src="/resources/js/hightstock/theme.js"></script>
    -->

    <script src="/bower_components/jquery/dist/jquery.min.js" type="text/javascript"></script>
    <script src="/bower_components/bootstrap/dist/js/bootstrap.min.js" type="text/javascript"></script>


    <script src="/bower_components/angular/angular.min.js" type="text/javascript"></script>
    <script src="/bower_components/angular-resource/angular-resource.min.js" type="text/javascript"></script>


    <script src="/bower_components/highcharts/highstock.js" type="text/javascript"></script>
    <script src="/bower_components/highcharts-ng/dist/highcharts-ng.js" type="text/javascript"></script>


    <script src="/content/js/index.js"></script>


    <!--
    <script src="/content/js/test.js"></script>
    -->

    <link rel="stylesheet" type="text/css" href="/bower_components/bootstrap/dist/css/bootstrap.min.css"/>


	<script type="text/javascript">
	$(document).ready(function() {
		//$(".fancybox").fancybox();
	});
	</script>
	
	<!-- Add fancyBox -->
    <!--
    <link rel="stylesheet" href="/content/libs/fancybox/source/jquery.fancybox.css?v=2.1.5" type="text/css" media="screen" />
    <script type="text/javascript" src="/content/libs/fancybox/source/jquery.fancybox.pack.js?v=2.1.5"></script>
    -->

</head>
<body>


<div>
    <span>Current Account</span>
    <div ng-controller="AccountController as vm">
        <div>{{vm.user.mail}}</div>
        <div>{{vm.user.balance}}</div>
    </div>


    <div>
        <form action="/login" method="post">
            <input type="text" name="username" /><br/>
            <input type="password" name="password"><br/>
            <input type="submit" value="Login"/>
        </form>
    </div>

</div>



 <div class="container tro">
     <div class="row header pull-left">
         <a href="/">
             <div class="logo pull-left">
                 UNITY diamonds <span>Trade Diamonds</span>
             </div>
         </a>
         <ul class="inline pull-right menu">
            <li><a href="/">Главная</a></li>
             <li><a href="/">Торги</a></li>
             <li><a href="/">Обучение</a></li>
             <li class="profile"><a href="/"> <i></i> Профиль</a></li>
         </ul>
     </div>

     <div class="row">


         <div ng-controller="AvailableController as vm">
             <div class="left-sidebar pull-left">
                 <div class="row">
                     <div class="table-header-blue">
                         <span class="pull-left">Available diamonds</span>
                     </div>
                     <table class="table-striped table-bordered statistic">
                         <thead>

                         <tr>
                             <th>Number</th>
                             <th>Diamond Name</th>
                             <th>Price</th>
                             <th>Type</th>
                             <th>Carats</th>
                             <th>Clarity</th>
                             <th>Score</th>
                             <th>Total Stock</th>
                             <!--
                             <th>Pic</th>
                             -->
                         </tr>
                         </thead>
                         <tbody>

                         <tr ng-repeat="diamond in vm.availableDiamonds" ng-click="chooseAvailableDiamond(diamond)">
                                     <td>{{diamond.id}}</td>
                                     <td>{{diamond.name}}</td>
                                     <td>{{diamond.price}} $</td>
                                     <td>{{diamond.diamondType}}</td>
                                     <td>{{diamond.carats}}</td>
                                     <td>{{diamond.clarity}}</td>
                                     <td>{{diamond.totalStockAmount}}</td>
                             <!--
                                     <td> <a class="fancybox" rel="group" href="../static/content/image/real_diamond.png"><img src="../static/content/image/diamond.png" alt="" /></a></td>
                               -->
                         </tr>


                         <tr class="green-tr">
                             <td>09923</td>
                             <td>Kings Diamons</td>
                             <td>25 502 $</td>

                             <td>Emirald</td>
                             <td>50</td>
                             <td>0.34</td>
                             <!--
                             <td> <img src="../static/content/image/diamond.png" /> </td>
                             -->
                             <td>10</td>
                         </tr>

                         <tr class="options-closed">
                             <td>4123</td>
                             <td colspan="6">Diamond Sold</td>
                         </tr>
                         </tbody>
                     </table>
                 </div>
             </div>
         </div>

         <div class="content pull-left">
            <div class="row content-two-column">

                <div ng-controller="BidderController as vm">
                    <div class="pull-left button-block">

                        <div class="buy-block">
                            <div>
                                Do You want to buy {{vm.buyDiamond.name}} ({{vm.buyDiamond.id}})?
                            </div>

                            <div class="price">PRICE:  <input ng-model="vm.buyOrder.price"> $</input></div>
                            <div class="amount">AMOUNT: <input ng-model="vm.buyOrder.amount"> $</input></div>

                            <input type="hidden" ng-model="vm.buyOrder.tradeOrderType" ng-init="vm.buyOrder.tradeOrderType='BUY'" />

                            <a class="button black" href="#" ng-click="$event.preventDefault(); createTradeOrder(vm.buyOrder, vm.buyDiamond)">BUY ORDER</a>
                        </div>


                        <div class="sell-block">
                            <div>
                                Open {{vm.sellDiamond.name}} ({{vm.sellDiamond.id}}) For Sale?
                            </div>

                            <div class="price">PRICE:</div>
                            <div class="btn-group size">
                                <button class="btn">-</button>
                                <input ng-model="vm.sellOrder.price" type="text" class="btn"/>
                                <button class="btn">+</button>
                            </div>
                            <div class="amount">AMOUNT: <input ng-model="vm.sellOrder.amount"> $</input></div>

                            <input type="hidden" ng-model="vm.sellOrder.tradeOrderType" ng-init="vm.sellOrder.tradeOrderType='SELL'" />
                            <a href="#" class="button black" ng-click="$event.preventDefault(); createTradeOrder(vm.sellOrder, vm.sellDiamond)">SELL ORDER</a>
                        </div>

                        <!--
                        <div class="buy-block">
                            <div>
                                Do You want to buy {{vm.buyDiamond.name}} ({{vm.buyDiamond.id}})?
                            </div>

                            <div class="price">PRICE: <span>{{vm.buyDiamond.price}} $</span></div>
                            <a class="button black" href="#" ng-click="$event.preventDefault(); buyDiamond(vm.buyDiamond, vm.currentAccount)">BUY</a>

                        </div>
                        <div class="sell-block" ng-show="vm.sellDiamond">
                            <div>
                                Open {{vm.sellDiamond.name}} ({{vm.sellDiamond.id}}) For Sale?
                            </div>

                            <div class="btn-group size">
                                <button class="btn">-</button>
                                <input ng-model="vm.sellDiamond.price" value="{{vm.sellDiamond.price}}" type="text" class="btn"/>
                                <button class="btn">+</button>
                            </div>
                            <a href="#" class="button black" ng-click="$event.preventDefault(); sellDiamond(vm.sellDiamond, vm.currentAccount)">SALE</a>
                        </div>
                        -->
                    </div>
                </div>



                <div class="pull-right clearfix graph-block">
                        <div class="graph-header">
                            <a class="pull-left" href="/">Technical analysis</a>
                            <span class="date pull-right">Mar 26 2015    11:06:19</span>
                        </div>

                        <div ng-controller="ChartController as vm">
                            <div class="row">
                                <highchart id="chart1" config="chartConfig" class="span10"></highchart>
                            </div>
                        </div>
                </div>


                <div class="row">
                    <a class="after-graph pull-right" href="/">Trade History</a>
                </div>


                <div ng-controller="TradeOrderController as vm">
                    <div style="margin-top:20px;">Live orders</div>
                    <table class="table-striped big-table table-bordered statistic" >
                        <thead>
                        <tr>
                            <th>Number</th>
                            <th>Diamond Name</th>
                            <th>Amount</th>
                            <th>Price</th>
                            <th>Date</th>
                            <th>Status</th>
                            <th>Type</th>
                            <th>----</th>
                        </tr>
                        </thead>
                        <tbody>
                        <tr ng-repeat="tradeOrder in vm.liveTradeOrders">
                            <td>{{tradeOrder.id}}</td>
                            <td>{{tradeOrder.diamond.name}}</td>
                            <td>{{tradeOrder.amount}}</td>
                            <td>{{tradeOrder.price}}</td>
                            <td>{{tradeOrder.creationDate}}</td>
                            <td>{{tradeOrder.traderOrderStatus}}</td>
                            <td>{{tradeOrder.tradeOrderType}}</td>
                            <td><a href="#" class="button black" ng-click="$event.preventDefault(); cancelTradeOrder(tradeOrder)">CANCEL</a></td>
                        </tr>
                        </tbody>
                    </table>


                    <div style="margin-top:20px;">History orders</div>
                    <table class="table-striped big-table table-bordered statistic" >
                        <thead>
                        <tr>
                            <th>Number</th>
                            <th>Diamond Name</th>
                            <th>Amount</th>
                            <th>Price</th>
                            <th>Date Created</th>
                            <th>Date Executed</th>
                            <th>Status</th>
                            <th>Type</th>
                        </tr>
                        </thead>
                        <tbody>
                        <tr ng-repeat="tradeOrder in vm.historyTradeOrders">
                            <td>{{tradeOrder.id}}</td>
                            <td>{{tradeOrder.diamond.name}}</td>
                            <td>{{tradeOrder.amount}}</td>
                            <td>{{tradeOrder.price}}</td>
                            <td>{{tradeOrder.creationDate}}</td>
                            <td>{{tradeOrder.executionDate}}</td>
                            <td>{{tradeOrder.traderOrderStatus}}</td>
                            <td>{{tradeOrder.tradeOrderType}}</td>
                        </tr>
                        </tbody>
                    </table>

                </div>


                <!--
                <div ng-controller="OwnedController as vm">
                    <div style="margin-top:20px;">Owned diamonds</div>
                    <table class="table-striped big-table table-bordered statistic" >
                        <thead>
                        <tr>
                            <th>Number</th>
                            <th>Name</th>
                            <th>Buy Price</th>
                            <th>Buy Date</th>
                            <th>Market Price</th>
                            <th>Type</th>
                            <th>Carats</th>
                            <th>Clarity</th>

                        </tr>
                        </thead>
                        <tbody>
                        <tr ng-repeat="diamond in vm.ownedDiamonds" ng-click="chooseOwnedDiamond(diamond)">
                             <td>{{diamond.id}}</td>
                             <td>{{diamond.name}}</td>
                             <td> $</td>
                             <td>26 june 16</td>
                             <td>{{diamond.price}} $</td>
                             <td>{{diamond.diamondType}}</td>
                             <td>{{diamond.carats}}</td>
                             <td>{{diamond.clarity}}</td>
                        </tr>
                        </tbody>
                    </table>
                </div>


                <div ng-controller="SaleController as vm">
                    <div style="margin-top:20px;">Diamonds for the sale</div>
                    <table class="table-striped big-table table-bordered statistic">

                        <thead>
                        <tr>
                            <th>Number</th>
                            <th>Name</th>
                            <th>Buy Price</th>
                            <th>Buy Date</th>
                            <th>Sell Price</th>
                            <th>Type</th>
                            <th>Carats</th>
                            <th>Clarity</th>
                            <th>Status</th>
                            <th>Cancel</th>
                        </tr>
                        </thead>3
                        <tbody>
                        <tr ng-repeat="diamond in vm.saleDiamonds" ng-click="chooseOpenForSale(diamond)">
                             <td>{{diamond.id}}</td>
                             <td>{{diamond.name}}</td>
                             <td>$</td>
                             <td></td>
                             <td>$</td>
                             <td>{{diamond.diamondType}}</td>
                             <td>{{diamond.carats}}</td>
                             <td>{{diamond.clarity}}</td>
                             <td>{{diamond.status}}</td>
                            <td><button ng-click="hideFromSale(diamond)"></button></td>
                        </tr>
                        </tbody>
                    </table>
                </div>
                -->
            </div>
         </div>
     </div>

     <div ng-controller="RegisterController as vm">
         <form novalidate class="simple-form">

             E-mail: <input type="text" ng-model="user.email" /><br />
             Phone: <input type="text" ng-model="user.phone" /><br />
             Password: <input type="pwd" ng-model="user.pwd" /><br />
             <!--
             Capcha: <input type="pwd" ng-model="user.captcha" /><br />
             -->
             <!--
             <div id="Recaptcha"></div>
              -->

             <input type="button" ng-click="reset()" value="Reset" />
             <input type="submit" ng-click="update(user)" value="Save" />
         </form>
         <pre>user = {{user | json}}</pre>
         <pre>master = {{master | json}}</pre>
     </div>


     <!--
     <div ng-controller="LoginController as vm">
         <form novalidate class="simple-form">
             E-mail: <input type="text" ng-model="user.email" /><br />
             Password: <input type="pwd" ng-model="user.pwd" /><br />
             <input type="submit"  value="Login" />
         </form>
     </div>
       -->


     <div class="row footer pull-left">
        <div class="row footer-top">
            <a href="/">
                <div class="logo pull-left">
                    UNITY diamonds <span>Trade Diamonds</span>
                </div>
            </a>
            <div class="copyright pull-right">
                <span>© 2014-2015</span> Правовая информация
            </div>
        </div>
         <div class="row footer-bottom">
                <ul class="inline social">
                    <li><a class="facebook" href="/"></a></li>
                    <li><a class="twitter" href="/"></a></li>
                    <li><a class="in" href="/"></a></li>
                    <li><a class="google" href="/"></a></li>
                    <li><a class="wk" href="/"></a></li>
                </ul>
         </div>
     </div>
 </div>

 <script src="/content/js/main.js"></script>


 <!--
 <script src="//www.google.com/recaptcha/api.js?onload=CaptchaCallback&render=explicit" async defer></script>
 <script type="text/javascript">
     var CaptchaCallback = function () {
         grecaptcha.render('Recaptcha', {'sitekey': '6LeIRwUTAAAAAIYGH56PGrgIZj-nyJ8As2_BiiwM'});
     };
 </script>
-->
</body>
</html>