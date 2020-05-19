package com.prism.action;

import java.io.IOException;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;

import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;

import com.prism.cut.Cut;
import com.prism.db.Prism;

@WebServlet("/v/*")
public class PrismViewAction extends PrismAction {

	private static final long serialVersionUID = 3025511887438993284L;

	@Override
	protected Map<String, Object> service(Prism prism, Map<String, Object> param) {
		Prism.View view = prism.view();
		
		String template = view.template();
		String text = view.text();
		Map<String,String> var = view.var();
		
		VelocityContext vc = new VelocityContext();
		Map<String, Object> v = new HashMap<String, Object>();
		Cut cut = null;
		if(view.containsKey("ref") && beans.containsBean(view.get("ref")+"")) {
			cut = beans.getBean(view.get("ref")+"", Cut.class);
		}
		if(cut!=null) {
			cut.begin(v);
		}
		vc.put("v", v);
		for (Map.Entry<String, String> en: var.entrySet()) {
			vc.put(en.getKey(), en.getValue());
		}
		getVc(text,vc);
		if(cut!=null) {
			cut.end(v);
		}
		
		req.setAttribute("v", v);
		
		try {
			req.getRequestDispatcher(template).forward(req, res);
		} catch (ServletException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return null;
	}

	private String getVc(String str, VelocityContext vc) {
		Velocity.init();
		StringWriter stringwriter = new StringWriter();

		Velocity.evaluate(vc, stringwriter, "mystring", str);
		return stringwriter.toString();
	}
}
