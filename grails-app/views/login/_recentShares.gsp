<g:each in="${recentPostList}" var="recentPost">


    <div style="padding-top:30px" class="panel-body">

            <div class="row">
                <div class="col-sm-2">
                    <g:link controller="user" action="profile" params="[id: recentPost.userID]">
                        <ls:userImage userId="${recentPost.userID}" class="img img-responsive img-thumbnail" height="75px"
                                      width="75px"/>
                    </g:link>
                </div>


                <div class="col-sm-6">
                    <div class="col-sm-4 ">
                        <h3 class="header">@${recentPost.userFirstName}</h3>
                </div>

                <div class="col-sm-4" style="margin-top:25px">
                    <span class="text-muted">@${recentPost.userName}</span>
                </div>

                <div class="col-sm-4 pull-right col-sm-push-10 linkUnderlineAll">
                    <a href="#">${recentPost.topicName}</a>
                </div>

            </div>

            <div class="col-sm-8">

                <p style="margin-left:100px; margin-right:40px ;text-align:center">
                    ${recentPost.description}
                </p>
            </div>
        </div>

        <div class="row">
            <div class="col-sm-12">
                <div class="col-sm-8 linkUnderlineAll col-sm-push-10 pull right">
                    <a href="#">View Post</a>
                </div>
            </div>
        </div>
    </div>
    </g:each>
