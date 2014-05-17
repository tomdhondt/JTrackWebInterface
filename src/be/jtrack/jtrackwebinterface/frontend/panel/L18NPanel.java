package be.jtrack.jtrackwebinterface.frontend.panel;

import java.io.File;
import java.io.Serializable;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Locale;
import java.util.ResourceBundle;

import com.vaadin.server.VaadinService;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Panel;

public class L18NPanel extends Panel implements Serializable {
	/**
	 * Serial version ID
	 */
	private static final long serialVersionUID = 8391892709652832833L;
	protected ResourceBundle  captions;
	protected Locale locale;
	/**
	 * Default Constructor for the Class
	 */
	public L18NPanel(){
		locale = new Locale("Messages",Locale.getDefault().toString());
		String basepath = VaadinService.getCurrent().getBaseDirectory().getAbsolutePath();
		File file = new File(basepath+"/WEB-INF/resourcebundle");
		try {
			URL[] urls ={file.toURI().toURL()};
			ClassLoader loader = new URLClassLoader(urls);
			captions = ResourceBundle.getBundle("Messages", locale ,loader);
		} catch (MalformedURLException e){
			Notification.show("Error Caption");
		}catch(Exception e){
			Notification.show("Error Caption");
		}
	}
}
