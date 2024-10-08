package bot.crew.testapplicationbotcrew.repositories;

import bot.crew.testapplicationbotcrew.entities.Lector;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LectorRepository extends JpaRepository<Lector, Long> {
    @Query("SELECT l FROM Lector l WHERE l.firstName LIKE %:template% OR l.lastName LIKE %:template%")
    List<Lector> searchByTemplate(@Param("template") String template);
}
