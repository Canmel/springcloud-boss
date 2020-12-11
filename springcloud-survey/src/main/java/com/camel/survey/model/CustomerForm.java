package com.camel.survey.model;

import lombok.Data;

@Data
public class CustomerForm extends Customer {
    private String createdAtStart;

    private String createdAtEnd;
}
