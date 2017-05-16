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
            <h3>Forgot Password?</h3>
            <g:form class="form-horizontal" mapping="reset">
                <div class="form-group">
                    <label for="email" class="col-sm-2 control-label">email</label>
                    <div class="col-sm-6">
                        <g:field type="text" class="form-control" name="email" id="email" />
                    </div>
                </div>
                <div class="form-group">
                    <div class="col-sm-offset-2 col-sm-10">
                        <button type="submit" class="btn btn-default">Submit</button>
                    </div>
                </div>
            </g:form>
        </div>
    </div>
</div>

</body>
</html>
