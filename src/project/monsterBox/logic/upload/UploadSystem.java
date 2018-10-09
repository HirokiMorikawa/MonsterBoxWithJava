package project.monsterBox.logic.upload;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;

import project.monsterBox.model.UploadFileList;
import project.monsterBox.model.UserProfile;

/**
 * @author morikawahiroki
 *
 */
public class UploadSystem {
	private String host;
	private String dir;
	private String title;
	private String comment;
	private UploadLogicTemplate logic;

	public UploadSystem(String host, String dir, String title, String comment) {
		this.host = host;
		this.dir = dir;
		this.title = title;
		this.comment = comment;
	}

	public String doUpload(HttpServletRequest request, Part part) {
		UserProfile profile = (UserProfile) request.getSession().getAttribute("profile");
		UploadFileList list = (UploadFileList) request.getServletContext().getAttribute("uploadList");
		logic = new UploadLogic(profile, "auth", part, title,
				comment);
		if (logic.doUpload(list)) {
			request.getSession().setAttribute("profile", profile);
			request.getServletContext().setAttribute("uploadList", list);
			return host + "/" + dir;
		} else {
			return host + "/" + dir;
		}
	}
}
