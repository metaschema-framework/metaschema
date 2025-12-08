# PRD: Metapath Expression Documentation Improvement

| Field | Value |
|-------|-------|
| **PRD ID** | 20251207-metapath-documentation |
| **Status** | Draft |
| **Author** | David Waltermire |
| **Created** | 2025-12-07 |
| **Last Updated** | 2025-12-07 |

## Overview

### Problem Statement

The Metaschema specification documentation for Metapath expressions is incomplete and inconsistent:

1. **Incomplete metapath.md**: The main Metapath documentation page is only 15 lines and explicitly states it is incomplete
2. **Undefined "special" data type**: The word "special" is used in attribute tables without definition
3. **Inconsistent linking**: Some Metapath-taking attributes link to metapath.md, others don't
4. **Scattered evaluation context**: Evaluation context is explained only in constraints.md, not centralized

### Goals

1. Expand `metapath.md` to provide comprehensive Metapath expression documentation
2. Standardize the "special" data type notation across all specification pages
3. Add a dedicated Metapath expressions section to constraints.md
4. Add a glossary entry for Metapath
5. Ensure all Metapath-taking attributes consistently link to metapath.md

### Non-Goals

1. Schema modifications (this is documentation-only)
2. Changes to the metaschema-java implementation
3. Full XPath 3.1 compatibility documentation (will note differences but not exhaustive)
4. Tutorial content (specification focus only)

### Success Metrics

| Metric | Current | Target |
|--------|---------|--------|
| metapath.md line count | 15 | 150+ |
| Undefined "special" usages | 12+ | 0 |
| Metapath attributes with links | ~50% | 100% |
| Glossary coverage | Missing | Complete |

## Background

### Current State Analysis

**metapath.md** (15 lines):
- Brief overview stating Metapath is based on XPath 3.1
- Notes that not all XPath features are supported
- Explicit "will be documented in an updated version" statement
- No syntax reference, functions, or evaluation context

**constraints.md** - "special" usage inconsistencies:

| Attribute | Current Table Notation | Links to Metapath? |
|-----------|----------------------|-------------------|
| `@target` | special | In prose only |
| `@test` | special | No |
| `@expression` | `[special](/specification/syntax/metapath)` | Yes (best pattern) |
| `<prop>` | special | N/A (not Metapath) |
| `<remarks>` | special | N/A (not Metapath) |
| `<enum>` | special | N/A (not Metapath) |
| `<message>` | special | No |

**Evaluation context documentation**:
- Explained in "Constraint Processing" section (lines 520-557)
- Scattered references throughout constraint type sections
- Not present in metapath.md itself

## Requirements

### Functional Requirements

#### FR-1: Expand metapath.md

The Metapath documentation page MUST include:

1. **Syntax Overview**
   - Path expressions (absolute `/`, relative `.`, `..`)
   - Child axis navigation
   - Operators (comparison, logical, arithmetic)
   - Predicates with `[...]` syntax
   - Variable references (`$variablename`)

2. **Evaluation Context**
   - Focus node concept
   - Context item (`.`)
   - Parent navigation (`..`)

3. **Data Model**
   - Assembly nodes
   - Field nodes
   - Flag nodes
   - Contrast with XPath element/attribute model

4. **Built-in Functions**
   - Node functions: `count()`, `exists()`, `empty()`
   - String functions: `string()`, `concat()`, `starts-with()`, `contains()`
   - Numeric functions: `sum()`, `round()`, `floor()`, `ceiling()`
   - Boolean functions: `true()`, `false()`, `not()`

5. **Differences from XPath 3.1**
   - Data model differences
   - Unsupported features notation

6. **Use in Constraints**
   - Reference table linking to constraints.md

#### FR-2: Standardize Data Type Notation

Replace "special" with specific notations:

| Data Category | New Notation |
|--------------|--------------|
| Metapath expressions | `[metapath](/specification/syntax/metapath)` |
| Complex structures | `(structured)` |
| Template strings | `[template](#section-link)` |

#### FR-3: Add Metapath Section to constraints.md

Insert new section covering:
- Metapath-accepting attributes table
- Evaluation focus summary table
- Link to detailed constraint processing

#### FR-4: Add Glossary Entry

Add Metapath definition to glossary.md following existing format.

### Non-Functional Requirements

#### NFR-1: Documentation Style

- Use RFC 2119 keywords (MUST, SHOULD, MAY) consistently
- Follow patterns in `website/SPECIFICATION-WRITING-GUIDE.md`
- Maintain existing documentation voice and structure

#### NFR-2: Cross-References

- All Metapath-taking attributes MUST link to metapath.md
- metapath.md MUST link to constraints.md for constraint-specific details
- Glossary entry MUST link to metapath.md

## Implementation Phases

### Phase 1: Expand metapath.md

**Scope**: Transform 15-line stub into comprehensive documentation

**Files Modified**:
- `website/content/specification/syntax/metapath.md`

### Phase 2: Standardize constraints.md Tables

**Scope**: Update all attribute tables to use consistent notation

**Files Modified**:
- `website/content/specification/syntax/constraints.md`

**Tables to Update**:
- Common Constraint Data
- allowed-values
- expect
- has-cardinality
- index
- key-field
- matches

### Phase 3: Add Metapath Section to constraints.md

**Scope**: Insert new "Metapath Expressions in Constraints" section

**Files Modified**:
- `website/content/specification/syntax/constraints.md`

### Phase 4: Update definitions.md

**Scope**: Standardize "special" notation for consistency

**Files Modified**:
- `website/content/specification/syntax/definitions.md`

### Phase 5: Add Glossary Entry

**Scope**: Add Metapath term definition

**Files Modified**:
- `website/content/specification/glossary.md`

## Testing Strategy

### Documentation Verification

1. **Build verification**: Hugo build must succeed without errors
2. **Link verification**: All internal links must resolve
3. **Rendering verification**: Review rendered pages for formatting issues

### Content Review

1. Technical accuracy review by domain expert
2. Consistency check against metaschema-java implementation
3. Cross-reference verification between pages

## Risks and Mitigations

| Risk | Impact | Likelihood | Mitigation |
|------|--------|------------|------------|
| Function documentation may not match metaschema-java | Medium | Medium | Reference DefaultFunctionLibrary.java for accuracy |
| Breaking existing links | High | Low | Search for existing links before changing anchors |
| Inconsistent RFC 2119 usage | Low | Medium | Review against SPECIFICATION-WRITING-GUIDE.md |

## Open Questions

1. Should we document ALL Metapath functions from metaschema-java, or a curated subset?
2. What level of XPath 3.1 compatibility details should be included?
3. Should examples use real-world OSCAL patterns or simplified examples?

## Related Documents

- Metaschema Specification: `website/content/specification/`
- [XPath 3.1 Specification](https://www.w3.org/TR/xpath-31/)
- [metaschema-java Function Library](https://github.com/metaschema-framework/metaschema-java/blob/main/core/src/main/java/gov/nist/secauto/metaschema/core/metapath/function/library/DefaultFunctionLibrary.java)
- [metaschema-java Expression Parser (CST Visitor)](https://github.com/metaschema-framework/metaschema-java/blob/develop/core/src/main/java/gov/nist/secauto/metaschema/core/metapath/cst/BuildCSTVisitor.java)
