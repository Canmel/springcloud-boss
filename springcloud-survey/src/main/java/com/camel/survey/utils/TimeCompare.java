package com.camel.survey.utils;

import com.camel.core.utils.ResultUtil;
import com.camel.survey.model.ZsWorkShift;
import org.apache.poi.ss.formula.functions.T;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 时间对比工具类，先日期，在时间
 * @author Zjr
 */
public class TimeCompare {
    public static List<Integer> dateCompare(Map<Integer,String> indexAndDate){
        List<Integer> indexList = new ArrayList<>();
        Iterator it = indexAndDate.entrySet().iterator() ;
        while (it.hasNext())
        {
            Map.Entry entry = (Map.Entry) it.next() ;
            Integer key = (Integer)entry.getKey() ;
            String value = (String)entry.getValue() ;
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date now = new Date();
            try {
                //比较日期
                Date stardate = dateFormat.parse(value);
                if(stardate.getTime()<now.getTime()){
                    indexList.add(key);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return indexList;
    }


    public static List<Integer> timeCompare(Map<Integer, ZsWorkShift> indexAndTime){
        List<Integer> indexList = new ArrayList<>();
        Iterator it = indexAndTime.entrySet().iterator() ;
        int i = 0;
        while (it.hasNext())
        {
            Map.Entry entry = (Map.Entry) it.next() ;
            Integer key =(Integer) entry.getKey() ;
            ZsWorkShift value = (ZsWorkShift)entry.getValue() ;
            SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
            Date now = new Date();
            long time = 30*60*1000;//30分钟
            String format = timeFormat.format(now);
            try {
                //比较时间
                Date nowtime =  timeFormat.parse(format);
                Date compareTimeS = timeFormat.parse(value.getStartTime()+":00");
                Date compareTimeE = timeFormat.parse(value.getEndTime()+":00");
                //拿当前时间和开始时间作比较
                if(nowtime.getTime()<compareTimeS.getTime()){
                    Date sumTime = new Date(nowtime.getTime()+time);
                    //当前时间小于开始时间，则当前时间加30分钟，继续比较
                    if(sumTime.getTime()>compareTimeS.getTime()){
                        indexList.add(key);
                    }
                }else{
                    //当前时间大于开始时间，则当前时间和结束时间比较
                    if(nowtime.getTime()<compareTimeE.getTime()){
                        indexList.add(key);
                        i++;
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return indexList;
    }
}
