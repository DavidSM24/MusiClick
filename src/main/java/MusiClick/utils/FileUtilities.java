package MusiClick.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class FileUtilities {
	
	public static void saveFile(String ficheroOriginal, String ficheroCopia) throws IOException {
		File f = new File(ficheroOriginal);
		InputStream is = new FileInputStream(f);

		OutputStream outstream = new FileOutputStream(new File(ficheroCopia));
		byte[] buffer = new byte[4096];
		int len;
		while ((len = is.read(buffer)) > 0) {
		    outstream.write(buffer, 0, len);
		}
		outstream.close();

	}
	
	public static void removeFile(String url) {
		File f = new File(url);
		f.delete();
	}
}
