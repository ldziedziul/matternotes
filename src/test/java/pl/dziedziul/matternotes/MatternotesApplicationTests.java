package pl.dziedziul.matternotes;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import pl.dziedziul.matternotes.rest.NotesController;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MatternotesApplicationTests {

	@Autowired
	private NotesController notesController;

	@Test
	public void contextLoads() {
		assertNotNull(notesController);
	}

}
