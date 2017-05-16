
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <meta name="layout" content="main">
    <asset:stylesheet src="style.css"/>
    <title>Dashboard</title>
</head>

<body>
<div class="row" style="padding: 2px 15px ">

    <div class="col-md-5">
        <div class="row" style="padding: 10px 0;border: 1px solid #000  ; border-radius: 7px;border-width: 3px; margin: 0" >
                <g:render template="/templates/userPanel" model="[userDetails: userDetails]"/>
            </div>

        <br>

        <div class="row">
            <div class="panel panel-default panel-primary panelTrendingTopics">
                <div class="panel-heading">
                    Subscriptions
                </div>
                <div style="overflow-y:scroll; height: 400px;">
                    <g:each in="${subscriptions}">
                        <g:render template="/templates/topicPanel" model="[topic: it]"/>
                    </g:each>
                </div>

            </div>
        </div>

        <br/>
        <div class="row" style="overflow-y:scroll; height:auto;max-height: 400px">
            <g:render template="/templates/trendingTopics"/>
        </div>
    </div>

    <div class="col-md-7">
        <div class="row">
            <div class="row">
                <div class="panel panel-default panel-primary panelTrendingTopics">
                    <div class="panel-heading">
                        Inbox
                    </div>
                    <div class="panel-body">
                        <g:each in="${readingItems}" var="readingItem">
                            <g:render template="/templates/postPanel" model="[post: readingItem]"/>
                        </g:each>
                        <g:paginate class="pagination" total="${totalReadingItems}" controller="user" action="index"
                                    max="${searchCO.max}" offset="${searchCO.offset}" />
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
</html>