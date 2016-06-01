package Strategy;

import java.lang.reflect.Field;

import com.archangel.design.Parameters;
import com.archangel.design.StrategyInterface;;

public class HelpStrategy implements StrategyInterface {

	@Override
	public void run() {
		System.out.println("Log Cleaner version 1.0");
		Field[] f = Parameters.class.getDeclaredFields();
		System.out.println("Available commands:");
		for (Field field : f) {
			try {
				System.out.println(field.get(null));
			} catch (IllegalArgumentException | IllegalAccessException e) {
				e.printStackTrace();
			}
		}
	}

}
