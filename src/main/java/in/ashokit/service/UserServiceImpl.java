package in.ashokit.service;

import in.ashokit.binding.LoginForm;
import in.ashokit.binding.SignUpForm;
import in.ashokit.binding.UnlockForm;
import in.ashokit.constants.AppConstants;
import in.ashokit.entity.UserDetailsEntity;
import in.ashokit.repo.UserDetailsRepository;
import in.ashokit.util.EmailUtils;
import in.ashokit.util.PwdUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private EmailUtils emailUtils;

    @Autowired
    private HttpSession session;

    @Autowired
    private UserDetailsRepository urepo;

    @Override
    public String login(LoginForm loginForm) {

        UserDetailsEntity entity = urepo.findByuserEmailAnduserPwd(loginForm.getEmail(),loginForm.getPassword());

        if(entity == null){
            return AppConstants.INVALID_CRED;
        }

        if(entity.getAccStatus().equals(AppConstants.STR_LOCKED))
        {
            return AppConstants.ACCOUNT_LOCKED;
        }

        session.setAttribute(AppConstants.USERID, entity.getUserId());
        return AppConstants.SUCCESS;
    }

    /***
     * From UI we are not getting the pwd , so it to tmp first
     * @param signupForm
     * @return
     */
    @Override
    public boolean signup(SignUpForm signupForm) {

      UserDetailsEntity udEntity = urepo.findByuserEmail(signupForm.getUserEmail());

      if(udEntity!= null){
          return false;
      }


        // Todo: 1. Copy data from binding object to entity object
        UserDetailsEntity entity = new UserDetailsEntity();
        BeanUtils.copyProperties(signupForm, entity);
        //Todo : 2. Generate random password and set to the object
        String tempPwd = PwdUtils.generateRandomPassword();
        entity.setUserPwd(tempPwd);
        // Todo : 3. set account status as locked
        entity.setAccStatus(AppConstants.STR_LOCKED);
        // Todo : 4. Insert record
        urepo.save(entity);
        // Todo: 5. Send email to unlock account
        String subject = AppConstants.UNLOCK_EMAIL_SUBJECT;
        String to = signupForm.getUserEmail();
        StringBuffer body = new StringBuffer();
        body.append(AppConstants.TEMP_PWD_UNLOCK);
        body.append(AppConstants.TEMP_PASSWORD + tempPwd);
        body.append("<br/>");
        body.append("<a href =\"http://localhost:8080/unlock?email=" + to + "\">Click here to unlock your account </a>");

        emailUtils.sendEmail(subject, body.toString(), to);
        return true;
    }

    @Override
    public boolean forgotPwd(String email) {
       UserDetailsEntity entity =  urepo.findByuserEmail(email);
       if(entity == null) {
           return false;
       }
           String subject = AppConstants.RECOVER_PWD_EMAIL_SUBJECT;
           String body = AppConstants.YOUR_PWD +entity.getUserPwd();

           emailUtils.sendEmail(subject, body.toString(), email);

        return true;
    }

    @Override
    public boolean unlock(UnlockForm unlockForm) {
        UserDetailsEntity entity = urepo.findByuserEmail(unlockForm.getEmail());
        if(entity.getUserPwd().equals(unlockForm.getTempPassword())) {

            entity.setUserPwd(unlockForm.getNewPassword());
            entity.setAccStatus(AppConstants.STR_UNLOCKED);
            urepo.save(entity);
            return true;
        } else {
            return false;
        }
    }
}
