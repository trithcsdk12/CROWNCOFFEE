/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.g5.component;

import javax.swing.Icon;

/**
 *
 * @author anhba
 */
public class Model_Menu {
    

    
    public String getMenuName() {
        return menuName;
    }
    
    public void setMenuName(String menuName) {
        this.menuName = menuName;
    }
    
    public Icon getIcon() {
        return icon;
    }
    
    public void setIcon(Icon icon) {
        this.icon = icon;
    }
    

    
    public Model_Menu() {
    }
    
    public Model_Menu(String menuName, Icon icon) {
        this.menuName = menuName;
        this.icon = icon;
        
    }
    
    private String menuName;
    private Icon icon;
}
