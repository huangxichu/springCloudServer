package com.hjyd.common.fileupload.ftp;

import com.hjyd.config.FTPConfig;
import com.hjyd.exception.ApplicationException;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;
import org.apache.commons.pool2.PooledObject;
import org.apache.commons.pool2.PooledObjectFactory;
import org.apache.commons.pool2.impl.DefaultPooledObject;

import java.io.IOException;

/**
 * 连接工厂
 */
//@Component
public class FtpClientFactory implements PooledObjectFactory<FTPClient> {

//    @Autowired
    FTPConfig ftpConfig;

    public FtpClientFactory() {
    }


    public FtpClientFactory(FTPConfig ftpConfig) {
        this.ftpConfig = ftpConfig;
    }

    //创建连接到池中
    @Override
    public PooledObject<FTPClient> makeObject() {
        FTPClient ftpClient = new FTPClient();//创建客户端实例
        return new DefaultPooledObject<FTPClient>(ftpClient);
    }

    //销毁连接，当连接池空闲数量达到上限时，调用此方法销毁连接
    @Override
    public void destroyObject(PooledObject<FTPClient> pooledObject) {
        FTPClient ftpClient = pooledObject.getObject();
        try {
            ftpClient.logout();
            if (ftpClient.isConnected()) {
                ftpClient.disconnect();
            }
        } catch (IOException e) {
            throw new RuntimeException("Could not disconnect from server.", e);
        }
    }

    //链接状态检查
    @Override
    public boolean validateObject(PooledObject<FTPClient> pooledObject) {
        FTPClient ftpClient = pooledObject.getObject();
        try {
            return ftpClient.sendNoOp();
        } catch (IOException e) {
            return false;
        }
    }

    //初始化连接
    @Override
    public void activateObject(PooledObject<FTPClient> pooledObject) throws Exception {
        FTPClient ftpClient = pooledObject.getObject();
        ftpClient.connect(ftpConfig.getIp(), ftpConfig.getPort());
        ftpClient.login(ftpConfig.getUserName(), ftpConfig.getPassWord());
        ftpClient.setFileType(FTP.BINARY_FILE_TYPE);//设置上传文件类型为二进制，否则将无法打开文件
        if (!FTPReply.isPositiveCompletion(ftpClient.getReplyCode())) {
//            log.error("未连接到FTP，用户名或密码错误。");
            throw new ApplicationException("未连接到FTP，用户名或密码错误。");
        } else {
            System.out.println("FTP连接成功。");
        }
        // 开启服务器对UTF-8的支持，如果服务器支持就用UTF-8编码，否则就使用本地编码（GBK）.
        String LOCAL_CHARSET = "GBK";
        if (FTPReply.isPositiveCompletion(ftpClient.sendCommand("OPTS UTF8", "ON"))) {
            LOCAL_CHARSET = "UTF-8";
        }
        ftpClient.setControlEncoding(LOCAL_CHARSET);
    }

    //钝化连接，使链接变为可用状态
    @Override
    public void passivateObject(PooledObject<FTPClient> pooledObject) throws Exception {
        FTPClient ftpClient = pooledObject.getObject();
        try {
            ftpClient.changeWorkingDirectory(ftpConfig.getRoot());
            ftpClient.logout();
            if (ftpClient.isConnected()) {
                ftpClient.disconnect();
            }
        } catch (IOException e) {
            throw new RuntimeException("Could not disconnect from server.", e);
        }
    }

    // 用于连接池中获取pool属性
    public FTPConfig getConfig() {
        return ftpConfig;
    }
}