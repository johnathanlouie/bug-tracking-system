package edu.uci.ics.dts;

import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "SendEmail", urlPatterns = {"/SendEmail"})
public class SendEmail extends LoginServlet {

    // change these two variables to get this servlet to send emails
    private static final String GMAIL_ACCOUNT = "address@gmail.com";
    private static final String GMAIL_PASSWORD = "password";

    @Override
    protected LoginServletHelper mainMethod(HttpServletRequest request, HttpServletResponse response) {
        String receiver = DatabaseHandler.getUserByName(request.getParameter("assignee")).getEmail();
        String messageBody = DatabaseHandler.getBugById(Integer.parseInt(request.getParameter("bugid"))).toString() + request.getParameter("message");
        String subject = request.getParameter("subject");
        String emailStatus;
        try {
            gmail(GMAIL_ACCOUNT, GMAIL_PASSWORD, receiver, subject, messageBody);
            emailStatus = "Email sent.";
        } catch (MessagingException ex) {
            Logger.getLogger(SendEmail.class.getName()).log(Level.SEVERE, null, ex);
            emailStatus = "Email was not sent. Either Gmail is down or the admin did not properly configure the servlet SendEmail.";
        }
        request.setAttribute("message", emailStatus);
        String forwardPage = "/updatebugresults.jsp";
        return new LoginServletHelper(request, response, forwardPage);
    }

    public static void gmail(String login, String password, String recipient, String subject, String messageBody) throws MessagingException {
        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.socketFactory.port", "465");
        props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.port", "465");

        Session session = Session.getDefaultInstance(
                props,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(login, password);
                    }
                }
        );

        Message message = new MimeMessage(session);
        message.setFrom(new InternetAddress("from@no-spam.com"));
        message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipient));
        message.setSubject(subject);
        message.setText(messageBody);

        Transport.send(message);

    }
}
