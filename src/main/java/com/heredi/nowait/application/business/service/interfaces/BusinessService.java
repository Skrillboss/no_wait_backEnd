package com.heredi.nowait.application.business.service.interfaces;

import com.google.zxing.WriterException;
import com.heredi.nowait.application.business.dto.in.AddItemRequestDTO;
import com.heredi.nowait.application.business.dto.in.SaveItemIdToMailDTO;
import com.heredi.nowait.application.business.dto.out.AddItemResponseDTO;
import com.heredi.nowait.application.business.dto.out.BusinessResponseDTO;
import com.heredi.nowait.application.business.dto.in.CreateBusinessRequestDTO;
import jakarta.mail.MessagingException;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public interface BusinessService {
    AddItemResponseDTO addItem(AddItemRequestDTO addItemRequestDTO) throws IOException, WriterException;
    boolean saveItemIdToMail(SaveItemIdToMailDTO saveItemIdToMailDTO) throws IOException, WriterException, MessagingException;
}
