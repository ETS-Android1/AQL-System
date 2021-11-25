package de.foellix.aql.datastructure;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import de.foellix.aql.Log;
import de.foellix.aql.SimSmsTest;
import de.foellix.aql.datastructure.handler.QueryHandler;

class QueryToStringTest extends SimSmsTest {
	@BeforeAll
	public static void setup() {
		Log.setLogLevel(Log.DEBUG);
		Log.setPrefixEnabled(false);
	}

	@Test
	public void test01() {
		final String query = "Flows IN App('" + SIM_APP + "') ?";
		assertTrue(test(query));
	}

	@Test
	public void test02() {
		final String query = "Permissions IN App('" + SIM_APP + "') ?";
		assertTrue(test(query));
	}

	@Test
	public void test03() {
		final String query = "Flows IN App('" + SIM_APP + "' | 'TEST') ?";
		assertTrue(test(query));
	}

	@Test
	public void test04() {
		final String query = "Flows IN App('" + SIM_APP + "' | 'TEST' | 'TEST2') ?";
		assertTrue(test(query));
	}

	@Test
	public void test05() {
		final String query = "Flows IN App('" + SIM_APP + "') ?\nPermissions IN App('" + SIM_APP + "') ?";
		assertTrue(test(query));
	}

	@Test
	public void test06() {
		final String query = "Flows FROM App('" + SIM_APP + "') TO App('" + SMS_APP + "') ?";
		assertTrue(test(query));
	}

	@Test
	public void test07() {
		final String query = "FILTER [\n\tFlows IN App('" + SIM_APP + "') ?\n] ?";
		assertTrue(test(query));
	}

	@Test
	public void test08() {
		final String query = "FILTER [\n\tFlows IN App('" + SIM_APP + "') ? | 'complete' = 'true'\n] ?";
		assertTrue(test(query));
	}

	@Test
	public void test09() {
		final String query = "FILTER [\n\tFlows IN App('" + SIM_APP + "') ? | '' = 'true'\n] ?";
		assertTrue(test(query));
	}

	@Test
	public void test10() {
		final String query = "FILTER [\n\tFlows IN App('" + SIM_APP + "') ? | 'complete' = ''\n] ?";
		assertTrue(test(query));
	}

	@Test
	public void test11() {
		final String query = "FILTER [\n\tFlows IN App('" + SIM_APP + "') ? | Flows\n] ?";
		assertTrue(test(query));
	}

	@Test
	public void test12() {
		final String query = "FILTER [\n\tFlows IN App('" + SIM_APP + "') ? | 'complete' = 'true' | Flows\n] ?";
		assertTrue(test(query));
	}

	@Test
	public void test13() {
		final String query = "CONNECT [\n\tFlows IN App('" + SIM_APP + "') ?,\n\tFlows IN App('" + SMS_APP
				+ "') ?\n] ?";
		assertTrue(test(query));
	}

	@Test
	public void test14() {
		final String query = "CONNECT [\n\tFILTER [\n\t\tFlows IN App('" + SIM_APP + "') ?\n\t] ?,\n\tFlows IN App('"
				+ SMS_APP + "') ?\n] ?";
		assertTrue(test(query));
	}

	@Test
	public void test15() {
		final String query = "'test.xml' ?";
		assertTrue(test(query));
	}

	@Test
	public void test16() {
		final String query = "FILTER [\n\t'test.xml' !\n] ?";
		assertTrue(test(query));
	}

	// Some additional tests for AQL 2.0
	@Test
	public void test17() {
		final String query = "FILTER [\n\tFlows IN App('" + SIM_APP + "') ? | App('" + SIM_APP + "')\n] ?";
		assertTrue(test(query));
	}

	@Test
	public void test18() {
		final String query = "{ Flows IN App('" + SIM_APP + "') ? }";
		assertTrue(test(query));
	}

	@Test
	public void test19() {
		final String query = "{ Flows IN App('" + SIM_APP + "') ? }\n{ Permissions IN App('" + SIM_APP + "') ? }";
		assertTrue(test(query));
	}

	@Test
	public void test20() {
		final String query = "{ Flows IN App('" + SIM_APP + "') FEATURING { Arguments IN App('" + SIM_APP
				+ "') . } ? }";
		assertTrue(test(query));
	}

	@Test
	public void test21() {
		final String query = "{ Flows IN App('" + SIM_APP + "') WITH 'SAS' = 'SorucesAndSinks.txt' ? }";
		assertTrue(test(query));
	}

	@Test
	public void test22() {
		final String query = "Flows IN App('" + SIM_APP
				+ "') WITH 'SAS' = 'SorucesAndSinks.txt', 'Callbacks' = 'AndroidCallbacks.txt' ?";
		assertTrue(test(query));
	}

	@Test
	public void test23() {
		final String query = "Flows IN App('" + SIM_APP
				+ "') WITH 'Callbacks' = 'AndroidCallbacks.txt', 'SAS' = { Sources IN App('" + SIM_APP
				+ "') WITH 'extra' = 'extra.txt' ? } ?";
		assertTrue(test(query));
	}

	@Test
	public void test24() {
		final String query = "Flows IN App('" + SIM_APP + "') FEATURING 'FlowDroid', 'Native' ?";
		assertTrue(test(query));
	}

	@Test
	public void test25() {
		final String query = "Flows IN App({ Slice FROM App('" + SIM_APP + "') TO App('" + SIM_APP + "') ! }) ?";
		assertTrue(test(query));
	}

	@Test
	public void test26() {
		final String query = "FILTER [\n\tUNIFY [\n\t\tFlows IN App('" + SIM_APP + "') ?,\n\t\tSources IN App('"
				+ SIM_APP + "') ?\n\t] ? | 'converted' = { Arguments IN App('" + SIM_APP + "') . }\n] ?";
		assertTrue(test(query));
	}

	@Test
	public void test27() {
		final String query = "Flows IN App('" + SIM_APP + "') WITH 'SourcesAndSinks' = { TOFD [\n\tSources IN App('"
				+ SIM_APP + "') ?\n] ! } ?";
		assertTrue(test(query));
	}

	@Test
	public void test28() {
		final String query = "Flows IN App({ Slice FROM Statement('$r6 = virtualinvoke $r5.<android.telephony.TelephonyManager: java.lang.String getDeviceId()>()')->Method('<com.example.deviceid_broadcast.broadcast_deviceid: void onReceive(android.content.Context,android.content.Intent)>')->Class('com.example.deviceid_broadcast.broadcast_deviceid')->App('"
				+ SIM_APP
				+ "') ! }) WITH 'SourcesAndSinks' = '/media/sf_brewJicer/env/FlowDroid/DroidBench/SourcesAndSinks.txt' ?";
		assertTrue(test(query));
	}

	private static boolean test(String query) {
		final String temp = QueryHandler.parseQuery(query).toString();
		if (!temp.equals(query)) {
			Log.msg("Test failed:\n'" + query + "'\n'" + temp + "'\n", Log.DEBUG);
			return false;
		} else {
			Log.msg("Test passed!\n", Log.DEBUG);
			return true;
		}
	}
}