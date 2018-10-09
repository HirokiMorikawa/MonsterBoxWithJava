package project.monsterBox.logic;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;

/**
 * デシリアライズを行う
 *
 * @author morikawahiroki
 *
 */
public abstract class Deserializer<T> {
	//private String home;
	private String dir;
	private String fileName;

	/**
	 * 変数の初期化
	 *
	 * @param dir
	 *            保存先ディレクトリ名
	 * @param fileName
	 *            ファイル名
	 */
	public Deserializer(String dir, String fileName) {
		//this.home = System.getProperty("user.home");
		this.dir = dir;
		this.fileName = fileName;
	}

	/**
	 * 変数の初期化を行う
	 */
	public Deserializer() {
		dir = "";
		fileName = "file.tmp";
	}

	/**
	 * ファイル名の取得
	 *
	 * @param fileName
	 */
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	/**
	 * オブジェクトが格納されたバイナリファイルをオブジェクトとして変換する
	 *
	 * @return
	 */
	public abstract boolean doDeserialize();

	/**
	 * デシリアライズ化したオブジェクトを取得する． デシリアライズ化して失敗した場合，nullを返す
	 *
	 * @param <T>
	 *
	 * @return
	 */
	public abstract T getDeserealizedObject();

	/**
	 * デシリアライズ用オブジェクトの生成を行う
	 *
	 * @return
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	protected ObjectInputStream getObjectIntputStream() throws FileNotFoundException, IOException {
		return new ObjectInputStream(new FileInputStream(dir + "/" + fileName));
	}
}
