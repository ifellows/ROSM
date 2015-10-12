package edu.cens.spatial;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.util.Collection;

import javax.swing.BoxLayout;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JMenuBar;
import javax.swing.JRootPane;
import javax.swing.JScrollPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;

import org.rosuda.JGR.JGR;
import org.rosuda.JGR.JGRObjectManager;
import org.rosuda.JGR.RController;
import org.rosuda.JGR.robjects.RObject;
import org.rosuda.JGR.toolkit.DataTable;
import org.rosuda.JGR.toolkit.ObjectBrowserTree;
import org.rosuda.REngine.REXPMismatchException;
import org.rosuda.deducer.Deducer;
import org.rosuda.deducer.data.DataViewerTab;

public class CoordView extends DataViewerTab {

	JScrollPane pane;
	String dat;
	boolean list;
	JList lis;
	CoordView(String data,boolean listCoords){
		super();
		list=listCoords;
		setData(data);
		//JGRObjectManager m = new JGRObjectManager();
		
		//oBrowser = new ObjectBrowserTree(null, JGR.OTHERS, "other");
		//pane = new JScrollPane(oBrowser);
		//this.add(pane);
	}
	
	public void setData(String data) {
		this.removeAll();
		dat = data;
		if(!list){
			
			this.setLayout(new BorderLayout());
			RObject o = new RObject("coordinates("+dat+")","matrix",false);
			org.rosuda.ibase.SVarSet vs = RController.newSet(o);
			DataTable dt = new DataTable(vs, o.getType(), false,false);
			//JRootPane rp = dt.getRootPane();
			Container rp = dt.getContentPane();
			pane = new JScrollPane(rp);
			this.add(pane);
		}else{
			this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
			int l=0;
			try {
				l=Deducer.timedEval("length("+dat+")").asInteger();
			} catch (REXPMismatchException e) {
				e.printStackTrace();
			}
			
			Object[] buttons = new Object[l];
			for(int i=0;i<l;i++){
				JButton b = new JButton((i+1)+"");
				buttons[i] = b;
			//	b.addActionListener(bl);
			}
			lis = new JList(buttons);
			lis.setCellRenderer(new ButtonRenderer());
			pane = new JScrollPane(lis);
			pane.setMaximumSize(new Dimension(100,10000));
			lis.addListSelectionListener(new ButtonListener());
			this.add(pane);
		}
	}

	public void refresh() {
		// TODO Auto-generated method stub

	}

	public JMenuBar generateMenuBar() {
		// TODO Auto-generated method stub
		return null;
	}

	public void cleanUp() {
		// TODO Auto-generated method stub

	}
	class ButtonListener implements ListSelectionListener{

		public void valueChanged(ListSelectionEvent arg0) {
			if(arg0.getValueIsAdjusting())
				return;
			String index = lis.getSelectedIndex()+"";
			int i = -1;
			//System.out.println("index:"+index);
			i = Integer.parseInt(index);
			RObject o = new RObject("coordinates("+dat+")[["+(i+1)+"]][[1]]","matrix",false);
			org.rosuda.ibase.SVarSet vs = RController.newSet(o);
			new DataTable(vs, o.getType(), false);
		}
		
	}	

}

class ButtonRenderer extends DefaultListCellRenderer {
	public Component getListCellRendererComponent(JList list,
            Object value,
            int index,
            boolean isSelected,
            boolean hasFocus) {
		return (Component) value;
	}
}

