package in.ashokit.repo;

import in.ashokit.entity.StudentInquiryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentInqRepo extends JpaRepository<StudentInquiryEntity, Integer> {
}
