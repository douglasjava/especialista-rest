package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.api.assembler.UsuarioInputDisassembler;
import com.algaworks.algafood.api.assembler.UsuarioModelAssembler;
import com.algaworks.algafood.api.model.UsuarioModel;
import com.algaworks.algafood.api.model.input.SenhaInput;
import com.algaworks.algafood.api.model.input.UsuarioComSenhaInput;
import com.algaworks.algafood.api.model.input.UsuarioInput;
import com.algaworks.algafood.domain.model.Usuario;
import com.algaworks.algafood.domain.repository.UsuarioRepository;
import com.algaworks.algafood.domain.service.CadastroUsuarioService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    private UsuarioRepository usuarioRepository;
    private CadastroUsuarioService cadastroUsuario;
    private UsuarioModelAssembler usuarioModelAssembler;
    private UsuarioInputDisassembler usuarioInputDisassembler;

    public UsuarioController(UsuarioRepository usuarioRepository,
                             CadastroUsuarioService cadastroUsuario,
                             UsuarioModelAssembler usuarioModelAssembler,
                             UsuarioInputDisassembler usuarioInputDisassembler) {

        this.usuarioRepository = usuarioRepository;
        this.cadastroUsuario = cadastroUsuario;
        this.usuarioModelAssembler = usuarioModelAssembler;
        this.usuarioInputDisassembler = usuarioInputDisassembler;
    }

    @GetMapping
    public List<UsuarioModel> listar() {
        return usuarioModelAssembler.toCollectionModel(usuarioRepository.findAll());
    }

    @GetMapping("/{usuarioId}")
    public UsuarioModel buscar(@PathVariable Long usuarioId) {
        Usuario usuario = cadastroUsuario.buscarOuFalhar(usuarioId);

        return usuarioModelAssembler.toModel(usuario);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UsuarioModel adicionar(@RequestBody @Valid UsuarioComSenhaInput usuarioInput) {
        Usuario usuario = usuarioInputDisassembler.toDomainObject(usuarioInput);

        return usuarioModelAssembler.toModel(cadastroUsuario.salvar(usuario));
    }

    @PutMapping("/{usuarioId}")
    public UsuarioModel atualizar(@PathVariable Long usuarioId,
                                  @RequestBody @Valid UsuarioInput usuarioInput) {

        Usuario usuarioAtual = cadastroUsuario.buscarOuFalhar(usuarioId);

        usuarioInputDisassembler.copyToDomainObject(usuarioInput, usuarioAtual);

        //O scopo do JPA permite que a atualização seja feita sem a chamada do método save
        // pois o objeto está sendo gerenciado pelo EntityManager quando realizamos o "buscarOuFalhar"
        // O método abaixo está anotado com @transaction, portanto após a execução dele o commite será realizado
        // e toda alteração que tive sido feito no objeto gerenciado será comitado, portanto o mesmo não precisa
        // ter ação alguma apenas está anotado com a @transaction
        //cadastroUsuario.atualizar();

        return usuarioModelAssembler.toModel(cadastroUsuario.salvar(usuarioAtual));
    }

    @PutMapping("/{usuarioId}/senha")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void alterarSenha(@PathVariable Long usuarioId, @RequestBody @Valid SenhaInput senha) {
        cadastroUsuario.alterarSenha(usuarioId, senha.getSenhaAtual(), senha.getNovaSenha());
    }
}