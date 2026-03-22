package core.basesyntax.service;

public class AddOperationHandler implements OperationHandler {
    @Override
    public int apply(int current, int transaction) {
        return current + transaction;
    }
}