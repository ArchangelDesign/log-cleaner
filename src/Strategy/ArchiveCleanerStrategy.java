package Strategy;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;

import com.archangel.design.StrategyInterface;

public class ArchiveCleanerStrategy extends ParamsReader implements
		StrategyInterface {
	
	private File archive;
	private String archivePath;
	private ArrayList<String> archivedFiles = new ArrayList<String>();

	public ArchiveCleanerStrategy(ArrayList<String> params) {
		this.params = params;
	}
	
	@Override
	public void run() {
		this.loadConfig();
		this.initialize();
		this.process();
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
		String acceptedExtension = this.getConfigValue("ArchiveExtension");
		if (acceptedExtension.length() == 0) {
			this.error("Missing config parameter ArchiveExtension.");
			System.exit(100);
		}
		
		for (File f : list) {
			String fileName = f.getName();
			if (fileName.endsWith(acceptedExtension))
				this.archivedFiles.add(f.getAbsolutePath());
		}	
	}
	
	private String getFileNameFromAbsolutePath(String path)
	{
		int ind = path.lastIndexOf('/') + 1;
		return path.substring(ind);
	}
	
	private ArrayList<String> groupFileTypes(ArrayList<String> matchingNames) {
		ArrayList<String> types = new ArrayList<String>();

		for (String n : matchingNames) {
			String name = getFileNameFromAbsolutePath(n);
			if (!types.contains(name.substring(17))) {
				types.add(name.substring(17));
			}
		}
		return types;
	}
	
	private ArrayList<String> getFilesOfType(String type)
	{
		ArrayList<String> result = new ArrayList<String>();
		for (String name : this.archivedFiles) {
			String fileName = getFileNameFromAbsolutePath(name);
			if (type.compareTo(fileName.substring(17)) == 0) {
				result.add(name);
			}
		}
		return result;
	}

	private void getFileGroups(ArrayList<String> nonMatchingNames,
			ArrayList<String> matchingNames) {
		for (String path : this.archivedFiles) {
			String name = this.getFileNameFromAbsolutePath(path);
			if (name.matches("^\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}-(.*)")) {
				matchingNames.add(path);
			} else {
				nonMatchingNames.add(path);
			}
		}
	}
	
	private void process()
	{
		ArrayList<String> matching = new ArrayList<String>();
		ArrayList<String> nonMatching = new ArrayList<String>();
		this.getFileGroups(nonMatching, matching);
		ArrayList<String> types = this.groupFileTypes(matching);
		
		int totalFiles = this.archivedFiles.size();
		if (totalFiles == 0) {
			return;
		}
		
		for(String type : types) {
			ArrayList<String> filesToProcess = this.getFilesOfType(type);
			if (filesToProcess.size() < 4) {
				this.echo("Files of type '" + type + "' skipped.");
				continue;
			}
			Collections.sort(filesToProcess);
			int deletedFiles = 0;
			for (int i = 0; i < (filesToProcess.size() - 3); i++) {
				File f = new File(filesToProcess.get(i));
				f.delete();
				f = null;
				deletedFiles++;
			}
			this.echo("Deleted " + deletedFiles + " files in namespace " + type);
		}
	}

}
