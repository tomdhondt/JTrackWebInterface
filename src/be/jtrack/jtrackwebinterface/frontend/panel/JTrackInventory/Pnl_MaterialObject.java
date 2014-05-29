package be.jtrack.jtrackwebinterface.frontend.panel.JTrackInventory;

//import java.util.Iterator;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.vaadin.server.Resource;
import com.vaadin.ui.AbstractComponent;
import com.vaadin.ui.Accordion;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
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
import be.jtrackinventory.service.dto.MaterialComponentObjectDTO;
import be.jtrackinventory.service.dto.MaterialDTO;
import be.jtrackinventory.service.dto.MaterialObjectDTO;
import be.jtrackinventory.service.dto.PropertyDTO;
import be.jtrackinventory.service.dto.ValueDTO;
import be.jtrackinventory.service.manager.IManager;
@SuppressWarnings("unchecked")
public class Pnl_MaterialObject  extends L18NPanel{
	/**
	 * SerialVersionID
	 */
	private static final long serialVersionUID = 115136397492800554L;
	/*
	 * Instance members
	 */
	ApplicationContext context = new ClassPathXmlApplicationContext(new String[] {"applicationContent.xml"});
	IManager<MaterialObjectDTO> iManagerMaterialObject = (IManager<MaterialObjectDTO>) context.getBean("iManagerMaterialObject");
	IManager<MaterialComponentObjectDTO> iManagerMaterialComponentObject = (IManager<MaterialComponentObjectDTO>) context.getBean("iManagerMaterialComponentObject");
	IManager<ValueDTO> iManagerValue= (IManager<ValueDTO>) context.getBean("iManagerValue");
	/* size */
	private final String abstractComponentHeight = "25px";
	private final String abstractButtonWidht = "120px";
	private final String abstractComponentWidht = "340px";
	/* Accordion */
	private Accordion acr_PropertySettingsDescription;
	/* Table */
	private Table tbl_MaterialObject;
	
	/* TextBox */
	private TextField txt_MaterialObjectCaption;
	private TextField txt_MaterialObjectUniqueId;
	/* Label */
	private Label lbl_PropertySettingsDescription;
	private Label lbl_MaterialObjectCaption;
	private Label lbl_MaterialObjectUniqueId;
	/* TabSheet */
	private TabSheet tbs_MaterialObject;
	/* Button */
	private Button btn_Save;
	private Button btn_New;
	private Button btn_Copy;
	private Button btn_Delete;
	/* String */
	private String materialObjectUniqueId;
	private String materialUniqueId;
	/* TreemMap */
	private Map<String, MaterialDTO> mapMaterial = new TreeMap<String, MaterialDTO>();
	private Map<String, MaterialObjectDTO> mapMaterialObject = new TreeMap<String, MaterialObjectDTO>();
	private Map<String, MaterialComponentObjectDTO> mapMaterialComponentObject = new TreeMap<String, MaterialComponentObjectDTO>();
	private Map<String, PropertyDTO> mapProperty = new TreeMap<String, PropertyDTO>();
	private Map<String,TextField> mapPropertyAdded = new TreeMap<String,TextField>();
	private Map<String,ValueDTO> mapValue = new TreeMap<String, ValueDTO>();
	private Map<String,ValueDTO> mapPropertyIdValue = new TreeMap<String, ValueDTO>();
	private Map<String, MaterialComponentObjectDTO> mapMaterialComponentObjectAdded = new TreeMap<String, MaterialComponentObjectDTO>();
	private Map<String, Map<String,TextField>> mapMaterialComponentObjectValueAdded = new TreeMap<String, Map<String,TextField>>();
	private List<TextField> lst_globalMaterialComponentObject = new ArrayList<TextField>();
	/* TreeTable */
	private TreeTable trt_JTrackInventoryView_Structure;
	/* GridLayout */
	private GridLayout grd_MaterialObject;
	/**
	 * Default constructor for the Class
	 */
	public Pnl_MaterialObject(){
		init();
	}
	/**
	 * Constructor for the Class
	 * @param materialObjectUniqueId as MaterialObject uniqueId
	 * @param materialUniqueId as material UniqueId
	 * @param mapMaterial as Map<String,MaterialDTO>
	 * @param mapMaterialObject as Map<String,MaterialObjectDTO>
	 * @param mapProperty as Map<String,PropertyDTO>
	 */
	public Pnl_MaterialObject(
			TreeTable treeTable, 
			String materialObjectUniqueId, 
			String materialUniqueId,
			Map<String, MaterialDTO> mapMaterial, 
			Map<String, MaterialObjectDTO> mapMaterialObject,
			Map<String, MaterialComponentObjectDTO> mapMaterialComponentObject, 
			Map<String, PropertyDTO> mapProperty){
		this();
		this.trt_JTrackInventoryView_Structure = treeTable;
		this.materialObjectUniqueId = materialObjectUniqueId;
		this.materialUniqueId = materialUniqueId;
		this.mapProperty = mapProperty;
		this.mapMaterialComponentObject = mapMaterialComponentObject;
		this.mapMaterial = mapMaterial;
		this.mapMaterialObject = mapMaterialObject;
		initializeField();
	}
	/*
	 * initialize the Class
	 */
	@SuppressWarnings("deprecation")
	private void init(){
		/* TextBox */
		this.txt_MaterialObjectCaption = new TextField();
		this.txt_MaterialObjectCaption.setHeight(abstractComponentHeight);
		this.txt_MaterialObjectCaption.setWidth(abstractComponentWidht);
		this.txt_MaterialObjectCaption.setDescription(captions.getString("CAP.DESC.15"));
		this.txt_MaterialObjectCaption.addFocusListener(new Lsn_FocusListner_TextField_HelpText(this.lbl_PropertySettingsDescription, this.txt_MaterialObjectCaption));
		this.txt_MaterialObjectUniqueId = new TextField();
		this.txt_MaterialObjectUniqueId.setHeight(abstractComponentHeight);
		this.txt_MaterialObjectUniqueId.setWidth(abstractComponentWidht);
		this.txt_MaterialObjectUniqueId.setEnabled(false);
		/* Label */
		this.lbl_PropertySettingsDescription = new Label(captions.getString("CAP.LBL.6"));
		this.lbl_MaterialObjectCaption = new Label(captions.getString("CAP.LBL.2"));
		this.lbl_MaterialObjectUniqueId = new Label(captions.getString("CAP.LBL.1"));
		/* Button */
		this.btn_Save = new Button(captions.getString("CAP.BTN.1"));
		this.btn_Copy = new Button(captions.getString("CAP.BTN.2"));
		this.btn_New = new Button(captions.getString("CAP.BTN.3"));
		this.btn_Delete = new Button(captions.getString("CAP.BTN.4"));
		this.btn_Save.setHeight(this.abstractComponentHeight);
		this.btn_Copy.setHeight(this.abstractComponentHeight);
		this.btn_New.setHeight(this.abstractComponentHeight);
		this.btn_Delete.setHeight(this.abstractComponentHeight);
		this.btn_Save.setWidth(this.abstractButtonWidht);
		this.btn_Copy.setWidth(this.abstractButtonWidht);
		this.btn_New.setWidth(this.abstractButtonWidht);
		this.btn_Delete.setWidth(this.abstractButtonWidht);
		this.btn_Save.setIcon(Icon.iconSave);
		this.btn_Copy.setIcon(Icon.iconCopy);
		this.btn_New.setIcon(Icon.iconNew);
		this.btn_Delete.setIcon(Icon.iconDelete);
		this.btn_Save.addClickListener(new Button.ClickListener() {
			/**
			 * Serial Version iD
			 */
			private static final long serialVersionUID = -4163569192914228200L;
			@Override
			public void buttonClick(ClickEvent event) {
				StringBuilder output = new StringBuilder(captions.getString("CAP.DESC.16"));
				boolean checkOk = true;
				/* check the MaterialObject Caption */
				if(txt_MaterialObjectCaption.getValue().equals("")){
					checkOk = false;
					output.append(mapProperty.get(lbl_MaterialObjectCaption.getValue()));
					Notification.show(output.toString());	
				}
				/* Check the MaterialObject values */
				for(TextField txt : mapPropertyAdded.values()){
					/* get the uniqueId of the property */
					if(null != mapProperty.get(txt.getId())){
						/* get the PropertyType */
						if(null != mapProperty.get(txt.getId()).getPropertyTypeDTO()){
							/* validate the value of the property */
							if(!validateValue(mapProperty.get(txt.getId()).getPropertyTypeDTO().getRegex(),txt.getValue())){
								output.append(mapProperty.get(txt.getId()).getCaption());
								checkOk = false;
							}
						}
					}
				}
				/* show error when needed */
				if(checkOk == false){
					Notification.show(output.toString());
				}
				/* persist the objects in the database */
				if(checkOk == true){
					/* get the Material in the mapMaterial */
					if(null!= mapMaterial){
						/* check if the Material exists in the database */
						if(null != mapMaterial.get(materialUniqueId)){
							/* create a new MaterialObject */
							MaterialObjectDTO materialObjectDTO = null;
							/* check if the MaterialObject already exists in the database */
							if(0 < txt_MaterialObjectUniqueId.getValue().length()){
								/* update the MaterialObject in the database */
								/* get the MaterialObject */
								MaterialObjectDTO matObject = mapMaterialObject.get(txt_MaterialObjectUniqueId.getValue());
								if(null != matObject){
									matObject.setCaption(txt_MaterialObjectCaption.getValue());
									/* update the object in the database */
									try {
										/* update the MaterialObjectDTO */
										iManagerMaterialObject.update(matObject);
										/* update the values */
										for(TextField txt : mapPropertyAdded.values()){
											/* get the PropertyDTO that is connected to the TextField */ 
											if(null != mapProperty.get(txt.getId())){
												/* Get the ValueDTO */
												if(null != mapPropertyIdValue.get(txt.getId())){
													/* Set the value for the ValueDTO */
													mapPropertyIdValue.get(txt.getId()).setValue(txt.getValue());
													/* Update the value to the database */
													try {
														iManagerValue.update(mapPropertyIdValue.get(txt.getId()));
													} catch (ManagerException mXe) {
														Notification.show(mXe.getCaption());
													}
												}
											}
										}
										/* Update the TreeTable */
										for(Object itemId : trt_JTrackInventoryView_Structure.getItemIds()){
											Notification.show(trt_JTrackInventoryView_Structure.getItem(itemId).getItemProperty(captions.getString("CAP.TRT.6")).getValue() + " - " + matObject.getUniqueId());
											
											if(trt_JTrackInventoryView_Structure.getItem(itemId).getItemProperty(captions.getString("CAP.TRT.6")).getValue().equals(matObject.getUniqueId())){
												trt_JTrackInventoryView_Structure.getItem(itemId).getItemProperty(captions.getString("CAP.TRT.4")).setValue("      " + matObject.getCaption());
											}
										}
										/* Show message */
										Notification.show(captions.getString("CAP.DESC.7") + " " + txt_MaterialObjectCaption);
									} catch (ManagerException mXe) {
										Notification.show(mXe.getCaption());
									}
								}
							}else{
								/* persist the MaterialObject in the database */
								materialObjectDTO = new MaterialObjectDTO();
								/* Set the MaterialObject properties */
								materialObjectDTO.setCaption(txt_MaterialObjectCaption.getValue());
								materialObjectDTO.setMaterialDTO(mapMaterial.get(materialUniqueId));
								/* set the MaterialObject in the mapMaterialObject */
								mapMaterialObject.put(materialObjectDTO.getUniqueId(), materialObjectDTO);
								/* mapPropertyAdded = new TreeMap<String,TextField>(); */
								for(TextField txt : mapPropertyAdded.values()){
									/* get the PropertyDTO that is connected to the TextField */ 
									if(null != mapProperty.get(txt.getId())){
										/* create a new ValueDTO */
										ValueDTO val = new ValueDTO(mapProperty.get(txt.getId()));
										val.setValue(txt.getValue());
										/* persist the value to the database */
										try {
											iManagerValue.persist(val);
										} catch (ManagerException mXe) {
											Notification.show(mXe.getCaption());
										}
										/* add the value to the MaterialObjectDTO */
										materialObjectDTO.getValues().add(val);
									}
								}
								/* Persist the MaterialObjectDTO to the database */
								try {
									iManagerMaterialObject.persist(materialObjectDTO);
									/* set the textField with the uniqueId */
									txt_MaterialObjectUniqueId.setValue(materialObjectDTO.getUniqueId());
									materialObjectUniqueId = materialObjectDTO.getUniqueId();
									/* add the MaterialObject to the TreeTable */
									for(Object obj : trt_JTrackInventoryView_Structure.getItemIds()){
										if(materialUniqueId.equals(trt_JTrackInventoryView_Structure.getItem(obj).getItemProperty(captions.getString("CAP.TRT.6")).getValue())){
											Object itemId = trt_JTrackInventoryView_Structure.addItem(new Object[]{"      " + materialObjectDTO.getCaption(), materialObjectDTO.getUniqueId()},null);
											trt_JTrackInventoryView_Structure.setParent(itemId, obj);
											setItemIcon(itemId, Icon.iconMaterialObject, trt_JTrackInventoryView_Structure);						
										}
									}
									/* Show message success */
									Notification.show(captions.getString("CAP.DESC.8") + " " + txt_MaterialObjectCaption.getValue());
									/* Persist MaterialComponentObject */
									persistMaterialComponentObject(materialObjectDTO);
								} catch (ManagerException mXe) {
									Notification.show(mXe.getCaption());
								}
							}
						}					
					}
				}
			}
		});
		this.btn_Copy.addClickListener(new Button.ClickListener() {
			/**
			 * Serial Version iD
			 */
			private static final long serialVersionUID = -4163569192914228200L;
			@Override
			public void buttonClick(ClickEvent event) {
				/* check if the mapMaterialComponentObject isn't null */			
				if(null != mapMaterialComponentObject){
					/* iterate the MaterialComponentObject */
					for(MaterialComponentObjectDTO matCompObjDTO : mapMaterialComponentObject.values()){
						/* Check the MaterialObjectDTO*/
						if(null != matCompObjDTO.getMaterialObjectDTO()) {
							/* Check the materialObjectUniqueId*/
							if(null != materialObjectUniqueId){
								if(matCompObjDTO.getMaterialObjectDTO().getUniqueId().equals(materialObjectUniqueId)){
								}
							}
						}
					}
				}
			}
		});
		this.btn_Delete.addClickListener(new Button.ClickListener() {
			/**
			 * Serial Version iD
			 */
			private static final long serialVersionUID = -4163569192914228200L;
			@Override
			public void buttonClick(ClickEvent event) {
				if(0 < txt_MaterialObjectUniqueId.getValue().length()){
					/* remove from the database */
					try {
						iManagerMaterialObject.remove(txt_MaterialObjectUniqueId.getValue());
						Notification.show(captions.getString("CAP.DESC.6") + " " + txt_MaterialObjectCaption);
					} catch (ManagerException mXe) {
						Notification.show(mXe.getCaption());
					}
					/* delete the property to the TreeTable */
					for(Object obj : trt_JTrackInventoryView_Structure.getItemIds()){
						if(mapMaterialObject.get(materialObjectUniqueId).getUniqueId().equals(trt_JTrackInventoryView_Structure.getItem(obj).getItemProperty(captions.getString("CAP.TRT.6")).getValue())){
							trt_JTrackInventoryView_Structure.removeItem(obj);
						}
					}
				}
			}
		});
		/* Accordion */ 
		this.acr_PropertySettingsDescription = new Accordion();
		this.acr_PropertySettingsDescription.addTab(this.lbl_PropertySettingsDescription,captions.getString("CAP.TAB.1"),Icon.iconHelp);
		/* Table */
		this.tbl_MaterialObject = new Table();
		this.tbl_MaterialObject.setId("1");
		this.tbl_MaterialObject.setSizeFull();
		this.tbl_MaterialObject.setSelectable(true);
		this.tbl_MaterialObject.addContainerProperty(captions.getString("CAP.TBL.3"), Integer.class, null);
		this.tbl_MaterialObject.addContainerProperty(captions.getString("CAP.TBL.1"), AbstractComponent.class, null);
		this.tbl_MaterialObject.addContainerProperty(captions.getString("CAP.TBL.2"), AbstractComponent.class, null);
		this.tbl_MaterialObject.setColumnHeaderMode(Table.COLUMN_HEADER_MODE_HIDDEN);
		/* TabSheet */
		this.tbs_MaterialObject = new TabSheet();
		this.tbs_MaterialObject.setImmediate(true);
		this.tbs_MaterialObject.addTab(tbl_MaterialObject);
		this.tbs_MaterialObject.getTab(tbl_MaterialObject).setIcon(Icon.iconMaterialObject);
		this.tbs_MaterialObject.getTab(tbl_MaterialObject).setCaption(captions.getString("CAP.TBS.7"));
		/* GridLayout */
		this.grd_MaterialObject = new GridLayout(4,5);
		this.grd_MaterialObject.addComponent(this.tbs_MaterialObject,0,0,3,2);
		this.grd_MaterialObject.addComponent(this.acr_PropertySettingsDescription,0,3,3,3);
		this.grd_MaterialObject.addComponent(this.btn_Delete,0,4,0,4);
		this.grd_MaterialObject.addComponent(this.btn_Copy,1,4,1,4);
		this.grd_MaterialObject.addComponent(this.btn_New,2,4,2,4);
		this.grd_MaterialObject.addComponent(this.btn_Save,3,4,3,4);
		this.grd_MaterialObject.setComponentAlignment(this.tbs_MaterialObject,Alignment.TOP_LEFT);
		this.grd_MaterialObject.setComponentAlignment(this.acr_PropertySettingsDescription,Alignment.MIDDLE_LEFT);
		this.grd_MaterialObject.setComponentAlignment(this.btn_Delete, Alignment.MIDDLE_CENTER);
		this.grd_MaterialObject.setComponentAlignment(this.btn_Copy, Alignment.MIDDLE_CENTER);
		this.grd_MaterialObject.setComponentAlignment(this.btn_New, Alignment.MIDDLE_CENTER);
		this.grd_MaterialObject.setComponentAlignment(this.btn_Save, Alignment.MIDDLE_CENTER);
		this.grd_MaterialObject.setHeight("100%");
		this.grd_MaterialObject.setWidth("99%");
		this.grd_MaterialObject.setMargin(false);
		this.setContent(this.grd_MaterialObject);
		this.setSizeFull();
	}
	/* Method will persist the materialComponentObject in the database */
	private void persistMaterialComponentObject(MaterialObjectDTO materialObjectDTO){
		/* 
		 * 
		 * Persist the MaterialComponentObjectDTO in the database 
		 * 
		 */
		boolean checkOK = true;
		StringBuilder error = new StringBuilder(captions.getString("CAP.DESC.16"));	
		for(String i : mapMaterialComponentObjectValueAdded.keySet()){
			/* iterate the values specific to a certain MaterialComponent */
			Map<String, TextField> values = mapMaterialComponentObjectValueAdded.get(i);
			/* Create the MaterialComponentObject */
			MaterialComponentObjectDTO mcoDTO = mapMaterialComponentObjectAdded.get(i);
			/* check the Caption for the MaterialComponentObject */
			for(TextField txtField : lst_globalMaterialComponentObject){
				/* check the TextField parent */ 
				if(mcoDTO.getUniqueId().equals(txtField.getParent().getId())){
					/* check the TextField Caption */
					if(txtField.getId().equals(captions.getString("CAP.LBL.2"))){
						/* check the value for the caption */
						if(txtField.getValue().equals("")){
							error.append(captions.getString("CAP.LBL.2"));
							checkOK = false;
						}
					}
				}
			}
			/* Check the values of the MaterialComponentObject */
			for(String sti : values.keySet()){
				/* Create a new ValueDTO */
				if(null != mapProperty.get(sti)) {
					/* Get the PropertyType */
					if(null != mapProperty.get(sti).getPropertyTypeDTO()){
						/* Get the Regex */
						if(null != mapProperty.get(sti).getPropertyTypeDTO().getRegex()){
							/* When the check isn't correct */
							if(!validateValue(mapProperty.get(sti).getPropertyTypeDTO().getRegex(), values.get(sti).getValue())){
								error.append(mapProperty.get(sti).getCaption());
								checkOK = false;
							}
						}
					}
				}
			}
			if(checkOK==true ){
				/* Set the Properties for the MaterialComponentObject */
				mcoDTO.setMaterialObjectDTO(materialObjectDTO);
				/* Set the Caption */
				for(TextField txtField : lst_globalMaterialComponentObject){
					/* check the TextField parent */ 
					if(mcoDTO.getUniqueId().equals(txtField.getParent().getId())){
						/* check the TextField Caption */
						if(txtField.getId().equals(captions.getString("CAP.LBL.2"))){
							/* check the value for the caption */
							if(!txtField.getValue().equals("")){
								/* Set the Caption MaterialComponentObject */
								mcoDTO.setCaption(txtField.getValue());
							}else{
								error.append(captions.getString("CAP.LBL.2"));
								checkOK = false;
							}
						}
					}
				}
				/* iterate the Values added to the MaterialComponent */
				for(String sti : values.keySet()){
					/* Create a new ValueDTO */
					if(null != mapProperty.get(sti)) {
						/* Set the PropertyDTO */
						ValueDTO valDTO = new ValueDTO(mapProperty.get(sti));
						/* Set the Value */
						valDTO.setValue(values.get(sti).getValue());
						/* Persist the ValueDTO to the database */
						try {
							iManagerValue.persist(valDTO);
						} catch (ManagerException e) {
							Notification.show(e.getCaption());
						}
						/* add the ValueDTO to the MaterialComponentObject */
						if(null != mcoDTO){
							if(null != mcoDTO.getValues()){
								mcoDTO.getValues().add(valDTO);
							}
						}
					}
				}
				/* Persist the MaterialComponentObject */
				try {
					iManagerMaterialComponentObject.persist(mcoDTO);
					mapMaterialComponentObject.put(mcoDTO.getUniqueId(), mcoDTO);
					/* Show message success */
					Notification.show(captions.getString("CAP.DESC.8") + " " + mcoDTO.getCaption());
					/* Set the UniqueId for the MaterialCompopnent TextField uniqueId */
					for(TextField txtField : lst_globalMaterialComponentObject){
						/* check the TextField uniqueId */
						if(txtField.getId().equals(captions.getString("CAP.LBL.1"))){
							txtField.setValue(mcoDTO.getUniqueId());
						}
					}
				} catch (ManagerException e) {
					Notification.show(e.getCaption());
				}
			}else{
				/* show a error message when the check isn't correct */
				Notification.show(error.toString());
			}
		}
	}
	
	
	/*
	 * initialize the field values 
	 */
	private void initializeField(){
		/* MaterialObject is already in the database */
		if(null != materialObjectUniqueId){
			if(null != mapMaterialObject.get(materialObjectUniqueId)){
				/* add the base fields */
				this.tbl_MaterialObject.addItem(new Object[]{this.tbl_MaterialObject.size() + 1, this.lbl_MaterialObjectCaption, this.txt_MaterialObjectCaption},null);
				this.tbl_MaterialObject.addItem(new Object[]{this.tbl_MaterialObject.size() + 1, this.lbl_MaterialObjectUniqueId, this.txt_MaterialObjectUniqueId},null);
				this.txt_MaterialObjectCaption.setValue( mapMaterialObject.get(materialObjectUniqueId).getCaption());
				this.txt_MaterialObjectUniqueId.setValue( mapMaterialObject.get(materialObjectUniqueId).getUniqueId());
			}
		}else{
			this.tbl_MaterialObject.addItem(new Object[]{this.tbl_MaterialObject.size() + 1, this.lbl_MaterialObjectCaption, this.txt_MaterialObjectCaption},null);
			this.tbl_MaterialObject.addItem(new Object[]{this.tbl_MaterialObject.size() + 1, this.lbl_MaterialObjectUniqueId, this.txt_MaterialObjectUniqueId},null);
		}
		/* add the Properties to the Table */
		/* check witch Properties belong to the Material */
		if(null != materialUniqueId){
			if(null != mapProperty){
				if(null != mapMaterial.get(materialUniqueId)){
					if(null != mapMaterial.get(materialUniqueId).getPropertyGroupDTO()){
						for(PropertyDTO prDTO : mapProperty.values()){
							if(null != prDTO.getPropertyGroupDTO()){
								if(prDTO.getPropertyGroupDTO().getUniqueId().equals(mapMaterial.get(materialUniqueId).getPropertyGroupDTO().getUniqueId())){
									/* create TextField */
									TextField txt_Value = new TextField();
									txt_Value.setDescription(prDTO.getDescription());
									txt_Value.setWidth(this.abstractComponentWidht);
									txt_Value.setHeight(this.abstractComponentHeight);
									txt_Value.addFocusListener(new Lsn_FocusListner_TextField_HelpText(this.lbl_PropertySettingsDescription, txt_Value));
									txt_Value.setId(prDTO.getUniqueId());
									tbl_MaterialObject.addItem(new Object[]{this.tbl_MaterialObject.size() + 1,new Label(prDTO.getCaption()), txt_Value},null);
									mapPropertyAdded.put(prDTO.getUniqueId(),txt_Value);
								}
							}
						}
					}
				}
			}
		}
		/* fill the values when the object is found */
		if(null != materialObjectUniqueId){
			if(null != mapMaterialObject.get(materialObjectUniqueId)){
				for(ValueDTO val : mapMaterialObject.get(materialObjectUniqueId).getValues()){
					if(null != val.getPropertyDTO()){
						/* set the content for the value in the textbox */
						mapPropertyAdded.get(val.getPropertyDTO().getUniqueId()).setValue(val.getValue());
						/* add the value in the mapValue */
						mapValue.put(val.getUniqueId(), val);
						/* add the value in the map Value MaterialProperty */
						mapPropertyIdValue.put(val.getPropertyDTO().getUniqueId(),val);
					}
				}
			}
		}
		/* create the TabSheet for the MaterialComponentObject */
		if(null != materialObjectUniqueId){
			/* check if the mapMaterialComponentObject isn't null */			
			if(null != mapMaterialComponentObject){
				/* iterate the MaterialComponentObject */
				List<String> lst_MaterialComponentsAdded = new ArrayList<String>();
				for(MaterialComponentObjectDTO matCompObjDTO : mapMaterialComponentObject.values()){
					/* Check the MaterialObjectDTO*/
					if(null != matCompObjDTO.getMaterialObjectDTO()) {
						/* Check the materialObjectUniqueId*/
						if(null != materialObjectUniqueId){
							if(matCompObjDTO.getMaterialObjectDTO().getUniqueId().equals(materialObjectUniqueId)){
								/* create the existing MaterialComponentObject */
								this.createMaterialComponentObjectTabSheet(matCompObjDTO);
								/* set it as added to to the list */
								lst_MaterialComponentsAdded.add(matCompObjDTO.getMaterialComponentDTO().getUniqueId());
							}
						}
//						/* check or all the materialComponents are added sins the last changes */
//						if(null != matCompObjDTO.getMaterialObjectDTO().getMaterialDTO()){
//							if(null != matCompObjDTO.getMaterialObjectDTO().getMaterialDTO().getMaterialComponents()){	
//								if(lst_MaterialComponentsAdded.size() < matCompObjDTO.getMaterialObjectDTO().getMaterialDTO().getMaterialComponents().size()){
//									/* check witch are missing and add them */
//									for(MaterialComponentDTO dto : matCompObjDTO.getMaterialObjectDTO().getMaterialDTO().getMaterialComponents()){
//										if(!lst_MaterialComponentsAdded.contains(dto.getUniqueId())){
//											/* create a new materialObjectComponent and set the MaterialComponent */
//											MaterialComponentObjectDTO matCompObjDTO_ = new MaterialComponentObjectDTO(dto);
//											this.createMaterialComponentObjectTabSheet(matCompObjDTO_);
//											/* add the MaterialComponentObject as added in the map TabSheets added */
//											this.mapMaterialComponentObjectAdded.put(dto.getUniqueId(), matCompObjDTO_);
//											lst_MaterialComponentsAdded.add(dto.getUniqueId());
//										}
//									}
//								}
//							}
//						}
					}
				}
			}
		}else{
			/* create the empty MaterialComponentObjects defined by the Material */
			if(null != materialUniqueId){
				if(null != mapMaterial){
					if(null != mapMaterial.get(materialUniqueId)){
						/* for every MaterialComponent Object a new TabSheet */
						for(MaterialComponentDTO materialComponentDTO: mapMaterial.get(materialUniqueId).getMaterialComponents()){
							/* create a new materialObjectComponent and set the MaterialComponent */
							MaterialComponentObjectDTO matCompObjDTO = new MaterialComponentObjectDTO(materialComponentDTO);
							/* create the materialComponentObject TabSheet */
							try{
								this.createMaterialComponentObjectTabSheet(matCompObjDTO);
								/* add the MaterialComponentObject as added in the map TabSheets added */
								this.mapMaterialComponentObjectAdded.put(matCompObjDTO.getUniqueId(), matCompObjDTO);
							}catch(Exception e){
								Notification.show("Could not add the materialComponent!");
							}
						}
					}
				}
			}
		}
	}
	/* method will validate the field content depending on the regex */
	private boolean validateValue(String regex, String value){
		boolean checkPast = false;
		if(null != regex){
			if(null != value){
				Pattern p = Pattern.compile(regex);
				Matcher m = p.matcher(value);
				checkPast = m.find();
			}
		}
		return checkPast;
	}
	/* method will billed the MaterialComponent TabSheet */
	@SuppressWarnings("deprecation")
	private void createMaterialComponentObjectTabSheet(MaterialComponentObjectDTO materialComponentObjectDTO){
		if(null != materialComponentObjectDTO){
			/* Create Table */
			Table tbl_MatCompTable = new Table();
			tbl_MatCompTable.setId(materialComponentObjectDTO.getUniqueId());
			tbl_MatCompTable.setSizeFull();
			tbl_MatCompTable.setSelectable(true);
			tbl_MatCompTable.addContainerProperty(captions.getString("CAP.TBL.3"), Integer.class, null);
			tbl_MatCompTable.addContainerProperty(captions.getString("CAP.TBL.1"), AbstractComponent.class, null);
			tbl_MatCompTable.addContainerProperty(captions.getString("CAP.TBL.2"), AbstractComponent.class, null);
			tbl_MatCompTable.setColumnHeaderMode(Table.COLUMN_HEADER_MODE_HIDDEN);
			/* add the fixed TextFields to the Table */
			TextField txt_Caption = new TextField();
			TextField txt_UniqueId = new TextField();
			txt_Caption.setHeight(abstractComponentHeight);
			txt_UniqueId.setHeight(abstractComponentHeight);
			txt_Caption.setWidth(abstractComponentWidht);
			txt_UniqueId.setWidth(abstractComponentWidht);
			txt_Caption.setDescription(captions.getString("CAP.DESC.15"));
			txt_Caption.addFocusListener(new Lsn_FocusListner_TextField_HelpText(this.lbl_PropertySettingsDescription, txt_Caption));
			txt_UniqueId.setEnabled(false);
			txt_Caption.setId(captions.getString("CAP.LBL.2"));
			txt_UniqueId.setId(captions.getString("CAP.LBL.1"));
			/* add the TextBox to the Table */
			tbl_MatCompTable.addItem(new Object[]{tbl_MatCompTable.size() + 1, new Label(captions.getString("CAP.LBL.2")),txt_Caption},null);
			tbl_MatCompTable.addItem(new Object[]{tbl_MatCompTable.size() + 1, new Label(captions.getString("CAP.LBL.1")),txt_UniqueId},null);
			/* add the TextFields to the list */
			lst_globalMaterialComponentObject.add(txt_UniqueId);
			lst_globalMaterialComponentObject.add(txt_Caption);
			/* map contains the different values added / materialComponentObject */
			Map<String, TextField> mapValues = new TreeMap<String, TextField>();
			/* set the PropertGroups */
			if(null != materialComponentObjectDTO.getMaterialComponentDTO().getPropertyGroupDTO()){
				for(PropertyDTO prDTO : mapProperty.values()){
					if(null != prDTO.getPropertyGroupDTO()){
						if(prDTO.getPropertyGroupDTO().getUniqueId().equals(materialComponentObjectDTO.getMaterialComponentDTO().getPropertyGroupDTO().getUniqueId())){
							/* create TextField */
							TextField txt_Value = new TextField();
							txt_Value.setDescription(prDTO.getDescription());
							txt_Value.setWidth(this.abstractComponentWidht);
							txt_Value.setHeight(this.abstractComponentHeight);
							txt_Value.addFocusListener(new Lsn_FocusListner_TextField_HelpText(this.lbl_PropertySettingsDescription, txt_Value));
							txt_Value.setId(prDTO.getUniqueId());
							tbl_MatCompTable.addItem(new Object[]{this.tbl_MaterialObject.size() + 1,new Label(prDTO.getCaption()), txt_Value},null);
							mapValues.put(prDTO.getUniqueId(),txt_Value);
						}
					}
				}
			}
			/* add  the values / materialComponentObject in the map */
			mapMaterialComponentObjectValueAdded.put(materialComponentObjectDTO.getUniqueId(), mapValues);
			/* TabSheet settings  */
			this.tbs_MaterialObject.addTab(tbl_MatCompTable);
			this.tbs_MaterialObject.getTab(tbl_MatCompTable).setIcon(Icon.iconMaterialComponentObject);
			this.tbs_MaterialObject.getTab(tbl_MatCompTable).setCaption(materialComponentObjectDTO.getMaterialComponentDTO().getCaption());
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
