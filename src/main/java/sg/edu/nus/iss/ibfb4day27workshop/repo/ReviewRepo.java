package sg.edu.nus.iss.ibfb4day27workshop.repo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import com.mongodb.client.result.UpdateResult;

import sg.edu.nus.iss.ibfb4day27workshop.model.Review;

@Repository
public class ReviewRepo {
    
    @Autowired
    MongoTemplate mt;

    // write create review function (using mongotemplate.insert)

    // get review by id

    // get review by user


    public long updateReview(Review r) {
        Query query = new Query();
        query.addCriteria(Criteria.where("_id").is(r.getCid()));

        Update updateOps = new Update()
        .set("rating", r.getRating())
        .set("comment", r.getComment())
        .set("posted", r.getPosted())
        .set("edited", r.getEdited());

        UpdateResult updateResults = mt.updateMulti(query, updateOps, Review.class, "comments");

        return updateResults.getModifiedCount();
    }
}
