package com.github.xwine.end.spring.util;

import com.github.xwine.end.mock.MockContext;
import com.github.xwine.end.mock.util.StringUtils;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author xwine
 * @date 2020-03-14 09:01
 */
public class FtpClientTools {
    public FTPClient ftp = null;
    private String remotePath = "/";
    public String remoteServer = null;

    /**
     * 获取FTPClient对象
     *
     * @param ftpHost     服务器IP
     * @param ftpPort     服务器端口号
     * @param ftpUserName 用户名
     * @param ftpPassword 密码
     * @return FTPClient
     */
    private FtpClientTools(String ftpHost, int ftpPort,
                          String ftpUserName, String ftpPassword, String remotePath) {
        try {
            this.remotePath = remotePath;
            ftp = new FTPClient();
            // 连接FPT服务器,设置IP及端口
            ftp.connect(ftpHost, ftpPort);
            // 设置用户名和密码
            ftp.login(ftpUserName, ftpPassword);
            // 设置连接超时时间,20秒
            ftp.setConnectTimeout(20000);
            // 设置中文编码集，防止中文乱码
            ftp.setControlEncoding("UTF-8");
            if (!FTPReply.isPositiveCompletion(ftp.getReplyCode())) {
                MockContext.LOG.info("[O-MOCK] can't connect FTP，user password error");
                ftp.disconnect();
            } else {
                MockContext.LOG.debug("[O-MOCK] connect FTP success ");
                this.remoteServer = ftpHost;
                //判断FPT目标文件夹时候存在不存在则创建
                if (!ftp.changeWorkingDirectory(remotePath)) {
                    ftp.makeDirectory(remotePath);
                }
                //跳转目标目录
                ftp.changeWorkingDirectory(remotePath);
                ftp.enterLocalPassiveMode();
            }
        } catch (Exception e) {
            MockContext.LOG.info("[O-MOCK]FTP connect exception！！！");
            closeFTP();
        }
    }

    /**
     * 关闭FTP方法
     *
     * @return
     */
    public void closeFTP() {
        try {
            if (ftp != null) {
                ftp.logout();
                MockContext.LOG.debug("[O-MOCK] close FTP success");
            }
        } catch (Exception e) {
            MockContext.LOG.error("[O-MOCK]close FTP fail");
        } finally {
            if (ftp != null && ftp.isConnected()) {
                try {
                    ftp.disconnect();
                } catch (IOException ioe) {
                    MockContext.LOG.error("[O-MOCK]close FTP fail");
                }
            }
        }
    }

    public void downloadAll(String localPath, boolean over) {
        try {
            FTPFile[] files = ftp.listFiles();
            for (FTPFile file : files) {
                if (file.isFile()) {
                    this.downLoadFile(file.getName(), localPath, over);
                }
            }
        } catch (Exception e) {
            MockContext.LOG.error("[O-MOCK] download from remote exception");
        }
    }


    public void downLoadFile(String fileName, String localPath, boolean over) {
        OutputStream out = null;
        try {
            File rootFile = new File(localPath);
            if (!rootFile.exists()) {
                rootFile.mkdirs();
            }
            // 取得指定文件并下载
            File downFile = new File(localPath + "/"
                    + fileName);
            //本地有的话不再下载
            if (downFile.exists() && !over) {
                return;
            }
            out = new FileOutputStream(downFile);
            // 绑定输出流下载文件,需要设置编码集，不然可能出现文件为空的情况
            boolean flag = ftp.retrieveFile(new String(fileName.getBytes("GBK"),"iso-8859-1"), out);
            out.flush();
            if (flag) {
                MockContext.LOG.debug("[O-MOCK] download success: filename：{}", fileName);
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
    }

    public boolean upload(String fileNamePath) {
        boolean flag = false;
        InputStream in = null;
        try {
            //设置二进制传输，使用BINARY_FILE_TYPE，ASC容易造成文件损坏
            ftp.setFileType(FTPClient.BINARY_FILE_TYPE);

            //上传文件
            File file = new File(fileNamePath);
            in = new FileInputStream(file);
            String tempName = file.getName();
            flag = ftp.storeFile(new String(tempName.getBytes("GBK"),"iso-8859-1"), in);
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

    public boolean uploadByDic(String dic,File[] files) throws IOException {
        String origin = ftp.printWorkingDirectory();
        //判断FPT目标文件夹时候存在不存在则创建
        if (!ftp.changeWorkingDirectory(dic)) {
            ftp.makeDirectory(new String(dic.getBytes("GBK"),"iso-8859-1"));
        }
        //跳转目标目录
        ftp.changeWorkingDirectory(dic);
        try {
            for (File file : files) {
                if (file.isFile()) {
                    this.upload(file.getAbsolutePath());
                }
                if (file.isDirectory()) {
                    this.uploadByDic(file.getName(), file.listFiles());
                }
            }
        } finally {
            ftp.changeWorkingDirectory(origin);
        }
        return true;
    }




    public void delete(String fileName) {
        try {
            if (this.exsist(fileName)) {
                ftp.deleteFile(new String(fileName.getBytes("GBK"),"iso-8859-1"));
                MockContext.LOG.debug("[O-MOCK] delete file success filename：{}", fileName);
            }
        } catch (Exception e) {
            MockContext.LOG.error("[O-MOCK]delete exception: {}", fileName);
        }
    }

    /**
     * 判断文件是否存在
     *
     * @param fileName
     * @return
     */
    public boolean exsist(String fileName) {
        try {
            FTPFile[] files = ftp.listFiles();
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

    /**
     * 判断目录是否存在
     *
     * @param dicName
     * @return
     */
    public boolean exsistDic(String dicName) {
        try {
            FTPFile[] files = ftp.listDirectories();
            for (FTPFile file : files) {
                if (file.getName().equals(dicName)) {
                    return true;
                }
            }
            MockContext.LOG.info("[O-MOCK]file:{}not exsist", dicName);
        } catch (Exception e) {
            MockContext.LOG.error("[O-MOCK]file dic exsist exception: {}", dicName);
        }
        return false;
    }

    /**
     * 获取文件树
     *
     * @return
     */
    public List<String> getFileTree() {
        List<String> list = new ArrayList<String>();
        try {
            // 获取目录下文件集合
            FTPFile[] files = ftp.listFiles();
            for (FTPFile file : files) {
                if (file.isFile()) {
                    list.add(file.getName());
                }
            }
        } catch (Exception e) {
            MockContext.LOG.error("[O-MOCK]file dic tree exception");
        }
        return list;
    }





    /**
     * 获取文件树
     *
     * @return
     */
    public FTPFile[] getAllFileFtpTree() {
        FTPFile[] files = null;
        try {
            ftp.enterLocalPassiveMode();
            // 获取目录下文件集合
            files = ftp.listFiles();
        } catch (Exception e) {
            MockContext.LOG.error("[O-MOCK]find file tree exception");
        }
        return files;
    }



    /**
     * 获取目录树
     *
     * @return
     */
    public FTPFile[] getAllDicFtpTree() {
        FTPFile[] files = null;
        try {
            ftp.enterLocalPassiveMode();
            // 获取目录下目录集合
            files = ftp.listDirectories();
        } catch (Exception e) {
            MockContext.LOG.error("[O-MOCK] fetch fileTree exception");
        }
        return files;
    }

    public String getFileContent(String fileName) {
        InputStream in = null;
        try {
            if (this.exsist(fileName)) {
                in = ftp.retrieveFileStream(new String(fileName.getBytes("GBK"),"iso-8859-1"));
                if (in.available()<1) {
                    Thread.sleep(1000);
                }
                if (in.available()<1) {
                    Thread.sleep(2000);
                }
                if (in.available()<1) {
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

    public void uploadFile(String fileName, String dataString) {
        ByteArrayInputStream in = null;
        try {
            byte[] data = dataString.getBytes("UTF-8");
            in = new ByteArrayInputStream(data, 0, data.length);
            boolean flag = ftp.storeFile(new String(fileName.getBytes("GBK"),"iso-8859-1"), in);
            if (flag) {
                MockContext.LOG.debug("[O-MOCK]update success: {}", fileName);
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
    }

    public boolean deleteDic(String dicName) throws IOException {
        String origin = ftp.printWorkingDirectory();
        try {
            if (!ftp.changeWorkingDirectory(dicName)) {
                ftp.makeDirectory(new String(dicName.getBytes("GBK"),"iso-8859-1"));
            }
            //跳转目标目录
            ftp.changeWorkingDirectory(dicName);
            FTPFile[] ftpFiles = ftp.listFiles();
            for (FTPFile ftpFile : ftpFiles) {
                if (ftpFile.isFile()) {
                    ftp.deleteFile(ftpFile.getName());
                }
                if (ftpFile.isDirectory()) {
                    //递归删除
                    deleteDic(ftpFile.getName());
                }
            }
            ftp.changeToParentDirectory();
            boolean flag = ftp.removeDirectory(dicName);
            return flag;
        } catch (Exception e) {
            MockContext.LOG.error("[O-MOCK] ftpClientTools.deleteDic exception");
        } finally {
            ftp.changeWorkingDirectory(origin);
        }
        return false;
    }

    public void cd(String path) throws IOException {
        //判断FPT目标文件夹时候存在不存在则创建
        if (!ftp.changeWorkingDirectory(path)) {
            ftp.makeDirectory(new String(path.getBytes("GBK"),"iso-8859-1"));
        }
        //跳转目标目录
        ftp.changeWorkingDirectory(path);
    }

    public void cdParent() throws IOException {
        ftp.changeToParentDirectory();
    }

    public void mkdir(String dic) throws IOException {
        ftp.makeDirectory(new String(dic.getBytes("GBK"),"iso-8859-1"));
    }

    public static String getWorkDic(String dicName) {
       if (StringUtils.isNotBlank(dicName)) {
           return "/"+MockContext.getConfig().getAppName()+"/"+MockContext.getConfig().getNowUser()+dicName;
       }
        return "/"+MockContext.getConfig().getAppName()+"/"+MockContext.getConfig().getNowUser();
    }

    private static String encode(String name) throws UnsupportedEncodingException {
        return new String(name.getBytes("GBK"),"iso-8859-1");
    }



    private FtpClientTools() {}

    public static FtpClientTools getInstance(String path) {
        FtpClientTools ftpClientTools = new FtpClientTools(MockContext.getConfig().getFtpHost(),MockContext.getConfig().getFtpPort()
                , MockContext.getConfig().getUserName(), MockContext.getConfig().getPassword(),
                path);

        if (StringUtils.isNotEmpty(ftpClientTools.remoteServer)) {
            return ftpClientTools;
        }
        return null;
    }



    public static void main(String[] args) throws Exception {
        //        FtpClientTools ftpClientTools = new FtpClientTools("10.170.148.15", 21, "friday", "20200313", "/");
//        ftpClientTools.upload(MockContext.getConfig().getPath()+"/config.json");
//       ftpClientTools.delete("elasticsearch.yml");
//        ftpClientTools.ftp.removeDirectory("ids");
//        ftpClientTools.closeFTP();
    }

}
