package br.com.lampdata.shij;

import java.io.File;
import java.io.FileInputStream;
import java.util.Properties;

public abstract class Task {
	
	protected String name;
	protected Properties props;

	public void setName(String name) {
		this.name = name;
	}

	public void initialize() throws Exception {
		props = new Properties();
		File cfg = new File(Main.CONF, name+".cfg");
		FileInputStream fis = new FileInputStream(cfg);
		props.load(fis);
		fis.close();
	}

	public abstract void execute(String transaction, String repository) throws TaskException;

}
