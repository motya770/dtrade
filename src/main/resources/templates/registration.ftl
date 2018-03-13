<html ng-app="diamondApp">
<body>
<link rel="stylesheet" href= "/content/css/material-input.css" />
<link rel="stylesheet" href= "/bower_components/angular-material/angular-material.min.css"/>
<link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">



 <!-- Angular Material requires Angular.js Libraries -->
 <!--
  <script src="https://ajax.googleapis.com/ajax/libs/angularjs/1.5.5/angular.min.js"></script>
  <script src="https://ajax.googleapis.com/ajax/libs/angularjs/1.5.5/angular-animate.min.js"></script>
  <script src="https://ajax.googleapis.com/ajax/libs/angularjs/1.5.5/angular-aria.min.js"></script>
  <script src="https://ajax.googleapis.com/ajax/libs/angularjs/1.5.5/angular-messages.min.js"></script>
  -->

<script src="/bower_components/angular/angular.js"></script>
<script src="/bower_components/angular-animate/angular-animate.min.js"></script>
<script src="/bower_components/angular-aria/angular-aria.min.js"></script>
<script src="/bower_components/angular-messages/angular-messages.min.js"></script>
<script src="/bower_components/angular-material/angular-material.min.js"></script>
<script src="/content/js/registration.js" ></script>


<!--
<script src="/resources/content/js/plat/registration.js" ></script>
-->

<!--
<script src="//www.google.com/recaptcha/api.js?onload=CaptchaCallback&render=explicit" async defer></script>
<script type="text/javascript">
    var CaptchaCallback = function () {
        grecaptcha.render('DemoRecaptcha', {'sitekey': '6LeIRwUTAAAAAIYGH56PGrgIZj-nyJ8As2_BiiwM'});
        grecaptcha.render('RealRecaptcha', {'sitekey': '6LeIRwUTAAAAAIYGH56PGrgIZj-nyJ8As2_BiiwM'});
    };
</script>
-->

<!--
<h1>ttt</h1>
-->
<div ng-controller="DemoCtrl" layout="column" layout-padding="" ng-cloak="" class="material-input", class="input-demo-Errors, ng-app="MyApp">

  <br>
    <md-input-container md-no-float="" class="md-block">
    <!--
    <md-icon md-svg-src="bower_components/material-design-icons/editor/svg/production/ic_merge_type_24px.svg"></md-icon>
    -->

    <!--
    <i class="material-icons">phone</i>
     -->

     <!--
         <md-icon> face </md-icon>
         <md-icon md-font-set="material-icons"> face </md-icon>
         <md-icon>  </md-icon>
          -->
    <md-icon class="material-icons md-light md-48"> phone </md-icon>

    <md-icon md-svg-src="img/icons/ic_phone_24px.svg"></md-icon>
      <input ng-model="user.phone" type="text" placeholder="Phone Number(Full format)">
    </md-input-container>

    <md-input-container md-no-float="" class="md-block">
    <md-icon class="material-icons md-light md-48"> place </md-icon>
     <md-icon md-svg-src="img/icons/ic_email_24px.svg" class="email"></md-icon>
          <input ng-model="user.address" type="text" placeholder="Address">
          <md-icon md-svg-src="img/icons/ic_place_24px.svg" style="display:inline-block;"></md-icon>
     </md-input-container>

    <md-input-container class="md-block">
        <!-- Use floating placeholder instead of label -->
        <md-icon class="material-icons md-light md-48"> email </md-icon>
        <md-icon md-svg-src="img/icons/ic_email_24px.svg" class="email"></md-icon>
         <input ng-model="user.email" type="email" placeholder="Email (required)" ng-required="true">
    </md-input-container>

 <!--
    <md-input-container md-no-float="" class="md-block">
     <md-icon md-svg-src="img/icons/ic_email_24px.svg" class="email"></md-icon>
          <input ng-model="user.password" type="password" placeholder="Password">
        </md-input-container>

        <md-input-container md-no-float="" class="md-block">
         <md-icon md-svg-src="img/icons/ic_email_24px.svg" class="email"></md-icon>
                 <input ng-model="user.password" type="password" placeholder="Repeat password">
                </md-input-container>
                -->

               <form name="newPasswordForm" role="form" ng-submit="newPasswordForm.$valid && ok()" novalidate>
                <md-input-container md-no-float="" class="md-block">
                 <label for="newPassword">New Password</label>
                 <input type="password" name="newPassword" ng-model="password.new"
                 ng-minlength="6" required />
                  <span class="help-block"
                   ng-show="newPasswordForm.newPassword.$dirty && newPasswordForm.newPassword.$invalid">
                   Please enter a new password, it must be at least 6 characters long.
                   </span>
                    </md-input-container>
                     <md-input-container md-no-float="" class="md-block">
                     <label for="newPasswordConfirm">Confirm New Password</label>
                     <input type="password" name="newPasswordConfirm"
                     ng-model="password.confirm" ng-minlength="6"
                     value-matches="password.new" required />
                     <span class="help-block"
                     ng-show="newPasswordForm.newPasswordConfirm.$dirty && newPasswordForm.newPasswordConfirm.$invalid">
                     Please enter the same password again to confirm.
                     </span>
                     </md-input-container>
                     </form>

     <md-input-container class="md-block">
            <md-checkbox name="tos" ng-model="project.tos" required="">
              I accept the terms of service.
            </md-checkbox>
            <div ng-messages="projectForm.tos.$error" multiple="" md-auto-hide="false">
              <div ng-message="required">
                You must accept the terms of service before you can proceed.
              </div>
            </div>
          </md-input-container>
          <md-input-container class="md-block">
            <md-switch class="md-primary" name="special" ng-model="project.special" required="">
              Enable special options.
            </md-switch>
            <div ng-messages="projectForm.special.$error" multiple="">
              <div ng-message="required">
                You must enable all special options before you can proceed.
              </div>
            </div>
          </md-input-container>
          <div>
            <md-button type="submit">Submit</md-button>
          </div>



  </md-content>

</div>

</body>
</html>