package Strategy;

import java.io.File;
import java.util.ArrayList;

import com.archangel.design.StrategyInterface;

public class ArchiveCleanerStrategy extends ParamsReader implements
		StrategyInterface {
	
	private File archive;
	private String archivePath;
	private ArrayList<String> archivedFiles = new ArrayList<String>();

	@Override
	public void run() {
		this.loadConfig();
		this.initialize();
	}
	
	private void initialize() {
		this.archivePath = this.getConfigValue("ArchiveDir");
		if (this.archivePath.length() == 0) {
			this.error("ArchiveDir config value not found.");
			System.exit(100);
		}
		this.archive = new File(this.archivePath);
		if (!this.archive.isDirectory()) {
			this.error("ArchiveDir is not a directory.");
			System.exit(100);
		}
		File[] list = this.archive.listFiles();
		
		for (File f : list) {
			this.archivedFiles.add(f.getAbsolutePath());
		}
	}
	
	private void process()
	{
		int totalFiles = this.archivedFiles.size();
		if (totalFiles == 0) {
			return;
		}
	}

}
