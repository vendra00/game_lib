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
import javax.validation.constraints.Size;
import java.util.LinkedList;
import java.util.List;

@Entity @Getter @Setter
public class Console extends AbstractEntity{

    @NotEmpty(message = "console name is required")
    private String name;

    @Size(min = 4, max = 4, message = "console year is required. ex: 2010")
    private String year;

    @ManyToOne
    @JoinColumn(name = "company_id")
    @NotNull(message = "company is required")
    @JsonIgnoreProperties({"consoles"})
    private Company company;

    @OneToMany(mappedBy = "console")
    @Nullable
    private List<Game> games = new LinkedList<>();

}
