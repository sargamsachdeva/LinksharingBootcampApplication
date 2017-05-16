<g:each in="${topicList}">
    <g:render template="/templates/topicPanel" model="[topic:it]"/>
</g:each>