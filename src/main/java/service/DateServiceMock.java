package service;

import java.time.LocalDateTime;

public class DateServiceMock implements DateService {

    public static final LocalDateTime MOCKED_DATE = LocalDateTime.parse("2018-01-01T10:00:01");

    @Override
    public LocalDateTime now() {
        return MOCKED_DATE;
    }
}
