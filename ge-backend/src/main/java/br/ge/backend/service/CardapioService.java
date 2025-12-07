package br.ge.backend.service;

import br.ge.backend.dto.ItemCardapioDTO;
import br.ge.backend.entity.ItemCardapio;
import br.ge.backend.repository.ItemCardapioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CardapioService {

    private final ItemCardapioRepository itemRepository;

    public List<ItemCardapioDTO> buscarItens(String termo, String semIngrediente) {
        List<ItemCardapio> itens;

        if (semIngrediente != null && !semIngrediente.isEmpty()) {
            itens = itemRepository.findByIngredientesNotContainingIgnoreCase(semIngrediente);
        } else if (termo != null && !termo.isEmpty()) {
            itens = itemRepository.findByNomeContainingIgnoreCaseOrIngredientesContainingIgnoreCase(termo, termo);
        } else {
            itens = itemRepository.findAll();
        }

        return itens.stream().map(this::converterParaDTO).collect(Collectors.toList());
    }

    @Transactional
    public void indisponibilizarItem(Long id) {
        ItemCardapio item = itemRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Item do cardápio não encontrado"));

        item.setDisponivelNaCozinha(false);
        itemRepository.save(item);
    }

    public List<ItemCardapioDTO> buscarDestaquesSemana() {
        LocalDateTime umaSemanaAtras = LocalDateTime.now().minusDays(7);

        List<ItemCardapio> destaques = itemRepository.findItensMaisVendidos(
            umaSemanaAtras,
            PageRequest.of(0, 5)
        );

        return destaques.stream().map(this::converterParaDTO).collect(Collectors.toList());
    }

    private ItemCardapioDTO converterParaDTO(ItemCardapio item) {
        ItemCardapioDTO dto = new ItemCardapioDTO();
        dto.setId(item.getId());
        dto.setNome(item.getNome());
        dto.setIngredientes(item.getIngredientes());
        dto.setPreco(item.getPreco());
        dto.setDisponivel(item.isDisponivelNaCozinha());
        if (item.getCategoria() != null) {
            dto.setCategoriaNome(item.getCategoria().getNome());
        }
        return dto;
    }

}
