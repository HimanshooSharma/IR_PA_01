package Tools;

import java.util.HashSet;

import java.io.File;
import java.io.IOException;;

public class RecusiveFiles {

	public static HashSet<File> getFilesfromPath(File dir) throws IOException{
		HashSet<File> files = new HashSet<File>();
		
		File[] listFiles = dir.listFiles();
		
		for (File file :  listFiles) {
			if(file.isDirectory())
				files.addAll(getFilesfromPath(file));
			else
				files.add(file);
				
		}
		
		return files;
	}
}
