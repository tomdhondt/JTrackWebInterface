package be.jtrack.jtrackwebinterface.listner;

import be.jtrack.jtrackwebinterface.frontend.panel.JTrackInventory.Pnl_JTrackInventoryView;

import com.vaadin.event.ItemClickEvent;
import com.vaadin.event.ItemClickEvent.ItemClickListener;

public class Lsn_ItemClickListner_Pnl_Inventory_TreeTable implements ItemClickListener{
	/**
	 * Serial Version ID
	 */
	private static final long serialVersionUID = 3744589195741784315L;
	private Pnl_JTrackInventoryView panel;
	/**
	 * Default constructor
	 */
	public Lsn_ItemClickListner_Pnl_Inventory_TreeTable(){}
	public Lsn_ItemClickListner_Pnl_Inventory_TreeTable(Pnl_JTrackInventoryView panel){
		this.panel = panel;
	}
	@Override
	public void itemClick(ItemClickEvent event) {
		
	}

}
