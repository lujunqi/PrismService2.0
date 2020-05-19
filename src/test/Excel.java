package test;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.StringWriter;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;

public class Excel {
	@SuppressWarnings("resource")
	public static void main(String[] args) {
		try {
			File file = new File("d:/xx.xlsx");
			long fileSize = file.length();
			FileInputStream in = new java.io.FileInputStream(file);
			byte[] buffer = new byte[(int) fileSize];
			int offset = 0;
			int numRead = 0;
			while (offset < buffer.length && (numRead = in.read(buffer, offset, buffer.length - offset)) >= 0) {
				offset += numRead;
			}

			in.close();

			Workbook wb = null;
			try {
				wb = new HSSFWorkbook(new ByteArrayInputStream(buffer));
			} catch (Exception e) {
				wb = new XSSFWorkbook(new ByteArrayInputStream(buffer));

			}
			Map<String, String> map = new HashMap<String, String>();
			Sheet data = wb.getSheet("data");
			VelocityContext vc = new VelocityContext();
			for (int i = 1; i < data.getPhysicalNumberOfRows(); i++) {
				Row row = data.getRow(i);
				String key = getCellValue(row.getCell(0));
				String val = getCellValue(row.getCell(1));
				map.put(key, val);

			}
			Sheet sheet = wb.getSheet("Sheet1");
			Excel excel = new Excel();
			excel.map = map;
			vc.put("v", excel);

			Velocity.init();

			for (int i = 1; i < sheet.getPhysicalNumberOfRows(); i++) {
				Row row = sheet.getRow(i);
				if (row != null) {
					for (int j = 0; j < row.getLastCellNum(); j++) {
						Cell cell = row.getCell(j);
						String val = getCellValue(cell);
						System.out.println(val);
						if (val.indexOf("$") != -1) {
							StringWriter stringwriter = new StringWriter();
							Velocity.evaluate(vc, stringwriter, "mystring", val);
							String sw = stringwriter.toString();

							if (excel.isNum(sw)) {
								cell.setCellType(CellType.NUMERIC);
								cell.setCellValue(Double.parseDouble(sw));
							} else {
								cell.setCellValue(sw);
							}

						}

					}
				}

			}
			FileOutputStream out = new FileOutputStream("d:\\workbook.xlsx");

			wb.write(out);
			out.flush();
			System.out.println(map);
		} catch (IOException e1) {
			e1.printStackTrace();
		}

	}

	private Map<String, String> map = new HashMap<String, String>();

	private boolean isNum(String val) {
		try {
			Double.parseDouble(val);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public double fw(double begin, double end) {// 范围
		int total = 0;
		for (Map.Entry<String, String> en : map.entrySet()) {
			double cj = Double.parseDouble(en.getValue());
			if(cj>begin && cj<=end) {
				total++;
			}
		}
		return total;
	}

	public double fw100(double begin, double end) {// 范围百分百
		int total = 0;
		for (Map.Entry<String, String> en : map.entrySet()) {
			double cj = Double.parseDouble(en.getValue());
			if(cj>begin && cj<=end) {
				total++;
			}
		}
		 
//		double res = new BigDecimal(null).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
		return total;
	}

	
	public double heji() {// 合计
		double total = 0;
		for (Map.Entry<String, String> en : map.entrySet()) {
			total += Double.parseDouble(en.getValue());
		}
		return total;
	}

	public double hj() {// 合计
		return heji();
	}

	public double pingjun() {// 平均
		double total = heji();
		double data = total / map.size();
		double res = new BigDecimal(data).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
		return res;
	}

	public double pj() {// 平均
		return pingjun();
	}

	public String chengji(String key) {// 绑定成绩
		if (map.containsKey(key)) {
			return map.get(key);
		} else {
			return "0";
		}
	}

	public String cj(String key) {// 绑定成绩
		return chengji(key);
	}

	public static String getCellValue(Cell cell) {
		String cellValue = "";
		if (cell == null) {
			return "";
		}
		// 以下是判断数据的类型
		switch (cell.getCellTypeEnum()) {
		case NUMERIC: // 数字
			if (org.apache.poi.ss.usermodel.DateUtil.isCellDateFormatted(cell)) {
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				cellValue = sdf.format(org.apache.poi.ss.usermodel.DateUtil.getJavaDate(cell.getNumericCellValue()))
						.toString();
			} else {
				DataFormatter dataFormatter = new DataFormatter();
				cellValue = dataFormatter.formatCellValue(cell);
			}
			break;
		case STRING: // 字符串
			cellValue = cell.getStringCellValue();
			break;
		case BOOLEAN: // Boolean
			cellValue = cell.getBooleanCellValue() + "";
			break;
		case FORMULA: // FORMULA
			cellValue = cell.getCellFormula();
			break;
		default:
			cellValue = "未知类型";
			break;
		}
		return cellValue;
	}

}
