package com.x.demo.function;

import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Date;
import java.util.Properties;

/**
 * @ClassName MailUtils
 * @Description java开发发送邮箱模块
 * @Author X
 * @Date 2022/7/8 14:49
 * @Version 1.0
 **/
public class MailUtils {

    /**创建发送人，需要邮箱的账号和密码 密码不是邮箱登陆的密码，是smtp协议的密码，是我们自己定义 */
    public static String myEmailAccount = "17600578201@163.com";
    public static String myEmailPassword = "493960766jiu";

    /**创建收件人 */
    public static String receiveMailAccount = "xuqiang9712@163.com";

    /**发送邮件要有服务器，连接网易的服务器，创建网易邮箱的服务器地址 */
    public static String myEmailSMTPHost = "smtp.163.com";

    public static void main(String[] args) throws Exception {
        //设置咱们连接服务器前提 配置连接服务器的参数
        Properties props = new Properties();
        //使用的一个协议，Javamail规范要求
        props.setProperty("mail.transport.protocol","smtp");
        //发件人的邮箱smtp服务器地址
        props.setProperty("mail.smtp.host",myEmailSMTPHost);
        //需要请求的认证
        props.setProperty("mail.smtp.auth","true");
        //创建一个会话对象，连接邮箱服务器
        Session session = Session.getInstance(props);
        //打印日志
        session.setDebug(true);
        //创建一封邮件 内容 主题，发送人是谁，保存，发送时间
        MimeMessage message = createMimeMessage(myEmailAccount,receiveMailAccount,session);
        //传输对象
        Transport transport = session.getTransport();
        //连接邮箱服务器
        transport.connect(myEmailAccount,myEmailPassword);
        // 发送邮件                     所有的发送人，包括密送人，抄送人
        transport.sendMessage(message, message.getAllRecipients());
        //关闭连接
        transport.close();
  }

  /** 创建邮件内容的方法
   * @param sendMail 发送人
   * @param receiveMail 收件人
   * @param session 会话对象
   * @return 邮件
   * @throws Exception 捕获异常
   */
  public static MimeMessage createMimeMessage(String sendMail, String receiveMail, Session session)throws Exception {
      //创建一封邮件
      MimeMessage message = new MimeMessage(session);
      //设置发件人
      message.setFrom(new InternetAddress(sendMail,"哈哈","UTF-8"));
      //设置收件人
      message.setRecipient(MimeMessage.RecipientType.TO,new InternetAddress(receiveMail,"嘿嘿","UTF-8"));
      //设置邮件正文
      message.setContent("测试邮件发送","text/html;charset=UTF-8");
      //设置发送的时间
      message.setSentDate(new Date());
      //保存邮件
      message.saveChanges();
      return message;
  }
}
