package demo.demo.payload;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.Date;
@NoArgsConstructor
public class ErrorDetailes {
    private String message;
    private Date date;
    private String Uri;

    public ErrorDetailes(String message, Date date, String description) {
    }
}
