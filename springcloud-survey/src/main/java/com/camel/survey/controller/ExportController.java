package com.camel.survey.controller;

import com.camel.survey.utils.ExportExcelUtils;
import com.camel.survey.vo.Excel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

/**
 *
 *                 ___====-_  _-====___
 *           _--^^^#####//      \\#####^^^--_
 *        _-^##########// (    ) \\##########^-_
 *       -############//  |\^^/|  \\############-
 *     _/############//   (@::@)   \\############\_
 *    /#############((     \\//     ))#############\
 *   -###############\\    (oo)    //###############-
 *  -#################\\  / VV \  //#################-
 * -###################\\/      \//###################-
 *_#/|##########/\######(   /\   )######/\##########|\#_
 *|/ |#/\#/\#/\/  \#/\##\  |  |  /##/\#/  \/\#/\#/\#| \|
 *`  |/  V  V  `   V  \#\| |  | |/#/  V   '  V  V  \|  '
 *   `   `  `      `   / | |  | | \   '      '  '   '
 *                    (  | |  | |  )
 *                   __\ | |  | | /__
 *                  (vvv(VVV)(VVV)vvv)
 * <导出>
 * @author baily
 * @since 1.0
 * @date 2019/12/24
 **/
@RequestMapping("/export")
@RestController
public class ExportController {

    @GetMapping("/survey/total/{id}")
    public void total(@PathVariable Integer id, HttpServletRequest request, HttpServletResponse response) {
        String excelName = "订单详情表";

        String start=request.getParameter("start");
        String end=request.getParameter("end");
        System.out.println("打印的起始日期为："+start+"，打印的结束日期为："+end);
        //得到所有要导出的数据
        List<Excel> orderlist = new ArrayList<>();

        //获取需要转出的excel表头的map字段
        LinkedHashMap<String, String> fieldMap = new LinkedHashMap<>();
        fieldMap.put("id","编号");
        fieldMap.put("link_man","姓名");
        fieldMap.put("amount_real","价格");
        fieldMap.put("date_add","日期");
        fieldMap.put("status_str","订单状态");
        fieldMap.put("mobie","收货电话");
        fieldMap.put("address","地址");
        fieldMap.put("detailValue","订单详情");

        //导出用户相关信息
        ExportExcelUtils.export(excelName,orderlist,fieldMap,response);
    }
}
