package project.monsterBox.logic.access;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;

import project.monsterBox.model.User;

/**
 * ユーザ情報ファイルにアクセスし，選択・削除・変更を行うクラス
 *
 * 拡張可能クラス
 *
 * @author morikawahiroki
 *
 */
public class AuthenUserFileAccessor {

	/**
	 * サーブレットを起動した時に生成されるディレクトリ名を含むパス
	 */
	private String defaultPath;

	/**
	 * ユーザのアカウント情報が記録されているファイル名
	 */
	private String filename;

	public AuthenUserFileAccessor() {
		this.defaultPath = "auth/";
		// this.defaultPath = "/home/hirocospa/auth/";
		this.filename = "authUser.csv";
		createAuthenUserFile();
	}

	/**
	 * アカウント登録されたすべてのユーザ情報を取得する． IOExeptionのときnullを返す
	 *
	 * @return アカウント登録されたすユーザ情報のリスト
	 */
	public List<User> findAll() {
		Path file = authFile();
		List<User> userList = new ArrayList<User>();
		try {
			// csv.read(Files.readAllLines(file));
			List<String> lines = Files.readAllLines(file);
			for (String str : lines) {
				userList.add(this.toUser(str));
			}
		} catch (IOException e) {
			e.printStackTrace();
			return new ArrayList<User>();
		}
		return userList;
	}

	/**
	 * アカウント登録されたユーザのリストから引数で与えられたログインIDのリストを見つける
	 *
	 * @param user
	 *            ログインID
	 * @return ログインIDが見つかればtrue，そうでなければfalse
	 */
	public boolean findByUser(String userID) {
		return this.findAll().stream().filter(e -> e.getLoginID().equals(userID)).findFirst().isPresent();
	}

	/**
	 * ユーザ情報をインサートする
	 *
	 * @param user
	 * @return
	 */
	public boolean insertUser(User user) {
		Path file = authFile();
		try {
			Files.write(file, (user.toString() + "\n").getBytes(), StandardOpenOption.APPEND);
			return true;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * ユーザ情報を削除する
	 *
	 * @param dUser
	 * @return
	 */
	public boolean deleteUser(User dUser) {
		// CSV csv = new CSV();
		Path file = authFile();
		List<User> users = findAll();
		boolean isDelete = false;
		if (users == null)
			return false;

		// 削除
		isDelete = users.remove(dUser);
		if (isDelete) {
			StringBuilder sb = new StringBuilder();
			for (User user : users) {
				sb.append(user.toString() + "\n");
			}
			try {
				Files.write(file, sb.toString().getBytes());
			} catch (IOException e) {
				e.printStackTrace();
				return false;
			}
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 認証用ファイルの生成
	 */
	private void createAuthenUserFile() {
		Path root = getPath(defaultPath);
		Path file = getPath(defaultPath + filename);

		if (Files.notExists(root)) {
			try {
				Files.createDirectory(root);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		/*
		 * ファイルが生成されていない
		 */
		if (Files.notExists(file)) {
			try {
				Files.createFile(file);
			} catch (IOException e) {
				// TODO 自動生成された catch ブロック
				e.printStackTrace();
			}
		}
	}

	/**
	 * csvで記述されたデータをUserオブジェクトに変換する
	 *
	 * @param str
	 *            csvデータ
	 * @return Userオブジェクト
	 */
	public User toUser(String str) {
		String[] s = str.split(",");
		return new User(s[0], s[1]);
	}

	public Path authFile() {
		Path path = getPath(defaultPath + filename);
		System.out.println(path.toAbsolutePath().toString());
		return path;
	}

	public Path getPath(String path) {
		return Paths.get(path);
	}
}
