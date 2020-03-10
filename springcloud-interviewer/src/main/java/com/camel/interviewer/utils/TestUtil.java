package com.camel.interviewer.utils;

import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import java.util.Random;
import java.util.UUID;

public class TestUtil {
    public static void main(String[] args) {
        System.out.println(String.format("%04d",new Random().nextInt(9999)));
    }
}
