package com.github.xwine.end.spring.util;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FileUtil {

    public static byte[] readByteArrayFromResource(String filePath) throws IOException {

        InputStream in = FileUtil.class.getResourceAsStream(filePath);

        ByteArrayOutputStream bos = new ByteArrayOutputStream();

        try {
            in = new BufferedInputStream(in);
            int buf_size = 1024;
            byte[] buffer = new byte[buf_size];
            int len = 0;
            while (-1 != (len = in.read(buffer, 0, buf_size))) {
                bos.write(buffer, 0, len);
            }
            return bos.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
            throw e;
        } finally {
            try {
                in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            bos.close();
        }
    }

    public static List<String>  getAllFileNames(String dic) {
        List<String> list = new ArrayList<String>();
        File file = new File(dic);
        if (file.isDirectory()) {
            String[] files = file.list();
            list = Arrays.asList(files);
        }
        return list;
    }

    /**
     * 返回所有文件List
     * @param dic
     * @return
     */
    public static List<File> getAllFiles(String dic) {
        List<File> list = new ArrayList<File>();
        File file = new File(dic);
        if (file.isDirectory()) {
            File[] files = file.listFiles();
            list = Arrays.asList(files);
        }
        return list;
    }

    public static String getDataFromFile(String filePath) {
        String json = "";
        FileInputStream in = null;
        File file = new File(filePath);
        try {
            if (!file.exists()) {
                return null;
            }
            in = new FileInputStream(file);
            int size = in.available();
            byte[] buffer = new byte[size];
            in.read(buffer);
            json = new String(buffer, "UTF-8");
            return json;
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

}
