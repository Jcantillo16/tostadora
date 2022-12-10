/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servicios;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;

/**
 *
 * @author sergi
 */
public class ReportService {
    public JasperPrint generateUserReport(ReportNotesDataSource dataSource) throws JRException {
         Map<String, Object> parameters = new HashMap<>();
        parameters.put("title", "Tostadora De Cafe San Jorge");
        JasperPrint jp = JasperFillManager.fillReport(Paths.get("Carrito.jasper").toAbsolutePath().toString(), parameters, dataSource);
        return jp;
        
        
    }
    
}
