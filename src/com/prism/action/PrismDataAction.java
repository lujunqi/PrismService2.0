package com.prism.action;

import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.annotation.WebServlet;

import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;

import com.prism.db.Prism;

@WebServlet("/data/*")
public class PrismDataAction extends PrismAction {

	private static final long serialVersionUID = 2406753836864542547L;

	@Override
	protected Map<String, Object> service(Prism prism, Map<String, Object> param) {
		Map<String, Object> result = new HashMap<String, Object>();
		Map<String, Object> db = prism.db(param);
		String _return = prism.getReturn();
		if (_return != null) {
			VelocityContext vc = new VelocityContext();
			result.put("code", "0");
			vc.put("result", result);
			vc.put("db", db);
			vc.put("param", param);
			_return(_return, vc);
			
		} else {
			result.put("code", "0");
			result.putAll(db);
		}
		return result;
	}

	private String _return(String str, VelocityContext vc) {
		Velocity.init();
		StringWriter stringwriter = new StringWriter();

		Velocity.evaluate(vc, stringwriter, "mystring", str);
		return stringwriter.toString();
	}

}
