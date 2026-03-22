package core.basesyntax.service;

import core.basesyntax.repository.StorageInterface;

public class FruitExistValidator {
    private final StorageInterface storage;

    public FruitExistValidator(StorageInterface storage) {
        this.storage = storage;
    }

    public int getCurrentQuantity(String fruitName) {
        Integer quantity = storage.getQuantity(fruitName);
        return (quantity != null) ? quantity : 0;
    }
}
