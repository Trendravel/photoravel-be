package trendravel.photoravel_be.domain.location.dto.response;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;
import org.springframework.http.MediaType;
import trendravel.photoravel_be.db.enums.Category;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@Schema(description = "장소 생성/수정(이미지 미포함) 응답 DTO",
        contentEncoding = MediaType.APPLICATION_JSON_VALUE)
public class LocationResponseDto {

    @Schema(description = "장소ID")
    private Long LocationId;
    @Schema(description = "위도")
    private double latitude;
    @Schema(description = "경도")
    private double longitude;
    @Schema(description = "주소")
    private String address;
    @Schema(description = "장소 설명")
    private String description;
    @Schema(description = "장소명")
    private String name;
    @Schema(description = "장소 이미지들")
    private List<String> images;


    @Schema(description = "장소 작성 유저")
    private String userName;

    @Schema(description = "장소 생성일")
    private LocalDateTime createdAt;
    @Schema(description = "장소 수정일")
    private LocalDateTime updatedAt;

    @Schema(description = "카테고리")
    private String category;

}
