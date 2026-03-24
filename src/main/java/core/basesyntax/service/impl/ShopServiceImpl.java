package core.basesyntax.service.impl;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.Operation;
import core.basesyntax.service.ShopService;
import core.basesyntax.strategy.OperationHandler;
import java.util.List;
import java.util.Map;

public class ShopServiceImpl implements ShopService {
    private final Map<Operation, OperationHandler> handlers;

    // Zauważ: nie wstrzykujemy już tutaj Storage!
    public ShopServiceImpl(Map<Operation, OperationHandler> handlers) {
        this.handlers = handlers;
    }

    @Override
    public Map<String, Integer> process(List<FruitTransaction> transactions) {
        Map<String, Integer> resultMap = new java.util.HashMap<>();

        for (FruitTransaction transaction : transactions) {
            OperationHandler handler = handlers.get(transaction.getOperation());
            int currentQuantity = resultMap.getOrDefault(transaction.getFruitName(), 0);
            int newQuantity = handler.apply(currentQuantity, transaction.getQuantity());
            resultMap.put(transaction.getFruitName(), newQuantity);
        }
        return resultMap;
    }
}
