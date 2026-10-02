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
* phive-rules-brazil - Validation rules for the Brazilian DF-e documents (NF-e/NFC-e, CT-e, CT-e OS, GTV-e, CT-e Simplificado, MDF-e, NFCom, NF3e, BP-e, NFAg, NFGas) and the NFS-e Padrão Nacional (since v5.0.6)
* phive-rules-cii - Validation rules for pure UN/CEFACT CII (without any Schematron)
* phive-rules-crs - Validation rules for the OECD Common Reporting Standard (CRS) XML Schema V2.0 and V3.0 (since v5.0.4)
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
  <artifactId>phive-rules-brazil</artifactId>
  <version>x.y.z</version>
</dependency>

<dependency>
  <groupId>com.helger.phive.rules</groupId>
  <artifactId>phive-rules-cii</artifactId>
  <version>x.y.z</version>
</dependency>

<dependency>
  <groupId>com.helger.phive.rules</groupId>
  <artifactId>phive-rules-crs</artifactId>
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

v5.0.6 - work in progress
* Added the new module `phive-rules-brazil` with the XML Schemas of the Brazilian electronic fiscal documents, Group ID `br.gov.nfe`.
  Only the current schema package of each document type is contained:
  * NF-e/NFC-e (model 55/65) "Pacote de Liberação 010f v1.04" - `nfe` and `nfe-proc` with version `4.0.0-pl010f_v1_04`
  * CT-e (model 57), CT-e OS (model 67), GTV-e (model 64) and CT-e Simplificado, NT 2026.002 RTC v1.01 corr 2 - `cte`, `cte-os`, `gtve`, `cte-simp` and the respective `-proc` variants with version `4.0.0-nt2026_002_v1_01_corr2`
  * MDF-e (model 58), NT 2025.001 v1.04 - `mdfe` and `mdfe-proc` with version `3.0.0-nt2025_001_v1_04`
  * NFCom (model 62), NF3e (model 66), BP-e (model 63) incl. BP-e TM and BP-e TA, NFAg (model 75) and NFGas (model 76), NT 2026.002 RTC v1.01 - `nfcom`, `nf3e`, `bpe`, `bpe-tm`, `bpe-ta`, `nfag`, `nfgas` and the respective `-proc` variants (none for BP-e TA) with version `1.0.0-nt2026_002_v1_01`
  * NFS-e Padrão Nacional layout 1.01, schema package of 2026-07-27 - `nfse-dps` and `nfse` with version `1.1.0-v20260727`
  The DVR version consists of the layout version and a classifier for the schema package, as the layout version is not changed when a new schema package is published.
  For each document there is one VES for the document as sent by the issuer (e.g. `NFe`) and one for the authorized document including the protocol (e.g. `nfeProc`).
  No Schematron rules exist - the business rules are only published as tables in the official manuals and are checked by the tax authority

v5.0.5 - 2026-09-30
* Updated the TEAPPSXML 3.0 XML Schema in place to the current upstream version "TEAPPSXML v.3.0 - 26.3.2018, updated 30.9.2019 ROUNDINGS -pattern" - the VES coordinate `com.tieto:teappsxml:3.0` is unchanged.
  See [issue #1](https://github.com/phax/phive-rules-foundations/issues/1).
  The pattern of the `ROUNDINGS` amount type now allows up to 15 integer digits instead of only 1
* Added the fatturaPA 1.2.3 XML Schema (valid from 2025-04-01), VES coordinate `it.fatturapa:invoice:1.2.3`, and deprecated `it.fatturapa:invoice:1.2.2`.
  See [issue #2](https://github.com/phax/phive-rules-foundations/issues/2).
  Compared to 1.2.2 it adds the document type `TD29` and the tax regime `RF20`.
  This requires `ph-fatturapa` 3.1.1, which is therefore a release prerequisite.
  The "Fattura Semplificata" is still not supported

v5.0.4 - 2026-09-25
* Added the new module `phive-rules-crs` with the OECD Common Reporting Standard (CRS) XML Schema, VES coordinates `org.oecd.ties:crs:2.0` and `org.oecd.ties:crs:3.0`.
  See [phive-rules issue #57](https://github.com/phax/phive-rules/issues/57).
  The schemas are the ones published by IRAS on the [CRS Filing](https://www.iras.gov.sg/taxes/international-tax/common-reporting-standard-(crs)/crs-filing) page, where V2.0 is the prevailing schema and V3.0 applies with effect from 1 January 2027.
  They carry the OECD target namespaces `urn:oecd:ties:crs:v2` and `urn:oecd:ties:crs:v3` and contain nothing Singapore specific, which is why the Group ID is `org.oecd.ties` and not a national one.
  The two packages ship different content under the same file name `CommonTypesFatcaCrs_v2.0.xsd` - the V3.0 one adds the account type `OECD606` "Specified Electronic Money Product" - so each version has its own copy of all five XSDs.
  Neither the OECD nor IRAS publish example documents, so the two test files are hand written and contain no real data.

v5.0.3 - 2026-09-22
* Provided the KSeF XSD includes as part of the deployment, so that no external data access is needed
* Using CII D22B uncoupled XSDs

v5.0.2 - 2026-09-01
* Updated to UBL 2.5 final

v5.0.1 - 2026-08-03
* Moved the shared registration SPI and helper classes out into the separate [phive-rules-shared](https://github.com/phax/phive-rules-shared) project (Maven artifact `com.helger.phive.rules:phive-rules-shared`, package `com.helger.phive.rules.shared`).
  The `phive-rules-foundation-api` module was removed; all format modules now depend on `phive-rules-shared`

v5.0.0 - 2026-08-03
* Initial release after extraction from [phive-rules](https://github.com/phax/phive-rules) v4.5.0
* Contains the foundational XSD-only document format modules (`phive-rules-cii`, `phive-rules-ubl`, `phive-rules-ebinterface`, `phive-rules-facturae`, `phive-rules-fatturapa`, `phive-rules-finvoice`, `phive-rules-ksef`, `phive-rules-osa`, `phive-rules-teapps`) and the new `phive-rules-foundation-api` (package `com.helger.phive.rules.foundation`) holding the shared registration SPI and helpers

---

My personal [Coding Styleguide](https://github.com/phax/meta/blob/master/CodingStyleguide.md) |
It is appreciated if you star the GitHub project if you like it.
