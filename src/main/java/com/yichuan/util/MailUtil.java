package com.yichuan.util;

import com.sun.mail.util.MailSSLSocketFactory;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class MailUtil implements Runnable
{
   private String email; //收件人邮箱
   private String code; //激活码

    public MailUtil(String email, String code) {
        this.email = email;
        this.code = code;
    }
    @Override
    public void run()
    {
        //1.创建连接对象Javax.mail.session
        //2.创建message
        String from="823852831@qq.com";
        String host="smtp.qq.com";
        Properties properties=System.getProperties();

        properties.setProperty("mail.smtp.host",host);
        properties.setProperty("mail.smtp.auth","true");
        try{
            MailSSLSocketFactory sf=new MailSSLSocketFactory();
            sf.setTrustAllHosts(true);
            properties.put("mail.smtp.ssl.enable", "true");
            properties.put("mail.smtp.ssl.socketFactory", sf);
            properties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
            Session session=Session.getDefaultInstance(properties, new Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication("823852831@qq.com","hlumkonhwvqibfgh");
                }

            });
            // 2.创建邮件对象
            Message message = new MimeMessage(session);
            // 2.1设置发件人
            message.setFrom(new InternetAddress(from));
            // 2.2设置接收人
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(email));
            // 2.3设置邮件主题
            message.setSubject("测试测试");
            // 2.4设置邮件内容
            /**String content = "<html><head></head><body><h1>这是一封激活邮件,激活请点击以下链接</h1><h3><a href='http://localhost:8080/RegisterDemo/ActiveServlet?code="
                    + code + "'>http://localhost:8080/RegisterDemo/ActiveServlet?code=" + code
                    + "</href></h3></body></html>";
             **/
            String content="无语！！！！！！"+code;
            message.setContent(content, "text/html;charset=UTF-8");
            // 3.发送邮件
            try {
                Transport.send(message);
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }

            System.out.println("邮件成功发送!");
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

}
