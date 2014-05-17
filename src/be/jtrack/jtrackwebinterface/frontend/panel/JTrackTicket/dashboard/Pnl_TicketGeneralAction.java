package be.jtrack.jtrackwebinterface.frontend.panel.JTrackTicket.dashboard;

import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.Button;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.Label;

import be.jtrack.jtrackwebinterface.frontend.panel.L18NPanel;

public class Pnl_TicketGeneralAction extends L18NPanel {
	/**
	 * Serial version ID
	 */
	private static final long serialVersionUID = 7742734090176308380L;
	/* GridView */
	private GridLayout grd_General;
	/* Label */
	private Label lbl_Title;
	/* Button */
	private Button btn_New;
	/**
	 * Constructor for the Class
	 */
	public Pnl_TicketGeneralAction(){
		init();
	}
	private void init(){
		/* Label */
		this.lbl_Title = new Label(captions.getString("CAP.PNL.6"));
		this.lbl_Title.setStyleName("header"); 
		/* Button */
		this.btn_New = new Button(captions.getString("CAP.BTN.3"));
		/* GridLayout */
		this.grd_General = new GridLayout(2,2);
		this.grd_General.addComponent(this.lbl_Title,0,0,1,0);
		this.grd_General.addComponent(this.btn_New,0,1,1,1);
//		this.grd_General.setComponentAlignment(this.btn_Close, Alignment.BOTTOM_RIGHT);
		this.grd_General.setHeight("100%");
		this.grd_General.setWidth("100%");
		this.grd_General.setSizeFull();
		this.grd_General.setMargin(new MarginInfo(true, true, true, true));
		this.setContent(this.grd_General);
//		this.btn_New.setWidth(this.grd_General.getWidth(),grd_General.getWidthUnits());
		/* general */
		this.setWidth("250px");
	}
}
