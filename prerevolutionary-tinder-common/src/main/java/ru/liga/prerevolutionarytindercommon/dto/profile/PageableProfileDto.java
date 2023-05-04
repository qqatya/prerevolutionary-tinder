package ru.liga.prerevolutionarytindercommon.dto.profile;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PageableProfileDto {
    private List<ProfileDto> content;
    private Integer totalPages;
    private Long totalElements;

}
