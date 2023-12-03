/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.g5.component;

import com.g5.ui.DangNhapJDialog;
import com.g5.ui.DoiMatKhauJDialog;
import com.g5.ui.MainFrame;
import com.g5.ui.QuenMatKhauJDialog;
import com.g5.util.Auth;
import com.g5.util.TextMes;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionAdapter;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.border.EmptyBorder;
import net.miginfocom.swing.MigLayout;

/**
 *
 * @author anhba
 */
public class Menu extends javax.swing.JPanel {

    private MigLayout layout;
    private JPanel panelMenu;
    private JButton cmdMenu;
    private JButton cmdExit;
    private JButton cmdMini;
    private Header header;
    private Info bottom;
    private Date date;
    private About about;
    private JPopupMenu popupMenu = new JPopupMenu();
    private JMenuItem menuItem = new JMenuItem();
    //  private JButton cmdLogOut;
    //  private JButton cmdChangePass;
    private EventMenuSelected eventMenuSelected;

    public void setEventMenuSelected(EventMenuSelected eventMenuSelected) {
        this.eventMenuSelected = eventMenuSelected;
    }

    /**
     * Creates new form Menu
     */
    public Menu() {
        initComponents();
        setOpaque(false);
        init();
        popupMenu.setOpaque(false);
        popupMenu.setEnabled(false);
        popupMenu.setBorder(new EmptyBorder(1, 1, 1, 1));
        menuItem.setBorder(new EmptyBorder(1, 1, 1, 1));
        menuItem.setBackground(new Color(99, 126, 118));
        menuItem.setForeground(Color.WHITE);

        about.addEvenHDSD(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                TextMes.Alert(null, "Hướng dẫn sử dụng");
            }
        });
        about.addEvenThongTin(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                TextMes.Alert(null, "Về chúng tôi");
            }
        });
        about.addEvenDoiMK(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                if (TextMes.Comform(null, "Bạn có chắc muốn đổi mật khẩu?")) {
                    MainFrame.setStatus(false);
                    DoiMatKhauJDialog.setStatus(true);
                };
            }
        });
        about.addEvenDangXuat(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                if (TextMes.Comform(null, "Bạn có chắc muốn đăng xuất?")) {
                    MainFrame.setStatus(false);
                    DangNhapJDialog.setStatus(true);
                };
            }
        });
    }


    
    void init() {
        setLayout(new MigLayout("wrap, fillx, insets 0", "5[fill]0", "5[]0[]5"));// wrap: xuống dòng mỗi khi thêm, fillx: full độ dài, insets 0: không khoảng cách
        panelMenu = new JPanel();                                                              // [fill]: khoảng cách ngang, [][]: khoảng cách dọc
        header = new Header();
        bottom = new Info();
        date = new Date();

        //////     setting = new Setting();
        createButtonMenu();
        createButtonExit();
        createButtonMini();
        //    createButtonChangePass();
        //   createButtonLogOut();
        panelMenu.setOpaque(false);
        layout = new MigLayout("fillx, wrap", "0[fill]5", "0[]0[]5"); // component | []: Khoang cac ngang | [][]: Khoang cach doc
        panelMenu.setLayout(layout);
        about = new About();
        add(cmdMenu, "pos 1al 0al 100% , height 50!");
        add(cmdMini, "pos 1al 770 100% , height 50!");
        add(cmdExit, "pos 1al 1al 100% , height 50!");
        //   add(cmdLogOut, "pos -170 1al 100% , height 50!");
        //  add(cmdChangePass, "pos -170 770 100% , height 50!");
        //   add(cmdMini, "pos 1al 1al 100% 50, height 50!"); // pos trái phải rộng 
        //  add(cmdMini,"pos 1al 1al 50% , height 50!");
        add(header);
        add(panelMenu);

        add(bottom);
        ////////   add(setting);
        add(date);
        add(about);

        date.startClock();

    }

    public void addMenu(Model_Menu menu) {
        MenuItem item = new MenuItem(menu.getIcon(), menu.getMenuName(), panelMenu.getComponentCount(), menu.getText());

        item.addEvent(new EventMenuSelected() {
            @Override
            public void selected(int index) {
                if (Auth.isLogin() && Auth.user.getVaitro() == 0 && index == 1) {
                    TrangChu(0);

                    return;
                }

                clearMenu(index);
                repaint();

            }
        });
        item.addEvent(eventMenuSelected);
        item.addMouseListener(new MouseAdapter() {

        });
        panelMenu.add(item);
        if (item.getIndex() == 0) {
            item.setSelected(true);
        }

    }

    public void TrangChu(int index) {
        for (Component com : panelMenu.getComponents()) {
            try {
                MenuItem item = (MenuItem) com;
                if (item.getIndex() == index) {
                    item.setSelected(true);
                }
                if (item.getIndex() != index) {
                    item.setSelected(false);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
        repaint();
    }

    public MenuItem itemMenuNhanVien = null;
    public MenuItem itemMenuThongKe = null;

    public void menuRemove(int index) {
        for (Component com : panelMenu.getComponents()) {
            try {
                MenuItem item = (MenuItem) com;
                if (item.getIndex() == index) {
                    itemMenuNhanVien = item;
                    panelMenu.remove(com);
                    //  panelMenu.add(item, 5);
                    //   item.setVisible(false);
                    //   panelMenu.remove(com);
                    break;
                }

            } catch (Exception e) {
                e.printStackTrace();
            }

        }
        repaint();

    }

    public void menuRemove2(int index) {
        for (Component com : panelMenu.getComponents()) {
            try {
                MenuItem item = (MenuItem) com;
                if (item.getIndex() == index) {
                    itemMenuThongKe = item;
                    panelMenu.remove(com);

                    break;
                }

            } catch (Exception e) {
                e.printStackTrace();
            }

        }
        repaint();

    }

    public void menuFull() {

        try {
            if (itemMenuNhanVien != null && itemMenuThongKe != null) {
                panelMenu.add(itemMenuThongKe);
                panelMenu.add(itemMenuNhanVien);
                itemMenuThongKe = null;
                itemMenuNhanVien = null;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        repaint();

    }

    public void createButtonMenu() {
        cmdMenu = new JButton();
        cmdMenu.setContentAreaFilled(false); //  không cho phép vẽ nền của button
        cmdMenu.setCursor(new Cursor(Cursor.HAND_CURSOR));
        cmdMenu.setIcon(new ImageIcon(Menu.class.getResource("/com/g5/logos/Menu.png")));
        cmdMenu.setBorder(new EmptyBorder(0, 1, 0, 16));

        cmdMenu.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                popupMenu.removeAll();
                menuItem.setText("Menu");
                popupMenu.add(menuItem);
                popupMenu.show(cmdMenu, e.getX() + 10, e.getY() - 10);

            }

            public void mouseExited(MouseEvent e) {
                popupMenu.removeAll();
                popupMenu.setVisible(false);
            }
        });
    }

    public void setEnableButtonMenu(boolean bl) {
        cmdMenu.setEnabled(bl);
    }

    public void setEnableButtonMini(boolean bl) {
        cmdMini.setEnabled(bl);
    }

    public void setEnableButtonExit(boolean bl) {
        cmdExit.setEnabled(bl);
    }

//    public void setEnableButtonChangePass(boolean bl) {
//    //    cmdChangePass.setEnabled(bl);
//    }
//
//    public void setEnableButtonLogOut(boolean bl) {
//   //     cmdLogOut.setEnabled(bl);
//    }
    public void createButtonExit() {
        cmdExit = new JButton();
        cmdExit.setContentAreaFilled(false);
        cmdExit.setCursor(new Cursor(Cursor.HAND_CURSOR));
        cmdExit.setIcon(new ImageIcon(Menu.class.getResource("/com/g5/logos/Cancel.png")));
        cmdExit.setBorder(new EmptyBorder(0, 1, 0, 11)); // vị trí của Button exit
        cmdExit.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                popupMenu.removeAll();
                menuItem.setText("Thoát");
                popupMenu.add(menuItem);
                popupMenu.show(cmdExit, e.getX() + 10, e.getY() - 10);

            }

            public void mouseExited(MouseEvent e) {
                popupMenu.removeAll();
                popupMenu.setVisible(false);
            }
        });
    }

    public void createButtonMini() {
        cmdMini = new JButton();
        cmdMini.setContentAreaFilled(false);
        cmdMini.setCursor(new Cursor(Cursor.HAND_CURSOR));
        cmdMini.setIcon(new ImageIcon(Menu.class.getResource("/com/g5/logos/Minimize.png")));
        cmdMini.setBorder(new EmptyBorder(0, 1, 0, 11));
        cmdMini.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                popupMenu.removeAll();
                menuItem.setText("Thu nhỏ");
                popupMenu.add(menuItem);
                popupMenu.show(cmdMini, e.getX() + 10, e.getY() - 10);

            }

            public void mouseExited(MouseEvent e) {
                popupMenu.removeAll();
                popupMenu.setVisible(false);
            }
        });
    }

//    public void createButtonChangePass() {
//        cmdChangePass = new JButton();
//        cmdChangePass.setContentAreaFilled(false);
//        cmdChangePass.setCursor(new Cursor(Cursor.HAND_CURSOR));
//        cmdChangePass.setIcon(new ImageIcon(Menu.class.getResource("/com/g5/logos/Password_Reset.png")));
//        cmdChangePass.setBorder(new EmptyBorder(0, 1, 0, 11));
//    }
//
//    public void createButtonLogOut() {
//        cmdLogOut = new JButton();
//        cmdLogOut.setContentAreaFilled(false);
//        cmdLogOut.setCursor(new Cursor(Cursor.HAND_CURSOR));
//        cmdLogOut.setIcon(new ImageIcon(Menu.class.getResource("/com/g5/logos/Logout.png")));
//        cmdLogOut.setBorder(new EmptyBorder(0, 1, 0, 11));
//    }
    public void addEventButtonMenu(ActionListener evt) {
        cmdMenu.addActionListener(evt);
    }

    public void addEventButtonExit(ActionListener evt) {
        cmdExit.addActionListener(evt);
    }

    public void addEventButtonMini(ActionListener evt) {
        cmdMini.addActionListener(evt);
    }

//    public void addEventButtonChangePass(ActionListener evt) {
//        cmdChangePass.addActionListener(evt);
//    }
//
//    public void addEventButtonLogOut(ActionListener evt) {
//        cmdLogOut.addActionListener(evt);
//    }
//    private int x;
//    private int y;
//
//    public void initMoving(JFrame frame) {
//        header.addMouseListener(new MouseAdapter() {
//            @Override
//            public void mousePressed(MouseEvent me) {
//                x = me.getX();
//                y = me.getY();
//            }
//
//        });
//        header.addMouseMotionListener(new MouseMotionAdapter() {
//            @Override
//            public void mouseDragged(MouseEvent me) {
//                frame.setLocation(me.getXOnScreen() - x, me.getYOnScreen() - y);
//            }
//        });
//    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        GradientPaint gra = new GradientPaint(0, 0, Color.decode("#136a8a"), 0, getHeight(), Color.decode("#267871"));
        g2.setPaint(gra);
        g2.fillRect(0, 0, getWidth(), getHeight());
        super.paintComponent(g); //To change body of generated methods, choose Tools | Templates.
    }

//    void checkVaiTro() {
//        MenuItem[] menu = new MenuItem[panelMenu.getComponentCount()];
//        int i = 0;
//        for (Component com : panelMenu.getComponents()) {
//            try {
//                MenuItem item = (MenuItem) com;
//                if (item.getIndex() == 1) {
//                    item.setSelected(false);
//                }
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//
//        }
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//
//            }
//        }).start();
//    }
    private void clearMenu(int index) {
        for (Component com : panelMenu.getComponents()) {
            try {
                MenuItem item = (MenuItem) com;
                if (item.getIndex() != index) {
                    item.setSelected(false);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        }

    }

    public void setAlpha(float alpha) {
        header.setAlpha(alpha);
        bottom.setAlpha(alpha);
        date.setAlpha(alpha);

        about.setAlpha(alpha);
    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
