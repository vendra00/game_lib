package com.gamelib.game_lib.repository;

import com.gamelib.game_lib.model.Console;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ConsoleRepository extends JpaRepository<Console, UUID> {
    @Query("select c from Console c "
            + "where lower(c.name) like lower(concat('%', :searchTerm, '%')) ")
    List<Console> search(@Param("searchTerm") String searchTerm);
}
