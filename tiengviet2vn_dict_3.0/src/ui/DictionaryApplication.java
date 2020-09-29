package ui;

import java.awt.event.*;
import javax.swing.text.*;
import javax.swing.event.*;
import javax.swing.border.*;
import javax.swing.*;
import java.awt.*;

import import_export.DictionaryCommandline;
import import_export.DictionaryManagement;

public class DictionaryApplication {
	public static void runApplication(DictionaryCommandline cmd, DictionaryManagement mn) {
		JFrame f = new JFrame("Testing");
		JButton b = new JButton("Click here!___");
		JPanel p = new JPanel(new GridLayout(0, 2));
		JPanel defPanel = new JPanel();
		JPanel schPanel = new JPanel();
		JPanel sbrPanel = new JPanel();
		GridBagConstraints c = new GridBagConstraints();
		Border loweredbevel = BorderFactory.createLoweredBevelBorder();

		JTextArea schwd = new JTextArea("Word here");
		JTextArea sgn = new JTextArea("Suggestions here");
		JTextArea def = new JTextArea("Vu Hien xau trai!!!");

		schwd.setBorder(loweredbevel);
		sgn.setBorder(loweredbevel);
		def.setBorder(loweredbevel);

		schwd.getDocument().addDocumentListener(new DocumentListener() {
			public void changedUpdate(DocumentEvent e) {
				//do nothing
			}
			public void insertUpdate(DocumentEvent e) {
				String[] sgn_list = cmd.dictionarySearchSamePrefix(schwd.getText(), mn.getDict());
				String txt = "";
				if(schwd.getText().isEmpty()) {
					sgn_list[0]=null;
				}
				for(int i=0; sgn_list[i]!=null; i++) {
					txt += (sgn_list[i] + "\n");
				}
				sgn.setText(txt);
				def.setText(cmd.dictionarySearchExact(schwd.getText(), mn.getDict()));
			}
			public void removeUpdate(DocumentEvent e) {
				String[] sgn_list = cmd.dictionarySearchSamePrefix(schwd.getText(), mn.getDict());
				String txt = "";
				if(schwd.getText().isEmpty()) {
					sgn_list[0]=null;
				}
				for(int i=0; sgn_list[i]!=null; i++) {
					txt += (sgn_list[i] + "\n");
				}
				sgn.setText(txt);
				def.setText(cmd.dictionarySearchExact(schwd.getText(), mn.getDict()));
			}
		});

		p.add(schPanel);
		p.add(defPanel);

		defPanel.setLayout(new GridBagLayout());
		schPanel.setLayout(new GridBagLayout());
		sbrPanel.setLayout(new GridBagLayout());

		c.fill = GridBagConstraints.BOTH;
		c.insets = new Insets(2, 2, 2, 2);

		c.weightx = 1;
		c.weighty = 1;
		c.gridx = 0;
		c.gridy = 0;
		defPanel.add(def, c);

		c.weightx = 1;
		c.weighty = 1;
		c.gridx = 0;
		c.gridy = 0;
		sbrPanel.add(schwd, c);

		c.weightx = 0;
		c.weighty = 1;
		c.gridx = 1;
		c.gridy = 0;
		sbrPanel.add(new JButton("Search"), c);

		c.weightx = 1;
		c.weighty = 0;
		c.gridx = 0;
		c.gridy = 0;
		schPanel.add(sbrPanel, c);

		c.weightx = 1;
		c.weighty = 1;
		c.gridx = 0;
		c.gridy = 1;
		schPanel.add(sgn, c);

		f.setBounds(650, 200, 400, 200);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.add(p);
		f.setVisible(true);
	}
}