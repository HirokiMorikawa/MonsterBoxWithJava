package project.monsterBox.logic.profile;

import project.monsterBox.model.User;
import project.monsterBox.model.UserProfile;

/**
 *
 * ユーザプロファイルを作成する
 *
 * @author morikawahiroki
 *
 */
public class UserProfileCreator {
	private UserProfile profile;

	public UserProfileCreator(User user) {
		init(user);
	}

	private void init(User user) {
		profile = new UserProfile(user);
	}

	public void setUser(User user) {
		init(user);
	}

	public UserProfile createUserProfile() {
		return profile;
	}
}
