package com.gamelib.game_lib.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Entity @Getter @Setter
public class Game extends AbstractEntity {

    @NotEmpty
    private String name;

    @NotEmpty
    private String year;

    @ManyToOne
    @JoinColumn(name = "company_id")
    @NotNull
    @JsonIgnoreProperties({"games"})
    private Company company;

    @ManyToOne
    @JoinColumn(name = "console_id")
    @NotNull
    @JsonIgnoreProperties({"games"})
    private Console console;


}
