package org.example.app.report;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/reports")
@RequiredArgsConstructor
public class ReportController {

    private final JdbcTemplate jdbc;

    @GetMapping("/inventory")
    public String inventoryReport(Model model) {
        String sql = """
            SELECT
                ba.productID,
                ba.serialNumber,
                am.modelName,
                mf.brandName        AS manufacturer,
                am.protectionLevel,
                ba.conditionStatus,
                ba.productionDate,
                DATE_ADD(ba.productionDate,
                    INTERVAL am.warrantyYears YEAR) AS expiryDate,
                DATEDIFF(
                    DATE_ADD(ba.productionDate,
                        INTERVAL am.warrantyYears YEAR),
                    CURDATE())       AS daysUntilExpiry,
                b.batchNumber,
                b.unitCost,
                ba.inspectionDate
            FROM bodyarmor ba
            JOIN armormodel am   ON ba.modelID = am.modelID
            JOIN manufacturer mf ON am.manufacturerID = mf.manufacturerID
            LEFT JOIN batch b     ON ba.modelID = b.modelID
            ORDER BY ba.conditionStatus, am.modelName
            """;
        List<Map<String, Object>> rows = jdbc.queryForList(sql);
        model.addAttribute("rows", rows);
        return "report/inventory";
    }

    @GetMapping("/certification")
    public String certificationReport(Model model) {
        String sql = """
            SELECT
                am.modelID,
                am.modelName,
                amc.certificationCode,
                c.standardName,
                am.protectionLevel,
                amc.verificationDate,
                IF(amc.isValid = 1, 'Valid', 'Lapsed') AS certStatus,
                COUNT(ba.productID)  AS totalUnits,
                SUM(CASE WHEN ba.conditionStatus
                    NOT IN ('Expired','Decommissioned')
                    THEN 1 ELSE 0 END) AS activeUnits
            FROM armormodel am
            JOIN armormodelcertification amc
                 ON am.modelID = amc.modelID
            JOIN certification c
                 ON amc.certificationCode = c.certificationCode
            LEFT JOIN bodyarmor ba ON am.modelID = ba.modelID
            GROUP BY am.modelID, am.modelName, amc.certificationCode,
                     c.standardName, am.protectionLevel,
                     amc.verificationDate, amc.isValid
            ORDER BY am.modelID, amc.certificationCode
            """;
        List<Map<String, Object>> rows = jdbc.queryForList(sql);
        model.addAttribute("rows", rows);
        return "report/certification";
    }

    @GetMapping("/readiness")
    public String readinessReport(Model model) {
        String sql = """
            SELECT
                am.modelName,
                am.protectionLevel,
                COUNT(ba.productID)                            AS totalUnits,
                SUM(ba.conditionStatus = 'New')                AS newUnits,
                SUM(ba.conditionStatus = 'Used')               AS usedUnits,
                SUM(ba.conditionStatus = 'Damaged')            AS damagedUnits,
                SUM(ba.conditionStatus = 'Expired')            AS expiredUnits,
                SUM(ba.conditionStatus = 'Decommissioned')     AS decommUnits,
                ROUND(AVG(
                    DATEDIFF(
                        DATE_ADD(ba.productionDate,
                            INTERVAL am.warrantyYears YEAR),
                        CURDATE())
                ), 0)                                          AS avgDaysUntilExpiry
            FROM armormodel am
            LEFT JOIN bodyarmor ba ON am.modelID = ba.modelID
            GROUP BY am.modelID, am.modelName, am.protectionLevel
            ORDER BY totalUnits DESC, am.modelName
            """;
        List<Map<String, Object>> rows = jdbc.queryForList(sql);
        model.addAttribute("rows", rows);
        return "report/readiness";
    }
}