<div class="modal fade" id="sendInvite" tabindex="-1" role="dialog" aria-labelledby="sendInvite">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                <h4 class="modal-title" id="myModalLabel">Send Invitation</h4>
            </div>
            <div class="modal-body">
                <g:form class="form-horizontal" controller="user" action="sendInvite">
                    <div class="form-group">
                        <label for="email" class="col-sm-2 control-label">Email</label>
                        <div class="col-sm-10">
                            <g:field type="email" class="form-control" name="email" />
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="topic" class="col-sm-2 control-label">Topic</label>
                        <div class="col-sm-10">
                            <g:select class="btn dropdown-toggle col-sm-8 form-control" name="topic" from="${session.user?.getSubscribedTopics()}" optionKey="id"/>
                        </div>
                    </div>
                    <div class="form-group">
                        <div class="col-sm-offset-2 col-sm-10">
                            <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
                            <button type="submit" class="btn btn-default">Invite</button>
                        </div>
                    </div>
                </g:form>
            </div>
        </div>
    </div>
</div>
