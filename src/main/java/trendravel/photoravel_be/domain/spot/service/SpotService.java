package trendravel.photoravel_be.domain.spot.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import trendravel.photoravel_be.domain.spot.dto.request.SpotRequestDto;
import trendravel.photoravel_be.domain.spot.dto.response.SpotResponseDto;
import trendravel.photoravel_be.db.spot.Spot;
import trendravel.photoravel_be.db.respository.LocationRepository;
import trendravel.photoravel_be.db.respository.SpotRepository;
import trendravel.photoravel_be.commom.service.ImageService;

import java.util.List;
import java.util.Optional;

/**
 *  - User 연관관계는 아직 미설정
 */

@Service
@RequiredArgsConstructor
public class SpotService {

    private final SpotRepository spotRepository;
    private final LocationRepository locationRepository;
    private final ImageService imageService;

    public SpotResponseDto createSpot(
            SpotRequestDto spotRequestDto, List<MultipartFile> images) {
        Spot spot = Spot.builder()
                .description(spotRequestDto.getDescription())
                .title(spotRequestDto.getTitle())
                .latitude(spotRequestDto.getLatitude())
                .longitude(spotRequestDto.getLongitude())
                .images(imageService.uploadImages(images))
                .location(locationRepository.findById(spotRequestDto.
                        getLocationId()).get())
                .build();

        spotRepository.save(spot);

        return SpotResponseDto
                .builder()
                .spotId(spot.getId())
                .description(spot.getDescription())
                .latitude(spot.getLatitude())
                .longitude(spot.getLongitude())
                .images(spot.getImages())
                .title(spot.getTitle())
                .createdTime(spot.getCreatedAt())
                .updatedTime(spot.getUpdatedAt())
                .build();
    }

    public SpotResponseDto createSpot(
            SpotRequestDto spotRequestDto) {
        Spot spot = Spot.builder()
                .description(spotRequestDto.getDescription())
                .title(spotRequestDto.getTitle())
                .latitude(spotRequestDto.getLatitude())
                .longitude(spotRequestDto.getLongitude())
                .location(locationRepository.findById(spotRequestDto.
                        getLocationId()).get())
                .build();

        spotRepository.save(spot);

        return SpotResponseDto
                .builder()
                .spotId(spot.getId())
                .description(spot.getDescription())
                .latitude(spot.getLatitude())
                .longitude(spot.getLongitude())
                .title(spot.getTitle())
                .createdTime(spot.getCreatedAt())
                .updatedTime(spot.getUpdatedAt())
                .build();
    }


    @Transactional
    public SpotResponseDto updateSpot(
            SpotRequestDto spotRequestDto, List<MultipartFile> images) {

        Optional<Spot> spot = spotRepository.findById(
                spotRequestDto.getSpotId());

        if(spot.isEmpty()){
            // 추후 Exception Controller 만들어 처리할 계획
        }
        spot.get().updateSpot(spotRequestDto, imageService.uploadImages(images));

        return SpotResponseDto
                .builder()
                .spotId(spot.get().getId())
                .title(spot.get().getTitle())
                .description(spot.get().getDescription())
                .latitude(spot.get().getLatitude())
                .longitude(spot.get().getLongitude())
                .images(spot.get().getImages())
                .createdTime(spot.get().getCreatedAt())
                .updatedTime(spot.get().getUpdatedAt())
                .build();
    }

    @Transactional
    public SpotResponseDto updateSpot(
            SpotRequestDto spotRequestDto) {

        Optional<Spot> spot = spotRepository.findById(
                spotRequestDto.getSpotId());

        if(spot.isEmpty()){
            // 추후 Exception Controller 만들어 처리할 계획
        }
        spot.get().updateSpot(spotRequestDto);

        return SpotResponseDto
                .builder()
                .spotId(spot.get().getId())
                .title(spot.get().getTitle())
                .description(spot.get().getDescription())
                .latitude(spot.get().getLatitude())
                .longitude(spot.get().getLongitude())
                .createdTime(spot.get().getCreatedAt())
                .updatedTime(spot.get().getUpdatedAt())
                .build();
    }

    public void deleteSpot(Long id){
        spotRepository.deleteById(id);
    }


}