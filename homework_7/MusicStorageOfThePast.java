package homework_7;

import java.util.Objects;

public class MusicStorageOfThePast implements Comparable<MusicStorageOfThePast> {
    private final int year;
    private final String artist;
    private final String title;
    private final float length;
    private final int tracks;

    public MusicStorageOfThePast(
            String artistName,
            String songTitle,
            int releaseYear,
            float songLength,
            int t
    ) {
        artist = artistName;
        title = songTitle;
        year = releaseYear;
        length = songLength;
        tracks = t;
    }

    public String getTitle() {
        return title;
    }

    public String getArtist() {
        return artist;
    }

    public int getYear() {
        return year;
    }

    public float getLength() {
        return length;
    }

    public int getTracks() {
        return tracks;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj == null)
            return false;
        return Objects.equals(toString(), obj.toString());
    }

    @Override
    public String toString() {
        return String.format("%s, by %s. Released in %d", title, artist, year);
    }

    @Override
    public int compareTo(MusicStorageOfThePast o) {
        int result = 0;

        if (year < o.getYear()) {
            result = -1;
        } else if(year > o.getYear()) {
            result = 1;
        } else if (title.compareTo(o.getTitle()) != 0) {
            result = title.compareTo(o.getTitle());
        } else if (artist.compareTo(o.getArtist()) != 0) {
            result = artist.compareTo(o.getArtist());
        } else if (length < o.getLength()) {
            result = -1;
        } else if (length > o.getLength()) {
            result = 1;
        }

        return result;
    }
}
