package it.xpug.frameworkless.hangman;

public class HangmanControllerTest {
//
////    private MockMvc mockMvc;
//
//    private GameRepository gameRepository;
//
//    @Test
//    public void createNewGame() throws Exception {
//        when(gameRepository.createNewGame()).thenReturn(new Game(123L));
//
//        mockMvc.perform(post("/hangman/game"))
//                .andExpect(status().isCreated())
//                .andExpect(jsonPath("gameId", is("7b")))
//        ;
//    }
//
//    @Test
//    public void createNewGameWithGivenWord() throws Exception {
//        when(gameRepository.createNewGame("zot")).thenReturn(new Game(16L));
//
//        mockMvc.perform(post("/hangman/game").param("word", "zot"))
//                .andExpect(status().isCreated())
//                .andExpect(jsonPath("gameId", is("10")))
//        ;
//    }
//
//    @Test
//    public void findGame() throws Exception {
//        Game game = new Game(255L);
//        when(gameRepository.findGame(255L)).thenReturn(Optional.of(game));
//
//        mockMvc.perform(get("/hangman/game/ff"))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("gameId", is("ff")))
//                .andExpect(jsonPath("guessesRemaining", is(18)))
//                .andExpect(jsonPath("hits", is(emptyList())))
//                .andExpect(jsonPath("misses", is(emptyList())))
//        ;
//    }
//
//    @Test
//    public void cannotFindGame() throws Exception {
//        when(gameRepository.findGame(any())).thenReturn(Optional.empty());
//
//        mockMvc.perform(get("/hangman/game/777"))
//                .andExpect(status().isNotFound())
//        ;
//    }
//
//    @Test
//    public void guess() throws Exception {
//        Game game = new Game(255L, new Prisoner("pippo"));
//        when(gameRepository.findGame(255L)).thenReturn(Optional.of(game));
//
//        mockMvc.perform(post("/hangman/game/ff/guesses").param("guess", "x"))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("gameId", is("ff")))
//                .andExpect(jsonPath("guessesRemaining", is(17)))
//                .andExpect(jsonPath("hits", is(emptyList())))
//                .andExpect(jsonPath("misses", is(singletonList("x"))))
//        ;
//
//        verify(gameRepository).update(game);
//    }
//
//    @Test
//    public void guessOnInexistentGame() throws Exception {
//        when(gameRepository.findGame(any())).thenReturn(Optional.empty());
//
//        mockMvc.perform(
//                post("/hangman/game/ff/guesses")
//                .param("guess", "a")
//        )
//                .andExpect(status().isNotFound())
////                .andExpect(jsonPath("message", is("Game with id 'ff' not found")))
////                .andExpect(jsonPath("status", is(404)))
//        ;
//    }
//
//    @Test
//    public void guessTooLongWord() throws Exception {
//        Game game = new Game(255L, new Prisoner("pippo"));
//        when(gameRepository.findGame(255L)).thenReturn(Optional.of(game));
//
//        mockMvc.perform(
//                post("/hangman/game/ff/guesses")
//                        .param("guess", "xxxx")
//                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
//                        .accept(MediaType.APPLICATION_JSON)
//        )
//                .andExpect(status().isBadRequest())
////                .andExpect(jsonPath("message", is("Guess 'xxxx' invalid: must be a single letter")))
////                .andExpect(jsonPath("status", is(400)))
//        ;
//    }
}
