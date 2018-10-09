package project.monsterBox.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author morikawahiroki
 *
 */
public class FollowUserList implements Serializable{
	List<UserProfile> followUserList;

	public FollowUserList() {
		this.followUserList = new ArrayList<UserProfile>();
	}

	public List<UserProfile> getFollowUserList() {
		return followUserList;
	}

	public void addFollowUser(UserProfile profile) {
		this.followUserList.add(profile);
	}
}
