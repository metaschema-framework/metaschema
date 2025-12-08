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

        try {
            SchemaFactory factory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
            Schema schema = factory.newSchema(new File(schemaPath));
            Validator validator = schema.newValidator();
            validator.validate(new StreamSource(new File(xmlPath)));
            System.out.println(xmlPath + " is VALID");
        } catch (Exception e) {
            System.out.println(xmlPath + " is INVALID: " + e.getMessage());
            System.exit(1);
        }
    }
}
