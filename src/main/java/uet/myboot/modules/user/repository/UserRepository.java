package uet.myboot.modules.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestMapping;
import uet.myboot.modules.user.models.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {


}
