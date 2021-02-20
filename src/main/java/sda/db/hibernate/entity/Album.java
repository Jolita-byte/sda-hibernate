package sda.db.hibernate.entity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collector;
import java.util.stream.Collectors;

@Entity
@Table(name = "albums")
public class Album {
    @Id
    @GeneratedValue
    private UUID id;
    private String name;

    @ManyToMany (cascade = CascadeType.ALL)
    private List<Song> songs = new ArrayList<>();

    @OneToOne
    private Author author;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Song> getSongs() {
        return songs;
    }

    public void setSongs(List<Song> songs) {
        this.songs = songs;
    }

    public void addSong(Song song){
        song.getAlbum().add(this);
        songs.add(song);
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    @Override
    public String toString() {
        return "Album{" +
                "name='" + name + '\'' +
                ", songs: " + songs.stream().map(Song::getName).collect(Collectors.toList()) +
                ", author=" + author +
                '}';
    }
}
