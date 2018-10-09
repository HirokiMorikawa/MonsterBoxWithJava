package project.monsterBox.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import project.monsterBox.logic.access.AuthenUserFileAccessor;
import project.monsterBox.logic.login.LoginSystem;
import project.monsterBox.model.User;

/**
 * ログイン認証を行うServlet
 *
 * @author morikawahiroki
 *
 */
@WebServlet("/login")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	/**
	 * 認証が成功した時の正しいリダイレクト先のパス
	 */
	private String trueRedirectPath;

	//private AuthSystem auth;
	private LoginSystem loginSystem;
	/**
	 * AuthSystemの初期化処理を行う
	 */
	public LoginServlet() {
		super();
	}

	/**
	 * 認証が成功した時の正しいリダイレクト先のパスを設定する
	 */
	public void init() {
		setTrueRedirectPath("/top");
		loginSystem = new LoginSystem(new AuthenUserFileAccessor());
	}

	/**
	 * 正しいリダイレクト先のパスを設定する
	 *
	 * @param path
	 *            リダイレクト先
	 */
	private void setTrueRedirectPath(String path) {
		this.trueRedirectPath = path;
	}

	/**
	 * 正しいリダイレクト先のパスを取得
	 *
	 * @return 認証が成功した時の正しいリダイレクト先のパ
	 */
	private String getTrueRedirectPath() {
		return trueRedirectPath;
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		String userid = request.getParameter("userid");
		String password = request.getParameter("password");
		String redirect = "";
		if(userid != null && password != null ) {
			redirect = (loginSystem.doLogin(request, new User(userid, password)))? "." + getTrueRedirectPath() : "./";
		}
		response.sendRedirect(redirect);
	}

}
