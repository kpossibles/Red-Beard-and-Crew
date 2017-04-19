import org.junit.Assert;
import static org.junit.Assert.*;
import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

public class DirTest {
	
	public Directory dir;
	public DirectoryProxy dirP;
	public MainDirectory mDir;
	public Employee e1;
	public Employee e2;
	public Employee e3;
	public ArrayList<Employee> employees;
	
	@Before
	public void setUp() {
		dir = new Directory();
		dirP = new DirectoryProxy();
		mDir = new MainDirectory();

		/*
		 *  Mary Smith ACCT 4147665644
		 *	Bob Smith ACCT 4142024000
		 *	Bill Atkinson ENG 4149909899
		 */
		e1 = new Employee("Mary", "Smith", "ACCT", "4147665644");
		e2 = new Employee("Bob", "Smith", "ACCT", "4142024000");
		e3 = new Employee("Bill", "Atkinson", "ENG", "4149909899");
		
		employees = new ArrayList<Employee>();
		employees.add(e1);
		employees.add(e2);
		employees.add(e3);
	}
	@Test
	public void emptyEmployees(){
		String s = dir.print();
		s.replace("\n", "");
		s = dir.print();
		s.replace("\n", "");
		assertTrue(s.equalsIgnoreCase("<empty directory>"));
	}
	
	@Test
	public void addEmployees123(){
		dir.add(employees);
		String s = dir.print();
		s.replace("\n", "");
		assertTrue(s.equalsIgnoreCase("Atkinson, Bill ENG 4149909899Smith, Mary ACCT 4147665644Smith, Bob ACCT 4142024000"));
	}
	
	@Test
	public void clearEmployees123(){
		dir.add(employees);
		String s = dir.print();
		s.replace("\n", "");
		assertTrue(s.equalsIgnoreCase("Atkinson, Bill ENG 4149909899Smith, Mary ACCT 4147665644Smith, Bob ACCT 4142024000"));
		dir.clear();
		s = dir.print();
		s.replace("\n", "");
		assertTrue(s.equalsIgnoreCase("<empty directory>"));
	}
}
