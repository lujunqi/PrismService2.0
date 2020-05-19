package com.prism.action;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

@WebServlet("/up/file")
public class PrismStreamFile extends HttpServlet {
	private static final long serialVersionUID = 3L;
	private static final String UP = "upload/";

	@SuppressWarnings("unchecked")
	public void service(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			res.setContentType("text/json; charset=utf-8");
			DiskFileItemFactory factory = new DiskFileItemFactory();
			// 2.创建ServletFileUpload
			ServletFileUpload upload = new ServletFileUpload(factory);
			// 设置上传中文名称乱码
			upload.setHeaderEncoding("utf-8");
			List<FileItem> items = upload.parseRequest(req);

			Map<String, Object> map = new HashMap<String, Object>();

			// 遍历items,得到所有的上传信息
			for (FileItem item : items) {
				if (item.getName() != null) {
					String fn = item.getName();
					// 保存图片
					String fileTyle = fn.substring(fn.lastIndexOf("."), fn.length());
					byte a[] = item.get();
					String key = DigestUtils.md5Hex(a);
					String fileName = key + fileTyle;
					String uploadPath = getServletContext().getRealPath("/") + UP;
					File file = new File(new File(uploadPath), fileName);
					if (!file.exists()) {
						file.createNewFile();
						FileOutputStream fos = new FileOutputStream(file);
						fos.write(a);
						fos.flush();
						fos.close();

					}
					String cp = req.getContextPath() + "/";
					map.put("src", cp + UP + fileName);
					// map.put("_file_byte", item.get());

				} else {
					String name = item.getFieldName();
					String val = item.getString();
					map.put(name, val);
				}
			}

			result.put("code", "0");
			result.put("data", map);

		} catch (Exception e) {
			e.printStackTrace();
			result.put("msg", e.getMessage());
			result.put("code", "1");
		}
		Gson gson2 = new GsonBuilder().enableComplexMapKeySerialization().create();
		res.getWriter().print(gson2.toJson(result));
	}
}
