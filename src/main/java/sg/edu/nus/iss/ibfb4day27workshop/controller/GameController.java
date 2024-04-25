package sg.edu.nus.iss.ibfb4day27workshop.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import sg.edu.nus.iss.ibfb4day27workshop.exception.GameNotFoundException;
import sg.edu.nus.iss.ibfb4day27workshop.model.EditedComment;
import sg.edu.nus.iss.ibfb4day27workshop.model.Game;
import sg.edu.nus.iss.ibfb4day27workshop.model.Review;
import sg.edu.nus.iss.ibfb4day27workshop.service.GameService;

@RestController
@RequestMapping("/games")
public class GameController {
    @Autowired
    GameService gameService;

    @GetMapping()
    public ResponseEntity<List<Game>> getAllGames() {
        List<Game> games = gameService.getAllGames();

        return ResponseEntity
                .status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(games);
    }

    // Pagination GetMapping parameters passed in as @RequestParam

    @PostMapping()
    public ResponseEntity<Game> createGame(@RequestBody Game game) {

        Game savedGame = gameService.createGame(game);

        return ResponseEntity
                .status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(savedGame);
    }

    @PutMapping()
    public ResponseEntity<Long> updateGame(@RequestBody Game editedgame) {

        Long result = gameService.updateGame(editedgame);

        return ResponseEntity
                .status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(result);
    }

    @DeleteMapping()
    public ResponseEntity<Long> deleteGame(@RequestBody Game gameToDelete) {

        Long result = gameService.deleteGame(gameToDelete);

        return ResponseEntity
                .status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(result);
    }

    // post using requestbody
    @PostMapping("/review")
    public ResponseEntity postReviewOnGame(@RequestBody Review review) {
        Review insertedReview = null;

        try {
            insertedReview = this.gameService.createReview(review);
        } catch (GameNotFoundException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body("");
        }

        return ResponseEntity
                .ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(insertedReview.toJson().toString());
    }

    // post using form_urlencoded
    @PostMapping("/review2")
    public ResponseEntity postReviewOnGame2(@ModelAttribute Review review) {
        Review insertedReview = null;

        try {
            insertedReview = this.gameService.createReview(review);
        } catch (GameNotFoundException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body("");
        }

        return ResponseEntity
                .ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(insertedReview.toJson().toString());
    }


    @PutMapping("/review/{reviewId}")
    public ResponseEntity<Long> updateExistingReview(@RequestBody EditedComment ec, @PathVariable Integer reviewId) {
        ec.setCid(reviewId.toString());

        long modified = this.gameService.updateReview(ec);

        return ResponseEntity.ok(modified);
    }

    @GetMapping("/review/{reviewId}")
    public ResponseEntity getReviewsWithRating(@PathVariable Integer reviewId) {
        Review r = this.gameService.getReviewById(reviewId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(r.toJsonEdited().toString());
    }
}
