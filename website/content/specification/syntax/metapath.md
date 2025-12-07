---
title: "Metapath Expression Language"
description: "A specification of the Metapath expression language used for selecting and evaluating Metaschema data."
weight: 60
---

# Metapath Expression Language

Metaschema includes support for an expression language called *Metapath*, which allows for selecting and evaluating Metaschema modules and data instances that conform to a Metaschema module. A Metapath can be used to query all Metaschema-supported formats (i.e., JSON, YAML, XML) using a common, Metaschema module-bound syntax. This means a Metapath can be used to query the same data regardless of the underlying format used, as long as that data is bound to a Metaschema module. This provides consistent portability of Metapath expressions against multiple serialization forms for the same data set.

Metapath is a customization of the [XML Path Language (XPath) 3.1](https://www.w3.org/TR/2017/REC-xpath-31-20170321/), which has been adjusted to work with a Metaschema module-based model. This means the underlying [XML Data model](https://www.w3.org/TR/xpath-datamodel-31/) used by XPath, which exposes element and attribute nodes, is replaced with the Metaschema data model, which exposes flag, field, and assembly nodes.

XPath was chosen as a basis for Metapath because it provides for both *selection* of nodes and logical *evaluation* of node values, the latter of which is required for supporting Metaschema module [constraints](/specification/syntax/constraints/). Other path languages (e.g., [JSON Path](https://goessner.net/articles/JsonPath/), [JSON Pointer](https://www.rfc-editor.org/rfc/rfc6901.html)) were not chosen, due to limitations in *evaluation* capabilities and because their syntax was specific to JSON.

<<<<<<< HEAD
## Data Model

Metapath operates on the Metaschema data model, which differs from XPath's XML-based data model.

### Node Types

The Metaschema data model exposes the following node types:

| Node Type | Description | XPath Equivalent |
|-----------|-------------|------------------|
| Document | The root of a document instance | Document node |
| Assembly | A complex container with child content (flags and model items) | Element node |
| Field | A named value with optional flags | Element node |
| Flag | A simple name/value pair | Attribute node |

Unlike XPath's element/attribute distinction, Metaschema uses assembly/field/flag to represent the hierarchical structure of data. This abstraction allows Metapath expressions to work consistently across JSON, YAML, and XML representations of the same data.

### Format-Agnostic Querying

The same Metapath expression queries equivalent data regardless of format. For example, the expression `/catalog/metadata/title` selects the title field from:

{{< tabs JSON YAML XML >}}
{{% tab %}}
```json
{
  "catalog": {
    "metadata": {
      "title": "Example Catalog"
    }
  }
}
```
{{% /tab %}}
{{% tab %}}
```yaml
catalog:
  metadata:
    title: Example Catalog
```
{{% /tab %}}
{{% tab %}}
```xml
<catalog>
  <metadata>
    <title>Example Catalog</title>
  </metadata>
</catalog>
```
{{% /tab %}}
{{< /tabs >}}

This format independence is a key feature of Metapath, enabling validation rules and queries to be defined once and applied to any supported serialization format.

### Node Identity

Each node in a Metaschema document instance has a unique identity. Two nodes are identical if and only if they represent the same node in the document tree.

## Syntax Overview

Metapath expressions follow XPath 3.1 syntax with adaptations for the Metaschema data model.

### Path Expressions

Path expressions select nodes from the document tree.

| Syntax | Description | Example |
|--------|-------------|---------|
| `/` | Absolute path from document root | `/catalog/metadata` |
| `.` | Current context node | `.` |
| `..` | Parent of current node | `../sibling` |
| `name` | Child with specified name | `metadata` |
| `@name` | Flag with specified name | `@id` |
| `*` | All child nodes | `catalog/*` |
| `//` | Descendant-or-self axis | `//control` |

### Predicates

Predicates filter node sequences using conditions enclosed in square brackets.

```text
expression[predicate]
```

Examples:
- `control[@id='ac-1']` - Select control with id flag equal to 'ac-1'
- `param[1]` - Select the first param child
- `part[title]` - Select part children that have a title child

### Operators

#### Comparison Operators

| Operator | Description |
|----------|-------------|
| `=` | Equal |
| `!=` | Not equal |
| `<` | Less than |
| `>` | Greater than |
| `<=` | Less than or equal |
| `>=` | Greater than or equal |
| `eq` | Value equality |
| `ne` | Value inequality |
| `lt` | Value less than |
| `gt` | Value greater than |
| `le` | Value less than or equal |
| `ge` | Value greater than or equal |

#### Logical Operators

| Operator | Description |
|----------|-------------|
| `and` | Logical AND |
| `or` | Logical OR |

The `not()` function provides logical negation.

#### Arithmetic Operators

| Operator | Description |
|----------|-------------|
| `+` | Addition |
| `-` | Subtraction |
| `*` | Multiplication |
| `div` | Division |
| `idiv` | Integer division |
| `mod` | Modulus (remainder) |

### Sequence Operators

| Operator | Description |
|----------|-------------|
| `,` | Sequence concatenation |
| `\|` | Sequence union |

### Conditional Expressions

Metapath supports conditional expressions:

```text
if (condition) then expression1 else expression2
```

Example:
```text
if (count(./memory) > 0) then sum(./memory/byte-size) else 0
```

### Variable References

Variables are referenced using the `$` prefix:

```text
$variablename
```

Variables are bound using [`<let>`](/specification/syntax/constraints/#let) declarations in constraints.

## Evaluation Context

When a Metapath expression is evaluated, it operates within an *evaluation context* that determines how the expression is interpreted.

### Focus Node

The *focus node* (also called *context node* or *evaluation focus*) is the node against which the expression is evaluated. Path expressions are resolved relative to this node.

- The context item `.` refers to the current focus node
- Relative paths navigate from the focus node
- The parent axis `..` navigates to the focus node's parent

### Dynamic Context

The dynamic context includes:

- **Context item**: The current focus node
- **Context position**: The position of the context item within a sequence (1-based)
- **Context size**: The total number of items in the current sequence
- **Variable bindings**: Values bound to variables via `<let>` declarations
- **Current date and time**: The current timestamp for date/time functions

### Evaluation in Constraints

When Metapath expressions are used in [constraints](/specification/syntax/constraints/), the evaluation context varies by attribute:

| Attribute | Focus Node |
|-----------|-----------|
| `@target` | The content node associated with the definition where the constraint is declared |
| `@test` | Each node selected by the `@target` expression, evaluated in sequence |
| `@expression` (in `<let>`) | The current node in the constraint evaluation context |
| `<message>` templates | The failing target node |

For detailed constraint evaluation semantics, see [Constraint Processing](/specification/syntax/constraints/#constraint-processing).

## Built-in Functions

Metapath provides a library of built-in functions. Functions may have zero, one, or multiple arguments. Functions are called using the syntax:

```text
function-name()
function-name(argument)
function-name(argument1, argument2, ...)
```

### Node Functions

| Function | Description |
|----------|-------------|
| `count(sequence)` | Returns the number of items in a sequence |
| `exists(sequence)` | Returns `true` if the sequence is non-empty |
| `empty(sequence)` | Returns `true` if the sequence is empty |
| `head(sequence)` | Returns the first item in a sequence |
| `tail(sequence)` | Returns all items except the first |

### String Functions

| Function | Description |
|----------|-------------|
| `string(item)` | Converts an item to a string |
| `concat(string, string, ...)` | Concatenates strings |
| `starts-with(string, prefix)` | Tests if string starts with prefix |
| `ends-with(string, suffix)` | Tests if string ends with suffix |
| `contains(string, substring)` | Tests if string contains substring |
| `substring(string, start, length?)` | Extracts a substring |
| `string-length(string)` | Returns the length of a string |
| `normalize-space(string)` | Normalizes whitespace |
| `upper-case(string)` | Converts to uppercase |
| `lower-case(string)` | Converts to lowercase |

### Numeric Functions

| Function | Description |
|----------|-------------|
| `sum(sequence)` | Returns the sum of numeric values |
| `avg(sequence)` | Returns the average of numeric values |
| `min(sequence)` | Returns the minimum value |
| `max(sequence)` | Returns the maximum value |
| `round(number)` | Rounds to the nearest integer |
| `floor(number)` | Rounds down to an integer |
| `ceiling(number)` | Rounds up to an integer |
| `abs(number)` | Returns the absolute value |

### Boolean Functions

| Function | Description |
|----------|-------------|
| `true()` | Returns boolean `true` |
| `false()` | Returns boolean `false` |
| `not(boolean)` | Negates a boolean value |
| `boolean(item)` | Converts an item to a boolean |

## Differences from XPath 3.1

While Metapath is based on XPath 3.1, there are notable differences:

### Data Model Differences

1. **Node types**: Metapath uses assembly/field/flag instead of element/attribute
2. **Format independence**: Expressions work across JSON, YAML, and XML representations
3. **Module binding**: Expressions are interpreted in the context of a Metaschema module

### Unsupported Features

The following XPath 3.1 features are not currently supported in Metapath:

- Namespace axis and namespace nodes
- Processing instruction nodes
- Comment nodes
- Some advanced XPath functions (consult implementation documentation)

### Extended Features

Metapath extends XPath with:

- Format-agnostic path expressions
- Integration with Metaschema constraints
- Template expressions in constraint messages using `{expression}` syntax

## Use in Constraints

Metapath expressions are primarily used in [Metaschema constraints](/specification/syntax/constraints/) for:

| Purpose | Attribute/Element | Example |
|---------|------------------|---------|
| Target selection | `@target` | `target="./child-field"` |
| Condition testing | `@test` | `test="count(.) > 0"` |
| Variable binding | `@expression` | `expression="ancestor::assembly/@id"` |
| [Message templates](/specification/syntax/constraints/#constraint-messages) | `<message>` | `Value {.} is invalid` |

For comprehensive constraint documentation, see [Constraints](/specification/syntax/constraints/).

For detailed information about how Metapath differs from XPath 3.1, including supported features, function library, and data model differences, see [Metapath vs. XPath 3.1: Key Differences](../metapath-overview/).
