package it.xpug.frameworkless.hangman.service;

import it.xpug.frameworkless.hangman.db.GameRepository;
import it.xpug.frameworkless.hangman.domain.Game;
import it.xpug.frameworkless.hangman.domain.Guess;
import it.xpug.frameworkless.hangman.domain.Prisoner;
import it.xpug.frameworkless.hangman.web.ClientError;
import org.junit.Test;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import static java.util.Arrays.stream;
import static java.util.stream.Collectors.toSet;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;
import static org.mockito.Mockito.*;

public class HangmanServiceTest {

    GameRepository gameRepository = mock(GameRepository.class);
    HangmanService hangmanService = new HangmanService(gameRepository);

    @Test
    public void gameIsCreated_withRandomWord() throws Exception {
        Game newGame = aGame();
        when(gameRepository.createNewGame()).thenReturn(newGame);

        GameResponse gameResponse = hangmanService.createNewGame(Optional.empty());

        assertThat(gameResponse, is(GameResponse.from(newGame)));
    }

    @Test
    public void gameIsCreated_withSpecifiedWord() throws Exception {
        Game newGame = aGame();
        when(gameRepository.createNewGame("WORD")).thenReturn(newGame);

        GameResponse gameResponse = hangmanService.createNewGame(Optional.of("WORD"));

        assertThat(gameResponse, is(GameResponse.from(newGame)));
    }

    @Test
    public void findGame() throws Exception {
        Game foundGame = aGame();
        when(gameRepository.findGame(0x99L)).thenReturn(Optional.of(foundGame));

        GameResponse gameResponse = hangmanService.findGame("99");

        assertThat(gameResponse, is(GameResponse.from(foundGame)));
    }

    @Test(expected = GameNotFoundException.class)
    public void findGame_notFound() throws Exception {
        when(gameRepository.findGame(0x77L)).thenReturn(Optional.empty());

        hangmanService.findGame("77");
    }

    @Test
    public void guess_ok() throws Exception {
        Game foundGame = aGame("word");
        when(gameRepository.findGame(0x99L)).thenReturn(Optional.of(foundGame));

        GameResponse gameResponse = hangmanService.guess("99", "x");

        assertThat(gameResponse.getHits(), is(setOf()));
        assertThat(gameResponse.getMisses(), is(setOf("x")));
    }

    @Test(expected = InvalidGuessException.class)
    public void guess_parameterTooBig() throws Exception {
        hangmanService.guess("99", "ab");
    }

    @Test(expected = GameNotFoundException.class)
    public void guess_notFound() throws Exception {
        when(gameRepository.findGame(anyLong())).thenReturn(Optional.empty());

        hangmanService.guess("99", "a");
    }

    private Set<Guess> setOf(String ... strings) {
        return stream(strings).map(Guess::new).collect(toSet());
    }

    private Game aGame() {
        return new Game(789L);
    }

    private Game aGame(String word) {
        return new Game(789L, new Prisoner(word));
    }
}