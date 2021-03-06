/*
 * Copyright (c) 2012 Jeremy Goetsch
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.jgoetsch.tradeframework.order.processing;

import com.jgoetsch.tradeframework.marketdata.MarketData;

public class TrailingStopOrderProcessor extends StopOrderProcessor {

	private double trailingAmount;

	public TrailingStopOrderProcessor(int quantity, double stopPrice, double trailingAmount) {
		super(quantity, stopPrice);
		this.trailingAmount = trailingAmount;
	}

	public TrailingStopOrderProcessor(int quantity, double stopPrice, double trailingAmount, TriggerMethod triggerMethod) {
		super(quantity, stopPrice, triggerMethod);
		this.trailingAmount = trailingAmount;
	}

	@Override
	protected void onNotTriggered(MarketData marketData) {
		setStopPrice(isSelling() ? Math.max(getStopPrice(), marketData.getLast() - trailingAmount)
				: Math.min(getStopPrice(), marketData.getLast() + trailingAmount));
	}

}
