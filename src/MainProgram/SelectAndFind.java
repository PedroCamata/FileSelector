package MainProgram;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

public class SelectAndFind {

	static File masterFolder;

	static List<String> filenames;

	static List<File> foundedFiles;

	public static final String FOLDER_FILE_SELECTED = "SELECTED";

	public static String FindFiles(File folder, String files) {

		masterFolder = folder;
		foundedFiles = new ArrayList<File>();

		if (!folder.exists()) {
			return "ERROR: Folder doesn't exists";
		}

//		if (new File(folder.getAbsolutePath() + File.separator + FOLDER_FILE_SELECTED).exists())
//		{
//			return "ERROR: Folder " + FOLDER_FILE_SELECTED + " already exists in this folder";
//		}

		if (files.isEmpty()) {
			return "ERROR: Text field is empty";
		}

		filesValidate(files);

		if (!filenames.isEmpty()) {

			String filesNotFound = "";
			for (int i = 0; i < filenames.size(); i++) {
				filesNotFound += filenames.get(i) + "\n";
			}

			return "ERROR: File(s) not found\n\n" + filesNotFound;
		}

		if (!copyFileProcess()) {
			return "ERROR: Something is wrong";
		}

		return "SUCCESS: Files were found and copied into the folder \"" + FOLDER_FILE_SELECTED + "\"";
	}

	private static void filesValidate(String filesString) {

		// Convert "," to "\n"
		filesString = filesString.replace(", ", ",").replace(" ,", ",").replace(",", "\n");

		String[] fileArray = filesString.split("\n");
		filenames = new ArrayList<String>();
		// Transform to List
		for (int i = 0; i < fileArray.length; i++) {
			if (fileArray[i].length() > 0) {
				filenames.add(fileArray[i]);
			}
		}

		findFiles(masterFolder);

	}

	private static boolean findFiles(File folder) {

		// Get all directories
		String[] directories = folder.list(new FilenameFilter() {
			@Override
			public boolean accept(File current, String name) {
				return new File(current, name).isDirectory();
			}
		});

		if (directories != null) {
			// Recursive
			for (int i = 0; i < directories.length; i++) {
				if (filenames.isEmpty()) {
					return true;
				}
				findFiles(new File(folder.getAbsolutePath() + File.separator + directories[i]));
			}
		}

		// Get all files
		String[] fileStringArray = folder.list(new FilenameFilter() {
			@Override
			public boolean accept(File current, String name) {
				return new File(current, name).isFile();
			}
		});

		if (fileStringArray == null) {
			return true;
		}

		for (int i = 0; i < fileStringArray.length; i++) {
			File tempFile = new File(folder.getAbsolutePath() + File.separator + fileStringArray[i]);
			if (tempFile.isFile()) {
				String tempFileName = removeFileNameExtension(tempFile.getName());
				for (int j = 0; j < filenames.size(); j++) {
					if ((filenames.get(j).contains(".") && tempFile.getName().toUpperCase().equals(filenames.get(j).toUpperCase()))
					|| (!filenames.get(j).contains(".") && tempFileName.equals(removeFileNameExtension(filenames.get(j))))) {
						// File has been found
						foundedFiles.add(tempFile);
						filenames.remove(j);
						break;
					}
				}
			}
		}

		return true;
	}

	private static String removeFileNameExtension(String fileName) {
		return fileName.replaceFirst("[.][^.]+$", "").toUpperCase();
	}

	private static boolean copyFileProcess() {
		for (int i = 0; i < foundedFiles.size(); i++) {
			try {
				copyFile(foundedFiles.get(i));
			} catch (IOException e) {
				return false;
			}
		}
		return true;
	}

	private static void copyFile(File source) throws IOException {

		File folder = new File(masterFolder.getPath() + File.separator + FOLDER_FILE_SELECTED + File.separator);
		if (!folder.exists()) {
			folder.mkdirs();
		}

		File filepath = new File(
				masterFolder.getPath() + File.separator + FOLDER_FILE_SELECTED + File.separator + source.getName());
		Files.copy(source.toPath(), filepath.toPath());
	}

}
