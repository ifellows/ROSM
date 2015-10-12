package edu.cens.spatial;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import org.rosuda.JGR.JGR;
import org.rosuda.deducer.Deducer;
import org.rosuda.deducer.data.DataViewerController;
import org.rosuda.deducer.widgets.param.Param;
import org.rosuda.deducer.widgets.param.ParamCharacter;
import org.rosuda.deducer.widgets.param.ParamColor;
import org.rosuda.deducer.widgets.param.ParamNumeric;
import org.rosuda.deducer.widgets.param.ParamVector;
import org.rosuda.deducer.widgets.param.RFunction;
import org.rosuda.deducer.widgets.param.RFunctionList;
import org.rosuda.ibase.toolkit.EzMenuSwing;

import edu.cens.spatial.plots.ColoredPointsElementModel;
import edu.cens.spatial.plots.PointsElementModel;
import edu.cens.spatial.plots.SpatialPlotBuilder;

public class DeducerSpatial
{

	//protected static SpatialPlotBuilder spatialWindow = null; 
	
	public static ActionListener cListener = new SpatialMenuListener();

	public static void init()
	{
		try
		{
			if (Deducer.isJGR())
			{
				int menuIndex = 6;
				insertMenu(JGR.MAINRCONSOLE, "Spatial", menuIndex);
				EzMenuSwing.addJMenuItem(JGR.MAINRCONSOLE, "Spatial", "Load Shape File", "shape", cListener);
				EzMenuSwing.addJMenuItem(JGR.MAINRCONSOLE, "Spatial","Load Census Data", "census", cListener);
				EzMenuSwing.getMenu(JGR.MAINRCONSOLE, "Spatial").addSeparator();		
				EzMenuSwing.addJMenuItem(JGR.MAINRCONSOLE, "Spatial","Data -> Spatial", 
						"conv_pnt", cListener);
				EzMenuSwing.addJMenuItem(JGR.MAINRCONSOLE, "Spatial","Spatial -> Data", 
						"conv_df", cListener);
				EzMenuSwing.getMenu(JGR.MAINRCONSOLE, "Spatial").addSeparator();
				EzMenuSwing.addJMenuItem(JGR.MAINRCONSOLE, "Spatial", "Spatial Plot Builder", "builder", cListener);
			}
			DataViewerController.init();
			DataViewerController.addDataType("SpatialPointsDataFrame", "sp-p");
			DataViewerController.addTabFactory("SpatialPointsDataFrame",
					"Data View", new SpatialDataViewFactory());
			DataViewerController.addTabFactory("SpatialPointsDataFrame",
					"Variable View", new SpatialVariableViewFactory());
			DataViewerController.addTabFactory("SpatialPointsDataFrame",
					"Coordinates", new CoordViewFactory(false));

			DataViewerController.addDataType("SpatialPolygonsDataFrame",
					"sp-py");
			DataViewerController.addTabFactory("SpatialPolygonsDataFrame",
					"Data View", new SpatialDataViewFactory());
			DataViewerController.addTabFactory("SpatialPolygonsDataFrame",
					"Variable View", new SpatialVariableViewFactory());
			DataViewerController.addTabFactory("SpatialPolygonsDataFrame",
					"Centroids", new CoordViewFactory(false));

			DataViewerController.addDataType("SpatialLinesDataFrame", "sp-l");
			DataViewerController.addTabFactory("SpatialLinesDataFrame",
					"Data View", new SpatialDataViewFactory());
			DataViewerController.addTabFactory("SpatialLinesDataFrame",
					"Variable View", new SpatialVariableViewFactory());
			DataViewerController.addTabFactory("SpatialLinesDataFrame",
					"Paths", new CoordViewFactory(true));
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	
	public static boolean rgdalCheck(){
		if(!Deducer.isLoaded("rgdal")){
			if(Deducer.isInstalled("rgdal")){
				Deducer.execute("library(rgdal)");
				return true;
			}else{
				int inst = JOptionPane.showOptionDialog(null, "The package rgdal is required for most functionality in Deducer Spatial. Would you like to install it now?",
						"Install", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE,null,
						new String[]{"Yes","No"}, "Yes");
				if(inst==JOptionPane.OK_OPTION){
					if(System.getProperty("os.name").startsWith("Mac"))
						Deducer.execute("setRepositories(ind=c(9,2))\ninstall.packages('rgdal')\nlibrary(rgdal)");
					else
						Deducer.execute("install.packages('rgdal')\nlibrary(rgdal)");
				}
				return false;
			}
		}else
			return true;
	}

	public static void insertMenu(JFrame f, String name, int index)
	{
		JMenuBar mb = f.getJMenuBar();
		JMenu m = EzMenuSwing.getMenu(f, name);
		if (m == null && index < mb.getMenuCount())
		{
			JMenuBar mb2 = new JMenuBar();
			int cnt = mb.getMenuCount();
			for (int i = 0; i < cnt; i++)
			{
				if (i == index)
				{
					mb2.add(new JMenu(name));
				}
				mb2.add(mb.getMenu(0));
			}
			f.setJMenuBar(mb2);
		}
		else if (m == null && index == mb.getMenuCount())
		{
			EzMenuSwing.addMenu(f, name);
		}
	}

	public static void main(String[] args)
	{
		final SpatialPlotBuilder b = new SpatialPlotBuilder();
		JPanel temp = new JPanel();
		temp.add(new JButton("subset") // the menu doesn't work so add a button.
		{
			{
				addActionListener(new ActionListener()
				{
					public void actionPerformed(ActionEvent e)
					{
						b.beginSubsetting();
					}
				});
			}
		});
		JFrame f = new JFrame();
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.add(b.getContentPane(), BorderLayout.CENTER);
		f.add(temp,BorderLayout.SOUTH);
		//f.pack();
		f.setVisible(true);
		//b.setVisible(true);
	}
	
	public static Param makeColorScaleParam(){
		RFunction rf;
		ParamCharacter pc;
		ParamNumeric pn;
		ParamVector pv;
		ParamColor p;
		RFunctionList pfl = new RFunctionList();
		pfl.setName("palette");
		pfl.setTitle("Palette");
		pfl.setRequired(false);
		
		
		//// Brewer
		rf = new RFunction("brewer_pal");
		rf.setTitle("Color brewer");
		pc = new ParamCharacter("palette");
		pc.setViewType(Param.VIEW_COMBO);
		pc.setValue("Blues");
		pc.setDefaultValue("Blues");
		pc.setOptions(new String[]{"YlOrRd","YlOrBr","YlGnBu","YlGn","Reds","RdPu",
				"Pruples","PuRd","PuBuGn","PuBu","OrRd","Oranges","Greys","Greens",
				"GnBu","BuPu","BuGn","Blues","","Set3","Set2","Set1","Pastel2","Pastel1",
				"Paired","Dark2","Accent","","Spectral","RdYlGn","RdYlBu","RdGy",
				"RdBu","PuOr","PRGn","PiYG","BrBG"});
		rf.add(pc);
		pfl.addRFunction("Color brewer", rf);
		
		
		//// Hue
		rf = new RFunction("hue_pal");
		rf.setTitle("Hue");
		pv = new ParamVector();
		pv.setName("h");
		pv.setTitle("Hue range");
		pv.setViewType(Param.VIEW_TWO_VALUE_ENTER);
		pv.setValue(new String[]{"15","375"});
		pv.setDefaultValue(new String[]{"15","375"});
		rf.add(pv);		
		
		pn = new ParamNumeric();
		pn.setName("l");
		pn.setTitle("Luminance [0, 100]");
		pn.setViewType(Param.VIEW_ENTER);
		pn.setValue(new Double(65));
		pn.setDefaultValue(new Double(65));
		pn.setLowerBound(new Double(0));
		pn.setUpperBound(new Double(100));
		rf.add(pn);
		
		pn = new ParamNumeric();
		pn.setName("c");
		pn.setTitle("Chroma");
		pn.setViewType(Param.VIEW_ENTER);
		pn.setValue(new Double(100));
		pn.setDefaultValue(new Double(100));
		pn.setLowerBound(new Double(0));
		rf.add(pn);
		
		pn = new ParamNumeric();
		pn.setName("h.start");
		pn.setTitle("Hue start");
		pn.setViewType(Param.VIEW_ENTER);
		pn.setValue(new Double(0));
		pn.setDefaultValue(new Double(0));
		pn.setLowerBound(new Double(0));
		rf.add(pn);

		pn = new ParamNumeric();
		pn.setName("direction");
		pn.setTitle("Colour wheel direction");
		pn.setViewType(Param.VIEW_COMBO);
		pn.setValue(new Double(1.0));
		pn.setDefaultValue(new Double(1.0));
		pn.setOptions(new String[] {"1.0","-1.0"});
		pn.setLabels(new String[] {"clockwise","counter clockwise"});
		rf.add(pn);
		pfl.addRFunction("Hue", rf);

		////sequential
		rf = new RFunction("seq_gradient_pal");
		rf.setTitle("Sequential");
		
		p = new ParamColor();
		p.setName("low");
		p.setTitle("Low");
		p.setViewType(Param.VIEW_COLOR);
		p.setValue(Color.decode("#132B43"));
		p.setDefaultValue(Color.decode("#132B43"));
		rf.add(p);

		p = new ParamColor();
		p.setName("high");
		p.setTitle("High");
		p.setViewType(Param.VIEW_COLOR);
		p.setValue(Color.decode("#56B1F7"));
		p.setDefaultValue(Color.decode("#56B1F7"));
		rf.add(p);
		
		pc = new ParamCharacter();
		pc.setName("space");
		pc.setTitle("Space");
		pc.setViewType(Param.VIEW_COMBO);
		pc.setValue("rgb");
		pc.setDefaultValue("rgb");
		pc.setOptions(new String[] {"rgb","Lab"});
		rf.add(pc);
		pfl.addRFunction("Sequential", rf);
		
		return pfl;
	}
	
	
	
}

class SpatialMenuListener implements ActionListener
{


	public void actionPerformed(ActionEvent act)
	{
		String cmd = act.getActionCommand();
		if (cmd.equals("shape"))
		{
			ShapeFileLoader sfl = new ShapeFileLoader();
			sfl.setVisible(true);
			sfl.runInR();
		}
		else if (cmd.equals("conv_pnt"))
		{
			DFPointConvertDialog conv = DFPointConvertDialog.getInstance();;
			conv.run();
		}
		else if (cmd.equals("builder"))
		{
//			if (DeducerSpatial.spatialWindow == null)
//			{
//				DeducerSpatial.spatialWindow = new SpatialPlotBuilder();
//			}
//			DeducerSpatial.spatialWindow.setVisible(true);
			
			SpatialPlotBuilder b = new SpatialPlotBuilder();
			b.setVisible(true);
		}else if(cmd.equals("census")){
			Deducer.timedEval(".getDialog(\"Load Census Data\")$run()");
		}else if(cmd.equals("conv_df")){
			ConvertToDataFrame.runConvertDialog();
		}
//		else if (cmd.equals("subset"))
//		{
//			//The subset menu item should be grayed out if the window is not open.
//			if (DeducerSpatial.spatialWindow != null)
//			{
//				//setCursor(new Cursor(Cursor.CROSSHAIR_CURSOR));
//				DeducerSpatial.spatialWindow.beginSubsetting();
//			}
//		}
	}

}
