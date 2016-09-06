<html ng-app="diamondApp">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <title>TRO</title>


    <link rel="stylesheet" type="text/css" href="/content/libs/bootstrap/css/bootstrap-select.css">


    <link rel="stylesheet" href="/content/css/style.css"/>
    <link rel="stylesheet" href="/content/css/diamond.css"/>


    <script src="/bower_components/jquery/dist/jquery.min.js" type="text/javascript"></script>
    <script src="/bower_components/bootstrap/dist/js/bootstrap.min.js" type="text/javascript"></script>

    <script src="/bower_components/angular/angular.min.js" type="text/javascript"></script>
    <script src="/bower_components/angular-resource/angular-resource.min.js" type="text/javascript"></script>

    <script src="/content/js/index.js"></script>

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
                             <th>Number {{5+3}}</th>
                             <th>Diamond Name</th>
                             <th>Price</th>
                             <th>Type</th>
                             <th>Carats</th>
                             <th>Clarity</th>
                             <th>Pic</th>
                         </tr>
                         </thead>
                         <tbody>


                         <tr ng-repeat="diamond in vm.availableDiamonds">
                                     <td>{{diamond.id}}</td>
                                     <td>{{diamond.name}}</td>
                                     <td>{{diamond.price}} $</td>
                                     <td>{{diamond.diamondType}}</td>
                                     <td>{{diamond.carats}}</td>
                                     <td>{{diamond.clarity}}</td>
                                     <td> <a class="fancybox" rel="group" href="../static/content/image/real_diamond.png"><img src="../static/content/image/diamond.png" alt="" /></a></td>
                         </tr>


                         <tr class="green-tr">
                             <td>09923</td>
                             <td>Kings Diamons</td>
                             <td>25 502 $</td>

                             <td>Emirald</td>
                             <td>50</td>
                             <td>0.34</td>
                             <td> <img src="../static/content/image/diamond.png" /> </td>
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
                <div class="pull-left button-block">
                    <div>
                        Do You want to buy Kings Diamons (09923)?
                    </div>

                    <div class="price">PRICE: <span>25 502.00 $</span></div>
                    <a class="button black" href="/">BUY</a>

                    <div>
                        Do You want to sell Amarillo Starlight (1245)?
                    </div>

                    <div class="btn-group size">
                        <button class="btn">-</button>
                        <input value="4800" type="text" class="btn">4 800</input>
                        <button class="btn">+</button>
                    </div>
                    <a class="button black" href="/">OPEN FOR A SALE</a>
                </div>

                <div class="pull-right clearfix graph-block">
                    <div class="graph-header">
                        <a class="pull-left" href="/">Technical analysis</a>
                        <span class="date pull-right">Mar 26 2015    11:06:19</span>
                    </div>
                    <img src="/content/image/graf.png" alt=""/>
                </div>
                <div class="row">
                    <a class="after-graph pull-right" href="/">Trade History</a>
                </div>


                <div ng-controller="OwnedController in vm">
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
                        <tr ng-repeat="diamond in vm.ownedDiamonds">
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
                        </tr>
                        </thead>
                        <tbody>
                        <tr ng-repeat="diamond in vm.saleDiamonds">
                             <td>{{diamond.id}}</td>
                             <td>{{diamond.name}}</td>
                             <td>$</td>
                             <td></td>
                             <td>$</td>
                             <td>{{diamond.diamondType}}</td>
                             <td>{{diamond.carats}}</td>
                             <td>{{diamond.clarity}}</td>
                             <td>{{diamond.status}}</td>
                        </tr>
                        </tbody>
                    </table>
                </div>
            </div>
         </div>
     </div>

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
</body>
</html>