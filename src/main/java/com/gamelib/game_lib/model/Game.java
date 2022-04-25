package com.gamelib.game_lib.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.NumberFormat;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity @Getter @Setter
public class Game extends AbstractEntity {

    @NotEmpty(message = "game title is required")
    private String name;

    @Size(min = 4, max = 4, message = "year title is required. ex: 2010")
    private String year;

    @ManyToOne
    @JoinColumn(name = "company_id")
    @NotNull(message = "company is required")
    @JsonIgnoreProperties({"games"})
    private Company company;

    @ManyToOne
    @JoinColumn(name = "console_id")
    @NotNull(message = "console is required")
    @JsonIgnoreProperties({"games"})
    private Console console;


}
