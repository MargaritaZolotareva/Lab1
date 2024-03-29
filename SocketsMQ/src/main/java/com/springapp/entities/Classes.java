package com.springapp.entities;
// Generated Nov 14, 2016 12:02:28 AM by Hibernate Tools 4.3.1



/**
 * Classes generated by hbm2java
 */
public class Classes  implements java.io.Serializable {


     private int classId;
     private String className;
     private Integer departmentId;
     private Integer sectionNum;
     private Integer instructorId;
     private String term;
     private String units;
     private Integer year;
     private String location;
     private String daysAndTimes;
     private String notes;

    public Classes() {
    }

	
    public Classes(int classId) {
        this.classId = classId;
    }
    public Classes(int classId, String className, Integer departmentId, Integer sectionNum, Integer instructorId, String term, String units, Integer year, String location, String daysAndTimes, String notes) {
       this.classId = classId;
       this.className = className;
       this.departmentId = departmentId;
       this.sectionNum = sectionNum;
       this.instructorId = instructorId;
       this.term = term;
       this.units = units;
       this.year = year;
       this.location = location;
       this.daysAndTimes = daysAndTimes;
       this.notes = notes;
    }
   
    public int getClassId() {
        return this.classId;
    }
    
    public void setClassId(int classId) {
        this.classId = classId;
    }
    public String getClassName() {
        return this.className;
    }
    
    public void setClassName(String className) {
        this.className = className;
    }
    public Integer getDepartmentId() {
        return this.departmentId;
    }
    
    public void setDepartmentId(Integer departmentId) {
        this.departmentId = departmentId;
    }
    public Integer getSectionNum() {
        return this.sectionNum;
    }
    
    public void setSectionNum(Integer sectionNum) {
        this.sectionNum = sectionNum;
    }
    public Integer getInstructorId() {
        return this.instructorId;
    }
    
    public void setInstructorId(Integer instructorId) {
        this.instructorId = instructorId;
    }
    public String getTerm() {
        return this.term;
    }
    
    public void setTerm(String term) {
        this.term = term;
    }
    public String getUnits() {
        return this.units;
    }
    
    public void setUnits(String units) {
        this.units = units;
    }
    public Integer getYear() {
        return this.year;
    }
    
    public void setYear(Integer year) {
        this.year = year;
    }
    public String getLocation() {
        return this.location;
    }
    
    public void setLocation(String location) {
        this.location = location;
    }
    public String getDaysAndTimes() {
        return this.daysAndTimes;
    }
    
    public void setDaysAndTimes(String daysAndTimes) {
        this.daysAndTimes = daysAndTimes;
    }
    public String getNotes() {
        return this.notes;
    }
    
    public void setNotes(String notes) {
        this.notes = notes;
    }




}


