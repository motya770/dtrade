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

        <table class="table" class="table-responsive">
            <tr>
                <th>Activity Id</th>
                <th>Seller mail</th>
                <th>Buyer mail</th>
                <th>Asset Id</th>
                <th>Asset Name</th>
                <th>Price</th>
                <th>Date</th>
                <th>Activity Type</th>
            </tr>

            <tbody>

                    <tr ng-repeat="diamondActivity in vm.diamondActivities">
                            <td>{{diamondActivity.id}}</td>
                            <td>{{diamondActivity.seller.mail}}</td>
                            <td>{{diamondActivity.buyer.mail}}</td>
                            <td>{{diamondActivity.diamond.id}}</td>
                            <td>{{diamondActivity.diamond.name}}</td>
                            <td>{{diamondActivity.price}}</td>
                            <td>{{diamondActivity.date | date}}</td>
                            <td>{{diamondActivity.diamondActivityType}}</td>
                    </tr>

            </tbody>
        </table>


        <div style="margin-top: 100px;"><h3>Balance Activity</h3></div>
        <table class="table" class="table-responsive">
            <tr>
                <th>Activity Id</th>
                <th>Balance Activity Type</th>
                <th>Amount</th>
                <th>createDate</th>
            </tr>
            <tbody>
            <tr ng-repeat="balanceActivity in vm.balanceActivities">
                <td>{{balanceActivity.id}}</td>
                <td>{{balanceActivity.balanceActivityType}}</td>
                <td>{{balanceActivity.amount}}</td>
                <td>{{balanceActivity.createDate |  date : format : timezone}}</td>
            </tr>
            </tbody>
        </div>


</body>
</html>