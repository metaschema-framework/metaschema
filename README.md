# Metaschema

Metaschema provides a common, format-agnostic modeling framework supporting schema, code, and documentation generation *all in one*.

Current modeling technologies (i.e. XML Schema, JSON Schema, Schematron) are:

- Uneven in their modeling expressiveness and validation capability;
- Unable to express a single model that applies to all supported formats;
- Bound to specific formats (i.e., XML, JSON, YAML); and
- Time-consuming and labor-intensive when maintaining multiple format-specific models.

In addition to these basic challenges, there is a need to:

- Express and validate cross-instance relationships;
- Generate parsing code in multiple programming languages;
- Support lossless content conversion between all supported formats; and
- Maintain format-specific documentation.

To address the challenges above, Metaschema supports automated:

- Code and documentation generation;
- Robust rules-based, format agnostic validation capabilities; and
- Content conversion between supported formats (i.e., XML, JSON, YAML).

Metaschema achieves this with:

- A modeling abstraction that unifies the modeling and validation capabilities of existing schema and validation technologies.
- Tools that automatically generate code, schemas, documentation, and format-appropriate content converters from Metaschema-based models.

## Resources

- [Metaschema Specification](https://framework.metaschema.dev/specification/)
- [Getting Started with Metaschema](https://framework.metaschema.dev/tutorials/)
- [Metaschema Use Cases](https://framework.metaschema.dev/use/)
- [Metaschema XML Schema and XML and JSON Data Type Schemas](https://github.com/metaschema-framework/metaschema/tree/main/schema)

## Related work

- A [Java implementation](https://github.com/metaschema-framework/metaschema-java) provides Metaschema parsing, data instance validation and parsing, content conversion, Java code generation, and XSD and JSON schema generation capabilities.

## Relationship to prior work

The contents of this repository is based on work from the [Metaschema repository](https://github.com/usnistgov/metaschema/) maintained by the National Institute of Standards and Technology (NIST), the [contents of which have been dedicated in the worldwide public domain](https://github.com/metaschema-framework/metaschema/blob/0954786fb628039cb7dc008ccb7fa029ba251f16/LICENSE.md) using the [CC0 1.0 Universal](https://creativecommons.org/publicdomain/zero/1.0/) public domain dedication. This repository builds on this prior work, maintaining the [CCO license](https://github.com/metaschema-framework/metaschema/blob/main/LICENSE.md) on any new works in this repository.

This work is focused on adding general use features needed by the Metaschema user community, to include new Module capabilities, greater support for external constraints, support for additional formats, and improved documentation. This work is intended to backwards compatible with the prior NIST work, allowing Metaschema modules produced under earlier versions of Metaschema to continue to work as intended.
