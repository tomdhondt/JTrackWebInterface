package be.jtrack.jtrackwebinterface.frontend.panel;

import java.util.Collection;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import be.jtrack.jtrackwebinterface.frontend.panel.global.L18NPanel;
import be.jtrackteamviewerplugin.service.dto.TeamViewerConnectionDTO;
import be.jtrackteamviewerplugin.service.manager.TeamViewerConnectionManager;

import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Window;

public class Pnl_OkCancel extends L18NPanel {
	/**
	 * Serial version ID
	 */
	private static final long serialVersionUID = 3189089513961855418L;
	/* instance members */
	private static ApplicationContext context = new ClassPathXmlApplicationContext(new String[] {"applicationContent.xml"});
	private static TeamViewerConnectionManager teamViewerConnectionManager = (TeamViewerConnectionManager) context.getBean("teamViewerConnectionManager");
	/* Button */
	private Button btn_OK;
	private Button btn_Cancel;
	/* Label */
	private Label lbl_Message;
	/* GridLayout */
	private GridLayout grd_Layout;
	private TeamViewerConnectionDTO teamViewerConnectionDTO;
	/**
	 * Default Constructor for the Class
	 */
	public Pnl_OkCancel(TeamViewerConnectionDTO teamViewerConnectionDTO){
		this.teamViewerConnectionDTO = teamViewerConnectionDTO;
		this.init();
	}
	private void init(){
		/* initialize Button */
		this.btn_Cancel = new Button(super.captions.getString("PNL_OkCancel_btn_Cancel"));
		this.btn_Cancel.setWidth("80px");
		this.btn_Cancel.addClickListener(new Button.ClickListener() {
			/**
			 * Serial version ID
			 */
			private static final long serialVersionUID = 4388679493016699247L;
			@Override
			public void buttonClick(ClickEvent event) {
				Collection<Window> windList = getUI().getWindows();
				for(Window win : windList){
					getUI().removeWindow(win);
				}
			}
		});
		this.btn_OK = new Button(super.captions.getString("PNL_OkCancel_btn_OK"));
		this.btn_OK.setWidth("80px");
		this.btn_OK.addClickListener(new Button.ClickListener() {
			/**
			 * Serial version ID
			 */
			private static final long serialVersionUID = 7815771276391224175L;
			@Override
			public void buttonClick(ClickEvent event) {
				boolean success = teamViewerConnectionManager.remove(teamViewerConnectionDTO);
				if(success){
					Notification.show(captions.getString("Pnl_OkCancel_Delete_Object"));
				}
				Collection<Window> windList = getUI().getWindows();
				for(Window win : windList){
					getUI().removeWindow(win);
				}
			}
		});
		/* initialize Label */
//		this.lbl_Message = new Label(super.captions.getString("PNL_OkCancel_lbl_Message"));
		this.lbl_Message = new Label(this.teamViewerConnectionDTO.toString());
		/*
		 * Create a gridLayout
		 */
		this.grd_Layout = new GridLayout(2,2);
		this.grd_Layout.setSpacing(true);
		this.grd_Layout.addComponent(this.lbl_Message,0,0,1,0);
		this.grd_Layout.addComponent(this.btn_OK,0,1);
		this.grd_Layout.addComponent(this.btn_Cancel,1,1);
		this.grd_Layout.setComponentAlignment(this.btn_OK,Alignment.MIDDLE_CENTER);
		this.grd_Layout.setComponentAlignment(this.btn_Cancel,Alignment.MIDDLE_CENTER);
		this.grd_Layout.setMargin(new MarginInfo(false,false,false,true));
		this.grd_Layout.setSizeFull();
		this.setContent(this.grd_Layout);
	}
}
