package org.openstreetmap.gui.jmapviewer.tilesources;


public class StamenTileSource {

	
	public static class Toner extends StamenLayerTileSource{
		public Toner(){
			super("toner","png");
		}
        @Override
        public int getMaxZoom() {
            return 20;
        }
	}
	
	public static class Terrain extends StamenLayerTileSource{
		public Terrain(){
			super("terrain","jpg");
		}
        @Override
        public int getMaxZoom() {
            return 18;
        }
	}	
	
	public static class WaterColor extends StamenLayerTileSource{
		public WaterColor(){
			super("watercolor","jpg");
		}
        @Override
        public int getMaxZoom() {
            return 16;
        }
	}
}
