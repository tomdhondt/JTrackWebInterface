package be.jtrack.jtrackwebinterface.frontend.panel.JTrackTicket.manage;

import java.util.List;

import main.java.info.jtrac.service.dto.UserDTO;
import main.java.info.jtrac.util.MappingUtil;

import com.google.gwt.user.client.ui.CheckBox;
import com.vaadin.event.ItemClickEvent;
import com.vaadin.event.ItemClickEvent.ItemClickListener;
import com.vaadin.ui.Button;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Table;

import be.jtrack.jtrackwebinterface.frontend.panel.L18NPanel;
import be.jtrack.jtrackwebinterface.util.Icon;

public class Pnl_ApplicationOverview extends L18NPanel{
	/**
	 * Serial Version ID
	 */
	private static final long serialVersionUID = 8384204719450331487L;
	/* instance members */
	private final String abstractComponentHeight = "25px";
	private final String abstractButtonWidht = "120px";
	private final String abstractComponentWidht = "340px";
	/* data */
//	private List<SpaceDTO> lst_Space;
	/* Label */
	private Label lbl_Title;
	/* Table */
	private Table tbl_ApplicationOverview;
	/* GridLayout */
	private GridLayout grd_General;
	/* Button */
	private Button btn_New;
	private Button btn_Disable;
	/**
	 * Default constructor for the Class
	 */
	public Pnl_ApplicationOverview(){
		this.init();
	}
	/*
	 * Method will initialize the Panel
	 */
	private void init(){
		/* Label */
		this.lbl_Title = new Label(captions.getString("CAP.LBL.22"));
		/* Button */
		this.btn_New = new Button(captions.getString("CAP.BTN.3"));
		this.btn_Disable = new Button(captions.getString("CAP.BTN.7"));
		this.btn_New.setHeight(this.abstractComponentHeight);
		this.btn_Disable.setHeight(this.abstractComponentHeight);
		this.btn_New.setWidth(this.abstractButtonWidht);
		this.btn_Disable.setWidth(this.abstractButtonWidht);
		this.btn_New.setIcon(Icon.iconNew);
		this.btn_Disable.setIcon(Icon.iconDisable);
		/* Table */
		this.tbl_ApplicationOverview = createTable();
		/* GridLayout */
		this.grd_General = new GridLayout(3,3);
		
		
	}
	private Table createTable(){
		Table table = new Table();
		table.setWidth("100%");
		table.setHeight("100%");
		table.setSelectable(true);
		table.addContainerProperty(captions.getString("CAP.TBL.3"), String.class,null);
		table.addContainerProperty(captions.getString("CAP.TBL.14"), String.class,null);
		table.addContainerProperty(captions.getString("CAP.TBL.15"), String.class,null);
		table.addContainerProperty(captions.getString("CAP.TBL.16"), String.class,null);
		table.addContainerProperty(captions.getString("CAP.TBL.17"), String.class,null);
		table.addContainerProperty(captions.getString("CAP.TBL.18"), CheckBox.class,null);
		table.addItemClickListener(new ItemClickListener(){
			/**
			 * Serial Version ID
			 */
			private static final long serialVersionUID = 616623580204168867L;
			@Override
			public void itemClick(ItemClickEvent event){
				Object object = tbl_ApplicationOverview.getValue();
				if(null != object){
//					/* get the object out the map */
//					txt_LoginName.setValue((String) tbl_Users.getItem(object).getItemProperty(captions.getString("CAP.TBL.9")).getValue());
//					txt_CompleteName.setValue((String)tbl_Users.getItem(object).getItemProperty(captions.getString("CAP.TBL.10")).getValue());
//					txt_Email.setValue((String)tbl_Users.getItem(object).getItemProperty(captions.getString("CAP.TBL.11")).getValue());
//					cmb_UserRole.setValue((String)tbl_Users.getItem(object).getItemProperty(captions.getString("CAP.TBL.13")).getValue());
//					ptxt_ConfirmPassWord.setValue("xxxxxxxxxx");
//					ptxt_Password.setValue("xxxxxxxxxx");
//					ptxt_ConfirmPassWord.setValue(ptxt_Password.getValue());
				}
			}
		});
		this.init_tbl_ApplicationOverview(table);
		return table;
	}
	/*
	 * Method will set the values in the Table
	 */
	private void init_tbl_ApplicationOverview(Table table) {
//		int counter = 0;
//		for(UserDTO u : this.lst_Space){
//			Object object = table.addItem(new Object[]{MappingUtil.mapLongToString(counter++),u.getLoginName(), u.getName(),u.getEmail(), u.getUserRole()}, null);
//			this.map_AddedUserDTO.put(u.getLoginName(), object);
//		}
	}
}
