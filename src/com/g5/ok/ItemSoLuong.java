/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.g5.ok;

import java.awt.Dimension;
import javax.swing.JButton;

/**
 *
 * @author anhba
 */
public class ItemSoLuong extends javax.swing.JPanel {

    JButton btn = new JButton();
    int w = 50;
    int h = 50;

    /**
     * Creates new form NewJPanel
     */
    public ItemSoLuong() {
        initComponents();

        this.add(btn);
    }

    public void loadSize() {
        btn.setSize(w, h);
    }

    public void setW(int w) {
        this.w = w;
    }

    public void setH(int h) {

        this.h = h;
    }

    public void setText(String text) {

        btn.setText("");
        btn.setText(text);
    }

    public void setButton(String text, int w, int h) {
        btn.setSize(new Dimension(w, h));
        btn.setText("");
        btn.setText(text);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 45, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 45, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}