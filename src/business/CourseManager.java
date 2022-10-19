/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package business;

import java.util.ArrayList;
import java.util.HashMap;
import model.dao.CourseTable;
import model.dao.MenuTable;
import model.entity.Course;

/**
 *
 * @author milar
 */
public class CourseManager {
     private MenuTable menuTable;
    private CourseTable courseTable;

    public CourseManager(){
        this.menuTable = new MenuTable();
        this.courseTable = new CourseTable();
    }

    public ArrayList getAll(){
        ArrayList<Course> courses = this.courseTable.getAll();
        ArrayList<HashMap<String,Object>> res  = new ArrayList<HashMap<String,Object>>();
        for(Course course : courses){
            res.add(course.map());
        }
        return res;
    }

    public ArrayList getFrom(Object searchParam, String paramName ){
        ArrayList<Course> courses = this.courseTable.getFrom(searchParam, paramName);
        ArrayList<HashMap<String,Object>> res  = new ArrayList<HashMap<String,Object>>();
        for(Course course : courses){
            res.add(course.map());
        }
        return res;
    }
    
    
}
