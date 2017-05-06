package com.aelchemy.plaintextarchiver.ui;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SpringLayout;
import javax.swing.filechooser.FileFilter;

import org.apache.commons.io.FileUtils;

import com.aelchemy.plaintextarchiver.archive.Archiver;
import com.aelchemy.plaintextarchiver.extract.Extractor;
import com.aelchemy.plaintextarchiver.file.DirectoryCrawler;

public class UserInterface {

	private static final String WINDOW_TITLE = "Plain Text Archiver";
	private static final String DIRECTORY_TITLE = "Directory Details";
	private static final String ARCHIVE_TITLE = "Archive Details";
	private static final String FILE_SELECT_LABEl = "Select";
	private static final String ARCHIVE_LABEL = "Archive";
	private static final String EXTRACT_LABEL = "Extract";

	private static JFrame frame;
	private static JFileChooser fileChooser;
	private static JLabel titleLabel;
	private static JLabel pathLabel;
	private static JButton actionButton;
	private static ActionListener actionListener;

	public static void loadInterface() {
		frame = new JFrame(WINDOW_TITLE);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		fileChooser = new JFileChooser();
		fileChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
		fileChooser.setFileFilter(new PtaFileFilter());

		Container contentPane = frame.getContentPane();
		SpringLayout layout = new SpringLayout();
		contentPane.setLayout(layout);
		contentPane.setPreferredSize(new Dimension(240, 200));

		JPanel panel = new JPanel();
		SpringLayout panelLayout = new SpringLayout();
		panel.setLayout(panelLayout);
		panel.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1));
		layout.putConstraint(SpringLayout.NORTH, panel, 5, SpringLayout.NORTH, contentPane);
		layout.putConstraint(SpringLayout.EAST, panel, -10, SpringLayout.EAST, contentPane);
		layout.putConstraint(SpringLayout.SOUTH, panel, -30, SpringLayout.SOUTH, contentPane);
		layout.putConstraint(SpringLayout.WEST, panel, 10, SpringLayout.WEST, contentPane);
		contentPane.add(panel);

		titleLabel = new JLabel("Directory Details");
		titleLabel.setFont(new Font(titleLabel.getFont().getName(), Font.BOLD, 14));
		panelLayout.putConstraint(SpringLayout.HORIZONTAL_CENTER, titleLabel, 0, SpringLayout.HORIZONTAL_CENTER, panel);
		panelLayout.putConstraint(SpringLayout.NORTH, titleLabel, 5, SpringLayout.NORTH, panel);
		panel.add(titleLabel);

		JLabel filePathLabel = new JLabel("Path");
		filePathLabel.setFont(new Font(filePathLabel.getFont().getName(), Font.BOLD, 13));
		panelLayout.putConstraint(SpringLayout.NORTH, filePathLabel, 2, SpringLayout.SOUTH, titleLabel);
		panelLayout.putConstraint(SpringLayout.WEST, filePathLabel, 10, SpringLayout.WEST, panel);
		panel.add(filePathLabel);

		pathLabel = new JLabel();
		pathLabel.setFont(new Font(pathLabel.getFont().getName(), Font.PLAIN, 11));
		panelLayout.putConstraint(SpringLayout.NORTH, pathLabel, 0, SpringLayout.SOUTH, filePathLabel);
		panelLayout.putConstraint(SpringLayout.WEST, pathLabel, 10, SpringLayout.WEST, panel);
		panelLayout.putConstraint(SpringLayout.EAST, pathLabel, 10, SpringLayout.EAST, panel);
		panel.add(pathLabel);

		actionButton = new JButton();
		actionButton.setFocusable(false);
		layout.putConstraint(SpringLayout.NORTH, actionButton, 5, SpringLayout.SOUTH, panel);
		layout.putConstraint(SpringLayout.HORIZONTAL_CENTER, actionButton, 0, SpringLayout.HORIZONTAL_CENTER, contentPane);
		layout.putConstraint(SpringLayout.SOUTH, actionButton, -5, SpringLayout.SOUTH, contentPane);
		contentPane.add(actionButton);

		panel.setVisible(true);
		frame.pack();

		promptForFile();
	}

	/**
	 * Creates a {@link JFileChooser} dialog prompting the user to select a directory or .pta file.
	 * <p>
	 * If a directory or .pta file is selected its details are displayed in the UI, otherwise the application is
	 * terminated.
	 */
	private static void promptForFile() {
		// Hide the UI.
		frame.setVisible(false);

		// Prompt the user for a directory/file.
		int result = fileChooser.showDialog(frame, FILE_SELECT_LABEl);
		if (result == JFileChooser.APPROVE_OPTION) {
			// If a directory/file is chosen display its details.
			File selectedFile = fileChooser.getSelectedFile();
			if (selectedFile.isDirectory()) {
				displayDirectory(selectedFile);
			} else {
				displayArchive(selectedFile);
			}
		} else {
			// Otherwise terminate the application.
			System.exit(0);
		}
	}

	private static void displayDirectory(final File directory) {
		// Set the frame title.
		titleLabel.setText(DIRECTORY_TITLE);

		// Set the directory path.
		pathLabel.setText("<html>" + directory.getAbsolutePath() + "</html>");

		// Set the action button text.
		actionButton.setText(ARCHIVE_LABEL);

		// Remove any existing action listener.
		if (actionListener != null) {
			actionButton.removeActionListener(actionListener);
		}

		// Add the archive action listener.
		actionButton.addActionListener(actionListener = new ArchiveActionListener(directory));

		// Display the UI.
		frame.setVisible(true);
	}

	private static void displayArchive(final File archive) {
		// Set the frame title.
		titleLabel.setText(ARCHIVE_TITLE);

		// Set the directory path.
		pathLabel.setText("<html>" + archive.getAbsolutePath() + "</html>");

		// Set the action button text.
		actionButton.setText(EXTRACT_LABEL);

		// Remove any existing action listener.
		if (actionListener != null) {
			actionButton.removeActionListener(actionListener);
		}

		// Add the archive action listener.
		actionButton.addActionListener(actionListener = new ExtractActionListener(archive));

		// Display the UI.
		frame.setVisible(true);
	}

	private static class ArchiveActionListener implements ActionListener {

		private final File directory;

		public ArchiveActionListener(final File directory) {
			this.directory = directory;
		}

		@Override
		public void actionPerformed(final ActionEvent event) {
			try {
				FileUtils.writeStringToFile(new File(directory.getParent() + "/" + directory.getName() + ".pta"),
						Archiver.createArchive(directory.getName(), DirectoryCrawler.crawlDirectory(directory)), "UTF-8");
				System.exit(0);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}

	private static class ExtractActionListener implements ActionListener {

		private final File archive;

		public ExtractActionListener(final File archive) {
			this.archive = archive;
		}

		@Override
		public void actionPerformed(final ActionEvent event) {
			try {
				Extractor.extractArchive(archive, archive.getParentFile());
				System.exit(0);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}

	/**
	 * {@link FileFilter} implementation that allows only directories or .pta files.
	 */
	private static class PtaFileFilter extends FileFilter {

		@Override
		public boolean accept(final File file) {
			if (file.isDirectory()) {
				return true;
			} else {
				return file.getName().endsWith(".pta");
			}
		}

		@Override
		public String getDescription() {
			return ".pta, directory";
		}

	}

}
