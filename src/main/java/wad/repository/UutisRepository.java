package wad.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import wad.domain.Uutinen;

public interface UutisRepository extends JpaRepository<Uutinen, Long> {
}
