package com.backend.projectapi.controller;
import com.backend.projectapi.DTO.PrijavaDTO;
import com.backend.projectapi.ResponseData;
import com.backend.projectapi.model.Korisnik;
import com.backend.projectapi.model.Prijava;
import com.backend.projectapi.response.PrijavaResponse;
import com.backend.projectapi.service.KorisnikService;
import com.backend.projectapi.service.PrijavaService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

import java.time.ZonedDateTime;
import java.util.Arrays;
import java.util.List;

@RestController
public class PrijaveController extends ApplicationController {


    private final PrijavaService prijavaService;

    public PrijaveController(PrijavaService prijavaService, KorisnikService korisnikService){
        this.prijavaService=prijavaService;
    }

    @GetMapping("/prijave")
    public ResponseEntity<ResponseData<List<Prijava>>> getAllPrijave(@RequestParam(required = false) Long kreatorId,
                                                                             @RequestParam(required = false) String active,
                                                                             @RequestParam(required = false) Long parent_id,
                                                                             @RequestParam(required = false) ZonedDateTime dateFrom,
                                                                             @RequestParam(required = false) ZonedDateTime dateTo,
                                                                             @RequestParam(required = false) Double lat,
                                                                             @RequestParam(required = false) Double lng,
                                                                             @RequestParam(required = false) Long uredId,
                                                                             @RequestParam(required = false) Long... ostecenjeId){

        return new ResponseEntity<>(ResponseData.success(prijavaService.getAllPrijave(kreatorId,active,parent_id,dateFrom,dateTo,lat,lng,uredId, ostecenjeId)), HttpStatus.OK);
    }

    @GetMapping("/mojePrijave")
    public ResponseEntity<ResponseData<List<Prijava>>> getMojePrijave(){
        return new ResponseEntity<>(ResponseData.success(prijavaService.getMojePrijave(currentUser.getId())),HttpStatus.OK);
    }

    @GetMapping("/prijave/{id}")
    public ResponseEntity<ResponseData<PrijavaResponse>> getPrijavaById(@PathVariable("id") Long id, Model model){
        System.out.println(currentUser.getId());
        return new ResponseEntity<>(ResponseData.success(prijavaService.findById(id)),HttpStatus.OK);
    }

    @PatchMapping("/makeChild")
    public ResponseEntity<Object> makeChildPrijavu(@RequestParam Long parent_id, @RequestParam Long child_id){
        return new ResponseEntity<>(ResponseData.success(prijavaService.makeChildPrijavu(parent_id,child_id)),HttpStatus.OK);
    }

    @PostMapping("/addPrijava")
    public ResponseEntity<Object> addPrijave(@ModelAttribute PrijavaDTO prijavaDTO, HttpServletRequest req) throws IOException {
        System.out.println("u controlleru smo");
        System.out.println(prijavaDTO.getOpis());
        System.out.println(prijavaDTO.getNaziv());
        System.out.println(prijavaDTO.getUred());
        System.out.println(prijavaDTO.getLatitude());
        System.out.println(prijavaDTO.getLongitude());
        return new ResponseEntity<>(ResponseData.success(prijavaService.addPrijave(prijavaDTO,req,currentUser)),HttpStatus.OK);
    }

    @DeleteMapping("/deletePrijava")
    public ResponseEntity<Object> deletePrijava(@RequestParam Long id){
        boolean status = prijavaService.deletePrijava(id);
        return new ResponseEntity<>(status ? ResponseData.success("Prijava obrisana") : ResponseData.error(Arrays.asList("Prijava neuspjesno obrisana")), HttpStatus.OK);
    }

    @PatchMapping("/updatePrijava")
    public ResponseEntity<Object> updatePrijava(@RequestParam Long id, @RequestParam PrijavaDTO prijavaDTO,@RequestParam MultipartFile[] slike){
        return new ResponseEntity<>(ResponseData.success(prijavaService.updatePrijava(id,prijavaDTO,slike)),HttpStatus.OK);
    }


    //todo mogu joj pristupiti ADMIN i korisnici ureda od te prijave
    @PatchMapping("/dovrsiPrijavu")
    public ResponseEntity<Object> dovrsiPrijavu(@RequestParam Long id){
        return new ResponseEntity<>(ResponseData.success(prijavaService.dovrsiPrijavu(id)),HttpStatus.OK);
    }
}
