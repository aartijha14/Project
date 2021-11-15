package com.ttn.project2.Service;

import com.ttn.project2.Model.User;
import com.ttn.project2.email.EmailSender;
import com.ttn.project2.repository.UserAddressRepository;
import com.ttn.project2.repository.UserRepository;
import com.ttn.project2.repository.ConfirmationTokenRepo;
import com.ttn.project2.Model.ResetToken;
import com.ttn.project2.repository.ResetTokenRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {

    @Autowired
    UserRepository repo;

    @Autowired
    ResetTokenService resetTokenService;

    @Autowired
    ResetTokenRepo resetRepo;
    @Autowired
    ConfirmationTokenRepo tokenRepo;

    @Autowired
    BCryptPasswordEncoder passwordEncoder;

    @Autowired
    UserAddressRepository userAddressRepository;

    @Autowired
    EmailSender emailSender;

    // User Login
    public String loginUser(@RequestBody User user) {
        User existingUser = repo.findByEmailIgnoreCase(user.getEmail());
        if (existingUser != null) {

            // Use encoder.matches to compare raw password with encrypted password
            if (existingUser.isEnabled()) {
                if (passwordEncoder.matches(user.getPassword(), existingUser.getPassword())) {
                    //if (user.getPassword().equals(existingUser.getPassword())) {

                    // Successfully logged in
                    existingUser.setInvalidAttemptCount(0);
                    existingUser.setActive(true);
                    String token = UUID.randomUUID().toString();
                    existingUser.setAccessToken(token);

                    repo.save(existingUser);
                    return "Successfully Logged In ! You May Login Now " + " Your Access token is : " + token;

                } else {

                    // Wrong password
                    if (existingUser.getInvalidAttemptCount() == 3) {
                        existingUser.setLocked(true);
                        repo.save(existingUser);
                        return "To many invalid attempt Account locked ";
                    } else {
                        int i = existingUser.getInvalidAttemptCount();
                        existingUser.setInvalidAttemptCount(i + 1);
                        repo.save(existingUser);
                        return "Incorrect password. Try again.";
                    }
                }
            } else {
                return "Account is disable, to continue please confirm the link sent to your Register Email id";
            }
        } else {
            return "The email provided does not exist!";

        }
    }

    //User Logout
    public String logoutUser(@RequestBody User user) {
        User existingUser = repo.findByEmailIgnoreCase(user.getEmail());
        User access = repo.findByAccessToken(user.getAccessToken());
        if (existingUser != null) {
            if (access != null) {
                // Use encoder.matches to compare raw password with encrypted password
                if (existingUser.isActive()) {
                    if (passwordEncoder.matches(user.getPassword(), existingUser.getPassword())) {
                        existingUser.setActive(false);
                        existingUser.setAccessToken(null);

                        // Successfully logged out
                        repo.save(existingUser);
                        return "Successfully logged out!";
                    } else {
                        // Wrong password
                        return "Incorrect password. Try again.";
                    }
                } else {
                    return "Already logged out";
                }

            } else {
                return "Access token is not valid";
            }

        } else {
            return "The email provided does not exist!";
        }
    }


    //Forget Password
    public String forgotPassword(@RequestBody User user) {
        Optional<User> optional = Optional.ofNullable(repo.findByEmailIgnoreCase(user.getEmail()));

        if (!optional.isPresent()) {
            return "We didn't find an account for the e-mail address";
        } else {

            User user1 = optional.get();

            long i = user1.getId();
            tokenRepo.deleteById(i);

            String token = UUID.randomUUID().toString();

            ResetToken resetToken = new ResetToken(
                    token,
                    LocalDateTime.now().plusMinutes(15),
                    user1
            );
            resetTokenService.saveResetToken(resetToken);

            String link = "http://localhost:8080/reset?token=" + token;

            //Send Email
            emailSender.send(
                    user.getEmail(),
                    buildEmail(user1.getFirstName(), link));

            return token;

        }
    }

    // Reset Password
    @Transactional
    public String resetToken(@RequestBody User user) {
        ResetToken reset_Token = resetRepo.findByToken(user.getResetToken());
        LocalDateTime expiredAt = reset_Token.getExpiresAt();

        if (reset_Token != null) {

            User user1 = repo.findByEmailIgnoreCase(reset_Token.getUser().getEmail());
            user1.setPassword(passwordEncoder.encode(user.getPassword()));

            long i = user1.getId();
            resetRepo.deleteById(i);

            repo.save(user1);

            return "You have successfully reset your password";

        } else {

            return "Oops!  This is an invalid token";

        }
    }


    // Save User
    public void save(User user) {
        repo.save(user);
    }

    // View Address
    public List<User> viewAddress() {

        return this.repo.findAll();
    }


    //Email
    private String buildEmail(String name, String link) {
        return "<div style=\"font-family:Helvetica,Arial,sans-serif;font-size:16px;margin:0;color:#0b0c0c\">\n" +
                "\n" +
                "<span style=\"display:none;font-size:1px;color:#fff;max-height:0\"></span>\n" +
                "\n" +
                "  <table role=\"presentation\" width=\"100%\" style=\"border-collapse:collapse;min-width:100%;width:100%!important\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\">\n" +
                "    <tbody><tr>\n" +
                "      <td width=\"100%\" height=\"53\" bgcolor=\"#0b0c0c\">\n" +
                "        \n" +
                "        <table role=\"presentation\" width=\"100%\" style=\"border-collapse:collapse;max-width:580px\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" align=\"center\">\n" +
                "          <tbody><tr>\n" +
                "            <td width=\"70\" bgcolor=\"#0b0c0c\" valign=\"middle\">\n" +
                "                <table role=\"presentation\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse\">\n" +
                "                  <tbody><tr>\n" +
                "                    <td style=\"padding-left:10px\">\n" +
                "                  \n" +
                "                    </td>\n" +
                "                    <td style=\"font-size:28px;line-height:1.315789474;Margin-top:4px;padding-left:10px\">\n" +
                "                      <span style=\"font-family:Helvetica,Arial,sans-serif;font-weight:700;color:#ffffff;text-decoration:none;vertical-align:top;display:inline-block\">Reset Your Password</span>\n" +
                "                    </td>\n" +
                "                  </tr>\n" +
                "                </tbody></table>\n" +
                "              </a>\n" +
                "            </td>\n" +
                "          </tr>\n" +
                "        </tbody></table>\n" +
                "        \n" +
                "      </td>\n" +
                "    </tr>\n" +
                "  </tbody></table>\n" +
                "  <table role=\"presentation\" class=\"m_-6186904992287805515content\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse;max-width:580px;width:100%!important\" width=\"100%\">\n" +
                "    <tbody><tr>\n" +
                "      <td width=\"10\" height=\"10\" valign=\"middle\"></td>\n" +
                "      <td>\n" +
                "        \n" +
                "                <table role=\"presentation\" width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse\">\n" +
                "                  <tbody><tr>\n" +
                "                    <td bgcolor=\"#1D70B8\" width=\"100%\" height=\"10\"></td>\n" +
                "                  </tr>\n" +
                "                </tbody></table>\n" +
                "        \n" +
                "      </td>\n" +
                "      <td width=\"10\" valign=\"middle\" height=\"10\"></td>\n" +
                "    </tr>\n" +
                "  </tbody></table>\n" +
                "\n" +
                "\n" +
                "\n" +
                "  <table role=\"presentation\" class=\"m_-6186904992287805515content\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse;max-width:580px;width:100%!important\" width=\"100%\">\n" +
                "    <tbody><tr>\n" +
                "      <td height=\"30\"><br></td>\n" +
                "    </tr>\n" +
                "    <tr>\n" +
                "      <td width=\"10\" valign=\"middle\"><br></td>\n" +
                "      <td style=\"font-family:Helvetica,Arial,sans-serif;font-size:19px;line-height:1.315789474;max-width:560px\">\n" +
                "        \n" +
                "            <p style=\"Margin:0 0 20px 0;font-size:19px;line-height:25px;color:#0b0c0c\">Hi " + name + ",</p><p style=\"Margin:0 0 20px 0;font-size:19px;line-height:25px;color:#0b0c0c\"> Please click on the below link to reset your password: </p><blockquote style=\"Margin:0 0 20px 0;border-left:10px solid #b1b4b6;padding:15px 0 0.1px 15px;font-size:19px;line-height:25px\"><p style=\"Margin:0 0 20px 0;font-size:19px;line-height:25px;color:#0b0c0c\"> <a href=\"" + link + "\">Reset Now</a> </p></blockquote>\n Link will expire in 15 Minutes. <p>See you soon!</p>" +
                "        \n" +
                "      </td>\n" +
                "      <td width=\"10\" valign=\"middle\"><br></td>\n" +
                "    </tr>\n" +
                "    <tr>\n" +
                "      <td height=\"30\"><br></td>\n" +
                "    </tr>\n" +
                "  </tbody></table><div class=\"yj6qo\"></div><div class=\"adL\">\n" +
                "\n" +
                "</div></div>";
    }

}