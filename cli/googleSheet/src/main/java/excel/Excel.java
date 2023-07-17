package excel;

import java.io.FileInputStream;
import java.util.HashMap;
import java.util.Vector;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class Excel {

	enum COLUMNS_COL_HEADERS {
		id, label, required, calculated, repeatable
	}

	enum COLUMNS_ROW_HEADERS {
		user_name, text, user_id, email, timestamp
	}

	enum DOCUMENTS_COL_HEADERS {
		id, label, no_of_columns, column_1, column_2, column_3, column_4, column_5, column_6
	}

	enum DOCUMENTS_ROW_HEADERS {
		compaign_001
	}

	static final String EXCEL_FILE_NAME = "C:\\Users\\salim\\OneDrive\\Documents\\GitHub\\serverless-demo\\cli\\googleSheet\\maas_compaign_xlsheet.xlsx";
	private static final int COLUMN_SHEET_NO = 1;

	private static final String enumCOLUMNS_COL_HEADERS = "enum COLUMNS_COL_HEADERS";
	private static final String enumCOLUMNS_ROW_HEADERS = "enum COLUMNS_ROW_HEADERS";;
	private static final String enumDOCUMENTS_COL_HEADERS = "enum DOCUMENTS_COL_HEADERS";
	private static final String enumDOCUMENTS_ROW_HEADERS = "enum DOCUMENTS_ROW_HEADERS";;

	private XSSFWorkbook workbook;

	public Excel() throws Exception {

		FileInputStream file = new FileInputStream(EXCEL_FILE_NAME);
		workbook = new XSSFWorkbook(file);
		file.close();

		Sheet sheet = workbook.getSheetAt(COLUMN_SHEET_NO);
		CodeGen code = new CodeGen();
		code.start(enumCOLUMNS_COL_HEADERS);
		HashMap<COLUMNS_ROW_HEADERS, HashMap<COLUMNS_COL_HEADERS, String>> hRows = new HashMap<COLUMNS_ROW_HEADERS, HashMap<COLUMNS_COL_HEADERS, String>>();
		Vector<COLUMNS_COL_HEADERS> vCols = new Vector<COLUMNS_COL_HEADERS>();
		HashMap<COLUMNS_COL_HEADERS, String> hCols = new HashMap<COLUMNS_COL_HEADERS, String>();
		for (Row row : sheet) {
			if (row.getRowNum() == 0) {
				for (Cell cell : row) {
					try {
						vCols.add(COLUMNS_COL_HEADERS.valueOf(getString(cell)));
						hCols.put(COLUMNS_COL_HEADERS.valueOf(getString(cell)), null);
					} catch (Exception e) {
					}
					code.add(cell.toString());
				}
				code.end();
				code.start(enumCOLUMNS_ROW_HEADERS);
			} else {
				@SuppressWarnings("unchecked")
				HashMap<COLUMNS_COL_HEADERS, String> clonedHcol = (HashMap<COLUMNS_COL_HEADERS, String>) hCols.clone();
				for (Cell cell : row) {
					if (cell.getColumnIndex() == 0) {
						code.add(cell.toString());
						try {
							hRows.put(COLUMNS_ROW_HEADERS.valueOf(getString(cell)), clonedHcol);
						} catch (Exception e) {
						}
						;
					} else {
						clonedHcol.put(vCols.get(cell.getColumnIndex()), getString(cell));
					}
				}
			}
		}
		code.end();
		code.finish();
		workbook.close();
		System.out.println(hRows);
	}

	public Excel(int documentSheetNumber) throws Exception {

		FileInputStream file = new FileInputStream(EXCEL_FILE_NAME);
		workbook = new XSSFWorkbook(file);
		file.close();

		Sheet sheet = workbook.getSheetAt(documentSheetNumber);
		CodeGen code = new CodeGen();
		code.start(enumDOCUMENTS_COL_HEADERS);
		HashMap<DOCUMENTS_ROW_HEADERS, HashMap<DOCUMENTS_COL_HEADERS, String>> hRows = new HashMap<DOCUMENTS_ROW_HEADERS, HashMap<DOCUMENTS_COL_HEADERS, String>>();
		Vector<DOCUMENTS_COL_HEADERS> vCols = new Vector<DOCUMENTS_COL_HEADERS>();
		HashMap<DOCUMENTS_COL_HEADERS, String> hCols = new HashMap<DOCUMENTS_COL_HEADERS, String>();
		for (Row row : sheet) {
			if (row.getRowNum() == 0) {
				for (Cell cell : row) {
					try {
						vCols.add(DOCUMENTS_COL_HEADERS.valueOf(getString(cell)));
						hCols.put(DOCUMENTS_COL_HEADERS.valueOf(getString(cell)), null);
					} catch (Exception e) {
					}
					code.add(cell.toString());
				}
				code.end();
				code.start(enumDOCUMENTS_ROW_HEADERS);
			} else {
				@SuppressWarnings("unchecked")
				HashMap<DOCUMENTS_COL_HEADERS, String> clonedHcol = (HashMap<DOCUMENTS_COL_HEADERS, String>) hCols
						.clone();
				for (Cell cell : row) {
					if (cell.getColumnIndex() == 0) {
						code.add(cell.toString());
						try {
							hRows.put(DOCUMENTS_ROW_HEADERS.valueOf(getString(cell)), clonedHcol);
						} catch (Exception e) {
						}
						;
					} else {
						try {
							clonedHcol.put(vCols.get(cell.getColumnIndex()), getString(cell));
						} catch (Exception e) {
						}
						;
					}
				}
			}
		}
		code.end();
		code.finish();
		workbook.close();
		System.out.println(hRows);
	}

	public static String getString(Cell cell) {
		String string = "";
		switch (cell.getCellType()) {
		case NUMERIC:
		case FORMULA:
			string = "" + cell.getNumericCellValue();
			break;
		case STRING:
			string = cell.getStringCellValue();
			break;
		default:
			break;
		}
		return string.trim();
	}
}
