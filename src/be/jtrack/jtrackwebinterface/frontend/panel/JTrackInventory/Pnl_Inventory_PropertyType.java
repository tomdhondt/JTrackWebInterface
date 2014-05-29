package be.jtrack.jtrackwebinterface.frontend.panel.JTrackInventory;

import java.util.Iterator;
import java.util.List;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.vaadin.data.Container;
import com.vaadin.data.Item;
import com.vaadin.event.Action;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Table;
import com.vaadin.ui.Window;
import com.vaadin.ui.Button.ClickEvent;

import be.jtrack.jtrackwebinterface.frontend.panel.global.L18NPanel;
import be.jtrack.jtrackwebinterface.util.ResourceUtil;
import be.jtrackinventory.exception.manager.ManagerException;
import be.jtrackinventory.service.dto.PropertyTypeDTO;
import be.jtrackinventory.service.manager.IManager;

@SuppressWarnings("unchecked")
public class Pnl_Inventory_PropertyType extends L18NPanel{
	/**
	 * Serial version ID
	 */
	private static final long serialVersionUID = 8941084249378339389L;
	/*
	 * instance members
	 */
	ApplicationContext context = new ClassPathXmlApplicationContext(new String[] {"applicationContent.xml"});
	IManager<PropertyTypeDTO> iManagerPropertyType = (IManager<PropertyTypeDTO>) context.getBean("iManagerPropertyType");
	private final Action act_tbl_Inventory_PropertyType_Add = new Action(super.captions.getString("Pnl_Inventory_PropertyType_act_tbl_Inventory_PropertyType_Add"),ResourceUtil.getFileResource("/VAADIN/themes/icons/png/add.png"));
	private final Action act_tbl_Inventory_PropertyType_Delete = new Action(super.captions.getString("Pnl_Inventory_PropertyType_act_tbl_Inventory_PropertyType_Delete"),ResourceUtil.getFileResource("/VAADIN/themes/icons/png/delete.png"));
	private final Action[] ACTIONS = new Action[] {act_tbl_Inventory_PropertyType_Add,act_tbl_Inventory_PropertyType_Delete};
	/* Label */
	private Button btn_Close;
	private Button btn_Edit;
	/* GridLayout */
	private GridLayout grl_Inventory_PropertyType;
	/* Table */
	private Table tbl_Inventory_PropertyType;
	/**
	 * Default constructor for the Class
	 */
	public Pnl_Inventory_PropertyType(){
		this.init();
	}
	/*
	 * Method will initialize the Panel
	 */
	private  void init(){
		/* Button */
		this.btn_Close = new Button(captions.getString("PNL_btn_Close"));
		this.btn_Close.setWidth("120px");
		this.btn_Close.addClickListener(new Button.ClickListener() {
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
		this.btn_Edit = new Button(captions.getString("PNL_btn_edit"));
		this.btn_Edit.setWidth("120px");
		this.btn_Edit.addClickListener(new Button.ClickListener() {
			/**
			 * Serial Version iD
			 */
			private static final long serialVersionUID = -7899291233506208783L;
			@Override
			public void buttonClick(ClickEvent event) {
				/* Save the data in the database */
				if(tbl_Inventory_PropertyType.isEditable()){
					/* set the changes in the container */
					Container container = tbl_Inventory_PropertyType.getContainerDataSource();
					for(Object object : container.getItemIds()){
						/*
						 * Create the PropertyFieldDTO
						 */
						Item item = container.getItem(object);
						/* Persist or update the object in the database */
						PropertyTypeDTO dto = new PropertyTypeDTO();
						String id = (String) item.getItemProperty(captions.getString("PNL_Inventory_PropertyType_tbl_Inventory_PropertyType_uniqueId")).getValue();
						dto.setCaption((String) item.getItemProperty(captions.getString("PNL_Inventory_PropertyType_tbl_Inventory_PropertyType_caption")).getValue());
						dto.setDescription((String) item.getItemProperty(captions.getString("PNL_Inventory_PropertyType_tbl_Inventory_PropertyType_description")).getValue());
						dto.setRegex((String) item.getItemProperty(captions.getString("PNL_Inventory_PropertyType_tbl_Inventory_PropertyType_regex")).getValue());
						dto.setActive(true);
						if("" != id){
							/* update the object in the database */
							try {
								dto.setUniqueId(id);
								iManagerPropertyType.update(dto);
							} catch (ManagerException mXe) {
								Notification.show(mXe.getCaption());
							}
						}else{
							/* persist the object in the database */
							try {
								iManagerPropertyType.persist(dto);
							} catch (ManagerException mXe) {
								Notification.show(mXe.getCaption());
							}
						}
					}
					/* update the table content */
					Table tbl_new  = createTable();
					grl_Inventory_PropertyType.replaceComponent(tbl_Inventory_PropertyType, tbl_new);
					tbl_Inventory_PropertyType = tbl_new;
					tbl_Inventory_PropertyType.setEditable(true);
				}
				tbl_Inventory_PropertyType.setEditable(!tbl_Inventory_PropertyType.isEditable());               
				btn_Edit.setCaption((tbl_Inventory_PropertyType.isEditable() ? captions.getString("PNL_btn_save") : captions.getString("PNL_btn_edit")));
			}
		});
		/* Table */
		this.tbl_Inventory_PropertyType = this.createTable();
		/* GridLayout */
		this.grl_Inventory_PropertyType = new GridLayout(4,2);
		this.grl_Inventory_PropertyType.addComponent(this.tbl_Inventory_PropertyType,0,0,3,0);
		this.grl_Inventory_PropertyType.addComponent(this.btn_Edit,0,1);
		this.grl_Inventory_PropertyType.addComponent(this.btn_Close,3,1);
		this.grl_Inventory_PropertyType.setComponentAlignment(this.btn_Edit,Alignment.BOTTOM_LEFT);
		this.grl_Inventory_PropertyType.setComponentAlignment(this.btn_Close,Alignment.BOTTOM_RIGHT);
		this.grl_Inventory_PropertyType.setSizeFull();
		this.grl_Inventory_PropertyType.setMargin(new MarginInfo(true, true, false, true));
		this.setContent(this.grl_Inventory_PropertyType);
	}
	/* create the container fields */
	private void createTableFields(Table table){
		table.addContainerProperty(captions.getString("PNL_Inventory_PropertyType_tbl_Inventory_PropertyType_uniqueId"), String.class,"");
		table.addContainerProperty(captions.getString("PNL_Inventory_PropertyType_tbl_Inventory_PropertyType_caption"),String.class,"");
		table.addContainerProperty(captions.getString("PNL_Inventory_PropertyType_tbl_Inventory_PropertyType_description"),String.class,"");
		table.addContainerProperty(captions.getString("PNL_Inventory_PropertyType_tbl_Inventory_PropertyType_regex"),String.class,"");
	}
	/*
	 * Fill the table with data
	 */
	private void setTableContent(Table table){
		/* find the options in the database */
		try {
			List<PropertyTypeDTO> listDTO = this.iManagerPropertyType.findAll();			
			try{
			/* add the data to the table */
			int counter = 0;
				for(PropertyTypeDTO dto : listDTO){
					Object itemId = table.addItem(new Object[] {dto.getUniqueId(),dto.getCaption(),dto.getDescription(),dto.getRegex()}, counter++);
					/* add the entry to disable changes at the container fields */
					if(null != itemId){
						table.getContainerProperty(itemId, captions.getString("PNL_Inventory_PropertyType_tbl_Inventory_PropertyType_uniqueId")).setReadOnly(true);
					}
				}
			}catch(Exception eXe){
				Notification.show(captions.getString("PNL_Inventory_PropertyType_tbl_Inventory_PropertyType_error"));	
			}
		} catch (ManagerException mXe) {
			Notification.show(mXe.getCaption());
		}
	}
	/* Method will create a table */
	private Table createTable(){
		Table table = new Table(super.captions.getString("Pnl_Inventory_PropertyType_tbl_Inventory_PropertyType"));
		/* create the Table Fields */
		this.createTableFields(table);
		/* create the Table data */
		this.setTableContent(table);
		/* set the spects of the table */
		table.setStyleName("iso3166");
		table.setWidth("100%");
		table.setHeight("500px");
		table.setSelectable(true);
		table.setEditable(false);
		table.setMultiSelect(false);
		table.setImmediate(true);
		table.setColumnReorderingAllowed(true);
		table.setColumnCollapsingAllowed(true);
		table.addActionHandler(new com.vaadin.event.Action.Handler() {
			/**
			 * Serial Version ID
			 */
			private static final long serialVersionUID = 6521175119038776137L;
			@Override
			public void handleAction(Action action, Object sender, Object target) {
				if(null != action.getCaption()){
					/* Action to perform when AddTicket is selected */
					if(captions.getString("Pnl_Inventory_PropertyType_act_tbl_Inventory_PropertyType_Add") == action.getCaption()){
						Container container = tbl_Inventory_PropertyType.getContainerDataSource();
						Object itemId = tbl_Inventory_PropertyType.addItem(new Object[] {"","","",""}, (1+ container.size()));
						/* add the entry to disable changes at the container fields */
						if(null != itemId){
							tbl_Inventory_PropertyType.getContainerProperty(itemId, captions.getString("PNL_Inventory_PropertyType_tbl_Inventory_PropertyType_uniqueId")).setReadOnly(true);
						}
						tbl_Inventory_PropertyType.setEditable(true);
						btn_Edit.setCaption(captions.getString("PNL_btn_save"));
					}
					/* Remove the CustomFieldDTO from the database */
					if(captions.getString("Pnl_Inventory_PropertyType_act_tbl_Inventory_PropertyType_Delete") == action.getCaption()){
						Object id = tbl_Inventory_PropertyType.getValue();
						if(null != id){
			                /*
							 * Create the CustomFieldDTO
							 */
			                Item item = tbl_Inventory_PropertyType.getItem(id);
							/*
							 * Remove the object out the database
							 */
			                String uniqueId = (String) item.getItemProperty(captions.getString("PNL_Inventory_PropertyType_tbl_Inventory_PropertyType_uniqueId")).getValue();
			                boolean success = false;
							try {
								success = iManagerPropertyType.remove(uniqueId);
							} catch (ManagerException mXe) {
								Notification.show(captions.getString("Pnl_Inventory_PropertyType_act_tbl_Inventory_PropertyType_Delete_NotSuccess") + " : " + mXe.toString());
							}
							if(success){
								/* update the table content */
								Table tbl_new  = createTable();
								grl_Inventory_PropertyType.replaceComponent(tbl_Inventory_PropertyType, tbl_new);
								tbl_Inventory_PropertyType = tbl_new;
								/* Notify the user the update was a success */
								Notification.show(captions.getString("Pnl_Inventory_PropertyType_act_tbl_Inventory_PropertyType_Delete_Success"));
							}
						}else{
							Notification.show(captions.getString("Pnl_Inventory_PropertyType_act_tbl_Inventory_PropertyType_Delete_SelectValue"));
						}
					}
				}
			}
			@Override
			public Action[] getActions(Object target, Object sender) {
				return ACTIONS;
			}
		});
		return table;
	}
}
