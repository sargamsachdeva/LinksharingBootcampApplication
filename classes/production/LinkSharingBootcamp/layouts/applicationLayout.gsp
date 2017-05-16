%{--
<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
 <html lang="en" class="no-js">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width,initial-scale=1">
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <title><g:layoutTitle default="Linksharing"/></title>
    <asset:javascript src="jquery-2.2.0.min.js"/>
    <asset:stylesheet src="bootstrap.css"/>
    <asset:stylesheet src="bootstrap-theme.min.css"/>
    <asset:stylesheet src="font-awesome.min.css"/>
    <asset:stylesheet src="application.css"/>
    <g:layoutHead/>
</head>

<body>
</body>
</html>--}%

<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<!--[if lt IE 7 ]> <html lang="en" class="no-js ie6"> <![endif]-->
<!--[if IE 7 ]>    <html lang="en" class="no-js ie7"> <![endif]-->
<!--[if IE 8 ]>    <html lang="en" class="no-js ie8"> <![endif]-->
<!--[if IE 9 ]>    <html lang="en" class="no-js ie9"> <![endif]-->
<!--[if (gt IE 9)|!(IE)]><!--> <html lang="en" class="no-js"><!--<![endif]-->
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width,initial-scale=1">
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <title><g:layoutTitle default="Linksharing"/></title>
    <asset:javascript src="jquery.js"/>
    <asset:stylesheet src="bootstrap.min.css"/>
    <asset:stylesheet src="bootstrap-theme.min.css"/>
    <asset:stylesheet src="font-awesome.min.css"/>
    <asset:stylesheet src="application.css"/>
    <g:layoutHead/>
</head>

<body>
<nav class="navbar navbar-default navbar-static-top">
    <div class="container-fluid">
        <div class="navbar-header">
            <button type="button" class="navbar-toggle collapsed" data-toggle="collapse"
                    data-target="#linksharingnavbar"
                    aria-expanded="false" aria-controls="navbar">
                <span class="sr-only">Toggle navigation</span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </button>
            <g:if test="${session.user}">
                <g:link class="navbar-brand modal-title" uri="/user">LinkSharing
                </g:link>
            </g:if>
            <g:else>
                <g:link uri="/" class="navbar-brand modal-title">LinkSharing
                </g:link>
            </g:else>

        </div>

        <div id="linksharingnavbar" class="navbar-collapse collapse">
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
                            <a class="btn" role="button" data-toggle="modal" title="Create Topic"
                               data-placement="bottom"
                               data-target="#createtopicModal">
                                <span class="fa fa-weixin"></span>
                            </a>
                            <a class="btn" id="inviteModalBtn" role="button" data-toggle="modal"
                               data-target="#sendinviteModal" title="Send Invitation"
                               data-placement="bottom">
                                <span class="glyphicon glyphicon-envelope"></span>
                            </a>
                            <a class="btn" id="linkResourceModalBtn" role="button" data-toggle="modal"
                               data-target="#sharelinkModal" title="Link Resource"
                               data-placement="bottom">
                                <span class="fa fa-link"></span>
                            </a>
                            <a class="btn" id="documentResourceModalBtn" role="button" data-toggle="modal"
                               data-target="#sharedocModal" title="Document Resource"
                               data-placement="bottom">
                                <span class="fa fa-file-o"></span>
                            </a>
                        </span>
                    </li>
                    <li>
                        <div class="dropdown">
                            <a class="btn dropdown-toggle" type="button" data-toggle="dropdown">
                                <span class="glyphicon glyphicon-user"
                                      style="font-size: large"></span>${session.user.getName()}
                                <span class="caret"></span>
                            </a>
                            <ul class="dropdown-menu">
                                <li><g:link controller="user" action="profile"
                                            params="[id: session.user.id]">Profile</g:link></li>
                                <li><g:link controller="user" action="edit">Edit Profile</g:link></li>
                                <g:if test="${session.user.isAdmin}">
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

<div class="container">
    <g:if test="${session.user}">
        %{--<g:render template="/topic/create"/>
        <g:render template="/topic/email"/>
--}%    %{--<g:render template="/resource/linkResourceCreate"/>--}%
    %{--<g:render template="/resource/documentResourceCreate"/>--}%
    </g:if>
    <g:else>
        <g:render template="/login/forgotPassword"/>
    </g:else>

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
    <div class="jsonObjectResponse" style="display: none">
        <button type="button" class="close" data-dismiss="alert" aria-label="Close"><span
                aria-hidden="true">&times;</span></button>
    </div>


    <g:layoutBody/>
</div>
<asset:javascript src="bootstrap.min.js"/>
<asset:javascript src="application.js"/>
<asset:javascript src="jquery.validate.min.js"/>
<asset:javascript src="additional-methods.min.js"/>
</body>
</html>