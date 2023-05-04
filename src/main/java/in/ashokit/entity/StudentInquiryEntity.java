package in.ashokit.entity;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.math.BigInteger;
import java.time.LocalDate;

@Data
@Entity
@Table(name = "AIT_STUDENT_INQUIRIES")
public class StudentInquiryEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer inquiryId;
    private String studentName;
    private String studentPhoneNo;
    private String classMode;
    private String courseName;
    private String inquiryStatus;
    @CreationTimestamp
    @Column(name = "create_date")
    private LocalDate createdDate;
    @UpdateTimestamp
    private LocalDate updatedDate;


    @ManyToOne
    @JoinColumn(name = "userId")
    private UserDetailsEntity user;


}
