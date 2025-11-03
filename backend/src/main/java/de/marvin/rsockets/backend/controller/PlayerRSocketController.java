package de.marvin.rsockets.backend.controller;

import de.marvin.rsockets.backend.domain.Player;
import de.marvin.rsockets.backend.services.PlayerService;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.List;

@Controller
public class PlayerRSocketController {

    private final PlayerService playerService;

    public PlayerRSocketController(PlayerService playerService) {
        this.playerService = playerService;
    }

    @MessageMapping("players.list")
    public Flux<Player> getPlayers() {
        return Flux.fromIterable(playerService.getAllPlayers());
    }

    @MessageMapping("players.add")
    public Mono<Player> addPlayer(String username) {
        return Mono.just(playerService.addPlayer(username));
    }

    @MessageMapping("players.stream")
    public Flux<List<Player>> streamPlayers() {
        // Emit the initial list of players immediately
        return Flux.concat(
            Flux.just(playerService.getAllPlayers()),
            // Then emit the updated list every 5 seconds after adding a random player
            Flux.interval(Duration.ofSeconds(5))
                .map(tick -> {
                    playerService.addRandomPlayer();
                    return playerService.getAllPlayers();
                })
        );
    }
}
