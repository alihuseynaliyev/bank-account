package az.alinazim.bank.account.controller;

import az.alinazim.bank.account.model.request.AccountRequest;
import az.alinazim.bank.account.model.request.TransactionRequest;
import az.alinazim.bank.account.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequestMapping("/api/v1/accounts")
@RequiredArgsConstructor
public class AccountController {
    private final AccountService accountService;

    @PostMapping
    @ResponseStatus(CREATED)
    public void createAccount(@RequestBody AccountRequest accountRequest) {
        accountService.createAccount(accountRequest);
    }

    @PostMapping("/a2a")
    public void transferMoney(@RequestBody TransactionRequest transactionRequest) {
        accountService.transferMoney(transactionRequest);
    }

    @PostMapping("/a2a/batch")
    public void processBatchTransactions(@RequestBody List<TransactionRequest> transactionRequestList){
        accountService.processBatchTransactions(transactionRequestList);
    }
}
