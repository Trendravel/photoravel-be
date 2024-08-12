package trendravel.photoravel_be.domain.location.service;


import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import trendravel.photoravel_be.commom.image.service.ImageService;
import trendravel.photoravel_be.domain.location.dto.request.LocationRequestDto;
import trendravel.photoravel_be.domain.location.dto.response.LocationResponseDto;
import trendravel.photoravel_be.db.location.Location;
import trendravel.photoravel_be.db.respository.location.LocationRepository;
import trendravel.photoravel_be.domain.location.dto.response.LocationSingleReadResponseDto;
import trendravel.photoravel_be.domain.review.dto.response.RecentReviewsDto;

import java.util.List;
import java.util.Optional;


/**
 *  - User 연관관계는 아직 미설정
 */

@Service
@RequiredArgsConstructor
public class LocationService {

    private final LocationRepository locationRepository;
    private final ImageService imageService;

    public LocationResponseDto createLocation(
            LocationRequestDto locationRequestDto, List<MultipartFile> images) {
        Location location = locationRepository.save(Location.builder()
                .description(locationRequestDto.getDescription())
                .name(locationRequestDto.getName())
                .latitude(locationRequestDto.getLatitude())
                .longitude(locationRequestDto.getLongitude())
                .images(imageService.uploadImages(images))
                .address(locationRequestDto.getAddress())
                .views(0)
                .build());

        return LocationResponseDto
                .builder()
                .LocationId(location.getId())
                .description(location.getDescription())
                .name(location.getName())
                .latitude(location.getLatitude())
                .longitude(location.getLongitude())
                .images(location.getImages())
                .address(location.getAddress())
                .createdTime(location.getCreatedAt())
                .updatedTime(location.getUpdatedAt())
                .build();
    }

    public LocationResponseDto createLocation(
            LocationRequestDto locationRequestDto) {
        Location location = locationRepository.save(Location.builder()
                .description(locationRequestDto.getDescription())
                .name(locationRequestDto.getName())
                .latitude(locationRequestDto.getLatitude())
                .longitude(locationRequestDto.getLongitude())
                .address(locationRequestDto.getAddress())
                .views(0)
                .build());

        return LocationResponseDto
                .builder()
                .LocationId(location.getId())
                .description(location.getDescription())
                .name(location.getName())
                .latitude(location.getLatitude())
                .longitude(location.getLongitude())
                .address(location.getAddress())
                .createdTime(location.getCreatedAt())
                .updatedTime(location.getUpdatedAt())
                .build();
    }

    @Transactional
    public LocationSingleReadResponseDto readSingleLocation(Long id){
        Location location = locationRepository.findById(id).orElse(null);

        if(location == null){
            //예외처리
        }
        location.increaseViews();
        List<RecentReviewsDto> reviews = locationRepository.recentReviews(location.getId());

        return LocationSingleReadResponseDto.builder()
                .LocationId(location.getId())
                .latitude(location.getLatitude())
                .longitude(location.getLongitude())
                .address(location.getAddress())
                .name(location.getName())
                .description(location.getDescription())
                .updatedTime(location.getUpdatedAt())
                .createdTime(location.getCreatedAt())
                .images(location.getImages())
                .views(location.getViews())
                .ratingAvg(String.format("%.2f", ratingAverage(reviews)))
                .recentReviewDtos(reviews)
                .build();
    }

    private double ratingAverage(List<RecentReviewsDto> reviews) {
        double sum = 0;
        for (RecentReviewsDto review : reviews) {
            sum += review.getRating();
        }
        return sum / reviews.size();
    }


    @Transactional
    public LocationResponseDto updateLocation(
            LocationRequestDto locationRequestDto, List<MultipartFile> images) {

        Optional<Location> location = locationRepository.findById(
                locationRequestDto.getLocationId());

        if(location.isEmpty()){
            // 추후 Exception Controller 만들어 처리할 계획
        }
        location.get().updateLocation(locationRequestDto, imageService.uploadImages(images));


        return LocationResponseDto
                .builder()
                .LocationId(location.get().getId())
                .description(location.get().getDescription())
                .name(location.get().getName())
                .latitude(location.get().getLatitude())
                .longitude(location.get().getLongitude())
                .images(location.get().getImages())
                .address(location.get().getAddress())
                .createdTime(location.get().getCreatedAt())
                .updatedTime(location.get().getUpdatedAt())
                .build();
    }

    @Transactional
    public LocationResponseDto updateLocation(
            LocationRequestDto locationRequestDto) {

        Optional<Location> location = locationRepository.findById(
                locationRequestDto.getLocationId());

        if(location.isEmpty()){
            // 추후 Exception Controller 만들어 처리할 계획
        }
        location.get().updateLocation(locationRequestDto);


        return LocationResponseDto
                .builder()
                .LocationId(location.get().getId())
                .description(location.get().getDescription())
                .name(location.get().getName())
                .latitude(location.get().getLatitude())
                .longitude(location.get().getLongitude())
                .address(location.get().getAddress())
                .createdTime(location.get().getCreatedAt())
                .updatedTime(location.get().getUpdatedAt())
                .build();
    }

    public void deleteLocation(Long id){
        locationRepository.deleteById(id);
    }



}
