package uet.myboot.modules.music.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uet.myboot.modules.genre.models.MusicGenre;
import uet.myboot.modules.music.models.Music;
import uet.myboot.modules.music.respositories.MusicRepository;
import uet.myboot.modules.singer.models.Singer;
import uet.myboot.modules.user.models.User;

import java.util.Collections;
import java.util.List;

@Service
public class MusicService {

    @Autowired
    MusicRepository musicRepository;

    public List<Music> getAll() {
        return musicRepository.findAll();
    }

    public boolean deleteBy(int id) {
        int before = getAll().size();
        musicRepository.deleteById(id);
        int last = getAll().size();
        return before > last;
    }

    public boolean save(Music t) {
        int before = getAll().size();
        musicRepository.save(t);
        int last = getAll().size();
        return before < last;
    }

    public Music getOne(int id) {
        return musicRepository.getMusicById(id);
    }

    public List<Music> filterMusics(List<Music> musics) {
        for(Music music:musics) {
            for(Singer singer:music.getSingers()) {
                singer.setMusics(Collections.emptyList());
            }
            for(MusicGenre musicGenre:music.getMusicGenres()) {
                musicGenre.setMusics(Collections.emptyList());
            }
            for(User user:music.getUsers()) {
                user.setFavourites(Collections.emptySet());
            }
        }
        return musics;
    }

}
