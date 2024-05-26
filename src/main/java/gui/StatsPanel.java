package gui;

import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.BorderLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class StatsPanel extends JPanel {
    private JLabel statsLabel;
    private JTextArea outputArea;
    private JTextField inputField;
    private JButton submitButton;

    public StatsPanel() {
        setLayout(new BorderLayout());

        // Stats label
        statsLabel = new JLabel("Player Stats: ");
        add(statsLabel, BorderLayout.NORTH);

        // Output area
        outputArea = new JTextArea(10, 20);
        outputArea.setEditable(false);
        add(outputArea, BorderLayout.CENTER);

        // Input field and submit button
        JPanel inputPanel = new JPanel();
        inputField = new JTextField(15);
        submitButton = new JButton("Submit");
        inputPanel.add(inputField);
        inputPanel.add(submitButton);
        add(inputPanel, BorderLayout.SOUTH);

        // Add action listener for submit button
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String inputText = inputField.getText();
                outputArea.append("Input submitted: " + inputText + "\n");
                inputField.setText(""); // Clear the input field
            }
        });
    }

    public void updateStats(String stats) {
        statsLabel.setText("Player Stats: " + stats);
    }

    public void appendOutput(String text) {
        outputArea.append(text + "\n");
    }
}

