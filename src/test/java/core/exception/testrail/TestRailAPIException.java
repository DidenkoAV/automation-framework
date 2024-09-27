/**
 * TestRail API binding for Java (API v2, available since TestRail 3.0)
 *
 * Learn more:
 *
 * http://docs.gurock.com/testrail-api2/start
 * http://docs.gurock.com/testrail-api2/accessing
 *
 * Copyright Gurock Software GmbH. See license.md for details.
 */

package core.exception.testrail;
 
public class TestRailAPIException extends RuntimeException
{
	public TestRailAPIException(String message)
	{
		super(message);
	}
}
