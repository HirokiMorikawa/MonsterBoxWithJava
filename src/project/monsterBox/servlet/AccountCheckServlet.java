package project.monsterBox.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import project.monsterBox.logic.access.AuthenUserFileAccessor;
import project.monsterBox.logic.login.UserCheckSystem;


/**
 * 入力されたログインIDが存在しているかどうかチェックするサーブレット
 */
@WebServlet("/check")
public class AccountCheckServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private UserCheckSystem userCheckSystem;

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String userid = (request.getParameter("userid") != null) ? request.getParameter("userid") : "";
		String returnMess = "";
		if (userCheckSystem.doCheck(userid)) {
			returnMess = "アカウントがすでに存在しています";
		}
		response.setContentType("application/json");
		response.setCharacterEncoding("utf-8");
		response.getWriter().println("{\"result\":\"" + returnMess + "\"}");
	}

	public void init() {
		userCheckSystem = new UserCheckSystem(new AuthenUserFileAccessor());
	}

}
