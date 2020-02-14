package atdd.station.web;

import atdd.station.domain.station.Station;
import atdd.station.domain.station.StationRepository;
import atdd.station.service.StationService;
import atdd.station.web.dto.StationRequestDto;
import atdd.station.web.dto.StationResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@RestController
public class StationController {

    @Autowired
    private StationService stationService;

    @PostMapping("/stationsTest")
    public @ResponseBody ResponseEntity stations(@RequestBody String inputJson) {
        Logger logger = Logger.getLogger("stationTest");
        logger.info(inputJson);

        URI location = URI.create("/stationsTest");
        return ResponseEntity.created(location)
                .body(inputJson);
    }

    @PostMapping("/stations")
    public ResponseEntity createStation(@RequestBody StationRequestDto stationRequestDto) {
        Logger logger = Logger.getLogger("createStation");
        URI location = URI.create("/stations/create");

       Station station = stationService.create(stationRequestDto);
       StationResponseDto stationResponseDto = new StationResponseDto(station);

        return ResponseEntity.created(location)
                .body(stationResponseDto);
    }

    @GetMapping("/stations")
    public ResponseEntity selectStationList(){

        List<StationResponseDto> stationResponseDtoList = stationService.select();

        return ResponseEntity.ok().body(stationResponseDtoList);
    }

    @GetMapping("/stations/{id}")
    public ResponseEntity selectStation(@PathVariable Long id) {

        stationService.create(StationRequestDto.builder().name("강남역").build());

        StationResponseDto stationResponseDto = stationService.findById(id);

        return  ResponseEntity.ok().body(stationResponseDto);
    }

    @DeleteMapping("/stations/{id}")
    public void deleteStation(@PathVariable Long id) {

        stationService.create(StationRequestDto.builder().name("강남역").build());

        stationService.delete(id);

    }
}
