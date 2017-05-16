package com.ttn.linksharingbootcamp


class ReadingItemController {

    def index() {

        render "hello"
    }

    def changeisRead(Long id, Boolean isRead) {

        User user = session.user
        Resource resource = Resource?.get(id)

        log.warn("user-> ${user} id-> ${resource} isread->${isRead}")

        if (ReadingItem.executeUpdate("update ReadingItem set isRead=:isRead where resource=:resource" +
                " and user=:user", [isRead: isRead, resource: resource, user: user])) {

           render flash.message = "Reading Item Status Changed"

        } else {
            render flash.error = "Reading Item Status not Changed"

        }
    }

    def toggleIsRead(Long id) {

        User user = session.user
        Resource resource = Resource.get(id)
        log.warn("resource-> ${resource}")
        if (user && resource) {
            ReadingItem readingItem = ReadingItem.findByResourceAndUser(resource, user)
            if (readingItem) {
                readingItem.isRead = !readingItem.isRead
            } else {
                readingItem = new ReadingItem(resource: resource, user: user, isRead: true)
            }
            if (readingItem.save(flush:true)) {
                readingItem.isRead = !readingItem.isRead
                flash.message = "Status updated"
            } else {
                flash.error = readingItem.errors.allErrors.collect { message(error: it) }.join('<br/>')
            }
        } else {
            render(view: '/user/index')
        }
        redirect(url:request.getHeader("referer"))
    }

}
