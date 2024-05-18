package sender;

import geo.GeoServiceMock;
import i18n.LocalizationServiceMock;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.EnumSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mockito;
import ru.netology.entity.Country;
import ru.netology.entity.Location;
import ru.netology.geo.GeoServiceImpl;
import ru.netology.i18n.LocalizationService;
import ru.netology.geo.GeoService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.netology.i18n.LocalizationServiceImpl;
import ru.netology.sender.MessageSender;
import ru.netology.sender.MessageSenderImpl;

import java.util.HashMap;
import java.util.Map;

public class MessageSenderTestWithCustomMocks {

    @Test
    void test_RussianLocalization_with_RussianIP() {
        GeoServiceMock geoServiceMock = new GeoServiceMock();
        geoServiceMock.setLocation(new Location("Moscow", Country.RUSSIA, null, 0));

        LocalizationServiceMock localizationServiceMock = new LocalizationServiceMock();
        localizationServiceMock.setLocale("Добро пожаловать");

        MessageSenderImpl messageSender = new MessageSenderImpl(geoServiceMock, localizationServiceMock);
        Map<String, String> headers = new HashMap<String, String>();
        headers.put(MessageSenderImpl.IP_ADDRESS_HEADER, "172.123.12.19");

        String expected = "Добро пожаловать";

        String actual = messageSender.send(headers);

        Assertions.assertEquals(expected, actual);
    }

    @Test
    void test_USLocalization_with_USIp() {
        GeoService geoService = Mockito.mock(GeoService.class);
        LocalizationService localizationService = Mockito.mock(LocalizationService.class);

        Mockito.when(geoService.byIp("96.44.183.149")).thenReturn(new Location("New York", Country.USA, null,  0));
        Mockito.when(localizationService.locale(Country.USA)).thenReturn("Welcome");

        MessageSenderImpl messageSender = new MessageSenderImpl(geoService, localizationService);
        Map<String, String> headers = new HashMap<String, String>();
        headers.put(MessageSenderImpl.IP_ADDRESS_HEADER, "96.44.183.149");

        String expected = "Welcome";

        String actual = messageSender.send(headers);

        Assertions.assertEquals(expected, actual);
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "127.0.0.1",
            "172.0.32.11",
            "96.44.183.149",
            "172.0.0.0",
            "96.0.0.0",
            "50.0.0.0"
    })
    void test_Location_byIp(String ip) {
        GeoService geoService = new GeoServiceImpl();
        Location result = geoService.byIp(ip);

        Assertions.assertEquals(result,  new Location("Moscow", Country.RUSSIA, "Lenina", 15));
    }

    @ParameterizedTest
    @EnumSource(Country.class)
    void test_locale_with_Country(Country country) {
        LocalizationService localizationService = new LocalizationServiceImpl();
        String result = localizationService.locale(country);

        Assertions.assertEquals(result, "Welcome");
    }
}
