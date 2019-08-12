package com.hjyd.common.fileupload.impl;


import com.hjyd.common.fileupload.IFileUpload;
import com.hjyd.exception.ApplicationException;
import com.hjyd.util.ConstantUtils;
import com.hjyd.util.FileUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.List;

/**
 * @program：
 * @description：文件上传接口
 * @author：林黎明
 * @create：2019-05-10 14:01
 */
@Slf4j
@Service
public class LocalFileUploadImpl implements IFileUpload {


	public String uploadRoot = "";
	
	public LocalFileUploadImpl(){
		uploadRoot = ConstantUtils.LOCAL_UPLOAD_PATH;   //本地上传文件地址
	}

	/**
	 * 封装上传文件的目标地址
	 *
	 * @param destFileName  目标文件名称
	 * @param metaDataId    原始数据ID
	 *
	 * @return
	 */
	@Override
	public String makeFilePath(String destFileName, String metaDataId){
		String destFilePath = "";
		try {					
			String uploadRoot = "";
			uploadRoot = ConstantUtils.LOCAL_UPLOAD_PATH;
			destFilePath = uploadRoot + metaDataId + File.separator + destFileName;
			
		} catch (Exception e) {
			e.printStackTrace();
			log.error("封装文件路径失败："+e.getMessage(),e);
		}	
		return destFilePath;
	}
	
	
	
	/**
	 * 上传文件函数
	 * 
	 * @param file  要上传的文件
	 * @param destPath 要存放上传文件的目标地址
	 * 
	 */
	@Override
	public boolean upload(MultipartFile file, String destPath) {
		boolean bRetVal = true;
		try {			
			// 设置以二进制流的方式传输
//			String remoteFileName = ""; // 对远程目录的处理
			if (destPath.contains(File.separator)) {
//				remoteFileName = destPath.substring(destPath.lastIndexOf(File.separator) + 1);
				// 创建服务器远程目录结构，创建失败直接返回
				if (!createDirecroty(destPath)) {
					log.info("创建服务器目录失败！");
					return false;
				}
			}
			File newFile = new File(destPath);
			File f = FileUtils.multipartToFile(file);
			bRetVal = copyFile(newFile, f);
            if(bRetVal){
			   log.info("上传成功");
            }else{
               log.info("上传失败");
            }
		} catch (ApplicationException e) {
			throw e;
		} catch (Exception e) {
			throw new ApplicationException(e,"文件上传失败");
		}
		
		return bRetVal;
	}


	@Override
    public boolean delete(String filePath) {
		boolean bRetVal = false;
		try{
			deleteDirecroty(filePath);
			bRetVal = true;
		}catch(Exception e){
			log.error("文件删除失败："+e.getMessage(),e);
		}
		return bRetVal;
	}

	@Override
	public boolean delete(List<String> filePaths) {
		boolean bRetVal = false;
		try{
			if(!CollectionUtils.isEmpty(filePaths)){
				for(String filePath : filePaths){
					deleteDirecroty(filePath);
				}
			}
			bRetVal = true;
		}catch(Exception e){
			log.error("文件删除失败："+e.getMessage(),e);
		}
		return bRetVal;
	}

	@Override
	public void replace(String filePath, String destFilePath) {
		// TODO Auto-generated method stub

	}
	
	
	/**
	 * 读取文件写入文件(支持大文件)
	 * 
	 * @param nFile  要写入的文件
	 * @param oFile  要读取的文件
	 * @author linking
	 * @since  2019-05-10
	 * 
	 * @return
	 */
	public static boolean copyFile(File nFile, File oFile) {
		boolean bRetVal = true;
		try{		
			//InputStreamReader isr = new InputStreamReader(in);
			//OutputStreamWriter osw = new OutputStreamWriter(new FileOutputStream(nFile));
			
			//最大的流为60Mb，当文件的容量大于60Mb的时候便分开流
			final int MAX_BYTE = 1000000; 
			long streamTotal = 0;     //接受流的容量
			int streamNum = 0;        //流需要分开的数量
			int leave = 0;            //文件剩下的字符数
			byte[] inOutb;            //byte数组接受文件的数据
			
			//创建流文件读入与写出类
			InputStream inStream;
			  inStream = new FileInputStream(oFile);
			OutputStream outStream;
			  outStream = new FileOutputStream(nFile);
			  
			//通过available方法取得流的最大字符数
			streamTotal = inStream.available();
			
			//取得流文件需要分开的数量
			streamNum = (int) Math.floor(streamTotal/MAX_BYTE);
			
			//分开文件之后，剩余的数量
			leave = (int)streamTotal%MAX_BYTE;
			
			//文件的容量大于60Mb时进入循环
			if(streamNum > 0){
				for(int i=0; i<streamNum; i++){
					inOutb = new byte[MAX_BYTE];
					
					//读入流，保存在byte数组
					inStream.read(inOutb, 0, MAX_BYTE);
					outStream.write(inOutb);  //写出流
					outStream.flush();  //更新写出的经果
				}
			}
			
			//写出剩下的流数据
			inOutb = new byte[leave];
			inStream.read(inOutb, 0, leave);
			outStream.write(inOutb);
			outStream.flush();
			
			inStream.close();
			outStream.close();
			
		}catch(Exception e){
			bRetVal = false;
			e.printStackTrace();
			log.error("拷贝文件失败！" + e.getMessage(), e);
		}
		return bRetVal;
	}

	/**
	 * 创建本地目录
	 * @param path
	 * @return
	 * @throws IOException
	 */
	public  static boolean createDirecroty(String path) throws IOException {
		String directory = path.substring(0, path.lastIndexOf(File.separator) + 1);
		// 如果目录不存在，则递归创建目录
		if (!directory.equalsIgnoreCase(File.separator)) {
			int start = 0;
			int end = 0;
			if (directory.startsWith(File.separator)) {
				start = 1;
			} else {
				start = 0;
			}
			end = directory.indexOf(File.separator, start);
			while (true) {
				String subDirectory = new String(path.substring(0, end));
				File file = new File(subDirectory);
				if(!file.exists()){
					file.mkdir();
				}
				start = end + 1;
				end = directory.indexOf(File.separator, start);
				if(end < 0){
					break;
				}
			}
			return true;
		}else{
			return false;
		}
	}
	

	/**
	 * @Description：删除目录及目录下所有的文件
	 * @Param：
	 * @return：
	 */
	public static void deleteDirecroty(String path){
		File file = new File(path);
		if(!file.isDirectory()){
			file.delete();
		}else{
			String[] filelist = file.list();
			if(filelist.length < 1){
				file.delete();
				return;
			}
			for(int i = 0; i < filelist.length; i++){
				File delFile = new File(path + File.separator + filelist[i]);
				if(!delFile.isDirectory()){
					delFile.delete();
				}else{
					deleteDirecroty(path + File.separator + filelist[i]);
				}
			}
			file.delete();
		}
	}
	



	/**
	 * 文件下载 (注意:文件上传本地方式,暂时不用从目标服务器拷贝文件到本地服务器)
	 * @param remoteFilePath   下载文件路径  如：/toDownload/test.txt
	 * @return
	 * @throws Exception
	 */
	@Override
	public void download(String remoteFilePath, HttpServletRequest request, HttpServletResponse response) {

	}

}
