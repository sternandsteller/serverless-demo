package excel;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Vector;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class Excel {
	private static final String enumCOLUMNS_COL_HEADERS = "enum COLUMNS_COL_HEADERS";
	private static final String enumCOLUMNS_ROW_HEADERS = "enum COLUMNS_ROW_HEADERS";;
	private static final String enumDOCUMENTS_COL_HEADERS = "enum DOCUMENTS_COL_HEADERS";
	private static final String enumDOCUMENTS_ROW_HEADERS = "enum DOCUMENTS_ROW_HEADERS";
	private static final String COLUMN_SHEET_NAME = "columns";
	private static final String DOCUMENTS_SHEET_NAME = "documents";

	enum COLUMNS_COL_HEADERS {
		id, label, required, calculated, repeatable
	}

	enum COLUMNS_ROW_HEADERS {
		user_name, text, user_id, email, timestamp
	}

	enum DOCUMENTS_COL_HEADERS {
		id, label, column_1, column_2, column_3, column_4, column_5, column_6
	}

	enum DOCUMENTS_ROW_HEADERS {
		compaign_001
	}

	static final String EXCEL_FILE_NAME = "C:\\Users\\salim\\OneDrive\\Documents\\GitHub\\serverless-demo\\cli\\googleSheet\\maas_compaign_xlsheet.xlsx";
	private static final String COLUMNS_JAVA = "Columns.java";
	private static final String C_JAVA = "C.java";

	Excel() throws Exception {
		XSSFWorkbook workbook = open(EXCEL_FILE_NAME);
		HashMap<COLUMNS_ROW_HEADERS, LinkedHashMap<COLUMNS_COL_HEADERS, String>> columns = getColumns(workbook);
		getClasses(columns);
		close(workbook);
	}

	private void getClasses(HashMap<COLUMNS_ROW_HEADERS, LinkedHashMap<COLUMNS_COL_HEADERS, String>> columns)
			throws IOException {
		CodeGen code = new CodeGen();
		code.start(CodeGen.PUBLIC_CLASS, CodeGen.COLUMNS);
		for (COLUMNS_ROW_HEADERS col : columns.keySet()) {
			code.start(CodeGen.PUBLIC_CLASS, col.toString());
			HashMap<COLUMNS_COL_HEADERS, String> hCol = columns.get(col);
			for (COLUMNS_COL_HEADERS attributes : hCol.keySet()) {
				code.getter(attributes.toString(), hCol.get(attributes));
			}
			code.end();
		}
		code.end();
		code.finish(COLUMNS_JAVA);
	}

	public HashMap<DOCUMENTS_ROW_HEADERS, HashMap<COLUMNS_ROW_HEADERS, LinkedHashMap<COLUMNS_COL_HEADERS, String>>> getDocumentColumns(
			XSSFWorkbook workbook, HashMap<DOCUMENTS_ROW_HEADERS, HashMap<DOCUMENTS_COL_HEADERS, String>> documents,
			HashMap<COLUMNS_ROW_HEADERS, LinkedHashMap<COLUMNS_COL_HEADERS, String>> columns) throws Exception {

		HashMap<DOCUMENTS_ROW_HEADERS, HashMap<COLUMNS_ROW_HEADERS, LinkedHashMap<COLUMNS_COL_HEADERS, String>>> documentsColumns = new HashMap<DOCUMENTS_ROW_HEADERS, HashMap<COLUMNS_ROW_HEADERS, LinkedHashMap<COLUMNS_COL_HEADERS, String>>>();

		for (DOCUMENTS_ROW_HEADERS document : documents.keySet()) {
			HashMap<COLUMNS_ROW_HEADERS, LinkedHashMap<COLUMNS_COL_HEADERS, String>> hDocumentColumn = new HashMap<COLUMNS_ROW_HEADERS, LinkedHashMap<COLUMNS_COL_HEADERS, String>>();
			for (DOCUMENTS_COL_HEADERS documentColumn : documents.get(document).keySet()) {
				COLUMNS_ROW_HEADERS columnRowHeader = COLUMNS_ROW_HEADERS.valueOf(documentColumn.toString());
				hDocumentColumn.put(columnRowHeader, columns.get(columnRowHeader));
			}
			documentsColumns.put(document, hDocumentColumn);
		}
		return documentsColumns;
	}

	public HashMap<COLUMNS_ROW_HEADERS, LinkedHashMap<COLUMNS_COL_HEADERS, String>> getColumns(XSSFWorkbook workbook)
			throws Exception {

		FileInputStream file = new FileInputStream(EXCEL_FILE_NAME);
		workbook = new XSSFWorkbook(file);
		file.close();

		Sheet sheet = workbook.getSheet(COLUMN_SHEET_NAME);
		CodeGen code = new CodeGen();
		code.start(enumCOLUMNS_COL_HEADERS);
		HashMap<COLUMNS_ROW_HEADERS, LinkedHashMap<COLUMNS_COL_HEADERS, String>> hRows = new HashMap<COLUMNS_ROW_HEADERS, LinkedHashMap<COLUMNS_COL_HEADERS, String>>();
		Vector<COLUMNS_COL_HEADERS> vCols = new Vector<COLUMNS_COL_HEADERS>();
		LinkedHashMap<COLUMNS_COL_HEADERS, String> hCols = new LinkedHashMap<COLUMNS_COL_HEADERS, String>();
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
				LinkedHashMap<COLUMNS_COL_HEADERS, String> clonedHcol = (LinkedHashMap<COLUMNS_COL_HEADERS, String>) hCols
						.clone();
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
		code.finish(C_JAVA);
		workbook.close();
		return hRows;
	}

	public XSSFWorkbook open(String excelFileName) throws Exception {
		FileInputStream file = new FileInputStream(excelFileName);
		XSSFWorkbook workbook = new XSSFWorkbook(file);
		file.close();
		return workbook;
	}

	public void close(XSSFWorkbook workbook) throws IOException {
		workbook.close();
	}

	public void getDocuments(XSSFWorkbook workbook) throws Exception {

		HashMap<DOCUMENTS_ROW_HEADERS, HashMap<DOCUMENTS_COL_HEADERS, String>> hRows = new HashMap<DOCUMENTS_ROW_HEADERS, HashMap<DOCUMENTS_COL_HEADERS, String>>();
		Sheet sheet = workbook.getSheet(DOCUMENTS_SHEET_NAME);
		CodeGen code = new CodeGen();
		code.start(enumDOCUMENTS_COL_HEADERS);
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
						} catch (Exception e) {
						}
						;
					}
				}
			}
		}
		code.end();
		code.finish(C_JAVA);
		workbook.close();
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
