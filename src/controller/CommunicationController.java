/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

/**
 *
 * @author milar
 */
public final class CommunicationController {
    
    private static final CommunicationController commController = new CommunicationController();
    
    private UtilitiesPaneController utilitiesPaneController = null;
    private ProductsPaneController productsPaneController = null;
    private CategoryPaneController categoryPaneController = null;
    private MenuPaneController menuPaneController = null;

    private CommunicationController() {
    }

    public static CommunicationController getInstance(){
    return commController;
    }

    public void setCategoryPaneController(CategoryPaneController catController){
        if(this.categoryPaneController == null){
            this.categoryPaneController = catController;
        }
    }
    
    public CategoryPaneController getCategoryPaneController(){
        return this.categoryPaneController;
    }
    
    public void setProductPaneController(ProductsPaneController productsPaneController){
        if(this.productsPaneController == null){
            this.productsPaneController = productsPaneController;
        }
    }
    
    public ProductsPaneController getProductsPaneController(){
        return this.productsPaneController;
    }
    
    public void setUtilitiesPaneController(CategoryPaneController catController){
        if(this.utilitiesPaneController == null){
            this.utilitiesPaneController = utilitiesPaneController;
        }
    }
    
    public UtilitiesPaneController getUtilitiesPaneController(){
        return this.utilitiesPaneController;
    }
    
    
    public void setMenuPaneController(CategoryPaneController catController){
        if(this.menuPaneController == null){
            this.menuPaneController = menuPaneController;
        }
    }
    
    public MenuPaneController getMenuPaneController(){
        return this.menuPaneController;
    }
}

