/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import controller.ControllerForView;
import dao.ReceiptTable;
import entity.Receipt;
import java.sql.Date;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;


/**
 *
 * @author Natalia
 */
public class TestClass {
    
    public static void main(String[] args){
        //Product p = new Product(1, "prodotto_di_prova", 10,10);
        LocalDate ldate = LocalDate.of(2020,11,2);
//        Receipt r = new Receipt(5,date,10);
//        ControllerForView.getInstance().save(r);  
//        
//        //per convertire il timestamp che sqlite salva in localdate (dividere per mille il timestamp)
//        LocalDate localdate = Instant.ofEpochSecond(1604271600).atZone(ZoneId.systemDefault()).toLocalDate();
//        System.out.println(localdate);

//        String test = "test";
//        Integer testInt = 14;
//        System.err.println(testInt.getClass().getName().replace("java.lang.", ""));
//        System.err.println(test.getClass().getName().replace("java.lang.", ""));
//        System.err.println(date.getClass().getName().replace("java.time.", ""));

        ReceiptTable rt = new ReceiptTable();
        Integer num = 5;
        Date date = Date.valueOf(ldate);
        List<Receipt> resList = rt.getFrom(date);
        for(Receipt r : resList){
            System.out.println(r.getNumber());
        }

    }
    
}
