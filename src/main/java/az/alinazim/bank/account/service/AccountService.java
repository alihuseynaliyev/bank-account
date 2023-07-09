package az.alinazim.bank.account.service;

import az.alinazim.bank.account.dao.repository.AccountRepository;
import az.alinazim.bank.account.model.request.AccountRequest;
import az.alinazim.bank.account.model.request.TransactionRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;

import static az.alinazim.bank.account.mapper.AccountMapper.ACCOUNT_MAPPER;

@Service
@RequiredArgsConstructor
public class AccountService {
    private final AccountRepository accountRepository;

    @Transactional
    public void transferMoney(TransactionRequest transactionRequest) {
        var amount = transactionRequest.getAmount();
        var fromAccount = accountRepository
                .findById(transactionRequest.getFromAccountId())
                .orElseThrow(() -> new RuntimeException("Account not found"));

        var toAccount = accountRepository
                .findById(transactionRequest.getToAccountId())
                .orElseThrow(() -> new RuntimeException("Account not found"));

        if (fromAccount.getBalance().compareTo(amount) < 0)
            throw new RuntimeException("Insufficient balance");

        fromAccount.setBalance(fromAccount.getBalance().subtract(amount));
        toAccount.setBalance(toAccount.getBalance().add(amount));
        accountRepository.saveAll(Arrays.asList(fromAccount, toAccount));
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
    public void processBatchTransactions(List<TransactionRequest> transactionRequestList) {
        try {
            transactionRequestList.forEach(this::transferMoney);
        } catch (Exception exception) {
            System.out.println("Exception occurred: " + exception);
        }
    }

    public void createAccount(AccountRequest accountRequest) {
        accountRepository.save(ACCOUNT_MAPPER.buildAccountEntity(accountRequest));
    }

}

