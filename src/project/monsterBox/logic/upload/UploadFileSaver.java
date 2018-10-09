package project.monsterBox.logic.upload;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;

import javax.servlet.http.Part;

/**
 * ユーザがアップロードしたファイルの保存処理を行う
 *
 * @author morikawahiroki
 *
 */
public class UploadFileSaver {
	/**
	 * 保存先のディレクトリ
	 */
	private String path;
	/**
	 * ユーザId
	 */
	private String userid;

	/**
	 * フィールドの変数を初期化する
	 * @param userid ユーザId
	 * @param path 保存先ディレクトリ
	 */
	public UploadFileSaver(String userid, String path) {
		this.userid = userid;
		this.path = path;
		//ディレクトリの生成
		createUserDir();
	}

	/**
	 * ユーザが送信してきたファイルデータをサーバに保存する処理を行う
	 *
	 * @param part アップロードされたファイル
	 * @param time 時間
	 */
	public boolean saveUploadFile(Part part, LocalDateTime time) {
		try {
			Path p = Paths.get(path + "/" + userid + "_dir"
					+ "/"/* + time.toString() */ + part.getSubmittedFileName());
			//ファイルが存在しない時作成する
			if (Files.notExists(p))
				Files.createFile(p);
			//ファイル入力
			InputStream is = part.getInputStream();
			//ファイル出力
			OutputStream os = Files.newOutputStream(p);
			//書き込み
			int buf = 0;
			while((buf = is.read()) != -1) {
				os.write(buf);
			}
			return true;
		} catch (IOException e) {//エラー出たらfalse
			return false;
		}
	}

	/**
	 * ユーザ用のディレクトリを生成する
	 *
	 * @throws IOException
	 */
	private void createUserDir() {
		Path p = Paths.get(path + "/" + userid + "_dir");
		if (Files.notExists(p)) {
			try {
				Files.createDirectory(p);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
