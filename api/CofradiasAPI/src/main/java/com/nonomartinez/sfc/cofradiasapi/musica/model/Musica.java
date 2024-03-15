package com.nonomartinez.sfc.cofradiasapi.musica.model;

import com.nonomartinez.sfc.cofradiasapi.paso.model.Paso;
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
public class Musica {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator", parameters = {
            @Parameter(name = "uuid_gen_strategy_clas", value = "org.hibernate.id.uuid.CurstomVersionOneStrategy")
    })
    private UUID id;

    private String nombre, localidad;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_banda")
    private TipoBanda tipoBanda;

    private int annoFundacion;

    @ManyToMany(mappedBy = "acompannamiento", fetch = FetchType.EAGER)
    private List<Paso> pasos = new ArrayList<>();

}
