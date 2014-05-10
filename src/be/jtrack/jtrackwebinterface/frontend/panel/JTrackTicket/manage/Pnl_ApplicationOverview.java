package be.jtrack.jtrackwebinterface.frontend.panel.JTrackTicket.manage;

import com.vaadin.event.ItemClickEvent;
import com.vaadin.event.ItemClickEvent.ItemClickListener;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.CheckBox;
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
		this.lbl_Title.setStyleName("header"); 
		/* Button */
		this.btn_New = new Button(captions.getString("CAP.BTN.3"));
		this.btn_New.setWidth(this.abstractButtonWidht);
		this.btn_New.setIcon(Icon.iconNew);
		this.btn_New.setHeight(this.abstractComponentHeight);
		/* Table */
		this.tbl_ApplicationOverview = createTable();
		/* GridLayout */
		this.grd_General = new GridLayout(3,4);
		this.grd_General.addComponent(this.lbl_Title,0,0,2,0);
		this.grd_General.addComponent(this.tbl_ApplicationOverview,0,1,2,2);
		this.grd_General.addComponent(this.btn_New,2,3,2,3);
		this.grd_General.setComponentAlignment(this.btn_New, Alignment.BOTTOM_RIGHT);
		this.grd_General.setHeight("100%");
		this.grd_General.setWidth("100%");
		this.grd_General.setSizeFull();
		this.grd_General.setMargin(new MarginInfo(true, true, true, true));
		this.setContent(this.grd_General);
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
		table.addContainerProperty(captions.getString("CAP.TBL.19"), Button.class,null);
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
