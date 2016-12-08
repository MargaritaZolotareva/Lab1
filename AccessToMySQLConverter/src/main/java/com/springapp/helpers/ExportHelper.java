/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.springapp.helpers;

import com.springapp.entities.Students;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

/**
 *
 * @author user
 */
public class ExportHelper {
    public static void writeExcel(List<Students> students, String excelFilePath) throws IOException {
        Workbook workbook = new HSSFWorkbook();
        Sheet sheet = workbook.createSheet();

        int rowCount = 0;

        for (Students student : students) {
            Row row = sheet.createRow(++rowCount);
            writeStudent(student, row);
        }

        try (FileOutputStream outputStream = new FileOutputStream(excelFilePath)) {
            workbook.write(outputStream);
        }
    }

    public static void writeResultsExcel(ResultSet results, String excelFilePath) throws IOException, SQLException {
        Workbook workbook = new HSSFWorkbook();
        Sheet sheet = workbook.createSheet();

        int rowCount = 0;
        while (results.next()) {
            Row row = sheet.createRow(++rowCount);
            writeResult(results, row);
        }

        try (FileOutputStream outputStream = new FileOutputStream(excelFilePath)) {
            workbook.write(outputStream);
        }
    }

    public static void writeResult(ResultSet rs, Row row) throws SQLException {
        Cell cell = row.createCell(0);
        cell.setCellValue(rs.getString(1));

        cell = row.createCell(1);
        cell.setCellValue(rs.getString(2));

        cell = row.createCell(2);
        cell.setCellValue(rs.getString(3));

        cell = row.createCell(3);
        cell.setCellValue(rs.getString(4));

        cell = row.createCell(4);
        cell.setCellValue(rs.getString(5));

        cell = row.createCell(5);
        cell.setCellValue(rs.getInt(6));

        cell = row.createCell(6);
        cell.setCellValue(rs.getBoolean(7));

        cell = row.createCell(7);
        cell.setCellValue(rs.getString(8));

        cell = row.createCell(8);
        cell.setCellValue(rs.getBoolean(9));

        cell = row.createCell(9);
        cell.setCellValue(rs.getDouble(10));

        cell = row.createCell(10);
        cell.setCellValue(rs.getInt(11));
    }

    public static void writeStudent(Students student, Row row) {
        Cell cell = row.createCell(0);
        cell.setCellValue(student.getStudentId());

        cell = row.createCell(1);
        cell.setCellValue(student.getFirstName());

        cell = row.createCell(2);
        cell.setCellValue(student.getLastName());
    }
}
