package com.ocean.tools.file;

import cn.hutool.core.io.FileUtil;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.util.List;

/**
 * 视频类文件工具类
 */
public class VideoFileTools {

    public static void renameSelfieVideoOfCityTown(List<File> fileList) {
        if (CollectionUtils.isEmpty(fileList)) {
            return;
        }
        fileList.stream().filter(file -> StringUtils.contains(file.getName(), "#")).forEach(file -> {
            String parent = file.getParent();
            String fileName = file.getName();
            fileName = StringUtils.replace(fileName, "——", "_");

            String prefix = FileUtil.getPrefix(fileName);
            String suffix = FileUtil.getSuffix(fileName);
            String newFileName = null;
            if (StringUtils.contains(prefix, "_")) {
                String[] strs = StringUtils.split(prefix, "_");
                newFileName = StringUtils.join(strs[1], "#", strs[0]) + "." + suffix;
                file.renameTo(new File(parent + "/" + newFileName));
            }
        });
    }

    public static void findRepeatSelfieVideo(String rootPath) {
        List<String> fileNames = FileUtil.listFileNames(rootPath + "/1");

        String sourceFileNames = StringUtils.joinWith(",", fileNames);
        FileUtil.loopFiles(rootPath + "/2").forEach(file -> {
            String fileName = file.getName();
            if (!StringUtils.contains(sourceFileNames, fileName)) {
                System.out.println(fileName);
            }
        });
    }

    public static void main(String[] args) {
        String rootPath = "/Users/ocean/Documents/work/vhall/我的坚果云/person/西棠/全网呼叫崔红旗/";
        //renameSelfieVideoOfCityTown(FileUtil.loopFiles(rootPath));
        findRepeatSelfieVideo(rootPath);
    }
}
