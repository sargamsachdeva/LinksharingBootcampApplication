package linksharingbootcamp

class UrlMappings {


    static mappings = {

        name reset: "/reset"(controller: 'login', action: 'resetPassword', method: 'POST')
        name resetSubmit: "/reset/submit"(controller: 'login', action: 'resetPasswordProcess', method: 'POST')

        "/$controller/$action?/$id?(.$format)?"{
            constraints {
                // apply constraints here
            }
        }

        "/"(controller: 'login',action: 'index')
        "500"(view:'/error')
        "404"(view:'/notFound')

    }
}
