package be.jtrack.jtrackwebinterface.frontend.panel.JTrackTicket.dashboard;

import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.GridLayout;

import be.jtrack.jtrackwebinterface.frontend.panel.L18NPanel;

public class Pnl_TicketCreate extends L18NPanel{
	/* 
	 * Instance members 
	 */
	/* GridView */
	private GridLayout grd_General;
	/**
	 * Default constructor for the Class
	 */
	public Pnl_TicketCreate(){
		init();
	}
	/*
	 * method will init the Panel
	 */
	private void init(){
		/* GridLayout */
		this.grd_General = new GridLayout(2,1);
		this.grd_General.addComponent(new Pnl_TicketGeneralAction(),0,0);
		this.grd_General.addComponent(new Pnl_TicketDashBoard(),1,0);
		this.grd_General.setColumnExpandRatio(0, 0);
		this.grd_General.setColumnExpandRatio(1, 1);
		this.grd_General.setHeight("100%");
		this.grd_General.setWidth("100%");
		this.grd_General.setSizeFull();
		this.grd_General.setMargin(new MarginInfo(true, true, true, true));
		this.setContent(this.grd_General);
	}
}
