import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Rolls {
    private static Rolls singleInstance = null;
    private Map<Integer, Integer> allRollsOfDice1 = new HashMap<>();
    private Map<Integer, Integer> allRollsOfDice2 = new HashMap<>();

    public static Rolls getInstance() {
        // To ensure only one instance is created
        if (singleInstance == null) {
            singleInstance = new Rolls();
        }
        return singleInstance;
    }

    public void loadRolls() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        List rolls1 = objectMapper.readValue(Paths.get("rolls_1.json").toFile(), List.class);
        AtomicInteger i1 = new AtomicInteger(0);
        setAllRollsOfDice1((Map<Integer, Integer>) rolls1.stream().collect(Collectors.toMap(n -> i1.incrementAndGet(), Function.identity())));

//        System.out.println(allRollsOfDice1);
        AtomicInteger i2 = new AtomicInteger(0);
        List rolls2 = objectMapper.readValue(Paths.get("rolls_2.json").toFile(), List.class);
        setAllRollsOfDice2((Map<Integer, Integer>) rolls2.stream().collect(Collectors.toMap(n -> i2.incrementAndGet(), Function.identity())));
//        System.out.println(allRollsOfDice2);
    }

    public Map<Integer, Integer> getAllRollsOfDice1() {
        return allRollsOfDice1;
    }

    public void setAllRollsOfDice1(Map<Integer, Integer> allRollsOfDice1) {
        this.allRollsOfDice1 = allRollsOfDice1;
    }

    public Map<Integer, Integer> getAllRollsOfDice2() {
        return allRollsOfDice2;
    }

    public void setAllRollsOfDice2(Map<Integer, Integer> allRollsOfDice2) {
        this.allRollsOfDice2 = allRollsOfDice2;
    }

}
