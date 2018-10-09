package project.monsterBox.listener;

import javax.servlet.ServletContext;
import javax.servlet.ServletRequestEvent;
import javax.servlet.ServletRequestListener;
import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpServletRequest;

/**
 * Application Lifecycle Listener implementation class
 * ApplicationRequestListener
 *
 */
@WebListener
public class ApplicationRequestListener implements ServletRequestListener {

	/**
	 * Default constructor.
	 */
	public ApplicationRequestListener() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see ServletRequestListener#requestDestroyed(ServletRequestEvent)
	 */
	public void requestDestroyed(ServletRequestEvent sre) {
		// TODO Auto-generated method stub
	}

	/**
	 * @see ServletRequestListener#requestInitialized(ServletRequestEvent)
	 */
	public void requestInitialized(ServletRequestEvent sre) {

		// requestの取得
		HttpServletRequest request = (HttpServletRequest) sre.getServletRequest();

		// リクエスト先URIの取得
		String requestURL = request.getRequestURL().toString();
		// ここでContextPathより左のパスを取得
		// つまり，プロトコル://ホスト名[他のpath]が得られる
		// この変数を利用することで絶対パスによるルーティング（リダイレクト）の設定を行うことができる.
		request.setAttribute("host", requestURL.split(request.getContextPath())[0] + request.getContextPath());
		request.setAttribute("access", requestURL.split(request.getContextPath())[1]);

		String host = (String) request.getAttribute("host");
		String access = (String) request.getAttribute("access");

		// ログ出力
		ServletContext context = sre.getServletContext();
		context.log("domain + context is " + host);
		context.log("アクセス先 " + access);
	}

}
