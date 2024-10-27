package com.heredi.nowait.application.model.item.service.interfaces;

import com.google.zxing.WriterException;
import com.heredi.nowait.application.model.item.dto.in.ItemRequestDTO;
import com.heredi.nowait.application.model.item.dto.out.ItemResponseDTO;
import jakarta.mail.MessagingException;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public interface ItemService {
    ItemResponseDTO create(Long userId, ItemRequestDTO itemRequestDTO);
    void saveItemIdQrToMail(Long userId, String itemId) throws IOException, WriterException, MessagingException;
    ItemResponseDTO get(String itemId);
}
