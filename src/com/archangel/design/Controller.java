package com.archangel.design;
import java.util.ArrayList;

import Strategy.ArchiveCleanerStrategy;
import Strategy.HelpStrategy;
import Strategy.LogArchiverStrategy;


public class Controller {
	
	public static final int STRATEGY_MISSING 		= 0;
	public static final int STRATEGY_HELP			= 1;
	public static final int STRATEGY_LOGARCHIVER 	= 2;
	public static final int STRATEGY_ARCHIVECLEANER = 3;
	
	private int strategy = 0;
	private ArrayList<String> params = new ArrayList<String>();
	private int paramCount = 0;
	
	public Controller(String[] params)
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
			this.strategy = Controller.STRATEGY_MISSING;
			return;
		}
		
		if (this.paramExists(Parameters.param_help)) {
			this.strategy = Controller.STRATEGY_HELP;
		}
		
		if (this.paramExists(Parameters.param_log_archiver)) {
			this.strategy = Controller.STRATEGY_LOGARCHIVER;
		}
		
		if (this.paramExists(Parameters.param_log_cleaner)) {
			this.strategy = Controller.STRATEGY_ARCHIVECLEANER;
		}
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
		this.getStrategyClass(this.strategy).run();
	}
	
	private StrategyInterface getStrategyClass(int strategy)
	{
		switch (strategy) {
		case Controller.STRATEGY_MISSING:
		case Controller.STRATEGY_HELP:
			return new HelpStrategy();
		case Controller.STRATEGY_LOGARCHIVER:
			return new LogArchiverStrategy(this.params);
		case Controller.STRATEGY_ARCHIVECLEANER:
			return new ArchiveCleanerStrategy();
		default:
			return new HelpStrategy();
		}
	}
}
