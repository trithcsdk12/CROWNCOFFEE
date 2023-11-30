package com.g5.util;

import com.g5.ui.MainFrame;
import java.awt.Component;
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
        JOptionPane.showMessageDialog(comp, text, "Thông báo", JOptionPane.INFORMATION_MESSAGE);
    }

    public static boolean Comform(Component comp, String text) {
        UIManager.put("OptionPane.yesButtonText", "Có");
        UIManager.put("OptionPane.noButtonText", "Không");
        int choose = JOptionPane.showConfirmDialog(comp, text, "Thông báo", JOptionPane.YES_NO_OPTION);
        if(choose == JOptionPane.YES_NO_OPTION){
        return true;
        }
    return false;
    }

}
