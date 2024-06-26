package com.nonomartinez.sfc.cofradiasapi.paso.model;

import com.nonomartinez.sfc.cofradiasapi.hermandad.model.Hermandad;
import com.nonomartinez.sfc.cofradiasapi.musica.model.Musica;
import jakarta.persistence.*;
import lombok.*;
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
public class Paso {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator", parameters = {
            @Parameter(name = "uuid_gen_strategy_clas", value = "org.hibernate.id.uuid.CurstomVersionOneStrategy")
    })
    private UUID id;

    private String imagen, capataz;
    private int numCostaleros;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "paso_musica",
        joinColumns = @JoinColumn(name = "id_paso", referencedColumnName = "id"),
        inverseJoinColumns = @JoinColumn(name = "id_musica", referencedColumnName = "id"))
    private List<Musica> acompannamiento = new ArrayList<>();

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_hermandad", foreignKey = @ForeignKey(name = "fk_paso_hermandad"))
    private Hermandad hermandad;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "paso_imagenes", joinColumns = @JoinColumn(name = "paso_id"))
    @Column(name = "imagenes")
    private List<String> galeriaImagenes = new ArrayList<>();
}
