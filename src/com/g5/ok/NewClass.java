/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.g5.ok;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JFrame;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

/**
 *
 * @author anhba
 */
public class NewClass {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Blurred Overlay Example");
            frame.setSize(500,500);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            JLayeredPane layeredPane = new JLayeredPane();
            layeredPane.setSize(500,500);

            JPanel mainPanel = new JPanel();
            mainPanel.setSize(100,100);
            mainPanel.setBackground(Color.lightGray);
            mainPanel.setPreferredSize(new Dimension(200, 200));

            JPanel overlayPanel = new JPanel();
            overlayPanel.setBackground(new Color(255, 255, 255, 150)); // Màu mờ có thể được đặt ở đây

            // Thêm overlayPanel vào layeredPane với lớp mờ
            layeredPane.add(mainPanel, JLayeredPane.DEFAULT_LAYER);
            layeredPane.add(overlayPanel, JLayeredPane.POPUP_LAYER);

            // Thêm MouseListener để lắng nghe sự kiện khi click chuột vào mainPanel
            mainPanel.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    // Hiển thị hoặc ẩn overlayPanel khi click chuột
                    overlayPanel.setVisible(!overlayPanel.isVisible());
                }
            });

            frame.getContentPane().add(layeredPane);
            frame.pack();
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }
}
