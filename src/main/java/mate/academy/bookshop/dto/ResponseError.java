package mate.academy.bookshop.dto;

import java.time.LocalDateTime;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ResponseError {
    private LocalDateTime timestamp;
    private List<String> errors;
}
