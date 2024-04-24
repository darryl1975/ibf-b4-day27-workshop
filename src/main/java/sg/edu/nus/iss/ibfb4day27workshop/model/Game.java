package sg.edu.nus.iss.ibfb4day27workshop.model;

import org.springframework.data.annotation.Id;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Game {

    @Id
    private Integer gid;
    private String name;
    private Integer year;
    private Integer rating;
    private Integer userRating;
    private String url;
    private String image;

    public JsonObject toJson() {
        return Json.createObjectBuilder()
                .add("gid", getGid())
                .add("name", getName())
                .add("year", getYear())
                .add("rating", getRating())
                .add("userRating", getUserRating())
                .add("url", getUrl())
                .add("image", getImage())
                .build();
    }
}
