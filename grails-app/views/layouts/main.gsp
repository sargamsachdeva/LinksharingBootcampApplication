<!doctype html>
<html lang="en" class="no-js">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <meta http-equiv="X-UA-Compatible" content="IE=edge"/>
    <title>
        <g:layoutTitle default="Grails"/>
    </title>
    <meta name="viewport" content="width=device-width, initial-scale=1"/>
    <asset:stylesheet src="font-awesome.min.css"/>
    <asset:stylesheet src="application.css"/>
    <asset:stylesheet src="style.css"/>

    <g:layoutHead/>
</head>
<body>
<nav class="navbar navbar-default">
    <div class="container-fluid">
        <!-- Brand and toggle get grouped for better mobile display -->
        %{--<div class="navbar-header">
            <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#bs-example-navbar-collapse-1" aria-expanded="false">
                <span class="sr-only">Menu</span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </button>
            <a class="navbar-brand modal-title" href="#">Link Sharing</a>
        </div>
--}%
        <!-- Collect the nav links, forms, and other content for toggling -->

        <div class="navbar-header">

            <g:link controller="login" action="index" class="navbar-brand modal-title" >Link Sharing</g:link>
        </div>

        <div >
        <ul class="nav navbar-nav navbar-right">
            <li>
                <g:form name="globalSearchForm" class="navbar-form form-inline" method="get"
                        controller="resource" action="search">
                    <div class="form-group">
                        <label class="sr-only" for="globalSearchBox">Search</label>

                        <div class="input-group">
                            <div class="input-group-addon globalSearchBtn"><span class="glyphicon glyphicon-search"
                                                                                 style="font-size: large"></span>
                            </div>
                            <g:textField name="q" type="text" class="form-control globalSearchBox"
                                         id="globalSearchBox" placeholder="Search"/>
                            <g:hiddenField name="global" value="${true}"/>
                            <div class="input-group-addon globalSearchCancelBtn"><span
                                    class="glyphicon glyphicon-remove globalSearchCancelBtn"
                                    style="font-size: large"></span></div>
                        </div>
                    </div>
                    <g:hiddenField name="visibility" id="visibility" class="visibility"
                                   value="PUBLIC"/>
                </g:form>
            </li>
            <g:if test="${session.user}">
                <li>
                    <span>
                        <a class="btn customSpan" role="button" data-toggle="modal" title="Create Topic"
                           data-placement="bottom"
                           data-target="#createtopicModal">
                            <span class="glyphicon glyphicon-pencil glyphicon-custom "></span>
                        </a>
                        <a class="btn customSpan" id="inviteModalBtn" role="button" data-toggle="modal"
                           data-target="#sendInvite" title="Send Invitation"
                           data-placement="bottom">
                            <span class="glyphicon glyphicon-envelope glyphicon-custom"></span>
                        </a>
                        <a class="btn customSpan" id="linkResourceModalBtn" role="button" data-toggle="modal"
                           data-target="#sharelinkModal" title="Link Resource"
                           data-placement="bottom">
                            <span class="glyphicon glyphicon-paperclip glyphicon-custom"></span>
                        </a>
                        <a class="btn customSpan" id="documentResourceModalBtn" role="button" data-toggle="modal"
                           data-target="#sharedocModal" title="Document Resource"
                           data-placement="bottom">
                            <span class="glyphicon glyphicon-edit glyphicon-custom"></span>
                        </a>
                    </span>
                </li>
                <li>
                    <div class="dropdown">
                        <a class="btn dropdown-toggle" type="button" data-toggle="dropdown">
                            <span class="glyphicon glyphicon-user glyphicon-user-trending-topics"
                                  style="font-size: large"></span>${session.user.userName}
                            <span class="caret"></span>
                        </a>
                         <ul class="dropdown-menu" style="background-color: #204d74">
                            <li><g:link>Profile</g:link></li>
                            <li><g:link controller="user" action="showEditProfile">Edit Profile</g:link></li>

                             <g:if test="${session.user.admin}">
                                 <li><g:link controller="user" action="registeredUsers
                                    ">Users</g:link></li>
                             </g:if>
                             <li><g:link controller="login" action="logout">Logout</g:link></li>
                        </ul>
                    </div>
                </li>
            </g:if>
        </ul>
        </div>
    </div>
</nav>

<br>
<g:if test="${flash.message}">
    <div class="alert alert-success alert-dismissable">
        <button type="button" class="close" data-dismiss="alert" aria-label="Close"><span
                aria-hidden="true">&times;</span></button>
        ${flash.message}
    </div>
</g:if>


<g:if test="${flash.error}">
    <div class="alert alert-danger alert-warning alert-dismissable">
        <button type="button" class="close" data-dismiss="alert" aria-label="Close"><span
                aria-hidden="true">&times;</span></button>
        ${flash.error}
    </div>
</g:if>

<g:layoutBody/>

<div class="footer" role="contentinfo"></div>
<g:if test="${session.user}">
    <g:render template="/topic/create"></g:render>
    <g:render template="/resource/edit"></g:render>
    <g:render template="/resource/linkResourceCreate"></g:render>
    <g:render template="/resource/documentResourceCreate"></g:render>
    <g:render template="/user/sendInvite"></g:render>
    <g:render template="/topic/edit"></g:render>

</g:if>
<asset:javascript src="application.js"/>

</body>
</html>
