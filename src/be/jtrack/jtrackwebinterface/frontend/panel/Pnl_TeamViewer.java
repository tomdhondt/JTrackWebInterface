package be.jtrack.jtrackwebinterface.frontend.panel;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import be.jtrack.jtrackwebinterface.frontend.panel.global.L18NPanel;
import be.jtrackteamviewerplugin.service.dto.TeamViewerDTO;
import be.jtrackteamviewerplugin.service.manager.TeamViewerManager;

import com.vaadin.ui.Alignment;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.TreeTable;

public class Pnl_TeamViewer extends L18NPanel {
	/**
	 * Serial version id
	 */
	private static final long serialVersionUID = -4920705059567382778L;
	/*
	 * instance members
	 */
	ApplicationContext context = new ClassPathXmlApplicationContext(new String[] {"applicationContent.xml"});
	TeamViewerManager teamViewerManager = (TeamViewerManager)context.getBean("teamViewerManager");
	/* TeamViewerListDTO */
	private List<TeamViewerDTO> listTeamViewerDTO = new ArrayList<TeamViewerDTO>();
	/* Tree */
	private TreeTable tre_TeamViewer;
	/* GridLayout */
	private GridLayout grd_TeamViewer;
	/**
	 * Default constructor
	 */
	public Pnl_TeamViewer(){
		this.init();
	}
	/**
	 * Constructor for the Class
	 */
	public Pnl_TeamViewer(List<TeamViewerDTO> listTeamViewerDTO){
		this.listTeamViewerDTO = listTeamViewerDTO;
		this.init();
	}
	/**
	 * Method will initialize the panel
	 */
	private void init(){
		/*
		 * Tree
		 */
		this.tre_TeamViewer = new TreeTable();
		this.tre_TeamViewer.setSelectable(true);        
		this.tre_TeamViewer.setMultiSelect(false);
		this.tre_TeamViewer.setColumnFooter(super.captions.getString("PNL_teamviewer_tre_TeamViewer_Id"), super.captions.getString("PNL_teamviewer_tre_TeamViewer_Counter") + Integer.toString(this.tre_TeamViewer.getContainerDataSource().size()/2));		
		this.tre_TeamViewer.setFooterVisible(true);
		this.tre_TeamViewer.setWidth("100%");
		this.tre_TeamViewer.setHeight("525px");
		/* set the container properties */
		this.tre_TeamViewer.addContainerProperty(captions.getString("PNL_teamviewer_tre_TeamViewer_FieldName"), String.class, null);
		this.tre_TeamViewer.addContainerProperty(captions.getString("PNL_teamviewer_tre_TeamViewer_Value"), String.class, null);
		/* set the content of the table */
//		List<TeamViewerDTO> listTeamViewer =  teamViewerManager.findAll();
		int itemIdCounter = 0;
		for(TeamViewerDTO dto : listTeamViewerDTO){
			Object levelOne = this.tre_TeamViewer.addItem(new Object[] {super.captions.getString("PNL_teamviewer_tre_TeamViewer_Address"), dto.getAddress()}, itemIdCounter++);
			Object levelTwo_A = this.tre_TeamViewer.addItem(new Object[] {super.captions.getString("PNL_teamviewer_tre_TeamViewer_PartnerName"), dto.getPartnerDTO().getName()}, itemIdCounter++);
			Object levelTwo_B = this.tre_TeamViewer.addItem(new Object[] {super.captions.getString("PNL_teamviewer_tre_TeamViewer_Notes"), dto.getPartnerDTO().getNotes()}, itemIdCounter++);
			Object levelTwo_C = this.tre_TeamViewer.addItem(new Object[] {super.captions.getString("PNL_teamviewer_tre_TeamViewer_BasicCharge"), dto.getPartnerDTO().getBasicCharge()}, itemIdCounter++);
			Object levelTwo_D = this.tre_TeamViewer.addItem(new Object[] {super.captions.getString("PNL_teamviewer_tre_TeamViewer_BasicChargeTime"), dto.getPartnerDTO().getBasicChargeTime()}, itemIdCounter++);
			Object levelTwo_E= this.tre_TeamViewer.addItem(new Object[] {super.captions.getString("PNL_teamviewer_tre_TeamViewer_HourlyRate"), dto.getPartnerDTO().getHourlyRate()}, itemIdCounter++);
			this.tre_TeamViewer.setParent(levelTwo_A,levelOne);
			this.tre_TeamViewer.setParent(levelTwo_B,levelOne);
			this.tre_TeamViewer.setParent(levelTwo_C,levelOne);
			this.tre_TeamViewer.setParent(levelTwo_D,levelOne);
			this.tre_TeamViewer.setParent(levelTwo_E,levelOne);
		}
		/*
		 * GridLayout
		 */
		this.grd_TeamViewer = new GridLayout(2,2);
		this.grd_TeamViewer.addComponent(this.tre_TeamViewer,0,0,1,1);
		this.grd_TeamViewer.setComponentAlignment(this.tre_TeamViewer,Alignment.MIDDLE_CENTER);
		this.grd_TeamViewer.setWidth("100%");
		this.grd_TeamViewer.setSizeFull();
		this.setContent(this.grd_TeamViewer);
	}
}
