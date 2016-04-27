/**
 * Excel语法文件
 */

package com.prism.common.bean;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import jxl.format.CellFormat;
import jxl.write.Label;
import jxl.write.WritableCell;
import jxl.write.WritableCellFeatures;
import jxl.write.WritableSheet;

import com.prism.common.Arith;

public class VMExcel {
	private WritableSheet ws;
	private WritableSheet ws_templete;
	public VMExcel(WritableSheet ws,WritableSheet ws_templete) {
		this.ws = ws;
		this.ws_templete = ws_templete;
	}
	// int c, int r, Object value, int mc, int mr, String type
	public void add(Map<String,Object> map){
		add(toInt(map.get("c")),toInt(map.get("r")),map.get("value"),toInt(map.get("mc")),toInt(map.get("mr")),map.get("type")+"");
	}
	private int toInt(Object obj){
		try{
			return Integer.parseInt(obj+"");
		}catch(Exception e){
			return 0;
		}
	}
	public void add(int c, int r, Object value) {
		add(c, r, value, c, r, "String");
	}

	public void add(int c, int r, Object value, String type) {
		add(c, r, value, c, r, type);
	}

	public void add(int c, int r, Object value, int mc, int mr) {
		add(c, r, value, mc, mr, "String");
	}

	public void add(int c, int r, Object value, int mc, int mr, String type) {
		try {
			WritableCell cell = ws_templete.getWritableCell(mc, mr);
			CellFormat format = cell.getCellFormat();
			if (format == null) {
				ws.addCell(setCell(c, r, value, format, type));
			} else {
				ws.addCell(setCell(c, r, value, format, type));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public boolean eq(Object arg1,Object arg2){
		return arg1.equals(arg2);
	}
	private WritableCell setCell(int c, int r, Object value, CellFormat format,
			String type) {
		WritableCell result = ws.getWritableCell(c, r);
		// 字符串
		if ("String".equals(type)) {
			if (format != null) {
				Label label = new Label(c, r, value + "", format);
				result = label;
			} else {
				Label label = new Label(c, r, value + "");
				result = label;
			}
		}
		// 数字
		if ("Number".equals(type)) {
			if (format != null) {
				jxl.write.Number n = new jxl.write.Number(c, r,
						Arith.getDouble(value), format);
				result = n;
			} else {
				jxl.write.Number n = new jxl.write.Number(c, r,
						Arith.getDouble(value));
				result = n;
			}
		}
		// 函数
		if ("Formula".equals(type)) {
			if (format != null) {
				jxl.write.Formula n = new jxl.write.Formula(c, r, value + "",
						format);
				result = n;
			} else {
				jxl.write.Formula n = new jxl.write.Formula(c, r, value + "");
				result = n;
			}
		}
		// 下拉框
		if ("List".equals(type)) {
			if (format != null) {
				Label label = new Label(c, r, "请选择", format);
				WritableCellFeatures wcf = new WritableCellFeatures();
				List<String> angerlist = new ArrayList<String>();
				String[] values = (value + "").split(",");
				for (int i = 0; i < values.length; i++) {
					angerlist.add(values[i]);
				}
				wcf.setDataValidationList(angerlist);
				label.setCellFeatures(wcf);
			} else {
				Label label = new Label(c, r, "请选择");
				WritableCellFeatures wcf = new WritableCellFeatures();
				List<String> angerlist = new ArrayList<String>();
				String[] values = (value + "").split(",");
				for (int i = 0; i < values.length; i++) {
					angerlist.add(values[i]);
				}
				wcf.setDataValidationList(angerlist);
				label.setCellFeatures(wcf);
			}
		}
		return result;
	}
}
