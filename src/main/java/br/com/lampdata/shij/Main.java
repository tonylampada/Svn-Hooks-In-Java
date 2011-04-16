package br.com.lampdata.shij;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

public class Main {
	
	public static File SHIJ_HOME;
	public static File CONF;
	public static File SCRIPTS;
	public static Map<String, Task> taskMap;
	public static String script;
	public static String transaction;
	public static String repository;
	public static List<Task> taskList;
	
	
	public static void main(String[] args) throws Exception {
		String shijHome = System.getenv("SHIJ_HOME");
		if(shijHome == null){
			System.err.println("SHIJ_HOME is not set!");
		}
		validate(args);
		script = args[0];
		transaction = args[1];
		repository = args[2];
		initialize();
		loadScript();
		run();
	}

	private static void loadScript() throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(new File(SCRIPTS, script))));
		String line = null;
		taskList = new ArrayList<Task>();
		while((line = br.readLine()) != null){
			line = line.trim();
			if(line.length() != 0 && !line.startsWith("#")){
				taskList.add(taskMap.get(line));
			}
		}
		br.close();
	}

	private static void run() throws Exception {
		for(Task task : taskList){
			try{
				task.initialize();
				task.execute(transaction, repository);
			} catch (TaskException e){
				System.err.println(e.getMessage());
				System.exit(1);
			}
		}
	}

	private static void validate(String[] args) {
		if(args.length != 3){
			System.err.println("shij expectedParameters: <script> <transaction> <repository>");
		}
	}

	private static void initialize() throws Exception {
		SHIJ_HOME = new File(System.getenv("SHIJ_HOME"));
		CONF = new File(SHIJ_HOME, "conf");
		SCRIPTS = new File(SHIJ_HOME, "scripts");
		File tasksFile = new File(CONF, "tasks.cfg");
		Properties props = new Properties();
		FileInputStream fis = new FileInputStream(tasksFile);
		props.load(fis);
		fis.close();
		for(Object key : props.keySet()){
			String name = (String)key;
			Task task = (Task)Class.forName(props.getProperty(name)).newInstance();
			task.setName(name);
			taskMap.put(name, task);
		}
	}
}
