/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.g5.ui;

import com.g5.component.Menu;
import com.g5.component.MenuItem;
import com.g5.component.Setting;
import com.g5.component.EventMenuSelected;
import com.g5.form.HoaDonJPanel;
import com.g5.form.KhuyenMai;
import com.g5.form.NhanVienJPanel;
import com.g5.form.SanPhamJPanel;
import com.g5.form.ThongKeJPanel;
import com.g5.form.TrangChuJPanel;
import com.g5.component.Model_Menu;
import com.g5.util.Auth;
import com.g5.util.TextMes;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import net.miginfocom.swing.MigLayout;
import org.jdesktop.animation.timing.Animator;
import org.jdesktop.animation.timing.TimingTarget;
import org.jdesktop.animation.timing.TimingTargetAdapter;

/**
 *
 * @author anhba
 */
public class MainFrame extends javax.swing.JFrame {
    
    public static final org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(MainFrame.class);

    
    private static MainFrame mainFrame = new MainFrame();
    private Menu menu = new Menu();
    private JPanel main = new JPanel();
    private MigLayout layout;
    private Animator animator;
    private boolean menuShow;
    public static boolean logOut = false;
    
    public MainFrame() {
        this.setUndecorated(true);
        initComponents();
        init();
        setHome();
        
    }
    
    
    
    void setHome() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0;;) {
                    try {
                        Thread.sleep(1000);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    if(logOut){
                        showForm(new TrangChuJPanel());
                        menu.TrangChu(0);
                        logOut = false;
                        
                    }
                }
            }
        }).start();
    }
    
    static void setStatus(boolean bl) {
        if (!bl) {
            logOut = true;
            mainFrame.dispose();
            //   mainFrame.setVisible(bl);
        }
      //  logOut = false;
        mainFrame.setVisible(bl);
    }
    
    static boolean isOpen() {
        return mainFrame.isVisible();
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panelBody = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        javax.swing.GroupLayout panelBodyLayout = new javax.swing.GroupLayout(panelBody);
        panelBody.setLayout(panelBodyLayout);
        panelBodyLayout.setHorizontalGroup(
            panelBodyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 802, Short.MAX_VALUE)
        );
        panelBodyLayout.setVerticalGroup(
            panelBodyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 602, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panelBody, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panelBody, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    void init() {
        //setSize(1000, 800);
        setLocationRelativeTo(null);
        this.setExtendedState(this.MAXIMIZED_BOTH);
        
        layout = new MigLayout("fill", "0[]8[]0", "0[fill]0"); // layout cua form chinh "giản cách bên trái[]giản cách ở giữ[] giản cách bên phải
        panelBody.setLayout(layout); // set layout cho form chinh

        main.setOpaque(false);
        main.setLayout(new BorderLayout());
        menu.addEventButtonMenu(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!animator.isRunning()) {
                    menu.setEnableButtonMenu(false);
                    menu.setEnableButtonMini(false);
                    menu.setEnableButtonExit(false);
                    menu.setEnableButtonChangePass(false);
                    menu.setEnableButtonLogOut(false);
                    animator.start();
                    
                }
            }
        });
        menu.addEventButtonExit(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        menu.addEventButtonMini(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                MainFrame.this.setState(Frame.ICONIFIED);
                //    Main.this.dispose();
            }
        });
        menu.addEventButtonMini(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                MainFrame.this.setState(Frame.ICONIFIED);
                //    Main.this.dispose();
            }
        });
        menu.addEventButtonChangePass(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (TextMes.Comform(main, "Bạn có chắc muốn đổi mật khẩu?")) {
                    setStatus(false);
                    DoiMatKhauJDialog.setStatus(true);
                }
                
            }
        });
        
        menu.addEventButtonLogOut(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (TextMes.Comform(main, "Bạn có chắc muốn đăng xuất?")) {
                    setStatus(false);
                    DangNhapJDialog.setStatus(true);
                }
                
            }
        });
        
        menu.setEventMenuSelected(new EventMenuSelected() {
            @Override
            public void selected(int index) {
                if (index == 0) {
                    showForm(new TrangChuJPanel());
                }
                if (index == 1) {
                    if (Auth.user.getVaitro() == 0) {
                        showForm(new TrangChuJPanel());
                        TextMes.Alert(null, "Bạn không có quyền truy cập");
                        return;
                    }
                    showForm(new NhanVienJPanel());
                }
                if (index == 2) {
                    showForm(new HoaDonJPanel());
                }
                if (index == 3) {
                    showForm(new SanPhamJPanel());
                }
                if (index == 4) {
                    showForm(new KhuyenMai());
                }
                if (index == 5) {
                    showForm(new ThongKeJPanel());
                }
            }
        });
        
        loadFormMenu();
        
        panelBody.add(menu, "w 62!");
        panelBody.add(main, "w 100%");
        
        TimingTarget target = new TimingTargetAdapter() {
            @Override
            public void timingEvent(float fraction) {
                double width;
                if (menuShow) {
                    width = 62 + (180 * (1f - fraction));
                    menu.setAlpha(1f - fraction);
                } else {
                    width = 62 + (180 * fraction);
                    menu.setAlpha(fraction);
                }
                layout.setComponentConstraints(menu, "w " + width + "!");
                panelBody.revalidate();
            }
            
            @Override
            public void end() {
                if (menu.getWidth() > 63) {
                    menuShow = true;
                    menu.setEnableButtonMenu(true);
                    menu.setEnableButtonMini(true);
                    menu.setEnableButtonExit(true);
                    menu.setEnableButtonChangePass(true);
                    menu.setEnableButtonLogOut(true);
                    return;
                }
                if (menu.getWidth() < 63) {
                    menuShow = false;
                    menu.setEnableButtonMenu(true);
                    menu.setEnableButtonMini(true);
                    menu.setEnableButtonExit(true);
                    menu.setEnableButtonChangePass(true);
                    menu.setEnableButtonLogOut(true);
                    return;
                }
                
            }
            
        };
        animator = new Animator(500, target);
        animator.setResolution(0);
        animator.setAcceleration(0.5f);
        animator.setDeceleration(0.5f);
        //  menu.initMoving(Main.this);
        showForm(new TrangChuJPanel());
    }
    
    private void loadFormMenu() {
        menu.addMenu(new Model_Menu("Trang chủ", new ImageIcon(MainFrame.class.getResource("/com/g5/image/Home_2.png"))));
        menu.addMenu(new Model_Menu("Nhân viên", new ImageIcon(MainFrame.class.getResource("/com/g5/image/Person_1.png"))));
        menu.addMenu(new Model_Menu("Hóa đơn", new ImageIcon(MainFrame.class.getResource("/com/g5/image/Bill.png"))));
        menu.addMenu(new Model_Menu("Sản phẩm", new ImageIcon(MainFrame.class.getResource("/com/g5/image/Box.png"))));
        menu.addMenu(new Model_Menu("Khuyến mãi", new ImageIcon(MainFrame.class.getResource("/com/g5/image/Voucher_1.png"))));
        menu.addMenu(new Model_Menu("Thống kê", new ImageIcon(MainFrame.class.getResource("/com/g5/image/Combo_Chart.png"))));
        
    }
    
    private void showForm(Component com) {
        if (!Auth.isLogin()) {
            main.removeAll();
            main.add(new TrangChuJPanel());
            main.repaint();
            main.revalidate();
            return;
        }
        main.removeAll();
        main.add(com);
        main.repaint();
        main.revalidate();
    }
    
    void checkVaiTro() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0;;) {
                    try {
                        Thread.sleep(500);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    if (Auth.isLogin() && Auth.user.getVaitro() == 0) {
                        
                    }
                }
                
            }
        }).start();
    }
    
    private static boolean openDangNhap() {
//        if (Auth.isLogin()) {
//            return true;
//        }
        DangNhapJDialog.setStatus(true);
        return Auth.isLogin();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                if (openDangNhap()) {
                    logger.info("Người dùng [" + Auth.user.getMaNV() + "]: "+Auth.user.getHoTen()+" đã đăng nhập vào hệ thống");
                    setStatus(true);
                };
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel panelBody;
    // End of variables declaration//GEN-END:variables
}
