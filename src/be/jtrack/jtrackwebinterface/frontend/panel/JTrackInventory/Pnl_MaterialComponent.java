package be.jtrack.jtrackwebinterface.frontend.panel.JTrackInventory;

import java.util.Map;
import java.util.TreeMap;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.vaadin.server.Resource;
import com.vaadin.shared.ui.combobox.FilteringMode;
import com.vaadin.ui.AbstractComponent;
import com.vaadin.ui.Accordion;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.Table;
import com.vaadin.ui.TextField;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.TreeTable;

import be.jtrack.jtrackwebinterface.frontend.panel.global.L18NPanel;
import be.jtrack.jtrackwebinterface.listner.Lsn_FocusListner_TextField_HelpText;
import be.jtrack.jtrackwebinterface.util.Icon;
import be.jtrackinventory.exception.manager.ManagerException;
import be.jtrackinventory.service.dto.MaterialComponentDTO;
import be.jtrackinventory.service.dto.PropertyDTO;
import be.jtrackinventory.service.dto.PropertyGroupDTO;
import be.jtrackinventory.service.manager.IManager;

@SuppressWarnings("unchecked")
public class Pnl_MaterialComponent extends L18NPanel{
	/**
	 * Serial Version ID
	 */
	private static final long serialVersionUID = -8502732214768485811L; 
	/*
	 * Instance members
	 */
	ApplicationContext context = new ClassPathXmlApplicationContext(new String[] {"applicationContent.xml"});
	IManager<PropertyGroupDTO> iManagerPropertyGroup = (IManager<PropertyGroupDTO>) context.getBean("iManagerPropertyGroup");
	IManager<MaterialComponentDTO> iManagerMaterialComponent = (IManager<MaterialComponentDTO>) context.getBean("iManagerMaterialComponent");
	/* data */
	private Map<String, MaterialComponentDTO> mapMaterialComponent = new TreeMap<String, MaterialComponentDTO>();
	private Map<String, PropertyGroupDTO> mapPropertyGroup = new TreeMap<String, PropertyGroupDTO>();
	private Map<String, PropertyGroupDTO> mapPropertyGroupCaption = new TreeMap<String, PropertyGroupDTO>();
	private Map<String, PropertyDTO> mapProperty = new TreeMap<String, PropertyDTO>();
	/* GridLayout */
	private GridLayout grd_MaterialComponent;
	/* size */
	private final String abstractComponentHeight = "25px";
	private final String abstractButtonWidht = "120px";
	private final String abstractComponentWidht = "340px";
	/* Label */
	private Label lbl_PropertySettingsDescription;
	/* ComboBox */
	private ComboBox cmb_PropertyGroup;
	/* Accordion */
	private Accordion acr_PropertyGroupSettingsDescription;
	/* Button */
	private Button btn_Save;
	private Button btn_New;
	private Button btn_Copy;
	private Button btn_Delete;
	/* TextField */
	private TextField txt_UniqueId;
	private TextField txt_Caption;
	private TextField txt_Description;
	/* TabSheet */
	private TabSheet tbs_MaterialComponent;
	/* Table */
	private Table tbl_MaterialComponentSettings;
	private TreeTable tbl_MaterialComponentGroupProperty;
	/* String */
	private String materialComponentUniqueID;
	/* TreeTable */
	private TreeTable trt_JTrackInventoryView_Base;
	/**
	 * Default constructor for the Class
	 */
	public Pnl_MaterialComponent(){
		this.init();
	}
	/**
	 * Constructor for the Class
	 * @param materialComponentDTO as MaterialComponentDTO
	 */
	public Pnl_MaterialComponent(TreeTable jtrackInventoryBase, Map<String, MaterialComponentDTO> mapMaterialComponent, Map<String,PropertyGroupDTO> mapPropertyGroup, Map<String,PropertyDTO> mapProperty, String materialComponentUniqueID){	
		this();
		this.trt_JTrackInventoryView_Base = jtrackInventoryBase;
		this.mapMaterialComponent = mapMaterialComponent;
		this.mapPropertyGroup = mapPropertyGroup;
		this.mapProperty = mapProperty;
		this.materialComponentUniqueID = materialComponentUniqueID;
		this.initFields();
	}
	/*
	 * Init the panel
	 */
	@SuppressWarnings("deprecation")
	private void init(){
		/* Label */
		this.lbl_PropertySettingsDescription = new Label(captions.getString("CAP.LBL.6"));
		/* Accordion */ 
		this.acr_PropertyGroupSettingsDescription = new Accordion();
		this.acr_PropertyGroupSettingsDescription.addTab(this.lbl_PropertySettingsDescription,captions.getString("CAP.TAB.1"),Icon.iconHelp);
		/* TextField */
		this.txt_Caption = new TextField();
		this.txt_Caption.setDescription(captions.getString("CAP.DESC.9"));
		this.txt_Caption.addFocusListener(new Lsn_FocusListner_TextField_HelpText(this.lbl_PropertySettingsDescription, this.txt_Caption));
		this.txt_Caption.setWidth(this.abstractComponentWidht);
		this.txt_Caption.setHeight(this.abstractComponentHeight);
		this.txt_Description = new TextField();
		this.txt_Description.setDescription(captions.getString("CAP.DESC.10"));
		this.txt_Description.addFocusListener(new Lsn_FocusListner_TextField_HelpText(this.lbl_PropertySettingsDescription, this.txt_Description));
		this.txt_Description.setWidth(this.abstractComponentWidht);
		this.txt_Description.setHeight(this.abstractComponentHeight);
		this.txt_UniqueId = new TextField();
		this.txt_UniqueId.setDescription(captions.getString("CAP.DESC.11"));
		this.txt_UniqueId.addFocusListener(new Lsn_FocusListner_TextField_HelpText(this.lbl_PropertySettingsDescription, this.txt_UniqueId));
		this.txt_UniqueId.setWidth(this.abstractComponentWidht);
		this.txt_UniqueId.setHeight(this.abstractComponentHeight);
		this.txt_UniqueId.setEnabled(false);
		/* ComboBox */
		this.cmb_PropertyGroup = new ComboBox();
		this.cmb_PropertyGroup.setHeight(this.abstractComponentHeight);
		this.cmb_PropertyGroup.setWidth(this.abstractComponentWidht);
		this.cmb_PropertyGroup.setDescription(captions.getString("CAP.DESC.3"));
		this.cmb_PropertyGroup.addFocusListener(new Lsn_FocusListner_TextField_HelpText(this.lbl_PropertySettingsDescription, this.cmb_PropertyGroup));
		this.cmb_PropertyGroup.setFilteringMode(FilteringMode.CONTAINS);
		/* Button */
		this.btn_Save = new Button(captions.getString("CAP.BTN.1"));
		this.btn_Copy = new Button(captions.getString("CAP.BTN.2"));
		this.btn_New = new Button(captions.getString("CAP.BTN.3"));
		this.btn_Delete = new Button(captions.getString("CAP.BTN.4"));
		this.btn_Save.setWidth(this.abstractButtonWidht);
		this.btn_Copy.setWidth(this.abstractButtonWidht);
		this.btn_New.setWidth(this.abstractButtonWidht);
		this.btn_Delete.setWidth(this.abstractButtonWidht);
		this.btn_Save.setIcon(Icon.iconSave);
		this.btn_Copy.setIcon(Icon.iconCopy);
		this.btn_New.setIcon(Icon.iconNew);
		this.btn_Delete.setIcon(Icon.iconDelete);
		this.btn_Delete.addClickListener(new Button.ClickListener() {
			/**
			 * Serial Version iD
			 */
			private static final long serialVersionUID = 8431665432682866374L;
			@Override
			public void buttonClick(ClickEvent event) {
				if(txt_UniqueId.getValue() != ""){
					/* delete the object in the database */
					try {
						iManagerMaterialComponent.remove(mapMaterialComponent.get(materialComponentUniqueID).getUniqueId());
						Notification.show(captions.getString("CAP.DESC.6") + " " + mapMaterialComponent.get(materialComponentUniqueID).getCaption());
					} catch (ManagerException mXe) {
						Notification.show(mXe.getCaption());
					}
				}
				/* delete the property to the TreeTable */
				for(Object obj : trt_JTrackInventoryView_Base.getItemIds()){
					if(mapMaterialComponent.get(materialComponentUniqueID).getUniqueId().equals(trt_JTrackInventoryView_Base.getItem(obj).getItemProperty(captions.getString("CAP.TRT.6")).getValue())){
						trt_JTrackInventoryView_Base.removeItem(obj);
					}
				}
				/* clear the PropertyTable */
				tbl_MaterialComponentGroupProperty.removeAllItems();
				/* reset the values */
				txt_Caption.setValue("");
				txt_Description.setValue("");
				txt_UniqueId.setValue("");
				cmb_PropertyGroup.setValue(null);
			}
		});
		this.btn_New.addClickListener(new Button.ClickListener() {
			/**
			 * Serial Version iD
			 */
			private static final long serialVersionUID = 8431665432682866374L;
			@Override
			public void buttonClick(ClickEvent event) {
				/* reset the values */
				txt_Caption.setValue("");
				txt_Description.setValue("");
				txt_UniqueId.setValue("");
				cmb_PropertyGroup.setValue(null);
			}
		});
		this.btn_Save.addClickListener(new Button.ClickListener() {
			/**
			 * Serial Version iD
			 */
			private static final long serialVersionUID = -1821112114237445574L;
			@Override
			public void buttonClick(ClickEvent event) {
				/* get the PropertyGroup out the ComboBox */
				PropertyGroupDTO prGroupDTO = null;
				MaterialComponentDTO materialComponentDTO = null;
				if(null != mapPropertyGroupCaption){
					if(null != cmb_PropertyGroup.getValue()){
						prGroupDTO = mapPropertyGroupCaption.get(cmb_PropertyGroup.getValue());
					}
				}
				if(txt_UniqueId.getValue() != ""){
					/* update the object */
					mapMaterialComponent.get(materialComponentUniqueID).setCaption(txt_Caption.getValue());
					mapMaterialComponent.get(materialComponentUniqueID).setDescription(txt_Description.getValue());
					mapMaterialComponent.get(materialComponentUniqueID).setPropertyGroupDTO(prGroupDTO);
					/* update the object in the database */
					try {
						iManagerMaterialComponent.update(mapMaterialComponent.get(materialComponentUniqueID));
						Notification.show(captions.getString("CAP.DESC.7"));
					} catch (ManagerException mXe) {
						Notification.show(mXe.getCaption());
					}
				}else{
					/* create PropertyDTO */
					materialComponentDTO = new MaterialComponentDTO();
					/* update the properties */
					materialComponentDTO.setCaption(txt_Caption.getValue());
					materialComponentDTO.setDescription(txt_Description.getValue());
					materialComponentDTO.setPropertyGroupDTO(prGroupDTO);
					/* persist the object in the database */
					try {
						iManagerMaterialComponent.persist(materialComponentDTO);
						Notification.show(captions.getString("CAP.DESC.8"));
						/* Set the field for the uniqueID */
						txt_UniqueId.setValue(materialComponentDTO.getUniqueId());
						mapMaterialComponent.put(materialComponentDTO.getUniqueId(), materialComponentDTO);
					} catch (ManagerException mXe) {
						Notification.show(mXe.getCaption());
					}
				}
				/* add the property to the TreeTable */
				for(Object obj : trt_JTrackInventoryView_Base.getItemIds()){
					if("3".equals(trt_JTrackInventoryView_Base.getItem(obj).getItemProperty(captions.getString("CAP.TRT.6")).getValue())){
						Object itemId = trt_JTrackInventoryView_Base.addItem(new Object[]{"      " + materialComponentDTO.getCaption(), materialComponentDTO.getUniqueId()},null);
						trt_JTrackInventoryView_Base.setParent(itemId, obj);
						setItemIcon(itemId, Icon.iconMaterialComponent, trt_JTrackInventoryView_Base);
					}
				}
			}
		});
		/* Table MaterialComponent Settings */
		this.tbl_MaterialComponentSettings = new Table();
		this.tbl_MaterialComponentSettings.setSizeFull();
		this.tbl_MaterialComponentSettings.addContainerProperty(captions.getString("CAP.TBL.1"), String.class, null);
		this.tbl_MaterialComponentSettings.addContainerProperty(captions.getString("CAP.TBL.2"), AbstractComponent.class, null);
		this.tbl_MaterialComponentSettings.setColumnHeaderMode(Table.COLUMN_HEADER_MODE_HIDDEN);
		/* set Values PropertyGroup */
		this.tbl_MaterialComponentSettings.addItem(new Object[] {captions.getString("CAP.LBL.2"),this.txt_Caption}, null);
		this.tbl_MaterialComponentSettings.addItem(new Object[] {captions.getString("CAP.LBL.3"),this.txt_Description}, null);
		this.tbl_MaterialComponentSettings.addItem(new Object[] {captions.getString("CAP.LBL.4"),this.cmb_PropertyGroup}, null);
		this.tbl_MaterialComponentSettings.addItem(new Object[] {captions.getString("CAP.LBL.1"),this.txt_UniqueId}, null);
		/* Table MaterialComponent Properties */
		this.tbl_MaterialComponentGroupProperty = new TreeTable();
		this.tbl_MaterialComponentGroupProperty.setSizeFull();
		this.tbl_MaterialComponentGroupProperty.addContainerProperty(captions.getString("CAP.TRT.1"), String.class, null);
		this.tbl_MaterialComponentGroupProperty.addContainerProperty(captions.getString("CAP.TRT.2"), String.class, null);
		this.tbl_MaterialComponentGroupProperty.setSelectable(true);
		/* TabSheet */
		this.tbs_MaterialComponent = new TabSheet();
		this.tbs_MaterialComponent.setImmediate(true);
		this.tbs_MaterialComponent.addTab(this.tbl_MaterialComponentSettings);
		this.tbs_MaterialComponent.getTab(this.tbl_MaterialComponentSettings).setIcon(Icon.iconMaterialComponent);
		this.tbs_MaterialComponent.getTab(this.tbl_MaterialComponentSettings).setCaption(captions.getString("CAP.TBS.4"));
		this.tbs_MaterialComponent.addTab(this.tbl_MaterialComponentGroupProperty);
		this.tbs_MaterialComponent.getTab(this.tbl_MaterialComponentGroupProperty).setIcon(Icon.iconProperty);
		this.tbs_MaterialComponent.getTab(this.tbl_MaterialComponentGroupProperty).setCaption(captions.getString("CAP.TBS.2"));
		/* GridLayout */
		this.grd_MaterialComponent = new GridLayout(4,5);
		this.grd_MaterialComponent.addComponent(this.tbs_MaterialComponent,0,0,3,2);
		this.grd_MaterialComponent.addComponent(this.acr_PropertyGroupSettingsDescription,0,3,3,3);
		this.grd_MaterialComponent.addComponent(this.btn_Delete,0,4,0,4);
		this.grd_MaterialComponent.addComponent(this.btn_Copy,1,4,1,4);
		this.grd_MaterialComponent.addComponent(this.btn_New,2,4,2,4);
		this.grd_MaterialComponent.addComponent(this.btn_Save,3,4,3,4);
		this.grd_MaterialComponent.setComponentAlignment(this.acr_PropertyGroupSettingsDescription, Alignment.BOTTOM_LEFT);
		this.grd_MaterialComponent.setComponentAlignment(this.btn_Delete, Alignment.BOTTOM_CENTER);
		this.grd_MaterialComponent.setComponentAlignment(this.btn_Copy, Alignment.BOTTOM_CENTER);
		this.grd_MaterialComponent.setComponentAlignment(this.btn_New, Alignment.BOTTOM_CENTER);
		this.grd_MaterialComponent.setComponentAlignment(this.btn_Save, Alignment.BOTTOM_CENTER);
		this.grd_MaterialComponent.setMargin(true);
		this.grd_MaterialComponent.setSizeFull();
		this.setContent(this.grd_MaterialComponent);
		this.setSizeFull();
	}
	/*
	 * Method will set the value of the different properties when the Property give in the Constructor isn't null
	 */
	private void initFields(){
		if(null != this.mapPropertyGroup){
			/* set the values for the ComboBox */
			for(PropertyGroupDTO dto : this.mapPropertyGroup.values()){
				this.cmb_PropertyGroup.addItem(dto.getCaption());
				this.mapPropertyGroupCaption.put(dto.getCaption(), dto);
			}
			/* set the MaterialComponent values */
			if(null != materialComponentUniqueID){
				if(null != this.mapMaterialComponent.get(materialComponentUniqueID)){
					this.txt_Caption.setValue(this.mapMaterialComponent.get(materialComponentUniqueID).getCaption());
					this.txt_Description.setValue(this.mapMaterialComponent.get(materialComponentUniqueID).getDescription());
					this.txt_UniqueId.setValue(this.mapMaterialComponent.get(materialComponentUniqueID).getUniqueId());
					if(null != this.mapMaterialComponent.get(materialComponentUniqueID).getPropertyGroupDTO()){
						this.cmb_PropertyGroup.select(this.mapMaterialComponent.get(materialComponentUniqueID).getPropertyGroupDTO().getCaption());
					}else{
						this.cmb_PropertyGroup.setValue(null);
					}
				}
			}
			/* set the MaterialComponent PropertyGroup Properties */
			if(null != materialComponentUniqueID){
				if(null != this.mapMaterialComponent.get(materialComponentUniqueID)){
					if(null != this.mapMaterialComponent.get(materialComponentUniqueID).getPropertyGroupDTO()){
						PropertyGroupDTO dto = mapPropertyGroup.get(this.mapMaterialComponent.get(materialComponentUniqueID).getPropertyGroupDTO().getUniqueId());
						if(null != dto){
							Object parent = this.tbl_MaterialComponentGroupProperty.addItem(new Object[]{dto.getCaption(), dto.getDescription()}, null);
							this.setItemIcon(parent,Icon.iconPropertyGroup,this.tbl_MaterialComponentGroupProperty);
							for(PropertyDTO propertyDto : mapProperty.values()){
								if(null != propertyDto.getPropertyGroupDTO()){
									if(dto.getUniqueId().equals(propertyDto.getPropertyGroupDTO().getUniqueId())){
										Object child = this.tbl_MaterialComponentGroupProperty.addItem(new Object[]{propertyDto.getCaption(), propertyDto.getDescription()}, null);
										this.tbl_MaterialComponentGroupProperty.setParent(child,parent);
										this.setItemIcon(child,Icon.iconProperty,this.tbl_MaterialComponentGroupProperty);
									}
								}
							}
						}
					}
				}
			}
		}
	}
	/*
	 * Method will set the icon of a TreeTable
	 */
	private void setItemIcon(Object item, Resource icon, TreeTable table){
		/* check the values */
		if(null != item){
			if(null != table){
				if(null != icon){
					/* Set the Icon */
					table.setItemIcon(item, icon);
				}
			}
		}
	}
	
}
