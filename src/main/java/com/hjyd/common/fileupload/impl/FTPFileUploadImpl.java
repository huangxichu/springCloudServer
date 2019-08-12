package com.hjyd.common.fileupload.impl;

import com.hjyd.common.fileupload.IFileUpload;
import com.hjyd.common.fileupload.ftp.FtpPool;
import com.hjyd.config.FTPConfig;
import com.hjyd.exception.ApplicationException;
import com.hjyd.util.ConstantUtils;
import com.hjyd.util.FileUtils;
import com.hjyd.util.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.SocketException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.InflaterInputStream;

/**
 * @program：
 * @description：文件上传FTP服务实现类
 * @author：林黎明
 * @create：2019-05-15 09:40
 * @update：2019-06-23 11:02 黄细初
 */
@Slf4j
public class FTPFileUploadImpl implements IFileUpload {

    /**
     * 本地字符编码
     */
    public static final String LOCAL_CHARSET = "UTF-8";

    // FTP协议里面，规定文件名编码为iso-8859-1
    public static final String SERVER_CHARSET = "iso-8859-1";

    @Autowired
    private ResourceLoader resourceLoader;

    @Autowired
    FtpPool pool;

    @Autowired
    FTPConfig ftpConfig;

    /**
     * @Description：向FTP服务器上传文件
     * @Param：file 上传到FTP服务器上的文件
     * @Author：黄细初
     * @Date：2019/6/23
     */
    @Override
    public boolean upload(MultipartFile file, String destPath) {
        FTPClient ftpClient = pool.getFTPClient();
        //开始进行文件上传
        String fileName = file.getOriginalFilename();
        InputStream input = null;
        try {
            input = file.getInputStream();
            if (input.available() == 0) {
                throw new ApplicationException("文件大小为0的文件请不要上传哦~~~");
            }

            ftpClient.setControlEncoding(LOCAL_CHARSET);


//            String remote = destPath + "/" + fileName;

            // 设置上传目录(没有则创建)
            if (!createDir(destPath, ftpClient)) {
                throw new ApplicationException("切入FTP目录失败：" + destPath);
            }


            ftpClient.enterLocalPassiveMode();  //设置被动模式

            //确保中文名称文件转码后正常上传
            fileName = new String(fileName.getBytes("GBK"), "ISO-8859-1");

            boolean result = ftpClient.storeFile(fileName, input);//执行文件传输
            if (!result) {//上传失败
                throw new ApplicationException("上传失败");
            }

        } catch (ApplicationException e) {
            throw e;
        } catch (Exception e) {
            throw new ApplicationException(e, "上传文件失败");
        } finally {
            try {
                if (input != null) {
                    input.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            System.out.println("开始归还连接");
            pool.returnFTPClient(ftpClient);//归还资源
        }
        return true;
    }

    /**
     * Description: 从FTP服务器下载文件
     *
     * @param pathName FTP服务器中的文件名
     * @param response 响应客户的响应体
     * @Version1.0
     */
    @Override
    public void download(String pathName, HttpServletRequest request,
                         HttpServletResponse response) {
        FTPClient ftpClient = pool.getFTPClient();
        String fileName = pathName.substring(pathName.lastIndexOf("/") + 1);
        InputStream in = null;
        OutputStream byteOut = null;
        try {
            //处理IE下载文件的中文名称乱码的问题
            String header = request.getHeader("User-Agent").toUpperCase();
            if (header.contains("MSIE") || header.contains("TRIDENT") || header.contains("EDGE")) {
                fileName = URLEncoder.encode(fileName, LOCAL_CHARSET);
                fileName = fileName.replace("+", "%20");    //IE下载文件名空格变+号问题
            } else {
                //不转码 下载的文件名中文会变成 "_"
                fileName = new String(fileName.getBytes(), SERVER_CHARSET);
            }
            response.setHeader("content-type", "application/octet-stream");
            response.setContentType("application/octet-stream");
            response.setHeader("Content-Disposition", "attachment;filename=" + fileName);
            response.setHeader("filename", fileName);
            response.setHeader("Access-Control-Expose-Headers", "filename");

//            pathName = new String(pathName.getBytes(FtpConstants.LOCAL_CHARSET),
//                    FtpConstants.SERVER_CHARSET);
            //下载用本地模式
            ftpClient.enterLocalPassiveMode();
            String path = pathName.substring(0, pathName.lastIndexOf("/") + 1);
            path = StringUtils.trimStart(path, "/");
            path = StringUtils.trimEnd(path, "/");
            String[] arr = path.split("/");
            for (String sbfDir : arr) {
                //目录编码，解决中文路径问题
                String d = new String(sbfDir.getBytes("GBK"), "iso-8859-1");
                //尝试切入目录
                if (!ftpClient.changeWorkingDirectory(d)) {
                    throw new ApplicationException("下载失败");
                }
            }
            fileName = new String(fileName.getBytes("GBK"), "iso-8859-1");
//            ftpClient.changeWorkingDirectory(pathName);
            in = ftpClient.retrieveFileStream(fileName);

            //可以设置长度 在进行网络操作时往往出错，因为你调用available()方法时，
            //对发发送的数据可能还没有到达，你得到的count是0。
            //byte[] bytes = new byte[in.available()];
            int count = 0;
            while (count == 0) {
                count = in.available();
            }
            byte[] bytes = new byte[count];

            byteOut = response.getOutputStream();
            //此处大于0 不然死循环
            int bufsize = 0;
            while ((bufsize = in.read(bytes, 0, bytes.length)) > 0) {
                byteOut.write(bytes, 0, bufsize);
            }
            byteOut.flush();
        } catch (ApplicationException e) {
            throw e;
        } catch (Exception e) {
            e.printStackTrace();
            throw new ApplicationException(e, "文件下载失败");
        } finally {
            //注意要先关闭流 否则会出现卡死问题
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (byteOut != null) {
                try {

                    byteOut.close();

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            System.out.println("开始归还连接");
            long bgn = System.currentTimeMillis();
            pool.returnFTPClient(ftpClient);
            long end = System.currentTimeMillis();
            System.out.println("归还连接耗时:" + (end - bgn));

        }
    }

    @Override
    public boolean delete(String filePath) {
        boolean bRetVal = false;
        FTPClient ftpClient = pool.getFTPClient();
        try {
            log.debug("删除文件：" + filePath);
            bRetVal = ftpClient.deleteFile(filePath);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new ApplicationException(e, "原文件删除失败");
        } finally {
            System.out.println("开始归还连接");
            long bgn = System.currentTimeMillis();
            pool.returnFTPClient(ftpClient);
            long end = System.currentTimeMillis();
            System.out.println("归还连接耗时:" + (end - bgn));
        }
        return bRetVal;
    }

    @Override
    public boolean delete(List<String> filePaths) {
        FTPClient ftpClient = pool.getFTPClient();
        try {
            if(!CollectionUtils.isEmpty(filePaths)){
                for(String filePath : filePaths){
//                    bRetVal = ftpClient.deleteFile(filePath);
                    ftpClient.dele(filePath);
                }
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new ApplicationException(e, "原文件删除失败");
        } finally {
            System.out.println("开始归还连接");
            long bgn = System.currentTimeMillis();
            pool.returnFTPClient(ftpClient);
            long end = System.currentTimeMillis();
            System.out.println("归还连接耗时:" + (end - bgn));
        }
        return true;
    }

    @Override
    public void replace(String filePath, String destFilePath) {

    }

    @Override
    public String makeFilePath(String destFileName, String metaDataId) {
        String destFilePath = "";
        try {
            String uploadRoot = ftpConfig.getWorkDirs();//FTP上传文件路径
            destFileName = new String(destFileName.getBytes(), "utf-8");
            //destFilePath = uploadRoot + metaDataId + "/" + destFileName;  //部署在UNIX下面才行
            destFilePath = uploadRoot + metaDataId + "/" + destFileName;    //如果FTP服务器是UNIX环境，而程序在Windows下跑，就这样写
        } catch (Exception e) {
            throw new ApplicationException(e, "封装文件路径失败");
        }
        return destFilePath;
    }


    /**
     * 创建目录(有则切换目录，没有则创建目录)
     *
     * @param dir
     * @return
     */
    public boolean createDir(String dir, FTPClient ftpClient) throws ApplicationException {
        if (StringUtils.nullOrBlank(dir)) {
            return true;
        }
        String d;
        try {
            //目录编码，解决中文路径问题
            d = new String(dir.getBytes(LOCAL_CHARSET), "iso-8859-1");
            //尝试切入目录
            if (ftpClient.changeWorkingDirectory(d)) {
                return true;
            }
            dir = StringUtils.trimStart(dir, "/");
            dir = StringUtils.trimEnd(dir, "/");
            String[] arr = dir.split("/");
//            StringBuffer sbfDir = new StringBuffer();
            //循环生成子目录
            for (String sbfDir : arr) {
//                sbfDir.append("/");
//                sbfDir.append(s);
                //目录编码，解决中文路径问题
                d = new String(sbfDir.getBytes("GBK"), "iso-8859-1");
                //尝试切入目录
                if (ftpClient.changeWorkingDirectory(d)) {
                    continue;
                }
                if (!ftpClient.makeDirectory(d)) {
                    throw new ApplicationException("[失败]ftp创建目录：" + sbfDir);
                }
                ftpClient.changeWorkingDirectory(d);
                System.out.println("[成功]创建ftp目录：" + sbfDir);
            }
            //将目录切换至指定路径
            ftpClient.changeWorkingDirectory(d);
        } catch (Exception e) {
            throw new ApplicationException(e, "[失败]ftp创建目录：" + dir);
        }
        return true;
    }
}
