package be.jtrack.jtrackwebinterface.frontend.panel.JTrackTicket.dashboard;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import main.java.info.jtrac.exception.manager.ManagerException;
import main.java.info.jtrac.service.dto.ItemDTO;
import main.java.info.jtrac.service.manager.IManager;

import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.Component;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.Label;

import be.jtrack.jtrackwebinterface.frontend.panel.global.L18NPanel;
@SuppressWarnings("unchecked")
public class Pnl_TicketDashBoard extends L18NPanel{
	/**
	 * Serial version ID
	 */
	private static final long serialVersionUID = -8724280411126668124L;
	ApplicationContext context = new ClassPathXmlApplicationContext(new String[] {"applicationContent.xml"});
	IManager<ItemDTO> iItemManager = (IManager<ItemDTO>) context.getBean("iItemManager");
	/* GridView */
	private GridLayout grd_General;
	/* Label */
	private Label lbl_Title;
	/* Data */
	private List<ItemDTO> lst_ItemDTO;
	private List<Component> lst_AddedComponent;
	/**
	 * Default constructor for the Class
	 */
	public Pnl_TicketDashBoard(){
		init();
	}
	private void init(){
		/* Label */
		this.lbl_Title = new Label(captions.getString("CAP.LBL.36"));
		this.lbl_Title.setStyleName("header");
		/* amount of tickets */
		this.lst_ItemDTO = new ArrayList<ItemDTO>();
		this.lst_AddedComponent = new ArrayList<Component>();
		try {
			this.lst_ItemDTO = iItemManager.findAll(2);
		} catch (ManagerException e) {
		}
		int itemCount = lst_ItemDTO.size();
		/* GridLayout */
		this.grd_General = new GridLayout(1,(itemCount + 1));
		this.grd_General.addComponent(lbl_Title,0,0);
		this.lst_AddedComponent = addItemComponents(this.lst_ItemDTO,lst_AddedComponent);
		this.grd_General.setHeight("100%");
		this.grd_General.setWidth("100%");
		this.grd_General.setSizeFull();
		this.grd_General.setMargin(new MarginInfo(false, false, false, false));
		this.setContent(this.grd_General);
	}
	/* 
	 * Method will add the components to the GridView
	 */
	public List<Component> addItemComponents(List<ItemDTO> listItemDTO, List<Component> listComponent){
		/* remove components */
		for(Component comp : lst_AddedComponent){
			this.grd_General.removeComponent(comp);
		}
		/* add components */
		List<Component> lst_addedComponent = new ArrayList<Component>();
		int counter = 1;
		for(ItemDTO item : listItemDTO){
			Component comp = new Pnl_TicketDashBoardItemDetail(item);
			this.grd_General.addComponent(comp,0,counter);
			lst_addedComponent.add(comp);
			counter++;
		}
		return lst_addedComponent;
	}
		/* 
	 * Method will add the components to the GridView
	 */
	public void replaceItemComponent(ItemDTO itemDTO, Component component){
		/* remove components */
		if(null != component && null != itemDTO){
			Component comp = new Pnl_TicketDashBoardItemDetail(itemDTO);
			this.grd_General.removeComponent(component);
			this.grd_General.addComponent(comp);
			this.lst_AddedComponent.remove(component);
			this.lst_AddedComponent.add(comp);
		}
	}
}
