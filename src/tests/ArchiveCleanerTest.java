package tests;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

import Strategy.*;

import com.archangel.design.*;

public class ArchiveCleanerTest {

	@Test
	public void test() {
		
	}
	
	@Test
	public void ControllerTest()
	{
		String params[] = {"--archive-clean", "--config-file=dummy.cfg"};
		Controller controller = new Controller(params);
		assertEquals(Controller.STRATEGY_ARCHIVECLEANER, controller.getStrategy());
	}
	
	@Test
	public void StrategyTest()
	{
		ArrayList<String> params = new ArrayList<String>();
		params.add("--archive-clean");
		params.add("--config-file=dummy.cfg");
	
		ArchiveCleanerStrategy strategy = new ArchiveCleanerStrategy(params);
		strategy.run();
	}

}
