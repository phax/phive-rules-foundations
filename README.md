# phive-rules-foundations

A set of foundational, preconfigured validation rules for PHIVE (Philip Helger Integrative Validation Engine) - pronounced `[ˈfaɪv]`.

This project holds the **foundational document formats** - the pure structural (XSD only, no Schematron) validation rules that other rule sets build upon - together with the shared registration SPI and helper classes used across the whole phive-rules ecosystem. It was extracted from [phive-rules](https://github.com/phax/phive-rules) in 2026 so that these rarely-changing foundations can be versioned and released independently. It is versioned separately, starting at `5.0.0`.

This project is part of my Peppol solution stack. See https://github.com/phax/peppol for other components and libraries in that area.

All projects found in here rely on the PHIVE validation engine provided by https://github.com/phax/phive

This project is divided into the following sub-projects:
* phive-rules-foundation-api - Shared API: the validation rules registration SPI (`IValidationRulesRegistrarSPI`), the `ValidationRulesRegistrar`, and the core helper classes (`PhiveRulesHelper`, `PhiveRulesTestHelper`, `PhiveRulesInitializationException`) used by all rule modules
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
  <artifactId>phive-rules-ubl</artifactId>
  <version>x.y.z</version>
</dependency>
```

# News and noteworthy

v5.0.0 - work in progress
* Initial release after extraction from [phive-rules](https://github.com/phax/phive-rules) v4.5.0
* Contains the foundational XSD-only document format modules (`phive-rules-cii`, `phive-rules-ubl`, `phive-rules-ebinterface`, `phive-rules-facturae`, `phive-rules-fatturapa`, `phive-rules-finvoice`, `phive-rules-ksef`, `phive-rules-osa`, `phive-rules-teapps`) and the new `phive-rules-foundation-api` (package `com.helger.phive.rules.foundation`) holding the shared registration SPI and helpers

---

My personal [Coding Styleguide](https://github.com/phax/meta/blob/master/CodingStyleguide.md) |
It is appreciated if you star the GitHub project if you like it.
