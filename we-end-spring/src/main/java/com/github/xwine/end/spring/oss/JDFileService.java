package com.github.xwine.end.spring.oss;

import java.util.ArrayList;
import java.util.List;

public class JDFileService {


    private JDFileService(){}


    public static List<String> fetchDicList() {
        return new ArrayList<>();
    }

    public static List<String> fetchFiles(String dic) {
        List<String> result = new ArrayList<>();
        return result;
    }

    public static boolean uploadFile(String fileName) {

        return false;
    }


    public static boolean uploadString(String dic,String fileName, String dataString) {
        return false;
    }

    public static boolean downloadFile(String dic,String fileName) {
        return false;
    }
    
    public static String fetchFileContent(String dic,String fileName) {
        return null;
    }


    public static void main(String[] args) {

        System.out.println("上传结果:" + uploadFile("fetch.json"));

        for (String s : fetchDicList()) {
            System.out.println(s);
        }

        for (String c : fetchFiles("xwine/")) {
            System.out.println(c);
        }

        System.out.println("下载结果：" + downloadFile("xwine","t1.json"));

        System.out.println("文件内容："+fetchFileContent("xwine","t1.json"));

        System.out.println("上传文件内容:"+uploadString("test/","ceshi.json","hello"));
    }
}
