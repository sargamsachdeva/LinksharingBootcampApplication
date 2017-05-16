<div class="modal fade openModal" id="topicEditModal" tabindex="-1" role="dialog"
     aria-labelledby="topicEditModal">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h4 class="modal-title" id="topicEditModalLabel">Edit Topic Name</h4>
            </div>

            <div class="modal-body">
                <g:form name="topicnameEditForm" class="form-horizontal" controller="topic"
                        action="edit">
                    <g:field type="hidden" value="0" name="id"></g:field>
                    <div class="form-group row">
                        <label for="topicname" class="col-sm-12 form-control-label">TTopic Name</label>
                    </div>

                    <div class="form-group row">
                        <div class="col-sm-12">
                            <g:textField name='topicname' class="col-sm-12" id="topicname"
                                        value="${topicname}"/>
                        </div>
                    </div>

                    <div class="form-group row">
                        <div class="col-sm-4">
                            <button type="button" class="btn btn-default btn-block" data-dismiss="modal">Close</button>
                        </div>

                        <div class="col-sm-8">
                            <g:submitButton type="submit" name="submit" class="btn btn-primary btn-block"
                                            value="Save"/>
                        </div>
                    </div>
                </g:form>
            </div>
        </div>
    </div>
</div>