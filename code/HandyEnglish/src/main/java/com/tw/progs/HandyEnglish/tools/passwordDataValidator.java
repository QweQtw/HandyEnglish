package com.tw.progs.HandyEnglish.tools;

import org.springframework.stereotype.Component;

/**
 * Created by VCLERK on 26.03.2017.
 */
@Component
public class passwordDataValidator {

    public boolean isValidPassword(String email) {
        String ePattern = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).{4,10}$";
        java.util.regex.Pattern p = java.util.regex.Pattern.compile(ePattern);
        java.util.regex.Matcher m = p.matcher(email);
        return m.matches();
    }
}
