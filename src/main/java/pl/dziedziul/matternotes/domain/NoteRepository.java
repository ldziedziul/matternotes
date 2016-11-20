package pl.dziedziul.matternotes.domain;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface NoteRepository extends JpaRepository<Note, Long> {

	Optional<Note> findByChannelIdAndUserIdAndType(String channelId, String userId, NoteType type);

	Optional<Note> findByTitleAndUserIdAndType(String title, String userId, NoteType type);

	List<Note> findAllByUserIdOrderByTitle(String userId);
}
