
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import org.jaudiotagger.audio.AudioFile;
import org.jaudiotagger.audio.AudioFileIO;
import org.jaudiotagger.audio.exceptions.CannotReadException;
import org.jaudiotagger.audio.exceptions.InvalidAudioFrameException;
import org.jaudiotagger.audio.exceptions.ReadOnlyFileException;
import org.jaudiotagger.tag.FieldKey;
import org.jaudiotagger.tag.Tag;
import org.jaudiotagger.tag.TagException;

public class SongDatabase {
	// Instance variables
	private SongList songs = new SongList();
	private AudioFile f;
	private SongList tempSongs = new SongList();

	// Constructor
	public SongDatabase() {
		SongList songs = new SongList();
	}
	
	// Gets the head of the linked list
	public SongNode getHead() {
		return songs.getHead();
	}
	// Recursively iterates over the directory and sub-directories, and adds all
	// mp3 files to the linked list
	public void addToList(Path path) {
		if (!Files.isDirectory(path)) {
			System.out.println("Path is not a directory. ");
			return;
		}
		try {
			DirectoryStream<Path> mp3folder = Files.newDirectoryStream(path);
			for (Path file : mp3folder) {
				if (Files.isDirectory(file)) {
					addToList(file);
				}
				String filename = file.toString();
				if (filename.contains(".mp3")) {
					f = AudioFileIO.read(file.toFile());
					Tag tag = f.getTag();
					String artist = tag.getFirst(FieldKey.ARTIST);
					String title = tag.getFirst(FieldKey.TITLE);
					tempSongs.insertAtFront(title, artist, filename);
				}
			}
			mp3folder.close();
		} catch (CannotReadException | IOException | TagException | ReadOnlyFileException
				| InvalidAudioFrameException e) {
			e.printStackTrace();
		} finally {
			tempSongs.sortList(songs);
		}
	}
	
	// Returns the size of the linked list
	public int listSize(){
		SongNode curr = songs.getHead();
		int sum = 0;
		while (curr != null) {
			sum ++;
			curr = curr.next();
		}
		return sum;
	}
	
	// Gets the filename of the node at the passed index
	public String getFn(int index) {
		SongNode curr = songs.getHead();
		SongNode curr2 = songs.getHead();
		int counter = 0;
		int counter2 = 0;
		if (index < 0) {
			return "Invalid index. Please input a number greater than 0";
		}
		while (curr2 != null) {
			counter2++;
			curr2 = curr2.next();
		}
		if (counter2 >= index) {
			while (counter < index) {
				curr = curr.next();
				counter++;
			}
			
			return curr.getFilename();
		} else {
			System.out.println("That index is out of range!");
		}
		return curr.getFilename();
	}
	
	public void add(SongNode node){
		songs.append(node.getSongName(), node.getArtist(), node.getFilename());
	}
}
