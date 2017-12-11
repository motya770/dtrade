package com.dtrade.utils;

import java.nio.file.Files;
import java.nio.file.Paths;

public class TestUtils {

    public void test(){

    }

    public static void main(String... args) throws  Exception{

        sun.security.x509.X500Name x500Name = new  sun.security.x509.X500Name("One", "Two", "Three", "Four", "Five", "Six");

        try{
//            CertAndKeyGen certAndKeyGen = new CertAndKeyGen("RSA", "MD5WithRSA");
//            certAndKeyGen.generate(1024);
//            certAndKeyGen.getSelfCertificate(x500Name, 20000);
//
//            Class<?> clazz = Class.forName("sun.security.tools.keytool.CertAndKeyGen");
//            Constructor<?> constructor =  clazz.getConstructor(String.class, String.class);
//            Object object = constructor.newInstance("RSA", "MD5WithRSA");
//            Method method =clazz.getMethod("generate", int.class);
//            method.invoke(object, 1024);
//
//            Method getSert = clazz.getMethod("getSelfCertificate", x500Name.getClass(), long.class);
//            X509Certificate certificate = (X509Certificate) getSert.invoke(object, x500Name, 200000l);


            Files.write(Paths.get("/Users/kudelin/Desktop/work/work/dtrade/src/main/resources/test.txt"), "test".getBytes());
        }catch (Exception e){
            e.printStackTrace();
        }

    }
}
