package tiengviet2vn_dict.ui;

import java.awt.event.*;
import javax.swing.event.*;
import javax.swing.*;
import java.awt.*;

import tiengviet2vn_dict.import_export.DictionaryCommandline;
import tiengviet2vn_dict.import_export.DictionaryManagement;
import tiengviet2vn_dict.words_handler.Word;

/**
 * Create app gui and all feature in the app.
 */
public class DictionaryApplication {
	private final DictionaryCommandline cmd;
	private final DictionaryManagement mn;

	private final JFrame appFrame = new JFrame("tiengviet2vn_dict_3.0");
	private final JDialog addFrame = new JDialog(appFrame, "Add word.", true); // add word window.
	private final JDialog delFrame = new JDialog(appFrame, "Confirm.", true); // remove word window.
	private final JDialog edtFrame = new JDialog(appFrame, "Edit word", true); // editing frame
	private final JDialog strDialog = new JDialog(appFrame, "Sentence Translator", true); //sentence translation dialog

	private final JPanel appPanel = new JPanel(new GridBagLayout()); // main panel.
	private final JPanel funPanel = new JPanel(new GridLayout(1, 0, 5, 0)); // word functional panel
	private final JPanel dfcPanel = new JPanel(new GridLayout(1, 0, 5, 0)); // definition functional panel
	private final JPanel wrdPanel = new JPanel(new GridLayout(0, 2));
	private final JPanel defPanel = new JPanel(); // definition area
	private final JPanel schPanel = new JPanel(); // searching area
	private final JPanel sbrPanel = new JPanel(); // search box area
	private final JPanel sgnPanel = new JPanel(); // suggestion area
	private final JPanel strPanel = new JPanel(); // sentence to be translated panel
	private final JPanel trsPanel = new JPanel(); // translated sentence panel
	private final JPanel trsPanelVi = new JPanel(); // translated sentence panel
	private final JPanel trsPanelEn = new JPanel(); // translated sentence panel

	private final JButton addButton = new JButton("Add word");
	private final JButton strButton = new JButton("Sentence Translator");
	private final JButton trsButton = new JButton("Translate");
	private final JButton delButton = new JButton(); // delete word
	private final JButton edtButton = new JButton(); // edit word
	private final JButton ttsButton = new JButton(); // pronounce word
	private final JButton stsButtonEn = new JButton(); // pronounce sentence
	private final JButton stsButtonVi = new JButton(); // pronounce sentence

	private final JLabel editTarget = new JLabel(" Word: ");

	JTextField schwd = new JTextField("Word here");
	JTextArea def = new JTextArea("Definition here");
	JTextArea sntce = new JTextArea("Sentence here");
	JTextArea trs = new JTextArea("Translated sentence here");
	JList<String> sgn = new JList<>();
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
	 * Make a button flat
	 */
	public void makeFlat(JButton b) {
		b.setBackground(new Color(188, 188, 188));
		b.setFocusPainted(false);
		// b.setBorderPainted(false);
	}

	public void addBorder(JTextArea tA) {
		tA.setBorder(BorderFactory.createLineBorder(new Color(127, 138, 148)));
	}

	/**
	 * Add action to components.
	 */
	public void addAction() {
		addButton.addActionListener(e -> addFrame.setVisible(true));

		delButton.addActionListener(e -> {
			if (sgn.getSelectedValue() == null) {
				JOptionPane.showMessageDialog(appFrame, "You must select a word in the dictionary!");
			} else delFrame.setVisible(true);
		});

		edtButton.addActionListener(e -> {
			if (sgn.getSelectedValue() == null) {
				JOptionPane.showMessageDialog(appFrame, "You must select a word in the dictionary!");
			} else {
				editTarget.setText(" " + sgn.getSelectedValue());
				edtFrame.setVisible(true);
			}
		});

		trsButton.addActionListener(e ->
				trs.setText(cmd.sentenceTranslator(sntce.getText())));

		strButton.addActionListener(e -> strDialog.setVisible(true));

		schwd.getDocument().addDocumentListener(new DocumentListener() {
			public void changedUpdate(DocumentEvent e) {
				//do nothing
			}
			public void insertUpdate(DocumentEvent e) {
				String[] sgn_list = cmd.dictionarySearchSamePrefix(schwd.getText(), mn.getDict());
				if(schwd.getText().isEmpty()) {
					sgn_list = new String[mn.getDict().getLength()];
					for (int i = 0; i < mn.getDict().getLength(); ++i) {
						sgn_list[i] = mn.getDict().getWord(i).getWord_target();
					}
				}
				sgn.setListData(sgn_list);
				def.setText(cmd.dictionarySearchExact(schwd.getText(), mn.getDict()));
			}
			public void removeUpdate(DocumentEvent e) {
				String[] sgn_list = cmd.dictionarySearchSamePrefix(schwd.getText(), mn.getDict());
				if(schwd.getText().isEmpty()) {
					sgn_list = new String[mn.getDict().getLength()];
					for (int i = 0; i < mn.getDict().getLength(); ++i) {
						sgn_list[i] = mn.getDict().getWord(i).getWord_target();
					}
				}
				sgn.setListData(sgn_list);
				def.setText(cmd.dictionarySearchExact(schwd.getText(), mn.getDict()));
			}
		});

		sgn.addListSelectionListener(e -> {
			if(e.getValueIsAdjusting()) {
				return;
			}
			def.setText(cmd.dictionarySearchExact(sgn.getSelectedValue(), mn.getDict()));
		});
	}

	/**
	 * Add word feature
	 */
	public void addAddFrame() {
        addFrame.setSize(300, 300);
        addFrame.setLocationRelativeTo(null);
        addFrame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);

        JPanel mainPanel = new JPanel(new GridBagLayout());
        JPanel targPanel = new JPanel(new GridLayout(2, 0));
        JPanel pronPanel = new JPanel(new GridLayout(2, 0));
        JPanel explPanel = new JPanel(new GridBagLayout());

        JLabel targ = new JLabel(" New Word:");
        JLabel pron = new JLabel(" Pronunciation: ");
        JLabel expl = new JLabel(" Definition:");

        JTextField targField = new JTextField();
        JTextField pronField = new JTextField();
        JTextArea explField = new JTextArea();

        addBorder(explField);
        explField.setLineWrap(true);
        explField.setWrapStyleWord(true);

        JButton finishAdd = new JButton("Add");
        makeFlat(finishAdd);

        targPanel.add(targ);
        targPanel.add(targField);

        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.BOTH;

        GBCfill(c, 0, 3, 0, 0);
        mainPanel.add(finishAdd, c);

        GBCfill(c, 0, 1, 1, 1);
        targPanel.add(targField, c);

        GBCfill(c, 0, 0, 1, 0);
        mainPanel.add(targPanel, c);

        pronPanel.add(pron);
        pronPanel.add(pronField);

        GBCfill(c, 0, 1, 1, 0);
        mainPanel.add(pronPanel, c);

        GBCfill(c, 0, 0, 1, 0);
        explPanel.add(expl, c);

        GBCfill(c, 0, 1, 1, 1);
        explPanel.add(explField, c);

        GBCfill(c, 0, 2, 1, 1);
        mainPanel.add(explPanel, c);

        addFrame.add(mainPanel);

        targField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    pronField.requestFocus();
                }
            }
        });

        pronField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    explField.requestFocus();
                }
            }
        });

        finishAdd.addActionListener(e -> {
            if (targField.getText().length() == 0) {
                return;
            }

            if (targField.getText().contains("@")) {
                JOptionPane.showMessageDialog(addFrame, "Must not contain '@' character!");
                return;
            }

            if (explField.getText().length() == 0 && pronField.getText().length() == 0) {
                return;
            }

            String target = targField.getText();
            String explain = "/" + pronField.getText() + "/\n" + explField.getText();
            for (int i = explain.length() - 1; i >= 0; --i) {
                if (explain.charAt(i) == '\n') {
                    explain = explain.substring(0, explain.length() - 1);
                } else {
                    break;
                }
            }
            explain+="\n";

            Word word = new Word(target, explain);

            if (mn.getDict().existed(word)) {
                JOptionPane.showMessageDialog(addFrame, "This word is already existed!");
                return;
            }

            mn.getDict().addWord(word);
            mn.addToFile(target, explain);
            targField.setText("");
            pronField.setText("");
            explField.setText("");
        });
    }

	/**
	 * Remove word feature.
	 */
	public void addDelFrame() {
		setDelButton(delButton);

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
				mn.deleteFromFile(sgn.getSelectedValue(), def.getText());
			}
			delFrame.dispose();
		});

		no.addActionListener(e -> delFrame.dispose());
	}

	/**
	 * Sentence translator window.
	 */
	public void addStrDialog() {
		strDialog.setSize(350, 200);
		strDialog.setLocationRelativeTo(null);
		strDialog.add(strPanel);

		strPanel.setLayout(new GridBagLayout());
		trsPanel.setLayout(new GridBagLayout());
		trsPanelVi.setLayout(new GridBagLayout());
		trsPanelEn.setLayout(new GridBagLayout());

		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.BOTH;
		c.insets = new Insets(2, 2, 2, 2);

		sntce.setLineWrap(true);
		sntce.setWrapStyleWord(true);
		trs.setEditable(false);
		trs.setLineWrap(true);
		trs.setWrapStyleWord(true);

		stsButtonEn.addActionListener(e -> {
			try {
		    	if(!sntce.getText().isEmpty()) {
		    		cmd.speak(sntce.getText(), "en-US");
				}
			} catch(Exception ev) {
		    	ev.printStackTrace();
			}
		});

		stsButtonVi.addActionListener(e -> {
			try {
		    	if(!trs.getText().isEmpty()) {
		    		cmd.speak(trs.getText(), "vi");
				}
			} catch(Exception ev) {
		    	ev.printStackTrace();
			}
		});

		addBorder(trs);
		addBorder(sntce);

		GBCfill(c, 0, 0, 1, 1);
		strPanel.add(trsPanel, c);

		GBCfill(c, 0, 0, 1, 0);
		trsPanel.add(trsPanelEn, c);

		GBCfill(c, 0, 1, 1, 1);
		trsPanel.add(sntce, c);

		GBCfill(c, 0, 0, 1, 1);
		trsPanelEn.add(trsButton, c);
		makeFlat(trsButton);

		GBCfill(c, 1, 0, 0, 1);
		trsPanelEn.add(stsButtonEn, c);
		makeFlat(stsButtonEn);
		setAudioButton(stsButtonEn);

		GBCfill(c, 1, 0, 1, 1);
		strPanel.add(trsPanelVi, c);

		GBCfill(c, 0, 0, 0, 0);
		trsPanelVi.add(stsButtonVi, c);
		makeFlat(stsButtonVi);
		setAudioButton(stsButtonVi);

		GBCfill(c, 0, 1, 1, 1);
		trsPanelVi.add(trs, c);
	}

	/**
	 * Add audio icon to the button
	 * @param b The button whose icon needs to be added
	 */
	public void setAudioButton(JButton b) {
		ImageIcon icon = new ImageIcon("./icon/audio.png");
		Image img = icon.getImage();
		b.setIcon(new ImageIcon(img.getScaledInstance(15, 15, Image.SCALE_SMOOTH)));
	}

	/**
	 * Add edit icon to the button
	 * @param b The button whose icon needs to be added
	 */
	public void setEditButton(JButton b) {
		ImageIcon icon = new ImageIcon("./icon/edit.png");
		Image img = icon.getImage();
		b.setIcon(new ImageIcon(img.getScaledInstance(15, 15, Image.SCALE_SMOOTH)));
	}

	/**
	 * Add delete icon to the button
	 * @param b The button whose icon needs to be added
	 */
	public void setDelButton(JButton b) {
		ImageIcon icon = new ImageIcon("./icon/delete.png");
		Image img = icon.getImage();
		b.setIcon(new ImageIcon(img.getScaledInstance(15, 15, Image.SCALE_SMOOTH)));
	}

	/**
	 * Edit a specific word window.
	 */
	public void addEditFrame() {
		setEditButton(edtButton);

		edtFrame.setSize(300, 300);
		edtFrame.setLocationRelativeTo(null);
		edtFrame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);

		JPanel mainPanel = new JPanel(new GridBagLayout());
		JPanel pronPanel = new JPanel(new GridLayout(2, 0));
		JPanel explPanel = new JPanel(new GridBagLayout());

		JLabel pron = new JLabel(" Pronunciation: ");
		JLabel expl = new JLabel(" Definition:");

		JTextField pronField = new JTextField();
		JTextArea explField = new JTextArea();

		addBorder(explField);
		explField.setLineWrap(true);
		explField.setWrapStyleWord(true);

		JButton finishUpdate = new JButton("Update");

		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.BOTH;

		GBCfill(c, 0, 3, 0, 0);
		mainPanel.add(finishUpdate, c);

		GBCfill(c, 0, 0, 1, 0);
		mainPanel.add(editTarget, c);

		pronPanel.add(pron);
		pronPanel.add(pronField);

		GBCfill(c, 0, 1, 1, 0);
		mainPanel.add(pronPanel, c);

		GBCfill(c, 0, 0, 1, 0);
		explPanel.add(expl, c);

		GBCfill(c, 0, 1, 1, 1);
		explPanel.add(explField, c);

		GBCfill(c, 0, 2, 1, 1);
		mainPanel.add(explPanel, c);

		edtFrame.add(mainPanel);

		pronField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					explField.requestFocus();
				}
			}
		});

		finishUpdate.addActionListener(e -> {
			if (explField.getText().length() == 0 && pronField.getText().length() == 0) {
				return;
			}

			String explain = "/" + pronField.getText() + "/\n" + explField.getText();
			int index = -1;
			for (int i = 0; i < mn.getDict().getLength(); ++i) {
				if (mn.getDict().getWord(i).getWord_target().equals(sgn.getSelectedValue())) {
					index = i;
					break;
				}
			}
			mn.getDict().getWord(index).setWord_explain(explain);
			mn.editFromFile(sgn.getSelectedValue(), def.getText(), explain);
			pronField.setText("");
			explField.setText("");
		});
	}

	/**
	 * Text to speech feature.
	 */
	public void setTtsButton() {
		setAudioButton(ttsButton);
		ttsButton.addActionListener(e -> {
		    try {
		    	if (sgn.getSelectedValue() != null) {
		    		cmd.speak(sgn.getSelectedValue(), "en-US");
				} else {
					JOptionPane.showMessageDialog(delFrame, "You must select a word in the scroll panel");
				}
			} catch (Exception ev) {
		    	ev.printStackTrace();
			}
        });
	}

	/**
	 * Run the App with User Interface.
	 */
	public void runApplication() {
		addAddFrame();
		addDelFrame();
		addStrDialog();
		addEditFrame();

		setEditButton(edtButton);
		setAudioButton(ttsButton);
		setTtsButton();

		ImageIcon icon = new ImageIcon("./icon/find.png");
		Image img = icon.getImage();
		icon = new ImageIcon(img.getScaledInstance(15, 15, Image.SCALE_SMOOTH));
		JLabel findLabel = new JLabel(icon, JLabel.CENTER);

		def.setLineWrap(true);
		def.setWrapStyleWord(true);
		GridBagConstraints c = new GridBagConstraints();

		sgn_scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		def_scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		def.setEditable(false);

		addAction();

		wrdPanel.add(schPanel);
		wrdPanel.add(defPanel);
		funPanel.add(addButton);
		funPanel.add(strButton);
		dfcPanel.add(ttsButton);
		dfcPanel.add(edtButton);
		dfcPanel.add(delButton);

		makeFlat(ttsButton);
		makeFlat(edtButton);
		makeFlat(delButton);
		makeFlat(strButton);
		makeFlat(addButton);

		defPanel.setLayout(new GridBagLayout());
		schPanel.setLayout(new GridBagLayout());
		sbrPanel.setLayout(new GridBagLayout());
		sgnPanel.setLayout(new GridBagLayout());

		c.fill = GridBagConstraints.BOTH;
		c.insets = new Insets(2, 2, 2, 2);

		GBCfill(c, 0, 0, 0, 0);
		defPanel.add(dfcPanel, c);

		GBCfill(c, 0, 1, 1, 1);
		defPanel.add(def_scroll, c);

		GBCfill(c, 0, 0, 1, 1);
		sbrPanel.add(schwd, c);

		GBCfill(c, 1, 0, 0, 1);
		sbrPanel.add(findLabel, c);

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