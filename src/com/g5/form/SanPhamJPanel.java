/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package com.g5.form;

import com.g5.entity.GiaSP;
import com.g5.entity.NhanVien;
import com.g5.entity.SanPham;
import com.g5.entityDAO.NhanVienDAOImpl;
import com.g5.entityDAO.SanPhamChiTietDAO;
import com.g5.entityDAO.SanPhamDao;
import com.g5.util.Auth;
import com.g5.util.JDBCHelper;
import com.g5.util.XImage;
import com.g5.util.TextMes;
import com.g5.util.Validate;
import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Properties;
import java.util.Set;
import javax.imageio.ImageIO;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author KIM NGAN
 */
public class SanPhamJPanel extends javax.swing.JPanel {

    public static final Properties props = JDBCHelper.loadDbProperties();
    int row = -1;
    SanPhamDao dao = new SanPhamDao();
    SanPhamChiTietDAO daoCT = new SanPhamChiTietDAO();
    NhanVienDAOImpl daoNV = new NhanVienDAOImpl();
    NhanVien nv = daoNV.getByID(Auth.user.getMaNV());
    final int w = 198;
    final int h = 248;
    int count;

    /**
     * Creates new form SanPhamJPanel
     */
    public SanPhamJPanel() {
        initComponents();
        setOpaque(false);
        setSize(1100, 800);
        fillTableDSSP();
        cboLoaiSP();
        //   first();
        fillTableSPCT();
        fillForm();
        init();
        //    firstCT();
        tblSanPham.getTableHeader().setFont(new Font("Tohoma", 1, 16));
        tblChiTiet.getTableHeader().setFont(new Font("Tohoma", 1, 16));
        tblSanPham.getTableHeader().setBackground(new Color(32, 136, 203));
        tblChiTiet.getTableHeader().setForeground(new Color(255, 255, 255));
        btnSua.setEnabled(false);
        btnXoa.setEnabled(false);
        btnSua1.setEnabled(false);
        btnXoa1.setEnabled(false);
        btnThem1.setEnabled(false);
    }

    void init() {
        txtTimKiem.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                fillSearch();

            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                fillSearch();

            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                //  timKiem();  //   throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
            }
        });
    }

    void fillForm() {
        dao.resetIdentity(dao.getMaxMaSP());
        txtMaNV.setText(Auth.user.getMaNV() + "");
        txtMaSP.setText((dao.getMaxMaSP() + 1) + "");
        btnThem.setEnabled(true);
    }

    void getNVID() {
        NhanVien nv = daoNV.getByID(Auth.user.getMaNV());
    }

    void fillTableDSSP() {
        DefaultTableModel model = (DefaultTableModel) tblSanPham.getModel();
        model.setRowCount(0);
        try {
            List<SanPham> list = dao.getAll();
            int i = 0;
            for (SanPham sp : list) {
                String gianguyenlieu = Validate.chuyenGiaVietNam(sp.getGiaNguyenLieu());
                Object row[] = {
                    ++i,
                    sp.getMaSP(),
                    sp.getTenSP(),
                    sp.getSoLuong(),
                    sp.getMaNV(),
                    sp.getMoTa(),
                    sp.getHinh(),
                    sp.getLoaiSP(),
                    gianguyenlieu
                //   sp.getGiaNguyenLieu()
                };
                model.addRow(row);
            }
            this.row = 1;
        } catch (Exception e) {
            System.out.println("Loi du lieu");
        }
    }

    void spctReset() {
        DefaultTableModel model = (DefaultTableModel) tblChiTiet.getModel();
        if (txtMaSP1.getText().trim().isEmpty()) {
            return;
        }
        String tenSP = "";
        try {
            tenSP = (String) tblChiTiet.getValueAt(0, 1);
        } catch (Exception e) {
            return;
        }
        if (tenSP.trim().isEmpty()) {
            return;
        }

        model.setRowCount(0);
        try {
            List<GiaSP> list = daoCT.selectByID(Integer.parseInt(txtMaSP1.getText()));
            int i = 0;

            for (GiaSP sp : list) {
                String giasanpham = Validate.chuyenGiaVietNam(sp.getGia());

                Object row[] = {
                    //                    sp.getMaGSP(),
                    //   sp.getMaSP(),
                    ++i,
                    tenSP,
                    sp.getSize(),
                    giasanpham
                };
                model.addRow(row);
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Loi du lieu");
        }
    }

    void fillTableSPCT() {
        DefaultTableModel model = (DefaultTableModel) tblChiTiet.getModel();
        if (txtMaSP.getText().trim().isEmpty()) {
            return;
        }
        model.setRowCount(0);
        String tenSP = "";
        try {
            tenSP = txtTenSP.getText().trim();
        } catch (Exception e) {
            return;
        }

        try {
            List<GiaSP> list = daoCT.selectByID(Integer.parseInt(txtMaSP.getText()));
            int i = 0;
            for (GiaSP sp : list) {
                String giasanpham = Validate.chuyenGiaVietNam(sp.getGia());
                Object row[] = {
                    //                    sp.getMaGSP(),
                    //   sp.getMaSP(),
                    ++i,
                    tenSP,
                    sp.getSize(),
                    giasanpham
                };
                model.addRow(row);
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Loi du lieu");
        }

    }

    void first() {
        this.row = 0;
        tblSanPham.setRowSelectionInterval(row, row);
        this.editSP();
    }

    void prev() {
        if (this.row > 0) {
            this.row--;
            tblSanPham.setRowSelectionInterval(row, row);
            this.editSP();
        }
    }

    void next() {
        if (this.row < tblSanPham.getRowCount() - 1) {
            this.row++;
            tblSanPham.setRowSelectionInterval(row, row);
            this.editSP();
        }
    }

    void last() {
        this.row = tblSanPham.getRowCount() - 1;
        tblSanPham.setRowSelectionInterval(row, row);
        this.editSP();
    }

    void editSP() {
        Integer maSP = (Integer) tblSanPham.getValueAt(this.row, 1);
        SanPham sp = dao.getByID(maSP);
        txtMaSP1.setText(sp.getMaSP() + "");
        this.setFormSP(sp);
        this.updateStatusSP();
    }

    void setFormSP(SanPham sp) {
        GiaSP gsp = new GiaSP();
        txtMaSP.setText(String.valueOf(sp.getMaSP()));
        txtTenSP.setText(sp.getTenSP());
        txtMaNV.setText(String.valueOf(sp.getMaNV()));
        txtSoLuong.setText(String.valueOf(sp.getSoLuong()));
        cboLoaiSP.setSelectedItem(sp.getLoaiSP());
        txtMoTa.setText(sp.getMoTa());
        txtHinh.setText(sp.getHinh());
        lblHinh.setToolTipText(sp.getHinh());
        if (sp.getHinh() != null) {
            BufferedImage originalImage = null;
            boolean loadimg = false;
            try {
                originalImage = ImageIO.read(new File("src//com//g5//image//" + sp.getHinh().trim()));
                loadimg = true;
                txtHinh.setText(sp.getHinh());
            } catch (Exception e) {
                loadimg = false;
                //   e.printStackTrace();
            }
            if (!loadimg) {
                try {
                    lblHinh.setIcon(null);
                    originalImage = ImageIO.read(new File("src//com//g5//image//" + "null.png"));
                } catch (Exception e) {
                    //   e.printStackTrace();
                }

            }
            ImageIcon icon = new ImageIcon(originalImage);
            Image image = icon.getImage();
            Image scaledImage = image.getScaledInstance(w, h, Image.SCALE_DEFAULT);
            ImageIcon scaledIcon = new ImageIcon(scaledImage);
            lblHinh.setIcon(scaledIcon);
        }
        String formattedValue = String.format("%.0f", sp.getGiaNguyenLieu());

        //   System.out.println("Formatted Value: " + formattedValue);
        txtGiaNL.setText(formattedValue);
    }

    void firstCT() {
        this.row = 0;
        tblChiTiet.setRowSelectionInterval(row, row);
        this.editCT();
    }

    void prevCT() {
        if (this.row > 0) {
            this.row--;
            tblChiTiet.setRowSelectionInterval(row, row);
            this.editCT();
        }
    }

    void nextCT() {
        if (this.row < tblChiTiet.getRowCount() - 1) {
            this.row++;
            tblChiTiet.setRowSelectionInterval(row, row);
            this.editCT();
        }
    }

    void lastCT() {
        this.row = tblChiTiet.getRowCount() - 1;
        tblChiTiet.setRowSelectionInterval(row, row);
        this.editCT();
    }

    void editCT() {
        if (txtMaSP1.getText().trim().isEmpty()) {
            return;
        }
        btnSua1.setEnabled(true);
        btnXoa1.setEnabled(true);
        btnThem1.setEnabled(false);
        // String size = (String) tblChiTiet.getValueAt(this.row, 2);////////////////////
        GiaSP sp = daoCT.getByID(Integer.parseInt(txtMaSP1.getText().trim()));
        this.setFormCT(sp);
    }

    void setFormCT(GiaSP gsp) {
        List<String> list = null;
        try {
            list = dao.getSize2(gsp.getMaSP());
        } catch (Exception e) {
            return;
        }

        if (list == null) {
            return;
        }
        txtSize.removeAllItems();
        for (String string : list) {
            txtSize.addItem(string.toString().trim());
        }
        if (list.size() > 0) {
            if (index != -1) {
                txtSize.setSelectedIndex(index);
                index = -1;
            } else {
                txtSize.setSelectedIndex(0);
            }
            String formattedValue = String.format("%.0f", dao.getGiaByMaSPAndSize(Integer.parseInt(txtMaSP1.getText().trim()), txtSize.getSelectedItem().toString().trim()));

            //   System.out.println("Formatted Value: " + formattedValue);
            txtGia1.setText(formattedValue);

        }
    }

    void fillSearch() {
        List<SanPham> list;
        if (rdoTKma.isSelected()) {
            list = dao.selectByKeyword(txtTimKiem.getText().trim());
        } else {
            list = dao.selectByName(txtTimKiem.getText().trim());
        }

        if (list.size() <= 0) {
            return;
        }

        DefaultTableModel model = (DefaultTableModel) tblSanPham.getModel();
        model.setRowCount(0);
        int i = 0;
        for (SanPham sp : list) {
            Object row[] = {
                ++i,
                sp.getMaSP(),
                sp.getTenSP(),
                sp.getSoLuong(),
                sp.getMaNV(),
                sp.getMoTa(),
                sp.getHinh(),
                sp.getLoaiSP(),
                sp.getGiaNguyenLieu()
            };
            model.addRow(row);

        }
    }

    void updateStatusSP() {
        boolean edit = (this.row >= 0);
//        boolean first = (this.row == 0);
//        boolean last = (this.row == tblSanPham.getRowCount() - 1);
        txtMaSP.setEditable(!edit);
        txtMaSP1.setEditable(false);
        btnThem1.setEnabled(false);
        txtHinh.setEditable(false);
        txtMaNV.setEditable(false);
        txtMaNV.setText(nv.getMaNV() + "");
        tblSanPham.setDefaultEditor(Object.class, null);
        tblChiTiet.setDefaultEditor(Object.class, null);
        btnThem.setEnabled(!edit);
        btnSua.setEnabled(edit);
        btnXoa.setEnabled(edit);
//        btnFirst.setEnabled(edit && !first);
//        btnPrev.setEnabled(edit && !first);
//        btnNext.setEnabled(edit && !last);
//        btnLast.setEnabled(edit && !last);
    }

    void resetIdentity() {
        dao.resetIdentity(dao.getMaxMaSP());
        daoCT.resetIdentity(daoCT.getMaxGiaSP());
    }

    void find() {
        dao.getByID(Integer.valueOf(txtMaSP.getText()));
    }

    public void selectImage() {
        JFileChooser fileChooser = new JFileChooser("src\\com\\g5\\image");
        if (fileChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            if (XImage.save(file)) {
                try {
                    BufferedImage originalImage = ImageIO.read(new File(file.toString()));
                    ImageIcon icon = new ImageIcon(originalImage);
                    Image image = icon.getImage();
                    Image scaledImage = image.getScaledInstance(w, h, Image.SCALE_DEFAULT);
                    ImageIcon scaledIcon = new ImageIcon(scaledImage);
                    lblHinh.setToolTipText(file.getName());
                    lblHinh.setIcon(scaledIcon);
                    txtHinh.setText(file.getName());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    SanPham getFormSP() {
        SanPham sp = new SanPham();
        sp.setTenSP(txtTenSP.getText());
        sp.setSoLuong(Integer.parseInt(txtSoLuong.getText()));
        sp.setMaNV(Integer.parseInt(txtMaNV.getText()));
        sp.setMoTa(txtMoTa.getText());
        sp.setHinh(txtHinh.getText());
        sp.setLoaiSP((String) cboLoaiSP.getSelectedItem());
        sp.setGiaNguyenLieu((Float.parseFloat(txtGiaNL.getText())));
        return sp;
    }

    GiaSP getFormCT() {
        GiaSP sp = new GiaSP();

        sp.setMaSP(Integer.parseInt(txtMaSP1.getText()));
        sp.setSize(txtSize.getSelectedItem().toString().trim());
        sp.setGia(Float.parseFloat(txtGia1.getText()));
        return sp;
    }

    void clearSP() {
        txtMaSP.setText((dao.getMaxMaSP() + 1) + "");
        txtTenSP.setText("");
        txtHinh.setText("Chưa chọn ảnh");

        lblHinh.setIcon(null);
        lblHinh.removeAll();
        txtGiaNL.setText("");
        cboLoaiSP.setSelectedItem(0);
        txtMoTa.setText("");
        txtSoLuong.setText("");
        txtGia.setText("");
    }

    void clearCT() {
        DefaultTableModel model = (DefaultTableModel) tblChiTiet.getModel();
        model.setRowCount(0);
        txtMaSP1.setText(txtMaSP.getText());
        List<String> list = dao.getSize2(Integer.parseInt(txtMaSP1.getText().trim()));
        txtSize.removeAllItems();
        for (String string : list) {
            txtSize.addItem(string.toString().trim());
        }
        txtGia1.setText("0");
        if (list.size() > 0) {

            txtSize.setSelectedIndex(0);
            String formattedValue = String.format("%.0f", dao.getGiaByMaSPAndSize(Integer.parseInt(txtMaSP1.getText().trim()), txtSize.getSelectedItem().toString().trim()));
            txtGia1.setText(formattedValue);
        }

        fillTableSPCT();

    }

    GiaSP FirstPrice() {
        GiaSP fp = new GiaSP();
        fp.setMaSP(Integer.parseInt(txtMaSP.getText().trim()));
        fp.setSize("Size M");
        fp.setGia(Float.parseFloat(txtGia.getText().trim()));
        return fp;
    }

    void insertSP() {
        kiemLoi();
        resetIdentity();
        try {
            if (daoCT.tensp(txtTenSP.getText().trim()) == 1) {
                TextMes.Alert(this, "Đã có sản phẩm này");
                return;
            }
            SanPham sp = this.getFormSP();
            dao.create(sp);
            fillTableDSSP();
            if (!txtGia.getText().trim().isEmpty()) {
                GiaSP fp = this.FirstPrice();
                daoCT.create(fp);
            }

            TextMes.Alert(null, "Thêm sản phẩm thành công");
            // fillTableSPCT();
            resetSPCT();
            updateStatusSP();
            clearSP();
        } catch (Exception e) {
            e.printStackTrace();
            TextMes.Alert(this, "Lỗi thêm sản phẩm");
        }
    }

    void insertCT() {
        kiemLoi();
        try {
            GiaSP sp = this.getFormCT();
            List<Object[]> list = daoCT.check(sp.getSize().trim(), sp.getMaSP());

            boolean containsOne = false;
            for (Object[] row : list) {
                for (Object element : row) {
                    if (element instanceof Integer && (Integer) element == 1) {
                        containsOne = true;
                        break;
                    }
                }
            }
            if (containsOne) {
                TextMes.Alert(null, "Vui lòng chọn size khác");

                return;
            } else {

                daoCT.create(sp);
                TextMes.Alert(this, "Thêm giá thành công");
            }

            spctReset();
            //  fillTableSPCT();
            updateStatusSP();
            clearCT();
        } catch (Exception e) {
            e.printStackTrace();
            //  TextMes.Alert(this, "Lỗi giá sản phẩm");
        }
    }

    void removeCbo(JComboBox cbo) {
        Set<Object> uniqueItems = new HashSet<>();
        Object selected = cbo.getSelectedItem();

        int itemCount = cbo.getItemCount();
        for (int i = 0; i < itemCount; i++) {
            Object item = cbo.getItemAt(i);
            if (!uniqueItems.contains(item)) {
                uniqueItems.add(item);
            } else {
                cbo.removeItem(item);
            }
        }

        cbo.setSelectedItem(selected);
    }

    void kiemLoi() {
        if (txtTenSP.getText().trim() == null) {
            TextMes.Alert(this, "Chưa nhập tên SP");
            return;
        } else if (txtGiaNL.getText().trim() == null) {
            TextMes.Alert(this, "Chưa nhập so luong");
            return;
        } else if (txtMoTa.getText().trim() == null) {
            TextMes.Alert(this, "Chưa nhập mô tả");
            return;
        }
    }

    void resetSPCT() {
        txtMaSP1.setText("");
        txtGia1.setText("");
        txtSize.removeAllItems();

        DefaultTableModel model = (DefaultTableModel) tblChiTiet.getModel();
        model.setRowCount(0);
    }

    void deleteSP() {
        try {
            int masp = Integer.parseInt(txtMaSP.getText().trim());
            daoCT.deteleByMaSP(masp);
            dao.deteleByID(masp);
            TextMes.Alert(null, "Xóa sản phẩm thành công");
            fillTableDSSP();
            resetSPCT();
            clearSP();
        } catch (Exception e) {
            e.printStackTrace();
            TextMes.Alert(this, "Lỗi xóa SP");
        }
    }

    void deleteCT() {
        try {
            if (daoCT.getSLGiaSP(Integer.parseInt(txtMaSP1.getText().trim())) <= 1) {
                TextMes.Alert(this, "Phải có ít nhất 1 giá");
                return;
            }
            daoCT.deteleByID(Integer.parseInt(txtMaSP1.getText().trim()), String.valueOf(txtSize.getSelectedItem().toString().trim()));
            TextMes.Alert(this, "Xóa giá sản phẩm thành công");
            fillTableSPCT();
            clearCT();
        } catch (Exception e) {
            e.printStackTrace();
            TextMes.Alert(this, "Lỗi xóa giá");
        }
    }

    void updateSP() {
        kiemLoi();
        try {
            SanPham sp = this.getFormSP();
            sp.setMaSP(Integer.parseInt(txtMaSP.getText()));
            dao.update(sp);
            TextMes.Alert(null, "Cập nhật sản phẩm thành công");
            fillTableDSSP();
            //   fillTableSPCT();
        } catch (Exception e) {
            e.printStackTrace();
            TextMes.Alert(this, "Lỗi cập nhật sản phẩm");
        }
    }

    void updateCT() {
        kiemLoi();
        this.row = tblChiTiet.getSelectedRow();
        try {
            GiaSP sp = this.getFormCT();

            daoCT.update(sp);
            //    fillTableSPCT();
            TextMes.Alert(this, "Cập nhật sản phẩm thành công");
            spctReset();
        } catch (Exception e) {
            e.printStackTrace();
            TextMes.Alert(this, "Lỗi cập nhật sản phẩm");
        }
    }

    void cboLoaiSP() {
        DefaultComboBoxModel<String> model = (DefaultComboBoxModel<String>) cboLoaiSP.getModel();
        model.removeAllElements();
        try {
            List<SanPham> list = dao.getAll();
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

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        buttonGroup2 = new javax.swing.ButtonGroup();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblChiTiet = new javax.swing.JTable();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane10 = new javax.swing.JScrollPane();
        tblSanPham = new javax.swing.JTable();
        txtTimKiem = new javax.swing.JTextField();
        rdoTKma = new javax.swing.JRadioButton();
        rdoTKten = new javax.swing.JRadioButton();
        jPanel4 = new javax.swing.JPanel();
        jPanel6 = new javax.swing.JPanel();
        lblHinh = new javax.swing.JLabel();
        txtHinh = new javax.swing.JTextField();
        lblGiaNL = new javax.swing.JLabel();
        lblMaSP = new javax.swing.JLabel();
        txtMaSP = new javax.swing.JTextField();
        lblSoLuong = new javax.swing.JLabel();
        txtGiaNL = new javax.swing.JTextField();
        txtSoLuong = new javax.swing.JTextField();
        txtMaNV = new javax.swing.JTextField();
        lblMaNV = new javax.swing.JLabel();
        lblTenSP = new javax.swing.JLabel();
        txtTenSP = new javax.swing.JTextField();
        lblLoaiSP = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        cboLoaiSP = new javax.swing.JComboBox<>();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtMoTa = new javax.swing.JTextArea();
        lblMoTa = new javax.swing.JLabel();
        lblMoTa1 = new javax.swing.JLabel();
        txtGia = new javax.swing.JTextField();
        jPanel5 = new javax.swing.JPanel();
        btnMoi = new javax.swing.JButton();
        btnXoa = new javax.swing.JButton();
        btnSua = new javax.swing.JButton();
        btnThem = new javax.swing.JButton();
        btnNext = new javax.swing.JButton();
        btnLast = new javax.swing.JButton();
        btnPrev = new javax.swing.JButton();
        btnFirst = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jPanel7 = new javax.swing.JPanel();
        txtGia1 = new javax.swing.JTextField();
        lblGia1 = new javax.swing.JLabel();
        lblSize = new javax.swing.JLabel();
        txtMaSP1 = new javax.swing.JTextField();
        lblTenSP1 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        btnMoi1 = new javax.swing.JButton();
        btnThem1 = new javax.swing.JButton();
        btnXoa1 = new javax.swing.JButton();
        btnSua1 = new javax.swing.JButton();
        txtSize = new javax.swing.JComboBox<>();
        jButton3 = new javax.swing.JButton();

        setPreferredSize(new java.awt.Dimension(1350, 800));

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Giá sản phẩm", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 18))); // NOI18N

        tblChiTiet.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        tblChiTiet.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "STT", "Tên SP", "Size", "Giá"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblChiTiet.setFocusable(false);
        tblChiTiet.setIntercellSpacing(new java.awt.Dimension(0, 0));
        tblChiTiet.setRowHeight(30);
        tblChiTiet.setSelectionBackground(new java.awt.Color(102, 255, 102));
        tblChiTiet.setShowVerticalLines(false);
        tblChiTiet.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseExited(java.awt.event.MouseEvent evt) {
                tblChiTietMouseExited(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                tblChiTietMousePressed(evt);
            }
        });
        jScrollPane2.setViewportView(tblChiTiet);
        if (tblChiTiet.getColumnModel().getColumnCount() > 0) {
            tblChiTiet.getColumnModel().getColumn(0).setPreferredWidth(40);
            tblChiTiet.getColumnModel().getColumn(1).setPreferredWidth(150);
            tblChiTiet.getColumnModel().getColumn(2).setPreferredWidth(50);
        }

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Danh sách sản phẩm", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 18))); // NOI18N

        tblSanPham.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        tblSanPham.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "STT", "Mã SP", "Tên SP", "Số lượng", "Mã NV", "Mô tả", "Hình", "Loại SP", "Giá nhập"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false
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
            public void mousePressed(java.awt.event.MouseEvent evt) {
                tblSanPhamMousePressed(evt);
            }
        });
        jScrollPane10.setViewportView(tblSanPham);
        if (tblSanPham.getColumnModel().getColumnCount() > 0) {
            tblSanPham.getColumnModel().getColumn(0).setPreferredWidth(50);
            tblSanPham.getColumnModel().getColumn(1).setPreferredWidth(50);
            tblSanPham.getColumnModel().getColumn(2).setPreferredWidth(150);
            tblSanPham.getColumnModel().getColumn(3).setPreferredWidth(50);
            tblSanPham.getColumnModel().getColumn(4).setPreferredWidth(50);
            tblSanPham.getColumnModel().getColumn(5).setPreferredWidth(100);
        }

        txtTimKiem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtTimKiemActionPerformed(evt);
            }
        });

        buttonGroup2.add(rdoTKma);
        rdoTKma.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        rdoTKma.setSelected(true);
        rdoTKma.setText("Theo mã");

        buttonGroup2.add(rdoTKten);
        rdoTKten.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        rdoTKten.setText("Theo tên");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane10)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(txtTimKiem, javax.swing.GroupLayout.PREFERRED_SIZE, 343, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(rdoTKma)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(rdoTKten)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(txtTimKiem, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(rdoTKma)
                        .addComponent(rdoTKten)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane10, javax.swing.GroupLayout.DEFAULT_SIZE, 263, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Quản lí sản phẩm", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 18))); // NOI18N

        jPanel6.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel6.setPreferredSize(new java.awt.Dimension(200, 200));

        lblHinh.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblHinhMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lblHinh, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lblHinh, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 248, Short.MAX_VALUE)
        );

        txtHinh.setEditable(false);
        txtHinh.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtHinh.setText("Chưa chọn ảnh");
        txtHinh.setMinimumSize(new java.awt.Dimension(7, 30));
        txtHinh.setPreferredSize(new java.awt.Dimension(78, 30));

        lblGiaNL.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lblGiaNL.setText("Giá nhập");

        lblMaSP.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lblMaSP.setText("Mã sản phẩm");

        txtMaSP.setEditable(false);
        txtMaSP.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtMaSP.setPreferredSize(new java.awt.Dimension(7, 30));

        lblSoLuong.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lblSoLuong.setText("Số lượng");

        txtGiaNL.setPreferredSize(new java.awt.Dimension(7, 30));
        txtGiaNL.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtGiaNLActionPerformed(evt);
            }
        });

        txtSoLuong.setPreferredSize(new java.awt.Dimension(7, 30));

        txtMaNV.setEditable(false);
        txtMaNV.setPreferredSize(new java.awt.Dimension(7, 30));

        lblMaNV.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lblMaNV.setText("Mã NV");

        lblTenSP.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lblTenSP.setText("Tên sản phẩm");

        txtTenSP.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtTenSP.setPreferredSize(new java.awt.Dimension(7, 30));

        lblLoaiSP.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lblLoaiSP.setText("Loại sản phẩm");

        jButton1.setText("+");
        jButton1.setPreferredSize(new java.awt.Dimension(33, 21));
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        cboLoaiSP.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        cboLoaiSP.setPreferredSize(new java.awt.Dimension(29, 30));
        cboLoaiSP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboLoaiSPActionPerformed(evt);
            }
        });

        txtMoTa.setColumns(20);
        txtMoTa.setRows(5);
        jScrollPane1.setViewportView(txtMoTa);

        lblMoTa.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lblMoTa.setText("Mô tả");

        lblMoTa1.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lblMoTa1.setText("Giá");

        txtGia.setEditable(false);
        txtGia.setPreferredSize(new java.awt.Dimension(7, 30));
        txtGia.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtGiaActionPerformed(evt);
            }
        });

        btnMoi.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/g5/logos/Broom.png"))); // NOI18N
        btnMoi.setPreferredSize(new java.awt.Dimension(50, 50));
        btnMoi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMoiActionPerformed(evt);
            }
        });

        btnXoa.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/g5/logos/Remove.png"))); // NOI18N
        btnXoa.setPreferredSize(new java.awt.Dimension(50, 50));
        btnXoa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXoaActionPerformed(evt);
            }
        });

        btnSua.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/g5/logos/Edit.png"))); // NOI18N
        btnSua.setPreferredSize(new java.awt.Dimension(50, 50));
        btnSua.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSuaActionPerformed(evt);
            }
        });

        btnThem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/g5/logos/Plus.png"))); // NOI18N
        btnThem.setPreferredSize(new java.awt.Dimension(50, 50));
        btnThem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThemActionPerformed(evt);
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

        btnPrev.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        btnPrev.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/g5/logos/Back.png"))); // NOI18N
        btnPrev.setPreferredSize(new java.awt.Dimension(40, 40));
        btnPrev.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPrevActionPerformed(evt);
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

        jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/g5/logos/Image.png"))); // NOI18N
        jButton2.setPreferredSize(new java.awt.Dimension(50, 50));
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnFirst, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnThem, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnSua, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnPrev, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(btnXoa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnNext, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(btnMoi, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnLast, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(btnThem, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnSua, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnMoi, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnXoa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnLast, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnNext, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnPrev, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnFirst, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 0, 0))
        );

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtHinh, javax.swing.GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE))
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lblMaNV)
                                    .addComponent(lblMaSP)
                                    .addComponent(lblSoLuong))
                                .addGap(38, 38, 38)
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(txtMaNV, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(txtMaSP, javax.swing.GroupLayout.PREFERRED_SIZE, 174, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(txtSoLuong, javax.swing.GroupLayout.PREFERRED_SIZE, 174, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addComponent(lblGiaNL)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(txtGiaNL, javax.swing.GroupLayout.PREFERRED_SIZE, 174, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblLoaiSP)
                            .addComponent(lblMoTa1)
                            .addComponent(lblMoTa)
                            .addComponent(lblTenSP))
                        .addGap(19, 19, 19)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtTenSP, javax.swing.GroupLayout.PREFERRED_SIZE, 223, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addGroup(jPanel4Layout.createSequentialGroup()
                                    .addComponent(cboLoaiSP, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addComponent(txtGia, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 223, Short.MAX_VALUE))))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(100, 100, 100)
                        .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(30, 30, 30))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(txtHinh, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblMaSP)
                            .addComponent(txtMaSP, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblTenSP)
                            .addComponent(txtTenSP, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(14, 14, 14)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblMaNV)
                            .addComponent(txtMaNV, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblLoaiSP)
                            .addComponent(cboLoaiSP, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(15, 15, 15)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblSoLuong)
                            .addComponent(txtSoLuong, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblMoTa1)
                            .addComponent(txtGia, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(15, 15, 15)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(lblGiaNL)
                                .addComponent(txtGiaNL, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(lblMoTa)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 24, Short.MAX_VALUE)
                        .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );

        jPanel7.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Quản lí giá", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 18))); // NOI18N

        txtGia1.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtGia1.setPreferredSize(new java.awt.Dimension(7, 30));

        lblGia1.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lblGia1.setText("Giá");

        lblSize.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lblSize.setText("Size");

        txtMaSP1.setEditable(false);
        txtMaSP1.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtMaSP1.setPreferredSize(new java.awt.Dimension(7, 30));
        txtMaSP1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtMaSP1ActionPerformed(evt);
            }
        });

        lblTenSP1.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lblTenSP1.setText("Mã sản phẩm");

        btnMoi1.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        btnMoi1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/g5/logos/Broom.png"))); // NOI18N
        btnMoi1.setPreferredSize(new java.awt.Dimension(50, 50));
        btnMoi1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMoi1ActionPerformed(evt);
            }
        });

        btnThem1.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        btnThem1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/g5/logos/Plus.png"))); // NOI18N
        btnThem1.setPreferredSize(new java.awt.Dimension(50, 50));
        btnThem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThem1ActionPerformed(evt);
            }
        });

        btnXoa1.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        btnXoa1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/g5/logos/Remove.png"))); // NOI18N
        btnXoa1.setPreferredSize(new java.awt.Dimension(50, 50));
        btnXoa1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXoa1ActionPerformed(evt);
            }
        });

        btnSua1.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        btnSua1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/g5/logos/Edit.png"))); // NOI18N
        btnSua1.setPreferredSize(new java.awt.Dimension(50, 50));
        btnSua1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSua1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnThem1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnSua1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnXoa1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnMoi1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(btnXoa1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnSua1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnThem1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnMoi1, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(19, Short.MAX_VALUE))
        );

        txtSize.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtSize.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtSizeActionPerformed(evt);
            }
        });

        jButton3.setText("+");
        jButton3.setPreferredSize(new java.awt.Dimension(33, 21));
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblTenSP1)
                    .addComponent(lblSize, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblGia1, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtGia1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtMaSP1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addComponent(txtSize, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(25, 25, 25))
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGap(49, 49, 49)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(52, Short.MAX_VALUE))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblTenSP1)
                    .addComponent(txtMaSP1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(lblSize, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtSize, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(2, 2, 2)))
                .addGap(17, 17, 17)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblGia1, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtGia1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(99, 99, 99))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(10, 10, 10)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(10, 10, 10)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void tblSanPhamMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblSanPhamMouseClicked
        // TODO add your handling code here:

    }//GEN-LAST:event_tblSanPhamMouseClicked

    private void tblSanPhamMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblSanPhamMousePressed
        // TODO add your handling code here:
        this.row = tblSanPham.getSelectedRow();
        this.editSP();
        this.fillTableSPCT();
        this.editCT();
        txtGia.setEditable(false);
        txtGia.setText("");
    }//GEN-LAST:event_tblSanPhamMousePressed
    int index = -1;
    private void tblChiTietMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblChiTietMousePressed
        // TODO add your handling code here:
        index = tblChiTiet.getSelectedRow();
        this.editCT();
    }//GEN-LAST:event_tblChiTietMousePressed

    private void cboLoaiSPActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboLoaiSPActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cboLoaiSPActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        String loaisp = "";
        loaisp = JOptionPane.showInputDialog("Thêm loại").trim();
        if (loaisp.trim().isEmpty()) {
            return;
        }
        DefaultComboBoxModel model = (DefaultComboBoxModel) cboLoaiSP.getModel();
        model.addElement(loaisp.trim());
        removeCbo(cboLoaiSP);
    }//GEN-LAST:event_jButton1ActionPerformed

    private void btnMoiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMoiActionPerformed
        // TODO add your handling code here:
        clearSP();
        btnThem.setEnabled(true);
        txtGia.setEditable(true);
        btnXoa.setEnabled(false);
        btnSua.setEnabled(false);
    }//GEN-LAST:event_btnMoiActionPerformed

    private void btnXoaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXoaActionPerformed
        // TODO add your handling code here:
        deleteSP();
    }//GEN-LAST:event_btnXoaActionPerformed

    private void btnSuaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSuaActionPerformed
        // TODO add your handling code here:
        updateSP();
    }//GEN-LAST:event_btnSuaActionPerformed

    private void btnThemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThemActionPerformed
        // TODO add your handling code here:
        insertSP();

    }//GEN-LAST:event_btnThemActionPerformed

    private void btnNextActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNextActionPerformed
        // TODO add your handling code here:

        next();
    }//GEN-LAST:event_btnNextActionPerformed

    private void btnLastActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLastActionPerformed
        // TODO add your handling code here:
        last();
    }//GEN-LAST:event_btnLastActionPerformed

    private void btnPrevActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPrevActionPerformed
        // TODO add your handling code here:
        prev();
    }//GEN-LAST:event_btnPrevActionPerformed

    private void btnFirstActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFirstActionPerformed
        // TODO add your handling code here:
        first();
    }//GEN-LAST:event_btnFirstActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
        selectImage();
    }//GEN-LAST:event_jButton2ActionPerformed

    private void txtTimKiemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtTimKiemActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtTimKiemActionPerformed

    private void lblHinhMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblHinhMouseClicked
        // TODO add your handling code here:
        selectImage();
    }//GEN-LAST:event_lblHinhMouseClicked

    private void txtGiaNLActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtGiaNLActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtGiaNLActionPerformed

    private void txtGiaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtGiaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtGiaActionPerformed

    private void txtMaSP1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtMaSP1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtMaSP1ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // TODO add your handling code here:
        String size = "";
        size = JOptionPane.showInputDialog("Thêm size");

        if (size.isEmpty()) {
            return;
        }
        DefaultComboBoxModel model = (DefaultComboBoxModel) txtSize.getModel();
        model.addElement(size.trim());
        removeCbo(cboLoaiSP);
    }//GEN-LAST:event_jButton3ActionPerformed

    private void tblChiTietMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblChiTietMouseExited
        // TODO add your handling code here:
    }//GEN-LAST:event_tblChiTietMouseExited

    private void txtSizeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtSizeActionPerformed
        // TODO add your handling code here:
        selectCombobox();
    }//GEN-LAST:event_txtSizeActionPerformed

    private void btnSua1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSua1ActionPerformed
        // TODO add your handling code here:
        updateCT();
    }//GEN-LAST:event_btnSua1ActionPerformed

    private void btnXoa1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXoa1ActionPerformed
        // TODO add your handling code here:
        deleteCT();
    }//GEN-LAST:event_btnXoa1ActionPerformed

    private void btnThem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThem1ActionPerformed
        // TODO add your handling code here:
        insertCT();
        updateStatusSP();
    }//GEN-LAST:event_btnThem1ActionPerformed

    private void btnMoi1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMoi1ActionPerformed
        // TODO add your handling code here:
        //  clearCT();
        fillTableSPCT();
        btnThem1.setEnabled(true);
        txtGia1.requestFocus();
        resetIdentity();
    }//GEN-LAST:event_btnMoi1ActionPerformed

    void selectCombobox() {

        if (txtSize.getSelectedIndex() == -1) {
            return;
        }
        String formattedValue = String.format("%.0f", dao.getGiaByMaSPAndSize(Integer.parseInt(txtMaSP1.getText().trim()), txtSize.getSelectedItem().toString().trim()));

        txtGia1.setText(formattedValue);
    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnFirst;
    private javax.swing.JButton btnLast;
    private javax.swing.JButton btnMoi;
    private javax.swing.JButton btnMoi1;
    private javax.swing.JButton btnNext;
    private javax.swing.JButton btnPrev;
    private javax.swing.JButton btnSua;
    private javax.swing.JButton btnSua1;
    private javax.swing.JButton btnThem;
    private javax.swing.JButton btnThem1;
    private javax.swing.JButton btnXoa;
    private javax.swing.JButton btnXoa1;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.ButtonGroup buttonGroup2;
    private javax.swing.JComboBox<String> cboLoaiSP;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane10;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel lblGia1;
    private javax.swing.JLabel lblGiaNL;
    private javax.swing.JLabel lblHinh;
    private javax.swing.JLabel lblLoaiSP;
    private javax.swing.JLabel lblMaNV;
    private javax.swing.JLabel lblMaSP;
    private javax.swing.JLabel lblMoTa;
    private javax.swing.JLabel lblMoTa1;
    private javax.swing.JLabel lblSize;
    private javax.swing.JLabel lblSoLuong;
    private javax.swing.JLabel lblTenSP;
    private javax.swing.JLabel lblTenSP1;
    private javax.swing.JRadioButton rdoTKma;
    private javax.swing.JRadioButton rdoTKten;
    private javax.swing.JTable tblChiTiet;
    private javax.swing.JTable tblSanPham;
    private javax.swing.JTextField txtGia;
    private javax.swing.JTextField txtGia1;
    private javax.swing.JTextField txtGiaNL;
    private javax.swing.JTextField txtHinh;
    private javax.swing.JTextField txtMaNV;
    private javax.swing.JTextField txtMaSP;
    private javax.swing.JTextField txtMaSP1;
    private javax.swing.JTextArea txtMoTa;
    private javax.swing.JComboBox<String> txtSize;
    private javax.swing.JTextField txtSoLuong;
    private javax.swing.JTextField txtTenSP;
    private javax.swing.JTextField txtTimKiem;
    // End of variables declaration//GEN-END:variables
}
