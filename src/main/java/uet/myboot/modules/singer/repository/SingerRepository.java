package uet.myboot.modules.singer.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uet.myboot.modules.singer.models.Singer;

@Repository
public interface SingerRepository extends JpaRepository<Singer, Integer> {
}
