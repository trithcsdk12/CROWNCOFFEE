/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.g5.component;

import com.g5.util.XImage;
import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

/**
 *
 * @author anhba
 */
public class Info extends javax.swing.JPanel {

    private float alpha;

    public void setAlpha(float alpha) {
        this.alpha = alpha;
    }

    /**
     * Creates new form Bottom
     */
    public Info() {
        initComponents();
        setOpaque(false);
        setBackground(new Color(65, 152, 216));
        setInfo();
    }

    @Override
    public void paint(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha));
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(getBackground());
        g2.fillRoundRect(5, 5, getWidth() - 15, getHeight() - 10, 20, 20);
        super.paint(g); //To change body of generated methods, choose Tools | Templates.
    }

    public void setInfo() {
        txtName.setText("Tên: Anhbao5cm");
        txtRole.setText("Chức vụ: Nhân viên");
        XImage.selectImage2("haha.png", lblImage,180,120);

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lblImage = new javax.swing.JLabel();
        txtName = new javax.swing.JLabel();
        txtRole = new javax.swing.JLabel();

        lblImage.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/g5/image/Name.png"))); // NOI18N
        lblImage.setBorder(javax.swing.BorderFactory.createEmptyBorder(5, 5, 5, 5));
        lblImage.setPreferredSize(new java.awt.Dimension(150, 120));

        txtName.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtName.setForeground(new java.awt.Color(255, 255, 255));
        txtName.setText("Tên");

        txtRole.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtRole.setForeground(new java.awt.Color(255, 255, 255));
        txtRole.setText("Chức vụ:");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(9, 9, 9)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtName)
                    .addComponent(txtRole)
                    .addComponent(lblImage, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(126, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblImage, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(9, 9, 9)
                .addComponent(txtName)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(txtRole)
                .addGap(10, 10, 10))
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel lblImage;
    private javax.swing.JLabel txtName;
    private javax.swing.JLabel txtRole;
    // End of variables declaration//GEN-END:variables
}
