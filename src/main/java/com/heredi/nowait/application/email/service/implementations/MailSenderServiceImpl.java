package com.heredi.nowait.application.email.service.implementations;

import com.heredi.nowait.application.email.service.interfaces.MailSenderService;
import com.heredi.nowait.application.email.dto.EmailDTO;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
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

    public MailSenderServiceImpl(JavaMailSender javaMailSender, TemplateEngine templateEngine) {
        this.templateEngine = templateEngine;
    }

    @Override
    public void sendNewMail(EmailDTO emailDTO) throws MessagingException {
        try{
            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            helper.setTo(emailDTO.getAddressee());
            helper.setSubject(emailDTO.getAffair());
            helper.addAttachment(emailDTO.getAttachmentFileName(), emailDTO.getFileImage());
            Context context = new Context();
            context.setVariable("message", emailDTO.getMessage());
            String contentHTML = templateEngine.process("email", context);

            helper.setText(contentHTML, true);
            javaMailSender.send(message);

        }catch (Exception e){
            throw  new RuntimeException("Error al enviar el correo: " + e.getMessage(), e);
        }
    }
}