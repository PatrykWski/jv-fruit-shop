package core.basesyntax.service.impl;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.Operation;
import core.basesyntax.repository.StorageInterface;
import core.basesyntax.strategy.OperationHandler;
import java.util.List;
import java.util.Map;

public class ShopServiceImpl implements core.basesyntax.service.ShopService {
    private final StorageInterface storage;
    private final Map<Operation, OperationHandler> handlers;

    public ShopServiceImpl(StorageInterface storage, Map<Operation, OperationHandler> handlers) {
        this.storage = storage;
        this.handlers = handlers;
    }

    @Override
    public void process(List<FruitTransaction> transactions) {
        for (FruitTransaction transaction : transactions) {
            OperationHandler handler = handlers.get(transaction.getOperation());

            if (handler == null) {
                throw new RuntimeException(
                        "Nie znaleziono handlera dla: " + transaction.getOperation());
            }

            int currentQuantity = storage.getQuantity(transaction.getFruitName());
            int newQuantity = handler.apply(currentQuantity, transaction.getQuantity());

            storage.put(transaction.getFruitName(), newQuantity);
        }
    }
}
