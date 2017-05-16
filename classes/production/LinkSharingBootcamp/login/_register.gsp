<div class="panel panel-default panel-primary panelTrendingTopics">
    <div class="panel-heading">
        Register
    </div>

    <div class="panel-body">
        <g:uploadForm name="registration" controller="login" action="registration" class="form-horizontal">
            <div class="form-group row">
                <label for="firstName" class="col-sm-4 form-control-label">First Name *</label>

                <div class="col-sm-8">
                    <g:textField name="firstName" type="text" class="form-control" id="firstName"
                                 value="${userCo?.firstName}"/>
                </div>

                <div class="alert-danger">
                    <g:fieldError field="firstName" bean="${userCo}"/>
                </div>
            </div>

            <div class="form-group row">
                <label for="lastName" class="col-sm-4 form-control-label">Last Name *</label>

                <div class="col-sm-8">
                    <g:textField name="lastName" type="text" class="form-control" id="lastName"
                                 value="${userCo?.lastName}"/>
                </div>

                <div class="alert-danger"><g:fieldError field="lastName" bean="${userCo}"/></div>
            </div>

            <div class="form-group row">
                <label for="email" class="col-sm-4 form-control-label">Email *</label>

                <div class="col-sm-8">
                    <g:field name="email" type="email" class="form-control" id="email" value="${userCo?.email}"/>
                </div>

                <div class="alert-danger"><g:fieldError field="email" bean="${userCo}"/></div>
            </div>

            <div class="form-group row">
                <label for="userName" class="col-sm-4 form-control-label">User Name *</label>

                <div class="col-sm-8">
                    <g:textField name="userName" type="text" class="form-control" id="userName"
                                 value="${userCo?.userName}"/>
                </div>

                <div class="alert-danger"><g:fieldError field="userName" bean="${userCo}"/></div>
            </div>

            <div class="form-group row">
                <label for="password" class="col-sm-4 form-control-label">Password *</label>

                <div class="col-sm-8">
                    <g:field name="password" type="password" class="form-control" id="password"
                             value="${userCo?.password}"/>
                </div>
            </div>

            <div class="form-group row">
                <label for="confirmPassword" class="col-sm-4 form-control-label">Confirm Password *</label>

                <div class="col-sm-8">
                    <g:field name="confirmPassword" type="password" class="form-control" id="confirmPassword"/>
                </div>
            </div>

            <div class="form-group row">
                <label for="photo" class="col-sm-4 form-control-label">Photo</label>

                <div class="col-sm-8">
                    <input type="file" name="file" accept=".jpeg,.jpg,.png,.gif" id="photo">
                    <span class="file-custom"></span>
                </div>
            </div>

            <div class="form-group row">
                <div class="col-sm-offset-1 col-sm-6 pull-right">
                    <g:submitButton type="submit" name="submit" value="Register"
                                    class="btn btn-primary btn-block"/>
                </div>
            </div>
        </g:uploadForm>
    </div>
</div>