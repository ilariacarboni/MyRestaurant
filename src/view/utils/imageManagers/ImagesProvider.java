package view.utils.imageManagers;

import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;

import java.util.ArrayList;
import java.util.HashMap;

import static javafx.scene.layout.BackgroundPosition.CENTER;
import static javafx.scene.layout.BackgroundRepeat.NO_REPEAT;
import static javafx.scene.layout.BackgroundRepeat.REPEAT;
import static javafx.scene.layout.BackgroundSize.DEFAULT;

public class ImagesProvider {

    private static final ImagesProvider imagesProvider = new ImagesProvider();

    //----------------------BACKGROUND----------------------//
    private LocatedImage backgroundImage;
    private final String BACKGROUND_IMAGE_PATH = "/view/style/img/background/grey.jpeg";
    private Background background;

    //----------------------CATEGORIES----------------------//
    private HashMap<String, LocatedImage> categoriesImgs;
    private HashMap<String, LocatedImage> categoryNameImgs;
    private HashMap<String, LocatedImage> categoryProductsIcons;

    //----------------------MENU----------------------//
    private LocatedImage defaultDishImage;
    private HashMap<String, LocatedImage> menuImages;
    private final String DEFAULT_DISH_IMAGE_PATH = "/view/style/img/others/default-dish.png";
    private HashMap<String, LocatedImage> coursesImages;

    //----------------------EMPLOYEES----------------------//
    private LocatedImage defaultEmployeeImage;
    private HashMap<String, LocatedImage> employeesBasicImg;
    private HashMap<String, LocatedImage> employeesImages;
    private final String EMPLOYEE_DEFAULT_IMAGE_PATH = "/view/style/img/employee-icons/employee-of-the-month.png";
    public final String EMPLOYEE_DEFAULT_IMAGE_TYPE = "default";
    private final String EMPLOYEE_CHEF_IMAGE_PATH = "/view/style/img/employee-icons/chef.png";
    public final String EMPLOYEE_CHEF_IMAGE_TYPE = "chef";
    private final String EMPLOYEE_WAITER_IMAGE_PATH = "/view/style/img/employee-icons/waiter.png";
    public final String EMPLOYEE_WAITER_IMAGE_TYPE = "waiter";

    //----------------------ORDERS----------------------//
    private LocatedImage deliveringImage;
    private final String DELIVERING_IMAGE_PATH = "/view/style/img/buttons-icon/delivery.png";
    private LocatedImage historyImage;
    private final String HISTORY_IMAGE_PATH = "/view/style/img/buttons-icon/historical.png";

    //----------------------PRODUCTS----------------------//
    private LocatedImage defaultProductImg;
    private final String PRODUCT_DEFAULT_IMAGE_PATH = "/view/style/img/others/no-results.png";
    private HashMap<Integer, LocatedImage> productsImages;

    //----------------------UTILITIES----------------------//
    private LocatedImage gasImage;
    private final String GAS_IMAGE_PATH = "/view/style/img/utility-icons/flame.png";
    private LocatedImage bulbImage;
    private final String BULB_IMAGE_PATH = "/view/style/img/utility-icons/idea.png";
    private LocatedImage waterDropImage;
    private final String WATER_DROP_IMAGE_PATH = "/view/style/img/utility-icons/water-drop.png";

    //----------------------BUTTON ICONS----------------------//
    private final String BACK_ICON = "/view/style/img/others/back.png";
    private LocatedImage backIcon;
    private final String NEXT_ICON = "/view/style/img/others/close.png";
    private LocatedImage nextIcon;

    public static ImagesProvider getInstance(){
        return imagesProvider;
    }

    private ImagesProvider() {
        this.initializeEmployeesBasicImages();
        this.productsImages = new HashMap<>();
    }

    //----------------------BACKGROUND----------------------//
    public LocatedImage getBackgroundImage(){
        if(backgroundImage == null){
            backgroundImage = new LocatedImage(this.BACKGROUND_IMAGE_PATH);
        }
        return this.backgroundImage;
    }

    public Background getBackground(){
        if(this.background == null){
            LocatedImage backImg = this.getBackgroundImage();
            this.background = new Background(new BackgroundImage(backImg, REPEAT, NO_REPEAT, CENTER, DEFAULT));
        }
        return this.background;
    }

    //----------------------CATEGORIES----------------------//
    public void initializeCategoriesImg(ArrayList<HashMap<String, Object>> categoriesInfo){
        this.categoriesImgs = new HashMap<>();
        this.categoryNameImgs = new HashMap<>();
        this.categoryProductsIcons = new HashMap<>();
        for(HashMap<String, Object> categoryInfo : categoriesInfo){
            String categoryName = categoryInfo.get("name").toString();
            LocatedImage categoryImg = new LocatedImage(categoryInfo.get("img").toString());
            LocatedImage categoryNameImg = new LocatedImage(categoryInfo.get("nameImg").toString());
            LocatedImage categoryProductIcon = new LocatedImage(categoryInfo.get("icon").toString());
            this.categoriesImgs.put(categoryName, categoryImg);
            this.categoryNameImgs.put(categoryName, categoryNameImg);
            this.categoryProductsIcons.put(categoryName, categoryProductIcon);
        }
    }

    public LocatedImage getCategoryImage(String categoryName){
        return this.categoriesImgs.get(categoryName);
    }

    public LocatedImage getCategoryNameImg(String categoryName){
        return this.categoryNameImgs.get(categoryName);
    }

    public LocatedImage getCategoryProductIcon(String categoryName){
        return this.categoryProductsIcons.get(categoryName);
    }

    //----------------------MENU----------------------//
    public void initializeMenuImg(ArrayList<HashMap<String, Object>> menu){
        this.menuImages = new HashMap<>();
        for(HashMap<String, Object> item: menu){
            String imagePath = item.get("image").toString();
            if(!imagePath.isEmpty()){
                this.menuImages.put(item.get("nameDish").toString(), new LocatedImage(imagePath));
            }
        }
    }

    public LocatedImage getMenuImage(String nameDish){
        return this.menuImages.get(nameDish);
    }

    public LocatedImage getDefaultDishImage(){
        if(defaultDishImage == null){
            defaultDishImage = new LocatedImage(this.DEFAULT_DISH_IMAGE_PATH);
        }
        return this.defaultDishImage;
    }

    public void initializeCoursesImages(ArrayList<HashMap<String, Object>> courses){
        this.coursesImages = new HashMap<>();
        for(HashMap<String, Object> course : courses){
            String imagePath = course.get("img").toString();
            if(!imagePath.isEmpty()){
                this.coursesImages.put(course.get("name").toString(), new LocatedImage(imagePath));
            }
        }
    }

    public LocatedImage getCourseImage(String courseName){
        return this.coursesImages.get(courseName);
    }

    //----------------------EMPLOYEES----------------------//
    private void initializeEmployeesBasicImages(){
        this.employeesBasicImg = new HashMap<>();
        employeesBasicImg.put(this.EMPLOYEE_DEFAULT_IMAGE_TYPE, new LocatedImage(this.EMPLOYEE_DEFAULT_IMAGE_PATH));
        employeesBasicImg.put(this.EMPLOYEE_CHEF_IMAGE_TYPE, new LocatedImage(this.EMPLOYEE_CHEF_IMAGE_PATH));
        employeesBasicImg.put(this.EMPLOYEE_WAITER_IMAGE_TYPE, new LocatedImage(this.EMPLOYEE_WAITER_IMAGE_PATH));
    }

    public void initializeEmployeesImages(ArrayList<HashMap<String, Object>> employees){
        this.employeesImages = new HashMap<>();
        for(HashMap<String, Object> employee : employees){
            String imagePath = employee.get("image").toString();
            if(!imagePath.isEmpty()){
                this.employeesImages.put(employee.get("codice_fiscale").toString(), new LocatedImage(imagePath));
            }
        }
    }

    public LocatedImage getEmployeeImage(String fiscalCode){
        return this.employeesImages.get(fiscalCode);
    }

    public LocatedImage getDefaultEmployeeImage(){
        if(defaultEmployeeImage == null){
            defaultEmployeeImage = new LocatedImage(this.EMPLOYEE_DEFAULT_IMAGE_PATH);
        }
        return this.defaultEmployeeImage;
    }

    public LocatedImage getEmployeeBasicImage(String type){
        return this.employeesBasicImg.get(type);
    }

    //----------------------ORDERS----------------------//
    public LocatedImage getDeliveringImage(){
        if(deliveringImage == null){
            deliveringImage = new LocatedImage(this.DELIVERING_IMAGE_PATH);
        }
        return this.deliveringImage;
    }

    public LocatedImage getHistoryImage(){
        if(historyImage == null){
            historyImage = new LocatedImage(this.HISTORY_IMAGE_PATH);
        }
        return this.historyImage;
    }

    //----------------------PRODUCTS----------------------//
    public LocatedImage getDefaultProductImage(){
        if(defaultProductImg == null){
            defaultProductImg = new LocatedImage(this.PRODUCT_DEFAULT_IMAGE_PATH);
        }
        return this.defaultProductImg;
    }

    //----------------------UTILITIES----------------------//
    public LocatedImage getGasImage(){
        if(gasImage == null){
            gasImage = new LocatedImage(this.GAS_IMAGE_PATH);
        }
        return this.gasImage;
    }

    public LocatedImage getBulbImage(){
        if(bulbImage == null){
            bulbImage = new LocatedImage(this.BULB_IMAGE_PATH);
        }
        return this.bulbImage;
    }

    public LocatedImage getWaterDropImage(){
        if(waterDropImage == null){
            waterDropImage = new LocatedImage(this.WATER_DROP_IMAGE_PATH);
        }
        return this.waterDropImage;
    }

    public LocatedImage getProductImage(int productBarcode, String path){
        LocatedImage res = this.productsImages.get(productBarcode);
        if(res == null){
            path = "file:"+path;
            res = new LocatedImage(path);
            this.productsImages.put(productBarcode,res);
        }
        return res;
    }

    //----------------------BUTTON ICONS----------------------//
    public LocatedImage getBackIcon(){
        if(this.backIcon == null){
            this.backIcon = new LocatedImage(this.BACK_ICON);
        }
        return this.backIcon;
    }

    public LocatedImage getNextIcon(){
        if(this.nextIcon == null){
            this.nextIcon = new LocatedImage(this.NEXT_ICON);
        }
        return this.nextIcon;
    }
}
