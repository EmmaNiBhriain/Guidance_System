package wit.org.guidancesystem

import org.junit.Test

import org.junit.Assert.*
import wit.org.guidancesystem.activities.RoomGraph

/**
 * Unit test for testing the RoomGraph Class
 */
class RoomGraphTests {

    private val roomGraph = RoomGraph()

    /**
     * Test that calculating the date for four days ago works
     */
    @Test
    fun testGetDate(){
        val pastDate = roomGraph.getDate(4)
        assertEquals("24/4/2019", pastDate)
    }




}
