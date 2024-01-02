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

    public void transferAndReceiverAccountValidations(Optional<TransferAndBalanceConsultEntity> transferEntity, Optional<TransferAndBalanceConsultEntity> receiveEntity, RequestCheckingAccountTransferDTO requestDTO) throws Exception {

        if (!requestDTO.getCheckingAccountFrom().getIssuer().equals(transferEntity.get().getIssuer())
        || !requestDTO.getCheckingAccountFrom().getNumber().equals(transferEntity.get().getNumber())) {
            throw new BusinessException(MessageUtils.WRONG_ISSUER_OR_NUMBER);
        }

        String today = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
        if (!today.equals(transferEntity.get().getDate())) {
            transferEntity.get().setDailyLimitUsed(0.00);
            transferEntity.get().setDate(LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")));
        }
        if (!transferEntity.get().getIsActive() || !receiveEntity.get().getIsActive()) {
            throw new BusinessException(MessageUtils.NO_ACTIVE);
        }
        double transferAmount = requestDTO.getTransferAmount();
        double amountAvailable = transferEntity.get().getBalance();
        int compareAmountAvailable = Double.compare(transferAmount, amountAvailable);
        if (compareAmountAvailable > 0) {
            throw new BusinessException(MessageUtils.NO_AVAILABLE_BALANCE);
        }
        double resultBalance = amountAvailable - transferAmount;

        double limitUsed = transferEntity.get().getDailyLimitUsed();
        limitUsed = limitUsed + transferAmount;
        double maxAmountDaily = DailyLimit.MAX_VALUE.toDouble();
        int compareLimitDaily = Double.compare(limitUsed, maxAmountDaily);
        if (compareLimitDaily > 0) {
            throw new BusinessException(MessageUtils.DAILY_AMOUNT_ALREADY_USED);
        }
        transferEntity.get().setDailyLimitUsed(limitUsed);
        transferEntity.get().setBalance(resultBalance);

        double balanceReceived = receiveEntity.get().getBalance() + transferAmount;
        receiveEntity.get().setBalance(balanceReceived);
        if (!today.equals(receiveEntity.get().getDate())) {
            receiveEntity.get().setDailyLimitUsed(0.00);
            receiveEntity.get().setDate(LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")));
        }
    }
}
