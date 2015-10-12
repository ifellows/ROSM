package edu.cens.spatial;

import java.util.Vector;

import javax.swing.JOptionPane;

import org.rosuda.REngine.REXP;
import org.rosuda.REngine.REXPMismatchException;
import org.rosuda.deducer.Deducer;

public class ConvertToDataFrame {

	public static String last = null;
	public static String lastNewName = null;
	
	public static void runConvertDialog(){
		new Thread(new Runnable(){

			public void run() {
				try{
					Vector<String> datasets = new Vector<String>();
					String[] tmp;
					
					String cls = "SpatialPolygonsDataFrame";
					REXP rexp = Deducer.timedEval("get.objects('"+cls+"')");
					if(rexp!=null && rexp.isString()){
						tmp = rexp.asStrings();
						for(int i=0;i<tmp.length;i++)
							datasets.add(tmp[i]);
					}
					cls = "SpatialPointsDataFrame";
					rexp = Deducer.timedEval("get.objects('"+cls+"')");
					if(rexp!=null && rexp.isString()){
						tmp = rexp.asStrings();
						for(int i=0;i<tmp.length;i++)
							datasets.add(tmp[i]);
					}
					cls = "SpatialLinesDataFrame";
					rexp = Deducer.timedEval("get.objects('"+cls+"')");
					if(rexp!=null && rexp.isString()){
						tmp = rexp.asStrings();
						for(int i=0;i<tmp.length;i++)
							datasets.add(tmp[i]);
					}
					Object[] objs = datasets.toArray();
					Object result = JOptionPane.showInputDialog(null, "Choose spatial data to convert", 
							"Spatial -> data.frame", JOptionPane.PLAIN_MESSAGE, null, objs, last);
					if(result!=null){
						String newName;
						if(result.toString().equals(last) && lastNewName != null)
							newName = lastNewName;
						else
							newName = Deducer.getUniqueName(result+".df");
						newName = JOptionPane.showInputDialog("New data.frame name:", newName);
						if(newName==null)
							return;
						last = result.toString();
						lastNewName = newName;
						Deducer.execute(newName + " <- as.data.frame("+result+")");
					}
					
				}catch(Exception e){
					e.printStackTrace();
				}
			}
			
		}).start();
	}
	
	
}
