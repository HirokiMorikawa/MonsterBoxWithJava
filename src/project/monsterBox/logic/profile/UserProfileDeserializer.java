package project.monsterBox.logic.profile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;

import project.monsterBox.logic.Deserializer;
import project.monsterBox.model.UserProfile;

/**
 * @author morikawahiroki
 *
 */
public class UserProfileDeserializer extends Deserializer<UserProfile> {

	private UserProfile profile;
	private boolean isNew;

	public UserProfileDeserializer(String dir, String userid) {
		super(dir, userid);
		// this.profile = new UserProfile();
		isNew = false;
	}

	@Override
	public boolean doDeserialize() {
		try {
			ObjectInputStream objectInputStream = this.getObjectIntputStream();
			try {
				profile = (UserProfile) objectInputStream.readObject();
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
	public UserProfile getDeserealizedObject() {
		return profile;
	}

}
