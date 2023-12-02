/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.g5.form;

import com.g5.entity.NhanVien;
import com.g5.entityDAO.NhanVienDAOImpl;
import com.g5.ui.MainFrame;
import com.g5.util.Auth;
import com.g5.util.TextMes;
import com.g5.util.XDate;
import com.g5.util.XImage;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.InputStream;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JViewport;
import javax.swing.SwingUtilities;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import org.apache.log4j.PropertyConfigurator;

/**
 *
 * @author anhba
 */
public class NhanVienJPanel extends javax.swing.JPanel {

    public static final org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(NhanVienJPanel.class);

    final int w = 198;
    final int h = 248;
    int index = 0;
    boolean selected = false;

    /**
     * Creates new form Form1
     */
    public NhanVienJPanel() {
        initComponents();
        setOpaque(false);

        tblNhanVien.getTableHeader().setFont(new Font("Tohama", 1, 16));
        tblNhanVien.setRowMargin(1);
        btnThem.setEnabled(false);
        btnXoa.setEnabled(false);
        btnSua.setEnabled(false);
        txtDiaChi.setLineWrap(true); // Cho phép xuống dòng tự động
        txtDiaChi.setWrapStyleWord(true); // Xuống dòng theo từ
        fillTable();
        fillForm();
        init();
        PropertyConfigurator.configure("src/com/g5/log/log4j.properties");

//        JViewport viewport = (JViewport) SwingUtilities.getAncestorOfClass(JViewport.class, tblNhanVien);
//                Rectangle rect = tblNhanVien.getCellRect(tblNhanVien.getRowCount() - 1, 0, true);
//                viewport.scrollRectToVisible(rect);
    }

    NhanVienDAOImpl nvDAO = new NhanVienDAOImpl();

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

    void fillSearch() {
        List<NhanVien> list;
        if (rdoTKma.isSelected()) {
            list = nvDAO.selectByKeyword(txtTimKiem.getText().trim());
        } else {
            list = nvDAO.selectByName(txtTimKiem.getText().trim());
        }

        DefaultTableModel model = (DefaultTableModel) tblNhanVien.getModel();
        model.setRowCount(0);
        int i = 0;
        for (NhanVien nhanVien : list) {
            String vaitro = "Có lỗi xảy ra";
            if (nhanVien.getVaitro() == 0) {
                vaitro = "Nhân viên";
            }
            if (nhanVien.getVaitro() == 1) {
                vaitro = "Quản lí";
            }
            if (nhanVien.getVaitro() == 2) {
                vaitro = "Chủ";
            }

            model.addRow(new Object[]{++i, nhanVien.getMaNV(), nhanVien.getHoTen(), nhanVien.getMatkhau(), nhanVien.getSDT(), nhanVien.getEmail(), nhanVien.isGioitinh() ? "Nam" : "Nữ", vaitro, XDate.toString(nhanVien.getNgaysinh(), "dd-MM-yyyy"), nhanVien.getDiachi(), nhanVien.getTrangthai() ? "Làm việc" : "Nghỉ việc", nhanVien.getHinh()});

        }
    }

    void fillTable() {
        List<NhanVien> list = nvDAO.getAll();
        DefaultTableModel model = (DefaultTableModel) tblNhanVien.getModel();
        model.setRowCount(0);
        int i = 0;
        for (NhanVien nhanVien : list) {
            String vaitro = "Có lỗi xảy ra";
            if (nhanVien.getVaitro() == 0) {
                vaitro = "Nhân viên";
            }
            if (nhanVien.getVaitro() == 1) {
                vaitro = "Quản lí";
            }
            if (nhanVien.getVaitro() == 2) {
                vaitro = "Chủ";
            }

            model.addRow(new Object[]{++i, nhanVien.getMaNV(), nhanVien.getHoTen(), nhanVien.getMatkhau(), nhanVien.getSDT(), nhanVien.getEmail(), nhanVien.isGioitinh() ? "Nam" : "Nữ", vaitro, XDate.toString(nhanVien.getNgaysinh(), "dd-MM-yyyy"), nhanVien.getDiachi(), nhanVien.getTrangthai() ? "Làm việc" : "Nghỉ việc", nhanVien.getHinh()});

        }

//        if(i !=0){
//            setForm(getTable(0));
//               tblNhanVien.setRowSelectionInterval(0, 0);
//    tblNhanVien.scrollRectToVisible(tblNhanVien.getCellRect(0, 0, true));
//        }
    }

    boolean checkItMe() {
        return String.valueOf(Auth.user.getMaNV()).trim().equalsIgnoreCase(txtMaNV.getText().trim()) ? true : false;
    }

    void fillForm() {
        txtMaNV.setText(String.valueOf(nvDAO.getMaxMaNV() + 1));

    }

    public void selectImage() {
        JFileChooser fileChooser = new JFileChooser("src\\com\\g5\\logos");
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

    NhanVien getTable(int i) {
        int MaNV = -1;

        if (selected == false) {

            if (i != -1) {
                index = i;
            } else {
                index = tblNhanVien.getSelectedRow();
            }

            MaNV = Integer.parseInt(tblNhanVien.getValueAt(index, 1).toString());

        }
        if (selected == true) {
            MaNV = Integer.parseInt(tblNhanVien.getValueAt(index, 1).toString());
            selected = false;
        }

        NhanVien nv = nvDAO.getByID(MaNV);
        nv.setMaNV(nv.getMaNV());
        nv.setHoTen(nv.getHoTen());
        nv.setVaitro(nv.getVaitro());
        nv.setDiachi(nv.getDiachi());
        nv.setEmail(nv.getEmail());
        nv.setGioitinh(nv.isGioitinh());
        nv.setHinh(nv.getHinh());
        nv.setMatkhau(nv.getMatkhau());
        nv.setSDT(nv.getSDT());
        nv.setNgaysinh(nv.getNgaysinh());
        nv.setTrangthai(nv.getTrangthai());

        return MaNV != -1 ? nv : null;
    }

    NhanVien getForm() {
        NhanVien nv = new NhanVien();
        nv.setMaNV(Integer.parseInt(txtMaNV.getText().trim()));
        nv.setHoTen(txtHoTen.getText().trim());
        int vt = 0;
        if (rdoChu.isSelected()) {
            vt = 2;
        }
        if (rdoQL.isSelected()) {
            vt = 1;
        }
        if (rdoNV.isSelected()) {
            vt = 0;
        }
        nv.setVaitro(vt);
        nv.setTrangthai(rdoLamViec.isSelected() ? true : false);
        nv.setSDT(txtSDT.getText().trim());
        String date = XDate.ChuyenNgay(txtNgaySinh.getDate());//  ngay/thang/nam
        nv.setNgaysinh(XDate.toDate2(date, "dd/MM/yyyy"));

        nv.setDiachi(txtDiaChi.getText().trim());
        nv.setMatkhau(txtMatKhau.getText());
        nv.setEmail(txtEmail.getText().trim());
        nv.setGioitinh(rdoNam.isSelected() ? true : false);
        nv.setHinh(txtHinh.getText());
        return nv;
    }

    void insertNV() {
        NhanVien nv = getForm();
        if (nv.getVaitro() == 2) {
            TextMes.Alert(this, "Bạn không đủ quyền đề làm điều này");
        }
        nvDAO.create(nv);
        fillTable();
        fillForm();
        clear(new NhanVien());
        if (tblNhanVien.getRowCount() != 0) {
            tblNhanVien.setRowSelectionInterval(tblNhanVien.getRowCount() - 1, tblNhanVien.getRowCount() - 1);
            tblNhanVien.scrollRectToVisible(tblNhanVien.getCellRect(tblNhanVien.getRowCount() - 1, 0, true));
        }

        TextMes.Alert(this, "Thêm nhân viên mới thành công");
        logger.info("Người dùng [Mã nhân viên: " + Auth.user.getMaNV() + " | Họ tên: " + Auth.user.getHoTen() + "]" + " vừa thêm nhân viên :" + nv.getMaNV());
    }

    void clear(NhanVien nv) {
        fillForm();
        int vaitroTK = Auth.user.getVaitro();
        txtDiaChi.setText(nv.getDiachi());
        txtEmail.setText(nv.getEmail());
        lblHinh.removeAll();
        lblHinh.setIcon(null);
        txtHinh.setText("Chưa chọn ảnh");
        txtHoTen.setText(nv.getHoTen());
        txtMatKhau.setText(nv.getMatkhau());
        txtNgaySinh.setDate(nv.getNgaysinh() == null ? new Date() : new Date());
        txtSDT.setText(nv.getSDT());
        txtXNMatKhau.setText("");

        if (vaitroTK == 1) {
            rdoNV.setEnabled(true);
            rdoQL.setEnabled(false);
            rdoNV.setSelected(true);
        }
        if (vaitroTK == 2) {
            rdoNV.setEnabled(true);
            rdoQL.setEnabled(true);
            rdoNV.setSelected(true);
        }

        btnSua.setEnabled(false);

        btnThem.setEnabled(true);
        btnXoa.setEnabled(false);
        rdoLamViec.setSelected(true);
        rdoNam.setSelected(true);
        rdoChu.setEnabled(false);
    }

    void setForm(NhanVien nv) {

        btnThem.setEnabled(false);
        btnMoi.setEnabled(true);
        txtMaNV.setText(nv.getMaNV() + "");
        txtHoTen.setText(nv.getHoTen().trim());
        txtMatKhau.setText(nv.getMatkhau().trim());
        txtXNMatKhau.setText(nv.getMatkhau().trim());
        txtEmail.setText(nv.getEmail().trim());
        txtSDT.setText(nv.getSDT().trim());

        if (nv.isGioitinh()) {
            rdoNam.setSelected(true);
        } else {
            rdoNu.setSelected(false);
        }

        txtDiaChi.setText(nv.getDiachi().trim());

        if (nv.getTrangthai()) {
            rdoLamViec.setSelected(true);
        } else {
            rdoNgi.setSelected(true);
        }

        txtNgaySinh.setDate(nv.getNgaysinh());
        lblHinh.setToolTipText(nv.getHinh().trim());
        if (nv.getHinh() != null) {
            BufferedImage originalImage = null;
            boolean loadimg = false;
            try {
                originalImage = ImageIO.read(new File("src//com//g5//logos//" + nv.getHinh().trim()));
                loadimg = true;
                txtHinh.setText(nv.getHinh());
            } catch (Exception e) {
                loadimg = false;
                //   e.printStackTrace();
            }
            if (!loadimg) {
                try {
                    lblHinh.setIcon(null);
                    originalImage = ImageIO.read(new File("src//com//g5//logos//" + "null.png"));
                    txtHinh.setText("Lỗi tải ảnh");
                } catch (Exception e) {
                    //   e.printStackTrace();
                }

            }

            ImageIcon icon = new ImageIcon(originalImage);

            Image image = icon.getImage();

            Image scaledImage = image.getScaledInstance(w, h, Image.SCALE_DEFAULT);

            ImageIcon scaledIcon = new ImageIcon(scaledImage);

            lblHinh.setIcon(scaledIcon);

            int vaitro = nv.getVaitro();
            if (vaitro == 0) {
                rdoChu.setEnabled(false);
                rdoQL.setEnabled(false);
                rdoNV.setEnabled(true);
                rdoNV.setSelected(true);
            }
            if (vaitro == 1) {
                rdoChu.setEnabled(false);
                rdoNV.setEnabled(false);
                rdoQL.setEnabled(true);
                rdoQL.setSelected(true);
            }
            if (vaitro == 2) {
                rdoQL.setEnabled(false);
                rdoNV.setEnabled(false);
                rdoChu.setEnabled(true);
                rdoChu.setSelected(true);
            }

            int maNVTK = Auth.user.getMaNV(); // 
            int vaitroTK = Auth.user.getVaitro();
            if (vaitroTK == 0) {
                btnThem.setEnabled(false);
                btnSua.setEnabled(false);
                btnXoa.setEnabled(false);
                btnMoi.setEnabled(false);
                return;
            }
            switch (vaitroTK) {
                case 1:{///// tài khoản quản lí
                   
                    btnSua.setEnabled(true);
                   if(vaitro == 2 || (vaitro == 1 && nv.getMaNV() != maNVTK)){
                   btnXoa.setEnabled(false);
                   btnSua.setEnabled(false);
                   btnThem.setEnabled(false);
                   
                   
                   }
                   if(vaitro == 0){
                    btnXoa.setEnabled(true);                   
                   }
                   
                break;
                }
                case 2:{//// tải khoản chủ
                    btnSua.setEnabled(true);
                    btnMoi.setEnabled(true);
                    btnXoa.setEnabled(true);
                    

                   if(vaitro == 2 && nv.getMaNV() == maNVTK){
                   btnXoa.setEnabled(false);
                   btnSua.setEnabled(true);
                   btnThem.setEnabled(false);
                   
                   rdoQL.setEnabled(false);
                   rdoNV.setEnabled(false);
                   }
                   if(vaitro == 2 && nv.getMaNV() != maNVTK){
                   btnXoa.setEnabled(false);
                   btnSua.setEnabled(false);
                   btnThem.setEnabled(false);
                   
                   rdoQL.setEnabled(false);
                   rdoNV.setEnabled(false);
                   }
                   if(vaitro != 2){
                   btnXoa.setEnabled(true);
                   btnSua.setEnabled(true);
                   btnThem.setEnabled(false);
                   
                   rdoNV.setEnabled(true);
                   rdoQL.setEnabled(true);
                   }
                break;
                }
                default:{
                    btnXoa.setEnabled(true);
                    btnSua.setEnabled(true);
                    btnThem.setEnabled(false);
                    btnMoi.setEnabled(true);
                break;
                }
                
            }

        }
    }

    void deleteNV() {
        if (checkItMe()) {
            TextMes.Alert(this, "Không được xóa chính mình");
            return;
        }
        int id = Integer.parseInt(txtMaNV.getText().trim());
        NhanVien nv = nvDAO.getByID(id);

        if (nv == null) {
            return;
        }
        if (Auth.user.getVaitro() != 2 && (nv.getVaitro() == 2 || nv.getVaitro() == 1)) {
            TextMes.Alert(this, "Bạn không đủ quyền đề làm điều này");
            return;
        }

        nvDAO.deteleByID(Integer.parseInt(txtMaNV.getText().trim()));
        fillTable();
        fillForm();
        clear(new NhanVien());
        if (tblNhanVien.getRowCount() != 0) {
            tblNhanVien.setRowSelectionInterval(0, 0);
            tblNhanVien.scrollRectToVisible(tblNhanVien.getCellRect(0, 0, true));
        }

        TextMes.Alert(this, "Đã xóa nhân viên: " + id);
        logger.info("Người dùng [Mã nhân viên: " + Auth.user.getMaNV() + " | Họ tên: " + Auth.user.getHoTen() + "]" + " vừa xóa nhân viên :" + id);
    }

    void updateNV() {

        NhanVien nv = getForm();
        if (Auth.user.getVaitro() != 2 && nv.getVaitro() == 2) {
            TextMes.Alert(this, "Bạn không đủ quyền đề làm điều này");
            return;
        }
        nvDAO.update(nv);

        fillTable();
        fillForm();
        clear(new NhanVien());
        for (int i = 0; i < tblNhanVien.getRowCount(); i++) {
            if (Integer.parseInt((tblNhanVien.getValueAt(i, 1) + "").trim()) == nv.getMaNV()) {

                tblNhanVien.setRowSelectionInterval(i, i);
                tblNhanVien.scrollRectToVisible(tblNhanVien.getCellRect(i, 0, true));
                break;
            }
        }
        TextMes.Alert(this, "Cập nhật thành công");
        logger.info("Người dùng [Mã nhân viên: " + Auth.user.getMaNV() + " | Họ tên: " + Auth.user.getHoTen() + "]" + " vừa cập nhật nhân viên :" + nv.getMaNV());

    }

    void firstTable() {
        if (tblNhanVien.getRowCount() == 0) {
            clear(new NhanVien());
            return;
        }
        index = 0;

        tblNhanVien.setRowSelectionInterval(index, index);
        tblNhanVien.scrollRectToVisible(tblNhanVien.getCellRect(index, 0, true));

        selected = true;
        setForm(getTable(-1));
    }

    void nextTable() {
        if (tblNhanVien.getRowCount() == 0) {
            clear(new NhanVien());
            return;
        }
        index++;
        if (index >= tblNhanVien.getRowCount()) {
            index = tblNhanVien.getRowCount() - 1;
        }
        tblNhanVien.setRowSelectionInterval(index, index);
        tblNhanVien.scrollRectToVisible(tblNhanVien.getCellRect(index, 0, true));
        selected = true;
        setForm(getTable(-1));
    }

    void backTable() {
        if (tblNhanVien.getRowCount() == 0) {
            clear(new NhanVien());
            return;
        }
        index--;
        if (index <= 0) {
            index = 0;
        }
        tblNhanVien.setRowSelectionInterval(index, index);
        tblNhanVien.scrollRectToVisible(tblNhanVien.getCellRect(index, 0, true));
        selected = true;
        setForm(getTable(-1));
    }

    void lastTable() {
        if (tblNhanVien.getRowCount() == 0) {
            clear(new NhanVien());
            return;
        }
        index = tblNhanVien.getRowCount() - 1;
        tblNhanVien.setRowSelectionInterval(index, index);
        tblNhanVien.scrollRectToVisible(tblNhanVien.getCellRect(index, 0, true));
        selected = true;
        setForm(getTable(-1));
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
        buttonGroup3 = new javax.swing.ButtonGroup();
        buttonGroup4 = new javax.swing.ButtonGroup();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane10 = new javax.swing.JScrollPane();
        tblNhanVien = new javax.swing.JTable();
        txtTimKiem = new javax.swing.JTextField();
        rdoTKma = new javax.swing.JRadioButton();
        rdoTKten = new javax.swing.JRadioButton();
        jPanel2 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        lblHinh = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        txtMaNV = new javax.swing.JTextField();
        txtHoTen = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        txtHinh = new javax.swing.JTextField();
        txtNgaySinh = new com.toedter.calendar.JDateChooser();
        rdoNV = new javax.swing.JRadioButton();
        rdoQL = new javax.swing.JRadioButton();
        rdoNam = new javax.swing.JRadioButton();
        rdoNu = new javax.swing.JRadioButton();
        jPanel4 = new javax.swing.JPanel();
        btnMoi = new javax.swing.JButton();
        btnXoa = new javax.swing.JButton();
        btnSua = new javax.swing.JButton();
        btnThem = new javax.swing.JButton();
        btnN = new javax.swing.JButton();
        btnL = new javax.swing.JButton();
        btnB = new javax.swing.JButton();
        btnF = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jLabel12 = new javax.swing.JLabel();
        rdoLamViec = new javax.swing.JRadioButton();
        rdoNgi = new javax.swing.JRadioButton();
        jLabel11 = new javax.swing.JLabel();
        rdoChu = new javax.swing.JRadioButton();
        txtMatKhau = new javax.swing.JTextField();
        txtXNMatKhau = new javax.swing.JTextField();
        txtSDT = new javax.swing.JTextField();
        txtEmail = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtDiaChi = new javax.swing.JTextArea();

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Danh sách nhân viên", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 18))); // NOI18N

        tblNhanVien.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        tblNhanVien.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", null},
                {null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "STT", "MÃ NV", "Tên NV", "Mật khẩu", "SÐT", "Email", "Giới tính", "Vai trò", "Ngày sinh", "Địa chỉ", "Trạng thái", "Hình"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblNhanVien.setFocusable(false);
        tblNhanVien.setIntercellSpacing(new java.awt.Dimension(0, 0));
        tblNhanVien.setRowHeight(30);
        tblNhanVien.setSelectionBackground(new java.awt.Color(204, 204, 255));
        tblNhanVien.setShowVerticalLines(false);
        tblNhanVien.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblNhanVienMouseClicked(evt);
            }
        });
        jScrollPane10.setViewportView(tblNhanVien);
        if (tblNhanVien.getColumnModel().getColumnCount() > 0) {
            tblNhanVien.getColumnModel().getColumn(0).setPreferredWidth(50);
            tblNhanVien.getColumnModel().getColumn(1).setPreferredWidth(50);
            tblNhanVien.getColumnModel().getColumn(2).setPreferredWidth(100);
            tblNhanVien.getColumnModel().getColumn(3).setPreferredWidth(50);
            tblNhanVien.getColumnModel().getColumn(5).setPreferredWidth(200);
            tblNhanVien.getColumnModel().getColumn(6).setResizable(false);
            tblNhanVien.getColumnModel().getColumn(6).setPreferredWidth(50);
            tblNhanVien.getColumnModel().getColumn(7).setPreferredWidth(50);
            tblNhanVien.getColumnModel().getColumn(9).setResizable(false);
            tblNhanVien.getColumnModel().getColumn(9).setPreferredWidth(100);
            tblNhanVien.getColumnModel().getColumn(11).setPreferredWidth(50);
        }

        txtTimKiem.setPreferredSize(new java.awt.Dimension(7, 30));
        txtTimKiem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtTimKiemActionPerformed(evt);
            }
        });

        buttonGroup4.add(rdoTKma);
        rdoTKma.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        rdoTKma.setSelected(true);
        rdoTKma.setText("Theo mã");

        buttonGroup4.add(rdoTKten);
        rdoTKten.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        rdoTKten.setText("Theo tên");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(txtTimKiem, javax.swing.GroupLayout.PREFERRED_SIZE, 377, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(rdoTKma)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(rdoTKten)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jScrollPane10, javax.swing.GroupLayout.DEFAULT_SIZE, 1246, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(txtTimKiem, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(rdoTKma)
                        .addComponent(rdoTKten)))
                .addGap(10, 10, 10)
                .addComponent(jScrollPane10, javax.swing.GroupLayout.DEFAULT_SIZE, 290, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Quản lí", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 18))); // NOI18N

        jPanel3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel3.setPreferredSize(new java.awt.Dimension(200, 200));

        lblHinh.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblHinhMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lblHinh, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lblHinh, javax.swing.GroupLayout.DEFAULT_SIZE, 248, Short.MAX_VALUE)
        );

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel2.setText("Mã NV:");

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel3.setText("Họ tên:");

        jLabel4.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel4.setText("Mật khẩu:");

        jLabel5.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel5.setText("XN mật khẩu:");

        jLabel6.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel6.setText("SDT:");

        txtMaNV.setEditable(false);
        txtMaNV.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtMaNV.setPreferredSize(new java.awt.Dimension(7, 30));

        txtHoTen.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtHoTen.setPreferredSize(new java.awt.Dimension(7, 30));

        jLabel7.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel7.setText("Giới tính:");

        jLabel8.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel8.setText("Vai trò:");

        jLabel9.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel9.setText("Ngày sinh:");

        jLabel10.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel10.setText("Địa chỉ:");

        txtHinh.setEditable(false);
        txtHinh.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtHinh.setText("Chưa chọn ảnh");
        txtHinh.setMinimumSize(new java.awt.Dimension(7, 30));
        txtHinh.setPreferredSize(new java.awt.Dimension(78, 30));

        txtNgaySinh.setPreferredSize(new java.awt.Dimension(74, 30));

        buttonGroup2.add(rdoNV);
        rdoNV.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        rdoNV.setSelected(true);
        rdoNV.setText("Nhân viên");

        buttonGroup2.add(rdoQL);
        rdoQL.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        rdoQL.setText("Quản lí");

        buttonGroup1.add(rdoNam);
        rdoNam.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        rdoNam.setSelected(true);
        rdoNam.setText("Nam");

        buttonGroup1.add(rdoNu);
        rdoNu.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        rdoNu.setText("Nữ");

        btnMoi.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/g5/image/Broom_2.png"))); // NOI18N
        btnMoi.setPreferredSize(new java.awt.Dimension(50, 50));
        btnMoi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMoiActionPerformed(evt);
            }
        });

        btnXoa.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/g5/image/Remove.png"))); // NOI18N
        btnXoa.setPreferredSize(new java.awt.Dimension(50, 50));
        btnXoa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXoaActionPerformed(evt);
            }
        });

        btnSua.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/g5/image/Edit_2.png"))); // NOI18N
        btnSua.setPreferredSize(new java.awt.Dimension(50, 50));
        btnSua.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSuaActionPerformed(evt);
            }
        });

        btnThem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/g5/image/Plus.png"))); // NOI18N
        btnThem.setPreferredSize(new java.awt.Dimension(50, 50));
        btnThem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThemActionPerformed(evt);
            }
        });

        btnN.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        btnN.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/g5/image/Next.png"))); // NOI18N
        btnN.setPreferredSize(new java.awt.Dimension(40, 40));
        btnN.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNActionPerformed(evt);
            }
        });

        btnL.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        btnL.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/g5/image/Last.png"))); // NOI18N
        btnL.setPreferredSize(new java.awt.Dimension(40, 40));
        btnL.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLActionPerformed(evt);
            }
        });

        btnB.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        btnB.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/g5/image/Back.png"))); // NOI18N
        btnB.setPreferredSize(new java.awt.Dimension(40, 40));
        btnB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBActionPerformed(evt);
            }
        });

        btnF.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        btnF.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/g5/image/First.png"))); // NOI18N
        btnF.setPreferredSize(new java.awt.Dimension(40, 40));
        btnF.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnFActionPerformed(evt);
            }
        });

        jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/g5/image/Image.png"))); // NOI18N
        jButton2.setPreferredSize(new java.awt.Dimension(50, 50));
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnF, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnThem, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnSua, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnB, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(btnXoa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnN, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(btnMoi, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnL, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(57, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(btnThem, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnSua, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnMoi, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnXoa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnL, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnN, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnB, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnF, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 0, 0))
        );

        jLabel12.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel12.setText("Trạng thái:");

        buttonGroup3.add(rdoLamViec);
        rdoLamViec.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        rdoLamViec.setSelected(true);
        rdoLamViec.setText("Làm việc");
        rdoLamViec.setPreferredSize(new java.awt.Dimension(71, 21));

        buttonGroup3.add(rdoNgi);
        rdoNgi.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        rdoNgi.setText("Nghỉ việc");

        jLabel11.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel11.setText("Email:");

        buttonGroup2.add(rdoChu);
        rdoChu.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        rdoChu.setText("Chủ");

        txtMatKhau.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtMatKhau.setPreferredSize(new java.awt.Dimension(7, 30));

        txtXNMatKhau.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtXNMatKhau.setPreferredSize(new java.awt.Dimension(7, 30));

        txtSDT.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtSDT.setPreferredSize(new java.awt.Dimension(7, 30));

        txtEmail.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtEmail.setPreferredSize(new java.awt.Dimension(7, 30));

        txtDiaChi.setColumns(20);
        txtDiaChi.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtDiaChi.setRows(5);
        jScrollPane1.setViewportView(txtDiaChi);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(200, 200, 200)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txtHinh, javax.swing.GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(30, 30, 30)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel11))
                .addGap(20, 20, 20)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(txtHoTen, javax.swing.GroupLayout.DEFAULT_SIZE, 294, Short.MAX_VALUE)
                            .addComponent(txtMatKhau, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(txtXNMatKhau, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(txtSDT, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(txtEmail, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(txtMaNV, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(43, 43, 43)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel12)
                                    .addComponent(jLabel10))
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel2Layout.createSequentialGroup()
                                        .addGap(18, 18, 18)
                                        .addComponent(rdoLamViec, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(20, 20, 20)
                                        .addComponent(rdoNgi))
                                    .addGroup(jPanel2Layout.createSequentialGroup()
                                        .addGap(17, 17, 17)
                                        .addComponent(jScrollPane1))))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel9)
                                    .addComponent(jLabel8)
                                    .addComponent(jLabel7))
                                .addGap(20, 20, 20)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel2Layout.createSequentialGroup()
                                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(rdoNV)
                                            .addComponent(rdoNam))
                                        .addGap(20, 20, 20)
                                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(rdoNu)
                                            .addGroup(jPanel2Layout.createSequentialGroup()
                                                .addComponent(rdoQL)
                                                .addGap(20, 20, 20)
                                                .addComponent(rdoChu, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE))))
                                    .addComponent(txtNgaySinh, javax.swing.GroupLayout.PREFERRED_SIZE, 280, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                    .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(26, 26, 26))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(26, 26, 26)
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(16, 16, 16)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel2)
                                    .addComponent(txtMaNV, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel7)
                                    .addComponent(rdoNam)
                                    .addComponent(rdoNu))
                                .addGap(15, 15, 15)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(txtHoTen, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel3)
                                    .addComponent(jLabel8)
                                    .addComponent(rdoNV)
                                    .addComponent(rdoQL)))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(65, 65, 65)
                                .addComponent(rdoChu)))
                        .addGap(15, 15, 15)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel4)
                                .addComponent(txtMatKhau, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel9))
                            .addComponent(txtNgaySinh, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(15, 15, 15)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(txtXNMatKhau, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel5)
                                    .addComponent(jLabel10))
                                .addGap(15, 15, 15)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(txtSDT, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel6)))
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(15, 15, 15)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtEmail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel11)
                            .addComponent(jLabel12)
                            .addComponent(rdoLamViec, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(rdoNgi))))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtHinh, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        txtNgaySinh.setDateFormatString("dd/MM/yyyy");
        txtNgaySinh.setLocale(new Locale("vi", "VN"));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void txtTimKiemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtTimKiemActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtTimKiemActionPerformed

    private void tblNhanVienMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblNhanVienMouseClicked
        // TODO add your handling code here:
        setForm(getTable(-1));
    }//GEN-LAST:event_tblNhanVienMouseClicked

    private void btnThemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThemActionPerformed
        // TODO add your handling code here:
        insertNV();


    }//GEN-LAST:event_btnThemActionPerformed

    private void btnSuaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSuaActionPerformed
        // TODO add your handling code here:
        updateNV();

    }//GEN-LAST:event_btnSuaActionPerformed

    private void btnXoaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXoaActionPerformed
        // TODO add your handling code here:
        deleteNV();

    }//GEN-LAST:event_btnXoaActionPerformed

    private void btnMoiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMoiActionPerformed
        // TODO add your handling code here:
        clear(new NhanVien());

    }//GEN-LAST:event_btnMoiActionPerformed

    private void lblHinhMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblHinhMouseClicked
        // TODO add your handling code here:
        selectImage();

    }//GEN-LAST:event_lblHinhMouseClicked

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
        selectImage();
    }//GEN-LAST:event_jButton2ActionPerformed

    private void btnNActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNActionPerformed
        // TODO add your handling code here:

        nextTable();

    }//GEN-LAST:event_btnNActionPerformed

    private void btnLActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLActionPerformed
        // TODO add your handling code here:
        lastTable();
    }//GEN-LAST:event_btnLActionPerformed

    private void btnBActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBActionPerformed
        // TODO add your handling code here:
        backTable();
    }//GEN-LAST:event_btnBActionPerformed

    private void btnFActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFActionPerformed
        // TODO add your handling code here:
        firstTable();
    }//GEN-LAST:event_btnFActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnB;
    private javax.swing.JButton btnF;
    private javax.swing.JButton btnL;
    private javax.swing.JButton btnMoi;
    private javax.swing.JButton btnN;
    private javax.swing.JButton btnSua;
    private javax.swing.JButton btnThem;
    private javax.swing.JButton btnXoa;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.ButtonGroup buttonGroup2;
    private javax.swing.ButtonGroup buttonGroup3;
    private javax.swing.ButtonGroup buttonGroup4;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
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
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane10;
    private javax.swing.JLabel lblHinh;
    private javax.swing.JRadioButton rdoChu;
    private javax.swing.JRadioButton rdoLamViec;
    private javax.swing.JRadioButton rdoNV;
    private javax.swing.JRadioButton rdoNam;
    private javax.swing.JRadioButton rdoNgi;
    private javax.swing.JRadioButton rdoNu;
    private javax.swing.JRadioButton rdoQL;
    private javax.swing.JRadioButton rdoTKma;
    private javax.swing.JRadioButton rdoTKten;
    private javax.swing.JTable tblNhanVien;
    private javax.swing.JTextArea txtDiaChi;
    private javax.swing.JTextField txtEmail;
    private javax.swing.JTextField txtHinh;
    private javax.swing.JTextField txtHoTen;
    private javax.swing.JTextField txtMaNV;
    private javax.swing.JTextField txtMatKhau;
    private com.toedter.calendar.JDateChooser txtNgaySinh;
    private javax.swing.JTextField txtSDT;
    private javax.swing.JTextField txtTimKiem;
    private javax.swing.JTextField txtXNMatKhau;
    // End of variables declaration//GEN-END:variables
}
