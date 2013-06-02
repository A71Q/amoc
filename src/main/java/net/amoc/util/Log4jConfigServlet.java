package net.amoc.util;

import net.amoc.web.SessionKeys;
import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.LogManager;
import org.apache.log4j.xml.DOMConfigurator;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * User: atiq2
 * Date: Dec 10, 2007
 */

/**
 * Initialize log4j.  This servlet is also used to re-load the log4j.properties files.  A GET request sent to this
 * servlet will make it re-read the properties file.
 * <p/>
 * This was originally written by Drew/Wai Nam.  Later we put the log4j.properties file in the classpath for making
 * things simpler.  Now we are going back to this servlet because we often need to change the log4j config and
 * we do not want to re-start tomcat for that.
 * <p/>
 * The location of the lo4j config file should be specified using a context parameter to this servlet, named
 * <code>log4jConfigLocation</code>.  The file location is relatived to WEB-INF.
 */
public class Log4jConfigServlet extends HttpServlet {

    /**
     * Read the config file.
     */
    private String reloadLog4jProps() {
        String file = getServletContext().getInitParameter("log4jConfigLocation");
        String fullPath = null;

        if (file != null) {
            fullPath = getServletContext().getRealPath(file);
            DOMConfigurator.configure(fullPath);
        } else {
            BasicConfigurator.configure();
        }

        return fullPath;
    }

    /**
     * Called when at servlet startup.
     */
    public void init() {
        String path = reloadLog4jProps();
        System.out.println("Log4jConfigServlet: Configured log4j from [" + path + "]");
    }

    public void doGet(HttpServletRequest req, HttpServletResponse res) {

        /*
         * Check if user is logged in.  This should protect us from abuse by random evil spirit floating
         * in the world.
         */
        HttpSession sess = req.getSession(false);
        if (sess == null) {
            return;
        }

        Object login = sess.getAttribute(SessionKeys.AUTH);
        if (login == null) {
            return;
        }

        LogManager.resetConfiguration();         // out with the old...
        String path = reloadLog4jProps();               // in with the new.

        System.out.println("Log4jConfigServlet: Re-loaded log4j config file");

        res.setContentType("text/html");

        try {
            PrintWriter pw = res.getWriter();

            String cont = new StringBuffer().append("<html>\n")
                    .append("<body>\n")
                    .append("Reloaded Log4j properties:<br><code>")
                    .append(path)
                    .append("</code>\n")
                    .append("</body>\n</html>\n")
                    .toString();
            pw.write(cont);

        } catch (IOException ioe) {
            System.out.println("Log4jConfigServlet: Cannot write response");
        }
    }

}
