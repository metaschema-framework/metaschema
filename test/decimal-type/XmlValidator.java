import javax.xml.XMLConstants;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import java.io.File;
import org.xml.sax.SAXNotRecognizedException;
import org.xml.sax.SAXNotSupportedException;

public class XmlValidator {
    public static void main(String[] args) {
        if (args.length < 2) {
            System.out.println("Usage: java XmlValidator <schema.xsd> <document.xml>");
            System.exit(1);
        }

        String schemaPath = args[0];
        String xmlPath = args[1];

        File schemaFile = new File(schemaPath);
        File xmlFile = new File(xmlPath);

        if (!schemaFile.exists()) {
            System.out.println("Schema file not found: " + schemaPath);
            System.exit(1);
        }
        if (!xmlFile.exists()) {
            System.out.println("XML file not found: " + xmlPath);
            System.exit(1);
        }

        try {
            SchemaFactory factory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);

            // Harden against XXE / SSRF-style external resolution
            factory.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);
            try {
                factory.setProperty(XMLConstants.ACCESS_EXTERNAL_DTD, "");
                factory.setProperty(XMLConstants.ACCESS_EXTERNAL_SCHEMA, "");
            } catch (IllegalArgumentException | SAXNotRecognizedException | SAXNotSupportedException ex) {
                // Some JAXP implementations don't support these properties
                System.err.println("Warning: could not set external access restrictions: " + ex.getMessage());
            }

            Schema schema = factory.newSchema(schemaFile);
            Validator validator = schema.newValidator();

            try {
                validator.setProperty(XMLConstants.ACCESS_EXTERNAL_DTD, "");
                validator.setProperty(XMLConstants.ACCESS_EXTERNAL_SCHEMA, "");
            } catch (IllegalArgumentException ex) {
                System.err.println("Warning: could not set validator external access restrictions: " + ex.getMessage());
            }

            validator.validate(new StreamSource(xmlFile));
            System.out.println(xmlPath + " is VALID");
        } catch (Exception e) {
            System.err.println(xmlPath + " is INVALID: " + e.getMessage());
            System.exit(1);
        }
    }
}
