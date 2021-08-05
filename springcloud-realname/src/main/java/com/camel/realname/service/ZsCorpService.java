package com.camel.realname.service;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.service.IService;
import com.camel.core.entity.Result;
import com.camel.realname.model.ZsCorp;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.IIOException;
import javax.servlet.http.HttpServletResponse;
import java.io.FileNotFoundException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

public interface ZsCorpService extends IService<ZsCorp> {

    /**
     * 上传文件到犀牛云
     * @param file 上传文件
     * @param imgName 文件类型名称
     * @return 返回JSONObject里面获取key
     */
    JSONObject upload(MultipartFile file,String imgName);

    /**
     * 改变企业认证信息
     * @param zsCorp 认证信息
     * @return Result
     */
    Result change(ZsCorp zsCorp);

    /**
     * 用于页面访问图片
     * @param userId 用户id
     * @param imgName 图片类型名称
     * @param response response
     */
    void showImage(Integer userId,String imgName,HttpServletResponse response) throws FileNotFoundException, IIOException;

    /**
     * 根据绑定的用户id，获取存储图片的key
     * @param userId 用户id
     * @param imgName 图片类型名称
     * @return 图片访问url
     */
    String getImageAddr(Integer userId,String imgName) throws FileNotFoundException;

    /**
     * 用于上传时返回url，此时数据库中没有key
     * @param userId 用户id
     * @param key key
     * @param imgName 图片类型名称
     * @return url
     */
    String getImgUrlByKey(String key,String imgName);

    /**
     * 根据用户id查询企业实名认证信息
     * @return Result
     */
    Result getOneByUid() throws InvocationTargetException, FileNotFoundException, IllegalAccessException, NoSuchMethodException;

    /**
     *  查询所有企业认证信息(status > 1)
     *  @param zsCorp 查询条件
     * @return
     */
    List<ZsCorp> getList(ZsCorp zsCorp) throws InvocationTargetException, FileNotFoundException, IllegalAccessException, NoSuchMethodException;

    /**
     * 审核改变状态
     * @param zsCorp 企业认证信息
     * @return Result
     */
    Result audit(ZsCorp zsCorp);

}
