package com.springapp.accesstomysqlconverter;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import static com.sun.xml.messaging.saaj.util.Base64.base64Decode;
import org.apache.commons.codec.binary.Base64;
 
import javax.crypto.Cipher;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import static org.eclipse.persistence.internal.oxm.conversion.Base64.base64Encode;
 
public class RSACipher {
 
    public String encrypt(String rawText, PublicKey publicKey, String transformation, String encoding)
            throws IOException, GeneralSecurityException {
 
        Cipher cipher = Cipher.getInstance(transformation);
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);
 
        return Base64.encodeBase64String(cipher.doFinal(rawText.getBytes(encoding)));
    }
 
    public String decrypt(String cipherText, PrivateKey privateKey, String transformation, String encoding)
            throws IOException, GeneralSecurityException {
 
        Cipher cipher = Cipher.getInstance(transformation);
        cipher.init(Cipher.DECRYPT_MODE, privateKey);
 
        return new String(cipher.doFinal(Base64.decodeBase64(cipherText)), encoding);
    }
    
    
    public static PrivateKey loadPrivateKey(String key64) throws GeneralSecurityException {
        byte[] privateKeyBytes = base64Decode(key64).getBytes();
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(privateKeyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        PrivateKey priv = keyFactory.generatePrivate(keySpec);
        return priv;
    }

    public static PublicKey loadPublicKey(String stored) throws GeneralSecurityException {
        byte[] data = base64Decode(stored).getBytes();
        X509EncodedKeySpec spec = new X509EncodedKeySpec(data);
        KeyFactory fact = KeyFactory.getInstance("RSA");
        return fact.generatePublic(spec);
    }
    
    public static String savePublicKey(PublicKey publ) throws GeneralSecurityException {
        KeyFactory fact = KeyFactory.getInstance("RSA");
        X509EncodedKeySpec spec = fact.getKeySpec(publ,
                X509EncodedKeySpec.class);
        return new String(base64Encode(spec.getEncoded()));
    }

    public static String savePrivateKey(PrivateKey priv) throws GeneralSecurityException {
        KeyFactory fact = KeyFactory.getInstance("RSA");
        PKCS8EncodedKeySpec spec = fact.getKeySpec(priv,
                PKCS8EncodedKeySpec.class);
        return new String(base64Encode(spec.getEncoded()));
    }
}