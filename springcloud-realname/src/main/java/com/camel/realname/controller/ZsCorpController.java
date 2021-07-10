package com.camel.realname.controller;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.service.IService;
import com.camel.core.controller.BaseCommonController;
import com.camel.core.entity.Result;
import com.camel.core.enums.ResultEnum;
import com.camel.core.utils.ResultUtil;
import com.camel.realname.model.ZsCorp;
import com.camel.realname.service.ZsCorpService;
import com.qiniu.util.IOUtils;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.imageio.IIOException;
import javax.servlet.http.HttpServletResponse;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;

@RestController
@RequestMapping("/zsCorp")
public class ZsCorpController extends BaseCommonController {
    public static final String BUCKET_NAME_URL = "http://image.meedesidy.top";

    @Resource
    private ZsCorpService zsCorpService;

    /**
     * 修改企业认证信息
     * @param zsCorp 修改信息
     * @return Result
     */
    @PostMapping("/change")
    public Result change(@RequestBody ZsCorp zsCorp){
        return zsCorpService.change(zsCorp);
    }

    /**
     * 查询用户绑定的企业认证信息
     * @return Result
     */
    @GetMapping("/getOne")
    public Result getOne(){
        return zsCorpService.getOneByUid();
    }

    /**
     * 上传图片到七牛云
     * @return
     */
    @PostMapping("/upload")
    public Result upload(@RequestParam("file") MultipartFile file, String fileType) throws FileNotFoundException {
        JSONObject object = zsCorpService.upload(file,fileType);
        if(object != null && object.getString("key") != null){
            String url = zsCorpService.getImgUrlByKey(object.getString("key"),fileType);
            HashMap<String, String> map = new HashMap<>();
            map.put("key",object.getString("key"));
            map.put("url",url);
            map.put("id",fileType);
            return ResultUtil.success("上传成功",map);
        }
        return ResultUtil.error(ResultEnum.SERVICE_ERROR.getCode(),"上传失败");
    }

    /**
     * 用于网页展示图片的链接接口
     * @param imgName 文件名
     * @return Result
     */
    @GetMapping("/image/{imgName}")
    public void showImage(@PathVariable("imgName") String imgName,
                          HttpServletResponse response)
            throws FileNotFoundException, IIOException {
        zsCorpService.showImage(imgName, response);
    }

    @Override
    public IService getiService() {
        return zsCorpService;
    }

    @Override
    public String getMouduleName() {
        return "企业实名认证";
    }

    /**
     * 通过请求地址下载文件
     * @param downloadUrl 七牛下载文件地址
     * @return 文件字节数据
     */
    private byte[] downloadFile(String downloadUrl) {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(downloadUrl).build();
        okhttp3.Response response = null;
        try {
            response = client.newCall(request).execute();
            if (response.isSuccessful()) {
                okhttp3.ResponseBody body = response.body();
                InputStream is = body.byteStream();
                return IOUtils.toByteArray(is);
            }
            return null;
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return null;
    }

//    public static void main(String[] args) {
//        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
//        String encode = passwordEncoder.encode("123456");
//        System.out.println(encode);
//    }
}
