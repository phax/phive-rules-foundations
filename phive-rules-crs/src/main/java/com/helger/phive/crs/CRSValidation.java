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
package com.helger.phive.crs;

import org.jspecify.annotations.NonNull;

import com.helger.annotation.concurrent.Immutable;
import com.helger.base.enforce.ValueEnforcer;
import com.helger.collection.commons.CommonsArrayList;
import com.helger.collection.commons.ICommonsList;
import com.helger.diver.api.coord.DVRCoordinate;
import com.helger.io.resource.ClassPathResource;
import com.helger.phive.api.executorset.IValidationExecutorSetRegistry;
import com.helger.phive.rules.shared.DVRHelper;
import com.helger.phive.xml.executorset.VesXmlBuilder;
import com.helger.phive.xml.source.IValidationSourceXML;

/**
 * OECD Common Reporting Standard (CRS) validation configuration.
 * <p>
 * The CRS XML Schema is issued by the OECD for the automatic exchange of financial account
 * information and is used unchanged by the national tax administrations. The Inland Revenue
 * Authority of Singapore (IRAS) for example requires Reporting SGFIs to file their CRS Return in
 * this format - CRS XML Schema V2.0 currently, and V3.0 with effect from 1 January 2027.
 * <p>
 * The root element is <code>CRS_OECD</code> in the namespace <code>urn:oecd:ties:crs:v2</code>
 * respectively <code>urn:oecd:ties:crs:v3</code>. Note that the two schema packages ship different
 * content under the same file name <code>CommonTypesFatcaCrs_v2.0.xsd</code>, so they must be kept
 * apart.
 *
 * @author Philip Helger
 */
@Immutable
public final class CRSValidation
{
  public static final String GROUP_ID = "org.oecd.ties";

  /** OECD CRS XML Schema V2.0 */
  public static final DVRCoordinate VID_CRS_20 = DVRHelper.createCoordinate (GROUP_ID, "crs", "2.0");
  /** OECD CRS XML Schema V3.0 */
  public static final DVRCoordinate VID_CRS_30 = DVRHelper.createCoordinate (GROUP_ID, "crs", "3.0");

  private CRSValidation ()
  {}

  @NonNull
  private static ClassLoader _getCL ()
  {
    return CRSValidation.class.getClassLoader ();
  }

  /**
   * Register all standard CRS validation execution sets to the provided registry.
   *
   * @param aRegistry
   *        The registry to add the artefacts. May not be <code>null</code>.
   */
  public static void initCRS (@NonNull final IValidationExecutorSetRegistry <IValidationSourceXML> aRegistry)
  {
    ValueEnforcer.notNull (aRegistry, "Registry");

    // CRS 2.0
    {
      final ICommonsList <ClassPathResource> aResList = new CommonsArrayList <> (new ClassPathResource ("/external/schemas/2.0/isocrstypes_v1.1.xsd",
                                                                                                        _getCL ()),
                                                                                 new ClassPathResource ("/external/schemas/2.0/oecdcrstypes_v5.0.xsd",
                                                                                                        _getCL ()),
                                                                                 new ClassPathResource ("/external/schemas/2.0/CommonTypesFatcaCrs_v2.0.xsd",
                                                                                                        _getCL ()),
                                                                                 new ClassPathResource ("/external/schemas/2.0/FatcaTypes_v1.2.xsd",
                                                                                                        _getCL ()),
                                                                                 new ClassPathResource ("/external/schemas/2.0/CrsXML_v2.0.xsd",
                                                                                                        _getCL ()));
      VesXmlBuilder.builder ()
                   .vesID (VID_CRS_20)
                   .displayNamePrefix ("OECD CRS ")
                   .notDeprecated ()
                   .addXSD (aResList)
                   .registerInto (aRegistry);
    }

    // CRS 3.0
    {
      final ICommonsList <ClassPathResource> aResList = new CommonsArrayList <> (new ClassPathResource ("/external/schemas/3.0/isocrstypes_v1.1.xsd",
                                                                                                        _getCL ()),
                                                                                 new ClassPathResource ("/external/schemas/3.0/oecdcrstypes_v5.0.xsd",
                                                                                                        _getCL ()),
                                                                                 new ClassPathResource ("/external/schemas/3.0/CommonTypesFatcaCrs_v2.0.xsd",
                                                                                                        _getCL ()),
                                                                                 new ClassPathResource ("/external/schemas/3.0/FatcaTypes_v1.2.xsd",
                                                                                                        _getCL ()),
                                                                                 new ClassPathResource ("/external/schemas/3.0/CrsXML_v3.0.xsd",
                                                                                                        _getCL ()));
      VesXmlBuilder.builder ()
                   .vesID (VID_CRS_30)
                   .displayNamePrefix ("OECD CRS ")
                   .notDeprecated ()
                   .addXSD (aResList)
                   .registerInto (aRegistry);
    }
  }
}
