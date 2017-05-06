package com.aelchemy.plaintextarchiver;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;

import com.aelchemy.plaintextarchiver.archive.Archiver;
import com.aelchemy.plaintextarchiver.extract.Extractor;
import com.aelchemy.plaintextarchiver.file.DirectoryCrawler;
import com.aelchemy.plaintextarchiver.ui.UserInterface;

/**
 * Temporary class containing a main method so I can execute this without creating a UI.
 * 
 * @author Aelexe
 * 
 */
public class Main {

	private static final String MODE_ARCHIVE = "archive";
	private static final String MODE_EXTRACT = "extract";

	/**
	 * Main method for command line execution. Supports both archiving and extraction.<br>
	 * If no flags are provided the user interface is loaded.
	 * <p>
	 * <b>Archiving</b>: archive archiveDirectory outputDirectory <br>
	 * <b>Extracting</b>: extract archiveFile outputDirectory
	 * 
	 * @param args Command line arguments to control the flow of the application.
	 * @throws IOException
	 */
	public static void main(final String[] args) throws IOException {
		if (args.length == 0) {
			UserInterface.loadInterface();
			return;
		}
		if (args.length < 3) {
			System.err.println("Please provide a mode (" + MODE_ARCHIVE + " or " + MODE_EXTRACT
					+ "), an archive file/directory and an output directory.");
			return;
		}

		String mode = args[0];

		if (mode.equals(MODE_ARCHIVE)) {
			File rootDirectory = new File(args[1]);
			File outputDirectory = new File(args[2]);

			FileUtils.writeStringToFile(outputDirectory,
					Archiver.createArchive(rootDirectory.getName() + ".pta", DirectoryCrawler.crawlDirectory(rootDirectory)), "UTF-8");
		} else if (mode.equals(MODE_EXTRACT)) {
			File archiveFile = new File(args[1]);
			File outputDirectory = new File(args[2]);

			Extractor.extractArchive(archiveFile, outputDirectory);
		}
	}

}
