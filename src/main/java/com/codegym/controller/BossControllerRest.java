package com.codegym.controller;


import com.codegym.dto.ResponseMessage;
import com.codegym.model.Boss;
import com.codegym.service.boss.IBossService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/apic")
public class BossControllerRest {

    @Autowired
    IBossService bossService;
    @GetMapping("/listBoss")
    public ResponseEntity<?> listBoss(){
        return new ResponseEntity<>( bossService.findAll(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> createBoss(@RequestBody Boss boss){
        bossService.save(boss);
        return new ResponseEntity<>(new ResponseMessage("create success!"), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateBoss(@PathVariable Long id, @RequestBody Boss boss){
        Optional<Boss> boss1 = bossService.findById(id);
        if(!boss1.isPresent()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        boss.setId(id);
        bossService.save(boss);
        return new ResponseEntity<>(new ResponseMessage("Update Success!"), HttpStatus.OK);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteBoss(@PathVariable Long id){
        Optional<Boss> staff = bossService.findById(id);
        if(!staff.isPresent()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
       bossService.remove(id);
        return new ResponseEntity<>(new ResponseMessage("Delete Success!"), HttpStatus.OK);
    }
    @GetMapping("/search")
    public ResponseEntity<?> findAllByName( String nickName){
        return new ResponseEntity<>(bossService.findAllByBossContaining(nickName),HttpStatus.OK);
    }
}
