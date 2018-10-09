package project.monsterBox.logic.login;

import project.monsterBox.logic.Serializer;
import project.monsterBox.logic.access.AuthenUserFileAccessor;
import project.monsterBox.logic.profile.UserProfileCreator;
import project.monsterBox.logic.profile.UserProfileSerializer;
import project.monsterBox.logic.profile.UserProfilingSystem;
import project.monsterBox.model.User;

/**
 * アカウント作成に関する処理を行うクラス．
 *
 * @author morikawahiroki
 *
 */
public class SignInSystem {
	private AuthenUserFileAccessor fileAccess;

	/**
	 * ファイルにアクセスする方法に関するオブジェクトをセットする
	 *
	 * @param fileAccess
	 */
	public SignInSystem(AuthenUserFileAccessor fileAccess) {
		this.fileAccess = fileAccess;
	}

	/**
	 * アカウントの作成処理を行う．ユーザIDとパスワードにより作成する．ユーザIDがすでに存在すれば何もしない．
	 * アカウントが作成されると同時にユーザプロファイルも自動生成する．
	 *
	 *
	 * @param user
	 *            ユーザ情報
	 * @return
	 */
	public boolean doSign_in(User user) {
		String userid = user.getLoginID();
		// 登録して成功したとき，登録を成功したことのメッセージを送信する
		if (sign_in(user)) {

			System.out.println("ユーザはアカウント名:" + userid + "でアカウントを作成しました");

			// ユーザのプロファイルを読み込む
			UserProfilingSystem ups = new UserProfilingSystem(userid);
			if (!ups.hasUserProfile()) {
				ups.createUserProfileIfNotExist(new UserProfileCreator(user).createUserProfile());
				ups.save();
			}

			return true;
		}
		// 登録しようとして失敗，つまりすでにユーザが存在しているとき
		// 登録しようとして失敗した旨のメッセージを送信する
		else {
			return false;
		}
	}

	/**
	 * ユーザ作成
	 */
	private boolean sign_in(User user) {
		if (fileAccess.findByUser(user.getLoginID()))
			return false;
		else {
			if (fileAccess.insertUser(user)) {
				Serializer serializer = new UserProfileSerializer(new UserProfileCreator(user), "auth");
				boolean success = serializer.doSerialize();
				if (!success)
					fileAccess.deleteUser(user);
				return success;
			} else {
				return false;
			}
		}
	}
}
