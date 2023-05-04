package in.ashokit.entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "AIT_INQUIRY_STATUS")
public class InqStatusEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer statusId;
    private String statusName;

}
