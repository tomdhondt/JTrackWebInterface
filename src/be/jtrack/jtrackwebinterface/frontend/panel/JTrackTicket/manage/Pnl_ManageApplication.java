package be.jtrack.jtrackwebinterface.frontend.panel.JTrackTicket.manage;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import main.java.info.jtrac.exception.manager.ManagerException;
import main.java.info.jtrac.service.dto.SpaceDTO;
import main.java.info.jtrac.service.manager.IManager;

import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.Button;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.Window;

import be.jtrack.jtrackwebinterface.frontend.panel.global.L18NPanel;
import be.jtrack.jtrackwebinterface.util.Icon;

@SuppressWarnings("unchecked")
public class Pnl_ManageApplication extends L18NPanel{
	/**
	 * Serial version ID
	 */
	private static final long serialVersionUID = 203587219333323242L;
	/*
	 * Instance members
	 */
	ApplicationContext context = new ClassPathXmlApplicationContext(new String[] {"applicationContent.xml"});	
	IManager<SpaceDTO> iSpaceManager = (IManager<SpaceDTO>) context.getBean("iSpaceManager");
	/* instance members */
	private final String abstractComponentHeight = "25px";
	private final String abstractButtonWidht = "120px";
	/* Button */
	private Button btn_Next;
	/* GridLayout */
	private GridLayout grd_General;
	/* data */
	List<SpaceDTO> lst_SpaceDTO;
	/* Window */
	private Window win_BaseWindow;
	/**
	 * Default constructor for the Class
	 */
	public Pnl_ManageApplication(Window window){
		this.win_BaseWindow = window;
		init();
	}
	private void init(){
		/* Data */
		this.initData();
		/* Button */
		this.btn_Next = new Button(captions.getString("CAP.BTN.6"));
		this.btn_Next.setHeight(this.abstractComponentHeight);
		this.btn_Next.setWidth(this.abstractButtonWidht);
		this.btn_Next.setIcon(Icon.iconArrowRight);
		/* GridLayout */
		this.grd_General = new GridLayout(1,1);
		this.grd_General.addComponent(new Pnl_ApplicationOverview(this.lst_SpaceDTO,this.win_BaseWindow),0,0);
		this.grd_General.setHeight("100%");
		this.grd_General.setWidth("100%");
		this.grd_General.setSizeFull();
		this.grd_General.setMargin(new MarginInfo(false, false, false, false));
		this.setContent(this.grd_General);
	}
	/*
	 * Method will init the data needed in the frontend
	 */
	private void initData(){
		try {
			this.lst_SpaceDTO = this.iSpaceManager.findAll();
		} catch (ManagerException e) {
			this.lst_SpaceDTO = new ArrayList<SpaceDTO>();
		}
	}
}
