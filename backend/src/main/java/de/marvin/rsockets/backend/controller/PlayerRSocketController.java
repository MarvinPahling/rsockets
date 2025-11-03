package de.marvin.rsockets.backend.controller;

import de.marvin.rsockets.backend.domain.Player;
import de.marvin.rsockets.backend.services.PlayerService;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

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
}
