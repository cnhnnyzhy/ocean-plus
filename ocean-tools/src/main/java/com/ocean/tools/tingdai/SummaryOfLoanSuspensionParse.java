package com.ocean.tools.tingdai;

import cn.hutool.core.io.FileUtil;
import cn.hutool.http.HttpUtil;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.google.common.collect.Lists;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * @Description: 停贷信息解析
 * @Author: yang.zhang
 * @Date: 2022/7/14 16:47
 */
public class SummaryOfLoanSuspensionParse {
    private static final String LEFT_BRACKET_ZH = "（";
    private static final String RIGHT_BRACKET_ZH = "）";
    private static final String LEFT_BRACKET_EN = "(";
    private static final String RIGHT_BRACKET_EN = ")";

    private static final String JSON_FILE_PATH = "/Users/ocean/Documents/temp/tingdai/全国各省市烂尾楼停贷通知汇总.json";


    private static List<String> NOT_STOP_TIME_PROJECTS = Lists.newArrayList("君悦花园（知音湖院子）", "奥园悦城（汇景园）");

    private static final Map<String, List<String>> SPECIFIC_PROJECTS = new HashMap<String, List<String>>() {
        {
            put("恒大童世界四号地（廊桥水乡）（9月）", Lists.newArrayList("恒大童世界四号地（廊桥水乡）", "9月"));
            put("恒大书香门第15", Lists.newArrayList("恒大书香门第15、16栋", ""));
            put("16栋", null);
        }

    };


    public static void main(String[] args) throws IOException {
        String url = "https://github.com/WeNeedHome/SummaryOfLoanSuspension/blob/main/README.md";
        saveResult(filterExistingData(parse(url)));
    }

    private static String getContent(String url) {
        return HttpUtil.get(url);
    }

    private static Map<String, Map<String, List<ProjectInfo>>> parse(String url) throws IOException {
        Document document = Jsoup.parse(new URL(url), 300000);
        Elements elements = document.getElementsByTag("h2");
        Map<String, Map<String, List<ProjectInfo>>> dataMap = new HashMap<>(64);
        elements.forEach(element -> {
            String provinceText = element.text();
            if (StringUtils.equalsAnyIgnoreCase(provinceText, notTargetData())) {
                return;
            }
            String province = StringUtils.substringBefore(StringUtils.replace(provinceText, " ", ""), "[");
            Map<String, List<ProjectInfo>> cityMap = dataMap.get(province);
            if (Objects.isNull(cityMap)) {
                cityMap = new HashMap<>(32);
                dataMap.put(province, cityMap);
            }
            Element ul = element.nextElementSibling();
            Elements liElements = ul.getElementsByTag("li");
            for (Element li : liElements) {
                String[] liText = StringUtils.replaceEach(li.text(), new String[]{" ", "：", "，", LEFT_BRACKET_EN, RIGHT_BRACKET_EN}, new String[]{"", ":", ",", LEFT_BRACKET_ZH, RIGHT_BRACKET_ZH}).split(":");
                String city = StringUtils.substringBefore(liText[0], LEFT_BRACKET_ZH);

                String[] projects = liText[1].split(",");
                List<ProjectInfo> projectList = Optional.ofNullable(cityMap.get(city)).orElse(new ArrayList<>(32));
                Set<String> projectSet = new HashSet<>();
                Arrays.stream(projects).forEach(project -> {
                    ProjectInfo projectInfo = buildProjectInfo(province, city, project);
                    if (Objects.isNull(projectInfo) || projectSet.contains(projectInfo.getName())) {
                        return;
                    }
                    projectList.add(projectInfo);
                    projectSet.add(projectInfo.getName());
                });
                if (!cityMap.containsKey(city)) {
                    cityMap.put(city, projectList);
                }
            }
        });
        return dataMap;
    }

    private static ProjectInfo buildProjectInfo(String province, String city, String project) {
        if (StringUtils.startsWith(project, LEFT_BRACKET_ZH)) {
            return null;
        }
        LocalDateTime now = LocalDateTime.now();
        String stopTime = "";
        if (SPECIFIC_PROJECTS.containsKey(project)) {
            List<String> list = SPECIFIC_PROJECTS.get(project);
            if (CollectionUtils.isNotEmpty(list)) {
                project = list.get(0);
                stopTime = list.get(1);
            } else {
                return null;
            }
        } else if (StringUtils.contains(project, LEFT_BRACKET_ZH) && !NOT_STOP_TIME_PROJECTS.contains(project)) {
            String[] strs = StringUtils.split(project, LEFT_BRACKET_ZH);
            project = strs[0];
            stopTime = StringUtils.replace(strs[1], RIGHT_BRACKET_ZH, "");
        }
        if (StringUtils.isNotBlank(stopTime)) {
            if (!StringUtils.contains(stopTime, "月")) {
                stopTime = stopTime + "月";
            }
            if (!StringUtils.contains(stopTime, "年")) {
                stopTime = now.getYear() + "年" + stopTime;
            }
        }
        return new ProjectInfo(project, "", stopTime, city, province, now.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
    }

    private static List<ProjectInfo> filterExistingData(Map<String, Map<String, List<ProjectInfo>>> dataMap) {
        if (dataMap == null || dataMap.isEmpty()) {
            return null;
        }
        Set<String> existingData = getExistingData();
        List<ProjectInfo> list = new ArrayList<>();
        dataMap.forEach((province, cityMap) -> {
            cityMap.forEach((city, projectList) -> {
                projectList.forEach(project -> {
                    if (CollectionUtils.isNotEmpty(existingData) && existingData.contains(buildUniqueKey(project))) {
                        return;
                    }
                    list.add(project);
                });
            });
        });
        return list;
    }

    private static String buildUniqueKey(ProjectInfo projectInfo) {
        return projectInfo.getProvince() + "#" + projectInfo.getCity() + "#" + projectInfo.getName();
    }

    private static Set<String> getExistingData() {
        File file = new File(JSON_FILE_PATH);
        if (!file.exists()) {
            return null;
        }
        Set<String> set = new HashSet<>();
        FileUtil.readUtf8Lines(file).forEach(line -> {
            if (StringUtils.isBlank(line)) {
                return;
            }
            ProjectInfo projectInfo = JSON.parseObject(line, new TypeReference<ProjectInfo>() {
            });
            if (projectInfo == null) {
                return;
            }
            set.add(buildUniqueKey(projectInfo));
        });
        return set;
    }

    private static void saveResult(List<ProjectInfo> projectInfoList) throws IOException {
        File file = new File(JSON_FILE_PATH);
        if (!file.exists()) {
            file.createNewFile();
        }
        List<String> lines = new ArrayList<>();
        ExcelWriter writer = ExcelUtil.getWriter("/Users/ocean/Documents/temp/tingdai/全国各省市烂尾楼停贷通知汇总" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss")) + ".xlsx");
        writer.writeHeadRow(Lists.newArrayList("省份", "城市", "项目名称", "通知日期", "停贷时间"));
        projectInfoList.forEach(project -> {
            writer.writeRow(Lists.newArrayList(project.getProvince(), project.getCity(), project.getName(), project.getNoticeDate(), project.getExecuteDate()));
            lines.add(JSON.toJSONString(project));
        });
        writer.flush();
        FileUtil.appendUtf8Lines(lines, file);
    }

    private static String[] notTargetData() {
        return new String[]{"其他数据公示处", "SummaryOfLoanSuspension/README.md", "Footer"};
    }
}
