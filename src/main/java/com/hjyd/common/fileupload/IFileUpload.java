package com.hjyd.common.fileupload;

import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;


/**
 * @program：
 * @description：文件上传接口
 * @author：林黎明
 * @create：2019-05-10 14:01
 */
public interface IFileUpload {

	/**
	 * 文件上传
	 * @param file 被上传文件
	 * @param destPath  目标目录 
	 */
	boolean upload(MultipartFile file, String destPath);

	/**
	 * 文件下载
	 * @param pathName   FTP服务器中的文件名
	 * @param request   响应客户的响应体
	 */
	void download(String pathName, HttpServletRequest request, HttpServletResponse response);
	/**
	 * 文件删除
	 * @param filePath 文件全路径
	 */
	boolean delete(String filePath);
	/**
	 * 文件删除 (批量)
	 * @param filePaths 文件全路径
	 */
	boolean delete(List<String> filePaths);
	/**
	 * 替换文件
	 * @param filePath 新文件
	 * @param destFilePath 被替换的文件
	 */
	void replace(String filePath, String destFilePath);
	
	/**
	 * 封装上传文件的目标地址
	 * 
	 * @param destFileName  目标文件名称
	 * @param metaDataId    原始数据ID
	 *
	 * @return
	 */
	String makeFilePath(String destFileName, String metaDataId);

}
