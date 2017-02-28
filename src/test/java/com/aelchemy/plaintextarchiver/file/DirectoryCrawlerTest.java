package com.aelchemy.plaintextarchiver.file;

import static org.junit.Assert.assertEquals;

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
		TestUtil.assertListContains("one.txt", TestUtil.readResourceFile("/directory_one/one.txt"), files);
		TestUtil.assertListContains("two.txt", TestUtil.readResourceFile("/directory_one/two.txt"), files);
	}

	@Test
	public void testCrawlDirectory_DirectoryTwo() throws IOException, URISyntaxException {
		List<FileContent> files = DirectoryCrawler.crawlDirectory(TestUtil.getResourceFile("/directory_two"));
		assertEquals(2, files.size());
		TestUtil.assertListContains("one.txt", TestUtil.readResourceFile("/directory_two/one.txt"), files);
		TestUtil.assertListContains("sub_directory/two.txt", TestUtil.readResourceFile("/directory_two/sub_directory/two.txt"), files);
	}

}
