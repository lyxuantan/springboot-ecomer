package vn.aop;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.Normalizer;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.springframework.util.StringUtils;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class AopCommon {

	private static final Predicate<String> IS_NOT_SPACES_ONLY = Pattern.compile("^\\s*$").asPredicate().negate();

	public static String implode(String delimiter, List<String> data) {
		return data.stream().filter(IS_NOT_SPACES_ONLY).collect(Collectors.joining(delimiter));
	}

	public static boolean isHttp(String s) {
		String patter = "^(http|https|ftp)://.*$";
		if (s.matches(patter)) {
			return true;
		}
		return false;
	}

	public static String getAlphaNumericString(int n, boolean isNumberOnly) {
		String AlphaNumericString = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
		if (isNumberOnly == true) {
			AlphaNumericString = "0123456789";
		}
		StringBuilder sb = new StringBuilder(n);
		for (int i = 0; i < n; i++) {
			int index = (int) (AlphaNumericString.length() * Math.random());
			sb.append(AlphaNumericString.charAt(index));
		}
		return sb.toString();
	}

	public static String setFileName(String name) throws NoSuchAlgorithmException {
		String nameUniqiue = name + AmazonUtil.dateToInt();
		MessageDigest md = MessageDigest.getInstance("MD5");
		byte[] hashInBytes = md.digest(nameUniqiue.getBytes(StandardCharsets.UTF_8));

		StringBuilder sb = new StringBuilder();
		byte[] arrayOfByte1;
		int j = (arrayOfByte1 = hashInBytes).length;
		for (int i = 0; i < j; i++) {
			byte b = arrayOfByte1[i];
			sb.append(String.format("%02x", new Object[] { Byte.valueOf(b) }));
		}
		return sb.toString();
	}

	public static String pathNameFile(String name) {
		// android.png
		// fileFrags = ["android", "png"]
		String[] fileFrags = name.split("\\.");
		try {
			// png
			return fileFrags[fileFrags.length - 1];
		} catch (Exception e) {
			return "txt";
		}
	}

	public static String deAccent(String str) {
		String nfdNormalizedString = Normalizer.normalize(str, Normalizer.Form.NFD);
		Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
		return pattern.matcher(nfdNormalizedString).replaceAll("").replaceAll("Đ", "D").replace("đ", "");
	}

	public static <T> List<T> parseJsonArray(String json, Class<T> clazz) {
		
		var list = new ArrayList<T>();
		if(StringUtils.isEmpty(json))
			return list;
		
		try {
			var objectMapper = new ObjectMapper();
			var tree = objectMapper.readTree(json);
			for (JsonNode jsonNode : tree) {
				list.add(objectMapper.treeToValue(jsonNode, clazz));
			}
		} catch (Exception rex) {}
		
		return list;
	}
}
