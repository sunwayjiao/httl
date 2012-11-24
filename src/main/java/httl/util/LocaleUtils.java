/*
 *  Licensed to the Apache Software Foundation (ASF) under one or more
 *  contributor license agreements.  See the NOTICE file distributed with
 *  this work for additional information regarding copyright ownership.
 *  The ASF licenses this file to You under the Apache License, Version 2.0
 *  (the "License"); you may not use this file except in compliance with
 *  the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package httl.util;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Pattern;

public class LocaleUtils {

	private static final Pattern UNDER_LINE_PATTERN = Pattern.compile("_");

	private static final int LOCALE_CACHE_SIZE = 10000;

	private static final Map<String, Locale> LOCALE_CACHE = Collections.synchronizedMap(new LinkedHashMap<String, Locale>() {
		private static final long serialVersionUID = 1377741378297004026L;
		@Override
		protected boolean removeEldestEntry(Entry<String, Locale> eldest) {
			return size() > LOCALE_CACHE_SIZE;
		}
	});

	public static Locale getLocale(String name) {
		if (name == null || name.length() == 0) {
			return null;
		}
		Locale locale = LOCALE_CACHE.get(name);
		if (locale == null) {
			String[] parts = UNDER_LINE_PATTERN.split(name);
			if (parts.length > 2) {
				locale = new Locale(parts[0], parts[1], parts[2]);
				LOCALE_CACHE.put(name, locale);
			} else if (parts.length > 1) {
				locale = new Locale(parts[0], parts[1]);
				LOCALE_CACHE.put(name, locale);
			} else if (parts.length > 0) {
				locale = new Locale(parts[0]);
				LOCALE_CACHE.put(name, locale);
			}
		}
		return locale;
	}

}