package net.amoc.controller.common;

import net.amoc.command.TemperatureAvg;
import net.amoc.command.WeatherAvg;
import net.amoc.dao.CommonDao;
import net.amoc.domain.common.Station;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author Atiqur Rahman
 * @since 01/06/2013 1:01 PM
 */
@Controller
public class DashboardController {
    private static final Logger log = Logger.getLogger(DashboardController.class);

    @Autowired
    @Qualifier("commonDao")
    private CommonDao commonDao;

    @RequestMapping(value = "/dashboard", method = RequestMethod.GET)
    public String showForm(HttpServletRequest request, ModelMap model) {

        List<Station> allStation = commonDao.getAllStation();
        model.put("stations", allStation);
        log.debug(allStation.size());
        return "common/mainPage";
    }

    @RequestMapping(value = "/dashboard/rainfall/{stationId}", method = RequestMethod.GET)
    @ResponseBody
    public String showData(@PathVariable String stationId, HttpServletRequest request, ModelMap model) {

        final List<WeatherAvg> allStation = commonDao.getWeatherAvg(stationId);

        StringBuilder sb = new StringBuilder();

        sb.append("{\n" +
                "    \"cols\": [\n" +
                getCol("year", "Year", "number") +
                "        ,\n" +
                getCol("rainFall", "Rain Faill", "number") +
                "    ],");
        sb.append("\"rows\": [");
        int idx = 0;
        for (WeatherAvg wa : allStation) {
            if (idx > 0) {
                sb.append(",");
            }
            sb.append(getRow(wa.getYear(), wa.getAvgRainFall()));
            idx++;
        }
        sb.append("]\n" +
                "}");
        return sb.toString();
    }

    @RequestMapping(value = "/dashboard/temperature/{stationId}", method = RequestMethod.GET)
    @ResponseBody
    public String showTemp(@PathVariable String stationId, HttpServletRequest request, ModelMap model) {

        final List<TemperatureAvg> allStation = commonDao.getTemAvg(stationId);

        StringBuilder sb = new StringBuilder();

        sb.append("{\n" +
                "    \"cols\": [\n" +
                getCol("year", "Year", "number") +
                "        ,\n" +
                getCol("max", "Max", "number") +
                "        ,\n" +
                getCol("min", "Min", "number") +
                "    ],");
        sb.append("\"rows\": [");
        int idx = 0;
        for (TemperatureAvg wa : allStation) {
            if (idx > 0) {
                sb.append(",");
            }
            sb.append(getRowTemp(wa.getYear(), wa.getAvgMax(), wa.getAvgMin()));
            idx++;
        }
        sb.append("]\n" +
                "}");
        return sb.toString();
    }

    private String getRowTemp(String year, double avgMax, double avgMin) {
        return "{\"c\":[" + getString(year) + "," + getNumber(avgMax)+ "," + getNumber(avgMin) + "]}";
    }

    public String getCol(String id, String label, String type) {
        return "{\"id\":\"\",\"label\":\"" + label + "\",\"pattern\":\"\",\"type\":\"number\"}";
    }

    public String getRow(String year, Double rainFall) {
        return "{\"c\":[" + getString(year) + "," + getNumber(rainFall) + "]}";
    }

    private String getNumber(Double rainFall) {
        return "{\"v\" : " + rainFall + ",\"f\":null}";
    }

    private String getString(String year) {
        return "{\"v\" : \"" + year + "\",\"f\":null}";
    }
}
