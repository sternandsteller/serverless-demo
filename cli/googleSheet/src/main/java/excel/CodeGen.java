package excel;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.regex.Pattern;

import org.apache.commons.io.FileUtils;

public class CodeGen {
	private static final String PACKAGE_EXCEL = "package excel;";
	private static final String OPEN_BRACE = "{";
	private static final Object COMMA = ",";
	private static final String CLOSE_BRACE = "}";
	private static final String NL = "\n";
	public static final String PUBLIC_CLASS = "public class ";
	public static final String COLUMNS = "Columns";
	private static final String UNDER_SCORE = "_";
	private static final String PUBLIC = "private ";
	private static final String STRING = "String ";
	private static final String SEMICOLON = ";";
	private static final String GET = "get";

	private static final String OPEN_BRACKET = "(";
	private static final String CLOSE_BRACKET = ")";
	private static final String RETURN = "return ";
	private static final String DQ = "\"";
	private static final String NULL_TEXT = "null";
	private static final String YES_NO = "YES NO";
	private static final String IS = "is";
	private static final String BOOLEAN = "boolean ";
	StringBuffer code;
	boolean bCommaNeeded = false;

	public CodeGen() {
		code = new StringBuffer(PACKAGE_EXCEL + NL);
	}

	public void add(String string) {
		code.append((bCommaNeeded) ? COMMA : "");
		code.append(string);
		bCommaNeeded = true;
	}

	public void start(String publicClass, String className) {
		code.append(NL + publicClass + toCamelCase(className) + OPEN_BRACE);
		bCommaNeeded = false;
	}

	public void start(String string) {
		code.append(NL + string + OPEN_BRACE);
		bCommaNeeded = false;
	}

	public void end() {
		code.append(CLOSE_BRACE + NL);
		bCommaNeeded = false;
	}

	public void finish(String filename) throws IOException {
		FileUtils.writeStringToFile(new File(filename), code.toString(), Charset.forName("UTF-8"));
		System.out.println(NL + filename + NL + code.toString());
	}

	public String toCamelCase(String text) {
		StringBuffer camelString = new StringBuffer();
		for (String word : text.split(Pattern.quote(UNDER_SCORE))) {
			camelString.append(word.substring(0, 1).toUpperCase() + word.substring(1).toLowerCase());
		}
		return camelString.toString();
	}

	public void getter(String attribute, String value) {
		if (value != null && !value.equals(NULL_TEXT)) {
			value = value.trim();
			String getterName = toCamelCase(attribute);
			if (YES_NO.contains(value.toUpperCase())) {
				code.append(PUBLIC + BOOLEAN + IS + getterName + OPEN_BRACKET + CLOSE_BRACKET + OPEN_BRACE);
				boolean bReturnValue = (value.length() > 2);
				code.append(RETURN + bReturnValue + SEMICOLON + CLOSE_BRACE);
			} else {
				code.append(PUBLIC + STRING + GET + getterName + OPEN_BRACKET + CLOSE_BRACKET + OPEN_BRACE);
				code.append(RETURN + DQ + value + DQ + SEMICOLON + CLOSE_BRACE);
			}
		}
	}

}
