/**
 * 
 */
package be.jtrack.jtrackwebinterface.util;

import java.io.File;

import com.vaadin.server.FileResource;
import com.vaadin.server.VaadinService;

/**
 * Class ResourceUtil
 * @author tom.dhondt - created at : 26-dec.-2013
 *
 */
public abstract class ResourceUtil {
	public static final String basePath = VaadinService.getCurrent().getBaseDirectory().getAbsolutePath();
	/**
	 * Method will create a resource needed by the domain model
	 * @param path as String of the location of the path, from the basepath of. example /VAADIN/themes/icons/image.png
	 * @return resource as FileResource
	 */
	public static FileResource getFileResource(String path){
		FileResource resource = null;
		/* check the content of the path */
		if(null != path){
			/* check that the path exists */
			File file = new File(basePath + path);
			if(file.exists()== true){
				resource = new FileResource(new File(basePath + path));
			}else{
				resource = new FileResource(new File(basePath + "/VAADIN/themes/icons/png/exclamation.png"));
			}
		}else{
			resource = new FileResource(new File(basePath + "/VAADIN/themes/icons/png/exclamation.png"));
		}
		return resource;
	}
}
