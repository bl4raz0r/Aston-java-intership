import lombok.Getter;

@Getter
public class BankAccount {
    private final String bankAccountID;
    private final double bankAccountBalance;

    public BankAccount(String bankAccountID, double bankAccountBalance) {
        this.bankAccountID = bankAccountID;
        this.bankAccountBalance = bankAccountBalance;
    }

    @Override
    public String toString() {
        return  "Идентификационный номер банковского счета: " + bankAccountID + '\n' +
                "Баланс банковского счета: " + bankAccountBalance + " руб.";
    }
}