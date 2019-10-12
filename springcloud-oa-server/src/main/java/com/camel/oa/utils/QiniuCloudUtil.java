package com.camel.oa.utils;

import com.camel.oa.config.QiNiuConfig;
import org.springframework.beans.factory.annotation.Autowired;

public class QiniuCloudUtil {

    @Autowired
    private QiNiuConfig qiNiuConfig;

//    public void upload() {
//        qiNiuConfig
//    }

    class Ret {
        public long fsize;
        public String key;
        public String hash;
        public int width;
        public int height;
    }
}
