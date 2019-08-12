package com.hjyd.common.fileupload;


import com.hjyd.common.fileupload.impl.FTPFileUploadImpl;
import com.hjyd.common.fileupload.impl.LocalFileUploadImpl;
import com.hjyd.util.ConstantUtils;
import com.hjyd.util.ConstantUtils;

/**
 * @program：
 * @description：文件上传接口
 * @author：林黎明 注意：此工厂类暂停用,上传分发在FTPConfig类完成 2019-05-23  by linking
 * @create：2019-05-10 14:01
 */
public class FileUploadFactory {


    private String uploadFlag = "";
    private String uploadFlagLocal = "LOCAL";

    private static FileUploadFactory install = new FileUploadFactory();

    private FileUploadFactory() {
        this.uploadFlag = ConstantUtils.UPLOAD_FLAG;
        this.uploadFlagLocal = ConstantUtils.UPLOAD_FLAG_LOCAL;
    }

    public static FileUploadFactory getInstall() {
        return install;
    }

    public IFileUpload getFileUpload() {
        if (!uploadFlagLocal.equals(uploadFlag)) {
            return new FTPFileUploadImpl();
        }
        return new LocalFileUploadImpl();
    }
}
