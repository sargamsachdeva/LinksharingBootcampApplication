<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <meta name="layout" content="main">
    <title>Search Page</title>
</head>

<body>
<div>
    <div class="row">
        <div class="col-xs-4">
            <div class="row">
                <div style="overflow-y:scroll; height: 400px;">
                    <g:render template="/templates/trendingTopics" model="[topic: topic]"/>
                </div>
            </div>
<br>
            <div class="row">
                <div class="panel panel-default panel-primary panelTrendingTopics">
                    <div class="panel-heading panelHeaders">
                        Top Posts
                    </div>

                    <div class="panel-body" style="overflow-y:scroll; height: 400px;">
                        <g:each in="${topPosts}" var="post">
                            <g:render template="/templates/postPanel" model="[post: post]"/>
                        </g:each>
                    </div>
                </div>

            </div>
        </div>

        <div class="col-xs-7 col-xs-push-1">
            <div class="panel panel-default panel-primary panelTrendingTopics">
                <div class="panel-heading panelHeaders">
                    Search Result
                </div>

                <div class="panel-body" style="overflow-y:scroll; height: 800px;">
                    <g:if test="${posts}">
                        <g:each in="${posts}" var="post">
                            <g:render template="/templates/postPanel" model="[post: post]"/>
                        </g:each>
                    </g:if>
                    <g:else>
                        No results to be shown.
                    </g:else>
                </div>
            </div>

        </div>
    </div>
</div>
</body>
</html>