package uet.myboot.modules.genre.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uet.myboot.modules.genre.models.MusicGenre;
import uet.myboot.modules.genre.service.MusicGenreService;

import java.util.List;

@RestController
@RequestMapping("/music-genre")
public class MusicGenreController {

    @Autowired
    MusicGenreService musicGenreService;

    @GetMapping
    public List<MusicGenre> getAll() {
        return musicGenreService.filterMusicGenres(musicGenreService.getAll());
    }

    @GetMapping(value = "/top")
    public List<MusicGenre> getAllGenreTop() {
        return musicGenreService.filterMusicGenres(musicGenreService.getAll().subList(0, 3));
    }

    @GetMapping(value = "/bot")
    public List<MusicGenre> getAllGenreBot() {
        return musicGenreService.filterMusicGenres(musicGenreService.getAll().subList(3, 6));
    }
}
