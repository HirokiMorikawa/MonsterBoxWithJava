package project.monsterBox.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import project.monsterBox.logic.profile.UserProfilingSystem;
import project.monsterBox.model.Comment;
import project.monsterBox.model.CommentList;
import project.monsterBox.model.OwnerComment;
import project.monsterBox.model.UploadFile;
import project.monsterBox.model.UploadFileList;
import project.monsterBox.model.UserProfile;

/**
 * アップロードファイルの情報をJSONデータで取得するためのサーブレットクラス
 *
 * @author morikawahiroki
 *
 */
@WebServlet("/top/details")
public class DetailsServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * userid title filename time ownerComment otherUsersComments
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		String userid = "";
		String filename = "";
		// 存在しなければnullとなる
		Cookie[] cookies = request.getCookies();
		for (Cookie cookie : cookies) {
			if (cookie.getName().equals("userid")) {
				userid = cookie.getValue();
				System.out.println(cookie.getName() + ":" + cookie.getValue());
			} else if (cookie.getName().equals("filename")) {
				filename = cookie.getValue();
				System.out.println(cookie.getName() + ":" + cookie.getValue());
			}
		}

		/*
		 * 現在，ユーザが見ている他のユーザのアップロードファイルの情報をセッションに追加する
		 */
		request.getSession().setAttribute("watched_user", userid);
		request.getSession().setAttribute("watched_filename", filename);

		UploadFileList ufl = (UploadFileList) request.getServletContext().getAttribute("uploadList");

		UploadFile u = null;

		for (UploadFile uf : ufl.uploadFileList()) {
			if (uf.getOwnerComment().getUserId().equals(userid) && uf.getFileName().equals(filename)) {
				u = uf;
			}
		}

		String returnMess = "";

		StringBuilder sb = new StringBuilder();
		sb.append("{");
		if (u != null) {
			if (((String) request.getSession().getAttribute("update")).equals("on")) {
				UserProfilingSystem ups = new UserProfilingSystem(
						(UserProfile) request.getSession().getAttribute("profile"));
				ups.addWatchHistory(u);
				ups.save();
				request.setAttribute("profile", ups.getUserProfile());
			}

			// JSONデータの生成をおこなう
			String upfname = u.getFileName();
			OwnerComment ownerComment = u.getOwnerComment();
			CommentList comments = u.getCommentList();
			sb.append("\"title\"").append(":").append("\"").append(ownerComment.getTitle()).append("\",");
			sb.append("\"userid\"").append(":").append("\"").append(ownerComment.getUserId()).append("\",");
			sb.append("\"filename\"").append(":").append("\"").append(upfname).append("\",");
			sb.append("\"time\"").append(":").append("\"").append(u.getUploadTime().toString()).append("\",");
			sb.append("\"ownerComment\"").append(":").append("\"").append(ownerComment.getComment()).append("\",");
			sb.append("\"comments\"").append(":").append("[");
			int count = 0;
			for (Comment c : comments.getCommentList()) {
				sb.append("{");
				sb.append("\"comment\"").append(":").append("\"").append(c.getComment()).append("\",");
				sb.append("\"userid\"").append(":").append("\"").append(c.getUserId()).append("\"");
				sb.append("}");
				if (count < comments.getCommentList().size() - 1)
					sb.append(",");
				count++;
			}

			sb.append("],");
			sb.append("\"thumbnail\":\"" + u.getThumbnail() + "\",");
			String isImg = "false";
			if(u.isImage()) {
				isImg = "true";
			}
			sb.append("\"img\":\"" + isImg  + "\"");

		} else {
			sb.append("\"title\":\"\",");
			sb.append("\"userid\":\"\",");
			sb.append("\"filename\":\"\",");
			sb.append("\"time\":\"\",");
			sb.append("\"ownerComment\":\"\",");
			sb.append("\"comments\":[{\"comment\":\"\",\"userid\":\"\"}],");
			sb.append("\"thumbnail\":\"\",");
			sb.append("\"img\":\"" + "false" + "\"");
		}
		sb.append("}");
		returnMess = sb.toString();
		System.out.println(returnMess);
		response.setCharacterEncoding("utf-8");
		response.setContentType("application/json");
		response.getWriter().println(returnMess);
	}

}
