package core.basesyntax.app;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.Operation;
import core.basesyntax.repository.FileService;
import core.basesyntax.repository.FruitParser;
import core.basesyntax.repository.StorageInterface;
import core.basesyntax.db.Storage;
import core.basesyntax.service.impl.FileServiceImpl;
import core.basesyntax.strategy.AddOperationHandler;
import core.basesyntax.strategy.BalanceHandler;
import core.basesyntax.service.impl.ShopServiceImpl;
import core.basesyntax.service.ShopService;
import core.basesyntax.strategy.OperationHandler;
import core.basesyntax.strategy.PurchaseHandler;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        FruitParser fruitParser = new FruitParser();
        FileService fileService = new FileServiceImpl(fruitParser);
        StorageInterface storage = new Storage();

        OperationHandler addHandler = new AddOperationHandler();
        OperationHandler purchaseHandler = new PurchaseHandler();

        Map<Operation, OperationHandler> operationHandlers = new HashMap<>();
        operationHandlers.put(Operation.b, new BalanceHandler());
        operationHandlers.put(Operation.s, addHandler);
        operationHandlers.put(Operation.r, addHandler);
        operationHandlers.put(Operation.p, purchaseHandler);

        List<FruitTransaction> transactions = fileService.loadFromFile("input.csv");

        ShopService shopService = new ShopServiceImpl(storage, operationHandlers);
        shopService.process(transactions);

        fileService.saveToFile(storage.getAll(), "report.csv");

        System.out.println("Raport został wygenerowany pomyślnie!");
    }
}
