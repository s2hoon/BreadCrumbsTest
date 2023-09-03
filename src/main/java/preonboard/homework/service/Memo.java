package preonboard.homework.service;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
public class Memo {
    private int id;
    private String title;
    private String content;
    private int parentId;


}
