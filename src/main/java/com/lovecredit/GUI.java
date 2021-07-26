package com.lovecredit;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;

public class GUI {

    private JPanel panel;
    private JFrame frame = new JFrame();
    private HashMap<String, JLabel> keyPanelHashMap = new HashMap<>();
    private HashMap<String, JLabel> valuePanelHashMap = new HashMap<>();

    public GUI() {
        frame.setSize(500, 1500);
        frame.setLocation(300,200);

        panel = new JPanel();
        panel.setBorder(BorderFactory.createEmptyBorder(30,30,10,30));
        panel.setLayout(new GridLayout(0, 1));
        frame.add(panel, BorderLayout.CENTER);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("ForegroundWindowDurationMeasure");
        frame.pack();
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        new GUI();
    }

    public void visualizeForegroundCheckData(HashMap<String, Double> hashMap) {
        if (keyPanelHashMap.isEmpty()) {
            for (String key : hashMap.keySet()) {
                addNewKeyToHashMap(hashMap, key);
            }
        } else {
            for (String key : hashMap.keySet()) {
                if (valuePanelHashMap.containsKey(key)) {
                    valuePanelHashMap.get(key).setText(String.valueOf(hashMap.get(key)));
                } else {
                    addNewKeyToHashMap(hashMap, key);
                }
            }
        }
    }

    private void addNewKeyToHashMap(HashMap<String, Double> hashMap, String key) {
        keyPanelHashMap.put(key, new JLabel(key + ": "));
        valuePanelHashMap.put(key, new JLabel(String.valueOf(hashMap.get(key))));
        panel.add(keyPanelHashMap.get(key));
        panel.add(valuePanelHashMap.get(key));
    }
}
