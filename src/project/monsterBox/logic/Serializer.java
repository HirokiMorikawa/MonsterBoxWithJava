package project.monsterBox.logic;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

/**
 * シリアライズ化するためのオブジェクト
 *
 * @author morikawahiroki
 *
 */
public abstract class Serializer {

	private String dir;
	private String fileName;

	public Serializer(String dir, String fileName) {
		this.dir = dir;
		this.fileName = fileName;
	}

	public Serializer() {
		dir = "";
		fileName = "file.tmp";
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public abstract boolean doSerialize();

	protected ObjectOutputStream getObjectOutputStream() throws FileNotFoundException, IOException {
		return new ObjectOutputStream(new FileOutputStream(dir + "/" + fileName));
	}
}
