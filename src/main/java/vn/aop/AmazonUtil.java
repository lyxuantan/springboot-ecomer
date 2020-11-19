package vn.aop;

import java.util.Date;

import javax.annotation.PostConstruct;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Component
public class AmazonUtil {

	private static Logger logger = LoggerFactory.getLogger(AmazonUtil.class);
	private static final int VALUE_NOT_CONVERT = 0;
	
	@Autowired
	private Environment environment;
	
	@Value("${amazon.host}")
	String amazonHost;
	
	@PostConstruct
	public void init() {
		Util.host = environment.getProperty("amazon.host");
	}
	
	public String getFileById(int objectId, String imageFile, String folder) {
		
		if(objectId == VALUE_NOT_CONVERT) 
			return null;
		
		String patter = "^(http|https|ftp)://.*$";
        if (imageFile.matches(patter)){
            return imageFile;
        } else {
        	int total = getFolderUpload(objectId);
            String filePath = folder + total + '/' + imageFile;
            return environment.getProperty("amazon.host") + filePath;
        }
	}
	
	public static int getFolderUpload(Integer objectId) {
		
		if(objectId == null)
			return getFolderUpload();
		// 09-01-2020
		String _dateStr = new SimpleDateFormat("MM-01-yyyy").format(new Date(objectId * 1000L));
		try {
			Date _date = new SimpleDateFormat("MM-01-yyyy").parse(_dateStr);
			// 1982839233/2048
			// 20-09-2020 --> 780111
			// 21-09-2020 --> 780111
			return (int) Math.ceil((double) AmazonUtil.dateToInt(_date)/2048);
		} catch (ParseException e) {
			logger.info("ParseException Date : {}", e.getMessage());
			return VALUE_NOT_CONVERT;
		}
	}
	
	public static int getFolderUpload() {
		return getFolderUpload(dateToInt());
	}
	
	public static int dateToInt() {
		return (int) (new Date().getTime()/1000);
	}
	
	public static int dateToInt(Date _d) {
		return (int) (_d.getTime()/1000);
	}
	
	public static class Util {
		public static String host;
	}
}