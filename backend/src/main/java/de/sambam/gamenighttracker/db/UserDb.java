package de.sambam.gamenighttracker.db;

import de.sambam.gamenighttracker.model.User;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;
import java.util.Optional;

public interface UserDb extends PagingAndSortingRepository<User, String> {

    Optional<User> findById(String Id);

    @Override
    List<User> findAll();
}
