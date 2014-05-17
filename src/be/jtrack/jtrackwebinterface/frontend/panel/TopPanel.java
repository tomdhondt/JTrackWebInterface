package be.jtrack.jtrackwebinterface.frontend.panel;

import be.jtrack.jtrackwebinterface.frontend.panel.JTrackInventory.Pnl_Inventory_PropertyType;
import be.jtrack.jtrackwebinterface.frontend.panel.JTrackTicket.dashboard.Pnl_TicketDashBoard;
import be.jtrack.jtrackwebinterface.frontend.panel.JTrackTicket.manage.Pnl_ManageApplication;
import be.jtrack.jtrackwebinterface.frontend.panel.JTrackTicket.manage.Pnl_ManageUsers;
import be.jtrack.jtrackwebinterface.util.ResourceUtil;

import com.vaadin.ui.GridLayout;
import com.vaadin.ui.MenuBar;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Window;
import com.vaadin.ui.MenuBar.Command;
import com.vaadin.ui.MenuBar.MenuItem;

public class TopPanel extends L18NPanel {
	/**
	 * Serial Version ID
	 */
	private static final long serialVersionUID = 3909209893557787008L;
	/* Name of the view */
	protected static final String VIEWNAME = "top";
	/* MenuBar */
	private MenuBar mbr_Global;
	private MenuBar.MenuItem mbi_Global;
	private MenuBar.MenuItem mbi_TeamViewer;
	private MenuBar.MenuItem mbi_Inventory;
	private MenuBar.MenuItem mbi_Ticket;
	private MenuBar.MenuItem mbi_Help;
	/* Window */
	private Window wdw_BaseWindow;
	/* GridLayout */
	private GridLayout grl_TopPanel;
	/**
	 * Default constructor for the Class
	 */
	public TopPanel(){
		this.init();
	}
	private void init(){
		/* MenuBar */
		this.mbr_Global = new MenuBar();
		this.mbi_Global = this.mbr_Global.addItem(super.captions.getString("PNL_TopPanel_mbi_Global"), null);
		this.mbi_Global.addItem(super.captions.getString("PNL_TopPanel_mbi_Global_Language"),this.MenuCommand()).setEnabled(false);
		this.mbi_Global.addItem(super.captions.getString("PNL_TopPanel_mbi_Global_Close"),this.MenuCommand()).setEnabled(false);
		this.mbi_TeamViewer = this.mbr_Global.addItem(super.captions.getString("PNL_TopPanel_mbi_TeamViewer"), null);
		this.mbi_TeamViewer.addItem(super.captions.getString("PNL_TopPanel_mbi_TeamViewer_CustomField"), this.TeamViewer_CustomField());
		this.mbi_Inventory = this.mbr_Global.addItem(super.captions.getString("PNL_TopPanel_mbi_Inventory"), null);
		this.mbi_Inventory.addItem(super.captions.getString("PNL_TopPanel_mbi_Inventory_PropertyType"), this.Inventory_PropertyType());
		this.mbi_Ticket = this.mbr_Global.addItem(super.captions.getString("CAP.PNL.2"), null);
		this.mbi_Ticket.addItem(super.captions.getString("CAP.PNL.1"), this.Ticket_ManageUsers());
		this.mbi_Ticket.addItem(super.captions.getString("CAP.PNL.3"), this.Ticket_ManageSpace());
		this.mbi_Ticket.addItem(super.captions.getString("CAP.PNL.5"), this.Ticket_DashBoard());
		this.mbi_Help = this.mbr_Global.addItem(super.captions.getString("PNL_TopPanel_mbi_Help"), null);
		this.mbi_Help.addItem(super.captions.getString("PNL_TopPanel_mbi_Help_Info"), this.MenuCommand()).setIcon(ResourceUtil.getFileResource("/VAADIN/themes/icons/png/help.png"));
		/*  GridLayout */
		this.grl_TopPanel = new GridLayout(1,1);
		this.grl_TopPanel.addComponent(mbr_Global, 0, 0);
		this.setContent(this.grl_TopPanel);
	}
	/*
	 * Method will execute the command activate by the click on the menuItem
	 * @return command as com.vaadin.ui.MenuBar.Command
	 */
    private Command MenuCommand(){
    	Command command = new Command() {
			/**
			 * Serial version ID
			 */
			private static final long serialVersionUID = 1L;
			@Override
			public void menuSelected(MenuItem selectedItem) {
				Notification.show("Action " + selectedItem.getText());
				
			}
		};
		return command;
    }
    /*
     * Method will create the MenuBar Command for the TeamViewer CustomField   
     * @return Command
     */
    private Command TeamViewer_CustomField(){
    	Command command = new Command() {
			/**
			 * Serial Version ID
			 */
			private static final long serialVersionUID = 5223585746425208885L;

			@Override
			public void menuSelected(MenuItem selectedItem) {
				/* initialize Window to confirm deleting the connection*/
				wdw_BaseWindow = new Window(captions.getString("PNL_TopPanel_wdw_TeamViewerCustomField_title"));
				wdw_BaseWindow.setClosable(false);
				wdw_BaseWindow.setResizable(false);
				wdw_BaseWindow.setModal(true);
				wdw_BaseWindow.setContent(new Pnl_CustomField());
				wdw_BaseWindow.setWidth("50%");
				wdw_BaseWindow.setHeight("600px");
				/* show the notification window */
				getUI().addWindow(wdw_BaseWindow);
			}
		};
		return command;
    }
    /*
     * Method will create the MenuBar Command for the Inventory PropertyType   
     * @return Command
     */
    private Command Inventory_PropertyType(){
    	Command command = new Command() {
			/**
			 * Serial Version ID
			 */
			private static final long serialVersionUID = 5223585746425208885L;

			@Override
			public void menuSelected(MenuItem selectedItem) {
				/* initialize Window to confirm deleting the connection*/
				wdw_BaseWindow = new Window(captions.getString("PNL_TopPanel_wdw_Inventory_PropertyType_title"));
				wdw_BaseWindow.setClosable(false);
				wdw_BaseWindow.setResizable(false);
				wdw_BaseWindow.setModal(true);
				wdw_BaseWindow.setContent(new Pnl_Inventory_PropertyType());
				wdw_BaseWindow.setWidth("50%");
				wdw_BaseWindow.setHeight("600px");
				/* show the notification window */
				getUI().addWindow(wdw_BaseWindow);
			}
		};
		return command;
    }
    /*
     * Method will create the MenuBar Command for the Inventory PropertyType   
     * @return Command
     */
    private Command Ticket_ManageUsers(){
    	Command command = new Command() {
			/**
			 * Serial Version ID
			 */
			private static final long serialVersionUID = 5223585746425208885L;

			@Override
			public void menuSelected(MenuItem selectedItem) {
				/* initialize Window to confirm deleting the connection*/
				wdw_BaseWindow = new Window(captions.getString("CAP.PNL.1"));
				wdw_BaseWindow.setClosable(true);
				wdw_BaseWindow.setResizable(false
						);
				wdw_BaseWindow.setModal(true);
				wdw_BaseWindow.setContent(new Pnl_ManageUsers());
				wdw_BaseWindow.setWidth("50%");
				wdw_BaseWindow.setHeight("600px");
				/* show the notification window */
				getUI().addWindow(wdw_BaseWindow);
			}
		};
		return command;
    }
    private Command Ticket_ManageSpace(){
    	Command command = new Command() {
			/**
			 * Serial Version ID
			 */
			private static final long serialVersionUID = 5223585746425208885L;

			@Override
			public void menuSelected(MenuItem selectedItem) {
				/* initialize Window to confirm deleting the connection*/
				wdw_BaseWindow = new Window(captions.getString("CAP.PNL.3"));
				wdw_BaseWindow.setClosable(true);
				wdw_BaseWindow.setResizable(false);
				wdw_BaseWindow.setModal(true);
				wdw_BaseWindow.setContent(new Pnl_ManageApplication(wdw_BaseWindow));
				wdw_BaseWindow.setWidth("50%");
				/* show the notification window */
				getUI().addWindow(wdw_BaseWindow);
			}
		};
		return command;
    }
    private Command Ticket_DashBoard(){
    	Command command = new Command() {
			/**
			 * Serial Version ID
			 */
			private static final long serialVersionUID = 5223585746425208885L;

			@Override
			public void menuSelected(MenuItem selectedItem) {
				/* initialize Window to confirm deleting the connection*/
				wdw_BaseWindow = new Window(captions.getString("CAP.PNL.5"));
				wdw_BaseWindow.setClosable(true);
				wdw_BaseWindow.setResizable(false);
				wdw_BaseWindow.setModal(true);
				wdw_BaseWindow.setContent(new Pnl_TicketDashBoard(wdw_BaseWindow));
				wdw_BaseWindow.setWidth("50%");
				/* show the notification window */
				getUI().addWindow(wdw_BaseWindow);
			}
		};
		return command;
    }
}
