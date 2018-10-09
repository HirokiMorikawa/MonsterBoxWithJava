package project.monsterBox.model;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 *
 * アップロードされたファイルのクラス
 *
 * @author morikawahiroki
 *
 */
public class UploadFile implements Serializable {

	private static final long serialVersionUID = 2479045701509545562L;

	/**
	 * mimeTypeつまりファイルの種類
	 */
	private String mimeType;
	/**
	 * 投稿者名
	 */
	private String uploadUserName;
	/**
	 * ファイル名
	 */
	private String fileName;
	/**
	 * アップロードしたファイルに対するタイトルとコメント
	 */
	private OwnerComment ownerComment;
	/**
	 * 他のユーザのコメント
	 */
	private CommentList comments;
	/**
	 * 時間
	 */
	private LocalDateTime uploadTime;
	/**
	 * お気に入りしたユーザのリスト
	 */
	private FollowUserList followUserList;

	public UploadFile(String mimeType, String uploadUserName, String fileName, String title, String ownerComment,
			LocalDateTime time) {
		this.mimeType = mimeType;
		this.uploadUserName = uploadUserName;
		this.fileName = fileName;
		this.ownerComment = new OwnerComment(uploadUserName, title, ownerComment);
		this.uploadTime = time;
		this.comments = new CommentList();
		this.followUserList = new FollowUserList();
	}

	public String getMimeType() {
		return mimeType;
	}

	/**
	 * @return uploadUserName
	 */
	public String getUploadUserName() {
		return uploadUserName;
	}

	/**
	 * @return fileName
	 */
	public String getFileName() {
		return fileName;
	}

	/**
	 * @return ownerComment
	 */
	public OwnerComment getOwnerComment() {
		return ownerComment;
	}

	/**
	 * @return comments
	 */
	public CommentList getCommentList() {
		return comments;
	}

	public FollowUserList getFollowUserList() {
		return followUserList;
	}

	/**
	 * @return uploadTime
	 */
	public LocalDateTime getUploadTime() {
		return uploadTime;
	}

	/**
	 * サムネイル画像のファイルメイ
	 *
	 * @return
	 */
	public String getThumbnail() {
		String th_name = "other.png";
		switch (mimeType) {
		case "text/html":
			th_name = "html.png";
			break;
		case "image/gif":
			th_name = fileName;
			break;
		case "image/jpeg":
			th_name = fileName;
			break;
		case "image/png":
			th_name = fileName;
			break;
		case "audio/mpeg":
			th_name = "mp3.png";
			break;
		case "application/zip":
			th_name = "zip.png";
			break;
		case "aaplication/pdf":
			th_name = "pdf.png";
		default:
			break;
		}
		return th_name;
	}

	public boolean isImage() {
		boolean result = false;
		switch (mimeType) {
		case "image/gif":
			result = true;
			break;
		case "image/jpeg":
			result = true;
			break;
		case "image/png":
			result = true;
			break;
		default:
			break;
		}
		return result;
	}

	public void setMimetype(String mimeType) {
		this.mimeType = mimeType;
	}

	/**
	 * @param uploadUserName
	 *            セットする uploadUserName
	 */
	public void setUploadUserName(String uploadUserName) {
		this.uploadUserName = uploadUserName;
	}

	/**
	 * @param fileName
	 *            セットする fileName
	 */
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	/**
	 * @param ownerComment
	 *            セットする ownerComment
	 */
	public void setOwnerComment(OwnerComment ownerComment) {
		this.ownerComment = ownerComment;
	}

	/**
	 * @param comments
	 *            セットする comments
	 */
	public void setComments(CommentList comments) {
		this.comments = comments;
	}

	/**
	 * @param uploadTime
	 *            セットする uploadTime
	 */
	public void setUploadTime(LocalDateTime uploadTime) {
		this.uploadTime = uploadTime;
	}

	public void setFollowUserList(FollowUserList followUserList) {
		this.followUserList = followUserList;
	}

}
