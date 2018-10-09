package project.monsterBox.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.StringJoiner;
import java.util.stream.Collectors;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import project.monsterBox.model.UploadFileList;
import project.monsterBox.model.UserProfile;

/**
 * ユーザのプロファイル情報を取得するサーブレット
 *
 * @author morikawahiroki
 *
 */
@WebServlet("/top/profile")
public class ProfileServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		UserProfile profile = (UserProfile) request.getSession().getAttribute("profile");
		// アップロード履歴
		UploadFileList uploadHistory = profile.getUploadHistory();
		// 閲覧履歴
		UploadFileList watchHistory = profile.getWatchHistory();
		// お気に入りリスト
		UploadFileList favoriteList = profile.getUserFavoriteList();

		StringJoiner joiner = new StringJoiner(",","{\"userid\":\""+ profile.getUser().getLoginID()+"\"," ,"}");

		joiner.add(uploadHistory.uploadFileList().stream().map(e -> String.format("{\"title\":\"%s\",\"userid\":\"%s\",\"filename\":\"%s\", \"thumbnail\":\"%s\", \"img\":\"%b\"}",
				e.getOwnerComment().getTitle(), e.getUploadUserName(), e.getFileName(), e.getThumbnail(), e.isImage())).collect(Collectors.joining(",", "\"uploadHistory\":[", "]")));

		joiner.add(watchHistory.uploadFileList().stream().map(e -> String.format("{\"title\":\"%s\",\"userid\":\"%s\",\"filename\":\"%s\", \"thumbnail\":\"%s\", \"img\":\"%b\"}",
				e.getOwnerComment().getTitle(), e.getUploadUserName(), e.getFileName(), e.getThumbnail(), e.isImage())).collect(Collectors.joining(",", "\"watchHistory\":[", "]")));

		joiner.add(favoriteList.uploadFileList().stream().map(e -> String.format("{\"title\":\"%s\",\"userid\":\"%s\",\"filename\":\"%s\",\"thumbnail\":\"%s\", \"img\":\"%b\"}",
				e.getOwnerComment().getTitle(), e.getUploadUserName(), e.getFileName(), e.getThumbnail(), e.isImage())).collect(Collectors.joining(",", "\"favoriteList\":[", "]")));

		System.out.println(joiner.toString());
		request.getSession().setAttribute("update", "on");
		response.setCharacterEncoding("utf-8");
		response.setContentType("application/json");

		PrintWriter pw = response.getWriter();
		pw.println(joiner.toString());
		pw.flush();
		pw.close();
	}
}
