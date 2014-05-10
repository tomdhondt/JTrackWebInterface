package be.jtrack.jtrackwebinterface.frontend.panel.JTrackTicket.manage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import main.java.info.jtrac.domain.UserRole;
import main.java.info.jtrac.exception.manager.ManagerException;
import main.java.info.jtrac.service.dto.UserDTO;
import main.java.info.jtrac.service.manager.IManager;
import main.java.info.jtrac.util.MappingUtil;

import com.vaadin.data.Item;
import com.vaadin.event.Action;
import com.vaadin.event.ItemClickEvent;
import com.vaadin.event.ItemClickEvent.ItemClickListener;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.Table;
import com.vaadin.ui.TextField;
import com.vaadin.ui.Button.ClickEvent;

import be.jtrack.jtrackwebinterface.frontend.panel.L18NPanel;
import be.jtrack.jtrackwebinterface.util.Icon;

@SuppressWarnings("unchecked")
public class Pnl_ManageUsers extends L18NPanel{
	/**
	 * Serial version ID
	 */
	private static final long serialVersionUID = -6794336572545604923L;
	/* size */
	private final String abstractComponentHeight = "25px";
	private final String abstractButtonWidht = "120px";
	private final String abstractComponentWidht = "340px";
	private List<UserDTO> lst_UserDTO;
	private Map<String,Object> map_AddedUserDTO;
	private Map<Item, UserRole> map_AddedUserRole;
	/* 
	 * instance members
	 */
	ApplicationContext context = new ClassPathXmlApplicationContext(new String[] {"applicationContent.xml"});	
	IManager<UserDTO> iUserManager = (IManager<UserDTO>) context.getBean("iUserManager");
	/* Action */
	private final Action act_tbl_Users_Add = new Action(super.captions.getString("CAP.ACT.1"),Icon.iconNew);
	private final Action act_tbl_Users_Refresh = new Action(super.captions.getString("CAP.ACT.2"),Icon.iconRedo);
	private final Action[] ACTIONS = new Action[] {act_tbl_Users_Add, act_tbl_Users_Refresh};
	/* Label */
	private Label lbl_Title;
	private Label lbl_LoginName;
	private Label lbl_CompleteName;
	private Label lbl_Email;
	private Label lbl_Password;
	private Label lbl_ConfirmPassWord;
	private Label lbl_UserRole;
	/* TextField */
	private TextField txt_LoginName;
	private TextField txt_CompleteName;
	private TextField txt_Email;
	/* PasswordField */
	private PasswordField ptxt_Password;
	private PasswordField ptxt_ConfirmPassWord;
	/* CheckBox */
	/* Button */
	private Button btn_Save;
	/* Table */
	private Table tbl_Users;
	/* GridLayout */
	private GridLayout grd_General;
	/* ComboBox */
	private ComboBox cmb_UserRole;
	/**
	 * Default Constructor for the Class
	 */
	public Pnl_ManageUsers(){
		this.init();
	}
	/* initialize the Panel */
	private void init(){
		/* init data */
		this.initData();
		/* ComboBox */
		this.cmb_UserRole = new ComboBox();
		this.cmb_UserRole.setImmediate(true);
		this.cmb_UserRole.setWidth(this.abstractComponentWidht);
		this.cmb_UserRole.setHeight(this.abstractComponentHeight);
		for(UserRole u : UserRole.getValues()){
			Item item = this.cmb_UserRole.addItem(u.getRole());
			map_AddedUserRole.put(item, u);
		}
		this.cmb_UserRole.setValue(UserRole.ADMIN.getRole());
		/* Table */
		this.tbl_Users = this.create_tbl_Users();
		/* Button */
		this.btn_Save = new Button(captions.getString("CAP.BTN.1"));
		this.btn_Save.setWidth(this.abstractButtonWidht);
		this.btn_Save.setIcon(Icon.iconSave);
		this.btn_Save.addClickListener(new Button.ClickListener() {
			/**
			 * Serial Version iD
			 */
			private static final long serialVersionUID = -4238213653800686242L;
			@Override
			public void buttonClick(ClickEvent event) {
				if(comparePassWordField()){
					/* create new UserDTO */
					UserDTO userDTO = new UserDTO();
					userDTO.setLoginName(txt_LoginName.getValue());
					userDTO.setName(txt_CompleteName.getValue());
					userDTO.setEmail(txt_Email.getValue());
					userDTO.setPassword(ptxt_Password.getValue());
					/* get userRole */
					UserRole u = map_AddedUserRole.get(cmb_UserRole.getItem(cmb_UserRole.getValue()));
					userDTO.setUserRole(u.getRole());
					/* persist the user */
					try {
						/* persist the user */
						iUserManager.persist(userDTO);
						lst_UserDTO.add(userDTO);
						/* update the table */
						update_tbl_Users();
					} catch (ManagerException e) {
						if(null != e.getMessage()){
							Notification.show(captions.getString(e.getMessage()));
						}else{
							Notification.show(captions.getString("CAP.DESC.19"));
						}
					}
				}else{
					Notification.show(captions.getString("CAP.DESC.18"));
					ptxt_ConfirmPassWord.setValue("");
					ptxt_Password.setValue("");
				}
			}
		});
		/* Label */
		this.lbl_Title = new Label("");
		this.lbl_LoginName = new Label(captions.getString("CAP.LBL.8"));
		this.lbl_CompleteName = new Label(captions.getString("CAP.LBL.9"));
		this.lbl_Email = new Label(captions.getString("CAP.LBL.10"));
		this.lbl_Password = new Label(captions.getString("CAP.LBL.12"));
		this.lbl_ConfirmPassWord = new Label(captions.getString("CAP.LBL.13"));
		this.lbl_UserRole = new Label(captions.getString("CAP.LBL.14"));
		/* TextField */
		this.txt_LoginName = new TextField();
		this.txt_Email = new TextField();
		this.txt_CompleteName = new TextField();
		this.txt_LoginName.setHeight(abstractComponentHeight);
		this.txt_Email.setHeight(abstractComponentHeight);
		this.txt_CompleteName.setHeight(abstractComponentHeight);
		this.txt_LoginName.setWidth(abstractComponentWidht);
		this.txt_Email.setWidth(abstractComponentWidht);
		this.txt_CompleteName.setWidth(abstractComponentWidht);
		/* PasswordField */
		this.ptxt_Password = new PasswordField();
		this.ptxt_ConfirmPassWord = new PasswordField();
		this.ptxt_Password.setHeight(abstractComponentHeight);
		this.ptxt_ConfirmPassWord.setHeight(abstractComponentHeight);
		this.ptxt_Password.setWidth(abstractComponentWidht);
		this.ptxt_ConfirmPassWord.setWidth(abstractComponentWidht);
		/* GridLayout */
		this.grd_General = new GridLayout(2,10);
		this.grd_General.addComponent(this.lbl_Title,0,0,1,0);
		this.grd_General.addComponent(this.lbl_LoginName,0,1,0,1);
		this.grd_General.addComponent(this.txt_LoginName,1,1,1,1);
		this.grd_General.addComponent(this.lbl_CompleteName,0,2,0,2);
		this.grd_General.addComponent(this.txt_CompleteName,1,2,1,2);
		this.grd_General.addComponent(this.lbl_Email,0,3,0,3);
		this.grd_General.addComponent(this.txt_Email,1,3,1,3);
		this.grd_General.addComponent(this.lbl_Password,0,5,0,5);
		this.grd_General.addComponent(this.ptxt_Password,1,5,1,5);
		this.grd_General.addComponent(this.lbl_ConfirmPassWord,0,6,0,6);
		this.grd_General.addComponent(this.ptxt_ConfirmPassWord,1,6,1,6);
		this.grd_General.addComponent(this.lbl_UserRole,0,7,0,7);
		this.grd_General.addComponent(this.cmb_UserRole,1,7,1,7);
		this.grd_General.addComponent(this.btn_Save,1,8,1,8);
		this.grd_General.addComponent(this.tbl_Users,0,9,1,9);
		this.grd_General.setComponentAlignment(btn_Save, Alignment.BOTTOM_RIGHT);
		this.grd_General.setSizeFull();
		this.grd_General.setMargin(new MarginInfo(true, true, false, true));
		this.setContent(this.grd_General);
	}
	/* 
	 * Method will clear the value out of the different field defined 
	 */
	private void resetFields(){
		/* TextField */
		this.txt_LoginName.setValue("");
		this.txt_CompleteName.setValue("");
		this.txt_Email.setValue("");
		/* PasswordField */
		this.ptxt_Password.setValue("");
		this.ptxt_ConfirmPassWord.setValue("");
		this.cmb_UserRole.setValue(UserRole.ADMIN.getRole());
	}
	/*
	 * Method will initialize the table
	 */
	private void init_tbl_Users(Table table){
		int counter = 0;
		for(UserDTO u : this.lst_UserDTO){
			Object object = table.addItem(new Object[]{MappingUtil.mapLongToString(counter++),u.getLoginName(), u.getName(),u.getEmail(), u.getUserRole()}, null);
			this.map_AddedUserDTO.put(u.getLoginName(), object);
		}
	}
	/*
	 * Method will compare the PasswordField and the confirmed PasswordField
	 */
	private boolean comparePassWordField(){
		return this.ptxt_ConfirmPassWord.getValue().equals(this.ptxt_Password.getValue());
	}
	/*
	 * Method will initiate the list that are needed to visualize the data 
	 */
	private void initData(){
		try {
			this.lst_UserDTO = this.iUserManager.findAll();
		} catch (ManagerException e) {
			this.lst_UserDTO = new ArrayList<UserDTO>();
		}
		this.map_AddedUserDTO = new HashMap<String,Object>();
		this.map_AddedUserRole = new HashMap<Item, UserRole>();
	}
	/*
	 * Method will update the table content
	 */
	private void update_tbl_Users(){
		/* update the table content */
		Table tbl_new  = create_tbl_Users();
		grd_General.replaceComponent(tbl_Users, tbl_new);
		tbl_Users = tbl_new;
		tbl_Users.setEditable(false);
	}
	/*
	 * Method will create a new Table for the user overview
	 */
	private Table create_tbl_Users(){
		Table table = new Table();
		table.setWidth("100%");
		table.setHeight("100%");
		table.setSelectable(true);
		table.addContainerProperty(captions.getString("CAP.TBL.3"), String.class,null);
		table.addContainerProperty(captions.getString("CAP.TBL.9"), String.class,null);
		table.addContainerProperty(captions.getString("CAP.TBL.10"), String.class,null);
		table.addContainerProperty(captions.getString("CAP.TBL.11"), String.class,null);
		table.addContainerProperty(captions.getString("CAP.TBL.13"), String.class,null);
		table.addItemClickListener(new ItemClickListener(){
			/**
			 * Serial Version ID
			 */
			private static final long serialVersionUID = 616623580204168867L;
			@Override
			public void itemClick(ItemClickEvent event){
				Object object = tbl_Users.getValue();
				if(null != object){
					/* get the object out the map */
					txt_LoginName.setValue((String) tbl_Users.getItem(object).getItemProperty(captions.getString("CAP.TBL.9")).getValue());
					txt_CompleteName.setValue((String)tbl_Users.getItem(object).getItemProperty(captions.getString("CAP.TBL.10")).getValue());
					txt_Email.setValue((String)tbl_Users.getItem(object).getItemProperty(captions.getString("CAP.TBL.11")).getValue());
					cmb_UserRole.setValue((String)tbl_Users.getItem(object).getItemProperty(captions.getString("CAP.TBL.13")).getValue());
					ptxt_ConfirmPassWord.setValue("xxxxxxxxxx");
					ptxt_Password.setValue("xxxxxxxxxx");
					ptxt_ConfirmPassWord.setValue(ptxt_Password.getValue());
				}
			}
		});
		table.addActionHandler(new com.vaadin.event.Action.Handler() {
			/**
			 * Serial Version ID
			 */
			private static final long serialVersionUID = 1L;
			@Override
			public void handleAction(Action action, Object sender, Object target) {
				if(null != action.getCaption()){
					/* Create a new User */
					if(captions.getString("CAP.ACT.1") == action.getCaption()){
						/* reset the content of the fields */
						resetFields();
					}
					/* Refresh the Table */
					if(captions.getString("CAP.ACT.2") == action.getCaption()){
						update_tbl_Users();
					}
				}
			}
			@Override
			public Action[] getActions(Object target, Object sender) {
				return ACTIONS;
			}
		});
		this.init_tbl_Users(table);
		return table;
	}
}
