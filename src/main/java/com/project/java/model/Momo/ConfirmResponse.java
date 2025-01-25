package com.project.java.model.Momo;

import com.project.java.enums.ConfirmRequestType;

public class ConfirmResponse extends Response {
    private Long amount;
    private Long transId;
    private String requestId;
    private ConfirmRequestType requestType;
}