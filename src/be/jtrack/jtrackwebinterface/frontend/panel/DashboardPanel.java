package be.jtrack.jtrackwebinterface.frontend.panel;

import java.io.Serializable;

import be.jtrack.jtrackwebinterface.frontend.panel.JTrackInventory.Pnl_JTrackInventoryView;
import be.jtrack.jtrackwebinterface.frontend.panel.JTrackTicket.manage.Pnl_Login;

import com.vaadin.annotations.Theme;
import com.vaadin.navigator.Navigator;
import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.AbsoluteLayout;
import com.vaadin.ui.Panel;
import com.vaadin.ui.UI;

@Theme("reindeer")
public class DashboardPanel extends UI implements Serializable {
	/**
	 * Serial version ID
	 */
	private static final long serialVersionUID = 8180348612999495444L;
	/*
	 * instance members
	 */
	private Panel leftPanel;
	private Panel midlePanel;
	private Panel topPanel;
	private Panel buttomPanel;
	/*
	 * Method will initialize the Class
	 */
	@Override
	protected void init(VaadinRequest request) {
		/* Set the Basis Properties of the page */
		this.getPage().setTitle("JTrack Web Interface");
		AbsoluteLayout layout = new AbsoluteLayout();
		this.leftPanel = new NavigationPanel();
		this.leftPanel.setSizeFull();
		this.midlePanel = new Panel();
		this.midlePanel.setDescription("");
		this.midlePanel.setSizeFull();
		this.topPanel = new TopPanel();
		this.topPanel.setSizeFull();
		this.buttomPanel = new Panel("");
		this.buttomPanel.setSizeFull();
		layout.addComponent(this.topPanel, "left: 3%; right: 3%; top: 0%; bottom: 90%;");
		layout.addComponent(this.buttomPanel, "left: 3%; right: 3%; top: 90%; bottom: 00%;");
		layout.addComponent(this.leftPanel, "left: 3%; right: 89%; top: 30px; bottom: 10%;");
		layout.addComponent(this.midlePanel, "left: 11%; right: 3%; top: 30px; bottom: 10%;");
		this.setHeight("100%");
		this.setWidth("100%");
		this.setContent(layout);
		/* create navigator */
		new Navigator(this, this.midlePanel);
		getNavigator().addView(Pnl_Login.VIEWNAME, Pnl_Login.class);
		getNavigator().addView(HomePanel.VIEWNAME, HomePanel.class);
		getNavigator().addView(SearchPanel.VIEWNAME, SearchPanel.class);
		getNavigator().addView(Pnl_JTrackInventoryView.VIEWNAME, Pnl_Login.class);
		getNavigator().navigateTo(Pnl_JTrackInventoryView.VIEWNAME);
	}
}
