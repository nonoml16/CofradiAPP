package com.nonomartinez.sfc.cofradiasapi.hermandad.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.nonomartinez.sfc.cofradiasapi.MyPage;
import com.nonomartinez.sfc.cofradiasapi.card.dto.GetCardDTO;
import com.nonomartinez.sfc.cofradiasapi.card.dto.PostCardDTO;
import com.nonomartinez.sfc.cofradiasapi.hermandad.dto.GetHermandadDTO;
import com.nonomartinez.sfc.cofradiasapi.hermandad.dto.GetHermandadWebListDTO;
import com.nonomartinez.sfc.cofradiasapi.hermandad.dto.PostHermandadDTO;
import com.nonomartinez.sfc.cofradiasapi.hermandad.dto.PutHermandadDTO;
import com.nonomartinez.sfc.cofradiasapi.hermandad.model.Dias;
import com.nonomartinez.sfc.cofradiasapi.hermandad.service.HermandadService;
import com.nonomartinez.sfc.cofradiasapi.hermandad.views.HermandadViews;
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
@RequestMapping("/hermandades")
public class HermandadController {

    private final HermandadService hermandadService;

    @Operation(summary = "Obtiene todas las hermandades")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Se han encontrado hermandades",
                    content = { @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = GetHermandadWebListDTO.class)),
                            examples = {@ExampleObject(
                                    value = """
                                            {
                                                        "result": [
                                                            {
                                                                "id": "80712f97-4d00-4719-98a5-2aa6ed062b4d",
                                                                "escudo": "la_misiÃ³n.png",
                                                                "nombre": "La misiÃ³n",
                                                                "dia": "VIERNES_DOLORES",
                                                                "numPasos": 0,
                                                                "numNazarenos": 600,
                                                                "numHnos": 1300
                                                            },
                                                            {
                                                                "id": "f3e1e475-57a8-4db7-984b-14ba6d6a09d3",
                                                                "escudo": "dulce_nombre_de_bellavista.png",
                                                                "nombre": "Dulce Nombre de Bellavista",
                                                                "dia": "VIERNES_DOLORES",
                                                                "numPasos": 0,
                                                                "numNazarenos": 370,
                                                                "numHnos": 900
                                                            },
                                                        ],
                                                        "size": 10,
                                                        "totalElements": 34,
                                                        "pageNumber": 0,
                                                        "first": true,
                                                        "last": false
                                                    }
                                            """
                            )}
                    )}),
            @ApiResponse(responseCode = "404",
                    description = "No se ha encontrado ninguna hermandad",
                    content = @Content),
    })
    @GetMapping("/")
    public MyPage<GetHermandadWebListDTO> getAllHdades(Pageable pageable){
        return hermandadService.getAllHermandadesPaginable(pageable);
    }

    @Operation(summary = "Obtiene todas las hermandades")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Se han encontrado hermandades",
                    content = { @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = GetHermandadWebListDTO.class)),
                            examples = {@ExampleObject(
                                    value = """
                                            [
                                                {
                                                    "id": "80712f97-4d00-4719-98a5-2aa6ed062b4d",
                                                    "escudo": "la_misiÃ³n.png",
                                                    "nombre": "La misiÃ³n",
                                                    "dia": "VIERNES_DOLORES",
                                                    "numPasos": 0,
                                                    "numNazarenos": 600,
                                                    "numHnos": 1300
                                                },
                                                {
                                                    "id": "f3e1e475-57a8-4db7-984b-14ba6d6a09d3",
                                                    "escudo": "dulce_nombre_de_bellavista.png",
                                                    "nombre": "Dulce Nombre de Bellavista",
                                                    "dia": "VIERNES_DOLORES",
                                                    "numPasos": 0,
                                                    "numNazarenos": 370,
                                                    "numHnos": 900
                                                },
                                            ]
                                            """
                            )}
                    )}),
            @ApiResponse(responseCode = "404",
                    description = "No se ha encontrado ninguna hermandad",
                    content = @Content),
    })
    @GetMapping("/web/")
    public List<GetHermandadWebListDTO> getAllHdadesWeb(){
        return hermandadService.getAllHermandades();
    }

    @Operation(summary = "Obtiene todas las hermandades por dia")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Se han encontrado hermandades",
                    content = { @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = GetHermandadDTO.class)),
                            examples = {@ExampleObject(
                                    value = """
                                            [
                                                 {
                                                     "id": "61da4278-3319-4ef5-ab24-0d51703259ab",
                                                     "nombre": "Jesus Despojado",
                                                     "escudo": null
                                                 },
                                                 {
                                                     "id": "d55bba37-a7e8-405e-a50c-a12dd93e11d5",
                                                     "nombre": "La Paz",
                                                     "escudo": null
                                                 },
                                             ]
                                            """
                            )}
                    )}),
            @ApiResponse(responseCode = "404",
                    description = "No se ha encontrado ninguna hermandad",
                    content = @Content),
    })
    @GetMapping("/{dia}")
    @JsonView(HermandadViews.HermandadList.class)
    public List<GetHermandadDTO> getAllHermandadesFromDiaSalida(@PathVariable Dias dia){
        return hermandadService.getHermandadPorDia(dia);
    }

    @Operation(summary = "Obtiene todas las hermandades por dia")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Se han encontrado hermandades",
                    content = { @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = GetHermandadWebListDTO.class)),
                            examples = {@ExampleObject(
                                    value = """
                                            [
                                                {
                                                    "id": "61da4278-3319-4ef5-ab24-0d51703259ab",
                                                    "escudo": null,
                                                    "nombre": "Jesus Despojado",
                                                    "dia": "DOMINGO_DE_RAMOS",
                                                    "numPasos": 0,
                                                    "numNazarenos": 550,
                                                    "numHnos": 1400
                                                },
                                                {
                                                    "id": "d55bba37-a7e8-405e-a50c-a12dd93e11d5",
                                                    "escudo": null,
                                                    "nombre": "La Paz",
                                                    "dia": "DOMINGO_DE_RAMOS",
                                                    "numPasos": 0,
                                                    "numNazarenos": 2220,
                                                    "numHnos": 6000
                                                },
                                            ]
                                            """
                            )}
                    )}),
            @ApiResponse(responseCode = "404",
                    description = "No se ha encontrado ninguna hermandad",
                    content = @Content),
    })
    @GetMapping("/web/{dia}")
    public List<GetHermandadWebListDTO> getAllHermandadesFromDiaSalidaWeb(@PathVariable Dias dia){
        return hermandadService.getHermandadPorDiaWeb(dia);
    }

    @Operation(summary = "Obtiene los detalles de una hermandad")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Se ha encontrado la hermandad",
                    content = { @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = GetHermandadDTO.class)),
                            examples = {@ExampleObject(
                                    value = """
                                            {
                                                "id": "2a822aaf-05e5-42d3-8600-069ec12adac9",
                                                "nombre": "El Amor",
                                                "nombreCompleto": "Primitiva ArchicofradÃ­a Pontificia y Real Hermandad de Nazarenos de la Sagrada Entrada en JerusalÃ©n, SantÃ­simo Cristo del Amor, Ntra. SeÃ±ora del Socorro y Santiago ApÃ³stol",
                                                "escudo": "amor.png",
                                                "annoFundacion": 1618,
                                                "deInteres": "Este aÃ±o se podrÃ¡ ver el portentoso conjunto del paso de palio tras la restauraciÃ³n de las piezas bordades por parte del taller de Manuel Solano y el IAPH. Tras la Semana Santa decidirÃ¡n la restauraciÃ³n del Cristo",
                                                "numNazarenos": 1900,
                                                "numHermanos": 5600,
                                                "tiempoPaso": 54,
                                                "banda": [
                                                    {
                                                        "id": "a41349f7-a89d-4697-93b0-e810fe05993c",
                                                        "nombre": "Banda de Musica de Las Cigarreras"
                                                    }
                                                ],
                                                "sede": "Iglesia Colegial del Divino Salvador",
                                                "cards": [
                                                    {
                                                        "id": 1,
                                                        "titulo": "Escudo de la Hdad. de El Amor",
                                                        "imagen": null,
                                                        "nombreHermandad": "El Amor"
                                                    },
                                                    {
                                                        "id": 2,
                                                        "titulo": "Cruz de GuÃ­a",
                                                        "imagen": null,
                                                        "nombreHermandad": "El Amor"
                                                    },
                                                ],
                                                "imagenes": [
                                                    "amor1.png",
                                                    "amor2.png",
                                                    "amor3.png",
                                                    "amor4.png"
                                                ],
                                                "pasos": [
                                                    {
                                                        "id": "b9b6da21-b73f-447d-a7dc-8274c41c063e",
                                                        "imagen": "Santisimo Cristo del Amor"
                                                    },
                                                    {
                                                        "id": "cce03820-e440-40db-b42e-419572c5dedf",
                                                        "imagen": "Nuestra SeÃ±ora del Socorro"
                                                    }
                                                ]
                                            }
                                            """
                            )}
                    )}),
            @ApiResponse(responseCode = "404",
                    description = "No se ha encontrado ninguna hermandad",
                    content = @Content),
    })
    @GetMapping("/hermandad/{id}")
    @JsonView(HermandadViews.HermandadDetails.class)
    public GetHermandadDTO getHermandadId(@PathVariable UUID id){

        return hermandadService.getHermandad(id);
    }

    @Operation(summary = "Obtiene el dia de salida de una hermandad")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Se han encontrado la hermandad",
                    content = { @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = String.class)),
                            examples = {@ExampleObject(
                                    value = """
                                            "VIERNES_SANTO"
                                            """
                            )}
                    )}),
            @ApiResponse(responseCode = "404",
                    description = "No se ha encontrado ninguna hermandad",
                    content = @Content),
    })
    @GetMapping("/hermandad/{id}/dia")
    public Dias getDia(@PathVariable UUID id){
        return hermandadService.getDia(id);
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "La hermandad se ha creado de forma correcta", content = {
                    @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = PostHermandadDTO.class)),
                            examples = {@ExampleObject(
                                    value = """
                                                {
                                                      "nombre": "Casita",
                                                      "nombreCompleto": "Real Hermandad de Mi Casa",
                                                      "deInteres": "patata",
                                                      "dia": "VIERNES_SANTO",
                                                      "annoFundacion": 2024,
                                                      "numHermanos": 1400,
                                                      "numNazarenos": 300,
                                                      "tiempoPaso": 43,
                                                      "sede": "Calle Canalejas, 10"
                                                  }
                                            """
                            )}
                    )}),
            @ApiResponse(responseCode = "400",
                    description = "No se han aportado los datos correctamente",
                    content = @Content)
    })
    @Operation(summary = "Crear una nueva hermandad")
    @PostMapping("/hermandad/nueva")
    public ResponseEntity<PostHermandadDTO> saveHermandad(@RequestBody PostHermandadDTO postHermandadDTO){
        return ResponseEntity.status(201).body(hermandadService.addHermandad(postHermandadDTO));
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Edita la hermandad de forma correcta", content = {
                    @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = PutHermandadDTO.class)),
                            examples = {@ExampleObject(
                                    value = """
                                                {
                                                      "nombre": "Casita",
                                                      "nombreCompleto": "Real Hermandad de Mi Casa",
                                                      "deInteres": "patata",
                                                      "dia": "VIERNES_SANTO",
                                                      "annoFundacion": 2024,
                                                      "numHermanos": 1400,
                                                      "numNazarenos": 300,
                                                      "tiempoPaso": 43,
                                                      "sede": "Calle Canalejas, 10"
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
                                            "error": "The Hermandad or the list of it could not be found" 
                                        }       
                                    """
                            )
                            }))
    })
    @Operation(summary = "Edita una hermandad", description = "Devuelve la hermandad editada")
    @PutMapping("/hermandad/editar/{id}")
    public ResponseEntity<GetHermandadDTO> editHermandad(@PathVariable UUID id, @RequestBody PutHermandadDTO putHermandadDTO){
        return ResponseEntity.status(HttpStatus.CREATED).body(hermandadService.edit(putHermandadDTO, id));
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "202",
                    description = "La hermandad se ha borrado correctamente",
                    content = @Content
            ),
            @ApiResponse(responseCode = "404",
                    description = "No encontrado la hermandad a borrar",
                    content = @Content)
    })
    @Operation(summary = "Borrar Hermandad", description = "Devuelve 202 Accepted si todo va bien ")
    @DeleteMapping("/hermandad/{id}")
    public ResponseEntity<?> deleteHermandad(@PathVariable UUID id){

        boolean borrado = hermandadService.deleteHermandad(id);
        if(borrado)
            return ResponseEntity.status(HttpStatus.ACCEPTED).build();
        else return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
}
