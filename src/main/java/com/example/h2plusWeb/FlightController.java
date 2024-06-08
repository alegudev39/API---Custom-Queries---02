package com.example.h2plusWeb;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@RestController
@RequestMapping("/flights")
public class FlightController {

    @Autowired
    private FlightRepository flightRepository;

    @PostMapping("/provision")
    public List<Flight> provisionFlights(@RequestParam(required = false, defaultValue = "100") int n) {
        Random random = new Random();
        List<Flight> flights = IntStream.range(0, n)
                .mapToObj(i -> createRandomFlight(random))
                .collect(Collectors.toList());

        return flightRepository.saveAll(flights);
    }

    @GetMapping
    public Page<Flight> getAllFlights(@RequestParam(defaultValue = "0") int page,
                                      @RequestParam(defaultValue = "10") int size) {
        return flightRepository.findAll(PageRequest.of(page, size, Sort.by("fromAirport").ascending()));
    }

    @GetMapping("/ontime")
    public List<Flight> getOntimeFlights() {
        return flightRepository.findByStatus(FlightStatus.ONTIME);
    }

    @GetMapping("/status")
    public List<Flight> getFlightsByStatuses(@RequestParam FlightStatus p1, @RequestParam FlightStatus p2) {
        return flightRepository.findByStatuses(p1, p2);
    }

    private Flight createRandomFlight(Random random) {
        return new Flight(
                generateRandomString(random),
                generateRandomString(random),
                generateRandomString(random),
                FlightStatus.values()[random.nextInt(FlightStatus.values().length)]
        );
    }

    private String generateRandomString(Random random) {
        return random.ints(97, 123)
                .limit(10)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
    }
}
