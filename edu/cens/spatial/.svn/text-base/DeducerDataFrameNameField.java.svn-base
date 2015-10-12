/**
 * A text field for naming new R dataframes/variables.
 * 
 * It will display appropriate warnings, 
 * 
 */
package edu.cens.spatial;

import java.awt.Toolkit;

import javax.swing.JOptionPane;
import javax.swing.JTextField;

import org.rosuda.REngine.REXPMismatchException;
import org.rosuda.deducer.Deducer;


public class DeducerDataFrameNameField extends JTextField
{

	// *WITHOUT* displaying any dialogs, checks to see if the field is valid
	public boolean isValid()
	{
		return true;
	}

	public boolean isUnique()
	{
		return true;
	}
	
	/**
	 * Sometime a simple transformation will be performed on the name such that
	 * even weird things can become valid variables (special quotes usually)
	 * @return The variable name in the text field, possibly slightly transformed.
	 */
	public String getVariableName()
	{
		String ret = this.getText().equals("") ? null : this.getText();
		if (ret != null)
		{
			try
			{
				ret = (Deducer.timedEval("as.name('" + this.getText() + "')")).asString();
			}
			catch (REXPMismatchException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
				return null;
			}
		}
		return ret;
	}

	/**
	 * Call this method as part of your 'OK' or 'Run' button press.
	 * Will display dialogs to handle case of invalid var name, name conflicts,
	 * etc...
	 * 
	 * @return true if the TextField contains a valid variable.
	 */
	//TODO add warning if 'as.named' var-name is different from the typed one.
	public boolean tryExecute()
	{

			String newCorpusName =  getVariableName();


			boolean isUnique = newCorpusName != null && newCorpusName.equals(Deducer.getUniqueName(newCorpusName));
			boolean validAsIs = this.getText() == newCorpusName;

			boolean validCommand = true;

			if (this.getText() == null || this.getText().equals("")) 
			//(newCorpusName == null)
			{
				validCommand = false;
				Toolkit.getDefaultToolkit().beep();
				JOptionPane.showMessageDialog(null,
						"You must give the corpus a name.",
						"Alert",
						JOptionPane.ERROR_MESSAGE);
			}
			else if (!isUnique) //not unique
			{

				int n = JOptionPane.showConfirmDialog(
						null,
						"The corpus name \"" + newCorpusName + "\" is already in use."
						+"\nWould you like to overwrite the existing variable?",
						"Warning",
						JOptionPane.YES_NO_OPTION);
				validCommand = n == 0;

			}

			return validCommand;
	}
}
