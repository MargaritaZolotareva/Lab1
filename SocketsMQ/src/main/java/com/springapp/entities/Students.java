package com.springapp.entities;
// Generated Nov 14, 2016 12:02:28 AM by Hibernate Tools 4.3.1



/**
 * Students generated by hbm2java
 */
public class Students  implements java.io.Serializable {


     private int studentId;
     private String firstName;
     private String lastName;
     private String parentsNames;
     private String address;
     private String city;
     private String stateOrProvince;
     private String postalCode;
     private String phoneNumber;
     private String emailName;
     private String major;
     private String studentNumber;
     private String notes;

    public Students() {
    }

	
    public Students(int studentId, String firstName, String lastName) {
        this.studentId = studentId;
        this.firstName = firstName;
        this.lastName = lastName;
    }
    public Students(int studentId, String firstName, String lastName, String parentsNames, String address, String city, String stateOrProvince, String postalCode, String phoneNumber, String emailName, String major, String studentNumber, String notes) {
       this.studentId = studentId;
       this.firstName = firstName;
       this.lastName = lastName;
       this.parentsNames = parentsNames;
       this.address = address;
       this.city = city;
       this.stateOrProvince = stateOrProvince;
       this.postalCode = postalCode;
       this.phoneNumber = phoneNumber;
       this.emailName = emailName;
       this.major = major;
       this.studentNumber = studentNumber;
       this.notes = notes;
    }
   
    public int getStudentId() {
        return this.studentId;
    }
    
    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }
    public String getFirstName() {
        return this.firstName;
    }
    
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    public String getLastName() {
        return this.lastName;
    }
    
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    public String getParentsNames() {
        return this.parentsNames;
    }
    
    public void setParentsNames(String parentsNames) {
        this.parentsNames = parentsNames;
    }
    public String getAddress() {
        return this.address;
    }
    
    public void setAddress(String address) {
        this.address = address;
    }
    public String getCity() {
        return this.city;
    }
    
    public void setCity(String city) {
        this.city = city;
    }
    public String getStateOrProvince() {
        return this.stateOrProvince;
    }
    
    public void setStateOrProvince(String stateOrProvince) {
        this.stateOrProvince = stateOrProvince;
    }
    public String getPostalCode() {
        return this.postalCode;
    }
    
    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }
    public String getPhoneNumber() {
        return this.phoneNumber;
    }
    
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
    public String getEmailName() {
        return this.emailName;
    }
    
    public void setEmailName(String emailName) {
        this.emailName = emailName;
    }
    public String getMajor() {
        return this.major;
    }
    
    public void setMajor(String major) {
        this.major = major;
    }
    public String getStudentNumber() {
        return this.studentNumber;
    }
    
    public void setStudentNumber(String studentNumber) {
        this.studentNumber = studentNumber;
    }
    public String getNotes() {
        return this.notes;
    }
    
    public void setNotes(String notes) {
        this.notes = notes;
    }
}


