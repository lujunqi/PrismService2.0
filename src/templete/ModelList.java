package templete;

import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.springframework.stereotype.Component;

@Component("ModelList")
public class ModelList implements Templete {
	public static void main(String[] args) {
		ModelList m = new ModelList();
		String tm = "#set($m={});\n" + "$m.put('k','\"xx');\n"
				+ "$req.put('BUTTON',$m);";
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			m.vc.put("req", map);
			m.getResultfromContent(tm);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private VelocityContext vc = new VelocityContext();

	public void service(Map<String, Object> sourceMap, HttpServletRequest req) {
		for (Map.Entry<String, Object> en : sourceMap.entrySet()) {
			vc = new VelocityContext();
			String key = en.getKey();
			Object val = en.getValue();
			if ("MAPPING".equals(key)) {

				try {
					vc.put("Req", req);
					getResultfromContent(val + "");
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			req.setAttribute(key, val);

		}
	}

	private String getResultfromContent(String s) throws Exception {
		StringWriter stringwriter;
		Velocity.init();
		stringwriter = new StringWriter();
		Velocity.evaluate(vc, stringwriter, "mystring", s);
		return stringwriter.toString();
	}


}
