<!DOCTYPE html>
<html ng-app="diamondApp">
<head>
    <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
    <#include "parts/head.ftl">
    <script src="/content/js/registration.js" ></script>
</head>

<body class="page">

<!-- Custom HTML -->
<div class="site">
    <#include "parts/caption.ftl">
    <#include "parts/menu.ftl">
    <main class="content">

        <div class="def-cab-page">
            <div class="def-cab-page__inner">
                <div class="def-cab-page__content">
                    <h2 class="def-cab-page__heading">Вход</h2>
                    <div class="table-container">
                        <div style="width: 400px; margin-left: 8px; margin-bottom: 10px;">
                            <form action="/login" method="post">
                                <md-input-container class="md-block">
                                    <!-- Use floating placeholder instead of label -->
                                    <md-icon class="material-icons md-light md-48"> email </md-icon>
                                    <md-icon md-svg-src="img/icons/ic_email_24px.svg" class="username"></md-icon>
                                    <input type="email" name="username" placeholder="Email (required)" ng-required="true">
                                </md-input-container>

                                <md-input-container md-no-float="" class="md-block">
                                    <label for="password">Password</label>
                                    <input type="password" name="password"
                                           ng-minlength="8" required />
                                    <span class="help-block"
                                          ng-show="newPasswordForm.newPassword.$dirty && newPasswordForm.newPassword.$invalid">
                                            Please enter a new password, it must be at least 6 characters long.
                                    </span>
                                </md-input-container>

                                <!--
                                <input type="text" name="username" /><br/>
                                <input type="password" name="password"><br/>
                                -->

                                <md-button type="submit" class="md-raised md-primary">Login</md-button>
                            </form>
                        </div>

                        <div style="width: 400px;">
                    <h2 class="def-cab-page__heading">Регистрация</h2>
                        <div ng-controller="RegistrationController as vm" style="width:500px;" layout="column" layout-padding="" ng-cloak="" class="material-input", class="input-demo-Errors, ng-app="MyApp">
                         <br>
                        <md-input-container md-no-float="" class="md-block">

                            <md-icon class="material-icons md-light md-48"> phone </md-icon>

                            <md-icon md-svg-src="img/icons/ic_phone_24px.svg"></md-icon>
                            <input ng-model="account.phone" type="text" placeholder="Phone Number(Full format)">
                        </md-input-container>
                        <md-input-container class="md-block">
                            <!-- Use floating placeholder instead of label -->
                            <md-icon class="material-icons md-light md-48"> email </md-icon>
                            <md-icon md-svg-src="img/icons/ic_email_24px.svg" class="email"></md-icon>
                            <input ng-model="account.email" type="email" placeholder="Email (required)" ng-required="true">
                        </md-input-container>
                        <form name="newPasswordForm" role="form" ng-submit="newPasswordForm.$valid && ok()" novalidate>


                        <md-input-container md-no-float="" class="md-block">
                            <label for="newPassword">New Password</label>
                            <input type="password" name="newPassword" ng-model="account.password.new"
                                   ng-minlength="6" required />
                            <span class="help-block"
                                  ng-show="newPasswordForm.newPassword.$dirty && newPasswordForm.newPassword.$invalid">
                               Please enter a new password, it must be at least 6 characters long.
                        </span>
                        </md-input-container>
                        <md-input-container md-no-float="" class="md-block">
                            <label for="newPasswordConfirm">Confirm New Password</label>
                            <input type="password" name="newPasswordConfirm"
                                   ng-model="account.password.confirm" ng-minlength="6"
                                   value-matches="account.password.new" required />
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
                        <div>
                            <md-button class="md-raised md-primary" type="submit" ng-click="createAccount(account);">Register</md-button>
                        </div>
                        </md-content>
                    </div>

                    </div>
                </div>
            </div>
        </div>
    </main>

    <#include "parts/footer.ftl">
</div>

<#include "parts/bootom-scripts.ftl">

</body>
</html>



