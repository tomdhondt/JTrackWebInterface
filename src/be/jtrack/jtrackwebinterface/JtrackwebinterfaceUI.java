package be.jtrack.jtrackwebinterface;

import javax.servlet.annotation.WebServlet;

import be.jtrack.jtrackwebinterface.frontend.panel.global.DashboardPanel;

import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.UI;

@SuppressWarnings("serial")
public class JtrackwebinterfaceUI extends UI {
	/*
	 * Instance members 
	 */
	@Override
	protected void init(VaadinRequest request) {
	}
	@WebServlet(value = "/*", asyncSupported = true)
	@VaadinServletConfiguration(productionMode = false, ui = DashboardPanel.class)
	public static class Servlet extends VaadinServlet {
	}

//	@Override
//	protected void init(VaadinRequest request) {
//		final VerticalLayout layout = new VerticalLayout();
//		layout.setMargin(true);
//		setContent(layout);
//
//		Button button = new Button("Click Me");
//		button.addClickListener(new Button.ClickListener() {
//			public void buttonClick(ClickEvent event) {
//				layout.addComponent(new Label("Thank you for clicking"));
//			}
//		});
//		layout.addComponent(button);
//	}

}