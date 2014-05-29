package be.jtrack.jtrackwebinterface.frontend.panel.JTrackInventory;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.Page;
import com.vaadin.server.Page.BrowserWindowResizeEvent;
import com.vaadin.server.Page.BrowserWindowResizeListener;
import com.vaadin.server.Resource;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.Component;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.HorizontalSplitPanel;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Panel;
import com.vaadin.ui.Table.Align;
import com.vaadin.ui.TreeTable;
import com.vaadin.ui.VerticalSplitPanel;
import com.vaadin.data.Property;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.event.Action;
import com.vaadin.event.ItemClickEvent;
import com.vaadin.event.ItemClickEvent.ItemClickListener;

import be.jtrack.jtrackwebinterface.frontend.panel.global.L18NPanel;
import be.jtrack.jtrackwebinterface.util.BrowserUtil;
import be.jtrack.jtrackwebinterface.util.Icon;
import be.jtrackinventory.exception.manager.ManagerException;
import be.jtrackinventory.service.dto.MaterialComponentDTO;
import be.jtrackinventory.service.dto.MaterialComponentObjectDTO;
import be.jtrackinventory.service.dto.MaterialDTO;
import be.jtrackinventory.service.dto.MaterialObjectDTO;
import be.jtrackinventory.service.dto.PropertyDTO;
import be.jtrackinventory.service.dto.PropertyGroupDTO;
import be.jtrackinventory.service.dto.PropertyTypeDTO;
import be.jtrackinventory.service.dto.ValueDTO;
import be.jtrackinventory.service.manager.IManager;

@SuppressWarnings("unchecked")
public class Pnl_JTrackInventoryView extends L18NPanel implements View{
	/**
	 * Serial version ID
	 */
	private static final long serialVersionUID = 5008707187384227285L;
	ApplicationContext context = new ClassPathXmlApplicationContext(new String[] {"applicationContent.xml"});
	IManager<PropertyDTO> iManagerProperty = (IManager<PropertyDTO>) context.getBean("iManagerProperty");
	IManager<PropertyTypeDTO> iManagerPropertyType = (IManager<PropertyTypeDTO>) context.getBean("iManagerPropertyType");
	IManager<PropertyGroupDTO> iManagerPropertyGroup = (IManager<PropertyGroupDTO>) context.getBean("iManagerPropertyGroup");
	IManager<MaterialComponentDTO> iManagerMaterialComponent = (IManager<MaterialComponentDTO>) context.getBean("iManagerMaterialComponent");
	IManager<MaterialDTO> iManagerMaterial = (IManager<MaterialDTO>) context.getBean("iManagerMaterial");
	IManager<MaterialObjectDTO> iManagerMaterialObject = (IManager<MaterialObjectDTO>) context.getBean("iManagerMaterialObject");
	IManager<MaterialComponentObjectDTO> iManagerMaterialComponentObject = (IManager<MaterialComponentObjectDTO>) context.getBean("iManagerMaterialComponentObject");
	IManager<ValueDTO> iManagerValue = (IManager<ValueDTO>) context.getBean("iManagerValue");
	
	/* Action */
	private final Action act_tbl_Inventory_Add = new Action(super.captions.getString("CAP.ACT.1"),Icon.iconNew);
	private final Action act_tbl_Inventory_Refresh = new Action(super.captions.getString("CAP.ACT.2"),Icon.iconRefresh);
	private final Action act_tbl_Inventory_Object_New = new Action(super.captions.getString("CAP.ACT.1"),Icon.iconNew);
	private final Action act_tbl_Inventory_Object_Remove = new Action(super.captions.getString("CAP.ACT.3"),Icon.iconDelete);
	private final Action[] ACTIONS = new Action[] {act_tbl_Inventory_Add,act_tbl_Inventory_Refresh};
	private final Action[] ACTIONS_Object = new Action[] {act_tbl_Inventory_Object_New,act_tbl_Inventory_Object_Remove};
	/*
	 * Instance members
	 */
	/* data */
	private List<PropertyTypeDTO> lst_PropertyType;
	private List<PropertyDTO> lst_Property;
	private List<PropertyGroupDTO> lst_PropertyGroup;
	private List<MaterialComponentDTO> lst_MaterialComponent;
	private List<MaterialDTO> lst_Matererial;
	private List<MaterialObjectDTO> lst_MaterialObject;
	private List<MaterialComponentObjectDTO> lst_MaterialComponentObject;
	private List<ValueDTO> listValue;
	private Map<String, PropertyDTO> mapProperty;
	private Map<String, PropertyGroupDTO> mapPropertyGroup;
	private Map<String, PropertyTypeDTO> mapPropertyType;
	private Map<String, MaterialComponentDTO> mapMaterialComponent;
	private Map<String, MaterialDTO> mapMaterial;
	private Map<String,MaterialObjectDTO> mapMaterialObject;
	private Map<String,ValueDTO> mapValue;
	private Map<String, MaterialComponentObjectDTO> mapMaterialComponentObject;
	/* VIEW */
	public static final String VIEWNAME = "JTrackInventoryView";
	/* GridLayout */
	private GridLayout grd_JTrackInventoryView;
	/* Horizontal SplitPanel */
	private HorizontalSplitPanel hsp_JTrackInventoryView;
	/* Vertical SplitPanel */
	private VerticalSplitPanel vsp_JTrackInventoryView;
	/* CheckBox */
	private CheckBox chb_ChangeSplitPanelSize;
	/* TreeTable */
	private TreeTable trt_JTrackInventoryView_Base;
	private TreeTable trt_JTrackInventoryView_Structure;
	/* Panel */
	private Panel pnl_Panel = new Panel();
	/**
	 * Default constructor for the Class
	 */
	public Pnl_JTrackInventoryView(){
		this.init();
	}
	private void init(){
		/* resize the panel  */
		Page.getCurrent().addBrowserWindowResizeListener(new BrowserWindowResizeListener() {
			/**
			 * Serial version id
			 */
			private static final long serialVersionUID = 56942042038191543L;
			@Override
			public void browserWindowResized(BrowserWindowResizeEvent event) {
				/* Resize the Horizontal Split Panel*/
				resizeComponentHeight(hsp_JTrackInventoryView, 80);
			}
		});
		/* initiate the data */
		/* get the property */
		try{
			this.lst_Property = this.iManagerProperty.findAll();
			this.lst_PropertyType = this.iManagerPropertyType.findAll();
			this.lst_PropertyGroup = this.iManagerPropertyGroup.findAll();
			this.lst_MaterialComponent = this.iManagerMaterialComponent.findAll();
			this.lst_Matererial = this.iManagerMaterial.findAll();
			this.lst_MaterialObject = this.iManagerMaterialObject.findAll();
			this.lst_MaterialComponentObject = this.iManagerMaterialComponentObject.findAll();
			this.listValue = this.iManagerValue.findAll();
			/* create the PropertyType */
			this.mapPropertyType = new TreeMap<String, PropertyTypeDTO>();
			for(PropertyTypeDTO type : this.lst_PropertyType){
				this.mapPropertyType.put(type.getUniqueId(), type);
			}
		} catch (ManagerException mXe) {
			Notification.show(mXe.getCaption());
		}
		/* Label */
		/* CheckBox */
		this.chb_ChangeSplitPanelSize = new CheckBox(captions.getString("Pnl_JTrackInventoryView_chb_ChangeSplitPanelSize_unLock"));
		this.chb_ChangeSplitPanelSize.setValue(true);
		this.chb_ChangeSplitPanelSize.addValueChangeListener(new Property.ValueChangeListener() {
            private static final long serialVersionUID = -6857112166321059475L;
            /*
             * Method will evaluate the state of the CheckBox
             */
            public void valueChange(ValueChangeEvent event) {
            	/* get the value of the CheckBox */
            	boolean state = (Boolean) event.getProperty().getValue();
            	/* set the caption of the CheckBox */
            	if(true == state){
            		chb_ChangeSplitPanelSize.setCaption(captions.getString("Pnl_JTrackInventoryView_chb_ChangeSplitPanelSize_unLock"));
            	}else{
            		chb_ChangeSplitPanelSize.setCaption(captions.getString("Pnl_JTrackInventoryView_chb_ChangeSplitPanelSize_Lock"));
            	}
            	/* Set the state of the CheckBox */
            	hsp_JTrackInventoryView.setLocked(state);
            	vsp_JTrackInventoryView.setLocked(state);
            }
        });
		/* TreeTable */
		this.trt_JTrackInventoryView_Base = new TreeTable();
		this.trt_JTrackInventoryView_Structure =  new TreeTable();
		this.trt_JTrackInventoryView_Base.setWidth("100%");
		this.trt_JTrackInventoryView_Structure.setWidth("100%");
		this.trt_JTrackInventoryView_Base.setHeight("100%");
		this.trt_JTrackInventoryView_Structure.setHeight("100%");
		this.trt_JTrackInventoryView_Base.setSelectable(true);
		this.trt_JTrackInventoryView_Structure.setSelectable(true);
		this.trt_JTrackInventoryView_Base.addContainerProperty(captions.getString("CAP.TRT.5"), String.class, "",captions.getString("CAP.TRT.5"),Icon.iconBase,Align.LEFT);
		this.trt_JTrackInventoryView_Base.addContainerProperty(captions.getString("CAP.TRT.6"), String.class,null);		
		this.trt_JTrackInventoryView_Structure.addContainerProperty(captions.getString("CAP.TRT.4"), String.class,"",captions.getString("CAP.TRT.4"),Icon.iconStructure,Align.LEFT);
		this.trt_JTrackInventoryView_Structure.addContainerProperty(captions.getString("CAP.TRT.6"), String.class,null);
		this.trt_JTrackInventoryView_Base.setColumnWidth(captions.getString("CAP.TRT.6"), 0);
		this.trt_JTrackInventoryView_Structure.setColumnWidth(captions.getString("CAP.TRT.6"), 0);
		this.trt_JTrackInventoryView_Base.addItemClickListener(new ItemClickListener(){
			/**
			 * Serial Version ID
			 */
			private static final long serialVersionUID = 616623580204168867L;
			@Override
			public void itemClick(ItemClickEvent event){
				Object object = trt_JTrackInventoryView_Base.getValue();
				if(null != object){
					Object parent = trt_JTrackInventoryView_Base.getParent(object);
					if(null != parent){
						String baseParentType = (String) trt_JTrackInventoryView_Base.getItem(parent).getItemProperty(captions.getString("CAP.TRT.6")).getValue();
						String uniqueID = (String) trt_JTrackInventoryView_Base.getItem(object).getItemProperty(captions.getString("CAP.TRT.6")).getValue();
						Panel pnl_New = null;
						switch(Integer.parseInt(baseParentType)){
							case 1:
								/* refresh the PropertyType */
								try {
									lst_PropertyType = iManagerPropertyType.findAll();
									mapPropertyType.clear();
									for(PropertyTypeDTO dto : lst_PropertyType){
											mapPropertyType.put(dto.getUniqueId(), dto);
									}
								} catch (ManagerException e) {
								}
								/* Replace the Property panel */
								pnl_New = new Pnl_Property(trt_JTrackInventoryView_Base, mapProperty, mapPropertyGroup, mapPropertyType, uniqueID);
							break;
							case 2:
								/* Replace the PropertyGroup panel */
								pnl_New = new Pnl_PropertyGroup(trt_JTrackInventoryView_Base,mapPropertyGroup, mapProperty, uniqueID);
							break;
							case 3: 
								/* Replace the MaterialComponent panel */
								pnl_New = new Pnl_MaterialComponent(trt_JTrackInventoryView_Base, mapMaterialComponent, mapPropertyGroup, mapProperty, uniqueID);
							break;
							case 4: 
								/* Replace the Material panel */
								pnl_New = new Pnl_Material(trt_JTrackInventoryView_Base, trt_JTrackInventoryView_Structure, mapMaterial, mapMaterialComponent, mapPropertyGroup, mapProperty, uniqueID);
							break;
						}
						hsp_JTrackInventoryView.replaceComponent(pnl_Panel, pnl_New);
						pnl_Panel = pnl_New;
					}
				}		
			}
		});
		this.trt_JTrackInventoryView_Base.addActionHandler(new com.vaadin.event.Action.Handler() {
			/**
			 * Serial Version ID
			 */
			private static final long serialVersionUID = 1L;
			@Override
			public void handleAction(Action action, Object sender, Object target) {
				if(null != action.getCaption()){
					/* Action to perform when AddTicket is selected */
					/* Set the panel depending on the select TreeMap item selected */
					if(captions.getString("CAP.ACT.1") == action.getCaption()){
						Object object = trt_JTrackInventoryView_Base.getValue();
						if(null != object){
							String baseCategory = (String) trt_JTrackInventoryView_Base.getItem(object).getItemProperty(captions.getString("CAP.TRT.6")).getValue();
							Panel pnl_New = null;
							switch(Integer.parseInt(baseCategory)){
								/* Show the Property panel to the user */
								case 1:
									pnl_New = new Pnl_Property(trt_JTrackInventoryView_Base, mapProperty, mapPropertyGroup, mapPropertyType, null);
									break;
								/* Show the PropertyGroup panel to the user */
								case 2:
									pnl_New = new Pnl_PropertyGroup(trt_JTrackInventoryView_Base,mapPropertyGroup, mapProperty, null);
									break;
								/* Show the MaterialComponent panel to the user */
								case 3: 
									pnl_New = new Pnl_MaterialComponent(trt_JTrackInventoryView_Base, mapMaterialComponent, mapPropertyGroup, mapProperty, null);
									break;
								/* Show the Material panel to the user */
								case 4:
									pnl_New = new Pnl_Material(trt_JTrackInventoryView_Base, trt_JTrackInventoryView_Structure, mapMaterial, mapMaterialComponent, mapPropertyGroup, mapProperty, null);
									break;
								default:
									break;
							}
							/* Replace the Window for the User */
							if(null != pnl_New){
								hsp_JTrackInventoryView.replaceComponent(pnl_Panel, pnl_New);
								pnl_Panel = pnl_New;
							}
						}
					}
					/* Refresh the TreeTable */
					if(captions.getString("CAP.ACT.2") == action.getCaption()){
						Notification.show("Refresh"); 
					}
				}
			}
			
			@Override
			public Action[] getActions(Object target, Object sender) {
				return ACTIONS;
			}
		});
		this.trt_JTrackInventoryView_Structure.addActionHandler(new com.vaadin.event.Action.Handler() {
			/**
			 * Serial Version ID
			 */
			private static final long serialVersionUID = 1L;
			@Override
			public void handleAction(Action action, Object sender, Object target) {
				if(null != action.getCaption()){
					/* Action to perform when AddTicket is selected */
					/* Set the panel depending on the select TreeMap item selected */
					if(captions.getString("CAP.ACT.1") == action.getCaption()){
						Object object = trt_JTrackInventoryView_Structure.getValue();
						if(null != object){
							Panel pnl_New = null;
							String materialUniqueId = (String) trt_JTrackInventoryView_Structure.getItem(object).getItemProperty(captions.getString("CAP.TRT.6")).getValue();
							MaterialDTO dto = mapMaterial.get(materialUniqueId);
							if(null != dto){
								pnl_New = new Pnl_MaterialObject(trt_JTrackInventoryView_Structure,null, materialUniqueId, mapMaterial, mapMaterialObject,mapMaterialComponentObject, mapProperty);
							}
							/* Replace the Window for the User */
							if(null != pnl_New){
								hsp_JTrackInventoryView.replaceComponent(pnl_Panel, pnl_New);
								pnl_Panel = pnl_New;
							}
						}
					}
					if(captions.getString("CAP.ACT.3") == action.getCaption()){
						Notification.show("Delete");
					}
				}
			}
			@Override
			public Action[] getActions(Object target, Object sender) {
				return ACTIONS_Object;
			}			
		});
		this.trt_JTrackInventoryView_Structure.addItemClickListener(new ItemClickListener(){
			/**
			 * Serial Version ID
			 */
			private static final long serialVersionUID = 616623580204168867L;
			@Override
			public void itemClick(ItemClickEvent event){
				Object object = trt_JTrackInventoryView_Structure.getValue();
				if(null != object){
					Object parent = trt_JTrackInventoryView_Structure.getParent(object);					
					Panel pnl_New = null;
					/* get the Material and MaterialObject out the selected value out the TreeTable */
					try{
						String materialuniqueID = (String)trt_JTrackInventoryView_Structure.getItem(parent).getItemProperty(captions.getString("CAP.TRT.6")).getValue();
						String materialObjectUniqueId = (String)trt_JTrackInventoryView_Structure.getItem(object).getItemProperty(captions.getString("CAP.TRT.6")).getValue();
						/* create a new Panel with the found object */
						pnl_New = new Pnl_MaterialObject(trt_JTrackInventoryView_Structure,materialObjectUniqueId, materialuniqueID, mapMaterial, mapMaterialObject, mapMaterialComponentObject, mapProperty);
					}catch(Exception nFe){
						/* catch exception and give error message */
						Notification.show(captions.getString("CAP.DESC.17"));
						pnl_New = new Pnl_Inventory_Empty();
					}
					/* Replace the Window for the User */
					if(null != pnl_New){
						/* replace the component */
						hsp_JTrackInventoryView.replaceComponent(pnl_Panel, pnl_New);
						/* remove the component that we replaced */
						hsp_JTrackInventoryView.removeComponent(pnl_Panel);
						pnl_Panel = pnl_New;
					}
				}
			}
		});
		/* add the Property trt_JTrackInventoryView_Base */
		Object trt_JTrackInventoryView_Base_Property = this.trt_JTrackInventoryView_Base.addItem(new Object[] {"      " + captions.getString("CAP.TRT.1"),"1"}, null);
		Object trt_JTrackInventoryView_Base_PropertyGroup = this.trt_JTrackInventoryView_Base.addItem(new Object[] {"      " + captions.getString("CAP.TRT.7"),"2"}, null);
		Object trt_JTrackInventoryView_Base_MaterialComponent = this.trt_JTrackInventoryView_Base.addItem(new Object[] {"      " + captions.getString("CAP.TRT.8"),"3"}, null);
		Object trt_JTrackInventoryView_Base_Material = this.trt_JTrackInventoryView_Base.addItem(new Object[] {"      " + captions.getString("CAP.TRT.9"),"4"}, null);
		/* add the Property trt_JTrackInventoryView_Structure */
		Object trt_JTrackInventoryView_Structure_MaterialObject = this.trt_JTrackInventoryView_Structure.addItem(new Object[] {"      " + captions.getString("CAP.TRT.3"),"5"}, null);
//		Object trt_JTrackInventoryView_Structure_MaterialComponentObject = this.trt_JTrackInventoryView_Structure.addItem(new Object[] {"      "  captions.getString("CAP.TRT.4"),"6"}, null);
//		this.setItemIcon(trt_JTrackInventoryView_Structure_MaterialComponentObject, Icon.iconMaterialComponentObject, this.trt_JTrackInventoryView_Structure);
		this.setMaterialComponentObjectTreeTable(null,lst_MaterialComponent,lst_MaterialComponentObject);
		/* set the icon in the TreeTable*/
		this.setItemIcon(trt_JTrackInventoryView_Base_Property, Icon.iconProperty, this.trt_JTrackInventoryView_Base);
		this.setItemIcon(trt_JTrackInventoryView_Base_PropertyGroup, Icon.iconPropertyGroup, this.trt_JTrackInventoryView_Base);
		this.setItemIcon(trt_JTrackInventoryView_Base_MaterialComponent, Icon.iconMaterialComponent, this.trt_JTrackInventoryView_Base);
		this.setItemIcon(trt_JTrackInventoryView_Base_Material, Icon.iconMaterial, this.trt_JTrackInventoryView_Base);
		this.setItemIcon(trt_JTrackInventoryView_Structure_MaterialObject, Icon.iconMaterialObject, this.trt_JTrackInventoryView_Structure);
		/* set the data in the TreeTable  */
		this.setPropertyTreeTable(trt_JTrackInventoryView_Base_Property, this.lst_Property);
		this.setPropertyGroupTreeTable(trt_JTrackInventoryView_Base_PropertyGroup, this.lst_PropertyGroup);		
		this.setMaterialComponentTreeTable(trt_JTrackInventoryView_Base_MaterialComponent, this.lst_MaterialComponent);
		this.setMaterialTreeTable(trt_JTrackInventoryView_Base_Material, this.lst_Matererial);
		this.setMaterialObjectTreeTable(trt_JTrackInventoryView_Structure_MaterialObject,lst_Matererial,lst_MaterialObject);
		/* */
		this.trt_JTrackInventoryView_Base.setCollapsed(trt_JTrackInventoryView_Base_Property, true);
		this.trt_JTrackInventoryView_Base.setCollapsed(trt_JTrackInventoryView_Base_PropertyGroup, true);
		this.trt_JTrackInventoryView_Base.setCollapsed(trt_JTrackInventoryView_Base_MaterialComponent, true);
		this.trt_JTrackInventoryView_Base.setCollapsed(trt_JTrackInventoryView_Base_Material, true);
		/* Vertical SplitPanel */
		this.vsp_JTrackInventoryView = new VerticalSplitPanel();
		this.vsp_JTrackInventoryView.addComponent(this.trt_JTrackInventoryView_Structure);
		this.vsp_JTrackInventoryView.setSplitPosition(50);    
		this.vsp_JTrackInventoryView.addComponent(this.trt_JTrackInventoryView_Base);
		this.vsp_JTrackInventoryView.setLocked(true);
		this.vsp_JTrackInventoryView.setWidth("99%");
		/* Horizontal SplitPanel */
		this.hsp_JTrackInventoryView = new HorizontalSplitPanel();        
		this.hsp_JTrackInventoryView.setSplitPosition(25);           
		this.hsp_JTrackInventoryView.addComponent(this.vsp_JTrackInventoryView);
		Pnl_Inventory_Empty pnl = new Pnl_Inventory_Empty();
		this.hsp_JTrackInventoryView.addComponent(pnl);
		this.pnl_Panel = pnl;
		this.hsp_JTrackInventoryView.setLocked(true);
		this.hsp_JTrackInventoryView.setWidth("100%");
		this.resizeComponentHeight(hsp_JTrackInventoryView,80);
		/* GridLayout */
		this.grd_JTrackInventoryView = new GridLayout(1,2);
		this.grd_JTrackInventoryView.addComponent(this.chb_ChangeSplitPanelSize,0,1);
		this.grd_JTrackInventoryView.addComponent(this.hsp_JTrackInventoryView,0,0);
		this.grd_JTrackInventoryView.setComponentAlignment(this.chb_ChangeSplitPanelSize,Alignment.TOP_LEFT);
		this.grd_JTrackInventoryView.setComponentAlignment(this.hsp_JTrackInventoryView,Alignment.TOP_LEFT);
		this.grd_JTrackInventoryView.setHeight("100%");
		this.grd_JTrackInventoryView.setWidth("99%");
		this.setContent(this.grd_JTrackInventoryView);
		this.setSizeFull();
	}
	private void setMaterialComponentObjectTreeTable(
			Object trt_JTrackInventoryView_Structure_MaterialComponentObject,
			List<MaterialComponentDTO> lst_MaterialComponent,
			List<MaterialComponentObjectDTO> lst_MaterialComponentObject) {
		/* load the MaterialComponentObject in the map */
		this.mapMaterialComponentObject = new TreeMap<String, MaterialComponentObjectDTO>();
		for(MaterialComponentObjectDTO mCompObjDTO : lst_MaterialComponentObject){
			this.mapMaterialComponentObject.put(mCompObjDTO.getUniqueId(), mCompObjDTO);
		}
		/* load the MaterialComponent in the map */
		this.mapMaterialComponent = new TreeMap<String, MaterialComponentDTO>();
		for(MaterialComponentDTO mCompDTO : lst_MaterialComponent){
			this.mapMaterialComponent.put(mCompDTO.getUniqueId(), mCompDTO);
		}
		
	}
	/**
	 * Method will resize the object to a certain percentage of the depending on the height of the window
	 * @param object as Component
	 * @param percentage as int
	 */
	private void resizeComponentHeight(Component object, int percentage){
		/* Check integer */
		String browserheightInProcent = "200px";
		try{
			if(null != object){
				if(0 < percentage){
					double value = percentage * 0.01;
					browserheightInProcent = Double.toString(BrowserUtil.getBrowserHeight() * value);
					/* calculate the height of the object */
					if(browserheightInProcent.indexOf(".") > -1){
						browserheightInProcent = browserheightInProcent.substring(0, browserheightInProcent.indexOf("."));
					}
				}
			}
		}catch(Exception e){
			if(null != object){
				object.setHeight("100px");
				Notification.show("trouble resizing the component!");
			}
		}
		/* Set the object height in percent */
		object.setHeight(browserheightInProcent);
	}
	@Override
	public void enter(ViewChangeEvent event) {
	}
	/*
	 * Method will get all the properties out the database
	 */
	private void setPropertyTreeTable(Object baseObject, List<PropertyDTO> propertyDTOList){
		/* Check the properties */
		if(null != propertyDTOList && null != baseObject) {
			/* initiate the mapPropertyDTO */
			this.mapProperty = new TreeMap<String, PropertyDTO>();
			/* add the object to the view */
			for(PropertyDTO dto : propertyDTOList){
				/* add the value to the TreeTable */
				Object object = this.trt_JTrackInventoryView_Base.addItem(new Object[] { "      " + dto.getCaption(),dto.getUniqueId()}, null);
				setItemIcon(object, Icon.iconProperty, this.trt_JTrackInventoryView_Base);
				/* set the table Structure */
				this.trt_JTrackInventoryView_Base.setParent(object, baseObject);
				/* set the mapProperty */
				this.mapProperty.put(dto.getUniqueId(), dto);
			}
		}else{
			Notification.show("null");
		}
	}
	/*
	 * Method will get all the PropertyGroups out the database
	 */
	private void setPropertyGroupTreeTable(Object baseObject, List<PropertyGroupDTO> propertyGroupDTOList){
		/* Check the properties */
		if(null != propertyGroupDTOList && null != baseObject) {
			/* initiate the mapPropertyDTO */
			this.mapPropertyGroup = new TreeMap<String, PropertyGroupDTO>();
			/* add the object with no parents in the TreeTable */
			for(PropertyGroupDTO dto : propertyGroupDTOList){
				Object object = this.trt_JTrackInventoryView_Base.addItem(new Object[] { "      " + dto.getCaption(),dto.getUniqueId()}, null);
				setItemIcon(object, Icon.iconPropertyGroup, this.trt_JTrackInventoryView_Base);
				this.trt_JTrackInventoryView_Base.setParent(object, baseObject);
				this.mapPropertyGroup.put(dto.getUniqueId(),dto);
			}
		}
	}
	/*
	 * Method will get all the PropertyGroups out the database
	 */
	private void setMaterialComponentTreeTable(Object baseObject, List<MaterialComponentDTO> materialComponentDTOList){
		/* Check the MaterialComponent */
		if(null != materialComponentDTOList && null != baseObject) {
			/* initiate the mapPropertyDTO */
			this.mapMaterialComponent = new TreeMap<String, MaterialComponentDTO>();
			/* add the object with no parents in the TreeTable */
			for(MaterialComponentDTO dto : materialComponentDTOList){
				Object object = this.trt_JTrackInventoryView_Base.addItem(new Object[] { "      " + dto.getCaption(),dto.getUniqueId()}, null);
				setItemIcon(object, Icon.iconMaterialComponent, this.trt_JTrackInventoryView_Base);
				this.trt_JTrackInventoryView_Base.setParent(object, baseObject);
				this.mapMaterialComponent.put(dto.getUniqueId(),dto);
			}
		}
	}
	/*
	 * Method will get all the PropertyGroups out the database
	 */
	private void setMaterialTreeTable(Object baseObject, List<MaterialDTO> materialDTOList){
		/* Check the MaterialComponent */
		if(null != materialDTOList && null != baseObject) {
			/* initiate the mapPropertyDTO */
			this.mapMaterial = new TreeMap<String, MaterialDTO>();
			/* add the object with no parents in the TreeTable */
			for(MaterialDTO dto : materialDTOList){
				Object object = this.trt_JTrackInventoryView_Base.addItem(new Object[] { "      " + dto.getCaption(),dto.getUniqueId()}, null);
				setItemIcon(object, Icon.iconMaterial, this.trt_JTrackInventoryView_Base);
				this.trt_JTrackInventoryView_Base.setParent(object, baseObject);
				this.mapMaterial.put(dto.getUniqueId(),dto);
			}
		}
	}
	/*
	 * Method will get all the materials
	 */
	private void setMaterialObjectTreeTable(Object baseObject, List<MaterialDTO> materialDTOList, List<MaterialObjectDTO> materialObjectDTOList) {
		/* load the values in the map */
		this.mapValue = new TreeMap<String, ValueDTO>();
		for(ValueDTO valDTO : listValue){
			this.mapValue.put(valDTO.getUniqueId(), valDTO);
		}
		/* load the materialObjectDTO */
		if(null != materialObjectDTOList ){
			/* add the MaterialObjects part of the Material */
			this.mapMaterialObject = new TreeMap<String, MaterialObjectDTO>();
			for(MaterialObjectDTO matObjDTO : materialObjectDTOList){
				this.mapMaterialObject.put(matObjDTO.getUniqueId(), matObjDTO);
			}
		}
		/* Check the MaterialComponent */
		if(null != materialDTOList && null != baseObject) {
			/* initiate the mapPropertyDTO */
			this.mapMaterial = new TreeMap<String, MaterialDTO>();
			/* add the object with no parents in the TreeTable */
			for(MaterialDTO dto : materialDTOList){
				Object object = this.trt_JTrackInventoryView_Structure.addItem(new Object[] { "      " + dto.getCaption(),dto.getUniqueId()}, null);
				setItemIcon(object, Icon.iconMaterial, this.trt_JTrackInventoryView_Structure);
				this.trt_JTrackInventoryView_Structure.setParent(object, baseObject);
				this.mapMaterial.put(dto.getUniqueId(),dto);
				if(null != this.mapMaterialObject){
					for(MaterialObjectDTO matObjdto : this.mapMaterialObject.values()){
						if(null != matObjdto.getMaterialDTO()){
							if(matObjdto.getMaterialDTO().getUniqueId().equals(dto.getUniqueId())){
								Object child = this.trt_JTrackInventoryView_Structure.addItem(new Object[] { "      " + matObjdto.getCaption(),matObjdto.getUniqueId()}, null);
								setItemIcon(child, Icon.iconMaterialObject, this.trt_JTrackInventoryView_Structure);
								this.trt_JTrackInventoryView_Structure.setParent(child, object);
							}
						}
					}
				}
			}
		}
	}
	/*
	 * Method will set the icon of a TreeTable
	 */
	private void setItemIcon(Object item, Resource icon, TreeTable table){
		/* check the values */
		if(null != item){
			if(null != table){
				if(null != icon){
					/* Set the Icon */
					table.setItemIcon(item, icon);
				}
			}
		}
	}
}
