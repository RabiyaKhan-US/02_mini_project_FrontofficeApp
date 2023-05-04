package in.ashokit.entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "AIT_COURSES")
public class CourseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer courseId;
    private String courseName;

}
