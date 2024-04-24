package sg.edu.nus.iss.ibfb4day27workshop.model;

import java.time.LocalDateTime;

import jakarta.json.Json;
import jakarta.json.JsonObjectBuilder;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EditedComment {

    private String cid;
    private Integer rating;
    private String comment;
    private LocalDateTime posted;

    public JsonObjectBuilder toJSONString() {
        return Json.createObjectBuilder()
                .add("rating", getRating())
                .add("comment", getComment())
                .add("posted", getPosted().toString());
    }
}
