/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package com.g5.form;

import com.g5.entity.SanPhamKM;
import com.g5.entityDAO.KhuyenMaiDAO;
import com.g5.entityDAO.KhuyenMaiSPDAO;
import com.g5.util.Auth;
import java.util.Date;
import java.util.List;
import javax.swing.table.DefaultTableModel;
import com.g5.entity.*;
import com.g5.util.JDBCHelper;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.sql.*;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import static java.lang.Math.round;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.RowFilter;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableRowSorter;

/**
 *
 * @author sora6
 */
public class KhuyenMaiJpanel extends javax.swing.JPanel {

    int currentFilterColumn = 1;
    String MaKM = "0";
    KhuyenMaiDAO kmDAO = new KhuyenMaiDAO();
    KhuyenMaiSPDAO kmspDAO = new KhuyenMaiSPDAO();
    int choose;

    /**
     * Creates new form KhuyenMai
     */
    public KhuyenMaiJpanel() {
        initComponents();
        txtBatdau.setDate(new Date());
        txtKetthuc.setDate(new Date());
        txtManv.setText(Auth.user.getMaNV() + "");
        fillTable();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        txtKM = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        txtManv = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        txtBatdau = new com.toedter.calendar.JDateChooser();
        txtKetthuc = new com.toedter.calendar.JDateChooser();
        jScrollPane3 = new javax.swing.JScrollPane();
        tblSP = new javax.swing.JTable();
        txtChietkhau = new javax.swing.JTextField();
        jScrollPane5 = new javax.swing.JScrollPane();
        tblKM = new javax.swing.JTable();
        btnAddSp = new javax.swing.JButton();
        lbChoosen = new javax.swing.JLabel();
        btnAddd = new javax.swing.JButton();
        btnEdit = new javax.swing.JButton();
        btnDell = new javax.swing.JButton();
        btnClear = new javax.swing.JButton();

        setName("Test"); // NOI18N

        jLabel1.setText("Thông tin chương trình");

        jLabel2.setText("Tên chương trình (có thể ghi dấu):");

        jLabel3.setText("Mã NV:");

        txtKM.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N

        jLabel4.setText("Chiết khấu (%): ");

        txtManv.setEditable(false);
        txtManv.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N

        jLabel5.setText("Ngày bắt đầu");

        jLabel6.setText("Ngày kết thúc");

        txtBatdau.setDateFormatString("yyyy-MM-dd");
        //txtBatdau.setLocale(new Locale("vi", "VN"));
        txtBatdau.setMaximumSize(new java.awt.Dimension(210, 210));

        txtKetthuc.setDateFormatString("yyyy-MM-dd");
        //txtKetthuc.setLocale(new Locale("vi", "VN"));
        txtKetthuc.setMaximumSize(new java.awt.Dimension(210, 210));

        tblSP.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "STT", "Mã Sp", "Tên Sp", "Giảm", "Giá KM"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblSP.setRowHeight(30);
        jScrollPane3.setViewportView(tblSP);
        if (tblSP.getColumnModel().getColumnCount() > 0) {
            tblSP.getColumnModel().getColumn(0).setResizable(false);
            tblSP.getColumnModel().getColumn(1).setResizable(false);
            tblSP.getColumnModel().getColumn(2).setResizable(false);
            tblSP.getColumnModel().getColumn(3).setResizable(false);
            tblSP.getColumnModel().getColumn(4).setResizable(false);
        }

        txtChietkhau.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N

        tblKM.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "STT", "Mã KM", "Tên khuyến mãi", "Ngày bắt đầu", "Ngày kết thúc", "Mã NV", "Chiết khấu"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblKM.setRowHeight(50);
        tblKM.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblKMMouseClicked(evt);
            }
        });
        jScrollPane5.setViewportView(tblKM);
        if (tblKM.getColumnModel().getColumnCount() > 0) {
            tblKM.getColumnModel().getColumn(0).setMaxWidth(50);
            tblKM.getColumnModel().getColumn(1).setMaxWidth(50);
            tblKM.getColumnModel().getColumn(2).setResizable(false);
            tblKM.getColumnModel().getColumn(3).setResizable(false);
            tblKM.getColumnModel().getColumn(4).setResizable(false);
            tblKM.getColumnModel().getColumn(5).setMaxWidth(50);
            tblKM.getColumnModel().getColumn(6).setResizable(false);
        }

        btnAddSp.setText("Thêm sản phẩm");
        btnAddSp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddSpActionPerformed(evt);
            }
        });

        lbChoosen.setText("Mã KM : ");

        btnAddd.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/g5/logos/Plus.png"))); // NOI18N
        btnAddd.setPreferredSize(new java.awt.Dimension(50, 50));
        btnAddd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAdddActionPerformed(evt);
            }
        });

        btnEdit.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/g5/logos/Edit.png"))); // NOI18N
        btnEdit.setPreferredSize(new java.awt.Dimension(50, 50));
        btnEdit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditActionPerformed(evt);
            }
        });

        btnDell.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/g5/logos/Remove.png"))); // NOI18N
        btnDell.setPreferredSize(new java.awt.Dimension(50, 50));
        btnDell.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDellActionPerformed(evt);
            }
        });

        btnClear.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/g5/logos/Broom.png"))); // NOI18N
        btnClear.setPreferredSize(new java.awt.Dimension(50, 50));
        btnClear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnClearActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(45, 45, 45)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jScrollPane5, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel3)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtManv, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(176, 176, 176))
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(jLabel2)
                                .addGroup(layout.createSequentialGroup()
                                    .addComponent(jLabel4)
                                    .addGap(8, 8, 8)
                                    .addComponent(txtChietkhau))
                                .addComponent(txtKM, javax.swing.GroupLayout.PREFERRED_SIZE, 310, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 102, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(btnAddd, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(btnEdit, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(btnDell, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(btnClear, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel6)
                                    .addComponent(jLabel5))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(txtKetthuc, javax.swing.GroupLayout.DEFAULT_SIZE, 162, Short.MAX_VALUE)
                                    .addComponent(txtBatdau, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGap(6, 6, 6)))))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(lbChoosen)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnAddSp))
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 367, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(22, 22, 22))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addGroup(layout.createSequentialGroup()
                            .addContainerGap()
                            .addComponent(jLabel1)
                            .addGap(24, 24, 24)
                            .addComponent(jLabel2))
                        .addGroup(layout.createSequentialGroup()
                            .addGap(78, 78, 78)
                            .addComponent(txtKM, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(4, 4, 4)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel4)
                                .addComponent(txtChietkhau, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel5)
                            .addComponent(txtBatdau, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtKetthuc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel6))
                        .addGap(20, 20, 20)))
                .addGap(24, 24, 24)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel3)
                        .addComponent(txtManv, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(btnDell, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnClear, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnEdit, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnAddd, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane5, javax.swing.GroupLayout.DEFAULT_SIZE, 598, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 268, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lbChoosen)
                            .addComponent(btnAddSp))
                        .addGap(0, 0, Short.MAX_VALUE))))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void tblKMMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblKMMouseClicked
        // TODO add your handling code here:
        choose = tblKM.getSelectedRow();
        lbChoosen.setText("Mã KM : " + tblKM.getValueAt(choose, 1));
        fillform();
        fillTableSp((int) tblKM.getValueAt(choose, 1));
        MaKM = tblKM.getValueAt(choose, 1) + "";
        btnAddd.setEnabled(false);
        btnDell.setEnabled(true);
        btnEdit.setEnabled(true);
        btnAddSp.setEnabled(true);
    }//GEN-LAST:event_tblKMMouseClicked

    private void btnAddSpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddSpActionPerformed
        currentFilterColumn = 1;

        Frame frame = null;
        JDialog dialog = new JDialog(frame, "Thêm sản phẩm", true);

        dialog.setSize(800, 500);
        dialog.setLocationRelativeTo(frame);

        String[] columnNames = {"    ", "Mã SP", "Tên SP", "Giá"};
        Object[][] data = getSanPhamData(tblKM.getValueAt(choose, 1) + "");

        DefaultTableModel model = new DefaultTableModel(data, columnNames) {
            @Override
            public Class<?> getColumnClass(int column) {
                switch (column) {
                    case 0:
                        return Boolean.class;
                    default:
                        return String.class;
                }
            }
        };
        JTable table = new JTable(model);
        table.setPreferredScrollableViewportSize(new Dimension(600, 100));
        table.getColumnModel().getColumn(0).setPreferredWidth(50);
        table.getColumnModel().getColumn(1).setPreferredWidth(100);
        table.getColumnModel().getColumn(2).setPreferredWidth(350);

        table.getColumnModel().getColumn(0).setCellEditor(new DefaultCellEditor(new JCheckBox()));
        JScrollPane scrollPane = new JScrollPane(table);

        JButton updateButton = new JButton("Cập nhật");

        boolean[] initialState = new boolean[table.getRowCount()];
        for (int i = 0; i < table.getRowCount(); i++) {
            initialState[i] = (Boolean) table.getValueAt(i, 0);
        }

        JTextField searchField = new JTextField();
        updateButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                searchField.setText("");
                // Duyệt qua tất cả các hàng trong bảng
                for (int i = 0; i < table.getRowCount(); i++) {
                    int modelIndex = table.convertRowIndexToModel(i);
                    Boolean isChecked = (Boolean) table.getModel().getValueAt(modelIndex, 0);
                    String maSP = (String) table.getModel().getValueAt(modelIndex, 1);

                    if (isChecked != initialState[modelIndex]) {
                        updateSanPham(maSP, isChecked);
                    }
                }
                fillTableSp((int) tblKM.getValueAt(choose, 1));
                JOptionPane.showMessageDialog(null, "Cập nhật thành công!!");
                dialog.dispose();
            }
        });
        searchField.setPreferredSize(new Dimension(400, 35));
        searchField.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        searchPanel.add(searchField);

        ButtonGroup buttonGroup = new ButtonGroup();

        JRadioButton maSPButton = new JRadioButton("Theo mã SP");
        maSPButton.setSelected(true);
        buttonGroup.add(maSPButton);

        JRadioButton tenSPButton = new JRadioButton("Theo tên SP");
        buttonGroup.add(tenSPButton);
        maSPButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                currentFilterColumn = 1;
                searchField.setText("");
            }
        });

        tenSPButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                currentFilterColumn = 2;
                searchField.setText("");
            }
        });

        searchField.getDocument().addDocumentListener(new DocumentListener() {
            public void changedUpdate(DocumentEvent e) {
                filter();
            }

            public void removeUpdate(DocumentEvent e) {
                filter();
            }

            public void insertUpdate(DocumentEvent e) {
                filter();
            }

            public void filter() {
                String filterText = searchField.getText().toLowerCase();
                TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>((DefaultTableModel) table.getModel());
                table.setRowSorter(sorter);
                sorter.setRowFilter(new RowFilter<DefaultTableModel, Integer>() {
                    @Override
                    public boolean include(RowFilter.Entry<? extends DefaultTableModel, ? extends Integer> entry) {

                        String cellValue = entry.getStringValue(currentFilterColumn).toLowerCase();
                        return cellValue.contains(filterText);
                    }
                });
            }
        });

        JPanel radioPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        radioPanel.add(maSPButton);
        radioPanel.add(tenSPButton);

        JLabel searchLabel = new JLabel("Tìm kiếm");

        JPanel searchFieldPanel = new JPanel();
        searchFieldPanel.setLayout(new BoxLayout(searchFieldPanel, BoxLayout.X_AXIS));
        searchFieldPanel.add(searchField);

        JPanel searchAndRadioPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        searchAndRadioPanel.add(searchLabel);
        searchAndRadioPanel.add(searchFieldPanel);
        searchAndRadioPanel.add(radioPanel);

        JPanel northPanel = new JPanel(new BorderLayout());
        northPanel.add(searchAndRadioPanel, BorderLayout.NORTH);
        Font font = new Font("Tahoma", Font.PLAIN, 14);
        dialog.setFont(font);
        dialog.add(northPanel, BorderLayout.NORTH);
        dialog.add(updateButton, BorderLayout.SOUTH);
        dialog.add(scrollPane);
        setFontForAllComponents(dialog, new Font("Tahoma", Font.PLAIN, 14));
        Font headerFont = new Font("Tahoma", Font.PLAIN, 16);
        JTableHeader header = table.getTableHeader();
        header.setFont(headerFont);

        dialog.setVisible(true);
    }//GEN-LAST:event_btnAddSpActionPerformed

    private void btnAdddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAdddActionPerformed
        // TODO add your handling code here:
        try {
            if (!validateCheck(txtKM.getText(), txtChietkhau.getText())) {
                return;
            }
            if (!isValidDate(txtBatdau.getDate() + "", txtKetthuc.getDate() + "")) {
                JOptionPane.showMessageDialog(this, "Thời gian không hợp lệ!");
                return;
            }
            insertKM(getForm());
            JOptionPane.showMessageDialog(this, "Them thanh cong!!!");
            fillTable();
            clearForm();
            btnDell.setEnabled(false);
            btnEdit.setEnabled(false);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }//GEN-LAST:event_btnAdddActionPerformed

    private void btnEditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditActionPerformed
        // TODO add your handling code here:
        try {
            if (!validateCheck(txtKM.getText(), txtChietkhau.getText())) {
                return;
            }
            if (!isValidDate(txtBatdau.getDate() + "", txtKetthuc.getDate() + "")) {
                JOptionPane.showMessageDialog(this, "Thời gian không hợp lệ!");
                return;
            }
            updateKM(getForm((int) tblKM.getValueAt(choose, 1)));
            JOptionPane.showMessageDialog(this, "Sua thanh cong!!!");
            fillTable();
            clearForm();
            btnDell.setEnabled(false);
            btnEdit.setEnabled(false);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }//GEN-LAST:event_btnEditActionPerformed

    private void btnDellActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDellActionPerformed
        // TODO add your handling code here:
        try {

            deleteKM((Integer) tblKM.getValueAt(choose, 1));
            JOptionPane.showMessageDialog(this, "Xoa thanh cong khuyen mai so " + tblKM.getValueAt(choose, 0));
            fillTable();
            clearForm();
            btnDell.setEnabled(false);
            btnEdit.setEnabled(false);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }//GEN-LAST:event_btnDellActionPerformed

    private void btnClearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnClearActionPerformed
        // TODO add your handling code here:
        clearForm();
    }//GEN-LAST:event_btnClearActionPerformed

    public Object[][] getSanPhamData(String maKM) {
        ArrayList<Object[]> data = new ArrayList<Object[]>();
        try {
            Connection conn = JDBCHelper.openConnection();
            String sql = "SELECT  SanPham.MaSP, SanPham.TenSP, MAX(CASE WHEN MaKM = ? THEN 1 ELSE 0 END) AS IsInKM , GSP.Gia FROM SanPham LEFT JOIN KhuyenMaiChiTiet ON SanPham.MaSP = KhuyenMaiChiTiet.MaSP \n"
                    + "JOIN GiaSanPham GSP on GSP.MaSP = SanPham.MaSP where GSP.Size like '%M%' \n" + "GROUP BY SanPham.MaSP, SanPham.TenSP,GSP.Gia ORDER BY sanpham.masp ;";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, maKM);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Object[] row = new Object[4];
                JCheckBox cbbox = new JCheckBox();
                row[0] = rs.getBoolean("IsInKM");
                row[1] = rs.getString("MaSP");
                row[2] = rs.getString("TenSP");
                row[3] = rs.getFloat("Gia");
                data.add(row);
            }

            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        Object[][] array = new Object[data.size()][];
        for (int i = 0; i < data.size(); i++) {
            array[i] = data.get(i);
        }

        return array;
    }

    public void updateSanPham(String maSP, boolean isInKM) {
        try {

            Connection conn = JDBCHelper.openConnection();
            if (isInKM) {

                String sql = "IF EXISTS (SELECT * FROM KhuyenMaiChiTiet WHERE MaSP = ? AND MaKM = ?) UPDATE KhuyenMaiChiTiet SET MaSP = ? WHERE MaKM = ? ELSE INSERT INTO KhuyenMaiChiTiet (MaSP, MaKM) VALUES (?, ?)";
                PreparedStatement stmt = conn.prepareStatement(sql);
                stmt.setString(1, maSP);
                stmt.setString(2, MaKM);
                stmt.setString(3, maSP);
                stmt.setString(4, MaKM);
                stmt.setString(5, maSP);
                stmt.setString(6, MaKM);
                stmt.executeUpdate();
            } else {

                String sql = "DELETE FROM KhuyenMaiChiTiet WHERE MaSP = ? AND MaKM = ?";
                PreparedStatement stmt = conn.prepareStatement(sql);
                stmt.setString(1, maSP);
                stmt.setString(2, MaKM);
                stmt.executeUpdate();
            }

            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    void clearForm() {
        txtKM.setText("");
        txtChietkhau.setText("");
        txtBatdau.setDate(new Date());
        txtKetthuc.setDate(new Date());
        fillTableSp(0);
        lbChoosen.setText("Mã KM : ");
        txtManv.setText(Auth.user.getMaNV() + "");
        btnDell.setEnabled(false);
        btnEdit.setEnabled(false);
        btnAddSp.setEnabled(false);
        btnAddd.setEnabled(true);
    }

    void deleteKM(int id) {
        kmDAO.deteleByID(id);
    }

    KhuyenMai getForm() {
        KhuyenMai km = new KhuyenMai();
        km.setTenKM(txtKM.getText());
        km.setTGBatDau(txtBatdau.getDate());
        km.setTGKetThuc(txtKetthuc.getDate());
        km.setMaNV(Auth.user.getMaNV());
        float PTkm = Math.round(Float.parseFloat(txtChietkhau.getText()) * 100) / 100.0f;
        km.setPhanTramKM(PTkm);
        return km;
    }

    KhuyenMai getForm(int Ma) {
        KhuyenMai km = new KhuyenMai();
        km.setTenKM(txtKM.getText());
        km.setTGBatDau(txtBatdau.getDate());
        km.setTGKetThuc(txtKetthuc.getDate());
        km.setMaNV(Auth.user.getMaNV());
        float PTkm = Math.round(Float.parseFloat(txtChietkhau.getText()) * 100) / 100.0f;
        km.setPhanTramKM(PTkm);
        km.setMaKM(Ma);
        return km;
    }

    void insertKM(KhuyenMai km) {
        kmDAO.create(km);
    }

    void updateKM(KhuyenMai km) {
        kmDAO.update(km);
    }

    void fillTable() {
        btnDell.setEnabled(false);
        btnEdit.setEnabled(false);
        btnAddSp.setEnabled(false);
        DefaultTableModel model = (DefaultTableModel) tblKM.getModel();
        model.setRowCount(0);
        List<KhuyenMai> list = kmDAO.getAll();
        int i = 0;
        for (KhuyenMai km : list) {
            i += 1;
            model.addRow(new Object[]{i, km.getMaKM(), km.getTenKM(), km.getTGBatDau(), km.getTGKetThuc(), km.getMaNV(), km.getPhanTramKM()});
        }
    }

    void fillTableSp(int id) {
        DefaultTableModel modelSp = (DefaultTableModel) tblSP.getModel();
        modelSp.setRowCount(0);
        List<SanPhamKM> list = (List<SanPhamKM>) kmspDAO.getByCondition("SELECT * FROM KhuyenMaiChiTiet KMCT JOIN SanPham SP ON KMCT.MaSP = SP.MaSP JOIN GiaSanPham GSP ON SP.MaSP = GSP.MaSP JOIN KhuyenMai KM ON KM.MaKM = KMCT.MaKM WHERE KMCT.MaKM = ? AND GSP.Size like '%M%' ;", id);
        int i = 0;
        for (SanPhamKM sp : list) {
            i += 1;
            modelSp.addRow(new Object[]{i, sp.getMaSP(), sp.getTenSP(), sp.getGia() - (sp.getGia() * (1 - sp.getChietkhau())), sp.getGia() * (1 - sp.getChietkhau())});
        }

    }

    public void setFontForAllComponents(Container container, Font font) {
        for (Component component : container.getComponents()) {
            component.setFont(font);
            if (component instanceof Container) {
                setFontForAllComponents((Container) component, font);
            }
        }
    }

    void fillform() {
        txtKM.setText((String) tblKM.getValueAt(choose, 2));
        txtBatdau.setDate((Date) tblKM.getValueAt(choose, 3));
        txtKetthuc.setDate((Date) tblKM.getValueAt(choose, 4));
        txtManv.setText(tblKM.getValueAt(choose, 5) + "");
        txtChietkhau.setText(tblKM.getValueAt(choose, 6) + "");

    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAddSp;
    private javax.swing.JButton btnAddd;
    private javax.swing.JButton btnClear;
    private javax.swing.JButton btnDell;
    private javax.swing.JButton btnEdit;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JLabel lbChoosen;
    private javax.swing.JTable tblKM;
    private javax.swing.JTable tblSP;
    private com.toedter.calendar.JDateChooser txtBatdau;
    private javax.swing.JTextField txtChietkhau;
    private javax.swing.JTextField txtKM;
    private com.toedter.calendar.JDateChooser txtKetthuc;
    private javax.swing.JTextField txtManv;
    // End of variables declaration//GEN-END:variables

    private boolean validateCheck(String Ma, String Phantram) {
        if (Ma.trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Bạn chưa nhập tên khuyến mã");
            return false;
        }
        if (Phantram.trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Bạn chưa nhập phần trăm chiết khấu");
            return false;
        }
        try {
            Float.valueOf(Phantram);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Chiết khấu phải là số");
        }

        return true;
    }

    public boolean isValidDate(String startDateStr, String endDateStr) {
        try {
            SimpleDateFormat inputFormat = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy");
            SimpleDateFormat outputFormat = new SimpleDateFormat("yyyy/MM/dd");

            Date startDate = inputFormat.parse(startDateStr);
            Date endDate = inputFormat.parse(endDateStr);

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
}
