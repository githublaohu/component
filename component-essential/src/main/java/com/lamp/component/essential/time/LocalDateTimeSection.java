/*
 *Copyright (c) [Year] [name of copyright holder]
 *[Software Name] is licensed under Mulan PubL v2.
 *You can use this software according to the terms and conditions of the Mulan PubL v2.
 *You may obtain a copy of Mulan PubL v2 at:
 *         http://license.coscl.org.cn/MulanPubL-2.0
 *THIS SOFTWARE IS PROVIDED ON AN "AS IS" BASIS, WITHOUT WARRANTIES OF ANY KIND,
 *EITHER EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO NON-INFRINGEMENT,
 *MERCHANTABILITY OR FIT FOR A PARTICULAR PURPOSE.
 *See the Mulan PubL v2 for more details.
 */
package com.lamp.component.essential.time;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class LocalDateTimeSection {

	private LocalDateTime beginTime;

	private LocalDateTime endTime;

	private ChronoUnit chronoUnit;
	
	private int unitNum = 1;

	public LocalDateTimeSection(ChronoUnit chronoUnit) {
		this.chronoUnit = chronoUnit;
		beginTime = LocalDateTimeUtils.truncatedTo(LocalDateTime.now(), this.chronoUnit);
		endTime = beginTime.plus(unitNum, this.chronoUnit).minusSeconds(1);
	}

	public LocalDateTime getBeginTime() {
		return beginTime;
	}

	public LocalDateTime getEndTime() {
		return endTime;
	}
	
}
