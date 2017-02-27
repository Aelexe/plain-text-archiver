package com.aelchemy.plaintextarchiver.archive;

import java.io.IOException;
import java.net.URISyntaxException;

import org.junit.Test;

import com.aelchemy.plaintextarchiver.TestUtil;
import com.aelchemy.plaintextarchiver.file.DirectoryCrawler;

public class ArchiverTest {

	@Test
	public void testCreateArchive_DirectoryOne() throws IOException, URISyntaxException {
		TestUtil.assertStringEquals(TestUtil.readResourceFile("/archive/DirectoryOne.pta"),
				Archiver.createArchive("DirectoryOne", DirectoryCrawler.crawlDirectory(TestUtil.getResourceFile("/directory_one"))));
	}

	@Test
	public void testCreateArchive_DirectoryTwo() throws IOException, URISyntaxException {
		TestUtil.assertStringEquals(TestUtil.readResourceFile("/archive/DirectoryTwo.pta"),
				Archiver.createArchive("DirectoryTwo", DirectoryCrawler.crawlDirectory(TestUtil.getResourceFile("/directory_two"))));
	}
}
