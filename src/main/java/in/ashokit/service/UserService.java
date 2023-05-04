package in.ashokit.service;

import in.ashokit.binding.LoginForm;
import in.ashokit.binding.SignUpForm;
import in.ashokit.binding.UnlockForm;
import org.springframework.stereotype.Service;

@Service
public interface UserService{
    public String login(LoginForm loginForm);
    public boolean signup(SignUpForm signupForm);

    public boolean forgotPwd(String email);

    public boolean unlock(UnlockForm unlockForm);






}
