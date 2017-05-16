<div class="modal fade openModal" id="sharelinkModal" tabindex="-1" role="dialog" aria-labelledby="sharelinkModal">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h4 class="modal-title" id="sharelinkModalLabel">Link</h4>
            </div>

            <div class="modal-body">
                <g:form name="linkResourceSave" class="form-horizontal" controller="linkResource" action="save">
                    <div class="form-group row">
                        <label for="url" class="col-sm-4 form-control-label">Link *</label>

                        <div class="col-sm-8">
                            <g:field type="url" class="form-control col-sm-8" name="url"/>
                        </div>
                    </div>

                    <div class="form-horizontal row">
                        <label for="description" class="col-sm-4 form-control-label">Description *</label>

                        <div class="col-sm-8">
                            <g:textArea name="description" class="col-sm-8 form-control"/>
                        </div>
                    </div>

                    <div class="form-group row">
                        <label  class="col-sm-4 form-control-label">Topic *</label>

                        <div class="col-sm-8">
                            <div class="dropdown">
                                <ls:showSubscribedTopics/>
                            </div>
                        </div>
                    </div>

                    <div class="form-group row">
                        <div class="col-sm-4 form-control-label">
                            <button type="button" class="btn btn-default btn-block" data-dismiss="modal">Close</button>
                        </div>

                        <div class="col-sm-4 col-md-offset-1">
                            <button  name="createTopicBtn" id="createTopicBtn" value=""
                                     class="btn btn-block btn-primary">Share</button>
                        </div>
                    </div>
                </g:form>
            </div>
        </div>
    </div>
</div>