/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.springapp.helpers;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author user
 */
public class DbHelper {

    public static ResultSet fetchDataFromDB() throws SQLException {
        Connection conn = DriverManager.getConnection("jdbc:ucanaccess://C:/Users/user/Desktop/Students.accdb");
        Statement s = conn.createStatement();
        ResultSet rs = s.executeQuery("SELECT * FROM Denormalized");
        return rs;
    }
}
