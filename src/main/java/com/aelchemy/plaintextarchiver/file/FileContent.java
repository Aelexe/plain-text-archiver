package com.aelchemy.plaintextarchiver.file;

/**
 * {@link FileContent} contains a file's name and contents.
 * 
 * @author Aelexe
 *
 */
public class FileContent {
	private String fileName;
	private String fileContents;

	public FileContent(final String fileName, final String fileContents) {
		this.fileName = fileName;
		this.fileContents = fileContents;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(final String fileName) {
		this.fileName = fileName;
	}

	public String getFileContents() {
		return fileContents;
	}

	public void setFileContents(final String fileContents) {
		this.fileContents = fileContents;
	}
}
