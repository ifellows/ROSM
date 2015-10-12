package edu.cens.spatial;

import org.rosuda.deducer.data.DataViewerTab;
import org.rosuda.deducer.data.DataViewerTabFactory;

public class CoordViewFactory  implements DataViewerTabFactory{
	boolean list;
	CoordViewFactory(boolean listCoords){
		list=listCoords;
	}
	
	public DataViewerTab makeViewerTab(String dataName) {
		return new CoordView(dataName,list);
	}

}
