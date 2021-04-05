package com.ustudents.farmland.core.player;

import com.ustudents.engine.core.cli.print.Out;
import com.ustudents.engine.utility.SeedRandom;
import com.ustudents.farmland.Farmland;
import com.ustudents.farmland.component.GridComponent;
import com.ustudents.farmland.core.grid.Cell;
import com.ustudents.farmland.core.item.Crop;
import com.ustudents.farmland.core.item.Item;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class Bot {
    public static void playTurn() {
        // TODO: seed
        SeedRandom random = new SeedRandom();

        int action = random.generateInRange(0, 1);

        if (action == 0) {
            buyLand(random);
        } else if (action == 1) {
            addItem(random);
        }

        maintenanceCost();
    }

    public static void maintenanceCost(){
        for (int x = 0; x < Farmland.get().getCurrentSave().cells.size(); x++) {
            for (int y = 0; y < Farmland.get().getCurrentSave().cells.get(x).size(); y++) {
                Cell cell = Farmland.get().getCurrentSave().cells.get(x).get(y);

                if (cell.isOwnedByCurrentPlayer()){
                    Player player = Farmland.get().getCurrentSave().getCurrentPlayer();
                    player.setMoney(player.money - 1);
                }
            }
        }

    }

    public static void buyLand(SeedRandom random) {
        Player player = Farmland.get().getCurrentSave().getCurrentPlayer();

        if (player.money < 25) {
            return;
        }

        List<Cell> cells = player.getCloseCellsAvailable();

        if (!cells.isEmpty()) {
            Cell cellWanted = cells.get(random.generateInRange(0, cells.size() - 1));
            cellWanted.setOwned(true, player.getId());
            player.setMoney(player.money - 25);
        }
    }

    public static void addItem(SeedRandom random) {
        Player player = Farmland.get().getCurrentSave().getCurrentPlayer();

        int i = 0;
        while (true) {
            if (i == 20) {
                return;
            }

            List<Item> items = Farmland.get().getResourceDatabase().values().stream().filter(Objects::nonNull).collect(Collectors.toList());
            //List<Item> items = Farmland.get().getItemDatabase().values().stream().filter(j -> j instanceof Crop).collect(Collectors.toList());
            Item item = items.get(random.generateInRange(0, items.size() - 1));

            if (player.money < item.value) {
                i++;
                continue;
            }

            player.setMoney(player.money - item.value);
            Item clone = Item.clone(item);
            assert clone != null;
            clone.quantity = 1;

            List<Cell> cells = player.getOwnedCellsWithNoItem();

            if (!cells.isEmpty()) {
                Cell cellWanted = cells.get(random.generateInRange(0, cells.size() - 1));
                cellWanted.setItem(clone);
                Farmland.get().getSceneManager().getCurrentScene().getEntityByName("map").getComponent(GridComponent.class).onItemUsed.dispatch();
                break;
            } else {
                i++;
            }
        }
    }
}
