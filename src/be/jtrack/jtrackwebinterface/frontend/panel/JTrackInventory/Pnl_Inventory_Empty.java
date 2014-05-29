package be.jtrack.jtrackwebinterface.frontend.panel.JTrackInventory;

import com.vaadin.ui.Alignment;
import com.vaadin.ui.Embedded;
import com.vaadin.ui.GridLayout;

import be.jtrack.jtrackwebinterface.frontend.panel.global.L18NPanel;
import be.jtrack.jtrackwebinterface.util.Icon;

public class Pnl_Inventory_Empty extends L18NPanel {
	/**
	 * Serial Version ID
	 */
	private static final long serialVersionUID = -7376213813263085507L;
	/*
	 * Instance members
	 */
	/* GridLayout */
	private GridLayout grd_General;
	/**/
	private Embedded emb_Image;
	/**
	 * Default constructor for the Class
	 */
	public Pnl_Inventory_Empty(){
		init();
	}
	private void init(){
		/* Embedded */
		this.emb_Image = new Embedded("",Icon.iconJtrackInventory);
		/* GridLayout */
		this.grd_General = new GridLayout(1,1);
		this.grd_General.addComponent(this.emb_Image,0,0);
		this.grd_General.setComponentAlignment(this.emb_Image, Alignment.MIDDLE_CENTER);
		this.grd_General.setMargin(false);
//		this.grd_General.setSizeFull();
		this.grd_General.setHeight("100%");
		this.grd_General.setWidth("100%");
		this.setContent(this.grd_General);
		this.setSizeFull();
	}

}
