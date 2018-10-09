package project.monsterBox.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import project.monsterBox.logic.profile.UserProfilingSystem;
import project.monsterBox.model.AllAvatar;
import project.monsterBox.model.UserProfile;

/**
 * アバター設定のサーブレット<br>
 * 受け取ったアバター番号(0~28)に応じた各アバター画像のパス(プロフィール画面基準)を取得する
 *
 * @author k.minamoto
 */
@WebServlet("/top/avatarSet")
public class AvatarServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private AllAvatar allAvatar;

	public void init() {
		this.allAvatar = new AllAvatar();
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String nowAvatarNum = (request.getParameter("nowAvatarNum") == null) ? ""
				: (String) request.getParameter("nowAvatarNum");

		Integer number = -1;

		UserProfilingSystem ups = new UserProfilingSystem((UserProfile) request.getSession().getAttribute("profile"));
		String abatorPath = ups.getAvator().getAbatorFilePath();
		try {
			number = Integer.parseInt(nowAvatarNum);
		} catch (NumberFormatException e) {
		}
		if (0 <= number && number < allAvatar.getAbatorList().size()) {
			abatorPath = allAvatar.getAbatorList().get(number);
			ups.setAbator(number);
			ups.save();
		}

		StringBuilder builder = new StringBuilder();
		builder.append('{');
		builder.append("\"nowAvatar\":\"").append(abatorPath).append("\"");
		builder.append('}');
		String json = builder.toString();
		this.setJson(json, response);
	}

	/**
	 * クライアント側(javascript)に引数のjson文字列をいい感じにして渡す
	 *
	 * @param json
	 * @param response
	 * @throws IOException
	 */
	private void setJson(String json, HttpServletResponse response) throws IOException {
		System.out.println(json);
		response.setContentType("application/json");
		PrintWriter writer = response.getWriter();
		writer.append(json);
		writer.flush();
	}
}
