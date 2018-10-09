package project.monsterBox.logic.upload;

import java.time.LocalDateTime;

import javax.servlet.http.Part;

import project.monsterBox.model.UploadFile;
import project.monsterBox.model.UploadFileList;
import project.monsterBox.model.UserProfile;

/**
 * @author morikawahiroki
 *
 */
public abstract class UploadLogicTemplate {
	protected UserProfile profile;
	protected String userid;
	protected String path;
	protected Part part;
	protected LocalDateTime time;
	protected String title;
	protected String ownerComment;
	protected String mimeType;

	public UploadLogicTemplate(UserProfile profile, String path, Part part, String title, String ownerComment) {
		this.profile = profile;
		this.userid = profile.getUser().getLoginID();
		this.path = path;
		this.part = part;
		this.title = title;
		this.ownerComment = ownerComment;
		this.mimeType = part.getHeader("Content-Type");
	}

	/**
	 * ファイルをアップロードした時の処理を行う
	 */
	public boolean doUpload(UploadFileList upFileList) {
		time = LocalDateTime.now();
		if (doSaveFile()) {
			UploadFile upFile = doGenerateUploadFile();
			updateUserProfile(upFile);
			registerToCentralStore(upFileList, upFile);
			return true;
		}
		return false;
	}

	/**
	 * ファイルの保存処理を行う．
	 *
	 * @retrun 保存が成功した時true，失敗もしくはエラーが起きた時falseを返す
	 */
	protected abstract boolean doSaveFile();

	/**
	 *
	 * ファイルデータそのものを含まないオブジェクトを管理するためのUploadFileクラスの生成
	 */
	protected abstract UploadFile doGenerateUploadFile();
	// {
	// return new UploadFile(userid, part.getSubmittedFileName(), title,
	// ownerComment, time);
	// }

	/**
	 * アップロードしたユーザのプロファイルにその情報を記録する処理を行う
	 */
	protected abstract void updateUserProfile(UploadFile upFile);

	/**
	 * 全てのユーザがアップロードしたファイルを管理するためのCentralStoreオブジェクトに，
	 * 現在ユーザがアップロードしたファイルを情報として記録する
	 */
	protected abstract void registerToCentralStore(UploadFileList upFileList, UploadFile upFile);
}
