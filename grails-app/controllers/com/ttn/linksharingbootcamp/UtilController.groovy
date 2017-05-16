package com.ttn.linksharingbootcamp

class UtilController {

    def index() {

        log.info("Success")
        log.warn("warning..")
        log.error("error....")

        render "Params :  ${params}"
        log.info("Params ${params}")
    }
}
