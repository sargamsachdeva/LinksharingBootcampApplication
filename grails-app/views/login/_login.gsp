<div class="panel panel-default panel-primary panelTrendingTopics">
    <div class="panel-heading">
        Login
    </div>

    <div class="panel-body">
        <g:form name="loginForm" id="loginForm" class="form-horizontal" controller="login" action="login">
            <div class="form-group row">
                <label for="loginUserName" class="col-sm-4 form-control-label">User Name *</label>

                <div class="col-sm-8">
                    <g:textField name="loginUserName" class="form-control" id="loginUserName"/>
                </div>
            </div>

            <div class="form-group row">
                <label for="loginPassword" class="col-sm-4 form-control-label">Password *</label>

                <div class="col-sm-8">
                    <g:field name="loginPassword" type="password" class="form-control" id="loginPassword"/>
                </div>
            </div>

            <div class="form-group row">
                <div class="col-sm-6 form-control-label">
                    <a name="forgotPasswordLink" class="btn btn-default" href="${createLink(controller: 'login',action: 'forgotPassword')}">Forgot
                                Password</a>
                </div>

                <div class="col-sm-offset-3 col-sm-6 pull-right">
                    <g:submitButton name="loginBtn" value="Login" type="submit" formaction="/login/login"
                                    class="btn btn-primary btn-block"/>
                </div>
            </div>
        </g:form>
    </div>
</div>
