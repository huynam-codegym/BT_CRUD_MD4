package com.codegym.service.boss;

import com.codegym.model.Boss;
import com.codegym.repository.IBossRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class BossService implements IBossService{
    @Autowired
    IBossRepository bossRepository;

    @Override
    public Iterable<Boss> findAll() {
        return bossRepository.findAll();
    }

    @Override
    public Optional<Boss> findById(Long id) {
        return bossRepository.findById(id);
    }

    @Override
    public void save(Boss boss) {
        bossRepository.save(boss);
    }

    @Override
    public void remove(Long id) {
        bossRepository.deleteById(id);
    }

    @Override
    public Iterable<Boss> findAllByBossContaining(String nickName) {
        return bossRepository.findAllByBossContaining(nickName);
    }


}
