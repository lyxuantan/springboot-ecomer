package vn.validator;

import java.util.Arrays;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PhoneNumberValidator implements ConstraintValidator<PhoneNumberContraint, String> {

	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		var dauso = Arrays.asList("016", "098", "097", "056");
		Pattern pattern = Pattern.compile("\\d{10}");
		Matcher matcher = pattern.matcher(value);
		if(!matcher.matches()) {
			return false;
		}
		var thereStr = value.substring(0, 3);
		return dauso.contains(thereStr);
	}
}
