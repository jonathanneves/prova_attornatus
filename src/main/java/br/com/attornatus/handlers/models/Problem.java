package br.com.attornatus.handlers.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Problem {

    private Integer status;

    @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
    private LocalDateTime dateTime;
    private String title;
    private List<Field> fields;

    public Problem(Integer status, String title) {
        super();
        this.status = status;
        this.title = title;
        this.dateTime = LocalDateTime.now();
    }

    public Problem(Integer status, String title, List<Field> fields) {
        super();
        this.status = status;
        this.title = title;
        this.fields = fields;
        this.dateTime = LocalDateTime.now();
    }

}
