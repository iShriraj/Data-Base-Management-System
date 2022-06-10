import java.io.*;
import java.util.*;
import java.util.regex.*;  

public class DBMS_System {
	static String query="";
	public static void main(String[] args) {
		StartUp();
		Scanner scan = new Scanner(System.in);
		
		//Continuous Terminal Input ---------------------------------------------------
		while(!(query.equals("exit"))) {
			System.out.print("\n> ");
			query = scan.nextLine(); 
			String[] queryArr = query.split(" ");
			CheckSyntax(queryArr);
		}
		scan.close();
		/*----------------------------------------------------------------------------
		CREATE TABLE students ( id int , name varchar , dept varchar ) ;
		INSERT INTO students ( 01 , ' Nikhil ' , ' CS ' ) ;
		INSERT INTO students ( 02 , ' Shivansh ' , ' CS ' ) ;
		INSERT INTO students ( 03 , ' Sejal ' , ' CS ' ) ;
		INSERT INTO students ( 04 , ' Shriraj ' , ' CS ' ) ;
		INSERT INTO students ( 05 , ' Neel ' , ' MECH ' ) ;
		INSERT INTO students ( 01 , ' Nirvisha ' , ' IT ' ) ;
		*/
		}
	
	public static void WriteFile(String filename, String text) {
		FileWriter fw = null;
        BufferedWriter bw = null;
        PrintWriter pw = null;
		try {
			fw = new FileWriter(filename+".txt",true);
		    bw = new BufferedWriter(fw);
	        pw = new PrintWriter(bw);
	        pw.println(text);
	        pw.flush();
		} 
		catch (IOException e) {
		      System.out.println("An error occurred in MYSQL. Please try again.");
		      e.printStackTrace();
		}
		try {
            pw.close();
            bw.close();
            fw.close();
        } catch (IOException io) {
        }
	}
	public static void WriteFileWhere(String filename, String text, String clauseValue) {
		FileWriter fw = null;
        BufferedWriter bw = null;
        PrintWriter pw = null;
		try {
			fw = new FileWriter(filename+".txt",true);
		    bw = new BufferedWriter(fw);
	        pw = new PrintWriter(bw);
	        pw.println(text);
	        pw.flush();
		} 
		catch (IOException e) {
		      System.out.println("An error occurred in MYSQL. Please try again.");
		      e.printStackTrace();
		}
		try {
            pw.close();
            bw.close();
            fw.close();
        } catch (IOException io) {
        }
	}
	public static void ReadFileWhere(String filename, String clauseValue) {
		try {
			File f1=new File(filename+".txt"); 
		    String[] words = null; 
		    FileReader fr = new FileReader(f1); 
		    BufferedReader br = new BufferedReader(fr);
		    String s;
		    int o=0;
		    while((s=br.readLine())!=null){
		    	if(s.contains("#"+clauseValue+"#")) {
			    	words=s.split("#"); 
			    	for(int i =0;i<words.length;i++) {
			    		System.out.print(words[i]+" ");
			    	}
		    	}
		    	System.out.println("");
		    }		    
	        br.close(); 
		}catch(Exception e) {
			ShowError(query);
		}
	}
	public static void ReadFile(String filename) {
		try {
			File f1=new File(filename+".txt"); 
		    String[] words = null; 
		    FileReader fr = new FileReader(f1); 
		    BufferedReader br = new BufferedReader(fr);
		    String s;
		    int o=0;
		    while((s=br.readLine())!=null){
		    	words=s.split("#"); 
		    	for(int i =0;i<words.length;i++) {
		    		System.out.print(words[i]+" ");
		    	}
		    	System.out.println("");
		    } 
	        br.close(); 
		}catch(Exception e) {
			ShowError(query);
			e.printStackTrace();
		}
	}
	public static void checkColumn(String tablename, String column) {
		String checkString = "";
		String datatype="";
		try {
			File f1=new File("schema.txt"); 
		    String[] words = null; 
		    FileReader fr = new FileReader(f1); 
		    BufferedReader br = new BufferedReader(fr);
		    String s;
		    while((s=br.readLine())!=null){
		    	words=s.split("#"); 
		        
		    	if (words[0].equals(tablename)){
		        	for(int i=0;i<words.length;i++) {
		        		if(words[i].equals(column)) {
		        			datatype=words[i+1];
		        		}
		        	}
		        }
		    }
	        br.close(); 
		}catch(Exception e) {
			ShowError(query);
		}
		
	}
	public static void CheckSyntax(String[] queryArr){ 
		String arr[] = {"CREATE", "DROP", "DROP", "UPDATE", "SELECT", "DESCRIBE", "INSERT", "HELP","SHOW", "QUIT"}; 
		int opid = 0;
		for(int i=0; i<arr.length; i++) {
			if(arr[i].equalsIgnoreCase(queryArr[0]))
				opid = ++i; 
		}
		switch(opid){
			case 1 : CreateTable(queryArr);
				break;
			case 2 : DropTable(queryArr);
				break;
			case 3 : Delete(queryArr);
				break;
			case 4 :
				break;
			case 5 : Select(queryArr);
				break;
			case 6 : Describe(queryArr);
				break;
			case 7 : Insert(queryArr);
				break;
			case 8 : Help(queryArr);
				break;
			case 9 : ShowTables(queryArr);
				break;
			case 10 : 
				System.out.print("Thank you for using MySQL Command Client");
				System.exit(0);
				break;
			default : ShowError(query);
				break;
		}
		
	}
	// UPDATE Students SET DEPT = IT WHERE ID = 01 ;
	public static void Update(String queryArr[]) {
				String tablename = queryArr[1];
				int linenumber = TableExists(tablename);
				if(linenumber!=0) {
					if(queryArr[2].equalsIgnoreCase("SET")) {
						//if(checkColumn()) {
							if(queryArr[4].equalsIgnoreCase("=")) 
								ReadFileWhere(tablename,queryArr[9]);
							else
								System.out.println("not found in table");
						//}
						//else
						//	System.out.println("wrong column mentioned");
					}
					else {
						ReadFile(tablename);
					}
				}else {
					System.out.print("Error - Table Does not Exist\n");
					ShowError(query);
				}
			
		
	}
	
	
	public static void Select(String queryArr[]) {
		if(queryArr[1].equalsIgnoreCase("*")) {
			if(queryArr[2].equalsIgnoreCase("FROM")) {
				String tablename = queryArr[3];
				int linenumber = TableExists(tablename);
				if(linenumber!=0) {
					if(queryArr[4].equalsIgnoreCase("WHERE")) {
						//if(checkColumn()) {
							if(queryArr[6].equalsIgnoreCase("=")) 
								ReadFileWhere(tablename,queryArr[7]);
							else
								System.out.println("not found in table");
						//}
						//else
						//	System.out.println("wrong column mentioned");
					}
					else {
						ReadFile(tablename);
					}
				}else {
					System.out.print("Error - Table Does not Exist\n");
					ShowError(query);
				}
			}else {
				ShowError(query);
			}
		}else {
			ShowError(query);
		}
		//return 0;
	}
	public static void Describe(String queryArr[]){
		String tablename = queryArr[1];
		int linenumber = TableExists(tablename);
		if(linenumber!=0) {
			try {
				File f1=new File("schema.txt"); 
			    String[] words = null; 
			    FileReader fr = new FileReader(f1); 
			    BufferedReader br = new BufferedReader(fr);
			    String s;
			    int counter = 1;
			    while((s=br.readLine())!=null){
			    	if(linenumber != counter)
			    		continue;
			    	words=s.split("#");
			    	int length = words.length;
			    	length=length-1;
			    	for (int i=1; i<length; i++){
			        	System.out.print(words[i]);
			        	System.out.println(" -- "+words[++i]);
			        }
			    }
		        br.close(); 
			}catch(Exception e) {
				ShowError(query);
			}
		}else {
			System.out.print("Error - Table Does not Exist\n");
			ShowError(query);
		}
		System.out.println("\nQuery Ok, 0 rows affected (1.47 sec)\n");
	}
	
	public static void Insert(String queryArr[]){
		if(queryArr[1].equalsIgnoreCase("INTO")) {
			String tablename = queryArr[2];
			int linenumber = TableExists(tablename);
			if(linenumber!=0) {
				String s="#";
				int first = 0;
				for(int i=3;i<queryArr.length;i++) {
					if(queryArr[i].equals("(") || queryArr[i].equals(")") || queryArr[i].equals(";") || queryArr[i].equals(",")|| queryArr[i].equals("'")) 
						continue;
					if(first!=0)
						s = s.concat("#");
					first = 1;
					s = s.concat(queryArr[i]);
					
				}
				s=s+"#";
				WriteFile(tablename,s);
				System.out.println("Query Ok, 0 rows affected (1.47 sec)\n");
			}else {
				System.out.print("Error - Table Does not Exist\n");
				ShowError(query);
			}
		}else {
			ShowError(query);
		}
	}
	
	public static int TableExists(String tablename) {
		int linenumber = 0;
		try {
			File f1=new File("schema.txt"); 
		    String[] words = null; 
		    FileReader fr = new FileReader(f1); 
		    BufferedReader br = new BufferedReader(fr);
		    String s;
		    while((s=br.readLine())!=null){
		    	words=s.split("#"); 
		        linenumber++;
		    	if (words[0].equals(tablename)){
		        	return linenumber;
		        }
		    }
	        br.close(); 
		}catch(Exception e) {
			ShowError(query);
		}
		
		return 0;
	}
	public static void ShowTables(String queryArr[]) {
		if(queryArr.length != 3) {
			ShowError(query);
			return;
		}
		if(!queryArr[1].equalsIgnoreCase("TABLES")) {
			ShowError(query);
			return;
		}
		//Display Table Names
		System.out.println("Tables_in_Databse");
		
		try{  
			File file = new File("schema.txt");     
			FileReader fr = new FileReader(file);  
			BufferedReader br = new BufferedReader(fr);      
			String line;  
			while((line=br.readLine())!=null)  {
				String[] names = line.split("#", 2);
				System.out.print("1. "+names[0]+"\n");
			}  
			fr.close();  
		}
		catch(IOException e) {  
			e.printStackTrace();  
		}
		
	}
	public static void Delete(String queryArr[]) {
		String tablename = "";
		if(queryArr.length != 3) {
			ShowError(query);
			return;
		}
		if(!queryArr[1].equalsIgnoreCase("TABLE")) {
			ShowError(query);
			return;
		}
		
		tablename = queryArr[2];
		
		//Deleting tablename.txt
		File file = new File(tablename+".txt");
		if (file.delete()) {
			System.out.println("Query OK, 0 rows affected (0.02 sec)\n");
		}
		else {
			ShowError(query);
			return;
		}
		//Deleting Table Entry form Schema.txt
		try{  
			file = new File("schema.txt");     
			FileReader fr = new FileReader(file);  
			BufferedReader br = new BufferedReader(fr);   
			StringBuffer sb = new StringBuffer();   
			String line;  
			while((line=br.readLine())!=null)  {
				if(Pattern.matches(tablename+"+",line)){
					
				}else{
					sb.append(line);     
					sb.append("\n");
				}
			}  
			fr.close();  
		}  
		catch(IOException e) {  
			e.printStackTrace();  
		} 
	}
	
	public static void CreateTable(String queryArr[]) {
		String[] datatypes = {"int","varchar","bigint","date"};
		List<String> datatypelist = new ArrayList<>(Arrays.asList(datatypes));
		String s = "";
		String filename = "";
		if(!queryArr[1].equalsIgnoreCase("TABLE")) {
			ShowError(query);
			return;
		}
		try{
			filename = queryArr[2];
			int exists = TableExists(filename);
			if(exists!=0) {
				System.out.println("Error - The Table Already Exists in the Database");
				return;
			}
			s = s.concat(filename);
			File schema = new File(filename+".txt");
			if(schema.createNewFile());
		}catch (IOException e){
			System.out.println("An error occurred while creating schema file.");
			e.printStackTrace();
		}
		int nameflag = 0;
		for(int i=3;i<queryArr.length;i++) {
			
			if(queryArr[i].equals("(") || queryArr[i].equals(")") || queryArr[i].equals(";") || queryArr[i].equals(",")) {
				continue;
			}
			if(nameflag==0) {
				s = s.concat("#");
				s = s.concat(queryArr[i]);
			}
			else {
				if(datatypelist.contains(queryArr[i])) {
					s = s.concat("#");
					s = s.concat(queryArr[i]);
				}
				else {
					ShowError(query);
				}
				
			}
		}
		WriteFile("schema",s);
		System.out.println("Query Ok, 0 rows affected (1.47 sec)\n");
	}

	public static void DropTable(String arr[]) {
		if(!(arr.length == 4)) {
			ShowError(query);
			return;
		}
		if(!(arr[3].equals(";"))) {
			ShowError(query);
		}
		if(arr[0].equalsIgnoreCase("DROP")) {
			if(arr[1].equalsIgnoreCase("TABLE")) {
				String tablename = arr[2];
				try{         
					File f = new File(tablename+".txt");           
					f.delete(); 
					// Clear Table details from schema file 
					File f1=new File("schema.txt"); 
					File tempFile = new File("myTempFile.txt");
				    String[] words = null; 
				    FileReader fr = new FileReader(f1); 
				    BufferedReader br = new BufferedReader(fr);
				    BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));
				    String s;
				    int flag = 0;
				    while((s=br.readLine())!=null){
				    	words=s.split("#"); 
				        for (String word : words){
				        	if (word.equals(tablename)){
				        		flag = 1;
				            }
				        }
				        if(flag == 1) {
				        	flag = 0;
				        	continue;
				        }
				        writer.write(s + System.getProperty("line.separator"));
				    }
				    writer.close(); 
			        br.close(); 
			        tempFile.renameTo(f1);
			        
			        System.out.println("Query OK, 0 rows affected (0.02 sec)\n");
				}catch(Exception e){  
					System.out.println("ERROR 1051 (42S02): Unknown table '"+tablename+"'");
					System.out.print(e);
				}
			}else {
				ShowError(query);
			}
		}else {
			ShowError(query);
		}
		
	}
	public static void StartUp() {
		Scanner scan = new Scanner(System.in);
		System.out.print("MySQL Command Client\n"
				+ "Enter Password: ");
		String password = "nikhil";
		String pass = scan.next();
		if(password.equals(pass)) {
			System.out.println("\nWelcome to the MySQL monitor.  Commands end with ;\n" + 
					"Your MySQL connection id is 11\n" + 
					"\n" + 
					"Copyright (c) 2000, 2021, Group TY69 and/or its affiliates.\n" + 
					"\n" + 
					"Type 'help;' or '\\h' for help.\n\n");
			try{
				File schema = new File("schema.txt");
				schema.createNewFile();
			}catch (IOException e){
				System.out.println("An error occurred while creating schema file.");
				e.printStackTrace();
			}
		}else {
			System.out.println("ERROR 1045 (28000): Access denied for user 'nikhil'@'localhost' (using password: YES)");
		}
		
	}
	
	public static void ShowError(String query) {
		System.out.println("You have an error in your SQL syntax;\n"
		 		+ "Check the manual that corresponds to your MySQL server version for the right syntax to use near \n'"
		 		+ query+ "' at line 1\n");
	}
	
	public static void Help(String arr[]) {
		if(!(arr.length==1)) {
			ShowError(query);
			return;
		}

		System.out.print("For information about MySQL products and services, visit:\n" + 
				"   http://www.mysql.com/\n" + 
				"For developer information, including the MySQL Reference Manual, visit:\n" + 
				"   http://dev.mysql.com/\n" + 
				"To buy MySQL Enterprise support, training, or other products, visit:\n" + 
				"   https://shop.mysql.com/\n" + 
				"\n" +
				"List of all MySQL commands:\n" + 
				"Note that all text commands must be first on line and end with ';'\n" + 
				"01. Create Table CREATE TABLE table_name (attr_name1 attr_type1,attr_name2 attr_type2, …)\n" + 
				"02. Drop Table DROP TABLE table_name\n" + 
				"03. DESCRIBE T_NAME Describes the schema of table T_NAME.\n" + 
				"04. Insert INSERT INTO table_name VALUES(attr_value1,attr_value2, …)\n" + 
				"05. Note that NULL is not permitted for any attribute.\n" + 
				"06. Delete DELETE FROM table_name WHERE condition_list\n" + 
				"07. Update UPDATE table_name SET attr1_name = attr1_value,attr2_name = attr2_value… WHERE condition_list\n" + 
				"08. Select SELECT attr_list FROM table_list WHERE condition_list\n" + 
				"09. HELP TABLES Prints out the list of tables defined.\n" + 
				"10. HELP CMD Describes the built-in command CMD, where CMD is\n" + 
				"11. QUIT Quits the program\n\n");
	}
}
