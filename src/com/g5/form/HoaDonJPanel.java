/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package com.g5.form;

import com.g5.entity.HoaDon;
import com.g5.entity.HoaDonChiTiet;
import com.g5.entity.SanPham;
import com.g5.entityDAO.HoaDonChiTietDAO;
import com.g5.entityDAO.HoaDonDAO;
import com.g5.entityDAO.KhuyenMaiDAO;
import com.g5.entityDAO.SanPhamDao;
import com.g5.util.Auth;
import com.g5.util.ExcelUtil;
import com.g5.util.TextMes;
import com.g5.util.XDate;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import org.apache.poi.ss.usermodel.Workbook;

/**
 *
 * @author triphan
 */
public class HoaDonJPanel extends javax.swing.JPanel {

//    int MaSP;
    float giaTheoSize;
    float sale;
    float GiaKM;
    float tongtien = 0;

    /**
     * Creates new form HoaDonJPanel
     */
    public HoaDonJPanel() {
        initComponents();
        setOpaque(false);
        setEnable();
        cboLoai();
        FillTable();
        FindHoaDon();
        autoResizeColumns(tblHoaDon);
        autoResizeColumns(tblHoaDonChiTiet);
        autoResizeColumns(tblLichSuHD);
        this.setStatus(true);
        this.setStatusHDCT(true);
        soluong();
        tienkhach();
        setFontTable(tblHoaDon);
        setFontTable(tblHoaDonChiTiet);
        setFontTable(tblLichSuHD);
    }
    void setFontTable(JTable table){
        DefaultTableCellRenderer center = new DefaultTableCellRenderer();
        center.setHorizontalAlignment(JLabel.CENTER); 
        table.setDefaultRenderer(Object.class, center);
        table.getTableHeader().setBackground(Color.red);
        table.getTableHeader().setFont(new Font("Times New Roman", Font.BOLD, 18));
        table.setOpaque(false);
        table.getTableHeader().setBackground(new Color(32,136,203));
        table.getTableHeader().setForeground(new Color(255,255,255));
        table.setRowHeight(25);
    }
    private static void autoResizeColumns(JTable table) {
        TableColumnModel colModel = table.getColumnModel();
        for (int col = 0; col < table.getColumnCount(); col++) {
            int maxWidth = 0;
            for (int row = 0; row < table.getRowCount(); row++) {
                TableCellRenderer cellRenderer = table.getCellRenderer(row, col);
                Object value = table.getValueAt(row, col);
                Component comp = cellRenderer.getTableCellRendererComponent(table, value, false, false, row, col);
                maxWidth = Math.max(maxWidth, comp.getPreferredSize().width);
            }
            TableColumn column = colModel.getColumn(col);
            column.setPreferredWidth(maxWidth);
        }
    
}


    void setEnable() {
        txtMAHD.setEditable(false);
        txt_MASP.setEditable(false);
        txtGia.setEditable(false);
        txtKhuyenMai.setEditable(false);
        txtThanhTien.setEditable(false);
        txtMaNV.setEditable(false);
    }

    void soluong() {
        txtSoLuong.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(javax.swing.event.DocumentEvent e) {
                updateGiaBasedOnSoLuong();
            }

            @Override
            public void removeUpdate(javax.swing.event.DocumentEvent e) {
                updateGiaBasedOnSoLuong();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                updateGiaBasedOnSoLuong();

            }

        });
    }

    void tienkhach() {
        txt_tienkhachtra.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                TraTien();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                TraTien();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                TraTien();
            }

        });
    }

    void FindHoaDon() {
        txtTimKiem.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                FindIDHD();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                FindIDHD();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                FindIDHD();
            }

        });
    }
//    @Override
//    protected void paintComponent(Graphics g) {
//        Graphics2D g2 = (Graphics2D) g;
//        super.paintComponent(g); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/OverriddenMethodBody
//    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel6 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        btnNew = new javax.swing.JButton();
        btnInsert = new javax.swing.JButton();
        btnDelete = new javax.swing.JButton();
        btnUpdate = new javax.swing.JButton();
        txtGia = new javax.swing.JTextField();
        txtKhuyenMai = new javax.swing.JTextField();
        txt_MASP = new javax.swing.JTextField();
        txtMAHD = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        txtSoLuong = new javax.swing.JTextField();
        jLabel14 = new javax.swing.JLabel();
        cboTenSP = new javax.swing.JComboBox<>();
        cboLoai = new javax.swing.JComboBox<>();
        jLabel16 = new javax.swing.JLabel();
        txtThanhTien = new javax.swing.JTextField();
        cboSizeSP = new javax.swing.JComboBox<>();
        jLabel1 = new javax.swing.JLabel();
        lblSoLuongSP = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jLabel15 = new javax.swing.JLabel();
        lblTongTien = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        txt_tienkhachtra = new javax.swing.JTextField();
        jLabel18 = new javax.swing.JLabel();
        txt_tienthoi = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jLabel19 = new javax.swing.JLabel();
        btnFirst1 = new javax.swing.JButton();
        btnPrev1 = new javax.swing.JButton();
        btnNext1 = new javax.swing.JButton();
        btnLast1 = new javax.swing.JButton();
        jPanel5 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblHoaDonChiTiet = new javax.swing.JTable();
        jLabel13 = new javax.swing.JLabel();
        txt_MaHD = new javax.swing.JLabel();
        lblTrangthai = new javax.swing.JLabel();
        jTabbedPane2 = new javax.swing.JTabbedPane();
        jPanel2 = new javax.swing.JPanel();
        btnPrev = new javax.swing.JButton();
        btnThem = new javax.swing.JButton();
        btnSua = new javax.swing.JButton();
        jLabel8 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        btnFirst = new javax.swing.JButton();
        btnXoa = new javax.swing.JButton();
        btnNext = new javax.swing.JButton();
        btnMoi = new javax.swing.JButton();
        txtMaHD = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        txtMaNV = new javax.swing.JTextField();
        txtNgayTao = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtGhiChu = new javax.swing.JTextArea();
        btnLast = new javax.swing.JButton();
        jScrollPane10 = new javax.swing.JScrollPane();
        tblHoaDon = new javax.swing.JTable();
        txtTimKiem = new javax.swing.JTextField();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane11 = new javax.swing.JScrollPane();
        tblLichSuHD = new javax.swing.JTable();
        jButton3 = new javax.swing.JButton();

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Hóa đơn chi tiết", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Times New Roman", 1, 18))); // NOI18N

        jLabel2.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        jLabel2.setText("Mã hóa đơn ");

        jLabel5.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        jLabel5.setText("Mã sản phẩm");

        jLabel6.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        jLabel6.setText("Số lượng");

        jLabel9.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        jLabel9.setText("Giá");

        jLabel10.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        jLabel10.setText("Khuyến Mãi");

        jLabel11.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        jLabel11.setText("Size");

        btnNew.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btnNew.setText("Mới ");
        btnNew.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNewActionPerformed(evt);
            }
        });

        btnInsert.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btnInsert.setText("Thêm");
        btnInsert.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnInsertActionPerformed(evt);
            }
        });

        btnDelete.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btnDelete.setText("Xóa");
        btnDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteActionPerformed(evt);
            }
        });

        btnUpdate.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btnUpdate.setText("Sửa");
        btnUpdate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUpdateActionPerformed(evt);
            }
        });

        txtGia.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N

        txtKhuyenMai.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N

        txt_MASP.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N

        txtMAHD.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N

        jLabel12.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        jLabel12.setText("Tên sản phẩm");

        txtSoLuong.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N

        jLabel14.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        jLabel14.setText("Loại");

        cboTenSP.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        cboTenSP.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cboTenSP.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cboTenSPItemStateChanged(evt);
            }
        });
        cboTenSP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboTenSPActionPerformed(evt);
            }
        });

        cboLoai.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        cboLoai.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cboLoai.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cboLoaiItemStateChanged(evt);
            }
        });
        cboLoai.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                cboLoaiMouseClicked(evt);
            }
        });
        cboLoai.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboLoaiActionPerformed(evt);
            }
        });

        jLabel16.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        jLabel16.setText("Thành tiền");

        txtThanhTien.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N

        cboSizeSP.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        cboSizeSP.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cboSizeSP.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cboSizeSPItemStateChanged(evt);
            }
        });
        cboSizeSP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboSizeSPActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        jLabel1.setText("Số lượng còn");

        lblSoLuongSP.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        lblSoLuongSP.setToolTipText("");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap(18, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(btnNew)
                        .addGap(18, 18, 18)
                        .addComponent(btnInsert))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2)
                            .addComponent(jLabel9)
                            .addComponent(jLabel11)
                            .addComponent(jLabel16)
                            .addComponent(jLabel6))
                        .addGap(28, 28, 28)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(cboSizeSP, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(txtMAHD)
                            .addComponent(txtSoLuong)
                            .addComponent(txtGia)
                            .addComponent(txtThanhTien, javax.swing.GroupLayout.PREFERRED_SIZE, 148, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(41, 41, 41)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                                .addComponent(jLabel5)
                                .addGap(6, 6, 6))
                            .addComponent(jLabel12, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel10)
                            .addComponent(jLabel1)
                            .addComponent(jLabel14))))
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                        .addComponent(btnDelete, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnUpdate))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(lblSoLuongSP)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(txtKhuyenMai, javax.swing.GroupLayout.PREFERRED_SIZE, 148, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cboLoai, javax.swing.GroupLayout.PREFERRED_SIZE, 148, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cboTenSP, javax.swing.GroupLayout.PREFERRED_SIZE, 148, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txt_MASP, javax.swing.GroupLayout.PREFERRED_SIZE, 148, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(30, 30, 30))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(21, 21, 21)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGap(171, 171, 171)
                                .addComponent(lblSoLuongSP))
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(cboLoai, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel14))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(cboTenSP, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel12))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(txt_MASP, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel5))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(txtKhuyenMai, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel10))
                                .addGap(18, 18, 18)
                                .addComponent(jLabel1))))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtMAHD, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel2))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtSoLuong, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtGia, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel9))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(cboSizeSP, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel11))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtThanhTien, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel16))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 40, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnUpdate)
                    .addComponent(btnDelete)
                    .addComponent(btnInsert)
                    .addComponent(btnNew))
                .addContainerGap())
        );

        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder("Sự kiện hóa đơn"));

        jLabel15.setFont(new java.awt.Font("Times New Roman", 0, 24)); // NOI18N
        jLabel15.setText("Tổng tiền");

        lblTongTien.setFont(new java.awt.Font("Times New Roman", 0, 24)); // NOI18N
        lblTongTien.setForeground(new java.awt.Color(255, 51, 51));
        lblTongTien.setText("0");

        jLabel17.setFont(new java.awt.Font("Times New Roman", 0, 24)); // NOI18N
        jLabel17.setText("Tiền khách trả");

        txt_tienkhachtra.setFont(new java.awt.Font("Times New Roman", 0, 24)); // NOI18N

        jLabel18.setFont(new java.awt.Font("Times New Roman", 0, 24)); // NOI18N
        jLabel18.setText("Tiền trả khách:");

        txt_tienthoi.setFont(new java.awt.Font("Times New Roman", 1, 24)); // NOI18N

        jButton1.setBackground(new java.awt.Color(255, 51, 51));
        jButton1.setFont(new java.awt.Font("Times New Roman", 0, 24)); // NOI18N
        jButton1.setText("HỦY");

        jButton2.setBackground(new java.awt.Color(51, 51, 255));
        jButton2.setFont(new java.awt.Font("Times New Roman", 0, 24)); // NOI18N
        jButton2.setText("Thanh toán");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jLabel19.setFont(new java.awt.Font("Times New Roman", 0, 24)); // NOI18N
        jLabel19.setForeground(new java.awt.Color(255, 51, 51));
        jLabel19.setText("VND");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jButton1)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel15)
                        .addGap(40, 40, 40)
                        .addComponent(lblTongTien)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel19)
                        .addGap(106, 106, 106)
                        .addComponent(jLabel17)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txt_tienkhachtra, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(227, 227, 227)
                        .addComponent(jLabel18)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txt_tienthoi)
                    .addComponent(jButton2))
                .addContainerGap(375, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel18)
                    .addComponent(txt_tienthoi)
                    .addComponent(txt_tienkhachtra, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel17)
                    .addComponent(jLabel15)
                    .addComponent(lblTongTien)
                    .addComponent(jLabel19))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton1))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        btnFirst1.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btnFirst1.setText("<|");
        btnFirst1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnFirst1ActionPerformed(evt);
            }
        });

        btnPrev1.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btnPrev1.setText("<<");
        btnPrev1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPrev1ActionPerformed(evt);
            }
        });

        btnNext1.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btnNext1.setText(">>");
        btnNext1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNext1ActionPerformed(evt);
            }
        });

        btnLast1.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btnLast1.setText(">|");
        btnLast1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLast1ActionPerformed(evt);
            }
        });

        tblHoaDonChiTiet.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        tblHoaDonChiTiet.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "STT", "MASP", "SOLUONG", "GIÁ", "SIZE"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.Integer.class, java.lang.Integer.class, java.lang.Float.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                true, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblHoaDonChiTiet.setRowHeight(30);
        tblHoaDonChiTiet.setSelectionBackground(new java.awt.Color(255, 102, 102));
        tblHoaDonChiTiet.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblHoaDonChiTietMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tblHoaDonChiTiet);

        jLabel13.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        jLabel13.setText("Mã Hóa Đơn:");

        txt_MaHD.setFont(new java.awt.Font("Times New Roman", 0, 24)); // NOI18N
        txt_MaHD.setForeground(new java.awt.Color(255, 51, 51));

        lblTrangthai.setFont(new java.awt.Font("Times New Roman", 0, 24)); // NOI18N
        lblTrangthai.setForeground(new java.awt.Color(255, 51, 51));

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel13)
                .addGap(18, 18, 18)
                .addComponent(txt_MaHD)
                .addGap(81, 81, 81)
                .addComponent(lblTrangthai)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 644, Short.MAX_VALUE)
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 258, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel13)
                    .addComponent(txt_MaHD)
                    .addComponent(lblTrangthai)))
        );

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Hóa Đơn", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Times New Roman", 1, 18))); // NOI18N

        btnPrev.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btnPrev.setText("<<");
        btnPrev.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPrevActionPerformed(evt);
            }
        });

        btnThem.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btnThem.setText("Thêm");
        btnThem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThemActionPerformed(evt);
            }
        });

        btnSua.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btnSua.setText("Sửa");
        btnSua.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSuaActionPerformed(evt);
            }
        });

        jLabel8.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        jLabel8.setText("Ngày tạo");

        jLabel4.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        jLabel4.setText("Mã nhân viên");

        btnFirst.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btnFirst.setText("<|");
        btnFirst.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnFirstActionPerformed(evt);
            }
        });

        btnXoa.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btnXoa.setText("Xóa");
        btnXoa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXoaActionPerformed(evt);
            }
        });

        btnNext.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btnNext.setText(">>");
        btnNext.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNextActionPerformed(evt);
            }
        });

        btnMoi.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btnMoi.setText("Mới ");
        btnMoi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMoiActionPerformed(evt);
            }
        });

        txtMaHD.setEditable(false);
        txtMaHD.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        txtMaHD.setFocusable(false);

        jLabel7.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        jLabel7.setText("Ghi chú");

        jLabel3.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        jLabel3.setText("Mã hóa đơn");

        txtMaNV.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N

        txtNgayTao.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N

        txtGhiChu.setColumns(20);
        txtGhiChu.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        txtGhiChu.setRows(5);
        jScrollPane1.setViewportView(txtGhiChu);

        btnLast.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btnLast.setText(">|");
        btnLast.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLastActionPerformed(evt);
            }
        });

        tblHoaDon.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        tblHoaDon.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "STT", "TRẠNG THÁI", "MÃ HD", "NGÀY TẠO", "MÃ NV", "GHI CHÚ"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.Integer.class, java.lang.String.class, java.lang.Integer.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                true, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblHoaDon.setFocusable(false);
        tblHoaDon.setRowHeight(25);
        tblHoaDon.setSelectionBackground(new java.awt.Color(255, 102, 102));
        tblHoaDon.getTableHeader().setReorderingAllowed(false);
        tblHoaDon.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblHoaDonMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                tblHoaDonMouseEntered(evt);
            }
        });
        jScrollPane10.setViewportView(tblHoaDon);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane10, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 707, Short.MAX_VALUE)
                    .addComponent(txtTimKiem))
                .addContainerGap())
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(109, 109, 109)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(jLabel4)
                        .addComponent(jLabel7)
                        .addComponent(jLabel8))
                    .addComponent(jLabel3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(btnFirst, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnPrev, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnNext, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnLast, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtMaHD, javax.swing.GroupLayout.PREFERRED_SIZE, 198, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(txtNgayTao, javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(txtMaNV, javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 197, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(49, 49, 49)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(btnMoi, javax.swing.GroupLayout.PREFERRED_SIZE, 133, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnThem, javax.swing.GroupLayout.PREFERRED_SIZE, 133, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnXoa, javax.swing.GroupLayout.PREFERRED_SIZE, 133, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnSua, javax.swing.GroupLayout.PREFERRED_SIZE, 133, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(txtTimKiem, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane10, javax.swing.GroupLayout.DEFAULT_SIZE, 221, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel3)
                        .addComponent(txtMaHD, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(btnThem, javax.swing.GroupLayout.Alignment.TRAILING))
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(21, 21, 21)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel4)
                            .addComponent(txtMaNV, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(27, 27, 27)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtNgayTao, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel8)))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnMoi)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnXoa)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnSua)))
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 74, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(27, 27, 27)
                        .addComponent(jLabel7)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnFirst, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnPrev, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnNext, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnLast, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        jTabbedPane2.addTab("Hóa đơn", jPanel2);

        tblLichSuHD.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        tblLichSuHD.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "STT", "TRANGTHAI", "MAHD", "NGAYTAO", "MANV", "TONGTIEN", "TIENTRA", "GHI CHÚ"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.Integer.class, java.lang.String.class, java.lang.Integer.class, java.lang.Float.class, java.lang.Float.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                true, false, false, false, false, true, true, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblLichSuHD.setRowHeight(30);
        tblLichSuHD.setSelectionBackground(new java.awt.Color(255, 102, 102));
        tblLichSuHD.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblLichSuHDMouseClicked(evt);
            }
        });
        jScrollPane11.setViewportView(tblLichSuHD);

        jButton3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/g5/image/excel.png"))); // NOI18N
        jButton3.setText("Excel");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane11, javax.swing.GroupLayout.DEFAULT_SIZE, 707, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jButton3)
                        .addGap(12, 12, 12)))
                .addGap(16, 16, 16))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addComponent(jScrollPane11, javax.swing.GroupLayout.PREFERRED_SIZE, 236, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 282, Short.MAX_VALUE)
                .addComponent(jButton3)
                .addGap(16, 16, 16))
        );

        jTabbedPane2.addTab("Lịch sử", jPanel1);

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addComponent(jTabbedPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 729, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(1, 1, 1)
                        .addComponent(btnFirst1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnPrev1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnNext1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnLast1))
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGap(746, 746, 746)
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jTabbedPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 620, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnFirst1)
                    .addComponent(btnPrev1)
                    .addComponent(btnNext1)
                    .addComponent(btnLast1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, 1397, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(18, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void tblLichSuHDMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblLichSuHDMouseClicked
        if (evt.getClickCount() == 2) {
            this.ShowFormLichSu();
            TextMes.Alert(this, "Hóa đơn đã thanh toán");
        }
    }//GEN-LAST:event_tblLichSuHDMouseClicked

    private void tblHoaDonChiTietMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblHoaDonChiTietMouseClicked
        if (evt.getClickCount() == 1) {
            this.EditHDCT();
        }
    }//GEN-LAST:event_tblHoaDonChiTietMouseClicked

    private void btnLast1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLast1ActionPerformed
        this.moveLastHDCT();        // TODO add your handling code here:
    }//GEN-LAST:event_btnLast1ActionPerformed

    private void btnNext1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNext1ActionPerformed
        this.moveNextHDCT();        // TODO add your handling code here:
    }//GEN-LAST:event_btnNext1ActionPerformed

    private void btnPrev1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPrev1ActionPerformed
        this.movePrevHDCT();
    }//GEN-LAST:event_btnPrev1ActionPerformed

    private void btnFirst1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFirst1ActionPerformed
        this.moveFirstHDCT();
    }//GEN-LAST:event_btnFirst1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        ThanhToanDTB(getFormThanhToan());
    }//GEN-LAST:event_jButton2ActionPerformed

    private void cboSizeSPActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboSizeSPActionPerformed
        GiaSPBySize();
    }//GEN-LAST:event_cboSizeSPActionPerformed

    private void cboSizeSPItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cboSizeSPItemStateChanged
        // TODO add your handling code here:
    }//GEN-LAST:event_cboSizeSPItemStateChanged

    private void cboLoaiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboLoaiActionPerformed

    }//GEN-LAST:event_cboLoaiActionPerformed

    private void cboLoaiMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cboLoaiMouseClicked

    }//GEN-LAST:event_cboLoaiMouseClicked

    private void cboLoaiItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cboLoaiItemStateChanged
        CboTenSP();
    }//GEN-LAST:event_cboLoaiItemStateChanged

    private void cboTenSPActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboTenSPActionPerformed

        ShowInfo();
    }//GEN-LAST:event_cboTenSPActionPerformed

    private void cboTenSPItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cboTenSPItemStateChanged

    }//GEN-LAST:event_cboTenSPItemStateChanged

    private void btnUpdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUpdateActionPerformed
        updateHoaDon();
        JOptionPane.showMessageDialog(null, "Sua Thanh Cong");
    }//GEN-LAST:event_btnUpdateActionPerformed

    private void btnDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteActionPerformed
        deleteHDCT();
        JOptionPane.showMessageDialog(null, "Xoa Thanh Cong");
    }//GEN-LAST:event_btnDeleteActionPerformed

    private void btnInsertActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnInsertActionPerformed
        insertHDCT(getFormHDCT());
        clearHDCT();
    }//GEN-LAST:event_btnInsertActionPerformed

    private void btnNewActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNewActionPerformed
        clearHDCT();
    }//GEN-LAST:event_btnNewActionPerformed

    private void tblHoaDonMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblHoaDonMouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_tblHoaDonMouseEntered

    private void tblHoaDonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblHoaDonMouseClicked
        if (evt.getClickCount() == 2) {
            this.Edit();

        }
    }//GEN-LAST:event_tblHoaDonMouseClicked

    private void btnLastActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLastActionPerformed
        this.moveLast();
    }//GEN-LAST:event_btnLastActionPerformed

    private void btnMoiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMoiActionPerformed
        clearHoaDon();
    }//GEN-LAST:event_btnMoiActionPerformed

    private void btnNextActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNextActionPerformed
        this.moveNext();
    }//GEN-LAST:event_btnNextActionPerformed

    private void btnXoaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXoaActionPerformed
        deleteHoaDon();
        JOptionPane.showMessageDialog(null, "Xoa Thanh Cong");
    }//GEN-LAST:event_btnXoaActionPerformed

    private void btnFirstActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFirstActionPerformed
        this.moveFirst();
    }//GEN-LAST:event_btnFirstActionPerformed

    private void btnSuaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSuaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnSuaActionPerformed

    private void btnThemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThemActionPerformed
        // TODO add your handling code here:
        insert(getForm());
    }//GEN-LAST:event_btnThemActionPerformed

    private void btnPrevActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPrevActionPerformed
        this.movePrev();
    }//GEN-LAST:event_btnPrevActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        try {
            Workbook wo = ExcelUtil.printToLichSuHD(tblLichSuHD, hdDao);
            this.chooseDirectoryToSave(wo);
            Logger.getLogger(HoaDonJPanel.class.getName()).log(Level.INFO,
                "Export NguoiHoc to Excel file successful!");
        } catch (Exception e) {
            Logger.getLogger(HoaDonJPanel.class.getName()).log(Level.SEVERE, null, e);
        }
    }//GEN-LAST:event_jButton3ActionPerformed
    private void chooseDirectoryToSave(Workbook workbook) {
        JFileChooser choose = new JFileChooser();
        int x = choose.showSaveDialog(null);
        if (x == JFileChooser.APPROVE_OPTION) {
            try {
                String file = choose.getSelectedFile().getAbsolutePath().toString();
                FileOutputStream outFile = new FileOutputStream(file);
                workbook.write(outFile);
                workbook.close();
                outFile.close();
                TextMes.Alert(this, "Xuất tệp Excel thành công!");
            } catch (IOException ex) {
                Logger.getLogger(HoaDonJPanel.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnDelete;
    private javax.swing.JButton btnFirst;
    private javax.swing.JButton btnFirst1;
    private javax.swing.JButton btnInsert;
    private javax.swing.JButton btnLast;
    private javax.swing.JButton btnLast1;
    private javax.swing.JButton btnMoi;
    private javax.swing.JButton btnNew;
    private javax.swing.JButton btnNext;
    private javax.swing.JButton btnNext1;
    private javax.swing.JButton btnPrev;
    private javax.swing.JButton btnPrev1;
    private javax.swing.JButton btnSua;
    private javax.swing.JButton btnThem;
    private javax.swing.JButton btnUpdate;
    private javax.swing.JButton btnXoa;
    private javax.swing.JComboBox<String> cboLoai;
    private javax.swing.JComboBox<String> cboSizeSP;
    private javax.swing.JComboBox<String> cboTenSP;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane10;
    private javax.swing.JScrollPane jScrollPane11;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTabbedPane jTabbedPane2;
    private javax.swing.JLabel lblSoLuongSP;
    private javax.swing.JLabel lblTongTien;
    private javax.swing.JLabel lblTrangthai;
    private javax.swing.JTable tblHoaDon;
    private javax.swing.JTable tblHoaDonChiTiet;
    private javax.swing.JTable tblLichSuHD;
    private javax.swing.JTextArea txtGhiChu;
    private javax.swing.JTextField txtGia;
    private javax.swing.JTextField txtKhuyenMai;
    private javax.swing.JTextField txtMAHD;
    private javax.swing.JTextField txtMaHD;
    private javax.swing.JTextField txtMaNV;
    private javax.swing.JTextField txtNgayTao;
    private javax.swing.JTextField txtSoLuong;
    private javax.swing.JTextField txtThanhTien;
    private javax.swing.JTextField txtTimKiem;
    private javax.swing.JTextField txt_MASP;
    private javax.swing.JLabel txt_MaHD;
    private javax.swing.JTextField txt_tienkhachtra;
    private javax.swing.JLabel txt_tienthoi;
    // End of variables declaration//GEN-END:variables

    HoaDonDAO hdDao = new HoaDonDAO();
    HoaDonChiTietDAO hdctdao = new HoaDonChiTietDAO();

    // DÙNG CHO TẠO HÓA ĐƠN
    void FillTable() {
        DefaultTableModel model = (DefaultTableModel) tblHoaDon.getModel();
        DefaultTableModel lichSuModel = (DefaultTableModel) tblLichSuHD.getModel();
        lichSuModel.setRowCount(0);
        model.setRowCount(0);

        List<HoaDon> ls = hdDao.getAll();
        List<HoaDonChiTiet> list = hdctdao.getALL();
        txtMaHD.setText(hdDao.getLast());
        txtMaNV.setText(String.valueOf(Auth.user.getMaNV()));
        int a = 0;
        int b = 0;
        for (HoaDon hoaDon : ls) {
            boolean daThanhToan = hoaDon.isTrangthai();
            float tienKhachTra = hoaDon.getTienKhachTra();
            if (tienKhachTra == 0 && !daThanhToan) {
                Object[] rowData = new Object[]{++a, daThanhToan ? "Đã thanh toán" : "Chưa thanh toán", hoaDon.getMaHD(), hoaDon.getNgayTao(), hoaDon.getMaNV(), "", tienKhachTra, hoaDon.getGhiChu()};
                model.addRow(rowData);
            } else {
                Object[] rowData = new Object[]{++b, daThanhToan ? "Đã thanh toán" : "Chưa thanh toán", hoaDon.getMaHD(), hoaDon.getNgayTao(), hoaDon.getMaNV(), hoaDon.getTongTien(), tienKhachTra, hoaDon.getGhiChu()};
                lichSuModel.addRow(rowData);

            }
        }
    }

    void insert(HoaDon hd) {
        try {
            hdDao.create(hd);
            FillTable();
            clearHoaDon();
            JOptionPane.showMessageDialog(null, "ok");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    void deleteHoaDon() {
        hdDao.deteleByID(Integer.parseInt(txtMaHD.getText()));
        FillTable();
        clearHoaDon();
    }

    HoaDon getForm() {
        HoaDon hd = new HoaDon();
        hd.setMaHD(Integer.parseInt(txtMaHD.getText().trim()));
        hd.setMaNV(Integer.parseInt(txtMaNV.getText().trim()));
        hd.setGhiChu(txtGhiChu.getText().trim());
        hd.setNgayTao(XDate.toDate(XDate.getToDay().trim(), "dd/MM/yyyy"));
        hd.setTienKhachTra(0);
        hd.setTrangthai(false);
        return hd;
    }

    void clearHoaDon() {
        txtMaHD.setText(hdDao.getLast());
        txtMaNV.setText(String.valueOf(Auth.user.getMaNV()));
        txtNgayTao.setText("");
        txtGhiChu.setText("");
        lblTrangthai.setText("");
        txtMAHD.setText("");
        txt_MaHD.setText("");
        lblTrangthai.setText("");
        DefaultTableModel model = (DefaultTableModel) tblHoaDonChiTiet.getModel();
        model.setRowCount(0);
        clearHDCT();
        setStatus(true);
    }

    HoaDon getEntity() {
        int row = tblHoaDon.getSelectedRow();
        Integer id = (Integer) tblHoaDon.getValueAt(row, 2);
        HoaDon hoadon = hdDao.getByID(id);
        return hoadon;
    }

    HoaDon getEntityLichSu() {
        int row = tblLichSuHD.getSelectedRow();
        Integer id = (Integer) tblHoaDon.getValueAt(row, 1);
        HoaDon hoadon = hdDao.getByID(id);
        return hoadon;
    }

    void setEntityToForm(HoaDon hd) {
        txtMAHD.setText(String.valueOf(hd.getMaHD()));
        txtMaHD.setText(String.valueOf(hd.getMaHD()));
        txt_MaHD.setText(String.valueOf(hd.getMaHD()));
        txtMaNV.setText(String.valueOf(hd.getMaNV()));
        txtNgayTao.setText(XDate.ChuyenNgay(hd.getNgayTao()));
        lblTrangthai.setText("Chưa thanh toán");
    }

    void Edit() {
        HoaDon hoadon = this.getEntity();
        if (!hoadon.isTrangthai()) {
            int MaHD = hoadon.getMaHD();
            FillTableHoaDonChiTet(MaHD);
            this.setEntityToForm(hoadon);
            setStatus(false);
            setStatusHDCT(false);
        } else {
            JOptionPane.showMessageDialog(null, "Hóa đơn đã thanh toán");
            txt_MaHD.setText("");
        }
    }

    void ShowFormLichSu() {
        int row = tblLichSuHD.getSelectedRow();
        if (row != -1) {
            Integer id = (Integer) tblLichSuHD.getValueAt(row, 2);
            HoaDon hoadon = hdDao.getByID(id);
            if (hoadon.isTrangthai()) {
                HoaDonChiTiet hd = hdctdao.getByIDHD(id);
                if (hd != null) {
                    int MaHD = hoadon.getMaHD();
                    FillTableHoaDonChiTet(MaHD);
                    this.setEntityToForm(hoadon);
                    this.setEntityToHDCT(hd);
                    lblTrangthai.setText("Đã thanh toán");
                    lblTongTien.setText(tongtien + "");
                } else {
                    JOptionPane.showMessageDialog(this, "Không tìm thấy thông tin chi tiết hóa đơn.");
                }
            } else {
                JOptionPane.showMessageDialog(this, "Hóa đơn không ở trạng thái đã thanh toán.");
            }
        } else {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn một hóa đơn để xem lịch sử.");
        }
    }

    // HẾT
    void FillTableHoaDonChiTet(int MaHD) {
        DefaultTableModel model = (DefaultTableModel) tblHoaDonChiTiet.getModel();
        model.setRowCount(0);
        float tongtienThanhToan = 0;
        List<HoaDonChiTiet> list = hdctdao.getByID(MaHD);
        int a = 0;
        for (HoaDonChiTiet hd : list) {
            Object[] rowData = new Object[]{++a, hd.getMaSP(), hd.getSoluong(), hd.getGia(), hd.getSize()};
            model.addRow(rowData);
            tongtienThanhToan += hd.getGia() * hd.getSoluong() * (1 - hd.getPTkhuyenmai());
            tongtien = tongtienThanhToan;
        }
        if (list.isEmpty()) {
            lblTongTien.setText("");
        } else {
            lblTongTien.setText(tongtienThanhToan + "");
            txt_tienkhachtra.setText("");
            txt_tienthoi.setText("");
        }
        if (!list.isEmpty()) {
            txt_MaHD.setText(String.valueOf(list.get(0).getMaHD()));
        }
    }

    HoaDon getFormThanhToan() {
        HoaDon hd = new HoaDon();
        hd.setMaHD(Integer.parseInt(txt_MaHD.getText().trim()));
        hd.setMaNV(Integer.parseInt(txtMaNV.getText().trim()));
        hd.setGhiChu(txtGhiChu.getText().trim());
        hd.setNgayTao(XDate.toDate(XDate.getToDay().trim(), "dd/MM/yyyy"));
        hd.setTienKhachTra(Float.parseFloat(txt_tienkhachtra.getText().trim()));
        hd.setTongTien(Float.parseFloat(lblTongTien.getText().trim()));
        hd.setTrangthai(true);
        return hd;
    }

    HoaDonChiTiet getFormHDCT() {
        HoaDonChiTiet hd = new HoaDonChiTiet();
        hd.setMaHD(Integer.parseInt(txtMAHD.getText().trim()));
        hd.setMaSP(Integer.parseInt(txt_MASP.getText().trim()));
        hd.setSoluong(Integer.parseInt(txtSoLuong.getText().trim()));
        hd.setGia(Float.parseFloat(txtGia.getText().trim()));
        hd.setSize(cboSizeSP.getSelectedItem().toString());
        hd.setPTkhuyenmai(sale);
        return hd;
    }

    void updateSoluong() {
        SanPhamDao spdao = new SanPhamDao();
        int soluong = Integer.parseInt(txtSoLuong.getText().trim());
        int soluongsp = Integer.parseInt(lblSoLuongSP.getText().trim());
        List<SanPham> ls = spdao.getAll();
        for (SanPham sp : ls) {
            if (soluongsp >= soluong) {
                soluongsp -= soluong;
                sp.setSoLuong(soluongsp);
                spdao.upSoluong(sp);
            }
        }
    }

    void insertHDCT(HoaDonChiTiet hdct) {
        try {
            HoaDon hoadon = hdDao.getByID(Integer.parseInt(txtMAHD.getText().trim()));
            if (hoadon != null && !hoadon.isTrangthai()) {
                hdctdao.create(hdct);
                SanPhamDao spdao = new SanPhamDao();
                int soluong = Integer.parseInt(txtSoLuong.getText().trim());
                int soluongsp = Integer.parseInt(lblSoLuongSP.getText().trim());
                List<SanPham> ls = spdao.getAll();
                for (SanPham sp : ls) {
                    if (soluongsp >= soluong) {
                        soluongsp -= soluong;
                        sp.setSoLuong(soluongsp);
                        sp.setMaSP(Integer.parseInt(txt_MASP.getText().trim()));
                        spdao.upSoluong(sp);
                        System.out.println("Thành công");
                        break;
                    } else {
                        TextMes.alertRed(this, "Hết hàng");
                        return;
                    }

                }
                FillTableHoaDonChiTet(hoadon.getMaHD());
                JOptionPane.showMessageDialog(null, "Thêm chi tiết hóa đơn thành công");
            } else {
                JOptionPane.showMessageDialog(null, "Không thể thêm chi tiết hóa đơn cho hóa đơn đã thanh toán");
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Có lỗi xảy ra khi thêm chi tiết hóa đơn");
        }
    }

    void setEntityToHDCT(HoaDonChiTiet hd) {
        a = true;
        try {
            txt_MASP.setText(hd.getMaSP() + "");
            setEntityFromSanPham(hd.getMaSP());
            txtSoLuong.setText(hd.getSoluong() + "");
            txtGia.setText(hd.getGia() + "");
            txtKhuyenMai.setText(String.valueOf(sale));
            cboSizeSP.setSelectedItem(hd.getSize());
            if (hd.getPTkhuyenmai() != -1.0f) {
                float thanhtien = hd.getGia() * hd.getSoluong() * (1 - hd.getPTkhuyenmai());
                txtThanhTien.setText(String.valueOf(thanhtien));
            } else {
                float thanhtien = hd.getGia() * hd.getSoluong();
                txtThanhTien.setText(String.valueOf(thanhtien));
            }
            lblTongTien.setText(tongtien + "");

        } catch (Exception e) {
            e.printStackTrace();
        }
        a = false;
    }

    void setEntityFromSanPham(int MaSP) {
        SanPhamDao spdao = new SanPhamDao();
        List<SanPham> ls = spdao.getAll();
        for (SanPham sp : ls) {
            if (MaSP == sp.getMaSP()) {
                cboTenSP.setSelectedItem(sp.getTenSP());
                cboLoai.setSelectedItem(sp.getLoaiSP());
            }
        }
    }

    HoaDonChiTiet getEntityHD() {
        int row = tblHoaDonChiTiet.getSelectedRow();
        Integer id = (Integer) tblHoaDonChiTiet.getValueAt(row, 1);
        HoaDonChiTiet hoadon = hdctdao.getByIDSP(id);
        return hoadon;
    }

    void EditHDCT() {
        HoaDonChiTiet hd = this.getEntityHD();
        this.setEntityToHDCT(hd);
        setStatusHDCT(false);
    }

    void cboLoai() {
        DefaultComboBoxModel<String> model = (DefaultComboBoxModel<String>) cboLoai.getModel();
        model.removeAllElements();
        model.setSelectedItem("Chọn loại");
        try {
            SanPhamDao spdao = new SanPhamDao();
            List<SanPham> list = spdao.getAll();
            Set<String> uniqueLoaiSP = new HashSet<>();
            for (SanPham sanPham : list) {
                String loaiSP = sanPham.getLoaiSP();
                if (uniqueLoaiSP.add(loaiSP)) {
                    model.addElement(loaiSP);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    void CboTenSP() {
        DefaultComboBoxModel<String> modelTen = (DefaultComboBoxModel<String>) cboTenSP.getModel();
        modelTen.removeAllElements();
        modelTen.setSelectedItem("Chọn sản phẩm");
        try {
            SanPhamDao spdao = new SanPhamDao();
            List<SanPham> list = spdao.getAll();
            for (SanPham sanPham : list) {
                String loaiSP = sanPham.getLoaiSP();
                String tenSP = sanPham.getTenSP();
                if (loaiSP.equals(cboLoai.getSelectedItem())) {
                    modelTen.addElement(tenSP);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    boolean a = false;

    void ShowInfo() {
        if (a == true) {
            return;
        }
        DefaultComboBoxModel model = (DefaultComboBoxModel) cboSizeSP.getModel();
        model.removeAllElements();
        model.setSelectedItem("Chọn size");
        try {
            SanPhamDao spdao = new SanPhamDao();
            Set<String> unique = new HashSet<>();
            Object cboTen = cboTenSP.getSelectedItem();
            if (cboTen != null) {
                String selectedIndex = cboTen.toString();
                List<SanPham> ls = spdao.getAll();
                for (SanPham sp : ls) {
                    if (sp.getTenSP().equals(cboTen)) {
                        int maSP = sp.getMaSP();
                        int soluongsp = sp.getSoLuong();
                        List<String> list = spdao.getSize(maSP);
                        for (String size : list) {
                            model.addElement(size);
                        }
                        txt_MASP.setText(String.valueOf(maSP));
                        lblSoLuongSP.setText(soluongsp + "");
                        GiaSPBySize();
                        Sale(maSP);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    void model() {

    }

    void updateGiaBasedOnSoLuong() {
        try {
            String soLuongStr = txtSoLuong.getText().trim();

            if (soLuongStr.isEmpty() || giaTheoSize <= 0) {
                clearGiaThanhTien();
            } else {
                int soluong = Integer.parseInt(soLuongStr);
                float thanhtien = giaTheoSize * soluong;
                txtThanhTien.setText(String.valueOf(thanhtien));
                lblTongTien.setText(""); // Clear lblTongTien
            }
        } catch (NumberFormatException ex) {
            ex.printStackTrace();
            clearGiaThanhTien();
            txtGia.setText("");
        }
    }

    void clearGiaThanhTien() {
        txtThanhTien.setText("");
        lblTongTien.setText("");
        txt_tienkhachtra.setText("");
        txt_tienthoi.setText("");
    }

    void Sale(int MaSP) {
        KhuyenMaiDAO kmdao = new KhuyenMaiDAO();
        sale = kmdao.getKhuyenMai(MaSP);

        if (sale != -1.0f) {
            giaTheoSize *= (1 - sale);
            DecimalFormat df = new DecimalFormat("#%");
            String formattedSale = df.format(sale);
            txtKhuyenMai.setText(String.valueOf(formattedSale));
        } else {
            txtKhuyenMai.setText("Không có");
        }

        updateGiaBasedOnSoLuong();
    }

    void GiaSPBySize() {
        try {
            SanPhamDao spdao = new SanPhamDao();
            Object cbosize = cboSizeSP.getSelectedItem();
            if (cbosize != null) {
                String size = cbosize.toString();
                String MaSP = txt_MASP.getText();
                if (!MaSP.isEmpty()) {
                    giaTheoSize = spdao.getGiaByMaSPAndSize(Integer.parseInt(MaSP), size);
                    float giaGoc = spdao.getGiaByMaSPAndSize(Integer.parseInt(MaSP), size);
                    if (giaTheoSize != -1.0f) {
                        txtGia.setText(String.valueOf(giaGoc));
                        Sale(Integer.parseInt(MaSP));
                    } else if (size.equals("Chọn Size")) {
                        txtGia.setText("");
                    } else {
                        txtGia.setText("Không có giá cho size này");
                        txtThanhTien.setText("");
                    }
                } else {
                    cboSizeSP.setSelectedItem("Chọn Size");
                }
            } else {
                txtGia.setText("");
            }
        } catch (NumberFormatException ex) {
            ex.printStackTrace();
            txtGia.setText("");
        }
    }

    void TraTien() {
        String tienKhachTraText = txt_tienkhachtra.getText().trim();
        DecimalFormat df = new DecimalFormat("#,###.###");
        if (!tienKhachTraText.isEmpty()) {
            try {
                float tienkhachtra = Float.parseFloat(tienKhachTraText);
                float tienthoi = tienkhachtra - tongtien;
                txt_tienthoi.setText(String.format(df.format(tienthoi)) + "VND");
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        } else {
            txt_tienthoi.setText("");
        }
    }

    void ThanhToanDTB(HoaDon model) {
        try {
            hdDao.update(model);
            this.FillTable();
            DefaultTableModel chitietmodel = (DefaultTableModel) tblHoaDonChiTiet.getModel();
            chitietmodel.setRowCount(0);
            JOptionPane.showMessageDialog(null, "Thanh toán thành công");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    void clearHDCT() {
        txt_MASP.setText("");
        txtSoLuong.setText("");
        txtGia.setText("");
        txtKhuyenMai.setText("");
        txtThanhTien.setText("");
        lblSoLuongSP.setText("");
        setStatusHDCT(true);
    }

    void updateHoaDon() {
        HoaDonChiTiet hd = getFormHDCT();
        hdctdao.update(hd);
        FillTableHoaDonChiTet(hd.getMaHD());
    }

    void deleteHDCT() {
        hdctdao.deteleByID(Integer.parseInt(txt_MASP.getText()));
        HoaDonChiTiet hd = getFormHDCT();
        FillTableHoaDonChiTet(hd.getMaHD());
    }

    void moveFirst() {
        if (tblHoaDon.getRowCount() > 0) {
            int index = 0;
            setSelectRow(index);
            Edit();
        }
    }

    void movePrev() {
        int index = tblHoaDon.getSelectedRow() - 1;
        if (index >= 0) {
            setSelectRow(index);
            Edit();
        } else {
            TextMes.Alert(this, "Bạn đã ở hàng đầu tiên");
        }
    }

    void moveNext() {
        int index = tblHoaDon.getSelectedRow() + 1;
        if (index < tblHoaDon.getRowCount()) {
            setSelectRow(index);
            Edit();
        } else {
            TextMes.Alert(this, "Bạn đã ở hàng cuối cùng");
        }
    }

    void moveLast() {
        if (tblHoaDon.getRowCount() > 0) {
            int index = tblHoaDon.getRowCount() - 1;
            setSelectRow(index);
            Edit();
        }
    }

    void moveFirstHDCT() {
        if (tblHoaDonChiTiet.getRowCount() > 0) {
            int index = 0;
            setSelectRow(index);
            Edit();
        }
    }

    void movePrevHDCT() {
        int index = tblHoaDonChiTiet.getSelectedRow() - 1;
        if (index >= 0) {
            setSelectRow(index);
            Edit();
        } else {
            TextMes.Alert(this, "Bạn đã ở hàng đầu tiên");
        }
    }

    void moveNextHDCT() {
        int index = tblHoaDonChiTiet.getSelectedRow() + 1;
        if (index < tblHoaDonChiTiet.getRowCount()) {
            setSelectRow(index);
            Edit();
        } else {
            TextMes.Alert(this, "Bạn đã ở hàng cuối cùng");
        }
    }

    void moveLastHDCT() {
        if (tblHoaDonChiTiet.getRowCount() > 0) {
            int index = tblHoaDonChiTiet.getRowCount() - 1;
            setSelectRow(index);
            Edit();
        }
    }

    void setStatus(boolean insertable) {
        btnThem.setEnabled(insertable);
        btnSua.setEnabled(!insertable);
        btnXoa.setEnabled(!insertable);
        btnInsert.setEnabled(insertable);
        btnUpdate.setEnabled(!insertable);
        btnDelete.setEnabled(!insertable);
        int rowCount = tblHoaDon.getRowCount();
        boolean first = rowCount > 0 && tblHoaDon.getSelectedRow() == 0;
        boolean last = rowCount > 0 && tblHoaDon.getSelectedRow() == rowCount - 1;
        btnFirst.setEnabled(!insertable && !first);
        btnPrev.setEnabled(!insertable && !first);
        btnNext.setEnabled(!insertable && !last);
        btnLast.setEnabled(!insertable && !last);

    }

    void setStatusHDCT(boolean insertable) {
        btnInsert.setEnabled(insertable);
        btnUpdate.setEnabled(!insertable);
        btnDelete.setEnabled(!insertable);
        int rowCount = tblHoaDon.getRowCount();
        boolean first = rowCount > 0 && tblHoaDon.getSelectedRow() == 0;
        boolean last = rowCount > 0 && tblHoaDon.getSelectedRow() == rowCount - 1;
        btnFirst1.setEnabled(!insertable && !first);
        btnPrev1.setEnabled(!insertable && !first);
        btnNext1.setEnabled(!insertable && !last);
        btnLast1.setEnabled(!insertable && !last);
    }

    void setSelectRow(int index) {
        if (tblHoaDon.getRowCount() > 0) {
            tblHoaDon.setRowSelectionInterval(index, index);
        }
    }

    void FindIDHD() {
        /////// dòng 1759 vô cái hdDao Thêm hàm nào thiếu vào . {getByIDHD(maHDInt)}
        String maHD = txtTimKiem.getText().trim();
        try {
            if (!maHD.isEmpty()) {
                int maHDInt = Integer.parseInt(maHD);
                List<HoaDon> ls = hdDao.getByIDHD(maHDInt);
                DefaultTableModel model = (DefaultTableModel) tblHoaDon.getModel();
                model.setRowCount(0);
                for (HoaDon hd : ls) {
                    if (!hd.isTrangthai()) {
                        model.addRow(new Object[]{hd.isTrangthai() ? "Đã thanh toán" : "Chưa thanh toán", hd.getMaHD(), hd.getNgayTao(), hd.getMaNV(), hd.getGhiChu()});
                    }
                }
            } else {
                FillTable();
            }
        } catch (NumberFormatException ex) {
            // Handle the case where the input is not a valid integer
            ex.printStackTrace();
        }
    }
}
