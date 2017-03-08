import java.util.ArrayList;
import java.util.Collection;
import java.lang.reflect.Type;

import com.google.gson.*;
import com.google.gson.reflect.*;

public class MainDirectory {
	ArrayList<Employee> employees;
	Gson g;
	
	public MainDirectory(){
		employees = new ArrayList<>();
		g = new Gson();
	}
	
	public void add(String json){
		Type type = new TypeToken<Collection<Employee>>(){}.getType();
		Collection<Employee> list = g.fromJson(json, type);
		for(Employee e: list){
			employees.add(e);
		}
	}
	
	public void clear(){
		// TODO
		employees = new ArrayList<>();
	}
	
	public void print(){
		// TODO
		if(employees.size() == 0) {
			System.out.println("< empty string >");
		}
		else {
			for(Employee e : employees){
				System.out.println(e.toString());
			}
		}
	}
}
