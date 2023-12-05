/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package com.g5.form;

import com.g5.entity.HoaDon;
import com.g5.entityDAO.HoaDonDAO;
import com.g5.entityDAO.ThongKeDAO;
import com.toedter.calendar.JDateChooser;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import javax.swing.JLabel;
import javax.swing.event.AncestorEvent;
import javax.swing.event.AncestorListener;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Asus
 */
public class ThongKeJPanel extends javax.swing.JPanel {

    ThongKeDAO dao = new ThongKeDAO();
    HoaDonDAO hddao = new HoaDonDAO();

    /**
     * Creates new form ThongKeJPanel
     */
    public ThongKeJPanel() {
        initComponents();
        Font headerFont = new Font("Arial", Font.BOLD, 13);
        tblDoanhThu.getTableHeader().setFont(headerFont);
        tblSanPham.getTableHeader().setFont(headerFont);
        tblDoanhThu.setRowHeight(25); // Đặt chiều cao hàng là 25 pixel
        tblSanPham.setRowHeight(25); // Đặt chiều cao hàng là 25 pixel
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER); // Căn giữa nội dung ô
        tblDoanhThu.setDefaultRenderer(Object.class, centerRenderer); // Áp dụng renderer cho tất cả các ô
        tblSanPham.setDefaultRenderer(Object.class, centerRenderer); // Áp dụng renderer cho tất cả các ô
        tblDoanhThu.setBackground(Color.white); // Màu nền của bảng
        tblDoanhThu.setForeground(Color.black); // Màu chữ trong bảng
        tblSanPham.setBackground(Color.white); // Màu nền của bảng
        tblSanPham.setForeground(Color.black); // Màu chữ trong bảng
        tblDoanhThu.getTableHeader().setBackground(Color.lightGray); // Màu nền của tiêu đề cột
        tblDoanhThu.getTableHeader().setForeground(Color.black); // Màu chữ trong tiêu đề cột
        tblSanPham.getTableHeader().setBackground(Color.lightGray); // Màu nền của tiêu đề cột
        tblSanPham.getTableHeader().setForeground(Color.black); // Màu chữ trong tiêu đề cột

        fillThoiGian();
        fillDateChooser();
        fillTableDoanhThu();
        fillTableSanPham();

    }

//    private void fillThoiGian(){
//         dcrTu.addPropertyChangeListener(new PropertyChangeListener() {
//            @Override
//            public void propertyChange(PropertyChangeEvent evt) {
//                //  throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
//               fillTableDoanhThu();
//            }
//        });
//
//        dcrDen.addPropertyChangeListener(new PropertyChangeListener() {
//            @Override
//            public void propertyChange(PropertyChangeEvent evt) {
//                //  throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
//                fillTableDoanhThu();
//            }
//        });
//    }
//    
//    private void fillDateChooser() {
//        Date dateBatDau = new Date();// lây day hien tai
//        dcrTu.setDate(dateBatDau);//dat ngay hien tai cho JDate
//        Date dateKetThuc = new Date();
//        dcrDen.setDate(dateKetThuc);
//
//        // Lây thgian bat dau
////        Date fromDate = dcrTu.getDate();
////
////        // Lây thgian ket thuc
////        Date toDate = dcrDen.getDate();
////
////        // Ktra thgian dam bao khong qua toDate
////        if (fromDate != null && toDate != null && !fromDate.after(toDate));
//
//    }
//
//    private void fillTableDoanhThu() {
//        DefaultTableModel model = (DefaultTableModel) tblDoanhThu.getModel();
//        model.setRowCount(0);
//
//        Date tu = dcrTu.getDate();
//        if (tu == null) {
//            return;
//        }
//        Date den = dcrTu.getDate();
//        if (den == null) {
//            return;
//        }
//
//        List<Object[]> list = dao.getThongKeDoanhThu(tu, den);
//        for (Object[] row : list) {
//            model.addRow(row);
//        }
//
//    }
    private void fillThoiGian() {
        dcrTu.addPropertyChangeListener(new PropertyChangeListener() {
            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                fillTableDoanhThu();
            }
        });

        dcrDen.addPropertyChangeListener(new PropertyChangeListener() {
            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                fillTableDoanhThu();
            }
        });
    }

    private void fillDateChooser() {
        //Layt thoi gian hien tai BatDau/KetThuc
        Date dateBatDau = new Date();
        dcrTu.setDate(dateBatDau);
        Date dateKetThuc = new Date();
        dcrDen.setDate(dateKetThuc);

        // Lây thgian bat dau
        Date fromDate = dcrTu.getDate();

        // Lây thgian ket thuc
        Date toDate = dcrDen.getDate();

        // Ktra thgian dam bao khong qua toDate
        if (fromDate != null && toDate != null && !fromDate.after(toDate));
    }

    private void fillTableDoanhThu() {
        DefaultTableModel model = (DefaultTableModel) tblDoanhThu.getModel();
        model.setRowCount(0);

        Date tu = dcrTu.getDate();
        if (tu == null) {
            return;
        }
        Date den = dcrDen.getDate();
        if (den == null) {
            return;
        }

        List<Object[]> list = dao.getThongKeDoanhThu(tu, den);
        model.setRowCount(list.size()); //Dat so hang truoc khi them database

        int rowIndex = 0;

        

        for (Object[] row : list) {
            model.setValueAt(row[0], rowIndex, 0); // Su dung setValueAt() dê dat gtri vao o cu the
            model.setValueAt(row[1], rowIndex, 1);
            model.setValueAt(row[2], rowIndex, 2);
            model.setValueAt(row[3], rowIndex, 3);
            model.setValueAt(row[4], rowIndex, 4);
            model.setValueAt(row[5], rowIndex, 5);
            model.setValueAt(row[6], rowIndex, 6);
            rowIndex++;
            break;
        }
    }

    private void fillTableSanPham() {
        DefaultTableModel model = (DefaultTableModel) tblSanPham.getModel();
        model.setRowCount(0);
        List<Object[]> list = dao.getThongKeSanPham();
        for (Object[] row : list) {
            model.addRow(row);
        }
    }

//    private void SuKienBieuDo() {
//        DefaultCatego dataset = new DefaultCategoryDataset();
//
//        // Lấy dữ liệu từ bảng JTable
//        int rowCount = tblDoanhThu.getRowCount();
//        for (int i = 0; i < rowCount; i++) {
//            String productName = tblDoanhThu.getValueAt(i, 0).toString();
//            int quantity = Integer.parseInt(tblDoanhThu.getValueAt(i, 1).toString());
//            dataset.addValue(quantity, "Số lượng", productName);
//        }
//
//        // Tạo biểu đồ cột
//        JFreeChart chart = ChartFactory.createBarChart("Biểu đồ Số lượng sản phẩm", "Tên sản phẩm", "Số lượng", dataset, PlotOrientation.VERTICAL, false, true, false);
//
//        // Hiển thị biểu đồ
//        ChartFrame frame = new ChartFrame("Biểu đồ", chart);
//    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        tabs = new javax.swing.JTabbedPane();
        jPanel3 = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        dcrTu = new com.toedter.calendar.JDateChooser();
        jPanel5 = new javax.swing.JPanel();
        jScrollPane11 = new javax.swing.JScrollPane();
        tblDoanhThu = new javax.swing.JTable();
        dcrDen = new com.toedter.calendar.JDateChooser();
        jLabel15 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        lblTongNam = new javax.swing.JLabel();
        jPanel8 = new javax.swing.JPanel();
        jPanel9 = new javax.swing.JPanel();
        jScrollPane13 = new javax.swing.JScrollPane();
        tblSanPham = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();

        tabs.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        jLabel7.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(255, 0, 0));
        jLabel7.setText("Từ");

        jLabel6.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(0, 204, 51));
        jLabel6.setText("Chọn thời gian cần xem");

        jPanel5.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Chi tiết doanh thu", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 24))); // NOI18N

        tblDoanhThu.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null}
            },
            new String [] {
                "SỐ SẢN PHẨM BÁN ĐƯỢC", "TIỀN NHẬP HÀNG", "GIÁ CAO NHẤT", "GIÁ THẤP NHẤT", "GIÁ TRUNG BÌNH", "TIỀN BÁN ĐƯỢC", "LỢI NHUẬN"
            }
        ));
        tblDoanhThu.setFocusable(false);
        tblDoanhThu.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblDoanhThuMouseClicked(evt);
            }
        });
        jScrollPane11.setViewportView(tblDoanhThu);

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addComponent(jScrollPane11, javax.swing.GroupLayout.PREFERRED_SIZE, 963, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(51, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane11, javax.swing.GroupLayout.PREFERRED_SIZE, 390, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(19, Short.MAX_VALUE))
        );

        jLabel15.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel15.setForeground(new java.awt.Color(255, 0, 0));
        jLabel15.setText("Đến");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(151, 151, 151)
                        .addComponent(jLabel6))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(228, 228, 228)
                        .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(dcrTu, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(65, 65, 65)
                        .addComponent(jLabel15)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(dcrDen, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(29, 29, 29)
                        .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addComponent(jLabel6)
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(dcrDen, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(jLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel15, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(dcrTu, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(43, 43, 43))
        );

        tabs.addTab("Doanh Thu", jPanel3);

        lblTongNam.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        lblTongNam.setForeground(new java.awt.Color(51, 0, 255));
        lblTongNam.setText("...sản phẩm");

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 234, Short.MAX_VALUE)
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 141, Short.MAX_VALUE)
        );

        jPanel9.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Chi tiết sản phẩm", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 24))); // NOI18N

        tblSanPham.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "TÊN SẢN PHẨM", "SỐ LƯỢNG NHẬP VÀO", "SỐ LƯỢNG TRONG KHO", "SỐ LƯỢNG BÁN RA "
            }
        ));
        tblSanPham.setFocusable(false);
        tblSanPham.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblSanPhamMouseClicked(evt);
            }
        });
        jScrollPane13.setViewportView(tblSanPham);

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addGap(33, 33, 33)
                .addComponent(jScrollPane13, javax.swing.GroupLayout.PREFERRED_SIZE, 832, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(29, Short.MAX_VALUE))
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane13, javax.swing.GroupLayout.PREFERRED_SIZE, 414, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(25, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(858, 858, 858)
                .addComponent(lblTongNam)
                .addGap(283, 283, 283))
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(36, 36, 36)
                .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(15, 15, 15)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblTongNam)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(93, 93, 93)
                        .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        tabs.addTab("Sản Phẩm", jPanel4);

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 36)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(0, 0, 204));
        jLabel1.setText("Tổng hợp thống kê");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addComponent(tabs, javax.swing.GroupLayout.PREFERRED_SIZE, 1032, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(116, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(tabs, javax.swing.GroupLayout.PREFERRED_SIZE, 639, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void tblSanPhamMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblSanPhamMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_tblSanPhamMouseClicked

    private void tblDoanhThuMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblDoanhThuMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_tblDoanhThuMouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private com.toedter.calendar.JDateChooser dcrDen;
    private com.toedter.calendar.JDateChooser dcrTu;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane11;
    private javax.swing.JScrollPane jScrollPane13;
    private javax.swing.JLabel lblTongNam;
    private javax.swing.JTabbedPane tabs;
    private javax.swing.JTable tblDoanhThu;
    private javax.swing.JTable tblSanPham;
    // End of variables declaration//GEN-END:variables
}
