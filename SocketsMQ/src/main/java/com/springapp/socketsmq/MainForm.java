package com.springapp.socketsmq;

import com.springapp.entities.Assignments;
import com.springapp.entities.Classes;
import com.springapp.entities.Departments;
import com.springapp.entities.Instructors;
import com.springapp.entities.Results;
import com.springapp.entities.Students;
import com.springapp.entities.StudentsAndClasses;
import com.sun.messaging.jmq.util.BASE64Decoder;
import static com.sun.xml.messaging.saaj.util.Base64.base64Decode;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.security.GeneralSecurityException;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.spec.X509EncodedKeySpec;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.zip.DataFormatException;
import java.util.zip.Deflater;
import java.util.zip.Inflater;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.naming.InitialContext;
import javax.net.ssl.SSLServerSocket;
import javax.net.ssl.SSLServerSocketFactory;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import org.apache.commons.codec.binary.Base64;
import javax.jms.*;
import javax.naming.Context;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Margarita
 */
public class MainForm extends javax.swing.JFrame {

    static HashSet assignmentsIds = new HashSet();
    static HashSet classesIds = new HashSet();
    static HashSet departmentsIds = new HashSet();
    static HashSet instructorsIds = new HashSet();
    static HashSet resultsIds = new HashSet();
    static HashSet studentsIds = new HashSet();
    static HashSet studentsAndClassesIds = new HashSet();
    static Cipher cipher;
    SecretKey symmetricKey;
    PublicKey publicKey;

    /**
     * Creates new form MainForm
     */
    public MainForm() {
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup = new javax.swing.ButtonGroup();
        SocketsButton = new javax.swing.JRadioButton();
        MQButton = new javax.swing.JRadioButton();
        SendDataButton = new javax.swing.JButton();
        SendSymKey = new javax.swing.JButton();
        receivePublicRSA = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        buttonGroup.add(SocketsButton);
        SocketsButton.setSelected(true);
        SocketsButton.setText("Sockets");
        SocketsButton.setActionCommand("socketsButton");

        buttonGroup.add(MQButton);
        MQButton.setText("MQ");

        SendDataButton.setText("Send data");
        SendDataButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SendDataButtonActionPerformed(evt);
            }
        });

        SendSymKey.setText("Send key");
        SendSymKey.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SendSymKeyActionPerformed(evt);
            }
        });

        receivePublicRSA.setText("Receive public RSA");
        receivePublicRSA.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                receivePublicRSAActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(54, 54, 54)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(MQButton)
                            .addComponent(SocketsButton)
                            .addComponent(SendDataButton, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(21, 47, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(receivePublicRSA)
                            .addComponent(SendSymKey, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(58, 58, 58)
                .addComponent(SocketsButton)
                .addGap(32, 32, 32)
                .addComponent(MQButton)
                .addGap(18, 18, 18)
                .addComponent(SendDataButton, javax.swing.GroupLayout.DEFAULT_SIZE, 47, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(receivePublicRSA, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(SendSymKey, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(36, 36, 36))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    public static byte[] decrypt(byte[] dataBytes, SecretKey secretKey)
            throws Exception {
        java.util.Base64.Decoder decoder = java.util.Base64.getDecoder();
        byte[] encryptedTextByte = decoder.decode(dataBytes);
        cipher.init(Cipher.DECRYPT_MODE, secretKey);
        byte[] decryptedByte = cipher.doFinal(encryptedTextByte);
        //String decryptedText = new String(decryptedByte);
        return decryptedByte;
    }

    private void SendDataButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SendDataButtonActionPerformed
        try {
            // Fetch data from DB
            ResultSet rs = fetchDataFromDB();

            List<String> assignments = new ArrayList<>();
            List<String> classes = new ArrayList();
            List<String> departments = new ArrayList();
            List<String> instructors = new ArrayList();
            List<String> results = new ArrayList();
            List<String> students = new ArrayList();
            List<String> studentsAndClasses = new ArrayList();

            parseData(rs, assignments, classes, departments, instructors, results, students, studentsAndClasses);

            if (this.SocketsButton.isSelected()) {

            } else if (this.MQButton.isSelected()) {
                sendDataWithMQ(students);
                sendDataWithMQ(studentsAndClasses);
                sendDataWithMQ(classes);
                sendDataWithMQ(results);
                sendDataWithMQ(assignments);
                sendDataWithMQ(departments);
                sendDataWithMQ(instructors);
            }
        } catch (SQLException ex) {
            Logger.getLogger(MainForm.class
                    .getName()).log(Level.SEVERE, null, ex);
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(MainForm.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NoSuchPaddingException ex) {
            Logger.getLogger(MainForm.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_SendDataButtonActionPerformed

    private void sendKeyWithMQ() {
        try {
            // Generate symmetric key
            KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
            keyGenerator.init(128);
            this.symmetricKey = keyGenerator.generateKey();
            cipher = Cipher.getInstance("AES");
            System.out.println("Original symmetric key:" + Base64.encodeBase64String(this.symmetricKey.getEncoded()));
            Properties props = new Properties();
            props.setProperty("java.naming.factory.initial", "com.sun.enterprise.naming.SerialInitContextFactory");
            props.setProperty("java.naming.factory.url.pkgs", "com.sun.enterprise.naming");
            props.setProperty("java.naming.factory.state", "com.sun.corba.ee.impl.presentation.rmi.JNDIStateFactoryImpl");
            props.setProperty("org.omg.CORBA.ORBInitialHost", "localhost");
            props.setProperty("org.omg.CORBA.ORBInitialPort", "3700");
            Context ctx = new InitialContext(props);
            QueueConnectionFactory f = (QueueConnectionFactory) ctx.lookup("myQueueConnectionFactory");
            QueueConnection con = f.createQueueConnection();
            con.start();
            QueueSession ses = con.createQueueSession(false, Session.AUTO_ACKNOWLEDGE);
            Queue t = (Queue) ctx.lookup("keyQueue");
            QueueSender sender = ses.createSender(t);
            BytesMessage msg = ses.createBytesMessage();
            byte[] encKey = encryptByPublic(this.publicKey, this.symmetricKey.getEncoded());
            msg.writeBytes(encKey);
            sender.send(msg);
            System.out.println("Symmetric key successfully sent.");
            con.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    private void sendDataWithMQ(List<String> list) {
        try {
            Properties props = new Properties();
            props.setProperty("java.naming.factory.initial", "com.sun.enterprise.naming.SerialInitContextFactory");
            props.setProperty("java.naming.factory.url.pkgs", "com.sun.enterprise.naming");
            props.setProperty("java.naming.factory.state", "com.sun.corba.ee.impl.presentation.rmi.JNDIStateFactoryImpl");
            props.setProperty("org.omg.CORBA.ORBInitialHost", "localhost");
            props.setProperty("org.omg.CORBA.ORBInitialPort", "3700");
            Context ctx = new InitialContext(props);
            QueueConnectionFactory f = (QueueConnectionFactory) ctx.lookup("myQueueConnectionFactory");
            QueueConnection con = f.createQueueConnection();
            con.start();
            QueueSession ses = con.createQueueSession(false, Session.AUTO_ACKNOWLEDGE);
            Queue t = (Queue) ctx.lookup("dataQueue");
            QueueSender sender = ses.createSender(t);
            TextMessage msg = ses.createTextMessage();
            for (String obj : list) {
                msg.setText(obj);
                sender.send(msg);
            }
            System.out.println("List successfully sent.");
            con.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    private void SendSymKeyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SendSymKeyActionPerformed
        sendKeyWithMQ();
        this.SendSymKey.setEnabled(false);
    }//GEN-LAST:event_SendSymKeyActionPerformed

    private void receivePublicRSAActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_receivePublicRSAActionPerformed
        SSLSocket sslsocket = null;
        InputStream inputstream = null;
        InputStreamReader inputstreamreader = null;
        BufferedReader bufferedreader = null;
        SSLServerSocket sslserversocket = null;
        DataInputStream dIn = null;
        try {
            System.setProperty("javax.net.ssl.keyStore", "mySrvKeystore");
            System.setProperty("javax.net.ssl.keyStorePassword", "123456");
            SSLServerSocketFactory sslserversocketfactory
                    = (SSLServerSocketFactory) SSLServerSocketFactory.getDefault();
            sslserversocket
                    = (SSLServerSocket) sslserversocketfactory.createServerSocket(9999);
            System.out.println("Server started");
            // This will take one of the waiting connections
            sslsocket = (SSLSocket) sslserversocket.accept();

            inputstream = sslsocket.getInputStream();
            //inputstreamreader = new InputStreamReader(inputstream);
            //bufferedreader = new BufferedReader(inputstreamreader);

            dIn = new DataInputStream(inputstream);
            byte[] key = null;
            int length = dIn.readInt();// read length of incoming message
            if (length > 0) {
                key = new byte[length];
                dIn.readFully(key, 0, key.length); // read the message
            }
            //String key = null;
            //if ((key = bufferedreader.readLine()) != null) {
            //    System.out.println("Received public key: " + key);
            //}

            //byte[] keyBytes = (new BASE64Decoder()).decodeBuffer(key);
            X509EncodedKeySpec keySpec = new X509EncodedKeySpec(key);
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            this.publicKey = keyFactory.generatePublic(keySpec);//loadPublicKey(key);
            System.out.println("Received public key: " + savePublicKey(publicKey));
        } catch (Exception exception) {
            exception.printStackTrace();
        } finally {
            try {
                //bufferedreader.close();
                //inputstreamreader.close();
                inputstream.close();
                sslsocket.close();
                sslserversocket.close();
                System.clearProperty("javax.net.ssl.keyStore");
                System.clearProperty("javax.net.ssl.keyStorePassword");
            } catch (IOException ex) {
                Logger.getLogger(MainForm.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }//GEN-LAST:event_receivePublicRSAActionPerformed

    public static String savePublicKey(PublicKey publ) throws GeneralSecurityException {
        KeyFactory fact = KeyFactory.getInstance("RSA");
        X509EncodedKeySpec spec = fact.getKeySpec(publ,
                X509EncodedKeySpec.class);
        return new String(Base64.encodeBase64(spec.getEncoded()));
    }


    public static String encrypt(byte[] dataBytes, SecretKey secretKey)
            throws Exception {
        cipher.init(Cipher.ENCRYPT_MODE, secretKey);
        byte[] encryptedByte = cipher.doFinal(dataBytes);

        java.util.Base64.Encoder encoder = java.util.Base64.getEncoder();
        String encryptedText = encoder.encodeToString(encryptedByte);
        return encryptedText;
    }

    public static byte[] serialize(Object obj) throws IOException {
        try (ByteArrayOutputStream b = new ByteArrayOutputStream()) {
            try (ObjectOutputStream o = new ObjectOutputStream(b)) {
                o.writeObject(obj);
            }
            return b.toByteArray();
        }
    }

    private byte[] encryptByPublic(PublicKey publicKey, byte[] symmetricKey) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException, GeneralSecurityException {
        //Encrypt symmetric key by public key
        Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);
        byte[] encryptedkey = cipher.doFinal(symmetricKey);
        return encryptedkey;
    }

    public static byte[] compress(byte[] data) throws IOException {
        Deflater deflater = new Deflater();
        deflater.setInput(data);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length);
        deflater.finish();
        byte[] buffer = new byte[1024];
        while (!deflater.finished()) {
            int count = deflater.deflate(buffer); // returns the generated code... index  
            outputStream.write(buffer, 0, count);
        }
        outputStream.close();
        byte[] output = outputStream.toByteArray();
        return output;
    }

    private ResultSet fetchDataFromDB() throws SQLException {
        Connection conn = DriverManager.getConnection("jdbc:ucanaccess://C:/Users/Margarita/Desktop/Students.accdb");
        Statement s = conn.createStatement();
        ResultSet rs = s.executeQuery("SELECT * FROM Denormalized");
        return rs;
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

    private void parseData(ResultSet rs, List<String> assignments, List<String> classes, List<String> departments, List<String> instructors, List<String> results, List<String> students, List<String> studentsAndClasses) throws SQLException, NoSuchAlgorithmException, NoSuchPaddingException {
        KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
        keyGenerator.init(128);
        this.symmetricKey = keyGenerator.generateKey();
        cipher = Cipher.getInstance("AES");
        while (rs.next()) {
            Students student = createStudent(rs);
            StudentsAndClasses studentAndClass = createStudentAndClass(rs);
            Classes studentClass = createClass(rs);
            Results result = createResult(rs);
            Assignments assignment = createAssignment(rs);
            Departments department = createDepartment(rs);
            Instructors instructor = createInstructor(rs);

            try {
                if (!studentsIds.contains(student.getStudentId())) {
                    studentsIds.add(student.getStudentId());
                    String encStudent = encrypt(compress(serialize(student)), symmetricKey);
                    System.out.println(encStudent);
                    students.add(encStudent);
                    Object obj = deserialize(decompress(decrypt(encStudent.toString().getBytes(), symmetricKey)));
                    if (obj instanceof Students) {
                        System.out.println("Student found!");
                    }
                }
                if (!studentsAndClassesIds.contains(studentAndClass.getStudentClassId())) {
                    studentsAndClassesIds.add(studentAndClass.getStudentClassId());
                    String encStudentAndClass = encrypt(compress(serialize(studentAndClass)), symmetricKey);
                    System.out.println(encStudentAndClass);
                    studentsAndClasses.add(encStudentAndClass);
                }
                if (!classesIds.contains(studentClass.getClassId())) {
                    classesIds.add(studentClass.getClassId());
                    String encStudentClass = encrypt(compress(serialize(studentClass)), symmetricKey);
                    System.out.println(encStudentClass);
                    classes.add(encStudentClass);
                }
                if (!resultsIds.contains(result.getResultsId())) {
                    resultsIds.add(result.getResultsId());
                    String encResult = encrypt(compress(serialize(result)), symmetricKey);
                    System.out.println(encResult);
                    results.add(encResult);
                }
                if (!assignmentsIds.contains(assignment.getAssignmentId())) {
                    assignmentsIds.add(assignment.getAssignmentId());
                    String encAssignment = encrypt(compress(serialize(assignment)), symmetricKey);
                    System.out.println(encAssignment);
                    assignments.add(encAssignment);
                }
                if (!departmentsIds.contains(department.getDepartmentId())) {
                    departmentsIds.add(department.getDepartmentId());
                    String encDepartment = encrypt(compress(serialize(department)), symmetricKey);
                    System.out.println(encDepartment);
                    departments.add(encDepartment);
                }
                if (!instructorsIds.contains(instructor.getInstructorId())) {
                    instructorsIds.add(instructor.getInstructorId());
                    String encInstructor = encrypt(compress(serialize(instructor)), symmetricKey);
                    System.out.println(encInstructor);
                    instructors.add(encInstructor);
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    static Students createStudent(ResultSet rs) throws SQLException {
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

    static StudentsAndClasses createStudentAndClass(ResultSet rs) throws SQLException {
        StudentsAndClasses studentsAndClasses = new StudentsAndClasses();

        studentsAndClasses.setStudentClassId(rs.getInt(14));
        studentsAndClasses.setClassId(rs.getInt(15));
        studentsAndClasses.setStudentId(rs.getInt(16));
        studentsAndClasses.setGrade(rs.getString(17));

        return studentsAndClasses;
    }

    static Classes createClass(ResultSet rs) throws SQLException {
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

    static Results createResult(ResultSet rs) throws SQLException {
        Results results = new Results();

        results.setResultsId(rs.getInt(27));
        results.setStudentId(rs.getInt(28));
        results.setAssignmentId(rs.getInt(29));
        results.setScore(rs.getInt(46));
        results.setLate(rs.getBoolean(30));

        return results;
    }

    static Assignments createAssignment(ResultSet rs) throws SQLException {
        Assignments assignment = new Assignments();

        assignment.setAssignmentId(rs.getInt(31));
        assignment.setAssignmentsDescription(rs.getString(32));
        assignment.setClassId(rs.getInt(33));
        assignment.setExam(rs.getBoolean(34));
        assignment.setPercentOfGrade(rs.getFloat(35));
        assignment.setMaximumPoints(rs.getInt(47));

        return assignment;
    }

    static Departments createDepartment(ResultSet rs) throws SQLException {
        Departments department = new Departments();

        department.setDepartmentId(rs.getInt(36));
        department.setDepartmentName(rs.getString(37));
        department.setDepartmentNumber(rs.getInt(38));
        department.setDepartmentManager(rs.getString(39));
        department.setDepartmentChairperson(rs.getString(40));

        return department;
    }

    static Instructors createInstructor(ResultSet rs) throws SQLException {
        Instructors instructor = new Instructors();

        instructor.setInstructorId(rs.getInt(41));
        instructor.setInstructor(rs.getString(42));
        instructor.setEmailName(rs.getString(43));
        instructor.setPhoneNumber(rs.getString(44));
        instructor.setExtension(rs.getString(45));

        return instructor;
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;

                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(MainForm.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MainForm.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MainForm.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MainForm.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new MainForm().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JRadioButton MQButton;
    private javax.swing.JButton SendDataButton;
    private javax.swing.JButton SendSymKey;
    private javax.swing.JRadioButton SocketsButton;
    private javax.swing.ButtonGroup buttonGroup;
    private javax.swing.JButton receivePublicRSA;
    // End of variables declaration//GEN-END:variables
}
