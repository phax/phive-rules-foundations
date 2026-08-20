# phive-rules-foundations

<!-- ph-badge-start -->
[![Sonatype Central](https://maven-badges.sml.io/sonatype-central/com.helger.phive.rules/phive-rules-foundations-parent-pom/badge.svg)](https://maven-badges.sml.io/sonatype-central/com.helger.phive.rules/phive-rules-foundations-parent-pom/)
[![javadoc](https://javadoc.io/badge2/com.helger.phive.rules/phive-rules-cii/javadoc.svg)](https://javadoc.io/doc/com.helger.phive.rules/phive-rules-cii)

> If this project saved you some time or made your day a little easier, a star would mean a lot — it helps others find it too.
<!-- ph-badge-end -->

A set of foundational, preconfigured validation rules for PHIVE (Philip Helger Integrative Validation Engine) - pronounced `[ˈfaɪv]`.

This project holds the **foundational document formats** - the pure structural (XSD only, no Schematron) validation rules that other rule sets build upon. It builds on the shared registration SPI and helper classes provided by the separate [phive-rules-shared](https://github.com/phax/phive-rules-shared) project. It was extracted from [phive-rules](https://github.com/phax/phive-rules) in 2026 so that these rarely-changing foundations can be versioned and released independently. It is versioned separately, starting at `5.0.0`.

This project is part of my Peppol solution stack. See https://github.com/phax/peppol for other components and libraries in that area.

All projects found in here rely on the PHIVE validation engine provided by https://github.com/phax/phive

The shared API used by all rule modules - the validation rules registration SPI (`IValidationRulesRegistrarSPI`), the `ValidationRulesRegistrar`, and the core helper classes (`DVRHelper`, `PhiveRulesTestHelper`, `PhiveRulesInitializationException`) - now lives in the separate [phive-rules-shared](https://github.com/phax/phive-rules-shared) project (Maven artifact `com.helger.phive.rules:phive-rules-shared`).

This project is divided into the following sub-projects:
* phive-rules-cii - Validation rules for pure UN/CEFACT CII (without any Schematron)
* phive-rules-ebinterface - Validation rules for Austrian ebInterface
* phive-rules-facturae - Validation rules for the Spanish Facturae
* phive-rules-fatturapa - Validation rules for Italian fattura PA
* phive-rules-finvoice - Validation rules for Finnish Finvoice
* phive-rules-ksef - Validation rules for Polish KSeF
* phive-rules-osa - Validation rules for Hungarian NAV Online Számla (OSA) v2.0 and v3.0
* phive-rules-teapps - Validation rules for Finnish Tieto TEAPPSXML
* phive-rules-ubl - Validation rules for pure OASIS UBL (without any Schematron)

The Maven coordinates (`com.helger.phive.rules:phive-rules-<format>`) and the VES coordinates of the moved modules are unchanged compared to their previous home in `phive-rules`.

The Java code in this project is licensed under the Apache 2 license.
The code of the validation artefacts used may use a different license.

# Maven usage

Add the following to your `pom.xml` to use this artifact, replacing `x.y.z` with the latest version:

```xml
<dependency>
  <groupId>com.helger.phive.rules</groupId>
  <artifactId>phive-rules-cii</artifactId>
  <version>x.y.z</version>
</dependency>

<dependency>
  <groupId>com.helger.phive.rules</groupId>
  <artifactId>phive-rules-ebinterface</artifactId>
  <version>x.y.z</version>
</dependency>

<dependency>
  <groupId>com.helger.phive.rules</groupId>
  <artifactId>phive-rules-facturae</artifactId>
  <version>x.y.z</version>
</dependency>

<dependency>
  <groupId>com.helger.phive.rules</groupId>
  <artifactId>phive-rules-fatturapa</artifactId>
  <version>x.y.z</version>
</dependency>

<dependency>
  <groupId>com.helger.phive.rules</groupId>
  <artifactId>phive-rules-finvoice</artifactId>
  <version>x.y.z</version>
</dependency>

<dependency>
  <groupId>com.helger.phive.rules</groupId>
  <artifactId>phive-rules-ksef</artifactId>
  <version>x.y.z</version>
</dependency>

<dependency>
  <groupId>com.helger.phive.rules</groupId>
  <artifactId>phive-rules-osa</artifactId>
  <version>x.y.z</version>
</dependency>

<dependency>
  <groupId>com.helger.phive.rules</groupId>
  <artifactId>phive-rules-teapps</artifactId>
  <version>x.y.z</version>
</dependency>

<dependency>
  <groupId>com.helger.phive.rules</groupId>
  <artifactId>phive-rules-ubl</artifactId>
  <version>x.y.z</version>
</dependency>
```

# News and noteworthy

v5.0.1 - 2026-08-03
* Moved the shared registration SPI and helper classes out into the separate [phive-rules-shared](https://github.com/phax/phive-rules-shared) project (Maven artifact `com.helger.phive.rules:phive-rules-shared`, package `com.helger.phive.rules.shared`).
  The `phive-rules-foundation-api` module was removed; all format modules now depend on `phive-rules-shared`

v5.0.0 - 2026-08-03
* Initial release after extraction from [phive-rules](https://github.com/phax/phive-rules) v4.5.0
* Contains the foundational XSD-only document format modules (`phive-rules-cii`, `phive-rules-ubl`, `phive-rules-ebinterface`, `phive-rules-facturae`, `phive-rules-fatturapa`, `phive-rules-finvoice`, `phive-rules-ksef`, `phive-rules-osa`, `phive-rules-teapps`) and the new `phive-rules-foundation-api` (package `com.helger.phive.rules.foundation`) holding the shared registration SPI and helpers

---

My personal [Coding Styleguide](https://github.com/phax/meta/blob/master/CodingStyleguide.md) |
It is appreciated if you star the GitHub project if you like it.
