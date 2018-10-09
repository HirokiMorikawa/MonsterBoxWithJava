package project.monsterBox.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import project.monsterBox.logic.upload.UploadFileListDeserializer;
import project.monsterBox.logic.upload.UploadFileListSerializer;
import project.monsterBox.model.UploadFileList;

/**
 * Application Lifecycle Listener implementation class
 * ApplicationStartupListener
 *
 */
@WebListener
public class ApplicationStartupListener implements ServletContextListener {
	public void contextDestroyed(ServletContextEvent arg0) {
		UploadFileList ufl = (UploadFileList) arg0.getServletContext().getAttribute("uploadList");
		UploadFileListSerializer ufls = new UploadFileListSerializer(ufl, "auth");
		ufls.doSerialize();
	}

	/**
	 * @see ServletContextListener#contextInitialized(ServletContextEvent)
	 */
	public void contextInitialized(ServletContextEvent arg0) {
		UploadFileListDeserializer ufld = new UploadFileListDeserializer("auth");
		UploadFileList ufl = null;
		if (ufld.doDeserialize()) {
			ufl = ufld.getDeserealizedObject();
		} else {
			ufl = new UploadFileList();
		}
		arg0.getServletContext().setAttribute("uploadList", ufl);
	}

}
