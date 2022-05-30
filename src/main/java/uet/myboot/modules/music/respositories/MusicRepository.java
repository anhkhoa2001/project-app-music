package uet.myboot.modules.music.respositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uet.myboot.modules.music.models.Music;

@Repository
public interface MusicRepository extends JpaRepository<Music, Integer> {

    Music getMusicById(int id);
}
