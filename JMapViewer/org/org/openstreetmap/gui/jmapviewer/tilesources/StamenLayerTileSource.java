package org.openstreetmap.gui.jmapviewer.tilesources;

import org.openstreetmap.gui.jmapviewer.Coordinate;
import org.openstreetmap.gui.jmapviewer.interfaces.TileSource.TileUpdate;

public class StamenLayerTileSource extends AbstractOsmTileSource{
	private String exten;
	public StamenLayerTileSource(String layer,String ext){
		super("stamen-" + layer,"http://tile.stamen.com/"+layer);
		this.exten=ext;
	}
	
    public TileUpdate getTileUpdate() {
        return TileUpdate.IfNoneMatch;
    }
    
    @Override
    public int getMaxZoom() {
        return 15;
    }

    public String getAttributionText(int zoom, Coordinate topLeft, Coordinate botRight) {
        return "© Map tiles by Stamen Design, under CC BY 3.0. Data by OpenStreetMap, under CC BY SA.";
    }

    public String getAttributionLinkURL() {
        return "http://maps.stamen.com/";
    }

    public String getTermsOfUseURL() {
        return "http://maps.stamen.com/";
    }
    
    public String getTileType() {
        return exten;
    }
}