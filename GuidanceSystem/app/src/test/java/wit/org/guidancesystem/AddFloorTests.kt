package wit.org.guidancesystem
import org.junit.Test
import org.junit.Assert.*
import wit.org.guidancesystem.activities.AddFloor

/**
 * Unit test for testing the RoomGraph Class
 */
class AddFloorTests {

    private val addFloor = AddFloor()

    /**
     * Test that calculating the date for four days ago works
     */
    @Test
    fun testLargeVerifyDimensions(){
        var bigDimension = addFloor.validateDimension(2000)
        assertEquals(100, bigDimension)
    }

    @Test
    fun testSmallVerifyDimensions(){
        var smallDimension = addFloor.validateDimension(0)
        assertEquals(1, smallDimension)
    }

    @Test
    fun testValidVerifyDimensions(){
        var validDimension = addFloor.validateDimension(24)
        assertEquals(24, validDimension)
    }



}
