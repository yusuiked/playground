package org.yukung.tutorial.junit;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;
import static org.yukung.tutorial.junit.IsDate.*;

import java.util.Date;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class DateTest {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testDate() {
		assertThat(new Date(), is(dateOf(2012, 1, 12)));
	}

}
