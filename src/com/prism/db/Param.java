package com.prism.db;

import java.sql.Types;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Param {
	public String sep(boolean hasNext, String sep) {
		if (hasNext) {
			return sep;
		} else {
			return "";
		}
	}

	public List<P> list = new ArrayList<P>();
	public String _str(Object val) {
		return _in(val,Types.VARCHAR);
	}
	public boolean isN(Object obj) {
		
		boolean  result = false;
		
		if(obj == null) {
			result =  true;
		}else {
			if("".equals(obj+"")) {
				result =  true;
			}
		}
		
		
		return result;
	}
	public String _str(Object val,Object def) {
		if(val==null) {
			val = def;
		}
		return _in(val,Types.VARCHAR);
	}
	
	public String _int(Object val,Object def) {
		if(val==null) {
			val = def;
		}
		return _in(val,Types.INTEGER);
	}
	public String _int(Object val) {
		return _in(val,Types.INTEGER);
	}
	
	public String _date(Object val) {
		return _in(val,Types.DATE);
	}
	
	public String _in(Object val, int type) {
		
		P p = new P();
		p.setType(type);
		p.setVal(val);
		list.add(p);
		return "?";
	}

	 
	
	public class P extends HashMap<String, Object> {
		private static final long serialVersionUID = 1L;

		public void setVal(Object val) {
			this.put("val", val);
		}

		public void setType(int type) {
			this.put("type", type);
		}
		public int getType() {
			String s = get("type")+"";
			return new Integer(s).intValue();
		}
		

	}
}
