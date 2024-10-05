package com.heredi.nowait.application.email.service.interfaces;

import com.heredi.nowait.application.email.dto.EmailDTO;
import jakarta.mail.MessagingException;
import org.springframework.stereotype.Service;

@Service
public interface MailSenderService {
    public void sendNewMail(EmailDTO emailDTO) throws MessagingException;
}
