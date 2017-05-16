<div class="modal fade openModal" id="sharedocModal" tabindex="-1" role="dialog" aria-labelledby="sharedocModal">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h4 class="modal-title" id="sharedocModalLabel">Share Document</h4>
            </div>

            <div class="modal-body">
                <g:uploadForm name="upload" class="form-horizontal" controller="documentResource" action="upload">
                    <div class="form-group row">
                        <label for="doc" class="col-sm-4 form-control-label">Document *</label>

                        <div class="col-sm-8">
                            <input type="file" class="form-control col-sm-8" id="doc" name="file"
                                   accept=".pdf" required>
                        </div>
                    </div>

                    <div class="form-horizontal row">
                        <label for="description" class="col-sm-4 form-control-label">Description *</label>

                        <div class="col-sm-8">
                            <g:textArea name="description" class="form-control col-sm-8" id="description"/>
                        </div>
                    </div>

                    <div class="form-group row">
                        <label class="col-sm-4 form-control-label">Topic *</label>

                        <div class="col-sm-8">
                            <div class="dropdown">
                                <div class="dropdown">
                                    <ls:showSubscribedTopics/>
                                </div>
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
                </g:uploadForm>
            </div>
        </div>
    </div>
</div>