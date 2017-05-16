<!doctype html>
<html>
<head>
    <title>Welcome to Grails</title>

    <asset:link rel="icon" href="favicon.ico" type="image/x-ico" />
</head>
<body>
<content tag="nav">
    <form class="navbar-form navbar-left">
        <div class="form-group">
            <input type="text" class="form-control" placeholder="Search">
        </div>
    </form>
</content>

<div class="container-fluid">
    <div class="row" style="padding: 2px 15px;">
        <div class="col-md-12" >
            <g:form class="form-horizontal" mapping="resetSubmit">
                <div class="form-group">
                    <label for="password_login" class="col-sm-2 control-label">Password</label>
                    <div class="col-sm-10">
                        <g:field type="hidden" name="code" value="$code"></g:field>
                        <g:field type="password" class="form-control" name="password" id="password_login" />
                    </div>
                </div>
                <div class="form-group">
                    <label for="confirmPassword_login" class="col-sm-2 control-label">Confirm Password</label>
                    <div class="col-sm-10">
                        <g:field type="password" class="form-control" name="confirmPassword" id="confirmPassword_login" />
                    </div>
                </div>
                <div class="form-group">
                    <div class="col-sm-offset-2 col-sm-10">
                        <button type="submit" class="btn btn-default">update</button>
                    </div>
                </div>
            </g:form>
        </div>
    </div>
</div>

</body>
</html>
