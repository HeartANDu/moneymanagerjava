package com.example.moneymanager.accounting;

public enum TransactionAction {
    ADD (false),
    SUB (false),
    TRANSFER (true);

    private final boolean automatic;

    TransactionAction(boolean automatic) {
        this.automatic = automatic;
    }

    public boolean isAutomatic() {
        return automatic;
    }
}
