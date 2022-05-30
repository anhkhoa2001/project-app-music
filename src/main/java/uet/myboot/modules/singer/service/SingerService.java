package uet.myboot.modules.singer.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uet.myboot.modules.music.models.Music;
import uet.myboot.modules.singer.models.Singer;
import uet.myboot.modules.singer.repository.SingerRepository;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Service
public class SingerService {

    @Autowired
    SingerRepository singerRepository;

    public List<Singer> getAll() {
        return singerRepository.findAll();
    }

    public boolean deleteBy(int id) {
        int before = getAll().size();
        singerRepository.deleteById(id);
        int last = getAll().size();
        return before > last;
    }

    public boolean save(Singer t) {
        int before = getAll().size();
        singerRepository.save(t);
        int last = getAll().size();
        return before < last;
    }

    public Singer getOne(int id) {
        return singerRepository.findById(id).get();
    }

    public List<Singer> getAllByMusics() {
        List<Singer> singers = getAll();

        Collections.sort(singers, new Comparator<Singer>() {
            @Override
            public int compare(Singer singer1, Singer singer2) {
                return Integer.compare(singer2.getMusics().size(), singer1.getMusics().size());
            }
        });

        return singers;
    }

    public List<Singer> filterSingers(List<Singer> singers) {
        for(Singer singer:singers) {
            for(Music music:singer.getMusics()) {
                music.setSingers(Collections.emptyList());
                music.setMusicGenres(Collections.emptyList());
                music.setUsers(Collections.emptyList());
            }
        }

        return singers;
    }

}
