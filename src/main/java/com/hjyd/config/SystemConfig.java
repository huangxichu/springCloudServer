package com.hjyd.config;

import com.hjyd.common.fileupload.IFileUpload;
import com.hjyd.common.fileupload.ftp.FtpClientFactory;
import com.hjyd.common.fileupload.ftp.FtpPool;
import com.hjyd.common.fileupload.impl.FTPFileUploadImpl;
import com.hjyd.common.fileupload.impl.LocalFileUploadImpl;
import com.hjyd.util.ConstantUtils;
import com.hjyd.util.StringUtils;
import org.beetl.sql.ext.SnowflakeIDWorker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @program：server_demo
 * @description：
 * @author：黄细初
 * @create：2019-05-15 10:29
 */
@Configuration
public class SystemConfig {


    @Bean("snowflakeIDWorker")
    public SnowflakeIDWorker getSnowflakeIDWorker(){
        return new SnowflakeIDWorker(1L,2L);
    }

    @Bean("ftpConfig")
    public FTPConfig getFtpConfig(){
        return new FTPConfig();
    }

    @Bean("ftpClientFactory")
    public FtpClientFactory getFtpClientFactory(@Autowired FTPConfig ftpConfig){
        return new FtpClientFactory(ftpConfig);
    }

    @Bean("ftpPool")
    public FtpPool getFtpPool(@Autowired FtpClientFactory ftpClientFactory,@Autowired FTPConfig ftpConfig){
        return new FtpPool(ftpClientFactory,ftpConfig);
    }


    @Bean("fileUpload")
    public IFileUpload getIFileUpload(@Autowired FtpPool ftpPool,@Autowired FTPConfig ftpConfig){
        IFileUpload iFileUpload = null;

        if(!StringUtils.equals(ftpConfig.getUploadFlag(), ConstantUtils.UPLOAD_FLAG_LOCAL)){
            //上传到FTP服务器
            iFileUpload = new FTPFileUploadImpl();
        }else{
            //上传到本地服务器
            iFileUpload = new LocalFileUploadImpl();
        }

        return iFileUpload;
    }

}
