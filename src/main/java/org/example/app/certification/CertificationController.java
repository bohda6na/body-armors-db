package org.example.app.certification;

import lombok.RequiredArgsConstructor;
import org.example.app.bodyarmor.ArmorModelRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;

@Controller
@RequestMapping("/certification")
@RequiredArgsConstructor
public class CertificationController {

    private final CertificationRepository certificationRepository;
    private final ArmorModelRepository armorModelRepository;

    @GetMapping
    public String list(Model model) {
        model.addAttribute("certifications", certificationRepository.findAllOrdered());
        model.addAttribute("models", armorModelRepository.findAllOrdered());
        return "certification/list";
    }

    // ── FORM 3: Record Certification (INSERT / UPDATE) ────
    @GetMapping("/certify")
    public String certifyForm(Model model) {
        model.addAttribute("certifications", certificationRepository.findAllOrdered());
        model.addAttribute("models", armorModelRepository.findAllOrdered());
        return "certification/certify";
    }

    @PostMapping("/certify")
    public String certifySubmit(
            @RequestParam Integer modelID,
            @RequestParam String certificationCode,
            @RequestParam(required = false) LocalDate verificationDate,
            @RequestParam(defaultValue = "true") boolean isValid,
            RedirectAttributes ra) {

        certificationRepository.upsertCertification(
                modelID, certificationCode,
                verificationDate == null ? LocalDate.now() : verificationDate,
                isValid);

        ra.addFlashAttribute("success",
                "Certification " + certificationCode +
                " recorded for model #" + modelID +
                " — " + (isValid ? "Valid" : "Lapsed"));
        return "redirect:/certification";
    }
}