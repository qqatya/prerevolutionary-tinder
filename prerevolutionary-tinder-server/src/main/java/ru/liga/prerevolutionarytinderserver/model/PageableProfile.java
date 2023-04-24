package ru.liga.prerevolutionarytinderserver.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PageableProfile {
    private List<Profile> content;
    private Integer totalPages;
    private Long totalElements;

}
