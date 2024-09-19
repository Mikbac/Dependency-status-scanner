package pl.mikbac.dependencystatusscanner.project.converter;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import pl.mikbac.dependencystatusscanner.project.data.ResponsePageData;

import java.util.List;

/**
 * Created by MikBac on 04.05.2024
 */

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class PageConverter {

    public static <T> ResponsePageData<T> getResponsePage(List<T> data,
                                                          final int pageNumber,
                                                          final int pageSize,
                                                          final long totalElements) {
        return ResponsePageData.<T>builder()
                .data(data)
                .pageNumber(pageNumber)
                .pageSize(pageSize)
                .totalElements(totalElements)
                .build();
    }

}
