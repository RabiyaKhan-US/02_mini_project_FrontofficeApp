package in.ashokit.controllers;

import in.ashokit.binding.DashBoardResponse;
import in.ashokit.binding.InquiryForm;
import in.ashokit.binding.InquirySearchCriteria;
import in.ashokit.constants.AppConstants;
import in.ashokit.entity.StudentInquiryEntity;
import in.ashokit.repo.StudentInqRepo;
import in.ashokit.service.InquiryService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Optional;

@Controller
public class InquiryController {

    @Autowired
    private InquiryService inqService;
    @Autowired
    private HttpSession session;

    @Autowired
    private StudentInqRepo studentInqRepo;

    @GetMapping("/logout")
    public String logout() {
        session.invalidate();
        return "index";
    }

    @GetMapping("/dashboard")
    // Logic to fetch data for the dashboard
    public String dashboardPage(Model model) {
        Integer userId = (Integer) session.getAttribute(AppConstants.USERID);
        DashBoardResponse dashBoardData = inqService.getDashBoardResponse(userId);
        model.addAttribute("dashBoardData", dashBoardData);
        return "dashboard";
    }

    @GetMapping("/addInquiry")
    public String addInquiryPage(Model model) {
//        // get courses for drop dowm
//        List<String> courses = inqService.getCourseNames();
//        // get inquiry status for drop down
//        List<String> inqstatus = inqService.getInquiryStatus();
//        // Create binding class object
//        InquiryForm formObj = new InquiryForm();
//        // set data in model object
//
//        model.addAttribute("courseNames", courses);
//        model.addAttribute("inqStatusNames", inqstatus);
//        model.addAttribute("formObj", formObj);
//        return "addInquiry";
        initForm(model);
        return "addInquiry";

    }


    @PostMapping("/addInquiry")
    public String addInquiry(@ModelAttribute("formObj") InquiryForm formObj, Model model){
        System.out.print("formObj");
        System.out.println(formObj);
       boolean status =  inqService.saveInquiry(formObj);
       if(status){
           model.addAttribute("successMessage", "Inquiry Added");
       }else{
           model.addAttribute("errMessage", "Problem Occurred");
       }
        return "addInquiry";

    }


    private void initForm(Model model) {
        List<String> courses = inqService.getCourseNames();
        List<String> inquiryStatus = inqService.getInquiryStatus();
        InquiryForm formObj = new InquiryForm();
        model.addAttribute("courseNames", courses);
        model.addAttribute("inquiryStatus", inquiryStatus);
        model.addAttribute("formObj", formObj );

    }
    @GetMapping("/inquiries")
    public String viewInquiriesPage(InquirySearchCriteria criteria, Model model) {
        initForm(model);
       model.addAttribute("searchForm", new InquirySearchCriteria());
       List<StudentInquiryEntity> inquiries = inqService.getInquiries();
        model.addAttribute("inquiries", inquiries);
        return "view-inquiries";
    }


    @GetMapping("/filtered-inquiries")
    public String getFilteredInquiries(@RequestParam String cname, @RequestParam String sname, @RequestParam String mode, Model model){
        InquirySearchCriteria criteria = new InquirySearchCriteria();
        criteria.setCourseName(cname);;
        criteria.setInquiryStatus(sname);
        criteria.setClassMode(mode);
        Integer userId = (Integer) session.getAttribute("userId");
        List<StudentInquiryEntity> filteredInquiries = inqService.getFilteredInquiries(criteria,userId);
        model.addAttribute("inquiries", filteredInquiries);
       return "filtered-inquiries";
    }

    @GetMapping("/edit")
    public String editInq(@RequestParam("inquiryId") Integer inquiryId,Model model) {

        Optional<StudentInquiryEntity> findById = studentInqRepo.findById(inquiryId);

        if(findById.isPresent()) {

            StudentInquiryEntity studentInquiryEntity = findById.get();



            //get courses for the drop down
            List<String> courses = inqService.getCourseNames();
            //get enq status for drop down
            List<String> inquiryStatus = inqService.getInquiryStatus();
            //create binding class object
            InquiryForm formObj = new InquiryForm();

            BeanUtils.copyProperties(studentInquiryEntity, formObj);

            //set data in model object
            model.addAttribute("courseNames", courses);
            model.addAttribute("inquiryStatus", inquiryStatus);
            model.addAttribute("formObj", formObj );

        }

        return "addInquiry";
    }



}
