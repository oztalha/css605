package edu.gmu.toz.week10;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;

/**
 * An example of a graphical user interface (GUI) in Java.
 * 
 * @author randy
 *
 */
public class GuiExample extends JPanel {

	// Used during serialization (without it there is a compiler warning)
	private static final long serialVersionUID = 1L;

	// Some GUI components
	private JTextArea textArea;

	private JPanel buttonPanel;

	private JButton addTextButton;

	private JButton reverseTextButton;

	public GuiExample() {

		// There are lots of layouts but border layout tends to be one of the
		// more useful ones
		setLayout(new BorderLayout());

		add(getTextArea(), BorderLayout.CENTER);
		add(getButtonPanel(), BorderLayout.SOUTH);
	}

	/**
	 * A text area for showing some text.
	 * 
	 * Notice how all of the GUI components use lazy initialization.
	 * 
	 * @return
	 */
	public JTextArea getTextArea() {
		if (textArea == null) {
			textArea = new JTextArea();

			// Allow line wrapping
			textArea.setLineWrap(true);

			// Wraps at the end of a word
			textArea.setWrapStyleWord(true);
		}

		return textArea;
	}

	/**
	 * Used for organizing the buttons.
	 * 
	 * @return
	 */
	public JPanel getButtonPanel() {
		if (buttonPanel == null) {
			// Notice how you can have subpanels that have their own layout
			// manager. The FlowLayout is the default layout for a JPanel, so
			// actually it is not necessary to include it as an input to the
			// constructor, but for illustration purposes I included it.
			buttonPanel = new JPanel(new FlowLayout());

			// Add the buttons to the panel
			buttonPanel.add(getAddTextButton());
			buttonPanel.add(getReverseTextButton());
		}

		return buttonPanel;
	}

	/**
	 * Adds "some text" to the text area.
	 * 
	 * @return
	 */
	public JButton getAddTextButton() {
		if (addTextButton == null) {
			addTextButton = new JButton("Add Some Text");

			// Notice the use of an anonymous inner class here...
			addTextButton.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					// Add "some text" to the text area
					getTextArea().setText(
							getTextArea().getText() + " some text");
				}
			});
		}

		return addTextButton;
	}

	/**
	 * Reverses the text in the text area.
	 * 
	 * @return
	 */
	public JButton getReverseTextButton() {
		if (reverseTextButton == null) {
			reverseTextButton = new JButton("Reverse Text");

			reverseTextButton.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					String text = getTextArea().getText();

					// Using a string buffer to reverse the text
					StringBuffer sb = new StringBuffer(text);
					getTextArea().setText(sb.reverse().toString());
				}
			});
		}

		return reverseTextButton;
	}

	public static void main(String[] args) {
		JFrame frame = new JFrame("GUI Example");
		JPanel panel = new GuiExample();
		frame.getContentPane().add(panel);
		frame.setPreferredSize(new Dimension(300, 200));
		frame.pack();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}
}
