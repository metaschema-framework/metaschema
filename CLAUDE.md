# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Project Overview

Metaschema is a format-agnostic modeling framework that generates schemas, code, and documentation from a single model definition. It addresses limitations of format-specific technologies (XML Schema, JSON Schema) by providing a unified modeling abstraction.

The specification website is published at https://framework.metaschema.dev/

## Build Commands

```bash
# Install dependencies
npm install

# Build the website (requires Go for Hugo modules)
npm run build

# Serve website locally with hot reload
npm run serve
```

Hugo is installed via `hugo-bin` npm package. The version is configured in `package.json` under `hugo-bin.version`.

## Git Workflow

**All PRs must target the `develop` branch**, not `main`. The `main` branch receives merges only from `develop` during releases.

### Branch Naming
- `feature/*` - New features and enhancements
- `release/*` - Release preparation (format: `release/MAJOR.MINOR`)
- `fix/*` - Bug fixes

### Versioning
Uses [semantic versioning](https://semver.org/). Version is tracked in:
- `package.json`
- `schema/metaschema/metaschema-module-metaschema.xml`

### Submodules
Clone with submodules: `git clone --recurse-submodules`
Update submodules: `git submodule update --init --recursive`

## Repository Architecture

### Schema Definitions (`schema/`)
- `schema/metaschema/` - The Metaschema module definitions (XML format)
  - `metaschema-module-metaschema.xml` - Core module definition with version
  - `metaschema-module-constraints.xml` - Constraint definitions
- `schema/json/` - JSON Schema for datatypes
  - `metaschema-datatypes.json` - JSON Schema draft-07 datatype definitions
- `schema/xml/` - XML Schema definitions
  - `metaschema.xsd` - Main Metaschema XML Schema
  - `metaschema-datatypes.xsd` - XSD datatype definitions
  - `metaschema-markup-*.xsd` - Markup-related schemas

### Website (`website/`)
Hugo-based static site using Hugo modules (requires Go).
- `website/content/specification/` - Specification documentation
  - `datatypes.md` - Data type documentation (must stay in sync with schema files)
  - `syntax/` - Syntax documentation including constraints, definitions, metapath
- `website/config/` - Hugo configuration

### Test Suite (`test-suite/`)
Contains test fixtures and worked examples. Files in this directory are example data, not production schemas.

## Key Patterns

### Datatype Definitions
When adding or modifying datatypes, update three files in sync:
1. `schema/json/metaschema-datatypes.json` - JSON Schema definition
2. `schema/xml/metaschema-datatypes.xsd` - XML Schema definition
3. `website/content/specification/datatypes.md` - Documentation

Regex patterns must be valid for both ECMA-262 (JSON Schema) and XML Schema dialects. Both support Unicode property escapes (`\p{L}`, `\p{N}`).

### Constraint Types
Constraints are documented in `website/content/specification/syntax/constraints.md`. When adding new constraint types, update:
- The constraint type lists for `define-flag`, `define-field`, and `define-assembly`
- Add a new section with syntax table and examples

## Related Repositories
- [metaschema-java](https://github.com/metaschema-framework/metaschema-java) - Java implementation with parsing, validation, and code generation
