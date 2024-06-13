package com.nonomartinez.sfc.cofradiasapi.card.controller;

import com.nonomartinez.sfc.cofradiasapi.MyPage;
import com.nonomartinez.sfc.cofradiasapi.card.dto.GetCardDTO;
import com.nonomartinez.sfc.cofradiasapi.card.dto.GetCardDetailsDTO;
import com.nonomartinez.sfc.cofradiasapi.card.dto.GetCardWebDTO;
import com.nonomartinez.sfc.cofradiasapi.card.dto.PostCardDTO;
import com.nonomartinez.sfc.cofradiasapi.card.model.TipoCard;
import com.nonomartinez.sfc.cofradiasapi.card.service.CardService;
import com.nonomartinez.sfc.cofradiasapi.user.model.User;
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
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/cards")
public class CardController {

    private final CardService cardService;

    @Operation(summary = "Obtiene todas las cards del usuario autentificado")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Se han encontrado cards",
                    content = { @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = GetCardDTO.class)),
                            examples = {@ExampleObject(
                                    value = """
                                            {
                                                   "result": [
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
                                                   "size": 20,
                                                   "totalElements": 20,
                                                   "pageNumber": 0,
                                                   "first": true,
                                                   "last": true
                                               }                                        
                                            """
                            )}
                    )}),
            @ApiResponse(responseCode = "404",
                    description = "No se ha encontrado ninguna card",
                    content = @Content),
    })
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/user")
    public MyPage<GetCardDTO> getCardsUserAuth (@AuthenticationPrincipal User user, Pageable pageable){
        return cardService.getCardsUser(user.getId(), pageable);
    }

    @Operation(summary = "Obtiene todas las cards de un usuario")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Se han encontrado cards",
                    content = { @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = GetCardDTO.class)),
                            examples = {@ExampleObject(
                                    value = """
                                            {
                                                   "result": [
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
                                                   "size": 20,
                                                   "totalElements": 20,
                                                   "pageNumber": 0,
                                                   "first": true,
                                                   "last": true
                                               }                                        
                                            """
                            )}
                    )}),
            @ApiResponse(responseCode = "404",
                    description = "No se ha encontrado ninguna card",
                    content = @Content),
    })
    @GetMapping("/user/{id}")
    public MyPage<GetCardDTO> getCardsUser (@PathVariable UUID id, Pageable pageable){
        return cardService.getCardsUser(id, pageable);
    }

    @Operation(summary = "Obtiene todas las cards")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Se han encontrado cards",
                    content = { @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = GetCardDTO.class)),
                            examples = {@ExampleObject(
                                    value = """
                                            {
                                                   "result": [
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
                                                   "size": 20,
                                                   "totalElements": 20,
                                                   "pageNumber": 0,
                                                   "first": true,
                                                   "last": true
                                               }                                        
                                            """
                            )}
                    )}),
            @ApiResponse(responseCode = "404",
                    description = "No se ha encontrado ninguna card",
                    content = @Content),
    })
    @GetMapping("/")
    public MyPage<GetCardDTO> getAll(Pageable pageable){
        return cardService.getAllPageable(pageable);
    }

    @Operation(summary = "Obtiene todas las cards sin paginar")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Se han encontrado cards",
                    content = { @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = GetCardDTO.class)),
                            examples = {@ExampleObject(
                                    value = """
                                            [
                                                {
                                                    "id": 19,
                                                    "nombreCard": "Escudo de la A.P. El Olivo",
                                                    "nombreHermandad": "El Olivo",
                                                    "tipoCard": "ESCUDO"
                                                },
                                                {
                                                    "id": 20,
                                                    "nombreCard": "Cruz de Guia",
                                                    "nombreHermandad": "El Olivo",
                                                    "tipoCard": "CRUZ_DE_GUIA"
                                                },
                                            ]                                       
                                            """
                            )}
                    )}),
            @ApiResponse(responseCode = "404",
                    description = "No se ha encontrado ninguna card",
                    content = @Content),
    })
    @GetMapping("/web/")
    public List<GetCardWebDTO> getAllWeb(){
        return cardService.getAll();
    }

    @Operation(summary = "Obtiene todas las cards de un tipo sin paginar")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Se han encontrado cards del tipo correspondiente",
                    content = { @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = GetCardDTO.class)),
                            examples = {@ExampleObject(
                                    value = """
                                            [
                                                  {
                                                      "id": 22,
                                                      "nombreCard": "Paso de Ntro. Padre. Jesus de la Humildad",
                                                      "nombreHermandad": "El Olivo",
                                                      "tipoCard": "PASO"
                                                  },
                                                  {
                                                      "id": 4,
                                                      "nombreCard": "Paso del Stmo. Cristo del Amor",
                                                      "nombreHermandad": "El Amor",
                                                      "tipoCard": "PASO"
                                                  }
                                            ]                                  
                                            """
                            )}
                    )}),
            @ApiResponse(responseCode = "404",
                    description = "No se ha encontrado ninguna card",
                    content = @Content),
    })
    @GetMapping("/web/{tipoCard}")
    public List<GetCardWebDTO> getByTipo(@PathVariable TipoCard tipoCard){
        return cardService.getCardByTipo(tipoCard);
    }

    @Operation(summary = "Obtiene los detalles de una card")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Se han encontrado la card",
                    content = { @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = GetCardDTO.class)),
                            examples = {@ExampleObject(
                                    value = """
                                            {
                                                      "id": 1,
                                                      "urlImagen": null,
                                                      "titulo": "Escudo de la Hdad. de El Amor",
                                                      "descripcion": null,
                                                      "nombreFotografo": null,
                                                      "tipoCard": "ESCUDO"
                                                  }                             
                                            """
                            )}
                    )}),
            @ApiResponse(responseCode = "404",
                    description = "No se ha encontrado ninguna card",
                    content = @Content),
    })
    @GetMapping("/card/{id}")
    public ResponseEntity<GetCardDetailsDTO> getCardById(@PathVariable Long id){
        return ResponseEntity.status(200).body(cardService.getCardByID(id));
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "La card se ha creado de forma correcta", content = {
                    @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = PostCardDTO.class)),
                            examples = {@ExampleObject(
                                    value = """
                                                {
                                                     "urlImagen": "asdfhabsdlfhads",
                                                     "titulo": "Paso Grade",
                                                     "descripcion": "Lorem ipsum dolor sit amet",
                                                     "nombreFotografo": "Manolo",
                                                     "tipo": "PASO"
                                                 }
                                            """
                            )}
                    )}),
            @ApiResponse(responseCode = "400",
                    description = "No se han aportado los datos correctamente",
                    content = @Content)
    })
    @Operation(summary = "Crear una nueva card")
    @PostMapping("/nueva/{id}")
    public ResponseEntity<PostCardDTO> saveCard(@PathVariable UUID id, @RequestBody PostCardDTO postCardDTO){
        return ResponseEntity.status(201).body(cardService.addCard(postCardDTO, id));
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Edita la card de forma correcta", content = {
                    @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = PostCardDTO.class)),
                            examples = {@ExampleObject(
                                    value = """
                                                {
                                                     "urlImagen": "asdfhabsdlfhads",
                                                     "titulo": "Paso Grade",
                                                     "descripcion": "Lorem ipsum dolor sit amet",
                                                     "nombreFotografo": "Manolo",
                                                     "tipo": "PASO"
                                                 }
                                            """
                            )}
                    )}),
            @ApiResponse(responseCode = "404",
                    description = "No se ha encontrado la card",
                    content = @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = PostCardDTO.class)),
                            examples = {@ExampleObject(
                                    """
                                        {
                                            "error": "The Card or the list of it could not be found" 
                                        }       
                                    """
                            )
                            }))
    })
    @Operation(summary = "Edita una card", description = "Devuelve la card editada")
    @PutMapping("/card/{id}")
    public ResponseEntity<GetCardDTO> editCard(@PathVariable Long id, @RequestBody PostCardDTO postCardDTO){
        return ResponseEntity.status(HttpStatus.CREATED).body(cardService.edit(postCardDTO, id));
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "202",
                    description = "La card se ha borrado correctamente",
                    content = @Content
            ),
            @ApiResponse(responseCode = "404",
                    description = "No encontrado la card a borrar",
                    content = @Content)
    })
    @Operation(summary = "Borrar Card", description = "Devuelve 202 Accepted si todo va bien ")
    @DeleteMapping("/card/{id}")
    public ResponseEntity<?> deleteCard(@PathVariable Long id){

        boolean borrado = cardService.deleteCard(id);
        if(borrado)
            return ResponseEntity.status(HttpStatus.ACCEPTED).build();
        else return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
}
