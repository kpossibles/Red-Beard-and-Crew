
public class Employee {
	String title;
	String fname;
	String lname;
	String gender;
	String dept;
	String phone;
	
	public Employee(String _fname, String _lname, String _dept, String _phone, String _title, String _gender){
		fname = toTitleCase(_fname);
		lname =toTitleCase(_lname);
		dept = _dept.toUpperCase();
		phone = _phone;
		title = _title;
		gender = _gender;
	}
	
	private String toTitleCase(String str){
		String temp = str.toLowerCase();
		temp = temp.substring(0,1).toUpperCase() + temp.substring(1, temp.length());
		return temp;
	}
	
	public String toString(){
		return title + " " + fname + " " + lname + " " + dept + " " + phone;
	}

}
