import com.sarang.torang.data.feed.Feed
import com.sarang.torang.data.feed.visibleLike
import org.junit.Assert.assertEquals
import org.junit.Test
import java.util.Calendar
import java.util.Calendar.DAY_OF_MONTH
import java.util.Calendar.MONTH
import java.util.Calendar.SEPTEMBER
import java.util.Calendar.YEAR

class FeedTest {

    @Test
    fun calendarToDatestamp() {
        //test data
        val feed = Feed(
            reviewId = 0,
            commentAmount = 0,
            contents = "contents",
            isFavorite = false,
            isLike = false,
            likeAmount = 10,
            name = "name",
            profilePictureUrl = "profilePictureUrl",
            rating = 3.5f,
            restaurantId = 0,
            restaurantName = "restaurantName",
            userId = 0
        )
        //if like amount is zero the like comment is invisible
        assertEquals(feed.visibleLike, true)
    }

    @Test
    fun datestampToCalendar() {

    }
}
