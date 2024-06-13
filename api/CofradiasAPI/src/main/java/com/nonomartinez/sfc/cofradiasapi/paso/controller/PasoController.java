package com.nonomartinez.sfc.cofradiasapi.paso.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.nonomartinez.sfc.cofradiasapi.card.dto.PostCardDTO;
import com.nonomartinez.sfc.cofradiasapi.musica.dto.GetMusicaListDTO;
import com.nonomartinez.sfc.cofradiasapi.musica.dto.PostMusicaDTO;
import com.nonomartinez.sfc.cofradiasapi.paso.dto.GetPasoDTO;
import com.nonomartinez.sfc.cofradiasapi.paso.dto.PostPasoDTO;
import com.nonomartinez.sfc.cofradiasapi.paso.dto.PutPasoDTO;
import com.nonomartinez.sfc.cofradiasapi.paso.service.PasoService;
import com.nonomartinez.sfc.cofradiasapi.paso.views.PasoViews;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/pasos")
public class PasoController {

    private final PasoService pasoService;

    @Operation(summary = "Obtiene los detalles de un paso por id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Se ha el paso",
                    content = { @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = GetPasoDTO.class)),
                            examples = {@ExampleObject(
                                    value = """
                                            {
                                                 "id": "cce03820-e440-40db-b42e-419572c5dedf",
                                                 "imagen": "Nuestra SeÃ±ora del Socorro",
                                                 "capataz": "Manolo",
                                                 "numCostaleros": 150,
                                                 "acompannamiento": [
                                                     {
                                                         "id": "3f2b4699-eaf7-43c3-99c2-a9b1aa22e20c",
                                                         "nombre": "AM RedenciÃ³n"
                                                     }
                                                 ],
                                                 "hermandad": "Casita",
                                                 "imagenes": [
                                                     "amor3.png",
                                                     "amor4.png"
                                                 ]
                                             }
                                            """
                            )}
                    )}),
            @ApiResponse(responseCode = "404",
                    description = "No se ha encontrado ningun paso",
                    content = @Content),
    })
    @GetMapping("/paso/{id}")
    @JsonView(PasoViews.PasoDetailsView.class)
    public GetPasoDTO getPasoId(@PathVariable UUID id){

        return pasoService.getPaso(id);
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "El paso se ha creado de forma correcta", content = {
                    @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = PostPasoDTO.class)),
                            examples = {@ExampleObject(
                                    value = """
                                                {
                                                    "imagen": "Tumbaito",
                                                    "capataz": "Manolo",
                                                    "numCostaleros": 45
                                                }
                                            """
                            )}
                    )}),
            @ApiResponse(responseCode = "400",
                    description = "No se han aportado los datos correctamente",
                    content = @Content)
    })
    @Operation(summary = "Crear un nuevo paso")
    @PostMapping("/paso/nuevo/{idHermandad}")
    public ResponseEntity<PostPasoDTO> addPasoHermandad(@PathVariable UUID idHermandad, @RequestBody PostPasoDTO postPasoDTO){
        return ResponseEntity.status(201).body(pasoService.addPaso(postPasoDTO, idHermandad));
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Edita el paso de forma correcta", content = {
                    @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = PutPasoDTO.class)),
                            examples = {@ExampleObject(
                                    value = """
                                                {
                                                    "capataz": "Manolo",
                                                    "numCostaleros": 150
                                                }
                                            """
                            )}
                    )}),
            @ApiResponse(responseCode = "404",
                    description = "No se ha encontrado el paso",
                    content = @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = PutPasoDTO.class)),
                            examples = {@ExampleObject(
                                    """
                                        {
                                            "error": "The Paso or the list of it could not be found" 
                                        }       
                                    """
                            )
                            }))
    })
    @Operation(summary = "Edita un paso", description = "Devuelve el paso editado")
    @PutMapping("/paso/editar/{id}")
    public ResponseEntity<GetPasoDTO> editHermandad(@PathVariable UUID id, @RequestBody PutPasoDTO putPasoDTO){
        return ResponseEntity.status(HttpStatus.CREATED).body(pasoService.edit(putPasoDTO, id));
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "202",
                    description = "El paso se ha borrado correctamente",
                    content = @Content
            ),
            @ApiResponse(responseCode = "404",
                    description = "No encontrado el paso a borrar",
                    content = @Content)
    })
    @Operation(summary = "Borrar Paso", description = "Devuelve 202 Accepted si todo va bien ")
    @DeleteMapping("/paso/{id}")
    public ResponseEntity<?> deletePaso(@PathVariable UUID id){

        boolean borrado = pasoService.deletePaso(id);
        if(borrado)
            return ResponseEntity.status(HttpStatus.ACCEPTED).build();
        else return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "202",
                    description = "La musica se ha borrado correctamente",
                    content = @Content
            ),
            @ApiResponse(responseCode = "404",
                    description = "No encontrado la musica a borrar",
                    content = @Content)
    })
    @Operation(summary = "Borrar Musica de Paso", description = "Devuelve 202 Accepted si todo va bien ")
    @DeleteMapping("/paso/{idPaso}/musica/{idMusica}")
    public ResponseEntity<?> deleteMusicaPaso(@PathVariable UUID idPaso, @PathVariable UUID idMusica){

        boolean borrado = pasoService.deleteMusicaPaso(idPaso, idMusica);
        if(borrado)
            return ResponseEntity.status(HttpStatus.ACCEPTED).build();
        else return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "La musica se ha añadido correctamente",
                    content = @Content
            ),
            @ApiResponse(responseCode = "404",
                    description = "No encontrado la musica a añadir",
                    content = @Content)
    })
    @Operation(summary = "Añadir Musica a Paso", description = "Devuelve 200 OK si todo va bien ")
    @PostMapping("/paso/{idPaso}/musica/{idMusica}")
    public ResponseEntity<?> addMusicaPaso(@PathVariable UUID idPaso, @PathVariable UUID idMusica){

        boolean agregado = pasoService.addMusicaPaso(idPaso, idMusica);
        if(agregado)
            return ResponseEntity.status(HttpStatus.OK).build();
        else return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
}
