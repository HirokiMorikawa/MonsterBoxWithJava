package project.monsterBox.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import project.monsterBox.model.Comment;
import project.monsterBox.model.UploadFile;
import project.monsterBox.model.UploadFileList;

/**
 * アップロードされたファイルに対してユーザのコメントを登録するサーブレット
 */
@WebServlet("/top/postComment")
public class PostCommentServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		String userid = (String) request.getSession().getAttribute("userid");
		String comment = (String) request.getParameter("comment") != null ? (String) request.getParameter("comment")
				: "";

		String watchedUser = "";
		String watchedFilename = "";
		// 存在しなければnullとなる
		Cookie[] cookies = request.getCookies();
		//クッキー情報からアクセスしているファイルを取得する
		for (Cookie cookie : cookies) {
			if (cookie.getName().equals("userid")) {
				watchedUser = cookie.getValue();
				System.out.println(cookie.getName() + ":" + cookie.getValue());
			} else if (cookie.getName().equals("filename")) {
				watchedFilename = cookie.getValue();
				System.out.println(cookie.getName() + ":" + cookie.getValue());
			}
		}

		UploadFileList ufl = (UploadFileList) request.getServletContext().getAttribute("uploadList");
		UploadFile u = null;
		int point = -1;
		for (UploadFile uf : ufl.uploadFileList()) {
			if (uf.getOwnerComment().getUserId().equals(watchedUser) && uf.getFileName().equals(watchedFilename)) {
				u = uf;
				point = ufl.uploadFileList().indexOf(uf);
			}
		}

		//String returmMess = "コメントは投稿されませんでした";
		if (u != null && !comment.equals("")) {
			request.getSession().setAttribute("update", "off");
			//簡易的なサニタイズ
			comment = HtmlSanitizingUtil.sanitize(comment);

			System.out.println("ユーザ" + userid + "はコメント「" + comment + "」をファイル「" + watchedFilename + "」に投稿しました．");

			u.getCommentList().addComment(new Comment(userid,comment));
			ufl.uploadFileList().remove(point);
			ufl.uploadFileList().add(point, u);
			request.getServletContext().setAttribute("uploadList", ufl);

			//
			response.sendRedirect("./details.html");
		}

	}

}
