package com.mottu.mottuapi.controller;

import com.mottu.mottuapi.entity.OrdemServico;
import com.mottu.mottuapi.entity.Moto;
import com.mottu.mottuapi.repository.OrdemServicoRepository;
import com.mottu.mottuapi.service.MotoService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@Controller
@RequestMapping("/os")
public class OrdemServicoController {

    private final OrdemServicoRepository ordemRepo;
    private final MotoService motoService;

    public OrdemServicoController(OrdemServicoRepository ordemRepo, MotoService motoService) {
        this.ordemRepo = ordemRepo;
        this.motoService = motoService;
    }

    @GetMapping
    public String listar(Model model) {
        List<OrdemServico> lista = ordemRepo.findAll();
        model.addAttribute("osList", lista);
        return "os-list";
    }

    @GetMapping("/novo")
    public String novoForm(Model model) {
        OrdemServico os = new OrdemServico();
        model.addAttribute("ordem", os);
        model.addAttribute("motos", motoService.listarTodos());
        model.addAttribute("problemas", List.of(
                "Troca de farol",
                "Troca de bateria",
                "Troca das pastilhas de freio",
                "Troca de óleo",
                "Vazamento no motor",
                "Problema no sistema elétrico",
                "Pneus gastos",
                "Corrente frouxa",
                "Barulho anormal no motor",
                "OUTRO"
        ));
        return "os-form";
    }

    @PostMapping("/salvar")
    public String salvar(@ModelAttribute OrdemServico ordem) {
        if (ordem.getId() == null) {
            ordem.setStatus("PENDENTE");
            ordem.setDataAbertura(LocalDateTime.now());
        }
        ordemRepo.save(ordem);
        return "redirect:/os";
    }

    @GetMapping("/editar/{id}")
    public String editarForm(@PathVariable Long id, Model model) {
        OrdemServico os = ordemRepo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("OS não encontrada: " + id));
        model.addAttribute("ordem", os);
        model.addAttribute("motos", motoService.listarTodos());
        model.addAttribute("problemas", List.of(
                "Troca de farol",
                "Troca de bateria",
                "Troca das pastilhas de freio",
                "Troca de óleo",
                "Vazamento no motor",
                "Problema no sistema elétrico",
                "Pneus gastos",
                "Corrente frouxa",
                "Barulho anormal no motor",
                "OUTRO"
        ));
        return "os-form";
    }

    @GetMapping("/excluir/{id}")
    public String excluir(@PathVariable Long id, Authentication auth) {
        if (auth.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"))) {
            OrdemServico os = ordemRepo.findById(id)
                    .orElseThrow(() -> new IllegalArgumentException("OS não encontrada: " + id));
            ordemRepo.delete(os);
        }
        return "redirect:/os";
    }

    @GetMapping("/concluir/{id}")
    public String concluir(@PathVariable Long id, Authentication auth) {
        if (auth.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"))) {
            OrdemServico os = ordemRepo.findById(id)
                    .orElseThrow(() -> new IllegalArgumentException("OS não encontrada: " + id));
            os.setStatus("CONCLUÍDA");
            os.setDataConclusao(LocalDateTime.now());
            ordemRepo.save(os);
        }
        return "redirect:/os";
    }
}
