<div class="row">
    <div class="col-md-12">
        <div class="panel panel-primary">
            <div class="panel-heading">
                <h3 class="panel-title">Login</h3>
            </div>
            <div class="panel-body">
                <g:form class="form-horizontal" mapping="login">
                    <div class="form-group">
                        <label for="username_login" class="col-sm-2 control-label">Username</label>
                        <div class="col-sm-10">
                            <g:field type="text" class="form-control" name="username" id="username_login" />
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="password_login" class="col-sm-2 control-label">Password</label>
                        <div class="col-sm-10">
                            <g:field type="password" class="form-control" name="password" id="password_login" />
                        </div>
                    </div>
                    <div class="form-group">
                        <div class="col-sm-offset-2 col-sm-10">
                            <a href="${createLink(controller: 'login', action: 'forgotPassword')}">Forgot Password?</a>
                            <button type="submit" class="btn btn-default">update</button>
                        </div>
                    </div>
                </g:form>
            </div>
        </div>
    </div>
</div>