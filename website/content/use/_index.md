---
title: "Using the Metaschema Framework"
linkTitle: "Using Metaschema"
menu:
  primary:
    name: Using
    weight: 10
---

# Using the Metaschema Information Modeling Framework

Currently, a Metaschema [module](/specification/glossary/#metaschema-module) is defined using an [XML Schema-based format](https://github.com/metaschema-framework/metaschema/blob/develop/schema/xml/metaschema.xsd) and also using the [Metaschema module format](https://github.com/metaschema-framework/metaschema/blob/develop/schema/metaschema/metaschema-module-metaschema.xml).

An [ISO Schematron](https://schematron.com/) ruleset is also [provided](https://github.com/usnistgov/metaschema-xslt/blob/develop/src/validate/metaschema-composition-check.sch) to enforce some of the rules described in the Metaschema [specification](/specification/).

A set of [Metaschema external constraints](https://github.com/metaschema-framework/metaschema/blob/develop/schema/metaschema/metaschema-module-constraints.xml) are also provided that enforce many of the same rules as the ISO Schematron.

A [tutorial](/tutorials/1-getting-started/) covering basic concepts is provided that will walk you through an example of creating a new Metaschema module.

## Schema Generation

A Metaschema module can be used to generate a schema, expressed as an XML or JSON Schema, for the corresponding [*data model*](/specification/glossary/#data-model) in a format-specific, serializable form (e.g., XML, JSON, YAML). These generated schemas can be used to *validate* that data to be processed by the system is well-formed and consistent with the requirements of the data model.

## Other Generative Use Cases

Metaschema modules are used to generate other model-related artifacts based on the metaschema description. These artifacts include:

- Conversion utilities that can convert content instances between the XML and JSON formats derived from a given metaschema definition, ensuring the resulting content is schema-valid, model-identical, and equivalent to its input (without loss or addition when processed in a Metaschema-aware context).
- Model-aware utilities and mock datasets for generic display, data conversion, reformatting, and testing.
- XML and JSON model documentation for use on a website (e.g., the [OSCAL website](https://pages.nist.gov/OSCAL/documentation/schema/)).
- Programming language APIs used for parsing data conformant to a given data model into a set of language-specific objects, and also writing data in language-specific objects out to one of the supported data model formats.

## Related work

- A [Java implementation](https://github.com/metaschema-framework/metaschema-java) provides Metaschema parsing, data instance validation and parsing, content conversion, Java code generation, and XSD and JSON schema generation capabilities.
- An [XSLT implementation](https://github.com/usnistgov/metaschema-xslt) provides data instance validation, content conversion, documentation generation, and XSD and JSON schema generation capabilities. Note: This version is not actively maintained and may not support the full set of Metaschema features as compared to the Java implementation.
