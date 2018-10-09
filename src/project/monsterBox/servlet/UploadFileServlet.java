package project.monsterBox.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import project.monsterBox.logic.upload.UploadSystem;

/**
 * ユーザから送られてきたファイルをサーバが保存処理するためのサーブレット
 */
@WebServlet("/top/upload")
@MultipartConfig(maxFileSize = 1000000)
public class UploadFileServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public UploadFileServlet() {
		super();
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		this.doSaveFile(request, response);

	}

	/**
	 * ファイルの保存処理を行う
	 *
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	public void doSaveFile(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		String fileTitle = request.getParameter("title");
		String comment = request.getParameter("comment");
		Part part = request.getPart("file");
		// urlのcontectPath左側の取得
		String host = (String) request.getAttribute("host");
		if (fileTitle != null && comment != null) {
			fileTitle = HtmlSanitizingUtil.sanitize(fileTitle);
			comment = HtmlSanitizingUtil.sanitize(comment);
			UploadSystem system = new UploadSystem(host, "top", fileTitle, comment);
			String redirect = system.doUpload(request, part);
			response.sendRedirect(redirect);
		}
	}

}
