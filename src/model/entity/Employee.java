package model.entity;

/**
 *
 * @author milar
 */
import java.sql.Date; 
import java.time.LocalDate;
import java.util.HashMap;

public class Employee implements Entity{
    private final String TABLE_NAME = "employee";

    private String codice_fiscale;
    private String name;
    private String surname;
    private String role;
    private String begin_date;
    private String end_date;
    private int wage;
    
    public Employee (String codice_fiscale, String name, String surname,  String role, String begin_date , String end_date , int wage){
        this.codice_fiscale=codice_fiscale;
        this.name=name;
        this.surname=surname;
        this.role=role;
        this.begin_date=begin_date;
	this.end_date=end_date; // nel metodo conversione da localdate di java a date sql
        this.wage=wage;
    }
    
    public String getCodiceF(){
        return this.codice_fiscale;
    }
    
    public String getName(){
        return this.name;
    }
    
    public String getSurname(){
        return this.surname;
    }
    public String getRole(){
        return this.role;
    }
    
    public String getBeginDate(){
	//Date sql_begin_date = Date.valueOf(this.begin_date);
	return this.begin_date;
    }
    public String getEndDate(){
	//Date sql_end_date = Date.valueOf(this.end_date);
	return this.begin_date;
    }
    
    public int getWage(){
        return this.wage;
    }

    @Override
    public String getTableName() {
        return this.TABLE_NAME;
    }
    
    @Override
    public HashMap<String, Object> map() {
        HashMap<String, Object> res = new HashMap<String, Object>();
        res.put("codice_fiscale", this.codice_fiscale);
        res.put("name", this.name);
        res.put("surname", this.surname);
        res.put("role", this.role);
        res.put("begin_date", this.begin_date);
        res.put("end_date", this.end_date);
        res.put("wage", this.wage);
        return res;
    }
    
}
