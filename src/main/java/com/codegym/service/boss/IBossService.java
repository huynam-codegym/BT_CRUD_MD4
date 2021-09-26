package com.codegym.service.boss;

import com.codegym.model.Boss;
import com.codegym.service.IService;

public interface IBossService extends IService<Boss> {
    Iterable<Boss> findAllByBossContaining(String nickName);

}
