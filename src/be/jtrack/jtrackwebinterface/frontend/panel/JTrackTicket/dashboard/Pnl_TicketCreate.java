package be.jtrack.jtrackwebinterface.frontend.panel.JTrackTicket.dashboard;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import main.java.info.jtrac.domain.Attachment;
import main.java.info.jtrac.domain.Status;
import main.java.info.jtrac.exception.manager.ManagerException;
import main.java.info.jtrac.mail.Email;
import main.java.info.jtrac.service.dto.ItemDTO;
import main.java.info.jtrac.service.dto.SpaceDTO;
import main.java.info.jtrac.service.dto.UserDTO;
import main.java.info.jtrac.service.manager.IManager;

import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.TextField;
import com.vaadin.ui.Upload;
import com.vaadin.ui.Window;
import com.vaadin.ui.Button.ClickEvent;

import be.jtrack.jtrackwebinterface.frontend.panel.global.L18NPanel;
import be.jtrack.jtrackwebinterface.util.AttachmentUploader;
import be.jtrack.jtrackwebinterface.util.Icon;
import be.jtrackinventory.service.dto.MaterialObjectDTO;
@SuppressWarnings("unchecked")
public class Pnl_TicketCreate extends L18NPanel{
	/**
	 * Serial version id
	 */
	private static final long serialVersionUID = -9012174891481299574L;
	/* 
	 * Instance members 
	 */
	private final String abstractComponentHeight = "25px";
	private final String abstractButtonWidht = "120px";
	private final String abstractComponentWidht = "340px";
	ApplicationContext context = new ClassPathXmlApplicationContext(new String[] {"applicationContent.xml"});
	IManager<ItemDTO> iItemManager = (IManager<ItemDTO>) context.getBean("iItemManager");
	/* GridView */
	private GridLayout grd_General;
	/* Button */
	private Button btn_Save;
	private Button btn_Cancel;
	/* TextArea */
	private TextArea txa_TicketDescription;
	/* TextField */
	private TextField txt_TicketTitle;
	/* FileUpload */
	private Upload upl_Attachment;
	/* ComboBox */
	private ComboBox cmb_AllocateTo;
	private ComboBox cmb_State;
	private ComboBox cmb_Space;
	private ComboBox cmb_MaterialObject;
	/* Label */
	private Label lbl_Title;
	private Label lbl_TicketDescription;
	private Label lbl_TicketTitle;
	private Label lbl_AllocateTo;
	private Label lbl_State;
	private Label lbl_Attachment;
	private Label lbl_MailAllocatedTo;
	private Label lbl_Space;
	private Label lbl_MaterialObject;
	/* data */
	private Map<String, Status> map_ItemStatus;
	private Map<String, SpaceDTO> map_SpaceDTO;
	private Map<String, UserDTO> map_UserDTO;
	private Map<String, MaterialObjectDTO> map_MaterialObjectDTO;
	private List<SpaceDTO> lst_SpaceDTO;
	private List<UserDTO> lst_UserDTO;
	private List<MaterialObjectDTO> lst_MaterialObjectDTO;
	/* CheckBox */
	private CheckBox chb_mailAllocatedTo;
	/* Window */
	private Window wdw_Parent;
	/**
	 * Default constructor for the Class
	 */
	public Pnl_TicketCreate(){
		init();
	}
	/**
	 * Constructor for the class
	 * @param parent as Window
	 */
	public Pnl_TicketCreate(Window parent, List<SpaceDTO> listSpaceDTO, List<UserDTO> listUserDTO, List<MaterialObjectDTO> listMaterialObjectDTO){
		this();
		this.wdw_Parent = parent;
		this.lst_SpaceDTO = listSpaceDTO;
		this.lst_UserDTO = listUserDTO;
		this.lst_MaterialObjectDTO = listMaterialObjectDTO;
		this.initSpaceComboBox(this.cmb_Space, lst_SpaceDTO);
		this.initCmbAllocatedTo(this.cmb_AllocateTo, lst_UserDTO);
		this.initCmbMaterialObject(this.cmb_MaterialObject, lst_MaterialObjectDTO);
		this.lst_MaterialObjectDTO = listMaterialObjectDTO;
	}
	/*
	 * method will init the Panel
	 */
	private void init(){
		/* data */
		this.map_ItemStatus = new HashMap<String, Status>();
		this.map_SpaceDTO = new HashMap<String, SpaceDTO>();
		this.map_UserDTO = new HashMap<String, UserDTO>();
		this.map_MaterialObjectDTO = new HashMap<String, MaterialObjectDTO>();
		/* Receiver */
		AttachmentUploader receiver = new AttachmentUploader();
		/* Upload */
		this.upl_Attachment = new Upload(null,receiver);
		this.upl_Attachment.setButtonCaption("Start Upload");
		this.upl_Attachment.addSucceededListener(receiver);
		/* CheckBox */
		this.chb_mailAllocatedTo = new CheckBox();
		this.chb_mailAllocatedTo.setImmediate(true);
		this.chb_mailAllocatedTo.setValue(true);
		/* Button */
		this.btn_Save = new Button(captions.getString("CAP.BTN.1"));
		this.btn_Cancel = new Button(captions.getString("CAP.BTN.11"));
		this.btn_Save.setWidth(abstractButtonWidht);
		this.btn_Cancel.setWidth(abstractButtonWidht);
		this.btn_Save.setHeight(abstractComponentHeight);
		this.btn_Cancel.setHeight(abstractComponentHeight);
		this.btn_Save.setIcon(Icon.iconSave);
		this.btn_Cancel.setIcon(Icon.iconCancel);
		this.btn_Save.addClickListener(new Button.ClickListener() {
			/**
			 * Serial Version iD
			 */
			private static final long serialVersionUID = -7899291233506208783L;
			@Override
			public void buttonClick(ClickEvent event) {
				ItemDTO dto = getItemDTOValues();
				try {
					/* Persist the dto */
					iItemManager.persist(dto);
					/* send notificationMail */
					if((Boolean)chb_mailAllocatedTo.getValue()){
						Email email = new Email();
						email.setEmail_To(dto.getUser_assignedTo_email());
						email.setEmail_From("noreply@jtrack.be");
						email.setEmail_Subject("New Jtrack Item - ID : " + dto.getId() + " Subject : " + dto.getSummary());
						//email.sendItemStatusMail(dto);
					}
				} catch (ManagerException e) {
					Notification.show(e.getCaption());
				}
				wdw_Parent.close();
			}
		});
		this.btn_Cancel.addClickListener(new Button.ClickListener() {
			/**
			 * Serial Version iD
			 */
			private static final long serialVersionUID = -7899291233506208783L;
			@Override
			public void buttonClick(ClickEvent event) {
				wdw_Parent.close();
			}
		});
		/* TextArea */
		this.txa_TicketDescription = new TextArea();
		this.txa_TicketDescription.setWidth(abstractComponentWidht);
		/* TextField */
		this.txt_TicketTitle = new TextField();
		this.txt_TicketTitle.setWidth(abstractComponentWidht);
		this.txt_TicketTitle.setHeight(abstractComponentHeight);
		/* ComboBox */
		this.cmb_State = new ComboBox();
		this.cmb_State.setWidth(abstractComponentWidht);
		this.cmb_State.setHeight(abstractComponentHeight);
		this.initCmbState(this.cmb_State);
		this.cmb_AllocateTo = new ComboBox();
		this.cmb_AllocateTo.setWidth(abstractComponentWidht);
		this.cmb_AllocateTo.setHeight(abstractComponentHeight);
		this.cmb_Space = new ComboBox();
		this.cmb_Space.setWidth(abstractComponentWidht);
		this.cmb_Space.setHeight(abstractComponentHeight);
		this.cmb_MaterialObject = new ComboBox();
		this.cmb_MaterialObject.setWidth(abstractComponentWidht);
		this.cmb_MaterialObject.setHeight(abstractComponentHeight);
		/* Label */
		this.lbl_Title = new Label(captions.getString("CAP.LBL.26"));
		this.lbl_Title.setStyleName("header");
		this.lbl_TicketTitle = new Label(captions.getString("CAP.LBL.27"));
		this.lbl_TicketDescription = new Label(captions.getString("CAP.LBL.28"));
		this.lbl_AllocateTo = new Label(captions.getString("CAP.LBL.29"));
		this.lbl_State = new Label(captions.getString("CAP.LBL.30"));
		this.lbl_Attachment = new Label(captions.getString("CAP.LBL.31"));
		this.lbl_MailAllocatedTo = new Label(captions.getString("CAP.LBL.32"));
		this.lbl_Space = new Label(captions.getString("CAP.LBL.33"));
		this.lbl_MaterialObject = new Label(captions.getString("CAP.LBL.33"));
		this.lbl_Title.setHeight(abstractComponentHeight);
		this.lbl_TicketTitle.setHeight(abstractComponentHeight);
		this.lbl_TicketDescription.setHeight(abstractComponentHeight);
		this.lbl_AllocateTo.setHeight(abstractComponentHeight);
		this.lbl_State.setHeight(abstractComponentHeight);
		this.lbl_Attachment.setHeight(abstractComponentHeight);
		this.lbl_MailAllocatedTo.setHeight(abstractComponentHeight);
		this.lbl_Space.setHeight(abstractComponentHeight);
		this.lbl_MaterialObject.setHeight(abstractComponentHeight);
		/* GridLayout */
		this.grd_General = new GridLayout(2,10);
		this.grd_General.addComponent(this.lbl_Title,0,0);
		this.grd_General.addComponent(this.lbl_TicketTitle,0,1);
		this.grd_General.addComponent(this.lbl_TicketDescription,0,2);
		this.grd_General.addComponent(this.lbl_AllocateTo,0,3);
		this.grd_General.addComponent(this.lbl_State,0,4);
		this.grd_General.addComponent(this.lbl_Space,0,5);
		this.grd_General.addComponent(this.lbl_MaterialObject,0,6);
		this.grd_General.addComponent(this.lbl_Attachment,0,7);
		this.grd_General.addComponent(this.lbl_MailAllocatedTo,0,8);
		this.grd_General.addComponent(this.txt_TicketTitle,1,1);
		this.grd_General.addComponent(this.txa_TicketDescription,1,2);
		this.grd_General.addComponent(this.cmb_AllocateTo,1,3);
		this.grd_General.addComponent(this.cmb_State,1,4);
		this.grd_General.addComponent(this.cmb_Space,1,5);
		this.grd_General.addComponent(this.cmb_MaterialObject,1,6);
		this.grd_General.addComponent(this.upl_Attachment,1,7);
		this.grd_General.addComponent(this.chb_mailAllocatedTo,1,8);
		this.grd_General.addComponent(this.btn_Cancel,0,9);
		this.grd_General.addComponent(this.btn_Save,1,9);
		this.grd_General.setComponentAlignment(this.btn_Save, Alignment.BOTTOM_RIGHT);
		this.grd_General.setComponentAlignment(this.btn_Cancel, Alignment.BOTTOM_LEFT);
		this.grd_General.setHeight("100%");
		this.grd_General.setWidth("100%");
		this.grd_General.setSizeFull();
		this.grd_General.setMargin(new MarginInfo(true, true, true, true));
		this.setContent(this.grd_General);
	}
	/*
	 * Method will initialize the ComboBox For the AllocatedTo
	 * @param combo as ComboBox
	 */
	private void initCmbAllocatedTo(ComboBox comboBox, List<UserDTO> list){
		if(null != comboBox){
			for(UserDTO user : list){
				comboBox.addItem(user.getName());
				this.map_UserDTO.put(user.getName(), user);
			}
		}
	}
	/*
	 * Method will init the ComboBox For the Status
	 * @param combo
	 */
	private void initCmbState(ComboBox combo){
		if(null != combo){
			// Clean the ComboBox
			for(Status s : Status.values()){
				combo.addItem(captions.getString(s.getCaption()));
				this.map_ItemStatus.put(captions.getString(s.getCaption()), s);
			}
		}
	}
	/* 
	 * Method will initialize the ComboBox with the applications
	 */
	private void initSpaceComboBox(ComboBox comboBox, List<SpaceDTO> list){
		if(null != comboBox){
			for(SpaceDTO sp : list){
				comboBox.addItem(sp.getName());
				this.map_SpaceDTO.put(sp.getName(), sp);
			}
		}
	}
	/*
	 * Method will initialize the comboBox whit the materialObjects
	 */
	private void initCmbMaterialObject(ComboBox comboBox, List<MaterialObjectDTO> list) {
		for(MaterialObjectDTO mO : list){
			comboBox.addItem(mO.getCaption());
			this.map_MaterialObjectDTO.put(mO.getCaption(), mO);
		}
	}
	/*
	 * get the values out the fields and return a ItemDTO
	 * @return itemDTO as ItemDTO
	 */
	private ItemDTO getItemDTOValues(){
		/* get the values out the Panel */
		String title = this.txt_TicketTitle.getValue();
		String description = this.txa_TicketDescription.getValue();
		Status status = getSelectedvalueStatus(cmb_State, map_ItemStatus);
		SpaceDTO spaceDTO = getSelectedSpaceDTO(cmb_Space, map_SpaceDTO);
		UserDTO userDTO = getSelectedUserDTO(cmb_AllocateTo, map_UserDTO);
		MaterialObjectDTO materialObjectDTO = getSelectedMaterialObjectDTO(cmb_MaterialObject, map_MaterialObjectDTO);
		Attachment at = new Attachment();
		/* Set the values to the ItemDTO */
		ItemDTO dto = new ItemDTO();
		dto.setEditReason(title);
		dto.setDetail(description);
		if(null != status){
			dto.setStatus(status);
		}
		if(null != spaceDTO){
			dto.setSpace_Id(spaceDTO.getId());
		}
		if(null != userDTO){
			dto.setUser_assignedTo_Id(userDTO.getId());
			dto.setUser_assignedTo_email(userDTO.getEmail());
		}
		dto.setSequenceNum("1");
		dto.setTimeStamp(Calendar.getInstance().getTime());
		return dto;
	}
	private UserDTO getSelectedUserDTO(ComboBox comboBox, Map<String, UserDTO> map) {	
		return map.get(comboBox.getValue());
	}
	/*
	 * Method will get the selected value ComboBox Status
	 */
	private Status getSelectedvalueStatus(ComboBox comboBox, Map<String,Status> map){
		return map.get(comboBox.getValue());
	}
	/*
	 * Method will get the selected value out the comboBox
	 * @param comboBox as ComboBox
	 * @param map as Map
	 * @return found object as SpaceDTO
	 */
	private SpaceDTO getSelectedSpaceDTO(ComboBox comboBox, Map<String, SpaceDTO> map){
		return map.get(comboBox.getValue());
	}
	/*
	 * Method will get the selected value out the comboBox
	 * @param comboBox as ComboBox
	 * @param map as Map
	 * @return found object as MaterialObjectDTO
	 */
	private MaterialObjectDTO getSelectedMaterialObjectDTO(ComboBox comboBox, Map<String, MaterialObjectDTO> map){
		return map.get(comboBox.getValue());
	}
}
