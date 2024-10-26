package com.heredi.nowait.application.model.business.service.implementations;

import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import com.heredi.nowait.application.model.business.dto.out.AddItemResponseDTO;
import com.heredi.nowait.application.model.business.mapper.BusinessMapper;
import com.heredi.nowait.application.model.business.service.interfaces.BusinessService;
import com.heredi.nowait.application.model.item.dto.in.ItemRequestDTO;
import com.heredi.nowait.application.model.item.dto.out.ItemResponseDTO;
import com.heredi.nowait.application.model.item.mapper.ItemMapper;
import com.heredi.nowait.application.model.email.service.interfaces.MailSenderService;
import com.heredi.nowait.application.model.email.dto.EmailDTO;
import com.heredi.nowait.application.model.item.service.interfaces.ItemService;
import com.heredi.nowait.domain.business.model.Business;
import com.heredi.nowait.domain.business.port.BusinessRepository;
import com.heredi.nowait.domain.item.model.Item;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import static com.heredi.nowait.application.utility.QrGeneratorService.createQR;

@Service
public class BusinessServiceImpl implements BusinessService {

    private final BusinessRepository businessRepository;
    @Autowired
    private final BusinessMapper businessMapper;
    @Autowired
    private final ItemMapper itemMapper;
    @Autowired
    private MailSenderService mailSenderService;
    @Autowired
    private ItemService itemService;

    public BusinessServiceImpl(BusinessRepository businessRepository, BusinessMapper businessMapper, ItemMapper itemMapper) {
        this.businessRepository = businessRepository;
        this.businessMapper = businessMapper;
        this.itemMapper = itemMapper;
    }

    @Transactional
    @Override
    public boolean saveItemIdToMail(String businessId, String itemId) throws IOException, WriterException, MessagingException {
        try{
            Business business = businessRepository.getBusiness(Long.parseLong(businessId));
            ItemResponseDTO item = this.itemService.get(itemId);

            String path = "itemId.png";
            String charset = "UTF-8";
            Map<EncodeHintType, ErrorCorrectionLevel> hashMap
                    = new HashMap<EncodeHintType,
                    ErrorCorrectionLevel>();

            hashMap.put(EncodeHintType.ERROR_CORRECTION,
                    ErrorCorrectionLevel.L);

            createQR(item.getId(), path, charset, hashMap, 200, 200);
            File file = new File(path);
            EmailDTO emailDTO = new EmailDTO(
                    business.getEmail(),
                    item.getName(),
                    item.getDescription(),
                    item.getId(),
                    file
            );
            mailSenderService.sendNewMail(emailDTO);
            return true;
        }catch (Exception e){
            throw new RuntimeException("Error sending the email: " + e.toString(), e);
        }
    }
}
