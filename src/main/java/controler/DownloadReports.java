/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controler;


import facade.CartsFacadeLocal;
import facade.ProductsFacadeLocal;
import java.io.IOException;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperPrint;
import servicios.ReportNotesDataSource;
import servicios.ReportService;

@Named(value = "downloadReports")
@RequestScoped
public class DownloadReports {

    
    @EJB
    ProductsFacadeLocal pfl;

    @EJB
    CartsFacadeLocal cfl;
    
    

    
    private ReportService reportService = new ReportService();

    /**
     * Creates a new instance of DownloadReports
     */
    public DownloadReports() {
    }

    public void download()throws JRException, IOException{
         JasperPrint jp = reportService.generateUserReport(new ReportNotesDataSource(cfl.leerTodos()));
            HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
            response.addHeader("Content-disposition", "attachment; filename=Carrito.pdf");
            ServletOutputStream sos = response.getOutputStream();
            JasperExportManager.exportReportToPdfStream(jp, sos);
            sos.flush();
            sos.close();
            FacesContext.getCurrentInstance().responseComplete();


        }
     
    }
    


