package project.monsterBox.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import project.monsterBox.logic.profile.UserProfilingSystem;
import project.monsterBox.model.UploadFile;
import project.monsterBox.model.UploadFileList;
import project.monsterBox.model.UserProfile;

/**
 * ユーザがお気に入りボタンを押した時，ユーザプロファイルにお気に入り情報を追加する処理をするサーブレット
 *
 * @author morikawahiroki
 *
 */
@WebServlet("/top/pushFavorite")
public class FaboriteButtonServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String userid = (String) request.getSession().getAttribute("watched_user");
		String filename = (String) request.getSession().getAttribute("watched_filename");

		UploadFileList ufl = (UploadFileList) request.getServletContext().getAttribute("uploadList");
		UploadFile u = null;
		int index = -1;
		for (UploadFile uf : ufl.uploadFileList()) {
			if (uf.getOwnerComment().getUserId().equals(userid) && uf.getFileName().equals(filename)) {
				u = uf;
				index = ufl.uploadFileList().indexOf(uf);
			}
		}

		/*
		 * お気に入り登録処理をおこなう
		 */
		if (u != null) {
			UserProfile profile = (UserProfile) request.getSession().getAttribute("profile");
			UserProfilingSystem ups = new UserProfilingSystem(profile);
			ups.addFavorite(u);
			ups.save();
			request.getSession().setAttribute("profile", profile);

			/*
			 * uploadFileを更新した値に変換する処理
			 */
			ufl.uploadFileList().remove(index);

			u.getFollowUserList().addFollowUser(profile);

			ufl.uploadFileList().add(index, u);

			request.getServletContext().setAttribute("uploadList", ufl);

		}

		request.getSession().setAttribute("update", "off");

		response.sendRedirect("./details.html");

	}

}
