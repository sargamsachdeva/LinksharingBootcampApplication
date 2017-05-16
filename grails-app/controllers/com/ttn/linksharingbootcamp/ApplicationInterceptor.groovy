package com.ttn.linksharingbootcamp

class ApplicationInterceptor {

    ApplicationInterceptor() {
        matchAll()
    }

    boolean before() {
        log.info("Params :: ${params}")
        true
    }

    boolean after() {
        true
    }

    void afterView() {
        // no-op
    }

}

