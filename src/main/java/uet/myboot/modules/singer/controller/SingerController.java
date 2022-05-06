package uet.myboot.modules.singer.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import uet.myboot.modules.music.services.MusicService;
import uet.myboot.modules.singer.models.Singer;
import uet.myboot.modules.singer.service.SingerService;

import java.util.List;

@RestController
@RequestMapping(value = "/singer")
public class SingerController {

    @Autowired
    SingerService singerService;

    @Autowired
    MusicService musicService;

    @GetMapping
    public List<Singer> getAll() {

        return singerService.filterSingers(singerService.getAll());
    }

    @GetMapping("/album")
    public List<Singer> getAllByMusics(@RequestParam("count") int count) {
        List<Singer> singers = singerService.getAllByMusics().subList(0, count);
        return singerService.filterSingers(singers);
    }

}
