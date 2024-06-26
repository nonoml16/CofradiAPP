package com.nonomartinez.sfc.cofradiasapi.musica.service;

import com.nonomartinez.sfc.cofradiasapi.MyPage;
import com.nonomartinez.sfc.cofradiasapi.exception.NotFoundException;
import com.nonomartinez.sfc.cofradiasapi.hermandad.model.Dias;
import com.nonomartinez.sfc.cofradiasapi.hermandad.model.Hermandad;
import com.nonomartinez.sfc.cofradiasapi.hermandad.repository.HermandadRepository;
import com.nonomartinez.sfc.cofradiasapi.musica.dto.GetMusicaDTO;
import com.nonomartinez.sfc.cofradiasapi.musica.dto.GetMusicaHermandadDTO;
import com.nonomartinez.sfc.cofradiasapi.musica.dto.GetMusicaListDTO;
import com.nonomartinez.sfc.cofradiasapi.musica.dto.PostMusicaDTO;
import com.nonomartinez.sfc.cofradiasapi.musica.model.Musica;
import com.nonomartinez.sfc.cofradiasapi.musica.model.TipoBanda;
import com.nonomartinez.sfc.cofradiasapi.musica.repository.MusicaRepository;
import com.nonomartinez.sfc.cofradiasapi.paso.model.Paso;
import com.nonomartinez.sfc.cofradiasapi.paso.repository.PasoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@RequiredArgsConstructor
public class MusicaService {

    private final MusicaRepository musicaRepository;
    private final PasoRepository pasoRepository;
    private final HermandadRepository hermandadRepository;

    public GetMusicaDTO getMusicaDetails(UUID id){
        Optional<Musica> musicaOptional = musicaRepository.findById(id);
        if(musicaOptional.isEmpty())
            throw new NotFoundException("Musica");

        return GetMusicaDTO.of(musicaOptional.get());
    }

    public List<GetMusicaHermandadDTO> getFiveRandomBandas(){
        List<Musica> allBandas = musicaRepository.findAll();

        // Si hay menos de 5 bandas, devolver todas las disponibles
        if (allBandas.size() <= 5) {
            return allBandas
                    .stream()
                    .map(GetMusicaHermandadDTO::of)
                    .toList();
        }

        Random random = new Random();
        List<Musica> randomBandas = new ArrayList<>();
        List<Integer> selectedIndexes = new ArrayList<>();

        while (randomBandas.size() < 5) {
            int randomIndex = random.nextInt(allBandas.size());
            if (!selectedIndexes.contains(randomIndex)) {
                randomBandas.add(allBandas.get(randomIndex));
                selectedIndexes.add(randomIndex);
            }
        }

        return randomBandas
                .stream()
                .map(GetMusicaHermandadDTO::of)
                .toList();
    }

    public List<GetMusicaListDTO> getAllBandas(){

        return musicaRepository.findAll().stream().map(GetMusicaListDTO::of).toList();
    }

    public MyPage<GetMusicaListDTO> getAllBandasPaginado(Pageable pageable){
        Page<Musica> bandaPage = musicaRepository.findAll(pageable);

        return MyPage.of(bandaPage.map(GetMusicaListDTO::of));
    }

    public List<GetMusicaListDTO> getBandasPorTipo(TipoBanda tipoBanda){

        return musicaRepository.findBandasByTipo(tipoBanda).stream().map(GetMusicaListDTO::of).toList();
    }

    public MyPage<GetMusicaListDTO> getBandasPorTipoPaginado(TipoBanda tipoBanda, Pageable pageable){
        Page<Musica> musicaPage = musicaRepository.findBandasByTipo(tipoBanda, pageable);

        return MyPage.of(musicaPage.map(GetMusicaListDTO::of));
    }

    public PostMusicaDTO addMusica(PostMusicaDTO nuevo){
        Musica musica = Musica.builder()
                .nombre(nuevo.nombre())
                .localidad(nuevo.localidad())
                .tipoBanda(TipoBanda.valueOf(nuevo.tipo()))
                .annoFundacion(nuevo.annoFundacion())
                .build();

        musicaRepository.save(musica);
        return nuevo;
    }

    public GetMusicaDTO edit(PostMusicaDTO postMusicaDTO, UUID id){
        Optional<Musica> musicaOptional = musicaRepository.findById(id);
        if(musicaOptional.isEmpty())
            throw  new NotFoundException("No existe la banda");

        Musica editada = musicaOptional.get();

        editada.setNombre(postMusicaDTO.nombre());
        editada.setAnnoFundacion(postMusicaDTO.annoFundacion());
        editada.setTipoBanda(TipoBanda.valueOf(postMusicaDTO.tipo()));
        editada.setLocalidad(postMusicaDTO.localidad());

        musicaRepository.save(editada);
        return GetMusicaDTO.of(editada);
    }

    @Transactional
    public boolean deleteMusica(UUID id){
        Optional<Musica> musicaOptional = musicaRepository.findById(id);
        if(musicaOptional.isEmpty())
            throw  new NotFoundException("No existe la banda");

        Musica aBorrar = musicaOptional.get();

        for(Paso paso : aBorrar.getPasos()){
            if(paso.getAcompannamiento().contains(aBorrar)){
                paso.getAcompannamiento().remove(aBorrar);
                pasoRepository.save(paso);
            }
        }

        musicaRepository.delete(aBorrar);

        return true;
    }

    public TipoBanda getTipo(UUID id){
        Optional<Musica> musicaOptional = musicaRepository.findById(id);
        if(musicaOptional.isEmpty())
            throw new NotFoundException("No existe banda");

        Musica musica = musicaOptional.get();
        return musica.getTipoBanda();
    }

    @Transactional
    public boolean deleteMusicaHermandad(UUID idBanda, UUID idHermandad){
        Optional<Musica> musicaOptional = musicaRepository.findById(idBanda);
        Optional<Hermandad> hermandadOptional = hermandadRepository.findById(idHermandad);

        if(musicaOptional.isEmpty())
            throw new NotFoundException("No existe banda");
        if(hermandadOptional.isEmpty())
            throw  new NotFoundException("No existe la hermandad");

        Musica aBorrar = musicaOptional.get();
        Hermandad aEditar = hermandadOptional.get();
        for(Paso paso : aEditar.getPasos()){
            if(paso.getAcompannamiento().contains(aBorrar)){
                paso.getAcompannamiento().remove(aBorrar);
                pasoRepository.save(paso);
            }
        }

        hermandadRepository.save(aEditar);


        return true;
    }
}
