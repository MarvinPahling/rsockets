package de.marvin.rsockets.backend.services;

import de.marvin.rsockets.backend.domain.Player;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class PlayerService {

    private final List<Player> players = new ArrayList<>();
    private final AtomicLong idCounter = new AtomicLong(1);
    private final Random random = new Random();

    private static final String[] ADJECTIVES = {
        "Swift", "Brave", "Mighty", "Clever", "Fierce", "Silent", "Bold", "Quick", "Wise", "Strong"
    };

    private static final String[] NAMES = {
        "Warrior", "Mage", "Knight", "Rogue", "Archer", "Paladin", "Wizard", "Hunter", "Ninja", "Samurai"
    };

    public PlayerService() {
        // Initialize with sample data
        players.add(new Player(idCounter.getAndIncrement(), "alice"));
        players.add(new Player(idCounter.getAndIncrement(), "bob"));
        players.add(new Player(idCounter.getAndIncrement(), "charlie"));
    }

    public List<Player> getAllPlayers() {
        return new ArrayList<>(players);
    }

    public Player addPlayer(String username) {
        Player newPlayer = new Player(idCounter.getAndIncrement(), username);
        players.add(newPlayer);
        return newPlayer;
    }

    public Player addRandomPlayer() {
        String randomUsername = ADJECTIVES[random.nextInt(ADJECTIVES.length)] +
                               NAMES[random.nextInt(NAMES.length)];
        return addPlayer(randomUsername);
    }
}
