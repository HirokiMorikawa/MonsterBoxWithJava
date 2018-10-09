package project.monsterBox.logic.upload;

import javax.servlet.http.Part;

import project.monsterBox.logic.profile.UserProfilingSystem;
import project.monsterBox.model.UploadFile;
import project.monsterBox.model.UploadFileList;
import project.monsterBox.model.UserProfile;

/**
 * @author morikawahiroki
 *
 */
public class UploadLogic extends UploadLogicTemplate {

	/**
	 *
	 * コンストラクタ．フィールドの変数を初期化する
	 *
	 * @param userid ファイルをアップロードしたユーザId
	 * @param path 保存先
	 * @param part ファイル
	 * @param title ファイルのタイトル
	 * @param ownerComment アップロードしたユーザの一言コメント
	 */
	public UploadLogic(UserProfile profile, String path, Part part, String title, String ownerComment) {
		super(profile, path, part, title, ownerComment);
	}

	@Override
	protected boolean doSaveFile() {
		UploadFileSaver ufs = new UploadFileSaver(userid,path);
		return ufs.saveUploadFile(part, time);

	}

	@Override
	protected UploadFile doGenerateUploadFile() {
		UploadFileGenerator ug = new UploadFileGenerator();
		//UploadFileの生成
		return ug.createUploadFile(mimeType,userid, title, part.getSubmittedFileName(), ownerComment, time);

	}

	@Override
	protected void updateUserProfile(UploadFile upFile) {
		UserProfilingSystem ups = new UserProfilingSystem(profile);
		ups.addUpdateFileHistory(upFile);
		ups.save();
	}

	@Override
	protected void registerToCentralStore(UploadFileList upFileList, UploadFile upFile) {
		upFileList.addUploadFile(upFile);
		UploadFileListSerializer ufld = new UploadFileListSerializer(upFileList,"auth");
		ufld.doSerialize();
	}

}
