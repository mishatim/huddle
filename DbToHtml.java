import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class DbToHtml {

	public interface DbAccessor {
		public List<Employee> getAllEmployees();
	}
	public interface GenericView {
		public String toHtmlTable(List<Employee> empLst);
	}
	
	// talk to Model and manipulate data here
	public class EmployeeService {
		DbAccessor db;
		public void setDb(DbAccessor d) {
			db = d;
		}
		public List<Employee> getEmployees() {
			return db.getAllEmployees();
		}
	}
	
	public class EmployeeController {
		public EmployeeService srv;
		public GenericView view;
		
		// route the response back
		public String getAll() {
			List<Employee> allEmps = srv.getEmployees();
			return view.toHtmlTable(allEmps);
		}
	}

	// Model: employee data here
	public class Employee {
		public Employee(String name, String mgr) {
			this.empName = name;
			this.mgrName = mgr;
		}
		public Employee(String emp_mgr) {
			String[] combo = emp_mgr.split(":");
			this.empName = combo[0].trim();
			this.mgrName = combo.length > 1 ? combo[1] : "";
		}
		String empName;
		String mgrName;
	}

	// render data to HTML here
	public class EmployeeView implements GenericView {
		
		@Override
		public String toHtmlTable(List<Employee> empLst) {
			StringBuilder sb = new StringBuilder();
			sb.append("<html><head><title></title></head>");
			sb.append("<body><H1>List of employees and managers<br><table>");
			// add table header
			sb.append("<tr><td><h3>EmployeeName</td>").append("<td><h3>ManagerName</td></tr>");
			// loop thru and add rows
			for (Employee empl : empLst) {
				// create a row
				sb.append("<tr><td>").append(empl.empName).append("</td>").append("<td>").append(empl.mgrName).append("</td></tr>");
			}
			
			sb.append("</table").append("</body>").append("</html>");
			return sb.toString();
		}
	}
	
	public class MockSQLDbAccessor implements DbAccessor {

		@Override
		public List<Employee> getAllEmployees() {
			List<Employee> empLst = new ArrayList<>();
			// say, we use JDBC to get data from MySQL
			try {
				String sqlHostName = "loacalhost";
				Connection conn = DriverManager.getConnection("jdbc:mysql://" + sqlHostName + "/test?user=huddle&password=huddleisgreat");
				String sql = "select emp.name as Employee, mgr.name as Manager from employee emp, employee mgr where emp.mgr_id = mgr.id";
				Statement stmt = conn.createStatement();
				ResultSet res = stmt.executeQuery(sql);
				while (res.next()) {
					empLst.add(new Employee(res.getString("Employee"), res.getString("Manager")));
				}
				
			} catch (Exception e) {
				e.printStackTrace();
			}
			finally {
				// close the result set and connection
				// ...
			}
			return empLst;
		}
		
	}
	public class MockFileDbAccessor implements DbAccessor {

		@Override
		public List<Employee> getAllEmployees() {
			
			List<Employee> empLst = new ArrayList<>();
			
			// say we use File system as storage
			// fancy Java8 :)
			Path path = Paths.get("./src/employees.db");
			try (Stream<String> lines = Files.lines(path)) {
				lines.forEach(emp_mgr -> empLst.add(new Employee(emp_mgr)));
			}
			catch (Exception ex) {
				ex.printStackTrace();
			}
			
			// no need to close the stream, it's done behind the scenes

			return empLst;
		}
		
	}
	
	// this is where all comes together:
	// inject implementations and run
	public static void main (String[] args) {
		DbToHtml html = new DbToHtml();
		
		// get db accessor
		DbAccessor db = html.new MockFileDbAccessor();
		
		// get an instance of view
		GenericView empView = html.new EmployeeView();
		
		// inject dbaccessor into service
		EmployeeService service = html.new EmployeeService();
		service.setDb(db);
		
		
		// create controller and inject service and view into it
		EmployeeController controller = html.new EmployeeController();
		controller.srv = service;
		controller.view = empView;
		
		// request list of employees with managers as HTML table
		String htmlResp = controller.getAll();
		
		System.out.println(htmlResp);
		
	}
}

