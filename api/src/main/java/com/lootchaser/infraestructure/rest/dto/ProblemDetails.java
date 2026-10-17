package com.lootchaser.infraestructure.rest.dto;

import java.util.Map;

public class ProblemDetails {
    public String type;
    public String title;
    public int status;
    public String detail;
    public String instance;
    public Map<String, String> invalidParams;

    public ProblemDetails (String type, String title, int status, String detail, String instance){
        this.type = type;
        this.title = title;
        this.status = status;
        this.detail = detail;
        this.instance = instance;
    }
}
