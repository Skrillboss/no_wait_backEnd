package com.heredi.nowait.application.model.paymentInfo.service.implementations;

import com.heredi.nowait.application.model.paymentInfo.dto.in.PaymentInfoRequestDTO;
import com.heredi.nowait.application.model.paymentInfo.dto.out.PaymentInfoResponseDTO;
import com.heredi.nowait.application.model.paymentInfo.mapper.PaymentInfoMapper;
import com.heredi.nowait.application.model.paymentInfo.service.interfaces.PaymentInfoService;
import com.heredi.nowait.domain.paymentInfo.model.PaymentInfo;
import com.heredi.nowait.domain.paymentInfo.port.PaymentInfoRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class PaymentInfoServiceImpl implements PaymentInfoService {

    private final PaymentInfoRepository paymentInfoRepository;

    private final PaymentInfoMapper paymentInfoMapper;

    PaymentInfoServiceImpl(PaymentInfoRepository paymentInfoRepository, PaymentInfoMapper paymentInfoMapper){
        this.paymentInfoRepository = paymentInfoRepository;
        this.paymentInfoMapper = paymentInfoMapper;
    }

    @Override
    public List<PaymentInfoResponseDTO> getPaymentInfo(Long userId) {
        List<PaymentInfo> paymentInfoList = paymentInfoRepository.getPaymentInfoByUserId(userId);
        return paymentInfoMapper.toPaymentInfoListResponseDTO(paymentInfoList);
    }

    @Override
    public PaymentInfoResponseDTO updatePaymentInfo(String paymentInfoId, PaymentInfoRequestDTO paymentInfoRequestDTO, Long userId) {
        return null;
    }
}
