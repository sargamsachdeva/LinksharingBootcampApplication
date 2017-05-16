<div class="panel panel-default panel-primary panelTrendingTopics">
    <div class="panel-heading">
        Trending Topics
    </div>
    <g:each in="${trendingTopics}" var="trendingTopic">
        <g:render template="/templates/topicPanel" model="[topic: trendingTopic]"/>
    </g:each>
</div>