package project.monsterBox.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import project.monsterBox.logic.access.AuthenUserFileAccessor;
import project.monsterBox.logic.login.SignInSystem;
import project.monsterBox.model.User;

/**
 * アカウントを作成する処理をするサーブレット
 *
 * @author morikawahiroki
 *
 */
@WebServlet("/signin")
public class SignInServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private SignInSystem signInSystem;

	/**
	 * アカウントの作成処理を行う．ユーザIDとパスワードにより作成する．ユーザIDがすでに存在すれば何もしない．
	 * アカウントが作成されると同時にユーザプロファイルも自動生成する． リダイレクトは発生させず，jsonデータによる結果を返す．
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		String userid = request.getParameter("userid");
		String password = request.getParameter("password");

		String returnMess = "アカウントを作成できませんでした";

		if (userid != null && password != null) {

			userid = HtmlSanitizingUtil.sanitize(userid);
			password = HtmlSanitizingUtil.sanitize(password);

			if (signInSystem.doSign_in(new User(userid, password)))
				returnMess = "アカウントを作成しました";
		}
		response.setContentType("application/json");
		response.setCharacterEncoding("utf-8");
		response.getWriter().println("{\"result\":\"" + returnMess + "\"}");
	}

	public void init() {
		this.signInSystem = new SignInSystem(new AuthenUserFileAccessor());
	}

}
