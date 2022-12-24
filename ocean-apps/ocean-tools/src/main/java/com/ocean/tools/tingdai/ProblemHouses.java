package com.ocean.tools.tingdai;

import cn.hutool.poi.excel.ExcelUtil;
import com.alibaba.fastjson.JSON;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description:
 * @Author: yang.zhang
 * @Date: 2022/9/11 22:39
 */
public class ProblemHouses {
    public static void main(String[] args) {
        String filePath = "/Users/ocean/Documents/问题楼盘列表.xlsx";
        List<List<Object>> dataList = ExcelUtil.getReader(filePath).read(1);

//        getMarkList(dataList);
//        getLableList(dataList);
        getPositionList(dataList);
    }

    private static void getMarkList(List<List<Object>> dataList) {
        List<Map<String, Object>> ret = new ArrayList<>();
        for (int i = 0; i < dataList.size(); i++) {
            List<Object> item = dataList.get(i);
            if (item.size() < 8) {
                continue;
            }
            Object jw = item.get(7);
            if (jw == null) {
                continue;
            }
            Map<String, Object> map = new HashMap<>();
            map.put("id", "mark" + i);
            map.put("styleId", "mark");
            map.put("position", "new TMap.LatLng(" + jw + ")AAA");
            ret.add(map);
        }
        System.out.println(JSON.toJSONString(ret));
    }


    private static void getLableList(List<List<Object>> dataList) {
        List<Map<String, Object>> ret = new ArrayList<>();
        for (int i = 0; i < dataList.size(); i++) {
            List<Object> item = dataList.get(i);
            if (item.size() < 8) {
                continue;
            }
            Object jw = item.get(7);
            if (jw == null) {
                continue;
            }
            Map<String, Object> map = new HashMap<>();
            map.put("id", "label" + i);
            map.put("styleId", "label");
            map.put("position", "new TMap.LatLng(" + jw + ")AAA");
            map.put("content", item.get(1));
            ret.add(map);
        }
        System.out.println(JSON.toJSONString(ret));
    }


    private static void getPositionList(List<List<Object>> dataList) {
        String template = "{position: new TMap.LatLng(%s)},";
        for (int i = 0; i < dataList.size(); i++) {
            List<Object> item = dataList.get(i);
            if (item.size() < 8) {
                continue;
            }
            Object jw = item.get(7);
            if (jw == null || StringUtils.isBlank(jw.toString())) {
                continue;
            }
            System.out.println(String.format(template, jw));
        }
    }

}
