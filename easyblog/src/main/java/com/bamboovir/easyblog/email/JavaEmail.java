package com.bamboovir.easyblog.email;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class JavaEmail
{

    Properties emailProperties;
    Session mailSession;
    MimeMessage emailMessage;

    public void sendEmail(String otherEmailAddress, String authURL)
    {
    	
        JavaEmail javaEmail = new JavaEmail();
        javaEmail.setMailServerProperties();
        try
        {
            javaEmail.createEmailMessage(otherEmailAddress,authURL);
            javaEmail.sendEmail();
        }
        catch (AddressException e)
        {
            System.out.println("Address Exception:" + e.getMessage());
            e.printStackTrace();
        }
        catch (MessagingException e)
        {
            System.out.println("Message Exception:" + e.getMessage());
            e.printStackTrace();
        }
    }

    public void setMailServerProperties()
    {

        String emailPort = "587";// gmail's smtp port

        emailProperties = System.getProperties();
        emailProperties.put("mail.smtp.port", emailPort);
        emailProperties.put("mail.smtp.auth", "true");
        emailProperties.put("mail.smtp.starttls.enable", "true");

    }

    public void createEmailMessage(String otherEmailAddress, String authURL) throws AddressException,
            MessagingException
    {
        String toEmails = otherEmailAddress;
        String emailSubject = "EasyBlog's Email";
        String emailBody = authURL;

        mailSession = Session.getDefaultInstance(emailProperties, null);
        emailMessage = new MimeMessage(mailSession);

            emailMessage.addRecipient(Message.RecipientType.TO,
                    new InternetAddress(toEmails));

        emailMessage.setSubject(emailSubject);
        emailMessage.setContent(emailBody, "text/html");// for a html email
        // emailMessage.setText(emailBody);// for a text email

    }

    public void sendEmail() throws AddressException, MessagingException
    {

        String emailHost = "smtp.gmail.com";
        String fromUser = "hannahchen1994123";// just the id alone without
        // @gmail.com
        String fromUserEmailPassword = "941028CHen";

        Transport transport = mailSession.getTransport("smtp");

        transport.connect(emailHost, fromUser, fromUserEmailPassword);
        transport.sendMessage(emailMessage, emailMessage.getAllRecipients());
        transport.close();
        System.out.println("Email sent successfully.");
    }

}
