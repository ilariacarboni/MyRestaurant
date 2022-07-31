package view.sceneControllers;


public final class CommunicationController {
    
    private static final CommunicationController commController = new CommunicationController();
    
    private ProductsPaneController productsPaneController = null;
    private CategoryPaneController categoryPaneController = null;
    private EmployeesListController employeesListController = null;
    
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

    
    public void setEmployeePaneController(EmployeesListController employeesListController){
        if(this.employeesListController == null){
            this.employeesListController = employeesListController;
        }
    }
    
    public EmployeesListController getEmployeePaneController(){
        return this.employeesListController;
    }
}


