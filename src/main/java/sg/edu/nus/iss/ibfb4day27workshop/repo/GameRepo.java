package sg.edu.nus.iss.ibfb4day27workshop.repo;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;

import sg.edu.nus.iss.ibfb4day27workshop.model.Game;

@Repository
public class GameRepo {

    @Autowired
    MongoTemplate mt;

    public List<Game> getAll() {
        List<Game> gameList = mt.findAll(Game.class, "games");
        return gameList;
    }

    public List<Game> getPaginatedGameList(int limit, int offset) {
        Query query = new Query();
        query.skip(limit * offset);
        query.limit(limit);

        List<Game> gameList = mt.find(query, Game.class, "games");
        return gameList;
    }

    public Game createGame(Game g) {
        Game createdGame = mt.save(g);
        return createdGame;
    }

    public long updateGame(Game g) {
        Query query = Query.query(Criteria.where("_id").is(g.getGid()));

        Update updateOps = new Update()
                .set("name", g.getName())
                .set("rating", g.getRating())
                .set("userRating", g.getUserRating())
                .set("year", g.getYear());

        UpdateResult updateResults = mt.updateMulti(query, updateOps, Game.class, "games");

        return updateResults.getModifiedCount();
    }

    public long deleteGame(int id) {
        Query query = Query.query(Criteria.where("_id").is(id));

        DeleteResult results = mt.remove(query, "games");

        return results.getDeletedCount();
    }

    public List<Game> findGameByName(String name) {
        Query query = Query.query(Criteria.where("name").is(name));

        return  mt.find(query, Game.class, "games");
    }


 }
