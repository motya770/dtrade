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

gulp.task('app', function() {
    return gulp.src(['./content/js/app.js', './content/js/**/*.js'])
        .pipe(plumber())
        .pipe(concat('app.js', {newLine: ';'}))
        .pipe(ngAnnotate({add: true}))
        .pipe(plumber.stop())
        .pipe(gulp.dest('./dist.min/'));
});
