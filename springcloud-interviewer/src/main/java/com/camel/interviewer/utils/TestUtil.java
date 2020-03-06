package com.camel.interviewer.utils;

import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import java.util.UUID;

public class TestUtil {
    public static void main(String[] args) {
        UUID uuid = UUID.randomUUID();
        System.out.println(uuid);
    }
}
