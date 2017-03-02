<html ng-app="diamondApp">

<head>
    <script src="/bower_components/jquery/dist/jquery.min.js" type="text/javascript"></script>
    <script src="/bower_components/bootstrap/dist/js/bootstrap.min.js" type="text/javascript"></script>
    <script src="/bower_components/angular/angular.min.js" type="text/javascript"></script>
    <script src="/bower_components/angular-resource/angular-resource.min.js" type="text/javascript"></script>

    <script src="/content/js/customer.js"></script>

    <link rel="stylesheet" type="text/css" href="/bower_components/bootstrap/dist/css/bootstrap.min.css"/>
</head>

<body>

<h4>Customer</h4>
<div ng-controller="CustomerController as vm">

    <div><h3>Diamond Activity</h3></div>
    <div ng-repeat="diamondActivity in vm.diamondActivities">
        <span>{{diamondActivity.id}}</span>
        <span>{{diamondActivity.seller.mail}}</span>
        <span>{{diamondActivity.buyer.mail}}</span>
        <span>{{diamondActivity.diamond.id}}</span>
        <span>{{diamondActivity.diamond.name}}</span>
        <span>{{diamondActivity.price}}</span>
        <span>{{diamondActivity.date}}</span>
        <span>{{diamondActivity.diamondActivityType}}</span>
    </div>

    <div style="margin-top: 100px;"><h3>Balance Activity</h3></div>
    <div ng-repeat="balanceActivity in vm.balanceActivities">
        <span>{{balanceActivity.id}}</span>
        <span>{{balanceActivity.account.mail}}</span>
        <span>{{balanceActivity.balanceActivityType}}</span>
        <span>{{balanceActivity.amount}}</span>
        <span>{{balanceActivity.createDate}}</span>
    </div>
</div>

</body>
</html>