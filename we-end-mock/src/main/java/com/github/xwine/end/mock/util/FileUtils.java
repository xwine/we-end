package com.github.xwine.end.mock.util;

import com.github.xwine.end.mock.gson.JsonElement;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;

/**
 * @author xwine
 * @date 2020-03-05 19:17
 */
public final class FileUtils {



    public static JsonElement getObjectFromFile(String filePath) {
        String json = "";
        File file = new File(filePath);
        try {
            if (!file.exists()) {
                return null;
            }
            FileInputStream in = new FileInputStream(file);
            int size = in.available();
            byte[] buffer = new byte[size];
            in.read(buffer);
            in.close();
            json = new String(buffer, "UTF-8");
            if (StringUtils.isNotEmpty(json) && (!"null".equals(json))) {
               return JsonUtils.parseJson(json);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void saveJsonToFile(JsonElement jsonObject, String path) {
        FileWriter writer;
        try {
            File file = new File(path);

            if (!file.exists()) {
                file.createNewFile();
            }
            writer = new FileWriter(file);
            //清空原文件内容
            writer.write("");
            writer.write(JsonUtils.beautyJson(jsonObject));
            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
