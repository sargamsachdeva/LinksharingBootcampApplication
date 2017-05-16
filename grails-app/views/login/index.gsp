
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <meta name="layout" content="main">
    <asset:stylesheet src="style.css"/>

    <title></title>
</head>

<body>
<div class="row" style="padding: 2% 5%">
    <div class="col-md-6">
        <div class="row">
            <div class="panel panel-default panel-primary panelTrendingTopics">
                <div class="panel-heading">
                    Recent Shares
                </div>

                <div style="height: 400px;overflow-y: auto">
                    <ls:recentShares/>
                </div>
            </div>
        </div>

        <div class="row">
            <div class="panel panel-default panel-primary panelTrendingTopics">
                <div class="panel-heading">
                    Top Posts
                </div>

                <div style="    height: auto;overflow-y: auto">
                   <ls:getTopPostss/>
                </div>

            </div>
        </div>
    </div>

    <div class="col-md-5 col-md-offset-1">
        <div class="row">
            <g:render template="/login/login"/>
        </div>

        <div class="row">
            <g:render template="/login/register"/>
        </div>
    </div>
</div>

<g:javascript>


</g:javascript>
</body>
</html>


