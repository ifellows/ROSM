package edu.cens.spatial;

import org.rosuda.REngine.REXPMismatchException;
import org.rosuda.deducer.Deducer;
import org.rosuda.deducer.toolkit.VariableSource;

public class SpatialVariableSource implements VariableSource{

	String className = "SpatialPointsDataFrame";
	
	public SpatialVariableSource(){}
	
	public SpatialVariableSource(String cls){
		className = cls;
	}
	
	public String[] getDataObjects() {
		String[] vals = new String[]{};
		try {
			vals = Deducer.timedEval("get.objects('" +className + "',includeInherited=TRUE)" ).asStrings();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return vals;
	}

	public String[] getVariableNames(String data) {
		String[] vals = new String[]{};
		try {
			vals = Deducer.timedEval("names("+data+"@data)" ).asStrings();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return vals;
	}

	public String fix(String data, String variable) {
		return data+"@data[[\""+variable+"\"]]";
	}

}
