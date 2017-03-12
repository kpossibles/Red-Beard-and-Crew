
public class Employee {
	String fname;
	String lname;
	String dept;
	String phone;
	
	public Employee(String _fname, String _lname, String _dept, String _phone){
		fname = toTitleCase(_fname);
		lname =toTitleCase(_lname);
		dept = _dept.toUpperCase();
		phone = _phone;
	}
	
	private String toTitleCase(String str){
		String temp = str.toLowerCase();
		temp = temp.substring(0,1).toUpperCase() + temp.substring(1, temp.length());
		return temp;
	}
	
	public String toString(){
		return lname + ", " + fname + " " + dept + " " + phone;
	}

}
