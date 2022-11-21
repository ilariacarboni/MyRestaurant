/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package business;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;
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
    
    public HashMap<String, HashMap<String, Object>> getCoursesInfo(){
        HashMap<String, Integer> dishesPerCourse = this.menuTable.getTotalDishesPerCourse();
        HashMap<String, String> mostOrderedPerCourse = this.menuTable.getMostRequestedPerCourse();

        Set<String> courses = dishesPerCourse.keySet();
        HashMap<String, HashMap<String, Object>> res = new HashMap<>();
        for (String course :courses){
            HashMap<String, Object> courseInfo = new HashMap<>();
            courseInfo.put("totalDishes", dishesPerCourse.get(course));
            courseInfo.put("most-ordered", mostOrderedPerCourse.get(course));
            res.put(course, courseInfo);
        }
        return res;
    }
    
}
