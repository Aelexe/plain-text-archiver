package com.aelchemy.plaintextarchiver.extract;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

import org.junit.Test;

import com.aelchemy.plaintextarchiver.TestUtil;
import com.aelchemy.plaintextarchiver.file.FileContent;

public class ExtractorTest {

	@Test
	public void testExtractArchiveName_DirectoryOne() throws URISyntaxException, IOException {
		assertEquals("DirectoryOne", Extractor.extractArchiveName(TestUtil.getResourceFile("/archive/DirectoryOne.pta")));
	}

	@Test
	public void testExtractArchiveName_DirectoryTwo() throws URISyntaxException, IOException {
		assertEquals("DirectoryTwo", Extractor.extractArchiveName(TestUtil.getResourceFile("/archive/DirectoryTwo.pta")));
	}

	@Test
	public void testExtractArchiveFiles_DirectoryOne() throws IOException, URISyntaxException {
		List<FileContent> files = Extractor.extractArchiveFiles(TestUtil.getResourceFile("/archive/DirectoryOne.pta"));

		assertEquals(2, files.size());
		TestUtil.assertListContains("one.txt", TestUtil.readResourceFile("/directory_one/one.txt"), files);
		TestUtil.assertListContains("two.txt", TestUtil.readResourceFile("/directory_one/two.txt"), files);
	}

	@Test
	public void testExtractArchiveFiles_DirectoryTwo() throws IOException, URISyntaxException {
		List<FileContent> files = Extractor.extractArchiveFiles(TestUtil.getResourceFile("/archive/DirectoryTwo.pta"));

		assertEquals(2, files.size());
		TestUtil.assertListContains("one.txt", TestUtil.readResourceFile("/directory_two/one.txt"), files);
		TestUtil.assertListContains("sub_directory/two.txt", TestUtil.readResourceFile("/directory_two/sub_directory/two.txt"), files);
	}

}
