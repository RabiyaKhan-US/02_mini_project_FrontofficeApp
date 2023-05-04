package in.ashokit.controllers;

import in.ashokit.binding.LoginForm;
import in.ashokit.binding.SignUpForm;
import in.ashokit.binding.UnlockForm;
import in.ashokit.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class UserController {

    @Autowired
    private UserService userService;


    @GetMapping("/login")
    public String loginPage(Model model) {
        model.addAttribute("loginForm", new LoginForm());
        return "login";
    }

    @PostMapping("/login")
    public String login(@ModelAttribute("loginForm") LoginForm loginForm, Model model) {
       String status = userService.login(loginForm);
       if(status.contains("success")){
           // Redirect request  to dashboard
           return "redirect:/dashboard";
       }
       model.addAttribute("errMessage", status);
        return "login";

    }

    @GetMapping("/signup")
    public String signPage(Model model) {
        model.addAttribute("user", new SignUpForm());
        return "signup";
    }

    @PostMapping("/signup")
    public String handleSignUp(@ModelAttribute("user") SignUpForm signUpForm, Model model) {
        boolean status = userService.signup(signUpForm);
        if (status) {
            model.addAttribute("Success", "Account Created! Check your email");
        } else {
            model.addAttribute("ErrMessage", "Choose Unique Email");
        }
        return "signup";

    }

    @GetMapping("/unlock")
    public String unlockPage(@RequestParam String email, Model model) {
        UnlockForm unlockFormObj = new UnlockForm();
        unlockFormObj.setEmail(email);
        model.addAttribute("unlock", unlockFormObj);
        return "unlock";
    }

    @PostMapping("/unlock")
    public String unlockPage(@ModelAttribute("unlock") UnlockForm unlock, Model model) {
        System.out.println(unlock);
        if (unlock.getNewPassword().equals(unlock.getConfirmPassword())) {
            boolean status = userService.unlock(unlock);
            if(status){
                model.addAttribute("successMessage", "Your Account Unlocked Successfully");
            }else{
                model.addAttribute("errMessage", "Given Temporary password is incorrect, Please check your email");
            }
        } else {
            model.addAttribute("errMessage", "New password and Confirm password should be same");
        }
        return "unlock";
    }

    @GetMapping("/forgot")
    public String forgotPwdPage() {
        return "forgotPwd";
    }


    @PostMapping("/forgotPwd")
    public String forgotPwd(@RequestParam("email") String email, Model model) {
        System.out.println(email);
        boolean status = userService.forgotPwd(email);
        if(status){
        model.addAttribute("successMessage", "Password sent to your email");
        }else{
            model.addAttribute("errMessage", "Invalid emailId");
        }

        return "forgotPwd";
    }

}
