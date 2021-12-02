package com.tujia.myssm.common.utils;

import java.util.Set;
import org.apache.commons.lang3.time.StopWatch;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.google.common.collect.Sets;
import com.tujia.framework.utility.PropertyUtil;

/**
 *
 * @author: songlinl
 * @create: 2021/11/22 18:29
 */
public class EmailUtil {
    /**
     * 预警的邮件账号
     */
    public static final String ALARM_EMAIL_FROM = PropertyUtil.getProperty("alarm.email.from", "yaohuaz@tujia.com");
    /**
     * 预警的邮件账号
     */
    public static final String ALARM_EMAIL_ACCOUNT = PropertyUtil.getProperty("alarm.email.account", "yaohuaz");
    /**
     * 预警的邮件密码
     */
    public static final String ALARM_EMAIL_PASSWD = PropertyUtil.getProperty("alarm.email.passwd", "");
    /**
     * 预警的邮件smtp
     */
    private static final String TUJIA_EMAIL_SMTP = PropertyUtil.getProperty("tujia.email.smtp", "mta.tujia.com");
    private static Logger logger = LoggerFactory.getLogger(EmailUtil.class);

    public static Boolean sendEmail(String emailMtaHost, String mailUserName, String mailUserPass, String mailFrom,
                                    String subject, String content, Set<String> emailList) {
        HtmlEmail email = new HtmlEmail();
        email.setHostName(emailMtaHost);
        email.setSmtpPort(25);
        email.setAuthentication(mailUserName, mailUserPass);
        email.setTLS(false);
        email.setSubject(subject);
        email.setCharset("UTF-8");
        try {
            StopWatch watch = new StopWatch();
            watch.start();
            email.addTo(emailList.toArray(new String[] {}));
            email.setFrom(mailFrom);
            email.setHtmlMsg(content);
            String msgId = email.send();
            logger.info("send mail to {} successfully, subject:{}, msgId:{}", email.getToAddresses(), subject, msgId);
            return Boolean.TRUE;
        } catch (EmailException e) {
            logger.error("send mail to {} exception, subject:{}", email.getToAddresses(), subject, e);
            return Boolean.FALSE;
        }
    }

    public static Boolean sendEmail(String mailUserName, String mailUserPass, String mailFrom, String subject,
                                    String content, Set<String> emailList) {
        return EmailUtil.sendEmail(TUJIA_EMAIL_SMTP, mailUserName, mailUserPass, mailFrom, subject, content, emailList);
    }

    public static void main(String[] args) {
        sendEmail(ALARM_EMAIL_ACCOUNT, ALARM_EMAIL_PASSWD, ALARM_EMAIL_FROM, "ssss", "hello",
                  Sets.newHashSet("songlinl"));
    }
}
