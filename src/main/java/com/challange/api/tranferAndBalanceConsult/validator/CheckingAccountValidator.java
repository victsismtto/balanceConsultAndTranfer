package com.challange.api.tranferAndBalanceConsult.validator;

import com.challange.api.tranferAndBalanceConsult.utils.MessageUtils;
import com.challange.api.tranferAndBalanceConsult.enuns.DailyLimit;
import com.challange.api.tranferAndBalanceConsult.exception.BusinessException;
import com.challange.api.tranferAndBalanceConsult.model.dto.RequestCheckingAccountDTO;
import com.challange.api.tranferAndBalanceConsult.model.entity.AccountsEntity;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Component
public class CheckingAccountValidator {

    public void transferAndReceiverAccountValidations(AccountsEntity transferEntity, AccountsEntity receiveEntity, RequestCheckingAccountDTO requestDTO) {
        String today = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
        checkIssuerNumber(requestDTO, transferEntity);
        dateVerification(today, transferEntity);
        accountIsActive(transferEntity, receiveEntity);
        availableAmountAndDailyLimit(requestDTO, transferEntity, receiveEntity);
        dateVerification(today, receiveEntity);
    }

    private void checkIssuerNumber(RequestCheckingAccountDTO requestDTO, AccountsEntity transferEntity) {
        if (!requestDTO.getCheckingAccountFrom().getIssuer().equals(transferEntity.getIssuer())
                || !requestDTO.getCheckingAccountFrom().getNumber().equals(transferEntity.getNumber())) {
            throw new BusinessException(MessageUtils.WRONG_ISSUER_OR_NUMBER);
        }
    }
    private void dateVerification(String today, AccountsEntity entity) {
        if (!today.equals(entity.getDate())) {
            entity.setDailyLimitUsed(0.00);
            entity.setDate(LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")));
        }
    }
    private void accountIsActive(AccountsEntity transferEntity, AccountsEntity receiveEntity) {
        if (!transferEntity.getIsActive() || !receiveEntity.getIsActive()) {
            throw new BusinessException(MessageUtils.NO_ACTIVE);
        }
    }
    private void availableAmountAndDailyLimit(RequestCheckingAccountDTO requestDTO, AccountsEntity transferEntity, AccountsEntity receiveEntity) {
        double transferAmount = requestDTO.getTransferAmount();
        double amountAvailable = transferEntity.getBalance();
        if (Double.compare(transferAmount, amountAvailable) > 0) {
            throw new BusinessException(MessageUtils.NO_AVAILABLE_BALANCE);
        }

        double resultBalance = amountAvailable - transferAmount;
        double limitUsed = transferEntity.getDailyLimitUsed();
        limitUsed = limitUsed + transferAmount;
        if (Double.compare(limitUsed, DailyLimit.MAX_VALUE.toDouble()) > 0) {
            throw new BusinessException(MessageUtils.DAILY_AMOUNT_ALREADY_USED);
        }

        receiveEntity.setBalance(receiveEntity.getBalance() + transferAmount);
        transferEntity.setDailyLimitUsed(limitUsed);
        transferEntity.setBalance(resultBalance);

    }
}
