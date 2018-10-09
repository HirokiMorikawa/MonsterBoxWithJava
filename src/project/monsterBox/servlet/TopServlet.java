package project.monsterBox.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import project.monsterBox.model.UploadFile;
import project.monsterBox.model.UploadFileList;
import project.monsterBox.model.UserProfile;

/**
 * ログインユーザ用ポータルページのためのサーブレット
 *
 * @author morikawahiroki
 *
 */
@WebServlet(urlPatterns = { "/top/info" })
public class TopServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public TopServlet() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		// String param = request.getParameter("param");
		UserProfile profile = (UserProfile) request.getSession().getAttribute("profile");
		UploadFileList upList = (UploadFileList) request.getServletContext().getAttribute("uploadList");
		/*
		 * ユーザIDをレスポンスとして返す
		 */
		String userIdJson = String.format("{\"userid\":\"%s\", \"nowAvatar\":\"%s\"", profile.getUser().getLoginID(),
				profile.getAbator().getAbatorFilePath());

		List<UploadFile> fileList = new ArrayList<UploadFile>();
		/*
		 * ランキングの情報を取得する
		 */
		List<UploadFile> rankingList = new ArrayList<UploadFile>();
		for (UploadFile uf : profile.getUploadHistory().uploadFileList()) {
			fileList.add(uf);
		}
		for (UploadFile uf : upList.uploadFileList()) {
			rankingList.add(uf);
		}

		Collections.reverse(fileList);

		Collections.sort(rankingList, new Comparator<UploadFile>() {
			@Override
			public int compare(UploadFile obj1, UploadFile obj2) {
				return obj2.getFollowUserList().getFollowUserList().size()
						- obj1.getFollowUserList().getFollowUserList().size();
			}
		});

		// 0~4番目の要素からjsonデータを生成する
		String fileListJson = fileList.stream().filter(e -> fileList.indexOf(e) < 5).collect(Collectors.toList())
				.stream()
				.map(e -> String.format(
						"{\"userid\":\"%s\",\"title\":\"%s\",\"filename\":\"%s\", \"thumbnail\":\"%s\", \"img\":\"%b\"}",
						e.getOwnerComment().getUserId(), e.getOwnerComment().getTitle(), e.getFileName(),
						e.getThumbnail(), e.isImage()))
				.collect(Collectors.joining(",", ",\"fileList\":[", "]"));

		String rankingJson = rankingList.stream().filter(e -> rankingList.indexOf(e) < 10)
				.map(e -> String.format(
						"{\"userid\":\"%s\",\"title\":\"%s\",\"filename\":\"%s\", \"thumbnail\":\"%s\", \"img\":\"%b\"}",
						e.getOwnerComment().getUserId(), e.getOwnerComment().getTitle(), e.getFileName(),
						e.getThumbnail(), e.isImage()))
				.collect(Collectors.joining(",", ",\"rankingList\":[", "]}"));
		request.getSession().setAttribute("update", "on");
		response.setContentType("application/json");
		response.setCharacterEncoding("utf-8");
		response.getWriter().print(userIdJson + fileListJson + rankingJson);
	}
}
