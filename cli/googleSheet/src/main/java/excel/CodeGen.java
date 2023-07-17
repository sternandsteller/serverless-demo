package excel;

public class CodeGen {
	private static final String OPEN_BRACE = "{";
	private static final Object COMMA = ",";
	private static final String CLOSE_BRACE = "}";
	private static final String NL = "\n";
	StringBuffer code;
	boolean bCommaNeeded = false;

	public CodeGen() {
		code = new StringBuffer("");
	}

	public void add(String string) {
		code.append((bCommaNeeded) ? COMMA : "");
		code.append(string);
		bCommaNeeded = true;
	}

	public void start(String string) {
		code.append(NL + string + OPEN_BRACE);
		bCommaNeeded = false;
	}

	public void end() {
		code.append(CLOSE_BRACE + NL);
		bCommaNeeded = false;
	}

	public void finish() {
		System.out.println(code.toString());
	}

}
