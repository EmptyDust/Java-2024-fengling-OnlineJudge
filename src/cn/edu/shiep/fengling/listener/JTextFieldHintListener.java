package cn.edu.shiep.fengling.listener;

import java.awt.Color;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.*;

public class JTextFieldHintListener implements FocusListener {
    private final String hintText;
    private final JTextArea jTextArea;
    public JTextFieldHintListener(JTextArea jTextArea, String hintText) {
        this.jTextArea = jTextArea;
        this.hintText = hintText;
        jTextArea.setText(hintText);
        jTextArea.setForeground(Color.GRAY);
    }

    @Override
    public void focusGained(FocusEvent e) {
        String temp = jTextArea.getText();
        if(temp.equals(hintText)) {
            jTextArea.setText("");
            jTextArea.setForeground(Color.BLACK);
        }
    }

    @Override
    public void focusLost(FocusEvent e) {
        String temp = jTextArea.getText();
        if(temp.isEmpty()) {
            jTextArea.setForeground(Color.GRAY);
            jTextArea.setText(hintText);
        }
    }
}