package com.pontodosjogos.pontodosjogos.service;

import com.pontodosjogos.pontodosjogos.controller.request.EnderecoRequest;
import com.pontodosjogos.pontodosjogos.controller.response.EnderecoResponse;
import com.pontodosjogos.pontodosjogos.entity.Endereco;
import com.pontodosjogos.pontodosjogos.entity.Usuario;
import com.pontodosjogos.pontodosjogos.exception.RecursoNaoEncontradoException;
import com.pontodosjogos.pontodosjogos.mapper.EnderecoMapper;
import com.pontodosjogos.pontodosjogos.repository.EnderecoRepository;
import com.pontodosjogos.pontodosjogos.repository.UsuarioRepository;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EnderecoService {

    private final EnderecoRepository enderecoRepository;
    private final UsuarioRepository usuarioRepository;
    private final EnderecoMapper enderecoMapper;

    /**
     * Retorna todos os endereços vinculados a um usuário.
     * @param usuarioId O ID do usuário (obtido via JWT).
     * @return Lista de EnderecoResponse.
     */
    @Transactional(readOnly = true)
    public List<EnderecoResponse> buscarEnderecosPorUsuario(Long usuarioId) {
        // Encontra todos os endereços onde a FK 'usuario_id' é igual ao ID fornecido.
        List<Endereco> enderecos = enderecoRepository.findByUsuarioId(usuarioId);

        return enderecos.stream()
                .map(enderecoMapper::toResponse) // Mapeia cada entidade para o DTO
                .collect(Collectors.toList());
    }

    /**
     * Busca um endereço específico do usuário.
     */
    @Transactional(readOnly = true)
    public Endereco buscarEntidadePorIdEUsuario(Long enderecoId, Long usuarioId) {
        // Método de segurança: garante que o endereço pertence ao usuário
        return enderecoRepository.findByIdAndUsuarioId(enderecoId, usuarioId)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Endereço não encontrado ou não pertence ao usuário."));
    }

    // =================================================================
    // MÉTODOS DE MANIPULAÇÃO (CREATE/UPDATE/DELETE)
    // =================================================================

    /**
     * Adiciona um novo endereço ao cadastro do usuário.
     * @param usuarioId O ID do usuário logado.
     * @param request O DTO de requisição com os dados do novo endereço.
     * @return O EnderecoResponse do novo endereço criado.
     */
    @Transactional
    public EnderecoResponse adicionarNovoEndereco(Long usuarioId, EnderecoRequest request) {
        // 1. Busca a entidade Usuario real
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Usuário não encontrado."));

        // 2. Mapeia o DTO para a Entidade Endereco
        Endereco novoEndereco = enderecoMapper.toEntity(request);

        // 3. Vincula o novo endereço ao usuário
        novoEndereco.setUsuario(usuario);

        // 4. Salva no banco de dados
        Endereco enderecoSalvo = enderecoRepository.save(novoEndereco);

        // 5. Retorna o DTO de resposta
        return enderecoMapper.toResponse(enderecoSalvo);
    }

    /**
     * Atualiza os dados de um endereço existente.
     */
    @Transactional
    public EnderecoResponse atualizarEndereco(Long enderecoId, Long usuarioId, EnderecoRequest request) {
        // 1. Busca o endereço existente, garantindo que ele pertence ao usuário
        Endereco enderecoExistente = buscarEntidadePorIdEUsuario(enderecoId, usuarioId);

        // 2. Mapeia os dados do DTO para a entidade existente (Mapeamento de Fusão)
        enderecoMapper.updateEntityFromDto(request, enderecoExistente);

        // O campo 'usuario' (FK) é mantido inalterado pelo @MappingTarget

        // 3. Salva a atualização
        Endereco enderecoAtualizado = enderecoRepository.save(enderecoExistente);

        return enderecoMapper.toResponse(enderecoAtualizado);
    }

    /**
     * Remove um endereço do cadastro do usuário.
     */
    @Transactional
    public void removerEndereco(Long enderecoId, Long usuarioId) {
        // Busca o endereço, garantindo a propriedade e lançando exceção se não existir
        Endereco enderecoParaRemover = buscarEntidadePorIdEUsuario(enderecoId, usuarioId);

        enderecoRepository.delete(enderecoParaRemover);
    }
}
