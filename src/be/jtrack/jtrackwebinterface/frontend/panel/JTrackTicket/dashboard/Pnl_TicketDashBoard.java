package be.jtrack.jtrackwebinterface.frontend.panel.JTrackTicket.dashboard;

import java.util.ArrayList;
import java.util.List;

import main.java.info.jtrac.service.dto.ItemDTO;
import main.java.info.jtrac.service.dto.SpaceDTO;
import main.java.info.jtrac.service.dto.ItemDTO;

import com.vaadin.data.Item;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Table;
import com.vaadin.ui.Window;
import com.vaadin.ui.Button.ClickEvent;

import be.jtrack.jtrackwebinterface.frontend.panel.L18NPanel;
import be.jtrack.jtrackwebinterface.util.Icon;

public class Pnl_TicketDashBoard extends L18NPanel{
	/**
	 * Serial version ID
	 */
	private static final long serialVersionUID = -8724280411126668124L;
	/* instance members */
	private final String abstractComponentHeight = "25px";
	private final String abstractButtonWidht = "120px";
	/* Window */
	private Window win_BaseWindow;
	/* GridView */
	private GridLayout grd_General;
	/* Label */
	private Label lbl_Title;
	/* Button */
	private Button btn_Close;
	/* Table */
	private Table tbl_TicketOverview;
	private Table tbl_Status;
	/* Data */
	private List<String> lst_Ticket;
	private List<ItemDTO> lst_ItemDTO;
	/**
	 * Default constructor for the class
	 */
	public Pnl_TicketDashBoard(){
		this.lst_Ticket = new ArrayList<String>();
		for(int i = 0; i < 3 ; i++){
			this.lst_Ticket.add("green");
			this.lst_Ticket.add("blue");
			this.lst_Ticket.add("yellow");
			this.lst_Ticket.add("red");
		}
		this.init();
	}
	/**
	 * Constructor for the Class
	 */
	public Pnl_TicketDashBoard(Window window){
		this();
		this.win_BaseWindow = window;
	}
	/*
	 * Method will init the panel
	 */
	private void init(){
		/* Label */
		this.lbl_Title= new Label(captions.getString("CAP.LBL.23"));
		this.lbl_Title.setStyleName("header"); 
		/* Button */
		this.btn_Close = new Button(captions.getString("CAP.BTN.9"));
		this.btn_Close.setWidth(this.abstractButtonWidht);
		this.btn_Close.setIcon(Icon.iconClose);
		this.btn_Close.setHeight(this.abstractComponentHeight);
		this.btn_Close.addClickListener(new Button.ClickListener() {
			/**
			 * Serial Version iD
			 */
			private static final long serialVersionUID = -7899291233506208783L;
			@Override
			public void buttonClick(ClickEvent event) {
				win_BaseWindow.close();
			}
		});
		/* Table */
		this.tbl_TicketOverview = this.createTable();
		this.tbl_Status = this.createTable();
		this.init_tbl_Status(this.tbl_Status);
		this.init_tbl_ApplicationOverview(this.tbl_TicketOverview);
		/* GridLayout */
		this.grd_General = new GridLayout(2,3);
		this.grd_General.addComponent(this.lbl_Title,0,0);
		this.grd_General.addComponent(this.tbl_Status,0,1,0,1);
		this.grd_General.addComponent(this.tbl_TicketOverview,1,1,1,1);
		this.grd_General.addComponent(this.btn_Close,1,2);
		this.grd_General.setComponentAlignment(this.btn_Close, Alignment.BOTTOM_RIGHT);
		this.grd_General.setHeight("100%");
		this.grd_General.setWidth("100%");
		this.grd_General.setSizeFull();
		this.grd_General.setMargin(new MarginInfo(true, true, true, true));
		this.setContent(this.grd_General);
	}
	/*
	 * Create the basic table for the view
	 */
	public Table createTable(){
		Table table = new Table();
		table.setWidth("100%");
		table.setHeight("100%");
		table.setSelectable(true);
		table.addContainerProperty(captions.getString("CAP.TBL.3"), Integer.class,null);
		table.addContainerProperty(captions.getString("CAP.TBL.19"), String.class,null);
		return table;
	}
	/*
	 * Create the basic table for the view
	 */
	public Table createStatusTable(){
		Table table = new Table();
		table.setWidth("100%");
		table.setHeight("100%");
		table.setSelectable(true);
		table.addContainerProperty(captions.getString("CAP.TBL.3"), Integer.class,null);
		table.addContainerProperty(captions.getString("CAP.TBL.19"), String.class,null);
		return table;
	}
	/*
	 * Method will set the values in the Table
	 */
	private void init_tbl_ApplicationOverview(Table table) {
		int counter = 0;
		for(String object : this.lst_Ticket){
			table.addItem(new Object[] {++counter, object}, null);
		}
	}
	/*
	 * Method will set the values in the Table
	 */
	private void init_tbl_Status(Table table) {
		int counter = 0;
		for(String object : this.lst_Ticket){
			table.addItem(new Object[] {++counter, object}, null);
		}
		table.setCellStyleGenerator(new Table.CellStyleGenerator() {
			/**
			 * Serial version id
			 */
			private static final long serialVersionUID = 3132626962286547998L;
			@Override
			public String getStyle(Table source, Object itemId, Object propertyId) {
				Item item = source.getItem(itemId);
				
				return (String) item.getItemProperty(captions.getString("CAP.TBL.19")).getValue();
			}
		});
	}

}
