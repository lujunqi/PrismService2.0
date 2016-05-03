package templete;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class DynamicObj {
	public abstract  List<Map<String,Object>> run(List<Map<String,Object>> list);
	public Map<String,Object> add(int c, int r, Object value) {
		return add(c, r, value, c, r, "String");
	}

	
	public Map<String,Object> add(int c, int r, Object value, String type) {
		return add(c, r, value, c, r, type);
	}

	public Map<String,Object> add(int c, int r, Object value, int mc, int mr) {
		return add(c, r, value, mc, mr, "String");
	}
	public Map<String,Object> add(int c, int r, Object value, int mc, int mr, String type){
		Map<String, Object> m = new HashMap<String, Object>();
		m.put("c", c);
		m.put("r", r);
		m.put("value", value);
		m.put("mc", mc);
		m.put("mr", mr);
		m.put("type", type);
		
		return m;
	}
}
