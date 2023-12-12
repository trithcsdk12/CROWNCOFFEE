/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package com.g5.form;

import com.g5.entityDAO.HoaDonDAO;
import com.g5.entityDAO.ThongKeDAO;
import com.g5.entityDAO.TruyVanBieuDo;
import com.g5.util.Validate;
import com.toedter.calendar.JDateChooser;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.Range;
import org.jfree.data.category.DefaultCategoryDataset;

/**
 *
 * @author Asus
 */
public class ThongKeJPanel extends javax.swing.JPanel {

    ThongKeDAO dao = new ThongKeDAO();
    HoaDonDAO hddao = new HoaDonDAO();
    TruyVanBieuDo BĐ = new TruyVanBieuDo();

    /**
     * Creates new form ThongKeJPanel
     */
    public ThongKeJPanel() {
        initComponents();
        //  Font headerFont = new Font("Tohama", Font.BOLD, 16);
        tblDoanhThu.getTableHeader().setFont(new Font("Tohama", 1, 14));
        tblSanPham.getTableHeader().setFont(new Font("Tohama", 1, 14));

        tblSanPham.getTableHeader().setBackground(new Color(32, 136, 203));

        tblSanPham.getTableHeader().setForeground(new Color(255, 255, 255));

        tblDoanhThu.getTableHeader().setForeground(new Color(255, 255, 255));
        tblDoanhThu.getTableHeader().setBackground(new Color(32, 136, 203));
        tblSanPham.setOpaque(false);
        tblDoanhThu.setOpaque(false);
//        tblDoanhThu.getTableHeader().setFont(headerFont);
//        tblSanPham.getTableHeader().setFont(headerFont);
        tblDoanhThu.setRowHeight(30); // Đặt chiều cao hàng là 25 pixel
        tblSanPham.setRowHeight(30); // Đặt chiều cao hàng là 25 pixel
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER); // Căn giữa nội dung ô
        tblDoanhThu.setDefaultRenderer(Object.class, centerRenderer); // Áp dụng renderer cho tất cả các ô
        tblSanPham.setDefaultRenderer(Object.class, centerRenderer); // Áp dụng renderer cho tất cả các ô

        fillThoiGian();
        fillDateChooser();
        fillTableDoanhThu();
        fillTableSanPham();
        TaoBieuDo();
        LangNgheSuKienfillCbo();
        fillCboYear();
        //     fillPanel();

    }

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
        // Lấy thời gian hiện tại
        Date currentDate = new Date();

        // Lùi ngày bắt đầu về 1 năm
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(currentDate);
        calendar.add(Calendar.YEAR, -1);
        Date oneYearAgo = calendar.getTime();

        // Đặt ngày bắt đầu và ngày kết thúc
        dcrTu.setDate(oneYearAgo);
        dcrDen.setDate(currentDate);
    }

//    private void fillDateChooser() {
//        //Layt thoi gian hien tai BatDau/KetThuc
//        Date dateBatDau = new Date();
//        dcrTu.setDate(dateBatDau);
//        Date dateKetThuc = new Date();
//        dcrDen.setDate(dateKetThuc);
//
//        // Lây thgian bat dau
//        Date fromDate = dcrTu.getDate();
//
//        // Lây thgian ket thuc
//        Date toDate = dcrDen.getDate();
//
//        // Ktra thgian dam bao khong qua toDate
//        if (fromDate != null && toDate != null && !fromDate.after(toDate));
//    }
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
         //   NumberFormat numberFormat = new DecimalFormat("#,###");
        //    String formattedValue = numberFormat.format(row[0]); // Định dạng giá trị thành chuỗi có dấu phẩy ngăn cách hàng nghìn
            model.setValueAt(row[0], rowIndex, 0);

            // Các cột khác tương tự
            model.setValueAt(Validate.chuyenGiaVietNam(Float.parseFloat(row[1].toString())), rowIndex, 1);
            model.setValueAt(Validate.chuyenGiaVietNam(Float.parseFloat(row[2].toString())), rowIndex, 2);
            model.setValueAt(Validate.chuyenGiaVietNam(Float.parseFloat(row[3].toString())), rowIndex, 3);
            model.setValueAt(Validate.chuyenGiaVietNam(Float.parseFloat(row[4].toString())), rowIndex, 4);
            model.setValueAt(Validate.chuyenGiaVietNam(Float.parseFloat(row[5].toString())), rowIndex, 5);
            model.setValueAt(Validate.chuyenGiaVietNam(Float.parseFloat(row[6].toString())), rowIndex, 6);
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
        int max = 0;
        int temp = 0;
        int max2 = 0;
        List<Integer> li = new ArrayList<>();
        li.removeAll(li);
        for (int i = 0; i < tblSanPham.getRowCount(); i++) {
            li.add(Integer.parseInt(tblSanPham.getValueAt(i, 4).toString()));
        }
        if (li == null || li.isEmpty()) {
            return;
            //  throw new IllegalArgumentException("Danh sách không hợp lệ");
        }
        int maxSoLuong = li.get(0);
        for (int soLuong : li) {
            // So sánh với giá trị lớn nhất tạm thời
            if (soLuong > maxSoLuong) {
                // Nếu tìm thấy giá trị lớn hơn, gán lại giá trị lớn nhất
                maxSoLuong = soLuong;
            }
        }
        lblTongNam.setText("Số lượng bán được nhiều nhất: "+maxSoLuong);
    }

    public void TaoBieuDo() {
        boolean check = false;      // Tạo dữ liệu cho biểu đồ cột
        TruyVanBieuDo tv = new TruyVanBieuDo();
        if (cboYear.getSelectedItem() == null) {
            return;
        }
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();

        int year = Integer.parseInt(cboYear.getSelectedItem().toString());
        for (Object[] row : tv.getBieuDo(year)) {

            float t1 = Float.parseFloat(row[0].toString());
            float t2 = Float.parseFloat(row[1].toString());
            float t3 = Float.parseFloat(row[2].toString());
            float t4 = Float.parseFloat(row[3].toString());
            float t5 = Float.parseFloat(row[4].toString());
            float t6 = Float.parseFloat(row[5].toString());
            float t7 = Float.parseFloat(row[6].toString());
            float t8 = Float.parseFloat(row[7].toString());
            float t9 = Float.parseFloat(row[8].toString());
            float t10 = Float.parseFloat(row[9].toString());
            float t11 = Float.parseFloat(row[10].toString());
            float t12 = Float.parseFloat(row[11].toString());

            dataset.addValue(t1, "Doanh Thu", "T 1");
            dataset.addValue(t2, "Doanh Thu", "T 2");
            dataset.addValue(t3, "Doanh Thu", "T 3");
            dataset.addValue(t4, "Doanh Thu", "T 4");
            dataset.addValue(t5, "Doanh Thu", "T 5");
            dataset.addValue(t6, "Doanh Thu", "T 6");
            dataset.addValue(t7, "Doanh Thu", "T 7");
            dataset.addValue(t8, "Doanh Thu", "T 8");
            dataset.addValue(t9, "Doanh Thu", "T 9");
            dataset.addValue(t10, "Doanh Thu", "T 10");
            dataset.addValue(t11, "Doanh Thu", "T 11");
            dataset.addValue(t12, "Doanh Thu", "T 12");

            break;
        }

        // Tạo biểu đồ cột
        JFreeChart chart = ChartFactory.createBarChart(
                "Bar Chart Thống Kê", // Tiêu đề biểu đồ
                "Tháng", // Nhãn trục x
                "Doanh Thu", // Nhãn trục y
                dataset, // Dữ liệu
                PlotOrientation.VERTICAL, // Hướng biểu đồ
                true, // Hiển thị legend
                true, // Hiển thị tooltips
                false // Hiển thị URLs
        );

        // Tạo 1 ChartPanel 
        PanelBieuDo.removeAll();
        ChartPanel chartPanel = new ChartPanel(chart);
        //Thêm ChartPanel vào panel đã kéo thả
        PanelBieuDo.setLayout(new BorderLayout());
        PanelBieuDo.add(chartPanel, BorderLayout.CENTER);
        PanelBieuDo.validate();
        PanelBieuDo.repaint();
    }

    private void LangNgheSuKienfillCbo() {
        dcrTu.getDateEditor().addPropertyChangeListener(new PropertyChangeListener() {
            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                if ("date".equals(evt.getPropertyName())) {
                    // fillPanel();
                    fillCboYear();
                }
            }
        });

        dcrDen.getDateEditor().addPropertyChangeListener(new PropertyChangeListener() {
            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                if ("date".equals(evt.getPropertyName())) {
                    //fillPanel();
                    fillCboYear();
                }
            }
        });
    }

    private void fillCboYear() {
        DefaultComboBoxModel model = (DefaultComboBoxModel) cboYear.getModel();
        model.removeAllElements();
        Date startDate = dcrTu.getDate();
        Date endDate = dcrDen.getDate();

        if (startDate != null && endDate != null) {
            Calendar startCalendar = Calendar.getInstance();
            startCalendar.setTime(startDate);
            int startYear = startCalendar.get(Calendar.YEAR);

            Calendar endCalendar = Calendar.getInstance();
            endCalendar.setTime(endDate);
            int endYear = endCalendar.get(Calendar.YEAR);

            for (int year = startYear; year <= endYear; year++) {
                model.addElement(year);
            }
        }

        cboYear.repaint();
    }

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
        PanelBieuDo = new javax.swing.JPanel();
        cboYear = new javax.swing.JComboBox<>();
        jPanel4 = new javax.swing.JPanel();
        jPanel8 = new javax.swing.JPanel();
        jPanel9 = new javax.swing.JPanel();
        jScrollPane13 = new javax.swing.JScrollPane();
        tblSanPham = new javax.swing.JTable();
        lblTongNam = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();

        tabs.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        jLabel7.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(255, 0, 0));
        jLabel7.setText("Từ");

        jLabel6.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(0, 204, 51));
        jLabel6.setText("Chọn thời gian cần xem");

        jPanel5.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Chi tiết doanh thu", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 24))); // NOI18N

        tblDoanhThu.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        tblDoanhThu.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null}
            },
            new String [] {
                "Số Sản Phẩm Đã Bán ", "TIỀN NHẬP HÀNG", "GIÁ CAO NHẤT", "GIÁ THẤP NHẤT", "GIÁ TRUNG BÌNH", "TIỀN BÁN ĐƯỢC", "LỢI NHUẬN"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblDoanhThu.setFocusable(false);
        tblDoanhThu.setIntercellSpacing(new java.awt.Dimension(0, 0));
        tblDoanhThu.setRowHeight(30);
        tblDoanhThu.setSelectionBackground(new java.awt.Color(102, 255, 102));
        tblDoanhThu.setShowVerticalLines(false);
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
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane11)
                .addContainerGap())
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane11, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jLabel15.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel15.setForeground(new java.awt.Color(255, 0, 0));
        jLabel15.setText("Đến");

        PanelBieuDo.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        javax.swing.GroupLayout PanelBieuDoLayout = new javax.swing.GroupLayout(PanelBieuDo);
        PanelBieuDo.setLayout(PanelBieuDoLayout);
        PanelBieuDoLayout.setHorizontalGroup(
            PanelBieuDoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        PanelBieuDoLayout.setVerticalGroup(
            PanelBieuDoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 391, Short.MAX_VALUE)
        );

        cboYear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboYearActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(PanelBieuDo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(cboYear, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(17, 17, 17))
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGap(147, 147, 147)
                                .addComponent(jLabel6))
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGap(402, 402, 402)
                                .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(dcrTu, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(44, 44, 44)
                                .addComponent(jLabel15)
                                .addGap(18, 18, 18)
                                .addComponent(dcrDen, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel7)
                    .addComponent(dcrDen, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(dcrTu, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel15)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(30, 30, 30)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(PanelBieuDo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cboYear, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        tabs.addTab("Doanh Thu", jPanel3);

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

        tblSanPham.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        tblSanPham.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "STT", "Tên sản phẩm", "Số lượng nhập vào", "Số lượng trong kho", "Số lượng bán ra"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblSanPham.setFocusable(false);
        tblSanPham.setIntercellSpacing(new java.awt.Dimension(0, 0));
        tblSanPham.setRowHeight(30);
        tblSanPham.setSelectionBackground(new java.awt.Color(102, 255, 102));
        tblSanPham.setShowVerticalLines(false);
        tblSanPham.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblSanPhamMouseClicked(evt);
            }
        });
        jScrollPane13.setViewportView(tblSanPham);
        if (tblSanPham.getColumnModel().getColumnCount() > 0) {
            tblSanPham.getColumnModel().getColumn(0).setPreferredWidth(50);
        }

        lblTongNam.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        lblTongNam.setForeground(new java.awt.Color(51, 0, 255));
        lblTongNam.setText("...sản phẩm");

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel9Layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent(jScrollPane13, javax.swing.GroupLayout.DEFAULT_SIZE, 1135, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel9Layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(lblTongNam)))
                .addContainerGap())
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addComponent(jScrollPane13, javax.swing.GroupLayout.PREFERRED_SIZE, 504, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblTongNam)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(1312, 1312, 1312))
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(130, 130, 130)
                .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(132, 132, 132)
                .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
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
                .addGap(16, 16, 16)
                .addComponent(jLabel1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addComponent(tabs, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(tabs, javax.swing.GroupLayout.PREFERRED_SIZE, 639, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(226, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void tblSanPhamMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblSanPhamMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_tblSanPhamMouseClicked

    private void tblDoanhThuMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblDoanhThuMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_tblDoanhThuMouseClicked

    private void cboYearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboYearActionPerformed
        // TODO add your handling code here:
        TaoBieuDo();
        //  System.out.println("a");
    }//GEN-LAST:event_cboYearActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel PanelBieuDo;
    private javax.swing.JComboBox<String> cboYear;
    private com.toedter.calendar.JDateChooser dcrDen;
    private com.toedter.calendar.JDateChooser dcrTu;
    private com.toedter.calendar.JDateChooser dcrTu1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane11;
    private javax.swing.JScrollPane jScrollPane12;
    private javax.swing.JScrollPane jScrollPane13;
    private javax.swing.JLabel lblTongNam;
    private javax.swing.JTabbedPane tabs;
    private javax.swing.JTabbedPane tabs1;
    private javax.swing.JTable tblDoanhThu;
    private javax.swing.JTable tblDoanhThu1;
    private javax.swing.JTable tblSanPham;
    // End of variables declaration//GEN-END:variables
}
//    private void fillThoiGian(){
//         dcrTu.addPropertyChangeListener(new PropertyChangeListener() {
//            @Override
//            public void propertyChange(PropertyChangeEvent evt) {
// //        });
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
//    }               //  throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
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
//    public void fillPanel() {
//        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
//
//        TruyVanBieuDo truyVanBieuDo = new TruyVanBieuDo();
//        List<Object[]> data = truyVanBieuDo.getBieuDo(Integer.parseInt(cboYear.getSelectedItem().toString()));
//
//        int rowIndex = 0;
//
//        for (Object[] row : data) {
//            dataset.addValue((Number) row[0], "Doanh Thu", "Tháng 1");
//            dataset.addValue((Number) row[1], "Doanh Thu", "Tháng 2");
//            dataset.addValue((Number) row[2], "Doanh Thu", "Tháng 3");
//            dataset.addValue((Number) row[3], "Doanh Thu", "Tháng 4");
//            dataset.addValue((Number) row[4], "Doanh Thu", "Tháng 5");
//            dataset.addValue((Number) row[5], "Doanh Thu", "Tháng 6");
//            dataset.addValue((Number) row[6], "Doanh Thu", "Tháng 7");
//            dataset.addValue((Number) row[7], "Doanh Thu", "Tháng 8");
//            dataset.addValue((Number) row[8], "Doanh Thu", "Tháng 9");
//            dataset.addValue((Number) row[9], "Doanh Thu", "Tháng 10");
//            dataset.addValue((Number) row[10], "Doanh Thu", "Tháng 11");
//            dataset.addValue((Number) row[11], "Doanh Thu", "Tháng 12");
//            rowIndex++;
//            break;
//
//        }
//
//    }
