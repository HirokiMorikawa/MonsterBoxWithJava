package project.monsterBox.logic.upload;

import java.time.LocalDateTime;

import project.monsterBox.model.UploadFile;

/**
 *
 * アップロードしたファイルをオブジェクトとして管理するためにUploadFileオブジェクトを生成するクラス
 *
 * @author morikawahiroki
 *
 */
public class UploadFileGenerator {

	public UploadFile createUploadFile(String mimeType, String userid, String title, String filename,
			String ownerComment, LocalDateTime time) {
		return new UploadFile(mimeType, userid, filename, title, ownerComment, time);
	}

}
