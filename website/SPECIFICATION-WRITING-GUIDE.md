# Specification Writing Guide

This guide provides patterns and best practices for contributors writing or updating the Metaschema specification documentation.

## Document Structure

### Quick Reference vs. Detailed Documentation

The specification uses a two-tier documentation approach:

1. **Quick Reference (Syntax Overview)**: The [`_index.md`](/specification/syntax/) file provides a concise syntax overview showing only the recommended, non-deprecated values and options.

2. **Detailed Documentation**: Individual specification pages (e.g., `instances.md`, `definitions.md`) provide comprehensive documentation including deprecated values with clear deprecation notices.

**Example**: For the `@in-xml` attribute:
- The syntax overview shows only `WRAPPED|UNWRAPPED`
- The detailed documentation in `instances.md` includes `WITH_WRAPPER` with a clear deprecation notice explaining it is equivalent to `WRAPPED`

This pattern ensures:
- New users see only current, recommended options in the quick reference
- Existing implementations using deprecated values can find documentation
- The deprecation path is clearly communicated

## Handling Deprecated Features

When documenting deprecated features:

1. **In attribute/element tables**: List deprecated values last with "(deprecated)" notation
   ```markdown
   | `WRAPPED`, `UNWRAPPED`, or `WITH_WRAPPER` (deprecated) | optional | `WRAPPED` |
   ```

2. **In detailed sections**: Use a table row with bold "Deprecated" label
   ```markdown
   | `WITH_WRAPPER` | **Deprecated.** This value is equivalent to `WRAPPED` and SHOULD NOT be used. Use `WRAPPED` instead. |
   ```

3. **Add a callout**: Explain the deprecation context and migration path
   ```markdown
   {{<callout>}}
   The `WITH_WRAPPER` value is deprecated and is retained only for backward compatibility...
   {{</callout>}}
   ```

4. **Omit from quick reference**: Do not include deprecated values in the syntax overview (`_index.md`)

## RFC 2119 Keywords

Use [RFC 2119](https://www.rfc-editor.org/rfc/rfc2119) keywords consistently:

- **MUST** / **MUST NOT**: Absolute requirements or prohibitions
- **SHOULD** / **SHOULD NOT**: Strong recommendations (may be ignored with good reason)
- **MAY**: Optional behavior

Format these keywords in uppercase to distinguish them from normal prose.

**Example**:
> When the `@in-xml` attribute is not provided, the value MUST default to `WRAPPED`.

## Cross-References

### Internal Links

Use relative links to reference other specification sections:

```markdown
See [Definition Name Resolution](/specification/syntax/module/#definition-name-resolution)
```

### Anchors

When creating new sections that may be referenced:
- Use descriptive, lowercase, hyphenated anchor names
- Ensure anchors are stable (avoid renaming without updating all references)

## Tables

### Attribute Tables

Use consistent column headers:
```markdown
| Attribute | Data Type | Use      | Default Value |
|:---       |:---       |:---      |:---           |
```

### Element Tables

Use consistent column headers:
```markdown
| Element | Data Type | Use      |
|:---       |:---       |:---      |
```

### Behavior Tables

For documenting enumerated values and their behaviors:
```markdown
| Value | Behavior |
|:--- |:--- |
```

## Callouts

Use Hugo callout shortcodes for supplementary information:

```markdown
{{<callout>}}
Additional context or best practices that are helpful but not normative.
{{</callout>}}
```

Reserve callouts for:
- Best practices and recommendations
- Clarifying context
- Implementation notes
- Deprecation notices

## Code Examples

### Metaschema XML Examples

Use fenced code blocks with `xml` language and line numbers when referencing specific lines:

````markdown
```xml {linenos=table,hl_lines=[4]}
<define-flag name="id"/>
<define-assembly name="computer">
  <root-name>computer</root-name>
  <flag ref="id" required="yes"/>
</define-assembly>
```
````

### Multi-Format Examples

When showing equivalent content in JSON, YAML, and XML, use the tabs shortcode:

````markdown
{{< tabs JSON YAML XML >}}
{{% tab %}}
```json
{ "example": "value" }
```
{{% /tab %}}
{{% tab %}}
```yaml
example: "value"
```
{{% /tab %}}
{{% tab %}}
```xml
<example>value</example>
```
{{% /tab %}}
{{< /tabs >}}
````

## TODO Markers

When sections require future work, use a consistent TODO format with priority:

```markdown
TODO: P2: describe this with examples
```

Priority levels:
- **P1**: High priority, blocks other work
- **P2**: Medium priority, should be addressed soon
- **P3**: Low priority, nice to have

## Keeping Documentation in Sync

### Schema Files

When documenting data types or schema elements, ensure consistency across:
- `schema/json/metaschema-datatypes.json` (JSON Schema definitions)
- `schema/xml/metaschema-datatypes.xsd` (XML Schema definitions)
- `website/content/specification/datatypes.md` (documentation)

### Patterns and Descriptions

When a data type has a validation pattern:
- Document the pattern in both schema files
- Explain the pattern's meaning in the specification
- Ensure patterns are valid for their respective schema languages (ECMA-262 for JSON, XML Schema regex for XSD)
