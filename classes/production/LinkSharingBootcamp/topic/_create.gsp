<%@ page import="enums.Visibility" %>
<div class="modal fade" id="createtopicModal" tabindex="-1" role="dialog" aria-labelledby="createtopicModal">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h4 class="modal-title" id="createtopicModalLabel">Create Topic</h4>
            </div>

            <div class="modal-body">
                <g:form class="form-horizontal" name="topicCreate" controller="topic" action="save">
                    <div class="form-group row">
                        <label for="topicName" class="col-sm-4 form-control-label">Name *</label>

                        <div class="col-sm-8">
                            <g:textField name="topicName" placeholder="Topic Name"
                                         class="form-control col-sm-8"/>
                            <g:hiddenField name="createdBy" value="${session.user}"/>
                        </div>
                    </div>

                    <div class="form-group row">
                        <label for="visibility" class="col-sm-4 form-control-label">Visibility *</label>

                        <div class="col-sm-8">
                            <div class="dropdown">
                                <g:render name="visibility" class="btn dropdown-toggle"
                                          template="/templates/visibilitySelect"/>
                            </div>
                        </div>
                    </div>

                    <div class="form-group row">
                        <div class="col-sm-4 form-control-label">
                            <button type="button" class="btn btn-default btn-block" data-dismiss="modal">Close</button>
                        </div>

                        <div class="col-sm-4 col-md-offset-1">
                            <button name="createTopicBtn" id="createTopicBtn" value=""
                                    class="btn btn-block btn-primary">Create Topic</button>
                        </div>

                    </div>
                </g:form>
            </div>
        </div>
    </div>
</div>