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
package com.helger.phive.brazil.mock;

import static org.junit.Assert.assertTrue;

import org.jspecify.annotations.NonNull;

import com.helger.annotation.concurrent.Immutable;
import com.helger.annotation.style.ReturnsMutableCopy;
import com.helger.base.enforce.ValueEnforcer;
import com.helger.collection.commons.CommonsArrayList;
import com.helger.collection.commons.ICommonsList;
import com.helger.diver.api.coord.DVRCoordinate;
import com.helger.io.resource.ClassPathResource;
import com.helger.io.resource.IReadableResource;
import com.helger.phive.api.executorset.ValidationExecutorSetRegistry;
import com.helger.phive.api.mock.PhiveTestFile;
import com.helger.phive.brazil.BrazilValidation;
import com.helger.phive.xml.source.IValidationSourceXML;

@Immutable
public final class CTestFiles
{
  public static final ValidationExecutorSetRegistry <IValidationSourceXML> VES_REGISTRY = new ValidationExecutorSetRegistry <> ();
  static
  {
    BrazilValidation.initBrazil (VES_REGISTRY);
  }

  private CTestFiles ()
  {}

  @NonNull
  @ReturnsMutableCopy
  public static ICommonsList <PhiveTestFile> getAllTestFiles ()
  {
    final ICommonsList <PhiveTestFile> ret = new CommonsArrayList <> ();
    for (final DVRCoordinate aVESID : new DVRCoordinate [] { BrazilValidation.VID_NFE_PL010F,
                                                             BrazilValidation.VID_NFE_PROC_PL010F,
                                                             BrazilValidation.VID_CTE_NT2026_002,
                                                             BrazilValidation.VID_CTE_PROC_NT2026_002,
                                                             BrazilValidation.VID_CTE_OS_NT2026_002,
                                                             BrazilValidation.VID_CTE_OS_PROC_NT2026_002,
                                                             BrazilValidation.VID_GTVE_NT2026_002,
                                                             BrazilValidation.VID_GTVE_PROC_NT2026_002,
                                                             BrazilValidation.VID_CTE_SIMP_NT2026_002,
                                                             BrazilValidation.VID_CTE_SIMP_PROC_NT2026_002,
                                                             BrazilValidation.VID_MDFE_NT2025_001,
                                                             BrazilValidation.VID_MDFE_PROC_NT2025_001,
                                                             BrazilValidation.VID_NFCOM_NT2026_002,
                                                             BrazilValidation.VID_NFCOM_PROC_NT2026_002,
                                                             BrazilValidation.VID_NF3E_NT2026_002,
                                                             BrazilValidation.VID_NF3E_PROC_NT2026_002,
                                                             BrazilValidation.VID_BPE_NT2026_002,
                                                             BrazilValidation.VID_BPE_PROC_NT2026_002,
                                                             BrazilValidation.VID_BPE_TM_NT2026_002,
                                                             BrazilValidation.VID_BPE_TM_PROC_NT2026_002,
                                                             BrazilValidation.VID_BPE_TA_NT2026_002,
                                                             BrazilValidation.VID_NFAG_NT2026_002,
                                                             BrazilValidation.VID_NFAG_PROC_NT2026_002,
                                                             BrazilValidation.VID_NFGAS_NT2026_002,
                                                             BrazilValidation.VID_NFGAS_PROC_NT2026_002,
                                                             BrazilValidation.VID_NFSE_DPS_20260727,
                                                             BrazilValidation.VID_NFSE_20260727 })
      for (final IReadableResource aRes : getAllMatchingTestFiles (aVESID))
      {
        assertTrue ("Not existing test file: " + aRes.getPath (), aRes.exists ());
        ret.add (PhiveTestFile.createGoodCase (aRes, aVESID));
      }
    return ret;
  }

  @NonNull
  @ReturnsMutableCopy
  private static ICommonsList <? extends IReadableResource> _list (@NonNull final String sFolder,
                                                                   @NonNull final String... aFilenames)
  {
    return new CommonsArrayList <> (aFilenames,
                                    s -> new ClassPathResource ("/external/test-files/" + sFolder + "/" + s));
  }

  @NonNull
  @ReturnsMutableCopy
  public static ICommonsList <? extends IReadableResource> getAllMatchingTestFiles (@NonNull final DVRCoordinate aVESID)
  {
    ValueEnforcer.notNull (aVESID, "VESID");

    // No official example documents are published. See the PROVENANCE.txt file in each test file
    // folder for the origin of each file - they are either MIT licensed samples of akretion/nfelib
    // and Unimake or hand written synthetic documents with fake data and a dummy signature.

    // NF-e
    if (aVESID.equals (BrazilValidation.VID_NFE_PL010F))
      return _list ("nfe",
                    "nfe-from-nfelib-35180834128745000152550010000476711079516696.xml",
                    "nfe-from-nfelib-reforma-tributaria.xml");
    if (aVESID.equals (BrazilValidation.VID_NFE_PROC_PL010F))
      return _list ("nfe",
                    "26180875335849000115550010000016871192213331-nfe.xml",
                    "35180834128745000152550010000474281920007498-nfe.xml",
                    "35180834128745000152550010000474491454651420-nfe.xml",
                    "35180834128745000152550010000474501597356342-nfe.xml",
                    "35180834128745000152550010000474641681223493-nfe.xml",
                    "35180834128745000152550010000476051695511860-nfe.xml",
                    "35180834128745000152550010000476121675985748-nfe.xml",
                    "35180834128745000152550010000476491552806942-nfe.xml",
                    "35180834128745000152550010000476711079516696-nfe.xml",
                    "35180834128745000152550010000476781421693968-nfe.xml",
                    "35180834128745000152550010000476861118934859-nfe.xml",
                    "41170706117473000150550010000463202612756525-procNFe.xml",
                    "nfe_reforma_tributaria.xml");

    // CT-e, CT-e OS, GTV-e, CT-e Simplificado
    if (aVESID.equals (BrazilValidation.VID_CTE_NT2026_002))
      return _list ("cte", "cte-rodoviario.xml");
    if (aVESID.equals (BrazilValidation.VID_CTE_PROC_NT2026_002))
      return _list ("cte", "cte-proc-rodoviario.xml");
    if (aVESID.equals (BrazilValidation.VID_CTE_OS_NT2026_002))
      return _list ("cte", "cte-os.xml");
    if (aVESID.equals (BrazilValidation.VID_CTE_OS_PROC_NT2026_002))
      return _list ("cte", "cte-os-proc.xml");
    if (aVESID.equals (BrazilValidation.VID_GTVE_NT2026_002))
      return _list ("cte", "gtve.xml");
    if (aVESID.equals (BrazilValidation.VID_GTVE_PROC_NT2026_002))
      return _list ("cte", "gtve-proc.xml");
    if (aVESID.equals (BrazilValidation.VID_CTE_SIMP_NT2026_002))
      return _list ("cte", "cte-simp.xml");
    if (aVESID.equals (BrazilValidation.VID_CTE_SIMP_PROC_NT2026_002))
      return _list ("cte", "cte-simp-proc.xml");

    // MDF-e
    if (aVESID.equals (BrazilValidation.VID_MDFE_NT2025_001))
      return _list ("mdfe", "mdfe-rodoviario.xml");
    if (aVESID.equals (BrazilValidation.VID_MDFE_PROC_NT2025_001))
      return _list ("mdfe", "mdfe-proc-rodoviario.xml");

    // NFCom
    if (aVESID.equals (BrazilValidation.VID_NFCOM_NT2026_002))
      return _list ("nfcom", "nfcom.xml");
    if (aVESID.equals (BrazilValidation.VID_NFCOM_PROC_NT2026_002))
      return _list ("nfcom", "nfcom-proc.xml");

    // NF3e
    if (aVESID.equals (BrazilValidation.VID_NF3E_NT2026_002))
      return _list ("nf3e", "nf3e.xml");
    if (aVESID.equals (BrazilValidation.VID_NF3E_PROC_NT2026_002))
      return _list ("nf3e", "nf3e-proc.xml");

    // BP-e
    if (aVESID.equals (BrazilValidation.VID_BPE_NT2026_002))
      return _list ("bpe", "bpe.xml");
    if (aVESID.equals (BrazilValidation.VID_BPE_PROC_NT2026_002))
      return _list ("bpe", "bpe-proc.xml");
    if (aVESID.equals (BrazilValidation.VID_BPE_TM_NT2026_002))
      return _list ("bpe", "bpe-tm.xml");
    if (aVESID.equals (BrazilValidation.VID_BPE_TM_PROC_NT2026_002))
      return _list ("bpe", "bpe-tm-proc.xml");
    if (aVESID.equals (BrazilValidation.VID_BPE_TA_NT2026_002))
      return _list ("bpe", "bpe-ta.xml");

    // NFAg
    if (aVESID.equals (BrazilValidation.VID_NFAG_NT2026_002))
      return _list ("nfag", "nfag.xml");
    if (aVESID.equals (BrazilValidation.VID_NFAG_PROC_NT2026_002))
      return _list ("nfag", "nfag-proc.xml");

    // NFGas
    if (aVESID.equals (BrazilValidation.VID_NFGAS_NT2026_002))
      return _list ("nfgas", "nfgas.xml");
    if (aVESID.equals (BrazilValidation.VID_NFGAS_PROC_NT2026_002))
      return _list ("nfgas", "nfgas-proc.xml");

    // NFS-e
    if (aVESID.equals (BrazilValidation.VID_NFSE_DPS_20260727))
      return _list ("nfse",
                    "dps-simples.xml",
                    "ConsultarNFSeRPS-ped-sitnfserps.xml",
                    "GerarNfseMinima-env-loterps.xml",
                    "GerarNfseTagsIBSCBS-env-loterps.xml");
    if (aVESID.equals (BrazilValidation.VID_NFSE_20260727))
      return _list ("nfse", "ConsultarNFSeEnvio-ped-sitnfse.xml", "unimake-RetornoNACIONAL.xml");

    throw new IllegalArgumentException ("Invalid VESID: " + aVESID);
  }
}
