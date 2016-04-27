/**
 * EXCEL通用处理
 */
package com.prism.common;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import jxl.Cell;
import jxl.CellType;
import jxl.LabelCell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;

import com.prism.common.bean.VMExcel;

public class ExcelCommon {
	public static void main(String[] args) throws Exception {
	}

	private VelocityContext vc = new VelocityContext();

	/**
	 * List转Excel
	 * 
	 * @param param
	 *            入参
	 * @param modelPath
	 *            模板文件
	 * @param rule
	 *            规则
	 * @param os
	 *            输出流
	 */
	public void List2Excel(Map<String, Object> param, String modelPath, String rule, OutputStream os) {
		try {
			vc = new VelocityContext();

			Iterator<Map.Entry<String, Object>> it = param.entrySet().iterator();
			while (it.hasNext()) {
				Map.Entry<String, Object> e = (Map.Entry<String, Object>) it.next();
				vc.put(e.getKey(), e.getValue());
			}
			Workbook wb = Workbook.getWorkbook(new File(modelPath));
			WritableWorkbook wwb = Workbook.createWorkbook(os, wb);
			WritableSheet ws = null;

			WritableSheet ws_templete = wwb.getSheet(0);
			if (wwb.getSheetNames().length == 1) {
				wwb.copySheet(0, "Sheet2", 1);
			}
			ws = wwb.getSheet(1);

			VMExcel excel = new VMExcel(ws, ws_templete);
			 vc.put("excel", excel);
			 getResultfromContent(rule);

//			String fileName = "e:/dclass/TT2.java";
//			File file = new File(fileName);
//			try {
//				PrismCompiler pcompiler = new PrismCompiler();
//				Class<?> clazz = pcompiler.compilerRun(file);
//				Method method = clazz.getMethod("run", List.class);
//				@SuppressWarnings("unchecked")
//				List<Map<String, Object>> list = (List<Map<String, Object>>) method.invoke(clazz.newInstance(), param.get("this"));
//				for (Map<String, Object> map : list) {
//					excel.add(map);
//				}
//			} catch (NoSuchMethodException e) {
//				e.printStackTrace();
//			} catch (SecurityException e) {
//				e.printStackTrace();
//			} catch (IllegalAccessException e) {
//				e.printStackTrace();
//			} catch (IllegalArgumentException e) {
//				e.printStackTrace();
//			} catch (InvocationTargetException e) {
//				e.printStackTrace();
//			} catch (InstantiationException e) {
//				e.printStackTrace();
//			}


			wwb.write();
			wwb.close();
			os.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (BiffException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (RowsExceededException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (WriteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * EXCEL结果转换成LIST
	 * 
	 * @param is
	 *            输入流
	 * @param rule
	 *            规则
	 */
	public List<Map<String, String>> Excel2List(InputStream is, String rule) {
		List<Map<String, String>> contents = new ArrayList<Map<String, String>>();
		try {
			vc = new VelocityContext();
			getResultfromContent(rule);
			String sheetName = (String) vc.get("sheetName");
			@SuppressWarnings("unchecked")
			List<String> colsName = (List<String>) vc.get("colsName");
			@SuppressWarnings("unchecked")
			List<Integer> origin = (List<Integer>) vc.get("origin");
			int cols = colsName.size();
			int x = origin.get(0).intValue();
			int y = origin.get(1).intValue();
			Workbook rwb = Workbook.getWorkbook(is);
			Sheet st = rwb.getSheet(sheetName);
			int rows = st.getRows();

			for (int i = x; i < rows; i++) {
				Map<String, String> map = new HashMap<String, String>();
				for (int j = y; j < cols + y; j++) {
					Cell c00 = st.getCell(j, i);
					String strc00 = c00.getContents();
					if (c00.getType() == CellType.LABEL) {
						LabelCell labelc00 = (LabelCell) c00;
						strc00 = labelc00.getString();
					}
					String key = colsName.get(j - y);
					String value = strc00;
					map.put(key, value);
				}
				contents.add(map);
			}
			rwb.close();
			return contents;
		} catch (Exception e) {
			e.printStackTrace();
			return contents;
		}
	}

	private void getResultfromContent(String rule) {
		try {
			StringWriter stringwriter;
			Velocity.init();
			stringwriter = new StringWriter();
			Velocity.evaluate(vc, stringwriter, "mystring", rule);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
