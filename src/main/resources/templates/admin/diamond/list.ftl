<html>
<head>

    <script src="/bower_components/jquery/dist/jquery.min.js" type="text/javascript"></script>
    <script src="/bower_components/bootstrap/dist/js/bootstrap.min.js" type="text/javascript"></script>

    <link rel="stylesheet" type="text/css" href="/bower_components/bootstrap/dist/css/bootstrap.min.css"/>

</head>
<body>

    <h1>list6</h1>
    <div class="table-responsive">
        <table class="table">
            <tbody>

                <#list diamonds as diamond>
                    <tr>
                        <td>${diamond}</td>
                        <td>${diamond}</td>
                        <td>${diamond}</td>
                    </tr>
                </#list>
            </tbody>
        </table>
    </div>
</body>
</html>

