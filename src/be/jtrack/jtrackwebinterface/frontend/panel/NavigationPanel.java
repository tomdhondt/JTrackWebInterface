package be.jtrack.jtrackwebinterface.frontend.panel;

import com.vaadin.ui.Button;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.BaseTheme;

public class NavigationPanel extends L18NPanel {
	/**
	 * Serial Version ID
	 */
	private static final long serialVersionUID = -5475762757547686950L;
	/* Instance members */
	/* Button */
	private Button btn_TeamViewer;
	/* VerticalLayout */
	private VerticalLayout vtl_NavigationPanel;
	/*
	 * Default constructor for the class
	 */
	public NavigationPanel(){
		this.init();
	}
	private void init(){
		/* Button */
		this.btn_TeamViewer = new Button(super.captions.getString("NavigationPanel_btn_TeamViewer"));
		this.btn_TeamViewer.setStyleName(BaseTheme.BUTTON_LINK);
		this.btn_TeamViewer.setWidth("100%");
		this.btn_TeamViewer.setSizeFull();
		/* VerticalLayout */
		this.vtl_NavigationPanel = new VerticalLayout();
		this.vtl_NavigationPanel.setSizeFull();
		this.vtl_NavigationPanel.setWidth("100%");
		this.vtl_NavigationPanel.addComponent(this.btn_TeamViewer);
		this.setContent(this.vtl_NavigationPanel);
	}
}
