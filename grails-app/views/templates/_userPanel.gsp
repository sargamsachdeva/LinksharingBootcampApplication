
        <div class="col-md-4" >
            <ls:userImage userId="${userDetails.id}" class="img img-responsive img-thumbnail" height="75px"
                          width="75px"/>
        </div>

        <div class="col-md-8">
            <div class="row">
                <div class="col-sm-12">
            <span class="text-primary">${userDetails.firstName} ${userDetails.lastName}</span><br/>
            <span class="text-muted">@${userDetails.name}</span>
        </div>
            </div>

            <div class="row">
                <div class="col-md-6">
                    <span class="text-muted">Subscriptions</span><br/>
                    <span class="text-primary"><ls:subscriptionCount userId="${userDetails.id}"/></span>
                </div>

                <div class="col-md-6">
                    <span class="text-muted">Topics</span><br/>
                    <span class="text-primary"><ls:topicCount userId="${userDetails.id}"/></span>
                </div>
            </div>
        </div>
