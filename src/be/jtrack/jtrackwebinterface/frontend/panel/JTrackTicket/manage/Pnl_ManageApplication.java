package be.jtrack.jtrackwebinterface.frontend.panel.JTrackTicket.manage;

import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.Button;
import com.vaadin.ui.GridLayout;

import be.jtrack.jtrackwebinterface.frontend.panel.L18NPanel;
import be.jtrack.jtrackwebinterface.util.Icon;

public class Pnl_ManageApplication extends L18NPanel{
	/**
	 * Serial version ID
	 */
	private static final long serialVersionUID = 203587219333323242L;
	/* instance members */
	private final String abstractComponentHeight = "25px";
	private final String abstractButtonWidht = "120px";
	private final String abstractComponentWidht = "340px";
	/* Button */
	private Button btn_Next;
	/* GridLayout */
	private GridLayout grd_General;
	/**
	 * Default constructor for the Class
	 */
	public Pnl_ManageApplication(){
		init();
	}
	private void init(){
		/* Button */
		this.btn_Next = new Button(captions.getString("CAP.BTN.6"));
		this.btn_Next.setHeight(this.abstractComponentHeight);
		this.btn_Next.setWidth(this.abstractButtonWidht);
		this.btn_Next.setIcon(Icon.iconArrowRight);
		/* GridLayout */
		this.grd_General = new GridLayout(1,2);
		this.grd_General.addComponent(new Pnl_ApplicationOverview(),0,0);
		this.grd_General.addComponent(this.btn_Next,0,1);
		this.grd_General.setSizeFull();
		this.grd_General.setMargin(new MarginInfo(true, true, false, true));
		this.setContent(this.grd_General);
	}
	
}
