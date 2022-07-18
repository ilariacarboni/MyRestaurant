package controller;

/**
 *
 * @author Natalia
 */
public final class CommunicationController {
    
    private static final CommunicationController commController = new CommunicationController();
    private CategoryPaneController categoryPaneController = null;
    private ProductsPaneController productsPaneController = null;
    
    private CommunicationController(){
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
}
