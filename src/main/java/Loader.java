import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.swing.text.DateFormatter;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Timer;

public class Loader {

    public static void main(String[] args) throws Exception {

        String fileName = "res/data-18M.xml";
        File file = new File(fileName);

        long start = System.currentTimeMillis();
        long usage = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();

        SAXParserFactory factory = SAXParserFactory.newInstance();
        SAXParser parser = factory.newSAXParser();
        XMLHandler xmlHandler = new XMLHandler();

        parser.parse(file, xmlHandler);

        DBConnection.printVoterCounts();

        usage = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory() - usage;
        System.out.println(usage);
        System.out.println((float) (System.currentTimeMillis() - start) / 1000);


    }
}