package be.jtrack.jtrackwebinterface.frontend.panel.JTrackInventory;


import java.util.Map;
import java.util.TreeMap;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.vaadin.event.Action;
import com.vaadin.event.FieldEvents.BlurEvent;
import com.vaadin.event.FieldEvents.BlurListener;
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
import com.vaadin.ui.TreeTable;
import com.vaadin.ui.Button.ClickEvent;

import be.jtrack.jtrackwebinterface.frontend.panel.L18NPanel;
import be.jtrack.jtrackwebinterface.listner.Lsn_FocusListner_TextField_HelpText;
import be.jtrack.jtrackwebinterface.util.Icon;
import be.jtrackinventory.exception.manager.ManagerException;
import be.jtrackinventory.service.dto.MaterialComponentDTO;
import be.jtrackinventory.service.dto.MaterialDTO;
import be.jtrackinventory.service.dto.PropertyDTO;
import be.jtrackinventory.service.dto.PropertyGroupDTO;
import be.jtrackinventory.service.manager.IManager;
@SuppressWarnings("unchecked")
public class Pnl_Material extends L18NPanel{
	/**
	 * Serial Version ID
	 */
	private static final long serialVersionUID = -44527839229138513L;
	/*
	 * Instance members
	 */
	ApplicationContext context = new ClassPathXmlApplicationContext(new String[] {"applicationContent.xml"});
	IManager<MaterialDTO> iManagerMaterial = (IManager<MaterialDTO>) context.getBean("iManagerMaterial");
	/* Data */
	Map<String, MaterialDTO> mapMaterial = new TreeMap<String, MaterialDTO>();
	Map<String, MaterialComponentDTO> mapMaterialComponent = new TreeMap<String, MaterialComponentDTO>();
	Map<String, PropertyDTO> mapProperty = new TreeMap<String, PropertyDTO>();
	Map<String, PropertyGroupDTO> mapPropertyGroup = new TreeMap<String, PropertyGroupDTO>();
	Map<String, PropertyGroupDTO> mapPropertyGroupCaption = new TreeMap<String, PropertyGroupDTO>();
	Map<String, MaterialComponentDTO> mapMaterialComponentCaption = new TreeMap<String, MaterialComponentDTO>();
	private Map<String, Object> MaterialComponentItem = new TreeMap<String, Object>();
	/* Action */
	private final Action act_tbl_Inventory_Add = new Action(super.captions.getString("CAP.ACT.1"),Icon.iconNew);
	private final Action[] ACTIONS = new Action[] {act_tbl_Inventory_Add};
	/* size */
	private final String abstractComponentHeight = "25px";
	private final String abstractButtonWidht = "120px";
	private final String abstractComponentWidht = "340px";
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
	/* GridLayout */
	private GridLayout grd_Material;
	/* Accordion */
	private Accordion acr_PropertyGroupSettingsDescription;
	/* Table */
	private Table tbl_MaterialSettings;
	private Table tbl_MaterialComponent;
	/* TreeTable */
	private TreeTable trt_PropertyGroup;
	private TreeTable trt_JTrackInventoryView_Base;
	private TreeTable trt_JTrackInventoryView_Structure;
	/* TabSheet */
	private TabSheet tbs_MaterialSettings;
	/* ComboBox */
	private ComboBox cmb_PropertyGroup;
	private ComboBox cmb_MaterialComponent;
	/* String */
	private String materialUniqueID;
	/* Object */
	private Object itemId;
	/**
	 * Default constructor for the Class
	 */
	public Pnl_Material(){
		init();
	}
	/**
	 * Constructor for the Class
	 * @param material as MaterialDTO
	 */
	public Pnl_Material(
			TreeTable jtrackInventoryBase,
			TreeTable jtrackInventoryStructure,
			Map<String, MaterialDTO> mapMaterial,
			Map<String, MaterialComponentDTO> mapMaterialComponent, 
			Map<String, PropertyGroupDTO> mapPropertyGroup, 
			Map<String, PropertyDTO> mapProperty,
			String materialUniqueID){
		this();
		this.trt_JTrackInventoryView_Base = jtrackInventoryBase;
		this.trt_JTrackInventoryView_Structure = jtrackInventoryStructure;
		this.mapMaterial = mapMaterial;
		this.mapMaterialComponent = mapMaterialComponent;
		this.mapPropertyGroup = mapPropertyGroup;
		this.mapProperty = mapProperty;
		this.materialUniqueID = materialUniqueID;
		this.initFields();
	}
	/*
	 * Initialize the Panel
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
		this.btn_Copy.addClickListener(new Button.ClickListener() {
			/**
			 * Serial Version iD
			 */
			private static final long serialVersionUID = -2489698282302970749L;
			@Override
			public void buttonClick(ClickEvent event) {
				Notification.show("id : " + itemId.toString());
			}
		});
		this.btn_New.setIcon(Icon.iconNew);
		this.btn_Delete.setIcon(Icon.iconDelete);
		this.btn_Save.addClickListener(new Button.ClickListener() {
			/**
			 * Serial Version iD
			 */
			private static final long serialVersionUID = -2489698282302970749L;
			@Override
			public void buttonClick(ClickEvent event) {
				/* get the PropertyGroup */
				PropertyGroupDTO prGroupDTO = null;
				try{
					prGroupDTO = mapPropertyGroupCaption.get(cmb_PropertyGroup.getValue());
				}catch(Exception e){
				}
				if(!txt_UniqueId.getValue().equals("")){
					/* value already exist in the database */
					/* set the properties of the material */
					mapMaterial.get(materialUniqueID).setCaption(txt_Caption.getValue());
					mapMaterial.get(materialUniqueID).setDescription(txt_Description.getValue());
					/* Set the propertyGroupDTO */
					mapMaterial.get(materialUniqueID).setPropertyGroupDTO(prGroupDTO);
					/* update the object in the database */
					try {
						iManagerMaterial.update(mapMaterial.get(materialUniqueID));
						Notification.show(captions.getString("CAP.DESC.7"));
					} catch (ManagerException mXe) {
						Notification.show(mXe.getCaption());
					}
				}else{
					/* value already exist in the database */
					/* set the properties of the material */
					MaterialDTO matDTO = new MaterialDTO();
					matDTO.setCaption(txt_Caption.getValue());
					matDTO.setDescription(txt_Description.getValue());
					matDTO.setPropertyGroupDTO(prGroupDTO);
					/* persist the object in the database */
					try {
						iManagerMaterial.persist(matDTO);
						Notification.show(captions.getString("CAP.DESC.7"));
						mapMaterial.put(matDTO.getUniqueId(), matDTO);
						txt_UniqueId.setValue(matDTO.getUniqueId());
					} catch (ManagerException mXe) {
						Notification.show(mXe.getCaption());
					}
					/* add the property to the TreeTable */
					for(Object obj : trt_JTrackInventoryView_Base.getItemIds()){
						if("4".equals(trt_JTrackInventoryView_Base.getItem(obj).getItemProperty(captions.getString("CAP.TRT.6")).getValue())){
							Object itemId = trt_JTrackInventoryView_Base.addItem(new Object[]{"      " + matDTO.getCaption(), matDTO.getUniqueId()},null);
							Object itemId_str = trt_JTrackInventoryView_Structure.addItem(new Object[]{"      " + matDTO.getCaption(), matDTO.getUniqueId()},null);
							trt_JTrackInventoryView_Base.setParent(itemId, obj);
//							trt_JTrackInventoryView_Structure.setParent(itemId_str, obj);
							setItemIcon(itemId, Icon.iconMaterial, trt_JTrackInventoryView_Base);
							setItemIcon(itemId_str, Icon.iconMaterial, trt_JTrackInventoryView_Structure);
						}
					}
				}
			}
		});
		this.btn_Delete.addClickListener(new Button.ClickListener() {
			/**
			 * Serial Version iD
			 */
			private static final long serialVersionUID = 3639783192817086758L;
			@Override
			public void buttonClick(ClickEvent event) {
				/* get the selected value */
				if(!txt_UniqueId.equals("")){
					MaterialDTO dto = mapMaterial.get(txt_UniqueId.getValue());
					try {
						/* remove from the database */
						iManagerMaterial.remove(txt_UniqueId.getValue());
						/* delete the property to the TreeTable */
						for(Object obj : trt_JTrackInventoryView_Base.getItemIds()){
							if(mapMaterial.get(txt_UniqueId.getValue()).getUniqueId().equals(trt_JTrackInventoryView_Base.getItem(obj).getItemProperty(captions.getString("CAP.TRT.6")).getValue())){
								trt_JTrackInventoryView_Base.removeItem(obj);
							}
						}
						/* remove from the collection */
						mapMaterial.remove(txt_UniqueId.getValue());
						/* reset the fields */
						materialUniqueID = null;
						/* show message to the user */
						Notification.show(captions.getString("CAP.DESC.6") + dto.getCaption());
					} catch (ManagerException mXe) {
						Notification.show(mXe.getCaption());
					}
					/* init fields */
					initFields();
				}
			}
		});
		this.btn_New.addClickListener(new Button.ClickListener() {
			/**
			 * Serial Version iD
			 */
			private static final long serialVersionUID = -8765392751654123923L;
			@Override
			public void buttonClick(ClickEvent event) {
				/* reset the fields */
				materialUniqueID = null;
				initFields();
			}
		});
		/* Table */
		this.tbl_MaterialSettings = new Table();
		this.tbl_MaterialSettings.setSizeFull();
		this.tbl_MaterialSettings.addContainerProperty(captions.getString("CAP.TBL.1"), String.class, null);
		this.tbl_MaterialSettings.addContainerProperty(captions.getString("CAP.TBL.2"), AbstractComponent.class, null);
		this.tbl_MaterialSettings.setColumnHeaderMode(Table.COLUMN_HEADER_MODE_HIDDEN);
		/* set Values PropertyGroup */
		this.tbl_MaterialSettings.addItem(new Object[] {captions.getString("CAP.LBL.2"),this.txt_Caption}, null);
		this.tbl_MaterialSettings.addItem(new Object[] {captions.getString("CAP.LBL.3"),this.txt_Description}, null);
		this.tbl_MaterialSettings.addItem(new Object[] {captions.getString("CAP.LBL.4"),this.cmb_PropertyGroup}, null);
		this.tbl_MaterialSettings.addItem(new Object[] {captions.getString("CAP.LBL.1"),this.txt_UniqueId}, null);
		/* tb*/
		this.tbl_MaterialComponent = new Table();
		this.tbl_MaterialComponent.setSizeFull();
		this.tbl_MaterialComponent.addContainerProperty(captions.getString("CAP.TBL.3"), Integer.class, null);
		this.tbl_MaterialComponent.addContainerProperty(captions.getString("CAP.TBL.8"), AbstractComponent.class, null);
		this.tbl_MaterialComponent.addContainerProperty(captions.getString("CAP.TBL.5"), String.class, null);
		this.tbl_MaterialComponent.addContainerProperty(captions.getString("CAP.TBL.6"), AbstractComponent.class, null);
		this.tbl_MaterialComponent.addActionHandler(new com.vaadin.event.Action.Handler() {
			/**
			 * Serial Version ID
			 */
			private static final long serialVersionUID = -7493111800753942849L;
			@Override
			public void handleAction(Action action, Object sender, Object target) {
				/* init the ComboBox */
				cmb_MaterialComponent = new ComboBox();
				cmb_MaterialComponent.setHeight(abstractComponentHeight);
				cmb_MaterialComponent.setWidth(abstractComponentWidht);
				cmb_MaterialComponent.setDescription(captions.getString("CAP.DESC.12"));
				cmb_MaterialComponent.addFocusListener(new Lsn_FocusListner_TextField_HelpText(lbl_PropertySettingsDescription, cmb_MaterialComponent));
				cmb_MaterialComponent.setFilteringMode(FilteringMode.CONTAINS);
				/* set the values */
				if(null != mapMaterialComponent){
					for(MaterialComponentDTO dto : mapMaterialComponent.values()){
						cmb_MaterialComponent.addItem(dto.getCaption());
						mapMaterialComponentCaption.put(dto.getCaption(), dto);
					}
				}
				/* add listner */
				cmb_MaterialComponent.addListener(new BlurListener() {
					/**
					 * Serial Version ID
					 */
					private static final long serialVersionUID = -5214295355138850098L;
					@Override
					public void blur(BlurEvent event) {
						/* delete the dummy row */ 
						tbl_MaterialComponent.removeItem(itemId);
						/* create Button that allows the user to delete the row */
						Button btn_DeleteMatComponent = new Button();
						btn_DeleteMatComponent.setIcon(Icon.iconDelete);
						btn_DeleteMatComponent.setId(mapMaterialComponentCaption.get(cmb_MaterialComponent.getValue().toString()).getCaption());
						btn_DeleteMatComponent.addClickListener(new Button.ClickListener() {
							/**
							 * Serial Version iD
							 */
							private static final long serialVersionUID = 8698781425782617928L;
							/**
							 * Method will remove the line out the list
							 */
							@Override
							public void buttonClick(ClickEvent event) {
								/* remove the dummy line out the table */
								tbl_MaterialComponent.removeItem(MaterialComponentItem.get(cmb_MaterialComponent.getValue().toString()));
								/* get the MaterialComponent */
								MaterialComponentDTO matCompDTO = mapMaterialComponentCaption.get(cmb_MaterialComponent.getValue().toString());
								if(null != materialUniqueID){
									if(null != matCompDTO){
										/* set the materialComponent as null in the Material */
										mapMaterial.get(materialUniqueID).getMaterialComponents().remove(matCompDTO);
										/* Persist the object to the database */
										try {
											iManagerMaterial.update(mapMaterial.get(materialUniqueID));
											Notification.show(captions.getString("CAP.DESC.13"));
										} catch (ManagerException mXe) {
											Notification.show(mXe.getCaption());
										}
									}
								}
							}
						});
						/* add object to the mapComponentItem */
						cmb_MaterialComponent.setValue(cmb_MaterialComponent.getValue());
						cmb_MaterialComponent.setEnabled(false);
						/* add the new Item to the table */
						Object item = tbl_MaterialComponent.addItem(new Object[]{tbl_MaterialComponent.size() + 1,cmb_MaterialComponent,mapMaterialComponentCaption.get(cmb_MaterialComponent.getValue()).getDescription(),btn_DeleteMatComponent},null);
						MaterialComponentItem.put(cmb_MaterialComponent.getValue().toString(), item);
						/* get the MaterialComponent */
						MaterialComponentDTO matCompDTO = mapMaterialComponentCaption.get(cmb_MaterialComponent.getValue().toString());
						/* set the materialComponent in the Material */
						if(null != materialUniqueID){
							if(null != matCompDTO){
								mapMaterial.get(materialUniqueID).getMaterialComponents().add(matCompDTO);
								/* Persist the object to the database */
								try {
									iManagerMaterial.update(mapMaterial.get(materialUniqueID));
									Notification.show(captions.getString("CAP.DESC.13"));
								} catch (ManagerException mXe) {
									Notification.show(mXe.getCaption());
								}
							}
						}
					}
				});
				if(null != action.getCaption()){
					/* Create dummy Button */
					Button btn_DeleteMatComponent = new Button();
					btn_DeleteMatComponent.setIcon(Icon.iconDelete);
					/* Action to perform when AddMaterialComponent is selected */
					if(captions.getString("CAP.ACT.1") == action.getCaption()){
						itemId = tbl_MaterialComponent.addItem(new Object[]{tbl_MaterialComponent.size() + 1,cmb_MaterialComponent,"",btn_DeleteMatComponent},null);
						tbl_MaterialComponent.getContainerProperty(itemId, captions.getString("CAP.TBL.5")).isReadOnly();
					}
				}
			}
			@Override
			public Action[] getActions(Object target, Object sender) {
				return ACTIONS;
			}
		});
		/* TreeTable */
		this.trt_PropertyGroup = new TreeTable();
		this.trt_PropertyGroup.setSizeFull();
		this.trt_PropertyGroup.addContainerProperty(captions.getString("CAP.TRT.1"), String.class, null);
		this.trt_PropertyGroup.addContainerProperty(captions.getString("CAP.TRT.2"), String.class, null);
		this.trt_PropertyGroup.setSelectable(true);		
		/* TabSheet */
		this.tbs_MaterialSettings = new TabSheet();
		this.tbs_MaterialSettings.setImmediate(true);
		this.tbs_MaterialSettings.addTab(this.tbl_MaterialSettings);
		this.tbs_MaterialSettings.getTab(this.tbl_MaterialSettings).setIcon(Icon.iconMaterial);
		this.tbs_MaterialSettings.getTab(this.tbl_MaterialSettings).setCaption(captions.getString("CAP.TBS.3"));
		this.tbs_MaterialSettings.addTab(this.tbl_MaterialComponent);
		this.tbs_MaterialSettings.getTab(this.tbl_MaterialComponent).setIcon(Icon.iconMaterialComponent);
		this.tbs_MaterialSettings.getTab(this.tbl_MaterialComponent).setCaption(captions.getString("CAP.TBS.6"));
		this.tbs_MaterialSettings.addTab(this.trt_PropertyGroup);
		this.tbs_MaterialSettings.getTab(this.trt_PropertyGroup).setIcon(Icon.iconPropertyGroup);
		this.tbs_MaterialSettings.getTab(this.trt_PropertyGroup).setCaption(captions.getString("CAP.TBS.2"));
		/* GridLayout */
		this.grd_Material = new GridLayout(4,5);
		this.grd_Material.addComponent(this.tbs_MaterialSettings,0,0,3,2);
		this.grd_Material.addComponent(this.acr_PropertyGroupSettingsDescription,0,3,3,3);
		this.grd_Material.addComponent(this.btn_Delete,0,4,0,4);
		this.grd_Material.addComponent(this.btn_Copy,1,4,1,4);
		this.grd_Material.addComponent(this.btn_New,2,4,2,4);
		this.grd_Material.addComponent(this.btn_Save,3,4,3,4);
		this.grd_Material.setComponentAlignment(this.acr_PropertyGroupSettingsDescription, Alignment.BOTTOM_LEFT);
		this.grd_Material.setComponentAlignment(this.btn_Delete, Alignment.BOTTOM_CENTER);
		this.grd_Material.setComponentAlignment(this.btn_Copy, Alignment.BOTTOM_CENTER);
		this.grd_Material.setComponentAlignment(this.btn_New, Alignment.BOTTOM_CENTER);
		this.grd_Material.setComponentAlignment(this.btn_Save, Alignment.BOTTOM_CENTER);
		this.grd_Material.setMargin(true);
		this.grd_Material.setSizeFull();
		this.setContent(this.grd_Material);
		this.setSizeFull();
	}
	/*
	 * initialize the values
	 */
	private void initFields(){
		/* Set the values in the ComboBox */
		if(null != mapPropertyGroup){
			for(PropertyGroupDTO dto : mapPropertyGroup.values()){
				cmb_PropertyGroup.addItem(dto.getCaption());
				mapPropertyGroupCaption.put(dto.getCaption(), dto);
			}
			/* set the selected value in the ComboBox */
			if(null != this.materialUniqueID){
				if(null != mapMaterial){
					if(null != mapMaterial.get(materialUniqueID)){
						if(null != mapMaterial.get(materialUniqueID).getPropertyGroupDTO()){
							cmb_PropertyGroup.setValue(mapMaterial.get(materialUniqueID).getPropertyGroupDTO().getCaption());
						}
					}
				}
			}else{
				cmb_PropertyGroup.setValue(null);
			}
		}
		/* Set the values for the tab Material */
		if(null != this.materialUniqueID){
			if(null != mapMaterial){
				if(null != mapMaterial.get(materialUniqueID)){
					this.txt_Caption.setValue(mapMaterial.get(materialUniqueID).getCaption());
					this.txt_Description.setValue(mapMaterial.get(materialUniqueID).getDescription());
					this.txt_UniqueId.setValue(mapMaterial.get(materialUniqueID).getUniqueId());
				}
			}
		}else{
			this.txt_Caption.setValue("");
			this.txt_Description.setValue("");
			this.txt_UniqueId.setValue("");
		}
		/* load the MaterialComponents */
		if(null != this.materialUniqueID){
			if(null != mapMaterial){
				if(null != mapMaterial.get(materialUniqueID)){
					for(MaterialComponentDTO dto : mapMaterial.get(materialUniqueID).getMaterialComponents()){
						/* ComboBox */
						ComboBox cmb_MaterialComponent = new ComboBox();
						cmb_MaterialComponent.setHeight(abstractComponentHeight);
						cmb_MaterialComponent.setWidth(abstractComponentWidht);
						cmb_MaterialComponent.setDescription(captions.getString("CAP.DESC.12"));
						cmb_MaterialComponent.addFocusListener(new Lsn_FocusListner_TextField_HelpText(lbl_PropertySettingsDescription, cmb_MaterialComponent));
						cmb_MaterialComponent.setEnabled(false);
						/* add the materialComponent */
						cmb_MaterialComponent.addItem(dto.getCaption());
						cmb_MaterialComponent.setValue(dto.getCaption());
						mapMaterialComponentCaption.put(dto.getCaption(), dto);
						/* Create dummy Button */
						Button btn_DeleteMatComponent = new Button();
						btn_DeleteMatComponent.setIcon(Icon.iconDelete);
						btn_DeleteMatComponent.setId(dto.getCaption());
						btn_DeleteMatComponent.addClickListener(new Button.ClickListener() {
							/**
							 * Serial Version iD
							 */
							private static final long serialVersionUID = 8698781425782617928L;
							/**
							 * Method will remove the line out the list
							 */
							@Override
							public void buttonClick(ClickEvent event) {
								/* remove the dummy line out the table */
								tbl_MaterialComponent.removeItem(MaterialComponentItem.get(event.getComponent().getId()));
								/* get the MaterialComponent */
								MaterialComponentDTO matCompDTO = mapMaterialComponentCaption.get(event.getComponent().getId());
								if(null != materialUniqueID){
									if(null != matCompDTO){
										/* set the materialComponent as null in the Material */
										mapMaterial.get(materialUniqueID).getMaterialComponents().remove(matCompDTO);
										/* Persist the object to the database */
										try {
											iManagerMaterial.update(mapMaterial.get(materialUniqueID));
											Notification.show(captions.getString("CAP.DESC.13"));
										} catch (ManagerException mXe) {
											Notification.show(mXe.getCaption());
										}
									}
								}
							}
						});
						/* Action to perform when AddMaterialComponent is selected */
						Object item = tbl_MaterialComponent.addItem(new Object[]{tbl_MaterialComponent.size() + 1,cmb_MaterialComponent,dto.getDescription(),btn_DeleteMatComponent},null);
						tbl_MaterialComponent.getContainerProperty(item, captions.getString("CAP.TBL.5")).isReadOnly();
						tbl_MaterialComponent.getContainerProperty(item, captions.getString("CAP.TBL.3")).isReadOnly();
						tbl_MaterialComponent.getContainerProperty(item, captions.getString("CAP.TBL.8")).isReadOnly();
						MaterialComponentItem.put(dto.getCaption(), item);
					}
				}
			}
		}
		/* Set the TreeTable PropertyGroup */
		if(null != this.materialUniqueID){
			if(null != mapMaterial){
				if(null != mapMaterial.get(materialUniqueID)){
					/* Add the PropertyGroup of the Material */
					if(null != mapMaterial.get(materialUniqueID).getPropertyGroupDTO()){
						Object globalParent = this.trt_PropertyGroup.addItem(new Object[]{mapMaterial.get(materialUniqueID).getPropertyGroupDTO().getCaption(), mapMaterial.get(materialUniqueID).getPropertyGroupDTO().getDescription()}, null);
						this.setItemIcon(globalParent, Icon.iconPropertyGroup, this.trt_PropertyGroup);
						if(null != mapProperty){
							for(PropertyDTO prtDTO : mapProperty.values()){
								/* add the main propertyGroup */
								if(null != mapMaterial.get(materialUniqueID).getPropertyGroupDTO()){
									if(null != prtDTO.getPropertyGroupDTO()){
										if(prtDTO.getPropertyGroupDTO().getUniqueId().equals(mapMaterial.get(materialUniqueID).getPropertyGroupDTO().getUniqueId())){
											Object child = this.trt_PropertyGroup.addItem(new Object[]{prtDTO.getCaption(), prtDTO.getDescription()}, null);
											this.trt_PropertyGroup.setParent(child, globalParent);
											this.setItemIcon(child, Icon.iconProperty, this.trt_PropertyGroup);
										}
									}
								}
							}
						}
						/* add the propertyGroup of the MaterialComponent */
						for(MaterialComponentDTO matCompDTO : mapMaterial.get(materialUniqueID).getMaterialComponents()){
							setPropertyGroupObjects(matCompDTO.getPropertyGroupDTO(), globalParent);
						}
					}
				}
			}
		}
	}
	/* */
	private void setPropertyGroupObjects(PropertyGroupDTO propertyGroup, Object parent){
		if(null != propertyGroup){
			/* add the main propertyGroup */
			Object subParent = this.trt_PropertyGroup.addItem(new Object[]{propertyGroup.getCaption(), propertyGroup.getDescription()}, null);
			this.setItemIcon(subParent, Icon.iconPropertyGroup, this.trt_PropertyGroup);
			this.trt_PropertyGroup.setParent(subParent, parent);
			for(PropertyDTO prtDTO : mapProperty.values()){
				if(null != prtDTO.getPropertyGroupDTO()){
					if(prtDTO.getPropertyGroupDTO().getUniqueId().equals(propertyGroup.getUniqueId())){
						Object child = this.trt_PropertyGroup.addItem(new Object[]{prtDTO.getCaption(), prtDTO.getDescription()}, null);
						this.trt_PropertyGroup.setParent(child, subParent);
						this.setItemIcon(child, Icon.iconProperty, this.trt_PropertyGroup);
						for(PropertyGroupDTO groupDTO : propertyGroup.getSubPropertyGroupDTO()){
							setPropertyGroupObjects(groupDTO, subParent);
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
