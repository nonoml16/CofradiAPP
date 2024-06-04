package com.nonomartinez.sfc.cofradiasapi.musica.service;

import com.nonomartinez.sfc.cofradiasapi.MyPage;
import com.nonomartinez.sfc.cofradiasapi.exception.NotFoundException;
import com.nonomartinez.sfc.cofradiasapi.musica.dto.GetMusicaDTO;
import com.nonomartinez.sfc.cofradiasapi.musica.dto.GetMusicaHermandadDTO;
import com.nonomartinez.sfc.cofradiasapi.musica.dto.GetMusicaListDTO;
import com.nonomartinez.sfc.cofradiasapi.musica.dto.PostMusicaDTO;
import com.nonomartinez.sfc.cofradiasapi.musica.model.Musica;
import com.nonomartinez.sfc.cofradiasapi.musica.model.TipoBanda;
import com.nonomartinez.sfc.cofradiasapi.musica.repository.MusicaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class MusicaService {

    private final MusicaRepository musicaRepository;

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

    public MyPage<GetMusicaListDTO> getAllBandas(Pageable pageable){
        Page<Musica> bandaPage = musicaRepository.findAll(pageable);

        return MyPage.of(bandaPage.map(GetMusicaListDTO::of));
    }

    public MyPage<GetMusicaListDTO> getBandasPorTipo(TipoBanda tipoBanda, Pageable pageable){
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
}
