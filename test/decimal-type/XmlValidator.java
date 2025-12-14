import javax.xml.XMLConstants;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import java.io.File;

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
            Schema schema = factory.newSchema(schemaFile);
            Validator validator = schema.newValidator();
            validator.validate(new StreamSource(xmlFile));
            System.out.println(xmlPath + " is VALID");
        } catch (Exception e) {
            System.out.println(xmlPath + " is INVALID: " + e.getMessage());
            System.exit(1);
        }
    }
}
