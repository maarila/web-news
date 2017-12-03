package wad.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import wad.domain.Kategoria;

public interface KategoriaRepository extends JpaRepository<Kategoria, Long> {
    Kategoria findByNimi(String kategorianNimi);
}
