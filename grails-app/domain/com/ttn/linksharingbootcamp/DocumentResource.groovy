package com.ttn.linksharingbootcamp

import constants.Constants

class DocumentResource extends Resource {

    String filePath
    String fileName
    String contentType

    static transients = ['contentType',
                         'fileName']
/*

    static constraints = {
        filePath(blank: false)
        contentType(bindable: true, blank: false, validator: { val, obj ->
            return val.equals(Constants.DOCUMENT_CONTENT_TYPE)
        })

    }
*/

    public getFileName() {

        return this.fileName
    }
    @Override
    public String toString() {
        return "DocumentResource{" +
                "filePath='" + filePath + '\'' +
                ", fileName='" + fileName + '\'' +
                '}';
    }
}
