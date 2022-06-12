package ru.yandex.practicum.filmorate.model;

import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import java.time.LocalDate;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode (of = "id")
@Builder
public class User {

    private long id;
    @Email
    @NotNull
    @NotBlank
    private String email;
    @NotNull
    @NotBlank
    private String login;
    private String name;
    @Past
    @NotNull
    private LocalDate birthday;
}
