package in.ashokit.binding;

import lombok.Data;

@Data
public class DashBoardResponse {
    private Integer totalInquiries;
    private Integer totalEnrolled;
    private Integer totalLost;
    private Integer totalNew;
}


