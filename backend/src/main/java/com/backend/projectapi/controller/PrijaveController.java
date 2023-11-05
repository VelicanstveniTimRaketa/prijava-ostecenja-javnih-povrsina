package com.backend.projectapi.controller;
import com.backend.projectapi.DTO.PrijavaDTO;
import com.backend.projectapi.ResponseData;
import com.backend.projectapi.model.Prijava;
import com.backend.projectapi.service.KorisnikService;
import com.backend.projectapi.service.PrijavaService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.*;
import java.io.IOException;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api")
public class PrijaveController {


    private final PrijavaService prijavaService;

    public PrijaveController(PrijavaService prijavaService, KorisnikService korisnikService){
        this.prijavaService=prijavaService;
    }

    @GetMapping("/prijave")
    public ResponseEntity<ResponseData<List<Prijava>>> getAllPrijave(@RequestParam(required = false) Long kreatorId,
                                                                     @RequestParam(required = false) String active,
                                                                     @RequestParam(required = false) Long parent_id,
                                                                     @RequestParam(required = false) @DateTimeFormat(pattern="yyyy-MM-dd") Date dateFrom,
                                                                     @RequestParam(required = false) @DateTimeFormat(pattern="yyyy-MM-dd") Date dateTo,
                                                                     @RequestParam(required = false) Double lat,
                                                                     @RequestParam(required = false) Double lng,
                                                                     @RequestParam(required = false) Long... ostecenjeId){
        return new ResponseEntity<>(ResponseData.success(prijavaService.getAllPrijave(kreatorId,active,parent_id,dateFrom,dateTo,lat,lng, ostecenjeId)), HttpStatus.OK);
    }

    @GetMapping("/prijave/{id}")
    public ResponseEntity<Object> getPrijavaById(@PathVariable("id") Long id){
        return new ResponseEntity<>(ResponseData.success(prijavaService.findById(id)),HttpStatus.FOUND);
    }

    @PatchMapping("/makeChild")
    public ResponseEntity<Object> makeChildPrijavu(@RequestParam Long parent_id, @RequestParam Long child_id){
        return new ResponseEntity<>(ResponseData.success(prijavaService.makeChildPrijavu(parent_id,child_id)),HttpStatus.OK);
    }

    @PostMapping("/addPrijava")
    public ResponseEntity<Object> addPrijave(@ModelAttribute PrijavaDTO prijavaDTO, HttpServletRequest req) throws IOException {
        System.out.println("u controlleru smo");
        return new ResponseEntity<>(ResponseData.success(prijavaService.addPrijave(prijavaDTO,req)),HttpStatus.OK);
    }

    @DeleteMapping("/deletePrijava")
    public ResponseEntity<Object> deletePrijava(@RequestParam Long id){
        return new ResponseEntity<>(ResponseData.success(prijavaService.deletePrijava(id)), HttpStatus.OK);
    }
}
