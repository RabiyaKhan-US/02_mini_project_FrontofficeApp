package in.ashokit.service;

import in.ashokit.binding.DashBoardResponse;
import in.ashokit.binding.InquiryForm;
import in.ashokit.binding.InquirySearchCriteria;
import in.ashokit.entity.StudentInquiryEntity;

import java.util.List;

public interface InquiryService {

    public List<String> getCourseNames();
    public List<String> getInquiryStatus();
    public DashBoardResponse getDashBoardResponse(Integer userId);
    public boolean saveInquiry(InquiryForm inquiryForm);
    public List<StudentInquiryEntity> getInquiries();

    public List<StudentInquiryEntity> getFilteredInquiries(InquirySearchCriteria criteria, Integer userId );


}
