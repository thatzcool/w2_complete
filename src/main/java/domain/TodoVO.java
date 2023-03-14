package domain;


import lombok.*;

import java.time.LocalDate;

@Getter   //읽기 전용 데이터객체
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class TodoVO {
    private Long tno;
    private String title;
    private LocalDate dueDate;
    private boolean finished;
}
