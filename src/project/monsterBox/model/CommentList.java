package project.monsterBox.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author morikawahiroki
 *
 */
public class CommentList implements Serializable{
	private List<Comment> commentList;
	public CommentList() {
		commentList = new ArrayList<Comment>();
	}

	public List<Comment> getCommentList() {
		return commentList;
	}

	public void addComment(Comment comment) {
		commentList.add(comment);
	}
}
