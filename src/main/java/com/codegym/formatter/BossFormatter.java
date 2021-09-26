package com.codegym.formatter;

import com.codegym.model.Boss;
import com.codegym.service.boss.IBossService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.Formatter;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.util.Locale;
import java.util.Optional;

@Component
public class BossFormatter implements Formatter<Boss> {

    private IBossService bossService;

    @Autowired
    public BossFormatter(IBossService bossService) {
        this.bossService = bossService;
    }

    @Override
    public Boss parse(String text, Locale locale) throws ParseException {
        if (text!=null){
            Optional<Boss> provinceOptional = bossService.findById(Long.parseLong(text));
            return provinceOptional.orElse(null);
        }
        return null;
    }

    @Override
    public String print(Boss object, Locale locale) {
        return "[" + object.getId() + ", " +object.getNickName() + "]";
    }


}
