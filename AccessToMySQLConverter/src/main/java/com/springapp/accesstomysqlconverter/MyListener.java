/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.springapp.accesstomysqlconverter;

import static com.springapp.accesstomysqlconverter.MainForm.cipher;
import com.springapp.entities.Students;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.zip.DataFormatException;
import java.util.zip.Inflater;
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
            Object obj = deserialize(decompress(decrypt(m.toString().getBytes(), originalKey)));
            Class objClass = obj.getClass();
            Transaction tx = session.beginTransaction();
            switch (objClass.toString()) {
                case "Students":
                    break;
                case "StudentsAndClasses":
                    break;
                case "Classes":
                    break;
                case "Results":
                    break;
                case "Assignments":
                    break;
                case "Departments":
                    break;
                case "Instructors":
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

    public static byte[] decompress(byte[] data) throws IOException, DataFormatException {
        Inflater inflater = new Inflater();
        inflater.setInput(data);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length);
        byte[] buffer = new byte[1024];
        while (!inflater.finished()) {
            int count = inflater.inflate(buffer);
            outputStream.write(buffer, 0, count);
        }
        outputStream.close();
        byte[] output = outputStream.toByteArray();
        return output;
    }

    public static Object deserialize(byte[] bytes) throws IOException, ClassNotFoundException {
        try (ByteArrayInputStream b = new ByteArrayInputStream(bytes)) {
            try (ObjectInputStream o = new ObjectInputStream(b)) {
                return o.readObject();
            }
        }
    }
}
