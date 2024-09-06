package trendravel.photoravel_be.db.review;


import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import trendravel.photoravel_be.db.BaseEntity;
import trendravel.photoravel_be.db.guide.Guide;
import trendravel.photoravel_be.db.location.Location;
import trendravel.photoravel_be.db.spot.Spot;
import trendravel.photoravel_be.domain.review.dto.request.ReviewRequestDto;
import trendravel.photoravel_be.db.review.enums.ReviewTypes;
import trendravel.photoravel_be.domain.review.dto.request.ReviewUpdateImagesDto;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@Table(name = "REVIEW")
public class Review extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "review_id")
    private Long id;

    @Enumerated(EnumType.STRING)
    private ReviewTypes reviewType;

    @Column(length = 500)
    private String content;

    @NotNull(message = "필수 입력사항입니다.")
    private Double rating;

    @ElementCollection
    @CollectionTable(
            name = "review_images",
            joinColumns = @JoinColumn(name = "review_id")
    )
    @Builder.Default
    private List<String> images = new ArrayList<>();

    // 회원, 가이드 관련 연관관계 필드 추가 필요


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "location_id")
    private Location locationReview;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "spot_id")
    private Spot spotReview;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "guide_id")
    private Guide guideReview;



    //연관관계 편의 메소드
    public void setSpotReview(Spot spot) {
        this.spotReview = spot;
        spot.getReviews().add(this);
    }

    public void setLocationReview(Location location) {
        this.locationReview = location;
        location.getReview().add(this);
    }

    public void updateReview(ReviewUpdateImagesDto review, List<String> newImages) {
        this.content = review.getContent();
        this.rating = review.getRating();
        for (String deleteImage : review.getDeleteImages()) {
            this.images.remove(deleteImage);
        }
        this.images.addAll(newImages);
    }

    public void updateReview(ReviewRequestDto review) {
        this.content = review.getContent();
        this.rating = review.getRating();
    }

}
