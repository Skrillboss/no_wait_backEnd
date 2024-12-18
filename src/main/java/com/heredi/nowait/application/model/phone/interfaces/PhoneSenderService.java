package com.heredi.nowait.application.model.phone.interfaces;

import org.springframework.stereotype.Service;

@Service
public interface PhoneSenderService {
    public void sendMessage(String phoneClient, String message) ;
}
