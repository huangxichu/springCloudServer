package com.hjyd.config;
 
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * 说明：ftp配置
 * Created by luojie on 2019/04/16.
 */

public class FTPConfig {
 
    @Value("${ftp.username}")
    private String userName;
 
    @Value("${ftp.password}")
    private String passWord;
 
    @Value("${ftp.host}")
    private String ip;
 
    @Value("${ftp.port}")
    private int port;

    @Value("${ftp.root}")
    private String root;

    @Value("${ftp.workDirs}")
    private String workDirs;

    @Value("${ftp.maxTotal}")
    private int maxTotal;

    @Value("${ftp.minIdle}")
    private int minIdle;

    @Value("${ftp.maxIdle}")
    private int maxIdle;

    @Value("${ftp.maxWaitMillis}")
    private int maxWaitMillis;

    @Value("${ftp.uploadFlag}")
    private String uploadFlag;
 
    public String getUserName() {
        return userName;
    }
 
    public void setUserName(String userName) {
        this.userName = userName;
    }
 
    public String getPassWord() {
        return passWord;
    }
 
    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }
 
    public String getIp() {
        return ip;
    }
 
    public void setIp(String ip) {
        this.ip = ip;
    }
 
    public int getPort() {
        return port;
    }
 
    public void setPort(int port) {
        this.port = port;
    }

    public String getRoot() {
        return root;
    }

    public void setRoot(String root) {
        this.root = root;
    }

    public int getMaxTotal() {
        return maxTotal;
    }

    public void setMaxTotal(int maxTotal) {
        this.maxTotal = maxTotal;
    }

    public int getMinIdle() {
        return minIdle;
    }

    public void setMinIdle(int minIdle) {
        this.minIdle = minIdle;
    }

    public int getMaxIdle() {
        return maxIdle;
    }

    public void setMaxIdle(int maxIdle) {
        this.maxIdle = maxIdle;
    }

    public int getMaxWaitMillis() {
        return maxWaitMillis;
    }

    public void setMaxWaitMillis(int maxWaitMillis) {
        this.maxWaitMillis = maxWaitMillis;
    }

    public String getUploadFlag() {
        return uploadFlag;
    }

    public void setUploadFlag(String uploadFlag) {
        this.uploadFlag = uploadFlag;
    }

    public String getWorkDirs() {
        return workDirs;
    }

    public void setWorkDirs(String workDirs) {
        this.workDirs = workDirs;
    }
}
 