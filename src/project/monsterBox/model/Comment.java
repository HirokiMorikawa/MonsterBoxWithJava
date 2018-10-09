package project.monsterBox.model;

import java.io.Serializable;

/**
 * @author morikawahiroki
 *
 */
public class Comment implements Serializable {

	private static final long serialVersionUID = 60113510215082811L;

	private String userId;
	private String comment;
	public Comment(String userid,String comment) {
		this.userId = userid;
		this.comment = comment;
	}
	public String getUserId() {
		return userId;
	}

	public String getComment() {
		return comment;
	}
}
