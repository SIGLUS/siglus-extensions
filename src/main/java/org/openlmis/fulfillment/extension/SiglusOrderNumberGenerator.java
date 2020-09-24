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

import lombok.extern.slf4j.Slf4j;
import org.openlmis.fulfillment.domain.Order;
import org.openlmis.fulfillment.domain.RequisitionExtension;
import org.openlmis.fulfillment.extension.point.OrderNumberGenerator;
import org.openlmis.fulfillment.repository.RequisitionExtensionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("SiglusOrderNumberGenerator")
@Slf4j
public class SiglusOrderNumberGenerator implements OrderNumberGenerator {

  @Autowired
  private RequisitionExtensionRepository requisitionExtensionRepository;

  @Override
  public String generate(Order order) {
    RequisitionExtension requisitionExtension = requisitionExtensionRepository
        .findByRequisitionId(order.getExternalId());
    log.info("requisitionExtension: {}", requisitionExtension);
    return requisitionExtension.getRequisitionNumberPrefix().substring(6)
        + String.format("%07d", requisitionExtension.getRequisitionNumber());
  }

}
