var gulp = require('gulp');
var uglify = require('gulp-uglify');
var concat = require('gulp-concat');

gulp.task('default', function() {
    return gulp.src('./content/js/**/*.js')
    // Minify the file
        .pipe(uglify())
        // Output
        .pipe(gulp.dest('./dist/js'))
});

var gulp = require('gulp');
var concat = require('gulp-concat');
var ngAnnotate = require('gulp-ng-annotate');
var plumber = require('gulp-plumber');
var debug = require('gulp-debug');

gulp.task('app', function() {
    return gulp.src([
        './bower_components/angular/angular.min.js',
        './bower_components/angular-route/angular-route.min.js',
        './bower_components/angular-resource/angular-resource.min.js',
        './bower_components/angular-animate/angular-animate.min.js',
        './bower_components/angular-aria/angular-aria.min.js',
        './bower_components/angular-messages/angular-messages.min.js',
        './bower_components/angular-material/angular-material.min.js',
        './bower_components/angular-cookies/angular-cookies.js',
        './content/js/app.js',
        './content/js/app/app.config.js',
        './content/js/components/advanced.component.js',
        './content/js/app/account.service.js',
        './content/js/app/config.controller.js',
        './content/js/app/balance.controller.js',
        './content/js/app/diamond.service.js',
        './content/js/app/account.controller.js',
        './content/js/app/available.controller.js',
        './content/js/app/available.service.js',
        './content/js/app/historytradeorder.controller.js',
        './content/js/app/bidder.controller.js',
        './content/js/app/bookorder.controller.js',
        './content/js/app/bookorder.service.js',
        './content/js/app/chart2.controller.js',
        './content/js/app/register.controller.js',
        './content/js/app/tradeorder.account.service.js',
        './content/js/app/tradeorder.account.controller.js',
        './content/js/app/livetradeorder.controller.js',
        './content/js/app/tradeorder.service.js',
        './content/js/app/utils.js',
        './content/js/app/menu.controller.js',
        './content/js/app/alert.service.js',
        './content/js/app/diamond.controller.js',
        './content/js/app/registration.js',
        './content/js/app/balanceactivity.service.js',
        './content/js/app/balanceactivity.controller.js',
        './content/js/app/coinpayment.controller.js',
        './content/js/app/coin.controller.js',
        './bower_components/highcharts/highstock.js',
        './bower_components/jquery/dist/jquery.js',
        './bower_components/jquery.nicescroll/dist/jquery.nicescroll.js',
        './bower_components/angular-nicescroll/angular-nicescroll.js'
    ]).pipe(debug({title: 'unicorn:'}))
        .pipe(plumber())
        .pipe(concat('app.js', {newLine: ';'}))
        .pipe(ngAnnotate({add: true}))
        .pipe(plumber.stop())
        .pipe(uglify()).pipe(gulp.dest('./dist.min/js'));
});

// gulp.task('minify', function() {
//     return gulp.src('./dist.min/*.js')
//         .pipe(uglify())
//         .pipe(gulp.dest('./dist.min/js'))
// });