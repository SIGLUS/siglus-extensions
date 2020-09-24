/*
 * This program is part of the OpenLMIS logistics management information system platform software.
 * Copyright © 2017 VillageReach
 *
 * This program is free software: you can redistribute it and/or modify it under the terms
 * of the GNU Affero General Public License as published by the Free Software Foundation, either
 * version 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY;
 * without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU Affero General Public License for more details. You should have received a copy of
 * the GNU Affero General Public License along with this program. If not, see
 * http://www.gnu.org/licenses.  For additional information contact info@OpenLMIS.org.
 */

package org.openlmis.fulfillment.extension;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import java.util.UUID;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.openlmis.fulfillment.domain.Order;
import org.openlmis.fulfillment.domain.RequisitionExtension;
import org.openlmis.fulfillment.repository.RequisitionExtensionRepository;

@RunWith(MockitoJUnitRunner.class)
public class SiglusOrderNumberGeneratorTest {

  @Mock
  private RequisitionExtensionRepository requisitionExtensionRepository;

  @InjectMocks
  private SiglusOrderNumberGenerator siglusOrderNumberGenerator;

  @Test
  public void shouldGetOrderNumberFromNormalRequisition() {
    // given
    UUID requisitionId = UUID.randomUUID();
    RequisitionExtension requisitionExtension = RequisitionExtension.builder()
        .requisitionId(requisitionId)
        .requisitionNumber(54)
        .requisitionNumberPrefix("RNR-NO01042104")
        .build();
    when(requisitionExtensionRepository.findByRequisitionId(requisitionId))
        .thenReturn(requisitionExtension);
    Order order = new Order();
    order.setExternalId(requisitionId);

    // when
    String result = siglusOrderNumberGenerator.generate(order);

    // then
    assertEquals("010421040000054", result);
  }

  @Test
  public void shouldGetOrderNumberFromEmergencyRequisition() {
    // given
    UUID requisitionId = UUID.randomUUID();
    RequisitionExtension requisitionExtension = RequisitionExtension.builder()
        .requisitionId(requisitionId)
        .requisitionNumber(10123)
        .requisitionNumberPrefix("RNR-EM01041205")
        .build();
    when(requisitionExtensionRepository.findByRequisitionId(requisitionId))
        .thenReturn(requisitionExtension);
    Order order = new Order();
    order.setExternalId(requisitionId);

    // when
    String result = siglusOrderNumberGenerator.generate(order);

    // then
    assertEquals("010412050010123", result);
  }
}