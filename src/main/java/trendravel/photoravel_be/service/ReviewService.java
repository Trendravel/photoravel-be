package trendravel.photoravel_be.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import trendravel.photoravel_be.dto.request.ReviewRequestDto;
import trendravel.photoravel_be.dto.response.domain.ReviewResponseDto;
import trendravel.photoravel_be.entity.Review;
import trendravel.photoravel_be.entity.enums.ReviewTypes;
import trendravel.photoravel_be.repository.LocationRepository;
import trendravel.photoravel_be.repository.ReviewRepository;
import trendravel.photoravel_be.repository.SpotRepository;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final SpotRepository spotRepository;
    private final LocationRepository locationRepository;

    public ReviewResponseDto createReview(
            ReviewRequestDto reviewRequestDto) {


        Review review = reviewRepository.save(Review.builder()
                .reviewType(reviewRequestDto.getReviewType())
                .images(reviewRequestDto.getImages())
                .content(reviewRequestDto.getContent())
                .rating(reviewRequestDto.getRating())
                .locationReview(ReviewTypes.LOCATION ==
                        reviewRequestDto.getReviewType()
                        ? locationRepository.
                        findById(reviewRequestDto.getTypeId())
                        .orElse(null) : null)
                .spotReview(ReviewTypes.SPOT ==
                        reviewRequestDto.getReviewType()
                        ? spotRepository.findById(reviewRequestDto.getTypeId())
                        .orElse(null) : null)
                .build());

        return ReviewResponseDto
                .builder()
                .ReviewId(review.getId())
                .images(review.getImages())
                .rating(review.getRating())
                .content(review.getContent())
                .createdTime(review.getCreatedTime())
                .updatedTime(review.getUpdatedTime())
                .build();
    }


    @Transactional
    public ReviewResponseDto updateReview(
            ReviewRequestDto reviewRequestDto) {

        Optional<Review> review = reviewRepository.findById(
                reviewRequestDto.getReviewId());


        if(review.isEmpty()){
            // 추후 Exception Controller 만들어 처리할 계획
        }
        review.get().updateReview(reviewRequestDto);

        return ReviewResponseDto
                .builder()
                .ReviewId(review.get().getId())
                .images(review.get().getImages())
                .rating(review.get().getRating())
                .content(review.get().getContent())
                .createdTime(review.get().getCreatedTime())
                .updatedTime(review.get().getUpdatedTime())
                .build();
    }

    public void deleteReview(Long id){
        reviewRepository.deleteById(id);
    }

}