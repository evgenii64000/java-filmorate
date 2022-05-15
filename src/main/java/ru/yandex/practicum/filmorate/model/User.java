package ru.yandex.practicum.filmorate.model;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import java.time.LocalDate;

@Data
@Builder
public class User {

    private long id;
    private @Email @NotNull @NotBlank String email;
    private @NotNull @NotBlank String login;
    private String name;
    private @Past @NotNull LocalDate birthday;
}
