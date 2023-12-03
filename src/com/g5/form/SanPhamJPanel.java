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
import javax.swing.JFileChooser;
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
    int h = 265;
    int w = 191;
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
        first();
        fillTableSPCT();
        firstCT();
        tblSanPham.getTableHeader().setFont(new Font("Tohama", 1, 18));
        tblChiTiet.getTableHeader().setFont(new Font("Tohama", 1, 18));
    }

    void getCount() {
        try {
            List<GiaSP> list = daoCT.getAll();
            count = 0;
            for (GiaSP gsp : list) {
                count++;
            }
        } catch (Exception e) {
            System.out.println("Loi du lieu");
        }
    }

    void getNVID() {
        NhanVien nv = daoNV.getByID(Auth.user.getMaNV());
    }

    void fillTableDSSP() {
        DefaultTableModel model = (DefaultTableModel) tblSanPham.getModel();
        model.setRowCount(0);
        try {
            List<SanPham> list = dao.getAll();
            for (SanPham sp : list) {
                Object row[] = {
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
            this.row = 1;
        } catch (Exception e) {
            System.out.println("Loi du lieu");
        }
    }

    void fillTableSPCT() {
        DefaultTableModel model = (DefaultTableModel) tblChiTiet.getModel();
        model.setRowCount(0);
        String tenSP = (String) tblSanPham.getValueAt(this.row, 1);
        try {
            List<GiaSP> list = daoCT.selectByID(Integer.parseInt(txtMaSP.getText()));
            for (GiaSP sp : list) {
                Object row[] = {
                    sp.getMaGSP(),
                    sp.getMaSP(),
                    tenSP,
                    sp.getSize(),
                    sp.getGia()
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
        Integer maSP = (Integer) tblSanPham.getValueAt(this.row, 0);
        SanPham sp = dao.getByID(maSP);
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
        txtGiaNL.setText(String.valueOf(sp.getGiaNguyenLieu()));
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
        Integer maSP = (Integer) tblChiTiet.getValueAt(this.row, 1);
        String size = (String) tblChiTiet.getValueAt(this.row, 3);
        GiaSP sp = daoCT.getByID(maSP);
        this.setFormCT(sp);
    }

    void setFormCT(GiaSP gsp) {
        row = tblChiTiet.getSelectedRow();
        txtMaSP1.setText(String.valueOf((Integer) tblChiTiet.getValueAt(row, 1)));
        txtSize.setText(String.valueOf(tblChiTiet.getValueAt(row, 3)));
        txtGia1.setText(String.valueOf((Float) tblChiTiet.getValueAt(row, 4)));
    }

    void updateStatusSP() {
        boolean edit = (this.row >= 0);
        boolean first = (this.row == 0);
        boolean last = (this.row == tblSanPham.getRowCount() - 1);
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
        btnFirst.setEnabled(edit && !first);
        btnPrev.setEnabled(edit && !first);
        btnNext.setEnabled(edit && !last);
        btnLast.setEnabled(edit && !last);
    }

    void resetIdentity() {
        int column = tblSanPham.getRowCount();
        dao.resetIdentity(column);
    }

    void resetIdentityCT() {
        getCount();
        daoCT.resetIdentity(count);
    }

    void find() {
        dao.getByID(Integer.valueOf(txtMaSP.getText()));
    }

    public void selectImage() {
        JFileChooser fileChooser = new JFileChooser();
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
        sp.setSize(txtSize.getText());
        sp.setGia(Float.parseFloat(txtGia1.getText()));
        return sp;
    }

    void clearSP() {
        txtMaSP.setText(tblSanPham.getRowCount() + 1 + "");
        txtTenSP.setText("");
        txtHinh.setText("Chưa chọn ảnh");
        txtSoLuong.setText("");
        cboLoaiSP.setSelectedItem(0);
        txtMoTa.setText("");
        txtGiaNL.setText("");
        txtGia.setText("");
    }

    void clearCT() {
        txtMaSP1.setText(txtMaSP.getText());
        txtSize.setText("");
        txtGia1.setText("");
    }

    GiaSP FirstPrice() {
        GiaSP fp = new GiaSP();
        fp.setMaSP(Integer.parseInt(txtMaSP.getText()));
        fp.setSize("Size M");
        fp.setGia(Float.parseFloat(txtGia.getText()));
        return fp;
    }

    void insertSP() {
        kiemLoi();
        resetIdentity();
        try {
            SanPham sp = this.getFormSP();
            GiaSP fp = this.FirstPrice();
            dao.create(sp);
            fillTableDSSP();
            daoCT.create(fp);
            fillTableSPCT();
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
            daoCT.create(sp);
            fillTableSPCT();
            updateStatusSP();
            clearCT();
        } catch (Exception e) {
            e.printStackTrace();
            TextMes.Alert(this, "Lỗi thêm sản phẩm");
        }
    }

    void kiemLoi() {
        if (txtTenSP.getText().trim() == null) {
            TextMes.Alert(this, "Chưa nhập tên SP");
            return;
        } else if (txtSoLuong.getText().trim() == null) {
            TextMes.Alert(this, "Chưa nhập so luong");
            return;
        } else if (txtMoTa.getText().trim() == null) {
            TextMes.Alert(this, "Chưa nhập mô tả");
            return;
        }
    }

    void deleteSP() {
        try {
            int masp = Integer.parseInt(txtMaSP.getText().trim());
            daoCT.deteleByMaSP(masp);
            dao.deteleByID(masp);
            fillTableDSSP();
            clearSP();
        } catch (Exception e) {
            e.printStackTrace();
            TextMes.Alert(this, "Lỗi xóa SP");
        }
    }

    void deleteCT() {
        try {
            daoCT.deteleByID(Integer.parseInt(txtMaSP1.getText().trim()), String.valueOf(txtSize.getText()));
            fillTableSPCT();
            clearCT();
        } catch (Exception e) {
            e.printStackTrace();
            TextMes.Alert(this, "Lỗi xóa SP");
        }
    }

    void updateSP() {
        kiemLoi();
        try {
            SanPham sp = this.getFormSP();
            sp.setMaSP(Integer.parseInt(txtMaSP.getText()));
            dao.update(sp);
            fillTableDSSP();
            fillTableSPCT();
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
            sp.setMaGSP(Integer.parseInt((String.valueOf(tblChiTiet.getValueAt(row, 0)))));
            daoCT.update(sp);
            fillTableSPCT();
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
        lblDSSP = new javax.swing.JLabel();
        txtTimKiem = new javax.swing.JTextField();
        btnTimKiem = new javax.swing.JButton();
        lblHinh = new javax.swing.JLabel();
        lblMaSP = new javax.swing.JLabel();
        lblTenSP = new javax.swing.JLabel();
        lblSoLuong = new javax.swing.JLabel();
        lblLoaiSP = new javax.swing.JLabel();
        lblMoTa = new javax.swing.JLabel();
        txtMaSP = new javax.swing.JTextField();
        txtTenSP = new javax.swing.JTextField();
        cboLoaiSP = new javax.swing.JComboBox<>();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtMoTa = new javax.swing.JTextArea();
        btnMoi = new javax.swing.JButton();
        btnThem = new javax.swing.JButton();
        btnXoa = new javax.swing.JButton();
        btnSua = new javax.swing.JButton();
        btnFirst = new javax.swing.JButton();
        btnPrev = new javax.swing.JButton();
        btnNext = new javax.swing.JButton();
        btnLast = new javax.swing.JButton();
        jScrollPane10 = new javax.swing.JScrollPane();
        tblSanPham = new javax.swing.JTable();
        txtMaNV = new javax.swing.JTextField();
        lblMaNV = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        lblSize = new javax.swing.JLabel();
        lblGia1 = new javax.swing.JLabel();
        txtGia1 = new javax.swing.JTextField();
        btnSua1 = new javax.swing.JButton();
        btnXoa1 = new javax.swing.JButton();
        btnThem1 = new javax.swing.JButton();
        btnMoi1 = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblChiTiet = new javax.swing.JTable();
        btnFirst1 = new javax.swing.JButton();
        btnPrev1 = new javax.swing.JButton();
        btnNext1 = new javax.swing.JButton();
        btnLast1 = new javax.swing.JButton();
        lblTenSP1 = new javax.swing.JLabel();
        txtMaSP1 = new javax.swing.JTextField();
        txtSize = new javax.swing.JTextField();
        lblTimKiem = new javax.swing.JLabel();
        txtHinh = new javax.swing.JTextField();
        lblGia = new javax.swing.JLabel();
        txtSoLuong = new javax.swing.JTextField();
        lblGiaNL = new javax.swing.JLabel();
        txtGiaNL = new javax.swing.JTextField();
        txtGia = new javax.swing.JTextField();
        lblimg = new javax.swing.JLabel();

        setPreferredSize(new java.awt.Dimension(1350, 800));

        lblDSSP.setFont(new java.awt.Font("Times New Roman", 1, 24)); // NOI18N
        lblDSSP.setText("Danh sách sản phẩm");

        btnTimKiem.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        btnTimKiem.setText("Tìm Kiếm");
        btnTimKiem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTimKiemActionPerformed(evt);
            }
        });

        lblHinh.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        lblHinh.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                lblHinhMousePressed(evt);
            }
        });

        lblMaSP.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        lblMaSP.setText("Mã sản phẩm");

        lblTenSP.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        lblTenSP.setText("Tên sản phẩm");

        lblSoLuong.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        lblSoLuong.setText("Số lượng");

        lblLoaiSP.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        lblLoaiSP.setText("Loại sản phẩm");

        lblMoTa.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        lblMoTa.setText("Mô tả");

        cboLoaiSP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboLoaiSPActionPerformed(evt);
            }
        });

        txtMoTa.setColumns(20);
        txtMoTa.setRows(5);
        jScrollPane1.setViewportView(txtMoTa);

        btnMoi.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        btnMoi.setText("Mới ");
        btnMoi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMoiActionPerformed(evt);
            }
        });

        btnThem.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        btnThem.setText("Thêm");
        btnThem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThemActionPerformed(evt);
            }
        });

        btnXoa.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        btnXoa.setText("Xóa");
        btnXoa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXoaActionPerformed(evt);
            }
        });

        btnSua.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        btnSua.setText("Sửa");
        btnSua.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSuaActionPerformed(evt);
            }
        });

        btnFirst.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        btnFirst.setText("<|");
        btnFirst.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnFirstActionPerformed(evt);
            }
        });

        btnPrev.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        btnPrev.setText("<<");
        btnPrev.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPrevActionPerformed(evt);
            }
        });

        btnNext.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        btnNext.setText(">>");
        btnNext.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNextActionPerformed(evt);
            }
        });

        btnLast.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        btnLast.setText("|>");
        btnLast.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLastActionPerformed(evt);
            }
        });

        tblSanPham.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        tblSanPham.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null}
            },
            new String [] {
                "MÃ SP", "TÊN SP", "SỐ LƯỢNG", "MÃ NV", "MÔ TẢ", "HÌNH", "LOẠI SP", "GIÁ NGUYÊN LIỆU"
            }
        ));
        tblSanPham.setFocusable(false);
        tblSanPham.setRowHeight(30);
        tblSanPham.setSelectionBackground(new java.awt.Color(255, 102, 255));
        tblSanPham.setShowHorizontalLines(true);
        tblSanPham.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblSanPhamMouseClicked(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                tblSanPhamMousePressed(evt);
            }
        });
        jScrollPane10.setViewportView(tblSanPham);

        lblMaNV.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        lblMaNV.setText("Mã NV");

        jPanel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel2.setFont(new java.awt.Font("Times New Roman", 1, 24)); // NOI18N
        jLabel2.setText("Chi tiết sản phẩm");

        lblSize.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        lblSize.setText("Size:");

        lblGia1.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        lblGia1.setText("Giá:");

        btnSua1.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        btnSua1.setText("Sửa");
        btnSua1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSua1ActionPerformed(evt);
            }
        });

        btnXoa1.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        btnXoa1.setText("Xóa");
        btnXoa1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXoa1ActionPerformed(evt);
            }
        });

        btnThem1.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        btnThem1.setText("Thêm");
        btnThem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThem1ActionPerformed(evt);
            }
        });

        btnMoi1.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        btnMoi1.setText("Mới ");
        btnMoi1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMoi1ActionPerformed(evt);
            }
        });

        tblChiTiet.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        tblChiTiet.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "Mã GSP", "Mã SP", "Tên SP", "Size", "Giá"
            }
        ));
        tblChiTiet.setFocusable(false);
        tblChiTiet.setRowHeight(30);
        tblChiTiet.setSelectionBackground(new java.awt.Color(255, 102, 255));
        tblChiTiet.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                tblChiTietMousePressed(evt);
            }
        });
        jScrollPane2.setViewportView(tblChiTiet);

        btnFirst1.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        btnFirst1.setText("<|");
        btnFirst1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnFirst1ActionPerformed(evt);
            }
        });

        btnPrev1.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        btnPrev1.setText("<<");
        btnPrev1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPrev1ActionPerformed(evt);
            }
        });

        btnNext1.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        btnNext1.setText(">>");
        btnNext1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNext1ActionPerformed(evt);
            }
        });

        btnLast1.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        btnLast1.setText("|>");
        btnLast1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLast1ActionPerformed(evt);
            }
        });

        lblTenSP1.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        lblTenSP1.setText("Mã sản phẩm");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(btnFirst1, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(35, 35, 35)
                        .addComponent(btnPrev1, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnNext1, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(55, 55, 55)
                        .addComponent(btnLast1, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(16, 16, 16))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(lblTenSP1)
                        .addGap(18, 18, 18)
                        .addComponent(txtMaSP1)
                        .addContainerGap())
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(lblSize, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(45, 45, 45)
                        .addComponent(txtSize)
                        .addContainerGap())
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(btnMoi1, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnThem1, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnXoa1, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnSua1, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(lblGia1, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(46, 46, 46)
                        .addComponent(txtGia1)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jLabel2)
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblTenSP1)
                    .addComponent(txtMaSP1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblSize, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtSize, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblGia1, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtGia1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnMoi1)
                    .addComponent(btnThem1)
                    .addComponent(btnXoa1)
                    .addComponent(btnSua1))
                .addGap(27, 27, 27)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 273, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 13, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnFirst1)
                    .addComponent(btnPrev1)
                    .addComponent(btnNext1)
                    .addComponent(btnLast1))
                .addGap(4, 4, 4))
        );

        lblTimKiem.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        lblTimKiem.setText("Tìm mã sản phẩm:");

        txtHinh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtHinhActionPerformed(evt);
            }
        });

        lblGia.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        lblGia.setText("Giá");

        lblGiaNL.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        lblGiaNL.setText("Giá nguyên liệu");

        txtGiaNL.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtGiaNLActionPerformed(evt);
            }
        });

        lblimg.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        lblimg.setText("Hình");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(lblHinh, javax.swing.GroupLayout.PREFERRED_SIZE, 191, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(38, 38, 38)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(btnTimKiem)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(layout.createSequentialGroup()
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                    .addComponent(lblMaSP)
                                                    .addComponent(lblMaNV))
                                                .addGap(18, 18, 18)
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                    .addComponent(txtMaNV, javax.swing.GroupLayout.PREFERRED_SIZE, 174, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                    .addComponent(txtMaSP, javax.swing.GroupLayout.PREFERRED_SIZE, 174, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                            .addGroup(layout.createSequentialGroup()
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                    .addComponent(lblSoLuong)
                                                    .addComponent(lblGiaNL)
                                                    .addComponent(lblimg))
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                    .addComponent(txtGiaNL, javax.swing.GroupLayout.PREFERRED_SIZE, 174, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                    .addComponent(txtSoLuong, javax.swing.GroupLayout.PREFERRED_SIZE, 174, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                    .addComponent(txtHinh, javax.swing.GroupLayout.PREFERRED_SIZE, 174, javax.swing.GroupLayout.PREFERRED_SIZE))))
                                        .addGap(37, 37, 37)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addGroup(layout.createSequentialGroup()
                                                .addComponent(lblTenSP)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                .addComponent(txtTenSP))
                                            .addGroup(layout.createSequentialGroup()
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                    .addGroup(layout.createSequentialGroup()
                                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                            .addComponent(lblLoaiSP)
                                                            .addComponent(lblMoTa))
                                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED))
                                                    .addGroup(layout.createSequentialGroup()
                                                        .addComponent(lblGia, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                        .addGap(34, 34, 34)))
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 219, Short.MAX_VALUE)
                                                    .addComponent(cboLoaiSP, 0, 219, Short.MAX_VALUE)
                                                    .addComponent(txtGia)))))))
                            .addGroup(layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnMoi)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnThem)
                                .addGap(24, 24, 24)
                                .addComponent(btnXoa)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnSua)
                                .addGap(21, 21, 21)
                                .addComponent(btnFirst)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(btnPrev)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnNext)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnLast))))
                    .addComponent(lblDSSP)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(8, 8, 8)
                        .addComponent(lblTimKiem, javax.swing.GroupLayout.PREFERRED_SIZE, 174, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtTimKiem, javax.swing.GroupLayout.PREFERRED_SIZE, 561, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane10))
                .addGap(18, 18, 18)
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(38, 38, 38))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(lblDSSP)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(lblTimKiem, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(txtTimKiem, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(btnTimKiem, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(lblTenSP)
                                        .addComponent(txtTenSP, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(lblMaSP)
                                        .addComponent(txtMaSP, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(lblMaNV)
                                        .addComponent(txtMaNV, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(lblLoaiSP)
                                        .addComponent(cboLoaiSP, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(18, 18, 18)
                                        .addComponent(lblMoTa))
                                    .addGroup(layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addGroup(layout.createSequentialGroup()
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE))
                                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                    .addGroup(layout.createSequentialGroup()
                                                        .addGap(18, 18, 18)
                                                        .addComponent(lblSoLuong))
                                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                        .addComponent(txtSoLuong, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                                .addGap(18, 18, 18)
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                    .addComponent(txtHinh, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                    .addComponent(lblimg))))
                                        .addGap(18, 18, 18)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                            .addComponent(lblGiaNL)
                                            .addComponent(txtGiaNL, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(txtGia, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(lblGia))))
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(btnFirst, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(btnPrev, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(btnNext, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(btnLast, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(btnThem, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(btnXoa, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(btnSua, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(btnMoi, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))))
                            .addComponent(lblHinh, javax.swing.GroupLayout.PREFERRED_SIZE, 265, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jScrollPane10, javax.swing.GroupLayout.PREFERRED_SIZE, 224, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(19, 19, 19)
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(206, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void tblSanPhamMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblSanPhamMouseClicked
        // TODO add your handling code here:

    }//GEN-LAST:event_tblSanPhamMouseClicked

    private void btnMoiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMoiActionPerformed
        // TODO add your handling code here:
        clearSP();
        btnThem.setEnabled(true);
        txtMaSP.requestFocus();
    }//GEN-LAST:event_btnMoiActionPerformed

    private void btnMoi1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMoi1ActionPerformed
        // TODO add your handling code here:
        clearCT();
        btnThem1.setEnabled(true);
        txtGia1.requestFocus();
        resetIdentityCT();
    }//GEN-LAST:event_btnMoi1ActionPerformed

    private void btnThem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThem1ActionPerformed
        // TODO add your handling code here:
        insertCT();
        updateStatusSP();
    }//GEN-LAST:event_btnThem1ActionPerformed

    private void btnXoa1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXoa1ActionPerformed
        // TODO add your handling code here:
        deleteCT();
    }//GEN-LAST:event_btnXoa1ActionPerformed

    private void btnNextActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNextActionPerformed
        // TODO add your handling code here:
        next();
    }//GEN-LAST:event_btnNextActionPerformed

    private void btnFirstActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFirstActionPerformed
        // TODO add your handling code here:
        first();
    }//GEN-LAST:event_btnFirstActionPerformed

    private void btnPrevActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPrevActionPerformed
        // TODO add your handling code here:
        prev();
    }//GEN-LAST:event_btnPrevActionPerformed

    private void btnLastActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLastActionPerformed
        // TODO add your handling code here:
        last();
    }//GEN-LAST:event_btnLastActionPerformed

    private void tblSanPhamMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblSanPhamMousePressed
        // TODO add your handling code here:
        this.row = tblSanPham.getSelectedRow();
        this.editSP();
        this.fillTableSPCT();
    }//GEN-LAST:event_tblSanPhamMousePressed

    private void btnFirst1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFirst1ActionPerformed
        // TODO add your handling code here:
        firstCT();
    }//GEN-LAST:event_btnFirst1ActionPerformed

    private void btnPrev1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPrev1ActionPerformed
        // TODO add your handling code here:
        prevCT();
    }//GEN-LAST:event_btnPrev1ActionPerformed

    private void btnNext1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNext1ActionPerformed
        // TODO add your handling code here:
        nextCT();
    }//GEN-LAST:event_btnNext1ActionPerformed

    private void btnLast1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLast1ActionPerformed
        // TODO add your handling code here:
        lastCT();
    }//GEN-LAST:event_btnLast1ActionPerformed

    private void btnTimKiemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTimKiemActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnTimKiemActionPerformed

    private void btnXoaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXoaActionPerformed
        // TODO add your handling code here:
        deleteSP();
    }//GEN-LAST:event_btnXoaActionPerformed

    private void lblHinhMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblHinhMousePressed
        // TODO add your handling code here:
        selectImage();
    }//GEN-LAST:event_lblHinhMousePressed

    private void btnThemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThemActionPerformed
        // TODO add your handling code here:
        insertSP();
    }//GEN-LAST:event_btnThemActionPerformed

    private void btnSuaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSuaActionPerformed
        // TODO add your handling code here:
        updateSP();
    }//GEN-LAST:event_btnSuaActionPerformed

    private void txtHinhActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtHinhActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtHinhActionPerformed

    private void tblChiTietMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblChiTietMousePressed
        // TODO add your handling code here:
        this.row = tblChiTiet.getSelectedRow();
        this.editCT();
    }//GEN-LAST:event_tblChiTietMousePressed

    private void btnSua1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSua1ActionPerformed
        // TODO add your handling code here:
        updateCT();
    }//GEN-LAST:event_btnSua1ActionPerformed

    private void cboLoaiSPActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboLoaiSPActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cboLoaiSPActionPerformed

    private void txtGiaNLActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtGiaNLActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtGiaNLActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnFirst;
    private javax.swing.JButton btnFirst1;
    private javax.swing.JButton btnLast;
    private javax.swing.JButton btnLast1;
    private javax.swing.JButton btnMoi;
    private javax.swing.JButton btnMoi1;
    private javax.swing.JButton btnNext;
    private javax.swing.JButton btnNext1;
    private javax.swing.JButton btnPrev;
    private javax.swing.JButton btnPrev1;
    private javax.swing.JButton btnSua;
    private javax.swing.JButton btnSua1;
    private javax.swing.JButton btnThem;
    private javax.swing.JButton btnThem1;
    private javax.swing.JButton btnTimKiem;
    private javax.swing.JButton btnXoa;
    private javax.swing.JButton btnXoa1;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JComboBox<String> cboLoaiSP;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane10;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel lblDSSP;
    private javax.swing.JLabel lblGia;
    private javax.swing.JLabel lblGia1;
    private javax.swing.JLabel lblGiaNL;
    private javax.swing.JLabel lblHinh;
    private javax.swing.JLabel lblLoaiSP;
    private javax.swing.JLabel lblMaNV;
    private javax.swing.JLabel lblMaSP;
    private javax.swing.JLabel lblMoTa;
    private javax.swing.JLabel lblSize;
    private javax.swing.JLabel lblSoLuong;
    private javax.swing.JLabel lblTenSP;
    private javax.swing.JLabel lblTenSP1;
    private javax.swing.JLabel lblTimKiem;
    private javax.swing.JLabel lblimg;
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
    private javax.swing.JTextField txtSize;
    private javax.swing.JTextField txtSoLuong;
    private javax.swing.JTextField txtTenSP;
    private javax.swing.JTextField txtTimKiem;
    // End of variables declaration//GEN-END:variables
}
