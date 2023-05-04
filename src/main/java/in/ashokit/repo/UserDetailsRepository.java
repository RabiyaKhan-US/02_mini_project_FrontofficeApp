package in.ashokit.repo;

import in.ashokit.entity.UserDetailsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserDetailsRepository extends JpaRepository<UserDetailsEntity, Integer> {

    public UserDetailsEntity findByuserEmail(String email);

     @Query("select u from UserDetailsEntity u where u.userEmail=:e and u.userPwd=:p")
    public UserDetailsEntity findByuserEmailAnduserPwd(@Param("e")String email, @Param("p")String password);

    public UserDetailsEntity findByuserPwd(String password);


}