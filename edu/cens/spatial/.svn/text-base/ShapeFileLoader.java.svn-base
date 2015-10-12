package edu.cens.spatial;

import java.awt.FlowLayout;
import java.awt.Frame;
import java.io.File;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.filechooser.FileFilter;

import org.rosuda.JGR.RController;
import org.rosuda.JGR.toolkit.FileSelector;
import org.rosuda.deducer.Deducer;
import org.rosuda.deducer.toolkit.HelpButton;

public class ShapeFileLoader extends FileSelector{
	
	private static final String HELP_URL = "index.php?n=Main.SpatialLoadData";
	private JTextField rDataNameField;
	JTextField proj ;
	public ShapeFileLoader(Frame f) {
		super(f, "Load shape file", FileSelector.LOAD, null, true);
		JPanel namePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		namePanel.add(new JLabel("Set name: "));
		rDataNameField = new JTextField(12);
		namePanel.add(rDataNameField);
		namePanel.add(new JLabel("      Proj: "));

		proj = new JTextField(10);
		proj.setText("+proj=longlat +datum=WGS84");
		namePanel.add(proj);
		namePanel.add(new HelpButton(HELP_URL));
		//this.addFooterPanel(namePanel);
		/*
		 !path.toLowerCase().endsWith(".shp") &&
				!path.toLowerCase().endsWith(".shp") &&
				!path.toLowerCase().endsWith(".dbf") &&
				!path.toLowerCase().endsWith(".prj") &&
				!path.toLowerCase().endsWith(".sbn") &&
				!path.toLowerCase().endsWith(".sbx")
		 */
		
		if (this.isSwing())
		{
			this.getJFileChooser().addChoosableFileFilter(new FileFilter()
			{
				public String getDescription()
				{
					return "Shape Files (*.shp *.dbf *.prj *.sbn *.sbx)";
				}

				public boolean accept(File f)
				{
					String fname  = f.getName().toLowerCase();
					return 
					fname.toLowerCase().endsWith(".shp") ||
					fname.toLowerCase().endsWith(".dbf") ||
					fname.toLowerCase().endsWith(".prj") ||
					fname.toLowerCase().endsWith(".sbn") ||
					fname.toLowerCase().endsWith(".sbx") ||
					f.isDirectory();
				}
			});
		} //TODO handle the non-swing case
		
		DeducerSpatial.rgdalCheck();
	}
		
//		addChoosableFileFilter(new FileFilter()
//		{
//			public String getDescription()
//			{
//				return "Text files (*.txt *.rtf)";
//			}
//
//			public boolean accept(File f)
//			{
//				String fname  = f.getName().toLowerCase();
//				return 
//				fname.toLowerCase().endsWith(".shp") ||
//				fname.toLowerCase().endsWith(".shp") ||
//				fname.toLowerCase().endsWith(".dbf") ||
//				fname.toLowerCase().endsWith(".prj") ||
//				fname.toLowerCase().endsWith(".sbn") ||
//				fname.toLowerCase().endsWith(".sbx");
//			}
//		});
//		
//	}
	
	public ShapeFileLoader() {
		this(null);
	}
	
	public boolean runInR(){
		if (this.getFile() == null)
			return false;
		String rName = rDataNameField.getText();
		if (rName.length() == 0)
			rName = (this.getFile().indexOf(".") <= 0 ? Deducer.getUniqueName(this.getFile()) : 
					Deducer.getUniqueName(this.getFile().substring(0, this.getFile().indexOf("."))));
		rName = RController.makeValidVariableName(rName);
		String fileName = Deducer.addSlashes(this.getFile());
		String path =this.getDirectory().replace('\\', '/');
		if(!fileName.toLowerCase().endsWith(".shp") &&
				!fileName.toLowerCase().endsWith(".shp") &&
				!fileName.toLowerCase().endsWith(".dbf") &&
				!fileName.toLowerCase().endsWith(".prj") &&
				!fileName.toLowerCase().endsWith(".sbn") &&
				!fileName.toLowerCase().endsWith(".sbx")){
			JOptionPane.showMessageDialog(this, "This does not appear to be a shape file.\nAcceptable extensions are: .shp,.dbf,.prj,.sbx,.sbn");
			return false;
		}
		
		String fileNoExt = fileName.substring(0, fileName.length()-4);
		
		String command =rName +" <- readOGR(\""+Deducer.addSlashes(path)+
			"\", layer=\""+fileNoExt+"\")\n";
		command += rName +" <- spTransform("+rName+",osm())";
		Deducer.execute(command);
		
		return true;
	}
	
}
