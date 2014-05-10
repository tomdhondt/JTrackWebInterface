package be.jtrack.jtrackwebinterface.frontend.panel;

import java.util.Iterator;
import java.util.List;

import nl.knowlogy.validation.Message;
import nl.knowlogy.validation.ValidationException;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import be.jtrack.jtrackwebinterface.util.ResourceUtil;
import be.jtrackteamviewerplugin.business.enu.FieldDataType;
import be.jtrackteamviewerplugin.service.dto.CustomFieldDTO;
import be.jtrackteamviewerplugin.service.manager.CustomFieldManager;

import com.vaadin.data.Container;
import com.vaadin.data.Item;
import com.vaadin.data.Property;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.event.Action;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Table;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Window;

public class Pnl_CustomField extends L18NPanel{
	ApplicationContext context = new ClassPathXmlApplicationContext(new String[] {"applicationContent.xml"});
	CustomFieldManager customFieldManager = (CustomFieldManager)context.getBean("customFieldManager");
	private final Action act_tbl_CustomField_Add= new Action(super.captions.getString("PNL_CustomField_act_tbl_CustomField_Add"),ResourceUtil.getFileResource("/VAADIN/themes/icons/png/add.png"));
	private final Action act_tbl_CustomField_Delete= new Action(super.captions.getString("PNL_CustomField_act_CustomField_Delete"),ResourceUtil.getFileResource("/VAADIN/themes/icons/png/delete.png"));
	private final Action[] ACTIONS = new Action[] {act_tbl_CustomField_Add,act_tbl_CustomField_Delete};
	/**
	 * Serial Version ID
	 */
	private static final long serialVersionUID = 8384054407651538181L;
	/* Instance members */
	/* GridLayout */
	private GridLayout grl_CustomField;
	/* Button */
	private Button btn_Edit;
	private Button btn_Close;
	/* Table */
	private Table tbl_CustomField;
	/**
	 * Default constructor for the Class
	 */
	public Pnl_CustomField(){
		this.init();
	}
	@SuppressWarnings("deprecation")
	private void init(){
		/* Button */
		this.btn_Close = new Button(super.captions.getString("PNL_CustomField_btn_Close"));
		this.btn_Close.setWidth("120px");
		this.btn_Close.addListener(new Button.ClickListener() {
			/**
			 * Serial Version iD
			 */
			private static final long serialVersionUID = -7899291233506208783L;
			@Override
			public void buttonClick(ClickEvent event) {
				/* Close all Windows open */
				Iterator<Window> iterator = getUI().getWindows().iterator();
				while(iterator.hasNext()){
					Window window = iterator.next();
					window.close();
				}
			}
		});
		this.btn_Edit = new Button(super.captions.getString("PNL_CustomField_btn_edit"));
		this.btn_Edit.setWidth("120px");
		this.btn_Edit.addListener(new Button.ClickListener() {
			/**
			 * Serial Version ID
			 */
			private static final long serialVersionUID = 8974564786893840860L;
			@Override
			public void buttonClick(ClickEvent event) {
				/* Save the data */
				if(tbl_CustomField.isEditable()){
					/* set the changes in the container */
					Container container = tbl_CustomField.getContainerDataSource();
					for(Object object : container.getItemIds()){
						/*
						 * Create the CustomFieldDTO
						 */
						Item item = container.getItem(object);
						/* create the DTO */
						CustomFieldDTO dto = new CustomFieldDTO();
						/* set the dto ID */
						dto.setId((String) item.getItemProperty(captions.getString("PNL_CustomField_tbl_CustomField_id")).getValue());
						/* set the dto type */
		                ComboBox cmb = (ComboBox) item.getItemProperty(captions.getString("PNL_CustomField_tbl_CustomField_Type")).getValue();
		                FieldDataType fieldDataType = getFieldDataTypeIndex(cmb.getValue().toString());
						dto.setType(Integer.toString(fieldDataType.getType()));
						/* set the dto Value */
						dto.setValue((String) item.getItemProperty(captions.getString("PNL_CustomField_tbl_CustomField_Name")).getValue());
						/* set the dto UniqueID */
						dto.setUniqueID("");
						/* set the dto PartnerID */
						dto.setPartnerID("");
						/*
						 * Save the CustomFieldDTO
						 */
						StringBuilder s = new StringBuilder();
						try{
							/* Save data to the database */
							customFieldManager.persist(dto);
							s.append(captions.getString("PNL_CustomField_Save_Changes"));
						}catch(ValidationException vEx){
							/* Error handling with JValidate */
							Message msgType = vEx.getMessages().getMessage(dto, "type");
							Message msgValue = vEx.getMessages().getMessage(dto, "value");
							if(null != msgType){
								s.append(captions.getString("type.isrequired"));
								s.append(" ");
								s.append(captions.getString("type.enterInt"));
							}
							if(null != msgValue){
								if(s.toString().length() > 0){
									s.append(" & ");
								}
								s.append(captions.getString("value.isrequired"));
							}
						}
						/* Show notification */
						Notification.show(s.toString());
					}
					// Iterate over the item identifiers of the table.
					for (Iterator i = tbl_CustomField.getItemIds().iterator(); i.hasNext();) {
					    // Get the current item identifier, which is an integer.
					    int iid = (Integer) i.next();
					    // Now get the actual item from the table.
					    Item item = tbl_CustomField.getItem(iid);
					    // And now we can get to the actual checkbox object.
					    ComboBox cmb = (ComboBox)(item.getItemProperty(captions.getString("PNL_CustomField_tbl_CustomField_Type")).getValue());
					    cmb.setEnabled(false);
					}
				}else{
					// Iterate over the item identifiers of the table.
					for (Iterator i = tbl_CustomField.getItemIds().iterator(); i.hasNext();) {
					    // Get the current item identifier, which is an integer.
					    int iid = (Integer) i.next();
					    // Now get the actual item from the table.
					    Item item = tbl_CustomField.getItem(iid);
					    // And now we can get to the actual checkbox object.
					    ComboBox cmb = (ComboBox)(item.getItemProperty(captions.getString("PNL_CustomField_tbl_CustomField_Type")).getValue());
					    cmb.setEnabled(true);
					}
				}
				tbl_CustomField.setEditable(!tbl_CustomField.isEditable());               
				btn_Edit.setCaption((tbl_CustomField.isEditable() ? captions.getString("PNL_CustomField_btn_save") : captions.getString("PNL_CustomField_btn_edit")));
			}
		});
		this.tbl_CustomField=new Table(super.captions.getString("PNL_CustomField_tbl_CustomField"));
		this.tbl_CustomField.setStyleName("iso3166");
		this.tbl_CustomField.setWidth("100%");
		this.tbl_CustomField.setHeight("500px");
		this.tbl_CustomField.setSelectable(true);
		this.tbl_CustomField.setMultiSelect(false);
		this.tbl_CustomField.setImmediate(true);
		this.tbl_CustomField.setColumnReorderingAllowed(true);
		this.tbl_CustomField.setColumnCollapsingAllowed(true);
		this.setCustomFieldTable(this.tbl_CustomField);
		this.tbl_CustomField.addActionHandler(new com.vaadin.event.Action.Handler() {
			/**
			 * Serial Version ID
			 */
			private static final long serialVersionUID = 6521175119038776137L;
			@Override
			public void handleAction(Action action, Object sender, Object target) {
				if(null != action.getCaption()){
					/* Action to perform when AddTicket is selected */
					if(captions.getString("PNL_CustomField_act_tbl_CustomField_Add") == action.getCaption()){
						Container container = tbl_CustomField.getContainerDataSource();
						Object itemId = tbl_CustomField.addItem(new Object[] {"","",createFieldDataType(),""}, (1+ container.size()));
						/* add the entry to disable changes at the container fields */
						if(null != itemId){
							tbl_CustomField.getContainerProperty(itemId, captions.getString("PNL_CustomField_tbl_CustomField_id")).setReadOnly(true);					
							tbl_CustomField.getContainerProperty(itemId, captions.getString("PNL_CustomField_tbl_CustomField_Regex")).setReadOnly(true);
						}
						tbl_CustomField.setEditable(true);
						btn_Edit.setCaption(captions.getString("PNL_CustomField_btn_save"));
					}
					/* Remove the CustomFieldDTO from the database */
					if(captions.getString("PNL_CustomField_act_CustomField_Delete") == action.getCaption()){
						Object id = tbl_CustomField.getValue();
						if(null != id){
			                /*
							 * Create the CustomFieldDTO
							 */
			                Item item = tbl_CustomField.getItem(id);
							/* create the DTO */
							CustomFieldDTO dto = new CustomFieldDTO();
							/* set the dto ID */
							dto.setId((String) item.getItemProperty(captions.getString("PNL_CustomField_tbl_CustomField_id")).getValue());
							/* set the dto type */
			                ComboBox cmb = (ComboBox) item.getItemProperty(captions.getString("PNL_CustomField_tbl_CustomField_Type")).getValue();
			                FieldDataType fieldDataType = getFieldDataTypeIndex(cmb.getValue().toString());
							dto.setType(Integer.toString(fieldDataType.getType()));
							/* set the dto Value */
							dto.setValue((String) item.getItemProperty(captions.getString("PNL_CustomField_tbl_CustomField_Name")).getValue());
							/* set the dto UniqueID */
							dto.setUniqueID("");
							/* set the dto PartnerID */
							dto.setPartnerID("");
							/*
							 * Remove the object out the database
							 */
							boolean success = customFieldManager.remove(dto);
							if(success){
								Notification.show(captions.getString("PNL_CustomField_act_CustomField_Delete_success"));
							}else{
								Notification.show(success +" - " + captions.getString("PNL_CustomField_act_CustomField_Delete_exception"));
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
		 * GroupLayout
		 */
		this.grl_CustomField = new GridLayout(4,2);
		this.grl_CustomField.addComponent(this.tbl_CustomField,0,0,3,0);
		this.grl_CustomField.addComponent(this.btn_Edit,0,1);
		this.grl_CustomField.addComponent(this.btn_Close,3,1);
		this.grl_CustomField.setComponentAlignment(this.btn_Edit,Alignment.BOTTOM_LEFT);
		this.grl_CustomField.setComponentAlignment(this.btn_Close,Alignment.BOTTOM_RIGHT);
		this.grl_CustomField.setSizeFull();
		this.grl_CustomField.setMargin(new MarginInfo(true, true, false, true));
		this.setContent(this.grl_CustomField);
	}
	/*
	 * fill the CustomFieldTable with the values
	 */
	private void setCustomFieldTable(Table table){
		if(null != table){
			List<CustomFieldDTO> listDTO = customFieldManager.findAll();
			/* create the container columns */
			this.createCustomFieldTable();
			int counter = 0;
			for(CustomFieldDTO dto : listDTO){
				/* regex */
				String regex = "";
				/* create the Combo box with FieldDataTypes */
				ComboBox cmb_box_FieldDataType = new ComboBox();
				cmb_box_FieldDataType.setNullSelectionAllowed(false);
				/* add the values out the ENUM FieldDataType */
				for(FieldDataType type : FieldDataType.values()){
					/* create the label to add to the ComboBox */
					StringBuilder output = new StringBuilder("(");
					output.append(Integer.toString(type.getType()));
					output.append(") ");
					output.append(captions.getString(type.getName()));
					/* add the label to the ComboBox */
					cmb_box_FieldDataType.addItem(output.toString());
					/* Compare the value in the dto to set the selected value in the ComboBox */
					if(dto.getType().equals(Integer.toString(type.getType()))){
						cmb_box_FieldDataType.select(output.toString());
						regex = type.getRegex();
					}
				}
				/* addlistner to the ComboBox*/
				cmb_box_FieldDataType.addValueChangeListener(new Property.ValueChangeListener() {
		            /**
					 * Serial Version id
					 */
					private static final long serialVersionUID = 1L;
					/*
					 * Method will implement the ValueChangeEvent for the ComboBox
					 */
					@SuppressWarnings("unchecked")
					public void valueChange(ValueChangeEvent event) {
						Object id = tbl_CustomField.getValue();
						if(null != id){
			                Item item = tbl_CustomField.getItem(id);
			                ComboBox cmb = (ComboBox) item.getItemProperty(captions.getString("PNL_CustomField_tbl_CustomField_Type")).getValue();
			                /* set the regex in the table */
			                FieldDataType fieldDataType = getFieldDataTypeIndex(cmb.getValue().toString());
			                item.getItemProperty(captions.getString("PNL_CustomField_tbl_CustomField_Regex")).setValue(fieldDataType.getRegex());
						}
					}
				});
				cmb_box_FieldDataType.setEnabled(false);
				/* Set the item to the table container */
				Object itemId = this.tbl_CustomField.addItem(new Object[] {dto.getId(),dto.getValue(),cmb_box_FieldDataType,regex}, counter++);
				/* add the entry to disable changes at the container fields */
				if(null != itemId){
					this.tbl_CustomField.getContainerProperty(itemId, captions.getString("PNL_CustomField_tbl_CustomField_id")).setReadOnly(true);					
					this.tbl_CustomField.getContainerProperty(itemId, captions.getString("PNL_CustomField_tbl_CustomField_Regex")).setReadOnly(true);
				}
			}
		}
	}
	/* create te container fields */
	private void createCustomFieldTable(){
		this.tbl_CustomField.addContainerProperty(captions.getString("PNL_CustomField_tbl_CustomField_id"), String.class,"");
		this.tbl_CustomField.addContainerProperty(captions.getString("PNL_CustomField_tbl_CustomField_Name"),String.class,"");
		this.tbl_CustomField.addContainerProperty(captions.getString("PNL_CustomField_tbl_CustomField_Type"),ComboBox.class,null);
		this.tbl_CustomField.addContainerProperty(captions.getString("PNL_CustomField_tbl_CustomField_Regex"),String.class,"");
	}
	/* 
	 * create the Combobox with fieldDataTypes
	 */
	private ComboBox createFieldDataType(){
		/* create the Combo box with FieldDataTypes */
		ComboBox cmb_box_FieldDataType = new ComboBox();
		cmb_box_FieldDataType.setNullSelectionAllowed(false);
		/* add the values out the ENUM FieldDataType */
		for(FieldDataType type : FieldDataType.values()){
			/* create the label to add to the ComboBox */
			StringBuilder output = new StringBuilder("(");
			output.append(Integer.toString(type.getType()));
			output.append(") ");
			output.append(captions.getString(type.getName()));
			/* add the label to the ComboBox */
			cmb_box_FieldDataType.addItem(output.toString());
			if(type.getType() == 23){
				cmb_box_FieldDataType.select(output.toString());
			}
		}
		/* set valueChangeListner */
		cmb_box_FieldDataType.setImmediate(true);
		cmb_box_FieldDataType.addValueChangeListener(new Property.ValueChangeListener() {
            /**
			 * Serial Version id
			 */
			private static final long serialVersionUID = 1L;
			/*
			 * Method will implement the ValueChangeEvent for the ComboBox
			 */
			@SuppressWarnings("unchecked")
			public void valueChange(ValueChangeEvent event) {
				Object id = tbl_CustomField.getValue();
				if(null != id){
	                Item item = tbl_CustomField.getItem(id);
	                ComboBox cmb = (ComboBox) item.getItemProperty(captions.getString("PNL_CustomField_tbl_CustomField_Type")).getValue();
	                /* set the regex in the table */
	               FieldDataType fieldDataType = getFieldDataTypeIndex(cmb.getValue().toString());
	               item.getItemProperty(captions.getString("PNL_CustomField_tbl_CustomField_Regex")).setValue(fieldDataType.getRegex());
				}
			}
		});		
		return cmb_box_FieldDataType;
	}
	/*
	 * Method will get the id out of the label of the ComboBox 
	 * @param value as String
	 * @return field as FieldDataType 
	 */
	private FieldDataType getFieldDataTypeIndex(String value){
		/* Check if the value isn't null */
		FieldDataType fieldType = FieldDataType.string;
		if(null != value){
			/* filter the type of the FieldDataType */
			try{
				int type = Integer.parseInt(value.substring(value.indexOf("(")+1, value.indexOf(")")));
				switch(type){
					case 20 : 	fieldType = FieldDataType.number;
								break;
					case 21 : 	fieldType = FieldDataType.decimal;
								break;
					case 22 : 	fieldType = FieldDataType.date;
								break;
					case 23 : 	fieldType = FieldDataType.string;
								break;
					case 24 : 	fieldType = FieldDataType.email ;
								break;
					default :	fieldType = FieldDataType.string;
								break;
				}
			}catch(NumberFormatException nFe){
				Notification.show(value.substring(value.indexOf("("), value.indexOf(")")));
			}
		}
		return fieldType;
	}
}
