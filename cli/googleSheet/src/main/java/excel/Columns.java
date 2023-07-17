package excel;

public class Columns {
	public class Text {
		private String getLabel() {
			return "Memo";
		}

		private boolean isRequired() {
			return true;
		}

		private boolean isCalculated() {
			return false;
		}

		private boolean isRepeatable() {
			return true;
		}
	}

	public class UserName {
		private String getLabel() {
			return "Name";
		}

		private boolean isRequired() {
			return false;
		}

		private boolean isCalculated() {
			return true;
		}

		private boolean isRepeatable() {
			return false;
		}
	}

	public class Timestamp {
		private String getLabel() {
			return "Updated on";
		}

		private boolean isRequired() {
			return false;
		}

		private boolean isCalculated() {
			return true;
		}

		private boolean isRepeatable() {
			return false;
		}
	}

	public class Email {
		private String getLabel() {
			return "Email";
		}

		private boolean isRequired() {
			return false;
		}

		private boolean isCalculated() {
			return true;
		}

		private boolean isRepeatable() {
			return false;
		}
	}

	public class UserId {
		private String getLabel() {
			return "ID";
		}

		private boolean isRequired() {
			return false;
		}

		private boolean isCalculated() {
			return true;
		}

		private boolean isRepeatable() {
			return false;
		}
	}
}
