package sg.edu.nus.iss.ibfb4day27workshop.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sg.edu.nus.iss.ibfb4day27workshop.exception.GameNotFoundException;
import sg.edu.nus.iss.ibfb4day27workshop.model.EditedComment;
import sg.edu.nus.iss.ibfb4day27workshop.model.Game;
import sg.edu.nus.iss.ibfb4day27workshop.model.Review;
import sg.edu.nus.iss.ibfb4day27workshop.repo.GameRepo;
import sg.edu.nus.iss.ibfb4day27workshop.repo.ReviewRepo;

@Service
public class GameService {
    @Autowired
    GameRepo gameRepo;

    @Autowired
    ReviewRepo reviewRepo;

    public List<Game> getAllGames() {
        return gameRepo.getAll();
    }

    public List<Game> getAllGamesPaginated(int limit, int offset) {
        return gameRepo.getPaginatedGameList(limit, offset);
    }

    public Game createGame(Game game) {
        return gameRepo.createGame(game);
    }

    public long updateGame(Game game) {
        return gameRepo.updateGame(game);
    }

    public long deleteGame(Game game) {
        return gameRepo.deleteGame(game.getGid());
    }

    // write API in controller
    public Review createReview(Review r) throws GameNotFoundException {

        Game g = gameRepo.findById(r.getGameId());

        if (g == null) {
            throw new GameNotFoundException("No game record found");
        }

        r.setGameName(g.getName());
        r.setPosted(LocalDateTime.now());

        return reviewRepo.createReview(r);
    }

    // write API in controller
    public Review getReviewById(Integer reviewId) {
        return reviewRepo.getReviewById(reviewId);
    }

    // write API in controller
    public long updateReview(EditedComment ec) {
        Review result = reviewRepo.getReviewById(Integer.parseInt(ec.getCid()));
        List<EditedComment> list = result.getEdited();

        if (result.getEdited() == null) {
            list = new ArrayList<>();
            result.setEdited(list);
        }

        // add the old comment to edited
        EditedComment e = new EditedComment();
        e.setComment((result.getComment()));
        e.setRating(result.getRating());
        e.setPosted(result.getPosted());
        result.getEdited().add(e);

        // update the existing reviewId record with new comment & rating
        result.setComment(ec.getComment());
        result.setRating(ec.getRating());
        result.setPosted(LocalDateTime.now());
        return reviewRepo.updateReview(result);
    }
}
