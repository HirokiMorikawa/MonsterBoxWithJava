package project.monsterBox.model;

/**
 * @author morikawahiroki
 *
 */
public class OwnerComment extends Comment {

	private String title;

	/**
	 * @param userid
	 * @param comment
	 */
	public OwnerComment(String userid, String title, String comment) {
		super(userid, comment);
		this.title = title;
	}

	public String getTitle() {
		return title;
	}

}
