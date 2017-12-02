package wad.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import wad.domain.Kuva;

public interface KuvaRepository extends JpaRepository<Kuva, Long> {

}
