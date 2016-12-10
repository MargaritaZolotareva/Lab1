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
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;
import org.apache.commons.codec.binary.Base64;
import org.hibernate.Transaction;

/**
 *
 * @author Margarita
 */
public class MyListener implements MessageListener {

    private final SecretKey symmetricKey;
    org.hibernate.SessionFactory sessionFactory;

    public MyListener(SecretKey symmetricKey, org.hibernate.SessionFactory sessionFactory) {
        this.symmetricKey = symmetricKey;
        this.sessionFactory = sessionFactory;
    }

    @Override
    public void onMessage(Message m) {
        try {
            TextMessage msg = (TextMessage) m;
            Object obj = DataMethodHelper.deserialize(DataMethodHelper.decompress(decrypt(msg.getText().getBytes(), this.symmetricKey)));
            String objClass = obj.getClass().toString().replace("class com.springapp.entities.", "");
            org.hibernate.Session session = sessionFactory.openSession();
            Transaction tx = session.beginTransaction();
            switch (objClass) {
                case "Students":
                    Students student = (Students) obj;
                    System.out.println("Student found!");
                    session.save(student);
                    break;
                case "StudentsAndClasses":
                    StudentsAndClasses studentAndClass = (StudentsAndClasses) obj;
                    System.out.println("StudentAndClass found!");
                    session.save(studentAndClass);
                    break;
                case "Classes":
                    Classes studentsClass = (Classes) obj;
                    System.out.println("Class found!");
                    session.save(studentsClass);
                    break;
                case "Results":
                    Results result = (Results) obj;
                    System.out.println("Result found!");
                    session.save(result);
                    break;
                case "Assignments":
                    Assignments assignment = (Assignments) obj;
                    System.out.println("Assignment found!");
                    session.save(assignment);
                    break;
                case "Departments":
                    Departments department = (Departments) obj;
                    System.out.println("Department found!");
                    session.save(department);
                    break;
                case "Instructors":
                    Instructors instructor = (Instructors) obj;
                    System.out.println("Instructor found!");
                    session.save(instructor);
                    break;
            }
            tx.commit();
            session.close();
            System.out.println("following message is received:" + msg.getText());
        } catch (JMSException e) {
            System.out.println(e);
        } catch (Exception ex) {
            Logger.getLogger(MyListener.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static byte[] decrypt(byte[] dataBytes, SecretKey secretKey)
            throws Exception {
        byte[] encryptedTextByte = Base64.decodeBase64(dataBytes);
        cipher.init(Cipher.DECRYPT_MODE, secretKey);
        byte[] decryptedByte = cipher.doFinal(encryptedTextByte);
        return decryptedByte;
    }
}
