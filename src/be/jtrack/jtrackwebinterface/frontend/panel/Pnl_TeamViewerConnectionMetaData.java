package be.jtrack.jtrackwebinterface.frontend.panel;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import be.jtrack.jtrackwebinterface.frontend.panel.global.L18NPanel;
import be.jtrack.jtrackwebinterface.util.ResourceUtil;
import be.jtrackteamviewerplugin.business.enu.FieldDataType;
import be.jtrackteamviewerplugin.service.dto.CustomFieldDTO;
import be.jtrackteamviewerplugin.service.dto.TeamViewerConnectionDTO;
import be.jtrackteamviewerplugin.service.dto.TeamViewerConnectionMetaDataDTO;
import be.jtrackteamviewerplugin.service.manager.CustomFieldManager;
import be.jtrackteamviewerplugin.service.manager.TeamViewerConnectionManager;
import be.jtrackteamviewerplugin.service.manager.TeamViewerConnectionMetaDataManager;

import com.vaadin.data.Item;
import com.vaadin.event.Action;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Table;
import com.vaadin.ui.Button.ClickEvent;

public class Pnl_TeamViewerConnectionMetaData extends L18NPanel {
	/**
	 * Serial Version ID
	 */
	private static final long serialVersionUID = 2492996471252109419L;
	private final Action act_tbl_TeamViewerConnectionMetaData_AddMetaData = new Action(super.captions.getString("PNL_TeamViewerConnectionAddInfo_act_tbl_TeamViewerConnectionMetaData_AddMetaData"),ResourceUtil.getFileResource("/VAADIN/themes/icons/png/add.png"));
	private final Action act_tbl_teamViewerConnection_DeleteMetaData = new Action(super.captions.getString("PNL_TeamViewerConnectionAddInfo_act_tbl_TeamViewerConnectionMetaData_DeleteMetaData"),ResourceUtil.getFileResource("/VAADIN/themes/icons/png/delete.png"));
	private final Action[] ACTIONS = new Action[] {act_tbl_TeamViewerConnectionMetaData_AddMetaData, act_tbl_teamViewerConnection_DeleteMetaData};
	ApplicationContext context = new ClassPathXmlApplicationContext(new String[] {"applicationContent.xml"});
	TeamViewerConnectionMetaDataManager teamViewerConnectionMetaDataManager = (TeamViewerConnectionMetaDataManager) context.getBean("teamViewerConnectionMetaDataManager");
	TeamViewerConnectionManager teamViewerConnectionManager = (TeamViewerConnectionManager) context.getBean("teamViewerConnectionManager");
	CustomFieldManager customFieldManager = (CustomFieldManager) context.getBean("customFieldManager");
	/* Instance members */
	/* Table */
	private Table tbl_TeamViewerConnectionMetaData;
	/* GridLayout */
	private GridLayout grl_TeamViewerConnectionMetaData;
	/* Button */
	private Button btn_edit;
	/* List<CustomFieldDTO> */
	private List<CustomFieldDTO> listCustomFieldDTO = null;
	/* TeamViewerConnectionDTO */
	private TeamViewerConnectionDTO teamViewerConnectionDTO;
	/* List of added TeamViewerConnectionMetaData */
	List<Item> listAddedItemIds = new ArrayList<Item>();
	/**
	 * Default constructor for the Class
	 */
	public Pnl_TeamViewerConnectionMetaData(){
		this.init();
	}
	/**
	 * Default constructor for the Class
	 */
	public Pnl_TeamViewerConnectionMetaData(TeamViewerConnectionDTO teamViewerConnectionDTO){
		this.teamViewerConnectionDTO = teamViewerConnectionDTO;
		this.init();
	}
	/**
	 * @return the tbl_TeamViewerConnectionMetaData
	 */
	public Table getTable() {
		return tbl_TeamViewerConnectionMetaData;
	}

	/*
	 * Initialize the Panel
	 */
	@SuppressWarnings("deprecation")
	private void init(){
		/* Initialize CustomField */
		this.listCustomFieldDTO = this.customFieldManager.findAll();
		/*
		 * initialize Button
		 */
		this.btn_edit = new Button(super.captions.getString("PNL_TeamViewerConnectionAddInfo_btn_edit"));
		this.btn_edit.setWidth("120px");
		this.btn_edit.addListener(new Button.ClickListener() {
			/**
			 * Serial version id
			 */
			private static final long serialVersionUID = -679773861729961653L;
			@Override
			public void buttonClick(final ClickEvent event) {
				if(false == tbl_TeamViewerConnectionMetaData.isEditable()){
					// Iterate over the item identifiers of the table.
					for (@SuppressWarnings("rawtypes")
					Iterator i = tbl_TeamViewerConnectionMetaData.getItemIds().iterator(); i.hasNext();) {
					    // Get the current item identifier, which is an integer.
					    int iid = (Integer) i.next();
					    // Now get the actual item from the table.
					    Item item = tbl_TeamViewerConnectionMetaData.getItem(iid);
					    // And now we can get to the actual CheckBox object.
					    ComboBox cmb = (ComboBox)(item.getItemProperty(captions.getString("PNL_TeamViewerConnectionAddInfo_tbl_TeamViewerConnectionMetaData_Name")).getValue());
					    cmb.setEnabled(true);
					}
				}else{
					/* check the added MetaData with the regex */
					for(Item itemId : listAddedItemIds){
						try{
							ComboBox cmb_NewValue = (ComboBox) itemId.getItemProperty(captions.getString("PNL_TeamViewerConnectionAddInfo_tbl_TeamViewerConnectionMetaData_Name")).getValue();
							String string_NewValue = (String) itemId.getItemProperty(captions.getString("PNL_TeamViewerConnectionAddInfo_tbl_TeamViewerConnectionMetaData_value")).getValue();
							/* get the selected value in the */
							CustomFieldDTO foundCustomFieldDTO = null;
							for(CustomFieldDTO dto : listCustomFieldDTO){
								 if(dto.getValue().equals(cmb_NewValue.getValue())){
									 foundCustomFieldDTO = dto;		
								 }
							}
							/* Check if the ComboBox is filled */
							if(null != foundCustomFieldDTO){
								/* Create the TeamViewerConnectionMetaDataDTO */
								TeamViewerConnectionMetaDataDTO tvCMd = new TeamViewerConnectionMetaDataDTO();
								if(null != teamViewerConnectionDTO){
									tvCMd.setTeamViewerConnectionID(teamViewerConnectionDTO.getId());
									tvCMd.setTeamViewerConnectionWindowsUser(teamViewerConnectionDTO.getWindowsUser());
								}		
								tvCMd.setCustomFieldDTO(foundCustomFieldDTO);				
								tvCMd.setValue(string_NewValue);
								tvCMd.setUniqueID("{" +UUID.randomUUID().toString() +"}");
								/* get the FieldDataType */
								FieldDataType fieldDataType = FieldDataType.string;
								switch (Integer.parseInt(foundCustomFieldDTO.getType())){
									case 20: 	fieldDataType = FieldDataType.number;
												break;
									case 21: 	fieldDataType = FieldDataType.decimal;
												break;
									case 22:	fieldDataType = FieldDataType.date;
												break;
									case 23:	fieldDataType = FieldDataType.string;
												break;
									case 24: 	fieldDataType = FieldDataType.email;
												break;
									case 25:	fieldDataType = FieldDataType.state;
												break;
									default: 	fieldDataType = FieldDataType.string;
												break;
								}
								/* check the value depending on the Regex defined as a FieldType */
								boolean checkRegex = false;
								if(fieldDataType != FieldDataType.string){
									Pattern p = Pattern.compile(fieldDataType.getRegex());
									Matcher m = p.matcher(string_NewValue);
									checkRegex = m.matches();
								}else{
									/* because the content didn't matter don't check on regex */
									checkRegex = true;
								}
								/* Persist the TeamViewerConnectionDTO */
								/* Save the data in the database after Regex check */
								if(checkRegex == true){
									teamViewerConnectionMetaDataManager.persist(tvCMd);
									/* Delete the different objects out the table */
									tbl_TeamViewerConnectionMetaData.removeAllItems();
									/* Get the TeamViewerConnectionDTO out the database */
									teamViewerConnectionDTO = teamViewerConnectionManager.findByID(teamViewerConnectionDTO.getId());
									/* Set the TeamViewerConnectionDTO out the database */
									SetTableContent();
								}else{
									Notification.show(captions.getString("PNL_TeamViewerConnectionMetaData_Save_Regex_Error"));
								}
							}
						}catch(Exception eXp){
						}
					}
					// Iterate over the item identifiers of the table.
					for (@SuppressWarnings("rawtypes")
					Iterator i = tbl_TeamViewerConnectionMetaData.getItemIds().iterator(); i.hasNext();) {
					    // Get the current item identifier, which is an integer.
					    int iid = (Integer) i.next();
					    // Now get the actual item from the table.
					    Item item = tbl_TeamViewerConnectionMetaData.getItem(iid);
					    // And now we can get to the actual CheckBox object.
					    ComboBox cmb = (ComboBox)(item.getItemProperty(captions.getString("PNL_TeamViewerConnectionAddInfo_tbl_TeamViewerConnectionMetaData_Name")).getValue());
					    cmb.setEnabled(false);
					}
				}
				/* set the Caption of the button */
				tbl_TeamViewerConnectionMetaData.setEditable(!tbl_TeamViewerConnectionMetaData.isEditable());
				if(true == tbl_TeamViewerConnectionMetaData.isEditable()){
					btn_edit.setCaption(captions.getString("PNL_TeamViewerConnectionAddInfo_btn_save"));
				}else{
					btn_edit.setCaption(captions.getString("PNL_TeamViewerConnectionAddInfo_btn_edit"));
				}
			}
		});
		/*
		 * Create Table 
		 */
		this.tbl_TeamViewerConnectionMetaData=new Table();
		this.tbl_TeamViewerConnectionMetaData.setEditable(false);
		this.tbl_TeamViewerConnectionMetaData.setStyleName("iso3166");
		this.tbl_TeamViewerConnectionMetaData.setWidth("100%");
		this.tbl_TeamViewerConnectionMetaData.setHeight("500px");
		this.tbl_TeamViewerConnectionMetaData.setSelectable(true);
		this.tbl_TeamViewerConnectionMetaData.setMultiSelect(false);
		this.tbl_TeamViewerConnectionMetaData.setImmediate(true);
		this.tbl_TeamViewerConnectionMetaData.setColumnReorderingAllowed(true);
		this.tbl_TeamViewerConnectionMetaData.setColumnCollapsingAllowed(true);
		this.setTableFields();
		this.SetTableContent();
		this.tbl_TeamViewerConnectionMetaData.addActionHandler(new com.vaadin.event.Action.Handler() {
			/**
			 * Serial version ID
			 */
			private static final long serialVersionUID = -182453070045320632L;
			@SuppressWarnings("unchecked")
			@Override
			public void handleAction(Action action, Object sender, Object target) {
				if(null != action.getCaption()){
					/* Action to perform when AddTicket is selected */
					if(captions.getString("PNL_TeamViewerConnectionAddInfo_act_tbl_TeamViewerConnectionMetaData_AddMetaData") == action.getCaption()){
						String i = UUID.randomUUID().toString();
						Item item = tbl_TeamViewerConnectionMetaData.getContainerDataSource().addItem(i);
						ComboBox cmb_add = CustomFieldComboBox();
						cmb_add.setEnabled(true);
						tbl_TeamViewerConnectionMetaData.getContainerDataSource().getContainerProperty(i, captions.getString("PNL_TeamViewerConnectionAddInfo_tbl_TeamViewerConnectionMetaData_Id")).setValue("");
						tbl_TeamViewerConnectionMetaData.getContainerDataSource().getContainerProperty(i, captions.getString("PNL_TeamViewerConnectionAddInfo_tbl_TeamViewerConnectionMetaData_Name")).setValue(cmb_add);
						tbl_TeamViewerConnectionMetaData.getContainerDataSource().getContainerProperty(i, captions.getString("PNL_TeamViewerConnectionAddInfo_tbl_TeamViewerConnectionMetaData_value")).setValue("");
						item.getItemProperty(captions.getString("PNL_TeamViewerConnectionAddInfo_tbl_TeamViewerConnectionMetaData_Id")).setReadOnly(true);
						tbl_TeamViewerConnectionMetaData.setEditable(true);
						/* add item that is added in the table */
						listAddedItemIds.add(item);
						btn_edit.setCaption(captions.getString("PNL_TeamViewerConnectionAddInfo_btn_save"));
					}
					if(captions.getString("PNL_TeamViewerConnectionAddInfo_act_tbl_TeamViewerConnectionMetaData_DeleteMetaData") == action.getCaption()){
						/* replace the panel */
						Object itemId = null;
						try{
							itemId = tbl_TeamViewerConnectionMetaData.getValue();
						}catch(Exception eXp){
							Notification.show("Please select a TeamViewerConnection Meta Data line.");
						}
						if(null != itemId){
							String teamViewerConnectionMetaDataId =  tbl_TeamViewerConnectionMetaData.getContainerProperty(itemId, captions.getString("PNL_TeamViewerConnectionAddInfo_tbl_TeamViewerConnectionMetaData_Id")).getValue().toString();
							TeamViewerConnectionMetaDataDTO teamViewerConnectionMetaDataDTO = teamViewerConnectionMetaDataManager.findByID(teamViewerConnectionMetaDataId);
							boolean removed = teamViewerConnectionMetaDataManager.remove(teamViewerConnectionMetaDataDTO);
							if(false == removed){
								Notification.show(captions.getString("PNL_TeamViewerConnectionMetaData_Delete_Error"));
							}else{
								/* Delete the different objects out the table */
								tbl_TeamViewerConnectionMetaData.removeAllItems();
								/* Get the TeamViewerConnectionDTO out the database */
								teamViewerConnectionDTO = teamViewerConnectionManager.findByID(teamViewerConnectionDTO.getId());
								/* Set the TeamViewerConnectionDTO out the database */
								SetTableContent();
							}
						}
					}
				}
			}
			@Override
			public Action[] getActions(Object target, Object sender) {
				return ACTIONS;
			}
		});
		/* 
		 * GridLayout 
		 */
		this.grl_TeamViewerConnectionMetaData = new GridLayout(5,2);
		this.grl_TeamViewerConnectionMetaData.addComponent(this.tbl_TeamViewerConnectionMetaData,0,0,4,0);
		this.grl_TeamViewerConnectionMetaData.addComponent(this.btn_edit,0,1);
		this.grl_TeamViewerConnectionMetaData.setComponentAlignment(btn_edit,Alignment.BOTTOM_LEFT);
		this.grl_TeamViewerConnectionMetaData.setSizeFull();
		this.setContent(grl_TeamViewerConnectionMetaData);
	}
	/*
	 * Method will set the values into the table
	 * @param container
	 */
	@SuppressWarnings("unchecked")
	private void SetTableContent(){
		try{
			for(TeamViewerConnectionMetaDataDTO dto : this.teamViewerConnectionDTO.getTeamViewerConnectionMetaDataList()){
				Object id = this.tbl_TeamViewerConnectionMetaData.addItem();
				this.tbl_TeamViewerConnectionMetaData.getContainerProperty(id,super.captions.getString("PNL_TeamViewerConnectionAddInfo_tbl_TeamViewerConnectionMetaData_Id")).setValue(dto.getId());
				/* create the ComboBox of CustomFields */
				ComboBox cmb_New = this.CustomFieldComboBox();
				/* Set the default value */
				cmb_New.select(dto.getCustomFieldDTO().getValue());
				this.tbl_TeamViewerConnectionMetaData.getContainerProperty(id,super.captions.getString("PNL_TeamViewerConnectionAddInfo_tbl_TeamViewerConnectionMetaData_Name")).setValue(cmb_New);
				this.tbl_TeamViewerConnectionMetaData.getContainerProperty(id,super.captions.getString("PNL_TeamViewerConnectionAddInfo_tbl_TeamViewerConnectionMetaData_value")).setValue(dto.getValue());
				/* Set Id ReadOnly */
				this.tbl_TeamViewerConnectionMetaData.getContainerProperty(id, captions.getString("PNL_TeamViewerConnectionAddInfo_tbl_TeamViewerConnectionMetaData_Id")).setReadOnly(true);
				this.tbl_TeamViewerConnectionMetaData.getContainerProperty(id, captions.getString("PNL_TeamViewerConnectionAddInfo_tbl_TeamViewerConnectionMetaData_Name")).setReadOnly(true);
			}
		}catch(Exception eXp){
			
		}
	}
	/*
	 * Method will create a Container for the table
	 * @return container as Container 
	 */
	private void setTableFields(){
		this.tbl_TeamViewerConnectionMetaData.addContainerProperty(super.captions.getString("PNL_TeamViewerConnectionAddInfo_tbl_TeamViewerConnectionMetaData_Id"), String.class, "");
		this.tbl_TeamViewerConnectionMetaData.addContainerProperty(super.captions.getString("PNL_TeamViewerConnectionAddInfo_tbl_TeamViewerConnectionMetaData_Name"), ComboBox.class, new ComboBox());
		this.tbl_TeamViewerConnectionMetaData.addContainerProperty(super.captions.getString("PNL_TeamViewerConnectionAddInfo_tbl_TeamViewerConnectionMetaData_value"), String.class, "");
		this.tbl_TeamViewerConnectionMetaData.setColumnExpandRatio(super.captions.getString("PNL_TeamViewerConnectionAddInfo_tbl_TeamViewerConnectionMetaData_Id"), 0.1f);
		this.tbl_TeamViewerConnectionMetaData.setColumnExpandRatio(super.captions.getString("PNL_TeamViewerConnectionAddInfo_tbl_TeamViewerConnectionMetaData_Name"), 0.35f);
		this.tbl_TeamViewerConnectionMetaData.setColumnExpandRatio(super.captions.getString("PNL_TeamViewerConnectionAddInfo_tbl_TeamViewerConnectionMetaData_value"), 1f);
	}
	/*
	 * Method will create the comboBox with the CustomField available in it
	 * @return CustomField overview as ComboBox
	 */
	private ComboBox CustomFieldComboBox(){
		/* Create the ComboBox */
		ComboBox cmb_CustomField = new ComboBox();
		cmb_CustomField.setNullSelectionAllowed(false);
		cmb_CustomField.setSizeFull();
		cmb_CustomField.setImmediate(true);
		cmb_CustomField.setEnabled(false);
		/* add the data to the ComboBox */
		Object item = null;
		for(CustomFieldDTO dto : listCustomFieldDTO){
			item = cmb_CustomField.addItem(dto.getValue());
		}
		cmb_CustomField.setValue(item);
		return cmb_CustomField;
	}
}
