package com.hjyd.common.fileupload.ftp;

import com.hjyd.config.FTPConfig;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.pool2.impl.GenericObjectPool;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.springframework.beans.factory.annotation.Autowired;


/**
 * @Description：FTP连接池
 * 1.可以获取池中空闲链接
 * 2.可以将链接归还到池中
 * 3.当池中空闲链接不足时，可以创建链接
 * @Author：黄细初
 * @Date：2019/6/23
 */
public class FtpPool {

    FtpClientFactory factory;

    private final GenericObjectPool<FTPClient> internalPool;

    FTPConfig ftpConfig;

    //初始化连接池
    public FtpPool(@Autowired FtpClientFactory factory,@Autowired FTPConfig ftpConfig) {
        this.factory = factory;
        this.ftpConfig = ftpConfig;
        GenericObjectPoolConfig poolConfig = new GenericObjectPoolConfig();
        poolConfig.setMaxTotal(ftpConfig.getMaxTotal());
        poolConfig.setMinIdle(ftpConfig.getMinIdle());
        poolConfig.setMaxIdle(ftpConfig.getMaxIdle());
        poolConfig.setMaxWaitMillis(ftpConfig.getMaxWaitMillis());

        this.internalPool = new GenericObjectPool<FTPClient>(factory, poolConfig);
    }

    //从连接池中取连接
    public FTPClient getFTPClient() {
        try {
            return internalPool.borrowObject();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    //将链接归还到连接池
    public void returnFTPClient(FTPClient ftpClient) {
        try {
            internalPool.returnObject(ftpClient);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 销毁池子
     */
    public void destroy() {
        try {
            internalPool.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
 
 