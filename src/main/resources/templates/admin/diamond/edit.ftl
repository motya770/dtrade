<#import "/spring.ftl" as spring/>

<#include "/admin/header.ftl">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
<script>
    $(document).ready(function() {

        $("#submitButton").click(function(event) {

            // Stop default form Submit.
            event.preventDefault();

            // Call Ajax Submit.

            ajaxSubmitForm();

        });

    });

    function ajaxSubmitForm() {

        // Get form
        var form = $('#fileUploadForm')[0];

        var data = new FormData(form);

        $("#submitButton").prop("disabled", true);

        $.ajax({
            type: "POST",
            enctype: 'multipart/form-data',
            url: "/admin/diamond/image-upload/",
            data: data,

            // prevent jQuery from automatically transforming the data into a query string
            processData: false,
            contentType: false,
            cache: false,
            timeout: 1000000,
            success: function(data, textStatus, jqXHR) {

                $("#result").html(data);
                console.log("SUCCESS : ", data);
                $("#submitButton").prop("disabled", false);
                $('#fileUploadForm')[0].reset();
            },
            error: function(jqXHR, textStatus, errorThrown) {
                $("#result").html(jqXHR.responseText);
                console.log("ERROR : ", jqXHR.responseText);
                $("#submitButton").prop("disabled", false);
            }
        });
    }
</script>


<h1>Create Diamond</h1>
<div style="width: 500px;">
    <form method="post" action="/admin/diamond/save">
    <@spring.bind "diamond"/>

        <div>
            <input type="hidden" name="id" value="${(diamond.id)!""}">
        </div>

        <div>
            <input type="hidden" name="account.id" value="${(diamond.account.id)!""}"/>
        <div>

        <div>
            <input type="hidden" name="diamondStatus" value="${(diamond.diamondStatus)!""}">
        </div>

        <div class="form-group">
            <label for="name">Name</label>
            <input type="text" class="form-control" id="name" name="name" placeholder="name" value="${(diamond.name)!""}">
        </div>
        <div class="form-group">
            <label for="name">Price</label>
            <input type="number" class="form-control" name="price" id="price" placeholder="price" value="${(diamond.price)!""}">
        </div>

        <div class="form-group">
            <label for="name">Total Stock Amount</label>
            <input type="number" class="form-control" name="totalStockAmount" id="totalStockAmount" placeholder="totalStockAmount" value="${(diamond.totalStockAmount)!""}">
        </div>

        <div class="radio">

        <#assign stringStatusValue = (diamond.diamondType)!"" />
        <@spring.formRadioButtons "diamond.diamondType", diamondTypes, ""/><br><br>

        </div>

        <div class="form-group">
            <label for="name">Carats</label>
            <input type="number" class="form-control" id="carats" name="carats" placeholder="carats" value="${(diamond.carats)!""}">
        </div>

        <div class="form-group">
            <label for="clarity">Clarity</label>
            <input type="number" class="form-control" id="clarity" name="clarity" placeholder="clarity" value="${(diamond.clarity)!""}">
        </div>


        <button type="submit" class="btn btn-default">Submit</button>
    </form>


    <h1>Spring Boot File Upload with jQuery Ajax</h1>
    <form method="POST" enctype="multipart/form-data" id="fileUploadForm">
        Description: <br/>
        <input type="text" name="description" style="width:350px;"/>
        <br/><br/>
        Image to upload (1): <input type="file" name="file"/><br />
        Image to upload (1): <input type="text" name="diamond" value="${(diamond.id)!""}"/><br />
        <input type="submit" value="Submit" id="submitButton"/>
    </form>
    <h2>Upload Results:</h2>
    <div style="border:1px solid #ccc;padding: 5px;">
        <span id="result"></span>
    </div>

</div>

<#include "/admin/footer.ftl">


