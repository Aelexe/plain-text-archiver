package com.aelchemy.plaintextarchiver.file;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;

/**
 * {@link DirectoryCrawler} contains the functionality for crawling a directory and its subdirectorys for their files.
 * 
 * @author Aelexe
 *
 */
public class DirectoryCrawler {

	private DirectoryCrawler() {

	}

	/**
	 * Crawls a directory and its subdirectorys, retrieving a list of all files contained within.
	 * 
	 * @param rootDirectory Directory from which to begin crawling.
	 * @return A list of {@link FileContent} representing every file in the directory.
	 * @throws IOException
	 */
	public static List<FileContent> crawlDirectory(final File rootDirectory) throws IOException {
		return recursiveCrawlDirectory(rootDirectory, "", new ArrayList<FileContent>());
	}

	private static List<FileContent> recursiveCrawlDirectory(final File directory, final String path, final List<FileContent> files) throws IOException {
		for (File file : directory.listFiles()) {
			if (file.isDirectory()) {
				recursiveCrawlDirectory(file, path + file.getName() + "/", files);
			} else {
				files.add(new FileContent(path + file.getName(), FileUtils.readFileToString(file, "UTF-8")));
			}
		}

		return files;
	}

}
