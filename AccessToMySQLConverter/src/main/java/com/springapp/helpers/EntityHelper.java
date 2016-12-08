/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.springapp.helpers;

import com.springapp.entities.Assignments;
import com.springapp.entities.Classes;
import com.springapp.entities.Departments;
import com.springapp.entities.Instructors;
import com.springapp.entities.Results;
import com.springapp.entities.Students;
import com.springapp.entities.StudentsAndClasses;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author user
 */
public class EntityHelper {
    public static Students createStudent(ResultSet rs) throws SQLException {
        Students student = new Students();

        student.setFirstName(rs.getString(1));
        student.setLastName(rs.getString(2));
        student.setStudentId(rs.getInt(3));
        student.setParentsNames(rs.getString(4));
        student.setAddress(rs.getString(5));
        student.setCity(rs.getString(6));
        student.setStateOrProvince(rs.getString(7));
        student.setPostalCode(rs.getString(8));
        student.setPhoneNumber(rs.getString(9));
        student.setEmailName(rs.getString(10));
        student.setMajor(rs.getString(11));
        student.setStudentNumber(rs.getString(12));
        student.setNotes(rs.getString(13));

        return student;
    }

    public static StudentsAndClasses createStudentAndClass(ResultSet rs) throws SQLException {
        StudentsAndClasses studentsAndClasses = new StudentsAndClasses();

        studentsAndClasses.setStudentClassId(rs.getInt(14));
        studentsAndClasses.setClassId(rs.getInt(15));
        studentsAndClasses.setStudentId(rs.getInt(16));
        studentsAndClasses.setGrade(rs.getString(17));

        return studentsAndClasses;
    }

    public static Classes createClass(ResultSet rs) throws SQLException {
        Classes classes = new Classes();

        classes.setClassId(rs.getInt(18));
        classes.setClassName(rs.getString(19));
        classes.setSectionNum(rs.getInt(20));
        classes.setTerm(rs.getString(21));
        classes.setUnits(rs.getString(22));
        classes.setYear(rs.getInt(23));
        classes.setLocation(rs.getString(24));
        classes.setDaysAndTimes(rs.getString(25));
        classes.setNotes(rs.getString(26));

        return classes;
    }

    public static Results createResult(ResultSet rs) throws SQLException {
        Results results = new Results();

        results.setResultsId(rs.getInt(27));
        results.setStudentId(rs.getInt(28));
        results.setAssignmentId(rs.getInt(29));
        results.setScore(rs.getInt(46));
        results.setLate(rs.getBoolean(30));

        return results;
    }

    public static Assignments createAssignment(ResultSet rs) throws SQLException {
        Assignments assignment = new Assignments();

        assignment.setAssignmentId(rs.getInt(31));
        assignment.setAssignmentsDescription(rs.getString(32));
        assignment.setClassId(rs.getInt(33));
        assignment.setExam(rs.getBoolean(34));
        assignment.setPercentOfGrade(rs.getFloat(35));
        assignment.setMaximumPoints(rs.getInt(47));

        return assignment;
    }

    public static Departments createDepartment(ResultSet rs) throws SQLException {
        Departments department = new Departments();

        department.setDepartmentId(rs.getInt(36));
        department.setDepartmentName(rs.getString(37));
        department.setDepartmentNumber(rs.getInt(38));
        department.setDepartmentManager(rs.getString(39));
        department.setDepartmentChairperson(rs.getString(40));

        return department;
    }

    public static Instructors createInstructor(ResultSet rs) throws SQLException {
        Instructors instructor = new Instructors();

        instructor.setInstructorId(rs.getInt(41));
        instructor.setInstructor(rs.getString(42));
        instructor.setEmailName(rs.getString(43));
        instructor.setPhoneNumber(rs.getString(44));
        instructor.setExtension(rs.getString(45));

        return instructor;
    }
}
