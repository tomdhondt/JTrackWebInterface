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
import be.jtrackinventory.service.dto.PropertyDTO;
import be.jtrackinventory.service.dto.PropertyGroupDTO;
import be.jtrackinventory.service.manager.IManager;

@SuppressWarnings("unchecked")
public class Pnl_PropertyGroup extends L18NPanel{
	/**
	 * Serial Version id
	 */
	private static final long serialVersionUID = 6915425646606289680L;
	/*
	 * instance members
	 */
	ApplicationContext context = new ClassPathXmlApplicationContext(new String[] {"applicationContent.xml"});
	IManager<PropertyGroupDTO> iManagerPropertyGroup = (IManager<PropertyGroupDTO>) context.getBean("iManagerPropertyGroup");
	/* data */
	private Map<String, PropertyGroupDTO> mapPropertyGroup;
	private Map<String, PropertyDTO> mapProperty;
	private Map<String, Object> mapSubPropertyGroupItem;
	private Map<String, Object> mapPropertyGroupProperties;
	/* GridLayout */
	private GridLayout grd_PropertyGroup;
	/* size */
	private final String abstractComponentHeight = "25px";
	private final String abstractButtonWidht = "120px";
	private final String abstractComponentWidht = "340px";
	/* Label */
	private Label lbl_PropertySettingsDescription;
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
	/* Table */
	private Table tbl_PropertyGroupSettings;
	private Table tbl_PropertyGroupOverview;
	/* TreeTable */
	private TreeTable trt_PropertyGroup_Properties;
	private TreeTable trt_JTrackInventoryView_Base;
	/* TabSheet */
	private TabSheet tbs_PropertyGroup;
	/* ComboBox */
	private ComboBox cmb_PropertyGroup;
	/* String */
	private String materialComponentUniqueID;
	/**
	 * Default constructor for the Class
	 */
	private Pnl_PropertyGroup(){
		this.init();
	}
	/**
	 * Constructor for the Class
	 * @param propertyGroup as PropertyGroupDTO
	 */
	public Pnl_PropertyGroup(TreeTable jtrackInventoryBase, Map<String, PropertyGroupDTO> mapPropertyGroup, Map<String, PropertyDTO> mapProperty, String materialComponentUniqueID){
		this();
		this.trt_JTrackInventoryView_Base = jtrackInventoryBase;
		this.mapPropertyGroup = mapPropertyGroup;
		this.mapProperty = mapProperty;
		this.materialComponentUniqueID = materialComponentUniqueID;
		this.initFields();
	}
	/*
	 * Method will initialize the Panel
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
				if(null != mapPropertyGroup){
					Notification.show("not null");
				}else{
					Notification.show("null");
				}
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
						iManagerPropertyGroup.remove(materialComponentUniqueID);
						Notification.show(captions.getString("CAP.DESC.6") + " " + mapPropertyGroup.get(materialComponentUniqueID).getCaption());
					} catch (ManagerException mXe) {
						Notification.show(mXe.getCaption());
					}
				}
				/* delete the property to the TreeTable */
				for(Object obj : trt_JTrackInventoryView_Base.getItemIds()){
					if(mapPropertyGroup.get(materialComponentUniqueID).getUniqueId().equals(trt_JTrackInventoryView_Base.getItem(obj).getItemProperty(captions.getString("CAP.TRT.6")).getValue())){
						trt_JTrackInventoryView_Base.removeItem(obj);
					}
				}
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
				/* add the PropertyGroupDTO to the ComboBox */
				cmb_PropertyGroup.removeAllItems();
				for(PropertyGroupDTO dto : mapPropertyGroup.values()){
					cmb_PropertyGroup.addItem(dto.getCaption());
					mapPropertyGroup.put(dto.getCaption(), dto);
				}
				cmb_PropertyGroup.setValue(null);
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
				try{
					for(PropertyGroupDTO dto : mapPropertyGroup.values()){
						if(dto.getCaption() == (String)cmb_PropertyGroup.getValue()){
							prGroupDTO = dto;
						}
					}
				}catch(Exception e){
				}
				if(txt_UniqueId.getValue() != ""){
					/* update the object */
					mapPropertyGroup.get(materialComponentUniqueID).setCaption(txt_Caption.getValue());
					mapPropertyGroup.get(materialComponentUniqueID).setDescription(txt_Description.getValue());
					/* Get the selected PropertyGroup */
					if(null != prGroupDTO){
						mapPropertyGroup.get(materialComponentUniqueID).setParentPropertyGroupCaption(prGroupDTO.getCaption());
						mapPropertyGroup.get(materialComponentUniqueID).setParentPropertyGroupDescription(prGroupDTO.getDescription());
						mapPropertyGroup.get(materialComponentUniqueID).setParentPropertyGroupIsActive(prGroupDTO.isActive());
						mapPropertyGroup.get(materialComponentUniqueID).setParentPropertyGroupUniqueID(prGroupDTO.getUniqueId());
					}
					/* update the object in the database */
					try {
						iManagerPropertyGroup.update(mapPropertyGroup.get(materialComponentUniqueID));
						Notification.show(captions.getString("CAP.DESC.7"));
					} catch (ManagerException mXe) {
						Notification.show(mXe.getCaption());
					}
				}else{
					/* create PropertyDTO */
					PropertyGroupDTO propertyGroupDTO = new PropertyGroupDTO();
					/* update the properties */
					propertyGroupDTO.setCaption(txt_Caption.getValue());
					propertyGroupDTO.setDescription(txt_Description.getValue());
					/* Get the selected PropertyGroup */
					if(null != prGroupDTO){
						propertyGroupDTO.setParentPropertyGroupCaption(prGroupDTO.getCaption());
						propertyGroupDTO.setParentPropertyGroupDescription(prGroupDTO.getDescription());
						propertyGroupDTO.setParentPropertyGroupIsActive(prGroupDTO.isActive());
						propertyGroupDTO.setParentPropertyGroupUniqueID(prGroupDTO.getUniqueId());
					}
					/* persist the object in the database */
					try {
						mapPropertyGroup.put(propertyGroupDTO.getUniqueId(), propertyGroupDTO);
						iManagerPropertyGroup.persist(propertyGroupDTO);
						Notification.show(captions.getString("CAP.DESC.8"));
						/* Set the field for the uniqueID */
						txt_UniqueId.setValue(propertyGroupDTO.getUniqueId());
					} catch (ManagerException mXe) {
						Notification.show(mXe.getCaption());
					}
					/* add the property to the TreeTable */
					for(Object obj : trt_JTrackInventoryView_Base.getItemIds()){
						if("2".equals(trt_JTrackInventoryView_Base.getItem(obj).getItemProperty(captions.getString("CAP.TRT.6")).getValue())){
							Object itemId = trt_JTrackInventoryView_Base.addItem(new Object[]{"      " + propertyGroupDTO.getCaption(), propertyGroupDTO.getUniqueId()},null);
							trt_JTrackInventoryView_Base.setParent(itemId, obj);
							setItemIcon(itemId, Icon.iconPropertyGroup, trt_JTrackInventoryView_Base);
						}
					}
				}
			}
		});
		/* ComboBox */
		this.cmb_PropertyGroup = new ComboBox();
		this.cmb_PropertyGroup.setHeight(this.abstractComponentHeight);
		this.cmb_PropertyGroup.setWidth(this.abstractComponentWidht);
		this.cmb_PropertyGroup.setDescription(captions.getString("CAP.DESC.3"));
		this.cmb_PropertyGroup.addFocusListener(new Lsn_FocusListner_TextField_HelpText(this.lbl_PropertySettingsDescription, this.cmb_PropertyGroup));
		this.cmb_PropertyGroup.setFilteringMode(FilteringMode.CONTAINS);
		/* TreeTable */
		this.trt_PropertyGroup_Properties = new TreeTable();
		this.trt_PropertyGroup_Properties.setSizeFull();
		this.trt_PropertyGroup_Properties.addContainerProperty(captions.getString("CAP.TRT.1"), String.class, null);
		this.trt_PropertyGroup_Properties.addContainerProperty(captions.getString("CAP.TRT.2"), String.class, null);
		this.trt_PropertyGroup_Properties.setSelectable(true);
		/* Table */
		this.tbl_PropertyGroupSettings = new Table();
		this.tbl_PropertyGroupSettings.setSizeFull();
		this.tbl_PropertyGroupSettings.addContainerProperty(captions.getString("CAP.TBL.1"), String.class, null);
		this.tbl_PropertyGroupSettings.addContainerProperty(captions.getString("CAP.TBL.2"), AbstractComponent.class, null);
		this.tbl_PropertyGroupSettings.setColumnHeaderMode(Table.COLUMN_HEADER_MODE_HIDDEN);
		/* Table Set PropertyGroup objects */
		this.tbl_PropertyGroupSettings.addItem(new Object[] {captions.getString("CAP.LBL.2"),this.txt_Caption}, null);
		this.tbl_PropertyGroupSettings.addItem(new Object[] {captions.getString("CAP.LBL.3"),this.txt_Description}, null);
		this.tbl_PropertyGroupSettings.addItem(new Object[] {captions.getString("CAP.LBL.4"),this.cmb_PropertyGroup}, null);
		this.tbl_PropertyGroupSettings.addItem(new Object[] {captions.getString("CAP.LBL.1"),this.txt_UniqueId}, null);
		/* Table SubPropertyGroups */
		this.tbl_PropertyGroupOverview = new Table();
		this.tbl_PropertyGroupOverview.addContainerProperty(captions.getString("CAP.TBL.3"), String.class, null);
		this.tbl_PropertyGroupOverview.addContainerProperty(captions.getString("CAP.TBL.4"), String.class, null);
		this.tbl_PropertyGroupOverview.addContainerProperty(captions.getString("CAP.TBL.5"), String.class, null);
		this.tbl_PropertyGroupOverview.addContainerProperty(captions.getString("CAP.TBL.6"), AbstractComponent.class, null);
		this.tbl_PropertyGroupOverview.addContainerProperty(captions.getString("CAP.TBL.7"), AbstractComponent.class, null);
		this.tbl_PropertyGroupOverview.setSizeFull();
		this.tbl_PropertyGroupOverview.setSelectable(true);
		/* TabSheet */
		this.tbs_PropertyGroup = new TabSheet();
		this.tbs_PropertyGroup.setImmediate(true);
		this.tbs_PropertyGroup.addTab(this.tbl_PropertyGroupSettings);
		this.tbs_PropertyGroup.getTab(this.tbl_PropertyGroupSettings).setIcon(Icon.iconPropertyGroup);
		this.tbs_PropertyGroup.getTab(this.tbl_PropertyGroupSettings).setCaption(captions.getString("CAP.TBS.1"));
		this.tbs_PropertyGroup.addTab(this.tbl_PropertyGroupOverview);
		this.tbs_PropertyGroup.getTab(this.tbl_PropertyGroupOverview).setIcon(Icon.iconList);
		this.tbs_PropertyGroup.getTab(this.tbl_PropertyGroupOverview).setCaption(captions.getString("CAP.TBS.5"));
		this.tbs_PropertyGroup.addTab(this.trt_PropertyGroup_Properties);
		this.tbs_PropertyGroup.getTab(this.trt_PropertyGroup_Properties).setIcon(Icon.iconProperty);
		this.tbs_PropertyGroup.getTab(this.trt_PropertyGroup_Properties).setCaption(captions.getString("CAP.TBS.2"));
		/* GridLayout */
		this.grd_PropertyGroup = new GridLayout(4,5);
		this.grd_PropertyGroup.addComponent(this.tbs_PropertyGroup,0,0,3,2);
		this.grd_PropertyGroup.addComponent(this.acr_PropertyGroupSettingsDescription,0,3,3,3);
		this.grd_PropertyGroup.addComponent(this.btn_Delete,0,4,0,4);
		this.grd_PropertyGroup.addComponent(this.btn_Copy,1,4,1,4);
		this.grd_PropertyGroup.addComponent(this.btn_New,2,4,2,4);
		this.grd_PropertyGroup.addComponent(this.btn_Save,3,4,3,4);
		this.grd_PropertyGroup.setComponentAlignment(this.acr_PropertyGroupSettingsDescription, Alignment.BOTTOM_LEFT);
		this.grd_PropertyGroup.setComponentAlignment(this.btn_Delete, Alignment.BOTTOM_CENTER);
		this.grd_PropertyGroup.setComponentAlignment(this.btn_Copy, Alignment.BOTTOM_CENTER);
		this.grd_PropertyGroup.setComponentAlignment(this.btn_New, Alignment.BOTTOM_CENTER);
		this.grd_PropertyGroup.setComponentAlignment(this.btn_Save, Alignment.BOTTOM_CENTER);
		this.grd_PropertyGroup.setMargin(true);
		this.grd_PropertyGroup.setSizeFull();
		this.setContent(this.grd_PropertyGroup);
		this.setSizeFull();
	}
	/*
	 * Method will set the value of the different properties when the Property give in the Constructor isn't null
	 */
	private void initFields(){
		if(null != this.mapPropertyGroup){
			if(null != materialComponentUniqueID){
				this.txt_Caption.setValue(this.mapPropertyGroup.get(materialComponentUniqueID).getCaption());
				this.txt_Description.setValue(this.mapPropertyGroup.get(materialComponentUniqueID).getDescription());
				this.txt_UniqueId.setValue(this.mapPropertyGroup.get(materialComponentUniqueID).getUniqueId());
				/* set the values for the ComboBox */
				if(null != mapPropertyGroup){
					for(PropertyGroupDTO dto : mapPropertyGroup.values()){
						this.cmb_PropertyGroup.addItem(dto.getCaption());
					}
				}
				/* set the selected value */
				if(null != this.mapPropertyGroup.get(materialComponentUniqueID).getParentPropertyGroupCaption()){
					this.cmb_PropertyGroup.select(this.mapPropertyGroup.get(materialComponentUniqueID).getParentPropertyGroupCaption());
				}else{
					this.cmb_PropertyGroup.setValue(null);
				}
				/* TreeTable values */
				this.setPropertyGroupProperties();
			}
		}
	}
	/*
	 * Method will create the table PropertyGroup Properties
	 */
	private void setPropertyGroupProperties(){
		if(null != this.mapPropertyGroup.get(materialComponentUniqueID)){
			this.setSubPropertyGroupProperties(mapPropertyGroup.get(materialComponentUniqueID),null);
			
		}
	}
	/*
	 * Method will set the data on the table and treetable
	 * @param propertyGroup as PropertyGroup
	 * @param parent as Object
	 */
	private void setSubPropertyGroupProperties(PropertyGroupDTO propertyGroup, Object parent){
		if(propertyGroup.isActive() == true){
			/* add the parent to the TreeTable */
			Object globalParent = this.trt_PropertyGroup_Properties.addItem(new Object[]{propertyGroup.getCaption(),propertyGroup.getDescription()},null);
			this.setItemIcon(globalParent, Icon.iconPropertyGroup, this.trt_PropertyGroup_Properties);
			if(null == mapPropertyGroupProperties){
				mapPropertyGroupProperties = new TreeMap<String, Object>();
			}
			mapPropertyGroupProperties.put(propertyGroup.getUniqueId(), globalParent);
			Object object = null;
			/* add the SubPropertyGroups to tbl_PropertyGroupOverview */
			if(materialComponentUniqueID != propertyGroup.getUniqueId()){
				/* add the object to the subPorpertyGroups */
				Button btn_Delete = new Button();
				Button btn_Change = new Button();
				btn_Delete.setIcon(Icon.iconDelete);
				btn_Change.setIcon(Icon.iconChange);
				btn_Delete.setDescription(captions.getString("CAP.BTN.4"));
				btn_Change.setDescription(captions.getString("CAP.BTN.5"));
				btn_Delete.setId(propertyGroup.getUniqueId());
				btn_Change.setId(propertyGroup.getUniqueId());
				/* action listener button */
				btn_Change.addClickListener(new Button.ClickListener() {
					/**
					 * Serial Version iD
					 */
					private static final long serialVersionUID = 8431665432682866374L;
					@Override
					public void buttonClick(ClickEvent event) {
						Notification.show(event.getComponent().getId());
					}
				});
				btn_Delete.addClickListener(new Button.ClickListener() {
					/**
					 * Serial Version iD
					 */
					private static final long serialVersionUID = 8431665432682866374L;
					@Override
					public void buttonClick(ClickEvent event) {
						/* remove the reference in the PropertyGroup */
						mapPropertyGroup.get(event.getComponent().getId()).setParentPropertyGroupUniqueID(null);
						/* Update the object in the database */
						try {
							iManagerPropertyGroup.update(mapPropertyGroup.get(event.getComponent().getId()));
							tbl_PropertyGroupOverview.removeItem(mapSubPropertyGroupItem.get(event.getComponent().getId()));
							trt_PropertyGroup_Properties.removeItem(mapPropertyGroupProperties.get(event.getComponent().getId()));
							Notification.show(captions.getString("CAP.DESC.7"));
						} catch (ManagerException mXe) {
							Notification.show(mXe.getCaption());
						}
					}
				});
				/* add the object to the table  */
				Object item = this.tbl_PropertyGroupOverview.addItem(new Object[]{
						Integer.toString(this.tbl_PropertyGroupOverview.size()+1),
						propertyGroup.getCaption(),
						propertyGroup.getDescription(),
						btn_Delete, 
						btn_Change},null);
				/* init the TreeMap */
				if(null == mapSubPropertyGroupItem){
					mapSubPropertyGroupItem = new TreeMap<String, Object>();
				}
				/* add the item to the set objects */
				mapSubPropertyGroupItem.put(propertyGroup.getUniqueId(), item);
			}
			/* Own properties */
			for(PropertyDTO dto : mapProperty.values()){
				if(propertyGroup.getUniqueId().equals(dto.getPropertyGroupDTO().getUniqueId())){
					object = this.trt_PropertyGroup_Properties.addItem(new Object[]{dto.getCaption(),dto.getDescription()},null);
					this.setItemIcon(object, Icon.iconProperty, this.trt_PropertyGroup_Properties);
					this.trt_PropertyGroup_Properties.setParent(object, globalParent);
				}
			}
			/* sub PropertyGroup */
			for(PropertyGroupDTO subPropertyGroup : propertyGroup.getSubPropertyGroupDTO()){
				if(propertyGroup.isActive() == true){
					this.setSubPropertyGroupProperties(subPropertyGroup, object);
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
