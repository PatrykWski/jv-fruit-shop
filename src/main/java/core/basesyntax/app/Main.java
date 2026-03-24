package core.basesyntax.app;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.Operation;
import core.basesyntax.repository.FileService;
import core.basesyntax.repository.FruitParser;
import core.basesyntax.service.ShopService;
import core.basesyntax.service.impl.FileServiceImpl;
import core.basesyntax.service.impl.ShopServiceImpl;
import core.basesyntax.strategy.AddOperationHandler;
import core.basesyntax.strategy.BalanceHandler;
import core.basesyntax.strategy.OperationHandler;
import core.basesyntax.strategy.PurchaseHandler;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        FruitParser fruitParser = new FruitParser();
        FileService fileService = new FileServiceImpl(fruitParser);

        Map<Operation, OperationHandler> operationHandlers = new HashMap<>();
        operationHandlers.put(Operation.B, new BalanceHandler());
        operationHandlers.put(Operation.S, new AddOperationHandler());
        operationHandlers.put(Operation.R, new AddOperationHandler());
        operationHandlers.put(Operation.P, new PurchaseHandler());

        List<FruitTransaction> transactions = fileService.loadFromFile("input.csv");

        ShopService shopService = new ShopServiceImpl(operationHandlers);
        Map<String, Integer> finalInventory = shopService.process(transactions);

        fileService.saveToFile(finalInventory, "report.csv");

        System.out.println("Raport wygenerowany bez użycia współdzielonego Storage!");
    }
}
