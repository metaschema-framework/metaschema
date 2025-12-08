---
title: "Metapath vs. XPath 3.1: Key Differences"
linkTitle: "Metapath vs. XPath"
description: "Documents the key differences between Metapath and XPath 3.1, including data model, format support, function library, and feature compatibility."
weight: 61
---

# Metapath vs. XPath 3.1: Key Differences

## Overview

**Metapath** is an expression language that is part of the Metaschema Information Modeling Framework. While Metapath is explicitly described as "a customization of the [XML Path Language (XPath) 3.1](https://www.w3.org/TR/2017/REC-xpath-31-20170321/), which has been adjusted to work with a Metaschema module based model" ([Metapath Specification](../metapath/)), there are significant differences between the two languages.

The Metapath specification notes: "Not all XPath features are supported by Metapath, the specifics of which will be documented in an updated version of this page in the future" ([Metapath Specification](../metapath/)).

---

## 1. Fundamental Data Model Differences

### XPath 3.1 Data Model

XPath 3.1 operates on the [XML Data Model (XDM) 3.1](https://www.w3.org/TR/xpath-datamodel-31/), which exposes seven kinds of nodes:

- **Document nodes** (root)
- **Element nodes**
- **Attribute nodes**
- **Text nodes**
- **Comment nodes**
- **Processing instruction nodes**
- **Namespace nodes**

### Metapath Data Model

Metapath replaces the XML Data Model with the **Metaschema data model**, which exposes three primary node types:

| Metapath Node Type | Description | XPath Nearest Equivalent |
|--------------------|-------------|-------------------------|
| **Flag** | Simple, named information element with a value; leaf nodes used for identifying/qualifying information | Attribute (`@`) |
| **Field** | Complex named information element with a value and optional flag instances; edge nodes | Element with text content |
| **Assembly** | Complex composite information element with no value of its own; contains flags and a model of fields/assemblies | Element with child elements |

**Key Distinction**: In Metapath, there is no equivalent to XPath's text nodes, comment nodes, processing instruction nodes, or namespace nodes. The data model is designed around the Metaschema module constructs rather than XML serialization details.

**Reference**: [Information Modeling Specification](/specification/information-modeling/)

---

## 2. Format Agnosticism

### XPath 3.1

XPath 3.1 is inherently tied to XML. While implementations may work with JSON through conversion, XPath itself assumes XML document structure.

### Metapath

Metapath is designed to be **format-agnostic** and can query:

- XML
- JSON
- YAML

As stated in the specification: "A Metapath can be used to query all Metaschema supported formats (i.e., JSON, YAML, XML) using a common, Metaschema module-bound syntax. This means a Metapath can be used to query the same data regardless of the underlying format used, as long as that data is bound to a Metaschema module" ([Metapath Specification](../metapath/)).

This format agnosticism was a primary design goal, as other path languages like JSON Path and JSON Pointer "were not chosen, due to limitations in *evaluation* capabilities and because their syntax was specific to JSON" ([Metapath Specification](../metapath/)).

---

## 3. Node Selection and Addressing

### XPath 3.1 Approach

XPath uses axes (child, parent, ancestor, descendant, attribute, etc.) to navigate the XML tree. Attributes are accessed using the `@` prefix.

```xpath
/catalog/book/@isbn
//book/title
../sibling-element
```

### Metapath Approach

Metapath paths reference Metaschema model constructs directly:

- **Flags are accessed using the `@` prefix**, mirroring XPath's attribute syntax
- Fields and assemblies are accessed as path steps (similar to XPath element navigation)
- Navigation is through the Metaschema module's defined model structure
- The current context (`.`) refers to a flag, field, or assembly node

```metapath
.                    (current node)
..                   (parent node)
@flag-name           (flag on current node)
child-field          (child field or assembly)
$parent/sibling      (variable reference)
parent/@id           (flag 'id' on 'parent' node)
```

**Reference**: [Constraints Specification - Target](../constraints/#target)

---

## 4. Function Library

### XPath 3.1

XPath 3.1 includes over 200 built-in functions organized in namespaces including `fn:`, `math:`, `map:`, and `array:`. See [XPath and XQuery Functions and Operators 3.1](https://www.w3.org/TR/xpath-functions-31/).

### Metapath

Metapath implements a substantial subset of XPath 3.1 functions plus custom extensions. Based on [DefaultFunctionLibrary.java](https://github.com/metaschema-framework/metaschema-java/blob/develop/core/src/main/java/gov/nist/secauto/metaschema/core/metapath/function/library/DefaultFunctionLibrary.java) is the manifest of all implemented and planned to be implemented functions for [the reference implementation](https://github.com/metaschema-framework/metaschema-java). Below is an overview of these functions, categorized thematically into their capabilities.

#### Core Functions (`fn:`)

**Boolean Functions:**
- [`true()`](https://www.w3.org/TR/xpath-functions-31/#func-true), [`false()`](https://www.w3.org/TR/xpath-functions-31/#func-false), [`not()`](https://www.w3.org/TR/xpath-functions-31/#func-not), [`boolean()`](https://www.w3.org/TR/xpath-functions-31/#func-boolean)

**Numeric Functions:**
- [`abs()`](https://www.w3.org/TR/xpath-functions-31/#func-abs), [`ceiling()`](https://www.w3.org/TR/xpath-functions-31/#func-ceiling), [`floor()`](https://www.w3.org/TR/xpath-functions-31/#func-floor), [`round()`](https://www.w3.org/TR/xpath-functions-31/#func-round)
- [`avg()`](https://www.w3.org/TR/xpath-functions-31/#func-avg), [`count()`](https://www.w3.org/TR/xpath-functions-31/#func-count), [`max()`](https://www.w3.org/TR/xpath-functions-31/#func-max), [`min()`](https://www.w3.org/TR/xpath-functions-31/#func-min), [`sum()`](https://www.w3.org/TR/xpath-functions-31/#func-sum)

**String Functions:**
- [`compare()`](https://www.w3.org/TR/xpath-functions-31/#func-compare), [`concat()`](https://www.w3.org/TR/xpath-functions-31/#func-concat), [`contains()`](https://www.w3.org/TR/xpath-functions-31/#func-contains)
- [`starts-with()`](https://www.w3.org/TR/xpath-functions-31/#func-starts-with), [`ends-with()`](https://www.w3.org/TR/xpath-functions-31/#func-ends-with)
- [`lower-case()`](https://www.w3.org/TR/xpath-functions-31/#func-lower-case), [`upper-case()`](https://www.w3.org/TR/xpath-functions-31/#func-upper-case)
- [`normalize-space()`](https://www.w3.org/TR/xpath-functions-31/#func-normalize-space), [`string()`](https://www.w3.org/TR/xpath-functions-31/#func-string)
- [`string-length()`](https://www.w3.org/TR/xpath-functions-31/#func-string-length), [`string-join()`](https://www.w3.org/TR/xpath-functions-31/#func-string-join)
- [`substring()`](https://www.w3.org/TR/xpath-functions-31/#func-substring), [`substring-after()`](https://www.w3.org/TR/xpath-functions-31/#func-substring-after), [`substring-before()`](https://www.w3.org/TR/xpath-functions-31/#func-substring-before)
- [`matches()`](https://www.w3.org/TR/xpath-functions-31/#func-matches), [`tokenize()`](https://www.w3.org/TR/xpath-functions-31/#func-tokenize)

**Sequence Functions:**
- [`empty()`](https://www.w3.org/TR/xpath-functions-31/#func-empty), [`exists()`](https://www.w3.org/TR/xpath-functions-31/#func-exists)
- [`head()`](https://www.w3.org/TR/xpath-functions-31/#func-head), [`tail()`](https://www.w3.org/TR/xpath-functions-31/#func-tail), [`reverse()`](https://www.w3.org/TR/xpath-functions-31/#func-reverse)
- [`distinct-values()`](https://www.w3.org/TR/xpath-functions-31/#func-distinct-values), [`index-of()`](https://www.w3.org/TR/xpath-functions-31/#func-index-of)
- [`insert-before()`](https://www.w3.org/TR/xpath-functions-31/#func-insert-before), [`remove()`](https://www.w3.org/TR/xpath-functions-31/#func-remove)
- [`zero-or-one()`](https://www.w3.org/TR/xpath-functions-31/#func-zero-or-one), [`one-or-more()`](https://www.w3.org/TR/xpath-functions-31/#func-one-or-more), [`exactly-one()`](https://www.w3.org/TR/xpath-functions-31/#func-exactly-one)

**Node Functions:**
- [`data()`](https://www.w3.org/TR/xpath-functions-31/#func-data), [`path()`](https://www.w3.org/TR/xpath-functions-31/#func-path), [`root()`](https://www.w3.org/TR/xpath-functions-31/#func-root)
- [`local-name()`](https://www.w3.org/TR/xpath-functions-31/#func-local-name), [`name()`](https://www.w3.org/TR/xpath-functions-31/#func-name), [`namespace-uri()`](https://www.w3.org/TR/xpath-functions-31/#func-namespace-uri)
- [`has-children()`](https://www.w3.org/TR/xpath-functions-31/#func-has-children), [`innermost()`](https://www.w3.org/TR/xpath-functions-31/#func-innermost), [`outermost()`](https://www.w3.org/TR/xpath-functions-31/#func-outermost)

**URI Functions:**
- [`base-uri()`](https://www.w3.org/TR/xpath-functions-31/#func-base-uri), [`document-uri()`](https://www.w3.org/TR/xpath-functions-31/#func-document-uri), [`static-base-uri()`](https://www.w3.org/TR/xpath-functions-31/#func-static-base-uri)
- [`resolve-uri()`](https://www.w3.org/TR/xpath-functions-31/#func-resolve-uri), [`QName()`](https://www.w3.org/TR/xpath-functions-31/#func-QName)

**Document Functions:**
- [`doc()`](https://www.w3.org/TR/xpath-functions-31/#func-doc), [`doc-available()`](https://www.w3.org/TR/xpath-functions-31/#func-doc-available)

**Date/Time Functions:**
- [`current-date()`](https://www.w3.org/TR/xpath-functions-31/#func-current-date), [`current-dateTime()`](https://www.w3.org/TR/xpath-functions-31/#func-current-dateTime), [`current-time()`](https://www.w3.org/TR/xpath-functions-31/#func-current-time)
- [`dateTime()`](https://www.w3.org/TR/xpath-functions-31/#func-dateTime), [`implicit-timezone()`](https://www.w3.org/TR/xpath-functions-31/#func-implicit-timezone)

**Date/Time Component Extraction:**
- [`year-from-date()`](https://www.w3.org/TR/xpath-functions-31/#func-year-from-date), [`year-from-dateTime()`](https://www.w3.org/TR/xpath-functions-31/#func-year-from-dateTime)
- [`month-from-date()`](https://www.w3.org/TR/xpath-functions-31/#func-month-from-date), [`month-from-dateTime()`](https://www.w3.org/TR/xpath-functions-31/#func-month-from-dateTime)
- [`day-from-date()`](https://www.w3.org/TR/xpath-functions-31/#func-day-from-date), [`day-from-dateTime()`](https://www.w3.org/TR/xpath-functions-31/#func-day-from-dateTime)
- [`hours-from-dateTime()`](https://www.w3.org/TR/xpath-functions-31/#func-hours-from-dateTime), [`hours-from-time()`](https://www.w3.org/TR/xpath-functions-31/#func-hours-from-time)
- [`minutes-from-dateTime()`](https://www.w3.org/TR/xpath-functions-31/#func-minutes-from-dateTime), [`minutes-from-time()`](https://www.w3.org/TR/xpath-functions-31/#func-minutes-from-time)
- [`seconds-from-dateTime()`](https://www.w3.org/TR/xpath-functions-31/#func-seconds-from-dateTime), [`seconds-from-time()`](https://www.w3.org/TR/xpath-functions-31/#func-seconds-from-time)

**Duration Component Extraction:**
- [`years-from-duration()`](https://www.w3.org/TR/xpath-functions-31/#func-years-from-duration), [`months-from-duration()`](https://www.w3.org/TR/xpath-functions-31/#func-months-from-duration)
- [`days-from-duration()`](https://www.w3.org/TR/xpath-functions-31/#func-days-from-duration), [`hours-from-duration()`](https://www.w3.org/TR/xpath-functions-31/#func-hours-from-duration)
- [`minutes-from-duration()`](https://www.w3.org/TR/xpath-functions-31/#func-minutes-from-duration), [`seconds-from-duration()`](https://www.w3.org/TR/xpath-functions-31/#func-seconds-from-duration)

**Timezone Functions:**
- [`adjust-dateTime-to-timezone()`](https://www.w3.org/TR/xpath-functions-31/#func-adjust-dateTime-to-timezone), [`adjust-date-to-timezone()`](https://www.w3.org/TR/xpath-functions-31/#func-adjust-date-to-timezone), [`adjust-time-to-timezone()`](https://www.w3.org/TR/xpath-functions-31/#func-adjust-time-to-timezone)
- [`timezone-from-date()`](https://www.w3.org/TR/xpath-functions-31/#func-timezone-from-date), [`timezone-from-dateTime()`](https://www.w3.org/TR/xpath-functions-31/#func-timezone-from-dateTime), [`timezone-from-time()`](https://www.w3.org/TR/xpath-functions-31/#func-timezone-from-time)

**Comparison Functions:**
- [`deep-equal()`](https://www.w3.org/TR/xpath-functions-31/#func-deep-equal)

**Higher-Order Functions:**
- [`function-arity()`](https://www.w3.org/TR/xpath-functions-31/#func-function-arity), [`function-lookup()`](https://www.w3.org/TR/xpath-functions-31/#func-function-lookup), [`function-name()`](https://www.w3.org/TR/xpath-functions-31/#func-function-name)

#### Array Functions (`array:`)

- [`array:get()`](https://www.w3.org/TR/xpath-functions-31/#func-array-get) - Get member at index
- [`array:size()`](https://www.w3.org/TR/xpath-functions-31/#func-array-size) - Get array size
- [`array:put()`](https://www.w3.org/TR/xpath-functions-31/#func-array-put) - Replace member at index
- [`array:append()`](https://www.w3.org/TR/xpath-functions-31/#func-array-append) - Append member
- [`array:subarray()`](https://www.w3.org/TR/xpath-functions-31/#func-array-subarray) - Extract subarray
- [`array:remove()`](https://www.w3.org/TR/xpath-functions-31/#func-array-remove) - Remove member at index
- [`array:insert-before()`](https://www.w3.org/TR/xpath-functions-31/#func-array-insert-before) - Insert member before index
- [`array:head()`](https://www.w3.org/TR/xpath-functions-31/#func-array-head) - Get first member
- [`array:tail()`](https://www.w3.org/TR/xpath-functions-31/#func-array-tail) - Get all but first member
- [`array:reverse()`](https://www.w3.org/TR/xpath-functions-31/#func-array-reverse) - Reverse array
- [`array:join()`](https://www.w3.org/TR/xpath-functions-31/#func-array-join) - Concatenate arrays
- [`array:flatten()`](https://www.w3.org/TR/xpath-functions-31/#func-array-flatten) - Flatten nested arrays

#### Map Functions (`map:`)

- [`map:merge()`](https://www.w3.org/TR/xpath-functions-31/#func-map-merge) - Merge maps
- [`map:size()`](https://www.w3.org/TR/xpath-functions-31/#func-map-size) - Get map size
- [`map:keys()`](https://www.w3.org/TR/xpath-functions-31/#func-map-keys) - Get all keys
- [`map:contains()`](https://www.w3.org/TR/xpath-functions-31/#func-map-contains) - Check if key exists
- [`map:get()`](https://www.w3.org/TR/xpath-functions-31/#func-map-get) - Get value for key
- [`map:find()`](https://www.w3.org/TR/xpath-functions-31/#func-map-find) - Find values for key recursively
- [`map:put()`](https://www.w3.org/TR/xpath-functions-31/#func-map-put) - Add/update entry
- [`map:entry()`](https://www.w3.org/TR/xpath-functions-31/#func-map-entry) - Create single-entry map
- [`map:remove()`](https://www.w3.org/TR/xpath-functions-31/#func-map-remove) - Remove entry
- [`map:for-each()`](https://www.w3.org/TR/xpath-functions-31/#func-map-for-each) - Apply function to each entry

#### Metapath-Specific Functions (`mp:`)

| Function | Description |
|----------|-------------|
| `mp:recurse-depth()` | Recursive resolution of linked documents |
| `mp:base64-decode()` | Decode base64 content |
| `mp:base64-encode()` | Encode to base64 |

#### Cast Functions

Metapath automatically generates cast functions for all Metaschema data types (e.g., `xs:string()`, `xs:integer()`, `xs:boolean()`, etc.).

#### Functions Marked for Future Implementation

The source code indicates these XPath 3.1 functions are planned but not yet implemented:

**P1 (Priority 1):**
- [`generate-id()`](https://www.w3.org/TR/xpath-functions-31/#func-generate-id), [`last()`](https://www.w3.org/TR/xpath-functions-31/#func-last), [`replace()`](https://www.w3.org/TR/xpath-functions-31/#func-replace)
- [`round-half-to-even()`](https://www.w3.org/TR/xpath-functions-31/#func-round-half-to-even), [`subsequence()`](https://www.w3.org/TR/xpath-functions-31/#func-subsequence), [`translate()`](https://www.w3.org/TR/xpath-functions-31/#func-translate)

**P2 (Priority 2):**
- [`format-date()`](https://www.w3.org/TR/xpath-functions-31/#func-format-date), [`format-dateTime()`](https://www.w3.org/TR/xpath-functions-31/#func-format-dateTime), [`format-integer()`](https://www.w3.org/TR/xpath-functions-31/#func-format-integer)
- [`format-number()`](https://www.w3.org/TR/xpath-functions-31/#func-format-number), [`format-time()`](https://www.w3.org/TR/xpath-functions-31/#func-format-time), [`position()`](https://www.w3.org/TR/xpath-functions-31/#func-position)

**P3 (Priority 3):**
- [`array:for-each()`](https://www.w3.org/TR/xpath-functions-31/#func-array-for-each), [`array:filter()`](https://www.w3.org/TR/xpath-functions-31/#func-array-filter)
- [`array:fold-left()`](https://www.w3.org/TR/xpath-functions-31/#func-array-fold-left), [`array:fold-right()`](https://www.w3.org/TR/xpath-functions-31/#func-array-fold-right)
- [`array:for-each-pair()`](https://www.w3.org/TR/xpath-functions-31/#func-array-for-each-pair), [`array:sort()`](https://www.w3.org/TR/xpath-functions-31/#func-array-sort)

**Namespace URIs**:
- XPath Functions namespace: `http://www.w3.org/2005/xpath-functions`
- Metapath namespace: `http://csrc.nist.gov/ns/metaschema/metapath` (prefix: `mp`)

*Reference: [DefaultFunctionLibrary.java](https://github.com/metaschema-framework/metaschema-java/blob/develop/core/src/main/java/gov/nist/secauto/metaschema/core/metapath/function/library/DefaultFunctionLibrary.java)*

---

## 5. Variable Binding

### XPath 3.1

XPath 3.1 supports `let` expressions for variable binding:

```xpath
let $x := /catalog/book return $x/title
```

### Metapath

Metapath supports variable binding in two ways:

1. **Programmatically** - Variables can be bound through the Metapath API when executing expressions
2. **Using `let` statements** - Within the Metaschema constraint model via the `<let>` assembly

**Example using `let` in constraints:**

```xml
<constraint>
  <let var="parent" expression=".."/>
  <let var="sibling-count" expression="count($parent/sibling)"/>
  <expect target="." test="$sibling-count = 3"/>
</constraint>
```

**Key behaviors**:
- Variables are evaluated in encounter order
- If a previous variable is bound with the same name, the new value is bound in a sub-context to avoid side effects
- The sub-context is available to subsequent constraints and child node constraints

**Reference**: [Let Expressions Specification](../constraints/#let-expressions)

---

## 6. XPath 3.1 Feature Support in Metapath

Metapath supports most XPath 3.1 expression features. Based on the [Metapath10.g4 grammar](https://github.com/metaschema-framework/metaschema-java/blob/develop/core/src/main/antlr4/Metapath10.g4):

### Fully Supported XPath 3.1 Features

| Feature Category | Supported Features |
|-----------------|-------------------|
| **Expressions** | `for`, `let`, `some`/`every` (quantified), `if`/`then`/`else` |
| **Logical** | `or`, `and` |
| **Comparison** | General (`=`, `!=`, `<`, `<=`, `>`, `>=`) and Value (`eq`, `ne`, `lt`, `le`, `gt`, `ge`) |
| **Arithmetic** | `+`, `-`, `*`, `div`, `idiv`, `mod`, unary `+`/`-` |
| **String** | Concatenation operator (`\|\|`) |
| **Sequences** | Range (`to`), union (`\|`, `union`), `intersect`, `except` |
| **Type** | `instance of`, `treat as`, `cast as`, `castable as` |
| **Operators** | Arrow (`=>`), Simple map (`!`) |
| **Constructors** | Arrays (square `[]` and curly `array {}`), Maps (`map {}`) |
| **Functions** | Static calls, named function references (`#`), inline functions |
| **Lookups** | Postfix lookup, unary lookup (`?`) |
| **Path** | Absolute (`/`, `//`), relative paths, predicates |

### Supported Axes

**Forward axes:**
- `child::`, `descendant::`, `self::`, `descendant-or-self::`
- `following-sibling::`, `following::`
- `flag::` *(Metapath-specific for accessing flags)*

**Reverse axes:**
- `parent::`, `ancestor::`, `ancestor-or-self::`
- `preceding-sibling::`, `preceding::`

**Abbreviated syntax:**
- `.` (context item), `..` (parent), `@` (flag/attribute), `//` (descendant-or-self)

### Features Not Supported (Data Model Differences)

| XPath 3.1 Feature | Reason |
|-------------------|--------|
| Node comparisons (`is`, `<<`, `>>`) | Commented out in grammar; not applicable to Metaschema model |
| `namespace::` axis | No namespace nodes in Metaschema model |
| `comment()` test | No comment nodes in Metaschema model |
| `processing-instruction()` test | No PI nodes in Metaschema model |
| `namespace-node()` test | No namespace nodes in Metaschema model |
| `text()` test | Text is represented as field/flag values, not separate nodes |
| `schema-element()`, `schema-attribute()` tests | Not applicable to Metaschema model |

### Metapath-Specific Extensions

| Feature | Description |
|---------|-------------|
| `flag::` axis | Dedicated axis for navigating to flag nodes |
| `flag()` kind test | Test for flag nodes |
| `field()` kind test | Test for field nodes |
| `assembly()` kind test | Test for assembly nodes |

*Reference: The grammar is derived from the [XPath 3.1 grammar by Ken Domino et al.](https://github.com/antlr/grammars-v4/blob/63359bd91593ece31a384acd507ae860d6cf7ff7/xpath/xpath31/XPath31Parser.g4) with modifications for the Metaschema data model.*

---

## 7. Constraint Integration

### XPath 3.1

XPath expressions can be used with XSLT, XQuery, or Schematron for validation, but this requires additional tooling.

### Metapath

Metapath is natively integrated with Metaschema's **constraint system**. Constraints are first-class citizens:

- `allowed-values` - Enumerated value restrictions
- `expect` - Boolean test expressions
- `has-cardinality` - Occurrence count validation  
- `index` - Create addressable indexes
- `index-has-key` - Cross-reference validation
- `is-unique` - Uniqueness validation
- `matches` - Datatype and regex validation

**Reference**: [Constraints Specification](../constraints/)

---

## 8. Evaluation Context Differences

### XPath 3.1

The evaluation context includes:
- Static context (namespaces, variable declarations, function signatures)
- Dynamic context (context item, position, size, variable values, current date/time)
- Focus (context item, context position, context size)

### Metapath

The evaluation context is tied to the **Metaschema module**:
- The **evaluation focus** is the content node being processed
- Constraints are evaluated against content nodes associated with definitions
- Variables can be bound through `let` expressions in the constraint context

**Key Point**: "A constraint is defined relative to an assembly, field, or flag definition in a Metaschema module. All constraints associated with a definition MUST be evaluated against all associated content nodes" ([Constraints Processing](../constraints/#constraint-processing)).

---

## 9. Type System

### XPath 3.1

Uses XSD (XML Schema Definition) types and the XDM type hierarchy, including atomic types, node types, and function types.

### Metapath

Uses [Metaschema data types](/specification/datatypes/), which include:

**Simple types:**
- `string`, `token`, `uri`, `uri-reference`, `uuid`
- `decimal`, `integer`, `non-negative-integer`, `positive-integer`
- `boolean`
- `date`, `date-time`, `date-with-timezone`, `date-time-with-timezone`
- `day-time-duration`, `year-month-duration`
- `base64`, `email-address`, `hostname`, `ip-v4-address`, `ip-v6-address`

**Markup types:**
- `markup-line` (inline markup)
- `markup-multiline` (block-level markup)

**Reference**: [Data Types Specification](/specification/datatypes/)

---

## 10. Implementation

### Grammar

The [reference implementation](https://github.com/metaschema-framework/metaschema-java) and the Metapath specification uses an ANTLR-based grammar for parsing (`[Metapath10.g4](https://github.com/metaschema-framework/metaschema-java/blob/develop/core/src/main/antlr4/Metapath10.g4)`), derived from but distinct from XPath 3.1's grammar.

**Reference**: [Metapath10.g4](https://github.com/metaschema-framework/metaschema-java/blob/develop/core/src/main/antlr4/Metapath10.g4)

### Available Implementations

- **Java**: [Metaschema Java Tools](https://github.com/metaschema-framework/metaschema-java) - Primary implementation
- **XSLT**: [Metaschema XSLT](https://github.com/usnistgov/metaschema-xslt) - XSLT-based implementation
- **Go**: [GoComply Metaschema](https://github.com/GoComply/metaschema) - Community implementation

---

## Summary Comparison Table

| Aspect | XPath 3.1 | Metapath |
|--------|-----------|----------|
| **Data Model** | XDM (7 node types) | Metaschema (flag, field, assembly) |
| **Format Support** | XML only | XML, JSON, YAML |
| **Primary Purpose** | XML querying/transformation | Metaschema constraint evaluation |
| **Function Library** | 200+ functions | 100+ implemented (core, array, map) + custom extensions |
| **Type System** | XSD types | Metaschema data types |
| **Constraint Integration** | External (Schematron, etc.) | Native |
| **Namespace Nodes** | Supported | Not applicable |
| **Text Nodes** | Supported | Not applicable |
| **Attribute Syntax** | `@attribute` | `@flag` (same syntax) |
| **Specification Status** | W3C Recommendation | Metaschema Framework specification (evolving) |

---

## References

1. [Metapath Expression Language Specification](../metapath/)
2. [XPath 3.1 W3C Recommendation](https://www.w3.org/TR/2017/REC-xpath-31-20170321/)
3. [Metaschema Specification](/specification/)
4. [Metaschema Java Tools](https://github.com/metaschema-framework/metaschema-java)
5. [Information Modeling Specification](/specification/information-modeling/)
6. [Constraints Specification](../constraints/)
7. [Data Types Specification](/specification/datatypes/)
8. [GitHub: metaschema-framework/metaschema](https://github.com/metaschema-framework/metaschema)
9. [GitHub: metaschema-framework/metaschema-java](https://github.com/metaschema-framework/metaschema-java)
10. [XPath and XQuery Data Model 3.1](https://www.w3.org/TR/xpath-datamodel-31/)
11. [Metapath10.g4 Grammar](https://github.com/metaschema-framework/metaschema-java/blob/develop/core/src/main/antlr4/Metapath10.g4)
12. [BuildCSTVisitor.java](https://github.com/metaschema-framework/metaschema-java/blob/develop/core/src/main/java/gov/nist/secauto/metaschema/core/metapath/cst/BuildCSTVisitor.java)
13. [DefaultFunctionLibrary.java](https://github.com/metaschema-framework/metaschema-java/blob/develop/core/src/main/java/gov/nist/secauto/metaschema/core/metapath/function/library/DefaultFunctionLibrary.java)
