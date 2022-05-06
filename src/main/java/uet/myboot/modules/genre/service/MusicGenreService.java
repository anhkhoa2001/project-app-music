package uet.myboot.modules.genre.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uet.myboot.modules.genre.models.MusicGenre;
import uet.myboot.modules.genre.repository.MusicGenreRepository;
import uet.myboot.modules.music.models.Music;
import uet.myboot.modules.music.respositories.MusicRepository;

import java.util.List;

@Service
public class MusicGenreService {

    @Autowired
    MusicGenreRepository musicGenreRepository;

    public List<MusicGenre> getAll() {
        return musicGenreRepository.findAll();
    }

    public boolean deleteBy(int id) {
        int before = getAll().size();
        musicGenreRepository.deleteById(id);
        int last = getAll().size();
        return before > last;
    }

    public boolean save(MusicGenre t) {
        int before = getAll().size();
        musicGenreRepository.save(t);
        int last = getAll().size();
        return before < last;
    }

    public MusicGenre getOne(int id) {
        return musicGenreRepository.findById(id).get();
    }

}
