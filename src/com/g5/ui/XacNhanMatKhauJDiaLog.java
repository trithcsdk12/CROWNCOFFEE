/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JDialog.java to edit this template
 */
package com.g5.ui;

import com.g5.entity.NhanVien;
import com.g5.entityDAO.NhanVienDAOImpl;
import com.g5.util.Auth;
import java.awt.*;
import javax.swing.JFrame;
import java.util.List;
import javax.swing.JOptionPane;
import com.g5.DAO.NhanVienDAOinterface;
import com.g5.util.TextMes;

/**
 *
 * @author KIM NGAN
 */
public class XacNhanMatKhauJDiaLog extends javax.swing.JDialog {

    NhanVienDAOinterface dao = new NhanVienDAOImpl();
    private static XacNhanMatKhauJDiaLog dnJDialog = new XacNhanMatKhauJDiaLog(new javax.swing.JFrame(), true);
    public static final org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(MainFrame.class);
    private JFrame frame;

    /**
     * Creates new form DoiMatKhauJDialog
     */
    public XacNhanMatKhauJDiaLog(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        this.frame = (JFrame) parent;
        initComponents();
        setLocationRelativeTo(null);
        setTitle("Đổi mật khẩu");
        txtTaiKhoan.setText((QuenMatKhauJDialog.getMaNV() + "").trim());
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        btnHoanThanh = new javax.swing.JButton();
        btnThoat = new javax.swing.JButton();
        txtTaiKhoan = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        txtMK = new javax.swing.JPasswordField();
        txtXNMK = new javax.swing.JPasswordField();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setBackground(new java.awt.Color(255, 255, 255));

        jPanel1.setPreferredSize(new java.awt.Dimension(378, 370));

        jLabel2.setFont(new java.awt.Font("Times New Roman", 1, 24)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(51, 51, 255));
        jLabel2.setText("Xác nhân mật khẩu");

        jLabel5.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel5.setText("Xác nhận mật khẩu mới");

        btnHoanThanh.setBackground(new java.awt.Color(51, 255, 255));
        btnHoanThanh.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btnHoanThanh.setText("Hoàn Thành ");
        btnHoanThanh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnHoanThanhActionPerformed(evt);
            }
        });

        btnThoat.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btnThoat.setText("Thoát");
        btnThoat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThoatActionPerformed(evt);
            }
        });

        txtTaiKhoan.setEditable(false);
        txtTaiKhoan.setText("1");
        txtTaiKhoan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtTaiKhoanActionPerformed(evt);
            }
        });

        jLabel4.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel4.setText("Mật khẩu mới");

        jLabel6.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel6.setText("Tài khoản");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(80, 80, 80)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel6)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(btnHoanThanh, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnThoat, javax.swing.GroupLayout.DEFAULT_SIZE, 76, Short.MAX_VALUE))
                    .addComponent(jLabel5)
                    .addComponent(jLabel4)
                    .addComponent(txtTaiKhoan)
                    .addComponent(jLabel2)
                    .addComponent(txtMK)
                    .addComponent(txtXNMK))
                .addContainerGap(87, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(48, 48, 48)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtTaiKhoan, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(3, 3, 3)
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtMK, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtXNMK, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnHoanThanh, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnThoat, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(58, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 388, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnHoanThanhActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHoanThanhActionPerformed

        DoiMatKhau();

    }//GEN-LAST:event_btnHoanThanhActionPerformed

    private void btnThoatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThoatActionPerformed
        // TODO add your handling code here:
        System.exit(0);
    }//GEN-LAST:event_btnThoatActionPerformed

    private void txtTaiKhoanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtTaiKhoanActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtTaiKhoanActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(XacNhanMatKhauJDiaLog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(XacNhanMatKhauJDiaLog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(XacNhanMatKhauJDiaLog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(XacNhanMatKhauJDiaLog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                XacNhanMatKhauJDiaLog dialog = new XacNhanMatKhauJDiaLog(new javax.swing.JFrame(), true);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnHoanThanh;
    private javax.swing.JButton btnThoat;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPasswordField txtMK;
    private javax.swing.JTextField txtTaiKhoan;
    private javax.swing.JPasswordField txtXNMK;
    // End of variables declaration//GEN-END:variables
    private String maNV;

    public String getmaNV() {
        return maNV;
    }

    public void setmaNV(String maNV) {
        this.maNV = maNV;
    }
    NhanVienDAOImpl nvdao = new NhanVienDAOImpl();
    NhanVien nv = new NhanVien();

    public void DoiMatKhau() {

        String mkmoi = txtMK.getText().trim();
        String XNmk = txtXNMK.getText().trim();
        if (mkmoi.trim().length() == 0 || XNmk.trim().length() == 0) {
            return;
        }
        List<NhanVien> list = dao.getAll();
        if (!mkmoi.equals(XNmk)) {
            JOptionPane.showMessageDialog(null, "Xác nhận mật khẩu không đúng");
        } else {
            for (NhanVien nhanVien : list) {
                if (txtTaiKhoan.getText().trim().equalsIgnoreCase((String.valueOf(nhanVien.getMaNV())).trim())) {
                    nvdao.forgotpass(mkmoi, Integer.parseInt(txtTaiKhoan.getText().trim()));
                    if (TextMes.Comform(this, "Đổi mật khẩu thành công\nbạn có muốn quay lại đăng nhập")) {
                        this.dispose();
                        DangNhapJDialog.setStatus(true);
                        return;
                    } else {
                        System.exit(0);
                    };
                    
                }
                
            }
            if(TextMes.Comform(this, "Đổi mật khẩu thất bại vui lòng thử lại")){
            System.exit(0);
            }else{
            System.exit(0);
            };
            

        }
    }
}
