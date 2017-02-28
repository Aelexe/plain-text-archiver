package com.aelchemy.plaintextarchiver.extract;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.io.FileUtils;

import com.aelchemy.plaintextarchiver.file.FileContent;

/**
 * {@link Extractor} contains functionality for extracting the contents of a plain text archive file.
 * 
 * @author Aelexe
 *
 */
public class Extractor {

	private static final Pattern NAME_REGEX = Pattern.compile("---- plain-text-archive: (.*)(?:\\r)?\\n");
	private static final Pattern FILE_REGEX = Pattern.compile("-- plain-text-file: (.*)(?:\\r)?\\n(?s:(.*?))(?:\\r)?\\n-- plain-text-file-end");

	private Extractor() {

	}

	/**
	 * Extracts the contents of an archive into the output directory.
	 * 
	 * @param archive The archive to extract.
	 * @param outputDirectory The directory to extract it into.
	 * @throws IOException
	 */
	public static void extractArchive(final File archive, final File outputDirectory) throws IOException {
		// Read the archive file.
		String archiveContent = FileUtils.readFileToString(archive, "UTF-8");

		// Create the output directory if it doesn't exist.
		if (!outputDirectory.exists()) {
			outputDirectory.mkdirs();
		}

		// Delete the directory if it already exists for cleanliness, and then recreate it.
		File archiveDirectory = new File(outputDirectory, extractArchiveName(archiveContent));
		if (archiveDirectory.exists()) {
			archiveDirectory.delete();
		}
		archiveDirectory.mkdirs();

		for (FileContent fileContent : extractArchiveFiles(archiveContent)) {
			File file = new File(archiveDirectory.getAbsolutePath() + "/" + fileContent.getFileName());
			if (!file.getParentFile().exists()) {
				file.getParentFile().mkdirs();
			}
			FileUtils.write(file, fileContent.getFileContents(), "UTF-8");
		}
	}

	/**
	 * Returns the name of an archive.
	 * 
	 * @param archive The archive to extract the name of.
	 * @return The name of the archive.
	 * @throws IOException
	 */
	public static String extractArchiveName(final File archive) throws IOException {
		return extractArchiveName(FileUtils.readFileToString(archive, "UTF-8"));
	}

	private static String extractArchiveName(final String archiveContent) {
		Matcher nameMatcher = NAME_REGEX.matcher(archiveContent);
		if (nameMatcher.find()) {
			return nameMatcher.group(1);
		} else {
			return null;
		}
	}

	/**
	 * Returns a list of {@link FileContent} array representing the files in an archive.
	 * 
	 * @param archive The archive to extract the contents of.
	 * @return A list of {@link FileContent}.
	 * @throws IOException
	 */
	public static List<FileContent> extractArchiveFiles(final File archive) throws IOException {
		return extractArchiveFiles(FileUtils.readFileToString(archive, "UTF-8"));
	}

	private static List<FileContent> extractArchiveFiles(final String archiveContent) {
		List<FileContent> files = new ArrayList<FileContent>();

		Matcher fileMatcher = FILE_REGEX.matcher(archiveContent);
		while (fileMatcher.find()) {
			files.add(new FileContent(fileMatcher.group(1), fileMatcher.group(2)));
		}

		return files;
	}

}
