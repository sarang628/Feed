import androidx.compose.material.Text
import androidx.compose.ui.test.assertIsDisplayed
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Rule
import org.junit.runner.RunWith
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import org.junit.Test


@RunWith(AndroidJUnit4::class)
class FeedTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun garden_emptyGarden() {
//        composeTestRule.setContent {
//            Text(text = "Add plant")
//        }
//        composeTestRule.onNodeWithText("Add plant").assertIsDisplayed()
    }
}