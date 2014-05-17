package be.jtrack.jtrackwebinterface.frontend.panel.JTrackTicket.dashboard;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.Button;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.Label;

import be.jtrack.jtrackwebinterface.frontend.panel.L18NPanel;

public class Pnl_TicketGeneral extends L18NPanel implements View{
	public static final String VIEWNAME = "JTrackTicketView";
	/**
	 * Serial version ID
	 */
	private static final long serialVersionUID = 7742734090176308380L;
	/* 
	 * Instance members 
	 */
	/* GridView */
	private GridLayout grd_General;
	/* Label */
	private Label lbl_Title;
	/* Button */
	private Button btn_New;
	/**
	 * Default constructor for the Class
	 */
	public Pnl_TicketGeneral(){
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
//		this.grd_General.setComponentAlignment(this.btn_Close, Alignment.BOTTOM_RIGHT);
		this.grd_General.setColumnExpandRatio(0, 0);
		this.grd_General.setColumnExpandRatio(1, 1);
		this.grd_General.setHeight("100%");
		this.grd_General.setWidth("100%");
		this.grd_General.setSizeFull();
		this.grd_General.setMargin(new MarginInfo(true, true, true, true));
		this.setContent(this.grd_General);
	}
	@Override
	public void enter(ViewChangeEvent event) {
	}
}
