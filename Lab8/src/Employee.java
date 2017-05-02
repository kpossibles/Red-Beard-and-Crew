
public class Employee implements Comparable<Employee>{
	String title;
	String firstname;
	String lastname;
	String gender;
	String department;
	String phone;
	
	public Employee(String _fname, String _lname, String _dept, String _phone, String _title, String _gender){
		firstname = toTitleCase(_fname);
		lastname =toTitleCase(_lname);
		department = _dept.toUpperCase();
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
		if(firstname.length()+lastname.length()>8)
			return String.format("TITLE: %s\tNAME: %s %s\tDEPT: %s\tPHONE: %s", title, firstname, lastname, department, phone);
		else
			return String.format("TITLE: %s\tNAME: %s %s\t\tDEPT: %s\tPHONE: %s", title, firstname, lastname, department, phone);
	}

	@Override
	public int compareTo(Employee o) {
		int last = this.lastname.compareTo(o.lastname);
		return last == 0 ? this.firstname.compareTo(o.firstname) : last;
	}

}
