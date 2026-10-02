/*
 * Copyright (C) 2026 Philip Helger (www.helger.com)
 * philip[at]helger[dot]com
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.helger.phive.brazil;

import javax.xml.XMLConstants;
import javax.xml.validation.SchemaFactory;

import org.jspecify.annotations.NonNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.SAXNotRecognizedException;
import org.xml.sax.SAXNotSupportedException;

import com.helger.annotation.Nonempty;
import com.helger.annotation.concurrent.Immutable;
import com.helger.base.enforce.ValueEnforcer;
import com.helger.collection.commons.CommonsArrayList;
import com.helger.diver.api.coord.DVRCoordinate;
import com.helger.io.resource.ClassPathResource;
import com.helger.phive.api.executorset.IValidationExecutorSetRegistry;
import com.helger.phive.rules.shared.DVRHelper;
import com.helger.phive.xml.executorset.VesXmlBuilder;
import com.helger.phive.xml.source.IValidationSourceXML;
import com.helger.xml.ls.SimpleLSResourceResolver;
import com.helger.xml.sax.LoggingSAXErrorHandler;
import com.helger.xml.schema.XMLSchemaCache;

/**
 * Brazilian electronic fiscal documents validation configuration. This covers the DF-e documents
 * specified by ENCAT/SEFAZ (NF-e/NFC-e, CT-e, CT-e OS, GTV-e, CT-e Simplificado, MDF-e, NFCom,
 * NF3e, BP-e, BP-e TM, BP-e TA, NFAg and NFGas) as well as the NFS-e Padrão Nacional.
 * <p>
 * Only the XML Schemas are available - the business rules are only published as tables in the
 * Manuals (MOC) and Technical Notes (NT) and are checked by the authorizing tax authority.
 * <p>
 * The layout version of a document (e.g. NF-e 4.00) stays the same, while the schema packages are
 * updated several times a year. Therefore the DVR version consists of the layout version plus a
 * classifier identifying the schema package (e.g. <code>4.0.0-pl010f_v1_04</code> for the NF-e
 * "Pacote de Liberação 010f v1.04").
 * <p>
 * For each document there is one VES for the document itself as transmitted by the issuer (e.g.
 * <code>NFe</code>) and one for the authorized document wrapper including the protocol of the tax
 * authority (e.g. <code>nfeProc</code>). The modal specific parts of CT-e and MDF-e
 * (<code>infModal</code>) are declared as <code>xs:any</code> with <code>processContents="skip"</code>
 * and are therefore not validated.
 *
 * @author Philip Helger
 */
@Immutable
public final class BrazilValidation
{
  public static final String GROUP_ID = "br.gov.nfe";

  // NF-e / NFC-e (model 55 / 65) - Pacote de Liberação 010f v1.04
  private static final String VERSION_NFE_PL010F = "4.0.0-pl010f_v1_04";
  public static final DVRCoordinate VID_NFE_PL010F = DVRHelper.createCoordinate (GROUP_ID, "nfe", VERSION_NFE_PL010F);
  public static final DVRCoordinate VID_NFE_PROC_PL010F = DVRHelper.createCoordinate (GROUP_ID,
                                                                                      "nfe-proc",
                                                                                      VERSION_NFE_PL010F);

  // CT-e (model 57), CT-e OS (model 67), GTV-e (model 64) and CT-e Simplificado - NT 2026.002 RTC
  // v1.01 corr 2
  private static final String VERSION_CTE_NT2026_002 = "4.0.0-nt2026_002_v1_01_corr2";
  public static final DVRCoordinate VID_CTE_NT2026_002 = DVRHelper.createCoordinate (GROUP_ID,
                                                                                     "cte",
                                                                                     VERSION_CTE_NT2026_002);
  public static final DVRCoordinate VID_CTE_PROC_NT2026_002 = DVRHelper.createCoordinate (GROUP_ID,
                                                                                          "cte-proc",
                                                                                          VERSION_CTE_NT2026_002);
  public static final DVRCoordinate VID_CTE_OS_NT2026_002 = DVRHelper.createCoordinate (GROUP_ID,
                                                                                        "cte-os",
                                                                                        VERSION_CTE_NT2026_002);
  public static final DVRCoordinate VID_CTE_OS_PROC_NT2026_002 = DVRHelper.createCoordinate (GROUP_ID,
                                                                                             "cte-os-proc",
                                                                                             VERSION_CTE_NT2026_002);
  public static final DVRCoordinate VID_GTVE_NT2026_002 = DVRHelper.createCoordinate (GROUP_ID,
                                                                                      "gtve",
                                                                                      VERSION_CTE_NT2026_002);
  public static final DVRCoordinate VID_GTVE_PROC_NT2026_002 = DVRHelper.createCoordinate (GROUP_ID,
                                                                                           "gtve-proc",
                                                                                           VERSION_CTE_NT2026_002);
  public static final DVRCoordinate VID_CTE_SIMP_NT2026_002 = DVRHelper.createCoordinate (GROUP_ID,
                                                                                          "cte-simp",
                                                                                          VERSION_CTE_NT2026_002);
  public static final DVRCoordinate VID_CTE_SIMP_PROC_NT2026_002 = DVRHelper.createCoordinate (GROUP_ID,
                                                                                               "cte-simp-proc",
                                                                                               VERSION_CTE_NT2026_002);

  // MDF-e (model 58) - NT 2025.001 v1.04
  private static final String VERSION_MDFE_NT2025_001 = "3.0.0-nt2025_001_v1_04";
  public static final DVRCoordinate VID_MDFE_NT2025_001 = DVRHelper.createCoordinate (GROUP_ID,
                                                                                      "mdfe",
                                                                                      VERSION_MDFE_NT2025_001);
  public static final DVRCoordinate VID_MDFE_PROC_NT2025_001 = DVRHelper.createCoordinate (GROUP_ID,
                                                                                           "mdfe-proc",
                                                                                           VERSION_MDFE_NT2025_001);

  // NFCom (model 62), NF3e (model 66), BP-e (model 63), NFAg (model 75) and NFGas (model 76) - NT
  // 2026.002 RTC v1.01
  private static final String VERSION_NT2026_002 = "1.0.0-nt2026_002_v1_01";
  public static final DVRCoordinate VID_NFCOM_NT2026_002 = DVRHelper.createCoordinate (GROUP_ID,
                                                                                       "nfcom",
                                                                                       VERSION_NT2026_002);
  public static final DVRCoordinate VID_NFCOM_PROC_NT2026_002 = DVRHelper.createCoordinate (GROUP_ID,
                                                                                            "nfcom-proc",
                                                                                            VERSION_NT2026_002);
  public static final DVRCoordinate VID_NF3E_NT2026_002 = DVRHelper.createCoordinate (GROUP_ID,
                                                                                      "nf3e",
                                                                                      VERSION_NT2026_002);
  public static final DVRCoordinate VID_NF3E_PROC_NT2026_002 = DVRHelper.createCoordinate (GROUP_ID,
                                                                                           "nf3e-proc",
                                                                                           VERSION_NT2026_002);
  public static final DVRCoordinate VID_BPE_NT2026_002 = DVRHelper.createCoordinate (GROUP_ID,
                                                                                     "bpe",
                                                                                     VERSION_NT2026_002);
  public static final DVRCoordinate VID_BPE_PROC_NT2026_002 = DVRHelper.createCoordinate (GROUP_ID,
                                                                                          "bpe-proc",
                                                                                          VERSION_NT2026_002);
  public static final DVRCoordinate VID_BPE_TM_NT2026_002 = DVRHelper.createCoordinate (GROUP_ID,
                                                                                        "bpe-tm",
                                                                                        VERSION_NT2026_002);
  public static final DVRCoordinate VID_BPE_TM_PROC_NT2026_002 = DVRHelper.createCoordinate (GROUP_ID,
                                                                                             "bpe-tm-proc",
                                                                                             VERSION_NT2026_002);
  // No official proc schema exists for BP-e TA
  public static final DVRCoordinate VID_BPE_TA_NT2026_002 = DVRHelper.createCoordinate (GROUP_ID,
                                                                                        "bpe-ta",
                                                                                        VERSION_NT2026_002);
  public static final DVRCoordinate VID_NFAG_NT2026_002 = DVRHelper.createCoordinate (GROUP_ID,
                                                                                      "nfag",
                                                                                      VERSION_NT2026_002);
  public static final DVRCoordinate VID_NFAG_PROC_NT2026_002 = DVRHelper.createCoordinate (GROUP_ID,
                                                                                           "nfag-proc",
                                                                                           VERSION_NT2026_002);
  public static final DVRCoordinate VID_NFGAS_NT2026_002 = DVRHelper.createCoordinate (GROUP_ID,
                                                                                       "nfgas",
                                                                                       VERSION_NT2026_002);
  public static final DVRCoordinate VID_NFGAS_PROC_NT2026_002 = DVRHelper.createCoordinate (GROUP_ID,
                                                                                            "nfgas-proc",
                                                                                            VERSION_NT2026_002);

  // NFS-e Padrão Nacional layout 1.01 - schema package of 2026-07-27
  private static final String VERSION_NFSE_20260727 = "1.1.0-v20260727";
  public static final DVRCoordinate VID_NFSE_DPS_20260727 = DVRHelper.createCoordinate (GROUP_ID,
                                                                                        "nfse-dps",
                                                                                        VERSION_NFSE_20260727);
  public static final DVRCoordinate VID_NFSE_20260727 = DVRHelper.createCoordinate (GROUP_ID,
                                                                                    "nfse",
                                                                                    VERSION_NFSE_20260727);

  private static final Logger LOGGER = LoggerFactory.getLogger (BrazilValidation.class);

  private BrazilValidation ()
  {}

  @NonNull
  private static ClassLoader _getCL ()
  {
    return BrazilValidation.class.getClassLoader ();
  }

  @NonNull
  private static ClassPathResource _xsd (@NonNull @Nonempty final String sPath)
  {
    return new ClassPathResource ("/external/schemas/" + sPath, _getCL ());
  }

  /**
   * Register all Brazilian validation execution sets to the provided registry.
   *
   * @param aRegistry
   *        The registry to add the artefacts. May not be <code>null</code>.
   */
  public static void initBrazil (@NonNull final IValidationExecutorSetRegistry <IValidationSourceXML> aRegistry)
  {
    ValueEnforcer.notNull (aRegistry, "Registry");

    // NF-e / NFC-e
    {
      // The 010 packages contain no "procNFe_v4.00.xsd" - it is taken unchanged from PL_009p, as it
      // only includes "leiauteNFe_v4.00.xsd" which contains the type "TNfeProc"
      final String sBase = "nfe/PL_010f_v1.04/";
      VesXmlBuilder.builder ()
                   .vesID (VID_NFE_PL010F)
                   .displayNamePrefix ("NF-e ")
                   .notDeprecated ()
                   .addXSD (_xsd (sBase + "nfe_v4.00.xsd"))
                   .registerInto (aRegistry);
      VesXmlBuilder.builder ()
                   .vesID (VID_NFE_PROC_PL010F)
                   .displayNamePrefix ("NF-e Proc ")
                   .notDeprecated ()
                   .addXSD (_xsd (sBase + "procNFe_v4.00.xsd"))
                   .registerInto (aRegistry);
    }

    // CT-e, CT-e OS, GTV-e and CT-e Simplificado
    {
      final String sBase = "cte/PL_CTe_400_NT2026.002_RTC_1.01_corr_2/";
      VesXmlBuilder.builder ()
                   .vesID (VID_CTE_NT2026_002)
                   .displayNamePrefix ("CT-e ")
                   .notDeprecated ()
                   .addXSD (_xsd (sBase + "cte_v4.00.xsd"))
                   .registerInto (aRegistry);
      VesXmlBuilder.builder ()
                   .vesID (VID_CTE_PROC_NT2026_002)
                   .displayNamePrefix ("CT-e Proc ")
                   .notDeprecated ()
                   .addXSD (_xsd (sBase + "procCTe_v4.00.xsd"))
                   .registerInto (aRegistry);
      VesXmlBuilder.builder ()
                   .vesID (VID_CTE_OS_NT2026_002)
                   .displayNamePrefix ("CT-e OS ")
                   .notDeprecated ()
                   .addXSD (_xsd (sBase + "cteOS_v4.00.xsd"))
                   .registerInto (aRegistry);
      VesXmlBuilder.builder ()
                   .vesID (VID_CTE_OS_PROC_NT2026_002)
                   .displayNamePrefix ("CT-e OS Proc ")
                   .notDeprecated ()
                   .addXSD (_xsd (sBase + "procCTeOS_v4.00.xsd"))
                   .registerInto (aRegistry);
      VesXmlBuilder.builder ()
                   .vesID (VID_GTVE_NT2026_002)
                   .displayNamePrefix ("GTV-e ")
                   .notDeprecated ()
                   .addXSD (_xsd (sBase + "GTVe_v4.00.xsd"))
                   .registerInto (aRegistry);
      VesXmlBuilder.builder ()
                   .vesID (VID_GTVE_PROC_NT2026_002)
                   .displayNamePrefix ("GTV-e Proc ")
                   .notDeprecated ()
                   .addXSD (_xsd (sBase + "procGTVe_v4.00.xsd"))
                   .registerInto (aRegistry);
      VesXmlBuilder.builder ()
                   .vesID (VID_CTE_SIMP_NT2026_002)
                   .displayNamePrefix ("CT-e Simplificado ")
                   .notDeprecated ()
                   .addXSD (_xsd (sBase + "cteSimp_v4.00.xsd"))
                   .registerInto (aRegistry);
      VesXmlBuilder.builder ()
                   .vesID (VID_CTE_SIMP_PROC_NT2026_002)
                   .displayNamePrefix ("CT-e Simplificado Proc ")
                   .notDeprecated ()
                   .addXSD (_xsd (sBase + "procCTeSimp_v4.00.xsd"))
                   .registerInto (aRegistry);
    }

    // MDF-e
    {
      // MDF-e allows up to 20.000 referenced documents ("maxOccurs" of infCTe, infNFe and
      // infMDFeTransp) which exceeds the JDK default limit of 5.000
      final SchemaFactory aCustomSF = SchemaFactory.newInstance (XMLConstants.W3C_XML_SCHEMA_NS_URI);
      try
      {
        aCustomSF.setProperty ("jdk.xml.maxOccurLimit", Integer.valueOf (20_000));
      }
      catch (final SAXNotRecognizedException | SAXNotSupportedException ex)
      {
        LOGGER.error ("Failed to set XML property", ex);
      }
      final XMLSchemaCache aCustomSchemaCache = new XMLSchemaCache (aCustomSF,
                                                                    new LoggingSAXErrorHandler (),
                                                                    new SimpleLSResourceResolver ());

      final String sBase = "mdfe/PL_MDFe_300b_NT012025_1.04/";
      VesXmlBuilder.builder ()
                   .vesID (VID_MDFE_NT2025_001)
                   .displayNamePrefix ("MDF-e ")
                   .notDeprecated ()
                   .addXSD (new CommonsArrayList <> (_xsd (sBase + "mdfe_v3.00.xsd")), aCustomSchemaCache)
                   .registerInto (aRegistry);
      VesXmlBuilder.builder ()
                   .vesID (VID_MDFE_PROC_NT2025_001)
                   .displayNamePrefix ("MDF-e Proc ")
                   .notDeprecated ()
                   .addXSD (new CommonsArrayList <> (_xsd (sBase + "procMDFe_v3.00.xsd")), aCustomSchemaCache)
                   .registerInto (aRegistry);
    }

    // NFCom
    {
      final String sBase = "nfcom/PL_NFCOM_1.00_NT2026.002_RTC_1.01/";
      VesXmlBuilder.builder ()
                   .vesID (VID_NFCOM_NT2026_002)
                   .displayNamePrefix ("NFCom ")
                   .notDeprecated ()
                   .addXSD (_xsd (sBase + "nfcom_v1.00.xsd"))
                   .registerInto (aRegistry);
      VesXmlBuilder.builder ()
                   .vesID (VID_NFCOM_PROC_NT2026_002)
                   .displayNamePrefix ("NFCom Proc ")
                   .notDeprecated ()
                   .addXSD (_xsd (sBase + "procNFCom_v1.00.xsd"))
                   .registerInto (aRegistry);
    }

    // NF3e
    {
      final String sBase = "nf3e/PL_NF3E_1.00a_NT2026.002_RTC_1.01/";
      VesXmlBuilder.builder ()
                   .vesID (VID_NF3E_NT2026_002)
                   .displayNamePrefix ("NF3e ")
                   .notDeprecated ()
                   .addXSD (_xsd (sBase + "nf3e_v1.00.xsd"))
                   .registerInto (aRegistry);
      VesXmlBuilder.builder ()
                   .vesID (VID_NF3E_PROC_NT2026_002)
                   .displayNamePrefix ("NF3e Proc ")
                   .notDeprecated ()
                   .addXSD (_xsd (sBase + "procNF3e_v1.00.xsd"))
                   .registerInto (aRegistry);
    }

    // BP-e, BP-e TM and BP-e TA
    {
      final String sBase = "bpe/PL_BPe_100b_NT2026.002_RTC_1.01/";
      VesXmlBuilder.builder ()
                   .vesID (VID_BPE_NT2026_002)
                   .displayNamePrefix ("BP-e ")
                   .notDeprecated ()
                   .addXSD (_xsd (sBase + "bpe_v1.00.xsd"))
                   .registerInto (aRegistry);
      VesXmlBuilder.builder ()
                   .vesID (VID_BPE_PROC_NT2026_002)
                   .displayNamePrefix ("BP-e Proc ")
                   .notDeprecated ()
                   .addXSD (_xsd (sBase + "procBPe_v1.00.xsd"))
                   .registerInto (aRegistry);
      VesXmlBuilder.builder ()
                   .vesID (VID_BPE_TM_NT2026_002)
                   .displayNamePrefix ("BP-e TM ")
                   .notDeprecated ()
                   .addXSD (_xsd (sBase + "bpeTM_v1.00.xsd"))
                   .registerInto (aRegistry);
      VesXmlBuilder.builder ()
                   .vesID (VID_BPE_TM_PROC_NT2026_002)
                   .displayNamePrefix ("BP-e TM Proc ")
                   .notDeprecated ()
                   .addXSD (_xsd (sBase + "procBPeTM_v1.00.xsd"))
                   .registerInto (aRegistry);
      VesXmlBuilder.builder ()
                   .vesID (VID_BPE_TA_NT2026_002)
                   .displayNamePrefix ("BP-e TA ")
                   .notDeprecated ()
                   .addXSD (_xsd (sBase + "bpeTA_v1.00.xsd"))
                   .registerInto (aRegistry);
    }

    // NFAg
    {
      final String sBase = "nfag/PL_NFAg_NT2026.002_RTC_1.01/";
      VesXmlBuilder.builder ()
                   .vesID (VID_NFAG_NT2026_002)
                   .displayNamePrefix ("NFAg ")
                   .notDeprecated ()
                   .addXSD (_xsd (sBase + "nfag_v1.00.xsd"))
                   .registerInto (aRegistry);
      VesXmlBuilder.builder ()
                   .vesID (VID_NFAG_PROC_NT2026_002)
                   .displayNamePrefix ("NFAg Proc ")
                   .notDeprecated ()
                   .addXSD (_xsd (sBase + "procNFAg_v1.00.xsd"))
                   .registerInto (aRegistry);
    }

    // NFGas
    {
      final String sBase = "nfgas/PL_NFGas_NT2026.002_RTC_1.01/";
      VesXmlBuilder.builder ()
                   .vesID (VID_NFGAS_NT2026_002)
                   .displayNamePrefix ("NFGas ")
                   .notDeprecated ()
                   .addXSD (_xsd (sBase + "nfgas_v1.00.xsd"))
                   .registerInto (aRegistry);
      VesXmlBuilder.builder ()
                   .vesID (VID_NFGAS_PROC_NT2026_002)
                   .displayNamePrefix ("NFGas Proc ")
                   .notDeprecated ()
                   .addXSD (_xsd (sBase + "procNFGas_v1.00.xsd"))
                   .registerInto (aRegistry);
    }

    // NFS-e Padrão Nacional
    {
      // The schema package for "Produção Restrita" of 2026-07-27 is used, as the production package
      // of 2026-02-09 contains an invalid XSD regular expression for the DPS series ("^0{0,4}\d{1,5}$"
      // where "^" and "$" are literals in XSD) that rejects all real documents
      final String sBase = "nfse/esquemas-nfse-rtc-v1-01-20260727/";
      VesXmlBuilder.builder ()
                   .vesID (VID_NFSE_DPS_20260727)
                   .displayNamePrefix ("NFS-e DPS ")
                   .notDeprecated ()
                   .addXSD (_xsd (sBase + "DPS_v1.01.xsd"))
                   .registerInto (aRegistry);
      VesXmlBuilder.builder ()
                   .vesID (VID_NFSE_20260727)
                   .displayNamePrefix ("NFS-e ")
                   .notDeprecated ()
                   .addXSD (_xsd (sBase + "NFSe_v1.01.xsd"))
                   .registerInto (aRegistry);
    }
  }
}
