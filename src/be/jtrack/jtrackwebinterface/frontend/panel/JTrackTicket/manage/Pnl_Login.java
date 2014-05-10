package be.jtrack.jtrackwebinterface.frontend.panel.JTrackTicket.manage;

import be.jtrack.jtrackwebinterface.frontend.panel.L18NPanel;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.TextField;

public class Pnl_Login extends L18NPanel implements View {
	/**
	 * Serial version ID
	 */
	private static final long serialVersionUID = 9158850271199669714L;
	public static final String VIEWNAME = "login";
	/*
	 * Instance members
	 */
	/* Button */
	private Button btn_Login;
	/* Label */
	private Label lbl_Username;
	private Label lbl_Pass;
	/* TextField */
	private TextField txt_Username;
	/* PasswordField */
	private PasswordField ptxt_Pass;
	/* gridLayout */
	private GridLayout grd_General;
	/**
	 * Default constructor for the Class
	 */
	public Pnl_Login(){
		this.init();
	}
	/*
	 * Method will initialize the login panel
	 */
	private void init(){
		/* Button */
		this.btn_Login = new Button();
		/* Label */
		this.lbl_Username = new Label();
		this.lbl_Pass = new Label();
		/* TextField */
		this.txt_Username = new TextField();
		/* PasswordField */
		this.ptxt_Pass = new PasswordField();
		/* GridLayout */
		this.grd_General = new GridLayout(4,2);
//		this.grd_General.addComponent(this.,0,0,3,0);
//		this.grd_General.addComponent(this.btn_Edit,0,1);
//		this.grd_General.addComponent(this.btn_Close,3,1);
//		this.grd_General.setComponentAlignment(this.btn_Edit,Alignment.BOTTOM_LEFT);
//		this.grd_General.setComponentAlignment(this.btn_Close,Alignment.BOTTOM_RIGHT);
		this.grd_General.setSizeFull();
		this.grd_General.setMargin(new MarginInfo(true, true, false, true));
		this.setContent(this.grd_General);
	}
	@Override
	public void enter(ViewChangeEvent event) {		
	}
}
