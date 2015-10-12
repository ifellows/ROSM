package org.openstreetmap.gui.jmapviewer;

//License: GPL. Copyright 2008 by Jan Peter Stotz

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.openstreetmap.gui.jmapviewer.interfaces.TileLoader;
import org.openstreetmap.gui.jmapviewer.interfaces.TileSource;
import org.openstreetmap.gui.jmapviewer.tilesources.BingAerialTileSource;
import org.openstreetmap.gui.jmapviewer.tilesources.MapQuestAerialTileSource;
import org.openstreetmap.gui.jmapviewer.tilesources.MapQuestTileSource;
import org.openstreetmap.gui.jmapviewer.tilesources.OsmTileSource;
import org.openstreetmap.gui.jmapviewer.tilesources.OsmTileSource.CloudMade;
import org.openstreetmap.gui.jmapviewer.tilesources.StamenTileSource;

/**
 *
 * Demonstrates the usage of {@link JMapViewer}
 *
 * @author Jan Peter Stotz
 *
 */
public class Demo extends JFrame {

    private static final long serialVersionUID = 1L;

    public Demo() {
        super("JMapViewer Demo");
        setSize(400, 400);
        //final JMapViewer map = new JMapViewer();
        final JMapViewer map = new JMapViewer(new MemoryTileCache(),4);
        // map.setTileLoader(new OsmFileCacheTileLoader(map));
        setLayout(new BorderLayout());
        //setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        JPanel panel = new JPanel();
        JPanel helpPanel = new JPanel();
        add(panel, BorderLayout.NORTH);
        add(helpPanel, BorderLayout.SOUTH);
        JLabel helpLabel = new JLabel("Use right mouse button to move the map,\n "
                + "left double click or mouse wheel to zoom.");
        helpPanel.add(helpLabel);
        JButton button = new JButton("setDisplayToFitMapMarkers");
        button.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                map.setDisplayToFitMapMarkers();
            }
        });
        JComboBox tileSourceSelector = new JComboBox(new TileSource[] { 
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
        		new OsmTileSource.Transport() });
        tileSourceSelector.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent e) {
                map.setTileSource((TileSource) e.getItem());
            }
        });
        JComboBox tileLoaderSelector;
        try {
            tileLoaderSelector = new JComboBox(new TileLoader[] { new OsmFileCacheTileLoader(map),
                    new OsmTileLoader(map) });
        } catch (IOException e) {
            tileLoaderSelector = new JComboBox(new TileLoader[] { new OsmTileLoader(map) });
        }
        tileLoaderSelector.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent e) {
                map.setTileLoader((TileLoader) e.getItem());
            }
        });
        map.setTileLoader((TileLoader) tileLoaderSelector.getSelectedItem());
        panel.add(tileSourceSelector);
        panel.add(tileLoaderSelector);
        final JCheckBox showMapMarker = new JCheckBox("Map markers visible");
        showMapMarker.setSelected(map.getMapMarkersVisible());
        showMapMarker.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                map.setMapMarkerVisible(showMapMarker.isSelected());
            }
        });
        panel.add(showMapMarker);
        final JCheckBox showTileGrid = new JCheckBox("Tile grid visible");
        showTileGrid.setSelected(map.isTileGridVisible());
        showTileGrid.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                map.setTileGridVisible(showTileGrid.isSelected());
            }
        });
        panel.add(showTileGrid);
        final JCheckBox showZoomControls = new JCheckBox("Show zoom controls");
        showZoomControls.setSelected(map.getZoomContolsVisible());
        showZoomControls.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                map.setZoomContolsVisible(showZoomControls.isSelected());
            }
        });
        panel.add(showZoomControls);
        panel.add(button);
        
        final JTextField posField = new JTextField(30);
        helpPanel.add(posField);
        new DefaultMapController(map){
        	public void mouseClicked(MouseEvent e) {
        		super.mouseClicked(e);
				posField.setText(map.getToolTipText(e));
        	}
        };

        
        
        add(map, BorderLayout.CENTER);

        //
        //map.addMapMarker(new MapMarkerDot(49.814284999, 8.642065999));
        //map.addMapMarker(new MapMarkerDot(49.91, 8.24));
        //map.addMapMarker(new MapMarkerDot(49.71, 8.64));
        //map.addMapMarker(new MapMarkerDot(48.71, -1));
        //map.addMapMarker(new MapMarkerDot(49.8588, 8.643));

        // map.setDisplayPositionByLatLon(49.807, 8.6, 11);
        // map.setTileGridVisible(true);
    }

    /**
     * @param args
     */
    public static void main(String[] args) {
        // java.util.Properties systemProperties = System.getProperties();
        // systemProperties.setProperty("http.proxyHost", "localhost");
        // systemProperties.setProperty("http.proxyPort", "8008");
        new Demo().setVisible(true);
    }

}
