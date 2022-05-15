package ru.yandex.practicum.filmorate.model;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.*;
import java.time.LocalDate;

@Data
@Builder
public class Film {

    private long id;
    private @NotNull @NotBlank String name;
    private @Size(min = 1, max = 200) String description;
    private @NotNull LocalDate releaseDate;
    private @Positive Long duration;
}
