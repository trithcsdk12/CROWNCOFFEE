/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.g5.ui;

import com.g5.component.Menu;
import com.g5.component.EventMenuSelected;
import com.g5.form.HoaDonJPanel;
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
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import net.miginfocom.swing.MigLayout;
import org.apache.log4j.PropertyConfigurator;
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
        closeForm();
        PropertyConfigurator.configure("src/com/g5/log/log4j.properties");

    }

    void closeForm() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0;;) {
                    try {
                        Thread.sleep(1000);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
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
                    if (Auth.isLogin()) {
                        if (Auth.user.getVaitro() == 0) {
                            menu.menuRemove2(4);
                            menu.menuRemove(5);
                        } else {

                            menu.menuFull();
                        }

                    }

                    if (logOut) { //////Đăng xuất
                        showForm(new TrangChuJPanel());
                        menu.TrangChu(0);
                        logOut = false;

                    }
                }
            }
        }).start();
    }

    public static void setStatus(boolean bl) {
        if (!bl) {
            logOut = true;
            mainFrame.dispose();
            //   Auth.clear();
        }

        mainFrame.setVisible(bl);
    }

    static void exitForm() {
        System.exit(0);
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
            .addGap(0, 276, Short.MAX_VALUE)
        );
        panelBodyLayout.setVerticalGroup(
            panelBodyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 266, Short.MAX_VALUE)
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
//                    menu.setEnableButtonChangePass(false);
//                    menu.setEnableButtonLogOut(false);
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

            }
        });

//        menu.addEventButtonChangePass(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                if (TextMes.Comform(null, "Bạn có chắc muốn đổi mật khẩu?")) {
//                    setStatus(false);
//                    DoiMatKhauJDialog.setStatus(true);
//                }
//
//            }
//        });
//
//        menu.addEventButtonLogOut(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                if (TextMes.Comform(null, "Bạn có chắc muốn đăng xuất?")) {
//                    logger.info("Người dùng [Mã nhân viên: " + Auth.user.getMaNV() + " | Họ tên: " + Auth.user.getHoTen() + "]" + " đã đăng xuất");
//
//                    setStatus(false);
//
//                    DangNhapJDialog.setStatus(true);
//                }
//
//            }
//        });
        menu.setEventMenuSelected(new EventMenuSelected() {
            @Override
            public void selected(int index) {
                if (index == 0) {
                    showForm(new TrangChuJPanel());
                }
                if (index == 1) {
                    showForm(new HoaDonJPanel());
                }
                if (index == 2) {
                    showForm(new SanPhamJPanel());
                }
                if (index == 3) {
                //    showForm(new KhuyenMai());
                }
                if (index == 4) {
                    showForm(new ThongKeJPanel());
                }
                if (index == 5) {

                    if (Auth.user.getVaitro() == 0) {
                        showForm(new TrangChuJPanel());
                        TextMes.Alert(null, "Bạn không có quyền truy cập");
                        return;
                    }
                    showForm(new NhanVienJPanel());
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
//                    menu.setEnableButtonChangePass(true);
//                    menu.setEnableButtonLogOut(true);
                    return;
                }
                if (menu.getWidth() < 63) {
                    menuShow = false;
                    menu.setEnableButtonMenu(true);
                    menu.setEnableButtonMini(true);
                    menu.setEnableButtonExit(true);
//                    menu.setEnableButtonChangePass(true);
//                    menu.setEnableButtonLogOut(true);
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
        menu.addMenu(new Model_Menu("Trang chủ", new ImageIcon(MainFrame.class.getResource("/com/g5/logos/Home.png")), "Trang chủ"));
        menu.addMenu(new Model_Menu("Hóa đơn", new ImageIcon(MainFrame.class.getResource("/com/g5/logos/Bill.png")), "Hóa đơn"));
        menu.addMenu(new Model_Menu("Sản phẩm", new ImageIcon(MainFrame.class.getResource("/com/g5/logos/Box.png")), "Sản phẩm"));
        menu.addMenu(new Model_Menu("Khuyến mãi", new ImageIcon(MainFrame.class.getResource("/com/g5/logos/Voucher.png")), "Khuyến mãi"));
        menu.addMenu(new Model_Menu("Thống kê", new ImageIcon(MainFrame.class.getResource("/com/g5/logos/Combo_Chart.png")), "Thống kê"));
        menu.addMenu(new Model_Menu("Nhân viên", new ImageIcon(MainFrame.class.getResource("/com/g5/logos/Person.png")), "Nhân viên"));
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

                new ChaoJDialog(mainFrame, true).setVisible(true);
                if (openDangNhap()) {

                    setStatus(true);
                };
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel panelBody;
    // End of variables declaration//GEN-END:variables

}
