package project.monsterBox.model;

import java.io.Serializable;

/**
 * ユーザプロファイル
 *
 * @author morikawahiroki
 *
 */
public class UserProfile implements Serializable {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 601135355215082811L;

	private User user;
	/** アバター */
	private Abator abator;
	/** お気に入り */
	private UploadFileList userFavoriteList;
	/** 投稿ファイル履歴 */
	private UploadFileList uploadHistory;
	/** 閲覧履歴 */
	private UploadFileList watchHistory;

	/**
	 * @param user
	 * @param abator
	 * @param point
	 * @param favorite
	 * @param uploadHistory
	 * @param watchHistory
	 */
	public UserProfile() {
		this(new User(), new Abator(), new UploadFileList(), new UploadFileList(), new UploadFileList());
	}

	public UserProfile(User user) {
		this(user, new Abator(), new UploadFileList(), new UploadFileList(), new UploadFileList());
	}

	public UserProfile(User user, Abator abator, UploadFileList userFavoriteList, UploadFileList uploadHistory,
			UploadFileList watchHistory) {
		this.user = user;
		this.abator = abator;
		this.userFavoriteList = userFavoriteList;
		this.uploadHistory = uploadHistory;
		this.watchHistory = watchHistory;
	}

	public User getUser() {
		return user;
	}

	public Abator getAbator() {
		return abator;
	}

	public UploadFileList getUserFavoriteList() {
		return userFavoriteList;
	}

	public UploadFileList getUploadHistory() {
		return uploadHistory;
	}

	public UploadFileList getWatchHistory() {
		return watchHistory;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public void setAbator(Abator abator) {
		this.abator = abator;
	}

	public void setUserFavoriteList(UploadFileList userFavoriteList) {
		this.userFavoriteList = userFavoriteList;
	}

	public void setUploadHistory(UploadFileList uploadHistory) {
		this.uploadHistory = uploadHistory;
	}

	public void setWatchHistory(UploadFileList watchHistory) {
		this.watchHistory = watchHistory;
	}

}
