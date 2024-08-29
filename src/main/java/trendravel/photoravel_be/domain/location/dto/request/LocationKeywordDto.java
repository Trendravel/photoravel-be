package trendravel.photoravel_be.domain.location.dto.request;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.MediaType;

@Data
@AllArgsConstructor
@Schema(description = "(현재 위치 + 검색 키워드) 주변 장소 검색 요청 DTO",
        contentEncoding = MediaType.APPLICATION_JSON_VALUE)
public class LocationKeywordDto {

    @Schema(description = "경도")
    private double latitude;
    @Schema(description = "위도")
    private double longitude;
    @Schema(description = "검색 범위 (미터 단위)")
    private double range;
    @Schema(description = "검색 키워드")
    private String keyword;

}