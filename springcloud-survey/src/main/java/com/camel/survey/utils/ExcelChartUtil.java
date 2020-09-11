//package com.camel.survey.utils;
//
//import org.jfree.chart.ChartFactory;
//import org.jfree.chart.JFreeChart;
//import org.jfree.chart.labels.StandardPieSectionLabelGenerator;
//import org.jfree.chart.plot.CategoryPlot;
//import org.jfree.chart.plot.PiePlot;
//import org.jfree.chart.plot.PlotOrientation;
//import org.jfree.data.category.DefaultCategoryDataset;
//import org.jfree.data.general.DefaultPieDataset;
//
//import java.awt.*;
//import java.util.HashMap;
//import java.util.Iterator;
//import java.util.Map;
//import java.util.Set;
//
///**
// * excel图表工具
// */
//public class ExcelChartUtil {
//
//    public static JFreeChart createBar(String title,Map<String,Map<String,Double>> datas,String type,String danwei,Font font){
//        try {
//            //种类数据集
//            DefaultCategoryDataset ds = new DefaultCategoryDataset();
//
//
//            //获取迭代器：
//            Set<Map.Entry<String, Map<String, Double>>> set1 =  datas.entrySet();
//            Iterator iterator1= set1.iterator();
//            Iterator iterator2;
//            HashMap<String, Double> map;
//            Set<Map.Entry<String,Double>> set2;
//            Map.Entry entry1;
//            Map.Entry entry2;
//
//            while(iterator1.hasNext()){
//                entry1=(Map.Entry) iterator1.next();
//
//                map=(HashMap<String, Double>) entry1.getValue();
//                set2=map.entrySet();
//                iterator2=set2.iterator();
//                while (iterator2.hasNext()) {
//                    entry2= (Map.Entry) iterator2.next();
//                    ds.setValue(Double.parseDouble(entry2.getValue().toString()), entry2.getKey().toString(), entry1.getKey().toString());
//                }
//            }
//
//            //创建柱状图,柱状图分水平显示和垂直显示两种
//            JFreeChart chart = ChartFactory.createBarChart(title, type, danwei, ds, PlotOrientation.VERTICAL, true, true, true);
//
//            //设置整个图片的标题字体
//            chart.getTitle().setFont(font);
//
//            //设置提示条字体
//            font = new Font("宋体", Font.BOLD, 15);
//            chart.getLegend().setItemFont(font);
//
//            //得到绘图区
//            CategoryPlot plot = (CategoryPlot) chart.getPlot();
//            //得到绘图区的域轴(横轴),设置标签的字体
//            plot.getDomainAxis().setLabelFont(font);
//
//            //设置横轴标签项字体
//            plot.getDomainAxis().setTickLabelFont(font);
//
//            //设置范围轴(纵轴)字体
//            plot.getRangeAxis().setLabelFont(font);
//
//            plot.setForegroundAlpha(1.0f);
//            return chart;
//        } catch (Exception e) {
//            e.printStackTrace();
//            return null;
//        }
//    }
//
//    public static JFreeChart createLine(String title, Map<String, Map<String, Double>> datas, String type, String unit, Font font) {
//        try {
//            DefaultCategoryDataset ds = new DefaultCategoryDataset();
//            Set<Map.Entry<String, Map<String, Double>>> set1 = datas.entrySet();
//            Iterator iterator1 = set1.iterator();
//            Iterator iterator2;
//            HashMap<String, Double> map;
//            Set<Map.Entry<String, Double>> set2;
//            Map.Entry entry1;
//            Map.Entry entry2;
//            while (iterator1.hasNext()) {
//                entry1 = (Map.Entry) iterator1.next();
//                map = (HashMap<String, Double>) entry1.getValue();
//                set2 = map.entrySet();
//                iterator2 = set2.iterator();
//                while (iterator2.hasNext()) {
//                    entry2 = (Map.Entry) iterator2.next();
//                    ds.setValue(Double.parseDouble(entry2.getValue().toString()), entry2.getKey().toString(), entry1.getKey().toString());
//                }
//            }
//
//            //创建折线图,折线图分水平显示和垂直显示两种
//            // //2D折线图
//            JFreeChart chart = ChartFactory.createLineChart(title, type, unit, ds, PlotOrientation.VERTICAL, true, true, true);
//            // //3D折线图
////            JFreeChart chart2 = ChartFactory.createLineChart3D(title, type, unit, ds, PlotOrientation.VERTICAL, true, true, false);
//
//            //设置整个图片的标题字体
//            chart.getTitle().setFont(font);
//
//            //设置提示条字体
//            font = new Font("宋体", Font.BOLD, 15);
//            chart.getLegend().setItemFont(font);
//
//            //得到绘图区
//            CategoryPlot plot = (CategoryPlot) chart.getPlot();
//            //得到绘图区的域轴(横轴),设置标签的字体
//            plot.getDomainAxis().setLabelFont(font);
//
//            //设置横轴标签项字体
//            plot.getDomainAxis().setTickLabelFont(font);
//
//            //设置范围轴(纵轴)字体
//            font = new Font("宋体", Font.BOLD, 18);
//            plot.getRangeAxis().setLabelFont(font);
////            plot.setForegroundAlpha(1.0f);
//            return chart;
//        } catch (Exception e) {
//            e.printStackTrace();
//            return null;
//        }
//    }
//
//    public static JFreeChart createPie(String title, Map<String, Double> datas, Font font) {
//        try {
//            Set<Map.Entry<String, Double>> set = datas.entrySet();
//            DefaultPieDataset pds = new DefaultPieDataset();
//            Iterator iterator = set.iterator();
//            Map.Entry entry;
//            while (iterator.hasNext()) {
//                entry = (Map.Entry) iterator.next();
//                pds.setValue(entry.getKey().toString(), Double.parseDouble(entry.getValue().toString()));
//            }
//            // 生成一个饼图的图表：显示图表的标题、组装的数据、是否显示图例、是否生成贴士以及是否生成URL链接
//            JFreeChart chart = ChartFactory.createPieChart(title, pds, true, false, true);
//            // 设置图片标题的字体
//            chart.getTitle().setFont(font);
//            // 得到图块,准备设置标签的字体
//            PiePlot plot = (PiePlot) chart.getPlot();
//            //设置分裂效果,需要指定分裂出去的key
////            plot.setExplodePercent("摄像机", 0.1);  分裂效果，可选
//            // 设置标签字体
//            plot.setLabelFont(font);
//            // 设置图例项目字体
//            chart.getLegend().setItemFont(font);
//            // 设置开始角度
////            plot.setStartAngle(new Float(3.14f / 2f));  开始角度，意义不大
//            //设置plot的前景色透明度
//            plot.setForegroundAlpha(0.7f);
//            //设置plot的背景色透明度
//            plot.setBackgroundAlpha(0.0f);
//            //设置标签生成器(默认{0})
//            //{0}:key {1}:value {2}:百分比 {3}:sum
//            plot.setLabelGenerator(new StandardPieSectionLabelGenerator("{0}({1})/{2}"));  // 一般在{1}后面加单位，如：{0}({1}次)/{2}
//            //将内存中的图片写到本地硬盘
////            ChartUtilities.saveChartAsJPEG(new File("H:/a.png"), chart, 600, 300);
//            return chart;
//        } catch (Exception e) {
//            e.printStackTrace();
//            return null;
//        }
//    }
//}
