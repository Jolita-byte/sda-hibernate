package sda.db.hibernate;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;
import sda.db.hibernate.entity.Album;
import sda.db.hibernate.entity.Author;
import sda.db.hibernate.entity.Song;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import java.time.Instant;
import java.util.List;


public class Project {
    public void run(){
        SessionFactory sessionFactory = new Configuration()
                .configure("hibernate.cfg.xml")
                .addAnnotatedClass(Album.class)
                .addAnnotatedClass(Author.class)
                .addAnnotatedClass(Song.class)
                .buildSessionFactory();
        EntityManager em = sessionFactory.createEntityManager();
        EntityTransaction t = em. getTransaction();



        t.begin();

        Author author = new Author();
        author.setName("Super Author");

        Song songD = new Song("song D", author, 200, Instant.now());

        Album albumA = createAlbumA(author);
        albumA.addSong(songD);

        Album albumB = createAlbumB(author);
        albumB.addSong(songD);

        em.persist(author);
        em.persist(albumA);
        em.persist(albumB);

        t.commit();

        List<Song> songs = em.createQuery("FROM Song s", Song.class).getResultList();
        songs.forEach(System.out::println);

        List<Album> albumsA = em.createQuery("FROM Album", Album.class).getResultList();
        albumsA.forEach(System.out::println);




/*        try (Session session = sessionFactory.openSession()){
            Query q = session.createQuery("SELECT s FROM Song s", Song.class);
        }*/
    }

    private Album createAlbumA (Author author){
        Song song1 = new Song("Song A", author, 3, Instant.now());
        Song song2 = new Song("Song B", author, 6, Instant.now());

        Album album = new Album();
        album.setName("old Album");
        album.setAuthor(author);
        album.addSong(song1);
        album.addSong(song2);

        return album;
    }

    private Album createAlbumB (Author author){
        Song song1 = new Song("Song C", author, 2, Instant.now());

        Album album = new Album();
        album.setName("New Album");
        album.setAuthor(author);
        album.addSong(song1);

        return album;
    }
}
