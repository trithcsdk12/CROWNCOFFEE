package com.g5.ok;

import com.g5.util.XImage;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;


/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author anhba
 */
public class SanPhamClone extends javax.swing.JPanel {

    final int w = 180;
    final int h = 220;
    String imageURL = "null.png";
    JPanel overlayPanel = new JPanel();
    public boolean selected = false;
    public String maspChon = "0";

    public int masp = 0;
    public String thongtin = "";
    public String tensp = "Tên SP";

    public void setThongTin(String tensp, String tt) {
        this.tensp = tensp;
        this.thongtin = tensp + tt;
        
        txtThongTin.removeAll();
        txtThongTin.setText("");
        txtThongTin.setText(thongtin);

    }


    public String getTenSP() {
        return this.tensp;
    }

    public boolean getSelectdSP() {
        return this.selected;
    }

    public void setMaSP(int masp) {
        this.masp = masp;
        jCheckBox1.setText("");
        jCheckBox1.setText(masp + "");
    }

    public int getMaSP() {
        return this.masp;
    }

    public String getMaSPDuocChon() {
        return this.maspChon;
    }

    /**
     * Creates new form NewJPanel
     */
    public SanPhamClone() {
        initComponents();
        //   setOpaque(false);
        txtThongTin.setLineWrap(true);
        txtThongTin.setWrapStyleWord(true);
        //  setOpaque(false);
        //loadImage();

    }

    public void selectImage() {
        JFileChooser fileChooser = new JFileChooser("src\\com\\g5\\image");
        if (fileChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            System.out.println(fileChooser.getSelectedFile());
            File file = fileChooser.getSelectedFile();

            if (XImage.save(file)) {

                try {
                    System.out.println(file.toString());
                    BufferedImage originalImage = ImageIO.read(new File(file.toString()));

                    ImageIcon icon = new ImageIcon(originalImage);

                    Image image = icon.getImage();

                    Image scaledImage = image.getScaledInstance(w, h, Image.SCALE_DEFAULT);

                    ImageIcon scaledIcon = new ImageIcon(scaledImage);
                    txtAnh.removeAll();
                    txtAnh.setText("");
                    txtAnh.setIcon(null);
                    txtAnh.setToolTipText(file.getName());
                    txtAnh.setIcon(scaledIcon);

                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        }
    }

    public void loadImage() {

        File imageFile = new File(XImage.urlImage(imageURL));

        if (!imageFile.exists()) {
            imageFile = new File(XImage.urlImage("null.png"));
            System.out.println("Tập tin không tồn tại.");

        }
        try {
            BufferedImage originalImage = ImageIO.read(imageFile);

            ImageIcon icon = new ImageIcon(originalImage);

            Image image = icon.getImage();

            Image scaledImage = image.getScaledInstance(w, h, Image.SCALE_DEFAULT);
            txtAnh.removeAll();
            txtAnh.setText("");
            txtAnh.setIcon(null);
            ImageIcon scaledIcon = new ImageIcon(scaledImage);
            txtAnh.setToolTipText("");
            txtAnh.setIcon(scaledIcon);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void addSuKien() {
        SanPhamClone.this.setCursor(new Cursor(Cursor.HAND_CURSOR));
        txtAnh.setCursor(new Cursor(Cursor.HAND_CURSOR));
        txtThongTin.setCursor(new Cursor(Cursor.HAND_CURSOR));

        jCheckBox1.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                //  jCheckBox1.setSelected(!jCheckBox1.isSelected());

                if (jCheckBox1.isSelected()) {
                    selected = true;
                } else {
                    selected = false;
                }

            
                ManChinh.loadCacSP();
            }
        });

        txtThongTin.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                jCheckBox1.setSelected(!jCheckBox1.isSelected());

                if (jCheckBox1.isSelected()) {
                    selected = true;
                } else {
                    selected = false;
                }

            
                ManChinh.loadCacSP();
            }
        });
        this.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                jCheckBox1.setSelected(!jCheckBox1.isSelected());

                if (jCheckBox1.isSelected()) {
                    selected = true;
                } else {
                    selected = false;
                }

           
                ManChinh.loadCacSP();
            }
        });
    }

    public static String addGachNgang(String input) {
        StringBuilder result = new StringBuilder();

        // Duyệt qua từng ký tự trong chuỗi
        for (int i = 0; i < input.length(); i++) {
            // Thêm ký tự vào chuỗi kết quả và ký tự gạch ngang Unicode
            result.append(input.charAt(i)).append('\u0336');
        }

        // Chuyển đổi StringBuilder thành String và trả về
        return result.toString();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        txtAnh = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtThongTin = new javax.swing.JTextArea();
        jCheckBox1 = new javax.swing.JCheckBox();

        setBackground(new java.awt.Color(255, 255, 255));

        txtAnh.setText("Anh bảo clone");
        txtAnh.setPreferredSize(new java.awt.Dimension(180, 220));

        txtThongTin.setEditable(false);
        txtThongTin.setColumns(20);
        txtThongTin.setRows(5);
        txtThongTin.setText("Cà phê\n10,000 VND\n8,000 VND\nCà phê\n");
        txtThongTin.setAutoscrolls(false);
        txtThongTin.setFocusable(false);
        jScrollPane1.setViewportView(txtThongTin);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jCheckBox1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtAnh, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(txtAnh, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 3, Short.MAX_VALUE)
                .addComponent(jCheckBox1)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JCheckBox jCheckBox1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel txtAnh;
    private javax.swing.JTextArea txtThongTin;
    // End of variables declaration//GEN-END:variables
}
