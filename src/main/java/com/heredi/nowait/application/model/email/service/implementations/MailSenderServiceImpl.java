package com.heredi.nowait.application.model.email.service.implementations;

import com.heredi.nowait.application.exception.AppErrorCode;
import com.heredi.nowait.application.exception.AppException;
import com.heredi.nowait.application.model.email.service.interfaces.MailSenderService;
import com.heredi.nowait.application.model.email.dto.EmailDTO;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@Service
public class MailSenderServiceImpl implements MailSenderService {

    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    private final TemplateEngine templateEngine;

    public MailSenderServiceImpl(TemplateEngine templateEngine) {
        this.templateEngine = templateEngine;
    }

    @Override
    public void sendNewMail(String template, EmailDTO emailDTO) throws MessagingException {
        try{
            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            helper.setTo(emailDTO.getAddressee());
            helper.setSubject(emailDTO.getAffair());
            helper.addAttachment(emailDTO.getAttachmentFileName(), emailDTO.getFileImage());
            Context context = new Context();
            context.setVariable("message", emailDTO.getMessage());
            context.setVariable("nickName", emailDTO.getNickName());
            context.setVariable("email", emailDTO.getAddressee());
            String contentHTML = templateEngine.process(template, context);

            helper.setText(contentHTML, true);
            javaMailSender.send(message);

        }catch (Exception e){
             throw new AppException(
                    AppErrorCode.EMAIL_SENDING_FAILED,
                    "sendNewMail",
                    "Exception: " + e + " message: " + e.getMessage(),
                    HttpStatus. INTERNAL_SERVER_ERROR
            );
        }
    }
}