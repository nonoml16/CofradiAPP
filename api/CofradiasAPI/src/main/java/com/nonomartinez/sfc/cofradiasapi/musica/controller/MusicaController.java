package com.nonomartinez.sfc.cofradiasapi.musica.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.nonomartinez.sfc.cofradiasapi.MyPage;
import com.nonomartinez.sfc.cofradiasapi.card.dto.PostCardDTO;
import com.nonomartinez.sfc.cofradiasapi.hermandad.dto.GetHermandadWebListDTO;
import com.nonomartinez.sfc.cofradiasapi.hermandad.dto.PostHermandadDTO;
import com.nonomartinez.sfc.cofradiasapi.hermandad.dto.PutHermandadDTO;
import com.nonomartinez.sfc.cofradiasapi.musica.dto.GetMusicaDTO;
import com.nonomartinez.sfc.cofradiasapi.musica.dto.GetMusicaListDTO;
import com.nonomartinez.sfc.cofradiasapi.musica.dto.PostMusicaDTO;
import com.nonomartinez.sfc.cofradiasapi.musica.model.TipoBanda;
import com.nonomartinez.sfc.cofradiasapi.musica.service.MusicaService;
import com.nonomartinez.sfc.cofradiasapi.musica.views.MusicaViews;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/musica")
public class MusicaController {

    private final MusicaService musicaService;

    @Operation(summary = "Obtiene los detalles de una musica")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Se ha encontrado la musica",
                    content = { @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = GetMusicaDTO.class)),
                            examples = {@ExampleObject(
                                    value = """
                                            {
                                                         "id": "a41349f7-a89d-4697-93b0-e810fe05993c",
                                                         "nombre": "Banda de Musica de Las Avestruces",
                                                         "anno": 2014,
                                                         "localidad": "Gines",
                                                         "hermandades": [
                                                             {
                                                                 "id": "2a822aaf-05e5-42d3-8600-069ec12adac9",
                                                                 "nombre": "Casita",
                                                                 "escudo": "amor.png"
                                                             }
                                                         ]
                                                     }
                                            """
                            )}
                    )}),
            @ApiResponse(responseCode = "404",
                    description = "No se ha encontrado ninguna musica",
                    content = @Content),
    })
    @GetMapping("/{id}")
    @JsonView(MusicaViews.MusicaDetails.class)
    public GetMusicaDTO getMusicaDetails(@PathVariable UUID id){

        return musicaService.getMusicaDetails(id);
    }

    @Operation(summary = "Obtiene el tipo de una musca")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Se ha encontrado la musica",
                    content = { @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = String.class)),
                            examples = {@ExampleObject(
                                    value = """
                                            "BANDA_MUSICA"
                                            """
                            )}
                    )}),
            @ApiResponse(responseCode = "404",
                    description = "No se ha encontrado ninguna musica",
                    content = @Content),
    })
    @GetMapping("/{id}/tipo")
    public TipoBanda getMusicaTipo(@PathVariable UUID id){

        return musicaService.getTipo(id);
    }

    @Operation(summary = "Obtiene todas las musicas")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Se han encontrado musicas",
                    content = { @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = GetMusicaListDTO.class)),
                            examples = {@ExampleObject(
                                    value = """
                                            {
                                                "result": [
                                                    {
                                                        "id": "67f4f333-a865-4770-9ce5-c960133677d9",
                                                        "nombre": "Banda de la Columna y Azotes",
                                                        "tipo": "BANDA_CCTT",
                                                        "numHermandades": 0
                                                    },
                                                    {
                                                        "id": "d9dda852-c8f4-4aef-90fe-e62edccfc054",
                                                        "nombre": "Banda de MÃºsica MarÃ­a Stma. Del RocÃ­o",
                                                        "tipo": "BANDA_MUSICA",
                                                        "numHermandades": 0
                                                    },
                                                ],
                                                "size": 20,
                                                "totalElements": 17,
                                                "pageNumber": 0,
                                                "first": true,
                                                "last": true
                                            }
                                            """
                            )}
                    )}),
            @ApiResponse(responseCode = "404",
                    description = "No se ha encontrado ninguna musica",
                    content = @Content),
    })
    @GetMapping("/")
    public MyPage<GetMusicaListDTO> getAllBandas(Pageable pageable){
        return musicaService.getAllBandasPaginado(pageable);
    }

    @Operation(summary = "Obtiene todas las musicas")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Se han encontrado musicas",
                    content = { @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = GetMusicaListDTO.class)),
                            examples = {@ExampleObject(
                                    value = """
                                            [
                                                {
                                                    "id": "67f4f333-a865-4770-9ce5-c960133677d9",
                                                    "nombre": "Banda de la Columna y Azotes",
                                                    "tipo": "BANDA_CCTT",
                                                    "numHermandades": 0
                                                },
                                                {
                                                    "id": "d9dda852-c8f4-4aef-90fe-e62edccfc054",
                                                    "nombre": "Banda de MÃºsica MarÃ­a Stma. Del RocÃ­o",
                                                    "tipo": "BANDA_MUSICA",
                                                    "numHermandades": 0
                                                },
                                            ]
                                            """
                            )}
                    )}),
            @ApiResponse(responseCode = "404",
                    description = "No se ha encontrado ninguna musica",
                    content = @Content),
    })
    @GetMapping("/web/")
    public List<GetMusicaListDTO> getAllBandasWeb(){
        return musicaService.getAllBandas();
    }

    @Operation(summary = "Obtiene todas las musicas por tipo")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Se han encontrado musicas",
                    content = { @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = GetMusicaListDTO.class)),
                            examples = {@ExampleObject(
                                    value = """
                                            [
                                                {
                                                    "id": "d9dda852-c8f4-4aef-90fe-e62edccfc054",
                                                    "nombre": "Banda de MÃºsica MarÃ­a Stma. Del RocÃ­o",
                                                    "tipo": "BANDA_MUSICA",
                                                    "numHermandades": 0
                                                },
                                                {
                                                    "id": "9a3ecec5-b3b0-4059-b933-18cd9e1e1135",
                                                    "nombre": "Banda de MÃºsica Santa Ana de Dos Hermanas",
                                                    "tipo": "BANDA_MUSICA",
                                                    "numHermandades": 0
                                                },
                                            ]
                                            """
                            )}
                    )}),
            @ApiResponse(responseCode = "404",
                    description = "No se ha encontrado ninguna musica",
                    content = @Content),
    })
    @GetMapping("/web/{tipoBanda}")
    public List<GetMusicaListDTO> getBandasTipoWeb(@PathVariable TipoBanda tipoBanda){
        return musicaService.getBandasPorTipo(tipoBanda);
    }

    @Operation(summary = "Obtiene todas las musicas por tipo")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Se han encontrado musicas",
                    content = { @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = GetMusicaListDTO.class)),
                            examples = {@ExampleObject(
                                    value = """
                                            {
                                                "result": [
                                                    {
                                                        "id": "d9dda852-c8f4-4aef-90fe-e62edccfc054",
                                                        "nombre": "Banda de MÃºsica MarÃ­a Stma. Del RocÃ­o",
                                                        "tipo": "BANDA_MUSICA",
                                                        "numHermandades": 0
                                                    },
                                                    {
                                                        "id": "9a3ecec5-b3b0-4059-b933-18cd9e1e1135",
                                                        "nombre": "Banda de MÃºsica Santa Ana de Dos Hermanas",
                                                        "tipo": "BANDA_MUSICA",
                                                        "numHermandades": 0
                                                    },
                                                ],
                                                "size": 20,
                                                "totalElements": 8,
                                                "pageNumber": 0,
                                                "first": true,
                                                "last": true
                                            }
                                            """
                            )}
                    )}),
            @ApiResponse(responseCode = "404",
                    description = "No se ha encontrado ninguna musica",
                    content = @Content),
    })
    @GetMapping("/tipo/{tipoBanda}")
    public MyPage<GetMusicaListDTO> getBandasTipo(@PathVariable TipoBanda tipoBanda, Pageable pageable){
        return musicaService.getBandasPorTipoPaginado(tipoBanda, pageable);
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "La musica se ha creado de forma correcta", content = {
                    @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = PostMusicaDTO.class)),
                            examples = {@ExampleObject(
                                    value = """
                                                {
                                                    "nombre": "Banda de Musica de Las Lagartijas",
                                                    "localidad": "San José de la Rinconada",
                                                    "tipo": "BANDA_MUSICA",
                                                    "annoFundacion": 2014
                                                }
                                            """
                            )}
                    )}),
            @ApiResponse(responseCode = "400",
                    description = "No se han aportado los datos correctamente",
                    content = @Content)
    })
    @Operation(summary = "Crear una nueva musica")
    @PostMapping("/nueva")
    public ResponseEntity<PostMusicaDTO> saveMusica(@RequestBody PostMusicaDTO postMusicaDTO){
        return ResponseEntity.status(201).body(musicaService.addMusica(postMusicaDTO));
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Edita la musica de forma correcta", content = {
                    @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = PostMusicaDTO.class)),
                            examples = {@ExampleObject(
                                    value = """
                                                {
                                                       "nombre": "Banda de Musica de Las Avestruces",
                                                       "localidad": "Gines",
                                                       "tipo": "BANDA_MUSICA",
                                                       "annoFundacion": 2014
                                                   }
                                            """
                            )}
                    )}),
            @ApiResponse(responseCode = "404",
                    description = "No se ha encontrado la hermandad",
                    content = @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = PostCardDTO.class)),
                            examples = {@ExampleObject(
                                    """
                                        {
                                            "error": "The Musica or the list of it could not be found" 
                                        }       
                                    """
                            )
                            }))
    })
    @Operation(summary = "Edita una musica", description = "Devuelve la musica editada")
    @PutMapping("/{id}")
    public ResponseEntity<GetMusicaDTO> editMusica(@PathVariable UUID id, @RequestBody PostMusicaDTO postMusicaDTO){
        return ResponseEntity.status(HttpStatus.CREATED).body(musicaService.edit(postMusicaDTO, id));
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
    @Operation(summary = "Borrar Musica", description = "Devuelve 202 Accepted si todo va bien ")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteMusica(@PathVariable UUID id){

        boolean borrado = musicaService.deleteMusica(id);
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
    @Operation(summary = "Borrar Musica de una Hermandad", description = "Devuelve 202 Accepted si todo va bien ")
    @DeleteMapping("/{idMusica}/hermandad/{idHermandad}")
    public ResponseEntity<?> deleteMusicaHermandad(@PathVariable UUID idMusica, @PathVariable UUID idHermandad){

        boolean borrado = musicaService.deleteMusicaHermandad(idMusica, idHermandad);
        if(borrado)
            return ResponseEntity.status(HttpStatus.ACCEPTED).build();
        else return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
}
