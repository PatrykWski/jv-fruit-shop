package core.basesyntax.app;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.Operation;
import core.basesyntax.repository.FileService;
import core.basesyntax.repository.FileServiceImpl;
import core.basesyntax.repository.FruitParser;
import core.basesyntax.repository.Storage;
import core.basesyntax.repository.StorageInterface;
import core.basesyntax.service.AddOperationHandler;
import core.basesyntax.service.OperationHandler;
import core.basesyntax.service.PurchaseHandler;
import core.basesyntax.service.ShopService;
import core.basesyntax.service.ShopServiceImpl;
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
        operationHandlers.put(Operation.b, addHandler);
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
