package Strategy;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import com.archangel.design.StrategyInterface;

public class LogArchiverStrategy extends ParamsReader implements
		StrategyInterface {

	private File inputDir;
	private File outputDir;
	private ArrayList<String> logFiles = new ArrayList<String>();

	public LogArchiverStrategy(ArrayList<String> params) {
		this.params = params;
		this.paramCount = params.size();
	}

	@Override
	public void run() {
		this.echo("Log archiver strategy started.");
		this.loadConfig();
		this.initialize();
		this.loadFileList();
		this.archiveFiles();
		this.deleteFiles();
	}

	private void initialize() {
		this.inputDir = new File(this.getConfigValue("InputDir"));
		if (!this.inputDir.isDirectory()) {
			this.error("InputDir is not a directory.");
			System.exit(100);
		}

		this.outputDir = new File(this.getConfigValue("OutputDir"));
		if (!this.inputDir.isDirectory()) {
			this.error("OutputDir is not a directory.");
			System.exit(100);
		}
	}
	
	private void loadFileList()
	{
		String ext = this.getConfigValue("LogFileExtension");
		long maxSize = Long.parseLong(this.getConfigValue("MaxLogSize"));
		
		File[] list = this.inputDir.listFiles(new LogFilter(ext));
		for (File file : list) {
			if (file.length() > maxSize) {
				this.logFiles.add(file.getAbsolutePath());
			}
		}
	}
	
	private void archiveFiles()
	{
		if (this.logFiles.size() == 0) {
			return;
		}
		this.echo("Files to zip: " + String.valueOf(this.logFiles.size()));
		for (int i=0; i < this.logFiles.size(); i++) {
			try {
				this.zipFile(
						this.logFiles.get(i), 
						this.getFilenameForZip(this.logFiles.get(i)));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	private void deleteFiles()
	{
		if (this.logFiles.size() == 0) {
			return;
		}
		
		for (int i=0; i < this.logFiles.size(); i++) {
			
		}
	}
	
	private String getFilenameForZip(String name)
	{
		int index = name.lastIndexOf('/') + 1;
		String newName =  this.getDate() + "-" + name.substring(index);
		return this.getConfigValue("OutputDir") + newName + ".zip";
	}
	
	private void zipFile(String source, String destination) throws IOException
	{
		this.echo("Zipping file " + destination);
		FileOutputStream outputStream = new FileOutputStream(destination);
		ZipOutputStream zipOutput = new ZipOutputStream(outputStream);
		
		String orgFilename = source.substring(source.lastIndexOf('/')+1);
		ZipEntry entry = new ZipEntry(orgFilename);
		zipOutput.putNextEntry(entry);
		
		FileInputStream input = new FileInputStream(source);
		int len = 0;
		byte[] buffer = new byte[1024];
		while ((len = input.read(buffer)) > 0) {
			zipOutput.write(buffer, 0, len);
		}
		input.close();
		zipOutput.closeEntry();
		zipOutput.close();
	}
	
	private String getDate()
	{
		String y = String.valueOf(Calendar.getInstance().get(Calendar.YEAR));
		String m = String.valueOf(Calendar.getInstance().get(Calendar.MONTH)+1);
		String d = String.valueOf(Calendar.getInstance().get(Calendar.DAY_OF_MONTH));
		int h = Calendar.getInstance().get(Calendar.HOUR);
		String hour = "";
		String i = String.valueOf(Calendar.getInstance().get(Calendar.MINUTE));
		int a = Calendar.getInstance().get(Calendar.AM_PM);
		if (a == 1) h += 12;
		hour = String.valueOf(h);
		if (m.length() < 2) m = "0" + m;
		if (d.length() < 2) d = "0" + d;
		if (hour.length() < 2) hour = "0" + hour;
		if (i.length() < 2) i = "0" + i;
		return y + "-" + m + "-" + d + " " + hour + ":" + i;
	}

}

class LogFilter implements FilenameFilter
{
	private String ext;
	
	public LogFilter(String ext)
	{
		this.ext = ext;
	}

	@Override
	public boolean accept(File dir, String name) {
		return name.endsWith("." + this.ext);
	}
	
}
