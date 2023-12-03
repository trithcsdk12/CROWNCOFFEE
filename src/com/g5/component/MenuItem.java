/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.g5.component;

import com.g5.util.Auth;
import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import javax.swing.Icon;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;

/**
 *
 * @author anhba
 */
public class MenuItem extends javax.swing.JPanel {

    private final List<EventMenuSelected> events = new ArrayList<>();
    private JPopupMenu popupMenu = new JPopupMenu();
    private JMenuItem menuItem = new JMenuItem();
    private boolean open = false;
    private int index;
    private boolean selected;
    private boolean mouseOver;
    private String text;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public boolean isOpen() {
        return open;
    }

    public void setOpen(boolean open) {
        this.open = open;
    }

    /**
     * Creates new form Menu
     */
    public MenuItem(Icon icon, String name, int index, String text) {
        popupMenu.setOpaque(false);
        popupMenu.setEnabled(false);
        popupMenu.setBorder(new EmptyBorder(1, 1, 1, 1));
        menuItem.setBorder(new EmptyBorder(1, 1, 1, 1));
        menuItem.setBackground(new Color(99, 126, 118));
        menuItem.setForeground(Color.WHITE);
        initComponents();
        setOpaque(false);
        lblIcon.setText("");
        lblIcon.setIcon(icon);
        lblName.setText(name);
        this.index = index;
//        this.addMouseListener(new MouseAdapter() {
//            public void mouseEntered(MouseEvent e) {
//
//                popupMenu.removeAll();
//                menuItem.setText(text);
//                popupMenu.add(menuItem);
//                if (!isOpen()) {
//                    popupMenu.show(MenuItem.this, e.getX() + 10, e.getY() - 10);
//                }
//
//            }
//
//            public void mouseExited(MouseEvent e) {
//                popupMenu.removeAll();
//                popupMenu.setVisible(false);
//            }
//        });
//                item.addMouseListener(new MouseAdapter() {
//            public void mouseEntered(MouseEvent e) {
//                popupMenu.removeAll();
//                menuItem.setText("aa");
//                popupMenu.add(menuItem);
//                popupMenu.show(item, e.getX(), e.getY());
//            }
//
//            public void mouseExited(MouseEvent e) {
//                popupMenu.removeAll();
//                popupMenu.setVisible(false);
//            }
//        });
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                mouseOver = true;

            }

            @Override
            public void mouseExited(MouseEvent e) {
                mouseOver = false;
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                if (SwingUtilities.isLeftMouseButton(e)) {
                    if (mouseOver) {
                        if (Auth.isLogin() && (Auth.user.getVaitro() == 0 && index == 1)) {
                            selected = false;

                        } else {
                            selected = true;
                        }

                        repaint();
                        // even
                        runEvent();
                    }
                }
            }

        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        if (selected) {
            Graphics2D g2 = (Graphics2D) g;
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(new Color(1, 122, 167));
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5f));
            g2.fillRect(0, 0, getWidth(), getHeight());
            g2.setComposite(AlphaComposite.SrcOver);
            g2.setColor(new Color(245, 245, 245));
            g2.fillRect(0, 0, 2, getHeight());
        }
        super.paintComponent(g); //To change body of generated methods, choose Tools | Templates.
    }

    private void runEvent() {
        for (EventMenuSelected event : events) {
            event.selected(index);
        }
    }

    public void addEvent(EventMenuSelected event) {
        events.add(event);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lblIcon = new javax.swing.JLabel();
        lblName = new javax.swing.JLabel();

        lblIcon.setPreferredSize(new java.awt.Dimension(30, 30));

        lblName.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblName.setForeground(new java.awt.Color(255, 255, 255));
        lblName.setText("Menu name");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(lblIcon, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(50, 50, 50)
                .addComponent(lblName)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(lblIcon, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblName, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(10, 10, 10))
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel lblIcon;
    private javax.swing.JLabel lblName;
    // End of variables declaration//GEN-END:variables
}
