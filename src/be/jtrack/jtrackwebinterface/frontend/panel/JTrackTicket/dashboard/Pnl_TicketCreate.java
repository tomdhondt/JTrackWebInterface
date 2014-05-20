package be.jtrack.jtrackwebinterface.frontend.panel.JTrackTicket.dashboard;

import java.util.HashMap;
import java.util.Map;

import main.java.info.jtrac.domain.Status;

import com.vaadin.data.Item;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.TextField;
import com.vaadin.ui.Upload;
import com.vaadin.ui.Window;
import com.vaadin.ui.Button.ClickEvent;

import be.jtrack.jtrackwebinterface.frontend.panel.L18NPanel;
import be.jtrack.jtrackwebinterface.util.Icon;

public class Pnl_TicketCreate extends L18NPanel{
	/**
	 * Serial version id
	 */
	private static final long serialVersionUID = -9012174891481299574L;
	/* 
	 * Instance members 
	 */
	private final String abstractComponentHeight = "25px";
	private final String abstractButtonWidht = "120px";
	private final String abstractComponentWidht = "340px";
	/* GridView */
	private GridLayout grd_General;
	/* Button */
	private Button btn_Save;
	/* TextArea */
	private TextArea txa_TicketDescription;
	/* TextField */
	private TextField txt_TicketTitle;
	/* FileUpload */
	private Upload upl_Attachment;
	/* ComboBox */
	private ComboBox cmb_AllocateTo;
	private ComboBox cmb_State;
	/* Label */
	private Label lbl_Title;
	private Label lbl_TicketDescription;
	private Label lbl_TicketTitle;
	private Label lbl_AllocateTo;
	private Label lbl_State;
	private Label lbl_Attachment;
	/* data */
	private Map<Item, Status> map_ItemStatus;
	/* Window */
	private Window wdw_Parent;
	/**
	 * Default constructor for the Class
	 */
	public Pnl_TicketCreate(){
		init();
	}
	/**
	 * Constructor for the class
	 * @param parent as Window
	 */
	public Pnl_TicketCreate(Window parent){
		this();
		this.wdw_Parent = parent;
		init();
	}
	/*
	 * method will init the Panel
	 */
	private void init(){
		/* data */
		this.map_ItemStatus = new HashMap<Item, Status>();
		/* Upload */
		this.upl_Attachment = new Upload();
		/* Button */
		this.btn_Save = new Button(captions.getString("CAP.BTN.1"));
		this.btn_Save.setWidth(abstractButtonWidht);
		this.btn_Save.setHeight(abstractComponentHeight);
		this.btn_Save.setIcon(Icon.iconSave);
		this.btn_Save.addClickListener(new Button.ClickListener() {
			/**
			 * Serial Version iD
			 */
			private static final long serialVersionUID = -7899291233506208783L;
			@Override
			public void buttonClick(ClickEvent event) {
				wdw_Parent.close();
			}
		});
		/* TextArea */
		this.txa_TicketDescription = new TextArea();
		this.txa_TicketDescription.setWidth(abstractComponentWidht);
		/* TextField */
		this.txt_TicketTitle = new TextField();
		this.txt_TicketTitle.setWidth(abstractComponentWidht);
		this.txt_TicketTitle.setHeight(abstractComponentHeight);
		/* ComboBox */
		this.cmb_State = new ComboBox();
		this.cmb_State.setWidth(abstractComponentWidht);
		this.cmb_State.setHeight(abstractComponentHeight);
		this.initCmbState(this.cmb_State);
		this.cmb_AllocateTo = new ComboBox();
		this.cmb_AllocateTo.setWidth(abstractComponentWidht);
		this.cmb_AllocateTo.setHeight(abstractComponentHeight);
		/* Label */
		this.lbl_Title = new Label(captions.getString("CAP.LBL.26"));
		this.lbl_Title.setStyleName("header");
		this.lbl_TicketTitle = new Label(captions.getString("CAP.LBL.27"));
		this.lbl_TicketDescription = new Label(captions.getString("CAP.LBL.28"));
		this.lbl_AllocateTo = new Label(captions.getString("CAP.LBL.29"));
		this.lbl_State = new Label(captions.getString("CAP.LBL.30"));
		this.lbl_Title.setHeight(abstractComponentHeight);
		this.lbl_TicketTitle.setHeight(abstractComponentHeight);
		this.lbl_TicketDescription.setHeight(abstractComponentHeight);
		this.lbl_AllocateTo.setHeight(abstractComponentHeight);
		this.lbl_State.setHeight(abstractComponentHeight);
		/* GridLayout */
		this.grd_General = new GridLayout(2,6);
		this.grd_General.addComponent(this.lbl_Title,0,0);
		this.grd_General.addComponent(this.lbl_TicketTitle,0,1);
		this.grd_General.addComponent(this.lbl_TicketDescription,0,2);
		this.grd_General.addComponent(this.lbl_AllocateTo,0,3);
		this.grd_General.addComponent(this.lbl_State,0,4);
		this.grd_General.addComponent(this.txt_TicketTitle,1,1);
		this.grd_General.addComponent(this.txa_TicketDescription,1,2);
		this.grd_General.addComponent(this.cmb_AllocateTo,1,3);
		this.grd_General.addComponent(this.cmb_State,1,4);
		this.grd_General.addComponent(this.btn_Save,1,5);
		this.grd_General.setComponentAlignment(this.btn_Save, Alignment.BOTTOM_RIGHT);
		this.grd_General.setHeight("100%");
		this.grd_General.setWidth("100%");
		this.grd_General.setSizeFull();
		this.grd_General.setMargin(new MarginInfo(true, true, true, true));
		this.setContent(this.grd_General);
	}
	/*
	 * Method will init the ComboBox For the Status
	 * @param combo
	 */
	private void initCmbState(ComboBox combo){
		if(null != combo){
			// Clean the ComboBox
			for(Status s : Status.values()){
				Item item = combo.addItem(captions.getString(s.getCaption()));
				map_ItemStatus.put(item, s);
			}
		}
	}
}
