package project.monsterBox.logic.profile;

import java.util.ArrayList;
import java.util.List;

import project.monsterBox.logic.access.ProfileAccessor;
import project.monsterBox.model.Abator;
import project.monsterBox.model.AllAvatar;
import project.monsterBox.model.UploadFile;
import project.monsterBox.model.UserProfile;

/**
 *
 * ユーザの情報を扱うためのクラス
 *
 * @author morikawahiroki
 *
 */
public class UserProfilingSystem {

	/**
	 * ユーザプロファイルにアクセスする方法を定義したクラス
	 */
	private ProfileAccessor access;

	private UserProfile profile;

	/**
	 * ユーザプロファイルにアクセスする方法をインスタンス生成時に決定する
	 *
	 * @param access
	 *            ユーザプロファイルにアクセスする方法を記述したインスタンス
	 */
	public UserProfilingSystem(ProfileAccessor access, String userId) {
		this.access = access;
		profile = access.getUserProfile(userId);
	}

	public UserProfilingSystem(UserProfile profile) {
		this.access = new ProfileAccessor();
		this.profile = profile;
	}

	/**
	 * 自動的にProfileAccessorをユーザプロファイルにアクセスする方法として設定する.
	 */
	public UserProfilingSystem(String userId) {
		this(new ProfileAccessor(), userId);
	}

	/**
	 * 閲覧履歴を取得する
	 *
	 * @param userid
	 *            ユーザID
	 * @return 閲覧履歴が格納されているList型のインスタンス
	 */
	public List<UploadFile> watchHistory() {
		if (profile != null) {
			return profile.getWatchHistory().uploadFileList();
		}
		return new ArrayList<UploadFile>();
	}

	/**
	 * 閲覧履歴の設定
	 *
	 * @param history
	 *            閲覧履歴
	 */
	public void addWatchHistory(UploadFile history) {
		profile.getWatchHistory().addUploadFile(history);
		;
	}

	public void addUpdateFileHistory(UploadFile upFile) {
		profile.getUploadHistory().addUploadFile(upFile);
	}

	/**
	 * お気に入りの追加処理をおこなう
	 * @param favorite
	 */
	public void addFavorite(UploadFile favorite) {
		boolean isExist = false;
		for(UploadFile up :profile.getUserFavoriteList().uploadFileList()){
			if(up.getUploadTime().equals(favorite.getUploadTime()))
				isExist = true;
		}
		if(!isExist) profile.getUserFavoriteList().addUploadFile(favorite);
	}

	public void setAbator(int abatorNum) {
		AllAvatar avators = new AllAvatar();
		profile.setAbator(avators.getAbator(abatorNum));
	}

	public Abator getAvator() {
		return profile.getAbator();
	}

	/**
	 * ユーザプロファイルを取得する
	 *
	 * @return
	 */
	public UserProfile getUserProfile() {
		return this.profile;
	}

	/**
	 * 更新処理の後のコミット．これを実行しなければファイルとして保存されない
	 *
	 * @return
	 */
	public boolean save() {
		return access.existsUserProfile(profile.getUser().getLoginID()) ? access.saveUserProfile(profile) : false;
	}

	/**
	 * ユーザプロファイルが存在しているか確認
	 *
	 * @return
	 */
	public boolean hasUserProfile() {
		return access.existsUserProfile(profile.getUser().getLoginID());
	}

	/**
	 * ユーザプロファイルが存在しなければ生成する
	 *
	 * @return
	 */
	public boolean createUserProfileIfNotExist(UserProfile profile) {
		UserProfile p = this.profile;
		this.profile = profile;
		boolean result = false;
		if (!this.hasUserProfile()) {
			result = access.saveUserProfile(profile);
		}
		if (result) {
			System.out.println("プロファイルが生成されました");
		} else {
			this.profile = p;
			System.out.println("プロファイルが生成されませんでした");
		}
		return result;
	}

}
