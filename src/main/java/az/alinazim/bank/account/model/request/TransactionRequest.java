package az.alinazim.bank.account.model.request;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class TransactionRequest {

    private Long fromAccountId;
    private Long toAccountId;
    private BigDecimal amount;
}
