<div class="modal fade openModal" id="resourceEditModal" tabindex="-1" role="dialog"
     aria-labelledby="resourceEditModal">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h4 class="modal-title" id="resourceEditModalLabel">Edit Resource Description</h4>
            </div>

            <div class="modal-body">
                <g:form name="resourceDescriptionEditForm" class="form-horizontal" controller="resource"
                        action="save" params="[id: params.id]">
                    <div class="form-group row">
                        <label for="description" class="col-sm-12 form-control-label">Description</label>
                    </div>

                    <div class="form-group row">
                        <div class="col-sm-12">
                            <g:textArea name="description" class="col-sm-12" id="description"
                                        value="${description}"/>
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