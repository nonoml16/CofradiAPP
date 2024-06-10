package com.nonomartinez.sfc.cofradiasapi.hermandad.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.nonomartinez.sfc.cofradiasapi.MyPage;
import com.nonomartinez.sfc.cofradiasapi.hermandad.dto.GetHermandadDTO;
import com.nonomartinez.sfc.cofradiasapi.hermandad.dto.GetHermandadWebListDTO;
import com.nonomartinez.sfc.cofradiasapi.hermandad.dto.PostHermandadDTO;
import com.nonomartinez.sfc.cofradiasapi.hermandad.dto.PutHermandadDTO;
import com.nonomartinez.sfc.cofradiasapi.hermandad.model.Dias;
import com.nonomartinez.sfc.cofradiasapi.hermandad.service.HermandadService;
import com.nonomartinez.sfc.cofradiasapi.hermandad.views.HermandadViews;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;

@RestController
@RequiredArgsConstructor
@RequestMapping("/hermandades")
public class HermandadController {

    private final HermandadService hermandadService;

    @GetMapping("/")
    public MyPage<GetHermandadWebListDTO> getAllHdades(Pageable pageable){
        return hermandadService.getAllHermandadesPaginable(pageable);
    }

    @GetMapping("/web/")
    public List<GetHermandadWebListDTO> getAllHdadesWeb(){
        return hermandadService.getAllHermandades();
    }

    @GetMapping("/{dia}")
    @JsonView(HermandadViews.HermandadList.class)
    public List<GetHermandadDTO> getAllHermandadesFromDiaSalida(@PathVariable Dias dia){
        return hermandadService.getHermandadPorDia(dia);
    }

    @GetMapping("/web/{dia}")
    public List<GetHermandadWebListDTO> getAllHermandadesFromDiaSalidaWeb(@PathVariable Dias dia){
        return hermandadService.getHermandadPorDiaWeb(dia);
    }

    @GetMapping("/hermandad/{id}")
    @JsonView(HermandadViews.HermandadDetails.class)
    public GetHermandadDTO getHermandadId(@PathVariable UUID id){

        return hermandadService.getHermandad(id);
    }

    @PostMapping("/hermandad/nueva")
    public ResponseEntity<PostHermandadDTO> saveHermandad(@RequestBody PostHermandadDTO postHermandadDTO){
        return ResponseEntity.status(201).body(hermandadService.addHermandad(postHermandadDTO));
    }

    @PutMapping("/hermandad/editar/{id}")
    public ResponseEntity<GetHermandadDTO> editHermandad(@PathVariable UUID id, @RequestBody PutHermandadDTO putHermandadDTO){
        return ResponseEntity.status(HttpStatus.CREATED).body(hermandadService.edit(putHermandadDTO, id));
    }

    @DeleteMapping("/hermandad/{id}")
    public ResponseEntity<?> deleteHermandad(@PathVariable UUID id){

        boolean borrado = hermandadService.deleteHermandad(id);
        if(borrado)
            return ResponseEntity.status(HttpStatus.ACCEPTED).build();
        else return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
}
