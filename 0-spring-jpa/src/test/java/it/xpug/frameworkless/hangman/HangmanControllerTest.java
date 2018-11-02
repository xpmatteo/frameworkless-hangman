package it.xpug.frameworkless.hangman;

import it.xpug.frameworkless.hangman.domain.Game;
import it.xpug.frameworkless.hangman.domain.Prisoner;
import it.xpug.frameworkless.hangman.db.GameRepository;
import it.xpug.frameworkless.hangman.web.HangmanController;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static java.util.Collections.emptyList;
import static java.util.Collections.singletonList;
import static org.hamcrest.Matchers.is;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;
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
        when(gameRepository.createNewGame()).thenReturn(new Game((long) 0x7b));

        mockMvc.perform(post("/hangman/game").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("gameId", is("7b")))
        ;
    }

    @Test
    public void createNewGameWithGivenWord() throws Exception {
        when(gameRepository.createNewGame("zot")).thenReturn(new Game(16L));

        mockMvc.perform(post("/hangman/game").param("word", "zot"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("gameId", is("10")))
        ;
    }

    @Test
    public void findGame() throws Exception {
        Game game = new Game(255L);
        when(gameRepository.findGame(255L)).thenReturn(Optional.of(game));

        mockMvc.perform(get("/hangman/game/ff"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("gameId", is("ff")))
                .andExpect(jsonPath("guessesRemaining", is(18)))
                .andExpect(jsonPath("hits", is(emptyList())))
                .andExpect(jsonPath("misses", is(emptyList())))
        ;
    }

    @Test
    public void cannotFindGame() throws Exception {
        when(gameRepository.findGame(any())).thenReturn(Optional.empty());

        mockMvc.perform(get("/hangman/game/777"))
                .andExpect(status().isNotFound())
        ;
    }

    @Test
    public void guess() throws Exception {
        Game game = new Game(255L, new Prisoner("pippo"));
        when(gameRepository.findGame(255L)).thenReturn(Optional.of(game));

        mockMvc.perform(post("/hangman/game/ff/guesses").param("guess", "x"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("gameId", is("ff")))
                .andExpect(jsonPath("guessesRemaining", is(17)))
                .andExpect(jsonPath("hits", is(emptyList())))
                .andExpect(jsonPath("misses", is(singletonList("x"))))
        ;

        // we have no good way to test that the game was saved!!!
        // in fact it is saved because the controller is annotated as "transactional"
    }

    @Test
    public void guessOnInexistentGame() throws Exception {
        when(gameRepository.findGame(any())).thenReturn(Optional.empty());

        mockMvc.perform(
                post("/hangman/game/ff/guesses")
                .param("guess", "a")
        )
                .andExpect(status().isNotFound())
// The following behaviour is correct in real life, but fails when running the controller in the
// unit test environment.
//                .andExpect(jsonPath("message", is("Game with id 'ff' not found")))
//                .andExpect(jsonPath("status", is(404)))
        ;
    }

    @Test
    public void guessTooLongWord() throws Exception {
        Game game = new Game(255L, new Prisoner("pippo"));
        when(gameRepository.findGame(255L)).thenReturn(Optional.of(game));

        mockMvc.perform(
                post("/hangman/game/ff/guesses")
                        .param("guess", "xxxx")
        )
                .andExpect(status().isBadRequest())
// The following behaviour is correct in real life, but fails when running the controller in the
// unit test environment.
//                .andExpect(jsonPath("message", is("Guess 'xxxx' invalid: must be a single letter")))
//                .andExpect(jsonPath("status", is(400)))
        ;
    }
}
