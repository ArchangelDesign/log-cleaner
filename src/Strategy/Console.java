package Strategy;

import java.util.Date;

public class Console {
	
	private String currentDate()
	{
		Date d = new Date();
		return d.toString();
	}
	
	protected void echo(String s)
	{
		System.out.println(this.currentDate() + " | " + s);
	}
	
	protected void error(String s)
	{
		System.out.println(this.currentDate() + " | Error: "+s);
	}
}
