package ru.liga.prerevolutionarytindercommon.dto.favorite;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PageableFavoriteDto {
    private List<FavoriteDto> content;
    private Integer totalPages;
    private Long totalElements;
}
