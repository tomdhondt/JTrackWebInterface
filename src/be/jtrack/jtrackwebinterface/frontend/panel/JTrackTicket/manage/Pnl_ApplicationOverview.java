package be.jtrack.jtrackwebinterface.frontend.panel.JTrackTicket.manage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import main.java.info.jtrac.service.dto.SpaceDTO;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Table;
import com.vaadin.ui.Window;
import com.vaadin.ui.Button.ClickEvent;

import be.jtrack.jtrackwebinterface.frontend.panel.L18NPanel;
import be.jtrack.jtrackwebinterface.util.Icon;

public class Pnl_ApplicationOverview extends L18NPanel{
	/**
	 * Serial Version ID
	 */
	private static final long serialVersionUID = 8384204719450331487L;
	/* instance members */
	private final String abstractComponentHeight = "25px";
	private final String abstractButtonWidht = "120px";
	/* data */
//	private List<SpaceDTO> lst_Space;
	/* Label */
	private Label lbl_Title;
	/* Table */
	private Table tbl_ApplicationOverview;
	/* GridLayout */
	private GridLayout grd_General;
	/* Button */
	private Button btn_New;
	/* data */
	private List<SpaceDTO> lst_SpaceDTO;
	private Map<String, SpaceDTO> map_IdSpaceDTO;
	/* Window */
	private Window wdw_BaseWindow;
	/**
	 * Default constructor for the Class
	 */
	public Pnl_ApplicationOverview(){
		this.init();
		this.setId("01");
		lst_SpaceDTO = new ArrayList<SpaceDTO>();
	}
	public Pnl_ApplicationOverview(List<SpaceDTO> listSpaceDTO){
		this();
		this.lst_SpaceDTO = listSpaceDTO;
		this.init_tbl_ApplicationOverview(this.tbl_ApplicationOverview);
	}
	/*
	 * Method will initialize the Panel
	 */
	private void init(){
		/* data */
		this.map_IdSpaceDTO = new HashMap<String, SpaceDTO>();
		/* Label */
		this.lbl_Title = new Label(captions.getString("CAP.LBL.22"));
		this.lbl_Title.setStyleName("header"); 
		/* Button */
		this.btn_New = new Button(captions.getString("CAP.BTN.3"));
		this.btn_New.setWidth(this.abstractButtonWidht);
		this.btn_New.setIcon(Icon.iconNew);
		this.btn_New.setHeight(this.abstractComponentHeight);
		this.btn_New.addClickListener(new Button.ClickListener() {
			/**
			 * Serial Version iD
			 */
			private static final long serialVersionUID = -7899291233506208783L;
			@Override
			public void buttonClick(ClickEvent event) {
				wdw_BaseWindow = new Window(captions.getString("CAP.PNL.4"));
				wdw_BaseWindow.setClosable(false);
				wdw_BaseWindow.setResizable(false);
				wdw_BaseWindow.setModal(true);
				wdw_BaseWindow.setContent(new Pnl_ApplicationDetail(lst_SpaceDTO, wdw_BaseWindow));
				wdw_BaseWindow.setWidth("50%");
				/* show the notification window */
				getUI().addWindow(wdw_BaseWindow);
			}
		});
		/* Table */
		this.tbl_ApplicationOverview = createTable();
		/* GridLayout */
		this.grd_General = new GridLayout(3,4);
		this.grd_General.addComponent(this.lbl_Title,0,0,2,0);
		this.grd_General.addComponent(this.tbl_ApplicationOverview,0,1,2,2);
		this.grd_General.addComponent(this.btn_New,2,3,2,3);
		this.grd_General.setComponentAlignment(this.btn_New, Alignment.BOTTOM_RIGHT);
		this.grd_General.setHeight("100%");
		this.grd_General.setWidth("100%");
		this.grd_General.setSizeFull();
		this.grd_General.setMargin(new MarginInfo(true, true, true, true));
		this.setContent(this.grd_General);
	}
	/*
	 * Create the basic table for the view
	 */
	private Table createTable(){
		Table table = new Table();
		table.setWidth("100%");
		table.setHeight("100%");
		table.setSelectable(true);
		table.addContainerProperty(captions.getString("CAP.TBL.3"), Integer.class,null);
		table.addContainerProperty(captions.getString("CAP.TBL.14"), String.class,null);
		table.addContainerProperty(captions.getString("CAP.TBL.15"), String.class,null);
		table.addContainerProperty(captions.getString("CAP.TBL.16"), String.class,null);
		table.addContainerProperty(captions.getString("CAP.TBL.17"), String.class,null);
		table.addContainerProperty(captions.getString("CAP.TBL.18"), CheckBox.class,null);
		table.addContainerProperty(captions.getString("CAP.TBL.19"), Button.class,null);
		return table;
	}
	/*
	 * Method will set the values in the Table
	 */
	private void init_tbl_ApplicationOverview(Table table) {
		int counter = 0; 
		for(SpaceDTO spaceDTO : this.lst_SpaceDTO){
			this.map_IdSpaceDTO.put(spaceDTO.getId(), spaceDTO);
			Object child = table.addItem(new Object[] {++counter, spaceDTO.getName(),spaceDTO.getPrefixCode(), spaceDTO.getDescription(), "", new CheckBox(), this.createEditButton(spaceDTO)}, null);
		}
	}
	/*
	 * Method will set the button to edit the selected table row with SpaceDTO
	 */
	private Button createEditButton(SpaceDTO dto){
		/* create the button that will edit the line */
		Button button = new Button(captions.getString("CAP.BTN.7"));
		button.setId(dto.getId());
		button.addClickListener(new Button.ClickListener() {
			/**
			 * Serial Version iD
			 */
			private static final long serialVersionUID = -7899291233506208783L;
			@Override
			public void buttonClick(ClickEvent event) {
				Notification.show("SpaceDTO = " + event.getButton().getId());
			}
		});
		return button;
	}
	
}
