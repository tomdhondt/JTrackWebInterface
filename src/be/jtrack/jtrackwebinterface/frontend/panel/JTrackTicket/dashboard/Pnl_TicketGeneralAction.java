package be.jtrack.jtrackwebinterface.frontend.panel.JTrackTicket.dashboard;

import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.TextField;
import com.vaadin.ui.Window;
import com.vaadin.ui.Button.ClickEvent;

import be.jtrack.jtrackwebinterface.frontend.panel.L18NPanel;

public class Pnl_TicketGeneralAction extends L18NPanel {
	/**
	 * Serial version ID
	 */
	private static final long serialVersionUID = 7742734090176308380L;
	/*
	 * Instance members
	 */
	private final String abstractTextFieldWidth = "75px";
	/* Window */
	private Window wdw_TicketGeneral;
	/* GridView */
	private GridLayout grd_General;
	/* Label */
	private Label lbl_Title;
	private Label lbl_NumberOfOpenTicket;
	private Label lbl_NumberOfCloseTicket;
	private Label lbl_NumberOfNewTicket;
	/* Button */
	private Button btn_New;
	/* TextField */
	private TextField txt_NumberOfOpenTicket;
	private TextField txt_NumberOfCloseTicket;
	private TextField txt_NumberOfNewTicket;
	/**
	 * Constructor for the Class
	 */
	public Pnl_TicketGeneralAction(Pnl_TicketGeneral parent){
		init();
	}
	private void init(){
		/* Label */
		this.lbl_Title = new Label(captions.getString("CAP.PNL.6"));
		this.lbl_Title.setStyleName("header");
		this.lbl_NumberOfOpenTicket = new Label(captions.getString("CAP.LBL.24"));
		this.lbl_NumberOfCloseTicket = new Label(captions.getString("CAP.LBL.25"));
		this.lbl_NumberOfNewTicket = new Label(captions.getString("CAP.LBL.26"));
		/* Button */
		this.btn_New = new Button(captions.getString("CAP.BTN.10"));
		this.btn_New.setWidth(this.getWidth(), this.getWidthUnits());
		this.btn_New.addClickListener(new Button.ClickListener() {
			/**
			 * Serial Version iD
			 */
			private static final long serialVersionUID = -7899291233506208783L;
			@Override
			public void buttonClick(ClickEvent event) {
				/* initialize Window to confirm deleting the connection*/
				wdw_TicketGeneral = new Window(captions.getString("CAP.PNL.6"));
				wdw_TicketGeneral.setClosable(false);
				wdw_TicketGeneral.setResizable(false);
				wdw_TicketGeneral.setModal(true);
				wdw_TicketGeneral.setContent(new Pnl_TicketCreate(wdw_TicketGeneral));
				wdw_TicketGeneral.setWidth("50%");
				/* show the notification window */
				getUI().addWindow(wdw_TicketGeneral);
			}
		});
		/* TextField */
		this.txt_NumberOfOpenTicket = new TextField();
		this.txt_NumberOfCloseTicket = new TextField();
		this.txt_NumberOfNewTicket = new TextField();
		this.txt_NumberOfOpenTicket.setWidth(this.abstractTextFieldWidth);
		this.txt_NumberOfCloseTicket.setWidth(this.abstractTextFieldWidth);
		this.txt_NumberOfNewTicket.setWidth(this.abstractTextFieldWidth);
		/* GridLayout */
		this.grd_General = new GridLayout(2,5);
		this.grd_General.addComponent(this.lbl_Title,0,0);
		this.grd_General.addComponent(this.lbl_NumberOfOpenTicket,0,1);
		this.grd_General.addComponent(this.lbl_NumberOfNewTicket,0,2);
		this.grd_General.addComponent(this.lbl_NumberOfCloseTicket,0,3);
		this.grd_General.addComponent(this.txt_NumberOfOpenTicket,1,1);
		this.grd_General.addComponent(this.txt_NumberOfNewTicket,1,2);
		this.grd_General.addComponent(this.txt_NumberOfCloseTicket,1,3);
		this.grd_General.setComponentAlignment(this.lbl_NumberOfOpenTicket,Alignment.MIDDLE_LEFT);
		this.grd_General.setComponentAlignment(this.lbl_NumberOfNewTicket,Alignment.MIDDLE_LEFT);
		this.grd_General.setComponentAlignment(this.lbl_NumberOfCloseTicket,Alignment.MIDDLE_LEFT);
		this.grd_General.setComponentAlignment(this.txt_NumberOfOpenTicket,Alignment.BOTTOM_RIGHT);
		this.grd_General.setComponentAlignment(this.txt_NumberOfNewTicket,Alignment.BOTTOM_RIGHT);
		this.grd_General.setComponentAlignment(this.txt_NumberOfCloseTicket,Alignment.BOTTOM_RIGHT);
		this.grd_General.addComponent(this.btn_New,0,4,1,4);
		this.grd_General.setHeight("100%");
		this.grd_General.setWidth("100%");
		this.grd_General.setSizeFull();
		this.grd_General.setMargin(new MarginInfo(true, true, true, true));
		this.setContent(this.grd_General);
		/* general */
		this.setWidth("300px");
	}
}
