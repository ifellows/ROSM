package edu.cens.spatial;

import java.util.HashMap;

import org.openstreetmap.gui.jmapviewer.OsmMercator;
import org.openstreetmap.gui.jmapviewer.Tile;
import org.openstreetmap.gui.jmapviewer.MemoryTileCache;
import org.openstreetmap.gui.jmapviewer.tilesources.*;
import org.openstreetmap.gui.jmapviewer.tilesources.OsmTileSource.*;
import org.openstreetmap.gui.jmapviewer.TileController;
import org.openstreetmap.gui.jmapviewer.interfaces.TileCache;
import org.openstreetmap.gui.jmapviewer.interfaces.TileLoaderListener;
import org.openstreetmap.gui.jmapviewer.interfaces.TileSource;

public class RTileController extends TileController{

	public static HashMap<String,RTileController> map;
	public static TileSource[] sources;
	
	public RTileController(TileSource source, TileCache tileCache,
			TileLoaderListener listener) {
		super(source, tileCache, listener);
		tileSource = source;
	}

	public static RTileController getInstance(String type){
		if(type.equals("bing")){
			type = "Bing Aerial Maps";
		}
		if(map==null){
			map = new HashMap<String,RTileController>();
			sources = new TileSource[] { 
	        		new OsmTileSource.Mapnik(),
	        		new OsmTileSource.MapnikBw(),
	        		new OsmTileSource.MapToolKitTopo(),
	        		new OsmTileSource.Waze(),
	        		new MapQuestTileSource(),
	        		new MapQuestAerialTileSource(),
	                new BingAerialTileSource(),
	        		new StamenTileSource.Toner(),
	        		new StamenTileSource.Terrain(),
	        		new StamenTileSource.WaterColor(),
	        		new OsmTileSource.MapnikGerman(),
	        		new OsmTileSource.Wanderreitkarte(),
	        		new OsmTileSource.MapBox(),
	        		new OsmTileSource.Esri(),
	        		new OsmTileSource.EsriTopo(),
	        		new OsmTileSource.Nps(),
	        		new OsmTileSource.Apple(),
	        		new OsmTileSource.Skobbler(),
	        		new OsmTileSource.CloudMade("2"),
	        		new OsmTileSource.CloudMade("999"),
	        		new OsmTileSource.CloudMade("998"),
	        		new OsmTileSource.CloudMade("7"),
	        		new OsmTileSource.CloudMade("1960"),
	        		new OsmTileSource.CloudMade("1155"),
	        		new OsmTileSource.CloudMade("12284"),
	        		new OsmTileSource.HillShade(),
	        		new OsmTileSource.CycleMap(),
	        		new OsmTileSource.BBBikeGerman(),
	                new OsmTileSource.BBBike(), 
	        		new OsmTileSource.PublicTransport(),
	        		new OsmTileSource.Transport()};
		}
		RTileController cont = map.get(type);
		if(cont!=null)
			return cont;
		TileSource src = null;
		if(type.startsWith("cloudmade")){
			String id = type.split("-")[1];
			src = new OsmTileSource.CloudMade(id);
		}else{
			for(int i=0;i<sources.length;i++){
				if(sources[i].getName().equals(type)){
					src = sources[i];
					break;
				}
			}
		}
		if(src==null)
			return null;
		final MemoryTileCache tc1 = new MemoryTileCache();
		cont = new RTileController(src,tc1, new TileLoaderListener(){

			public TileCache getTileCache() {
				return tc1;
			}

			public void tileLoadingFinished(Tile arg0, boolean arg1) {}
			
		});
		map.put(type, cont);
		return cont;
		
	}

    public Tile getTile(int tilex, int tiley, int zoom) {
        int max = (1 << zoom);
        if (tilex < 0 || tilex >= max || tiley < 0 || tiley >= max)
            return null;
        Tile tile = tileCache.getTile(tileSource, tilex, tiley, zoom);
        if (tile == null) {
            tile = new Tile(tileSource, tilex, tiley, zoom);
            tileCache.addTile(tile);
            tile.loadPlaceholderFromCache(tileCache);
        }
        if (!tile.isLoaded()) {
            tileLoader.createTileLoaderJob(tileSource, tilex, tiley, zoom).run();
        }
        return tile;
    }
	
    public int[] getTileValues(double tilex, double tiley, double zoom) {
    	try{
    	int[] res = new int[]{};
    	Tile tile = getTile((int)Math.round(tilex),(int)Math.round(tiley),(int)Math.round(zoom));
    	return tile.getImage().getRGB(0, 0, 255, 255, null, 0, 255);
    	}catch(Exception e){
    		e.printStackTrace();
    	}
    	return null;
    }
    
    public static double[] getTileBBox(double tilex, double tiley, double zoom){
    	double[] res = new double[]{0,0,0,0};
    	int x = (int) Math.round(tilex);
    	int y = (int) Math.round(tiley);
    	int z = (int) Math.round(zoom);
    	res[0] = OsmMercator.XToLon(x, z);
    	res[2] = OsmMercator.XToLon(x+1, z);
    	res[1] = OsmMercator.YToLat(y, z);
    	res[3] = OsmMercator.YToLat(y+1, z);
    	
    	return res;
    }
    
	public static void setCloudMadeKey(String key){
		OsmTileSource.cloudMadeKey = key;
	}
}
