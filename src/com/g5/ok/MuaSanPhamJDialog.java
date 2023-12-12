/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.g5.ok;

import com.g5.entity.HoaDonChiTiet;
import com.g5.entity.SanPham;
import com.g5.entityDAO.HoaDonChiTietDAO;
import com.g5.entityDAO.KhuyenMaiDAO;
import com.g5.entityDAO.SanPhamChiTietDAO;
import com.g5.entityDAO.SanPhamDao;
import com.g5.util.TextMes;
import com.g5.util.Validate;
import com.g5.util.XImage;
import java.awt.Component;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import javax.swing.DefaultComboBoxModel;

import javax.swing.ImageIcon;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

/**
 *
 * @author anhba
 */
public class MuaSanPhamJDialog extends javax.swing.JDialog {
    
    PanelSanPham panelView = new PanelSanPham();
    public static String MaSP = "";
    //   static DefaultListModel model = new DefaultListModel();
    static DefaultTableModel modelTblMuaHang = new DefaultTableModel(new Object[]{"STT", "Mã SP", "Tên SP", "Số lượng", "Size", "Giá", "Tổng tiền"}, 0) {
        @Override
        public boolean isCellEditable(int row, int column) {
            // Chỉnh sửa thuộc tính này để không cho phép chỉnh sửa tất cả các ô trong hàng
            return false;
        }
    };
    
    static DefaultTableModel modelTblHDCT = new DefaultTableModel(new Object[]{"STT", "Mã SP", "Tên SP", "Số lượng", "Size", "Giá", "Tổng tiền"}, 0) {
        @Override
        public boolean isCellEditable(int row, int column) {
            // Chỉnh sửa thuộc tính này để không cho phép chỉnh sửa tất cả các ô trong hàng
            return false;
        }
    };
    
    static Set<String> uniqueItems = new HashSet<>();
    List<SanPham> list;
    List<ItemSanPham> listItemSP = new ArrayList<>();
    
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
                    txtTongSanPham.setText(tblHDCT.getRowCount() + "");
                    loadTongTien();
                }
            }
        }).start();
    }
    public boolean loadFull = false;
    public boolean loadList = false;
    
    void FillTableHoaDonChiTet(int MaHD) {
        
        modelTblHDCT.setRowCount(0);
        float tongtienThanhToan = 0;
        List<HoaDonChiTiet> list2 = new ArrayList<>();
        if (loadList == false) {
            list2.removeAll(list2);
            list2 = daoHDCT.getByID(MaHD);
            
            loadList = true;
        }
        
        int i = 0;
        //         danhSachSanPham.removeAll(danhSachSanPham);
        if (list2.size() == 0 || list2 == null) {
            return;
        }
        int stt = 0;
        for (HoaDonChiTiet hd : list2) {// danh sách các sản phẩm trong giỏ hàng

            //      SanPham sp = daoSP.getByID(hd.getMaSP());
            float tt = 0;
            
            float gia = hd.getGia();
            
            int soluong = hd.getSoluong();
            
            tt = gia * soluong;
            SanPham sp = daoSP.getByID(hd.getMaSP());
            
            modelTblHDCT.addRow(new Object[]{++stt, hd.getMaSP(), sp.getTenSP(), soluong, hd.getSize(), Validate.chuyenGiaVietNam(gia), Validate.chuyenGiaVietNam(tt)});
            
        }
        
        loadList();
        //    loadFull = false;
        loadList = false;

        //      loadFull = false;
        //     System.out.println("hello");
    }
    
    void chinhDoDaiCot() {
        TableColumnModel columnModel = tblHDCT.getColumnModel();
        TableColumn column = columnModel.getColumn(0);
        column.setPreferredWidth(20);
        
        TableColumnModel columnModel1 = tblHDCT.getColumnModel();
        TableColumn column1 = columnModel.getColumn(1);
        column1.setPreferredWidth(30);
        
        TableColumnModel columnModel2 = tblHDCT.getColumnModel();
        TableColumn column2 = columnModel.getColumn(2);
        column2.setPreferredWidth(150);
        
        TableColumnModel columnModel4 = tblHDCT.getColumnModel();
        TableColumn column4 = columnModel.getColumn(3);
        column4.setPreferredWidth(50);
        
        TableColumnModel columnModel5 = tblHDCT.getColumnModel();
        TableColumn column5 = columnModel.getColumn(4);
        column5.setPreferredWidth(50);
        
        TableColumnModel columnModel6 = tblMuaHang.getColumnModel();
        TableColumn column6 = columnModel6.getColumn(0);
        column6.setPreferredWidth(20);
        
        TableColumnModel columnModel7 = tblMuaHang.getColumnModel();
        TableColumn column7 = columnModel7.getColumn(1);
        column7.setPreferredWidth(30);
        
        TableColumnModel columnModel8 = tblMuaHang.getColumnModel();
        TableColumn column8 = columnModel8.getColumn(2);
        column8.setPreferredWidth(150);
        
        TableColumnModel columnModel9 = tblMuaHang.getColumnModel();
        TableColumn column9 = columnModel9.getColumn(3);
        column9.setPreferredWidth(50);
        
        TableColumnModel columnModel10 = tblMuaHang.getColumnModel();
        TableColumn column10 = columnModel10.getColumn(4);
        column10.setPreferredWidth(50);
        
    }
    public int mahdFILL = 0;

    /**
     * Creates new form MuaSanPhamJDialog
     */
    public MuaSanPhamJDialog(java.awt.Frame parent, boolean modal, int mahd) {
        super(parent, modal);
        initComponents();
        setLocationRelativeTo(null);
        setTitle("Hóa đơn chi tiết");
        setIconImage(XImage.getAppIcon());
        this.mahdFILL = mahd;
        jScrollPane1.setViewportView(panelView);
        loadAll();// tải ds sp vào list
        loadSanPham1();// bắt đàu thêm vào panel
        tblMuaHang.setModel(modelTblMuaHang);
        tblHDCT.setModel(modelTblHDCT);
        //   FillTableHoaDonChiTet(mahdFILL);

        chinhDoDaiCot();
        loadTongTien();
        updateTTandSL();
        resetForm();
        txtMaHD.setText(mahdFILL + "");
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
    
    public static void resetList() {
        //   SanPhamDao daoSP = new SanPhamDao();

        modelTblMuaHang.setRowCount(0);
        
        int i = 0;
        
        for (ItemSanPham sanPhamClone : danhSachSanPham) {
            
            sanPhamClone.setSelectdSP(false);
            sanPhamClone.setGiamua("0");
            sanPhamClone.setSoluongMua("0");
            sanPhamClone.setSizeMua("");
            sanPhamClone.setPtkm("0%");
            
        }
        
    }
    
    public static void loadList() {
        //   SanPhamDao daoSP = new SanPhamDao();

        modelTblMuaHang.setRowCount(0);
        
        int i = 0;
        
        for (ItemSanPham sanPhamClone : danhSachSanPham) {

            //    System.out.println(sanPhamClone.getTenSP()+" bl: "+sanPhamClone.getSelectdSP());
            if (sanPhamClone.getSelectdSP()) {
                
                modelTblMuaHang.addRow(new Object[]{++i, sanPhamClone.getMaSP(), sanPhamClone.getTenSP(), sanPhamClone.getSoluongMua(), sanPhamClone.getSizeMua(), Validate.chuyenGiaVietNam(Float.parseFloat(sanPhamClone.getGiamua())), Validate.chuyenGiaVietNam(Float.parseFloat(sanPhamClone.getTongtienMua()))});
                
            }
        }
        
    }
    SanPhamDao daoSP = new SanPhamDao();
    KhuyenMaiDAO daoKM = new KhuyenMaiDAO();
    SanPhamChiTietDAO daoSPCT = new SanPhamChiTietDAO();
    static ItemSanPham daoSPclone = new ItemSanPham(null);
    static List<ItemSanPham> danhSachSanPham = new ArrayList<>();
    Set<String> listSetLoaiSP = new HashSet<>();
    
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
                danhSachSanPham.removeAll(danhSachSanPham);
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
                    
                    sp.setThongTin(sanPham.getTenSP(), "\n\n" + (giagiam != -1 ? ItemSanPham.addGachNgang(String.valueOf(gia).replace(".0", "") + " VND") + "\n" + String.valueOf(giagiam).replace(".0", "") + " VND\n" : "\n" + String.valueOf(gia).replace(".0", "") + " VND\n") + sanPham.getLoaiSP());
                    // sp.setThongTin(sanPham.getTenSP()+ "\n\n", MaSP);
                    sp.setGia(gia);
                    sp.setMaSP(sanPham.getMaSP());
                    sp.setLoaisp(sanPham.getLoaiSP().trim());
                    if (giagiam != -1) {
                        sp.setGiagiam(giagiam);
                    }
//                    List<HoaDonChiTiet> list = daoHDCT.getByID(mahdFILL);
//                    for (HoaDonChiTiet hdct : list) {
//                        if (sp.getMaSP() == hdct.getMaSP()) {
//                            sp.setCheckBox(true);
//                        }
//                    }
                    //   System.out.println(sp.getGiagiam());
                    if (cboLoai.getSelectedItem().toString().trim().equalsIgnoreCase(sanPham.getLoaiSP().trim())) {
                        panelView.addSanPham(sp);
                        if (!checkListItemSP(sp.getMaSP())) {
                            
                            listItemSP.add(sp);
                            danhSachSanPham.add(sp);
                        }
                        
                    } else {
                        if (cboLoai.getSelectedItem().toString().trim().equalsIgnoreCase("Tất cả")) {
                            panelView.addSanPham(sp);
                            if (!checkListItemSP(sp.getMaSP())) {
                                
                                listItemSP.add(sp);
                                danhSachSanPham.add(sp);
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
        
        FillTableHoaDonChiTet(mahdFILL);
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
    
    HoaDonChiTietDAO daoHDCT = new HoaDonChiTietDAO();
    
    public void loadCapnhat() {
        if(tblHDCT.getRowCount() == 0){
        return;
        }
        for (int i = 0; i < tblHDCT.getRowCount(); i++) {
            String size = cboSize.getSelectedItem().toString();
            int soluong = Integer.parseInt(txtSoLuong.getText());
            float gia = Float.parseFloat(Validate.chuyenGiaMacDinh(txtGia.getText()));
            float tt = Float.parseFloat(Validate.chuyenGiaMacDinh(txtThanhTien.getText()));
            int masp = Integer.parseInt(tblHDCT.getValueAt(i, 1).toString());
            if (masp == Integer.parseInt(txtMaSP.getText())) {
                tblHDCT.setValueAt(soluong, i, 3);
                tblHDCT.setValueAt(size, i, 4);
                tblHDCT.setValueAt(Validate.chuyenGiaVietNam(gia), i, 5);
                tblHDCT.setValueAt(Validate.chuyenGiaVietNam(tt), i, 6);
            }
            
        }
    }
    
    public void capNhat() {
        
        for (int i = 0; i < tblHDCT.getRowCount(); i++) {
            int masp = Integer.parseInt(tblHDCT.getValueAt(i, 1).toString());
            int mahd = Integer.parseInt(txtMaHD.getText());
            int soluong = Integer.parseInt(tblHDCT.getValueAt(i, 3).toString());
            String size = tblHDCT.getValueAt(i, 4).toString().trim();
            float gia = Float.parseFloat(Validate.chuyenGiaMacDinh(tblHDCT.getValueAt(i, 5).toString()));
            HoaDonChiTiet hd = new HoaDonChiTiet(masp, soluong, gia, size, gia);
            daoHDCT.update2(hd, mahd);
            
        }
        TextMes.Alert(null, "Cập nhật thành công");
        
    }
    
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
                    if (daoHDCT.getSPbiTrung(hdct.getMaHD(), hdct.getMaSP(), hdct.getSize()) == 1) {/// tim thay sp
                        daoSP.upSoluongBiTrung(hdct.getSoluong(), hdct.getMaHD(), hdct.getMaSP(), hdct.getSize().trim());

                        //    TextMes.Alert(null, "Cập nhật chi tiết hóa đơn thành công");
                        //    return;
                    } else {
                        daoHDCT.create(hdct);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                
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
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel2 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        tblHDCT = new javax.swing.JTable();
        jScrollPane4 = new javax.swing.JScrollPane();
        tblMuaHang = new javax.swing.JTable();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        cboLoai = new javax.swing.JComboBox<>();
        btnLoad = new javax.swing.JButton();
        jLabel9 = new javax.swing.JLabel();
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
        jButton5 = new javax.swing.JButton();
        txt1 = new javax.swing.JLabel();
        txtTimKiem = new javax.swing.JTextField();
        txt = new javax.swing.JLabel();
        btnTK = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jLabel10 = new javax.swing.JLabel();
        txtTongSanPham = new javax.swing.JTextField();
        jButton2 = new javax.swing.JButton();
        txtTongTienMua = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        txtMaHD = new javax.swing.JTextField();
        jButton4 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        tblHDCT.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        tblHDCT.setModel(new javax.swing.table.DefaultTableModel(
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
        tblHDCT.setFocusable(false);
        tblHDCT.setIntercellSpacing(new java.awt.Dimension(0, 0));
        tblHDCT.setOpaque(false);
        tblHDCT.setRowHeight(30);
        tblHDCT.setShowVerticalLines(false);
        tblHDCT.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblHDCTMouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(tblHDCT);

        tblMuaHang.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        tblMuaHang.setModel(new javax.swing.table.DefaultTableModel(
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
        tblMuaHang.setFocusable(false);
        tblMuaHang.setIntercellSpacing(new java.awt.Dimension(0, 0));
        tblMuaHang.setOpaque(false);
        tblMuaHang.setRowHeight(30);
        tblMuaHang.setShowVerticalLines(false);
        tblMuaHang.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblMuaHangMouseClicked(evt);
            }
        });
        jScrollPane4.setViewportView(tblMuaHang);

        jLabel13.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel13.setText("Giỏ hàng");

        jLabel14.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel14.setText("Hóa đơn chi thiết");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane3)
                    .addComponent(jScrollPane4, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 627, Short.MAX_VALUE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel14)
                            .addComponent(jLabel13))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jLabel14)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 220, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel13)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 243, Short.MAX_VALUE)
                .addContainerGap())
        );

        jScrollPane1.setBackground(new java.awt.Color(255, 255, 255));
        jScrollPane1.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jScrollPane1MouseClicked(evt);
            }
        });

        cboLoai.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        cboLoai.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Tất cả" }));
        cboLoai.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboLoaiActionPerformed(evt);
            }
        });

        btnLoad.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        btnLoad.setText("Tải DS");
        btnLoad.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLoadActionPerformed(evt);
            }
        });

        jLabel9.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel9.setText("Loại sản phẩm");

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
        jButton1.setText("Thêm");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jLabel8.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel8.setText("Số lựng còn");

        txtTonKho.setEditable(false);
        txtTonKho.setPreferredSize(new java.awt.Dimension(57, 30));

        jButton5.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jButton5.setText("Cập nhật");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

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
                            .addComponent(txtThanhTien, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 220, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addGroup(jPanel1Layout.createSequentialGroup()
                                    .addComponent(jButton1)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jButton5))
                                .addComponent(txtTonKho, javax.swing.GroupLayout.PREFERRED_SIZE, 220, javax.swing.GroupLayout.PREFERRED_SIZE)))))
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
                    .addComponent(jButton1)
                    .addComponent(jButton5))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        txt1.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txt1.setText("Tìm kiếm");

        txtTimKiem.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        txt.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txt.setText("Nhấn để tải danh sách sản phẩm");

        btnTK.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        btnTK.setText("Tìm");
        btnTK.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTKActionPerformed(evt);
            }
        });

        jLabel10.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel10.setText("Tổng sản phẩm");

        txtTongSanPham.setEditable(false);
        txtTongSanPham.setPreferredSize(new java.awt.Dimension(57, 30));

        jButton2.setBackground(new java.awt.Color(0, 0, 255));
        jButton2.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jButton2.setForeground(new java.awt.Color(255, 255, 255));
        jButton2.setText("Thêm");
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

        jButton4.setBackground(new java.awt.Color(0, 0, 255));
        jButton4.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jButton4.setForeground(new java.awt.Color(255, 255, 255));
        jButton4.setText("Cập nhật");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(jLabel10)
                                .addComponent(jLabel11, javax.swing.GroupLayout.Alignment.LEADING))
                            .addComponent(jLabel12))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtTongSanPham, javax.swing.GroupLayout.DEFAULT_SIZE, 500, Short.MAX_VALUE)
                            .addComponent(txtTongTienMua, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(txtMaHD, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton4, javax.swing.GroupLayout.DEFAULT_SIZE, 49, Short.MAX_VALUE)
                    .addComponent(jButton2, javax.swing.GroupLayout.DEFAULT_SIZE, 49, Short.MAX_VALUE))
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
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
                            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))))
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

    private void tblHDCTMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblHDCTMouseClicked
        // TODO add your handling code here:
        if (tblHDCT.getRowCount() == 0) {
            return;
        }
        int masp = Integer.parseInt(tblHDCT.getValueAt(tblHDCT.getSelectedRow(), 1).toString());
        SanPham sp = daoSP.getByID(masp);
        txtTenSP.setText(sp.getTenSP());
        txtMaSP.setText(sp.getMaSP() + "");
        txtTonKho.setText(sp.getSoLuong() + "");
        if (tblHDCT.getValueAt(tblHDCT.getSelectedRow(), 3).toString().equals("")) {
            txtSoLuong.setText("1");
        } else {
            txtSoLuong.setText(tblHDCT.getValueAt(tblHDCT.getSelectedRow(), 3).toString());
        }
        
        DefaultComboBoxModel modelCbo = (DefaultComboBoxModel) cboSize.getModel();
        cboSize.removeAllItems();
        List<String> size = daoSP.getSize2(masp);
        for (String s : size) {
            modelCbo.addElement(s);
        }
        if (tblHDCT.getValueAt(tblHDCT.getSelectedRow(), 4).toString().equals("")) {
            cboSize.setSelectedIndex(0);
        } else {
            modelCbo.setSelectedItem(tblHDCT.getValueAt(tblHDCT.getSelectedRow(), 4).toString());
        }
        
        fillThongTinSP();
    }//GEN-LAST:event_tblHDCTMouseClicked

    private void jScrollPane1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jScrollPane1MouseClicked
        // TODO add your handling code here:
        //   System.out.println("hello");
    }//GEN-LAST:event_jScrollPane1MouseClicked

    private void cboLoaiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboLoaiActionPerformed
        // TODO add your handling code here:
        if (loadXong == true) {
            loadSanPham2();
        }
    }//GEN-LAST:event_cboLoaiActionPerformed

    private void btnLoadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLoadActionPerformed
        // TODO add your handling code here:
        loadSanPham2();
        txtTimKiem.setText("");
    }//GEN-LAST:event_btnLoadActionPerformed

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

    private void cboSizeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboSizeActionPerformed
        // TODO add your handling code here:
        fillThongTinSP();
    }//GEN-LAST:event_cboSizeActionPerformed

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

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        List<JTextField> textFieldList = List.of(txtMaSP, txtTenSP, txtSoLuong, txtGia, txtThanhTien);
        
        if (!Validate.kiemTraTrongText(textFieldList, null)) {
            return;
        }
        
        taiThongTinLenList();
        //   loadTongTien();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void btnTKActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTKActionPerformed
        // TODO add your handling code here:
        loadSanPhamTimKiem();
    }//GEN-LAST:event_btnTKActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
        xacNhan();
        FillTableHoaDonChiTet(mahdFILL);
        modelTblMuaHang.setRowCount(0);
        resetList();
        resetPanel();
        resetForm();
        //   danhSachSanPham.removeAll(danhSachSanPham);
        //  loadSanPham1();
    }//GEN-LAST:event_jButton2ActionPerformed
    
    public void resetForm() {
        txtKhuyenMai.setText("0%");
        txtGia.setText(Validate.chuyenGiaVietNam(0));
        txtThanhTien.setText(Validate.chuyenGiaVietNam(0));
        txtMaSP.setText("");
        txtTenSP.setText("");
        txtSoLuong.setText("0");
        txtTonKho.setText("");
        DefaultComboBoxModel model = (DefaultComboBoxModel) cboSize.getModel();
        model.removeAllElements();
        model.setSelectedItem("Chưa chọn sản phẩm");
        
    }
    
    public void resetPanel() {
        for (Component item : panelView.getComponents()) {
            ItemSanPham sp = (ItemSanPham) item;
            
            sp.setCheckBox(false);
            panelView.repaint();
        }
        //     panelView.removeAll();

//        jScrollPane1.setViewportView(panelView);
//        jScrollPane1.repaint();
    }

    private void txtTongTienMuaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtTongTienMuaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtTongTienMuaActionPerformed

    private void tblMuaHangMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblMuaHangMouseClicked
        // TODO add your handling code here:
        if (tblMuaHang.getRowCount() == 0) {
            return;
        }
        int masp = Integer.parseInt(tblMuaHang.getValueAt(tblMuaHang.getSelectedRow(), 1).toString());
        SanPham sp = daoSP.getByID(masp);
        txtTenSP.setText(sp.getTenSP());
        txtMaSP.setText(sp.getMaSP() + "");
        txtTonKho.setText(sp.getSoLuong() + "");
        if (tblMuaHang.getValueAt(tblMuaHang.getSelectedRow(), 3).toString().equals("")) {
            txtSoLuong.setText("1");
        } else {
            txtSoLuong.setText(tblMuaHang.getValueAt(tblMuaHang.getSelectedRow(), 3).toString());
        }
        
        DefaultComboBoxModel modelCbo = (DefaultComboBoxModel) cboSize.getModel();
        cboSize.removeAllItems();
        List<String> size = daoSP.getSize2(masp);
        for (String s : size) {
            modelCbo.addElement(s);
        }
        if (tblMuaHang.getValueAt(tblMuaHang.getSelectedRow(), 4).toString().equals("")) {
            cboSize.setSelectedIndex(0);
        } else {
            modelCbo.setSelectedItem(tblMuaHang.getValueAt(tblMuaHang.getSelectedRow(), 4).toString());
        }
        
        fillThongTinSP();
    }//GEN-LAST:event_tblMuaHangMouseClicked

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        // TODO add your handling code here:
        capNhat();
        resetForm();
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        // TODO add your handling code here:
        loadCapnhat();
    }//GEN-LAST:event_jButton5ActionPerformed

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
            java.util.logging.Logger.getLogger(MuaSanPhamJDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MuaSanPhamJDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MuaSanPhamJDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MuaSanPhamJDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                MuaSanPhamJDialog dialog = new MuaSanPhamJDialog(new javax.swing.JFrame(), true, 12);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
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
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
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
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JTable tblHDCT;
    private javax.swing.JTable tblMuaHang;
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
