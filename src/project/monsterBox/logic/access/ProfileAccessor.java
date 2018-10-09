package project.monsterBox.logic.access;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import project.monsterBox.logic.Deserializer;
import project.monsterBox.logic.Serializer;
import project.monsterBox.logic.profile.UserProfileDeserializer;
import project.monsterBox.logic.profile.UserProfileSerializer;
import project.monsterBox.model.UserProfile;

/**
 * UserProfileにアクセスするための手段を提供する
 *
 * @author morikawahiroki
 *
 */
public class ProfileAccessor {
	private String dir;

	/**
	 * 保存先ディレクトリの設定を行う
	 */
	public ProfileAccessor() {
		this.dir = "auth";
	}

	/**
	 * ユーザIDによってそのユーザーが持つプロフィール情報を取得する．
	 *
	 *
	 * @param userId
	 *            ユーザーID
	 * @return UserProfile
	 *         ユーザプロファイルが正常に取得できた場合UserProfileオブジェクトを返す．そうでない場合は新しくオブジェクトを生成してUserProfileを返す．
	 *         ファイルが存在しない時も新しくオブジェクトを生成してUserProfileを返す
	 */
	public UserProfile getUserProfile(String userId) {
		UserProfile emptyProfile = new UserProfile();
		emptyProfile.getUser().setLoginID(userId);
		if (this.existsUserProfile(userId)) {
			Deserializer<UserProfile> des = new UserProfileDeserializer(dir, userId);
			return des.doDeserialize() ? des.getDeserealizedObject() : emptyProfile;
		} else {
			return emptyProfile;
		}
	}

	/**
	 * ユーザプロファイルを所定のディレクトリにシリアライズ化してバイナリデータとして保存する．
	 *
	 * @param profile
	 *            ユーザプロファイル
	 * @return ユーザプロファイルを正しく保存できたときtrueを返す．そうでない場合はfalseを返す．
	 */
	public boolean saveUserProfile(UserProfile profile) {
		Serializer ser = new UserProfileSerializer(profile, dir);
		return ser.doSerialize();
	}

	/**
	 * ユーザプロファイルを削除する
	 *
	 * @param userId
	 *            ユーザID
	 * @return 削除に成功したときtrue，それ以外false
	 */
	public boolean deteleUserProfile(String userId) {
		Path path = Paths.get(dir + "/" + userId);
		try {
			return Files.deleteIfExists(path);
		} catch (IOException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * ユーザプロファイルが存在していか確認する
	 *
	 * @param userId
	 *            ユーザID
	 * @return ファイルが存在すればtrue，存在しなければfalse
	 */
	public boolean existsUserProfile(String userId) {
		Path path = Paths.get(dir + "/" + userId);
		return !Files.notExists(path);
	}
}
