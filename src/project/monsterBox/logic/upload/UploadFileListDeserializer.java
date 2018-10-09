package project.monsterBox.logic.upload;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;

import project.monsterBox.logic.Deserializer;
import project.monsterBox.model.UploadFileList;

/**
 * @author morikawahiroki
 *
 */
public class UploadFileListDeserializer extends Deserializer<UploadFileList>{

	private UploadFileList uploadFileList;
	private boolean isNew;

	public UploadFileListDeserializer(String dir) {
		super(dir,"uploadFileList");
		isNew = false;
	}

	@Override
	public boolean doDeserialize() {
		try {
			ObjectInputStream objectInputStream = this.getObjectIntputStream();
			try {
				uploadFileList = (UploadFileList) objectInputStream.readObject();
				isNew = true;
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
				isNew = false;
			}
			objectInputStream.close();
			return isNew;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return false;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}


	@Override
	public UploadFileList getDeserealizedObject() {
		return uploadFileList;
	}


}
