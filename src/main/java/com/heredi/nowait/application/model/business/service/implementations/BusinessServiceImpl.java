package com.heredi.nowait.application.model.business.service.implementations;

import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import com.heredi.nowait.application.model.business.dto.in.BusinessRequestDTO;
import com.heredi.nowait.application.model.business.dto.out.BusinessResponseDTO;
import com.heredi.nowait.application.model.business.mapper.BusinessMapper;
import com.heredi.nowait.application.model.business.service.interfaces.BusinessService;
import com.heredi.nowait.application.model.item.dto.out.ItemResponseDTO;
import com.heredi.nowait.application.model.item.mapper.ItemMapper;
import com.heredi.nowait.application.model.email.service.interfaces.MailSenderService;
import com.heredi.nowait.application.model.email.dto.EmailDTO;
import com.heredi.nowait.application.model.item.service.interfaces.ItemService;
import com.heredi.nowait.domain.business.model.Business;
import com.heredi.nowait.domain.business.port.BusinessRepository;
import com.heredi.nowait.domain.user.model.Users;
import com.heredi.nowait.domain.user.port.UserRepository;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static com.heredi.nowait.application.utility.QrGeneratorService.createQR;

@Service
public class BusinessServiceImpl implements BusinessService {

    private final BusinessRepository businessRepository;

    private final UserRepository userRepository;
    @Autowired
    private final BusinessMapper businessMapper;

    public BusinessServiceImpl(BusinessRepository businessRepository, UserRepository userRepository, BusinessMapper businessMapper) {
        this.businessRepository = businessRepository;
        this.userRepository = userRepository;
        this.businessMapper = businessMapper;
    }

    @Override
    public BusinessResponseDTO getBusiness(String businessId) {
        Business business = this.businessRepository.getBusiness(Long.parseLong(businessId));
        return this.businessMapper.toBusinessDTO(business);
    }

    @Override
    public BusinessResponseDTO updateBusiness(String businessId, BusinessRequestDTO businessRequestDTO, Long userId) {
        Business business = this.businessMapper.toBusiness(businessRequestDTO);
        business.setId(Long.parseLong(businessId));
        return this.businessMapper.toBusinessDTO(this.businessRepository.updateBusiness(userId, business));
    }
}
