package project.monsterBox.model;

import java.io.Serializable;

/**
 *
 * ユーザの基本的な情報つまり，ユーザIDとパスワードを扱うユーザモデル
 *
 * @author morikawahiroki
 *
 */
public class User implements Serializable {

	/**
	 * ユーザがアカウントを作成した時に設定されるログインID
	 */
	private String loginID;
	/**
	 * ユーザがアカウントを作成した時に設定されるパスワード
	 */
	private String password;

	public User() {
	}
	/**
	 * フィールドの初期化
	 * @param loginID
	 * @param password
	 */
	public User(String loginID, String password) {
		this.loginID = loginID;
		this.password = password;
	}

	public void setLoginID(String loginID) {
		this.loginID = loginID;
	}

	public String getLoginID() {
		return loginID;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPassword() {
		return password;
	}

	public boolean equals(Object o) {
		if (o instanceof User) {
			User u = (User) o;
			return loginID.equals(u.getLoginID()) && password.equals(u.getPassword());
		}
		return false;
	}

	public String toString() {
		return String.format("%s,%s", loginID, password);
	}

}
