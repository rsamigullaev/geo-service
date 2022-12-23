package ru.netology.sender;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.netology.entity.Location;
import ru.netology.geo.GeoService;
import ru.netology.i18n.LocalizationService;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class MessageSenderTest {

    @Mock
    GeoService geoService;

    @Mock
    LocalizationService localizationService;

    @Mock
    Location location;

    @ParameterizedTest
    @MethodSource("sourceForTest")
    void testSend(String ip) {

        String expected = ip.startsWith("172.") ? "Добро пожаловать\n" : "Welcome\n";

        when(geoService.byIp(ip)).thenReturn(location);
        when(localizationService.locale(any())).thenReturn(expected);

        MessageSender messageSender = new MessageSenderImpl(geoService, localizationService);
        Map<String, String> headers = new HashMap<>();
        headers.put(MessageSenderImpl.IP_ADDRESS_HEADER, ip);
        String actual = messageSender.send(headers);

        Assertions.assertEquals(expected, actual);

    }
    private static Stream<Arguments> sourceForTest() {
        return Stream.of(
                Arguments.of("172.564.41.25"),
                Arguments.of("96.25.783.87"));
    }
}