package org.example.app.bodyarmor;

import lombok.RequiredArgsConstructor;
import org.example.app.batch.Batch;
import org.example.app.batch.BatchRepository;
import org.example.app.certification.CertificationRepository;
import org.example.app.manufacturer.ManufacturerRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;

@Controller
@RequestMapping("/bodyarmor")
@RequiredArgsConstructor
public class BodyArmorController {

    private final BodyArmorRepository bodyArmorRepository;
    private final ArmorModelRepository armorModelRepository;
    private final BatchRepository batchRepository;
    private final ManufacturerRepository manufacturerRepository;
    private final CertificationRepository certificationRepository;

    // ── LIST ──────────────────────────────────────────────
    @GetMapping
    public String list(Model model) {
        model.addAttribute("units", bodyArmorRepository.findAllOrdered());
        model.addAttribute("models", armorModelRepository.findAllOrdered());
        return "bodyarmor/list";
    }

    // ── FORM 1: Register Batch (ADD) ──────────────────────
    @GetMapping("/register")
    public String registerForm(Model model) {
        model.addAttribute("manufacturers", manufacturerRepository.findAllOrdered());
        model.addAttribute("armor", new BodyArmor());
        model.addAttribute("batch", new Batch());
        model.addAttribute("armorModel", new ArmorModel());
        return "bodyarmor/register";
    }

    @PostMapping("/register")
    public String registerSubmit(
            @RequestParam String brandName,
            @RequestParam String countryOfOrigin,
            @RequestParam String modelName,
            @RequestParam String protectionLevel,
            @RequestParam(required = false) java.math.BigDecimal v50Velocity,
            @RequestParam(required = false) java.math.BigDecimal backfaceDeformation,
            @RequestParam(required = false) String SKU,
            @RequestParam(required = false) Integer warrantyYears,
            @RequestParam String batchNumber,
            @RequestParam(required = false) java.math.BigDecimal unitCost,
            @RequestParam(required = false) LocalDate deliveryDate,
            @RequestParam(required = false) Integer quantity,
            @RequestParam(required = false) LocalDate productionDate,
            @RequestParam(required = false) java.math.BigDecimal weight,
            @RequestParam(required = false) String size,
            RedirectAttributes ra) {

        // 1. Manufacturer — find or insert
        var mfr = manufacturerRepository.findAllOrdered().stream()
                .filter(m -> m.getBrandName().equalsIgnoreCase(brandName))
                .findFirst()
                .orElseGet(() -> {
                    var nm = new org.example.app.manufacturer.Manufacturer();
                    nm.setBrandName(brandName);
                    nm.setCountryOfOrigin(countryOfOrigin);
                    return manufacturerRepository.save(nm);
                });

        // 2. ArmorModel
        ArmorModel am = new ArmorModel();
        am.setManufacturerID(mfr.getManufacturerID());
        am.setModelName(modelName);
        am.setProtectionLevel(protectionLevel);
        am.setV50Velocity(v50Velocity);
        am.setBackfaceDeformation(backfaceDeformation);
        am.setSKU(SKU);
        am.setWarrantyYears(warrantyYears);
        ArmorModel saved = armorModelRepository.save(am);

        // 3. Batch
        batchRepository.insertBatch(saved.getModelID(), batchNumber, unitCost,
                deliveryDate, quantity == null ? 1 : quantity);

        // 4. BodyArmor — one per unit
        int qty = (quantity == null || quantity < 1) ? 1 : quantity;
        for (int i = 1; i <= qty; i++) {
            BodyArmor ba = new BodyArmor();
            ba.setModelID(saved.getModelID());
            ba.setSerialNumber(batchNumber + "-" + String.format("%03d", i));
            ba.setProductionDate(productionDate);
            ba.setConditionStatus("New");
            ba.setWeight(weight);
            ba.setSize(size);
            bodyArmorRepository.save(ba);
        }

        ra.addFlashAttribute("success",
                "Registered " + qty + " unit(s) for model: " + modelName);
        return "redirect:/bodyarmor";
    }

    // ── FORM 2: Update Condition (UPDATE) ─────────────────
    @GetMapping("/update/{id}")
    public String updateForm(@PathVariable Integer id, Model model) {
        BodyArmor ba = bodyArmorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Unit not found: " + id));
        model.addAttribute("unit", ba);
        return "bodyarmor/update";
    }

    @PostMapping("/update/{id}")
    public String updateSubmit(@PathVariable Integer id,
                               @RequestParam String conditionStatus,
                               @RequestParam(required = false) LocalDate inspectionDate,
                               RedirectAttributes ra) {
        bodyArmorRepository.updateCondition(id, conditionStatus,
                inspectionDate == null ? LocalDate.now() : inspectionDate);
        ra.addFlashAttribute("success",
                "Unit #" + id + " updated to: " + conditionStatus);
        return "redirect:/bodyarmor";
    }

    // ── FORM 4: Decommission (DELETE) ─────────────────────
    @PostMapping("/delete/{id}")
    public String delete(@PathVariable Integer id, RedirectAttributes ra) {
        bodyArmorRepository.deleteById(id);
        ra.addFlashAttribute("success", "Unit #" + id + " decommissioned.");
        return "redirect:/bodyarmor";
    }
}