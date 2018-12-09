package com.tw.progs.HandyEnglish.tools;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by VCLERK on 08.04.2017.
 */
public class CipherOps {

    public static String MD5(String input) {
        String result;
        try {
            final MessageDigest md5 = MessageDigest.getInstance("MD5");
            md5.reset();
            final String saltBgn = "|=--->";
            final String saltEnd = "=--->|";
            md5.update(StandardCharsets.UTF_8.encode(saltBgn+input+saltEnd));
            final byte[] resultByte = md5.digest();
            result = new BigInteger(1, resultByte).toString(16);
        } catch (NoSuchAlgorithmException e) {
            result = "";
        }
        return result;
    }
}
