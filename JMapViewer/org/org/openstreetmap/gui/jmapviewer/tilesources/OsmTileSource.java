package org.openstreetmap.gui.jmapviewer.tilesources;

import java.io.IOException;

import org.openstreetmap.gui.jmapviewer.Coordinate;

public class OsmTileSource {

    public static final String MAP_MAPNIK = "http://tile.openstreetmap.org";
    public static final String MAP_OSMA = "http://tah.openstreetmap.org/Tiles";
    public static String cloudMadeKey = "c94706833793432e93b3a9cd9e0cce52";

    public static class Mapnik extends AbstractOsmTileSource {
        public Mapnik() {
            super("osm", MAP_MAPNIK);
        }

        public TileUpdate getTileUpdate() {
            return TileUpdate.IfNoneMatch;
        }
    }
    
    public static class MapnikBw extends AbstractOsmTileSource {
        public MapnikBw() {
            super("osm-bw", "http://a.www.toolserver.org/tiles/bw-mapnik");
        }

        public TileUpdate getTileUpdate() {
            return TileUpdate.IfNoneMatch;
        }
    }
    
    public static class MapnikGerman extends AbstractOsmTileSource {
        public MapnikGerman() {
            super("osm-german", "http://tile.openstreetmap.de/tiles/osmde");
        }

        public TileUpdate getTileUpdate() {
            return TileUpdate.IfNoneMatch;
        }
    }    
    public static class CycleMap extends AbstractOsmTileSource {

        private static final String PATTERN = "http://%s.tile.opencyclemap.org/cycle";

        private static final String[] SERVER = { "a", "b", "c" };

        private int SERVER_NUM = 0;

        public CycleMap() {
            super("opencyclemap", PATTERN);
        }

        @Override
        public String getBaseUrl() {
            String url = String.format(this.baseUrl, new Object[] { SERVER[SERVER_NUM] });
            SERVER_NUM = (SERVER_NUM + 1) % SERVER.length;
            return url;
        }

        @Override
        public int getMaxZoom() {
            return 17;
        }

        public TileUpdate getTileUpdate() {
            return TileUpdate.LastModified;
        }

    }

    public static abstract class OsmaSource extends AbstractOsmTileSource {
        String osmaSuffix;

        public OsmaSource(String name, String osmaSuffix) {
            super(name, MAP_OSMA);
            this.osmaSuffix = osmaSuffix;
        }

        @Override
        public int getMaxZoom() {
            return 17;
        }

        @Override
        public String getBaseUrl() {
            return MAP_OSMA + "/" + osmaSuffix;
        }

        public TileUpdate getTileUpdate() {
            return TileUpdate.IfModifiedSince;
        }
    }

    
    
    public static class BBBike extends AbstractOsmTileSource {
        public BBBike() {
            super("osm-bbike", "http://a.tile.bbbike.org/osm/mapnik");
        }

		public TileUpdate getTileUpdate() {
			return TileUpdate.IfNoneMatch;
		}
		
	    public String getAttributionText(int zoom, Coordinate topLeft, Coordinate botRight) {
	        return "© OpenStreetMap contributors, CC-BY-SA ";
	    }

	    public String getAttributionLinkURL() {
	        return "http://openstreetmap.org/";
	    }

	    public String getTermsOfUseURL() {
	        return "http://www.openstreetmap.org/copyright";
	    }
    }        
    
    public static class BBBikeGerman extends AbstractOsmTileSource {
        public BBBikeGerman() {
            super("osm-bbike-german", "http://a.tile.bbbike.org/osm/mapnik-german");
        }

		public TileUpdate getTileUpdate() {
			return TileUpdate.IfNoneMatch;
		}
		
	    public String getAttributionText(int zoom, Coordinate topLeft, Coordinate botRight) {
	        return "© OpenStreetMap contributors, CC-BY-SA ";
	    }

	    public String getAttributionLinkURL() {
	        return "http://openstreetmap.org/";
	    }

	    public String getTermsOfUseURL() {
	        return "http://www.openstreetmap.org/copyright";
	    }
    }        
    
    public static class MapToolKitTopo extends AbstractOsmTileSource {
        public MapToolKitTopo() {
            super("maptoolkit-topo", "http://tile1.maptoolkit.net/terrain");
        }

		public TileUpdate getTileUpdate() {
			return TileUpdate.IfNoneMatch;
		}
		
	    public String getAttributionText(int zoom, Coordinate topLeft, Coordinate botRight) {
	        return "www.maptoolkit.net";
	    }

	    public String getAttributionLinkURL() {
	        return "http://maptoolkit.net/";
	    }

	    public String getTermsOfUseURL() {
	        return "http://www.maptoolkit.net";
	    }
    }    
    
    public static class Waze extends AbstractOsmTileSource {
        public Waze() {
            super("waze", "http://tiles1.waze.com/tiles");
        }

		public TileUpdate getTileUpdate() {
			return TileUpdate.IfNoneMatch;
		}
		
	    public String getAttributionText(int zoom, Coordinate topLeft, Coordinate botRight) {
	        return "www.waze.com";
	    }

	    public String getAttributionLinkURL() {
	        return "http://waze.com";
	    }

	    public String getTermsOfUseURL() {
	        return "http://www.waze.com/legal/tos/";
	    }
    } 
    
    public static class PublicTransport extends AbstractOsmTileSource {
        public PublicTransport() {
            super("osm-public-transport", "http://tile.memomaps.de/tilegen");
        }

		public TileUpdate getTileUpdate() {
			return TileUpdate.IfNoneMatch;
		}
		
	    public String getAttributionText(int zoom, Coordinate topLeft, Coordinate botRight) {
	        return "© OpenStreetMap contributors, CC-BY-SA ";
	    }

	    public String getAttributionLinkURL() {
	        return "http://openstreetmap.org/";
	    }

	    public String getTermsOfUseURL() {
	        return "http://www.openstreetmap.org/copyright";
	    }
    }    
    
    public static class Transport extends AbstractOsmTileSource {
        public Transport() {
            super("osm-transport", "http://a.tile2.opencyclemap.org/transport");
        }

		public TileUpdate getTileUpdate() {
			return TileUpdate.IfNoneMatch;
		}
		
	    public String getAttributionText(int zoom, Coordinate topLeft, Coordinate botRight) {
	        return "© OpenStreetMap contributors, CC-BY-SA ";
	    }

	    public String getAttributionLinkURL() {
	        return "http://openstreetmap.org/";
	    }

	    public String getTermsOfUseURL() {
	        return "http://www.openstreetmap.org/copyright";
	    }
    }    
    
    public static class Wanderreitkarte extends AbstractOsmTileSource {
        public Wanderreitkarte() {
            super("osm-wanderreitkarte", "http://www.wanderreitkarte.de/topo");
        }

		public TileUpdate getTileUpdate() {
			return TileUpdate.IfNoneMatch;
		}
		
	    public String getAttributionText(int zoom, Coordinate topLeft, Coordinate botRight) {
	        return "© OpenStreetMap contributors and http://www.wanderreitkarte.de, CC-BY-SA ";
	    }

	    public String getAttributionLinkURL() {
	        return "http://www.wanderreitkarte.de/";
	    }

	    public String getTermsOfUseURL() {
	        return "http://www.wanderreitkarte.de/licence_de.php";
	    }
    }  
    
    public static class MapBox extends AbstractOsmTileSource {
        public MapBox() {
            super("mapbox", "http://a.tiles.mapbox.com/v3/examples.map-vyofok3q");
        }

		public TileUpdate getTileUpdate() {
			return TileUpdate.IfNoneMatch;
		}
		
	    public String getAttributionText(int zoom, Coordinate topLeft, Coordinate botRight) {
	        return "© OpenStreetMap contributors and mapbox.com, CC-BY-SA ";
	    }

	    public String getAttributionLinkURL() {
	        return "http://mapbox.com/";
	    }

	    public String getTermsOfUseURL() {
	        return "http://mapbox.com";
	    }
    }   
    
    public static class OSM2World extends AbstractOsmTileSource {
        public OSM2World() {
            super("osm2world-3d", "http://c.tiles.osm2world.org/osm/pngtiles/n");
        }

		public TileUpdate getTileUpdate() {
			return TileUpdate.IfNoneMatch;
		}
		
	    public String getAttributionText(int zoom, Coordinate topLeft, Coordinate botRight) {
	        return "";
	    }

	    public String getAttributionLinkURL() {
	        return "http://osm2world.org/";
	    }

	    public String getTermsOfUseURL() {
	        return "http://osm2world.org/";
	    }
    }    
    
    public static class Esri extends AbstractOsmTileSource {
        public Esri() {
            super("esri", "http://server.arcgisonline.com/ArcGIS/rest/services/World_Street_Map/MapServer/tile");
        }
        
        public String getTileType() {
            return "jpg";
        }
        
        public String getExtension() {
            return "jpg";
        }

        public String getTilePath(int zoom, int tilex, int tiley) throws IOException {
            return "/" + zoom + "/" + tiley + "/" + tilex + "." + getExtension();
        }
        
		public TileUpdate getTileUpdate() {
			return TileUpdate.None;
		}
		
	    public String getAttributionText(int zoom, Coordinate topLeft, Coordinate botRight) {
	        return "© ESRI";
	    }

	    public String getAttributionLinkURL() {
	        return "http://www.arcgis.com/home/item.html?id=3b93337983e9436f8db950e38a8629af";
	    }

	    public String getTermsOfUseURL() {
	        return "http://services.arcgisonline.com/ArcGIS/rest/services/ESRI_Imagery_World_2D/MapServer";
	    }
    }    
    
    public static class EsriTopo extends AbstractOsmTileSource {
        public EsriTopo() {
            super("esri-topo", "http://services.arcgisonline.com/ArcGIS/rest/services/World_Topo_Map/MapServer/tile");
        }
        
        public String getTileType() {
            return "jpg";
        }
        
        public String getExtension() {
            return "jpg";
        }

        public String getTilePath(int zoom, int tilex, int tiley) throws IOException {
            return "/" + zoom + "/" + tiley + "/" + tilex + "." + getExtension();
        }
		public TileUpdate getTileUpdate() {
			return TileUpdate.None;
		}
		
	    public String getAttributionText(int zoom, Coordinate topLeft, Coordinate botRight) {
	        return "© ESRI";
	    }

	    public String getAttributionLinkURL() {
	        return "http://www.arcgis.com/home/item.html?id=f2498e3d0ff642bfb4b155828351ef0e";
	    }

	    public String getTermsOfUseURL() {
	        return "http://services.arcgisonline.com/ArcGIS/rest/services/ESRI_Imagery_World_2D/MapServer";
	    }
    } 
    
    public static class Nps extends AbstractOsmTileSource {
        public Nps() {
            super("nps", "http://services.arcgisonline.com/ArcGIS/rest/services/World_Physical_Map/MapServer/tile");
        }
        
        public String getTileType() {
            return "jpg";
        }
        
        public String getTilePath(int zoom, int tilex, int tiley) throws IOException {
            return "/" + zoom + "/" + tiley + "/" + tilex + "." + getExtension();
        }

		public TileUpdate getTileUpdate() {
			return TileUpdate.IfNoneMatch;
		}
		
	    public String getAttributionText(int zoom, Coordinate topLeft, Coordinate botRight) {
	        return "© National Park Service";
	    }

	    public String getAttributionLinkURL() {
	        return "http://www.arcgis.com/home/item.html?id=c4ec722a1cd34cf0a23904aadf8923a0";
	    }

	    public String getTermsOfUseURL() {
	        return "http://goto.arcgisonline.com/maps/World_Physical_Map";
	    }
    }    
    
    public static class Apple extends AbstractOsmTileSource {
        public Apple() {
            super("apple-iphoto", "");
        }
        
        public String getTilePath(int zoom, int tilex, int tiley) throws IOException {
            return "http://gsp2.apple.com/tile?api=1&style=slideshow&layers=default&lang=en_EN&z="+
            	zoom  + "&x=" +
            	tilex + "&y=" +
            	tiley + "&v=9";
        }
        
        public String getTileType() {
            return "jpeg";
        }

		public TileUpdate getTileUpdate() {
			return TileUpdate.IfNoneMatch;
		}
		
	    public String getAttributionText(int zoom, Coordinate topLeft, Coordinate botRight) {
	        return "© Apple with data from OpenStreetMap";
	    }

	    public String getAttributionLinkURL() {
	        return "http://apple.com";
	    }

	    public String getTermsOfUseURL() {
	        return "http://apple.com";
	    }
    }    
    
    public static class Skobbler extends AbstractOsmTileSource {
        public Skobbler() {
            super("skobbler", "http://tiles1.skobbler.net/osm_tiles2");
        }

		public TileUpdate getTileUpdate() {
			return TileUpdate.IfNoneMatch;
		}
		
	    public String getAttributionText(int zoom, Coordinate topLeft, Coordinate botRight) {
	        return "© OpenStreetMap contributors and skobbler.com, CC-BY-SA ";
	    }

	    public String getAttributionLinkURL() {
	        return "http://skobbler.com";
	    }

	    public String getTermsOfUseURL() {
	        return "http://skobbler.com";
	    }
    }   
  /*  
    public static class Map1Eu extends AbstractOsmTileSource {
        public Map1Eu() {
            super("map1eu", "http://alpha.map1.eu/tiles");
        }
        
        public String getTileType() {
            return "jpg";
        }
        
        public String getExtension() {
            return "jpg";
        }
        
		public TileUpdate getTileUpdate() {
			return TileUpdate.IfNoneMatch;
		}
		
	    public String getAttributionText(int zoom, Coordinate topLeft, Coordinate botRight) {
	        return "© OpenStreetMap contributors, CC-BY-SA and map1.eu, CC-BY-NC-ND 3.0 ";
	    }

	    public String getAttributionLinkURL() {
	        return "http://alpha.map1.eu/";
	    }

	    public String getTermsOfUseURL() {
	        return "http://alpha.map1.eu/";
	    }
    }   
*/
    
    public static class HillShade extends AbstractOsmTileSource {
        public HillShade() {
            super("hillshade", "http://toolserver.org/~cmarqu/hill");
        }

		public TileUpdate getTileUpdate() {
			return TileUpdate.IfNoneMatch;
		}
		
	    public String getAttributionText(int zoom, Coordinate topLeft, Coordinate botRight) {
	        return "© OpenStreetMap contributors, CC-BY-SA and http://toolserver.org/~cmarqu/";
	    }

	    public String getAttributionLinkURL() {
	        return "http://toolserver.org/~cmarqu/";
	    }

	    public String getTermsOfUseURL() {
	        return "http://toolserver.org/~cmarqu/";
	    }
    }
    
    public static class CloudMade extends AbstractOsmTileSource {
    	public String mapId;
        public CloudMade(String id) {
            super("cloudmade-" + id, "");
            mapId = id;
        }
        
        public String getTilePath(int zoom, int tilex, int tiley) throws IOException {
            return "http://a.tile.cloudmade.com/" + 
            	cloudMadeKey + "/" + mapId + "/256/" + zoom + "/" + tilex + "/" + tiley + "." + getExtension();
        }

		public TileUpdate getTileUpdate() {
			return TileUpdate.IfNoneMatch;
		}
		
	    public String getAttributionText(int zoom, Coordinate topLeft, Coordinate botRight) {
	        return "© OpenStreetMap contributors with CloudMade.com, CC-BY-SA";
	    }

	    public String getAttributionLinkURL() {
	        return "http://www.cloudmade.com";
	    }

	    public String getTermsOfUseURL() {
	        return "http://cloudmade.com/website-terms-conditions";
	    }
    }    
}
