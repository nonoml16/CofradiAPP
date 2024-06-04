package com.nonomartinez.sfc.cofradiasapi.hermandad.model;

import com.nonomartinez.sfc.cofradiasapi.card.model.Card;
import com.nonomartinez.sfc.cofradiasapi.paso.model.Paso;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.FetchMode;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Builder
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class Hermandad {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator", parameters = {
            @Parameter(name = "uuid_gen_strategy_clas", value = "org.hibernate.id.uuid.CurstomVersionOneStrategy")
    })
    private UUID id;

    private String nombre;

    private String nombreCompleto;
    private String sede, direccion, deInteres, escudo;
    private int annoFundacion, numNazarenos, numHermanos, tiempoDePaso;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "hermandad_imagenes", joinColumns = @JoinColumn(name = "hermandad_id"))
    @Column(name = "imagenes")
    private List<String> galeriaImagenes = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    @Column(name = "dias")
    private Dias diaSalida;

    @OneToMany(mappedBy = "hermandad", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Paso> pasos = new ArrayList<>();

    @OneToMany(mappedBy = "hermandad", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Card> cards = new ArrayList<>();
}
