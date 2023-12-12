/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.g5.util;

import java.awt.Image;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JMenu;

/**
 *
 * @author anhba
 */
public class XImage {

    public static ImageIcon imageIcon = new ImageIcon(XImage.class.getResource("/com/g5/logos/icon.png"));

    public static Image getAppIcon() {
        return imageIcon.getImage();
    }

    public static boolean save(File src) {
        File dst = new File("src/com/g5/image", src.getName());
        if (!dst.getParentFile().exists()) {
            dst.getParentFile().mkdirs();
        }
        try {
            Path from = Paths.get(src.getAbsolutePath());
            Path to = Paths.get(dst.getAbsolutePath());
            Files.copy(from, to, StandardCopyOption.REPLACE_EXISTING);
            return true;
        } catch (Exception ex) {
            return false;

        }

    }

    public static void selectImage(String file, JLabel lbl) {
        try {
            //   BufferedImage originalImage = ImageIO.read(new File(file.toString()));
            lbl.setText("");
            ImageIcon icon = new ImageIcon(XImage.class.getResource("/com/g5/image/" + file.trim()));

            Image image = icon.getImage();

            Image scaledImage = image.getScaledInstance(lbl.getWidth(), lbl.getHeight(), Image.SCALE_DEFAULT);

            ImageIcon scaledIcon = new ImageIcon(scaledImage);

            lbl.setIcon(scaledIcon);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void selectImage2(String file, JLabel lbl,int width, int height) {
        try {
            //   BufferedImage originalImage = ImageIO.read(new File(file.toString()));
            lbl.setText("");
            ImageIcon icon = new ImageIcon(XImage.class.getResource("/com/g5/image/" + file.trim()));

            Image image = icon.getImage();

            Image scaledImage = image.getScaledInstance(width, height, Image.SCALE_DEFAULT);

            ImageIcon scaledIcon = new ImageIcon(scaledImage);

            lbl.setIcon(scaledIcon);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void selectLogo(String file, JLabel lbl, int width, int height) {
        try {
            //   BufferedImage originalImage = ImageIO.read(new File(file.toString()));
            lbl.setText("");
            ImageIcon icon = new ImageIcon(XImage.class.getResource("/com/g5/logos/" + file.trim()));

            Image image = icon.getImage();

            Image scaledImage = image.getScaledInstance(width, height, Image.SCALE_DEFAULT);

            ImageIcon scaledIcon = new ImageIcon(scaledImage);

            lbl.setIcon(scaledIcon);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String urlLogo(String picture) {
        return XImage.class.getResource("/com/g5/logos/") + picture.trim();
    }

    public static String urlImage(String picture) {
        return XImage.class.getResource("/com/g5/image/") + picture.trim();
    }

    public static void chooseLogoMenu(String file, JMenu menu) {
        try {
            //   BufferedImage originalImage = ImageIO.read(new File(file.toString()));

            ImageIcon icon = new ImageIcon(XImage.class.getResource("/com/g5/logos/" + file));

            Image image = icon.getImage();

            Image scaledImage = image.getScaledInstance(menu.getWidth() - 20, menu.getHeight() - 10, Image.SCALE_DEFAULT);

            ImageIcon scaledIcon = new ImageIcon(scaledImage);

            menu.setIcon(scaledIcon);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
