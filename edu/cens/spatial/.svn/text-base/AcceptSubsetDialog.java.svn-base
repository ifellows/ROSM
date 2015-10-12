package edu.cens.spatial;

import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;


import edu.cens.spatial.plots.MapController;

public class AcceptSubsetDialog extends JDialog
{
	private static final String HELP_URL = "http://www.deducer.org/pmwiki/index.php?n=Main.DeducerSpatial";
	private final MapController mc;
	private boolean keepSelected = true;
	DeducerDataFrameNameField nameField;
	
	public AcceptSubsetDialog(JFrame parent, final MapController mc)
	{
		super(parent);
		this.mc = mc;
		setModal(false); //should be able to drag around and stuff
		//this.setAlwaysOnTop(true);
		nameField = new DeducerDataFrameNameField();
		nameField.setText("tmp");
		this.addWindowListener(new WindowAdapter() 
		{
		  public void windowClosing(WindowEvent e)
		  {
		  	mc.stopSubsetting();
		  }
		});

		initGui();
	}

	private void initGui()
	{
		final AcceptSubsetDialog thisPtr = this;
		this.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		
//		JButton acceptButton = new JButton("Accept");
//		acceptButton.addActionListener(new ActionListener()
//		{
//			public void actionPerformed(ActionEvent e)
//			{
//				boolean ok = true;//nameField.tryExecute();
//				if (ok)
//				{
//					try
//					{
//						setCursor(new Cursor(Cursor.WAIT_CURSOR));
//					
//						mc.executeSubsetting(keepSelected);
//						thisPtr.setVisible(false);
//					}
//					finally
//					{
//						setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
//					}
//					//dispose(); //TODO remove this if we need to remember the state from the last subset action
//				}
//			}
//		});
		
		
//		JButton rejectButton = new JButton("Cancel");
//		rejectButton.addActionListener(new ActionListener()
//		{
//			public void actionPerformed(ActionEvent e)
//			{
//				mc.stopSubsetting();
//				thisPtr.setVisible(false);
//			}
//		}); 
		
		final JRadioButton keepOnlySelectionButton = new JRadioButton("Keep Only Selected");
		final JRadioButton removeSelectionButton = new JRadioButton("Delete Selected");
		ButtonGroup radButGrp = new ButtonGroup();
		radButGrp.add(keepOnlySelectionButton);
		radButGrp.add(removeSelectionButton);
		
		keepOnlySelectionButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
					keepSelected = keepOnlySelectionButton.isSelected();
			}
		});
		
		removeSelectionButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				//keepSelected = keepOnlySelectionButton.isSelected();
				keepSelected = !removeSelectionButton.isSelected();
			}
		});
		keepOnlySelectionButton.setSelected(true);
		
	
		nameField.setPreferredSize(new Dimension(
				250,
				nameField.getPreferredSize().height
		));
		
		c.insets = new Insets(5, 5, 0, 5);
		c.anchor = GridBagConstraints.WEST;
		c.gridy = 0;
		c.gridx = 0;
		this.add(keepOnlySelectionButton, c);
		c.gridy = 1;
		c.gridx = 0;
		this.add(removeSelectionButton, c);
		
		JPanel subsetNamePanel = new JPanel(new GridBagLayout());
		
		c.gridx = 0;
		c.gridy = 0;
		c.weightx = 0;
		c.fill = c.NONE;
		subsetNamePanel.add(new JLabel("Subset Name:"), c);
		
		c.gridy = 0;
		c.gridx = 1;
		c.gridwidth = 1;
		c.weightx = 1;
		c.fill = c.HORIZONTAL;
		subsetNamePanel.add(nameField, c);
	
		c.gridy = 2;
		c.gridx = 0;
		this.add(subsetNamePanel, c);
		
		c.gridy = 3;
		c.gridx = 0;
		
		DeducerOkCancelPanel okp = new DeducerOkCancelPanel(this.rootPane)
		{
			protected void ok()
			{
				try
				{
					setCursor(new Cursor(Cursor.WAIT_CURSOR));
				
					mc.executeSubsetting(keepSelected, nameField.getText());
					thisPtr.setVisible(false);
				}
				finally
				{
					setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
				}
			}

			protected void cancel()
			{
				// TODO Auto-generated method stub
				mc.stopSubsetting();
				thisPtr.setVisible(false);
			}
			
		};
		okp.setHelpUrl(HELP_URL);
		this.add(okp, c);
		
		
		c.gridy = 2;
		c.gridx = 1;
		c.gridwidth = 1;
		//this.add(acceptButton, c);
		
		c.gridx = 0;
		//this.add(rejectButton, c);
		
		this.pack();
		this.setResizable(false);
	}

	public void setVisible(boolean b)
	{
		this.setLocationRelativeTo(getParent());
		super.setVisible(b);
	}
	
	public static void main(String[] args)
	{
		JDialog d = new AcceptSubsetDialog(null, null)
		{
			public void setVisible(boolean b)
			{
				if (!b)
				{
					System.exit(0);
				}
				super.setVisible(b);
			}
			
		};
		d.removeWindowListener(d.getWindowListeners()[0]);
		d.addWindowListener(new WindowAdapter()
		{
		  public void windowClosing(WindowEvent e)
		  {
		  	System.exit(0);
		  }
		});
		d.setVisible(true);
		
	}
}
