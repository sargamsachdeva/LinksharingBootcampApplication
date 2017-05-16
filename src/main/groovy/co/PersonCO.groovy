package co

import grails.validation.Validateable

class PersonCO implements Validateable {

    String fname
    String lname
  //  Integer age


    @Override
    public String toString() {
        return "PersonCO{" +
                "fname='" + fname + '\'' +
                ", lname='" + lname + '\'' +
                '}';
    }
}
