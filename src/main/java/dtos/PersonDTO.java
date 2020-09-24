/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dtos;

import entities.Person;

/**
 *
 * @author SJUBE
 */
public class PersonDTO {

    private long id;
    private String fName;
    private String lName;
    private String phone;

    public PersonDTO(Person p) {
        this.fName = p.getFname();
        this.lName = p.getLname();
        this.phone = p.getPhone();
        this.id = p.getId();
    }

    public PersonDTO(String fn, String ln, String phone) {
        this.fName = fn;
        this.lName = ln;
        this.phone = phone;
    }

    public PersonDTO() {
    }
    // getters setters hashcode and equals 

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getfName() {
        return fName;
    }

    public void setfName(String fName) {
        this.fName = fName;
    }

    public String getlName() {
        return lName;
    }

    public void setlName(String lName) {
        this.lName = lName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

}
