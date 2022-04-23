package com.gamelib.game_lib.repository;

import com.gamelib.game_lib.model.Game;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface GameRepository extends JpaRepository<Game, UUID> {
    @Query("select g from Game g "
            + "where lower(g.name) like lower(concat('%', :searchTerm, '%')) ")
    List<Game> search(@Param("searchTerm") String searchTerm);
}
