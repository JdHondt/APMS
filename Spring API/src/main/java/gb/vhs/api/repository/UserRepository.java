package gb.vhs.api.repository;


import gb.vhs.api.entity.User;
import gb.vhs.api.repository.implementation.CustomRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends CustomRepository<User, Long> {

    Optional<User> findFirstById(Long id);

    Optional<User> findFirstByEmail(String email);

    Optional<User> findFirstByPlayerId(Long playerId);

    @Transactional
    void deleteByEmail(String email);

    /**
     * Search a user by email, or name
     */
    @Query("SELECT usr FROM User usr " +
            "WHERE LOWER(usr.email) = LOWER(:query) OR LOWER(usr.firstName) LIKE LOWER(concat('%', :query, '%'))")
    List<User> findByName(
            @Param("query") String query,
            Pageable pageable);





}


