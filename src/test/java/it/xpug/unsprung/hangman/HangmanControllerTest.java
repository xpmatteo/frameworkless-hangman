package it.xpug.unsprung.hangman;

import it.xpug.unsprung.hangman.domain.Game;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static java.util.Collections.emptyList;
import static org.hamcrest.Matchers.is;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(HangmanController.class)
public class HangmanControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private GameRepository gameRepository;

    @Test
    public void createNewGame() throws Exception {
        when(gameRepository.createNewGame()).thenReturn(new Game(123L));

        mockMvc.perform(post("/hangman/game"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("gameId", is("7b")))
        ;
    }

    @Test
    public void findGame() throws Exception {
        Game game = new Game(255L);
        when(gameRepository.findGame(255L)).thenReturn(Optional.of(game));

        mockMvc.perform(get("/hangman/game/ff"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("gameId", is("ff")))
                .andExpect(jsonPath("prisoner.guesses_remaining", is(18)))
                .andExpect(jsonPath("prisoner.hits", is(emptyList())))
                .andExpect(jsonPath("prisoner.misses", is(emptyList())))
        ;
    }

    @Test
    public void cannotFindGame() throws Exception {
        when(gameRepository.findGame(any())).thenReturn(Optional.empty());

        mockMvc.perform(get("/hangman/game/777"))
                .andExpect(status().isNotFound())
        ;
    }

}
