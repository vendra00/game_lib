package com.gamelib.game_lib.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

import javax.annotation.Nullable;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.LinkedList;
import java.util.List;

@Entity @Getter @Setter
public class Company extends AbstractEntity{

    @NotEmpty
    @NotBlank
    private String name;

    @OneToMany(mappedBy = "company")
    @Nullable
    private List<Console> consoles = new LinkedList<>();

    @OneToMany(mappedBy = "company")
    @Nullable
    private List<Game> games = new LinkedList<>();

    @ManyToOne
    @JoinColumn(name = "country_id")
    @NotNull
    @JsonIgnoreProperties({"companies"})
    private Country country;
}
