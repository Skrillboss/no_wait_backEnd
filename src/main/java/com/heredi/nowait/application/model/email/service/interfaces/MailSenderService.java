package com.heredi.nowait.application.model.email.service.interfaces;

import com.heredi.nowait.application.model.email.dto.EmailDTO;
import jakarta.mail.MessagingException;
import org.springframework.stereotype.Service;

@Service
public interface MailSenderService {
    public void sendNewMail(String template, EmailDTO emailDTO) throws MessagingException;
}
