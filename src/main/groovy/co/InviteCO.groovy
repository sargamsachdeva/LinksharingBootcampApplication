package co

import grails.validation.Validateable

class InviteCO implements Validateable {
    String email
    Long topic

    @Override
    public String toString() {
        return "InviteCO{" +
                "email='" + email + '\'' +
                ", topic=" + topic +
                '}';
    }
}