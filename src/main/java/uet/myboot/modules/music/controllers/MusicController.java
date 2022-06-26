package uet.myboot.modules.music.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import uet.myboot.modules.genre.models.MusicGenre;
import uet.myboot.modules.genre.service.MusicGenreService;
import uet.myboot.modules.music.models.Music;
import uet.myboot.modules.music.services.MusicService;
import uet.myboot.modules.singer.models.Singer;
import uet.myboot.modules.singer.service.SingerService;
import uet.myboot.parent.main.EResponse;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.*;

@RestController
@RequestMapping("/music")
public class MusicController {

    @Autowired
    MusicService musicService;

    @Autowired
    MusicGenreService musicGenreService;

    @Autowired
    SingerService singerService;

    //lấy toàn bộ bài nhạc
    @GetMapping
    public List<Music> getAll() {
        return musicService.filterMusics(musicService.getAll());
    }


    //lấy bài nhạc có số lượt nghe cao nhất theo count
    @GetMapping(value = "/top-music")
    public List<Music> getTopMusic(HttpServletRequest request) {
        int count = request.getParameter("count").isEmpty() ? 0 : Integer.parseInt(request.getParameter("count"));
        List<Music> musics = musicService.getAll().subList(0, count);

        return musicService.filterMusics(musics);
    }

    //lấy bài nhạc theo thể loại nhạc
    @GetMapping(value = "/by-genre-id")
    public List<Music> getAllByGenreID(@RequestParam("id") int id) {
        List<Music> musics = new ArrayList<>();
        for(Music music:musicService.getAll()) {
            for(MusicGenre musicGenre:music.getMusicGenres()) {
                if(musicGenre.getId() == id) {
                    musics.add(music);
                }
            }
        }

        return musicService.filterMusics(musics);
    }

    //lấy nhạc theo id của ca sĩ
    @GetMapping(value = "/by-singer-id")
    public List<Music> getAllBySingerID(@RequestParam("id") int id) {
        List<Music> musics = new ArrayList<>();
        for(Music music:musicService.getAll()) {
            for(Singer singer:music.getSingers()) {
                if(singer.getId() == id) {
                    musics.add(music);
                }
            }
        }

        return musicService.filterMusics(musics);
    }

    //update view
    @PutMapping(value = "/update-view")
    public EResponse updateView(@Valid @RequestBody Music music) {
        try {
            musicService.update(music);

            return EResponse.SUCCESS;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return EResponse.FAILED;
        }

    }

/*    @GetMapping(value = "/create")
    public List<Singer> create() {
        List<Music> list = musicService.getAll();
        Set<String> setSingers = new HashSet<>();

        for (Music music:list) {
            String singer = music.getSinger();
            if(singer.contains("&")) {
                String[] singers = singer.split("&");
                setSingers.addAll(Arrays.asList(singers));
            } else {
                setSingers.add(singer);
            }
        }
        List<Singer> list0 = new ArrayList<>();

        for(String s:setSingers) {
            List<Music> list1 = new ArrayList<>();
            for (Music music:list) {
                if(music.getSinger().contains(s)) {
                    list1.add(music);
                }
            }
            Singer singer = new Singer(s, list1);
            list0.add(singer);
        }
        int id = 1;
        for(Singer singer : list0) {
            singer.setId(id);
            id++;
            singerService.save(singer);
        }

        return list0;
    }*/


}
