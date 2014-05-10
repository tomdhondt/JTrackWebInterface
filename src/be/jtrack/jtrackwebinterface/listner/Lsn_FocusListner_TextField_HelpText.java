package be.jtrack.jtrackwebinterface.listner;

import com.vaadin.event.FieldEvents.FocusEvent;
import com.vaadin.event.FieldEvents.FocusListener;
import com.vaadin.ui.AbstractComponent;
import com.vaadin.ui.Label;

public class Lsn_FocusListner_TextField_HelpText implements FocusListener{
	/*
	 * Instance members
	 */
	private Label lbl_object;
	private AbstractComponent com_object;
	/**
	 * Default Constructor for the Class
	 */
	public Lsn_FocusListner_TextField_HelpText(){}
	/**
	 * Constructor for the Class
	 */
	public Lsn_FocusListner_TextField_HelpText(Label label, AbstractComponent component){
		this.lbl_object = label;
		this.com_object = component;
	}
	/**
	 * Serial version ID
	 */
	private static final long serialVersionUID = 6257101969701115943L;
	/**
	 * Method will handle the focus changed event
	 */
	@Override
	public void focus(FocusEvent event) {
		if(null!= this.lbl_object){
			if(null!= this.com_object){
				this.lbl_object.setValue(this.com_object.getDescription());
			}else{
				this.lbl_object.setValue("-");
			}
		}
	} 

}
