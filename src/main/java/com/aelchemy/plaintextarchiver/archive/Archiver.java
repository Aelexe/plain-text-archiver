package com.aelchemy.plaintextarchiver.archive;

import java.util.List;

import com.aelchemy.plaintextarchiver.file.FileContent;

/**
 * {@link Archiver} contains the functionality for turning a list of {@link FileContent}s into a plain text archive
 * file.
 * 
 * @author Aelexe
 *
 */
public class Archiver {

	private Archiver() {

	}

	/**
	 * Creates a plain text archive string of the provided files.
	 * 
	 * @param archiveName Name of the archive.
	 * @param files Files to put in the archive.
	 * @return The plain text archive.
	 */
	public static String createArchive(final String archiveName, final List<FileContent> files) {
		StringBuilder archive = new StringBuilder();
		appendArchiveName(archiveName, archive);
		for (FileContent file : files) {
			appendFile(file, archive);
		}

		return archive.toString();
	}

	private static void appendArchiveName(final String archiveName, final StringBuilder archive) {
		archive.append("---- plain-text-archive: " + archiveName + "\n");
	}

	private static void appendFile(final FileContent file, final StringBuilder archive) {
		archive.append("-- plain-text-file: " + file.getFileName() + "\n");
		archive.append(file.getFileContents());
		archive.append("\n");
	}

}
