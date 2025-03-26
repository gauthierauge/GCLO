package fr.gclo.ui.composants;

import javax.swing.JComboBox;

@SuppressWarnings({ "serial", "rawtypes" })
public class JSDWComboBox extends JComboBox {

    public JSDWComboBox(String name) {
        super();

		setName(name);
		
		setSelectedIndex(-1);
		
		setMaximumRowCount(20);
    }

}
