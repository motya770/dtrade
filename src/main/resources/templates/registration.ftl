<html ng-app="diamondApp">
<body>
<link rel="stylesheet" href= "/content/css/material-input.css" />
<link rel="stylesheet" href= "/bower_components/angular-material/angular-material.min.css"/>



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

<h1>ttt</h1>
<div ng-controller="DemoCtrl" layout="column" layout-padding="" ng-cloak="" class="inputdemoIcons" ng-app="MyApp">

  <br>
  <md-content class="md-no-momentum">
    <md-input-container class="md-icon-float md-block">
      <!-- Use floating label instead of placeholder -->
      <label>Name</label>
      <md-icon md-svg-src="img/icons/ic_person_24px.svg" class="name"></md-icon>
      <input ng-model="user.name" type="text">
    </md-input-container>

    <md-input-container md-no-float="" class="md-block">
      <md-icon md-svg-src="img/icons/ic_phone_24px.svg"></md-icon>
      <input ng-model="user.phone" type="text" placeholder="Phone Number">
    </md-input-container>

    <md-input-container class="md-block">
      <!-- Use floating placeholder instead of label -->
      <md-icon md-svg-src="img/icons/ic_email_24px.svg" class="email"></md-icon>
      <input ng-model="user.email" type="email" placeholder="Email (required)" ng-required="true">
    </md-input-container>

    <md-input-container md-no-float="" class="md-block">
      <input ng-model="user.address" type="text" placeholder="Address">
      <md-icon md-svg-src="img/icons/ic_place_24px.svg" style="display:inline-block;"></md-icon>
    </md-input-container>

    <md-input-container class="md-icon-float md-icon-right md-block">
      <label>Donation Amount</label>
      <md-icon md-svg-src="img/icons/ic_card_giftcard_24px.svg"></md-icon>
      <input ng-model="user.donation" type="number" step="0.01">
      <md-icon md-svg-src="img/icons/ic_euro_24px.svg"></md-icon>
    </md-input-container>

  </md-content>

</div>

</body>
</html>