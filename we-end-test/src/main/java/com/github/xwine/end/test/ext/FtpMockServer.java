package com.github.xwine.end.test.ext;

import com.github.xwine.end.mock.MockContext;
import com.github.xwine.end.spring.spi.MockFileService;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FtpMockServer implements MockFileService {
    private static FTPClient ftp = null;
    private static String ftpHost = "127.0.0.1";
    private static Integer ftpPort = 21;
    private static String userName = "friday";
    private static String password = "20200313";

    private static FTPClient getFtp(String dic) {
        try {
            if (ftp != null && ftp.printWorkingDirectory() == null) {
                try {
                    ftp.logout();
                    ftp.disconnect();
                    ftp = null;
                } catch (Exception e){}
            }
            if (ftp == null) {
                ftp = new FTPClient();
                // 连接FPT服务器,设置IP及端口
                ftp.connect(ftpHost, ftpPort);
                // 设置用户名和密码
                ftp.login(userName, password);
                // 设置连接超时时间,20秒
                ftp.setConnectTimeout(20000);
                // 设置中文编码集，防止中文乱码
                ftp.setControlEncoding("UTF-8");
                if (!FTPReply.isPositiveCompletion(ftp.getReplyCode())) {
                    MockContext.LOG.info("[O-MOCK] can't connect FTP，user password error");
                    ftp.disconnect();
                } else {
                    MockContext.LOG.debug("[O-MOCK] connect FTP success ");
                    //判断FPT目标文件夹时候存在不存在则创建
                    if (!ftp.changeWorkingDirectory(dic)) {
                        ftp.makeDirectory(dic);
                    }
                    //跳转目标目录
                    ftp.changeWorkingDirectory(dic);
                    ftp.enterLocalPassiveMode();
                }
            }
            if (ftp != null) {
                //判断FPT目标文件夹时候存在不存在则创建
                if (!ftp.changeWorkingDirectory(dic)) {
                    ftp.makeDirectory(dic);
                }
                //跳转目标目录
                ftp.changeWorkingDirectory(dic);
            }
        } catch (Exception e) {
            MockContext.LOG.info("[O-MOCK]FtpMockServer connect ftp exception！！！");
        }
        return ftp;
    }


    @Override
    public List<String> fetchDicList() {
        List<String> result = new ArrayList<>();
        try {
            FTPFile[] files = getFtp("/" + MockContext.getConfig().getAppName()).listDirectories();
            for (FTPFile file : files) {
                if (file.isDirectory()) {
                    result.add(file.getName());
                }
            }
        } catch (Exception e) {
            MockContext.LOG.error("[O-MOCK] fetch fileTree exception");
        }
        return result;
    }

    @Override
    public List<String> fetchFiles(String dic) {
        List<String> result = new ArrayList<>();
        try {
            FTPFile[] ftpFiles = getFtp("/" + MockContext.getConfig().getAppName() + "/" + dic).listFiles();
            for (FTPFile ftpFile : ftpFiles) {
                if (ftpFile.isFile() && ftpFile.getName().endsWith(".json")) {
                    result.add(ftpFile.getName());
                }
            }
        } catch (Exception e) {
            MockContext.LOG.error("[O-MOCK] fetch fileTree exception");
        }
        return result;
    }

    @Override
    public boolean uploadFile(String fileName) {
        boolean flag = false;
        InputStream in = null;
        try {
            //设置二进制传输，使用BINARY_FILE_TYPE，ASC容易造成文件损坏
            getFtp("/" + MockContext.getConfig().getAppName() + "/" + MockContext.getConfig().getNowUser()).setFileType(FTPClient.BINARY_FILE_TYPE);
            String fileNamePath = MockContext.getConfig().getPath() + "/" + MockContext.getConfig().getIdCachePath() + "/" + fileName;

            //上传文件
            File file = new File(fileNamePath);
            in = new FileInputStream(file);
            String tempName = file.getName();
            flag = getFtp("/" + MockContext.getConfig().getAppName() + "/" + MockContext.getConfig().getNowUser()).storeFile(new String(tempName.getBytes("GBK"), "iso-8859-1"), in);
            if (flag) {
                MockContext.LOG.debug("[O-MOCK] upload success filename: {}", file.getName());
                flag = true;
            } else {
                MockContext.LOG.error("[O-MOCK] upload fail,filename:{}", file.getName());
            }
        } catch (Exception e) {
            MockContext.LOG.error("[O-MOCK]upload exception");
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return flag;
    }

    @Override
    public boolean uploadString(String dic, String fileName, String dataString) {
        ByteArrayInputStream in = null;
        try {
            byte[] data = dataString.getBytes("UTF-8");
            in = new ByteArrayInputStream(data, 0, data.length);
            boolean flag = getFtp("/" + MockContext.getConfig().getAppName() + "/" + dic).storeFile(new String(fileName.getBytes("GBK"), "iso-8859-1"), in);
            if (flag) {
                MockContext.LOG.debug("[O-MOCK]update success: {}", fileName);
                return true;
            } else {
                MockContext.LOG.error("[O-MOCK] update fail");
            }
        } catch (Exception e) {
            MockContext.LOG.error("[O-MOCK] upload error");
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return false;
    }

    @Override
    public boolean downloadFile(String dic, String fileName) {
        OutputStream out = null;
        try {
            File rootFile = new File(MockContext.getConfig().getPath() + "/" + MockContext.getConfig().getIdCachePath());
            if (!rootFile.exists()) {
                rootFile.mkdirs();
            }
            // 取得指定文件并下载
            File downFile = new File(MockContext.getConfig().getPath() + "/" + MockContext.getConfig().getIdCachePath() + "/"
                    + fileName);
            out = new FileOutputStream(downFile);
            // 绑定输出流下载文件,需要设置编码集，不然可能出现文件为空的情况
            boolean flag = getFtp("/" + MockContext.getConfig().getAppName() + "/" + dic).retrieveFile(new String(fileName.getBytes("GBK"), "iso-8859-1"), out);
            out.flush();
            if (flag) {
                MockContext.LOG.debug("[O-MOCK] download success: filename：{}", fileName);
                return true;
            } else {
                MockContext.LOG.error("[O-MOCK] download fail");
            }
        } catch (Exception e) {
            MockContext.LOG.error("[O-MOCK] download file exception");
        } finally {
            if (out != null) {
                try {
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return false;
    }

    @Override
    public String fetchFileContent(String dic, String fileName) {
        InputStream in = null;
        try {
            if (this.exist(dic, fileName)) {
                in = getFtp("/" + MockContext.getConfig().getAppName() + "/" + dic).retrieveFileStream(new String(fileName.getBytes("GBK"), "iso-8859-1"));
                if (in.available() < 1) {
                    Thread.sleep(1000);
                }
                if (in.available() < 1) {
                    Thread.sleep(2000);
                }
                if (in.available() < 1) {
                    Thread.sleep(3000);
                }
                int size = in.available();
                byte[] buffer = new byte[size];
                in.read(buffer);
                return new String(buffer, "UTF-8");
            }
        } catch (Exception e) {
            MockContext.LOG.error("[O-MOCK] fetch file content exception");
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


    /**
     * 判断文件是否存在
     *
     * @param fileName
     * @return
     */
    private boolean exist(String dic, String fileName) {
        try {
            FTPFile[] files = getFtp("/" + MockContext.getConfig().getAppName() + "/" + dic).listFiles();
            for (FTPFile file : files) {
                if (file.getName().equals(fileName)) {
                    return true;
                }
            }
            MockContext.LOG.info("[O-MOCK] file:{} not exsist", fileName);
        } catch (Exception e) {
            MockContext.LOG.error("[O-MOCK] file exsist exception: {}", fileName);
        }
        return false;
    }
}
