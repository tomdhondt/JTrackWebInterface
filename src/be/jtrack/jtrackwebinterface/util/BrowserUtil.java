package be.jtrack.jtrackwebinterface.util;

import com.vaadin.server.Page;

public abstract class BrowserUtil {
	/**
	 * Method will check the browser height
	 * @return int as Height of the browser
	 */
	public static int getBrowserHeight(){
		return Page.getCurrent().getBrowserWindowHeight();
	}

}
