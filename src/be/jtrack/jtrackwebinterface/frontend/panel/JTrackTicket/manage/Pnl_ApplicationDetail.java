package be.jtrack.jtrackwebinterface.frontend.panel.JTrackTicket.manage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import main.java.info.jtrac.service.dto.SpaceDTO;
import be.jtrack.jtrackwebinterface.frontend.panel.L18NPanel;
import be.jtrack.jtrackwebinterface.util.Icon;

import com.vaadin.data.Item;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Table;
import com.vaadin.ui.TextField;

public class Pnl_ApplicationDetail extends L18NPanel{
	/**
	 * Serial version ID
	 */
	private static final long serialVersionUID = 203587219333323242L;
	/* instance members */
	private final String abstractComponentHeight = "25px";
	private final String abstractButtonWidht = "120px";
	private final String abstractComponentWidht = "340px";
	/* Label */
	private Label lbl_Space_Details;
	private Label lbl_Space_DisplayName;
	private Label lbl_Space_SpaceKey;
	private Label lbl_Space_Description;
	private Label lbl_Space_MakePublic;
	private Label lbl_Space_CopyExisting;
	/* TextField */ 
	private TextField txt_Space_DisplayName;
	private TextField txt_Space_SpaceKey;
	private TextField txt_Space_Description;
	private TextField txt_Space_MakePublic;
	/* Combobox */
	private ComboBox cmb_Spaces;
	/* Button */
	private Button btn_CopySpaceDTO;
	private Button btn_Close;
	private Button btn_Save;
	/* GridLayout */
	private GridLayout grd_General;
	/* List */
	private List<SpaceDTO> lst_SpaceDTO;
	private Map<Item, SpaceDTO> map_ItemSpaceDTO;
	/**
	 * Default constructor for the Class
	 */
	public Pnl_ApplicationDetail(){
		this.init();
	}
	/**
	 * Constructor for the Class
	 * @param listSpaceDTO as List<SpaceDTO>
	 */
	public Pnl_ApplicationDetail(List<SpaceDTO> listSpaceDTO){
		this();
		this.lst_SpaceDTO = listSpaceDTO;
		this.initComboBoxSpaceDTO();
	}
	/*
	 * Method will initialize the panel
	 */
	private void init(){
		/* data */
		this.lst_SpaceDTO = new ArrayList<SpaceDTO>();
		this.map_ItemSpaceDTO = new HashMap<Item, SpaceDTO>();
		/* Button */
		this.btn_CopySpaceDTO = new Button(captions.getString("CAP.BTN.2"));
		this.btn_CopySpaceDTO.setWidth(this.abstractComponentWidht);
		this.btn_CopySpaceDTO.setHeight(this.abstractComponentHeight);
		this.btn_Close = new Button(captions.getString("CAP.BTN.9"));
		this.btn_Close.setHeight(this.abstractComponentHeight);
		this.btn_Close.setWidth(this.abstractButtonWidht);
		this.btn_Close.setIcon(Icon.iconClose);
		this.btn_Save = new Button(captions.getString("CAP.BTN.1"));
		this.btn_Save.setHeight(this.abstractComponentHeight);
		this.btn_Save.setWidth(this.abstractButtonWidht);
		this.btn_Save.setIcon(Icon.iconSave);
		/* ComboBox */
		this.cmb_Spaces = new ComboBox();
		this.cmb_Spaces.setHeight(this.abstractComponentHeight);
		this.cmb_Spaces.setWidth(this.abstractComponentWidht);
		/* Label */
		this.lbl_Space_Details = new Label(captions.getString("CAP.LBL.16"));
		this.lbl_Space_Details.setStyleName("header");
		this.lbl_Space_DisplayName = new Label(captions.getString("CAP.LBL.17"));
		this.lbl_Space_SpaceKey = new Label(captions.getString("CAP.LBL.18"));
		this.lbl_Space_Description = new Label(captions.getString("CAP.LBL.19"));
		this.lbl_Space_MakePublic = new Label(captions.getString("CAP.LBL.20"));
		this.lbl_Space_CopyExisting = new Label(captions.getString("CAP.LBL.21"));
		this.lbl_Space_DisplayName.setHeight(this.abstractComponentHeight);
		this.lbl_Space_SpaceKey.setHeight(this.abstractComponentHeight);
		this.lbl_Space_Description.setHeight(this.abstractComponentHeight);
		this.lbl_Space_MakePublic.setHeight(this.abstractComponentHeight);
		this.lbl_Space_CopyExisting.setHeight(this.abstractComponentHeight);
		this.lbl_Space_Details.setHeight(this.abstractComponentHeight);
		/* TextField */
		this.txt_Space_DisplayName = new TextField();
		this.txt_Space_SpaceKey = new TextField();
		this.txt_Space_Description = new TextField();
		this.txt_Space_MakePublic = new TextField();
		this.txt_Space_DisplayName.setHeight(this.abstractComponentHeight);
		this.txt_Space_SpaceKey.setHeight(this.abstractComponentHeight);
		this.txt_Space_Description.setHeight(this.abstractComponentHeight);
		this.txt_Space_MakePublic.setHeight(this.abstractComponentHeight);
		this.txt_Space_DisplayName.setWidth(this.abstractComponentWidht);
		this.txt_Space_SpaceKey.setWidth(this.abstractComponentWidht);
		this.txt_Space_Description.setWidth(this.abstractComponentWidht);
		this.txt_Space_MakePublic.setWidth(this.abstractComponentWidht);
		/* GridLayout */
		this.grd_General = new GridLayout(2,8);
		this.grd_General.addComponent(this.lbl_Space_Details,0,0);
		this.grd_General.addComponent(this.lbl_Space_DisplayName,0,1);
		this.grd_General.addComponent(this.lbl_Space_SpaceKey,0,2);
		this.grd_General.addComponent(this.lbl_Space_Description,0,3);
		this.grd_General.addComponent(this.lbl_Space_MakePublic,0,4);
		this.grd_General.addComponent(this.lbl_Space_CopyExisting,0,5);
		this.grd_General.addComponent(this.txt_Space_DisplayName,1,1,1,1);
		this.grd_General.addComponent(this.txt_Space_SpaceKey,1,2,1,2);
		this.grd_General.addComponent(this.txt_Space_Description,1,3,1,3);
		this.grd_General.addComponent(this.txt_Space_MakePublic,1,4,1,4);
		this.grd_General.addComponent(this.cmb_Spaces,1,5,1,5);
		this.grd_General.addComponent(this.btn_CopySpaceDTO,1,6,1,6);
		this.grd_General.addComponent(this.btn_Close,0,7);
		this.grd_General.addComponent(this.btn_Save,1,7);
		this.grd_General.setComponentAlignment(this.btn_Close, Alignment.BOTTOM_RIGHT);
		this.grd_General.setComponentAlignment(this.btn_Save, Alignment.BOTTOM_LEFT);
		this.grd_General.setSizeFull();
		this.grd_General.setMargin(new MarginInfo(true, true, true, true));
		this.setContent(this.grd_General);
	}
	/*
	 * Method will init the ComboBox
	 */
	private void initComboBoxSpaceDTO(){
		for(SpaceDTO dto : lst_SpaceDTO){
			Item object = cmb_Spaces.addItem(dto.getName());
			map_ItemSpaceDTO.put(object, dto);
		}
	}
}
