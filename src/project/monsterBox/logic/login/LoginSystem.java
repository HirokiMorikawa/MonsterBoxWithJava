package project.monsterBox.logic.login;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import project.monsterBox.logic.access.AuthenUserFileAccessor;
import project.monsterBox.logic.profile.UserProfileCreator;
import project.monsterBox.logic.profile.UserProfilingSystem;
import project.monsterBox.model.User;

/**
 * ユーザ認証を行うクラス
 *
 * @author morikawahiroki
 *
 */
public class LoginSystem {

	private AuthenUserFileAccessor fileAccess;

	/**
	 * ファイルにアクセスする方法に関するオブジェクトをセットする
	 *
	 * @param fileAccess
	 */
	public LoginSystem(AuthenUserFileAccessor fileAccess) {
		this.fileAccess = fileAccess;
	}

	/**
	 * ログイン処理を実行するメソッド．ログインして認証された時に何かのエラーでユーザプロファイルが生成されていなければ，初期化されたユーザプロファイルを生成する．
	 * ログイン処理中，ユーザプロファイルを読み込み，そのデータをHttpSessionに"profile"として格納する．同時に"userid"としてユーザIDを格納する．
	 * ログイン処理が終了した時，認証が成功したときと失敗したときtrueかfalseで値を返す.
	 *
	 * @param request
	 *            リクエスト
	 * @param user
	 *            ユーザ情報
	 * @return ．
	 */
	public boolean doLogin(HttpServletRequest request, User user) {
		String userid = user.getLoginID();
		// 認証成功
		if (login(user)) {
			System.out.println("アカウント:" + userid + "を持つユーザがログインしました");
			// セッション生成
			HttpSession session = request.getSession(true);
			// セッションにパラメータを追加
			session.setAttribute("userid", userid);

			// ユーザのプロファイルを読み込む
			UserProfilingSystem ups = new UserProfilingSystem(userid);

			// プロファイルが生成されていない時，プロファイルを生成する
			if (!ups.hasUserProfile()) {
				// プロファイルが存在しない時生成する
				ups.createUserProfileIfNotExist(new UserProfileCreator(user).createUserProfile());
				// ユーザ情報の更新
				ups.getUserProfile().setUser(user);
				// コミット
				ups.save();
			}
			request.getSession().setAttribute("profile", ups.getUserProfile());
			// System.out.println(up.getUserInformation());
			// 認証処理が終了したのでユーザのポータルページに行く
			return true;
		}
		// 認証に失敗したらコンテキストルート/に戻る
		else {
			return false;
		}
	}


	/**
	 * ログイン認証
	 *
	 * ログインIDとパスワードを入力すると認証結果が通知される
	 *
	 * ユーザーリストが存在していない時false, loginIDもしくは，passwordがnullのとき常にfalseを返す
	 *
	 * @return true -> 認証成功 :false -> 認証失敗
	 */
	private boolean login(User user) {
		List<User> userList = fileAccess.findAll();
		if (userList != null) {
			boolean exist = false;
			for (User u : userList) {
				if (u.getLoginID().equals(user.getLoginID()) && u.getPassword().equals(user.getPassword())) {
					exist = true;
				}
			}
			return exist;
		} else
			return false;
	}
}
