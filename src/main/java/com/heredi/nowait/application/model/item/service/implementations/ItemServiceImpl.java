package com.heredi.nowait.application.model.item.service.implementations;

import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import com.heredi.nowait.application.exception.AppErrorCode;
import com.heredi.nowait.application.exception.AppException;
import com.heredi.nowait.application.model.email.dto.EmailDTO;
import com.heredi.nowait.application.model.email.service.interfaces.MailSenderService;
import com.heredi.nowait.application.model.item.dto.in.ItemRequestDTO;
import com.heredi.nowait.application.model.item.dto.out.ItemResponseDTO;
import com.heredi.nowait.application.model.item.mapper.ItemMapper;
import com.heredi.nowait.application.model.item.service.interfaces.ItemService;
import com.heredi.nowait.domain.item.model.Item;
import com.heredi.nowait.domain.item.port.ItemRepository;
import com.heredi.nowait.domain.user.model.Users;
import com.heredi.nowait.domain.user.port.UserRepository;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static com.heredi.nowait.application.utility.QrGeneratorService.createQR;

@Service
public class ItemServiceImpl implements ItemService {

    private final ItemRepository itemRepository;

    private final UserRepository userRepository;

    @Autowired
    private MailSenderService mailSenderService;

    @Autowired
    private final ItemMapper itemMapper;

    public ItemServiceImpl(ItemRepository itemRepository, UserRepository userRepository, ItemMapper itemMapper){
        this.itemRepository = itemRepository;
        this.userRepository = userRepository;
        this.itemMapper = itemMapper;
    }

    @Transactional
    @Override
    public ItemResponseDTO create(Long userId, ItemRequestDTO itemRequestDTO) {
        Long businessId = this.userRepository.getUserById(userId).getBusiness().getId();
        Item item = itemRepository.create(businessId, itemMapper.toItem(itemRequestDTO));

        return itemMapper.toItemResponseDTO(item);
    }

    @Transactional
    @Override
    public void saveItemIdQrToMail(Long userId, String itemId) throws IOException, WriterException, MessagingException {
        Users user = this.userRepository.getUserById(userId);
        Item item = this.itemRepository.getItemById(Long.parseLong(itemId));

        boolean userHasItem = user.getBusiness().getId().equals(
                item.getBusiness().getId()
        );

        if(!userHasItem){
            throw new AppException(
                    AppErrorCode.ITEM_NOT_FOUND_IN_BUSINESS,
                    "saveItemIdQrToMail",
                    "ItemId: " + itemId + " BusinessId: " + user.getBusiness().getId(),
                    HttpStatus.FORBIDDEN
            );
        }

        try {

            String path = "itemId.png";
            String charset = "UTF-8";
            Map<EncodeHintType, ErrorCorrectionLevel> hashMap = new HashMap<>();

            hashMap.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.L);

            createQR(itemId, path, charset, hashMap, 200, 200);
            File file = new File(path);
            EmailDTO emailDTO = new EmailDTO(
                    user.getBusiness().getEmail(),
                    "NoWait: Id " + item.getName(),
                    "Item description: " + item.getDescription() +"\n \nEste es el QR generado, te recomendamos " +
                            "Imprimirlo y colocarlo en el lugar donde proporcionaras el producto o servicio",
                    user.getNickName(),
                    itemId,
                    file
            );
            mailSenderService.sendNewMail("qr", emailDTO);
        } catch (Exception e) {
            throw new AppException(
                    AppErrorCode.EMAIL_SENDING_FAILED,
                    "saveItemIdQrToMail",
                    "Exception: " + e + " message: " + e.getMessage(),
                    HttpStatus. INTERNAL_SERVER_ERROR
            );
        }
    }

    @Transactional
    @Override
    public ItemResponseDTO get(String itemId) {
        Item obteinedItem = itemRepository.getItemById(Long.parseLong(itemId));
        return itemMapper.toItemResponseDTO(obteinedItem);
    }
}
