package de.marvin.rsockets.backend.domain;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Player entity representing a user in the system")
public record Player(
    @Schema(description = "Unique identifier of the player", example = "1", accessMode = Schema.AccessMode.READ_ONLY)
    Long id,

    @Schema(description = "Username of the player", example = "john_doe", required = true, minLength = 3, maxLength = 50)
    String username
) {

}
