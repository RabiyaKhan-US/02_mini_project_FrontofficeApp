package in.ashokit.repo;

import in.ashokit.entity.InqStatusEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InqStatusRepo extends JpaRepository<InqStatusEntity, Integer> {

}