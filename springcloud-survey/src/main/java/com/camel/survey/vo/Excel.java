package com.camel.survey.vo;

import lombok.Data;

@Data
public class Excel {
    private Integer id;
    private String order_number;
    private String amount_real;
    private String date_add;
    private String status_str;
    private String link_man;
    private String mobie;
    private String address;
    private String detailValue;

    public Excel() {

    }

    public Excel(Integer id, String order_number, String amount_real, String date_add, String status_str, String link_man, String mobie, String address, String detailValue) {
        this.id = id;
        this.order_number = order_number;
        this.amount_real = amount_real;
        this.date_add = date_add;
        this.status_str = status_str;
        this.link_man = link_man;
        this.mobie = mobie;
        this.address = address;
        this.detailValue = detailValue;
    }
}
