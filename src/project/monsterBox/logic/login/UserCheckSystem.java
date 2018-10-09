package project.monsterBox.logic.login;

import project.monsterBox.logic.access.AuthenUserFileAccessor;

/**
 * @author morikawahiroki
 *
 */
public class UserCheckSystem {

	private AuthenUserFileAccessor fileAccess;

	/**
	 * ファイルにアクセスする方法に関するオブジェクトをセットする
	 *
	 * @param fileAccess
	 */
	public UserCheckSystem(AuthenUserFileAccessor fileAccess) {
		this.fileAccess = fileAccess;
	}

	/**
	 * ユーザアカウントの存在チェックを行う．
	 *
	 * @param user
	 *            ユーザ情報
	 * @return
	 */
	public boolean doCheck(String userId) {
		return idCheck(userId);
	}

	/**
	 * ユーザが存在しているか確認する
	 *
	 * @param userID
	 * @return
	 */
	public boolean idCheck(String userID) {
		return fileAccess.findByUser(userID);
	}

}
