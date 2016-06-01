package Strategy;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Properties;


public class ParamsReader extends Console {
	protected ArrayList<String> params = new ArrayList<String>();
	protected int paramCount = 0;
	private String configPath = "";
	private Properties config; 
	
	protected String getParamValue(String name)
	{
		String buf = "";
		for (int i=0; i < this.paramCount; i++) {
			buf = this.params.get(i);
			if (buf.indexOf('=') > -1) {
				String res[] = buf.split("=");
				return res[1];
			} 
		}
		return "";
	}
	
	protected boolean paramExists(String name) 
	{
		String buf = "";
		for (int i=0; i < this.paramCount; i++) {
			buf = this.params.get(i);
			if (buf.equalsIgnoreCase(name))
				return true;
		}
		return false;
	}
	
	protected boolean loadConfig()
	{
		this.configPath = this.getParamValue("--config-file");
		if (this.configPath.length() == 0) {
			this.error("missing config file!");
			this.echo("Use --config-file=<path> to specify config file");
			System.exit(100);
		}
		this.config = new Properties();
		try {
			this.config.load(new FileInputStream(this.configPath));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return true;
	}
	
	protected String getConfigValue(String name)
	{
		if (!this.config.containsKey(name)) {
			this.error("Config key " + name + " not found.");
			System.exit(100);
		}
		return this.config.getProperty(name);
	}
}
