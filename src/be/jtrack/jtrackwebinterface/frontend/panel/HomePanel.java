package be.jtrack.jtrackwebinterface.frontend.panel;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import be.jtrackteamviewerplugin.business.enu.FieldDataType;
import be.jtrackteamviewerplugin.service.dto.TeamViewerConnectionDTO;
import be.jtrackteamviewerplugin.service.dto.TeamViewerDTO;
import be.jtrackteamviewerplugin.service.manager.TeamViewerConnectionManager;
import be.jtrackteamviewerplugin.service.manager.TeamViewerConnectionMetaDataManager;
import be.jtrackteamviewerplugin.service.manager.TeamViewerManager;

import com.ibm.icu.text.SimpleDateFormat;
import com.vaadin.data.Container.Filter;
import com.vaadin.data.Container.Filterable;
import com.vaadin.data.Property;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.data.Validator;
import com.vaadin.data.util.HierarchicalContainer;
import com.vaadin.data.util.filter.SimpleStringFilter;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Panel;
import com.vaadin.ui.PopupDateField;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.TabSheet.SelectedTabChangeEvent;
import com.vaadin.ui.TabSheet.Tab;
import com.vaadin.ui.TreeTable;
import com.vaadin.ui.Button.ClickEvent;

public class HomePanel extends L18NPanel implements View, Observer{
	/**
	 * Serial version ID
	 */
	private static final long serialVersionUID = 1L;
	/* instance members */
	ApplicationContext context = new ClassPathXmlApplicationContext(new String[] {"applicationContent.xml"});
	TeamViewerManager teamViewerManager = (TeamViewerManager)context.getBean("teamViewerManager");
	TeamViewerConnectionManager teamViewerConnectionManager = (TeamViewerConnectionManager)context.getBean("teamViewerConnectionManager");
	protected static final String VIEWNAME = "home";
	/* button */
	private Button btn_search;
	/* ComboBox */
	private ComboBox cmb_orderTeamViewerBy;
	/* PopupDateField  */ 
	private PopupDateField pdf_TeamViewerConnectionDate;
	private int cmbState = 1;	
	/* Tree */
	private TreeTable tre_TeamViewer;
	/* GridLayout */
	private GridLayout grl_HomePanel;
	private GridLayout grl_tab_TeamViewer;
	private GridLayout grl_tab_TeamViewerConnection;
	private GridLayout grl_tab_TeamViewerConnectionMetaData;
	/* TeamViewerConnectionPanel*/
	private Pnl_TeamViewerConnection pnl_TeamViewerConnection;
	private Pnl_TeamViewerConnectionMetaData pnl_TeamViewerConnectionMetaData;
	private Pnl_TeamViewer pnl_TeamViewer;
	/* Tab */
	private TabSheet tab_HomePanel_TeamViewerConnection;
	private TabSheet tab_HomePanel_TeamViewer;
	private Tab tab_TeamViewerConnection;
	private Tab tab_TeamViewerConnectionMetaData;
	private Tab tab_TeamViewer;
	/**
	 * Default constructor for the Class
	 */
	public HomePanel(){
		this.init();
	}
	/*
	 * Initialize the Vaadin WebGUI
	 */
	@SuppressWarnings("deprecation")
	private void init(){	
		/* PopupDateField */
		this.pdf_TeamViewerConnectionDate = new PopupDateField("",new java.util.Date());
		this.pdf_TeamViewerConnectionDate.setDateFormat("dd-MM-yyyy");
		this.pdf_TeamViewerConnectionDate.setReadOnly(false);
		this.pdf_TeamViewerConnectionDate.setImmediate(true);
		this.pdf_TeamViewerConnectionDate.setWidth("200px");
		this.pdf_TeamViewerConnectionDate.addValidator(new Validator(){
			/**
			* Serial Version ID
			*/
			private static final long serialVersionUID = -2026769560095595167L;
			@Override
	        public void validate(Object value) throws InvalidValueException {
				Pattern pattern = Pattern.compile(FieldDataType.date.getRegex());
				Matcher matcher01 = pattern.matcher(pdf_TeamViewerConnectionDate.getValue().toString());
				if (matcher01.find())
					throw new InvalidValueException(captions.getString("PNL_home_pdf_TeamViewerConnectionDate_DateValidation"));
	        }
		});
		this.pdf_TeamViewerConnectionDate.addValueChangeListener(new ValueChangeListener(){
			/**
			 * Serial version is
			 */
			private static final long serialVersionUID = 6011973637203419342L;
			@Override
			public void valueChange(ValueChangeEvent event) {
				if(null !=pdf_TeamViewerConnectionDate.getValue()){
					Pnl_TeamViewerConnection pnl_TeamViewerConnectionNew = new Pnl_TeamViewerConnection(pdf_TeamViewerConnectionDate.getValue());
					List<TeamViewerDTO> listTeamViewerDTO = new ArrayList<TeamViewerDTO>();
					/* get the TeamViewerObjects out the teamViewerConnectionDTO */
					for(TeamViewerConnectionDTO dto : pnl_TeamViewerConnectionNew.getTeamViewerConnectionDTOList()){
						TeamViewerDTO teamViewerdto = teamViewerManager.findByID(dto.getTeamViewerID());
						if(!listTeamViewerDTO.contains(teamViewerdto)){
							listTeamViewerDTO.add(teamViewerdto);
						}
					}
					/* create the TeamViewer Panel */
					Pnl_TeamViewer pnl_TeamViewerNew = new Pnl_TeamViewer(listTeamViewerDTO);
					/* replace the panel */
					grl_tab_TeamViewerConnection.replaceComponent(pnl_TeamViewerConnection, pnl_TeamViewerConnectionNew);
					grl_tab_TeamViewer.replaceComponent(pnl_TeamViewer, pnl_TeamViewerNew);
					pnl_TeamViewerConnection = pnl_TeamViewerConnectionNew;
					pnl_TeamViewer = pnl_TeamViewerNew;
				}
			}
		});
		/*
		 * GridLayout
		 */
		this.grl_tab_TeamViewer = new GridLayout(1,1);
		this.grl_tab_TeamViewerConnection = new GridLayout(1,1);
		this.grl_tab_TeamViewerConnectionMetaData = new GridLayout(1,1);
		/* initialize pnl_TeamViewerConnection*/
		this.pnl_TeamViewerConnection = new Pnl_TeamViewerConnection(pdf_TeamViewerConnectionDate.getValue());
		this.pnl_TeamViewerConnectionMetaData = new Pnl_TeamViewerConnectionMetaData();
		/* Create the TeamViewerDTO list */
		List<TeamViewerDTO> listTeamViewerDTO = new ArrayList<TeamViewerDTO>();
		/* get the TeamViewerObjects out the teamViewerConnectionDTO */
		for(TeamViewerConnectionDTO dto : this.pnl_TeamViewerConnection.getTeamViewerConnectionDTOList()){
			TeamViewerDTO teamViewerdto = this.teamViewerManager.findByID(dto.getTeamViewerID());
			listTeamViewerDTO.add(teamViewerdto);
		}
		this.pnl_TeamViewer = new Pnl_TeamViewer(listTeamViewerDTO);
		/* initialize Button */
		this.btn_search = new Button(super.captions.getString("PNL_home_btn_search"));
		this.btn_search.setWidth("120px");
		this.btn_search.addListener(new Button.ClickListener() {
			/**
			 * Serial Version ID
			 */
			private static final long serialVersionUID = 1L;
			/*
			 * Method will change the editable state of the table
			 * @see com.vaadin.ui.Button.ClickListener#buttonClick(com.vaadin.ui.Button.ClickEvent)
			 */
			@Override
			public void buttonClick(ClickEvent event) {
				Notification.show(grl_HomePanel.getComponent(1, 1).getClass().toString());
				/* set the Table Editable */
				if(Pnl_TeamViewerConnection.class.equals(grl_HomePanel.getComponent(1, 1).getClass())){
					Pnl_TeamViewerConnection pnl_tvc = (Pnl_TeamViewerConnection) grl_HomePanel.getComponent(1, 1);
					pnl_tvc.getTable().setEditable(!pnl_tvc.getTable().isEditable());
				}
				if(Pnl_TeamViewerConnectionMetaData.class.equals(grl_HomePanel.getComponent(1, 1).getClass())){
					Pnl_TeamViewerConnectionMetaData pnl_tvc = (Pnl_TeamViewerConnectionMetaData) grl_HomePanel.getComponent(1, 1);
					pnl_tvc.getTable().setEditable(!pnl_tvc.getTable().isEditable());
				}
			}
		});
		/*
		 * GridLayout
		 */
		this.grl_tab_TeamViewer.setWidth("100%");
		this.grl_tab_TeamViewer.setSizeFull();
		this.grl_tab_TeamViewer.addComponent(this.pnl_TeamViewer);
		this.grl_tab_TeamViewerConnection.setWidth("100%");
		this.grl_tab_TeamViewerConnection.setSizeFull();
		this.grl_tab_TeamViewerConnection.addComponent(this.pnl_TeamViewerConnection);
		this.grl_tab_TeamViewerConnectionMetaData.setWidth("100%");
		this.grl_tab_TeamViewerConnectionMetaData.setSizeFull();
		this.grl_tab_TeamViewerConnectionMetaData.addComponent(this.pnl_TeamViewerConnectionMetaData);
		/*
		 * Tab
		 */
		this.tab_HomePanel_TeamViewer = new TabSheet();
		this.tab_HomePanel_TeamViewer.setCaption(super.captions.getString("PNL_home_tab_HomePanel_TeamViewer"));
		this.tab_HomePanel_TeamViewer.setWidth("100%");
		this.tab_HomePanel_TeamViewer.setSizeFull();
		this.tab_HomePanel_TeamViewerConnection = new TabSheet();
		this.tab_HomePanel_TeamViewerConnection.setCaption(super.captions.getString("PNL_home_tab_HomaPanel"));
		this.tab_HomePanel_TeamViewerConnection.setWidth("100%");
		this.tab_HomePanel_TeamViewerConnection.setSizeFull();
		this.tab_TeamViewer = this.tab_HomePanel_TeamViewer.addTab(this.grl_tab_TeamViewer,super.captions.getString("PNL_home_tab_HomePanel_TeamViewer"));
		this.tab_TeamViewerConnection = this.tab_HomePanel_TeamViewerConnection.addTab(this.grl_tab_TeamViewerConnection,super.captions.getString("PNL_home_tab_TeamViewerConnection"));
		this.tab_TeamViewerConnectionMetaData = this.tab_HomePanel_TeamViewerConnection.addTab(this.grl_tab_TeamViewerConnectionMetaData,super.captions.getString("PNL_home_tab_TeamViewerConnectionMetaData"));
		this.tab_HomePanel_TeamViewerConnection.addListener(new TabSheet.SelectedTabChangeListener() {
			/**
			 * Serial version ID
			 */
			private static final long serialVersionUID = 863141060866484179L;
			/**
			 * Method will fire the changed TabSheet
			 */
			public void selectedTabChange(SelectedTabChangeEvent event) {
				/* replace the panel */
				Object itemId = null;
				try{
					itemId = pnl_TeamViewerConnection.getTbl_TeamViewerConnection().getValue();
				}catch(Exception eXp){
					Notification.show("Please select a TeamViewerConnection");
				}
				Pnl_TeamViewerConnectionMetaData panelTvCMD = null;
				if(null != itemId){
					String teamViewerConnectionId =  pnl_TeamViewerConnection.getTbl_TeamViewerConnection().getContainerProperty(itemId, captions.getString("PNL_home_tbl_TeamViewerConnection_Id")).getValue().toString();
					TeamViewerConnectionDTO teamViewerConnectionDTO = teamViewerConnectionManager.findByID(teamViewerConnectionId);
					if(null != teamViewerConnectionDTO){
						panelTvCMD =  new Pnl_TeamViewerConnectionMetaData(teamViewerConnectionDTO);
					}
				}else{
					panelTvCMD =  new Pnl_TeamViewerConnectionMetaData();
				}
				grl_tab_TeamViewerConnectionMetaData.replaceComponent(pnl_TeamViewerConnectionMetaData, panelTvCMD);
				pnl_TeamViewerConnectionMetaData = panelTvCMD;
			}
		});
		/*
		 * GridLayout
		 */
		this.grl_HomePanel = new GridLayout(4,2);
		this.grl_HomePanel.setSpacing(true);
		this.grl_HomePanel.setWidth("100%");
		this.grl_HomePanel.setSizeFull();
		this.grl_HomePanel.addComponent(this.pdf_TeamViewerConnectionDate,0,0);
		this.grl_HomePanel.addComponent(this.tab_HomePanel_TeamViewer,0,1);
		this.grl_HomePanel.addComponent(this.tab_HomePanel_TeamViewerConnection,1,1,3,1);
		this.grl_HomePanel.addComponent(this.btn_search,1,0);
		this.grl_HomePanel.setComponentAlignment(this.tab_HomePanel_TeamViewer, Alignment.TOP_LEFT);
		this.grl_HomePanel.setComponentAlignment(this.tab_HomePanel_TeamViewerConnection, Alignment.TOP_LEFT);
		this.grl_HomePanel.setComponentAlignment(this.btn_search,Alignment.BOTTOM_LEFT);				
		this.setContent(this.grl_HomePanel);
	}	
	@Override
	public void enter(ViewChangeEvent event) {
		// TODO Auto-generated method stub
	}
	@Override
	public void update(Observable o, Object arg) {
		
		
	}
}
