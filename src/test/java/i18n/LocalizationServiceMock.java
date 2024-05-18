package i18n;

import ru.netology.entity.Country;
import ru.netology.entity.Location;
import ru.netology.i18n.LocalizationService;

public class LocalizationServiceMock implements LocalizationService {
    private String locale;

    public void setLocale(String locale) {
        this.locale = locale;
    }

    @Override
    public String locale(Country country) {
        return locale;
    }
}
