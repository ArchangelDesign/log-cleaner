package com.archangel.design;
import java.io.*;

public class ArchiveCleaner {
	
	private String archiveLocation = "/home/bpol0108/Dropbox/";
	
	//private String databasePattern = "enigma_database_";
	
	public boolean initialize() {
		File f = new File(this.archiveLocation);
		String s[] = f.list();
		for (String file : s) {
			System.out.println(file);
		}
		return true;
	}

}
