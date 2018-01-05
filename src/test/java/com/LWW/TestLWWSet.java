package com.LWW;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;

public class TestLWWSet {

	@Test
	public void testAddElementToSet() throws Exception {
		RecordRegister objLWWSet = new RecordRegister();
		Map<String, Long> testMap = new HashMap<String, Long>();
		Long ts = System.currentTimeMillis();
		testMap.put("1", ts);

		objLWWSet.addElementToRecordSet(testMap);

		for (Object key : objLWWSet.objAddSet.addSet.keySet()) {
			Assert.assertEquals("1", key);
			Assert.assertEquals(ts, objLWWSet.objAddSet.addSet.get(key));
		}
	}
 
	@Test
	public void testAddElementToSetdifferentTimeStamp() throws Exception {
		RecordRegister objLWWSet = new RecordRegister();
		Map<String, Long> testMap = new HashMap<String, Long>();
		testMap.put("1", System.currentTimeMillis());

		objLWWSet.addElementToRecordSet(testMap);

		for (Object key : objLWWSet.objAddSet.addSet.keySet()) {
			Assert.assertEquals("1", key);
			Assert.assertNotSame(System.currentTimeMillis(),
					objLWWSet.objAddSet.addSet.get(key));
		}
	}

	@Test
	public void testRemoveElementFromSet() throws Exception {
		RecordRegister objLWWSet = new RecordRegister();
		Map<String, Long> testMap = new HashMap<String, Long>();
		Long ts = System.currentTimeMillis();
		testMap.put("1", ts);

		objLWWSet.removeElementFromRecordSet(testMap);

		for (Object key : objLWWSet.objRemoveSet.removeSet.keySet()) {
			Assert.assertEquals("1", key);
			Assert.assertEquals
			(ts, objLWWSet.objRemoveSet.removeSet.get(key));
		}
	}

	@Test
	public void testRemoveElementFromSetDifferentTimeStamp() throws Exception {
		RecordRegister objLWWSet = new RecordRegister();
		Map<String, Long> testMap = new HashMap<String, Long>();
		testMap.put("1", System.currentTimeMillis());

		objLWWSet.removeElementFromRecordSet(testMap);

		for (Object key : objLWWSet.objRemoveSet.removeSet.keySet()) {
			Assert.assertEquals("1", key);
			Assert.assertNotSame(System.currentTimeMillis(),
					objLWWSet.objRemoveSet.removeSet.get(key));
		}
	}

	@Test
	public void testIsElementMemberOfLWWSet() throws Exception {

		RecordRegister objLWWSet = new RecordRegister();
		Map<String, Long> elementMap = new HashMap<String, Long>();
		Map<String, Long> mergedMap = new HashMap<String, Long>();
		Map<String, Long> removedMap = new HashMap<String, Long>();

		long ts = System.currentTimeMillis();
		elementMap.put("1", ts);
		mergedMap.put("1", ts);
		removedMap.put("1", ts);

		boolean result = objLWWSet.isElementMemberOfRecordSet(elementMap,
				mergedMap, removedMap);

		Assert.assertEquals(true, result);

	}
	
	@Test
	public void testIsElementMemberOfLWWSetConditionTwo() throws Exception {

		RecordRegister objLWWSet = new RecordRegister();
		Map<String, Long> elementMap = new HashMap<String, Long>();
		Map<String, Long> mergedMap = new HashMap<String, Long>();
		Map<String, Long> removedMap = new HashMap<String, Long>();

		long ts = System.currentTimeMillis();
		elementMap.put("1", ts);
		mergedMap.put("1", ts);
		removedMap.put("2", ts);
		boolean result = objLWWSet.isElementMemberOfRecordSet(elementMap,
				mergedMap, removedMap);

		Assert.assertEquals(true, result);

	}
	
	@Test
	public void testIsElementMemberOfLWWSetConditionThree() throws Exception {

		RecordRegister objLWWSet = new RecordRegister();
		Map<String, Long> elementMap = new HashMap<String, Long>();
		Map<String, Long> mergedMap = new HashMap<String, Long>();
		Map<String, Long> removedMap = new HashMap<String, Long>();
		removedMap.put("1", System.currentTimeMillis());
		long ts = System.currentTimeMillis();
		elementMap.put("1", ts);
		mergedMap.put("1", ts+ts);
		
		boolean result = objLWWSet.isElementMemberOfRecordSet(elementMap,
				mergedMap, removedMap);

		Assert.assertEquals(true, result);

	}

	@Test
	public void testMergeSets() throws Exception {
		
		
		List<Map> listOfLWWAdd = new ArrayList<Map>();
    	List<Map> listOfLWWRemove = new ArrayList<Map>();
    	
    	StringBuilder keys = new StringBuilder();
    	StringBuilder values = new StringBuilder();
    	
    	RecordRegister set1 = new RecordRegister();
    	Map map1  = new HashMap<String, String>();
    	Map map2  = new HashMap<String, String>();
    	Map map3  = new HashMap<String, String>();
    	Map map4  = new HashMap<String, String>();
    	
    	map1.put("1", System.currentTimeMillis()+1);
    	set1.addElementToRecordSet(map1);
    	map2.put("2", System.currentTimeMillis()+2);
    	set1.addElementToRecordSet(map2);
    	map3.put("3", System.currentTimeMillis()+3);
    	set1.addElementToRecordSet(map3);
    	map4.put("4", System.currentTimeMillis()+4);
    	set1.addElementToRecordSet(map4);
    	map3.put("3", System.currentTimeMillis()+5);
    	set1.removeElementFromRecordSet(map3);
    	
    	/*listOfMaps.add(map1);
    	listOfMaps.add(map2);
    	listOfMaps.add(map3);
    	listOfMaps.add(map4);*/
    	
    	String elementKey = null; 
   		for (Object key : set1.objAddSet.addSet.keySet()) {
   		   keys.append(key +", ");
   		   values.append(set1.objAddSet.addSet.get(key)+", ");
   		}
   		
   		System.out.println("keys of add lww1 are " + keys);
		System.out.println("values of add lww1 " + values);
		
		keys.setLength(0);
		values.setLength(0);
		
   		for (Object key : set1.objRemoveSet.removeSet.keySet()) {
   			keys.append(key +", ");
    		values.append(set1.objRemoveSet.removeSet.get(key)+", ");
   		}
   		
   		System.out.println("keys of remove lww1 are " + keys);
		System.out.println("values of remove lww1 " + values);
   		 
   		listOfLWWAdd.add(set1.objAddSet.addSet);
   		listOfLWWRemove.add(set1.objRemoveSet.removeSet);
    	
    	RecordRegister set2 = new RecordRegister();
    	Map map5  = new HashMap<String, String>();
    	Map map6  = new HashMap<String, String>();
    	Map map7  = new HashMap<String, String>();
    	Map map8  = new HashMap<String, String>();
    	
    	map5.put("5", System.currentTimeMillis()+6);
    	set2.addElementToRecordSet(map5);
    	map6.put("6", System.currentTimeMillis()+7);
    	set2.addElementToRecordSet(map6);
    	map7.put("7", System.currentTimeMillis()+8);
    	set2.addElementToRecordSet(map7);
    	map8.put("8", System.currentTimeMillis()+9);
    	set2.addElementToRecordSet(map8);
    	map7.put("7", System.currentTimeMillis()+10);
    	set2.removeElementFromRecordSet(map7);
    	
    	map3.put("3", System.currentTimeMillis()+11);
    	set2.addElementToRecordSet(map3);
    	
    	/*listOfMaps.add(map5);
    	listOfMaps.add(map6);
    	listOfMaps.add(map7);
    	listOfMaps.add(map8);*/
    	
    	keys.setLength(0);
    	values.setLength(0);
    	
    	for (Object key : set2.objAddSet.addSet.keySet()) {
   		    keys.append(key + ", ");
   		    values.append(set2.objAddSet.addSet.get(key) + ", ");
   		}
    	System.out.println("keys of add lww2 are " + keys);
		System.out.println("values of add lww2 " + values);
		
		keys.setLength(0);
		values.setLength(0);
		
    	for (Object key : set2.objRemoveSet.removeSet.keySet()) {
   		    keys.append(key + ", ");
   		    values.append(set2.objRemoveSet.removeSet.get(key)+ ", ");
   		}
    	
    	System.out.println("keys of remove lww2 are " + keys);
		System.out.println("values of remove lww2 " + values);
			
    	listOfLWWAdd.add(set2.objAddSet.addSet);
    	listOfLWWRemove.add(set2.objRemoveSet.removeSet);
    	
    	RecordRegister lws = new RecordRegister();
    	
    	//Map<String, Long> addResult = lws.mergeSets(s1.as.addSet, s2.as.addSet);
    	Map<String, Long> addResult = lws.mergeRecordSets(listOfLWWAdd);
    	
    	keys.setLength(0);
    	values.setLength(0);
    	
    	for (Object key : addResult.keySet()) {
   		    keys.append(key + ", ");
   		    values.append( addResult.get(key) + ", ");
   		}
    	System.out.println("keys of merged add are " + keys);
    	System.out.println("values of merged addd " + values);
    	
    	//Map<String, Long> removeResult = lws.mergeSets(s1.rs.removeSet, s2.rs.removeSet);
    	Map<String, Long> removeResult = lws.mergeRecordSets(listOfLWWRemove);
    	
    	keys.setLength(0);
    	values.setLength(0);
    	for (Object key : removeResult.keySet()) {
   		    keys.append(key + ", ");
   		    values.append(removeResult.get(key) + ", ");
   		}
    	System.out.println("keys of merged remove are " + keys);
		System.out.println("values of merged remove " + values);
    	
    	Boolean b = lws.isElementMemberOfRecordSet(map2, addResult, removeResult);
    	
    	System.out.println("map2 exists " + b);
    	
    	b = lws.isElementMemberOfRecordSet(map7, addResult, removeResult);
    	
    	System.out.println("map7 exists " + b);
    	
    	b = lws.isElementMemberOfRecordSet(map3, addResult, removeResult);

    	System.out.println("map3 exists " + b);
		
		
		
	}

}
