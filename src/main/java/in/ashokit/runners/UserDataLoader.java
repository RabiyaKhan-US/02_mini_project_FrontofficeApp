package in.ashokit.runners;

import in.ashokit.entity.CourseEntity;
import in.ashokit.entity.InqStatusEntity;
import in.ashokit.entity.UserDetailsEntity;
import in.ashokit.repo.CourseRepo;
import in.ashokit.repo.InqStatusRepo;
import in.ashokit.repo.UserDetailsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;


import java.util.Arrays;
import java.util.List;

@Component
public class UserDataLoader implements ApplicationRunner {

    @Autowired
    private CourseRepo courseRepo;


    @Autowired
    private InqStatusRepo inqStatusRepo;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        courseRepo.deleteAll();
        inqStatusRepo.deleteAll();

        CourseEntity ce1 = new CourseEntity();
        ce1.setCourseName("Java");
        CourseEntity ce2 = new CourseEntity();
        ce2.setCourseName("Python");
        CourseEntity ce3 = new CourseEntity();
        ce3.setCourseName("DevOps");

        courseRepo.saveAll(Arrays.asList(ce1,ce2,ce3));


        InqStatusEntity is1 = new InqStatusEntity();
        is1.setStatusName("New");
        InqStatusEntity is2 = new InqStatusEntity();
        is2.setStatusName("Enrolled");
        InqStatusEntity is3 = new InqStatusEntity();
        is3.setStatusName("Lost");

        inqStatusRepo.saveAll(Arrays.asList(is1,is2,is3));


    }
}