/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.g5.form;

import com.g5.entity.NhanVien;
import com.g5.entityDAO.NhanVienDAOImpl;
import com.g5.ui.Main;
import com.g5.util.Auth;
import com.g5.util.TextMes;
import com.g5.util.XDate;
import com.g5.util.XImage;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Image;
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
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

/**
 *
 * @author anhba
 */
public class NhanVienJPanel extends javax.swing.JPanel {

    final int w = 198;
    final int h = 224;
    int index = 0;
    boolean selected = false;

    /**
     * Creates new form Form1
     */
    public NhanVienJPanel() {
        initComponents();
        setOpaque(false);
        fillTable();
        fillForm();
        tblNhanVien.getTableHeader().setFont(new Font("Tohama", 1, 16));
        tblNhanVien.setRowMargin(1);
        btnThem.setEnabled(false);
        btnXoa.setEnabled(false);
        btnSua.setEnabled(false);
    }

    NhanVienDAOImpl nvDAO = new NhanVienDAOImpl();

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

    }

    boolean checkItMe() {
        return String.valueOf(Auth.user.getMaNV()).trim().equalsIgnoreCase(txtMaNV.getText().trim()) ? true : false;
    }

    void fillForm() {
        txtMaNV.setText(String.valueOf(nvDAO.getMaxMaNV() + 1));
        rdoChu.setVisible(false);
        if (Auth.user.getVaitro() == 2) {
            rdoQL.setVisible(true);
        }
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

    NhanVien getTable() {
        int MaNV = -1;
  
       if(selected == false){
          index = tblNhanVien.getSelectedRow();
      
         MaNV = Integer.parseInt(tblNhanVien.getValueAt(index, 1).toString());
       
         }
         if(selected == true){
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
        
        return MaNV != -1?nv:null;
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
        TextMes.Alert(this, "Thêm nhân viên mới thành công");
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
        
        if(vaitroTK == 1){
        rdoNV.setEnabled(true);
        rdoQL.setEnabled(false);
        rdoQL.setVisible(false);
        rdoChu.setVisible(false);
         rdoNV.setSelected(true);
        }
        if(vaitroTK == 2){
         rdoNV.setEnabled(true);
        rdoQL.setEnabled(true);
        rdoQL.setVisible(true);
        rdoChu.setVisible(false);
         rdoNV.setSelected(true);
        }
        
       
        
        btnSua.setEnabled(false);
        btnThem.setEnabled(true);
        btnXoa.setEnabled(false);
        rdoLamViec.setSelected(true);
        rdoNam.setSelected(true);
    }



    void setForm(NhanVien nv) {
        btnThem.setEnabled(false);
        btnMoi.setEnabled(true);
        txtMaNV.setText(nv.getMaNV() + "");
        txtHoTen.setText(nv.getHoTen());
        txtMatKhau.setText(nv.getMatkhau());
        txtXNMatKhau.setText(nv.getMatkhau());
        txtEmail.setText(nv.getEmail());
        txtSDT.setText(nv.getSDT());
        
        if (nv.isGioitinh()) {
            rdoNam.setSelected(true);
        } else {
            rdoNu.setSelected(false);
        }

        txtDiaChi.setText(nv.getDiachi());

        if (nv.getTrangthai()) {
            rdoLamViec.setSelected(true);
        } else {
            rdoNgi.setSelected(true);
        }

        txtNgaySinh.setDate(nv.getNgaysinh());
        lblHinh.setToolTipText(nv.getHinh());
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
            if(vaitro == 0){
                rdoChu.setVisible(false);
            rdoNV.setSelected(true);
            }
            if(vaitro == 1){
                rdoChu.setVisible(false);
            rdoQL.setSelected(true);
            }
            if(vaitro == 2){
                rdoChu.setVisible(true);
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
            switch(vaitroTK){
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
                   }
                   if(vaitro == 2 && nv.getMaNV() != maNVTK){
                   btnXoa.setEnabled(false);
                   btnSua.setEnabled(false);
                   btnThem.setEnabled(false);
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
        TextMes.Alert(this, "Đã xóa nhân viên: " + id);
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
        TextMes.Alert(this, "Cập nhật thành công");
    }
    

    
    void firstTable(){
        if(tblNhanVien.getRowCount() == 0){
            clear(new NhanVien());
            return;
        }
    index = 0;

       tblNhanVien.setRowSelectionInterval(index, index);

        
    selected = true;
        setForm(getTable());
    }
    void nextTable(){
                if(tblNhanVien.getRowCount() == 0){
            clear(new NhanVien());
            return;
        }
    index++;
    if(index>=tblNhanVien.getRowCount()){
    index = tblNhanVien.getRowCount()-1;
    }
    tblNhanVien.setRowSelectionInterval(index, index);
        selected = true;
        setForm(getTable());
    }
    void backTable(){
                if(tblNhanVien.getRowCount() == 0){
            clear(new NhanVien());
            return;
        }
    index--;
    if(index<=0){
    index = 0;
    }
    tblNhanVien.setRowSelectionInterval(index, index);
        selected = true;
        setForm(getTable());
    }
    void lastTable(){
                if(tblNhanVien.getRowCount() == 0){
            clear(new NhanVien());
            return;
        }
    index = tblNhanVien.getRowCount()-1;
    tblNhanVien.setRowSelectionInterval(index, index);
        selected = true;
        setForm(getTable());
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
        jPanel1 = new javax.swing.JPanel();
        jScrollPane10 = new javax.swing.JScrollPane();
        tblNhanVien = new javax.swing.JTable();
        txtTimKiem = new javax.swing.JTextField();
        btnTiemKiem = new javax.swing.JButton();
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
        txtMatKhau = new javax.swing.JPasswordField();
        txtXNMatKhau = new javax.swing.JPasswordField();
        txtSDT = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        txtDiaChi = new javax.swing.JTextField();
        txtHinh = new javax.swing.JTextField();
        txtNgaySinh = new com.toedter.calendar.JDateChooser();
        jButton2 = new javax.swing.JButton();
        rdoNV = new javax.swing.JRadioButton();
        rdoQL = new javax.swing.JRadioButton();
        rdoNam = new javax.swing.JRadioButton();
        rdoNu = new javax.swing.JRadioButton();
        jPanel4 = new javax.swing.JPanel();
        btnMoi = new javax.swing.JButton();
        btnXoa = new javax.swing.JButton();
        btnSua = new javax.swing.JButton();
        btnThem = new javax.swing.JButton();
        jButton6 = new javax.swing.JButton();
        jButton7 = new javax.swing.JButton();
        jButton8 = new javax.swing.JButton();
        jButton9 = new javax.swing.JButton();
        jLabel12 = new javax.swing.JLabel();
        rdoLamViec = new javax.swing.JRadioButton();
        rdoNgi = new javax.swing.JRadioButton();
        jLabel11 = new javax.swing.JLabel();
        txtEmail = new javax.swing.JTextField();
        rdoChu = new javax.swing.JRadioButton();

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
            tblNhanVien.getColumnModel().getColumn(5).setPreferredWidth(200);
        }

        txtTimKiem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtTimKiemActionPerformed(evt);
            }
        });

        btnTiemKiem.setText("Tìm Kiếm");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(txtTimKiem, javax.swing.GroupLayout.PREFERRED_SIZE, 377, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnTiemKiem)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jScrollPane10))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txtTimKiem)
                    .addComponent(btnTiemKiem, javax.swing.GroupLayout.DEFAULT_SIZE, 30, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
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
            .addComponent(lblHinh, javax.swing.GroupLayout.DEFAULT_SIZE, 224, Short.MAX_VALUE)
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

        txtMatKhau.setText("123");

        txtXNMatKhau.setText("123");

        jLabel7.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel7.setText("Giới tính:");

        jLabel8.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel8.setText("Vai trò:");

        jLabel9.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel9.setText("Ngày sinh:");

        jLabel10.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel10.setText("Địa chỉ:");

        txtHinh.setEditable(false);
        txtHinh.setText("Chưa chọn ảnh");

        jButton2.setText("Chọn ảnh");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        buttonGroup2.add(rdoNV);
        rdoNV.setSelected(true);
        rdoNV.setText("Nhân viên");

        buttonGroup2.add(rdoQL);
        rdoQL.setText("Quản lí");

        buttonGroup1.add(rdoNam);
        rdoNam.setSelected(true);
        rdoNam.setText("Nam");

        buttonGroup1.add(rdoNu);
        rdoNu.setText("Nữ");

        btnMoi.setText("Mới");
        btnMoi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMoiActionPerformed(evt);
            }
        });

        btnXoa.setText("Xóa");
        btnXoa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXoaActionPerformed(evt);
            }
        });

        btnSua.setText("Sửa");
        btnSua.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSuaActionPerformed(evt);
            }
        });

        btnThem.setText("Thêm");
        btnThem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThemActionPerformed(evt);
            }
        });

        jButton6.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        jButton6.setText("|<");
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });

        jButton7.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        jButton7.setText("<");
        jButton7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton7ActionPerformed(evt);
            }
        });

        jButton8.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        jButton8.setText(">");
        jButton8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton8ActionPerformed(evt);
            }
        });

        jButton9.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        jButton9.setText(">|");
        jButton9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton9ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(btnThem)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnSua)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnXoa)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnMoi))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jButton6)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton7)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton8)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton9)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnThem)
                    .addComponent(btnSua)
                    .addComponent(btnMoi)
                    .addComponent(btnXoa))
                .addGap(18, 18, 18)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton6)
                    .addComponent(jButton7)
                    .addComponent(jButton8)
                    .addComponent(jButton9))
                .addContainerGap(30, Short.MAX_VALUE))
        );

        jLabel12.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel12.setText("Trạng thái:");

        buttonGroup3.add(rdoLamViec);
        rdoLamViec.setSelected(true);
        rdoLamViec.setText("Làm việc");
        rdoLamViec.setPreferredSize(new java.awt.Dimension(71, 21));

        buttonGroup3.add(rdoNgi);
        rdoNgi.setText("Nghỉ việc");

        jLabel11.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel11.setText("Email:");

        buttonGroup2.add(rdoChu);
        rdoChu.setText("Chủ");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(50, 50, 50)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton2)
                    .addComponent(txtHinh, javax.swing.GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE))
                .addGap(30, 30, 30)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel2)
                                .addGap(88, 88, 88)
                                .addComponent(txtMaNV, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel3)
                                .addGap(87, 87, 87)
                                .addComponent(txtHoTen, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel4)
                                .addGap(71, 71, 71)
                                .addComponent(txtMatKhau, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel5)
                                .addGap(48, 48, 48)
                                .addComponent(txtXNMatKhau, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel6)
                                .addGap(103, 103, 103)
                                .addComponent(txtSDT, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel11)
                                .addGap(96, 96, 96)
                                .addComponent(txtEmail, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(50, 50, 50)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel7)
                            .addComponent(jLabel8)
                            .addComponent(jLabel9)
                            .addComponent(jLabel10)
                            .addComponent(jLabel12))
                        .addGap(15, 15, 15)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtNgaySinh, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtDiaChi, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(rdoLamViec, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(14, 14, 14)
                                .addComponent(rdoNgi))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(rdoNam)
                                    .addComponent(rdoNV))
                                .addGap(19, 19, 19)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(rdoNu)
                                    .addGroup(jPanel2Layout.createSequentialGroup()
                                        .addComponent(rdoQL)
                                        .addGap(19, 19, 19)
                                        .addComponent(rdoChu, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                        .addGap(0, 12, Short.MAX_VALUE))
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, 226, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(70, 70, 70)
                        .addComponent(txtNgaySinh, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(16, 16, 16)
                        .addComponent(txtDiaChi, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(15, 15, 15)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(rdoNgi)
                            .addComponent(rdoLamViec, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel7)
                            .addComponent(rdoNam)
                            .addComponent(rdoNu))
                        .addGap(14, 14, 14)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel8)
                            .addComponent(rdoNV)
                            .addComponent(rdoQL)
                            .addComponent(rdoChu))
                        .addGap(14, 14, 14)
                        .addComponent(jLabel9)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel10)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel12))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(1, 1, 1)
                                .addComponent(txtMaNV, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(15, 15, 15)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel3)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(1, 1, 1)
                                .addComponent(txtHoTen, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(15, 15, 15)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel4)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(1, 1, 1)
                                .addComponent(txtMatKhau, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(15, 15, 15)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel5)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(1, 1, 1)
                                .addComponent(txtXNMatKhau, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(15, 15, 15)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel6)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(1, 1, 1)
                                .addComponent(txtSDT, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(16, 16, 16)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel11)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(2, 2, 2)
                                .addComponent(txtEmail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addGap(10, 10, 10)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(txtHinh, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton2)))
                .addContainerGap(27, Short.MAX_VALUE))
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
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(50, 50, 50)
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void txtTimKiemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtTimKiemActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtTimKiemActionPerformed

    private void tblNhanVienMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblNhanVienMouseClicked
        // TODO add your handling code here:
        setForm(getTable());
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

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
        // TODO add your handling code here:
        firstTable();
       
    }//GEN-LAST:event_jButton6ActionPerformed

    private void jButton7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton7ActionPerformed
        // TODO add your handling code here:
        backTable();
     
    }//GEN-LAST:event_jButton7ActionPerformed

    private void jButton8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton8ActionPerformed
        // TODO add your handling code here:
        nextTable();
       
    }//GEN-LAST:event_jButton8ActionPerformed

    private void jButton9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton9ActionPerformed
        // TODO add your handling code here:
        lastTable();
      
    }//GEN-LAST:event_jButton9ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnMoi;
    private javax.swing.JButton btnSua;
    private javax.swing.JButton btnThem;
    private javax.swing.JButton btnTiemKiem;
    private javax.swing.JButton btnXoa;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.ButtonGroup buttonGroup2;
    private javax.swing.ButtonGroup buttonGroup3;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton7;
    private javax.swing.JButton jButton8;
    private javax.swing.JButton jButton9;
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
    private javax.swing.JScrollPane jScrollPane10;
    private javax.swing.JLabel lblHinh;
    private javax.swing.JRadioButton rdoChu;
    private javax.swing.JRadioButton rdoLamViec;
    private javax.swing.JRadioButton rdoNV;
    private javax.swing.JRadioButton rdoNam;
    private javax.swing.JRadioButton rdoNgi;
    private javax.swing.JRadioButton rdoNu;
    private javax.swing.JRadioButton rdoQL;
    private javax.swing.JTable tblNhanVien;
    private javax.swing.JTextField txtDiaChi;
    private javax.swing.JTextField txtEmail;
    private javax.swing.JTextField txtHinh;
    private javax.swing.JTextField txtHoTen;
    private javax.swing.JTextField txtMaNV;
    private javax.swing.JPasswordField txtMatKhau;
    private com.toedter.calendar.JDateChooser txtNgaySinh;
    private javax.swing.JTextField txtSDT;
    private javax.swing.JTextField txtTimKiem;
    private javax.swing.JPasswordField txtXNMatKhau;
    // End of variables declaration//GEN-END:variables
}
