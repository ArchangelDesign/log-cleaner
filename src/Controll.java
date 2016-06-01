import java.util.ArrayList;


public class Controll {
	
	public static final int STRATEGY_MISSING 	= 0;
	public static final int STRATEGY_HELP		= 1;
	
	private int strategy = 0;
	private ArrayList<String> params = new ArrayList<String>();
	private int paramCount = 0;
	
	public Controll(String[] params)
	{
		this.loadParams(params);
		this.pickStrategy();
	}
	
	private void loadParams(String[] params)
	{
		for (String param : params) {
			this.params.add(param);
		}
		this.paramCount = this.params.size();
	}
	
	private void pickStrategy() {
		if (this.paramCount == 0) {
			this.strategy = this.STRATEGY_MISSING;
			return;
		}
		
		if (this.paramExists("--help")) {
			this.strategy = this.STRATEGY_HELP;
		}
	}
	
	private String getParamValue(String name)
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
	
	private boolean paramExists(String name) 
	{
		String buf = "";
		for (int i=0; i < this.paramCount; i++) {
			buf = this.params.get(i);
			if (buf.equalsIgnoreCase(name))
				return true;
		}
		return false;
	}
	
	public void runStrategy()
	{
		switch (this.strategy) {
		case 0:
			
			break;

		default:
			;
		}
	}
}
