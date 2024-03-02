package com.TransactionHub.service;

import com.TransactionHub.exception.TransferException;
import com.TransactionHub.model.BankAccount;
import com.TransactionHub.repository.BankAccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BankAccountService {

    private final BankAccountRepository bankAccountRepository;
    private static final BigDecimal INTEREST = new BigDecimal("1.05");
    private static final BigDecimal MAX_INTEREST = new BigDecimal("2.07");

    @Scheduled(fixedRate = 60000)
    @Async
    public void chargeInterest() {
        List<BankAccount> all = bankAccountRepository.findAll();
        all.forEach(bankAccount -> {
            BigDecimal multiply = bankAccount.getCurrentAmount().multiply(INTEREST);
            BigDecimal maxAmount = bankAccount.getInitialAmount().multiply(MAX_INTEREST);
            if(multiply.compareTo(maxAmount) <= 0) {
                bankAccount.setCurrentAmount(multiply);
            }
        });
        bankAccountRepository.saveAll(all);
    }

    public void transferMoney(BankAccount sender, Integer recipientId, BigDecimal amount) {
        BankAccount recipient = bankAccountRepository.findById(recipientId)
                .orElseThrow(() -> new TransferException("Номер счёта не найден"));
        
        BigDecimal senderAmount = sender.getCurrentAmount();
        if(senderAmount.compareTo(amount) >= 0) {
            recipient.setCurrentAmount(recipient.getCurrentAmount().add(amount));
            sender.setCurrentAmount(senderAmount.subtract(amount));
            bankAccountRepository.save(sender);
            bankAccountRepository.save(recipient);
        } else {
            throw new TransferException("Недостаточно средств");
        }
    }

}
