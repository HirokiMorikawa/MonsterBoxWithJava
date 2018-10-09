package project.monsterBox.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * 認証を完了させたユーザがログアウトを実行するときの処理を実施するためのサーブレット
 *
 * @author morikawahiroki
 *
 */
@WebServlet("/logout")
public class LogoutServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public LogoutServlet() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession(false);
		if (session != null) {
			String id = (String) session.getAttribute("userid");
			// セッションの破棄を行う
			session.invalidate();
			System.out.println("アカウント:" + id + "を持つユーザがログアウトしました");
		}
		response.sendRedirect((String) request.getAttribute("host") + "/");
	}

}
