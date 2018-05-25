/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.leqienglish.util.verification;

/**
 *
 * @author duyi
 */
public class VerificationResult {

    private VerificationLV verificationLV;

    private String message;

    private Exception ex;

    public VerificationResult(VerificationLV verificationLV) {
        this.verificationLV = verificationLV;
    }

    public VerificationLV getVerificationLV() {
        return verificationLV;
    }

    public void setVerificationLV(VerificationLV verificationLV) {
        this.verificationLV = verificationLV;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Exception getEx() {
        return ex;
    }

    public void setEx(Exception ex) {
        this.ex = ex;
    }

    public enum VerificationLV {
        ERROR, WARNING, OK;
    }
}
