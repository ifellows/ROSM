package edu.cens.spatial;



import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.DefaultCellEditor;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JOptionPane;
import javax.swing.JTable;

import org.rosuda.JGR.DataLoader;
import org.rosuda.JGR.RController;
import org.rosuda.JGR.editor.Editor;
import org.rosuda.JGR.toolkit.AboutDialog;
import org.rosuda.JGR.util.ErrorMsg;
import org.rosuda.deducer.Deducer;
import org.rosuda.deducer.data.DataViewer;
import org.rosuda.deducer.data.VariableView;
import org.rosuda.deducer.toolkit.SaveData;
import org.rosuda.ibase.Common;
import org.rosuda.ibase.toolkit.EzMenuSwing;

public class SpatialVariableView extends VariableView {

	public SpatialVariableView(String dn){
		super(dn + "@data");	
	}
	
	public void setData(String data) {

		super.setData(data+"@data");
	}
	
	public JMenuBar generateMenuBar() {
		JFrame f = new JFrame();
		String[] Menu = { "+", "File", "@NNew Data","newdata", "@LOpen Data", "loaddata","@SSave Data", "Save Data", "-",
				 "-","@PPrint","print","~File.Quit", 
				"+","Edit","@CCopy","copy","@XCut","cut", "@VPaste","paste","-","Remove Data from Workspace", "Clear Data",
				"~Window", "+","Help","R Help","help", "~About","0" };
			JMenuBar mb = EzMenuSwing.getEzMenu(f, this, Menu);
			
			//preference and about for non-mac systems
			if(!Common.isMac()){
				EzMenuSwing.addMenuSeparator(f, "Edit");
				EzMenuSwing.addJMenuItem(f, "Help", "About", "about", this);	
				for(int i=0;i<mb.getMenuCount();i++){
					if(mb.getMenu(i).getText().equals("About")){
						mb.remove(i);
						i--;
					}
				}
			}
		return f.getJMenuBar();
	}

	public void actionPerformed(ActionEvent e) {
		//JGR.R.eval("print('"+e.getActionCommand()+"')");
		try{
			String cmd = e.getActionCommand();		
			if(cmd=="Open Data"){
				new DataLoader();	
			}else if(cmd=="Save Data"){
				new SaveData(dataName);
			}else if(cmd=="Clear Data"){
				if(dataName==null){
					JOptionPane.showMessageDialog(this, "Invalid selection: There is no data loaded.");
					return;
				}
				int confirm = JOptionPane.showConfirmDialog(null, "Remove Data Frame "+
						dataName+" from enviornment?\n" +
								"Unsaved changes will be lost.",
						"Clear Data Frame", JOptionPane.YES_NO_OPTION,
						JOptionPane.QUESTION_MESSAGE);
				if(confirm == JOptionPane.NO_OPTION)
					return;
				Deducer.execute("rm("+dataName + ")");
			}else if (cmd == "about")
				new AboutDialog(null);
			else if (cmd == "cut"){
					ex.cutSelection();
			}else if (cmd == "copy") {
					ex.copySelection();
			}else if (cmd == "paste") {
					ex.pasteSelection();
			} else if (cmd == "print"){
				try{
					ex.print(JTable.PrintMode.NORMAL);
				}catch(Exception exc){}
			}else if (cmd == "editor")
				new Editor();
			else if (cmd == "exit")
				((JFrame)this.getTopLevelAncestor()).dispose();
			else if(cmd=="newdata"){
				String inputValue = JOptionPane.showInputDialog("Data Name: ");
				inputValue = Deducer.getUniqueName(inputValue);
				if(inputValue!=null){
					Deducer.execute(inputValue.trim()+"<-data.frame(Var1=NA)");
				}
			}else if (cmd == "loaddata"){
				DataLoader dld= new DataLoader();
			}else if (cmd == "help")
				Deducer.execute("help.start()");
			else if (cmd == "table"){
				DataViewer inst = new DataViewer();
				inst.setLocationRelativeTo(null);
				inst.setVisible(true);
			}else if (cmd == "save")
				new SaveData(dataName);
			
		}catch(Exception e2){new ErrorMsg(e2);}
	}
	
	public void cleanUp() {
		
	}
}
