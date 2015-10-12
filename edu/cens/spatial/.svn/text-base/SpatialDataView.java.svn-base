package edu.cens.spatial;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;

import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import javax.swing.JSeparator;
import javax.swing.JTable;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumnModel;

import org.rosuda.JGR.DataLoader;
import org.rosuda.JGR.RController;
import org.rosuda.JGR.editor.Editor;
import org.rosuda.JGR.toolkit.AboutDialog;
import org.rosuda.JGR.util.ErrorMsg;

import org.rosuda.deducer.Deducer;
import org.rosuda.deducer.data.ColumnHeaderListener;
import org.rosuda.deducer.data.DataView;
import org.rosuda.deducer.data.DataViewer;
import org.rosuda.deducer.data.DataViewerTab;
import org.rosuda.deducer.data.ExScrollableTable;
import org.rosuda.deducer.data.ExTable;
import org.rosuda.deducer.data.RDataFrameModel;
import org.rosuda.deducer.data.DataView.DataViewColumnHeaderListener;
import org.rosuda.deducer.data.DataView.DataViewColumnHeaderListener.ColumnContextMenu;
import org.rosuda.deducer.data.RDataFrameModel.RCellRenderer;
import org.rosuda.deducer.toolkit.SaveData;

import org.rosuda.ibase.Common;
import org.rosuda.ibase.toolkit.EzMenuSwing;

public class SpatialDataView extends DataView implements ActionListener{

	SpatialDataView(String dataName) {
		super(dataName + "@data");
	}

	protected void init(String dataName){
		this.dataName = dataName;		
		RDataFrameModel dataModel = new RDataFrameModel(dataName){
			public int getRowCount(){
				return getRealRowCount();
			}
		};
		dataScrollPane = new ExScrollableTable(table =new ExTable(dataModel));
		dataScrollPane.setRowNamesModel(((RDataFrameModel) dataScrollPane.
				getExTable().getModel()).getRowNamesModel());
		dataScrollPane.getExTable().setDefaultRenderer(Object.class,
				dataModel.new RCellRenderer());
		dataScrollPane.displayContextualMenu(false);
		//table.setColumnListener(new DataViewColumnHeaderListener(table));
		this.setLayout(new BorderLayout());
		this.add(dataScrollPane);
		
	}

	public void setData(String data) {
		dataName = data + "@data";
		RDataFrameModel dataModel = new RDataFrameModel(dataName){
			public int getRowCount(){
				return getRealRowCount();
			}
		};
		((RDataFrameModel)table.getModel()).removeCachedData();
		table.setModel(dataModel);
		dataScrollPane.setRowNamesModel(((RDataFrameModel) dataScrollPane.
				getExTable().getModel()).getRowNamesModel());		
		dataScrollPane.getExTable().setDefaultRenderer(Object.class,
				dataModel.new RCellRenderer());
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
				//RController.refreshObjects();
			}else if (cmd == "about")
				new AboutDialog(null);
			else if (cmd == "cut"){
					table.cutSelection();
			}else if (cmd == "copy") {
					table.copySelection();
			}else if (cmd == "paste") {
					table.pasteSelection();
			} else if (cmd == "print"){
				try{
					table.print(JTable.PrintMode.NORMAL);
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
					//RController.refreshObjects();
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
		((RDataFrameModel)table.getModel()).removeCachedData();
	}
	
	
	public class DataViewColumnHeaderListener extends ColumnHeaderListener  {
		private ExTable table;
		private JPopupMenu menu;
		
		public DataViewColumnHeaderListener(ExTable t){
			table = t;
			JTableHeader header = table.getTableHeader();
			header.addMouseListener(this);
		}
		
		public void remove(){
			table.getTableHeader().removeMouseListener(this);
		}
		
		
		public void mouseClicked(MouseEvent evt){
			boolean isMac = System.getProperty("java.vendor").indexOf("Apple")>-1;
			TableColumnModel colModel = table.getColumnModel();		
			int vColIndex = colModel.getColumnIndexAtX(evt.getX());
			int mColIndex = table.convertColumnIndexToModel(vColIndex);	
			table.selectColumn(vColIndex);
			
			if(evt.getButton()==MouseEvent.BUTTON3 && !isMac){
				new ColumnContextMenu(evt);
			}
		}
		
		
		public void mousePressed(MouseEvent evt){
			boolean isMac = System.getProperty("java.vendor").indexOf("Apple")>-1;
			if(evt.isPopupTrigger() && isMac){
				new ColumnContextMenu(evt);	
			}
		}

		/** 
		 * The popup menu for column headers.
		 * 
		 * @author ifellows
		 *
		 */
		class ColumnContextMenu  implements ActionListener{
			int vColIndex,mColIndex;
			
			public ColumnContextMenu(MouseEvent evt){
				TableColumnModel colModel = table.getColumnModel();		
				vColIndex = colModel.getColumnIndexAtX(evt.getX());
				mColIndex = table.convertColumnIndexToModel(vColIndex);	
				menu = new JPopupMenu();
				table.getTableHeader().add(menu);
				
				JMenuItem sortItem = new JMenuItem ("Sort (Increasing)");
				sortItem.addActionListener(this);
				//menu.add( sortItem );
				sortItem = new JMenuItem ("Sort (Decreasing)");
				sortItem.addActionListener(this);
				//menu.add( sortItem );
				//menu.add( new JSeparator() );
				JMenuItem copyItem = new JMenuItem ("Copy");
				copyItem.addActionListener(this);
				menu.add( copyItem );
				JMenuItem cutItem = new JMenuItem ("Cut");
				cutItem.addActionListener(this);
				menu.add( cutItem );
				JMenuItem pasteItem = new JMenuItem ("Paste");
				pasteItem.addActionListener(this);
				menu.add ( pasteItem );
				menu.addSeparator();
				JMenuItem insertItem = new JMenuItem ("Insert");
				insertItem.addActionListener(this);
				menu.add( insertItem );
				JMenuItem insertNewItem = new JMenuItem ("Insert New Column");
				insertNewItem.addActionListener(this);
				menu.add( insertNewItem );
				JMenuItem removeItem = new JMenuItem ("Remove Column");
				removeItem.addActionListener(this);
				menu.add( removeItem );
				
				menu.show(evt.getComponent(), evt.getX(), evt.getY());
			}
			
			public void actionPerformed(ActionEvent e){
				
				JMenuItem source = (JMenuItem)(e.getSource());
				if(source.getText()=="Copy"){
					table.getCopyPasteAdapter().copy();
				} else if(source.getText()=="Cut"){
					table.cutColumn(vColIndex);
				} else if(source.getText()=="Paste"){
					table.getCopyPasteAdapter().paste();
				} else if(source.getText()=="Insert"){
					table.insertColumn(vColIndex);
				} else if(source.getText()=="Insert New Column"){
					table.insertNewColumn(vColIndex);
				} else if(source.getText()=="Remove Column"){
					table.removeColumn(vColIndex);
				} else if(source.getText().equals("Sort (Increasing)")){
					JOptionPane.showMessageDialog(null, "Sorting not supported for spatial data");
					//String cmd = dataName + " <- sort(" + dataName + ", by=~" +
					//	table.getColumnName(vColIndex).trim() + ")";
					//Deducer.execute(cmd);
				} else if(source.getText().equals("Sort (Decreasing)")){
					JOptionPane.showMessageDialog(null, "Sorting not supported for spatial data");
					//String cmd = dataName + " <- sort(" + dataName + ", by=~ -" +
					//table.getColumnName(vColIndex).trim() + ")";
					//Deducer.execute(cmd);
				}
				menu.setVisible(false);
			}
			
		}
		
		
	}

}