# Decimal Datatype Test Cases

This directory contains test schemas and content examples to validate the decimal datatype behavior as defined in PR #135.

## Schemas

- `decimal-test.xsd` - XML Schema with inline DecimalDatatype using pattern `-?(0|[1-9][0-9]*)(\.[0-9]+)?([eE][+-]?[0-9]+)?` and base type `xs:double`
- `decimal-test.json` - JSON Schema with inline DecimalDatatype using `type: "number"`

## Expected Behavior Matrix (Tested)

| Test Case | Example | JSON | XML |
|-----------|---------|:----:|:---:|
| Integers | `12` | ✓ | ✓ |
| Decimals | `12.34` | ✓ | ✓ |
| Positive with leading + | `+12.34` | ✗ | ✗ |
| Exponential notation | `1e3`, `1E+4`, `-2.5e-10` | ✓ | ✓ |
| Leading zeros | `01.23` | ✗ | ✗ |
| No leading digit | `.47` | ✗ | ✗ |
| Trailing decimal point | `123.` | ✗ | ✗ |
| Leading/trailing whitespace | ` 12.34 ` | ✓ | ✓ |

**Legend:** ✓ = Valid, ✗ = Invalid

### Notes

Both JSON and XML ignore/normalize whitespace around numeric values, so leading/trailing whitespace is accepted in both formats.

## Test Files

### XML Test Cases

- `xml-valid-cases.xml` - Contains values that SHOULD pass XML Schema validation
- `xml-invalid-cases.xml` - Contains commented-out invalid cases for individual testing

### JSON Test Cases

- `json-valid-cases.json` - Contains values that SHOULD pass JSON Schema validation
- `json-invalid-cases.json` - Documents invalid cases (many cannot be represented in valid JSON syntax)

## Key Differences Between JSON and XML

1. **Whitespace Handling**: Both XML Schema and JSON ignore whitespace around numeric values. XML Schema applies whitespace normalization (collapse) for `xs:double` before pattern matching. JSON ignores whitespace around values during parsing.

2. **Syntax Restrictions**: JSON syntax itself prohibits leading `+`, leading zeros (e.g., `01.23`), `.47`, and `123.` formats, making these parse errors rather than validation errors. The XML pattern explicitly rejects these as well.

## Running Validation Tests

### XML Validation

#### Using xmllint
```bash
xmllint --schema decimal-test.xsd xml-valid-cases.xml --noout
```

#### Using the Java XmlValidator (via Maven)
```bash
# Compile the validator
mvn compile

# Run validation on a specific XML file
mvn exec:java -Dexec.args="decimal-test.xsd xml-valid-cases.xml"
```

### JSON Validation (using ajv-cli)
```bash
ajv validate -s decimal-test.json -d json-valid-cases.json
```
