
public class SongNode {
	// Instance Variables
	private String songName;
	private String artist;
	private SongNode next;
	private String filename;

	// Constructors 
	public SongNode(String songName, String artist, SongNode next ) {
		this.songName = songName;
		this.artist = artist;
		this.next = next;
	}

	public SongNode(String songName, String artist, String filename) {
		this.songName = songName;
		this.artist = artist;
		this.filename = filename;
	}

	// Getters and Setters
	public String getSongName() {
		return songName;
	}

	public void setSongName(String songName) {
		this.songName = songName;
	}

	public String getArtist() {
		return artist;
	}

	public void setArtist(String artist) {
		this.artist = artist;
	}

	public SongNode next() {
		return next;
	}

	public void setNext(SongNode other) {
		next = other;
	}
	
	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	// To String Method
	public String toString() {
		return "SongNode [songName=" + songName + ", artist=" + artist + "]";
	}

}
