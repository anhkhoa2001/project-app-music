package uet.myboot.modules.genre.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uet.myboot.modules.genre.models.MusicGenre;

@Repository
public interface MusicGenreRepository extends JpaRepository<MusicGenre, Integer> {
}
