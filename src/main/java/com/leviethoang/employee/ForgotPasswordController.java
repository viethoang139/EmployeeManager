package com.leviethoang.employee;

import net.bytebuddy.utility.RandomString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;

@Controller
public class ForgotPasswordController {
    @Autowired
    private JavaMailSender sender;

    @Autowired
    private EmployeeService service;

    @GetMapping("/forgot_password")
    public String showForgotPasswordForm(){
        return "forgot_password_form";
    }

    @PostMapping("/forgot_password")
    public String processForgotPassword(HttpServletRequest request , Model model){
        String email = request.getParameter("email");
        String token = RandomString.make(45);
        try{
            service.updateResetPasswordToken(token , email);
            String resetPasswordLink = Utility.getSiteURL(request) + "/reset_password?token=" + token;
            sendEmail(email , resetPasswordLink);
            model.addAttribute("message", "We have sent a reset password link to your email. Please check.");
        } catch (EmployeeNotFoundException e) {
            model.addAttribute("error" , "Not found email");
        }catch (UnsupportedEncodingException | MessagingException e){
            model.addAttribute("error" , "Error while sending email");
        }
        return "forgot_password_form";
    }

    private void sendEmail(String recipientEmail, String link)  throws MessagingException, UnsupportedEncodingException{
        MimeMessage message = sender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);

        helper.setFrom("viethoangle231@gmail.com" , "Support");
        helper.setTo(recipientEmail);

        String subject = "Here's the link to reset your password";

        String content = "<p>Hello,</p>"
                + "<p>You have requested to reset your password.</p>"
                + "<p>Click the link below to change your password:</p>"
                + "<p><a href=\"" + link + "\">Change my password</a></p>"
                + "<br>"
                + "<p>Ignore this email if you do remember your password, "
                + "or you have not made the request.</p>";

        helper.setSubject(subject);

        helper.setText(content, true);

        sender.send(message);

    }

    @GetMapping("/reset_password")
    public String showResetPasswordForm(@Param(value = "token") String token , Model model){
        Employee employees = service.getByResetPasswordToken(token);
        model.addAttribute("token" , token);

        return "reset_password_form";
    }

    @PostMapping("/reset_password")
    public String processResetPasswordForm(HttpServletRequest request , Model model){
        String token = request.getParameter("token");
        String password = request.getParameter("password");
        Employee employee = service.getByResetPasswordToken(token);
        if(employee == null){
            model.addAttribute("message" , "Invalid Token");
        }
        else{
            service.updatePassword(employee , password);
            model.addAttribute("message" , " You have successfully changed your password.");
        }
        return "reset_password_form";
    }

}
