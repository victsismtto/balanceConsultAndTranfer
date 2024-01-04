package com.challange.api.tranferAndBalanceConsult.validator;

import com.challange.api.tranferAndBalanceConsult.utils.MessageUtils;
import com.challange.api.tranferAndBalanceConsult.enuns.DailyLimit;
import com.challange.api.tranferAndBalanceConsult.exception.BusinessException;
import com.challange.api.tranferAndBalanceConsult.model.dto.RequestCheckingAccountTransferDTO;
import com.challange.api.tranferAndBalanceConsult.model.entity.TransferAndBalanceConsultEntity;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

@Component
public class CheckingAccountValidator {

    public void transferAndReceiverAccountValidations(TransferAndBalanceConsultEntity transferEntity, TransferAndBalanceConsultEntity receiveEntity, RequestCheckingAccountTransferDTO requestDTO) throws Exception {
        String today = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
        checkIssuerNumber(requestDTO, transferEntity);
        dateVerification(today, transferEntity);
        accountIsActive(transferEntity, receiveEntity);
        availableAmountAndDailyLimit(requestDTO, transferEntity, receiveEntity);
        dateVerification(today, receiveEntity);
    }

    private void checkIssuerNumber(RequestCheckingAccountTransferDTO requestDTO, TransferAndBalanceConsultEntity transferEntity) {
        if (!requestDTO.getCheckingAccountFrom().getIssuer().equals(transferEntity.getIssuer())
                || !requestDTO.getCheckingAccountFrom().getNumber().equals(transferEntity.getNumber())) {
            throw new BusinessException(MessageUtils.WRONG_ISSUER_OR_NUMBER);
        }
    }
    private void dateVerification(String today, TransferAndBalanceConsultEntity entity) {
        if (!today.equals(entity.getDate())) {
            entity.setDailyLimitUsed(0.00);
            entity.setDate(LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")));
        }
    }
    private void accountIsActive(TransferAndBalanceConsultEntity transferEntity, TransferAndBalanceConsultEntity receiveEntity) {
        if (!transferEntity.getIsActive() || !receiveEntity.getIsActive()) {
            throw new BusinessException(MessageUtils.NO_ACTIVE);
        }
    }
    private void availableAmountAndDailyLimit(RequestCheckingAccountTransferDTO requestDTO, TransferAndBalanceConsultEntity transferEntity, TransferAndBalanceConsultEntity receiveEntity) {
        double transferAmount = requestDTO.getTransferAmount();
        double amountAvailable = transferEntity.getBalance();
        int compareAmountAvailable = Double.compare(transferAmount, amountAvailable);
        if (compareAmountAvailable > 0) {
            throw new BusinessException(MessageUtils.NO_AVAILABLE_BALANCE);
        }
        double resultBalance = amountAvailable - transferAmount;

        double limitUsed = transferEntity.getDailyLimitUsed();
        limitUsed = limitUsed + transferAmount;
        double maxAmountDaily = DailyLimit.MAX_VALUE.toDouble();
        int compareLimitDaily = Double.compare(limitUsed, maxAmountDaily);
        if (compareLimitDaily > 0) {
            throw new BusinessException(MessageUtils.DAILY_AMOUNT_ALREADY_USED);
        }
        transferEntity.setDailyLimitUsed(limitUsed);
        transferEntity.setBalance(resultBalance);

        double balanceReceived = receiveEntity.getBalance() + transferAmount;
        receiveEntity.setBalance(balanceReceived);
    }
}
