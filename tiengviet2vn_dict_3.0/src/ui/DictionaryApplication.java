package ui;

import java.awt.event.*;
import javax.swing.text.*;
import javax.swing.event.*;
import javax.swing.border.*;
import javax.swing.*;
import java.awt.*;

import java.io.*; 
import java.util.List;
import java.util.ArrayList; 

import import_export.DictionaryCommandline;
import import_export.DictionaryManagement;

public class DictionaryApplication {
	private final DictionaryCommandline cmd;
	private final DictionaryManagement mn;

	private final JFrame appFrame = new JFrame("tiengviet2vn_dict_3.0");
	private final JPanel appPanel = new JPanel(new GridLayout(0, 2)); // main panel.
	private final JPanel defPanel = new JPanel(); // definition area
	private final JPanel schPanel = new JPanel(); // searching area
	private final JPanel sbrPanel = new JPanel(); // search box area
	private final JPanel sgnPanel = new JPanel(); // suggestion area

	private final JButton schButton = new JButton("Search");
	JTextField schwd = new JTextField("Word here");
	JTextArea def = new JTextArea("Definition here");
	JList<String> sgn = new JList<String>();
	JScrollPane sgn_scroll = new JScrollPane(sgn);

	/**
	 * Constructor.
	 * @param cmd a DictionaryCommandLine object
	 * @param mn  a DictionaryManagement object
	 */
	public DictionaryApplication(DictionaryCommandline cmd, DictionaryManagement mn) {
		this.cmd = cmd;
		this.mn = mn;
	}

	/**
	 * fill the GridBadConstraints object with desired properties.
	 * @param c  GridBadConstraints object
	 * @param gx top down position in the grid
	 * @param gy left to right position in the grid
	 * @param wx vertical weight
	 * @param wy horizontal weight
	 */
	public void GBCfill(GridBagConstraints c, int gx, int gy, int wx, int wy) {
		c.gridx = gx;
		c.gridy = gy;
		c.weightx = wx;
		c.weighty = wy;
	}

	/**
	 * Add action to components.
	 */
	public void addAction() {
		schButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				def.setText(DictionaryCommandline.sentenceTranslator(schwd.getText()));
			}
		});

		schwd.getDocument().addDocumentListener(new DocumentListener() {
			public void changedUpdate(DocumentEvent e) {
				//do nothing
			}
			public void insertUpdate(DocumentEvent e) {
				String[] sgn_list = cmd.dictionarySearchSamePrefix(schwd.getText(), mn.getDict());
				if(schwd.getText().isEmpty()) {
					sgn_list = new String[0];
				}
				sgn.setListData(sgn_list);
				def.setText(cmd.dictionarySearchExact(schwd.getText(), mn.getDict()));
			}
			public void removeUpdate(DocumentEvent e) {
				String[] sgn_list = cmd.dictionarySearchSamePrefix(schwd.getText(), mn.getDict());
				if(schwd.getText().isEmpty()) {
					sgn_list = new String[0];
				}
				sgn.setListData(sgn_list);
				def.setText(cmd.dictionarySearchExact(schwd.getText(), mn.getDict()));
			}
		});

		sgn.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent e) {
				if(e.getValueIsAdjusting()) {
					return;
				}
				def.setText(cmd.dictionarySearchExact(sgn.getSelectedValue(), mn.getDict()));
			}
		});
	}

	/**
	 * Run the App with User Interface.
	 */
	public void runApplication() {
		GridBagConstraints c = new GridBagConstraints();
		Border loweredbevel = BorderFactory.createLoweredBevelBorder();
		Border blackline = BorderFactory.createLineBorder(Color.black);

		sgn_scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		schwd.setBorder(loweredbevel);
		sgn.setBorder(loweredbevel);
		def.setBorder(loweredbevel);
		def.setEditable(false);

		addAction();

		appPanel.add(schPanel);
		appPanel.add(defPanel);

		defPanel.setLayout(new GridBagLayout());
		schPanel.setLayout(new GridBagLayout());
		sbrPanel.setLayout(new GridBagLayout());
		sgnPanel.setLayout(new GridBagLayout());

		c.fill = GridBagConstraints.BOTH;
		c.insets = new Insets(2, 2, 2, 2);

		GBCfill(c, 0, 0, 1, 1);
		defPanel.add(def, c);

		GBCfill(c, 0, 0, 1, 1);
		sbrPanel.add(schwd, c);

		GBCfill(c, 1, 0, 0, 1);
		sbrPanel.add(schButton, c);

		GBCfill(c, 0, 0, 1, 0);
		schPanel.add(sbrPanel, c);

		GBCfill(c, 0, 1, 1, 1);
		schPanel.add(sgnPanel, c);

		GBCfill(c, 1, 1, 1, 1);
		sgnPanel.add(sgn_scroll, c);

		appFrame.setBounds(650, 200, 500, 500);
		appFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		appFrame.add(appPanel);
		appFrame.setVisible(true);
	}
}