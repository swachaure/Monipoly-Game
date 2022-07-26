import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class Board {

    private static Board singleInstance = null;
    private final Map<Integer, Property> propertiesMap = new HashMap<>();

    private final Map<String, Player> players = new HashMap<>(4);

    private int currentPosOfPlayer1;
    private int currentPosOfPlayer2;
    private int currentPosOfPlayer3;
    private int currentPosOfPlayer4;

    public static Board getInstance() {
        // To ensure only one instance is created
        if (singleInstance == null) {
            singleInstance = new Board();
        }
        return singleInstance;
    }

    public Map<String, Player> getPlayers() {
        return players;
    }

    public int getCurrentPosOfPlayer1() {
        return currentPosOfPlayer1;
    }

    public int getCurrentPosOfPlayer2() {
        return currentPosOfPlayer2;
    }

    public int getCurrentPosOfPlayer3() {
        return currentPosOfPlayer3;
    }

    public int getCurrentPosOfPlayer4() {
        return currentPosOfPlayer4;
    }

    public void setCurrentPosOfPlayer1(int currentPosOfPlayer1) {
        int current = this.currentPosOfPlayer1 + currentPosOfPlayer1;
        int div = current / 9;
        if (current % 9 == 0) {
            this.currentPosOfPlayer1 = 9;
        } else {
            this.currentPosOfPlayer1 = (current > 9) ? (current - (9 * div)) : current;
        }
        assignProperty(this.currentPosOfPlayer1, div, "Peter", 1);
    }

    public void setCurrentPosOfPlayer2(int currentPosOfPlayer2) {
        int current = this.currentPosOfPlayer2 + currentPosOfPlayer2;
        int div = current / 9;
        if (current % 9 == 0) {
            this.currentPosOfPlayer2 = 9;
        } else {
            this.currentPosOfPlayer2 = (current > 9) ? (current - (9 * div)) : current;
        }
        assignProperty(this.currentPosOfPlayer2, div, "Billy", 2);
    }

    public void setCurrentPosOfPlayer3(int currentPosOfPlayer3) {
        int current = this.currentPosOfPlayer3 + currentPosOfPlayer3;
        int div = current / 9;
        if (current % 9 == 0) {
            this.currentPosOfPlayer3 = 9;
        } else {
            this.currentPosOfPlayer3 = (current > 9) ? (current - (9 * div)) : current;
        }
        assignProperty(this.currentPosOfPlayer3, div, "Charlotte", 3);
    }

    public void setCurrentPosOfPlayer4(int currentPosOfPlayer4) {
        int current = this.currentPosOfPlayer4 + currentPosOfPlayer4;
        int div = current / 9;
        if (current % 9 == 0) {
            this.currentPosOfPlayer4 = 9;
        } else {
            this.currentPosOfPlayer4 = (current > 9) ? (current - (9 * div)) : current;
        }
        assignProperty(this.currentPosOfPlayer4, div, "Sweedal", 4);
    }

    private void assignProperty(int current, int div, String playerName, Integer playerID) {
        Property property = propertiesMap.get(current);
        Player player = players.get(playerName);
        if (current == 1) {
            player.addAmountIntoBalance(div);
        } else {
            if (property.getOwner() == null) {
                player.ownProperty(property);
                property.setOwner(player);
                propertiesMap.replace(current, property);
            } else {
                String owner = property.getOwner().getName();
                player.payRent(owner, property, property.getPrice());
                Player ownerObj = players.get(owner);
                ownerObj.takeRent(player, property, property.getPrice());
            }
        }
    }

    public void addPlayer(Player player) {
        players.put(player.getName(), player);
    }

    public void addPropertiesToBoard() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        List list = objectMapper.readValue(Paths.get("board.json").toFile(), List.class);
        AtomicInteger i = new AtomicInteger(0);
        for (Object map : list) {
            Map<String, Object> obj = (Map) map;
            Property property = new Property();
            property.setName((String) obj.get("name"));
            property.setColor((String) obj.get("colour"));
            property.setType((String) obj.get("type"));
            property.setPrice((Integer) obj.get("price"));
            propertiesMap.put(i.incrementAndGet(), property);
        }
    }

    public Map<Integer, Property> getPropertiesMap() {
        return propertiesMap;
    }

    public void printAllProperties() {
        for (Property p : propertiesMap.values()) {
            Utility.printDash();
            StringBuilder builder = new StringBuilder();
            builder.append("Name :- ").append(p.getName()).append("\t|");
            builder.append("\tType :- ").append(p.getType()).append("\t|");
            if (p.getPrice() != null) {
                builder.append("\tPrice :- ").append(p.getPrice()).append("$").append("\t|");
            }
            if (p.getColor() != null) {
                builder.append("\t Color :- ").append(p.getColor()).append("\t|");
            }
            System.out.println(builder);
        }
    }

    public void printAllPlayersName() {
        System.out.println("[ " + players.values().stream().map(Player::getName).collect(Collectors.joining(",")) + " ]");
    }

    public Integer getBalance(String playerName) {
        return null != players.get(playerName).getBalance() ? players.get(playerName).getBalance() : 0;
    }

}
