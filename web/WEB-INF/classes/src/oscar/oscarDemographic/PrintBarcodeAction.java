package oscar.oscarDemographic;

import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.HashMap;

import javax.servlet.ServletContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.oscarehr.PMmodule.web.BaseClientAction;

import com.quatro.common.KeyConstants;
import com.quatro.model.security.NoAccessException;

import oscar.OscarAction;
import oscar.OscarDocumentCreator;
import net.sf.jasperreports.engine.JREmptyDataSource;

public class PrintBarcodeAction extends BaseClientAction {
	private static final String TASK_FILL = "fill";
	private static final String TASK_PRINT = "print";
	private static final String TASK_PDF = "pdf";
	private static final String TASK_XML = "xml";
	private static final String TASK_XML_EMBED = "xmlEmbed";
	private static final String TASK_HTML = "html";
	private static final String TASK_RTF = "rtf";
	private static final String TASK_XLS = "xls";
	private static final String TASK_JXL = "jxl";
	private static final String TASK_CSV = "csv";
	private static final String TASK_ODT = "odt";
	private static final String TASK_RUN = "run";

    public PrintBarcodeAction() {
    }

    public ActionForward unspecified(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) throws NoAccessException {
        //patient
    	super.getAccess(request, KeyConstants.FUN_CLIENTPRINTLABEL, new Integer(0));
    	String classpath = (String)request.getSession().getServletContext().getAttribute("org.apache.catalina.jsp_classpath");
        if (classpath==null) classpath = (String)request.getSession().getServletContext().getAttribute("com.ibm.websphere.servlet.application.classpath");
        
        System.setProperty("jasper.reports.compile.class.path", classpath);

        HashMap parameters = new HashMap();
        parameters.put("demo", request.getParameter("clientId"));
        ServletOutputStream sos = null;
        InputStream ins = null;
        try {
            ServletContext context = getServlet().getServletContext();
            ins = getClass().getResourceAsStream("/oscar/oscarDemographic/barcode.jrxml");
//            ins = context.getResourceAsStream("/label.xml");
//            ins = new FileInputStream(System.getProperty("user.home") + "/label.xml");
        }
        catch (Exception ex1) {
            ex1.printStackTrace();
        }

        try {
            sos = response.getOutputStream();
        }
        catch (IOException ex) {
            ex.printStackTrace();
        }

        response.setHeader("Content-disposition", getHeader(response).toString());
        OscarDocumentCreator osc = new OscarDocumentCreator();
        try {
//            osc.fillDocumentStream( parameters, sos, "pdf", ins, DbConnectionFilter.getThreadLocalDbConnection());
            osc.fillDocumentStream( parameters, sos, "pdf", ins, new JREmptyDataSource());
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        return actionMapping.findForward("success");
    }

    private StringBuffer getHeader(HttpServletResponse response) {
        StringBuffer strHeader = new StringBuffer();
        strHeader.append("label_");
        strHeader.append(".pdf");
        response.setHeader("Cache-Control", "max-age=0");
        response.setDateHeader("Expires", 0);
        response.setContentType("application/pdf");
        StringBuffer sbContentDispValue = new StringBuffer();
        sbContentDispValue.append("inline; filename="); //inline - display
        sbContentDispValue.append(strHeader);
        return sbContentDispValue;
    }
}
