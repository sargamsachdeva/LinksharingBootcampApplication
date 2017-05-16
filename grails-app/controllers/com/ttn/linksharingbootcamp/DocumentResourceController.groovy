package com.ttn.linksharingbootcamp

import co.DocumentResourceCO
import constants.Constants

class DocumentResourceController {

    def resourceService
    def download(Long id) {

        DocumentResource documentResource = (DocumentResource) Resource.get(id)
        def file = resourceService.downloadDocumentResource(documentResource, session.user)

        if (file) {
            response.setContentType(Constants.DOCUMENT_CONTENT_TYPE)
            response.setHeader("Content-disposition", "attachment;filename=\"${documentResource.toString()}\"")
            response.outputStream << file.bytes
        } else {
            render flash.error = g.message(code: "com.ttn.linksharing.document.resource.download.resource.not.found")
        }

        redirect(url:request.getHeader("referer"))
    }

    def upload(DocumentResourceCO documentResourceCO) {

        User user1 = session['user']
        documentResourceCO.createdBy=user1

        if(documentResourceCO.validate()) {

            def file = params.file

            DocumentResource tempResource = resourceService.uploadDocumentResource(documentResourceCO, file,user1)
            if (tempResource) {
                flash.message = g.message(code:"com.ttn.linksharing.documentResource.save.document.resource.saved")
            }
            else {
                flash.error = g.message(code:"com.ttn.linksharing.documentResource.save.document.resource.not.saved")
            }
        }
        else {

            flash.error =g.message(code:"com.ttn.linksharing.documentResouce.save.document.resource.user.not.set")
        }
        redirect(controller: "user", action:"index" )
    }



}
