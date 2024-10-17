package com.heredi.nowait.application.model.business.service.interfaces;

import com.google.zxing.WriterException;
import com.heredi.nowait.application.model.business.dto.out.AddItemResponseDTO;
import com.heredi.nowait.application.model.item.dto.in.ItemRequestDTO;
import jakarta.mail.MessagingException;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public interface BusinessService {
    AddItemResponseDTO addItem(String businessId, ItemRequestDTO itemRequestDTO) throws IOException, WriterException;
    boolean saveItemIdToMail(String businessId, String itemId) throws IOException, WriterException, MessagingException;
}
