package de.marvin.rsockets.backend.controller;

import de.marvin.rsockets.backend.domain.Player;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

@RestController
@RequestMapping("/api/players")
@Tag(name = "Player", description = "Player management APIs")
public class PlayerController {

    private final List<Player> players = new ArrayList<>();
    private final AtomicLong idCounter = new AtomicLong(1);

    public PlayerController() {
        // Initialize with sample data
        players.add(new Player(idCounter.getAndIncrement(), "alice"));
        players.add(new Player(idCounter.getAndIncrement(), "bob"));
        players.add(new Player(idCounter.getAndIncrement(), "charlie"));
    }

    @Operation(
            summary = "Get all players",
            description = "Retrieves a list of all registered players in the system"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Successfully retrieved list of players",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = Player.class)
                    )
            )
    })
    @GetMapping
    public ResponseEntity<List<Player>> getAllPlayers() {
        return ResponseEntity.ok(players);
    }

    @Operation(
            summary = "Get player by ID",
            description = "Retrieves a specific player by their unique identifier"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Player found",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = Player.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Player not found",
                    content = @Content
            )
    })
    @GetMapping("/{id}")
    public ResponseEntity<Player> getPlayerById(
            @Parameter(description = "ID of the player to retrieve", required = true, example = "1")
            @PathVariable Long id) {
        Optional<Player> player = players.stream()
                .filter(p -> p.id().equals(id))
                .findFirst();
        return player.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(
            summary = "Create new player",
            description = "Creates a new player with the provided username"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "Player created successfully",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = Player.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid input",
                    content = @Content
            )
    })
    @PostMapping
    public ResponseEntity<Player> createPlayer(
            @Parameter(description = "Username for the new player", required = true)
            @RequestParam String username) {
        if (username == null || username.trim().isEmpty() || username.length() < 3) {
            return ResponseEntity.badRequest().build();
        }
        Player newPlayer = new Player(idCounter.getAndIncrement(), username);
        players.add(newPlayer);
        return ResponseEntity.status(HttpStatus.CREATED).body(newPlayer);
    }

    @Operation(
            summary = "Update player",
            description = "Updates an existing player's username"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Player updated successfully",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = Player.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Player not found",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid input",
                    content = @Content
            )
    })
    @PutMapping("/{id}")
    public ResponseEntity<Player> updatePlayer(
            @Parameter(description = "ID of the player to update", required = true, example = "1")
            @PathVariable Long id,
            @Parameter(description = "New username for the player", required = true)
            @RequestParam String username) {
        if (username == null || username.trim().isEmpty() || username.length() < 3) {
            return ResponseEntity.badRequest().build();
        }

        for (int i = 0; i < players.size(); i++) {
            if (players.get(i).id().equals(id)) {
                Player updatedPlayer = new Player(id, username);
                players.set(i, updatedPlayer);
                return ResponseEntity.ok(updatedPlayer);
            }
        }
        return ResponseEntity.notFound().build();
    }

    @Operation(
            summary = "Delete player",
            description = "Deletes a player from the system by their ID"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "204",
                    description = "Player deleted successfully",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Player not found",
                    content = @Content
            )
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePlayer(
            @Parameter(description = "ID of the player to delete", required = true, example = "1")
            @PathVariable Long id) {
        boolean removed = players.removeIf(p -> p.id().equals(id));
        if (removed) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
