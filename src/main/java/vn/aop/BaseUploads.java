package vn.aop;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import org.apache.commons.io.FileUtils;
import org.springframework.web.multipart.MultipartFile;

import vn.data.UploadsFiles;
import vn.exceptions.InvilidParamsException;

public class BaseUploads {

	public static String uploadSignFile(MultipartFile multipartFile, UploadsFiles uploadsFiles) throws Exception {
		String nameFile = multipartFile.getOriginalFilename();
		try {
			String extionsionFile = AopCommon.pathNameFile(nameFile);
			List<String> filePass = uploadsFiles.filePass();
			if(!filePass.contains(extionsionFile.toLowerCase())) {
				throw new InvilidParamsException("File không được phép upload !");
			}
	        String fileMd5 = AopCommon.setFileName(nameFile + AmazonUtil.dateToInt())  + "." +  AopCommon.pathNameFile(nameFile);
	        
	        Integer subFolder = AmazonUtil.getFolderUpload();
	        // E:java2006/sprint-ecomer/uploads/product/780710/
	        String fd = System.getProperty("user.dir") + uploadsFiles.folderUpload() + subFolder + "/";
	        File f = new File(fd);
	  		if(!f.exists()) f.mkdirs();
	  		
	        String filePath = fd + fileMd5;
	        // E:java2006/sprint-ecomer/uploads/product/780710/77ce427d234f6147abc1b13ef353c27c.PNG
	        InputStream fileStream = multipartFile.getInputStream();
	        File targetFile = new File(filePath);
	        FileUtils.copyInputStreamToFile(fileStream, targetFile);
	        
	        return subFolder + "/" + fileMd5;
		} catch (IOException | NoSuchAlgorithmException e) {
			throw e;
		}
	}
}
