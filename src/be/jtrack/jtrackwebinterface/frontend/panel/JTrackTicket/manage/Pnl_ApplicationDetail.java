package be.jtrack.jtrackwebinterface.frontend.panel.JTrackTicket.manage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import main.java.info.jtrac.dao.NameQueryParam;
import main.java.info.jtrac.exception.manager.ManagerException;
import main.java.info.jtrac.service.dto.MetadataDTO;
import main.java.info.jtrac.service.dto.SpaceDTO;
import main.java.info.jtrac.service.manager.IManager;
import be.jtrack.jtrackwebinterface.frontend.panel.L18NPanel;
import be.jtrack.jtrackwebinterface.util.Icon;

import com.vaadin.data.Item;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.TextField;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Window;
@SuppressWarnings("unchecked")
public class Pnl_ApplicationDetail extends L18NPanel{
	/**
	 * Serial version ID
	 */
	private static final long serialVersionUID = 203587219333323242L;
	/* instance members */
	private final String abstractComponentHeight = "25px";
	private final String abstractButtonWidht = "120px";
	private final String abstractComponentWidht = "340px";
	/*
	 * Instance members
	 */
	ApplicationContext context = new ClassPathXmlApplicationContext(new String[] {"applicationContent.xml"});
	MetadataDTO defaultMetaDataDTO = (MetadataDTO) context.getBean("defaultMetaDataDTO");
	IManager<SpaceDTO> iSpaceManager = (IManager<SpaceDTO>) context.getBean("iSpaceManager");
	IManager<MetadataDTO> iMetadataManager = (IManager<MetadataDTO>) context.getBean("iMetadataManager");
	/* Label */
	private Label lbl_Space_Details;
	private Label lbl_Space_DisplayName;
	private Label lbl_Space_SpaceKey;
	private Label lbl_Space_Description;
	private Label lbl_Space_MakePublic;
	private Label lbl_Space_CopyExisting;
	private Label lbl_SpaceDTO_Id;
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
	/* data */
	private List<SpaceDTO> lst_SpaceDTO;
	private Map<Item, SpaceDTO> map_ItemSpaceDTO;
	private MetadataDTO dto_Metadata;
	private SpaceDTO obj_SpaceDTO;
	/* Window */
	private Window win_ParentWindow;
	/* Panel */
	private Pnl_ApplicationOverview pnl_Parent;
	/* CheckBox */
	private CheckBox chb_GuestAllowed;
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
	public Pnl_ApplicationDetail(Pnl_ApplicationOverview pnl, List<SpaceDTO> listSpaceDTO, Window window, SpaceDTO dto){
		this();
		this.pnl_Parent = pnl;
		this.lst_SpaceDTO = listSpaceDTO;
		this.initComboBoxSpaceDTO();
		this.win_ParentWindow = window;
		this.obj_SpaceDTO = dto;
		this.initFields();
	}
	/*
	 * Method will initialize the panel
	 */
	private void init(){
		/* data */
		this.lst_SpaceDTO = new ArrayList<SpaceDTO>();
		this.lbl_SpaceDTO_Id = new Label();
		this.map_ItemSpaceDTO = new HashMap<Item, SpaceDTO>();
		/* Checkbox */
		this.chb_GuestAllowed = new CheckBox();
		this.chb_GuestAllowed.setValue(true);
		/* Button */
		this.btn_CopySpaceDTO = new Button(captions.getString("CAP.BTN.2"));
		this.btn_CopySpaceDTO.setWidth(this.abstractComponentWidht);
		this.btn_CopySpaceDTO.setHeight(this.abstractComponentHeight);
		this.btn_CopySpaceDTO.addClickListener(new Button.ClickListener() {
			/**
			 * Serial Version iD
			 */
			private static final long serialVersionUID = -7899291233506208783L;
			@Override
			public void buttonClick(ClickEvent event) {
				SpaceDTO dto = map_ItemSpaceDTO.get(cmb_Spaces.getValue());
				Notification.show(dto.getDescription());
			}
		});
		this.btn_Close = new Button(captions.getString("CAP.BTN.9"));
		this.btn_Close.setHeight(this.abstractComponentHeight);
		this.btn_Close.setWidth(this.abstractButtonWidht);
		this.btn_Close.setIcon(Icon.iconClose);
		this.btn_Close.addClickListener(new Button.ClickListener() {
			/**
			 * Serial Version iD
			 */
			private static final long serialVersionUID = -7899291233506208783L;
			@Override
			public void buttonClick(ClickEvent event) {
				win_ParentWindow.close();
			}
		});
		this.btn_Save = new Button(captions.getString("CAP.BTN.1"));
		this.btn_Save.setHeight(this.abstractComponentHeight);
		this.btn_Save.setWidth(this.abstractButtonWidht);
		this.btn_Save.setIcon(Icon.iconSave);
		this.btn_Save.addClickListener(new Button.ClickListener() {
			/**
			 * Serial Version iD
			 */
			private static final long serialVersionUID = -7899291233506208783L;
			@Override
			public void buttonClick(ClickEvent event) {
				System.out.println( lbl_SpaceDTO_Id.getValue());
				if(persistDefaultMetadataDTO()){
					if(null != lbl_SpaceDTO_Id.getValue() && 0 < lbl_SpaceDTO_Id.getValue().length()){
						try {
							iSpaceManager.update(getSpaceDTO());
						} catch (ManagerException e) {
							Notification.show(e.getMessage());
						}
					}else{
						try {
							SpaceDTO dto_Space = getSpaceDTO();
							dto_Space.setMd_Id(dto_Metadata.getId());
							iSpaceManager.persist(dto_Space);
							/* get the object out the database */
							List<NameQueryParam> list = new ArrayList<NameQueryParam>();
							list.add(new NameQueryParam(1,"name",dto_Space.getName()));
							List<SpaceDTO> listResult = iSpaceManager.findByCriteria(list, "Space_findByName");
							/* refresh the table content */
							if(null != listResult && listResult.size() > 0){
								if( null != listResult.get(listResult.size()-1)){
									pnl_Parent.refreshTable(listResult.get(listResult.size()-1));
								}
							}
						} catch (ManagerException e) {
							Notification.show(e.getMessage());
						}
					}
					Notification.show(captions.getString("CAP.DESC.8"));
					win_ParentWindow.close();
				}else{
					Notification.show(captions.getString("CAP.DESC.19"));
				}
			}
		});
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
		this.grd_General.addComponent(this.chb_GuestAllowed,1,4,1,4);
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
	/*
	 * Method will get the info out the TextFields
	 * @return
	 */
	private SpaceDTO getSpaceDTO(){
		SpaceDTO dto = null;		
		if(null != lbl_SpaceDTO_Id.getValue() && 0 == lbl_SpaceDTO_Id.getValue().length()){
			/* new SpaceDTO */
			dto = new SpaceDTO();
		}else{
			/* get the object out the map and update the values */
			for(SpaceDTO d : lst_SpaceDTO){
				if(lbl_SpaceDTO_Id.getValue().equals(d.getId())){
					dto = d;
				}
			}
		}
		dto.setDescription(txt_Space_Description.getValue());
		dto.setName(this.txt_Space_DisplayName.getValue());
		dto.setPrefixCode(this.txt_Space_SpaceKey.getValue());
		dto.setGuestAllowed(this.chb_GuestAllowed.getValue());
		return dto;
	}
	/*
	 * Method will persist the and create a default MetadataDTO object
	 */
	private boolean persistDefaultMetadataDTO(){
		boolean success = true; 
		try {
			this.defaultMetaDataDTO.setXmlString("<metadata><fields></fields></metadata>");
			this.iMetadataManager.persist(this.defaultMetaDataDTO);
		} catch (ManagerException e) {
			success = false;
		}
		try {
			List<NameQueryParam> list = new ArrayList<NameQueryParam>();
			list.add(new NameQueryParam(1,"name",defaultMetaDataDTO.getName()));
			List<MetadataDTO> listResult = this.iMetadataManager.findByCriteria(list, "findByName");
			if(null != listResult && listResult.size() > 0){
				this.dto_Metadata = listResult.get(listResult.size()-1);
			}
		} catch (ManagerException e) {
			success = false;
		}
		return success;
	}
	/*
	 * Method will init the fields when editing a value
	 */
	private void initFields() {
		if(null != this.obj_SpaceDTO){
			this.lbl_SpaceDTO_Id.setValue(obj_SpaceDTO.getId());
			this.txt_Space_Description.setValue(obj_SpaceDTO.getDescription());
			this.txt_Space_DisplayName.setValue(obj_SpaceDTO.getName());
			this.txt_Space_SpaceKey.setValue(obj_SpaceDTO.getPrefixCode());
			this.chb_GuestAllowed.setValue(obj_SpaceDTO.isGuestAllowed());
		}
	}
}
