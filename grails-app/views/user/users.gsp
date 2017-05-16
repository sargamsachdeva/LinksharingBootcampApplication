
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <meta name="layout" content="main">
    <title>Users</title>
</head>

<body>
<div class="panel panel-default panel-primary panelTrendingTopics">
    <div class="panel-heading">
        <div class="row">
            <div class="col-md-4">
                Users
            </div>

            <div class="col-md-8">
                <g:form name="adminUsersSearchForm" controller="user" action="registeredUsers" class="form-inline">
                    <div class="form-group">
                        <div class="input-group">
                            <select title="Select" class="dropdown btn btn-default" id="isActive" name="isActive">
                                <option value="${null}">All Users</option>
                                <option value="${true}">Active</option>
                                <option value="${false}">Inactive</option>
                            </select>
                        </div>
                        <label class="sr-only" for="q">Search</label>

                        <div class="input-group">
                            <div class="input-group-addon adminUsersSearchBtn">
                                <button type="submit">
                                    <span class="glyphicon glyphicon-search" style="font-size: small"></span>
                                </button>
                            </div>
                            <g:textField name="q" type="text" class="form-control"
                                         id="q" placeholder="Search"/>
                        </div>
                    </div>
                </g:form>
            </div>
        </div>
    </div>

    <div class="panel-body">
        <div class="table-responsive">
            <table class="table table-condensed table-hover">
                <thead>
                <tr>
                    <g:sortableColumn property="id" title="Id"/>
                    <g:sortableColumn property="userName" title="Username"/>
                    <g:sortableColumn property="email" title="Email"/>
                    <g:sortableColumn property="firstName" title="Firstname"/>
                    <g:sortableColumn property="lastName" title="Lastname"/>
                    <g:sortableColumn property="active" title="Active"/>
                    <th>Manage</th>
                </tr>
                </thead>
                <g:each in="${userList}">
                    <g:if test="${it.isActive}">
                        <g:set var="alertClass" value="alert alert-success"/>
                    </g:if>
                    <g:else>
                        <g:set var="alertClass" value="alert alert-danger"/>
                    </g:else>
                    <tr class="${alertClass}">
                        <td>${it.id}</td>
                        <td>${it.name}</td>
                        <td>${it.email}</td>
                        <td>${it.firstName}</td>
                        <td>${it.lastName}</td>
                        <td>${it.isActive}</td>
                        <td>
                            <g:if test="${it.isActive}">
                                <g:link controller="user" action="updateUserActiveStatus"
                                        params="[id: it.id]">De-Activate</g:link>
                            </g:if>
                            <g:else>
                                <g:link controller="user" action="updateUserActiveStatus"
                                        params="[id: it.id]">Activate</g:link>
                            </g:else>

                        </td>
                    </tr>
                </g:each>
            </table>
            <g:paginate class="pagination" total="${totalUsers}" controller="user" action="registeredUsers"
                        max="${userSearchCO.max}" offset="${userSearchCO.offset}"/>
        </div>
    </div>
</div>
</body>
</html>