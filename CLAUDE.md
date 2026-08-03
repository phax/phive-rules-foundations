# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Project Overview

**phive-rules-foundations** is a Maven multi-module project providing the *foundational* validation rules for [PHIVE](https://github.com/phax/phive) (Philip Helger Integrative Validation Engine): the pure structural (XSD only, **no Schematron**) document formats that other rule sets build upon, plus the shared registration SPI and helper classes used across the whole phive-rules ecosystem.

It was extracted from [phive-rules](https://github.com/phax/phive-rules) in 2026 so these rarely-changing foundations can be versioned and released independently. It is versioned separately, **starting at `5.0.0`**, while `phive-rules` continues on its own `4.x` line and depends on the artifacts built here.

Part of the Peppol solution stack: https://github.com/phax/peppol

The repository contains 10 sub-modules: `phive-rules-foundation-api` (the shared base) and 9 XSD-only format modules.

## Build Commands

```bash
mvn clean install                              # Full build (all modules)
mvn clean install -pl phive-rules-ubl          # Single module
mvn test -pl phive-rules-cii                   # Tests for one module
mvn test -pl phive-rules-ubl -Dtest=UBLValidationTest   # Single test class
```

CI targets Java 17 (built/tested on 17, 21, 25), matching `phive-rules`.

### Relationship to `phive-rules`

- The Maven coordinates (`com.helger.phive.rules:phive-rules-<format>`) and the VES/DVR coordinates of the moved modules are **unchanged** from their previous home in `phive-rules` — only the git repository and the artifact version line differ.
- `phive-rules` (the sibling repo) imports this project's parent POM as a BOM and depends on `phive-rules-foundation-api`, so **this project must be installed/released before `phive-rules` builds**. Locally: run `mvn install` here first.
- Do not introduce a dependency from this project onto `phive-rules` — the dependency direction is strictly `phive-rules` → `phive-rules-foundations`. Keeping foundations self-contained is the whole point of the split.

## Architecture

### Module Structure

Every format module follows the same pattern:

```
phive-rules-{format}/
├── src/main/java/.../
│   ├── {Format}Validation.java              # Registers validation rule sets (init… methods), XSD only
│   └── {Format}ValidationSPI.java           # SPI impl (IValidationRulesRegistrarSPI)
├── src/main/resources/
│   └── META-INF/services/com.helger.phive.rules.foundation.IValidationRulesRegistrarSPI
├── src/test/java/.../
│   ├── {Format}ValidationTest.java
│   ├── ValidationExecutionManagerFuncTest.java
│   ├── SPITest.java
│   └── mock/CTestFiles.java                  # Test file loading utility
└── src/test/resources/external/test-files/   # Sample XML documents
```

The XSD schemas themselves come from the underlying binding libraries (`ph-ubl`, `ph-cii`, `ph-ebinterface`, `ph-fatturapa`, …) via their marshallers — these modules register those schemas, they do not ship their own copies.

### Core Module: `phive-rules-foundation-api`

Package `com.helger.phive.rules.foundation`. Provides the shared base used by all rule modules (in this repo **and** in `phive-rules`):

- `IValidationRulesRegistrarSPI` — the SPI every rule module implements. `ValidationRulesRegistrar.registerAllValidationRules (registry)` discovers all implementations from the classpath via `ServiceLoader` and registers them, resolving cross-module `getAllPrerequisites()` ordering automatically (deferring/retrying until prerequisites are present).
- `ValidationRulesRegistrar` — the discovery/registration engine described above.
- `PhiveRulesHelper` — creates `DVRCoordinate` instances with version parsing (`createCoordinate`), wraps XSLT/Schematron executors (`createXSLT`), and `requireVESID (registry, coord)` looks up a prerequisite VES and throws `PhiveRulesInitializationException` if it is not yet registered.
- `PhiveRulesInitializationException` — thrown when a prerequisite VES is missing.
- `PhiveRulesTestHelper` — test utilities (`isContentCorrect`) used by every module's tests.

Dependencies of `foundation-api` are deliberately minimal: `slf4j-api` + `phive-xml` (which transitively brings ph-commons, ph-diver and ph-schematron). It has **no** dependency on any document-binding library — the format-specific UBL/CII helpers (`PhiveRulesUBLHelper`, `PhiveRulesCIIHelper`) intentionally live in `phive-rules-api`, not here.

### Validation Registration Pattern

Each `{Format}Validation.java` class:
1. Defines `GROUP_ID` and version constants.
2. Creates `DVRCoordinate` constants (e.g., `VID_UBL_21_INVOICE`) via `PhiveRulesHelper.createCoordinate (…)`.
3. Provides `init…(IValidationExecutorSetRegistry<IValidationSourceXML>)` that registers each VES via the phive builder — **XSD only, never `.addSchematron`**:
   ```java
   VesXmlBuilder.builder ()
                .vesID (VID_…)
                .displayNamePrefix ("…")
                .addXSD (…)            // from the format's marshaller, e.g. UBL21Marshaller
                .registerInto (aRegistry);
   ```
   Mark superseded VES with `.deprecated ()`.

### SPI Auto-Registration

Every module ships a `{Format}ValidationSPI` implementing `com.helger.phive.rules.foundation.IValidationRulesRegistrarSPI` (annotated `@IsSPIImplementation`, listed in `src/main/resources/META-INF/services/com.helger.phive.rules.foundation.IValidationRulesRegistrarSPI`). Its `registerValidationRules` delegates to the module's `init…` method(s). These foundation formats have no cross-module prerequisites, so they do not override `getAllPrerequisites()`.

There is no aggregator module here; the `phive-rules-all` / `phive-rules-all-legacy` aggregators live in the `phive-rules` repo and depend on these artifacts.

When adding a module, wire all of: the `init…` method, the `{Format}ValidationSPI` + its `META-INF/services` file, the `<module>` entry and the `dependencyManagement` BOM entry in the parent POM. Also add it to the `phive-rules` repo's `phive-rules-all` aggregator (pom dependency + a call in `initPhiveRules`).

### Naming new VES Coordinates

Follow the **DVR Coordinate naming conventions** in `../ph-diver/README.md` (section "Naming Best Practices for Group ID and Artefact ID"): reverse-DNS lowercase Group IDs rooted on the owner; lowercase kebab-case Artefact IDs describing the artefact with no version embedded; keep IDs stable across releases so pseudo-versions (`latest`, `latest-release`) keep working.

### Key Dependencies

| Dependency | Purpose |
|---|---|
| `phive-parent-pom` (12.1.0) | PHIVE validation engine |
| `ph-schematron` (10.0.0) | Schematron processing (transitive; not used for rule registration here) |
| `ph-ubl` (10.2.0) | OASIS UBL bindings (`phive-rules-ubl`) |
| `ph-cii` (4.1.2) | UN/CEFACT CII bindings (`phive-rules-cii`) |
| `ph-ebinterface` (8.1.0) | ebInterface bindings (`phive-rules-ebinterface`) |
| `ph-fatturapa` (3.1.0) | FatturaPA bindings (`phive-rules-fatturapa`) |
| `ph-commons` (12.3.3) | Core utilities (collections, IO, etc.) |
| `ph-diver` (4.2.1) | DVR coordinates / VESID |

### Modules

- `phive-rules-foundation-api` — shared base for all modules
- `phive-rules-cii` — pure UN/CEFACT CII
- `phive-rules-ubl` — pure OASIS UBL
- `phive-rules-ebinterface` — Austrian ebInterface
- `phive-rules-facturae` — Spanish Facturae
- `phive-rules-fatturapa` — Italian FatturaPA
- `phive-rules-finvoice` — Finnish Finvoice
- `phive-rules-ksef` — Polish KSeF
- `phive-rules-osa` — Hungarian NAV Online Számla (OSA)
- `phive-rules-teapps` — Finnish Tieto TEAPPSXML

## Imports & Annotations

This codebase uses **ph-commons 12.x** (restructured packages) and **JSpecify** nullness annotations. When writing or editing Java:
- Nullness: `org.jspecify.annotations.{NonNull,Nullable}` — never `javax.annotation.*` or `jakarta.annotation.*`.
- Core utilities live under `com.helger.base.*` and `com.helger.annotation.*` (e.g. `com.helger.annotation.Nonempty`, `com.helger.annotation.concurrent.Immutable`, `com.helger.base.enforce.ValueEnforcer`) — not the old monolithic `com.helger.commons.*`.
- The shared SPI/helpers are in `com.helger.phive.rules.foundation` (this repo). Their former locations in `com.helger.phive.rules.api` still exist in `phive-rules` as `@Deprecated` delegates — do not use those.
- Match the imports of a neighbouring `{Format}Validation.java` when in doubt.

## Testing

- **Framework:** JUnit 4
- **Test logging:** SLF4J Simple (`simplelogger.properties` in test resources)
- Each module has `SPITest` (validates the `META-INF/services` registration via `SPITestHelper`), format-specific validation tests, and functional tests running against real XML documents under `src/test/resources/external/test-files/`.

## Packaging

All format modules produce plain JARs (`<packaging>jar</packaging>`).
