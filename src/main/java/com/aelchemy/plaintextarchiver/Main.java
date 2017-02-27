package com.aelchemy.plaintextarchiver;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;

import com.aelchemy.plaintextarchiver.archive.Archiver;
import com.aelchemy.plaintextarchiver.file.DirectoryCrawler;

/**
 * Temporary class containing a main method so I can execute this without creating a UI.
 * 
 * @author Aelexe
 *
 */
public class Main {

	public static void main(final String[] args) throws IOException {
		if (args.length < 2) {
			System.err.println("Please provide a root directory for archiving and an output file to save it to.");
			return;
		}

		File rootDirectory = new File(args[0]);
		File outputDirectory = new File(args[1]);

		FileUtils.writeStringToFile(outputDirectory, Archiver.createArchive(rootDirectory.getName() + ".pta", DirectoryCrawler.crawlDirectory(rootDirectory)), "UTF-8");
	}

}
