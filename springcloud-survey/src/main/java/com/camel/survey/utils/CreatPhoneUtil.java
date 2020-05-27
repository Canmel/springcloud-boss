package com.camel.survey.utils;

import java.util.TreeSet;

public class CreatPhoneUtil {

    private static int getNum(int start,int end) {
        int a = (int)(Math.random()*(end-start+1)+start);
        return a;
    }
    /**
     * 返回手机号码
     */
    private static String[] telFirst=("134,135,136,137,138,139,147,150,151,152,157,158,159,172,178,182," +
            "183,184,187,188,195,197,198,130,131,132,145,155,156,166,175,176,185,186,196,133,149," +
            "153,180,181,189,173,177,190,191,193,199").split(",");
    private static String getTel() {
        int index=getNum(0,telFirst.length-1);
        String first=telFirst[index];
        String second=String.valueOf(getNum(0,9999)+10000).substring(1);
        String third=String.valueOf(getNum(0,9999)+10000).substring(1);
        return first+second+third;
    }

    public static TreeSet getBatchTel(int i){
        TreeSet telSet = new TreeSet();

        for (int j = 0; j < i; j++) {
            telSet.add(getTel());
        }
        return telSet;
    }

    public static void main(String[] args) {
        int i = 100;
        TreeSet batchTel = getBatchTel(i);
        while(batchTel.size()<100){
            batchTel.addAll(getBatchTel(i - batchTel.size()));
        }
        System.out.println(batchTel);
        }
    }

