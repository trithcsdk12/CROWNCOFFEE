/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package com.g5.form;

import com.g5.entity.HoaDon;
import com.g5.entity.HoaDonChiTiet;
import com.g5.entity.KhuyenMai;
import com.g5.entity.NhanVien;
import com.g5.entity.SanPham;
import com.g5.entityDAO.HoaDonChiTietDAO;
import com.g5.entityDAO.HoaDonDAO;
import com.g5.entityDAO.KhuyenMaiDAO;
import com.g5.entityDAO.NhanVienDAOImpl;
import com.g5.entityDAO.SanPhamDao;
import com.g5.util.Auth;
import com.g5.util.ExcelUtil;
import com.g5.util.JDBCHelper;
import com.g5.util.TextMes;
import com.g5.util.Validate;
import com.g5.util.XDate;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfWriter;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Properties;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;
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
    int indexHD = 0;
    int indexHDCT = 0;
    public static final Properties props = JDBCHelper.loadTTProperties();

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

    void setFontTable(JTable table) {
        DefaultTableCellRenderer center = new DefaultTableCellRenderer();
        center.setHorizontalAlignment(JLabel.CENTER);
        table.setDefaultRenderer(Object.class, center);
        table.getTableHeader().setBackground(Color.red);
        table.getTableHeader().setFont(new Font("Tohoma", Font.BOLD, 18));
        table.setOpaque(false);
        table.getTableHeader().setBackground(new Color(32, 136, 203));
        table.getTableHeader().setForeground(new Color(255, 255, 255));
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
        txtNgayTao.setEditable(false);
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
        btnInsert = new javax.swing.JButton();
        btnUpdate = new javax.swing.JButton();
        btnDelete = new javax.swing.JButton();
        btnNew = new javax.swing.JButton();
        btnFirst = new javax.swing.JButton();
        btnPrev = new javax.swing.JButton();
        btnNext = new javax.swing.JButton();
        btnLast = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        jLabel15 = new javax.swing.JLabel();
        lblTongTien = new javax.swing.JLabel();
        lbl_TienKhachTra = new javax.swing.JLabel();
        txt_tienkhachtra = new javax.swing.JTextField();
        jLabel18 = new javax.swing.JLabel();
        txt_tienthoi = new javax.swing.JLabel();
        jButton2 = new javax.swing.JButton();
        jLabel13 = new javax.swing.JLabel();
        txt_MaHD = new javax.swing.JLabel();
        jTabbedPane2 = new javax.swing.JTabbedPane();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane10 = new javax.swing.JScrollPane();
        tblHoaDon = new javax.swing.JTable();
        txtTimKiem = new javax.swing.JTextField();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane11 = new javax.swing.JScrollPane();
        tblLichSuHD = new javax.swing.JTable();
        jButton3 = new javax.swing.JButton();
        jPanel7 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        txtMaHD = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        txtMaNV = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        txtNgayTao = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtGhiChu = new javax.swing.JTextArea();
        btnMoi = new javax.swing.JButton();
        btnXoa = new javax.swing.JButton();
        btnSua = new javax.swing.JButton();
        btnThem = new javax.swing.JButton();
        btnFirst1 = new javax.swing.JButton();
        btnPrev1 = new javax.swing.JButton();
        btnNext1 = new javax.swing.JButton();
        btnLast1 = new javax.swing.JButton();
        jPanel8 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblHoaDonChiTiet = new javax.swing.JTable();

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Quản lí hóa đơn chi tiết", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 18))); // NOI18N

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel2.setText("Mã hóa đơn ");

        jLabel5.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel5.setText("Mã sản phẩm");

        jLabel6.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel6.setText("Số lượng");

        jLabel9.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel9.setText("Giá");

        jLabel10.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel10.setText("Khuyến Mãi");

        jLabel11.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel11.setText("Size");

        txtGia.setEditable(false);
        txtGia.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N

        txtKhuyenMai.setEditable(false);
        txtKhuyenMai.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N

        txt_MASP.setEditable(false);
        txt_MASP.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N

        txtMAHD.setEditable(false);
        txtMAHD.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N

        jLabel12.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel12.setText("Tên sản phẩm");

        txtSoLuong.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N

        jLabel14.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel14.setText("Loại");

        cboTenSP.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
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

        cboLoai.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
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

        jLabel16.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel16.setText("Thành tiền");

        txtThanhTien.setEditable(false);
        txtThanhTien.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N

        cboSizeSP.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
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

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel1.setText("Số lượng còn");

        lblSoLuongSP.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        lblSoLuongSP.setToolTipText("");

        btnInsert.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btnInsert.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/g5/logos/Plus.png"))); // NOI18N
        btnInsert.setPreferredSize(new java.awt.Dimension(50, 50));
        btnInsert.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnInsertActionPerformed(evt);
            }
        });

        btnUpdate.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btnUpdate.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/g5/logos/Edit.png"))); // NOI18N
        btnUpdate.setPreferredSize(new java.awt.Dimension(50, 50));
        btnUpdate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUpdateActionPerformed(evt);
            }
        });

        btnDelete.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btnDelete.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/g5/logos/Remove.png"))); // NOI18N
        btnDelete.setPreferredSize(new java.awt.Dimension(50, 50));
        btnDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteActionPerformed(evt);
            }
        });

        btnNew.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btnNew.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/g5/logos/Broom.png"))); // NOI18N
        btnNew.setPreferredSize(new java.awt.Dimension(50, 50));
        btnNew.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNewActionPerformed(evt);
            }
        });

        btnFirst.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        btnFirst.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/g5/logos/First.png"))); // NOI18N
        btnFirst.setPreferredSize(new java.awt.Dimension(40, 40));
        btnFirst.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnFirstActionPerformed(evt);
            }
        });

        btnPrev.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        btnPrev.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/g5/logos/Back.png"))); // NOI18N
        btnPrev.setPreferredSize(new java.awt.Dimension(40, 40));
        btnPrev.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPrevActionPerformed(evt);
            }
        });

        btnNext.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        btnNext.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/g5/logos/Next.png"))); // NOI18N
        btnNext.setPreferredSize(new java.awt.Dimension(40, 40));
        btnNext.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNextActionPerformed(evt);
            }
        });

        btnLast.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        btnLast.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/g5/logos/Last.png"))); // NOI18N
        btnLast.setPreferredSize(new java.awt.Dimension(40, 40));
        btnLast.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLastActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2)
                    .addComponent(jLabel9)
                    .addComponent(jLabel16)
                    .addComponent(jLabel6)
                    .addComponent(jLabel11))
                .addGap(10, 10, 10)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(cboSizeSP, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(txtMAHD)
                            .addComponent(txtSoLuong)
                            .addComponent(txtGia)
                            .addComponent(txtThanhTien, javax.swing.GroupLayout.PREFERRED_SIZE, 148, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGap(10, 10, 10)
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel10)
                                    .addComponent(jLabel1)
                                    .addComponent(jLabel14)
                                    .addComponent(jLabel5))
                                .addGap(24, 24, 24))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel12)
                                .addGap(18, 18, 18)))
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(lblSoLuongSP, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(cboTenSP, 0, 171, Short.MAX_VALUE)
                            .addComponent(cboLoai, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(txt_MASP)
                            .addComponent(txtKhuyenMai)))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(btnInsert, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(btnUpdate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(btnDelete, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(btnFirst, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(37, 37, 37)
                                .addComponent(btnPrev, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(btnNext, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(btnNew, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnLast, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(cboLoai, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel14))
                        .addGap(64, 64, 64)
                        .addComponent(txt_MASP, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(63, 63, 63)
                        .addComponent(lblSoLuongSP, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtMAHD, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel2))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtSoLuong, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel12)
                            .addComponent(cboTenSP, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtGia, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel9)
                            .addComponent(jLabel5))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(cboSizeSP, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel11)
                            .addComponent(jLabel10)
                            .addComponent(txtKhuyenMai, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtThanhTien, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel16)
                            .addComponent(jLabel1))))
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnInsert, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnUpdate, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnDelete, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnNew, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(btnFirst, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnPrev, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnNext, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnLast, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Thanh toán", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 18))); // NOI18N

        jLabel15.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel15.setText("Tổng tiền");

        lblTongTien.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        lblTongTien.setForeground(new java.awt.Color(255, 51, 51));
        lblTongTien.setText("0 VND");

        lbl_TienKhachTra.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        lbl_TienKhachTra.setText("Tiền khách trả");

        txt_tienkhachtra.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N

        jLabel18.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel18.setText("Tiền trả khách");

        txt_tienthoi.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N

        jButton2.setBackground(new java.awt.Color(0, 204, 51));
        jButton2.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jButton2.setText("Thanh toán");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jLabel13.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel13.setText("Mã Hóa Đơn:");

        txt_MaHD.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        txt_MaHD.setForeground(new java.awt.Color(51, 51, 255));
        txt_MaHD.setText("Chưa chọn");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel15)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblTongTien, javax.swing.GroupLayout.PREFERRED_SIZE, 313, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lbl_TienKhachTra)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txt_tienkhachtra, javax.swing.GroupLayout.PREFERRED_SIZE, 280, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel18)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txt_tienthoi, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel13)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txt_MaHD)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton2)))
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel18)
                        .addComponent(txt_tienkhachtra, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(lbl_TienKhachTra)
                        .addComponent(jLabel15)
                        .addComponent(lblTongTien))
                    .addComponent(txt_tienthoi, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(6, 6, 6)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel13)
                    .addComponent(txt_MaHD)
                    .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jTabbedPane2.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Danh sách hóa đơn", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 18))); // NOI18N

        tblHoaDon.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
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
                false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblHoaDon.setFocusable(false);
        tblHoaDon.setIntercellSpacing(new java.awt.Dimension(0, 0));
        tblHoaDon.setRowHeight(25);
        tblHoaDon.setSelectionBackground(new java.awt.Color(204, 204, 255));
        tblHoaDon.setShowVerticalLines(false);
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
                    .addComponent(jScrollPane10, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 693, Short.MAX_VALUE)
                    .addComponent(txtTimKiem))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(txtTimKiem, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane10, javax.swing.GroupLayout.DEFAULT_SIZE, 205, Short.MAX_VALUE)
                .addContainerGap())
        );

        jTabbedPane2.addTab("Hóa đơn", jPanel2);

        tblLichSuHD.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        tblLichSuHD.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "STT", "Trạng thái", "Mã HD", "Ngày tạo", "Mã NV", "Tổng tiền", "Ghi chú"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.Integer.class, java.lang.String.class, java.lang.Integer.class, java.lang.Float.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                true, false, false, false, false, true, false
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
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jButton3))
                    .addComponent(jScrollPane11, javax.swing.GroupLayout.DEFAULT_SIZE, 699, Short.MAX_VALUE))
                .addGap(16, 16, 16))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addComponent(jScrollPane11, javax.swing.GroupLayout.DEFAULT_SIZE, 216, Short.MAX_VALUE)
                .addGap(16, 16, 16)
                .addComponent(jButton3)
                .addContainerGap())
        );

        jTabbedPane2.addTab("Lịch sử", jPanel1);

        jPanel7.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Quản lí hóa đơn", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 18))); // NOI18N

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel3.setText("Mã hóa đơn");

        txtMaHD.setEditable(false);
        txtMaHD.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        txtMaHD.setFocusable(false);

        jLabel4.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel4.setText("Mã nhân viên");

        txtMaNV.setEditable(false);
        txtMaNV.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N

        jLabel8.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel8.setText("Ngày tạo");

        txtNgayTao.setEditable(false);
        txtNgayTao.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N

        jLabel7.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel7.setText("Ghi chú");

        txtGhiChu.setColumns(20);
        txtGhiChu.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        txtGhiChu.setRows(5);
        jScrollPane1.setViewportView(txtGhiChu);

        btnMoi.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btnMoi.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/g5/logos/Broom.png"))); // NOI18N
        btnMoi.setPreferredSize(new java.awt.Dimension(50, 50));
        btnMoi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMoiActionPerformed(evt);
            }
        });

        btnXoa.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btnXoa.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/g5/logos/Remove.png"))); // NOI18N
        btnXoa.setPreferredSize(new java.awt.Dimension(50, 50));
        btnXoa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXoaActionPerformed(evt);
            }
        });

        btnSua.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btnSua.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/g5/logos/Edit.png"))); // NOI18N
        btnSua.setPreferredSize(new java.awt.Dimension(50, 50));
        btnSua.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSuaActionPerformed(evt);
            }
        });

        btnThem.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btnThem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/g5/logos/Plus.png"))); // NOI18N
        btnThem.setPreferredSize(new java.awt.Dimension(50, 50));
        btnThem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThemActionPerformed(evt);
            }
        });

        btnFirst1.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        btnFirst1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/g5/logos/First.png"))); // NOI18N
        btnFirst1.setPreferredSize(new java.awt.Dimension(40, 40));
        btnFirst1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnFirst1ActionPerformed(evt);
            }
        });

        btnPrev1.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        btnPrev1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/g5/logos/Back.png"))); // NOI18N
        btnPrev1.setPreferredSize(new java.awt.Dimension(40, 40));
        btnPrev1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPrev1ActionPerformed(evt);
            }
        });

        btnNext1.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        btnNext1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/g5/logos/Next.png"))); // NOI18N
        btnNext1.setPreferredSize(new java.awt.Dimension(40, 40));
        btnNext1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNext1ActionPerformed(evt);
            }
        });

        btnLast1.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        btnLast1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/g5/logos/Last.png"))); // NOI18N
        btnLast1.setPreferredSize(new java.awt.Dimension(40, 40));
        btnLast1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLast1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel8)
                            .addComponent(jLabel3))
                        .addGap(23, 23, 23))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createSequentialGroup()
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)))
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(txtNgayTao, javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(txtMaNV, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 197, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(txtMaHD, javax.swing.GroupLayout.PREFERRED_SIZE, 198, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addComponent(jLabel7)
                        .addGap(30, 30, 30)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 231, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addGap(103, 103, 103)
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnThem, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnFirst1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnPrev1, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnSua, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnXoa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnNext1, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel7Layout.createSequentialGroup()
                                .addGap(10, 10, 10)
                                .addComponent(btnLast1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(btnMoi, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(btnPrev1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel7Layout.createSequentialGroup()
                                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel3)
                                    .addComponent(txtMaHD, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel7))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel4)
                                    .addComponent(txtMaNV, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(22, 22, 22)
                                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel8)
                                    .addComponent(txtNgayTao, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 132, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 98, Short.MAX_VALUE)
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnThem, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnSua, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnXoa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnMoi, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel7Layout.createSequentialGroup()
                                .addGap(11, 11, 11)
                                .addComponent(btnFirst1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(btnNext1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(btnLast1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))))
                .addContainerGap())
        );

        jPanel8.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Chi tiết hóa đơn", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 18))); // NOI18N

        tblHoaDonChiTiet.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        tblHoaDonChiTiet.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "STT", "Mã SP", "Số lượng", "GIÁ", "SIZE"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.Integer.class, java.lang.Integer.class, java.lang.Float.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblHoaDonChiTiet.setFocusable(false);
        tblHoaDonChiTiet.setIntercellSpacing(new java.awt.Dimension(0, 0));
        tblHoaDonChiTiet.setRowHeight(30);
        tblHoaDonChiTiet.setSelectionBackground(new java.awt.Color(204, 204, 255));
        tblHoaDonChiTiet.setShowVerticalLines(false);
        tblHoaDonChiTiet.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblHoaDonChiTietMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tblHoaDonChiTiet);

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2)
                .addContainerGap())
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 259, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addContainerGap())
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jTabbedPane2))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jPanel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(10, 10, 10))))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTabbedPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 331, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(15, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void tblLichSuHDMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblLichSuHDMouseClicked
        if (evt.getClickCount() == 1) {
            this.ShowFormLichSu();
            TextMes.Alert(this, "Hóa đơn đã thanh toán");
        }
    }//GEN-LAST:event_tblLichSuHDMouseClicked
    boolean clickHDCT = false;
    private void tblHoaDonChiTietMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblHoaDonChiTietMouseClicked
        if (evt.getClickCount() == 1) {
            clickHDCT = true;
            indexHDCT = tblHoaDonChiTiet.getSelectedRow();
            this.EditHDCT();
        }
    }//GEN-LAST:event_tblHoaDonChiTietMouseClicked

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed

        HoaDon hoaDon = getFormThanhToan();
        if (hoaDon != null) {
            ThanhToanDTB(hoaDon);

        }


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

    private void tblHoaDonMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblHoaDonMouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_tblHoaDonMouseEntered
    boolean clearHDCT = false;
    private void tblHoaDonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblHoaDonMouseClicked
        if (evt.getClickCount() == 2) {

            indexHD = tblHoaDon.getSelectedRow();
            this.Edit();
            clearHDCT();
            TextMes.Alert(null, "Nhấn mới để thêm sản phẩm");
        }
    }//GEN-LAST:event_tblHoaDonMouseClicked

    private void btnMoiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMoiActionPerformed
        clearHoaDon();
    }//GEN-LAST:event_btnMoiActionPerformed

    private void btnXoaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXoaActionPerformed
        deleteHoaDon();
        JOptionPane.showMessageDialog(null, "Xoa Thanh Cong");
    }//GEN-LAST:event_btnXoaActionPerformed

    private void btnSuaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSuaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnSuaActionPerformed

    private void btnThemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThemActionPerformed
        // TODO add your handling code here:
        insert(getForm());
    }//GEN-LAST:event_btnThemActionPerformed

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

    private void btnInsertActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnInsertActionPerformed
        // TODO add your handling code here:
        insertHDCT(getFormHDCT());

    }//GEN-LAST:event_btnInsertActionPerformed

    private void btnUpdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUpdateActionPerformed
        // TODO add your handling code here:
        updateHoaDon();
        JOptionPane.showMessageDialog(null, "Sửa thành công");
    }//GEN-LAST:event_btnUpdateActionPerformed

    private void btnDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteActionPerformed
        // TODO add your handling code here:
        deleteHDCT();
        JOptionPane.showMessageDialog(null, "Xóa thành công");
    }//GEN-LAST:event_btnDeleteActionPerformed

    private void btnNewActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNewActionPerformed
        // TODO add your handling code here:

        clearHDCT();
    }//GEN-LAST:event_btnNewActionPerformed

    private void btnFirst1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFirst1ActionPerformed
        // TODO add your handling code here:
        this.moveFirstHD();
    }//GEN-LAST:event_btnFirst1ActionPerformed

    private void btnPrev1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPrev1ActionPerformed
        // TODO add your handling code here:
        this.movePrevHD();
    }//GEN-LAST:event_btnPrev1ActionPerformed

    private void btnNext1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNext1ActionPerformed
        // TODO add your handling code here:

        this.moveNextHD();
    }//GEN-LAST:event_btnNext1ActionPerformed

    private void btnLast1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLast1ActionPerformed
        // TODO add your handling code here:
        this.moveLastHD();
    }//GEN-LAST:event_btnLast1ActionPerformed

    private void btnFirstActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFirstActionPerformed
        // TODO add your handling code here:
        moveFirstHDCT();
    }//GEN-LAST:event_btnFirstActionPerformed

    private void btnPrevActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPrevActionPerformed
        // TODO add your handling code here:
        movePrevHDCT();
    }//GEN-LAST:event_btnPrevActionPerformed

    private void btnNextActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNextActionPerformed
        // TODO add your handling code here:
        moveNextHDCT();
    }//GEN-LAST:event_btnNextActionPerformed

    private void btnLastActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLastActionPerformed
        // TODO add your handling code here:
        moveLastHDCT();
    }//GEN-LAST:event_btnLastActionPerformed
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
    private javax.swing.JLabel jLabel18;
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
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane10;
    private javax.swing.JScrollPane jScrollPane11;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTabbedPane jTabbedPane2;
    private javax.swing.JLabel lblSoLuongSP;
    private javax.swing.JLabel lblTongTien;
    private javax.swing.JLabel lbl_TienKhachTra;
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
        txtNgayTao.setText(XDate.toString(new Date(), "dd-MM-yyyy"));
        int a = 0;
        int b = 0;
        for (HoaDon hoaDon : ls) {

            boolean daThanhToan = hoaDon.isTrangthai();
            float tienKhachTra = hoaDon.getTienKhachTra();
            if (tienKhachTra == 0 && !daThanhToan) {
                Object[] rowData = new Object[]{++a, daThanhToan ? "Đã thanh toán" : "Chưa thanh toán", hoaDon.getMaHD(), hoaDon.getNgayTao(), hoaDon.getMaNV(), hoaDon.getGhiChu()};
                model.addRow(rowData);
            } else {
                Object[] rowData = new Object[]{++b, daThanhToan ? "Đã thanh toán" : "Chưa thanh toán", hoaDon.getMaHD(), hoaDon.getNgayTao(), hoaDon.getMaNV(), hoaDon.getTongTien(), hoaDon.getGhiChu()};
                lichSuModel.addRow(rowData);

            }
        }
    }

    void insert(HoaDon hd) {
        List<JTextField> textFieldList = List.of(txtMaHD, txtMaNV, txtNgayTao);

        if (!Validate.kiemTraTrongText(textFieldList, null)) {
            return;
        }
        try {
            hdDao.create(hd);
            FillTable();
            clearHoaDon();
            JOptionPane.showMessageDialog(null, "Thêm hóa đơn thành công");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    void deleteHoaDon() {
        hdDao.deteleByID(Integer.parseInt(txtMaHD.getText()));
        TextMes.Alert(null, "Xóa hóa đơn thành công");
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
        //   txtMaHD.setText(hdDao.getLast());
        txtMaNV.setText(String.valueOf(Auth.user.getMaNV()));
        txtNgayTao.setText(XDate.toString(new Date(), "dd-MM-yyyy"));
        txtGhiChu.setText("");

        txtMAHD.setText("");
        txt_MaHD.setText("Chưa chọn");
        txt_tienkhachtra.setText("");
        txt_tienthoi.setText("");

        lblTongTien.setText("0 VND");
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
        txtGhiChu.setText(hd.getGhiChu());

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

                    lblTongTien.setText(tongtien + " VND");
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
            if (hd.getPTkhuyenmai() != -1.0f) {
                tongtienThanhToan += hd.getGia() * hd.getSoluong() * (1 - hd.getPTkhuyenmai());
            } else {
                tongtienThanhToan += hd.getGia() * hd.getSoluong();

            }

            tongtien = tongtienThanhToan;
        }
        if (list.isEmpty()) {
            lblTongTien.setText("0 VND");
        } else {
            lblTongTien.setText(tongtienThanhToan + " VND");
            txt_tienkhachtra.setText("");
            txt_tienthoi.setText("");
        }
        if (!list.isEmpty()) {
            txt_MaHD.setText(String.valueOf(list.get(0).getMaHD()));
        }
    }

    HoaDon getFormThanhToan() {
        HoaDon hd = new HoaDon();
        try {
            hd.setMaHD(Integer.parseInt(txt_MaHD.getText().trim()));
            hd.setMaNV(Integer.parseInt(txtMaNV.getText().trim()));
            hd.setGhiChu(txtGhiChu.getText().trim());
            hd.setNgayTao(XDate.toDate(XDate.getToDay().trim(), "dd/MM/yyyy"));
            float tienKhachTra = Float.parseFloat(txt_tienkhachtra.getText().trim());
            hd.setTienKhachTra(tienKhachTra);
            String tien = lblTongTien.getText().trim();
            String tien2 = tien.replace("VND", "");
            float tongTien = Float.parseFloat(tien2.trim());
            hd.setTongTien(tongTien);
            hd.setTrangthai(true);
        } catch (NumberFormatException e) {
            TextMes.alertRed(null, "Lỗi: Định dạng số không đúng cho Tiền Khách Trả");
            return null;
        }
        return hd;
    }

    boolean kiemtra() {
        boolean maHD = !Validate.kiemTraMa(txtMAHD.getText().trim());
        boolean soluong = !Validate.kiemTraSo(txtSoLuong.getText().trim());
        String a = "";
        if (maHD) {
            a += "Chưa có mã hóa đơn";

        }
        if (soluong) {
            a += "Chưa có số lượng hoặc số lượng không đúng định dạng";
        }
        if (a.length() > 0) {
            TextMes.alertRed(null, a);
        }
        return true;
    }

    HoaDonChiTiet getFormHDCT() {

        HoaDonChiTiet hd = new HoaDonChiTiet();
        String maHDText = txtMAHD.getText().trim();
        String maSPText = txt_MASP.getText().trim();
        String soLuongText = txtSoLuong.getText().trim();
        String giaText = txtGia.getText().trim();

        // Kiểm tra mã hóa đơn
        if (!Validate.kiemTraMa(maHDText)) {
            TextMes.alertRed(null, "Chưa có mã hóa đơn");
            return null; // Trả về null nếu có lỗi
        }

        // Kiểm tra số lượng
        if (!Validate.kiemTraSo(soLuongText)) {
            TextMes.alertRed(null, "Chưa có số lượng hoặc số lượng không đúng định dạng");
            return null; // Trả về null nếu có lỗi
        }

        try {
            // Thực hiện chuyển đổi từ chuỗi sang số
            hd.setMaHD(Integer.parseInt(maHDText));
            hd.setMaSP(Integer.parseInt(maSPText));
            hd.setSoluong(Integer.parseInt(soLuongText));
            hd.setGia(Float.parseFloat(giaText));
        } catch (NumberFormatException e) {
            TextMes.alertRed(null, "Lỗi: Định dạng số không đúng");
            return null; // Trả về null nếu có lỗi
        }

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
        SanPhamDao spdao = new SanPhamDao();
        HoaDonChiTietDAO hdctDao = new HoaDonChiTietDAO();
        HoaDon hoadon = hdDao.getByID(Integer.parseInt(txtMAHD.getText().trim()));
        try {
            if (txtMAHD.getText().trim().isEmpty()) {
                return;
            }
            if (txtSoLuong.getText().trim().isEmpty()) {
                return;
            }
            
            
            try {
                if (hdctDao.getSPbiTrung(hdct.getMaSP(), hdct.getSize()) == 1) {
                    spdao.upSoluongBiTrung(hdct.getSoluong(), hdct.getMaSP(), hdct.getSize().trim());
                    FillTableHoaDonChiTet(hoadon.getMaHD());
                    JOptionPane.showMessageDialog(null, "Cập nhật chi tiết hóa đơn thành công");
                    return;
                }
            } catch (Exception e) {
                return;
            }

            if (hoadon != null && !hoadon.isTrangthai()) {

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
                        TextMes.alertRed(this, "Số lượng hàng không đủ");
                        return;
                    }

                }
                hdctdao.create(hdct);
                FillTableHoaDonChiTet(hoadon.getMaHD());
                JOptionPane.showMessageDialog(null, "Thêm chi tiết hóa đơn thành công");
            } else {
                JOptionPane.showMessageDialog(null, "Không thể thêm chi tiết hóa đơn cho hóa đơn đã thanh toán");
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Có lỗi xảy ra khi thêm chi tiết hóa đơn");
        }
        clearHDCT();
    }

    void setEntityToHDCT(HoaDonChiTiet hd) {
        a = true;
        SanPhamDao spdao = new SanPhamDao();
        try {
            DefaultComboBoxModel<String> modelSize = (DefaultComboBoxModel<String>) cboSizeSP.getModel();

            List<String> list = spdao.getSize(hd.getMaSP());
            DefaultComboBoxModel<String> modelTenSP = (DefaultComboBoxModel<String>) cboTenSP.getModel();
            modelTenSP.removeAllElements();
            DefaultComboBoxModel<String> modelLoai = (DefaultComboBoxModel<String>) cboLoai.getModel();
            modelLoai.removeAllElements();
            List<SanPham> ls = spdao.getAll();
            for (SanPham sp : ls) {
                if (hd.getMaSP() == sp.getMaSP()) {
                    modelTenSP.setSelectedItem(sp.getTenSP());
                    modelLoai.setSelectedItem(sp.getLoaiSP());
                    break;
                }
            }

            modelSize.removeAllElements();
            for (String size : list) {
                modelSize.addElement(size);
            }
            //    modelTen.setSelectedItem(tblHoaDonChiTiet.getValueAt(indexHDCT, 4).toString().trim());
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
            lblTongTien.setText(tongtien + " VND");

        } catch (Exception e) {
            e.printStackTrace();
        }
        a = false;
    }

    void setEntityFromSanPham(int MaSP) {
        SanPhamDao spdao = new SanPhamDao();
        DefaultComboBoxModel model = (DefaultComboBoxModel) cboTenSP.getModel();
        DefaultComboBoxModel modelLoai = (DefaultComboBoxModel) cboLoai.getModel();
        if (clickHDCT == true) {
            model.removeAllElements();
            clickHDCT = false;
        }
        List<SanPham> ls = spdao.getAll();
        for (SanPham sp : ls) {
            if (MaSP == sp.getMaSP()) {
                model.setSelectedItem(sp.getTenSP());
                modelLoai.setSelectedItem(sp.getLoaiSP());
                break;
            }
        }
    }

    HoaDonChiTiet getEntityHD() {
        //    int row = tblHoaDonChiTiet.getSelectedRow();
        Integer id = (Integer) tblHoaDonChiTiet.getValueAt(indexHDCT, 1);
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
        model.setSelectedItem("Chọn loại");
    }

    void CboTenSP() {
        DefaultComboBoxModel<String> modelTen = (DefaultComboBoxModel<String>) cboTenSP.getModel();
        modelTen.removeAllElements();
        modelTen.setSelectedItem("Chưa chọn");
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
                lblTongTien.setText("0 VND"); // Clear lblTongTien
            }
        } catch (NumberFormatException ex) {
            ex.printStackTrace();
            clearGiaThanhTien();
            txtGia.setText("");
        }
    }

    void clearGiaThanhTien() {
        txtThanhTien.setText("");
        lblTongTien.setText("0 VND");
        txt_tienkhachtra.setText("");
        txt_tienthoi.setText("");
    }

    public boolean isValidDate(String startDateStr, String endDateStr) {
        try {
            SimpleDateFormat inputFormat = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy");
            SimpleDateFormat outputFormat = new SimpleDateFormat("yyyy-MM-dd");

            Date startDate = inputFormat.parse(startDateStr);
            Date endDate = outputFormat.parse(endDateStr);

            String formattedStartDateStr = outputFormat.format(startDate);
            String formattedEndDateStr = outputFormat.format(endDate);

            startDate = outputFormat.parse(formattedStartDateStr);
            endDate = outputFormat.parse(formattedEndDateStr);

            if (startDate.equals(endDate) || startDate.before(endDate)) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    void Sale(int MaSP) {
        KhuyenMaiDAO kmdao = new KhuyenMaiDAO();
        try {
            if (kmdao.getSPINKM(MaSP) == 0) {
                txtKhuyenMai.setText("Không có");
                return;
            }
        } catch (Exception e) {
            return;
        }

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
                        txtGia.setText("Không có giá");
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
            String tienkhachtra = txt_tienkhachtra.getText().trim();
            if (tienkhachtra.isEmpty()) {
                return;
            }
            float tienKhachTra = Float.parseFloat(tienkhachtra);
            String tien = lblTongTien.getText().trim();
            String tien2 = tien.replace("VND", "");
            float tongTien = Float.parseFloat(tien2.trim());

            if (tienKhachTra < tongTien) {
                TextMes.alertRed(null, "Tiền khách trả chưa đủ");
                return;
            }
            hdDao.update(model);
            InHoaDon();
            this.FillTable();
            DefaultTableModel chitietmodel = (DefaultTableModel) tblHoaDonChiTiet.getModel();
            chitietmodel.setRowCount(0);
            JOptionPane.showMessageDialog(null, "Thanh toán thành công");
            clearHoaDon();
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

        DefaultComboBoxModel<String> model = (DefaultComboBoxModel<String>) cboLoai.getModel();
        //   model.removeAllElements();
        model.setSelectedItem("Chọn loại");
        DefaultComboBoxModel<String> modelTen = (DefaultComboBoxModel<String>) cboTenSP.getModel();
        //   modelTen.removeAllElements();
        modelTen.setSelectedItem("Chưa chọn");
        DefaultComboBoxModel<String> modelSize = (DefaultComboBoxModel<String>) cboSizeSP.getModel();
        //   modelSize.removeAllElements();
        modelSize.setSelectedItem("Chọn size");
        cboLoai();

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

    void moveFirstHD() {
        if (tblHoaDon.getRowCount() == 0) {

            return;
        }
        indexHD = 0;

        tblHoaDon.setRowSelectionInterval(indexHD, indexHD);
        tblHoaDon.scrollRectToVisible(tblHoaDon.getCellRect(indexHD, 0, true));

        Edit();

    }

    void movePrevHD() {
        if (tblHoaDon.getRowCount() == 0) {

            return;
        }
        indexHD--;
        if (indexHD <= 0) {
            indexHD = 0;
        }
        tblHoaDon.setRowSelectionInterval(indexHD, indexHD);
        tblHoaDon.scrollRectToVisible(tblHoaDon.getCellRect(indexHD, 0, true));

        Edit();

    }

    void moveNextHD() {

        if (tblHoaDon.getRowCount() == 0) {

            return;
        }
        indexHD++;
        if (indexHD >= tblHoaDon.getRowCount()) {
            indexHD = tblHoaDon.getRowCount() - 1;
        }
        tblHoaDon.setRowSelectionInterval(indexHD, indexHD);
        tblHoaDon.scrollRectToVisible(tblHoaDon.getCellRect(indexHD, 0, true));

        Edit();

    }

    void moveLastHD() {
        if (tblHoaDon.getRowCount() == 0) {

            return;
        }
        indexHD = tblHoaDon.getRowCount() - 1;
        tblHoaDon.setRowSelectionInterval(indexHD, indexHD);
        tblHoaDon.scrollRectToVisible(tblHoaDon.getCellRect(indexHD, 0, true));

        Edit();

    }

    void moveFirstHDCT() {
        if (tblHoaDonChiTiet.getRowCount() == 0) {
            return;
        }
        indexHDCT = 0;

        tblHoaDonChiTiet.setRowSelectionInterval(indexHDCT, indexHDCT);
        tblHoaDonChiTiet.scrollRectToVisible(tblHoaDonChiTiet.getCellRect(indexHDCT, 0, true));
        EditHDCT();
    }

    void movePrevHDCT() {

        indexHDCT--;
        if (indexHDCT <= 0) {
            indexHDCT = 0;
        }

        tblHoaDonChiTiet.setRowSelectionInterval(indexHDCT, indexHDCT);
        tblHoaDonChiTiet.scrollRectToVisible(tblHoaDonChiTiet.getCellRect(indexHDCT, 0, true));
        //   Edit();
        EditHDCT();

    }

    void moveNextHDCT() {
        indexHDCT++;
        if (indexHDCT >= tblHoaDonChiTiet.getRowCount()) {
            indexHDCT = tblHoaDonChiTiet.getRowCount() - 1;
        }
        //  Edit();

        tblHoaDonChiTiet.setRowSelectionInterval(indexHDCT, indexHDCT);
        tblHoaDonChiTiet.scrollRectToVisible(tblHoaDonChiTiet.getCellRect(indexHDCT, 0, true));
        EditHDCT();

    }

    void moveLastHDCT() {
        if (tblHoaDonChiTiet.getRowCount() > 0) {
            indexHDCT = tblHoaDonChiTiet.getRowCount() - 1;

            //    Edit();
        }

        tblHoaDonChiTiet.setRowSelectionInterval(indexHDCT, indexHDCT);
        tblHoaDonChiTiet.scrollRectToVisible(tblHoaDonChiTiet.getCellRect(indexHDCT, 0, true));
        EditHDCT();
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
        btnFirst1.setEnabled(!insertable && !first);
        btnPrev1.setEnabled(!insertable && !first);
        btnNext1.setEnabled(!insertable && !last);
        btnLast1.setEnabled(!insertable && !last);

    }

    void setStatusHDCT(boolean insertable) {
        btnInsert.setEnabled(insertable);
        btnUpdate.setEnabled(!insertable);
        btnDelete.setEnabled(!insertable);
        int rowCount = tblHoaDonChiTiet.getRowCount();
        boolean first = rowCount > 0 && tblHoaDonChiTiet.getSelectedRow() == 0;
        boolean last = rowCount > 0 && tblHoaDonChiTiet.getSelectedRow() == rowCount - 1;
        btnFirst.setEnabled(!insertable && !first);
        btnPrev.setEnabled(!insertable && !first);
        btnNext.setEnabled(!insertable && !last);
        btnLast.setEnabled(!insertable && !last);
    }

    void setSelectRow(int index) {
        tblHoaDon.setRowSelectionInterval(index, index);
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
                int a = 0;
                for (HoaDon hd : ls) {
                    if (!hd.isTrangthai()) {
                        model.addRow(new Object[]{++a, hd.isTrangthai() ? "Đã thanh toán" : "Chưa thanh toán", hd.getMaHD(), hd.getNgayTao(), hd.getMaNV(), hd.getGhiChu()});
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

    // In Hóa Đơn
    void InHoaDon() {
        SanPhamDao spdao = new SanPhamDao();
        NhanVienDAOImpl nvdao = new NhanVienDAOImpl();
        List<HoaDon> list = hdDao.getByIDHD(Integer.parseInt(txtMaHD.getText().trim()));
        Document document = new Document();
        LocalTime currentTime = LocalTime.now();

        // Định dạng hiển thị của giờ
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");

        // Chuyển đổi và in ra màn hình
        String formattedTime = currentTime.format(formatter);
        try {
            HoaDon hdTT = getFormThanhToan();

            // Tạo một đối tượng LocalDateTime để lấy thời gian hiện tại
            LocalDateTime now = LocalDateTime.now();

            // Định dạng thời gian theo định dạng yyyymmddhhmmss
            DateTimeFormatter formatter2 = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");

            // Chuyển đổi thời gian thành chuỗi
            String formattedDateTime = now.format(formatter2);

            // Tạo tên file mới với thời gian hiện tại
            String fileName = "vietnamese_" + formattedDateTime + ".pdf";

            // Tạo đối tượng PdfWriter với tên file mới
            PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(fileName));
            document.open();
            document.setPageSize(PageSize.A7);
            BaseFont bf = BaseFont.createFont(HoaDonJPanel.class.getResource("/com/g5/util/") + "font.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
            // HEADER
            com.itextpdf.text.Font fontheader = new com.itextpdf.text.Font(bf, 16, com.itextpdf.text.Font.BOLD);
            Paragraph paragraph = new Paragraph("THE CROWN COFFEE \n", fontheader);
            String diachi = props.getProperty("diachi");
            if (diachi.trim().length() == 0) {
                diachi = "Vui lòng nhập địa chỉ trong ThongTin.properties";
            }
            paragraph.add(diachi + " \n");
            paragraph.add("---------------------------------------------------------------------------\n");
            paragraph.setAlignment(Paragraph.ALIGN_CENTER);
            document.add(paragraph);

            // PHIEU THANH TOAN
            com.itextpdf.text.Font fontThanhToan = new com.itextpdf.text.Font(bf, 16);
            Paragraph paragraphTT = new Paragraph("PHIẾU HÓA ĐƠN\n", fontThanhToan);
            paragraphTT.setAlignment(Paragraph.ALIGN_CENTER);
            document.add(paragraphTT);
            Paragraph paragraphTT2 = new Paragraph("", fontThanhToan);
            paragraphTT2.add("\nMã hóa đơn:              " + hdTT.getMaHD());

            paragraphTT2.add("\nNgày tạo:                  " + XDate.toString(hdTT.getNgayTao(), "dd-MM-yyyy") + "   " + formattedTime);
            List<NhanVien> ls = nvdao.getByIDNV(hdTT.getMaNV());
            for (NhanVien nv : ls) {
                paragraphTT2.add("\nNhân viên:                " + nv.getHoTen());
            }
            paragraphTT.setAlignment(Paragraph.ALIGN_LEFT);
            document.add(paragraphTT2);
            Paragraph paragraphgach = new Paragraph("", fontThanhToan);
            paragraphgach.add("--------------------------------------------------------------------------\n");
            paragraphgach.setAlignment(Paragraph.ALIGN_JUSTIFIED_ALL);
            document.add(paragraphgach);

            // Thông tin sản phẩm
            // Thông tin sản phẩm
            com.itextpdf.text.Font fontSP = new com.itextpdf.text.Font(bf, 16);
            Paragraph paragraphSP = new Paragraph("", fontSP);
            float giaKhuyenMai = 0;
            float giaTong = 0;
            float tongtien = 0;
            float tienkhachtra = 0;
            float tienthoilai = 0;
            tienkhachtra += hdTT.getTienKhachTra();
            List<HoaDonChiTiet> listCT = hdctdao.getByID(hdTT.getMaHD());
            for (HoaDonChiTiet hoaDonChiTiet : listCT) {
                SanPham listSP = spdao.getByID(hoaDonChiTiet.getMaSP());
                paragraphSP.add("\n" + hoaDonChiTiet.getSoluong() + "     ");
                paragraphSP.add(listSP.getTenSP() + "               ");
                Chunk chunk = new Chunk(hoaDonChiTiet.getGia() + " ", fontSP);
                chunk.setUnderline(0.5f, 5f);
                paragraphSP.add(chunk);
                KhuyenMaiDAO kmdao = new KhuyenMaiDAO();
                float sale;
                sale = kmdao.getKhuyenMai(hoaDonChiTiet.getMaSP());
                giaKhuyenMai = hoaDonChiTiet.getGia();
                if (sale != -1.0f) {
                    giaKhuyenMai *= (1 - sale);
                } else {
                    giaKhuyenMai = hoaDonChiTiet.getGia();
//                        System.out.println(giaKhuyenMai);
                }
                giaTong = giaKhuyenMai * hoaDonChiTiet.getSoluong();
                tongtien = hdTT.getTongTien();
                tienthoilai = hdTT.getTienKhachTra() - hdTT.getTongTien();
                paragraphSP.add("              " + giaKhuyenMai);
                paragraphSP.add("              " + giaTong + "\n");
            }

            paragraphSP.setAlignment(Paragraph.ALIGN_LEFT);
            document.add(paragraphSP);
            document.add(paragraphgach);
            com.itextpdf.text.Font fontTong = new com.itextpdf.text.Font(bf, 16);
            Paragraph paragraphTong = new Paragraph("Tổng tiền:                                   "
                    + "                                                     ", fontTong);
            paragraphTong.add(tongtien + "");
            paragraphTong.setAlignment(Paragraph.ALIGN_LEFT);
            document.add(paragraphTong);
            document.add(paragraphgach);

            // Thanh Toán
            com.itextpdf.text.Font fontTra = new com.itextpdf.text.Font(bf, 16);
            Paragraph paragraphTra = new Paragraph("", fontTra);
            paragraphTra.add("Tiền mặt:                                   "
                    + "                                                     ");
            paragraphTra.add(hdTT.getTienKhachTra() + "\n");
            paragraphTra.add("Tiền thối lại:                                     "
                    + "                                              ");
            paragraphTra.add(tienthoilai + "");
            document.add(paragraphTra);
            document.close();
            writer.close();
            System.out.println("In Thanh Cong");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
