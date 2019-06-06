import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;

/**
 * The GUI Panel for the MPlayer project
 */

public class MPlayerPanel extends JPanel {

	private static final long serialVersionUID = 1L;

	// Instance Variables
	private SongDatabase songDatabase; // the class that contains songs
	private SongDatabase songDatabase2;
	private int count = 0;

	// panels
	JPanel topPanel, bottomPanel;
	JScrollPane centerPanel;
	Thread currThread = null;

	// buttons and search box
	JButton playButton, stopButton, exitButton, loadMp3Button;
	JTextField searchBox;
	JButton searchButton;

	int selectedSong = -1;
	JTable table = null;
	JTable table1 = null;
	JTable table2 = null;
	private final JFileChooser fc = new JFileChooser();

	MPlayerPanel(SongDatabase songCol) {
		this.songDatabase = songCol;
		this.setLayout(new BorderLayout());
		// Create panels: top, center, bottom

		// Create the top panel that has the Load mp3 button, the textfield and
		// the Search button to search for a song
		topPanel = new JPanel();
		topPanel.setLayout(new GridLayout(1, 4));

		// create buttons
		loadMp3Button = new JButton("Load mp3");
		searchBox = new JTextField(5);
		searchButton = new JButton("Search");
		exitButton = new JButton("Exit");
		playButton = new JButton("Play");
		stopButton = new JButton("Stop");

		// add a listener for each button
		loadMp3Button.addActionListener(new ButtonListener());
		exitButton.addActionListener(new ButtonListener());
		playButton.addActionListener(new ButtonListener());
		stopButton.addActionListener(new ButtonListener());
		searchButton.addActionListener(new ButtonListener());

		// add buttons and the textfield to the top panel
		topPanel.add(loadMp3Button);
		topPanel.add(searchBox);
		topPanel.add(searchButton);

		this.add(topPanel, BorderLayout.NORTH); // add the top panel to this
												// panel (MPlayer panel)

		// create the bottom panel and add three buttons to it
		bottomPanel = new JPanel();
		bottomPanel.setLayout(new GridLayout(1, 3));
		bottomPanel.add(playButton);
		bottomPanel.add(stopButton);
		bottomPanel.add(exitButton);

		this.add(bottomPanel, BorderLayout.SOUTH);

		// the panel in the center that shows mp3 songs
		centerPanel = new JScrollPane();
		this.add(centerPanel, BorderLayout.CENTER);

		// file chooser (opens another window that allows the user to select a
		// folder with files)
		// Set the default directory to the current directory
		fc.setCurrentDirectory(new File("."));

	}

	public class PlayerThread extends Thread {
		Player pl;

		PlayerThread(String filename) {
			FileInputStream file;
			try {
				// filename here contains mp3 you want to play
				file = new FileInputStream(filename);
				pl = new Player(file);
			} catch (FileNotFoundException e) {
				e.getMessage();
			} catch (JavaLayerException e) {
				e.getMessage();
			}
		}

		public void run() {
			try {
				pl.play();
			} catch (Exception e) {
				e.getMessage();
			}
		}
	}

	/** An inner listener class for buttons **/
	class ButtonListener implements ActionListener {

		public void actionPerformed(ActionEvent e) {

			if (e.getSource() == loadMp3Button) {
				System.out.println("Load mp3 button");
				songDatabase2 = new SongDatabase();
				songDatabase = new SongDatabase();
				// read all the mp3 files from mp3 directory
				fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				fc.setDialogTitle("Select a directory with mp3 songs");

				int returnVal = fc.showOpenDialog(MPlayerPanel.this);
				if (returnVal == JFileChooser.APPROVE_OPTION) {
					File dir = fc.getSelectedFile();
					// dir is the directory with the mp3 files that the user
					// selected

					songDatabase.addToList(dir.toPath());
					String columnNames[] = { "Song Name", "Artist" };
					String dataValues[][] = new String[songDatabase.listSize()][2];
					SongNode curr = songDatabase.getHead();
					while (curr != null) {
						for (int n = 0; n < songDatabase.listSize(); n++) {
							dataValues[n][0] = curr.getSongName();
							dataValues[n][1] = curr.getArtist();
							curr = curr.next();
						}
					}
					table = new JTable(dataValues, columnNames);
					centerPanel.getViewport().add(table);
					updateUI();

				}
			} else if (e.getSource() == playButton) {

				if (table == null) {
					return;
				}
				selectedSong = table.getSelectedRow();
				// Find the selected song in the SongDatabase and play it

				// This accounts for when the search text field is empty
				if (count == 2) {
					selectedSong = table2.getSelectedRow();
					currThread = new PlayerThread(songDatabase.getFn(selectedSong));
					currThread.start();
				}
				// Plays the song when the user searched for something
				if (songDatabase2.getHead() != null && !searchBox.getText().equals("")) {
					selectedSong = table1.getSelectedRow();
					currThread = new PlayerThread(songDatabase2.getFn(selectedSong));
					currThread.start();
				}
				// Accounts for a song playing and stops the on playing and
				// another one starting
				if (currThread != null && songDatabase2.getHead() == null) {
					currThread.stop();
					selectedSong = table.getSelectedRow();
					currThread = new PlayerThread(songDatabase.getFn(selectedSong));
				}

				if (songDatabase2.getHead() == null) {
					currThread = new PlayerThread(songDatabase.getFn(selectedSong));
					currThread.start();
				}

			} else if (e.getSource() == stopButton) {
				// FILL IN CODE: stop playing the song if its currently playing
				if (currThread != null) {
					currThread.stop();
				}
			} else if (e.getSource() == exitButton) {
				System.exit(0);
			}

			else if (e.getSource() == searchButton) {
				// FILL IN CODE - When the user presses the Search button,
				// your program should show only the songs that start with the
				// search word
				count++;
				String criteria = searchBox.getText();
				String columnNames1[] = { "Song Name", "Artist" };
				SongNode curr = songDatabase.getHead();
				SongNode curr3 = songDatabase.getHead();
				// Updates the gui when the box is empty and the user hits
				// search else find the song
				if (searchBox.getText().equals("")) {
					while (curr != null) {
						songDatabase2.add(curr);
						curr = curr.next();
					}

					String dataValues1[][] = new String[songDatabase.listSize()][2];
					while (curr3 != null) {
						for (int n = 0; n < songDatabase.listSize(); n++) {
							dataValues1[n][0] = curr3.getSongName();
							dataValues1[n][1] = curr3.getArtist();
							curr3 = curr3.next();
						}
					}

					centerPanel.getViewport().removeAll();
					table2 = new JTable(dataValues1, columnNames1);
					centerPanel.getViewport().add(table2);
				} else {
					while (curr != null) {
						if (curr.getSongName().startsWith(criteria)) {
							songDatabase2.add(curr);
						}
						curr = curr.next();
					}
					String dataValues1[][] = new String[songDatabase2.listSize()][2];
					SongNode curr2 = songDatabase2.getHead();
					while (curr2 != null) {
						for (int n = 0; n < songDatabase2.listSize(); n++) {
							dataValues1[n][0] = curr2.getSongName();
							dataValues1[n][1] = curr2.getArtist();
							curr2 = curr2.next();
						}
					}
					centerPanel.getViewport().removeAll();
					table1 = new JTable(dataValues1, columnNames1);
					centerPanel.getViewport().add(table1);
				}
			}
		} // actionPerformed
	} // ButtonListener

}
