package ui;

import java.awt.event.*;
import javax.swing.event.*;
import javax.swing.border.*;
import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.regex.Pattern;

import import_export.DictionaryCommandline;
import import_export.DictionaryManagement;
import words_handler.Word;

public class DictionaryApplication {
	private final DictionaryCommandline cmd;
	private final DictionaryManagement mn;

	private final JFrame appFrame = new JFrame("tiengviet2vn_dict_3.0");
	private final JDialog addFrame = new JDialog(appFrame, "Add word.", true); // add word window.
	private final JDialog delFrame = new JDialog(appFrame, "Confirm.", true); // remove word window.
	private final JDialog strDialog = new JDialog(appFrame, "Sentence Translator", true); //sentence translation dialog

	private final JPanel appPanel = new JPanel(new GridBagLayout()); // main panel.
	private final JPanel funPanel = new JPanel(new GridLayout(1, 0)); // functional panel
	private final JPanel wrdPanel = new JPanel(new GridLayout(0, 2));
	private final JPanel defPanel = new JPanel(); // definition area
	private final JPanel schPanel = new JPanel(); // searching area
	private final JPanel sbrPanel = new JPanel(); // search box area
	private final JPanel sgnPanel = new JPanel(); // suggestion area
	private final JPanel strPanel = new JPanel(); // sentence to be translated panel
	private final JPanel trsPanel = new JPanel(); // translated sentence panel

	private final JButton schButton = new JButton("Search");
	private final JButton addButton = new JButton("Add word");
	private final JButton delButton = new JButton("Remove word");
	private final JButton favButton = new JButton("My favourite words");
	private final JButton strButton = new JButton("Sentence Translator");
	private final JButton trsButton = new JButton("Translate");

	JTextField schwd = new JTextField("Word here");
	JTextArea def = new JTextArea("Definition here");
	JTextArea sntce = new JTextArea("Sentence here");
	JTextArea trs = new JTextArea("Translated sentence here");
	JList<String> sgn = new JList<String>();
	JScrollPane sgn_scroll = new JScrollPane(sgn);
	JScrollPane def_scroll = new JScrollPane(def);

	/**
	 * Constructor.
	 * @param cmd a DictionaryCommandLine object
	 * @param mn  a DictionaryManagement object
	 */
	public DictionaryApplication(DictionaryCommandline cmd, DictionaryManagement mn) {
		this.cmd = cmd;
		this.mn = mn;
		this.mn.getDict().sortDictionary();
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
		addButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				addFrame.setVisible(true);
			}
		});

		delButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (sgn.getSelectedValue() == null) {
					JOptionPane.showMessageDialog(appFrame, "You must select a word in the dictionary!");
					return;
				}
				else delFrame.setVisible(true);
			}
		});

		trsButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				trs.setText(DictionaryCommandline.sentenceTranslator(sntce.getText()));
			}
		});

		favButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

			}
		});

		strButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				strDialog.setVisible(true);
			}
		});

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
	 * Add word feature
	 */
	public void addAddFrame() {
		addFrame.setSize(300, 100);
		addFrame.setLocationRelativeTo(null);
		JPanel mainPanel = new JPanel(new GridBagLayout());
		JPanel targPanel = new JPanel(new GridLayout(2, 0));
		JPanel explPanel = new JPanel(new GridLayout(2, 0));

		JLabel targ = new JLabel(" New Word:");
		JLabel expl = new JLabel(" Definition:");
		JTextField targField = new JTextField();
		JTextField explField = new JTextField();
		JButton finishAdd = new JButton("Add");

		targPanel.add(targ);
		targPanel.add(targField);
		explPanel.add(expl);
		explPanel.add(explField);

		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.BOTH;
		GBCfill(c, 0, 0, 1, 1);
		mainPanel.add(targPanel, c);
		GBCfill(c, 1, 0, 1, 1);
		mainPanel.add(explPanel, c);
		GBCfill(c, 2, 0, 0, 1);
		mainPanel.add(finishAdd, c);

		addFrame.add(mainPanel);

		targField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					explField.requestFocus();
				}
			}
		});

		explField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					finishAdd.doClick();
				}
			}
		});

		finishAdd.addActionListener(e -> {
			if (!Pattern.matches(("[a-zA-Z]+"), targField.getText())) {
				return;
			}

			if (explField.getText().length() == 0) {
				return;
			}

			Word word = new Word(targField.getText(), explField.getText());

			if (mn.getDict().existed(word)) {
				JOptionPane.showMessageDialog(addFrame, "This word is already existed!");
				return;
			}

			mn.getDict().addWord(word);
			mn.getDict().sortDictionary();

			try {
				Writer en = new BufferedWriter(new FileWriter("../data/en.txt", true));
				Writer vi = new BufferedWriter(new FileWriter("../data/vi.txt", true));

				en.append("\n" + targField.getText());
				vi.append("\n" + explField.getText());

				en.close();
				vi.close();

				targField.setText("");
				explField.setText("");
			} catch (Exception ev) {
				System.out.println("No path found!");
			}
		});
	}

	/**
	 * Remove word feature.
	 */
	public void addDelFrame() {
		delFrame.setSize(250, 75);
		delFrame.setResizable(false);
		delFrame.setLocationRelativeTo(null);
		delFrame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);

		JPanel mainPanel = new JPanel();
		mainPanel.setLayout(null);
		mainPanel.setPreferredSize(new Dimension(250, 75));

		JLabel msg = new JLabel("Do you want to delete this word ?", SwingConstants.CENTER);
		JPanel msgPanel = new JPanel(new GridLayout(0, 1));
		JButton yes = new JButton("Yes");
		JButton no = new JButton("No");

		msgPanel.setBounds(0, 0, 250, 40);
		yes.setBounds(35, 40, 60, 25);
		no.setBounds(155, 40, 60, 25);

		msgPanel.add(msg);
		mainPanel.add(msgPanel);
		mainPanel.add(yes);
		mainPanel.add(no);
		delFrame.add(mainPanel);
		delFrame.pack();

		yes.addActionListener(e -> {
			if (sgn.getSelectedValue() == null) {
				JOptionPane.showMessageDialog(delFrame, "You must select a word to delete in the Dictionary!");
			} else {
				mn.deleteFromFile(sgn.getSelectedValue());
			}
			delFrame.dispose();
		});
	}

	public void addStrDialog() {
		Border loweredbevel = BorderFactory.createLoweredBevelBorder();

		strDialog.setSize(350, 200);
		strDialog.setLocationRelativeTo(null);
		strDialog.add(strPanel);

		strPanel.setLayout(new GridBagLayout());
		trsPanel.setLayout(new GridBagLayout());

		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.BOTH;
		c.insets = new Insets(2, 2, 2, 2);

		sntce.setBorder(loweredbevel);
		sntce.setLineWrap(true);
		sntce.setWrapStyleWord(true);
		trs.setBorder(loweredbevel);
		trs.setEditable(false);
		trs.setLineWrap(true);
		trs.setWrapStyleWord(true);

		GBCfill(c, 0, 0, 1, 1);
		strPanel.add(trsPanel, c);

		GBCfill(c, 0, 0, 1, 0);
		trsPanel.add(trsButton, c);

		GBCfill(c, 0, 1, 1, 1);
		trsPanel.add(sntce, c);

		GBCfill(c, 1, 0, 1, 1);
		strPanel.add(trs, c);
	}

	/**
	 * Run the App with User Interface.
	 */
	public void runApplication() {
		addAddFrame();
		addDelFrame();
		addStrDialog();

		def.setLineWrap(true);
		def.setWrapStyleWord(true);
		GridBagConstraints c = new GridBagConstraints();
		Border loweredbevel = BorderFactory.createLoweredBevelBorder();
		Border blackline = BorderFactory.createLineBorder(Color.black);

		sgn_scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		def_scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		schwd.setBorder(loweredbevel);
		sgn.setBorder(loweredbevel);
		def.setBorder(loweredbevel);
		def.setEditable(false);

		addAction();

		wrdPanel.add(schPanel);
		wrdPanel.add(defPanel);
		funPanel.add(addButton);
		funPanel.add(delButton);
		funPanel.add(favButton);
		funPanel.add(strButton);

		defPanel.setLayout(new GridBagLayout());
		schPanel.setLayout(new GridBagLayout());
		sbrPanel.setLayout(new GridBagLayout());
		sgnPanel.setLayout(new GridBagLayout());

		c.fill = GridBagConstraints.BOTH;
		c.insets = new Insets(2, 2, 2, 2);

		GBCfill(c, 0, 0, 1, 1);
		defPanel.add(def_scroll, c);

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

		GBCfill(c, 0, 0, 1, 0);
		appPanel.add(funPanel, c);

		GBCfill(c, 0, 1, 1, 1);
		appPanel.add(wrdPanel, c);

		appFrame.setSize(500, 500);
		appFrame.setLocationRelativeTo(null);
		appFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		appFrame.add(appPanel);
		appFrame.setVisible(true);
	}
}