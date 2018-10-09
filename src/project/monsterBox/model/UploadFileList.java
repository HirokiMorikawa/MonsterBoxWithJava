package project.monsterBox.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * UploadFileクラスのオブジェクトを格納するだけのクラス
 *
 * @author morikawahiroki
 *
 */
public class UploadFileList implements Serializable{
	private List<UploadFile> uploadFileList;

	public UploadFileList() {
		uploadFileList = new ArrayList<>();
	}

	/**
	 * 今までに追加されてきたUploadFileのリストを返す
	 * @return
	 */
	public List<UploadFile> uploadFileList() {
		return this.uploadFileList;
	}

	/**
	 * リストにUploadFileを追加する
	 * @param upFile
	 */
	public void addUploadFile(UploadFile upFile) {
		this.uploadFileList.add(upFile);
	}


}
