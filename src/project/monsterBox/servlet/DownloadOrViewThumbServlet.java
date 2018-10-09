package project.monsterBox.servlet;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import project.monsterBox.model.UploadFile;
import project.monsterBox.model.UploadFileList;

/**
 * ユーザがダウンロードボタンを押した時に，もしくはthumnail画像を表示する時に，リクエストされるサーブレット．このサーブレットでは，ユーザのSession情報を元に
 * 指定のファイルを読み込み，レスポンスにそのファイルを含める処理をおこなう.
 */
@WebServlet("/top/DorT")
public class DownloadOrViewThumbServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String param = (String) request.getParameter("param");
		String userid = (String) request.getParameter("userid");
		String filename = (String) request.getParameter("filename");
		if (param == null) {
			param = "D";
		}
		if (userid == null && filename == null) {
			userid = (String) request.getSession().getAttribute("watched_user");
			filename = (String) request.getSession().getAttribute("watched_filename");
		}
		UploadFileList ufl = (UploadFileList) request.getServletContext().getAttribute("uploadList");
		UploadFile u = null;

		for (UploadFile uf : ufl.uploadFileList()) {
			if (uf.getOwnerComment().getUserId().equals(userid) && uf.getFileName().equals(filename)) {
				u = uf;
			}
		}
		// ---------------------------------------------
		// <<<<<<<<<ここからファイル読み処理>>>>>>>>>>>>>>>>>
		// ---------------------------------------------
		Path path = Paths.get("auth/" + userid + "_dir/" + filename);

		if (Files.exists(path)) {
			BufferedInputStream bis = new BufferedInputStream(Files.newInputStream(path));
			if (param.equals("T")) {
				response.setContentType(u.getMimeType());
			} else {
				// ファイルダウンロードのためのヘッダを設定する
				response.setContentType("application/octet-stream");
				response.setHeader("Content-Disposition", "attachment; filename=" + u.getFileName());
				// response.setContentLength((int) Files.size(path));
			}
			int read = -1;
			// ---------------------------------------------
			// <<<<<<<<<ここからファイル出力処理>>>>>>>>>>>>>>>>>
			// ---------------------------------------------
			BufferedOutputStream bos = new BufferedOutputStream(response.getOutputStream());
			while ((read = bis.read()) != -1) {
				bos.write(read);
			}
			bos.flush();

			bis.close();
			bos.close();

		}

	}

}
