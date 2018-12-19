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
    function removeImage(diamondId, imageId) {
        $.ajax({
            type: "POST",
            //enctype: 'multipart/form-data',
            url: "/admin/diamond/image-remove?diamond=" + diamondId + "&image="  + imageId,
            // prevent jQuery from automatically transforming the data into a query string
            processData: false,
            contentType: "json",
            cache: false,
            timeout: 1000000,
            success: function(data, textStatus, jqXHR) {
                console.log("SUCCESS : ", data);
                window.location.reload();
            },
            error: function(jqXHR, textStatus, errorThrown) {
                console.log("ERROR : ", jqXHR.responseText);
            }
        });
    }
    
    function produceIdo(diamondId) {
        $.ajax({
            type: "POST",
            url: "/admin/diamond/make-ipo/" + diamondId,
            // prevent jQuery from automatically transforming the data into a query string
            processData: false,
            contentType: "json",
            cache: false,
            timeout: 1000000,
            success: function(data, textStatus, jqXHR) {
                console.log("SUCCESS : ", data);
                window.location.reload();
            },
            error: function(jqXHR, textStatus, errorThrown) {
                console.log("ERROR : ", jqXHR.responseText);
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
            <!--
        <div>
            <input type="hidden" name="diamondStatus" value="${(diamond.diamondStatus)!""}">
        </div>-->
        <div class="form-group">
            <label for="name">Name</label>
            <input type="text" class="form-control" id="name" name="name" placeholder="name" value="${(diamond.name)!""}">
        </div>
        <div class="form-group">
                <label for="ticketName">Ticket Name</label>
                <input type="text" class="form-control" id="ticketName" name="ticketName" placeholder="ticketName" value="${(diamond.ticketName)!""}">
        </div>
        <div class="radio">
            <label>Ticket provider</label><br/>
            <#assign stringTicketProviderValue = (diamond.ticketProvider)!"" />
            <@spring.formRadioButtons "diamond.ticketProvider", ticketProviders, ""/><br><br>
        </div>
        <div class="form-group">
            <label for="name">Price</label>
        ${(diamond.price)!""}
            <input type="number" step="0.01" class="form-control" name="price" id="price" placeholder="price" value="${(diamond.price?replace(",", ""))!""}">
        </div>
        <div class="form-group">
            <label for="name">Total Stock Amount</label>
            <input type="number" step="0.01" class="form-control" name="totalStockAmount" id="totalStockAmount" placeholder="totalStockAmount" value="${(diamond.totalStockAmount?replace(",", ""))!""}">
        </div>
        <div class="radio">
            <#assign stringStatusValue = (diamond.diamondType)!"" />
            <@spring.formRadioButtons "diamond.diamondType", diamondTypes, ""/><br><br>
        </div>

        <div class="form-group">
            <label for="name">Carats</label>
            <input type="number" step="0.01" class="form-control" id="carats" name="carats" placeholder="carats" value="${(diamond.carats)!""}">
        </div>
         ${diamond.color!""}
        <div class="radio">
            <label>Color</label><br/>
            <#assign colorStatusValue = (diamond.color)!"" />
            <@spring.formRadioButtons "diamond.color", colors, ""/><br><br>
        </div>

        <div class="radio">
            <label>Clarity</label><br/>
            <#assign stringStatusValue = (diamond.clarity)!"" />
            <@spring.formRadioButtons "diamond.clarity", clarities, ""/><br><br>
         </div>
         <div class="radio">
                <label>Cut</label><br/>
                <#assign stringStatusValue = (diamond.cut)!"" />
                <@spring.formRadioButtons "diamond.cut", cuts, ""/><br><br>
         </div>

            <div class="radio">
                <label>Status</label><br/>
            <#assign stringStatusValue = (diamond.diamondStatus)!"" />
            <@spring.formRadioButtons "diamond.diamondStatus", statuses, ""/><br><br>
            </div>

            <div class="form-group">
                <label for="hideTotalStockAmount">Hide Total Stock Amount</label>

                <#if diamond.hideTotalStockAmount == true>
                    <input type="checkbox" class="form-control" name="hideTotalStockAmount" id="hideTotalStockAmount" placeholder="hideTotalStockAmount"  checked>
                <#else>
                    <input type="checkbox" class="form-control" name="hideTotalStockAmount" id="hideTotalStockAmount" placeholder="hideTotalStockAmount">
                </#if>
            </div>

         <div class="radio">
                <label>Currency</label><br/>
            <#assign stringStatusValue = (diamond.currency)!"" />
            <@spring.formRadioButtons "diamond.currency", currencies, ""/><br><br>
         </div>

            <div class="radio">
                <label>Base Currency</label><br/>
            <#assign stringStatusValue = (diamond.getBase)!"" />
            <@spring.formRadioButtons "diamond.baseCurrency", currencies, ""/><br><br>
            </div>

            <div class="form-group">
                <label for="roboHighEnd">Robo High End</label>
                <input type="number" step="any" class="form-control" id="roboHighEnd" name="roboHighEnd" placeholder="roboHighEnd" value="${(diamond.roboHighEnd)!""}">
            </div>

            <div class="form-group">
                <label for="roboLowEnd">Robo Low End</label>
                <input type="number" step="any" class="form-control" id="roboLowEnd" name="roboLowEnd" placeholder="roboLowEnd" value="${(diamond.roboLowEnd)!""}">
            </div>

            <div class="form-group">
                <label for="roboLowEnd">Robo Max Amount</label>
                <#if diamond.roboMaxAmount??>
                    <input type="" step="any" class="form-control" id="roboMaxAmount" name="roboMaxAmount" placeholder="roboMaxAmount" value="#{(diamond.roboMaxAmount)}">
                <#else>
                    <input type="" step="any" class="form-control" id="roboMaxAmount" name="roboMaxAmount" placeholder="roboMaxAmount" value="">
                </#if>
            </div>

        <button type="submit" class="btn btn-default">Submit</button>
    </form>

    <h3>Produce Initial Diamond Offering</h3>
    <input type="button" onclick="produceIdo(${diamond.id!""});preventDefault();" value="Produce IDO"></input>
    <br/>
    <h3>Images already uploaded</h3>
    <#if diamond.images??>
        <#list diamond.images as image>
            <div>
                <img style="height: 100px; weight: 100px;" src="/image/diamond-image?image=${image.id!""}">
                <input type="button" onclick="removeImage(${diamond.id!""}, ${image.id!""}); preventDefault();"/>
            </div>
        </#list>
    </#if>

    <form method="POST" enctype="multipart/form-data" id="fileUploadForm">
        Description: <br/>
        <input type="text" name="description" style="width:350px;"/>
        <br/><br/>
        Image to upload (1): <input type="file" name="file"/><br />
        <input type="hidden" name="diamond" value="${(diamond.id)!""}"/><br />
        <input type="submit" value="Submit" id="submitButton"/>
    </form>
    <h4>Upload Results:</h4>
    <div style="border:1px solid #ccc;padding: 5px;">
        <span id="result"></span>
    </div>

</div>

<#include "/admin/footer.ftl">


