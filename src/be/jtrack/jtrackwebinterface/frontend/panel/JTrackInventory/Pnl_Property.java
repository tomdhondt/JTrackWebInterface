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
import com.vaadin.ui.TreeTable;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Table;
import com.vaadin.ui.TextField;

import be.jtrack.jtrackwebinterface.frontend.panel.global.L18NPanel;
import be.jtrack.jtrackwebinterface.listner.Lsn_FocusListner_TextField_HelpText;
import be.jtrack.jtrackwebinterface.util.Icon;
import be.jtrackinventory.exception.manager.ManagerException;
import be.jtrackinventory.service.dto.PropertyDTO;
import be.jtrackinventory.service.dto.PropertyGroupDTO;
import be.jtrackinventory.service.dto.PropertyTypeDTO;
import be.jtrackinventory.service.manager.IManager;

@SuppressWarnings("unchecked")
public class Pnl_Property extends L18NPanel{
	/*
	 * instance members
	 */
	ApplicationContext context = new ClassPathXmlApplicationContext(new String[] {"applicationContent.xml"});
	IManager<PropertyDTO> iManagerProperty = (IManager<PropertyDTO>) context.getBean("iManagerProperty");
	/* Property */
	private PropertyDTO propertyDTO;
	/* size */
	private final String abstractComponentHeight = "25px";
	private final String abstractButtonWidht = "120px";
	private final String abstractComponentWidht = "340px";
	/* Accordion */
	private Accordion acr_PropertySettingsDescription;
	/* TabSheet */
	private TabSheet tbs_Property;
	/* GridLayout */
	private GridLayout grd_Property_Property;
	/* Table */
	private Table tbl_PropertySettings;
	/* Label */
	private Label lbl_PropertySettingsDescription;
	/* Button */
	private Button btn_Save;
	private Button btn_New;
	private Button btn_Copy;
	private Button btn_Delete;
	/* TextField */
	private TextField txt_UniqueId;
	private TextField txt_Caption;
	private TextField txt_Description;
	/* ComboBox */
	private ComboBox cmb_PropertyType;
	private ComboBox cmb_PropertyGroup;
	/* Map */
	private Map<String, PropertyGroupDTO> mapPropertyGroup;
	private Map<String, PropertyGroupDTO> mapPropertyGroupCaption;
	private Map<String, PropertyTypeDTO> mapPropertyType;
	private Map<String, PropertyTypeDTO> mapPropertyTypeCaption;
	private Map<String, PropertyDTO> mapProperty;
	/* TreeTable */
	private TreeTable trt_JTrackInventoryView_Base;
	/* String */
	private String propertyUniqueID;
	/**
	 * Serial Version ID
	 */
	private static final long serialVersionUID = 6538522424927871721L;
	/**
	 * Default constructor
	 */
	private Pnl_Property(){
		this.init();
	}
	/**
	 * 
	 */
	public Pnl_Property(TreeTable jtrackInventoryBase, Map<String, PropertyDTO> mapProperty, Map<String, PropertyGroupDTO> mapPropertyGroup, Map<String, PropertyTypeDTO> mapPropertyType, String UniqueID){
		this();
		this.trt_JTrackInventoryView_Base = jtrackInventoryBase;
		this.propertyUniqueID = UniqueID;
		this.mapProperty = mapProperty;
		this.mapPropertyGroup = mapPropertyGroup;
		this.mapPropertyType = mapPropertyType;
		this.initFields();
	}
	@SuppressWarnings("deprecation")
	private void init(){
		/* Label */
		this.lbl_PropertySettingsDescription = new Label(captions.getString("CAP.LBL.6"));
		/* ComboBox */
		this.cmb_PropertyType = new ComboBox();
		this.cmb_PropertyType.setHeight(this.abstractComponentHeight);
		this.cmb_PropertyType.setWidth(this.abstractComponentWidht);
		this.cmb_PropertyType.setDescription(captions.getString("CAP.DESC.4"));
		this.cmb_PropertyType.addFocusListener(new Lsn_FocusListner_TextField_HelpText(this.lbl_PropertySettingsDescription, this.cmb_PropertyType));
		this.cmb_PropertyType.setFilteringMode(FilteringMode.CONTAINS);
		this.cmb_PropertyGroup = new ComboBox();
		this.cmb_PropertyGroup.setHeight(this.abstractComponentHeight);
		this.cmb_PropertyGroup.setWidth(this.abstractComponentWidht);
		this.cmb_PropertyGroup.setDescription(captions.getString("CAP.DESC.3"));
		this.cmb_PropertyGroup.addFocusListener(new Lsn_FocusListner_TextField_HelpText(this.lbl_PropertySettingsDescription, this.cmb_PropertyGroup));
		this.cmb_PropertyGroup.setFilteringMode(FilteringMode.CONTAINS);
		/* TextField */
		this.txt_UniqueId = new TextField();
		this.txt_UniqueId.setEnabled(false);
		this.txt_UniqueId.setHeight(this.abstractComponentHeight);
		this.txt_UniqueId.setDescription(captions.getString("CAP.DESC.5"));
		this.txt_UniqueId.setWidth(this.abstractComponentWidht);
		this.txt_Caption = new TextField();
		this.txt_Caption.setHeight(this.abstractComponentHeight);
		this.txt_Caption.setDescription(captions.getString("CAP.DESC.1"));
		this.txt_Caption.addFocusListener(new Lsn_FocusListner_TextField_HelpText(this.lbl_PropertySettingsDescription, this.txt_Caption));
		this.txt_Caption.setWidth(this.abstractComponentWidht);
		this.txt_Description = new TextField();
		this.txt_Description.setHeight(this.abstractComponentHeight);
		this.txt_Description.setDescription(captions.getString("CAP.DESC.2"));
		this.txt_Description.addFocusListener(new Lsn_FocusListner_TextField_HelpText(this.lbl_PropertySettingsDescription, this.txt_Description));
		this.txt_Description.setWidth(this.abstractComponentWidht);
		/* Accordion */ 
		this.acr_PropertySettingsDescription = new Accordion();
		this.acr_PropertySettingsDescription.addTab(this.lbl_PropertySettingsDescription,captions.getString("CAP.TAB.1"),Icon.iconHelp);
		/* TabSheet */
		this.tbs_Property = new TabSheet();
		this.tbs_Property.setImmediate(true);
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
		this.btn_Copy.addClickListener(new Button.ClickListener() {
			/**
			 * Serial Version iD
			 */
			private static final long serialVersionUID = 8431665432682866374L;
			@Override
			public void buttonClick(ClickEvent event) {
				StringBuilder output = new StringBuilder();
				if(null != mapPropertyGroup){
					for(PropertyGroupDTO dto : mapPropertyGroup.values()){
						output.append(dto.toString());
					}					
				}else{
					output.append("null");
				}
				Notification.show(output.toString());
			}
		});
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
						iManagerProperty.remove(propertyDTO.getUniqueId());
						Notification.show(captions.getString("CAP.DESC.6") + " " + propertyDTO.getCaption());
					} catch (ManagerException mXe) {
						Notification.show(mXe.getCaption());
					}
				}
				/* delete the property to the TreeTable */
				for(Object obj : trt_JTrackInventoryView_Base.getItemIds()){
					if(propertyDTO.getUniqueId().equals(trt_JTrackInventoryView_Base.getItem(obj).getItemProperty(captions.getString("CAP.TRT.6")).getValue())){
						trt_JTrackInventoryView_Base.removeItem(obj);
					}
				}
				/* reset the values */
				txt_Caption.setValue("");
				txt_Description.setValue("");
				txt_UniqueId.setValue("");
				cmb_PropertyGroup.setValue(null);
				cmb_PropertyType.setValue(null);
				/* reset the active Property */
				propertyDTO = null;
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
				cmb_PropertyType.setValue(null);
				/* reset the active Property */
				propertyDTO = null;
			}
		});
		this.btn_Save.addClickListener(new Button.ClickListener() {
			/**
			 * Serial Version iD
			 */
			private static final long serialVersionUID = -7899291233506208783L;
			@Override
			public void buttonClick(ClickEvent event) {
				/* get the PropertyGroup */
				PropertyGroupDTO prGroupDTO = null;
				if(null != mapPropertyGroup){
					if(null != cmb_PropertyGroup.getValue()){
						prGroupDTO = mapPropertyGroupCaption.get(cmb_PropertyGroup.getValue());
					}
				}
				/* get the PropertyType */
				PropertyTypeDTO prTypeDTO = null;
				if(null != mapPropertyType){
					if(null != cmb_PropertyType.getValue()){
						prTypeDTO = mapPropertyTypeCaption.get(cmb_PropertyType.getValue());
					}
				}
				if(txt_UniqueId.getValue() != ""){
					/* update the object */
					propertyDTO.setCaption(txt_Caption.getValue());
					propertyDTO.setDescription(txt_Description.getValue());
					propertyDTO.setPropertyGroupDTO(prGroupDTO);
					propertyDTO.setPropertyTypeDTO(prTypeDTO);
					/* update the object in the database */
					try {
						iManagerProperty.update(propertyDTO);
						Notification.show(captions.getString("CAP.DESC.7"));
					} catch (ManagerException mXe) {
						Notification.show(mXe.getCaption());
					}
				}else{
					/* create PropertyDTO */
					propertyDTO = new PropertyDTO();
					/* update the properties */
					propertyDTO.setCaption(txt_Caption.getValue());
					propertyDTO.setDescription(txt_Description.getValue());
					propertyDTO.setPropertyGroupDTO(prGroupDTO);
					propertyDTO.setPropertyTypeDTO(prTypeDTO);
					/* persist the object in the database */
					try {
						if(null == mapProperty){
							mapProperty = new TreeMap<String, PropertyDTO>();
						}
						mapProperty.put(propertyDTO.getUniqueId(), propertyDTO);
						iManagerProperty.persist(propertyDTO);
						txt_UniqueId.setValue(propertyDTO.getUniqueId());
						Notification.show(captions.getString("CAP.DESC.8"));
					} catch (ManagerException mXe) {
						Notification.show(mXe.getCaption());
					}
					/* add the property to the TreeTable */
					for(Object obj : trt_JTrackInventoryView_Base.getItemIds()){
						if("1".equals(trt_JTrackInventoryView_Base.getItem(obj).getItemProperty(captions.getString("CAP.TRT.6")).getValue())){
							Object itemId = trt_JTrackInventoryView_Base.addItem(new Object[]{"      " + propertyDTO.getCaption(), propertyDTO.getUniqueId()},null);
							trt_JTrackInventoryView_Base.setParent(itemId, obj);
							setItemIcon(itemId, Icon.iconProperty, trt_JTrackInventoryView_Base);
						}
					}
				}
			}
		});
		/* Table */
		this.tbl_PropertySettings = new Table();
		this.tbl_PropertySettings.setSizeFull();
		this.tbl_PropertySettings.addContainerProperty(captions.getString("CAP.TBL.1"), String.class, null);
		this.tbl_PropertySettings.addContainerProperty(captions.getString("CAP.TBL.2"), AbstractComponent.class, null);
		this.tbl_PropertySettings.setColumnHeaderMode(Table.COLUMN_HEADER_MODE_HIDDEN);
		/* set the values */
		this.tbl_PropertySettings.addItem(new Object[] {captions.getString("CAP.LBL.2"),this.txt_Caption}, null);
		this.tbl_PropertySettings.addItem(new Object[] {captions.getString("CAP.LBL.3"),this.txt_Description}, null);
		this.tbl_PropertySettings.addItem(new Object[] {captions.getString("CAP.LBL.4"),this.cmb_PropertyGroup}, null);
		this.tbl_PropertySettings.addItem(new Object[] {captions.getString("CAP.LBL.5"),this.cmb_PropertyType}, null);
		this.tbl_PropertySettings.addItem(new Object[] {captions.getString("CAP.LBL.1"),this.txt_UniqueId}, null);
		/* TabSheet */
		this.tbs_Property.addTab(this.tbl_PropertySettings);
		this.tbs_Property.getTab(this.tbl_PropertySettings).setIcon(Icon.iconProperty);
		this.tbs_Property.getTab(this.tbl_PropertySettings).setCaption(captions.getString("CAP.TBS.2"));
		/* Global layout */
		this.grd_Property_Property = new GridLayout(4,5);
		this.grd_Property_Property.addComponent(this.tbs_Property,0,0,3,2);
		this.grd_Property_Property.addComponent(this.acr_PropertySettingsDescription,0,3,3,3);
		this.grd_Property_Property.addComponent(this.btn_Delete,0,4,0,4);
		this.grd_Property_Property.addComponent(this.btn_Copy,1,4,1,4);
		this.grd_Property_Property.addComponent(this.btn_New,2,4,2,4);
		this.grd_Property_Property.addComponent(this.btn_Save,3,4,3,4);
		this.grd_Property_Property.setComponentAlignment(this.acr_PropertySettingsDescription, Alignment.BOTTOM_LEFT);
		this.grd_Property_Property.setComponentAlignment(this.btn_Delete, Alignment.BOTTOM_CENTER);
		this.grd_Property_Property.setComponentAlignment(this.btn_Copy, Alignment.BOTTOM_CENTER);
		this.grd_Property_Property.setComponentAlignment(this.btn_New, Alignment.BOTTOM_CENTER);
		this.grd_Property_Property.setComponentAlignment(this.btn_Save, Alignment.BOTTOM_CENTER);
		this.grd_Property_Property.setMargin(true);
		this.grd_Property_Property.setSizeFull();
		this.setContent(this.grd_Property_Property);
		this.setSizeFull();
	}
	/*
	 * Method will set the value of the different properties when the Property give in the Constructor isn't null
	 */
	private void initFields(){
		/* set the content for the cmb_PropertyGroup */
		if(null != this.mapPropertyGroup){
			this.mapPropertyGroupCaption = new TreeMap<String, PropertyGroupDTO>();
			for(PropertyGroupDTO dto : this.mapPropertyGroup.values()){
				this.cmb_PropertyGroup.addItem(dto.getCaption());
				this.mapPropertyGroupCaption.put(dto.getCaption(), dto);
			}
		}
		/* set the content for the cmb_PropertyType */
		if(null != this.mapPropertyType){
			this.mapPropertyTypeCaption = new TreeMap<String, PropertyTypeDTO>();
			for(PropertyTypeDTO dto : this.mapPropertyType.values()){
				this.cmb_PropertyType.addItem(dto.getCaption());
				this.mapPropertyTypeCaption.put(dto.getCaption(), dto);
			}
		}
		if(null != this.mapProperty){
			if(null != this.propertyUniqueID){
				this.propertyDTO = this.mapProperty.get(this.propertyUniqueID);
				/* check Property */
				if(null != this.propertyDTO){
					this.txt_Caption.setValue(propertyDTO.getCaption());
					this.txt_Description.setValue(propertyDTO.getDescription());
					this.txt_UniqueId.setValue(propertyDTO.getUniqueId());
					if(null != propertyDTO.getPropertyGroupDTO()){
						this.cmb_PropertyGroup.select(propertyDTO.getPropertyGroupDTO().getCaption());
					}else{
						this.cmb_PropertyGroup.setValue(null);
					}
					if(null != propertyDTO.getPropertyTypeDTO()){
						this.cmb_PropertyType.select(propertyDTO.getPropertyTypeDTO().getCaption());
					}else{
						this.cmb_PropertyType.setValue(null);
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
