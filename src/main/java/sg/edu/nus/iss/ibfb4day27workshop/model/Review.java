package sg.edu.nus.iss.ibfb4day27workshop.model;

import java.util.List;

import org.springframework.data.annotation.Id;

import jakarta.json.Json;
import jakarta.json.JsonArrayBuilder;
import jakarta.json.JsonObject;
import jakarta.json.JsonObjectBuilder;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Review extends EditedComment {

    @Id
    private Integer reviewId;
    private Integer gameId;
    private String gameName;
    private String user;

    private List<EditedComment> edited;

    public JsonObject toJson() {
        return Json.createObjectBuilder()
                .add("gameId", getGameId())
                .add("user", getUser())
                .add("gameName", getGameName())
                .add("rating", getRating())
                .add("comment", getComment())
                .add("posted", getPosted().toString())
                .build();

    }

    public JsonObject toJsonEdited() {
        boolean isEditedComments = false;

        JsonArrayBuilder arrBuilder = Json.createArrayBuilder();

        if (this.getEdited() != null) {
            List<JsonObjectBuilder> editedComments = this.getEdited()
                    .stream().map(t -> toJSONString())
                    .toList();

            if (editedComments.size() > 0) {
                isEditedComments = true;
            }
        }

        return Json.createObjectBuilder()
                .add("gameId", getGameId())
                .add("user", getUser())
                .add("gameName", getGameName())
                .add("rating", getRating())
                .add("comment", getComment())
                .add("posted", getPosted().toString())
                .add("edited", isEditedComments)
                .build();
    }

    public JsonObject toJsonEditedList() {

        JsonArrayBuilder arrBuilder = Json.createArrayBuilder();

        List<JsonObjectBuilder> editedComments = this.getEdited()
                .stream().map(t -> toJSONString())
                .toList();

        for(JsonObjectBuilder j : editedComments) {
            arrBuilder.add(j);
        }

        return Json.createObjectBuilder()
                .add("gameId", getGameId())
                .add("user", getUser())
                .add("gameName", getGameName())
                .add("rating", getRating())
                .add("comment", getComment())
                .add("posted", getPosted().toString())
                .add("edited", arrBuilder)
                .build();
    }
}
