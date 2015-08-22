package com.dalthow.etaron;

import java.awt.BorderLayout ;
import java.awt.Container ;
import java.awt.GridBagConstraints ;
import java.awt.GridBagLayout ;
import java.awt.Insets ;

import javax.swing.JComboBox;
import javax.swing.JFrame ;
import javax.swing.JLabel;
import javax.swing.JPanel ;
import javax.swing.JSlider;
import javax.swing.JTextField ;
import javax.swing.JTextPane ;

public class Config extends JFrame
{
	public Config()
	{
		initComponents();
		this.pack();
		this.setVisible(true);
	}
	private void initComponents() {

		panel1 = new JPanel();
		label1 = new JLabel();
		comboBox1 = new JComboBox();
		label2 = new JLabel();
		slider2 = new JSlider();
		label3 = new JLabel();
		slider1 = new JSlider();
		label4 = new JLabel();
		textField1 = new JTextField();
		label5 = new JLabel();
		textField2 = new JTextField();
		textPane1 = new JTextPane();

		//======== this ========
		setTitle("Setting");
		Container contentPane = getContentPane();
		contentPane.setLayout(new BorderLayout());

		//======== panel1 ========
		{
			panel1.setLayout(new GridBagLayout());

			//---- label1 ----
			label1.setText("Resolution:");
			panel1.add(label1, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
				GridBagConstraints.CENTER, GridBagConstraints.BOTH,
				new Insets(0, 0, 5, 5), 0, 0));
			panel1.add(comboBox1, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0,
				GridBagConstraints.CENTER, GridBagConstraints.BOTH,
				new Insets(0, 0, 5, 0), 0, 0));

			//---- label2 ----
			label2.setText("Music:");
			panel1.add(label2, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0,
				GridBagConstraints.CENTER, GridBagConstraints.BOTH,
				new Insets(0, 0, 5, 5), 0, 0));
			panel1.add(slider2, new GridBagConstraints(1, 1, 1, 1, 0.0, 0.0,
				GridBagConstraints.CENTER, GridBagConstraints.BOTH,
				new Insets(0, 0, 5, 0), 0, 0));

			//---- label3 ----
			label3.setText("Sound:");
			panel1.add(label3, new GridBagConstraints(0, 2, 1, 1, 0.0, 0.0,
				GridBagConstraints.CENTER, GridBagConstraints.BOTH,
				new Insets(0, 0, 5, 5), 0, 0));
			panel1.add(slider1, new GridBagConstraints(1, 2, 1, 1, 0.0, 0.0,
				GridBagConstraints.CENTER, GridBagConstraints.BOTH,
				new Insets(0, 0, 5, 0), 0, 0));

			//---- label4 ----
			label4.setText("Email:");
			panel1.add(label4, new GridBagConstraints(0, 4, 1, 1, 0.0, 0.0,
				GridBagConstraints.CENTER, GridBagConstraints.BOTH,
				new Insets(0, 0, 5, 5), 0, 0));
			panel1.add(textField1, new GridBagConstraints(1, 4, 1, 1, 0.0, 0.0,
				GridBagConstraints.CENTER, GridBagConstraints.BOTH,
				new Insets(0, 0, 5, 0), 0, 0));

			//---- label5 ----
			label5.setText("Password:");
			panel1.add(label5, new GridBagConstraints(0, 5, 1, 1, 0.0, 0.0,
				GridBagConstraints.CENTER, GridBagConstraints.BOTH,
				new Insets(0, 0, 0, 5), 0, 0));
			panel1.add(textField2, new GridBagConstraints(1, 5, 1, 1, 0.0, 0.0,
				GridBagConstraints.CENTER, GridBagConstraints.BOTH,
				new Insets(0, 0, 0, 0), 0, 0));
		}
		contentPane.add(panel1, BorderLayout.CENTER);
		pack();
		setLocationRelativeTo(getOwner());
		// JFormDesigner - End of component initialization  //GEN-END:initComponents
	}

	// JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
	// Generated using JFormDesigner Evaluation license - Trevi Awater
	private JPanel panel1;
	private JLabel label1;
	private JComboBox comboBox1;
	private JLabel label2;
	private JSlider slider2;
	private JLabel label3;
	private JSlider slider1;
	private JLabel label4;
	private JTextField textField1;
	private JLabel label5;
	private JTextField textField2;
	private JTextPane textPane1;
	// JFormDesigner - End of variables declaration  //GEN-END:variables
}
