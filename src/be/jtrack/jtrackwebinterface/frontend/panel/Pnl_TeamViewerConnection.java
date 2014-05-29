package be.jtrack.jtrackwebinterface.frontend.panel;

import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import be.jtrack.jtrackwebinterface.frontend.panel.global.L18NPanel;
import be.jtrack.jtrackwebinterface.util.ResourceUtil;
import be.jtrackteamviewerplugin.business.enu.FieldDataType;
import be.jtrackteamviewerplugin.business.enu.TeamViewerConnectionState;
import be.jtrackteamviewerplugin.service.dto.CustomFieldDTO;
import be.jtrackteamviewerplugin.service.dto.TeamViewerConnectionDTO;
import be.jtrackteamviewerplugin.service.dto.TeamViewerConnectionMetaDataDTO;
import be.jtrackteamviewerplugin.service.manager.CustomFieldManager;
import be.jtrackteamviewerplugin.service.manager.TeamViewerConnectionManager;
import be.jtrackteamviewerplugin.service.manager.TeamViewerConnectionMetaDataManager;
import be.jtrackteamviewerplugin.util.MappingUtil;

import com.vaadin.data.Item;
import com.vaadin.event.Action;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Table;
import com.vaadin.ui.Button.ClickEvent;

public class Pnl_TeamViewerConnection extends L18NPanel {
	/**
	 * Serial Version ID
	 */
	private static final long serialVersionUID = -276446280395220850L;
	/*
	 * instance members
	 */
	ApplicationContext context = new ClassPathXmlApplicationContext(new String[] {"applicationContent.xml"});
	TeamViewerConnectionManager teamViewerConnectionManager = (TeamViewerConnectionManager)context.getBean("teamViewerConnectionManager");
	TeamViewerConnectionMetaDataManager teamViewerConnectionMetaDataManager = (TeamViewerConnectionMetaDataManager)context.getBean("teamViewerConnectionMetaDataManager");
	CustomFieldManager customFieldManager = (CustomFieldManager) context.getBean("customFieldManager");
	/* Actions */
	private final Action act_tbl_teamViewerConnection_AddTicket = new Action(super.captions.getString("PNL_home_act_tbl_teamViewerConnection_AddTicket"),ResourceUtil.getFileResource("/VAADIN/themes/icons/png/add.png"));
	private final Action act_tbl_teamViewerConnection_DeleteConnection = new Action(super.captions.getString("act_tbl_teamViewerConnection_DeleteConnection"),ResourceUtil.getFileResource("/VAADIN/themes/icons/png/delete.png"));
	private final Action act_tbl_teamViewerConnection_SetStateChecked = new Action(super.captions.getString("act_tbl_teamViewerConnection_SetStateChecked"),ResourceUtil.getFileResource("/VAADIN/themes/icons/png/checked.png"));
	private final Action act_tbl_teamViewerConnection_SetStateBooked = new Action(super.captions.getString("act_tbl_teamViewerConnection_SetStateBooked"),ResourceUtil.getFileResource("/VAADIN/themes/icons/png/book.png"));
	private final Action act_tbl_teamViewerConnection_OpenConnectionAddInfo = new Action(super.captions.getString("act_tbl_teamViewerConnection_OpenConnectionAddInfo"),ResourceUtil.getFileResource("/VAADIN/themes/icons/png/application_edit.png"));
	private final Action[] ACTIONS = new Action[] { act_tbl_teamViewerConnection_AddTicket, act_tbl_teamViewerConnection_DeleteConnection, act_tbl_teamViewerConnection_SetStateChecked, act_tbl_teamViewerConnection_SetStateBooked,act_tbl_teamViewerConnection_OpenConnectionAddInfo };
	/* Button */
	private Button btn_edit;
	/* Table */
	private Table tbl_TeamViewerConnection; 
	/* GridLayout */
	private GridLayout grl_TeamViewerConnection;
	/* TeamViewerConnection */
	private List<TeamViewerConnectionDTO> teamViewerConnectionDTOList;
	/* Date */
	private Date connectionsDate;
	/**
	 * Default constructor for the Class
	 */
	public Pnl_TeamViewerConnection(){
		this.connectionsDate = new Date();
		this.init();
	}
	public Pnl_TeamViewerConnection(Date date){
		this.connectionsDate = date;
		this.init();
	}
	/**
	 * @return the tbl_TeamViewerConnection
	 */
	public Table getTable() {
		return tbl_TeamViewerConnection;
	}
	@SuppressWarnings("deprecation")
	private void init(){
		/*
		 * create the table
		 */
		this.tbl_TeamViewerConnection = createTeamViewerConnectionTable();
		/* create the Action handlers*/
		
		this.btn_edit = new Button(super.captions.getString("PNL_home_btn_edit"));
		this.btn_edit.setWidth("120px");
		this.btn_edit.addListener(new Button.ClickListener() {
			/**
			 * Serial version ID
			 */
			private static final long serialVersionUID = 6697724379503786428L;
			@Override
			public void buttonClick(final ClickEvent event) {
				/* write the updated values to the database */
				if(true == tbl_TeamViewerConnection.isEditable()){
					// Iterate over the item identifiers of the table.
					for (Iterator i = tbl_TeamViewerConnection.getItemIds().iterator(); i.hasNext();) {
					    // Get the current item identifier, which is an integer.
					    int iid = (Integer) i.next();
					    // Now get the actual item from the table.
					    Item item = tbl_TeamViewerConnection.getItem(iid);
					    // And now we can get to the actual CheckBox object.
					    String idTvC = (String) item.getItemProperty(captions.getString("PNL_home_tbl_TeamViewerConnection_Id")).getValue();
					    for(TeamViewerConnectionDTO dtoTvC : teamViewerConnectionDTOList){
					    	if(dtoTvC.getId().equals(idTvC)){
					    		dtoTvC.setNotes((String) item.getItemProperty(captions.getString("PNL_home_tbl_TeamViewerConnection_Notes")).getValue());
					    		dtoTvC.setHour((String) item.getItemProperty(captions.getString("PNL_home_tbl_TeamViewerConnection_Hour")).getValue());
//					    		dtoTvC.setMinutes((String) item.getItemProperty(captions.getString("PNL_home_tbl_TeamViewerConnection_Minutes")).getValue());
//					    		dtoTvC.setBill((Boolean) item.getItemProperty(captions.getString("PNL_home_tbl_TeamViewerConnection_Notes")).getValue());
					    	}
					    }
					}
					/* Save the changes in the database */
					for(TeamViewerConnectionDTO dtoTvC : teamViewerConnectionDTOList){
						teamViewerConnectionManager.update(dtoTvC);
					}
//					/* refresh the table */
//					Table table = createTeamViewerConnectionTable();
//					grl_TeamViewerConnection.replaceComponent(tbl_TeamViewerConnection, table);
//					tbl_TeamViewerConnection = table;
				}
				tbl_TeamViewerConnection.setEditable(!tbl_TeamViewerConnection.isEditable());               
				btn_edit.setCaption((tbl_TeamViewerConnection.isEditable() ? captions.getString("PNL_home_btn_save") : captions.getString("PNL_home_btn_edit")));   
			}
		});  
		/* GridLayout */
		this.grl_TeamViewerConnection = new GridLayout(4,2);
		this.grl_TeamViewerConnection.setWidth("100%");
		this.grl_TeamViewerConnection.setSizeFull();
		this.grl_TeamViewerConnection.addComponent(this.tbl_TeamViewerConnection,0,0,3,0);
		this.grl_TeamViewerConnection.addComponent(this.btn_edit,0,1);
		this.grl_TeamViewerConnection.setComponentAlignment(btn_edit, Alignment.BOTTOM_LEFT);
		this.setContent(this.grl_TeamViewerConnection);
	}
	/*
	 * create the data for the table data source
	 */
	private void setContentTeamViewerConnectionTable(Table table){
		try{
			TeamViewerConnectionDTO dtoCria = new TeamViewerConnectionDTO();
			dtoCria.setStart(MappingUtil.mapDateToString(connectionsDate,null));
			dtoCria.setIsDeleted(false);
			this.teamViewerConnectionDTOList = this.teamViewerConnectionManager.findByCriteria(dtoCria);
			if(null != table && null != this.teamViewerConnectionDTOList){
				int counter = 0;
				for(TeamViewerConnectionDTO dto : this.teamViewerConnectionDTOList){
					/* Set the item to the table container */
					/* prepare the state of the object */
					TeamViewerConnectionState state = TeamViewerConnectionState.open;
					StringBuilder output = new StringBuilder();
					for(TeamViewerConnectionMetaDataDTO tcMdto :dto.getTeamViewerConnectionMetaDataList()){
						if(tcMdto.getCustomFieldDTO().getType().equals("25")){
							try{
								String StateType = tcMdto.getValue().substring(1,tcMdto.getValue().indexOf(")"));
								switch (MappingUtil.mapStringToInt(StateType)){
									case 1 : 
										state = TeamViewerConnectionState.open;
										break;
									case 2 : 
										state = TeamViewerConnectionState.checked;
										break;
									case 3 : 
										state = TeamViewerConnectionState.booked;
										break;
									default : 
										state = TeamViewerConnectionState.open;
										break;
								}
							}catch(IndexOutOfBoundsException iOx){
								
							}
						}
					}
					/* create the label in the table */
					output.append("(");
					output.append("" + state.getType());
					output.append(")");
					output.append(" ");
					output.append(captions.getString(state.getName()));
					/*
					 * add the line to the table
					 */
					Object itemId = table.addItem(new Object[] {dto.getId(),dto.getTeamViewerAddress(),dto.getStart(),dto.getHour(),dto.getMinutes(),dto.getNotes(),dto.getWindowsUser(),dto.getPrice(),dto.getBill(),output.toString()}, counter++);
					/* add the entry to disable changes at the container fields */
					if(null != itemId){
						table.getContainerProperty(itemId, captions.getString("PNL_home_tbl_TeamViewerConnection_Id")).setReadOnly(true);					
						table.getContainerProperty(itemId, captions.getString("PNL_home_tbl_TeamViewerConnection_Address")).setReadOnly(true);
						table.getContainerProperty(itemId, captions.getString("PNL_home_tbl_TeamViewerConnection_Start")).setReadOnly(true);
	//					table.getContainerProperty(itemId, captions.getString("PNL_home_tbl_TeamViewerConnection_Hour")).setReadOnly(true);
	//					table.getContainerProperty(itemId, captions.getString("PNL_home_tbl_TeamViewerConnection_Minutes")).setReadOnly(true);
	//					table.getContainerProperty(itemId, captions.getString("PNL_home_tbl_TeamViewerConnection_Notes")).setReadOnly(true);
						table.getContainerProperty(itemId, captions.getString("PNL_home_tbl_TeamViewerConnection_WindowsUser")).setReadOnly(true);
						table.getContainerProperty(itemId, captions.getString("PNL_home_tbl_TeamViewerConnection_Price")).setReadOnly(true);
						table.getContainerProperty(itemId, captions.getString("PNL_home_tbl_TeamViewerConnection_Status")).setReadOnly(true);
	//					table.getContainerProperty(itemId, captions.getString("PNL_home_tbl_TeamViewerConnection_Bill")).setReadOnly(true);
						;
					}
				}
			}else{
				Notification.show("list is null!");
			}
		}catch(Exception eXp){
			Notification.show(" " + connectionsDate);
			Notification.show(eXp.getMessage());
		}
	}
	/* create the container fields */
	private Table createTeamViewerConnectionTable(){
		Table newTable  = new Table();
		newTable.addStyleName("TeamViewerState");
		newTable.setWidth("100%");
		newTable.setHeight("500px");
		newTable.setSelectable(true);
		newTable.setMultiSelect(false);
		newTable.setImmediate(true);
		newTable.setColumnReorderingAllowed(true);
		newTable.setColumnCollapsingAllowed(true);
		/* create the columns */
		newTable.addContainerProperty(captions.getString("PNL_home_tbl_TeamViewerConnection_Id"), String.class,"");
		newTable.addContainerProperty(captions.getString("PNL_home_tbl_TeamViewerConnection_Address"), String.class,"");
		newTable.addContainerProperty(captions.getString("PNL_home_tbl_TeamViewerConnection_Start"),String.class,"");
		newTable.addContainerProperty(captions.getString("PNL_home_tbl_TeamViewerConnection_Hour"),String.class,null);
		newTable.addContainerProperty(captions.getString("PNL_home_tbl_TeamViewerConnection_Minutes"),String.class,"");
		newTable.addContainerProperty(captions.getString("PNL_home_tbl_TeamViewerConnection_Notes"),String.class,"");
		newTable.addContainerProperty(captions.getString("PNL_home_tbl_TeamViewerConnection_WindowsUser"),String.class,null);
		newTable.addContainerProperty(captions.getString("PNL_home_tbl_TeamViewerConnection_Price"),String.class,"");
		newTable.addContainerProperty(captions.getString("PNL_home_tbl_TeamViewerConnection_Bill"),Boolean.class,"");
		newTable.addContainerProperty(captions.getString("PNL_home_tbl_TeamViewerConnection_Status"),String.class,"");
		/* set the CellStyleGenerator */
		newTable.setCellStyleGenerator(new Table.CellStyleGenerator() {
		    /**
			 * Serial Version id
			 */
			private static final long serialVersionUID = 1L;
			/* method will style the row color */
			@Override
			public String getStyle(Table source, Object itemId, Object propertyId) {
				String state = (String) tbl_TeamViewerConnection.getContainerProperty(itemId,captions.getString("PNL_home_tbl_TeamViewerConnection_Status")).getValue();
		        if(state.substring(1, state.indexOf(")")).equals(MappingUtil.mapIntToString(TeamViewerConnectionState.open.getType()))){
		        	return TeamViewerConnectionState.open.getColor();
		        }else if(state.substring(1, state.indexOf(")")).equals(MappingUtil.mapIntToString(TeamViewerConnectionState.checked.getType()))){
		        	return TeamViewerConnectionState.checked.getColor();
		        }else if(state.substring(1, state.indexOf(")")).equals(MappingUtil.mapIntToString(TeamViewerConnectionState.booked.getType()))){
		        	return TeamViewerConnectionState.booked.getColor();
		        }
		        return "white";
			}
		});
		/* set the content of the table */
		this.setContentTeamViewerConnectionTable(newTable);
		this.addActionHandlerTeamViewerConnection(newTable);
		/* return the Table */
		return newTable;
	}
	/*
	 * Method will set the actionhandle for the table 
	 */
	private void addActionHandlerTeamViewerConnection(Table table){
		table.addActionHandler(new com.vaadin.event.Action.Handler() {
			/**
			 * Serial Version ID
			 */
			private static final long serialVersionUID = -2560502069796441250L;
			@Override
			public void handleAction(Action action, Object sender, Object target) {
				if(null != action.getCaption()){
					/* Action to perform when AddTicket is selected */
					if(captions.getString("PNL_home_act_tbl_teamViewerConnection_AddTicket") == action.getCaption()){
						
					}
					/* Action to perform when Delete Connection is selected */
					if(captions.getString("act_tbl_teamViewerConnection_DeleteConnection") == action.getCaption()){
						/* get the selected TeamViewerConnection */
						Object itemId = tbl_TeamViewerConnection.getValue();						
						if(null != itemId){
							String teamViewerConnectionId = tbl_TeamViewerConnection.getContainerProperty(itemId, captions.getString("PNL_home_tbl_TeamViewerConnection_Id")).getValue().toString();
							TeamViewerConnectionDTO teamViewerConnectionDTO = teamViewerConnectionManager.findByID(teamViewerConnectionId);
							teamViewerConnectionManager.remove(teamViewerConnectionDTO);
							/* reload the table */
							Table table = createTeamViewerConnectionTable();
							grl_TeamViewerConnection.replaceComponent(tbl_TeamViewerConnection,  table);
							tbl_TeamViewerConnection = table;
							Notification.show(captions.getString("Pnl_OkCancel_Delete_Object"));
						}
					}
					/* Action to perform when Set State Checked is selected */
					if(captions.getString("act_tbl_teamViewerConnection_SetStateChecked") == action.getCaption()){
						Object itemId = tbl_TeamViewerConnection.getValue();
						if(null != itemId){
							/* TeamViewerConnection - get id */
							String teamViewerConnectionId = tbl_TeamViewerConnection.getContainerProperty(itemId, captions.getString("PNL_home_tbl_TeamViewerConnection_Id")).getValue().toString();
							String teamViewerConnectionWindowsUser = tbl_TeamViewerConnection.getContainerProperty(itemId, captions.getString("PNL_home_tbl_TeamViewerConnection_WindowsUser")).getValue().toString();
							TeamViewerConnectionDTO teamViewerConnectionDTO = teamViewerConnectionManager.findByID(teamViewerConnectionId);
							/* create the customField */
							CustomFieldDTO customFieldState = new CustomFieldDTO();
							customFieldState.setUniqueID("{" + UUID.randomUUID().toString() + "}");
							customFieldState.setValue(captions.getString("Customfield.TeamViewerState"));
							customFieldState.setType(Integer.toString(FieldDataType.state.getType()));
							customFieldState.setPartnerID("0");
							/* Check CustomField TeamViewerState if it exists */
							List<CustomFieldDTO> listC = customFieldManager.findAll();
							CustomFieldDTO cFieldDTO = null;
							/* create CustomField TeamViewerState if it doesn't exists */
							if(!listC.contains(customFieldState)){
								cFieldDTO = customFieldManager.persist(customFieldState);
							}else{
								cFieldDTO = listC.get(listC.indexOf(customFieldState));
							}
							/* check TeamViewerConnectionMetaData if this already exists */
							/* Create a new TeamViewerConnectionMetaData */
							TeamViewerConnectionMetaDataDTO teamViewerConnectionMetaDateDTO = null;
							/* TeamViewerConnectionMetaDateDTO - already exists in the TeamViewerConnection */
							boolean foundState = false;
							for(TeamViewerConnectionMetaDataDTO dto : teamViewerConnectionDTO.getTeamViewerConnectionMetaDataList()){
								if(dto.getCustomFieldDTO().equals(cFieldDTO)){
									teamViewerConnectionMetaDateDTO = dto;
									foundState = true;
								}
							}
							if(false == foundState){
								teamViewerConnectionMetaDateDTO = new TeamViewerConnectionMetaDataDTO();
								teamViewerConnectionMetaDateDTO.setTeamViewerConnectionID(teamViewerConnectionId);
								teamViewerConnectionMetaDateDTO.setUniqueID("{" + UUID.randomUUID().toString()+ "}");
								teamViewerConnectionMetaDateDTO.setCustomFieldDTO(cFieldDTO);
								teamViewerConnectionMetaDateDTO.setTeamViewerConnectionWindowsUser(teamViewerConnectionWindowsUser);
							}
							/* TeamViewerConnectionMetadata - set the state checked */
							StringBuilder output = new StringBuilder();
							output.append("(");
							output.append(MappingUtil.mapIntToString(TeamViewerConnectionState.checked.getType()));
							output.append(")");
							output.append(" ");
							output.append(captions.getString("checked.teamviewerconnectionstate"));
							teamViewerConnectionMetaDateDTO.setValue(output.toString());
							/* TeamViewerConnectionMetadata - persist the Changes / new object */
							TeamViewerConnectionMetaDataDTO tvMddto = teamViewerConnectionMetaDataManager.persist(teamViewerConnectionMetaDateDTO);
							/* reload the table */
							Table table = createTeamViewerConnectionTable();
							grl_TeamViewerConnection.replaceComponent(tbl_TeamViewerConnection, table);
							tbl_TeamViewerConnection = table;
							/* show message state changed */
							Notification.show(captions.getString("PNL_home_tbl_TeamViewerConnection_Msg_StateChanged") + tvMddto.getValue());
						}
					}
					/* Action to perform when Set State Booked is selected */
					if(captions.getString("act_tbl_teamViewerConnection_SetStateBooked") == action.getCaption()){
						Object itemId = tbl_TeamViewerConnection.getValue();
						if(null != itemId){
							/* TeamViewerConnection - get id */
							String teamViewerConnectionId = tbl_TeamViewerConnection.getContainerProperty(itemId, captions.getString("PNL_home_tbl_TeamViewerConnection_Id")).getValue().toString();
							String teamViewerConnectionWindowsUser = tbl_TeamViewerConnection.getContainerProperty(itemId, captions.getString("PNL_home_tbl_TeamViewerConnection_WindowsUser")).getValue().toString();
							TeamViewerConnectionDTO teamViewerConnectionDTO = teamViewerConnectionManager.findByID(teamViewerConnectionId);
							/* create the customField */
							CustomFieldDTO customFieldState = new CustomFieldDTO();
							customFieldState.setUniqueID("{" + UUID.randomUUID().toString() + "}");
							customFieldState.setValue(captions.getString("Customfield.TeamViewerState"));
							customFieldState.setType(Integer.toString(FieldDataType.state.getType()));
							customFieldState.setPartnerID("0");
							/* Check CustomField TeamViewerState if it exists */
							List<CustomFieldDTO> listC = customFieldManager.findAll();
							CustomFieldDTO cFieldDTO = null;
							/* create CustomField TeamViewerState if it doesn't exists */
							if(!listC.contains(customFieldState)){
								cFieldDTO = customFieldManager.persist(customFieldState);
							}else{
								cFieldDTO = listC.get(listC.indexOf(customFieldState));
							}
							/* check TeamViewerConnectionMetaData if this already exists */
							/* Create a new TeamViewerConnectionMetaData */
							TeamViewerConnectionMetaDataDTO teamViewerConnectionMetaDateDTO = null;
							/* TeamViewerConnectionMetaDateDTO - already exists in the TeamViewerConnection */
							boolean foundState = false;
							for(TeamViewerConnectionMetaDataDTO dto : teamViewerConnectionDTO.getTeamViewerConnectionMetaDataList()){
								if(dto.getCustomFieldDTO().equals(cFieldDTO)){
									teamViewerConnectionMetaDateDTO = dto;
									foundState = true;
								}
							}
							if(false == foundState){
								teamViewerConnectionMetaDateDTO = new TeamViewerConnectionMetaDataDTO();
								teamViewerConnectionMetaDateDTO.setTeamViewerConnectionID(teamViewerConnectionId);
								teamViewerConnectionMetaDateDTO.setUniqueID("{" + UUID.randomUUID().toString()+ "}");
								teamViewerConnectionMetaDateDTO.setCustomFieldDTO(cFieldDTO);
								teamViewerConnectionMetaDateDTO.setTeamViewerConnectionWindowsUser(teamViewerConnectionWindowsUser);
							}
							/* TeamViewerConnectionMetadata - set the state checked */
							StringBuilder output = new StringBuilder();
							output.append("(");
							output.append(MappingUtil.mapIntToString(TeamViewerConnectionState.booked.getType()));
							output.append(")");
							output.append(" ");
							output.append(captions.getString("booked.teamviewerconnectionstate"));
							teamViewerConnectionMetaDateDTO.setValue(output.toString());
							/* TeamViewerConnectionMetadata - persist the Changes / new object */
							TeamViewerConnectionMetaDataDTO tvMddto = teamViewerConnectionMetaDataManager.persist(teamViewerConnectionMetaDateDTO);
							/* reload the table */
							Table table = createTeamViewerConnectionTable();
							grl_TeamViewerConnection.replaceComponent(tbl_TeamViewerConnection,  table);
							tbl_TeamViewerConnection = table;
							/* show message state changed */
							Notification.show(captions.getString("PNL_home_tbl_TeamViewerConnection_Msg_StateChanged") + tvMddto.getValue());
						}
					}
					/* Action to perform when OpenConnectionAddInfo is selected */
					if(captions.getString("act_tbl_teamViewerConnection_OpenConnectionAddInfo") == action.getCaption()){
						
					}
				}
			}
			@Override
			public Action[] getActions(Object target, Object sender) {
				return ACTIONS;
			}
		});
	}
	/**
	 * @return the teamViewerConnectionDTOList
	 */
	public List<TeamViewerConnectionDTO> getTeamViewerConnectionDTOList() {
		return teamViewerConnectionDTOList;
	}
	/**
	 * @return the tbl_TeamViewerConnection
	 */
	public Table getTbl_TeamViewerConnection() {
		return tbl_TeamViewerConnection;
	}
	
}
