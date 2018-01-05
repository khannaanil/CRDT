package com.LWW;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 
 * @author anil.khanna
 * This class caters to the records management including adding new records, deleting records, and merging records.
 */
public class RecordRegister {

	AddRecordSet objAddSet = new AddRecordSet();
	RemoveRecordSet objRemoveSet = new RemoveRecordSet();

	public void addElementToRecordSet(Map<String, Long> record) throws Exception {
		String recordKey = null;
		Long recordValue = null;
		for (String key : record.keySet()) {
			recordKey = key;
			recordValue = record.get(key);
		}
		this.objAddSet.addSet.put(recordKey, recordValue);
	}

	public void removeElementFromRecordSet(Map<String, Long> record) throws Exception {
		String recordKey = null;
		Long recordValue = null;
		for (String key : record.keySet()) {
			recordKey = key;
			recordValue = record.get(key);
		}
		this.objRemoveSet.removeSet.put(recordKey, recordValue);
	}

	public Boolean isElementMemberOfRecordSet(Map<String, Long> record,
			Map<String, Long> mergedSet, Map<String, Long> removedSet) throws Exception {

		String recordKey = null;
		for (String key : record.keySet()) {
			recordKey = key;
		}

		if (mergedSet.containsKey(recordKey)
				&& !removedSet.containsKey(recordKey)) {
			return true;
		} else if (mergedSet.containsKey(recordKey)
				&& removedSet.containsKey(recordKey)) {

			Object addSetTS = mergedSet.get(recordKey);
			Object removeSetTS = removedSet.get(recordKey);

			if ((Long) addSetTS >= (Long) removeSetTS) {
				return true;
			}
		}
		return false;
	}

	public Map<String, Long> mergeRecordSets(List<Map> recordList) throws Exception {

		if (recordList.isEmpty())
			return null;

		if (recordList.size() == 1)
			return recordList.get(0);

		Map<String, Long> mergedMap = new HashMap<String, Long>();

		Set<String> keySetBase = recordList.get(0).keySet();
		Map<String, Long> baseElement = recordList.get(0);
		Set<String> intersectionKeySet = new HashSet<String>(keySetBase);

		for (Map element : recordList) {
			intersectionKeySet.retainAll(element.keySet());
		}

		Set<String> unionKeySet = new HashSet<String>(keySetBase);
		long baseVal;

		if (!intersectionKeySet.isEmpty()) {
			for (String mapIndex : intersectionKeySet) {
				baseVal = baseElement.get(mapIndex);
				for (Map map : recordList) {

					if (baseVal > (Long) map.get(mapIndex)) {
						mergedMap.put(mapIndex, baseVal);
					} else {
						mergedMap.put(mapIndex, (Long) map.get(mapIndex));
						baseVal = (Long) map.get(mapIndex);
					}
					map.keySet().remove(mapIndex);
					unionKeySet.addAll(map.keySet());
				}

				keySetBase.remove(mapIndex);
			}
		} else {
			for (Map map : recordList) {
				unionKeySet.addAll(map.keySet());
			}
		}

		for (String mapIndex : unionKeySet) {
			for (Map m : recordList) {
				if (m.get(mapIndex) != null) {
					mergedMap.put(mapIndex, (Long) m.get(mapIndex));
				}
			}
		}

		return mergedMap;
	}
}
