package com.codegym.repository;

import com.codegym.model.Boss;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface IBossRepository extends PagingAndSortingRepository<Boss,Long> {
    Iterable<Boss> findAllByBossContaining(String nickName);
}
