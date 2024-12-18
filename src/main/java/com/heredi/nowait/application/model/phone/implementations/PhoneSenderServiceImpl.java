package com.heredi.nowait.application.model.phone.implementations;

import com.heredi.nowait.application.model.phone.interfaces.PhoneSenderService;
import com.heredi.nowait.infrastructure.config.phone.PhoneConfiguration;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PhoneSenderServiceImpl implements PhoneSenderService {

    private final PhoneConfiguration phoneConfiguration;

    @Autowired
    public PhoneSenderServiceImpl(PhoneConfiguration phoneConfiguration){
        this.phoneConfiguration = phoneConfiguration;
    }

    @Override
    public void sendMessage(String phoneClient, String message) {
        Twilio.init(phoneConfiguration.getACCOUNT_SID(), phoneConfiguration.getAUTH_TOKEN());

        Message sms = Message.creator(
                new PhoneNumber(phoneClient),
                new PhoneNumber(phoneConfiguration.getPhoneHost()),
                message
        ).create();
        System.out.println("Message sent with SID: " + sms.getSid());
    }
}
