package in.ashokit.service;

import in.ashokit.binding.DashBoardResponse;
import in.ashokit.binding.InquiryForm;
import in.ashokit.binding.InquirySearchCriteria;
import in.ashokit.constants.AppConstants;
import in.ashokit.entity.CourseEntity;
import in.ashokit.entity.InqStatusEntity;
import in.ashokit.entity.StudentInquiryEntity;
import in.ashokit.entity.UserDetailsEntity;
import in.ashokit.repo.CourseRepo;
import in.ashokit.repo.InqStatusRepo;
import in.ashokit.repo.StudentInqRepo;
import in.ashokit.repo.UserDetailsRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class InquiryServiceImpl implements InquiryService {


    @Autowired
    private HttpSession session;
    @Autowired
    private StudentInqRepo studRepo;
    @Autowired
    private InqStatusRepo inqStatusRepo;
    @Autowired
    private CourseRepo crepo;

    @Autowired
    private UserDetailsRepository userDtlsrepo;

    @Override
    public List<String> getCourseNames() {
        // List<CourseEntity> findAll = crepo.findByCourseNames();

        List<CourseEntity> findAll = crepo.findAll();
        List<String> names = new ArrayList<>();
        for (CourseEntity entity : findAll) {
            names.add(entity.getCourseName());
        }
        return names;
    }

    @Override
    public List<String> getInquiryStatus() {
        List<InqStatusEntity> findAll = inqStatusRepo.findAll();
        List<String> statusList = new ArrayList<>();
        for (InqStatusEntity entity : findAll) {
            statusList.add(entity.getStatusName());
        }
        return statusList;
    }

    @Override
    public DashBoardResponse getDashBoardResponse(Integer userId) {
        DashBoardResponse dashBoardResponse = new DashBoardResponse();
        Optional<UserDetailsEntity> findById = userDtlsrepo.findById(userId);
        if (findById.isPresent()) {
            UserDetailsEntity userEntity = findById.get();
            List<StudentInquiryEntity> inquiries = userEntity.getInquiryEntities();
            Integer totalInquiries = inquiries.size();
            //Todo: Filter the data to get the lost inquiries
            Integer totalEnrolled = inquiries.stream()
                    .filter(e -> e.getInquiryStatus().equals(AppConstants.ENROLLED))
                    .collect(Collectors.toList()).size();
            Integer totalLost = inquiries.stream()
                    .filter(e -> e.getInquiryStatus().equals(AppConstants.LOST))
                    .collect(Collectors.toList()).size();

            Integer totalNew = inquiries.stream()
                    .filter(e -> e.getInquiryStatus().equals(AppConstants.NEW))
                    .collect(Collectors.toList()).size();

            dashBoardResponse.setTotalInquiries(totalInquiries);
            dashBoardResponse.setTotalEnrolled(totalEnrolled);
            dashBoardResponse.setTotalLost(totalLost);
            dashBoardResponse.setTotalNew(totalNew);
        }
        return dashBoardResponse;
    }

    @Override
    public boolean saveInquiry(InquiryForm inquiryForm) {
        StudentInquiryEntity studIEntity = new StudentInquiryEntity();
        BeanUtils.copyProperties(inquiryForm, studIEntity);
        Integer userId = (Integer) session.getAttribute(AppConstants.USERID);
        UserDetailsEntity userEntity = userDtlsrepo.findById(userId).get();
        studIEntity.setUser(userEntity);
        studRepo.save(studIEntity);
        return true;
    }

    @Override
    public List<StudentInquiryEntity> getInquiries() {
        Integer userId = (Integer) session.getAttribute(AppConstants.USERID);
        Optional<UserDetailsEntity> findById = userDtlsrepo.findById(userId);
        if (findById.isPresent()) {
            UserDetailsEntity userDetails = findById.get();
            List<StudentInquiryEntity> inquiries = userDetails.getInquiryEntities();
            return inquiries;
        }
        return null;
    }

    @Override
    public List<StudentInquiryEntity> getFilteredInquiries(InquirySearchCriteria criteria, Integer userId) {
       Optional<UserDetailsEntity> findById = userDtlsrepo.findById(userId);
        if (findById.isPresent()) {
            UserDetailsEntity userDetails = findById.get();
            List<StudentInquiryEntity> inquiries = userDetails.getInquiryEntities();
            if(null != criteria.getCourseName()  & !"".equals(criteria.getCourseName())){

               inquiries=  inquiries.stream().filter(e -> e.getCourseName().equals(criteria.getCourseName()))
                       .collect(Collectors.toList());
            }

            if(null != criteria.getInquiryStatus()  & !"".equals(criteria.getInquiryStatus())){

                inquiries=  inquiries.stream().filter(e -> e.getInquiryStatus().equals(criteria.getInquiryStatus()))
                        .collect(Collectors.toList());
            }

            if(null != criteria.getClassMode()  & !"".equals(criteria.getClassMode())){

                inquiries=  inquiries.stream().filter(e -> e.getClassMode().equals(criteria.getClassMode()))
                        .collect(Collectors.toList());
            }

           // return inquiries;
            return inquiries;
        }
        return null;
    }

}
