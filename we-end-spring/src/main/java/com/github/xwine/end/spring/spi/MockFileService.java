package com.github.xwine.end.spring.spi;

import java.util.List;

public interface MockFileService {

    /**
     * 获取远程目录
     * @return
     */
    List<String> fetchDicList();

    /**
     * 获取目录下所有文件
     * @param dic
     * @return
     */
    List<String> fetchFiles(String dic);

    /**
     * 上传文件
     * @param fileName
     * @return
     */
    boolean uploadFile(String fileName);


    boolean uploadString(String dic, String fileName, String dataString);

    boolean downloadFile(String dic, String fileName);

    String fetchFileContent(String dic, String fileName);
}
