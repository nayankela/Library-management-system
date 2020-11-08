package utility;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegistrationValidation {

	public boolean id(String id) {
		// validate ID
		String ID = "[A-Z]{4}-\\d{2,5}";

		Pattern pattern = Pattern.compile(ID);

		boolean result = false;
		Matcher matcher = pattern.matcher(id);
		if (matcher.matches()) {
			result = true;
		}
		return result;
	}

	// Password
	public boolean validPassword(String password) {

		boolean result = false;
		String REGEX = "^(?=.*[0-9])" + "(?=.*[a-z])(?=.*[A-Z])" + "(?=.*[@#$%^&+=])" + "(?=\\S+$).{8,20}$";

		Pattern p = Pattern.compile(REGEX);

		Matcher m = p.matcher(password);
		if (m.matches()) {
			result = true;
		}

		return result;
	}

	// VALIDATE MOBILE NUMBER.
	// 1) Begins with 0 or 91
	// 2) Then contains 6,7 or 8 or 9.
	// 3) Then contains 9 digits
	public boolean phoneNumber(String phoneNumber) {
		String MOBILE_PATTERN = "(0/91)?[7-9][0-9]{9}";
		Pattern pattern = Pattern.compile(MOBILE_PATTERN);

		boolean result = false;
		Matcher matcher = pattern.matcher(phoneNumber);
		if (matcher.matches()) {
			result = true;
		}
		return result;

	}

	// VALIDATE NAME
	public boolean name(String name) {

		//String NAME = "[A-Z][a-z]*";
		// String NAME = "^[aA-zZ]\\w{3,29}$";
		String NAME = "^[\\p{L} .'-]+$";
		Pattern pattern = Pattern.compile(NAME);

		boolean result = false;
		Matcher matcher = pattern.matcher(name);
		if (matcher.matches()) {
			result = true;
		}
		return result;
	}

}
