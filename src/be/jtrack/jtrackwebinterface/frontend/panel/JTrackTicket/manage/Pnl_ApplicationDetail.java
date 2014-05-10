package be.jtrack.jtrackwebinterface.frontend.panel.JTrackTicket.manage;

import be.jtrack.jtrackwebinterface.frontend.panel.L18NPanel;
import be.jtrack.jtrackwebinterface.util.Icon;

import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.Button;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Table;
import com.vaadin.ui.TextField;

public class Pnl_ApplicationDetail extends L18NPanel{
	/**
	 * Serial version ID
	 */
	private static final long serialVersionUID = 203587219333323242L;
	/* instance members */
	private final String abstractComponentHeight = "25px";
	private final String abstractButtonWidht = "120px";
	private final String abstractComponentWidht = "340px";
	/* Label */
	private Label lbl_Space_Details;
	private Label lbl_Space_DisplayName;
	private Label lbl_Space_SpaceKey;
	private Label lbl_Space_Description;
	private Label lbl_Space_MakePublic;
	private Label lbl_Space_CopyExisting;
	/* TextField */ 
	private TextField txt_Space_DisplayName;
	private TextField txt_Space_SpaceKey;
	private TextField txt_Space_Description;
	private TextField txt_Space_MakePublic;
	/* Button */
	private Button btn_Next;
	/* Table */
	private Table tbl_Space;
	/* GridLayout */
	private GridLayout grd_General;
	/**
	 * Default constructor for the Class
	 */
	public Pnl_ApplicationDetail(){
		this.init();
	}
	private void init(){
		/* Label */
		this.lbl_Space_Details = new Label(captions.getString("CAP.LBL.16"));
		this.lbl_Space_DisplayName = new Label(captions.getString("CAP.LBL.17"));
		this.lbl_Space_SpaceKey = new Label(captions.getString("CAP.LBL.18"));
		this.lbl_Space_Description = new Label(captions.getString("CAP.LBL.19"));
		this.lbl_Space_MakePublic = new Label(captions.getString("CAP.LBL.20"));
		this.lbl_Space_CopyExisting = new Label(captions.getString("CAP.LBL.21"));
		/* TextField */
		this.txt_Space_DisplayName = new TextField();
		this.txt_Space_SpaceKey = new TextField();
		this.txt_Space_Description = new TextField();
		this.txt_Space_MakePublic = new TextField();
		/* GridLayout */
		this.grd_General = new GridLayout(2,6);
		this.grd_General.addComponent(this.lbl_Space_Details,0,0);
		this.grd_General.addComponent(this.lbl_Space_DisplayName,0,0);
		this.grd_General.addComponent(this.lbl_Space_SpaceKey,0,1);
		this.grd_General.addComponent(this.lbl_Space_Description,0,2);
		this.grd_General.addComponent(this.lbl_Space_MakePublic,0,3);
		this.grd_General.addComponent(this.lbl_Space_CopyExisting,0,4);
		this.grd_General.addComponent(this.lbl_Space_CopyExisting,1,5);
		this.grd_General.addComponent(this.txt_Space_DisplayName,1,0);
		this.grd_General.addComponent(this.txt_Space_SpaceKey,1,1);
		this.grd_General.addComponent(this.txt_Space_Description,1,2);
		this.grd_General.addComponent(this.txt_Space_MakePublic,1,3);
		this.grd_General.setSizeFull();
		this.grd_General.setMargin(new MarginInfo(true, true, false, true));
		this.setContent(this.grd_General);
	}
}
