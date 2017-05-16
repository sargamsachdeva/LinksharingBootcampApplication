package com.ttn.linksharingbootcamp

import co.LinkResourceCO


class LinkResourceController {


    def resourceService;
    def index() { }


    def save(LinkResourceCO linkResourceCO) {

        User user1 = session['user']
        linkResourceCO.createdBy=user1

        if(linkResourceCO.validate()) {
            LinkResource linkResource =  resourceService.saveLinkResource(linkResourceCO,user1)

            if(linkResource) {
                flash.message=g.message(code:"com.ttn.linksharing.linkResource.save.link.resource.saved")
            }
            else {
                flash.error=g.message(code:"com.ttn.linksharing.linkResource.save.link.resource.not.saved")
            }

        }
        else {
            flash.error="validation error"
        }
        redirect(controller: "user", action:"index" )
    }
}