
public class Employee {
	String fname;
	String lname;
	String dept;
	String phone;
	
	public Employee(String _fname, String _lname, String _dept, String _phone){
		fname = _fname;
		lname = _lname;
		dept = _dept;
		phone = _phone;
	}
	
	public Employee(String json){
		//TODO
	}
	
	public String toJSON(){
		return String.format("{'fname':'%s','lname':'%s','dept':'%s','phone':'%s'}", fname, lname, dept, phone);
	}
	
	public String toString(){
		return lname + ", " + fname + " " + " " + dept + " " + phone;
	}

}
