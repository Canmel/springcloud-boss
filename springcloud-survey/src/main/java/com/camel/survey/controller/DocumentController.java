package com.camel.survey.controller;

import com.baomidou.mybatisplus.service.IService;
import com.camel.core.controller.BaseCommonController;
import com.camel.core.entity.Result;
import com.camel.core.enums.ResultEnum;
import com.camel.core.utils.ResultUtil;
import com.camel.survey.model.Document;
import com.camel.survey.service.DocumentService;
import com.qiniu.common.QiniuException;
import com.qiniu.util.IOUtils;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.Principal;

/**
 * 　　　　　　　 ┏┓　　　┏┓
 * 　　　　　　　┏┛┻━━━━━┛┻┓
 * 　　　　　　　┃         ┃ 　
 * 　　　　　　　┃    ━    ┃
 * 　　　　　　　┃  >   <  ┃
 * 　　　　　　　┃         ┃
 * 　　　　　　　┃... ⌒ ...┃
 * 　　　　　　　┃         ┃
 *             ┗━┓     ┏━┛
 *               ┃     ┃　Code is far away from bug with the animal protecting　　　　　　　　　　
 *               ┃     ┃   神兽保佑,代码无bug
 *               ┃     ┃　　　　　　　　　　　
 *               ┃     ┃  　　　　　　
 *               ┃     ┃        < 前端控制器>
 *               ┃     ┃　　　　　　　　　　　
 *               ┃     ┗━━━━┓   @author baily
 *               ┃          ┣┓
 *               ┃          ┏┛  @since 1.0
 *               ┗┓┓┏━━━━┳┓┏┛
 *                ┃┫┫    ┃┫┫    @date 2019-12-11
 *                ┗┻┛    ┗┻┛
 */
@RestController
@RequestMapping("/document")
public class DocumentController extends BaseCommonController {


    @Autowired
    private DocumentService service;

    /**
     * 获取分页列表
     * @param document
     * @returnsdf
     */
    @GetMapping
    public Result index(Document document) {
        return ResultUtil.success(service.selectPage(document));
    }

    /**
     * 上传文件
     * @param file
     * @return
     */
    @PostMapping
    public Result upload(@RequestParam("file") MultipartFile file, Principal principal) {
        OAuth2Authentication authentication = (OAuth2Authentication) principal;
        return ResultUtil.success(service.save(file, authentication));
    }

    /**
     * 获取预览地址
     * @param id
     * @return 获取下载地址
     */
    @GetMapping("/view/{id}")
    public Result view(@PathVariable Integer id) {
        try {
            return ResultUtil.success((Object) service.url(id));
        } catch (FileNotFoundException e) {
            return ResultUtil.error(ResultEnum.RESOURCESNOTFOUND);
        }
    }

    /**
     * 下载所需文件
     * @param id
     * @param response
     * @throws Exception
     */
    @GetMapping("/download/{id}")
    public void download(@PathVariable Integer id, HttpServletResponse response) throws Exception {
        Document document = service.selectById(id);
        if (ObjectUtils.isEmpty(document)) {
            throw new RuntimeException("找不到文件");
        }
        response.reset();
        response = setHeader(response, document.getDname());
        OutputStream output;
        String url = service.url(id);
        byte[] downloadFileByte = downloadFile(url);
        output = response.getOutputStream();
        output.write(downloadFileByte);
        output.flush();
        output.close();
    }

    /**
     * 删除文档
     * @param id
     * @return
     */
    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Integer id) throws QiniuException {
        return super.delete(id);
    }

    @GetMapping("/{id}")
    public Result url(@PathVariable("id") Integer id) throws FileNotFoundException {
        return ResultUtil.success((Object) service.url(id));
    }

    /**
     * 重设response
     *
     * @param response
     * @param fileName
     * @return
     */
    private HttpServletResponse setHeader(HttpServletResponse response, String fileName) {
        response.setHeader("Content-Disposition", "attachment;filename=" + fileName);
        response.setContentType("application;charset=UTF-8");
        response.setHeader("Pragma", "no-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expires", 0);
        return response;
    }

    /**
     * 通过请求地址下载文件
     * @param downloadUrl 七牛下载文件地址
     * @return 文件字节数据
     */
    private byte[] downloadFile(String downloadUrl) {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(downloadUrl).build();
        Response response = null;
        try {
            response = client.newCall(request).execute();
            if (response.isSuccessful()) {
                ResponseBody body = response.body();
                InputStream is = body.byteStream();
                return IOUtils.toByteArray(is);
            }
            return null;
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    @Override
    public IService getiService() {
        return service;
    }

    @Override
    public String getMouduleName() {
        return "文档";
    }

}