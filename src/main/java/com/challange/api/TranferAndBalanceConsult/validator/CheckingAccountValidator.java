package com.challange.api.TranferAndBalanceConsult.validator;

import com.challange.api.TranferAndBalanceConsult.enuns.DailyLimit;
import com.challange.api.TranferAndBalanceConsult.model.dto.RequestCheckingAccountTransferDTO;
import com.challange.api.TranferAndBalanceConsult.model.entity.CheckingAccountTranferEntity;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

@Component
public class CheckingAccountValidator {

    public void transferAndReceiverAccountValidations(Optional<CheckingAccountTranferEntity> transferEntity, Optional<CheckingAccountTranferEntity> receiveEntity, RequestCheckingAccountTransferDTO requestDTO) throws Exception {
        if (transferEntity.isEmpty() || receiveEntity.isEmpty()) {
            throw new Exception();
        }
        String today = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
        if (!today.equals(transferEntity.get().getDate())) {
            transferEntity.get().setDailyLimitUsed(0.00);
            transferEntity.get().setDate(LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")));
        }
        if (!transferEntity.get().getIsActive()) {
            throw new Exception();      //tratar esse erro depois
        }
        double transferAmount = requestDTO.getTransferAmount();
        double amountAvailable = transferEntity.get().getBalance();
        int compareAmountAvailable = Double.compare(transferAmount, amountAvailable);
        if (compareAmountAvailable > 0) {
            throw new Exception();      //tratar esse erro depois
        }
        double resultBalance = amountAvailable - transferAmount;

        double limitUsed = transferEntity.get().getDailyLimitUsed();
        limitUsed = limitUsed + transferAmount;
        double maxAmountDaily = DailyLimit.MAX_VALUE.toDouble();
        int compareLimitDaily = Double.compare(limitUsed, maxAmountDaily);
        if (compareLimitDaily > 0) {
            throw new Exception();      //tratar esse erro depois
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
