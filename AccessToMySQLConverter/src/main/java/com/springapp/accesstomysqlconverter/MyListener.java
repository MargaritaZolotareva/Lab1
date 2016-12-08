/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.springapp.accesstomysqlconverter;

import static com.springapp.accesstomysqlconverter.MainForm.cipher;
import com.springapp.entities.Assignments;
import com.springapp.entities.Classes;
import com.springapp.entities.Departments;
import com.springapp.entities.Instructors;
import com.springapp.entities.Results;
import com.springapp.entities.Students;
import com.springapp.entities.StudentsAndClasses;
import com.springapp.helpers.DataMethodHelper;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import javax.jms.*;
import org.hibernate.Transaction;

/**
 *
 * @author Margarita
 */
public class MyListener implements MessageListener {

    private byte[] key;
    org.hibernate.Session session;

    public MyListener(byte[] key, org.hibernate.Session session) {
        this.key = key;
        this.session = session;
    }

    public void onMessage(Message m) {
        try {
            TextMessage msg = (TextMessage) m;
            SecretKey originalKey = new SecretKeySpec(key, 0, key.length, "AES");
            Object obj = DataMethodHelper.deserialize(DataMethodHelper.decompress(decrypt(m.toString().getBytes(), originalKey)));
            Class objClass = obj.getClass();
            Transaction tx = session.beginTransaction();
            switch (objClass.toString()) {
                case "Students":
                    Students student = (Students) obj;
                    session.save(student);
                    break;
                case "StudentsAndClasses":
                    StudentsAndClasses studentAndClass = (StudentsAndClasses) obj;
                    session.save(studentAndClass);
                    break;
                case "Classes":
                    Classes studentsClass = (Classes) obj;
                    session.save(studentsClass);
                    break;
                case "Results":
                    Results result = (Results) obj;
                    session.save(result);
                    break;
                case "Assignments":
                    Assignments assignment = (Assignments) obj;
                    session.save(assignment);
                    break;
                case "Departments":
                    Departments department = (Departments) obj;
                    session.save(department);
                    break;
                case "Instructors":
                    Instructors instructor = (Instructors) obj;
                    session.save(instructor);
                    break;
            }

            tx.commit();
            System.out.println("following message is received:" + msg.getText());
        } catch (JMSException e) {
            System.out.println(e);
        } catch (Exception ex) {
            Logger.getLogger(MyListener.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static byte[] decrypt(byte[] dataBytes, SecretKey secretKey)
            throws Exception {
        java.util.Base64.Decoder decoder = java.util.Base64.getDecoder();
        byte[] encryptedTextByte = decoder.decode(dataBytes);
        cipher.init(Cipher.DECRYPT_MODE, secretKey);
        byte[] decryptedByte = cipher.doFinal(encryptedTextByte);
        return decryptedByte;
    }
}
