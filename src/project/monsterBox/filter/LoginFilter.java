package project.monsterBox.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * ログイン認証によって認証されたユーザだけが特定のurlにアクセスすることを許可する ための処理を行うフィルタクラス
 *
 * @author morikawahiroki
 *
 */
@WebFilter(filterName = "/LoginFilter", urlPatterns = { "/top/*", "/top" })
public class LoginFilter implements Filter {

	public void destroy() {

	}

	/**
	 * 許可ユーザだけをユーザ専用ページに通す処理を行う．許可されていないユーザは否応なしにContextRootに戻させる
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		// すでに認証が終えていたらセッションを取得できる．
		HttpSession session = ((HttpServletRequest) request).getSession(false);
		String redirect = ((HttpServletRequest) request).getContextPath();
		// redirectするかどうか
		boolean isRedirect = true;
		// セッションのnullチェックにより認証したかどうか簡易チェック
		if (session != null) {
			// セッションに登録されたidを取得する
			// String userid = (String) session.getAttribute("userid");
			// UserProfile profile = (UserProfile)
			// session.getAttribute("profile");
			// String watched_user =
			// (String)session.getAttribute("watched_user");
			// String watched_filename =
			// (String)session.getAttribute("watched_filename");
			// // セッションの破棄
			// session.invalidate();
			// if (userid != null && profile != null) {
			// // セッションの生成
			// session = ((HttpServletRequest) request).getSession(true);
			// session.setAttribute("userid", userid);
			// session.setAttribute("profile", profile);
			// session.setAttribute("watched_user", watched_user);
			// session.setAttribute("watched_filename", watched_filename);
			// isRedirect = false;
			// }
			isRedirect = false;
		}
		if (isRedirect) {
			System.out.println(
					"認証されていないユーザがサイト" + ((HttpServletRequest) request).getRequestURI() + "にアクセスしようとしたのでトップページに戻ります．");
			((HttpServletResponse) response).sendRedirect(redirect);
		} else {
			System.out.println("認証されたユーザ" + (String) session.getAttribute("userid") + "がサイト"
					+ ((HttpServletRequest) request).getRequestURI() + "にアクセスしました");
			chain.doFilter(request, response);
		}
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
	}

}
