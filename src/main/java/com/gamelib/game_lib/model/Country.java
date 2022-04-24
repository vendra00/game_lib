package com.gamelib.game_lib.model;

import lombok.Getter;
import lombok.Setter;

import javax.annotation.Nullable;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.util.LinkedList;
import java.util.List;

@Entity @Getter
@Setter
public class Country extends AbstractEntity {

    @NotEmpty
    @NotBlank
    private String name;

    @OneToMany(mappedBy = "country")
    @Nullable
    private List<Company> companies = new LinkedList<>();

}
