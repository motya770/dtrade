<!DOCTYPE html>
<html>
<head>
    <script async src="https://www.googletagmanager.com/gtag/js?id=UA-128823061-1"></script>
    <script>
        window.dataLayer = window.dataLayer || [];
        function gtag(){dataLayer.push(arguments);}
        gtag('js', new Date());

        gtag('config', 'UA-128823061-1');
    </script>
    <link rel="stylesheet" href="/theme/app/header.min.css">
    <link rel="stylesheet" href="/theme/app/css/main.min.css">
    <link rel="stylesheet" href="/theme/app/css/custom.css">
    <link rel="stylesheet" href="/theme/app/css/fonts.min.css">
</head>

<body class="page">

<!-- Custom HTML -->
<div class="site">
    <header class="header header--page" style="background-color: #4C4D65;">
        <div class="header__inner">
            <div class="header__logo">
                <a href="/">Exchange 1<br><small>Free stock and crypto trading</small></a>
            </div>
        </div>
    </header>

    <main class="content">
        <div class="def-cab-page">
            <div class="def-cab-page__inner">
                <div class="def-cab-page__content">
                    <h2 class="def-cab-page__heading">Referral link</h2>

                    <div class="lk-main-tabs">
                        <div class="lk-main-tab">
                            <div class="lk-main-tab__table" style="margin-left: 20px;" >
                                Share this link with your friends to get free tokens
                                <div>Link www.exchange1.io/?ref=${account.referral}</div>
                                <div>Referred count ${account.referredCount}</div>

                                    <!-- Go to www.addthis.com/dashboard to customize your tools -->
                                    <div class="addthis_inline_share_toolbox" data-url="www.exchange1.io/?ref=${account.referral}"
                                         data-title="This is unique invitation. Access your free tokens and trade for free stocks and crypto." data-description="Free stock and crypto trading"
                                         data-media="/theme/app/img/logo.png"></div>

                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </main>

    <#include "parts/footer.ftl">
</div>

<script type="text/javascript" src="//s7.addthis.com/js/300/addthis_widget.js#pubid=ra-5bde07ec854a3731"></script>

</body>
</html>



