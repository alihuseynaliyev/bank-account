package az.alinazim.bank.account.mapper;

import az.alinazim.bank.account.dao.entity.AccountEntity;
import az.alinazim.bank.account.model.request.AccountRequest;

public enum AccountMapper {
    ACCOUNT_MAPPER;

    public AccountEntity buildAccountEntity(AccountRequest accountRequest) {
        var accountEntity = new AccountEntity();
        accountEntity.setAccountNumber(accountRequest.getAccountNumber());
        accountEntity.setBalance(accountRequest.getBalance());
        return accountEntity;
    }
}
