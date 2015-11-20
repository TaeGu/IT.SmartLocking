package com.example.smartlocking;

import java.io.File;
import java.util.Date;
import java.util.Properties;
import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.MimeUtility;

import android.util.Log;

public class Mail extends javax.mail.Authenticator
{
	private String mailhost = "smtp.gmail.com";
	private String user;  
    private String password;
	private Session session;

	public Mail(String user, String password)
	{
		this.user = user;  
        this.password = password;
        
		Properties props = new Properties();  
        props.setProperty("mail.transport.protocol", "smtp");  
        props.setProperty("mail.host", mailhost);  
        props.put("mail.smtp.auth", "true");  
        props.put("mail.smtp.port", "465");  
        props.put("mail.smtp.socketFactory.port", "465");  
        props.put("mail.smtp.socketFactory.class",  
                "javax.net.ssl.SSLSocketFactory");  
        props.put("mail.smtp.socketFactory.fallback", "false");
        props.setProperty("mail.smtp.quitwait", "false");
        
        session = Session.getDefaultInstance(props, this);
	} // constructor
	
	protected PasswordAuthentication getPasswordAuthentication() {  
        return new PasswordAuthentication(user, password);  
    }
	
	public void sendMail(String subject, String body, String sender,
			String recipients)
	{
		try
		{
			Message msg = new MimeMessage(session);
			msg.setFrom(new InternetAddress(sender));
			msg.setSubject(subject);
			msg.setContent(body, "text/html;charset=EUC-KR");
			msg.setSentDate(new Date());
			msg.setRecipient(Message.RecipientType.TO, new InternetAddress(
					recipients));
			Transport.send(msg);
		} catch (Exception e)
		{
			Log.d("lastiverse", "Exception occured : ");
			Log.d("lastiverse", e.toString());
			Log.d("lastiverse", e.getMessage());
		} // try-catch
	} // vodi sendMail

	public void sendMailWithFile(String subject, String body, String sender,
			String recipients, String filePath, String fileName)
	{
		try
		{
			Message msg = new MimeMessage(session);
			msg.setFrom(new InternetAddress(sender));
			msg.setSubject(subject);
			msg.setContent(body, "text/html;charset=EUC-KR");
			msg.setSentDate(new Date());
			msg.setRecipient(Message.RecipientType.TO, new InternetAddress(
					recipients));

			MimeBodyPart attachPart = new MimeBodyPart();
			attachPart.setDataHandler(new DataHandler(new FileDataSource(
					new File(filePath))));
			Multipart multipart=new MimeMultipart();
			multipart.addBodyPart(attachPart);
			
			msg.setContent(multipart);
			attachPart.setFileName(fileName);
			
			Transport.send(msg);
			Log.i("lastiverse", "sent");
		} catch (Exception e)
		{
			Log.e("lastiverse", "Exception occured : ");
			Log.e("lastiverse", e.toString());
			Log.e("lastiverse", e.getMessage());
		} // try-catch
	} // void sendMailWithFile
} // class emailClient
