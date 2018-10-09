package project.monsterBox.logic.profile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectOutputStream;

import project.monsterBox.logic.Serializer;
import project.monsterBox.model.UserProfile;

/**
 * @author morikawahiroki
 *
 */
public class UserProfileSerializer extends Serializer {

	private UserProfile profile;

	public UserProfileSerializer(UserProfileCreator creator, String dir) {
		super(dir, creator.createUserProfile().getUser().getLoginID());
		this.profile = creator.createUserProfile();
	}

	public UserProfileSerializer(UserProfile profile, String dir) {
		super(dir, profile.getUser().getLoginID());
		this.profile = profile;
	}

	@Override
	public boolean doSerialize() {
		try {
			ObjectOutputStream stream = this.getObjectOutputStream();
			stream.writeObject(profile);
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
		return true;
	}

}
