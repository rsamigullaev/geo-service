package ru.netology.geo;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mock;
import ru.netology.entity.Country;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertThrows;


class GeoServiceTest {

    GeoService geoService;

    @ParameterizedTest
    @MethodSource("sourceForTest")
    void testByIp(String ip, Country expected) {
        GeoService geoService = new GeoServiceImpl();
        Country actual = geoService.byIp(ip).getCountry();

        Assertions.assertEquals(expected, actual);
    }

    @Test
    void testByCoordinates() {
        assertThrows(RuntimeException.class, () -> {
            geoService.byCoordinates();
        });

    }

    private static Stream<Arguments> sourceForTest() {
        return Stream.of(
                Arguments.of("172.564.41.25", Country.RUSSIA),
                Arguments.of("96.25.783.87", Country.USA));
    }
}
