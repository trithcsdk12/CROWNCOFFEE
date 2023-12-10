package com.g5.util;

import com.g5.ui.MainFrame;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.UIManager;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
/**
 *
 * @author anhba
 */
public class TextMes {

    public static void Alert(Component comp, String text) {


        UIManager.put("OptionPane.messageForeground", Color.BLACK);
        UIManager.put("OptionPane.messageFont", new Font("Tohama", Font.BOLD, 14));
        UIManager.put("OptionPane.okButtonText", "Oke");
        JOptionPane.showMessageDialog(comp, text, "Thông báo", JOptionPane.INFORMATION_MESSAGE);
    }

    public static boolean Comform(Component comp, String text) {
//        ImageIcon icon = new ImageIcon("path/to/warning.png");
//        UIManager.put("OptionPane.warningIcon", icon);

        UIManager.put("OptionPane.messageForeground", Color.BLACK);
        UIManager.put("OptionPane.messageFont", new Font("Tohama", Font.BOLD, 14));
        UIManager.put("OptionPane.yesButtonText", "Có");
        UIManager.put("OptionPane.noButtonText", "Không");
        int choose = JOptionPane.showConfirmDialog(comp, text, "Thông báo", JOptionPane.YES_NO_OPTION);
        if (choose == JOptionPane.YES_NO_OPTION) {
            return true;
        }
        return false;
    }

    public static void alertRed(Component parent, String message) {

        UIManager.put("OptionPane.messageForeground", Color.BLACK);
        UIManager.put("OptionPane.messageFont", new Font("Tohama", Font.BOLD, 14));
        UIManager.put("OptionPane.okButtonText", "Oke");
        JOptionPane.showMessageDialog(parent, new JLabel("<html><font color='red'>" + message + "</font></html>"), "Thông báo",
                JOptionPane.ERROR_MESSAGE);
    }

}
