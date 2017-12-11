package wad.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import wad.domain.Kategoria;

public interface KategoriaRepository extends JpaRepository<Kategoria, Long> {
    Kategoria findByNimi(String kategorianNimi);
    List<Kategoria> findByValikossaTrue();
}
