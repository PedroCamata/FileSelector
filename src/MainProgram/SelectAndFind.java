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

	public static final String FOLDER_NAME = "SELECIONADAS";

	public static String FindFiles(File folder, String files) {

		masterFolder = folder;
		foundedFiles = new ArrayList<File>();
		
		if (!folder.exists()) {
			return "ERRO: Pasta não existe"; // Folder doesn't exists
		}

		if (files.isEmpty()) {
			return "ERRO: Campo de Texto não foi preenchido"; // Text field wasn't filled
		}

		filesValidate(files);

		if (!filenames.isEmpty()) {
			return "ERRO: Algum(s) arquivo na lista não foi encontrado"; // Some file(s) wasn't found
		}

		if (copyFileProcess()) {
			return "SUCESSO: Arquivos encontrados e copiados para pasta!"; // Success
		} else {
			return "ERRO: Algo deu errado"; // Something is wrong
		}
	}

	private static void filesValidate(String filesString) {

		String[] fileArray = filesString.split("\n");
		filenames = new ArrayList<String>();
		// Transform to List
		for (int i = 0; i < fileArray.length; i++) {
			filenames.add(fileArray[i]);
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
				findFiles(new File(folder.getAbsolutePath() + "/" + directories[i]));
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

		List<File> fileArray = new ArrayList<File>();
		for (int i = 0; i < fileStringArray.length; i++) {
			fileArray.add(new File(folder.getAbsolutePath() + "/" + fileStringArray[i]));
		}

		for (int i = 0; i < fileArray.size(); i++) {
			for (int j = 0; j < filenames.size(); j++) {
				if (fileArray.get(i).isFile() && fileArray.get(i).getName().toUpperCase().equals(filenames.get(j).toUpperCase())) {
					// File has been found
					foundedFiles.add(fileArray.get(i));
					filenames.remove(j);
					break;
				}
			}
		}

		return true;
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

		File folder = new File(masterFolder.getPath() + "/" + FOLDER_NAME + "/");
		if (!folder.exists()) {
			folder.mkdirs();
		}

		File filepath = new File(masterFolder.getPath() + "/" + FOLDER_NAME + "/" + source.getName());
		Files.copy(source.toPath(), filepath.toPath());
	}

}
