package project.monsterBox.logic.upload;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectOutputStream;

import project.monsterBox.logic.Serializer;
import project.monsterBox.model.UploadFileList;

/**
 * UploadFileListクラスのシリアライズ化を実行するSerializerクラスの実装クラス
 *
 * @author morikawahiroki
 *
 */
public class UploadFileListSerializer extends Serializer {

	private UploadFileList uploadFileList;

	public UploadFileListSerializer(UploadFileList uploadFileList, String dir) {
		super(dir, "uploadFileList");
		this.uploadFileList = uploadFileList;
	}

	@Override
	public boolean doSerialize() {
		try {
			ObjectOutputStream stream = this.getObjectOutputStream();
			stream.writeObject(uploadFileList);
			stream.close();
		} catch (FileNotFoundException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
			return false;
		} catch (IOException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
			return false;
		}
		return false;
	}

}
