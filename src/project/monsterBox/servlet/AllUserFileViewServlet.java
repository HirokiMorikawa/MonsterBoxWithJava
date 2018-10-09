package project.monsterBox.servlet;

import java.io.IOException;
import java.util.stream.Collectors;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import project.monsterBox.model.UploadFileList;

/**
 *
 * 登録した全てのユーザがアップロードしたファイルの情報を取得するサーブレット
 *
 * @author morikawahiroki
 *
 */
@WebServlet("/top/view")
public class AllUserFileViewServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * title uploadUserId filename
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		//UploadFileList list = (UploadFileList) request.getServletContext().getAttribute("uploadList");
//		StringBuilder sb = new StringBuilder();
//		sb.append("[");
//		int count = 0;
//		for (UploadFile uf : list.uploadFileList()) {
//			sb.append("{");
//			String title = uf.getOwnerComment().getTitle();
//			String id = uf.getUploadUserName();
//			String filename = uf.getFileName();
//			sb.append("\"title\"").append(":").append("\"").append(title).append("\",");
//			sb.append("\"userid\"").append(":").append("\"").append(id).append("\",");
//			sb.append("\"filename\"").append(":").append("\"").append(filename).append("\"");
//			sb.append("}");
//			if (count < list.uploadFileList().size() - 1)
//				sb.append(",");
//			count++;
//		}
//		sb.append("]");
//		String re = list.uploadFileList().stream()
//				.map(e -> String.format("{\"title\":\"%s\",\"userid\":\"%s\",\"filename\":\"%s\"}",
//						e.getOwnerComment().getTitle(), e.getUploadUserName(), e.getFileName()))
//				.collect(Collectors.joining(",", "[", "]"));
		//String re = sb.toString();
		//System.out.println(re);
		request.getSession().setAttribute("update", "on");
		response.setCharacterEncoding("utf-8");
		response.setContentType("application/json");
		response.getWriter().println(((UploadFileList) request.getServletContext().getAttribute("uploadList")).uploadFileList().stream()
				.map(e -> String.format("{\"title\":\"%s\",\"userid\":\"%s\",\"filename\":\"%s\", \"thumbnail\":\"%s\", \"img\":\"%b\"}",
						e.getOwnerComment().getTitle(), e.getUploadUserName(), e.getFileName(), e.getThumbnail(), e.isImage()))
				.collect(Collectors.joining(",", "[", "]")));
	}
}
