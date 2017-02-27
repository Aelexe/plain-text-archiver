package com.aelchemy.plaintextarchiver.file;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

import org.junit.Test;

import com.aelchemy.plaintextarchiver.TestUtil;

public class DirectoryCrawlerTest {

	@Test
	public void testCrawlDirectory_DirectoryOne() throws IOException, URISyntaxException {
		List<FileContent> files = DirectoryCrawler.crawlDirectory(TestUtil.getResourceFile("/directory_one"));
		assertEquals(2, files.size());
		assertListContains("one.txt", TestUtil.readResourceFile("/directory_one/one.txt"), files);
		assertListContains("two.txt", TestUtil.readResourceFile("/directory_one/two.txt"), files);
	}

	@Test
	public void testCrawlDirectory_DirectoryTwo() throws IOException, URISyntaxException {
		List<FileContent> files = DirectoryCrawler.crawlDirectory(TestUtil.getResourceFile("/directory_two"));
		assertEquals(2, files.size());
		assertListContains("one.txt", TestUtil.readResourceFile("/directory_two/one.txt"), files);
		assertListContains("sub_directory/two.txt", TestUtil.readResourceFile("/directory_two/sub_directory/two.txt"), files);
	}

	public void assertListContains(final String fileName, final String fileContents, final List<FileContent> files) {
		for (FileContent file : files) {
			if (file.getFileName().equals(fileName) && file.getFileContents().equals(fileContents)) {
				return;
			}
		}
		fail("File list did not contain file: " + fileName + " with matching file content.");
	}

}
