package com.g5.ok;

import com.g5.entity.GiaSP;
import com.g5.entity.HoaDon;
import com.g5.entity.HoaDonChiTiet;
import com.g5.entity.SanPham;
import com.g5.entityDAO.HoaDonChiTietDAO;
import com.g5.entityDAO.HoaDonDAO;
import com.g5.entityDAO.KhuyenMaiDAO;
import com.g5.entityDAO.SanPhamChiTietDAO;
import com.g5.entityDAO.SanPhamDao;
import com.g5.util.TextMes;
import com.g5.util.Validate;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author anhba
 */
public class ManChinh extends javax.swing.JFrame {

    PanelSanPham panelView = new PanelSanPham();
    public static String MaSP = "";
    //   static DefaultListModel model = new DefaultListModel();
    static DefaultTableModel modelTblGioHang = new DefaultTableModel(new Object[]{"STT", "Mã SP", "Tên SP", "Số lượng", "Size", "Giá", "Tổng tiền"}, 0) {
        @Override
        public boolean isCellEditable(int row, int column) {
            // Chỉnh sửa thuộc tính này để không cho phép chỉnh sửa tất cả các ô trong hàng
            return false;
        }
    };

    static Set<String> uniqueItems = new HashSet<>();
    List<SanPham> list;
    List<ItemSanPham> listItemSP = new ArrayList<>();

    /**
     * Creates new form NewJFrame
     */
    void updateTTandSL() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                //   throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                for (int i = 0;;) {
                    try {
                        Thread.sleep(500);
                    } catch (Exception e) {

                    }
                    //  System.out.println(soluongMua);
                    txtTongSanPham.setText(soluongMua + "");
                    loadTongTien();
                }
            }
        }).start();
    }
    public boolean loadFull = false;

    void FillTableHoaDonChiTet(int MaHD) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int j = 0;;) {
                    try {
                        Thread.sleep(200);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    if (loadFull == true) {
                        DefaultTableModel model = (DefaultTableModel) tblGioHang.getModel();
                        model.setRowCount(0);
                        float tongtienThanhToan = 0;
                        List<HoaDonChiTiet> list2 = daoHDCT.getByID(MaHD);
                        int i = 0;
                        danhSachSanPham.removeAll(danhSachSanPham);
                        if (list2.size() == 0 || list2 == null) {
                            for (ItemSanPham item : listItemSP) {

                                danhSachSanPham.add(item);
                                //          System.out.println("add0");

                            }
                            loadList();
                            break;
                        } else {
                            for (HoaDonChiTiet hd : list2) {// danh sách các sản phẩm trong giỏ hàng

                                //      SanPham sp = daoSP.getByID(hd.getMaSP());
                                float tt = 0;
//                            float giagiam = 0;
                                float gia = hd.getGia();

                                int soluong = hd.getSoluong();
//                            float a = daoKM.getPTKMcuaSP(hd.getMaSP());
//                            
//                            if (a != 0) {
//                                giagiam = gia - (gia * (a / 100));
//                                tt = giagiam * soluong;
//                            } else {
//                                giagiam = gia;
//                                tt = giagiam * soluong;
//                            }
                                tt = gia * soluong;

                                //      System.out.println(listItemSP.size());
                                //     System.out.println("anh bao");
                                for (ItemSanPham item : listItemSP) {

                                    if (item.getMaSP() == hd.getMaSP()) {

                                        item.setSelectdSP(true);
                                        item.setSoluongMua(soluong + "");
                                        item.setSizeMua(hd.getSize());
                                        item.setGiamua(gia + "");
                                        item.setTongtienMua(tt + "");
                                        danhSachSanPham.add(item);
                                        //          System.out.println("add0");
                                    }else{
                                    danhSachSanPham.add(item);
                                    }

                                }

                            }
                            loadList();
                            break;
                        }

                        //      loadFull = false;
                        //     System.out.println("hello");
                    }

                }
            }
        }).start();

    }

    void chinhDoDaiCot() {
        TableColumnModel columnModel = tblGioHang.getColumnModel();
        TableColumn column = columnModel.getColumn(0);
        column.setPreferredWidth(20);

        TableColumnModel columnModel1 = tblGioHang.getColumnModel();
        TableColumn column1 = columnModel.getColumn(1);
        column1.setPreferredWidth(30);

        TableColumnModel columnModel2 = tblGioHang.getColumnModel();
        TableColumn column2 = columnModel.getColumn(2);
        column2.setPreferredWidth(150);

        TableColumnModel columnModel4 = tblGioHang.getColumnModel();
        TableColumn column4 = columnModel.getColumn(3);
        column4.setPreferredWidth(50);

        TableColumnModel columnModel5 = tblGioHang.getColumnModel();
        TableColumn column5 = columnModel.getColumn(4);
        column5.setPreferredWidth(50);

    }
    public int mahdFILL = 0;

    public ManChinh(int mahd) {
        initComponents();
        setLocationRelativeTo(null);
        this.mahdFILL = mahd;
        jScrollPane1.setViewportView(panelView);
        loadAll();// tải ds sp vào list
        loadSanPham1();// bắt đàu thêm vào panel
        tblGioHang.setModel(modelTblGioHang);
        FillTableHoaDonChiTet(mahdFILL);

        chinhDoDaiCot();
        loadTongTien();
        updateTTandSL();
        txtMaHD.setText(mahdFILL + "");
        txtTimKiem.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                //    loadSanPhamTimKiem();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                //    loadSanPhamTimKiem();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                //  timKiem();  //   throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
            }
        });

        txtSoLuong.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                fillThongTinSP();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                fillThongTinSP();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                //  timKiem();  //   throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
            }
        });
    }

    public void loadAll() {
        list = daoSP.getAll();
    }

    public static void addUniqueItem(String item) {
        // Kiểm tra xem chuỗi đã tồn tại trong Set chưa

        if (!uniqueItems.contains(item)) {
            // Nếu chưa tồn tại, thêm vào Set

            uniqueItems.add(item);
        }

    }

    public static void loadList() {
        //   SanPhamDao daoSP = new SanPhamDao();

        modelTblGioHang.setRowCount(0);

        int i = 0;
        //hello");
        //   System.out.println(danhSachSanPham.size());
        for (ItemSanPham sanPhamClone : danhSachSanPham) {
            //    System.out.println(sanPhamClone.getTenSP()+" bl: "+sanPhamClone.getSelectdSP());
            if (sanPhamClone.getSelectdSP()) {

                modelTblGioHang.addRow(new Object[]{++i, sanPhamClone.getMaSP(), sanPhamClone.getTenSP(), sanPhamClone.getSoluongMua(), sanPhamClone.getSizeMua(), Validate.chuyenGiaVietNam(Float.parseFloat(sanPhamClone.getGiamua())), Validate.chuyenGiaVietNam(Float.parseFloat(sanPhamClone.getTongtienMua()))});

            }
        }

    }

    public static String taoChuoiKhoangTrang(int soLuong) {
        // Kiểm tra nếu số lượng là âm hoặc bằng 0
        if (soLuong <= 0) {
            return ""; // Trả về chuỗi rỗng nếu không cần dấu khoảng trắng
        }

        // Khởi tạo chuỗi
        StringBuilder chuoiKhoangTrang = new StringBuilder();

        // Thêm dấu khoảng trắng vào chuỗi
        for (int i = 0; i < soLuong; i++) {
            chuoiKhoangTrang.append(" ");
        }

        return chuoiKhoangTrang.toString();
    }

    private static String addGachNgang(String input) {
        StringBuilder result = new StringBuilder();

        // Duyệt qua từng ký tự trong chuỗi
        for (int i = 0; i < input.length(); i++) {
            // Thêm ký tự vào chuỗi kết quả và ký tự gạch ngang Unicode
            result.append(input.charAt(i)).append('\u0336');
        }

        // Chuyển đổi StringBuilder thành String và trả về
        return result.toString();
    }

    public int getMaSP() {
        return Integer.parseInt(txtMaSP.getText().trim());
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        btnLoad = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        txtMaSP = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        txtTenSP = new javax.swing.JTextField();
        jButton3 = new javax.swing.JButton();
        cboSize = new javax.swing.JComboBox<>();
        jLabel4 = new javax.swing.JLabel();
        txtSoLuong = new javax.swing.JTextField();
        btnCong = new javax.swing.JButton();
        btnTru = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        txtKhuyenMai = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        txtGia = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        txtThanhTien = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jLabel8 = new javax.swing.JLabel();
        txtTonKho = new javax.swing.JTextField();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        tblGioHang = new javax.swing.JTable();
        txt = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jLabel10 = new javax.swing.JLabel();
        txtTongSanPham = new javax.swing.JTextField();
        jButton2 = new javax.swing.JButton();
        txtTongTienMua = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        txtMaHD = new javax.swing.JTextField();
        cboLoai = new javax.swing.JComboBox<>();
        jLabel9 = new javax.swing.JLabel();
        txt1 = new javax.swing.JLabel();
        txtTimKiem = new javax.swing.JTextField();
        btnTK = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jScrollPane1.setBackground(new java.awt.Color(255, 255, 255));
        jScrollPane1.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jScrollPane1MouseClicked(evt);
            }
        });

        btnLoad.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        btnLoad.setText("Tải DS");
        btnLoad.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLoadActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel1.setText("Mã sản phẩm");

        txtMaSP.setEditable(false);
        txtMaSP.setPreferredSize(new java.awt.Dimension(57, 30));

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel2.setText("Size sản phẩm");

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel3.setText("Tên sản phẩm");

        txtTenSP.setEditable(false);
        txtTenSP.setPreferredSize(new java.awt.Dimension(57, 30));

        jButton3.setText("Chọn SL");
        jButton3.setPreferredSize(new java.awt.Dimension(53, 30));
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        cboSize.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Chưa chọn sản phẩm" }));
        cboSize.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboSizeActionPerformed(evt);
            }
        });

        jLabel4.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel4.setText("Số lượng");

        txtSoLuong.setEditable(false);
        txtSoLuong.setText("1");

        btnCong.setText("+");
        btnCong.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCongActionPerformed(evt);
            }
        });

        btnTru.setText("-");
        btnTru.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTruActionPerformed(evt);
            }
        });

        jLabel5.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel5.setText("Khuyến mãi");

        txtKhuyenMai.setEditable(false);
        txtKhuyenMai.setPreferredSize(new java.awt.Dimension(57, 30));

        jLabel6.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel6.setText("Giá");

        txtGia.setEditable(false);
        txtGia.setPreferredSize(new java.awt.Dimension(57, 30));

        jLabel7.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel7.setText("Thành tiền");

        txtThanhTien.setEditable(false);
        txtThanhTien.setPreferredSize(new java.awt.Dimension(57, 30));

        jButton1.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jButton1.setText("Cập nhật");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jLabel8.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel8.setText("Số lựng còn");

        txtTonKho.setEditable(false);
        txtTonKho.setPreferredSize(new java.awt.Dimension(57, 30));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2)
                            .addComponent(jLabel4))
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(122, 122, 122)
                                .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(12, 12, 12)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(cboSize, javax.swing.GroupLayout.PREFERRED_SIZE, 220, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                        .addGroup(jPanel1Layout.createSequentialGroup()
                                            .addComponent(btnCong)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(btnTru))
                                        .addComponent(txtSoLuong, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))))))
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                            .addComponent(jLabel1)
                            .addGap(18, 18, 18)
                            .addComponent(txtMaSP, javax.swing.GroupLayout.PREFERRED_SIZE, 220, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(jPanel1Layout.createSequentialGroup()
                            .addComponent(jLabel3)
                            .addGap(13, 13, 13)
                            .addComponent(txtTenSP, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel5)
                            .addComponent(jLabel6))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtKhuyenMai, javax.swing.GroupLayout.PREFERRED_SIZE, 220, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtGia, javax.swing.GroupLayout.PREFERRED_SIZE, 220, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel7)
                            .addComponent(jLabel8))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jButton1)
                            .addComponent(txtThanhTien, javax.swing.GroupLayout.PREFERRED_SIZE, 220, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtTonKho, javax.swing.GroupLayout.PREFERRED_SIZE, 220, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(txtMaSP, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5)
                    .addComponent(txtKhuyenMai, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(txtTenSP, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6)
                    .addComponent(txtGia, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(cboSize, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7)
                    .addComponent(txtThanhTien, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(txtSoLuong, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtTonKho, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel8))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnCong)
                    .addComponent(btnTru)
                    .addComponent(jButton1))
                .addContainerGap(20, Short.MAX_VALUE))
        );

        tblGioHang.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        tblGioHang.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null}
            },
            new String [] {
                "STT", "Mã SP", "Tên SP", "Số lượng", "Size", "Giá", "Tổng tiền"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblGioHang.setFocusable(false);
        tblGioHang.setIntercellSpacing(new java.awt.Dimension(0, 0));
        tblGioHang.setOpaque(false);
        tblGioHang.setRowHeight(30);
        tblGioHang.setShowVerticalLines(false);
        tblGioHang.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblGioHangMouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(tblGioHang);
        if (tblGioHang.getColumnModel().getColumnCount() > 0) {
            tblGioHang.getColumnModel().getColumn(0).setPreferredWidth(50);
        }

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 681, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 505, Short.MAX_VALUE)
                .addContainerGap())
        );

        txt.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txt.setText("Nhấn để tải danh sách sản phẩm");

        jLabel10.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel10.setText("Tổng sản phẩm");

        txtTongSanPham.setEditable(false);
        txtTongSanPham.setPreferredSize(new java.awt.Dimension(57, 30));

        jButton2.setBackground(new java.awt.Color(0, 0, 255));
        jButton2.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jButton2.setForeground(new java.awt.Color(255, 255, 255));
        jButton2.setText("Xác nhận");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        txtTongTienMua.setEditable(false);
        txtTongTienMua.setPreferredSize(new java.awt.Dimension(57, 30));
        txtTongTienMua.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtTongTienMuaActionPerformed(evt);
            }
        });

        jLabel11.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel11.setText("Tổng tiền");

        jLabel12.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel12.setText("Mã HD");

        txtMaHD.setEditable(false);
        txtMaHD.setText("11");
        txtMaHD.setPreferredSize(new java.awt.Dimension(57, 30));

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(jLabel10)
                        .addComponent(jLabel11, javax.swing.GroupLayout.Alignment.LEADING))
                    .addComponent(jLabel12))
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txtTongSanPham, javax.swing.GroupLayout.DEFAULT_SIZE, 500, Short.MAX_VALUE)
                    .addComponent(txtTongTienMua, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtMaHD, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel12)
                    .addComponent(txtMaHD, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(txtTongSanPham, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtTongTienMua, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel11))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButton2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        cboLoai.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        cboLoai.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Tất cả" }));
        cboLoai.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboLoaiActionPerformed(evt);
            }
        });

        jLabel9.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel9.setText("Loại sản phẩm");

        txt1.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txt1.setText("Tìm kiếm");

        txtTimKiem.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        btnTK.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        btnTK.setText("Tìm");
        btnTK.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTKActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btnLoad)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txt, javax.swing.GroupLayout.PREFERRED_SIZE, 209, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(txt1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtTimKiem, javax.swing.GroupLayout.PREFERRED_SIZE, 225, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnTK)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel9)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(cboLoai, javax.swing.GroupLayout.PREFERRED_SIZE, 208, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 802, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnLoad)
                    .addComponent(txt)
                    .addComponent(cboLoai, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel9)
                    .addComponent(txt1)
                    .addComponent(txtTimKiem, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnTK))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 529, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addContainerGap())
                    .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
SanPhamDao daoSP = new SanPhamDao();
    KhuyenMaiDAO daoKM = new KhuyenMaiDAO();
    SanPhamChiTietDAO daoSPCT = new SanPhamChiTietDAO();
    static ItemSanPham daoSPclone = new ItemSanPham(null);
    static List<ItemSanPham> danhSachSanPham = new ArrayList<>();
    Set<String> listSetLoaiSP = new HashSet<>();
    private void btnLoadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLoadActionPerformed
        // TODO add your handling code here:
        loadSanPham2();
        txtTimKiem.setText("");

    }//GEN-LAST:event_btnLoadActionPerformed

    public void loadLoaiSP() {
        DefaultComboBoxModel modelCbo = (DefaultComboBoxModel) cboLoai.getModel();
        String cboChon = cboLoai.getSelectedItem().toString().trim();
        modelCbo.removeAllElements();
        modelCbo.addElement("Tất cả");
        for (String string : listSetLoaiSP) {
            modelCbo.addElement(string);
        }
        cboLoai.setSelectedItem(cboChon);
        loadXong = true;

    }

    public void loadSanPhamTimKiem() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                btnLoad.setEnabled(false);
                cboLoai.setEnabled(false);

                txtTimKiem.setEnabled(false);

                int index = 0;
                panelView.removeAll();
                txt.setText("Đang lấy danh sách sản phẩm");

                for (ItemSanPham i : listItemSP) {
                    try {
                        Thread.sleep(200);
                    } catch (Exception e) {

                    }
                    if (index == 3) {
                        txt.setText("Đang lấy danh sách sản phẩm");
                    }
                    txt.setText(txt.getText() + ".");

                    if (i.getTenSP().trim().toLowerCase().contains(txtTimKiem.getText().trim().toLowerCase())) {
                        panelView.add(i);
                    }

                    jScrollPane1.setViewportView(panelView);
                    //  danhSachSanPham.add(sp);
                    index++;
                }
                //   loadLoaiSP();
                btnLoad.setEnabled(true);
                cboLoai.setEnabled(true);
                txtTimKiem.setEnabled(true);
                btnTK.setEnabled(true);
                txt.setText("Đã tải xong");

                //  throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }
        }).start();

        //    n.loadImage();
        jScrollPane1.repaint();

    }

    public void loadSanPham1() {
        loadXong = false;
        new Thread(new Runnable() {
            @Override
            public void run() {
                btnLoad.setEnabled(false);
                cboLoai.setEnabled(false);
                btnTK.setEnabled(false);
                txtTimKiem.setEnabled(false);

                int index = 0;
                panelView.removeAll();
                txt.setText("Đang lấy danh sách sản phẩm");

                for (SanPham sanPham : list) {
                    try {
                        Thread.sleep(200);
                    } catch (Exception e) {

                    }
                    if (index == 3) {
                        txt.setText("Đang lấy danh sách sản phẩm");
                    }
                    txt.setText(txt.getText() + ".");

                    float giagiam = -1;
                    float gia = -1;
                    gia = daoSPCT.getGiaSP(sanPham.getMaSP());

                    if (daoKM.getPTKMcuaSP(sanPham.getMaSP()) != 0) {
                        try {
                            float a = daoKM.getPTKMcuaSP(sanPham.getMaSP());

                            giagiam = Math.round(gia - (gia * (a / 100)));
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }

                    ItemSanPham sp;

                    if (ManChinh.class.getResource("/com/g5/image/" + sanPham.getHinh().trim()) != null) {
                        sp = new ItemSanPham(new ImageIcon(ManChinh.class.getResource("/com/g5/image/" + sanPham.getHinh().trim())));
                    } else {
                        sp = new ItemSanPham(null);
                    }

                    sp.setThongTin(sanPham.getTenSP(), "\n\n" + (giagiam != -1 ? ItemSanPham.addGachNgang(String.valueOf(gia) + " VND") + "\n" + giagiam + " VND\n" : "\n" + gia + " VND\n") + sanPham.getLoaiSP());
                    // sp.setThongTin(sanPham.getTenSP()+ "\n\n", MaSP);
                    sp.setGia(gia);
                    sp.setMaSP(sanPham.getMaSP());
                    sp.setLoaisp(sanPham.getLoaiSP().trim());
                    if (giagiam != -1) {
                        sp.setGiagiam(giagiam);
                    }
                    List<HoaDonChiTiet> list = daoHDCT.getByID(mahdFILL);
                    for (HoaDonChiTiet hdct : list) {
                        if (sp.getMaSP() == hdct.getMaSP()) {
                            sp.setCheckBox(true);
                        }
                    }
                    //   System.out.println(sp.getGiagiam());
                    if (cboLoai.getSelectedItem().toString().trim().equalsIgnoreCase(sanPham.getLoaiSP().trim())) {
                        panelView.addSanPham(sp);
                        if (!checkListItemSP(sp.getMaSP())) {

                            listItemSP.add(sp);
                        }

                    } else {
                        if (cboLoai.getSelectedItem().toString().trim().equalsIgnoreCase("Tất cả")) {
                            panelView.addSanPham(sp);
                            if (!checkListItemSP(sp.getMaSP())) {

                                listItemSP.add(sp);
                            }
                        } else {
                            //    listItemSP.add(sp);
                            continue;
                        }

                    }

                    sp.addSuKien();
                    sp.setMaSP(sanPham.getMaSP());
                    listSetLoaiSP.add(sanPham.getLoaiSP());
                    jScrollPane1.setViewportView(panelView);
                    //    danhSachSanPham.add(sp);
                    index++;
                }
                loadFull = true;

                loadLoaiSP();
                btnLoad.setEnabled(true);
                cboLoai.setEnabled(true);
                txtTimKiem.setEnabled(true);
                btnTK.setEnabled(true);
                txt.setText("Đã tải xong");

                //  throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }
        }).start();

        //    n.loadImage();
        jScrollPane1.repaint();

    }

    public void loadSanPham2() {
        loadXong = false;
        new Thread(new Runnable() {
            @Override
            public void run() {
                btnLoad.setEnabled(false);
                cboLoai.setEnabled(false);
                btnTK.setEnabled(false);
                txtTimKiem.setEnabled(false);

                int index = 0;
                panelView.removeAll();
                txt.setText("Đang lấy danh sách sản phẩm");

                for (ItemSanPham i : listItemSP) {
                    try {
                        Thread.sleep(200);
                    } catch (Exception e) {

                    }
                    if (index == 3) {
                        txt.setText("Đang lấy danh sách sản phẩm");
                    }
                    txt.setText(txt.getText() + ".");

                    if (i.getLoaisp().trim().equalsIgnoreCase(cboLoai.getSelectedItem().toString().trim())) {
                        panelView.add(i);
                    }
                    if (cboLoai.getSelectedItem().toString().trim().equalsIgnoreCase("Tất cả")) {
                        panelView.add(i);
                    }

                    jScrollPane1.setViewportView(panelView);
                    //  danhSachSanPham.add(sp);
                    index++;
                }
                loadLoaiSP();
                btnLoad.setEnabled(true);
                cboLoai.setEnabled(true);
                txtTimKiem.setEnabled(true);
                btnTK.setEnabled(true);
                txt.setText("Đã tải xong");

                //  throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }
        }).start();

        //    n.loadImage();
        jScrollPane1.repaint();

    }

    public boolean checkListItemSP(int masp) {
        try {
            if (listItemSP.size() == 0 || listItemSP == null) {
                return false;
            }
        } catch (Exception e) {
            return false;
        }

        for (ItemSanPham i : listItemSP) {
            if (i.getMaSP() == masp) {
                return true;
            }
        }
        return false;
    }

    private void jScrollPane1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jScrollPane1MouseClicked
        // TODO add your handling code here:
        //   System.out.println("hello");
    }//GEN-LAST:event_jScrollPane1MouseClicked

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // TODO add your handling code here:
        if (txtMaSP.getText().trim().isEmpty()) {
            TextMes.Alert(null, "Vui lòng chọn sản phẩm");
            return;
        }
        int masp = 0;
        try {
            masp = Integer.parseInt(txtMaSP.getText().trim());
        } catch (Exception e) {
            e.printStackTrace();
        }
        //      System.out.println(listSP.getSelectedValue().replaceAll("[^0-9]", ""));
        SoLuongJDialog soluongJDialog = new SoLuongJDialog(null, true, masp);
        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0;;) {
                    try {
                        Thread.sleep(100);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    //    System.out.println("hi");
                    if (SoLuongJDialog.getSoLuongDC() != 0) {
                        //     SoLuongJDialog.Xuat();
                        txtSoLuong.setText(SoLuongJDialog.getSoLuongDC() + "");
                        SoLuongJDialog.setSoLuongDC(0);
                        soluongJDialog.setVisible(false);
                        break;
                    }
                }
                //     throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }
        }).start();
        soluongJDialog.setVisible(true);


    }//GEN-LAST:event_jButton3ActionPerformed


    private void btnCongActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCongActionPerformed
        // TODO add your handling code here:
        try {
            int i = 0;
            i = Integer.parseInt(txtSoLuong.getText().trim());
            txtSoLuong.setText((i + 1) + "");
        } catch (Exception e) {
            //    e.printStackTrace();
        }
        fillThongTinSP();
    }//GEN-LAST:event_btnCongActionPerformed

    private void btnTruActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTruActionPerformed
        // TODO add your handling code here:
        try {
            int i = 0;
            i = Integer.parseInt(txtSoLuong.getText().trim());
            if (i == 0) {
                txtSoLuong.setText("0");
            } else {
                txtSoLuong.setText((i - 1) + "");
            }

        } catch (Exception e) {
            //    e.printStackTrace();
        }
        fillThongTinSP();
    }//GEN-LAST:event_btnTruActionPerformed

    private void cboSizeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboSizeActionPerformed
        // TODO add your handling code here:
        fillThongTinSP();

    }//GEN-LAST:event_cboSizeActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        List<JTextField> textFieldList = List.of(txtMaSP, txtTenSP, txtSoLuong, txtGia, txtThanhTien);

        if (!Validate.kiemTraTrongText(textFieldList, null)) {
            return;
        }
        taiThongTinLenList();
      //  loadTongTien();
    }//GEN-LAST:event_jButton1ActionPerformed
    static List<String> listHoTro = new ArrayList<>();

    public void loadTongTien() {
        float tt = 0;
        for (ItemSanPham item : danhSachSanPham) {
            if (item.getSelectdSP()) {

                try {
                    if (item.getTongtienMua().equals("0")) {
                        continue;
                    }
                    tt += Float.parseFloat(item.getTongtienMua());

                } catch (Exception e) {
                    //    e.printStackTrace();
                }

            }
        }

        txtTongTienMua.setText(Validate.chuyenGiaVietNam(tt) + "");
        //   System.out.println(tienMua);
    }
    public static int soluongMua = 0;

    public static void loadSoLuong() {

        int sl = 0;
        for (ItemSanPham item : danhSachSanPham) {
            if (item.getSelectdSP()) {
                ++sl;
            }
        }

        soluongMua = sl;

    }

    public void taiThongTinLenList() {

        for (ItemSanPham sanPhamClone : danhSachSanPham) {
            if (sanPhamClone.getSelectdSP() && sanPhamClone.getMaSP() == Integer.parseInt(txtMaSP.getText().trim())) {
                sanPhamClone.setSoluongMua(txtSoLuong.getText().trim());
                sanPhamClone.setSizeMua(cboSize.getSelectedItem().toString().trim());
                sanPhamClone.setTongtienMua(Validate.chuyenGiaMacDinh(txtThanhTien.getText().trim()));
                sanPhamClone.setGiamua(Validate.chuyenGiaMacDinh(txtGia.getText().trim()));
                sanPhamClone.setPtkm(txtKhuyenMai.getText().trim());
            }
        }
        loadList();
    }

    boolean loadXong = false;
    private void cboLoaiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboLoaiActionPerformed
        // TODO add your handling code here:
        if (loadXong == true) {
            loadSanPham2();
        }

    }//GEN-LAST:event_cboLoaiActionPerformed

    private void btnTKActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTKActionPerformed
        // TODO add your handling code here:
        loadSanPhamTimKiem();
    }//GEN-LAST:event_btnTKActionPerformed

    private void tblGioHangMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblGioHangMouseClicked
        // TODO add your handling code here:
        if (tblGioHang.getRowCount() == 0) {
            return;
        }
        int masp = Integer.parseInt(tblGioHang.getValueAt(tblGioHang.getSelectedRow(), 1).toString());
        SanPham sp = daoSP.getByID(masp);
        txtTenSP.setText(sp.getTenSP());
        txtMaSP.setText(sp.getMaSP() + "");
        txtTonKho.setText(sp.getSoLuong() + "");
        if (tblGioHang.getValueAt(tblGioHang.getSelectedRow(), 3).toString().equals("")) {
            txtSoLuong.setText("1");
        } else {
            txtSoLuong.setText(tblGioHang.getValueAt(tblGioHang.getSelectedRow(), 3).toString());
        }

        DefaultComboBoxModel modelCbo = (DefaultComboBoxModel) cboSize.getModel();
        cboSize.removeAllItems();
        List<String> size = daoSP.getSize2(masp);
        for (String s : size) {
            modelCbo.addElement(s);
        }
        if (tblGioHang.getValueAt(tblGioHang.getSelectedRow(), 4).toString().equals("")) {
            cboSize.setSelectedIndex(0);
        } else {
            modelCbo.setSelectedItem(tblGioHang.getValueAt(tblGioHang.getSelectedRow(), 4).toString());
        }

        fillThongTinSP();
    }//GEN-LAST:event_tblGioHangMouseClicked

    private void txtTongTienMuaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtTongTienMuaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtTongTienMuaActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
        xacNhan();
    }//GEN-LAST:event_jButton2ActionPerformed
    HoaDonChiTietDAO daoHDCT = new HoaDonChiTietDAO();

    public void xacNhan() {
        for (ItemSanPham item : danhSachSanPham) {
            if (item.getSelectdSP() && !item.getTongtienMua().trim().equals("0")) {
                HoaDonChiTiet hdct = new HoaDonChiTiet();
                hdct.setMaSP(item.getMaSP());
                hdct.setGia(Float.parseFloat(item.getGiamua()));
                hdct.setPTkhuyenmai(Float.parseFloat(item.getPtkm().replace("%", "")));
                hdct.setSize(item.getSizeMua());
                hdct.setSoluong(Integer.parseInt(item.getSoluongMua()));
                hdct.setMaHD(Integer.parseInt(txtMaHD.getText().trim()));
                try {
                    if (daoHDCT.getSPbiTrung(hdct.getMaHD(), hdct.getMaSP(), hdct.getSize()) == 1) {
                        daoSP.upSoluongBiTrung(hdct.getSoluong(), hdct.getMaHD(), hdct.getMaSP(), hdct.getSize().trim());

                        //    TextMes.Alert(null, "Cập nhật chi tiết hóa đơn thành công");
                        //    return;
                    } else {
                        daoHDCT.create(hdct);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

//                HoaDonChiTiet hdct = new HoaDonChiTiet();
//                hdct.setMaSP(item.getMaSP());
//                hdct.setGia(Float.parseFloat(item.getGiamua()));
//                hdct.setPTkhuyenmai(Float.parseFloat(item.getPtkm().replace("%", "")));
//                hdct.setSize(item.getSizeMua());
//                hdct.setSoluong(Integer.parseInt(item.getSoluongMua()));
//                hdct.setMaHD(Integer.parseInt(txtMaHD.getText().trim()));
            }
        }
        TextMes.Alert(null, "Thêm thành công");
    }

    public void fillThongTinSP() {
        if (txtMaSP.getText().trim().isEmpty() || cboSize.getItemCount() == 0) {
            return;
        }

        try {
            int masp = 0;
            masp = Integer.parseInt(txtMaSP.getText().trim());
            float gia = 0;
            float giagiam = 0;
            float soluong = 0;
            float thanhtien = 0;
            NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(new Locale("vi", "VN"));

            // Định dạng số thành chuỗi
            gia = daoSP.getGiaByMaSPAndSize(masp, cboSize.getSelectedItem().toString().trim());

            float ptkm = daoKM.getPTKMcuaSP(masp);
            if (ptkm == 0) {
                txtKhuyenMai.setText("0%");
            } else {
                txtKhuyenMai.setText(String.valueOf(ptkm).replace(".0", "") + "%");
            }
//            System.out.println(Validate.chuyenGiaVietNam(gia) + "VN");
//            System.out.println(Validate.chuyenGiaMacDinh(Validate.chuyenGiaVietNam(gia)) +"MD");
            if (txtKhuyenMai.getText().trim() != "0%") {

                try {
                    float a = daoKM.getPTKMcuaSP(masp);
                    soluong = Integer.parseInt(txtSoLuong.getText().trim());
                    giagiam = gia - (gia * (a / 100));
                    String formattedNumber = currencyFormat.format((giagiam));
                    txtGia.setText(formattedNumber);
                    thanhtien = giagiam * soluong;
                    String formattedNumber2 = currencyFormat.format(thanhtien);
                    txtThanhTien.setText(formattedNumber2);
                } catch (Exception e) {
                    //  e.printStackTrace();
                }
            } else {

                String formattedNumber = currencyFormat.format(gia);
                txtGia.setText(formattedNumber + "");
                thanhtien = gia * soluong;
                String formattedNumber2 = currencyFormat.format(thanhtien);
                txtThanhTien.setText(formattedNumber2);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

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
            java.util.logging.Logger.getLogger(ManChinh.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ManChinh.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ManChinh.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ManChinh.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ManChinh(11).setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCong;
    private javax.swing.JButton btnLoad;
    private javax.swing.JButton btnTK;
    private javax.swing.JButton btnTru;
    private javax.swing.JComboBox<String> cboLoai;
    private javax.swing.JComboBox<String> cboSize;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JLabel jLabel1;
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
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTable tblGioHang;
    private javax.swing.JLabel txt;
    private javax.swing.JLabel txt1;
    private javax.swing.JTextField txtGia;
    private javax.swing.JTextField txtKhuyenMai;
    private javax.swing.JTextField txtMaHD;
    private javax.swing.JTextField txtMaSP;
    private javax.swing.JTextField txtSoLuong;
    private javax.swing.JTextField txtTenSP;
    private javax.swing.JTextField txtThanhTien;
    private javax.swing.JTextField txtTimKiem;
    private javax.swing.JTextField txtTonKho;
    private javax.swing.JTextField txtTongSanPham;
    private javax.swing.JTextField txtTongTienMua;
    // End of variables declaration//GEN-END:variables
}
