package com.mx.util;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

public class ExcelUtil {

	public static void writeExcel(File file, List<TableRow> tableRows) throws Exception {
		if (file.exists()) {
			file.delete();
		}
		file.createNewFile();
		// 创建工作簿

		WritableWorkbook workbook = Workbook.createWorkbook(file);
		// 创建sheet
		WritableSheet sheet = workbook.createSheet("sheet1", 0);
		Label label = null;

		// 第一行设置列名
		for (int i = 0; i < tableRows.size(); i++) {
			List<String> columnvalue = tableRows.get(i).getColumnValue();
			for (int j = 0; j < columnvalue.size(); j++) {
				label = new Label(j, i, columnvalue.get(j));
				sheet.addCell(label);
			}
		}
		// 写入数据
		workbook.write();
		workbook.close();
	}

	// 去读Excel的方法readExcel，该方法的入口参数为一个File对象
	public static List<String> readExcel(File f) throws BiffException, IOException {
		if (!f.exists()) {
			throw new IllegalAccessError("文件不存在");
		}
		List<String> result = new ArrayList<>();
		Workbook book = Workbook.getWorkbook(f);//
		Sheet[] sheets = book.getSheets(); // 获得第一个工作表对象
		for (Sheet sheet : sheets) {
			for (int i = 0; i < sheet.getRows(); i++) {
				for (int j = 0; j < sheet.getColumns(); j++) {
					Cell cell = sheet.getCell(j, i); // 获得单元格
					if (cell.getContents().equals("")) {
						continue;
					}
					result.add(cell.getContents());
				}
			}
		}
		return result;
	}

	public static void main(String[] args) throws BiffException, IOException {
		List<TableRow> datalist = ExcelUtil.readExcelRow(new File("D:\\batch workspace\\pangzong\\settaocan.xls"));

		for (TableRow tableRow : datalist) {
			System.out.println(tableRow.getParamstr());
		}
	}

	// 去读Excel的方法readExcel，一行一行读取数据
	public static List<TableRow> readExcelRow(File f) throws BiffException, IOException {
		if (!f.exists()) {
			throw new IllegalAccessError("文件不存在");
		}
		List<TableRow> result = new ArrayList<>();
		Workbook book = Workbook.getWorkbook(f);//
		// Sheet sheet = book.getSheet(0); // 获得第一个工作表对象
		Sheet[] sheets = book.getSheets(); // 获得第一个工作表对象
		for (Sheet sheet : sheets) {
			String[] header = new String[sheet.getColumns()];
			for (int i = 0; i < sheet.getRows(); i++) {
				TableRow tableRow = new TableRow();

				for (int j = 0; j < sheet.getColumns(); j++) {
					Cell cell = sheet.getCell(j, i); // 获得单元格
					if (cell.getContents().equals("")) {
						continue;
					}
					if (i == 0) {
						header[j] = cell.getContents();

					} else {

						tableRow.append(header[j] + "=" + cell.getContents() + "&");
					}

					tableRow.getColumnValue().add(cell.getContents());

				}
				result.add(tableRow);
			}
		}

		return result;
	}

	public static List<TableRow> readExcelRowFilePath(File f) throws BiffException, IOException {
		if (!f.exists()) {
			throw new IllegalAccessError("文件不存在");
		}
		List<TableRow> result = new ArrayList<>();
		Workbook book = Workbook.getWorkbook(f);//
		Sheet sheet = book.getSheet(0);
		for (int i = 0; i < sheet.getRows(); i++) {
			TableRow tableRow = new TableRow();

			for (int j = 0; j < sheet.getColumns(); j++) {
				Cell cell = sheet.getCell(j, i);
				if (cell.getContents().equals("")) {
					continue;
				}
				tableRow.append(cell.getContents() + "/");

				tableRow.getColumnValue().add(cell.getContents());

			}
			result.add(tableRow);
		}

		return result;
	}

}
