package service;

import java.time.LocalDateTime;

public class DateServiceImpl implements DateService {

    @Override
    public LocalDateTime now() {
        return LocalDateTime.now();
    }
}
